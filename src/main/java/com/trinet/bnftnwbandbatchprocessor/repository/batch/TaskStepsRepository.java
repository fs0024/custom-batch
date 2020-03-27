/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.repository.batch;


import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author imistry1
 *
 * 
 */

@Repository
public interface TaskStepsRepository extends JpaRepository<TaskItem, Integer> {
	
	
	@Transactional
	@Query(value = "INSERT INTO TASK_STEPS (BATCH_PROCESS_STEP_ID,BATCH_PROCESS_TASK_ID,STATUS) "
			+ "values (:batchProcessStepId,:batchProcessTaskId,:status)", nativeQuery = true)
	 void createTaskStep(@Param("batchProcessStepId") Integer batchProcessStepId,
                         @Param("batchProcessTaskId") Integer batchProcessTaskId, @Param("status") String status);
		
	
	
		
	
	

}
