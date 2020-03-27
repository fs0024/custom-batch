package com.trinet.bnftnwbandbatchprocessor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.trinet.bnftnwbandbatchprocessor.util.BatchManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {


    @Value( "${spring.application.name}" )
    private String processorName;

    @Value( "${batch.schedule}" )
    private String schedule;

    @Value( "${batch.autoschedule}" )
    private Boolean autoSchedule;



    @Autowired
    private BatchManager runner;
 
    @Override public void onApplicationEvent(ContextRefreshedEvent event) {


       if(autoSchedule){
           runner.scheduleRun("ratebandJob");
           log.info(processorName+": Scheduler scheduled runner on server start successfully for");
       }else {
           log.info(processorName+": Scheduler is not configured to schedule runner. Use /startProcess RestAPI as needed.");

       }



    }
}