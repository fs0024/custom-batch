/**
 * 
 */
package com.trinet.benefits.oe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trinet.benefits.oe.model.BaseApiResponse;
import com.trinet.benefits.oe.service.TaskStepService;
import com.trinet.benefits.oe.service.impl.TaskStepServiceImpl;
import com.trinet.benefits.oe.web.controller.TaskStepController;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing BatchProcessController")
public class TaskStepControllerTest {
	
	
	private static TaskStepService taskStepService;
	private static TaskStepController taskStepController;
	
	@BeforeAll
	public static void init() {
		taskStepService = Mockito.mock(TaskStepServiceImpl.class);
		taskStepController = new TaskStepController(taskStepService);
	}
	
	
	@Test
	public void testUpdateTaskStepPreparedFlag() {
		 int taskStepId = 1;
		 BaseApiResponse response = new BaseApiResponse();
		 
		 Mockito.when(taskStepService.updateTaskStepPrepareFlag(taskStepId)).thenReturn(1);
		 
		 ResponseEntity<BaseApiResponse> expectedResponse = new ResponseEntity<>(response,HttpStatus.OK);
		 assertEquals(expectedResponse.getStatusCode(), taskStepController.updateTaskStepPreparedFlag(taskStepId).getStatusCode());
	}
	
	
	@Test
	public void testUpdateTaskStepPreparedFlagFailed() {
		 int taskStepId = 1;
		 BaseApiResponse response = new BaseApiResponse();
		 
		 Mockito.when(taskStepService.updateTaskStepPrepareFlag(taskStepId)).thenReturn(0);
		 
		 ResponseEntity<BaseApiResponse> expectedResponse = new ResponseEntity<>(response,HttpStatus.CONFLICT);
		 assertEquals(expectedResponse.getStatusCode(), taskStepController.updateTaskStepPreparedFlag(taskStepId).getStatusCode());
	}

}
