package com.trinet.benefits.oe.service.impl;

import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.entity.TaskItem;
import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.model.BaseApiResponse;
import com.trinet.benefits.oe.repository.BatchProcessTaskRepository;
import com.trinet.benefits.oe.repository.TaskItemRepository;
import com.trinet.benefits.oe.repository.TaskStepsRepository;
import com.trinet.benefits.oe.service.TaskItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class TaskItemServiceImpl implements TaskItemService {

	@Autowired
	private TaskItemRepository taskItemRepository;

	@Autowired
	private TaskStepsRepository taskStepsRepository;

	@Autowired
	private BatchProcessTaskRepository batchProcessTaskRepository;

	@PersistenceContext(unitName = "batchJobPersistenceUnit")
	@Autowired
	@Qualifier("batchJobEntityManager")
	private EntityManager batchJobEntityManager;

	@Transactional
	@Override
	public void saveItems(List<TaskItem> taskItems) {
		taskItemRepository.saveAll(taskItems);
	}

	
	

	@Override
	public List<TaskItem> findTaskItemsForProcessing(String workerId, String status, TaskStep taskStep,
			int partitionSize) {

		Pageable pageable = PageRequest.of(0, partitionSize);

		List<TaskItem> items = taskItemRepository.findTaskItemByWorkerIdAndTaskStepAndStatus(workerId, taskStep, status,
				pageable);

		for (TaskItem item : items) {
			item.setStatus("PROCESSING");
		}

		taskItemRepository.saveAll(items);

		return items;
	}

	@Override
	@Transactional
	public Integer updateTaskItemStatus(Optional<String> status, List<TaskItem> items) {
		Integer updateCount = 0;
		if (!items.isEmpty()) {
			if (status.isPresent()) {
				for (TaskItem item : items) {
					if (item != null) {
						if (item.getFileItems() != null) {
							item.getFileItems().setTaskItem(item);
						}

						item.setStatus(status.get());
					}

				}
			}
			updateCount = this.taskItemRepository.saveAll(items).size();
			// Reconcile all Status for Task Step
			Integer pendingrec = taskItemRepository.countByStatusAndTaskStep("Ready", items.get(0).getTaskStep());
			if (pendingrec == 0) {
				TaskStep taskStep = taskStepsRepository.findById(items.get(0).getTaskStep().getId()).orElse(null);
				if (taskStep != null) {
					taskStep.setStatus("COMPLETED");
					taskStep.setFinishTime(getCurrentTimeStamp());
					taskStepsRepository.save(taskStep);
					// Reconcile status for batch process task
					BatchProcessTask batchProcessTask = taskStep.getBatchProcessTask();
					Integer taskStepC = taskStepsRepository.countRecsForStatus(batchProcessTask.getId(), "COMPLETED");//WAITING_INPUT
					Integer taskStepW = taskStepsRepository.countRecsForStatus(batchProcessTask.getId(), "WAITING_INPUT") ;
					Integer taskStepInv = taskStepsRepository.countRecsForStatus(batchProcessTask.getId(), "INVALID_INPUT") ;
					Integer taskStepP = taskStepsRepository.countRecsForStatus(batchProcessTask.getId(), "SOME_RECORDS_FAILED") ;
					if(taskStepW>0)
						taskStepC = taskStepC+taskStepW ;
					if(taskStepP>0)
						taskStepC = taskStepC +taskStepP ;

					if(taskStepInv>0)
						taskStepC = taskStepC +taskStepInv ;

					BatchProcess process = batchProcessTask.getBatchProcess();
					if (taskStepC == process.getBatchProcessStep().size()) {
						batchProcessTask.setFinalStatus("COMPLETED");
						if(taskStepW>0 ||taskStepP>0 || taskStepInv>0)
							batchProcessTask.setFinalStatus("PARTIAL_COMPLETED");
						batchProcessTask.setFinishTime(getCurrentTimeStamp());
						batchProcessTaskRepository.save(batchProcessTask);
					}

				}
				//Lets update to partial if found failed
				Integer pendingrecF = taskItemRepository.countByStatusAndTaskStep("FAILED", taskStep);
				if(pendingrecF>0){
					taskStep.setStatus("SOME_RECORDS_FAILED");
					taskStepsRepository.save(taskStep);
				}
			}

		}
		return updateCount;
	}
	
	
	
	private String getCurrentTimeStamp() {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
		
		return new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a").format(timestamp1);
	}

	@Transactional
	@Override
	public BaseApiResponse partitionItems(Integer taskStepId, Integer workerCount, String workerPrefix) {

		TaskStep stepDetails = taskStepsRepository.findById(taskStepId).orElse(null);
		BaseApiResponse response = new BaseApiResponse();
		if (stepDetails != null && !stepDetails.getIsPrepared().equals('Y')) {

			Integer totalItems = taskItemRepository.countUnPartionedRecs(taskStepId);

			int chunkSize = 0;

			if (totalItems < workerCount) {
				chunkSize = totalItems;
			} else {
				chunkSize = totalItems / workerCount;
			}

			int remainingRecords = totalItems % workerCount;
			int incrementor = 0 ;

			for (int i = 0; i < workerCount; i++) {

				String worker = workerPrefix + "-" + (i + 1);

				if ((i + 1) == workerCount) {
					chunkSize = chunkSize + remainingRecords;
				}

				if(chunkSize/1000 >0)
				{
					for (int j=0 ; j< chunkSize/1000;j++)	{
						updateChunk(taskStepId,worker,incrementor+((j*1000)+1),incrementor+(j+1)*1000);
	                  }

					if(chunkSize%1000 >0){
						updateChunk(taskStepId,worker,incrementor+(((chunkSize/1000)*1000)+1),incrementor+chunkSize);
					}



				}else {

					updateChunk(taskStepId,worker,incrementor+1,incrementor+chunkSize);

				}
				incrementor += chunkSize ;

				if(totalItems < workerCount) {
					break;
				}

			}
			stepDetails.setIsPrepared('Y');
			taskStepsRepository.save(stepDetails);
			response.setStatus("SUCCESS");
			response.setStatusCode("DONE");

		} else {

			response.setStatus("ALREADY_PARTITIONED");
			response.setStatusCode("FAILED");
		}

		return response;
	}


	private void updateChunk(Integer taskStepId,String worker , Integer strtIndex,Integer endIndex){
		try{
			List<Integer> list = taskItemRepository.getItemsInRange(taskStepId, strtIndex, endIndex);
			if(list!= null && list.size()>0) {
				Integer count = taskItemRepository.updateTaskItemPartion(list, worker);
			}

		}catch (Exception e){
			log.error("error in partitioning the for  step id: " + taskStepId);

		}

	}

	@Override
	@Transactional
	public void reconsileAllStatus(TaskStep step) throws Exception {
		try {
			// Reconsile all Status for Task Step
			Integer pendingrec = taskItemRepository.countByStatusAndTaskStep("Ready", step);
			if (pendingrec == 0) {
				TaskStep taskStep = taskStepsRepository.findById(step.getId()).orElse(null);
				if (taskStep != null) {
					taskStep.setStatus("COMPLETED");
					taskStepsRepository.save(taskStep);
					// Reconsile status for batch process task
					BatchProcessTask batchProcessTask = taskStep.getBatchProcessTask();
					Integer taskStepC = taskStepsRepository.countRecsForStatus(batchProcessTask.getId(), "COMPLETED");
					Integer taskStepW = taskStepsRepository.countRecsForStatus(batchProcessTask.getId(), "WAITING_INPUT") ;
					Integer taskStepInv = taskStepsRepository.countRecsForStatus(batchProcessTask.getId(), "INVALID_INPUT") ;
					Integer taskStepP = taskStepsRepository.countRecsForStatus(batchProcessTask.getId(), "SOME_RECORDS_FAILED") ;
					if(taskStepW>0)
						taskStepC = taskStepC+taskStepW ;
					if(taskStepP>0)
						taskStepC = taskStepC +taskStepP ;

					if(taskStepInv>0)
						taskStepC = taskStepC +taskStepInv ;

					BatchProcess process = batchProcessTask.getBatchProcess();
					if (taskStepC == process.getBatchProcessStep().size()) {
						batchProcessTask.setFinalStatus("COMPLETED");
						if(taskStepW>0 ||taskStepP>0 || taskStepInv>0)
							batchProcessTask.setFinalStatus("PARTIAL_COMPLETED");
						batchProcessTaskRepository.save(batchProcessTask);
					}

				}

			   //Lets update to partial if found failed
				Integer pendingrecF = taskItemRepository.countByStatusAndTaskStep("FAILED", step);
				if(pendingrecF>0){
					taskStep.setStatus("SOME_RECORDS_FAILED");
					taskStepsRepository.save(taskStep);
				}
			}
		} catch (Exception e) {

			throw e;
		}
	}
	
	@Override
	public List<TaskItem> getErrorRecordsForTaskItem(Integer taskStep,String status){
		
		//List<TaskItem> items= taskItemRepository.getErrorRecordsForTaskItem(taskStep);
		TaskStep step=new TaskStep();
		step.setId(taskStep);
		
		List<TaskItem> taskItems=taskItemRepository.findByTaskStepAndStatusNot(step, status);
		
		System.out.println("size.."+taskItems.size());
		
		return taskItems;
		

	}

}
