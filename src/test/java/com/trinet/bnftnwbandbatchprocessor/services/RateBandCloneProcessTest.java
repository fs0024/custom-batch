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
public class RateBandCloneProcessTest {

    @Mock
    private PeopleSoftServiceImpl peopleSoftService;


    @Mock
    private BatchApiService batchApiService;

    @InjectMocks
    RateBandCloneProcess rateBandCloneProcess ;




    @BeforeAll
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(rateBandCloneProcess,"chunkSize" ,5);
        ReflectionTestUtils.setField(rateBandCloneProcess,"workercount" ,5);



    }

    @AfterAll
    public void after() throws Exception {
    }





    @Test
    public void processtaskTest() throws Exception {

        Mockito.when(batchApiService.getTaskItems(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyInt())).thenThrow( new Exception());

        Boolean result =   rateBandCloneProcess.processtask(null,null,null,null,null);
        Assert.assertEquals(result,false);
    }


    @Test
    public void processtaskTest2() throws Exception {

        Mockito.when(batchApiService.getTaskItems(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyInt())).thenReturn(new ArrayList<>());
        TaskStep step = new TaskStep();
        step.setId(1);
        Boolean result =   rateBandCloneProcess.processtask(step,null,null,null,null);
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
        Mockito.when(peopleSoftService.cloneForwardLoop(Mockito.anyList(),Mockito.anyString(),Mockito.anyString())).thenReturn(lists);
        Mockito.when(batchApiService.updateItemStatus(Mockito.anyList(),Mockito.anyString())).thenReturn(new BaseApiResponse());
        BatchParamValue value = new BatchParamValue();
        value.setValue("");
        Boolean result =   rateBandCloneProcess.processtask(step,null,value,value,"");
        Assert.assertEquals(result,false);
    }

    @Test
    public void processtaskTest4() throws Exception {
        List<TaskItem> list = new ArrayList<>();
        list.add(new TaskItem());
        Mockito.when(batchApiService.getTaskItems(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyInt())).thenReturn(list);
        TaskStep step = new TaskStep();
        step.setId(1);
        List<List<TaskItem>>lists = new ArrayList<>();
        List<TaskItem> lst1 = new ArrayList<>();
        lists.add(lst1);
        lists.add(lst1);
        Mockito.when(peopleSoftService.cloneForwardLoop(Mockito.anyList(),Mockito.anyString(),Mockito.anyString())).thenThrow(new Exception());
        Mockito.when(batchApiService.updateItemStatus(Mockito.anyList(),Mockito.anyString())).thenReturn(new BaseApiResponse());
        BatchParamValue value = new BatchParamValue();
        value.setValue("");
        Boolean result =   rateBandCloneProcess.processtask(step,null,value,value,"");
        Assert.assertEquals(result,false);
    }
}