package com.trinet.bnftnwbandbatchprocessor.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BatchJobCompleteListener implements JobExecutionListener{

    private static final Logger LOGGER = LogManager.getLogger(BatchJobCompleteListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {



    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if( jobExecution.getStatus() == BatchStatus.COMPLETED ){
            //job success
            LOGGER.info("Batch job finised success");
        }
        else if(jobExecution.getStatus() == BatchStatus.FAILED){
            //job failure
            LOGGER.error("Batch job  Failed");
        }
    }
}
