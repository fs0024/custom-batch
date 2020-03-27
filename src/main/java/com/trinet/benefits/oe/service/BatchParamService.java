package com.trinet.benefits.oe.service;

import java.util.List;

import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessTask;

public interface BatchParamService {

	public List<BatchParam> getBatchParamsByBatchProcessId(BatchProcess batchProcess);




	public List<BatchParamValue> getBatchParamValuesByBatchTask(BatchProcessTask batchProcessTask);

}
