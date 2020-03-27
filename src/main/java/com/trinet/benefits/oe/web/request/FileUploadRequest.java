/**
 * 
 */
package com.trinet.benefits.oe.web.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileUploadRequest {

	@JsonProperty("batchProcessTaskId")
	private Integer batchProcessTaskId;

	@JsonProperty("batchProcessId")
	private Integer batchProcessId;
	
	@JsonProperty("taskStepId")
	private Integer taskStepId;
	
	@JsonProperty("batchParamId")
	private Integer batchParamId;
	
	@JsonProperty("trackingNumber")
	private String trackingNumber;
	

}