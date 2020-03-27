package com.trinet.benefits.oe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.repository.TaskStepsRepository;
import com.trinet.benefits.oe.service.TaskStepService;

@Service
public class TaskStepServiceImpl implements TaskStepService {



	@Autowired
	private TaskStepsRepository taskStepsRepository ;



	@Override
	public Integer getTaskStepIdByAppIdAndStep(String stepIdentifier, int taskId) {
		return taskStepsRepository.getTaskStepIdByAppIdAndStep(stepIdentifier, taskId);
	}

	@Override
	public Integer updateTaskStepPrepareFlag(int taskStepId) {
		  TaskStep	step  = taskStepsRepository.findById(taskStepId).orElse(null) ;
		  if(null != step && (step.getIsPrepared() == null || step.getIsPrepared().equals('N')) ){
			  step.setIsPrepared('Y');
			  taskStepsRepository.save(step);
			  return 1 ;
		  }
		return 0;


	}

}
