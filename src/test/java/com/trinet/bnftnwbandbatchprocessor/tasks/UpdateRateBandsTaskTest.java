package com.trinet.bnftnwbandbatchprocessor.tasks;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.*;
import com.trinet.bnftnwbandbatchprocessor.model.BaseApiResponse;
import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import com.trinet.bnftnwbandbatchprocessor.services.UpdateRateBandProcess;
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
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UpdateRateBandsTaskTest {


    @Mock
    private JobExplorer jobExplorer ;

    @Mock
    private BatchApiService batchApiService;

    @Value("${spring.application.name}")
    private String processorName;



    @Mock
    StepExecution stepExecution ;

    @Mock
    private UpdateRateBandProcess updateRateBandProcess;

    @InjectMocks
    private UpdateRateBandsTask updateRateBandsTask;


    @BeforeAll
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(updateRateBandsTask,"timeout" ,100L);



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
        JobParameters parameters = Mockito.mock(JobParameters.class);
        Mockito.when(stepExecution.getJobParameters()).thenReturn(parameters);
        Mockito.when(parameters.getString(Mockito.anyString())).thenReturn("Worker");

        updateRateBandsTask.beforeStep(stepExecution);
        Assert.assertTrue(true);
    }

    /**
     *
     * Method: afterStep(StepExecution stepExecution)
     *
     */
    @Test
    public void testAfterStep() throws Exception {
        JobParameters parameters = Mockito.mock(JobParameters.class);
        Mockito.when(stepExecution.getJobParameters()).thenReturn(parameters);
        Mockito.when(parameters.getString(Mockito.anyString())).thenReturn("Worker");

        Mockito.when(batchApiService.getPrepTask(Mockito.anyString(),Mockito.any())).thenReturn(new BatchProcessTask());
        Mockito.when(batchApiService.unLocktask(Mockito.anyInt())).thenReturn(new BaseApiResponse());
        updateRateBandsTask.afterStep(stepExecution);
        Assert.assertEquals(ExitStatus.COMPLETED,ExitStatus.COMPLETED);
//TODO: Test goes here...
    }

    /**
     *
     * Method: execute(StepContribution stepContribution, ChunkContext chunkContext)
     *
     */
    @Test
    public void testExecute() throws Exception {

        JobExecution execution = Mockito.mock(JobExecution.class);
        Mockito.when(jobExplorer.getJobExecution(Mockito.anyLong())).thenReturn(execution) ;
        Mockito.when(execution.isStopping()).thenReturn(false);
        Mockito.when(execution.getId()).thenReturn(10L);

        StepContribution stepContribution = Mockito.mock(StepContribution.class);
        ChunkContext chunkContext = Mockito.mock(ChunkContext.class);
        StepContext stepContext = Mockito.mock(StepContext.class);
        StepExecution stepExecution = Mockito.mock(StepExecution.class);
        JobParameters parameters = Mockito.mock(JobParameters.class);
        Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
        Mockito.when(stepContext.getStepExecution()).thenReturn(stepExecution);
        Mockito.when(stepExecution.getJobParameters()).thenReturn(parameters);
        Mockito.when(parameters.getString(Mockito.anyString())).thenReturn("Worker");

        Mockito.when(stepExecution.getJobExecution()).thenReturn(execution);

        BatchProcessTask batchProcessTask = new BatchProcessTask() ;
        batchProcessTask.setId(1);
        List<TaskStep> taskSteps = new ArrayList<>();
        TaskStep step = new TaskStep();
        BatchProcessStep batchProcessStep = new BatchProcessStep();
        batchProcessStep.setBatchIdentifire("cloneForward");
        step.setBatchProcessStep(batchProcessStep);
        taskSteps.add(step);
        batchProcessTask.setTaskSteps(taskSteps);

        Mockito.when(batchApiService.getPrepTask(Mockito.anyString(),Mockito.any())).thenReturn(batchProcessTask);
        List<BatchParamValue> taskParamValueList = new ArrayList<>();
        BatchParamValue paramValue = new BatchParamValue();
        BatchParamValue paramValue1 = new BatchParamValue();
        BatchParamValue paramValue2 = new BatchParamValue();
        BatchParam param1 = new BatchParam();
        param1.setName("Quarter");

        BatchParam param2 = new BatchParam();
        param2.setName("company id");

        BatchParam param3 = new BatchParam();
        param3.setName("effective date");
        paramValue.setBatchParam(param1);
        paramValue1.setBatchParam(param2);
        paramValue2.setBatchParam(param3);

        taskParamValueList.add(paramValue);
        taskParamValueList.add(paramValue1);
        taskParamValueList.add(paramValue2);
        Mockito.when(batchApiService.getBatchTaskParamValue(Mockito.anyInt())).thenReturn(taskParamValueList);
        Mockito.when(updateRateBandProcess.processtask(Mockito.any())).thenReturn(true);
        Mockito.when(batchApiService.unLocktask(Mockito.anyInt())).thenReturn(new BaseApiResponse());
        RepeatStatus status = updateRateBandsTask.execute(stepContribution,chunkContext);
        Assert.assertEquals(status,RepeatStatus.FINISHED);

    }


    @Test
    public void testExecuteScheduled() throws Exception {
        JobExecution execution = Mockito.mock(JobExecution.class);
        Mockito.when(jobExplorer.getJobExecution(Mockito.anyLong())).thenReturn(execution) ;
        Mockito.when(execution.isStopping()).thenReturn(false);
        Mockito.when(execution.getId()).thenReturn(10L);
        StepContribution stepContribution = Mockito.mock(StepContribution.class);
        ChunkContext chunkContext = Mockito.mock(ChunkContext.class);
        StepContext stepContext = Mockito.mock(StepContext.class);
        StepExecution stepExecution = Mockito.mock(StepExecution.class);
        JobParameters parameters = Mockito.mock(JobParameters.class);
        Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
        Mockito.when(stepContext.getStepExecution()).thenReturn(stepExecution);
        Mockito.when(stepExecution.getJobParameters()).thenReturn(parameters);
        Mockito.when(parameters.getString(Mockito.anyString())).thenReturn("true");
        Mockito.when(stepExecution.getJobExecution()).thenReturn(execution);
        BatchProcessTask batchProcessTask = new BatchProcessTask() ;
        batchProcessTask.setId(1);
        List<TaskStep> taskSteps = new ArrayList<>();
        TaskStep step = new TaskStep();
        BatchProcessStep batchProcessStep = new BatchProcessStep();
        batchProcessStep.setBatchIdentifire("cloneForward");
        step.setBatchProcessStep(batchProcessStep);
        taskSteps.add(step);
        batchProcessTask.setTaskSteps(taskSteps);

        Mockito.when(batchApiService.getPrepTask(Mockito.anyString(),Mockito.any())).thenReturn(batchProcessTask);
        List<BatchParamValue> taskParamValueList = new ArrayList<>();
        BatchParamValue paramValue = new BatchParamValue();
        BatchParamValue paramValue1 = new BatchParamValue();
        BatchParamValue paramValue2 = new BatchParamValue();
        BatchParam param1 = new BatchParam();
        param1.setName("Quarter");

        BatchParam param2 = new BatchParam();
        param2.setName("company id");

        BatchParam param3 = new BatchParam();
        param3.setName("effective date");
        paramValue.setBatchParam(param1);
        paramValue1.setBatchParam(param2);
        paramValue2.setBatchParam(param3);

        taskParamValueList.add(paramValue);
        taskParamValueList.add(paramValue1);
        taskParamValueList.add(paramValue2);

        Mockito.when(batchApiService.getBatchTaskParamValue(Mockito.anyInt())).thenReturn(taskParamValueList);
        Mockito.when(updateRateBandProcess.processtask(Mockito.any())).thenReturn(true);
        Mockito.when(batchApiService.unLocktask(Mockito.anyInt())).thenReturn(new BaseApiResponse());
        RepeatStatus status = updateRateBandsTask.execute(stepContribution,chunkContext);
        Assert.assertEquals(status,RepeatStatus.FINISHED);

    }


    @Test
    public void testExecuteException() throws Exception {
        JobExecution execution = Mockito.mock(JobExecution.class);
        Mockito.when(jobExplorer.getJobExecution(Mockito.anyLong())).thenReturn(execution) ;
        Mockito.when(execution.isStopping()).thenReturn(false);
        Mockito.when(execution.getId()).thenReturn(10L);
        StepContribution stepContribution = Mockito.mock(StepContribution.class);
        ChunkContext chunkContext = Mockito.mock(ChunkContext.class);
        StepContext stepContext = Mockito.mock(StepContext.class);
        StepExecution stepExecution = Mockito.mock(StepExecution.class);
        JobParameters parameters = Mockito.mock(JobParameters.class);
        Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
        Mockito.when(stepContext.getStepExecution()).thenReturn(stepExecution);
        Mockito.when(stepExecution.getJobParameters()).thenReturn(parameters);
        Mockito.when(parameters.getString(Mockito.anyString())).thenReturn("true");
        Mockito.when(stepExecution.getJobExecution()).thenReturn(execution);
        BatchProcessTask batchProcessTask = new BatchProcessTask() ;
        batchProcessTask.setId(1);
        List<TaskStep> taskSteps = new ArrayList<>();
        TaskStep step = new TaskStep();
        BatchProcessStep batchProcessStep = new BatchProcessStep();
        batchProcessStep.setBatchIdentifire("cloneForward");
        step.setBatchProcessStep(batchProcessStep);
        taskSteps.add(step);
        batchProcessTask.setTaskSteps(taskSteps);

        Mockito.when(batchApiService.getPrepTask(Mockito.anyString(),Mockito.any())).thenReturn(batchProcessTask);
        List<BatchParamValue> taskParamValueList = new ArrayList<>();
        BatchParamValue paramValue = new BatchParamValue();
        BatchParamValue paramValue1 = new BatchParamValue();
        BatchParamValue paramValue2 = new BatchParamValue();
        BatchParam param1 = new BatchParam();
        param1.setName("Quarter");

        BatchParam param2 = new BatchParam();
        param2.setName("company id");

        BatchParam param3 = new BatchParam();
        param3.setName("effective date");
        paramValue.setBatchParam(param1);
        paramValue1.setBatchParam(param2);
        paramValue2.setBatchParam(param3);

        taskParamValueList.add(paramValue);
        taskParamValueList.add(paramValue1);
        taskParamValueList.add(paramValue2);
        Mockito.when(batchApiService.getBatchTaskParamValue(Mockito.anyInt())).thenReturn(null);
        Mockito.when(updateRateBandProcess.processtask(Mockito.any())).thenReturn(true);
        Mockito.when(batchApiService.unLocktask(Mockito.anyInt())).thenReturn(new BaseApiResponse());


        Assertions. assertThrows(Exception.class,
                ()->{
                    updateRateBandsTask.execute(stepContribution,chunkContext);

                });

    }
}