/**
 * 
 */
package com.trinet.benefits.oe.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trinet.benefits.oe.service.impl.BatchProcesserService;
import com.trinet.benefits.oe.web.controller.ProcessorController;



/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing ProcessorController")
public class ProcessorControllerTest {
	
	
	private static BatchProcesserService batchProcesserService;
	private static ProcessorController processorController;
	private static String batchName = "test";
	
	
	@BeforeAll
	public static void init() {
		batchProcesserService = mock(BatchProcesserService.class);
		processorController = new ProcessorController(batchProcesserService);
		
	}
	
	@Test
	public void testRunNow() {
		
		
		when(batchProcesserService.runNow(batchName)).thenReturn(batchName);
		ResponseEntity<String> expectedResponse = new ResponseEntity<>(batchName,HttpStatus.OK);
		ResponseEntity<String> actualResponse = processorController.runNow(batchName);
		assertEquals(expectedResponse, actualResponse);
		
	}
	
	
	@Test
	public void testProcessorHealth() {
		
		when(batchProcesserService.processorHealth(batchName)).thenReturn(batchName);
		ResponseEntity<String> expectedResponse = new ResponseEntity<>(batchName,HttpStatus.OK);
		ResponseEntity<String> actualResponse = processorController.processorHealth(batchName);
		assertEquals(expectedResponse, actualResponse);
		
	}
	
	
	
	


}


