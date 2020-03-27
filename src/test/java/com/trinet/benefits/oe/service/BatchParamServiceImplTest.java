/**
 * 
 */
package com.trinet.benefits.oe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessParamMap;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.repository.BatchParamValueRepository;
import com.trinet.benefits.oe.repository.BatchProcessParamMapRepository;
import com.trinet.benefits.oe.service.impl.BatchParamServiceImpl;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing BatchParamService")
public class BatchParamServiceImplTest {
	
	@Mock
	private static BatchProcessParamMapRepository batchProcessParamMapRepo;
	
	@Mock
	private static BatchParamValueRepository batchParamValueRepository;
	
	@InjectMocks
	private static BatchParamServiceImpl batchParamServiceImpl;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testGetBatchParamsByBatchProcessId() {
		BatchProcess batchProcess = new BatchProcess();
		
		List<BatchProcessParamMap> batchProcessMapList = new ArrayList<>();
		
		BatchProcessParamMap batchProcessParamMap1 = new BatchProcessParamMap();
		batchProcessParamMap1.setId(1);
		
		BatchParam batchParam = new BatchParam();
		batchParam.setId(1);
		batchParam.setName("File");
		
		batchProcessParamMap1.setBatchParam(batchParam);
		
		batchProcessMapList.add(batchProcessParamMap1);
		
		List<BatchParam> batchParamListExpected = new ArrayList<>();
		
		
		
		Mockito.when( batchProcessParamMapRepo.findByBatchProcess(batchProcess)).thenReturn(batchProcessMapList);
		
		Set<BatchParam> paramSet = new HashSet<>();
		paramSet.add(batchParam);
		batchParamListExpected.add(batchParam);
		
		assertEquals(batchParamListExpected.size(),batchParamServiceImpl.getBatchParamsByBatchProcessId(batchProcess).size());
		
	}
	
	@Test
	public void testGetBatchParamValuesByBatchTask() {
		Mockito.when(batchParamValueRepository.findByBatchProcessTask(Mockito.any())).thenReturn(new ArrayList<BatchParamValue>());
		List<BatchParamValue> batchParamValueList = new ArrayList<>();
		assertEquals(batchParamValueList.size(), batchParamServiceImpl.getBatchParamValuesByBatchTask(new BatchProcessTask()).size());
		
		
	}
	
	

}
