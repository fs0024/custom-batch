/**
 * 
 */
package com.trinet.benefits.oe.repository;

import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.FileErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Repository
public interface FileErrorLogRepository extends JpaRepository<FileErrorLog,Integer> {
	
	@Transactional
	public default void saveFailedRecords(List<FileErrorLog> errorList) {
		this.saveAll(errorList);
	}
	
	
	public List<FileErrorLog> findByBatchParamValue(BatchParamValue batchParamValue);
	
	void deleteByBatchParamValue(BatchParamValue batchParamValue);

	
	

}
