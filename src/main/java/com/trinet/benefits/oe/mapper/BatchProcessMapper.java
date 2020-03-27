package com.trinet.benefits.oe.mapper;

import java.util.ArrayList;
import java.util.List;

import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchSchedule;
import com.trinet.benefits.oe.model.BatchProcessModel;

public class BatchProcessMapper {

	

	public  BatchProcess transformToBatchProcess(BatchProcessModel batchProcessModel) {

		BatchProcess batchProcess = new BatchProcess();

		if(batchProcessModel.getId() != 0) {
			batchProcess.setId(batchProcessModel.getId());
		}
		
		batchProcess.setDescription(batchProcessModel.getDescription());
		batchProcess.setName(batchProcessModel.getName());
		batchProcess.setBatchSchedule(batchProcessModel.getBatchSchedule());
		batchProcess.setAppId(batchProcessModel.getAppId());
		batchProcess.setBatchSchedule(batchProcessModel.getBatchSchedule());

		return batchProcess;
	}

	public  List<BatchProcessModel> transformToListofBatchProcessModel(List<BatchProcess> listOfBatch) {

		BatchProcessModel batchProcessModel;

		List<BatchProcessModel> batchProcessModelList = new ArrayList<>();

		for (BatchProcess batchProcess : listOfBatch) {

			batchProcessModel = new BatchProcessModel();

			batchProcessModel.setId(batchProcess.getId());
			batchProcessModel.setName(batchProcess.getName());
			batchProcessModel.setDescription(batchProcess.getDescription());

			BatchSchedule batchSchedule = new BatchSchedule();
			batchSchedule.setValue(batchProcess.getBatchSchedule().getValue());
			
			batchProcessModel.setBatchSchedule(batchSchedule);

			batchProcessModelList.add(batchProcessModel);

		}

		return batchProcessModelList;
	}

}
