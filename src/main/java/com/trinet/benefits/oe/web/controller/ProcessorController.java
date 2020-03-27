package com.trinet.benefits.oe.web.controller;

import static com.trinet.benefits.oe.web.Mappings.VERSION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trinet.benefits.oe.service.impl.BatchProcesserService;

@RequestMapping(VERSION)
@RestController
public class ProcessorController {
	
	
	private BatchProcesserService batchProcesserService;
	
	@Autowired
	public ProcessorController(BatchProcesserService batchProcesserService) {
		this.batchProcesserService= batchProcesserService;
	}
	
	@GetMapping("/runNow/{batchname}")
	public ResponseEntity<String> runNow(@PathVariable("batchname") String batchName) {
		
		
		return new ResponseEntity<>(batchProcesserService.runNow(batchName),HttpStatus.OK);
		
	}
	
	@GetMapping("/processorHealth/{batchname}")
	public ResponseEntity<String> processorHealth(@PathVariable("batchname") String batchName) {
		
		return new ResponseEntity<>( batchProcesserService.processorHealth(batchName),HttpStatus.OK);
		
	}

}
