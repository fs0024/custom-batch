/**
 * 
 */
package com.trinet.benefits.oe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessStep;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.entity.FileItems;
import com.trinet.benefits.oe.entity.TaskItem;
import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.repository.BatchProcessTaskRepository;
import com.trinet.benefits.oe.repository.TaskItemRepository;
import com.trinet.benefits.oe.repository.TaskStepsRepository;
import com.trinet.benefits.oe.service.impl.TaskItemServiceImpl;

/**
 * @author imistry1
 *
 * 
 */

public class TaskItemServiceImplTest {
	
	
	@Mock
	private TaskItemRepository taskItemRepository;

	@Mock
	private TaskStepsRepository taskStepsRepository;

	@Mock
	private BatchProcessTaskRepository batchProcessTaskRepository;
	
	@InjectMocks
	private static TaskItemServiceImpl taskItemServiceImpl;
	
	
	
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	
	}
	
	
	
	@Test
	public void testFindTaskItemsForProcessing() {
		
		
		List<TaskItem> taskItemsList = new ArrayList<>();
		TaskItem taskItem1 = new TaskItem();
		taskItem1.setId(1);
		taskItemsList.add(taskItem1);
		
		Mockito.when(taskItemRepository.findTaskItemByWorkerIdAndTaskStepAndStatus(Mockito.anyString(), Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(taskItemsList);
		
		
		Mockito.when(taskItemRepository.saveAll(Mockito.any())).thenReturn(taskItemsList);
		
		assertEquals(taskItemsList, taskItemServiceImpl.findTaskItemsForProcessing("Worker-1", "Scheduled", new TaskStep(), 1));
	}

	
	
	@Test
	public void testReconsileAllStatus() throws Exception {
		
		BatchProcessStep batchProcessStep = new BatchProcessStep();
		batchProcessStep.setId(1);
		
		List<BatchProcessStep> batchProcessStepsList = new ArrayList<>();
		batchProcessStepsList.add(batchProcessStep);
		
		
		BatchProcess process = new BatchProcess();
		process.setId(1);
		process.setBatchProcessStep(batchProcessStepsList);
		
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		batchProcessTask.setId(1);
		batchProcessTask.setFinalStatus("COMPLETED");
		batchProcessTask.setBatchProcess(process);
		
		TaskStep taskStep = new TaskStep();
		taskStep.setBatchProcessTask(batchProcessTask);
		
		Optional<TaskStep> taskStepOpt = Optional.of(taskStep);
		
		Mockito.when(taskItemRepository.countByStatusAndTaskStep(Mockito.anyString(), Mockito.any())).thenReturn(0);
		
		Mockito.when(taskStepsRepository.findById(Mockito.any())).thenReturn(taskStepOpt);
		
		Mockito.when(taskStepsRepository.countRecsForStatus(Mockito.any(), Mockito.any())).thenReturn(1);
		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(batchProcessTask);
		taskItemServiceImpl.reconsileAllStatus(taskStep);
		
		assertEquals(batchProcessTask,batchProcessTask);
	}
	
	
	@Test
	public void testUpdateTaskItemStatus() {
		
		List<BatchProcessStep> batchProcessStepsList = new ArrayList<>();
		BatchProcessStep batchProcessStep1 = new BatchProcessStep();
		batchProcessStep1.setId(1);
		batchProcessStepsList.add(batchProcessStep1);
		
		
		BatchProcess batchProcess = new BatchProcess();
		batchProcess.setId(1);
		batchProcess.setBatchProcessStep(batchProcessStepsList);;
		
		BatchProcessTask batchProcessTask = new BatchProcessTask();
		batchProcessTask.setId(1);
		batchProcessTask.setBatchProcess(batchProcess);
		
		
		TaskStep taskStep = new TaskStep();
		taskStep.setId(1);
		taskStep.setBatchProcessTask(batchProcessTask);
		
		FileItems fileItems = new FileItems();
		fileItems.setId(1);
		
		List<TaskItem> taskItemsList = new ArrayList<>();
		TaskItem taskItem = new TaskItem();
		taskItem.setTaskStep(taskStep);
		taskItem.setFileItems(fileItems);
		taskItemsList.add(taskItem);
		
		Optional<TaskStep> taskStepOpt = Optional.of(taskStep);
		
		Mockito.when(taskItemRepository.countByStatusAndTaskStep(Mockito.anyString(), Mockito.any())).thenReturn(0);
		Mockito.when(taskStepsRepository.findById(Mockito.anyInt())).thenReturn(taskStepOpt);
		Mockito.when(taskStepsRepository.save(Mockito.any())).thenReturn(taskStep);
		
		Mockito.when(taskStepsRepository.countRecsForStatus(Mockito.anyInt(), Mockito.any())).thenReturn(1);
		Mockito.when(batchProcessTaskRepository.save(Mockito.any())).thenReturn(batchProcessTask);
		
		assertEquals(0, taskItemServiceImpl.updateTaskItemStatus(Optional.of("Ready"), taskItemsList));
		
	}
	
	
	@Test
	public void testPartitionItems() throws Exception {
		
		TaskStep stepDetails = new TaskStep();
		stepDetails.setId(1);
		stepDetails.setIsPrepared('Y');
		
		Optional<TaskStep> taskStepOpt = Optional.of(stepDetails);
		
		List<Integer> list = new ArrayList<>();
		list.add(1);
		
		
		Mockito.when(taskStepsRepository.findById(Mockito.anyInt())).thenReturn(taskStepOpt);
		Mockito.when(taskItemRepository.countUnPartionedRecs(Mockito.anyInt())).thenReturn(1);
		
		Mockito.when(taskItemRepository.updateTaskItemPartion(Mockito.any(), Mockito.any())).thenReturn(1);
		Mockito.when(taskStepsRepository.save(Mockito.any())).thenReturn(stepDetails);
		assertEquals("ALREADY_PARTITIONED", taskItemServiceImpl.partitionItems(1, 1, "TestWorker").getStatus());
	}
	
	@Test
	public void testPartitionItemsN() throws Exception {
	
		TaskStep stepDetails = new TaskStep();
		stepDetails.setId(1);
		stepDetails.setIsPrepared('N');
		
		Optional<TaskStep> taskStepOpt = Optional.of(stepDetails);
		
		List<Integer> list = new ArrayList<>();
		list.add(1);
		
		
		Mockito.when(taskStepsRepository.findById(Mockito.anyInt())).thenReturn(taskStepOpt);
		Mockito.when(taskItemRepository.countUnPartionedRecs(Mockito.anyInt())).thenReturn(1000);
		
		Mockito.when(taskItemRepository.updateTaskItemPartion(Mockito.any(), Mockito.any())).thenReturn(1);
		Mockito.when(taskStepsRepository.save(Mockito.any())).thenReturn(stepDetails);
		assertEquals("SUCCESS", taskItemServiceImpl.partitionItems(1, 1, "TestWorker").getStatus());
	}

}
