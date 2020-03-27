/**
 * 
 */
package com.trinet.benefits.oe.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.trinet.benefits.oe.entity.BatchProcessAndTaskModel;

/**
 * @author imistry1
 *
 * 
 */
@Repository
public interface BatchDetailsDao {
	
	
	public List<BatchProcessAndTaskModel> getBatchAndTaskDetails(Integer batchProcessId, String trackingNumber);
	
	
}
