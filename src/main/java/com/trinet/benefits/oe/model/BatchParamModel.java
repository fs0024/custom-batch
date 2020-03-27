package com.trinet.benefits.oe.model;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;





@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class BatchParamModel {
	
	@Id
	@Column(name="batchParamId")
	private Integer id;
	
	@Column(name="batchParamName")
	private String name;
	
	@Column(name="batchParamType")
	private String type;
	
	@JsonIgnore
	@Column(name="batchParamValidation")
	private String validation;
	
	@Column(name="batchParamDescription")
	private String description;
	
	
	@Column(name="batchProcessId")
	private Integer batchProcessId;
	
	
	@Column(name="batchProcessStepId")
	private Integer batchProcessStepId;
	
	
	@Column(name="batchParamValue")
 	private String batchParamValue;
 	
 	@Column(name="batchProcessTaskId")
 	private Integer batchProcessTaskId;
 	
 	@Column(name="validationFlag")
 	private Character validationFlag;
 	
 	
 	
 	
 	
}
