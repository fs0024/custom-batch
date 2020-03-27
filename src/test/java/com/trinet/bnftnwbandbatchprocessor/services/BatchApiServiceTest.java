package com.trinet.bnftnwbandbatchprocessor.services;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcess;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcessTask;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.model.BaseApiResponse;
import com.trinet.bnftnwbandbatchprocessor.util.ApiEndPoints;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchApiServiceTest {


    @Mock
    private RestTemplate restTemplate ;



    @Mock
    private ApiEndPoints apiEndPoints ;

    @Mock
    private BatchLoginService loginService;

    @InjectMocks
    BatchApiService batchApiService ;

    @BeforeAll
    public void before() throws Exception {



        MockitoAnnotations.initMocks(this);


    }

    @AfterAll
    public void after() throws Exception {
    }


    @Test
    public void getApiCache() {

       Assert.assertEquals( 0,BatchApiService.getApiCache().size() ) ;
    }


    @Test
    public void getBatchConfig() throws  Exception{
        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getBatchConfigAppId()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BatchProcess process = new BatchProcess();
        process.setId(1);
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(process,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

       Assert.assertEquals(process.getId() ,batchApiService.getBatchConfig().getId()) ;
    }

    @Test
    public void getUnPrepTask() throws Exception{
        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getBatchTaskUrl()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BatchProcessTask process = new BatchProcessTask();
        process.setId(1);
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(process,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(process.getId() ,batchApiService.getUnPrepTask().getId()) ;

    }

    @Test
    public void preparePartiton() throws Exception {

        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getPartitionItemUrl()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BaseApiResponse process = new BaseApiResponse();
        process.setStatus("test");
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(process,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(process.getStatus() ,batchApiService.preparePartiton(1,"",1).getStatus()) ;
    }

    @Test
    public void getPrepTask() throws Exception{

        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getPartitionItemUrl()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BatchProcessTask process = new BatchProcessTask();
        process.setId(1);
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(process,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(process.getId() ,batchApiService.getPrepTask("",new Date()).getId()) ;

    }



    @Test
    public void getTaskItems() throws Exception {
               Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getGetTaskItemsUrl()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        TaskItem[] list = new TaskItem[1];
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(list,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(list.length ,batchApiService.getTaskItems("",1,"",1).size()) ;

    }

    @Test
    public void updateItemStatus() throws Exception{

        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getUpdateItemUrl()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BaseApiResponse process = new BaseApiResponse();
        process.setStatus("test");
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(process,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(process.getStatus() ,batchApiService.updateItemStatus(new ArrayList<>(),"").getStatus()) ;

    }

    @Test
    public void updarePrepFlag()throws Exception {

        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getUpdatePrepFlagUrl()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BaseApiResponse process = new BaseApiResponse();
        process.setStatus("test");
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(process,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(process.getStatus() ,batchApiService.updarePrepFlag(1).getStatus()) ;
    }

    @Test
    public void updareBatchProcessTaskStatus() throws Exception {

        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getUpdateTaskStatusUrl()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BaseApiResponse process = new BaseApiResponse();
        process.setStatus("test");
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(process,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(process.getStatus() ,batchApiService.updareBatchProcessTaskStatus(1,"").getStatus()) ;

    }

    @Test
    public void unLocktask() throws Exception{

        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getUnlocktaskUrl()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BaseApiResponse process = new BaseApiResponse();
        process.setStatus("test");
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(process,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(process.getStatus() ,batchApiService.unLocktask(1).getStatus()) ;

    }

    @Test
    public void getBatchTaskParamValue() throws  Exception{

        Mockito.when(apiEndPoints.getApiUrl()).thenReturn("");
        Mockito.when(apiEndPoints.getBatchTaskParamValue()).thenReturn("");
        Mockito.when(loginService.login()).thenReturn("");
        BatchParamValue[] list = new BatchParamValue[1];
        // ResponseEntity<T> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity(getAuthHeader()), inclass, uriVariables);
        ResponseEntity entity = new ResponseEntity(list,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(),Mockito.any(),Mockito.any(Class.class), (Object[]) Matchers.anyVararg())).thenReturn(entity) ;

        Assert.assertEquals(list.length ,batchApiService.getBatchTaskParamValue(1).size()) ;

    }
}