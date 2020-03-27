/**
 * 
 */
package com.trinet.benefits.oe.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trinet.benefits.oe.dao.BatchDetailsDao;
import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchProcessAndTaskModel;
import com.trinet.benefits.oe.entity.BatchProcessStep;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.repository.BatchProcessParamMapRepository;
import com.trinet.benefits.oe.repository.TaskStepsRepository;
import com.trinet.benefits.oe.service.FileStorageService;
import com.trinet.benefits.oe.service.FileValidatorService;
import com.trinet.benefits.oe.web.request.FileUploadRequest;

import lombok.extern.log4j.Log4j2;

/**
 * @author imistry1
 *
 * 
 */
@Service
@Log4j2
public class FileStorageServiceImpl implements FileStorageService {

	@Value("${ben.document.location}")
	private String documentLocation;

	@Autowired
	private BatchDetailsDao batchDetailsRepo;

	@Autowired
	private FileValidatorService fileValidatorService;

	@Autowired
	private BatchProcessParamMapRepository batchParamMapRepo;

	@Autowired
	private TaskStepsRepository taskStepRepository;

	@Override
	public String storeFilesOnServer(MultipartFile file, String batchProcessId, FileUploadRequest uploadRequest) {

		if (uploadRequest.getTaskStepId() == 0) {
			BatchParam batchParam = new BatchParam();
			batchParam.setId(uploadRequest.getBatchParamId());

			BatchProcessStep batchPrStep = batchParamMapRepo.findByBatchParam(batchParam).get(0).getBatchProcessStep();
			BatchProcessTask batchProcessTask = new BatchProcessTask();
			batchProcessTask.setId(uploadRequest.getBatchProcessTaskId());

			List<TaskStep> stepList = taskStepRepository.findByBatchProcessStepAndBatchProcessTask(batchPrStep,
					batchProcessTask);

			uploadRequest.setTaskStepId(stepList.get(0).getId());
		}

		BatchProcessAndTaskModel batchProcessTaskDetails = batchDetailsRepo
				.getBatchAndTaskDetails(Integer.parseInt(batchProcessId), uploadRequest.getTrackingNumber()).get(0);

		String fileSubDirectory = "/" + batchProcessTaskDetails.getAppId() + "/"
				+ batchProcessTaskDetails.getBatchProcessTaskId() + "/incoming";
		Path directory = Paths.get(documentLocation, fileSubDirectory);
		Path fileUploadPath = transferFile(file, directory);

		CompletableFuture<String> messageFuture = validateFileFuture(
				directory.toString() + "/" + file.getOriginalFilename(), batchProcessTaskDetails.getProcessId(),
				uploadRequest);

		if (fileUploadPath == null) {

			return "error while storing file";
		}

		try {
			return messageFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error(() -> "Upload process cannot proceed. Fatal error occured: " + e.getMessage());
			Thread.currentThread().interrupt();
			return "Error While validating file";
		}

	}

	private Path transferFile(MultipartFile file, Path directory) {
		Path filePath = null;
		try {

			// First create the directory structure if it does not exists
			Files.createDirectories(directory);

			// Now, rename the file and create the file path (filePath = directoryPath +
			// fileName)
			filePath = Paths.get(directory.toString(), file.getOriginalFilename());

			// Transfer the files (Replace old file with new file)
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {
			filePath = null;

		}
		// return the path where file was uploaded
		return filePath;
	}

	@Async
	public CompletableFuture<String> validateFileFuture(String uploadedFilePath, Integer batchProcessId,
			FileUploadRequest uploadRequest) {
		return CompletableFuture
				.completedFuture(fileValidatorService.validateFile(uploadedFilePath, batchProcessId, uploadRequest));
	}

}
