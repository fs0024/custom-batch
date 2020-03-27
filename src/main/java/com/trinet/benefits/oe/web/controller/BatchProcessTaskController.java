package com.trinet.benefits.oe.web.controller;

import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.model.BaseApiResponse;
import com.trinet.benefits.oe.service.BatchProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.trinet.benefits.oe.web.Mappings.CANCEL_TASK;
import static com.trinet.benefits.oe.web.Mappings.VERSION;

@RequestMapping(VERSION)
@RestController
public class BatchProcessTaskController {

    
    private BatchProcessTaskService batchProcessTaskService ;
    
    @Autowired
    public BatchProcessTaskController(BatchProcessTaskService batchProcessTaskService) {
		this.batchProcessTaskService = batchProcessTaskService;
	}

    @GetMapping(value={"/getUnPrepTask/{appId}/{stepIdentifier}","/getUnPrepTask/{appId}"})
    public ResponseEntity<BatchProcessTask> getUnPrepTask(@PathVariable("appId") String appId , @PathVariable("stepIdentifier") Optional<String> stepIdentifier ){
        BatchProcessTask response = batchProcessTaskService.getUnPrepTask(appId,stepIdentifier)  ;
         if(response!= null )
                   return new ResponseEntity<>(response , HttpStatus.OK);


        return new ResponseEntity<>(response ,HttpStatus.NOT_FOUND);
    }


    @GetMapping(value={"/getPreparedTask/{appId}/{stepIdentifier}","/getPreparedTask/{appId}"})
    public ResponseEntity<BatchProcessTask> getPreparedTask(@PathVariable("appId") String appId , @PathVariable("stepIdentifier") Optional<String> stepIdentifier ){
        BatchProcessTask response = batchProcessTaskService.getPrepedTask(appId,stepIdentifier)  ;
        if(response!= null )
            return new ResponseEntity<>(response , HttpStatus.OK);


        return new ResponseEntity<>(response ,HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/updateStatus/{id}/{status}")
    public ResponseEntity<BaseApiResponse> updateTaskStatus(@PathVariable("id") Integer id , @PathVariable("status") String status){
        BaseApiResponse response = new BaseApiResponse();
        if(batchProcessTaskService.updateTaskStatus(id,status)){
            response.setStatus("SUCCESS");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setStatus("FAILED");
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);

    }

    @GetMapping(value = "/unLockTask/{id}")
    public ResponseEntity<BaseApiResponse> updateTaskStatus(@PathVariable("id") Integer id ){
        BaseApiResponse response = new BaseApiResponse();
        if(batchProcessTaskService.unLockTask(id)){
            response.setStatus("SUCCESS");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setStatus("CONFLICT");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PutMapping(CANCEL_TASK)
    public ResponseEntity<String> cancelTask(@PathVariable("batchProcessTaskId") Integer batchProcessTaskId){

        return new ResponseEntity<>(batchProcessTaskService.cancelTask(batchProcessTaskId),HttpStatus.OK);
    }

}
