package com.trinet.benefits.oe.mapper;

import java.util.ArrayList;
import java.util.List;

import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessStep;
import com.trinet.benefits.oe.model.BatchParamModel;

public class BatchParamMapper {

	/*private BatchParamMapper() {

	}
*/
	public  BatchParam transformToBatchParam(BatchParamModel batchParamModel) {

		BatchProcess batchProcess = new BatchProcess();
		batchProcess.setId(batchParamModel.getBatchProcessId());

		BatchParam batchParam = new BatchParam();

		batchParam.setName(batchParamModel.getName());
		batchParam.setType(batchParamModel.getType());
		batchParam.setDescription(batchParamModel.getDescription());
		batchParam.setValidation(batchParamModel.getValidation());

		int batchProcessStepId = batchParamModel.getBatchProcessStepId();

		if (batchProcessStepId != 0) {
			BatchProcessStep batchProcessStep = new BatchProcessStep();
			batchProcessStep.setId(batchProcessStepId);
		}

		return batchParam;
	}

	public  List<BatchParamModel> transformToListOfBatchParamModel(List<BatchParam> batchParams) {

		List<BatchParamModel> batchParamModelList = new ArrayList<>();
		BatchParamModel batchParamModel;

		for (BatchParam batchParam : batchParams) {

			batchParamModel = new BatchParamModel();
			batchParamModel.setId(batchParam.getId());
			batchParamModel.setName(batchParam.getName());
			batchParamModel.setType(batchParam.getType());
			batchParamModel.setDescription(batchParam.getDescription());

			batchParamModelList.add(batchParamModel);
		}

		return batchParamModelList;

	}

}
