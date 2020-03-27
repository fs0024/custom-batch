
package com.trinet.benefits.oe.controller;

import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.model.BaseApiResponse;
import com.trinet.benefits.oe.service.BatchProcessTaskService;
import com.trinet.benefits.oe.service.impl.BatchProcessTaskServiceImpl;
import com.trinet.benefits.oe.web.controller.BatchProcessTaskController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing BatchProcessTaskController")
public class BatchProcessTaskControllerTest {
	
	private static BatchProcessTaskService batchProcessTaskService;
	private static BatchProcessTaskController batchProcessTaskController;
	private static String appId = "testAppId";
	private static Optional<String> stepIdentifier = Optional.of("testStepId");
	
	@BeforeAll
	public static void init() {
		batchProcessTaskService = mock(BatchProcessTaskServiceImpl.class);
		batchProcessTaskController = new BatchProcessTaskController(batchProcessTaskService);
		
	}
	
	@Test
	public void testGetUnPrepTask() {
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		
		when(batchProcessTaskService.getUnPrepTask(appId,stepIdentifier)).thenReturn(batchProcessTask);
		ResponseEntity<BatchProcessTask> expectedBatchProcessTask = new ResponseEntity<>(batchProcessTask,HttpStatus.OK);
		ResponseEntity<BatchProcessTask> actualBatchProcessTask = batchProcessTaskController.getUnPrepTask(appId,stepIdentifier); 
		assertEquals(expectedBatchProcessTask, actualBatchProcessTask);
		
	}
	
	@Test
	public void testGetUnPrepTaskNotFound() {
		BatchProcessTask batchProcessTask = null;
		
		when(batchProcessTaskService.getUnPrepTask(appId,stepIdentifier)).thenReturn(batchProcessTask);
		ResponseEntity<BatchProcessTask> expectedBatchProcessTask = new ResponseEntity<>(batchProcessTask,HttpStatus.NOT_FOUND);
		ResponseEntity<BatchProcessTask> actualBatchProcessTask = batchProcessTaskController.getUnPrepTask(appId,stepIdentifier); 
		assertEquals(expectedBatchProcessTask, actualBatchProcessTask);
		
	}
	
	
	@Test
	public void testGetPreparedTask() {
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		when(batchProcessTaskService.getPrepedTask(appId,stepIdentifier)).thenReturn(batchProcessTask);
		ResponseEntity<BatchProcessTask> expectedBatchProcessTask = new ResponseEntity<>(batchProcessTask,HttpStatus.OK);
		ResponseEntity<BatchProcessTask> actualBatchProcessTask = batchProcessTaskController.getPreparedTask(appId,stepIdentifier); 
		assertEquals(expectedBatchProcessTask, actualBatchProcessTask);
		
	}
	
	@Test
	public void testGetPreparedTaskNotFound() {
		BatchProcessTask batchProcessTask = null;
		when(batchProcessTaskService.getPrepedTask(appId,stepIdentifier)).thenReturn(batchProcessTask);
		ResponseEntity<BatchProcessTask> expectedBatchProcessTask = new ResponseEntity<>(batchProcessTask,HttpStatus.NOT_FOUND);
		ResponseEntity<BatchProcessTask> actualBatchProcessTask = batchProcessTaskController.getPreparedTask(appId,stepIdentifier); 
		assertEquals(expectedBatchProcessTask, actualBatchProcessTask);
		
	}
	
	
	@Test
	public void testUpdateTaskStatus() {
		Integer id = 1;
		String status = "Success";
		
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		when(batchProcessTaskService.updateTaskStatus(id,status)).thenReturn(true);
		ResponseEntity<BaseApiResponse> expectedResponse = new ResponseEntity<>(baseApiResponse,HttpStatus.OK);
		ResponseEntity<BaseApiResponse> actualResponse = batchProcessTaskController.updateTaskStatus(id,status);
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
	}
	
	@Test
	public void testUpdateTaskStatusConflict() {
		Integer id = 1;
		String status = "Success";
		
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		when(batchProcessTaskService.updateTaskStatus(id,status)).thenReturn(false);
		ResponseEntity<BaseApiResponse> expectedResponse = new ResponseEntity<>(baseApiResponse,HttpStatus.CONFLICT);
		ResponseEntity<BaseApiResponse> actualResponse = batchProcessTaskController.updateTaskStatus(id,status);
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
	}
	
	
	@Test
	public void testUpdateTaskStatusOnlyId() {
		Integer id = 1;
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		when(batchProcessTaskService.unLockTask(id)).thenReturn(true);
		ResponseEntity<BaseApiResponse> expectedResponse = new ResponseEntity<>(baseApiResponse,HttpStatus.OK);
		ResponseEntity<BaseApiResponse> actualResponse = batchProcessTaskController.updateTaskStatus(id);
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		
	}
	
	
	@Test
	public void testUpdateTaskStatusOnlyIdConflict() {
		Integer id = 1;
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		when(batchProcessTaskService.unLockTask(id)).thenReturn(false);
		ResponseEntity<BaseApiResponse> expectedResponse = new ResponseEntity<>(baseApiResponse,HttpStatus.OK);
		ResponseEntity<BaseApiResponse> actualResponse = batchProcessTaskController.updateTaskStatus(id);
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		
	}
	
	
	@Test
	public void testCancelTask() {
		Integer batchProcessTaskId = 1;
		String responseString = "Success";
		
		when(batchProcessTaskService.cancelTask(batchProcessTaskId)).thenReturn(responseString);
		ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseString,HttpStatus.OK);
		ResponseEntity<String> actualResponse = batchProcessTaskController.cancelTask(batchProcessTaskId);
		assertEquals(expectedResponse, actualResponse);
	}
	
	

}
