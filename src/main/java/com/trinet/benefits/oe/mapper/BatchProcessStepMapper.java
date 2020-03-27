package com.trinet.benefits.oe.mapper;

import com.trinet.benefits.oe.entity.BatchProcessStep;
import com.trinet.benefits.oe.model.BatchProcessStepModel;

public class BatchProcessStepMapper {
	
	private BatchProcessStepMapper() {}
	
	public static BatchProcessStep transformToBatchProcessStep(BatchProcessStepModel batchProcessStepModel) {
		
		BatchProcessStep batchProcessStep = new BatchProcessStep();
		
		batchProcessStep.setName(batchProcessStepModel.getName());
		batchProcessStep.setDescription(batchProcessStepModel.getDescription());
		
		return batchProcessStep;
		
	}

}
