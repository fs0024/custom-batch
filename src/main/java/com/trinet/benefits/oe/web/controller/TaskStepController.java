package com.trinet.benefits.oe.web.controller;

import static com.trinet.benefits.oe.web.Mappings.UPDATE_PREPARED_FLAG;
import static com.trinet.benefits.oe.web.Mappings.VERSION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trinet.benefits.oe.common.PathVariables;
import com.trinet.benefits.oe.model.BaseApiResponse;
import com.trinet.benefits.oe.service.TaskStepService;

@RequestMapping(VERSION)
@RestController
public class TaskStepController {

	
	private TaskStepService taskStepService;

	@Autowired
	public TaskStepController(TaskStepService taskStepService) {
		this.taskStepService = taskStepService;
	}
	
	@GetMapping(UPDATE_PREPARED_FLAG)
	public ResponseEntity<BaseApiResponse> updateTaskStepPreparedFlag(@PathVariable(PathVariables.TASK_STEP_ID) int taskStepId) {

		int count = taskStepService.updateTaskStepPrepareFlag(taskStepId);
		BaseApiResponse response = new BaseApiResponse();
		if (count == 1) {

			response.setStatusCode("SUCCESS");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setStatusCode("FAILED");
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

}
