/**
 * 
 */
package com.trinet.benefits.oe.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.trinet.benefits.oe.entity.ValidatorDef;

/**
 * @author imistry1
 *
 * 
 */
@Repository
public interface FileValidatorDao {
	public List<ValidatorDef> getFileValidorDefByFileValidatorId(Integer i);
	
	
	

}
