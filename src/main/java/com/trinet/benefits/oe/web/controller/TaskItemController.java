package com.trinet.benefits.oe.web.controller;

import static com.trinet.benefits.oe.web.Mappings.GET_TASK_ITEMS_BY_WORKER_ID;
import static com.trinet.benefits.oe.web.Mappings.UPDATE_TASK_ITEMS;
import static com.trinet.benefits.oe.web.Mappings.VERSION;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trinet.benefits.oe.common.PathVariables;
import com.trinet.benefits.oe.entity.TaskItem;
import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.model.BaseApiResponse;
import com.trinet.benefits.oe.service.TaskItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.trinet.benefits.oe.web.Mappings.*;

@RequestMapping(VERSION)
@RestController
public class TaskItemController {

	
	private TaskItemService taskItemService;
	
	
	@Autowired
	public TaskItemController(TaskItemService taskItemService) {
		this.taskItemService = taskItemService;
	}


	@GetMapping(GET_TASK_ITEMS_BY_WORKER_ID)
	public ResponseEntity<List<TaskItem>> findTaskItemByWorkerId(@PathVariable(PathVariables.WORKER_ID) String workerId,
			@PathVariable(PathVariables.TASK_STEP_ID) int taskStepId,
			@PathVariable(PathVariables.TASK_STATUS) String status,
			@PathVariable(PathVariables.PARTITION_SIZE) int partitionsize) {

		TaskStep taskStep = new TaskStep();
		taskStep.setId(taskStepId);

		List<TaskItem> listOfTaskItems = taskItemService.findTaskItemsForProcessing(workerId, status, taskStep,
				partitionsize);

		return new ResponseEntity<>(listOfTaskItems, HttpStatus.OK);

	}

	@PostMapping(UPDATE_TASK_ITEMS)
	public ResponseEntity<BaseApiResponse> updateTaskItemStatus(@PathVariable("status") Optional<String> status,
			@RequestBody List<TaskItem> list) {
		BaseApiResponse response = new BaseApiResponse();
		int count = taskItemService.updateTaskItemStatus(status, list);

		if (count == 0) {
			response.setStatusCode("FAILED");
			return new ResponseEntity<BaseApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		response.setStatusCode("SUCCESS");
		return new ResponseEntity<BaseApiResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/partionTaskItems/{taskStepId}/{workerCount}/{workerPrefix}")
	public ResponseEntity<BaseApiResponse> partitionTasks(@PathVariable("taskStepId") Integer taskStepid,
			@PathVariable("workerCount") Integer workerCount, @PathVariable("workerPrefix") String workerPrefix) {

		BaseApiResponse response = taskItemService.partitionItems(taskStepid, workerCount, workerPrefix);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("reconStatus/{stepId}")
	public ResponseEntity<String> reconStatus(@PathVariable("stepId") Integer taskStep) {
		TaskStep step = new TaskStep();
		step.setId(taskStep);
		try {
			taskItemService.reconsileAllStatus(step);
			return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/errorRecords/{stepId}")
	public ResponseEntity<List<TaskItem>> getErrorRecords(@PathVariable("stepId") Integer taskStep) {
		TaskStep step = new TaskStep();
		List<TaskItem> taskItems=new ArrayList<TaskItem>();
		//step.setId(taskStep);
		try {
			taskItems=taskItemService.getErrorRecordsForTaskItem(taskStep,"COMPLETED");
			return new ResponseEntity<>(taskItems, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(taskItems,HttpStatus.BAD_REQUEST);
		}

	}



}
