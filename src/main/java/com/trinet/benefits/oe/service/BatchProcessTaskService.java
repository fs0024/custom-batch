package com.trinet.benefits.oe.service;

import java.util.List;
import java.util.Optional;

import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.model.BatchProcessTaskModel;

public interface BatchProcessTaskService {
	
	public BatchProcessTask createTask(BatchProcessTask batchProcessTask);

	public List<BatchProcessTask> getAllTaskStepDetails(Integer batchProcessId);

	public String cancelTask(Integer batchProcessTaskId);

	public BatchProcessTask getUnPrepTask(String appId , Optional<String> stepIdentifier) ;

	public BatchProcessTask getPrepedTask(String appId, Optional<String> stepIdentifier);

	public boolean updateTaskStatus(Integer id,String status);

	public boolean unLockTask(Integer id);
	
	public BatchProcessTaskModel getTaskModelDetails(Integer batchProcessTaskId);

	
}
