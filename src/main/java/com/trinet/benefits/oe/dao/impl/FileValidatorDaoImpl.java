/**
 * 
 */
package com.trinet.benefits.oe.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trinet.benefits.oe.dao.FileValidatorDao;
import com.trinet.benefits.oe.entity.ValidatorDef;

/**
 * @author imistry1
 *
 * 
 */
@Repository
public class FileValidatorDaoImpl implements FileValidatorDao{
	
	@PersistenceContext(unitName="batchJobPersistenceUnit")
	@Autowired
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ValidatorDef> getFileValidorDefByFileValidatorId(Integer fileValidatorId) {
		String sqlString = "SELECT  \n" + 
				"  vdf.id as id,\n" + 
				"  vdf.sequence as sequence,\n" + 
				"  vdf.FILE_VALIDATOR_ID as fileValidatorId,\n" + 
				"  vdf.DEF as def,\n" + 
				"  vdf.VALUE as value\n" + 
				"FROM Validator_def vdf \n" + 
				"WHERE \n" + 
				"vdf.FILE_VALIDATOR_ID  = :fileValidatorId\n" + 
				"ORDER BY vdf.sequence";
		
		return em.createNativeQuery(sqlString,"validatorDefMap")
				.setParameter("fileValidatorId",fileValidatorId)
				.getResultList();
		
	}


	
	
	
	


}
