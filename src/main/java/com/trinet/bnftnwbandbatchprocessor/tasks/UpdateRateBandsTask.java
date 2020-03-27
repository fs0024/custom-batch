package com.trinet.bnftnwbandbatchprocessor.tasks;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcessTask;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;
import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import com.trinet.bnftnwbandbatchprocessor.services.UpdateRateBandProcess;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class UpdateRateBandsTask implements Tasklet, StepExecutionListener {



	@Autowired
	private JobExplorer jobExplorer ;

	@Autowired
	private BatchApiService batchApiService;

	@Value("${spring.application.name}")
	private String processorName;

	@Value("${batch.task.timeout}")
	private Long timeout;

	public static final String STEP_NAME = "updateRateBands";

	@Autowired
	private UpdateRateBandProcess updateRateBandProcess;

	private static Map<String,Integer> cache = new ConcurrentHashMap<>();

	@Override
	public void beforeStep(StepExecution stepExecution) {
		String worker = stepExecution.getJobParameters().getString("Worker");
		cache.put(worker,1);
		log.debug("Initialized updateRateBands Step");
	}


	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		String worker = stepExecution.getJobParameters().getString("Worker");
		cache.put(worker,0);
		if(!cache.containsValue(1)){
			// Unlock task
			Date jobdate = stepExecution.getJobParameters()
					.getDate("launchDate");
			BatchProcessTask task = null;
			try {
				 task = batchApiService.getPrepTask(STEP_NAME ,jobdate);
					if(task!= null ) {
					   batchApiService.unLocktask(task.getId());
					   String key = UpdateRateBandsTask.STEP_NAME+jobdate;
					   BatchApiService.getApiCache().remove(key);
					}
					cache.clear();
				log.info(" Unlocking task for updateRateBands ");

			} catch (Exception e) {

				log.error("Error Unlocking task for update rate band process");
			}

		}
		log.debug("Existing  updateRateBands Step");
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

		Boolean endOfProcess = false;
		BatchProcessTask task = null ;
		try{
			task = batchApiService.getPrepTask(UpdateRateBandsTask.STEP_NAME,jobdate);

		}catch (Exception e){


		}

		if (task != null) {
			TaskStep step = null;
			BatchParamValue quarter = null;
			BatchParamValue updateComp = null;
			BatchParamValue effectivedate = null;

			for (TaskStep taskStep : task.getTaskSteps()) {
				if (taskStep.getBatchProcessStep().getBatchIdentifire().equalsIgnoreCase(STEP_NAME)) {

					step = taskStep;
					break;
				}

			}

			List<BatchParamValue> taskParamValueList = batchApiService.getBatchTaskParamValue(task.getId());

			for (BatchParamValue param : taskParamValueList) {
				if (param.getBatchParam().getName().equalsIgnoreCase("quarter")) {
					quarter = param;

				}

				if (param.getBatchParam().getName().equalsIgnoreCase("company id")) {
					updateComp = param;

				}
				if (param.getBatchParam().getName().equalsIgnoreCase("effective date")) {
					effectivedate = param;

				}

			}

			log.info("processing clone with parameters : task Id :" + task.getId() + " quarter :" + quarter.getValue()
					+ " efectiveDate :" + effectivedate.getValue() + " cloneComp :" + updateComp.getValue() + "Worker :"
					+ worker);

			if (jobTimeOutSetting.equalsIgnoreCase("true")) {
				log.info("Processing ratebandupdate task in defined window");
				while (Timer.timeOutprocess(startTime, timeout) && !endOfProcess
				&& !jobExplorer.getJobExecution(chunkContext.getStepContext().getStepExecution().getJobExecution().getId()).isStopping()) {

					endOfProcess = updateRateBandProcess.processtask(step, effectivedate, updateComp, worker);

				}
				log.info("Exiting Processing ratebandupdate task in defined window");
			} else {
				log.info("Processing ratebandupdate task no time window");
				while (!endOfProcess && !jobExplorer.getJobExecution(chunkContext.getStepContext().getStepExecution().getJobExecution().getId()).isStopping()) {

					endOfProcess = updateRateBandProcess.processtask(step, effectivedate, updateComp, worker);

				}
				log.info("Exiting Processing ratebandupdate task no time window");

			}



		}

		log.debug("Existing Processing ratebandupdate Step");

		return RepeatStatus.FINISHED;
	}
}
