package com.trinet.benefits.oe.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.trinet.benefits.oe.entity.BatchSchedule;

import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class BatchProcessModel {
	
	private String description;
	private String name;
	private int id;
	private BatchSchedule batchSchedule;
	private String appId;
}
