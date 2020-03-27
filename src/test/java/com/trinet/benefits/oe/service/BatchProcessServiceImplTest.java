/**
 * 
 */
package com.trinet.benefits.oe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.repository.BatchProcessRepository;
import com.trinet.benefits.oe.service.impl.BatchProcessServiceImpl;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing BatchProcessServiceImpl")
@SuppressWarnings("unchecked")
public class BatchProcessServiceImplTest {
	
	
	@Mock
	private static BatchProcessRepository batchProcessRepository;
	
	@InjectMocks
	private static BatchProcessServiceImpl batchProcessService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testGetBatchProcessById() {
		
		Integer id = 1;
		List<BatchProcess> process = new ArrayList<>();
		process.add(new BatchProcess());
		
		BatchProcess processObj = new BatchProcess();
		
		
		Mockito.when(batchProcessRepository.findByAppId("test")).thenReturn(process);
		
		Optional<BatchProcess> expectedResponse = Optional.of(processObj); 
		
		assertEquals(expectedResponse, batchProcessService.getBatchProcessById("test"));
	}
	
	
	@Test
	public void testGetBatchProcessByIdEmpty() {
		List<BatchProcess> process = Collections.EMPTY_LIST;
		Mockito.when(batchProcessRepository.findByAppId("test")).thenReturn(process);
		
		Optional<BatchProcess> expectedResponse = Optional.empty();
		
		assertEquals(expectedResponse, batchProcessService.getBatchProcessById("test"));
	}
	
	@Test
	public void testGetBatchProcessByIdInteger() {
		Integer id = 1;
		Optional<BatchProcess> expectedResponse = Optional.empty();
		
		Mockito.when(batchProcessRepository.findById(id)).thenReturn(expectedResponse);
		
		assertEquals(expectedResponse, batchProcessService.getBatchProcessById(id));
	}
	
	
	@Test
	public void testRegisterProcess() {
		
		Mockito.when(batchProcessRepository.save(Mockito.any())).thenReturn(new BatchProcess());
		
		assertEquals(new BatchProcess(), batchProcessService.registerProcess(new BatchProcess()));
	}
	
	
	@Test
	public void testGetAllBatchProcess() {
		
		Mockito.when(batchProcessRepository.findAll()).thenReturn(new ArrayList<BatchProcess>());
		
		assertEquals(new ArrayList<>(), batchProcessService.getAllBatchProcess());
	}

}
