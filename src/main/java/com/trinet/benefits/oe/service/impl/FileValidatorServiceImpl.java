/**
 * 
 */
package com.trinet.benefits.oe.service.impl;

import com.trinet.benefits.oe.common.FileDataValidatorUtil;
import com.trinet.benefits.oe.dao.FileValidatorDao;
import com.trinet.benefits.oe.entity.*;
import com.trinet.benefits.oe.repository.BatchParamValueRepository;
import com.trinet.benefits.oe.repository.FileErrorLogRepository;
import com.trinet.benefits.oe.repository.TaskItemRepository;
import com.trinet.benefits.oe.repository.TaskStepsRepository;
import com.trinet.benefits.oe.service.FileValidatorService;
import com.trinet.benefits.oe.web.request.FileUploadRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author imistry1
 *
 * 
 */
@Service
@Component
@Log4j2
public class FileValidatorServiceImpl implements FileValidatorService {

	@Autowired
	private FileValidatorDao fileValidatorDaoService;

	@Autowired
	private BatchParamValueRepository batchParamValueRepo;

	@Autowired
	private FileErrorLogRepository fileErrorRepo;

	@Autowired
	private TaskStepsRepository taskStepsRepository;

	@Autowired
	private TaskItemRepository taskItemRepo;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String validateFile(String uploadedFilePath, Integer batchProcessId, FileUploadRequest uploadRequest) {
		try (Reader reader = Files.newBufferedReader(Paths.get(uploadedFilePath))) {


			log.info("file validation started:" + uploadedFilePath);



			boolean isFileValid = true;

			Integer insertBarrier = 0;

			Integer fileId = 0;
			List<FileErrorLog> invalidRecordList = new ArrayList<>();
			List<ValidatorDef> validations = fileValidatorDaoService.getFileValidorDefByFileValidatorId(1);

			List<TaskItem> taskItemList = new ArrayList<>();

			Map<Integer, ValidatorDef> regex = FileDataValidatorUtil.convertValidationListToMap(validations);
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

			BatchParam batchParam = new BatchParam();
			batchParam.setId(uploadRequest.getBatchParamId());

			BatchProcessTask batchProcessTask = new BatchProcessTask();
			batchProcessTask.setId(uploadRequest.getBatchProcessTaskId());

			BatchParamValue batchPramaValue = batchParamValueRepo
					.findByBatchParamAndBatchProcessTask(batchParam, batchProcessTask).get(0);

			TaskStep taskStep = taskStepsRepository.findById(uploadRequest.getTaskStepId()).orElse(null);
			log.info("Delete old records if exist");
			try{

				fileErrorRepo.deleteByBatchParamValue(batchPramaValue);
				taskItemRepo.deleteByTaskStep(taskStep);

			}catch (Exception e){

				log.error("Error Deleting old records for task items");
			}

			if (taskStep != null) {
				taskStep.setStatus("VALIDATIION_IN_PROGRESS");
				taskStepsRepository.save(taskStep);
			}

			StringBuilder builderErrorRecords;
			StringBuilder builderValidRecords;
			boolean isCurrRecordInValid = false;

			for (CSVRecord record : csvParser) {

				insertBarrier++;

				builderErrorRecords = new StringBuilder();
				builderValidRecords = new StringBuilder();
				StringJoiner validRecords = new StringJoiner(",");
				StringJoiner invalidRecords = new StringJoiner(",");
				StringJoiner invalidDataSeqNum = new StringJoiner(",");

				FileErrorLog errorLog = new FileErrorLog();

				for (int i = 0; i < record.size(); i++) {

					String validation = regex.get(i).getValue();
					validRecords.add(record.get(i));
					if(i==0 || i==1){
					  if(!FileDataValidatorUtil.isValidData(record.get(i), validation)){
						  invalidDataSeqNum.add("" + i);
						  invalidRecords.add(record.get(i));
						  isCurrRecordInValid = true;
						  isFileValid = false;
					  }

					} else {
						if (!record.get(i).trim().isEmpty()
								&& !FileDataValidatorUtil.isValidData(record.get(i), validation)) {
							invalidDataSeqNum.add("" + i);
							invalidRecords.add(record.get(i));
							isCurrRecordInValid = true;
							isFileValid = false;

					   }
					}





				}

				if (!isCurrRecordInValid) {
					builderValidRecords.append(validRecords);

					TaskItem taskItem = new TaskItem();

					FileItems validRecord = new FileItems();
					validRecord.setValue(builderValidRecords.toString());

					taskItem.setTaskStep(taskStep);
					taskItem.setDeleted('N');
					taskItem.setItemId(record.get(fileId));
					taskItem.setItemIdDesc("PeoID");
					taskItem.setStatus("Ready");
					validRecord.setTaskItem(taskItem);
					taskItem.setFileItems(validRecord);

					taskItemList.add(taskItem);

				} else {
					// Add invalid record entry into File Error Log table
					builderErrorRecords.append(invalidRecords);
					errorLog.setBatchParamValue(batchPramaValue);
					errorLog.setFileValues(builderErrorRecords.toString());
					errorLog.setItemId(record.get(fileId));
					errorLog.setRowNumber(record.getRecordNumber());
					errorLog.setValueSequence(invalidDataSeqNum.toString());
					invalidRecordList.add(errorLog);
					isCurrRecordInValid = false;

				}

				if (insertBarrier % 100 == 0) {

					persistTaskItems(taskItemList);
					persistFailedItems(invalidRecordList);
					taskItemList = new ArrayList<>();
					invalidRecordList = new ArrayList<>();
				}
			}

			csvParser.close();

			persistTaskItems(taskItemList);
			persistFailedItems(invalidRecordList);

			reconcileFileData(isFileValid, batchPramaValue, taskStep);

			log.info("file validation ended:" + uploadedFilePath);

		} catch (IOException e) {
			e.printStackTrace();
			log.error("error in reading banding file");
		}

		return "SUCCESS";

	}

	public void persistTaskItems(List<TaskItem> taskItemList) {
		for (TaskItem item : taskItemList) {
			taskItemRepo.saveAndFlush(item);
		}
	}

	public void persistFailedItems(List<FileErrorLog> invalidRecordList) {
		for (FileErrorLog errorLog : invalidRecordList) {
			fileErrorRepo.saveAndFlush(errorLog);
		}
	}

	private void reconcileFileData(boolean isFileValid, BatchParamValue batchPramaValue, TaskStep taskStep) {
		if (isFileValid) {
			batchPramaValue.setValidationFlag('T');
			taskStep.setStatus("SCHEDULED");
		} else {
			batchPramaValue.setValidationFlag('F');
			taskStep.setStatus("FAILED");
			taskItemRepo.deleteByTaskStep(taskStep);
		}

		batchParamValueRepo.save(batchPramaValue);
		taskStepsRepository.save(taskStep);

	}

}
