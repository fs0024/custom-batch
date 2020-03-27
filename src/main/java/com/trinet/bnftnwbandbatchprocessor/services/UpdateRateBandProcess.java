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
public class UpdateRateBandProcess implements TaskletProcess {

	@Autowired
	private PeopleSoftServiceImpl peopleSoftService;

	@Value("${batch.worker.count}")
	private Integer workercount;

	@Value("${update.chunk.size}")
	private Integer chunkSize;

	@Autowired
	private BatchApiService batchApiService;

	@Override
	public boolean processtask(Object... args) {

		TaskStep task = (TaskStep) args[0];
		BatchParamValue efectiveDate = (BatchParamValue) args[1];
		BatchParamValue cloneComp = (BatchParamValue) args[2];
		String workenName = (String) args[3];

		List<TaskItem> listOfItem = null;
		try {
			listOfItem = batchApiService.getTaskItems(workenName, task.getId(), "Ready", chunkSize);
		} catch (Exception e) {
			log.error("Error retrieving list of items for update rate band");
			return false;
		}

		if (!listOfItem.isEmpty()) {
			log.info("Processing list of clients :" + listOfItem.size());
			try {
			List<List<TaskItem>>lists = peopleSoftService.updateHealthLoop(listOfItem,cloneComp.getValue(),efectiveDate.getValue());

			   if(lists.get(0).size()>0) {
					batchApiService.updateItemStatus(lists.get(0), "COMPLETED");
				}
				if(lists.get(1).size()>0) {
					batchApiService.updateItemStatus(lists.get(1), "FAILED");
				}

			} catch (Exception e) {
				log.error("fatal Errror processing update for Clients : ["+ listOfItem + "] Error message +"+
						e.getMessage());


			}

			log.info(workenName +":Finished rate band update  for : "+ listOfItem);
			return false;
		}

		log.info(workenName +":Finished rateband update for all clients ");
		return true;
	}
}
