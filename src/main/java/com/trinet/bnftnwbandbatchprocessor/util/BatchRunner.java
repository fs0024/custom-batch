package com.trinet.bnftnwbandbatchprocessor.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import java.util.Date;

public class BatchRunner implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(BatchRunner.class);

    private String message;

    private JobLauncher jobLauncher ;

    private JobLauncher asyncJobLauncher ;

    private Integer workercount=5;

    private Job rateBandJobInit;


    private Job ratebandJob;

    public BatchRunner(String message , JobLauncher jobLauncher, JobLauncher asynJoblauncer, Job ratebandJobInit, Job ratebandJob, Integer workercount){
        this.message = message;
        this.jobLauncher=jobLauncher;
        this.asyncJobLauncher=asynJoblauncer ;
        this.ratebandJob=ratebandJob;
        this.rateBandJobInit =ratebandJobInit;
        this.workercount=workercount ;
    }

    public BatchRunner(String message , JobLauncher jobLauncher, JobLauncher asynJoblauncer, Job ratebandJobInit, Job ratebandJob){
        this.message = message;
        this.jobLauncher=jobLauncher;
        this.asyncJobLauncher=asynJoblauncer ;
        this.ratebandJob=ratebandJob;
        this.rateBandJobInit =ratebandJobInit;

    }

    @Override
    public void run() {

        LOGGER.debug(new Date()+" Runnable Task rateband "+
                " on thread "+Thread.currentThread().getName());


        JobParameters params = new JobParametersBuilder().addString("partitioner ", "clonePartitioner").addString("message" ,message).addDate("jobDate", new Date())
                .addString("timeout","true").addString("Worker", "master").toJobParameters();

        try {
            LOGGER.debug("Rate band process partitioning initialized "+new Date());
            JobExecution execution =  jobLauncher.run(rateBandJobInit, params);
            LOGGER.info("Rate band process partitioning finished with instance id "+execution.getJobInstance().getInstanceId()+" on "+new Date());
        } catch (Exception e){
            LOGGER.error("Rate band process partitioning error out  : " + e.getMessage());
        }

             Date launchdate = new Date();
             LOGGER.debug("Rate band process process started"+new Date());
            for(int i = 1 ; i<=workercount ;i++){
                JobParameters params2 = new JobParametersBuilder().addString("Worker", "Worker-"+i).addDate("jobDate", new Date()).addString("message" ,message)
                        .addString("timeout","true").addDate("launchDate", launchdate).toJobParameters();

                try {
                 JobExecution execution =  asyncJobLauncher.run(ratebandJob, params2);
                 execution.getExitStatus();

                } catch (Exception e){

                    LOGGER.error("Rate band process process error out"+e.getMessage());
                }

            }
           LOGGER.info("Launched rate rate band job for  "+new Date());

    }



}
