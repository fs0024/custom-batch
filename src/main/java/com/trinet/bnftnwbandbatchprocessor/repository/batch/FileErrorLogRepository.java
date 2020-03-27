/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.repository.batch;


import com.trinet.bnftnwbandbatchprocessor.entity.batch.FileErrorLog;
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
	
}
