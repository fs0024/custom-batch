package com.trinet.benefits.oe.web.controller;

import static com.trinet.benefits.oe.web.Mappings.GET_ALL_BATCH_PROCESS;
import static com.trinet.benefits.oe.web.Mappings.GET_BATCH_BY_ID;
import static com.trinet.benefits.oe.web.Mappings.REGISTER_BATCH;
import static com.trinet.benefits.oe.web.Mappings.VERSION;

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
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessStep;
import com.trinet.benefits.oe.mapper.BatchProcessMapper;
import com.trinet.benefits.oe.model.BatchProcessModel;
import com.trinet.benefits.oe.service.BatchProcessService;
import com.trinet.benefits.oe.service.impl.BatchProcesserService;
import com.trinet.benefits.oe.web.Mappings;

/**
 * @author dshah
 *
 */
@RequestMapping(VERSION)
@RestController
public class BatchProcessController {

	
	private  BatchProcessService batchProcessService;
	
	private BatchProcesserService batchProcesserService;
	
	@Autowired
	public BatchProcessController(BatchProcessService batchProcessService,BatchProcesserService batchProcesserService) {
		this.batchProcesserService = batchProcesserService;
		this.batchProcessService = batchProcessService;
	}

	@GetMapping(GET_ALL_BATCH_PROCESS)
	public ResponseEntity<List<BatchProcessModel>> getAllBatches() {
		BatchProcessMapper batchProcessMapper = new BatchProcessMapper();
		
		
		List<BatchProcessModel> batchProcessModelList = batchProcessMapper
				.transformToListofBatchProcessModel(batchProcessService.getAllBatchProcess());

		return new ResponseEntity<>(batchProcessModelList, HttpStatus.OK);
	}

	@GetMapping(Mappings.GET_BATCH_BY_APP_ID)
	public ResponseEntity<BatchProcess> getBatchDetail(@PathVariable(PathVariables.PROCESS_ID) String batchProcessId) {

		Optional<BatchProcess> batchProcess = batchProcessService.getBatchProcessById(batchProcessId);

		if (batchProcess.isPresent()) {
			batchProcess.get().setBatchProcessTasks(null);
			for (BatchProcessStep step : batchProcess.get().getBatchProcessStep()) {
				step.setTaskSteps(null);
			}
			return new ResponseEntity<>(batchProcess.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(GET_BATCH_BY_ID)
	public ResponseEntity<BatchProcess> getBatchDetail(@PathVariable(PathVariables.PROCESS_ID) int batchProcessId) {

		Optional<BatchProcess> batchProcess = batchProcessService.getBatchProcessById(batchProcessId);

		if (batchProcess.isPresent())
			return new ResponseEntity<>(batchProcess.get(), HttpStatus.OK);
		else 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(REGISTER_BATCH)
	public ResponseEntity<BatchProcess> registerBatchProcess(@RequestBody BatchProcessModel batchProcessModel) {

		BatchProcessMapper batchProcessMapper = new BatchProcessMapper();
		BatchProcess batchProcess = batchProcessService
				.registerProcess(batchProcessMapper.transformToBatchProcess(batchProcessModel));

		BatchProcess returnResponse = new BatchProcess();
		returnResponse.setAppId(batchProcess.getAppId());
		
		batchProcesserService.updateSchedule(batchProcessModel.getAppId());

		return new ResponseEntity<>(returnResponse, HttpStatus.OK);
	}
	
	
	@GetMapping("/stopSchedule/{appId}")
	public ResponseEntity<String> stopSchedule(@PathVariable("appId") String appId) {
		
		String response = batchProcesserService.stopSchedule(appId);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/stopjobs/{appId}")
	public ResponseEntity<String> stopJobs(@PathVariable("appId") String appId) {
		
		String response = batchProcesserService.stopJobs(appId);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/startSchedule/{appId}")
	public ResponseEntity<String> startSchedule(@PathVariable("appId") String appId) {
		
		String response = batchProcesserService.updateSchedule(appId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
