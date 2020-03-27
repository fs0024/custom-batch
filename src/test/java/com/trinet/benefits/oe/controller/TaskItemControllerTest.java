/**
 * 
 */
package com.trinet.benefits.oe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hpsf.Array;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.trinet.benefits.oe.common.PathVariables;
import com.trinet.benefits.oe.entity.TaskItem;
import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.model.BaseApiResponse;
import com.trinet.benefits.oe.service.TaskItemService;
import com.trinet.benefits.oe.service.impl.TaskItemServiceImpl;
import com.trinet.benefits.oe.web.controller.TaskItemController;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing TaskItemController")
public class TaskItemControllerTest {
	
	
	private static TaskItemService taskItemService;
	private static TaskItemController taskItemController;
	
	@BeforeAll
	public static void init() {
		taskItemService = Mockito.mock(TaskItemServiceImpl.class);
		taskItemController = new TaskItemController(taskItemService);
		

	}
	
	
	@Test
	public void testFindTaskItemByWorkerId() {
		String workerId = "Worker-1";
		int taskStepId = 1;
		String status = "IN_PROCESS";
		int partitionsize = 5;
		TaskStep taskStep = new TaskStep();
		taskStep.setId(1);
		
	
		
		List<TaskItem> listOfTaskItems = new ArrayList<>();
		
		Mockito.when(taskItemService.findTaskItemsForProcessing(workerId, status, taskStep,
				partitionsize)).thenReturn(listOfTaskItems);
		ResponseEntity<List<TaskItem>> expectedResponse = new ResponseEntity<>(listOfTaskItems,HttpStatus.OK);
		assertEquals(expectedResponse, taskItemController.findTaskItemByWorkerId(workerId, taskStepId, status, partitionsize));
		
		
		
	}
	
	
	
	@Test
	public void testUpdateTaskItemStatus() {
		Optional<String> status = Optional.empty();
		List<TaskItem> list = new ArrayList<>();
		String statusMsg = "SUCCESS";

		BaseApiResponse expectedBaseApiResponse = new BaseApiResponse();
		expectedBaseApiResponse.setStatus(statusMsg);

		Mockito.when(taskItemService.updateTaskItemStatus(status, list)).thenReturn(1);
		ResponseEntity<BaseApiResponse> expectedReponse = new ResponseEntity<>(expectedBaseApiResponse, HttpStatus.OK);
		
		assertEquals(expectedReponse.getStatusCode(), taskItemController.updateTaskItemStatus(status, list).getStatusCode());
		
	}
	
	
	@Test
	public void testUpdateTaskItemStatusZeroCount() {
		Optional<String> status = Optional.empty();
		List<TaskItem> list = new ArrayList<>();
		String statusMsg = "FAILED";

		BaseApiResponse expectedBaseApiResponse = new BaseApiResponse();
		expectedBaseApiResponse.setStatus(statusMsg);

		Mockito.when(taskItemService.updateTaskItemStatus(status, list)).thenReturn(0);
		ResponseEntity<BaseApiResponse> expectedReponse = new ResponseEntity<>(expectedBaseApiResponse, HttpStatus.BAD_REQUEST);
		
		assertEquals(expectedReponse.getStatusCode(), taskItemController.updateTaskItemStatus(status, list).getStatusCode());
		
	}
	
	
	@Test
	public void testPartitionTasks() {
		Integer taskStepid = 1;
		Integer workerCount = 1; 
		String workerPrefix = "test";
		
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		
		Mockito.when(taskItemService.partitionItems(taskStepid, workerCount, workerPrefix)).thenReturn(baseApiResponse);
		
		ResponseEntity<BaseApiResponse> expectedResponse = new ResponseEntity<>(baseApiResponse,HttpStatus.OK);
		assertEquals(expectedResponse, taskItemController.partitionTasks(taskStepid, workerCount, workerPrefix));
	}
	
	
	@Test
	public void testReconStatus() {
		Integer taskStep = 1;
		TaskStep step = new TaskStep();
		step.setId(taskStep);
		String responseString = "SUCCESS";
		ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseString,HttpStatus.OK);
		
		assertEquals(expectedResponse, taskItemController.reconStatus(taskStep));
		
		  
	}
	
	


	

}
