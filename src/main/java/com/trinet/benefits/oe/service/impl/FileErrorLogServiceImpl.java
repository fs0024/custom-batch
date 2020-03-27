/**
 * 
 */
package com.trinet.benefits.oe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.entity.FileErrorLog;
import com.trinet.benefits.oe.repository.BatchParamValueRepository;
import com.trinet.benefits.oe.repository.FileErrorLogRepository;
import com.trinet.benefits.oe.service.FileErrorLogService;

/**
 * @author imistry1
 *
 * 
 */
@Service
public class FileErrorLogServiceImpl implements FileErrorLogService {


	
	@Autowired
	private FileErrorLogRepository fileErrorRepo;
	
	@Autowired
	private BatchParamValueRepository batchParamValueRepo;
	
	
	
	@Override
	public List<FileErrorLog> getFileErrorDetails(Integer batchProcessTaskId,Integer batchProcessStepId) {
		
		List<FileErrorLog> errorLog = new ArrayList<>();
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		batchProcessTask.setId(batchProcessTaskId);
		List<BatchParamValue> paramValueList = batchParamValueRepo.findByBatchProcessTask(batchProcessTask);
		BatchParamValue fileParamObject = null;
		
		for(BatchParamValue value: paramValueList) {
			if(value.getBatchParam().getName().equalsIgnoreCase("File")) {
				fileParamObject = value;
				break;
			}
		}
		
		if(fileParamObject != null) {
			 errorLog = fileErrorRepo.findByBatchParamValue(fileParamObject);
		}
		
		
		return errorLog;
	}

	
	

}
