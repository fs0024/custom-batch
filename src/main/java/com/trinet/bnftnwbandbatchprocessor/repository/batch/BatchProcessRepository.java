package com.trinet.bnftnwbandbatchprocessor.repository.batch;


import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchProcessRepository extends JpaRepository<BatchProcess,Integer>{
	
	public BatchProcess findByAppId(String appId);
	
	public Optional<BatchProcess> findById(Integer id);
	

}
