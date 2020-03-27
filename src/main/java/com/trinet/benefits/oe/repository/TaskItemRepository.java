package com.trinet.benefits.oe.repository;

import com.trinet.benefits.oe.dao.TaskItemDao;
import com.trinet.benefits.oe.entity.TaskItem;
import com.trinet.benefits.oe.entity.TaskStep;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskItemRepository
		extends JpaRepository<TaskItem, Integer>, PagingAndSortingRepository<TaskItem, Integer>, TaskItemDao {

	public List<TaskItem> findTaskItemByWorkerIdAndTaskStepAndStatus(String workerId, TaskStep taskStep, String status,
			Pageable pageable);

	@Transactional
	public default void saveAllTaskItems(List<TaskItem> validRecords) {
		this.saveAll(validRecords);

	}

	Integer countByStatusAndTaskStep(String status, TaskStep taskStep);

	Integer countByTaskStep(TaskStep taskStep);

	void deleteByTaskStep(TaskStep taskStep);
	
	public List<TaskItem> findByTaskStepAndStatusNot(TaskStep step,String status);


}
