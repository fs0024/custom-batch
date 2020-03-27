/**
 * 
 */
package com.trinet.benefits.oe.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trinet.benefits.oe.dao.TaskStepDao;

/**
 * @author imistry1
 *
 * 
 */
@Repository
@SuppressWarnings("unchecked")
public class TaskStepDaoImpl implements TaskStepDao {

	@PersistenceContext(unitName = "batchJobPersistenceUnit")
	@Autowired
	private EntityManager em;

	@Transactional
	@Override
	public void createTaskStep(Integer batchProcessStepId, Integer batchProcessTaskId, String status) {
		String sqlQuery = "INSERT INTO TASK_STEPS (BATCH_PROCESS_STEP_ID,BATCH_PROCESS_TASK_ID,STATUS) "
				+ "values (:batchProcessStepId,:batchProcessTaskId,:status)";

		em.createNativeQuery(sqlQuery).setParameter("batchProcessStepId", batchProcessStepId)
				.setParameter("batchProcessTaskId", batchProcessTaskId).setParameter("status", status).executeUpdate();

	}

	@Modifying
	@Transactional
	@Override
	public void updateTaskStep(Integer batchProcessStepId, Integer batchProcessTaskId, String status) {
		String sqlQuery = "Update TASK_STEPS SET status = :status "
				+ "Where  batch_process_step_id = :batchProcessStepId AND batch_process_task_id =:batchProcessTaskId";

		em.createNativeQuery(sqlQuery).setParameter("batchProcessStepId", batchProcessStepId)
				.setParameter("batchProcessTaskId", batchProcessTaskId).setParameter("status", status).executeUpdate();

	}

	@Override
	public Integer getTaskStepIdByAppIdAndStep(String stepIdentifier, int taskId) {
		String sqlQuery = "select distinct ts.id from \n"
				+ "              batch_process bp join batch_process_steps bps on bp.id = bps.batch_process_id \n"
				+ "                               join batch_process_task bpt on bpt.batch_process_id = bp.id\n"
				+ "                               join task_steps ts on ts.batch_process_step_id = bps.id and ts.batch_process_task_id = bpt.id\n"
				+ "                               where bps.batch_identifier= :stepIdentifier and  bpt.id= :taskId";

		return ((BigDecimal) em.createNativeQuery(sqlQuery).setParameter("taskId", taskId)
				.setParameter("stepIdentifier", stepIdentifier).getSingleResult()).intValue();
	}

	@Modifying
	@Transactional
	@Override
	public Integer updateTaskStepPrepareFlag(int taskStepId) {
		String sqlQuery = "Update TASK_STEPS SET is_prepared = 'Y' Where id = :taskStepId ";

		return em.createNativeQuery(sqlQuery).setParameter("taskStepId", taskStepId).executeUpdate();

	}

	@Override
	public Integer countRecsForStatus(Integer bpctId, String status) {
		String sql = "Select count(*) from TASK_STEPS where batch_process_task_id = :bpctId and status = :status";
		return ((BigDecimal) em.createNativeQuery(sql).setParameter("bpctId", bpctId).setParameter("status",status)
				.getSingleResult()).intValue();
	}


	@Modifying
	@Transactional
	@Override
	public void updateTaskStepByTaskId(Integer batchProcessTaskId, String status) {
		String sqlQuery = "Update TASK_STEPS SET status = :status "
				+ "Where batch_process_task_id =:batchProcessTaskId";
		
		em.createNativeQuery(sqlQuery)
			.setParameter("batchProcessTaskId", batchProcessTaskId)
			.setParameter("status", status)
			.executeUpdate();
	

	}
	

	// Method to fetch TaskStep Id by batchProcessStepID and batchProcessTaskId
	@Override
	public Integer getTaskStepId(Integer batchProcessStepId, Integer batchProcessTaskId) {
		String sqlQuery = "Select ts.ID from Task_Steps ts where ts.BATCH_PROCESS_STEP_ID = :batchProcessStepId AND ts.BATCH_PROCESS_TASK_ID = :batchProcessTaskId";
		
	return ((BigDecimal)em.createNativeQuery(sqlQuery)
				.setParameter("batchProcessStepId", batchProcessStepId)
				.setParameter("batchProcessTaskId", batchProcessTaskId)
				.getSingleResult()).intValue();
		
		
		
		
	}
	
	
	public Map<Integer,String> getAllTaskStepIdStatusByProcessTaskId(Integer batchProcessTaskId) {
		Map<Integer,String> resultMap = new HashMap<>();
		
		String sqlQuery = "Select ts.ID,ts.STATUS from Task_Steps ts where ts.BATCH_PROCESS_TASK_ID = :batchProcessTaskId";
		
		List<Object[]> result = em.createNativeQuery(sqlQuery)
								.setParameter("batchProcessTaskId", batchProcessTaskId)
								.getResultList();
		
		for(Object[] row:result) {
			resultMap.put(((BigDecimal)row[0]).intValue(), (String)row[1]);
		}
		
		return resultMap;
	}

}
