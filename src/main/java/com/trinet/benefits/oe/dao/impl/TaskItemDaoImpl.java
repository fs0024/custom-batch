package com.trinet.benefits.oe.dao.impl;

import com.trinet.benefits.oe.dao.TaskItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class TaskItemDaoImpl implements TaskItemDao {

	@PersistenceContext(unitName = "batchJobPersistenceUnit")
	@Autowired
	private EntityManager em;



	@Override
	public Integer countUnPartionedRecs(Integer taskStepId) {
		String sql = "Select count(*) from TASK_ITEMS where WORKER_ID is null and task_step_id = :taskStepId ";
		return ((BigDecimal) em.createNativeQuery(sql).setParameter("taskStepId", taskStepId)
				.getSingleResult()).intValue();
	}


	@Override
	public Integer countRecsForStatus(Integer taskStepId, String status) {
		String sql = "Select count(*) from TASK_ITEMS where task_step_id = :taskStepId and status = :status";
		return ((BigDecimal) em.createNativeQuery(sql).setParameter("taskStepId", taskStepId).setParameter("status",status)
				.getSingleResult()).intValue();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getItemsInRange(Integer taskStepId, int startIndex, int endIndex) {

		String query = "SELECT ID \n" + "FROM(SELECT ID, ROW_NUMBER() OVER (ORDER BY ID) R FROM\n"
				+ " TASK_ITEMS WHERE task_step_id = :taskStepId ) \n"
				+ "                where R>= :startIndex AND R <= :endIndex";

		return (List<Integer>)(em.createNativeQuery(query).setParameter("taskStepId", taskStepId).setParameter("startIndex", startIndex)
				.setParameter("endIndex", endIndex).getResultList());

	}

	@Transactional
	@Override
	public Integer updateTaskItemPartion(List<Integer> itemIds, String worker) {
		String sqlQuery = "Update TASK_ITEMS SET status = 'Ready',WORKER_ID = :workerName Where id in ( :ids )";
		return em.createNativeQuery(sqlQuery).setParameter("workerName", worker).setParameter("ids", itemIds)
				.executeUpdate();
	}

}
