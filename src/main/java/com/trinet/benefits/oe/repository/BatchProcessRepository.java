package com.trinet.benefits.oe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinet.benefits.oe.entity.BatchProcess;

@Repository
public interface BatchProcessRepository extends JpaRepository<BatchProcess,Integer>{
	
	public List<BatchProcess> findByAppId(String appId);



	

}
