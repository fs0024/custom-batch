package com.trinet.benefits.oe.mapper;

import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.model.BatchProcessTaskModel;

import java.util.List;

public class BatchProcessTaskMapper {
	

	
		
	public  BatchProcessTask  transformToBatchProcessTask(BatchProcessTaskModel batchProcessTaskModel) {
				
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		batchProcessTask.setId(batchProcessTaskModel.getId());
		batchProcessTask.setStartDate(batchProcessTaskModel.getStartDate());
		batchProcessTask.setFinalStatus("PENDING");

		batchProcessTask.setBatchProcess(batchProcessTaskModel.getBatchProcess());
		List<BatchParamValue> batchParamValues = batchProcessTaskModel.getBatchParamValues();

		for (BatchParamValue batchParamValue : batchParamValues) {
			batchParamValue.setBatchProcessTask(batchProcessTask);
		}


		batchProcessTask.setBatchParamValues(batchParamValues);

		return batchProcessTask;
		
	}

}
