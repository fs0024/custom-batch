/**
 * 
 */
package com.trinet.benefits.oe.service;

import org.springframework.web.multipart.MultipartFile;

import com.trinet.benefits.oe.web.request.FileUploadRequest;

/**
 * @author imistry1
 *
 * 
 */
public interface FileStorageService {
	

	String storeFilesOnServer(MultipartFile file,String batchProcessId, FileUploadRequest uploadRequest);

	

}
