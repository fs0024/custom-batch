package com.trinet.benefits.oe.service;

public interface TaskStepService {
	public Integer getTaskStepIdByAppIdAndStep(String isPrepared, int taskId);
	public Integer updateTaskStepPrepareFlag(int taskStepId); 
}
