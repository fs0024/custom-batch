package com.trinet.benefits.oe.service.impl;

import com.trinet.benefits.oe.entity.*;
import com.trinet.benefits.oe.model.BatchProcessTaskModel;
import com.trinet.benefits.oe.repository.*;
import com.trinet.benefits.oe.service.BatchProcessService;
import com.trinet.benefits.oe.service.BatchProcessTaskService;
import com.trinet.benefits.oe.utils.RegexValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Log4j2
public class BatchProcessTaskServiceImpl implements BatchProcessTaskService {

	@Autowired
	private BatchProcessTaskRepository batchProcessTaskRepository;

	@Autowired
	private BatchParamValueRepository batchParamValueRepository;

	@Autowired
	private BatchParamRepository batchParamRepository;

	@Autowired
	private BatchProcessService batchProcessService;

	@Autowired
	private RegexValidator regexValidator;

	@Autowired
	private BatchProcessParamMapRepository batchProcessParamMapRepository;

	@Override
	public BatchProcessTask createTask(BatchProcessTask batchProcessTask) {

		// Lets populate database copy
		if (batchProcessTask.getId() != null) {

			BatchProcessTask dbbatchProcessTaskCopy = batchProcessTaskRepository.findById(batchProcessTask.getId())
					.orElse(null);

			if (dbbatchProcessTaskCopy == null) {
				throw new RuntimeException("Fatal error during task creation ");
			} else if (!dbbatchProcessTaskCopy.getFinalStatus().equalsIgnoreCase("PENDING")){
				throw new RuntimeException("Update not allowed at this moment  ");
			}
			else {
				batchProcessTask.setTaskSteps(dbbatchProcessTaskCopy.getTaskSteps());
				batchProcessTask.setTrackingNumber(dbbatchProcessTaskCopy.getTrackingNumber());
			}

		} else {
			BatchProcess process = batchProcessTask.getBatchProcess();
			List<BatchProcessStep> stepMasterList = process.getBatchProcessStep();
			List<TaskStep> stepList = new ArrayList<>();
			for (BatchProcessStep batchStep : stepMasterList) {
				TaskStep step = new TaskStep();
				step.setBatchProcessStep(batchStep);
				step.setIsPrepared('N');
				step.setBatchProcessTask(batchProcessTask);
				stepList.add(step);

			}
			batchProcessTask.setTaskSteps(stepList);
		}

		List<BatchParamValue> parameterList = batchProcessTask.getBatchParamValues();
		// Determine step creation

		List<TaskStep> taskSteps = batchProcessTask.getTaskSteps();
		Map<Integer, Boolean> validatiomMap = new HashMap<Integer, Boolean>();
		for (BatchParamValue paramValue : parameterList) {
			BatchParam param = batchParamRepository.findById(paramValue.getBatchParam().getId()).orElse(null);
			if (param != null) {

				if (!param.getType().equalsIgnoreCase("FILE")) {
					String validatetion = param.getValidation();
					String value = paramValue.getValue();
					if (regexValidator.validatate(validatetion, value)) {
						validatiomMap.put(param.getId(), true);
						paramValue.setValidationFlag('T');
					} else {
						paramValue.setValidationFlag('F');
						validatiomMap.put(param.getId(), false);

					}

				} else {

					if (paramValue.getValue().equalsIgnoreCase("NO_FILE")) {
						paramValue.setValidationFlag('F');
						validatiomMap.put(param.getId(), false);

					} else {

						validatiomMap.put(param.getId(), null);
					}

				}

			} else {

				throw new RuntimeException("Issue with configuration");
			}

		}

		for (TaskStep step : taskSteps) {

			if (step.getStatus() != null && (step.getStatus().equalsIgnoreCase("IN_PROCESS")
					|| step.getStatus().equalsIgnoreCase("COMPLETED"))) {
				continue;
			}
			BatchProcessStep batchProcessStep = step.getBatchProcessStep();

			// Get all params for this batchProcessStep . s
			List<BatchProcessParamMap> paramList = batchProcessParamMapRepository
					.findByBatchProcessStep(batchProcessStep);
			for (BatchProcessParamMap map : paramList) {
				BatchParam batchParam = map.getBatchParam();
				if (!batchParam.getType().equalsIgnoreCase("FILE")) {
					if (!validatiomMap.get(batchParam.getId())) {
						step.setStatus("INVALID_INPUT");
						break;
					}
				} else {
					Boolean validation = validatiomMap.get(batchParam.getId());
					if (validation == null) {

						step.setStatus("VALIDATION_IN_PROGRESS");
						break;
					}

					if (!validatiomMap.get(batchParam.getId())) {
						step.setStatus("WAITING_INPUT");
						break;
					}

				}

			}

			if (step.getStatus() == null || step.getStatus().isEmpty())
				step.setStatus("SCHEDULED");

		}
		// Assign tracking number if not aasigned

		if (batchProcessTask.getTrackingNumber() == null) {
			Integer maxTrackingNumber = batchProcessTaskRepository.findMaxTrackingNumber();

			if (maxTrackingNumber == null) {
				maxTrackingNumber = 0;
			}
			
			
			batchProcessTask.setTrackingNumber(maxTrackingNumber + 1);
		}

		return batchProcessTaskRepository.save(batchProcessTask);
	}

	@Override
	public List<BatchProcessTask> getAllTaskStepDetails(Integer batchProcessId) {

		BatchProcess batchProcess = new BatchProcess();
		batchProcess.setId(batchProcessId);

		return batchProcessTaskRepository.findBatchProcessTaskByBatchProcessOrderByStartDateDesc(batchProcess);

	}

