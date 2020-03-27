/**
 * 
 */
package com.trinet.benefits.oe.repository;

import com.trinet.benefits.oe.dao.TaskStepDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinet.benefits.oe.entity.BatchProcessStep;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.entity.TaskStep;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author imistry1
 *
 * 
 */

@Repository
public interface TaskStepsRepository extends JpaRepository<TaskStep, Integer>, TaskStepDao {

	public List<TaskStep> findByBatchProcessStepAndBatchProcessTask(BatchProcessStep step, BatchProcessTask task);

	@Transactional
	@Query(value = "INSERT INTO TASK_STEPS (BATCH_PROCESS_STEP_ID,BATCH_PROCESS_TASK_ID,STATUS) "
			+ "values (:batchProcessStepId,:batchProcessTaskId,:status)", nativeQuery = true)
	void createTaskStep(@Param("batchProcessStepId") Integer batchProcessStepId,
			@Param("batchProcessTaskId") Integer batchProcessTaskId, @Param("status") String status);

}
