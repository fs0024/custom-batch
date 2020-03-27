package com.trinet.bnftnwbandbatchprocessor.conf;


import com.trinet.bnftnwbandbatchprocessor.util.BatchJobCompleteListener;
import com.trinet.bnftnwbandbatchprocessor.tasks.WaitingArea;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.trinet.bnftnwbandbatchprocessor.tasks.PartitionTask;
import com.trinet.bnftnwbandbatchprocessor.tasks.RateBandCloneTask;
import com.trinet.bnftnwbandbatchprocessor.tasks.UpdateRateBandsTask;

@Configuration
@EnableBatchProcessing
public class BatchJobDefinition {



    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job rateBandJob(){


        return jobBuilderFactory
                .get("rateBandJob")
                .start(cloneForewardStep())
                .next(waitingArea())
                .next(updateRateBandsStep())
                .build();
    }



    @Bean
    public Job rateBandJobInit(){


        return jobBuilderFactory
                .get("rateBandJobInit")
                .start(prepareCloneStep())
                .build();
    }


    @Bean
    @Scope(value="job", proxyMode = ScopedProxyMode.INTERFACES)
    public JobExecutionListener batchJobCompletionListener() {

        return new BatchJobCompleteListener();
    }

    @Bean
    public Step prepareCloneStep() {
        return stepBuilderFactory.get("InitCloneForward")
                . tasklet(prepareJobTasklet())
                .build();
    }

    @Bean
    public Step cloneForewardStep() {
        return stepBuilderFactory.get("cloneForward")
                . tasklet(cloneForewardTasklet())
                .build();
    }

    @Bean
    public Step waitingArea() {
        return stepBuilderFactory.get("waitingArea")
                . tasklet(waitingTasklet())
                .build();
    }


    @Bean
    public Step updateRateBandsStep() {
        return stepBuilderFactory.get("updateRateBands")
                . tasklet(updateRateBandsTasklet())
                .build();
    }

    @Bean
    public Tasklet prepareJobTasklet(){

        return new PartitionTask();
    }


    @Bean
    public Tasklet cloneForewardTasklet(){

        return new RateBandCloneTask();
    }

    @Bean
    public Tasklet updateRateBandsTasklet(){

        return new UpdateRateBandsTask();
    }

    @Bean
    public Tasklet waitingTasklet(){

        return new WaitingArea();
    }
}
