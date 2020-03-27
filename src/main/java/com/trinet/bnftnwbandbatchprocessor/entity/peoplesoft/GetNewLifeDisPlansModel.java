package com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dshah
 *
 * 
 */
@SqlResultSetMapping(name = "getNewLifeDisPlansModel", entities = @EntityResult(entityClass = GetNewLifeDisPlansModel.class, fields = {
		@FieldResult(name = "banefitPlan", column = "PLAN_TYPE"),
		@FieldResult(name = "planType", column = "BENEFIT_PLAN"),
		@FieldResult(name = "bandCode", column = "BAND_CODE")
}
))
@Entity
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GetNewLifeDisPlansModel {
	
	@Id
	@Column(name = "BENEFIT_PLAN")
	private String banefitPlan;
	
	@Column(name = "PLAN_TYPE")
	private String planType;
	
	@Column(name = "BAND_CODE")
	private String bandCode;

}
