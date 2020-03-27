package com.trinet.benefits.oe.service;

import java.util.List;
import java.util.Optional;

import com.trinet.benefits.oe.entity.BatchProcess;

public interface BatchProcessService {
	
	public Optional<BatchProcess> getBatchProcessById(String appId);
	
	public Optional<BatchProcess> getBatchProcessById(Integer appId);
	
	public BatchProcess registerProcess(BatchProcess batchProcess);
	
	public List<BatchProcess> getAllBatchProcess();

}
