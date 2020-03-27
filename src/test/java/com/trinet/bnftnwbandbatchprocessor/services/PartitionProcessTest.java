package com.trinet.bnftnwbandbatchprocessor.services;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.*;
import com.trinet.bnftnwbandbatchprocessor.model.BaseApiResponse;
import com.trinet.bnftnwbandbatchprocessor.util.PeopleSoftServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PartitionProcessTest {


    @Mock
    private PeopleSoftServiceImpl peopleSoftService;


    @Mock
    private BatchApiService batchApiService;

    @Mock
    private InsertItemService insertItemService;

    @InjectMocks
    PartitionProcess partitionProcess ;



    @BeforeAll
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(partitionProcess,"workercount" ,5);



    }

    @AfterAll
    public void after() throws Exception {
    }



    @Test
    public void  processtask() throws  Exception{
        BatchProcessTask batchProcessTask = new BatchProcessTask() ;
        batchProcessTask.setId(1);

        List<TaskStep> taskSteps = new ArrayList<>();

        BatchProcessStep batchProcessStep = new BatchProcessStep();
        batchProcessStep.setBatchIdentifire("cloneForward");
        TaskStep step = new TaskStep();
        step.setBatchProcessStep(batchProcessStep);
        step.setIsPrepared('N');
        step.setStatus("SCHEDULED");

        BatchProcessStep batchProcessStep2 = new BatchProcessStep();
        batchProcessStep2.setBatchIdentifire("updateRateBands");
        TaskStep step2 = new TaskStep();
        step2.setBatchProcessStep(batchProcessStep2);
        step2.setIsPrepared('N');
        step2.setStatus("SCHEDULED");


        taskSteps.add(step);
        taskSteps.add(step2);



        batchProcessTask.setTaskSteps(taskSteps);

        BatchParamValue param = new BatchParamValue();
        BatchParam param1 = new BatchParam();
        param1.setName("Quarter");
        param.setBatchParam(param1);

         Mockito.when(insertItemService.removeOrphans(Mockito.any())).thenReturn("");
         Mockito.when(peopleSoftService.getPFClientsCount(Mockito.anyString())).thenReturn(2);
        List<String> list = new ArrayList<>();
        list.add("test");
        Mockito.when(peopleSoftService.getPFClients(Mockito.anyString(), Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(list);
        Mockito.when(insertItemService.insertItems(Mockito.anyList())).thenReturn("");

        Mockito.when(batchApiService.updarePrepFlag(Mockito.anyInt())).thenReturn(new BaseApiResponse());

        Mockito.when(batchApiService.preparePartiton(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt())).thenReturn(new BaseApiResponse());


        partitionProcess.processtask(batchProcessTask,param);


    }
}