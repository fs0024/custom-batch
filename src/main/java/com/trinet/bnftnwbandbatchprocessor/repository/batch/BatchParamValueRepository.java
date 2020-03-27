/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.repository.batch;


import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author imistry1
 *
 * 
 */
public interface BatchParamValueRepository extends JpaRepository<BatchParamValue, Integer>{
	
	@Query(value = "Select p.id from Batch_Param_value p where batch_param_id = :batchParamId",nativeQuery = true)
	Integer findIdByBatchParamId(@Param("batchParamId") Integer batchParamId);
	
	@Query(value = "Select bpv.id from Batch_Param_value bpv  \n" + 
			       "WHERE bpv.batch_param_id = :batchParamId and bpv.batch_process_task_id = :batchProcessTaskId", nativeQuery = true)
	Integer findIdByParamTypeAndProcessId(@Param("batchParamId") Integer batchParamId, @Param("batchProcessTaskId") Integer batchProcessTaskId);
	
	
	
	@Modifying
	@Transactional
	@Query(value="Update Batch_param_value set validation_flag = :validationFlag Where id = :batchParamValueId",nativeQuery=true)
	void updateBatchParamValueStatus(@Param("batchParamValueId") Integer batchParamValueId, @Param("validationFlag") Character validationFlag);
	
	

}
