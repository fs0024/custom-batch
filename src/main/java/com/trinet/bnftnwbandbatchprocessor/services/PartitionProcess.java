package com.trinet.bnftnwbandbatchprocessor.services;

import java.util.ArrayList;
import java.util.List;

import com.trinet.bnftnwbandbatchprocessor.util.PeopleSoftServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trinet.bnftnwbandbatchprocessor.tasks.RateBandCloneTask;
import com.trinet.bnftnwbandbatchprocessor.tasks.UpdateRateBandsTask;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcessTask;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PartitionProcess implements TaskletProcess {

	@Autowired
	private PeopleSoftServiceImpl peopleSoftService;

	@Value("${batch.worker.count}")
	private Integer workercount;

	@Autowired
	private BatchApiService batchApiService;

	@Autowired
	private InsertItemService insertItemService;

	@Override
	public boolean processtask(Object... args) {

		try {
			BatchProcessTask task = (BatchProcessTask) args[0];
			BatchParamValue quarter = (BatchParamValue) args[1];

			if (!task.getTaskSteps().isEmpty())

			{

				for (TaskStep step : task.getTaskSteps()) {
					if (step.getIsPrepared() != null && step.getStatus().equalsIgnoreCase("SCHEDULED")
							&& step.getIsPrepared().equals('N') && step.getBatchProcessStep().getBatchIdentifire()
									.equalsIgnoreCase(RateBandCloneTask.STEP_NAME)) {

						// Removed broken process orphan data if any.
						insertItemService.removeOrphans(step);

						// getting pfclient count
						int pfClientCount = peopleSoftService.getPFClientsCount(quarter.getValue());
						log.info("Total Client count :" + pfClientCount);
						// determining outerloop limits and remaining records
						Integer partionBarrier = 100;
						int chunkSize = pfClientCount / workercount;
						int remainingRecords = pfClientCount % workercount;
						int loopEndIndex = workercount + 1;

						if (remainingRecords == 0) {
							loopEndIndex = workercount;
						}

						int startIndex = 1;
						int endIndex = chunkSize;

						for (int j = 1; j <= loopEndIndex; j++) {

							// getting PFclients according to start and end index
							List<String> pfClients = peopleSoftService.getPFClients(quarter.getValue(), startIndex,
									endIndex);

							log.info("Client List :" + pfClients);
							int size = pfClients.size();

							List<TaskItem> taskItems = new ArrayList<>();

							// make task items for saving in TASK_ITEM table
							for (int i = 0; i < size; i++) {
								TaskItem item = new TaskItem();
								item.setItemId(pfClients.get(i));
								item.setItemIdDesc("PfClients");
								item.setDeleted('N');
								if (j == workercount + 1) {
									item.setWorkerId("Worker-" + 1);
								} else {
									item.setWorkerId("Worker-" + j);
								}

								item.setStatus("Ready");
								item.setTaskStep(step);

								taskItems.add(item);
								if (taskItems.size() == partionBarrier) {
									insertItemService.insertItems(taskItems);
									taskItems = new ArrayList<>();
								}

							}

							// setting the start and end index
							startIndex = endIndex + 1;
							if (j == workercount) {
								endIndex = endIndex + remainingRecords;
							} else {
								endIndex = endIndex + chunkSize;
							}

							// saving items to Task_Items table
							insertItemService.insertItems(taskItems);

						}

						batchApiService.updarePrepFlag(step.getId());
					}
					if (step.getIsPrepared() != null && step.getStatus().equalsIgnoreCase("Scheduled")
							&& step.getIsPrepared().equals('N') && step.getBatchProcessStep().getBatchIdentifire()
									.equalsIgnoreCase(UpdateRateBandsTask.STEP_NAME)) {
						batchApiService.preparePartiton(step.getId(), "Worker", workercount);
					}

				}
			}

		} catch (Exception e) {

			log.error("Error starting  partioning data for rate band ");
		}

		return true;
	}

}
