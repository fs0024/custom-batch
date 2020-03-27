package com.trinet.bnftnwbandbatchprocessor.services;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;
import com.trinet.bnftnwbandbatchprocessor.util.PeopleSoftServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RateBandCloneProcess implements TaskletProcess {

	@Autowired
	private PeopleSoftServiceImpl peopleSoftService;

	@Value("${batch.worker.count}")
	private Integer workercount;

	@Value("${clone.chunk.size}")
	private Integer chunkSize;

	@Autowired
	private BatchApiService batchApiService;

	@Override
	public boolean processtask(Object... args) {

		TaskStep task = (TaskStep) args[0];
		BatchParamValue efectiveDate = (BatchParamValue) args[2];
		BatchParamValue cloneComp = (BatchParamValue) args[3];
		String workerName = (String) args[4];

		List<TaskItem> list = null;
		try {
			list = batchApiService.getTaskItems(workerName, task.getId(), "Ready", chunkSize);
		} catch (Exception e) {
			log.error("Error retrieving list of items for Clone");
			return false;
		}

		if (!list.isEmpty()) {
			log.info("Processing list of clients using worker  :" +workerName +"<>"+ list.size());

			try {
				List<List<TaskItem>>lists = peopleSoftService.cloneForwardLoop(list, cloneComp.getValue(), efectiveDate.getValue());

				if(lists.get(0).size()>0) {
					batchApiService.updateItemStatus(lists.get(0), "COMPLETED");
				}
				if(lists.get(1).size()>0) {
					batchApiService.updateItemStatus(lists.get(1), "FAILED");
				}

			} catch (Exception e) {

				log.error("fatal Errror processing clone for Clients : ["+ list + "] Error message +"+
						e.getMessage());

			}

			return false;
		}

		log.info("RateBand clone task finished");
		return true;
	}
}
