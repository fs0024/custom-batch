package com.trinet.bnftnwbandbatchprocessor.util;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcess;
import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Component
public class BatchManager {

	private static final Logger LOGGER = LogManager.getLogger(BatchManager.class);
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	private final Map<String, ScheduledFuture<?>> scheduledTasks = new IdentityHashMap<>();

	@Autowired
	@Qualifier("asyncJobLauncher")
	private JobLauncher asyncJobLauncher;

	@Value("${spring.application.name}")
	private String processorName;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobExplorer jobExplorer ;

	@Autowired
	private Job rateBandJobInit;

	@Autowired
	private Job rateBandJob;

	@Autowired
	private BatchApiService batchApiService;

	@Value("${batch.worker.count}")
	private Integer workercount;

	@Value("${batch.task.timeout}")
	private Long timeout;

	@Value("${thread.wait.settings}")
	private Long waitTime;

	private String message = "ratebandJob";

	@Autowired
	private JobOperator jobOperator ;

	public String stopJobs(){
		Set<JobExecution> jobExecutions = new HashSet<>() ;
		 jobExecutions.addAll(jobExplorer.findRunningJobExecutions("rateBandJob"));
			if(jobExecutions != null && jobExecutions.size()>0) {
				jobExecutions.forEach(jobExecution -> {
					try {
						jobOperator.stop(jobExecution.getId());
					} catch (NoSuchJobExecutionException e) {
						LOGGER.error("Job job found for : ["+jobExecution.getJobInstance().getJobName()+"]"+e.getMessage());

					} catch (JobExecutionNotRunningException e) {
						LOGGER.error("Failed to Stop : ["+jobExecution.getJobInstance().getJobName()+"]"+e.getMessage());

					}
				});
				LOGGER.info("Successfully Stoppeds all jobs ");
				return "success" ;
			}else{
				LOGGER.info("No jobs currently running ");
				return "ERROR_NO_JOBS_RUNNING" ;
			}



	}

	public String updateSchedule(String jobName) throws Exception {
		try {

			ScheduledFuture<?> existingTask = scheduledTasks.get(jobName);

			if(existingTask != null ) {
				LOGGER.info("Cancelling existing task for Job :" + jobName);
				existingTask.cancel(false);
				if (existingTask.isCancelled()) {
					LOGGER.info("Successfully cancelled existing task for Job :" + jobName);
					scheduleRun(jobName);
				}
			}else{
				scheduleRun(jobName);
			}
		} catch (Exception e) {

			LOGGER.error("Error updating schedule for Job " + jobName + " :" + e.getMessage());
			return "ERROR_UPDATING_SCHEDULE" ;
		}
		return "success" ;
	}

	public String stopSchedule(String jobName) throws Exception {
		try {
			LOGGER.info("Stopping schedule for  job : " + jobName);
			ScheduledFuture<?> existingTask = scheduledTasks.get(jobName);

			existingTask.cancel(false);

			if (existingTask.isCancelled()) {
				LOGGER.info("Successfully Stopped schedule for job : " + jobName);

			}

		} catch (Exception e) {
			LOGGER.error("Error Stopping schedule for Job " + jobName + " :" + e.getMessage());
			return "ERROR_STOPPING_SCHEDULE" ;
		}
		return "success" ;
	}

	public String scheduleRun(String jobName) {
		try {
			Trigger cronTrigger;
			BatchProcess process = batchApiService.getBatchConfig();
			cronTrigger = new CronTrigger(process.getBatchSchedule().getValue());
			ScheduledFuture<?> task = taskScheduler.schedule(
					new BatchRunner(message, jobLauncher, asyncJobLauncher, rateBandJobInit, rateBandJob, workercount),
					cronTrigger);
			scheduledTasks.put(jobName, task);
			LOGGER.info("Successfully scheduled  job : " + jobName);
		} catch (Exception e) {
			LOGGER.error("Error scheduling job " + jobName + " :" + e.getMessage());
			return "ERROR_SCHEDULING" ;
		}
		return "success" ;
	}


	public String runNow() {

		LOGGER.debug(new Date() + " Runnable Task rateband " + " on thread " + Thread.currentThread().getName());

		JobParameters params = new JobParametersBuilder().addString("partitioner ", "clonePartitioner")
				.addString("message", message).addDate("jobDate", new Date()).addString("timeout", "false")
				.addString("Worker", "master").toJobParameters();

		try {
			LOGGER.debug("Rate band process partitioning initialized " + new Date());
			JobExecution execution = jobLauncher.run(rateBandJobInit, params);
			LOGGER.info("Rate band process partitioning finished with instance id "
					+ execution.getJobInstance().getInstanceId() + " on " + new Date());
		} catch (Exception e) {
			LOGGER.error("Rate band process partitioning error out  : " + e.getMessage());
			return "ERROR_LAUNCHING_PARTITION_JOBS" ;
		}

		LOGGER.debug("Rate band process process started" + new Date());
		Date launchdate = new Date();
		for (int i = 1; i <= workercount; i++) {
			JobParameters params2 = new JobParametersBuilder().addString("Worker", "Worker-" + i)
					.addDate("jobDate", new Date()).addString("message", message).addString("timeout", "false")
					.addDate("launchDate", launchdate).toJobParameters();

			try {
				asyncJobLauncher.run(rateBandJob, params2);

			} catch (Exception e) {

				LOGGER.error("Rate band process process error out" + e.getMessage());
				return "ERROR_LAUNCHING_JOBS" ;
			}

		}

		LOGGER.info("Launched rate rate band job for  " + new Date());
		return "success" ;
	}

}
