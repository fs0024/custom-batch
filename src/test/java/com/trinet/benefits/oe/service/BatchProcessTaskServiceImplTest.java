/**
 * 
 */
package com.trinet.benefits.oe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessParamMap;
import com.trinet.benefits.oe.entity.BatchProcessStep;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.model.BatchProcessTaskModel;
import com.trinet.benefits.oe.repository.BatchParamRepository;
import com.trinet.benefits.oe.repository.BatchParamValueRepository;
import com.trinet.benefits.oe.repository.BatchProcessParamMapRepository;
import com.trinet.benefits.oe.repository.BatchProcessTaskRepository;
import com.trinet.benefits.oe.repository.TaskStepsRepository;
import com.trinet.benefits.oe.service.impl.BatchProcessTaskServiceImpl;
import com.trinet.benefits.oe.utils.RegexValidator;

/**
 * @author imistry1
 *
 * 
 */
public class BatchProcessTaskServiceImplTest {
	
	@Mock
	private static BatchProcessTaskRepository batchProcessTaskRepository;

	@Mock
	private static BatchParamValueRepository batchParamValueRepository;

	@Mock
	private static TaskStepsRepository taskStepsRepository;

	@Mock
	private static BatchParamRepository batchParamRepository;

	@Mock
	private static BatchProcessService batchProcessService;

	@Mock
	private static RegexValidator regexValidator;

	@Mock
	private static BatchProcessParamMapRepository batchProcessParamMapRepository;
	
	@InjectMocks
	private static BatchProcessTaskServiceImpl batchProcessTaskServiceImpl;
	
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testCreateTask() {
		
		BatchProcess batchProcess = new BatchProcess();
		
		List<BatchProcessStep> batchProcessStepList = new ArrayList<>();
		BatchProcessStep processStep1 = new BatchProcessStep();
		processStep1.setId(1);
		batchProcessStepList.add(processStep1);
		
		batchProcess.setBatchProcessStep(batchProcessStepList);
		
		
		
		
		
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		batchProcessTask.setId(1);
		batchProcessTask.setBatchProcess(batchProcess);
		
		List<BatchParamValue> batchParamValuesList = new ArrayList<>();
		BatchParamValue batchParamValue1 = new BatchParamValue();
		batchParamValue1.setId(1);
		batchParamValue1.setValidationFlag('T');
		batchParamValue1.setValue("test.csv");
		
		BatchParam batchparam1 =new BatchParam();
		batchparam1.setId(1);
		batchParamValue1.setBatchParam(batchparam1);
		
		batchParamValuesList.add(batchParamValue1);
		
		
		
		batchProcessTask.setBatchParamValues(batchParamValuesList);
		
		
		BatchProcessTask dbbatchProcessTaskCopy = new BatchProcessTask();
		dbbatchProcessTaskCopy.setFinalStatus("PENDING");
		//dbbatchProcessTaskCopy.setTrackingNumber(1);
		dbbatchProcessTaskCopy.setTaskSteps(new ArrayList<>());
		
		Optional<BatchProcessTask> batchprocessTaskOpt = Optional.of(dbbatchProcessTaskCopy);
		
		
		Mockito.when(batchProcessTaskRepository.findById(Mockito.any())).thenReturn(batchprocessTaskOpt);
		
		
		BatchParam param = new BatchParam();
		param.setType("FILE");
		param.setValidation("asd");
		param.setId(1);
		
		Optional<BatchParam> paramOpt = Optional.of(param);
		
		Mockito.when(batchParamRepository.findById(Mockito.anyInt())).thenReturn(paramOpt);
		
		Mockito.when(batchProcessTaskRepository.findMaxTrackingNumber()).thenReturn(1);
		
		Mockito.when(batchProcessTaskRepository.save(batchProcessTask)).thenReturn(batchProcessTask);
		
		assertEquals(batchProcessTask.getId(), batchProcessTaskServiceImpl.createTask(batchProcessTask).getId());
		
		
		
	}
	
