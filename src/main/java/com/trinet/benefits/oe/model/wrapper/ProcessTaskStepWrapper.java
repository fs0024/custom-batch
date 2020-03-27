/**
 * 
 */
package com.trinet.benefits.oe.model.wrapper;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trinet.benefits.oe.model.TaskStepModel;

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
public class ProcessTaskStepWrapper {
	
	
	@JsonProperty("batchProcessTaskId")
	private Integer batchProcessTaskId;
	
	@JsonProperty("batchProcessId")
	private Integer batchProcessId;
	
	@JsonProperty("finalStatus")
	private String finalStatus;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	@JsonProperty("startDate")
	private LocalDate startDate;
	
	@JsonProperty("trackingNumber")
	private String trackingNumber;
	
	@JsonProperty("taskStepsList")
	private List<TaskStepModel> taskStepsList;

}
