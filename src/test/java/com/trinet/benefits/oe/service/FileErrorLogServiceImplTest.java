/**
 * 
 */
package com.trinet.benefits.oe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoInitializationException;

import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.entity.FileErrorLog;
import com.trinet.benefits.oe.repository.BatchParamValueRepository;
import com.trinet.benefits.oe.repository.FileErrorLogRepository;
import com.trinet.benefits.oe.service.impl.FileErrorLogServiceImpl;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing FileErrorLogServiceImpl")
@SuppressWarnings("unchecked")
public class FileErrorLogServiceImplTest {
	

	@Mock
	private static FileErrorLogRepository fileErrorRepo;
	
	@Mock
	private static BatchParamValueRepository batchParamValueRepo;
	
	@InjectMocks
	private static FileErrorLogServiceImpl fileErrorLogServiceImpl;
	
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void testGetFileErrorDetails() {
		Integer batchProcessTaskId = 1;
		Integer batchProcessStepId = 1;
		
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		BatchParam batchParam = new BatchParam();
		batchParam.setId(1);
		batchParam.setName("File");
		BatchParamValue paramValue = new BatchParamValue();
		paramValue.setId(1);
		paramValue.setBatchParam(batchParam);
		List<BatchParamValue> paramValueList = new ArrayList<>();
		paramValueList.add(paramValue);
		
		
		Mockito.when(batchParamValueRepo.findByBatchProcessTask(Mockito.any())).thenReturn(paramValueList);
		
		Mockito.when(fileErrorRepo.findByBatchParamValue(Mockito.any())).thenReturn(new ArrayList<FileErrorLog>());
		
		
		assertEquals(new ArrayList<FileErrorLog>(), fileErrorLogServiceImpl.getFileErrorDetails(batchProcessTaskId, batchProcessStepId));
	}
	
	
	@Test
	public void testGetFileErrorDetailsNofile() {
		Integer batchProcessTaskId = 1;
		Integer batchProcessStepId = 1;
		
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		BatchParam batchParam = new BatchParam();
		batchParam.setId(1);
		batchParam.setName("Test");
		BatchParamValue paramValue = new BatchParamValue();
		paramValue.setId(1);
		paramValue.setBatchParam(batchParam);
		List<BatchParamValue> paramValueList = new ArrayList<>();
		paramValueList.add(paramValue);
		
		
		Mockito.when(batchParamValueRepo.findByBatchProcessTask(Mockito.any())).thenReturn(paramValueList);
		
		Mockito.when(fileErrorRepo.findByBatchParamValue(Mockito.any())).thenReturn(new ArrayList<FileErrorLog>());
		
		
		assertEquals(new ArrayList<FileErrorLog>(), fileErrorLogServiceImpl.getFileErrorDetails(batchProcessTaskId, batchProcessStepId));
	}

}
