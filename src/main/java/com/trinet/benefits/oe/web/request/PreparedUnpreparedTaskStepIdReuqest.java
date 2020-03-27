package com.trinet.benefits.oe.web.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dshah
 *
 * 
 */
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreparedUnpreparedTaskStepIdReuqest {
	
	@JsonProperty("appId")
	private String appId;

	@JsonProperty("stepIdentifier")
	private String stepIdentifier;
	
	@JsonProperty("isPrepared")
	private String isPrepared;
	
	@JsonProperty("quarter")
	private String quarter;

}
