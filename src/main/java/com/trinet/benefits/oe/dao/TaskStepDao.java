/**
 * 
 */
package com.trinet.benefits.oe.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @author imistry1
 *
 * 
 */
@Repository
public interface TaskStepDao {
	
	void createTaskStep(Integer batchProcessStepId, Integer batchProcessTaskId, String status);
	void updateTaskStep(Integer batchProcessStepId, Integer batchProcessTaskId, String status);

	void updateTaskStepByTaskId(Integer batchProcessTaskId, String status);
	Integer getTaskStepId(Integer batchProcessStepId, Integer batchProcessTaskId);
	Map<Integer,String> getAllTaskStepIdStatusByProcessTaskId(Integer batchProcessTaskId);

	public Integer getTaskStepIdByAppIdAndStep(String stepIdentifier, int taskId);
	public Integer updateTaskStepPrepareFlag(int taskStepId);

	public Integer countRecsForStatus(Integer taskStepId, String status);


}
