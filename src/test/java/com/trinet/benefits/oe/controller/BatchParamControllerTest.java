/**
 * 
 */
package com.trinet.benefits.oe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.mapper.BatchParamMapper;
import com.trinet.benefits.oe.model.BatchParamModel;
import com.trinet.benefits.oe.service.BatchParamService;
import com.trinet.benefits.oe.service.impl.BatchParamServiceImpl;
import com.trinet.benefits.oe.web.controller.BatchParamController;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing BatchParamController")
public class BatchParamControllerTest{
	
	
	private static BatchParamService batchParamService = mock(BatchParamServiceImpl.class);
	
	private static BatchParamController batchParamController;
	
	private static List<BatchParam> batchParamsList;
	private static List<BatchParamValue> batchParamValueList;
	
	private static int processId =1;
	
	private static BatchProcess batchProcess;

	private static BatchProcessTask batchProcessTask;
	
	private static ResponseEntity<List<BatchParamValue>> responseEntityParamValueList;
	
	private static BatchParamMapper batchParamMapper;
	
	

	@BeforeAll
	public static void init() {
		
		batchParamMapper  = mock(BatchParamMapper.class);
		
		batchParamController = new BatchParamController(batchParamService);
		
		batchProcess = new BatchProcess();
		batchProcess.setId(processId);
		
		batchProcessTask = new BatchProcessTask();
		batchProcess.setId(1);
		
		
		BatchParamValue batchParamValue = new BatchParamValue();
		batchParamValue.setId(1);
		batchParamValue.setValue("testFile.csv");
		
		batchParamValueList = new ArrayList<>();
		batchParamValueList.add(batchParamValue);
		
		
		BatchParam batchParam1 = new BatchParam();
		batchParam1.setId(1);
		batchParam1.setDescription("File");
		batchParam1.setName("File");
		batchParam1.setType("file");
		batchParam1.setValidation("asdf");
		batchParam1.setBatchParamValues(batchParamValueList);
		
		
		batchParamsList = new ArrayList<>();
		batchParamsList.add(batchParam1);
		
		responseEntityParamValueList = new ResponseEntity<>(batchParamValueList,HttpStatus.OK);
		
		
		
		
		
		
	}
	
	
	@Test
	@DisplayName("Get Task Params")
	public void testGetTaskParams() {
		
		when(batchParamService.getBatchParamValuesByBatchTask(batchProcessTask)).thenReturn(batchParamValueList);
		
		ResponseEntity<List<BatchParamValue>> response = batchParamController.getTaskParams(1);
		
		assertEquals(responseEntityParamValueList.getStatusCode(), response.getStatusCode());
		
	}
	
	
	@Test
	@DisplayName("Get Params")
	public void testGetParams() {
		
		List<BatchParam> batchParamsList1 = new ArrayList<>();
		List<BatchParamModel> batchParamModels1 = new ArrayList<>();
		List<BatchParamModel> expectedBatchParamModel = new ArrayList<>();
		
		when(batchParamService.getBatchParamsByBatchProcessId(batchProcess)).thenReturn(batchParamsList1);
		
		
		PowerMockito.when(batchParamMapper.transformToListOfBatchParamModel(batchParamsList1)).thenReturn(batchParamModels1);
		
		
		ResponseEntity<List<BatchParamModel>> response = batchParamController.getParams(1);
		
		
		ResponseEntity<List<BatchParamModel>> expectedResponse = new ResponseEntity<>(expectedBatchParamModel,HttpStatus.OK);
		
		assertEquals(expectedResponse, response);
		
	}
	

}
