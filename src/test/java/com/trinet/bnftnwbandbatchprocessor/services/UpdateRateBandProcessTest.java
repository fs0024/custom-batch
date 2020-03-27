package com.trinet.bnftnwbandbatchprocessor.services;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;
import com.trinet.bnftnwbandbatchprocessor.model.BaseApiResponse;
import com.trinet.bnftnwbandbatchprocessor.util.PeopleSoftServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateRateBandProcessTest {

    @Mock
    private PeopleSoftServiceImpl peopleSoftService;


    @Mock
    private BatchApiService batchApiService;

    @InjectMocks
    UpdateRateBandProcess updateRateBandProcess ;




    @BeforeAll
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(updateRateBandProcess,"chunkSize" ,5);
        ReflectionTestUtils.setField(updateRateBandProcess,"workercount" ,5);



    }

    @AfterAll
    public void after() throws Exception {
    }





    @Test
    public void processtaskTest() throws Exception {

        Mockito.when(batchApiService.getTaskItems(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyInt())).thenThrow( new Exception());

        Boolean result =   updateRateBandProcess.processtask(null,null,null,null);
        Assert.assertEquals(result,false);
    }


    @Test
    public void processtaskTest2() throws Exception {

        Mockito.when(batchApiService.getTaskItems(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyInt())).thenReturn(new ArrayList<>());
        TaskStep step = new TaskStep();
        step.setId(1);
        Boolean result =   updateRateBandProcess.processtask(step,null,null,null);
        Assert.assertEquals(result,true);
    }

    @Test
    public void processtaskTest3() throws Exception {
        List<TaskItem> list = new ArrayList<>();
        list.add(new TaskItem());
        Mockito.when(batchApiService.getTaskItems(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyInt())).thenReturn(list);
        TaskStep step = new TaskStep();
        step.setId(1);
        List<List<TaskItem>>lists = new ArrayList<>();
        List<TaskItem> lst1 = new ArrayList<>();
        lists.add(lst1);
        lists.add(lst1);
        Mockito.when(peopleSoftService.updateHealthLoop(Mockito.anyList(),Mockito.anyString(),Mockito.anyString())).thenReturn(lists);
        Mockito.when(batchApiService.updateItemStatus(Mockito.anyList(),Mockito.anyString())).thenReturn(new BaseApiResponse());
        BatchParamValue value = new BatchParamValue();
        value.setValue("");
        Boolean result =   updateRateBandProcess.processtask(step,value,value,"");
        Assert.assertEquals(result,false);
    }
}