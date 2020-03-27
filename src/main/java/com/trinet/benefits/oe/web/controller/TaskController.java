package com.trinet.benefits.oe.web.controller;

import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.mapper.BatchProcessTaskMapper;
import com.trinet.benefits.oe.model.BatchProcessTaskModel;
import com.trinet.benefits.oe.service.BatchProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.trinet.benefits.oe.web.Mappings.*;

/**
 * @author dshah
 *
 */
@RequestMapping(VERSION)
@RestController
public class TaskController {

	
	private BatchProcessTaskService batchProcessTaskService;
	
	@Autowired
	public TaskController(BatchProcessTaskService batchProcessTaskService) {
		this.batchProcessTaskService = batchProcessTaskService;
	}

	@PostMapping(ADD_TASK)
	public ResponseEntity<BatchProcessTask> addTask(@RequestBody BatchProcessTaskModel batchProcessTaskModel) {
		BatchProcessTaskMapper batchProcessTaskMapper = new BatchProcessTaskMapper();
		BatchProcessTask response = new BatchProcessTask();
		BatchProcessTask batchProcessTask = batchProcessTaskMapper.transformToBatchProcessTask(batchProcessTaskModel);
        try {
	        BatchProcessTask saveTaskResponse = batchProcessTaskService.createTask(batchProcessTask);
			response.setId(saveTaskResponse.getId());
			response.setTrackingNumber(saveTaskResponse.getTrackingNumber());
			return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
         }

	}

	@GetMapping(GET_ALL_TASK+"/{batchProcessId}")
	public ResponseEntity<List<BatchProcessTask>> getAllTasks(@PathVariable("batchProcessId") Integer batchProcessId) {

		return new ResponseEntity<>(batchProcessTaskService.getAllTaskStepDetails(batchProcessId), HttpStatus.OK);
	}

	@GetMapping("/gettask/{batchprocesstaskId}")
	public ResponseEntity<BatchProcessTaskModel> getAllBatchParamValues(
			@PathVariable("batchprocesstaskId") Integer batchProcessTaskId) {

		return new ResponseEntity<>(batchProcessTaskService.getTaskModelDetails(batchProcessTaskId), HttpStatus.OK);
	}

}
