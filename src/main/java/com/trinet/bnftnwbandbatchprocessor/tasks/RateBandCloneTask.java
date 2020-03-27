package com.trinet.bnftnwbandbatchprocessor.tasks;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcessTask;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;
import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import com.trinet.bnftnwbandbatchprocessor.services.RateBandCloneProcess;
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
public class RateBandCloneTask implements Tasklet, StepExecutionListener {

	@Autowired
	private JobExplorer jobExplorer ;

	@Autowired
	private BatchApiService batchApiService;

	@Value("${spring.application.name}")
	private String processorName;

	@Value("${batch.task.timeout}")
	private Long timeout;

	public static final String STEP_NAME = "cloneForward";

	@Autowired
	private RateBandCloneProcess rateBandCloneProcess;


	@Override
	public void beforeStep(StepExecution stepExecution) {
		String worker = stepExecution.getJobParameters().getString("Worker");
		cache.put(worker,1);
		log.debug("Initialized cloneForward Step");
	}
	private static Map<String,Integer> cache = new ConcurrentHashMap<>();

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		String worker = stepExecution.getJobParameters().getString("Worker");
		cache.put(worker,0);
		if(!cache.containsValue(1)) {
			// Unlock task
			Date jobdate = stepExecution.getJobParameters()
					.getDate("launchDate");
			BatchProcessTask task = null;
			try {
				task = batchApiService.getPrepTask(RateBandCloneTask.STEP_NAME, jobdate);
				if (task != null) {
					batchApiService.unLocktask(task.getId());
					String key = RateBandCloneTask.STEP_NAME + jobdate;
					BatchApiService.getApiCache().remove(key);
				}
				cache.clear();
				log.info(" Unlocking task for clone process ");

			} catch (Exception e) {

				log.error("Error Unlocking task for clone process");
			} finally {
		
			}
		}
		log.debug("Existing  cloneForward Step");
		return ExitStatus.COMPLETED;
	}

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		// Start Time -2

		String worker = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("Worker");

		String jobTimeOutSetting = chunkContext.getStepContext().getStepExecution().getJobParameters()
				.getString("timeout");
		// Logic


		Date jobdate = chunkContext.getStepContext().getStepExecution().getJobParameters()
				.getDate("launchDate");

		Long startTime = System.currentTimeMillis();

		Boolean endOfProcess = false;
		BatchProcessTask task = null ;
		try{
			task = batchApiService.getPrepTask(RateBandCloneTask.STEP_NAME ,jobdate);
		}catch (Exception e){

		}

		if (task != null) {
			TaskStep step = null;
			BatchParamValue quarter = null;
			BatchParamValue cloneComp = null;
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
					cloneComp = param;

				}
				if (param.getBatchParam().getName().equalsIgnoreCase("effective date")) {
					effectivedate = param;

				}

			}
			log.info("processing clone with parameters : task Id :" + task.getId() + " quarter :"
					+ quarter.getValue() + " efectiveDate :" + effectivedate.getValue() + " cloneComp :"
					+ cloneComp.getValue() + "Worker :" + worker);
			if (jobTimeOutSetting.equalsIgnoreCase("true")) {
				log.info("Processing cloneForward task in defined window");
				while (Timer.timeOutprocess(startTime, timeout) && !endOfProcess
				&& !jobExplorer.getJobExecution(chunkContext.getStepContext().getStepExecution().getJobExecution().getId()).isStopping()) {
					;
					endOfProcess = rateBandCloneProcess.processtask(step, quarter, effectivedate, cloneComp, worker);

				}
				log.info("Exiting Processing cloneForward task in defined window");
			} else {
				log.info("Processing cloneForward task no time window");
				while (!endOfProcess
				&& !jobExplorer.getJobExecution(chunkContext.getStepContext().getStepExecution().getJobExecution().getId()).isStopping()) {

				  endOfProcess = rateBandCloneProcess.processtask(step, quarter, effectivedate, cloneComp, worker);

				}
				log.info("Exiting Processing cloneForward task no time window");

			}



		} else {
			log.info("Nothing to process for cloneforward ");
		}

		log.debug("Existing Processing cloneForward Step");

		return RepeatStatus.FINISHED;
	}
}
