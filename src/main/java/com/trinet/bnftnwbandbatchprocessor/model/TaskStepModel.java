/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.model;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskStepModel {

	@JsonProperty("stepName")
	private String stepName;

	@JsonProperty("stepDescription")
	private String stepDescription;

	@JsonProperty("isStepPrepared")
	private Character isStepPrepared;

	@JsonProperty("stepStatus")
	private String stepStatus;

	@JsonProperty("batchProcessStepId")
	private Integer batchProcessStepId;

}
