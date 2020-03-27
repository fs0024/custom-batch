/**
 * 
 */
package com.trinet.benefits.oe.web.controller;

import static com.trinet.benefits.oe.web.Mappings.UPLOAD_FILE;
import static com.trinet.benefits.oe.web.Mappings.VERSION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trinet.benefits.oe.service.FileStorageService;
import com.trinet.benefits.oe.web.request.FileUploadRequest;

/**
 * @author imistry1
 *
 * 
 */
@RequestMapping(VERSION)
@RestController
public class FileUploadController {
	
	
	private FileStorageService fileStorageService;
	
	@Autowired
	public FileUploadController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}
	
	
	@PostMapping(UPLOAD_FILE)
	public ResponseEntity<String> uplpoadFileToServer(@RequestPart("files") MultipartFile file,
			@RequestParam("batchProcessId") String batchProcessId,
			@RequestPart(name="FileUploadRequest", required = false) FileUploadRequest uploadRequest) {
		
		
		String message = fileStorageService.storeFilesOnServer(file,batchProcessId,uploadRequest);
		
		return new ResponseEntity<>(message,HttpStatus.OK);
	}

}
