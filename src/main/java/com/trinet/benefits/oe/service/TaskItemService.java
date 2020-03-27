package com.trinet.benefits.oe.service;

import java.util.List;
import java.util.Optional;

import com.trinet.benefits.oe.entity.TaskItem;
import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.model.BaseApiResponse;

public interface TaskItemService {
	public void saveItems(List<TaskItem> taskItems);

	

	public Integer updateTaskItemStatus(Optional<String> status, List<TaskItem> taskStepId);

	public BaseApiResponse partitionItems(Integer taskStepId, Integer workerCount, String workerPrefix);

	public void reconsileAllStatus(TaskStep step) throws Exception;
	
	public List<TaskItem> findTaskItemsForProcessing(String workerId, String status, TaskStep taskStep,
			int partitionSize);
	
	public List<TaskItem> getErrorRecordsForTaskItem(Integer taskStep,String status);
}
