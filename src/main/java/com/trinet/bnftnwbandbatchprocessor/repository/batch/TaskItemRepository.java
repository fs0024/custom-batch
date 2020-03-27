package com.trinet.bnftnwbandbatchprocessor.repository.batch;


import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskItemRepository extends JpaRepository<TaskItem, Integer>{
	public List<TaskItem> findTaskItemByWorkerIdAndTaskStep(String workerId, TaskStep taskStep);

	public List<TaskItem> findTaskItemByTaskStep(TaskStep taskStep);


}
