package com.trinet.bnftnwbandbatchprocessor.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class TaskItemModel {
	private int id;
	private Character deleted;
	private String itemId;
	private String itemIdDesc;
	private String status;
	private String workerId;

}
