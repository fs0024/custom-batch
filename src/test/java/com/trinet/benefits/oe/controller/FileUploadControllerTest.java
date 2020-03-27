/**
 * 
 */
package com.trinet.benefits.oe.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.tomcat.jni.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.trinet.benefits.oe.service.FileStorageService;
import com.trinet.benefits.oe.service.impl.FileStorageServiceImpl;
import com.trinet.benefits.oe.web.controller.FileUploadController;
import com.trinet.benefits.oe.web.request.FileUploadRequest;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing FileUploadController")
public class FileUploadControllerTest {
	
	private static FileStorageService fileStorageService;
	private static FileUploadController fileUploadController;
	
	
	@BeforeAll
	public static void init() {
		fileStorageService = mock(FileStorageServiceImpl.class);
		fileUploadController = new FileUploadController(fileStorageService);
		
	}
	
	
	@Test
	public void testUplpoadFileToServer() {
		File file = new File();
		byte[] testByte = null;
		MultipartFile fileMock = new MockMultipartFile("Test", testByte);
		String batchProcessId = "1";
		FileUploadRequest uploadRequest = new FileUploadRequest();
		String responseString = "Success";

		when(fileStorageService.storeFilesOnServer(fileMock, batchProcessId, uploadRequest)).thenReturn(responseString);
		ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseString, HttpStatus.OK);
		ResponseEntity<String> actualResponse = fileUploadController.uplpoadFileToServer(fileMock, batchProcessId, uploadRequest);
		
		assertEquals(expectedResponse, actualResponse);
	}

}
