/**
 * 
 */
package com.trinet.benefits.oe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.trinet.benefits.oe.service.impl.BatchLoginService;
import com.trinet.benefits.oe.service.impl.BatchProcesserService;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing BatchProcesserService")
@SuppressWarnings("unchecked")
public class BatchProcesserServiceTest {

	

	
	@Mock
	private static RestTemplate restTemplate;

	@Mock
	private static BatchLoginService loginService;
	
	@InjectMocks
	private static BatchProcesserService batchProcesserService;
	
	private static String batchName = "api-bnft-nwband-bpc";
	private static String token = "token";
	
	
	
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void testRunNow() {
		
		String responseString = "success";
		ReflectionTestUtils.setField(batchProcesserService, "apiBaseUrl", "http://dummyUrl");
		
		Mockito.when(loginService.login()).thenReturn(token);
		
		
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(),Mockito.any(),Mockito.any(Class.class))).thenReturn(new ResponseEntity<>(responseString,HttpStatus.OK));
		
		assertEquals(responseString, batchProcesserService.runNow(batchName));
		
	}
	
	
	@Test
	public void testProcessorHealth() {
		String responseString = "success";
		ReflectionTestUtils.setField(batchProcesserService, "apiBaseUrl", "http://dummyUrl");
		
		Mockito.when(loginService.login()).thenReturn(token);
		
		
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(),Mockito.any(),Mockito.any(Class.class))).thenReturn(new ResponseEntity<>(responseString,HttpStatus.OK));
		
		assertEquals(responseString, batchProcesserService.processorHealth(batchName));
	}
	
	
	@Test
	public void testUpdateSchedule() {
		String responseString = "success";
		ReflectionTestUtils.setField(batchProcesserService, "apiBaseUrl", "http://dummyUrl");
		
		Mockito.when(loginService.login()).thenReturn(token);
		
		
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(),Mockito.any(),Mockito.any(Class.class))).thenReturn(new ResponseEntity<>(responseString,HttpStatus.OK));
		
		assertEquals(responseString, batchProcesserService.updateSchedule(batchName));
	}
	
	
	@Test
	public void testStopSchedule() {
		String responseString = "success";
		ReflectionTestUtils.setField(batchProcesserService, "apiBaseUrl", "http://dummyUrl");
		
		Mockito.when(loginService.login()).thenReturn(token);
		
		
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(),Mockito.any(),Mockito.any(Class.class))).thenReturn(new ResponseEntity<>(responseString,HttpStatus.OK));
		
		assertEquals(responseString, batchProcesserService.stopSchedule(batchName));
	}
	
	
	@Test
	public void testStopJobs() {
		String responseString = "success";
		ReflectionTestUtils.setField(batchProcesserService, "apiBaseUrl", "http://dummyUrl");
		
		Mockito.when(loginService.login()).thenReturn(token);
		
		
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(),Mockito.any(),Mockito.any(Class.class))).thenReturn(new ResponseEntity<>(responseString,HttpStatus.OK));
		
		assertEquals(responseString, batchProcesserService.stopJobs(batchName));
	}
	
	
	
	
}
