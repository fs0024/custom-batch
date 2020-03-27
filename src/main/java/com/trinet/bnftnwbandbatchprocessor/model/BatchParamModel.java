package com.trinet.bnftnwbandbatchprocessor.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class BatchParamModel {
	
	private int id;
	private String name;
	private String type;
	private String validation;
	private String description;
	private int batchProcessId;
	private int batchProcessStepId;
	
}
