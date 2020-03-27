package com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author dshah
 *
 * 
 */
@SqlResultSetMapping(name = "getNewPlansModelMap", entities = @EntityResult(entityClass = GetNewPlansModel.class, fields = {
		@FieldResult(name = "id", column = "id"),
		@FieldResult(name = "planType", column = "PLAN_TYPE"),
		@FieldResult(name = "benefitPlan", column = "BENEFIT_PLAN"),
		@FieldResult(name = "planTypeLoc", column = "T2_PLAN_TYPE_LOC"),
		@FieldResult(name = "healthPlanType", column = "T2_HEALTH_PL_TYPE")
}
))
@Entity
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GetNewPlansModel {
	
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="PLAN_TYPE")
	private String planType;
	
	@Column(name="BENEFIT_PLAN")
	private String benefitPlan;

	@Column(name="T2_PLAN_TYPE_LOC")
	private String planTypeLoc;

	@Column(name="T2_HEALTH_PL_TYPE")
	private String healthPlanType;

}
