/**
 * 
 */
package com.trinet.benefits.oe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.trinet.benefits.oe.entity.TaskStep;
import com.trinet.benefits.oe.repository.TaskStepsRepository;
import com.trinet.benefits.oe.service.impl.TaskStepServiceImpl;

/**
 * @author imistry1
 *
 * 
 */
@DisplayName("Testing TaskStepServiceImpl")
@SuppressWarnings("unchecked")
public class TaskStepServiceImplTest {
	
	
	@Mock
	private static TaskStepsRepository taskStepsRepository ;
	
	
	@InjectMocks
	private static TaskStepServiceImpl taskStepServiceImpl;
	
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testUpdateTaskStepPrepareFlag() {
		
		Optional<TaskStep> step = Optional.of(new TaskStep());
		Mockito.when( taskStepsRepository.findById(Mockito.anyInt())).thenReturn(step);
		assertEquals(1, taskStepServiceImpl.updateTaskStepPrepareFlag(1));
		
	}
	
	@Test
	public void testUpdateTaskStepPrepareFlagFail() {
		
		TaskStep taskStepOp = new TaskStep();
		taskStepOp.setIsPrepared('Y');
		Optional<TaskStep> step = Optional.of(taskStepOp);
		Mockito.when( taskStepsRepository.findById(Mockito.anyInt())).thenReturn(step);
		assertEquals(0, taskStepServiceImpl.updateTaskStepPrepareFlag(1));
		
	}
	
	@Test
	public void testUpdateTaskStepPrepareFlagifNull() {
		
		TaskStep taskStepOp = new TaskStep();
		
		Optional<TaskStep> step = Optional.of(taskStepOp);
		Mockito.when( taskStepsRepository.findById(Mockito.anyInt())).thenReturn(step);
		assertEquals(1, taskStepServiceImpl.updateTaskStepPrepareFlag(1));
		
	}
	
	
	@Test
	public void testGetTaskStepIdByAppIdAndStep() {
		
		Mockito.when(taskStepsRepository.getTaskStepIdByAppIdAndStep(Mockito.anyString(), Mockito.anyInt())).thenReturn(1);
		
		assertEquals(1, taskStepServiceImpl.getTaskStepIdByAppIdAndStep("1", 1));
	}
	

}
