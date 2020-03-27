/**
 * 
 */
package com.trinet.benefits.oe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author imistry1
 *
 * 
 */
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BatchParamValueModel {
	
	 	@Id
	    @JsonProperty("id")
	    @Column(name="id")
	    private Integer id;
	 
	 	
	 	@Column(name="batchParamValue")
	 	private String batchParamValue;
	 	
	 	@Column(name="batchProcessTaskId")
	 	private Integer batchProcessTaskId;
	 	
	 	
	 	@Column(name="batchParamId")
	 	private Integer batchParamId;
	 	
	 	@Column(name="batchParamName")
	 	private String batchParamName;
	 	
	 	@Column(name="batchProcessStepId")
	 	private Integer batchProcessStepId;

}
