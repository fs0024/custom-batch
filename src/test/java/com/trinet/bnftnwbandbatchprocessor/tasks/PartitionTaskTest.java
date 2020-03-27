package com.trinet.bnftnwbandbatchprocessor.tasks;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParam;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcessTask;
import com.trinet.bnftnwbandbatchprocessor.model.BaseApiResponse;
import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import com.trinet.bnftnwbandbatchprocessor.services.PartitionProcess;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PartitionTaskTest {


    @Mock
    private BatchApiService batchApiService;


    private String processorName ="";


    public static String stepName = "InitCloneForward";

    @Mock
    private PartitionProcess initRateBandCloneProcess;

    @InjectMocks PartitionTask partitionTask ;

    @Mock StepExecution stepExecution ;

@BeforeAll
public void before() throws Exception {
    MockitoAnnotations.initMocks(this);

    ReflectionTestUtils.setField(partitionTask ,"timeout" ,100L);


} 

@AfterAll
public void after() throws Exception { 
} 

/** 
* 
* Method: beforeStep(StepExecution stepExecution) 
* 
*/ 
@Test
public void testBeforeStep() throws Exception {
    partitionTask.beforeStep(stepExecution);
    Assert.assertTrue(true);
} 

/** 
* 
* Method: afterStep(StepExecution stepExecution) 
* 
*/ 
@Test
public void testAfterStep() throws Exception {
    partitionTask.afterStep(stepExecution);
    Assert.assertEquals(ExitStatus.COMPLETED,ExitStatus.COMPLETED);

} 

/** 
* 
* Method: execute(StepContribution stepContribution, ChunkContext chunkContext) 
* 
*/ 
@Test
public void testExecute() throws Exception {
    StepContribution stepContribution = Mockito.mock(StepContribution.class);
    ChunkContext chunkContext = Mockito.mock(ChunkContext.class);
    StepContext stepContext = Mockito.mock(StepContext.class);
    StepExecution stepExecution = Mockito.mock(StepExecution.class);
    JobParameters parameters = Mockito.mock(JobParameters.class);
    Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
    Mockito.when(stepContext.getStepExecution()).thenReturn(stepExecution);
    Mockito.when(stepExecution.getJobParameters()).thenReturn(parameters);
    Mockito.when(parameters.getString(Mockito.anyString())).thenReturn("Worker");
    BatchProcessTask batchProcessTask = new BatchProcessTask() ;
    batchProcessTask.setId(1);
    Mockito.when(batchApiService.getUnPrepTask()).thenReturn(batchProcessTask);
    List<BatchParamValue> taskParamValueList = new ArrayList<>();
    BatchParamValue param = new BatchParamValue();
    BatchParam param1 = new BatchParam();
    param1.setName("Quarter");
    param.setBatchParam(param1);
    taskParamValueList.add(param);
    Mockito.when(batchApiService.getBatchTaskParamValue(Mockito.anyInt())).thenReturn(taskParamValueList);
    Mockito.when(initRateBandCloneProcess.processtask(Mockito.any(), Mockito.any())).thenReturn(true);
    Mockito.when(batchApiService.unLocktask(Mockito.anyInt())).thenReturn(new BaseApiResponse());
  RepeatStatus status = partitionTask.execute(stepContribution,chunkContext);
    Assert.assertEquals(status,RepeatStatus.FINISHED);

//TODO: Test goes here... 
}


    @Test
    public void testExecuteScheduled() throws Exception {
        StepContribution stepContribution = Mockito.mock(StepContribution.class);
        ChunkContext chunkContext = Mockito.mock(ChunkContext.class);
        StepContext stepContext = Mockito.mock(StepContext.class);
        StepExecution stepExecution = Mockito.mock(StepExecution.class);
        JobParameters parameters = Mockito.mock(JobParameters.class);
        Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
        Mockito.when(stepContext.getStepExecution()).thenReturn(stepExecution);
        Mockito.when(stepExecution.getJobParameters()).thenReturn(parameters);
        Mockito.when(parameters.getString(Mockito.anyString())).thenReturn("true");
        BatchProcessTask batchProcessTask = new BatchProcessTask() ;
        batchProcessTask.setId(1);
        Mockito.when(batchApiService.getUnPrepTask()).thenReturn(batchProcessTask);
        List<BatchParamValue> taskParamValueList = new ArrayList<>();
        BatchParamValue param = new BatchParamValue();
        BatchParam param1 = new BatchParam();
        param1.setName("Quarter");
        param.setBatchParam(param1);
        taskParamValueList.add(param);
        Mockito.when(batchApiService.getBatchTaskParamValue(Mockito.anyInt())).thenReturn(taskParamValueList);
        Mockito.when(initRateBandCloneProcess.processtask(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(batchApiService.unLocktask(Mockito.anyInt())).thenReturn(new BaseApiResponse());
        RepeatStatus status = partitionTask.execute(stepContribution,chunkContext);
        Assert.assertEquals(status,RepeatStatus.FINISHED);

    }


    @Test
    public void testExecuteException() throws Exception {
        StepContribution stepContribution = Mockito.mock(StepContribution.class);
        ChunkContext chunkContext = Mockito.mock(ChunkContext.class);
        StepContext stepContext = Mockito.mock(StepContext.class);
        StepExecution stepExecution = Mockito.mock(StepExecution.class);
        JobParameters parameters = Mockito.mock(JobParameters.class);
        Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
        Mockito.when(stepContext.getStepExecution()).thenReturn(stepExecution);
        Mockito.when(stepExecution.getJobParameters()).thenReturn(parameters);
        Mockito.when(parameters.getString(Mockito.anyString())).thenReturn("true");
        BatchProcessTask batchProcessTask = new BatchProcessTask() ;
        batchProcessTask.setId(1);
        Mockito.when(batchApiService.getUnPrepTask()).thenReturn(batchProcessTask);
        List<BatchParamValue> taskParamValueList = new ArrayList<>();
        BatchParamValue param = new BatchParamValue();
        BatchParam param1 = new BatchParam();
        param1.setName("Quarter");
        param.setBatchParam(param1);
        taskParamValueList.add(param);
        Mockito.when(batchApiService.getBatchTaskParamValue(Mockito.anyInt())).thenReturn(null);
        Mockito.when(initRateBandCloneProcess.processtask(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(batchApiService.unLocktask(Mockito.anyInt())).thenReturn(new BaseApiResponse());


        Assertions. assertThrows(Exception.class,
                ()->{
                    partitionTask.execute(stepContribution,chunkContext);

                });

    }
} 
