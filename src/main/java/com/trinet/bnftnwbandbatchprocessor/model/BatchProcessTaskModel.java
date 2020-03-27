package com.trinet.bnftnwbandbatchprocessor.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchParamValue;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.BatchProcess;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskStep;

import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class BatchProcessTaskModel {
	
	private String startDate;
	
	private List<BatchParamValue> batchParamValues;
	
	private List<TaskStep> taskSteps;
	
	private BatchProcess batchProcess;

}
