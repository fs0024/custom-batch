/**
 * 
 */
package com.trinet.benefits.oe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trinet.benefits.oe.web.controller.MonitorController;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing Monitor Controller")
public class MonitorControllerTest {
	
	private static MonitorController monitorController ;
	
	@BeforeAll
	public static void init() {
		monitorController = new MonitorController() ;
		
	}
	
	
	@Test
	@DisplayName("Test Monitor")
	public void testTestAPIStatus() {
		
		ResponseEntity<String> response = new ResponseEntity<>( "Api-call successful",HttpStatus.OK);
		
		assertEquals(monitorController.testAPIStatus(), response);
	}

}