	@Test
	public void testCreateTaskIdNull() {
		
		BatchProcess batchProcess = new BatchProcess();
		
		List<BatchProcessStep> batchProcessStepList = new ArrayList<>();
		BatchProcessStep processStep1 = new BatchProcessStep();
		processStep1.setId(1);
		batchProcessStepList.add(processStep1);
		
		batchProcess.setBatchProcessStep(batchProcessStepList);
		
		
		
		
		
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		//batchProcessTask.setId(1);
		batchProcessTask.setBatchProcess(batchProcess);
		
		List<BatchParamValue> batchParamValuesList = new ArrayList<>();
		BatchParamValue batchParamValue1 = new BatchParamValue();
		batchParamValue1.setId(1);
		batchParamValue1.setValidationFlag('T');
		batchParamValue1.setValue("test.csv");
		
		BatchParam batchparam1 =new BatchParam();
		batchparam1.setId(1);
		batchParamValue1.setBatchParam(batchparam1);
		
		batchParamValuesList.add(batchParamValue1);
		
		
		
		batchProcessTask.setBatchParamValues(batchParamValuesList);
		
		
		
		TaskStep taskStep1 = new TaskStep();
		taskStep1.setId(1);
		
		
		BatchProcessTask dbbatchProcessTaskCopy = new BatchProcessTask();
		dbbatchProcessTaskCopy.setFinalStatus("PENDING");
		//dbbatchProcessTaskCopy.setTrackingNumber(1);
		dbbatchProcessTaskCopy.setTaskSteps(new ArrayList<>());
		
		Optional<BatchProcessTask> batchprocessTaskOpt = Optional.of(dbbatchProcessTaskCopy);
		
		
		Mockito.when(batchProcessTaskRepository.findById(Mockito.any())).thenReturn(batchprocessTaskOpt);
		
		
		BatchParam param = new BatchParam();
		param.setType("Effective date");
		param.setValidation("asd");
		param.setId(1);
		
		
		Optional<BatchParam> paramOpt = Optional.of(param);
		
		List<BatchProcessParamMap> paramList = new ArrayList<>();
		
		BatchProcessParamMap batchProcessParamMap1 = new BatchProcessParamMap();
		batchProcessParamMap1.setId(1);
		batchProcessParamMap1.setBatchParam(param);
		
		paramList.add(batchProcessParamMap1);
	
		
		Mockito.when(batchParamRepository.findById(Mockito.anyInt())).thenReturn(paramOpt);
		
		Mockito.when(batchProcessParamMapRepository.findByBatchProcessStep(Mockito.any())).thenReturn(paramList);
		
		Mockito.when(batchProcessTaskRepository.findMaxTrackingNumber()).thenReturn(1);
		
		Mockito.when(batchProcessTaskRepository.save(batchProcessTask)).thenReturn(batchProcessTask);
		
		assertEquals(batchProcessTask.getId(), batchProcessTaskServiceImpl.createTask(batchProcessTask).getId());
		
		
		
	}
	
