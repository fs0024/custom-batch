package com.trinet.bnftnwbandbatchprocessor.repository.batch;


import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParam;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BatchParamRepository extends JpaRepository<BatchParam, Integer>{
	
	 List<BatchParam> findBatchParamByBatchProcess(BatchProcess batchProcess);
	 
	 @Modifying
	 @Transactional
	 @Query(value="update BATCH_PARAM set BATCH_PROCESS_STEP_ID = :batchProcessStepId where id in (:batchParamIds)", nativeQuery = true)
	 void updateBatchParams(@Param("batchProcessStepId") int batchProcessStepId, @Param("batchParamIds") List<Integer> batchParamIds) ;

	
	


	@Query(value = "Select p.id from Batch_Param p where type = :parameterType and batch_process_id = :batch_process_id",nativeQuery = true)
	Integer findIdByBatchProcessIdAndType(@Param("parameterType") String paramterType,
                                          @Param("batch_process_id") Integer batchProcessId);

	@Query(value = "Select p.BATCH_PROCESS_STEP_ID from Batch_Param p where type = :parameterType and batch_process_id = :batch_process_id",nativeQuery = true)
	Integer findProcStepIdByProcessIdParamType(@Param("batch_process_id") Integer batchProcessId, @Param("parameterType") String paramterType);

}
