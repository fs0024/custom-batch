/**
 * 
 */
package com.trinet.benefits.oe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.mapper.BatchProcessTaskMapper;
import com.trinet.benefits.oe.model.BatchProcessTaskModel;
import com.trinet.benefits.oe.service.BatchProcessTaskService;
import com.trinet.benefits.oe.service.impl.BatchProcessTaskServiceImpl;
import com.trinet.benefits.oe.web.controller.TaskController;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing TaskController")
public class TaskControllerTest {
	
	
	private static BatchProcessTaskService batchProcessTaskService;
	private static BatchProcessTaskMapper batchProcessTaskMapper;
	private static BatchProcessTask responseSpy;
	private static BatchProcessTask batchProcessTaskObj;
	private static TaskController taskControllerSpy;
	private static TaskController taskController;
	
	@BeforeAll
	public static void init() {
		batchProcessTaskService = Mockito.mock(BatchProcessTaskServiceImpl.class);
		batchProcessTaskMapper = Mockito.mock(BatchProcessTaskMapper.class);
		
		taskControllerSpy = Mockito.spy(new TaskController(batchProcessTaskService));
		responseSpy = Mockito.mock(BatchProcessTask.class);
		taskController = new TaskController(batchProcessTaskService);
	}
	
	
	/*@Test
	public void testAddTask() throws Exception {
		responseSpy = Mockito.mock(BatchProcessTask.class);
		BatchProcessTaskModel batchProcessTaskModel = new BatchProcessTaskModel();
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		BatchProcessTask saveTaskResponse = new BatchProcessTask();
		saveTaskResponse.setId(1);
		saveTaskResponse.setTrackingNumber("1");
		
		//PowerMockito.whenNew(BatchProcessTask.class).withAnyArguments().thenReturn(responseSpy);
		
		Mockito.when(batchProcessTaskMapper.transformToBatchProcessTask(batchProcessTaskModel)).thenReturn(batchProcessTask);
		
		Mockito.when(batchProcessTaskService.createTask(batchProcessTask)).thenReturn(saveTaskResponse);
		
		ResponseEntity<BatchProcessTask> expectedResponse = new ResponseEntity<>(batchProcessTask,HttpStatus.OK);
		
		ResponseEntity<BatchProcessTask> actualResponse = taskControllerSpy.addTask(batchProcessTaskModel);
		
		assertEquals(expectedResponse, actualResponse);
		
		
	}*/
	
	
	@Test
	public void testGetAllTasks() {
		Integer batchProcessId = 1;
		List<BatchProcessTask> batchProcessTaskList = new ArrayList<>();

		Mockito.when(batchProcessTaskService.getAllTaskStepDetails(batchProcessId)).thenReturn(batchProcessTaskList);

		ResponseEntity<List<BatchProcessTask>> expectedResponse = new ResponseEntity<>(batchProcessTaskList,
				HttpStatus.OK);
		ResponseEntity<List<BatchProcessTask>> actualResponse = taskController.getAllTasks(batchProcessId);
		assertEquals(expectedResponse, actualResponse);
	}
	
	
	@Test
	public void testGetAllBatchParamValues() {
		BatchProcessTaskModel batchProcessTaskModel = new BatchProcessTaskModel();
		Integer batchProcessTaskId = 1;
		
		Mockito.when(batchProcessTaskService.getTaskModelDetails(batchProcessTaskId)).thenReturn(batchProcessTaskModel);
		ResponseEntity<BatchProcessTaskModel> expectedResponse = new ResponseEntity<>(batchProcessTaskModel,HttpStatus.OK);
		
		assertEquals(expectedResponse, taskController.getAllBatchParamValues(batchProcessTaskId));
		
		
		
	}

}
