package com.trinet.bnftnwbandbatchprocessor;

import com.trinet.bnftnwbandbatchprocessor.util.BatchManager;
import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ContextClosedHandler implements ApplicationListener<ContextClosedEvent> {

	@Value("${spring.application.name}")
	private String processorName;

	@Autowired
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	ThreadPoolTaskScheduler threadPoolTaskScheduler;

	@Autowired
	BatchManager batchManager;

	@Autowired
	BatchApiService batchApiService;



	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		log.info(processorName + ": Stopping all jobs");
		batchManager.stopJobs();
		log.info(processorName + ": Shutting down pools");
		threadPoolTaskExecutor.shutdown();
		threadPoolTaskScheduler.shutdown();


	}
}