package com.trinet.bnftnwbandbatchprocessor.services;

import com.trinet.bnftnwbandbatchprocessor.entity.batch.FileItems;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;
import com.trinet.bnftnwbandbatchprocessor.repository.batch.FileItemRepository;
import com.trinet.bnftnwbandbatchprocessor.repository.batch.TaskItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class InsertItemService {

	@Autowired
	private TaskItemRepository taskItemRepository;
	@Autowired
	private FileItemRepository fileItemRepository;

	@Transactional(value = "batchTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public String insertItems(List<TaskItem> items) {
		this.taskItemRepository.saveAll(items);
		log.info("commiting  items : " + items.size());
       return "SUCCESS";
	}

	@Transactional(value = "batchTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public String removeOrphans(TaskStep step) {

		List<TaskItem> orphaned = taskItemRepository.findTaskItemByTaskStep(step);
		for (TaskItem item : orphaned) {
			FileItems dbitem = fileItemRepository.findByTaskItem(item);
			if (dbitem != null)
				fileItemRepository.delete(dbitem);
		}

		log.info("Removed orphaned  task  items : " + orphaned.size());
		taskItemRepository.deleteAll(orphaned);
		return "SUCCESS" ;

	}
}
