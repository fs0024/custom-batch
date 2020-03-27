package com.trinet.benefits.oe.web.controller;


import static com.trinet.benefits.oe.web.Mappings.GET_BATCH_PARAMS;
import static com.trinet.benefits.oe.web.Mappings.VERSION;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trinet.benefits.oe.common.PathVariables;
import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.mapper.BatchParamMapper;
import com.trinet.benefits.oe.model.BatchParamModel;
import com.trinet.benefits.oe.service.BatchParamService;

/**
 * @author dshah
 *
 */
@RequestMapping(VERSION)
@RestController
public class BatchParamController {

	private final BatchParamService batchParamService;

	@Autowired
	public BatchParamController(BatchParamService batchParamService) {
		this.batchParamService = batchParamService;
	}

	// This controller gets batch parameters associated with batch process
	@GetMapping(GET_BATCH_PARAMS)
	public ResponseEntity<List<BatchParamModel>> getParams(@PathVariable(PathVariables.PROCESS_ID) int processId) {

		BatchProcess batchProcess = new BatchProcess();
		batchProcess.setId(processId);

		List<BatchParam> batchParams = batchParamService.getBatchParamsByBatchProcessId(batchProcess);
		
		BatchParamMapper batchParamMapper = new BatchParamMapper();
		
		List<BatchParamModel> batchParamModelList = batchParamMapper.transformToListOfBatchParamModel(batchParams);

		return new ResponseEntity<>(batchParamModelList, HttpStatus.OK);

	}


	@GetMapping("getTaskParams/{id}")
	public ResponseEntity<List<BatchParamValue>> getTaskParams(@PathVariable("id") Integer id) {

		BatchProcessTask batchProcessTask = new BatchProcessTask();
		batchProcessTask.setId(id);

		List<BatchParamValue> batchParamValueList = batchParamService.getBatchParamValuesByBatchTask(batchProcessTask);

		return new ResponseEntity<>(batchParamValueList, HttpStatus.OK);
	}

}
