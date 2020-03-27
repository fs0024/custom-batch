/**
 * 
 */
package com.trinet.benefits.oe.service;

import org.springframework.stereotype.Service;

import com.trinet.benefits.oe.web.request.FileUploadRequest;

/**
 * @author imistry1
 *
 * 
 */
@Service
public interface FileValidatorService {
	
	public String validateFile(String uploadedFilePath,Integer batchProcessId,FileUploadRequest uploadRequest);

}