	@Override
	public String cancelTask(Integer batchProcessTaskId) {
		BatchProcessTask task = batchProcessTaskRepository.findById(batchProcessTaskId).orElse(null);
		if(task!= null){

			task.setFinalStatus("Cancelled");
			batchProcessTaskRepository.save(task);
		}
		return "Updated";
	}



	@Override
	@Transactional("transactionManager")
	public BatchProcessTask getUnPrepTask(String appId, Optional<String> stepIdentifier) {

		BatchProcessTask response = null;
		BatchProcess batchProcess = batchProcessService.getBatchProcessById(appId).orElse(null);
		
		log.info("fetching task for date: "+LocalDate.now());

		List<BatchProcessTask> list = batchProcessTaskRepository
				.findByBatchProcessAndFinalStatusAndStartDateLessThanEqual(batchProcess, "PENDING", LocalDate.now());

		if (!list.isEmpty()) {
			log.info(list.size() + " : tasks found for partitioning process");
			boolean found = false;
			for (BatchProcessTask task : list) {
				if (!task.getTaskSteps().isEmpty()) {
					for (TaskStep step : task.getTaskSteps()) {
						if (stepIdentifier.isPresent()) {
							if (step.getIsPrepared() != null && step.getStatus().equalsIgnoreCase("SCHEDULED")
									&& step.getIsPrepared().equals('N') && step.getBatchProcessStep()
											.getBatchIdentifire().equalsIgnoreCase(stepIdentifier.get())) {
								log.info(task.getId() + "tracking no" + task.getTrackingNumber()
										+ ": returning unprep task ");
								response = task;
								found = true;
								break;
							}

						} else {
							if (step.getIsPrepared() != null && step.getStatus().equalsIgnoreCase("SCHEDULED")
									&& step.getIsPrepared().equals('N')) {
								log.info(task.getId() + "tracking no" + task.getTrackingNumber()
										+ ": returning unprep task ");
								response = task;
								found = true;
								break;
							}
						}
					}

				}

				if (found) {
					// Lock the task
					response.setFinalStatus("LOCKED");
					batchProcessTaskRepository.save(response);
					return response;
				}
			}

		} else {

			log.info("No task found for partitioning");
		}

		return response;
	}

	@Override
	@Transactional("transactionManager")
	public BatchProcessTask getPrepedTask(String appId, Optional<String> stepIdentifier) {

		BatchProcessTask response = null;
		BatchProcess batchProcess = batchProcessService.getBatchProcessById(appId).orElse(null);

		List<BatchProcessTask> list = batchProcessTaskRepository
				.findByBatchProcessAndFinalStatusAndStartDateLessThanEqual(batchProcess, "IN_PROCESS", LocalDate.now());
		log.info(list.size() + " : prepared tasks found ");
		if (!list.isEmpty()) {
			boolean found = false;
			for (BatchProcessTask task : list) {
				if (!task.getTaskSteps().isEmpty()) {
					for (TaskStep step : task.getTaskSteps()) {
						if (stepIdentifier.isPresent()) {
							if (step.getIsPrepared() != null && step.getStatus().equalsIgnoreCase("SCHEDULED")
									&& step.getIsPrepared().equals('Y') && step.getBatchProcessStep()
											.getBatchIdentifire().equalsIgnoreCase(stepIdentifier.get())) {
								log.info(task.getId() + "tracking no" + task.getTrackingNumber()
										+ ": returning prepared task ");
								response = task;
								found = true;
								break;
							}

						} else {
							if (step.getIsPrepared() != null && step.getStatus().equalsIgnoreCase("SCHEDULED")
									&& step.getIsPrepared().equals('Y')) {
								log.info(task.getId() + "tracking no" + task.getTrackingNumber()
										+ ": returning prepared task ");
								response = task;
								found = true;
								break;
							}
						}
					}

				}

				if (found) {
					// Lock the task
					response.setFinalStatus("LOCKED");
					batchProcessTaskRepository.save(response);
					return response;
				}
			}

		} else {
			log.info("No prepared task found");
		}
		return response;
	}

	@Override
	public boolean updateTaskStatus(Integer id, String status) {
		BatchProcessTask task = batchProcessTaskRepository.findById(id).orElse(null);
		if (task != null) {
			task.setFinalStatus(status);
			batchProcessTaskRepository.save(task);
			return true;
		}
		return false;
	}

	@Override
	public boolean unLockTask(Integer id) {
		BatchProcessTask task = batchProcessTaskRepository.findById(id).orElse(null);
		if (task != null && !task.getFinalStatus().equalsIgnoreCase("COMPLETED")
				&& !task.getFinalStatus().equalsIgnoreCase("PARTIAL_COMPLETED")) {
			task.setFinalStatus("IN_PROCESS");
			batchProcessTaskRepository.save(task);
			return true;
		}
		return false;
	}

	@Override
	public BatchProcessTaskModel getTaskModelDetails(Integer batchProcessTaskId) {
		BatchProcessTask batchProcessTask = batchProcessTaskRepository.findById(batchProcessTaskId).orElse(null);

		if (batchProcessTask == null) {
			throw new RuntimeException("Fatal error while updating task");
		}

		List<BatchParamValue> batchParamValues = batchParamValueRepository.findByBatchProcessTask(batchProcessTask);

		BatchProcessTaskModel batchProcessTaskModel = new BatchProcessTaskModel();
		batchProcessTaskModel.setBatchParamValues(batchParamValues);
		batchProcessTaskModel.setStartDate(batchProcessTask.getStartDate());
		batchProcessTaskModel.setBatchProcess(batchProcessTask.getBatchProcess());
		batchProcessTaskModel.setId(batchProcessTaskId);
		batchProcessTaskModel.setFinalStatus(batchProcessTask.getFinalStatus());

		return batchProcessTaskModel;
	}

}
