package com.trinet.bnftnwbandbatchprocessor.tasks;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcessTask;
import com.trinet.bnftnwbandbatchprocessor.services.BatchApiService;
import com.trinet.bnftnwbandbatchprocessor.services.PartitionProcess;
import com.trinet.bnftnwbandbatchprocessor.util.Timer;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Log4j2
public class PartitionTask implements Tasklet, StepExecutionListener {

	

	@Autowired
	private BatchApiService batchApiService;

	@Value("${spring.application.name}")
	private String processorName;

	@Value("${batch.task.timeout}")
	private Long timeout;

	public static String stepName = "InitCloneForward";

	@Autowired
	private PartitionProcess initRateBandCloneProcess;

	@Override
	public void beforeStep(StepExecution stepExecution) {

		log.debug("Initialized InitCloneForward Step");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		log.debug("Existing  InitCloneForward Step");
		return ExitStatus.COMPLETED;
	}

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

		String worker = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("Worker");

		String jobTimeOutSetting = chunkContext.getStepContext().getStepExecution().getJobParameters()
				.getString("timeout");
		// Api Call to init prep {appID ,stepName,N } --> Id & Params

		// Psftt select need quarter
		BatchProcessTask task = null;
		try{
			task = batchApiService.getUnPrepTask();
		}catch (Exception e){
			e.printStackTrace();

			log.error("No task found for partioning ");
		}

		if (task != null) {

			BatchParamValue quaterParam = null;
			
			List<BatchParamValue> taskParamValueList = batchApiService.getBatchTaskParamValue(task.getId());

			for (BatchParamValue paramValue : taskParamValueList) {

				if (paramValue.getBatchParam().getName().equalsIgnoreCase("Quarter")) {
					quaterParam = paramValue;
					break;
				}
			}
			Long startTime = System.currentTimeMillis();

			Boolean endOfProcess = false;

			if (jobTimeOutSetting.equalsIgnoreCase("true")) {
				log.info("Processing InitCloneForward task in defined window");

                 while (Timer.timeOutprocess(startTime, timeout) && !endOfProcess) {

					endOfProcess = initRateBandCloneProcess.processtask(task, quaterParam);

				}

				log.info("Exiting InitCloneForward task in defined window");
			} else {
				log.info("Processing InitCloneForward task no time window");
				while (!endOfProcess) {

					endOfProcess = initRateBandCloneProcess.processtask(task, quaterParam);

				}

				log.info("Exiting InitCloneForward task no time window");

			}

			// Unlock task
			try {
				batchApiService.unLocktask(task.getId());

			} catch (Exception e) {
				log.error("Error Unlocking task for rateband partioning process");
			}

		} else {
			log.info("Nothing to process for partition ");
		}

		log.debug("Finished Processing InitCloneForward Step");

		return RepeatStatus.FINISHED;
	}
}