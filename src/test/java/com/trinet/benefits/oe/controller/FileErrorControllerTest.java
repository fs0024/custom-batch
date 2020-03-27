
package com.trinet.benefits.oe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trinet.benefits.oe.entity.FileErrorLog;
import com.trinet.benefits.oe.service.FileErrorLogService;
import com.trinet.benefits.oe.service.impl.FileErrorLogServiceImpl;
import com.trinet.benefits.oe.web.controller.FileErrorController;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing FileErrorController")
public class FileErrorControllerTest {
	
	
	@Autowired
	private static FileErrorLogService fileErrorLogService;
	
	private static FileErrorController fileErrorController;

	
	@BeforeAll
	public static void init() {
		fileErrorLogService = mock(FileErrorLogServiceImpl.class);
		fileErrorController = new FileErrorController(fileErrorLogService);
		
	}
	
	
	@Test
	public void testGetAllFileErrors() {
		Integer batchProcessTaskId = 1;
		Integer batchProcessStepId = 1;
		
		List<FileErrorLog> fileErrorLogList = new ArrayList<>();
		
		when(fileErrorLogService.getFileErrorDetails(batchProcessTaskId,batchProcessStepId)).thenReturn(fileErrorLogList);
		
		ResponseEntity<List<FileErrorLog>> expectedResponse = new ResponseEntity<>(fileErrorLogList,HttpStatus.OK);
		
		assertEquals(expectedResponse, fileErrorController.getAllFileErrors(batchProcessTaskId, batchProcessStepId));
		
	}
	

}
