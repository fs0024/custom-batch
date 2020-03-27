/**
 * 
 */
package com.trinet.benefits.oe.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.trinet.benefits.oe.configuration.AuthProperties;
import com.trinet.benefits.oe.service.impl.BatchLoginService;

import net.minidev.json.JSONObject;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing BatchLoginService")
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "environment.properties")
public class BatchLoginServiceTest {
	
	private static String authUrl = "http://dummyUrl";
	private static String schedulerUserName = "testuser";
	private static String schedulerPassword = "pwd";
	private static String responseString = "token";
	
	
	 @TestConfiguration
	    static class TestConfig {

	        @Bean
	        public BatchLoginService batchLoginServiceBuilder() {

	            return new BatchLoginService();
	        }
	        
	        
	        
	        
	        
	    }

	@Mock
	private static AuthProperties authProperties;

	@Mock
	private static RestTemplate restTemplate;
	

	@InjectMocks
	private static BatchLoginService batchLoginService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void	 testBatchLoginService() {
		
		
		
		
		HttpHeaders headers = new HttpHeaders();
		JSONObject authJsonObject = new JSONObject();
		headers.setContentType(MediaType.APPLICATION_JSON);
		authJsonObject.put("guid", schedulerUserName);
		authJsonObject.put("userpassword", schedulerPassword);
		HttpEntity<String> request = new HttpEntity<>(authJsonObject.toString(), headers);
		HttpEntity<String> requestSpy = Mockito.spy(request);
		
		ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseString,HttpStatus.OK);
		
		
		//Mockito.when(authProperties.getAuthUrl()).thenReturn(authUrl);
		Mockito.when(authProperties.getSchedulerUserName()).thenReturn(schedulerUserName);
		Mockito.when(authProperties.getSchedulerPassword()).thenReturn(schedulerPassword);
		Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),Mockito.any(Class.class))).thenReturn(new ResponseEntity<>(responseString,HttpStatus.OK));
		
		
		assertEquals(expectedResponse.getBody(),batchLoginService.login());
		
		
	}

}
