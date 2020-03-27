
package com.trinet.benefits.oe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessStep;
import com.trinet.benefits.oe.mapper.BatchProcessMapper;
import com.trinet.benefits.oe.model.BatchProcessModel;
import com.trinet.benefits.oe.service.BatchProcessService;
import com.trinet.benefits.oe.service.impl.BatchProcessServiceImpl;
import com.trinet.benefits.oe.service.impl.BatchProcesserService;
import com.trinet.benefits.oe.web.controller.BatchProcessController;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing BatchProcessController")
public class BatchProcessControllerTest {
	
	private static BatchProcessService batchProcessService;
	private static BatchProcesserService batchProcesserService;
	
	
	private static BatchProcessController batchProcessController;
	
	
	
	private static BatchProcessMapper batchProcessMapper;
	
	@BeforeAll
	public static void init() {
		
		batchProcessService = mock(BatchProcessServiceImpl.class);
		batchProcesserService = mock(BatchProcesserService.class);
		
		batchProcessMapper = mock(BatchProcessMapper.class);
		batchProcessController = new BatchProcessController(batchProcessService,batchProcesserService);
	}
	
	
	
	@Test
	public void testGetAllBatches() {
		List<BatchProcessModel> batchProcessModelList = new ArrayList<>();

		List<BatchProcess> batchProcessList = new ArrayList<>();
		
		when(batchProcessService.getAllBatchProcess()).thenReturn(batchProcessList);
		when(batchProcessMapper.transformToListofBatchProcessModel(batchProcessList)).thenReturn(batchProcessModelList);
		ResponseEntity<List<BatchProcessModel>> expectedResponse = new ResponseEntity<>(batchProcessModelList,HttpStatus.OK);
		ResponseEntity<List<BatchProcessModel>> actualResponse = batchProcessController.getAllBatches();
		assertEquals(expectedResponse, actualResponse);
		 
	}
	
	@Test
	public void testGetBatchDetail() {

		BatchProcess batchProcess = new BatchProcess();

		Optional<BatchProcess> batchProcessOpt = Optional.of(batchProcess);

		when(batchProcessService.getBatchProcessById(1)).thenReturn(batchProcessOpt);

		ResponseEntity<BatchProcess> expectedResponse = new ResponseEntity<>(batchProcess, HttpStatus.OK);
		ResponseEntity<BatchProcess> actualResponse = batchProcessController.getBatchDetail(1);

		assertEquals(expectedResponse, actualResponse);

	}
	
	@Test
	public void testGetBatchDetailNotFound() {

		BatchProcess batchProcess = new BatchProcess();

		Optional<BatchProcess> batchProcessOpt = Optional.empty();

		when(batchProcessService.getBatchProcessById(1)).thenReturn(batchProcessOpt);

		ResponseEntity<BatchProcess> expectedResponse = new ResponseEntity<>(batchProcess, HttpStatus.NOT_FOUND);
		ResponseEntity<BatchProcess> actualResponse = batchProcessController.getBatchDetail(1);

		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());

	}
	
	
	@Test
	public void testGetBatchDetailStringbatchProcessId() {

		List<BatchProcessStep> batchProcessStepList = new ArrayList<>() ;
		
		BatchProcessStep batchProcessStep = new BatchProcessStep();
		batchProcessStep.setId(1);
		batchProcessStep.setName("Rateband Updates");
		batchProcessStep.setDescription("Apply file updates to new clone");
		batchProcessStep.setBatchIdentifire("updateRateBands");
		
		batchProcessStepList.add(batchProcessStep);
		
		BatchProcess batchProcess = new BatchProcess();
		batchProcess.setBatchProcessStep(batchProcessStepList);
		
		String batchProcessId = "1";

		Optional<BatchProcess> batchProcessOpt = Optional.of(batchProcess);

		when(batchProcessService.getBatchProcessById(batchProcessId)).thenReturn(batchProcessOpt);

		ResponseEntity<BatchProcess> expectedResponse = new ResponseEntity<>(batchProcess, HttpStatus.OK);
		ResponseEntity<BatchProcess> actualResponse = batchProcessController.getBatchDetail(batchProcessId);

		assertEquals(expectedResponse, actualResponse);

	}
	
	
	@Test
	public void testGetBatchDetailStringbatchProcessIdNotFound() {

		List<BatchProcessStep> batchProcessStepList = new ArrayList<>() ;
		
		BatchProcessStep batchProcessStep = new BatchProcessStep();
		batchProcessStep.setId(1);
		batchProcessStep.setName("Rateband Updates");
		batchProcessStep.setDescription("Apply file updates to new clone");
		batchProcessStep.setBatchIdentifire("updateRateBands");
		
		batchProcessStepList.add(batchProcessStep);
		
		BatchProcess batchProcess = new BatchProcess();
		batchProcess.setBatchProcessStep(batchProcessStepList);
		
		String batchProcessId = "1";

		Optional<BatchProcess> batchProcessOpt = Optional.empty();

		when(batchProcessService.getBatchProcessById(batchProcessId)).thenReturn(batchProcessOpt);

		ResponseEntity<BatchProcess> expectedResponse = new ResponseEntity<>(batchProcess, HttpStatus.NOT_FOUND);
		ResponseEntity<BatchProcess> actualResponse = batchProcessController.getBatchDetail(batchProcessId);

		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());

	}
	
	
	/*@Test
	public void testRegisterBatchProcess() {
		String appId = "api-bnft-batch-engine";
		
		BatchProcessModel batchProcessModel = new BatchProcessModel();
		batchProcessModel.setId(1);
		batchProcessModel.setAppId(appId);
		batchProcessModel.setName("Test");
		batchProcessModel.setDescription("Test desc");
		
		BatchProcess batchProcess = new BatchProcess();
		batchProcess.setAppId(appId);
		
		when(batchProcessMapper.transformToBatchProcess(batchProcessModel)).thenReturn(batchProcess);
		
		when(batchProcessMock.getAppId()).thenReturn(appId);
		when(batchProcessService.registerProcess(batchProcess)).thenReturn(batchProcess);
		when(batchProcesserService.updateSchedule(batchProcessModel.getAppId())).thenReturn("Success");
		
		ResponseEntity<BatchProcess> expectedResponse = new ResponseEntity<>(batchProcess,HttpStatus.OK);
		ResponseEntity<BatchProcess> actualResponse = batchProcessController.registerBatchProcess(batchProcessModel);
		
		assertEquals(expectedResponse, actualResponse);
		
		
		
	}
	*/
	
	

}
