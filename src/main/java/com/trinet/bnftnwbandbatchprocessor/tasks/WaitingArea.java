package com.trinet.bnftnwbandbatchprocessor.tasks;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcessTask;
import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import com.trinet.bnftnwbandbatchprocessor.util.Timer;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Log4j2
public class WaitingArea implements Tasklet, StepExecutionListener {


    @Autowired
    private JobExplorer jobExplorer ;

    @Autowired
    private BatchApiService batchApiService;

    @Value("${spring.application.name}")
    private String processorName;

    @Value("${batch.task.timeout}")
    private Long timeout;


    @Value("${waiting.sleep.time}")
    private Long sleepTime;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.debug("Initialized Waiting Step");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.debug("Existing  Waiting area");
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String worker = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("Worker");

        String jobTimeOutSetting = chunkContext.getStepContext().getStepExecution().getJobParameters()
                .getString("timeout");


        Date jobdate = chunkContext.getStepContext().getStepExecution().getJobParameters()
                .getDate("launchDate");
        Long startTime = System.currentTimeMillis();



        if (jobTimeOutSetting.equalsIgnoreCase("true")) {
            log.info("waiting for task to be unlocked for next step  in defined window");
           boolean exit = false ;
            while (Timer.timeOutprocess(startTime, timeout )&& !exit
            &&!jobExplorer.getJobExecution(chunkContext.getStepContext().getStepExecution().getJobExecution().getId()).isStopping()) {

                BatchProcessTask task = null;
                try {
                    task = batchApiService.getPrepTask(RateBandCloneTask.STEP_NAME ,jobdate);
                } catch (Exception e) {
                    exit = true ;
                }


                Thread.sleep(sleepTime);

            }
            log.info("Exiting waiting area  in defined window");
        } else {
            log.info("waiting for task to be unlocked no time window");
            boolean exit = false ;
            while (!exit && !jobExplorer.getJobExecution(chunkContext.getStepContext().getStepExecution().getJobExecution().getId()).isStopping()) {

                BatchProcessTask task = null;
                try {
                    task = batchApiService.getPrepTask(RateBandCloneTask.STEP_NAME ,jobdate);
                } catch (Exception e) {
                    exit = true ;
                }

                Thread.sleep(sleepTime);
            }
            log.info("Exiting waiting area task no time window");

        }

        return RepeatStatus.FINISHED;
    }
}
