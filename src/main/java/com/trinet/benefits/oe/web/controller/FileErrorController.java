/**
 * 
 */
package com.trinet.benefits.oe.web.controller;

import static com.trinet.benefits.oe.web.Mappings.VERSION;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinet.benefits.oe.entity.FileErrorLog;
import com.trinet.benefits.oe.service.FileErrorLogService;

/**
 * @author imistry1
 *
 * 
 */
@RequestMapping(VERSION)
@RestController
public class FileErrorController {
	
	
	private FileErrorLogService fileErrorLogService;
	
	@Autowired
	public FileErrorController(FileErrorLogService fileErrorLogService) {
		this.fileErrorLogService = fileErrorLogService;
	}
	
	@GetMapping("/getErrors")
	public ResponseEntity<List<FileErrorLog>> getAllFileErrors(@RequestParam("batchProcessTaskId") Integer batchProcessTaskId,
			@RequestParam("batchProcessStepId") Integer batchProcessStepId) {
		
		
		return new ResponseEntity<>(fileErrorLogService.getFileErrorDetails(batchProcessTaskId,batchProcessStepId),HttpStatus.OK);
	}

}
