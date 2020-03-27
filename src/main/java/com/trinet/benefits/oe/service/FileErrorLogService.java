/**
 * 
 */
package com.trinet.benefits.oe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trinet.benefits.oe.entity.FileErrorLog;

/**
 * @author imistry1
 *
 * 
 */
@Service
public interface FileErrorLogService {
	
	
	public List<FileErrorLog> getFileErrorDetails(Integer batchProcessTaskId,Integer batchProcessStepId);

}