	@Test
	public void testCreateTaskIdNullFILE() {
		
		BatchProcess batchProcess = new BatchProcess();
		
		List<BatchProcessStep> batchProcessStepList = new ArrayList<>();
		BatchProcessStep processStep1 = new BatchProcessStep();
		processStep1.setId(1);
		batchProcessStepList.add(processStep1);
		
		batchProcess.setBatchProcessStep(batchProcessStepList);
		
		
		
		
		
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		//batchProcessTask.setId(1);
		batchProcessTask.setBatchProcess(batchProcess);
		
		List<BatchParamValue> batchParamValuesList = new ArrayList<>();
		BatchParamValue batchParamValue1 = new BatchParamValue();
		batchParamValue1.setId(1);
		batchParamValue1.setValidationFlag('T');
		batchParamValue1.setValue("test.csv");
		batchParamValue1.setValue("NO_FILE");
		
		BatchParam batchparam1 =new BatchParam();
		batchparam1.setId(1);
		batchParamValue1.setBatchParam(batchparam1);
		
		batchParamValuesList.add(batchParamValue1);
		
		
		
		batchProcessTask.setBatchParamValues(batchParamValuesList);
		
		
		
		TaskStep taskStep1 = new TaskStep();
		taskStep1.setId(1);
		
		
		BatchProcessTask dbbatchProcessTaskCopy = new BatchProcessTask();
		dbbatchProcessTaskCopy.setFinalStatus("PENDING");
		//dbbatchProcessTaskCopy.setTrackingNumber(1);
		dbbatchProcessTaskCopy.setTaskSteps(new ArrayList<>());
		
		Optional<BatchProcessTask> batchprocessTaskOpt = Optional.of(dbbatchProcessTaskCopy);
		
		
		Mockito.when(batchProcessTaskRepository.findById(Mockito.any())).thenReturn(batchprocessTaskOpt);
		
		
		BatchParam param = new BatchParam();
		param.setType("NO_FILE");
		param.setValidation("asd");
		param.setId(1);
		
		
		Optional<BatchParam> paramOpt = Optional.of(param);
		
		List<BatchProcessParamMap> paramList = new ArrayList<>();
		
		BatchProcessParamMap batchProcessParamMap1 = new BatchProcessParamMap();
		batchProcessParamMap1.setId(1);
		batchProcessParamMap1.setBatchParam(param);
		
		paramList.add(batchProcessParamMap1);
	
		
		Mockito.when(batchParamRepository.findById(Mockito.anyInt())).thenReturn(paramOpt);
		
		Mockito.when(batchProcessParamMapRepository.findByBatchProcessStep(Mockito.any())).thenReturn(paramList);
		
		Mockito.when(batchProcessTaskRepository.findMaxTrackingNumber()).thenReturn(1);
		
		Mockito.when(batchProcessTaskRepository.save(batchProcessTask)).thenReturn(batchProcessTask);
		
		assertEquals(batchProcessTask.getId(), batchProcessTaskServiceImpl.createTask(batchProcessTask).getId());
		
		
		
	}
	
	
	@Test
	public void testGetAllTaskStepDetails() {
		
		Mockito.when(batchProcessTaskRepository.findBatchProcessTaskByBatchProcessOrderByStartDateDesc(Mockito.any())).thenReturn(new ArrayList<BatchProcessTask>());
		
		assertEquals(new ArrayList<>() , batchProcessTaskServiceImpl.getAllTaskStepDetails(1));
	}
	
	
	@Test
	public void testCancelTask() {
		BatchProcessTask task = new BatchProcessTask();
		task.setId(1);
		
		Optional<BatchProcessTask> taskopt = Optional.of(task);
		
		Mockito.when( batchProcessTaskRepository.findById(Mockito.any())).thenReturn(taskopt);
		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(task);
		assertEquals("Updated", batchProcessTaskServiceImpl.cancelTask(1));
		
	}
	
	
	@Test
	public void testUpdateTaskStatusTrue() {
		
		BatchProcessTask task = new BatchProcessTask();
		task.setId(1);
		
		Optional<BatchProcessTask> taskopt = Optional.of(task);
		Mockito.when( batchProcessTaskRepository.findById(Mockito.any())).thenReturn(taskopt);
		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(task);
		assertEquals(true, batchProcessTaskServiceImpl.updateTaskStatus(1, "IN_PROCESS"));
		
	}
	

