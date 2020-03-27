/**
 * 
 */
package com.trinet.benefits.oe.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trinet.benefits.oe.dao.BatchDetailsDao;
import com.trinet.benefits.oe.entity.BatchProcessAndTaskModel;

/**
 * @author imistry1
 *
 * 
 */
@Repository
@SuppressWarnings("unchecked")
public class BatchDetailsDaoImpl implements BatchDetailsDao {
	
	@PersistenceContext(unitName="batchJobPersistenceUnit")
	@Autowired
	private EntityManager em;

	
	@Override
	public List<BatchProcessAndTaskModel> getBatchAndTaskDetails(Integer batchProcessId,String trackingNumber) {
		
		String sqlQuery = " Select   \n" + 
				"				DBMS_RANDOM.random as id,  \n" + 
				"				bp.id as processId,   \n" + 
				"				bp.name as name,  \n" + 
				"				bp.APPID as appId,  \n" + 
				"				bpt.ID as batchProcessTaskId  \n" + 
				"				from Batch_Process bp   \n" + 
				"				INNER JOIN BATCH_PROCESS_TASK bpt ON bpt.BATCH_PROCESS_ID = bp.id  \n" + 
				"				Where bp.id = :batchProcessId and bpt.TRACKING_NUMBER = :trackingNumber";
		
		return em.createNativeQuery(sqlQuery,"batchProcessAndTaskMap")
		  .setParameter("batchProcessId", batchProcessId)
		  .setParameter("trackingNumber", trackingNumber)
		  .getResultList();
	}

	
	
//	@Override
//    public List<BatchProcessTaskStepDetails> getBatchProcessTaskStepDetailsByProcessId(Integer batchProcessId) {
//        String sqlQuery = "select DBMS_RANDOM.random as id,bpt.id as batchProcessTaskId , bp.id as batchProcessId, bpt.final_status as finalStatus, bpt.start_date startDate, \n" + 
//                "bpt.tracking_number as trackingNumber, tp.batch_process_step_id as batchProcessStepId, bps.name as stepName, bps.description as stepDescription,\n" + 
//                "tp.status as taskStepStatus, tp.is_Prepared as StepPrepared\n" + 
//                "from batch_process bp join batch_process_task bpt\n" + 
//                "on bp.id = bpt.batch_process_id join task_steps tp \n" + 
//                "on tp.batch_process_task_id = bpt.id join batch_process_steps bps on \n" + 
//                "bps.id = tp.batch_process_step_id\n" + 
//                "Where bp.id = :batchProcessId";
//        
//        return em.createNativeQuery(sqlQuery,"batchProcessTaskStepMap")
//                .setParameter("batchProcessId", batchProcessId)
//                .getResultList();
//    }



	


}
