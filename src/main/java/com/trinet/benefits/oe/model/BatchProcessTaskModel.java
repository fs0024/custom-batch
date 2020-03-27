package com.trinet.benefits.oe.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcess;

import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchProcessTaskModel {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
	private LocalDate startDate;
	
	private List<BatchParamValue> batchParamValues;

	private BatchProcess batchProcess;

	private Integer id  ;
	
	private String finalStatus;

}