	@Test
	public void testUnLockTask() {
		BatchProcessTask task = new BatchProcessTask();
		task.setId(1);
		task.setFinalStatus("SCHEDULED");
		
		Optional<BatchProcessTask> taskopt = Optional.of(task);
		
		Mockito.when(batchProcessTaskRepository.findById(Mockito.any())).thenReturn(taskopt);
		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(task);
		
		assertEquals(true, batchProcessTaskServiceImpl.unLockTask(1));
		
	}
	
	
	@Test
	public void testGetTaskModelDetails() {
		BatchProcessTask task = new BatchProcessTask();
		task.setId(1);
		task.setFinalStatus("SCHEDULED");
		task.setStartDate(LocalDate.now());
		task.setBatchProcess(new BatchProcess());
		
		
		Optional<BatchProcessTask> taskopt = Optional.of(task);
		
		List<BatchParamValue> batchParamValues = new ArrayList<>();
		BatchParamValue batchParamValue1 = new BatchParamValue();
		batchParamValue1.setId(1);
		
		
		Mockito.when(batchProcessTaskRepository.findById(Mockito.any())).thenReturn(taskopt);
		Mockito.when(batchParamValueRepository.findByBatchProcessTask(Mockito.any())).thenReturn(batchParamValues);
		
		BatchProcessTaskModel batchProcessTaskModel = new BatchProcessTaskModel();
		batchProcessTaskModel.setFinalStatus("SCHEDULED");
		
		assertEquals(batchProcessTaskModel.getFinalStatus(), batchProcessTaskServiceImpl.getTaskModelDetails(1).getFinalStatus());
		
	}
	
	
	@Test
	public void testGetPrepedTask() {
		
		BatchProcess batchProcess = new BatchProcess();
		Optional<BatchProcess> batchProcessOpt = Optional.of(batchProcess);
		
		List<BatchProcessTask> batchProcessTasksList = new ArrayList<>();
		BatchProcessTask batchProcessTask1 = new BatchProcessTask();
		batchProcessTask1.setId(1);
		batchProcessTask1.setTrackingNumber(1);
		
		BatchProcessStep batchProcessStep = new BatchProcessStep();
		batchProcessStep.setId(1);
		batchProcessStep.setBatchIdentifire("Update band");
		
		TaskStep taskStep1 = new TaskStep();
		taskStep1.setIsPrepared('Y');
		taskStep1.setStatus("SCHEDULED");
		taskStep1.setBatchProcessStep(batchProcessStep);
		taskStep1.setBatchProcessStep(batchProcessStep);
		
		List<TaskStep> taskStepsList = new ArrayList<>();
		taskStepsList.add(taskStep1);
		
		batchProcessTask1.setTaskSteps(taskStepsList);
		
		batchProcessTasksList.add(batchProcessTask1);
		
		
		
		Mockito.when(batchProcessService.getBatchProcessById(Mockito.anyString())).thenReturn(batchProcessOpt);
		Mockito.when(batchProcessTaskRepository
				.findByBatchProcessAndFinalStatusAndStartDateLessThanEqual(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(batchProcessTasksList);
		
		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(batchProcessTask1);
		
		Optional<String> stepIdentifier = Optional.of("Update band");
		assertEquals(batchProcessTask1.getId(), batchProcessTaskServiceImpl.getPrepedTask("testAppId", stepIdentifier).getId());
		
		
	}
	
	@Test
	public void testGetPrepedTaskIsPreparedNull() {

		BatchProcess batchProcess = new BatchProcess();
		Optional<BatchProcess> batchProcessOpt = Optional.of(batchProcess);

		List<BatchProcessTask> batchProcessTasksList = new ArrayList<>();
		BatchProcessTask batchProcessTask1 = new BatchProcessTask();
		batchProcessTask1.setId(1);
		batchProcessTask1.setTrackingNumber(1);

		BatchProcessStep batchProcessStep = new BatchProcessStep();
		batchProcessStep.setId(1);
		batchProcessStep.setBatchIdentifire("Update band");

		TaskStep taskStep1 = new TaskStep();
		taskStep1.setIsPrepared('Y');
		taskStep1.setStatus("SCHEDULED");
		taskStep1.setBatchProcessStep(batchProcessStep);
		taskStep1.setBatchProcessStep(batchProcessStep);

		List<TaskStep> taskStepsList = new ArrayList<>();
		taskStepsList.add(taskStep1);

		batchProcessTask1.setTaskSteps(taskStepsList);

		batchProcessTasksList.add(batchProcessTask1);

		Mockito.when(batchProcessService.getBatchProcessById(Mockito.anyString())).thenReturn(batchProcessOpt);
		Mockito.when(batchProcessTaskRepository.findByBatchProcessAndFinalStatusAndStartDateLessThanEqual(Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(batchProcessTasksList);

		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(batchProcessTask1);

		Optional<String> stepIdentifier = Optional.empty();
		assertEquals(batchProcessTask1,
				batchProcessTaskServiceImpl.getPrepedTask("testAppId", stepIdentifier));

	}
	
	
	@Test
	public void testGetUnPrepTask() {
		BatchProcess batchProcess = new BatchProcess();
		Optional<BatchProcess> batchProcessOpt = Optional.of(batchProcess);
		
		List<BatchProcessTask> batchProcessTasksList = new ArrayList<>();
		BatchProcessTask batchProcessTask1 = new BatchProcessTask();
		batchProcessTask1.setId(1);
		batchProcessTask1.setTrackingNumber(1);

		BatchProcessStep batchProcessStep = new BatchProcessStep();
		batchProcessStep.setId(1);
		batchProcessStep.setBatchIdentifire("Update band");

		TaskStep taskStep1 = new TaskStep();
		taskStep1.setIsPrepared('N');
		taskStep1.setStatus("SCHEDULED");
		taskStep1.setBatchProcessStep(batchProcessStep);
		taskStep1.setBatchProcessStep(batchProcessStep);

		List<TaskStep> taskStepsList = new ArrayList<>();
		taskStepsList.add(taskStep1);

		batchProcessTask1.setTaskSteps(taskStepsList);

		batchProcessTasksList.add(batchProcessTask1);
		
		
		
		
		
		Mockito.when(batchProcessService.getBatchProcessById(Mockito.anyString())).thenReturn(batchProcessOpt);
		Mockito.when(batchProcessTaskRepository
		.findByBatchProcessAndFinalStatusAndStartDateLessThanEqual(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(batchProcessTasksList);
		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(batchProcessTask1);
		
		Optional<String> stepIdentifier = Optional.empty();
		assertEquals(batchProcessTask1.getId(),
				batchProcessTaskServiceImpl.getUnPrepTask("testAppId", stepIdentifier).getId());
		
		
		
	}
	
	
	@Test
	public void testGetUnPrepTaskIsPrepared() {
		BatchProcess batchProcess = new BatchProcess();
		Optional<BatchProcess> batchProcessOpt = Optional.of(batchProcess);
		
		List<BatchProcessTask> batchProcessTasksList = new ArrayList<>();
		BatchProcessTask batchProcessTask1 = new BatchProcessTask();
		batchProcessTask1.setId(1);
		batchProcessTask1.setTrackingNumber(1);

		BatchProcessStep batchProcessStep = new BatchProcessStep();
		batchProcessStep.setId(1);
		batchProcessStep.setBatchIdentifire("Update band");

		TaskStep taskStep1 = new TaskStep();
		taskStep1.setIsPrepared('N');
		taskStep1.setStatus("SCHEDULED");
		taskStep1.setBatchProcessStep(batchProcessStep);
		taskStep1.setBatchProcessStep(batchProcessStep);

		List<TaskStep> taskStepsList = new ArrayList<>();
		taskStepsList.add(taskStep1);

		batchProcessTask1.setTaskSteps(taskStepsList);

		batchProcessTasksList.add(batchProcessTask1);
		
		
		
		
		
		Mockito.when(batchProcessService.getBatchProcessById(Mockito.anyString())).thenReturn(batchProcessOpt);
		Mockito.when(batchProcessTaskRepository
		.findByBatchProcessAndFinalStatusAndStartDateLessThanEqual(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(batchProcessTasksList);
		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(batchProcessTask1);
		
		Optional<String> stepIdentifier = Optional.of("Update band");
		assertEquals(batchProcessTask1.getId(),
				batchProcessTaskServiceImpl.getUnPrepTask("testAppId", stepIdentifier).getId());
		
		
		
	}

}
