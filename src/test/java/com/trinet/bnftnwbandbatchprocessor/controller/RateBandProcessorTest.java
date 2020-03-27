package com.trinet.bnftnwbandbatchprocessor.controller;

import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import com.trinet.bnftnwbandbatchprocessor.util.BatchManager;
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


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RateBandProcessorTest {


    @Mock
    private BatchManager batchManager;

    @Mock
    private BatchApiService batchApiService ;

    @InjectMocks
    RateBandProcessor rateBandProcessor ;



    @BeforeAll
    public void before() throws Exception {

        MockitoAnnotations.initMocks(this);


    }

    @AfterAll
    public void after() throws Exception {
    }



    @Test
    public void monitor() {
       Assert.assertEquals("success", rateBandProcessor.monitor());
    }

    @Test
    public void updateSchedule() throws  Exception{

        Mockito.when(batchManager.updateSchedule(Mockito.anyString())).thenReturn("success");

        Assert.assertEquals("success", rateBandProcessor.updateSchedule());
    }

    @Test
    public void stopjobs() throws  Exception{
        Mockito.when(batchManager.stopJobs()).thenReturn("success");

        Assert.assertEquals("success", rateBandProcessor.stopjobs());
    }

    @Test
    public void startProcess() throws  Exception{
        Mockito.when(batchManager.scheduleRun(Mockito.anyString())).thenReturn("success");

        Assert.assertEquals("success", rateBandProcessor.startProcess());
    }

    @Test
    public void killProcess() throws  Exception{
        Mockito.when(batchManager.stopSchedule(Mockito.anyString())).thenReturn("success");

        Assert.assertEquals("success", rateBandProcessor.killProcess());
    }

    @Test
    public void runNow() throws  Exception{
        Mockito.when(batchManager.runNow()).thenReturn("success");

        Assert.assertEquals("success", rateBandProcessor.runNow());
    }
}