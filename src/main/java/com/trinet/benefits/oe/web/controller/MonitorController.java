package com.trinet.benefits.oe.web.controller;


import static com.trinet.benefits.oe.web.Mappings.APP_HEALTH;
import static com.trinet.benefits.oe.web.Mappings.VERSION;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author dshah
 *
 */
@RequestMapping(VERSION)
@RestController
public class MonitorController {

	

	
	@GetMapping(APP_HEALTH)
	public ResponseEntity<String> testAPIStatus() {
		return new ResponseEntity<>( "Api-call successful",HttpStatus.OK);
	}

}
