package com.trinet.bnftnwbandbatchprocessor.services;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcess;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcessTask;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.model.BaseApiResponse;
import com.trinet.bnftnwbandbatchprocessor.util.ApiEndPoints;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Log4j2
@Service
public class BatchApiService {

    @Autowired
    private RestTemplate restTemplate ;

    @Value( "${batch.schedule}" )
    private String schedule;

    @Value( "${spring.application.name}" )
    private String processorName;

    @Autowired
    private  ApiEndPoints apiEndPoints ;
    
    @Autowired
    private BatchLoginService loginService;


    public static Map<String, Object> getApiCache() {
        return apiCache;
    }


    private static Map<String,Object> apiCache = new HashedMap();



        private HttpHeaders getAuthHeader(HttpMethod httpMethod){
            String token = loginService.login();
            HttpHeaders headers = new HttpHeaders();
            if(httpMethod.equals(HttpMethod.GET))
              headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            headers.set("token", token);
            return headers ;
        }

      private <T> ResponseEntity<T> callApi(HttpMethod method,HttpEntity entity,String apiUrl,Class<T> inclass,Object... uriVariables )throws Exception{
          log.debug("Api call url :" + apiUrl);
         try {
            ResponseEntity<T> response = restTemplate.exchange(apiUrl, method, entity, inclass, uriVariables);
            return response;

         } catch (Exception e){
             log.error("Error invoking API : " +apiUrl+"<message>"+ e.getMessage());
             throw e ;
         }

       }

       private <T> ResponseEntity<T> callGetApi(String apiUrl,Class<T> inclass,Object... uriVariables ) throws Exception {
         return  callApi(HttpMethod.GET,new HttpEntity(getAuthHeader(HttpMethod.GET)),apiUrl,inclass,uriVariables) ;
         }


         public BatchProcess getBatchConfig()throws  Exception {
            String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getBatchConfigAppId();
            return callGetApi(apiUrl, BatchProcess.class,processorName).getBody();
    }
    
    


       public BatchProcessTask getUnPrepTask() throws  Exception{
           String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getBatchTaskUrl();
           return callGetApi(apiUrl, BatchProcessTask.class,processorName).getBody();

       }

       public BaseApiResponse preparePartiton(Integer stepId ,String workerPrefix, Integer workerCount)throws  Exception{
           String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getPartitionItemUrl();
           return callGetApi( apiUrl, BaseApiResponse.class,stepId, workerCount,workerPrefix).getBody();

       }


    public BatchProcessTask getPrepTask(String stepName) throws  Exception{

        String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getBatchTaskPreparedUrl();
        return callGetApi( apiUrl, BatchProcessTask.class,processorName, stepName).getBody();

    }


    public synchronized BatchProcessTask getPrepTask(String stepName, Date date) throws  Exception{
        String key = stepName+date.toString();
        if(apiCache.containsKey(key)) {
        	  log.debug("returning task from cache for step "+stepName);
              return (BatchProcessTask)apiCache.get(key);
        }
        BatchProcessTask task = getPrepTask(stepName);
        if(task!= null)
            apiCache.put(key,task);
        return task;

    }

    public List<TaskItem> getTaskItems(String workerName , Integer id ,String status ,Integer chunk)throws  Exception{
        String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getGetTaskItemsUrl();
        return Arrays.asList(callGetApi( apiUrl, TaskItem[].class, workerName,id,status,chunk).getBody());
    }


    public BaseApiResponse updateItemStatus (List<TaskItem> list,String status)throws  Exception{
         String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getUpdateItemUrl();
         return callApi(HttpMethod.POST, new HttpEntity(list,getAuthHeader(HttpMethod.POST)), apiUrl,BaseApiResponse.class, status).getBody();
    }


    public BaseApiResponse updarePrepFlag (Integer  id)throws  Exception{
        String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getUpdatePrepFlagUrl();
        return callGetApi( apiUrl, BaseApiResponse.class,id).getBody();

    }

    public BaseApiResponse updareBatchProcessTaskStatus (Integer  id, String status)throws  Exception{
        String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getUpdateTaskStatusUrl();
        return callGetApi(apiUrl, BaseApiResponse.class,id,status).getBody();
    }

    public BaseApiResponse unLocktask (Integer  id)throws  Exception{
        String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getUnlocktaskUrl();
        return callGetApi(apiUrl, BaseApiResponse.class,id).getBody();
    }
    
    public List<BatchParamValue> getBatchTaskParamValue(Integer id)throws  Exception {
        String apiUrl = apiEndPoints.getApiUrl() + apiEndPoints.getBatchTaskParamValue();
        return  Arrays.asList(callGetApi(apiUrl, BatchParamValue[].class,id).getBody());

       }
}



