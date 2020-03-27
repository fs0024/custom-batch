package com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author dshah
 *
 * 
 */
@SqlResultSetMapping(name = "insertNewOptnsMap", entities = @EntityResult(entityClass = InsertNewOptnModel.class, fields = {
		@FieldResult(name = "pfClient", column = "pf_client"),
		@FieldResult(name = "company", column = "company"),
		@FieldResult(name = "planYrStartDate", column = "T2_plan_dt_start"),
		@FieldResult(name = "quarter", column = "T2_OE_QUARTER") }))
@Entity
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InsertNewOptnModel {

	@Id
	@Column(name="pf_client")
	private String pfClient;

	@Column(name="company")
	private String company;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	@Column(name="T2_plan_dt_start")
	private String planYrStartDate;


	@Column(name="T2_OE_QUARTER")
	private String quarter;

}
