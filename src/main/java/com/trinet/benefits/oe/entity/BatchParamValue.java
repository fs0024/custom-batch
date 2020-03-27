package com.trinet.benefits.oe.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the BATCH_PARAM_VALUE database table.
 * 
 */
@Entity
@Table(name = "BATCH_PARAM_VALUE")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BatchParamValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "validation_flag")
	private Character validationFlag;

	@Column(name = "value")
	private String value;

	// bi-directional many-to-one association to BatchParam
	//@JsonBackReference(value="batchParam-batchParamValues")
	@ManyToOne
	@JoinColumn(name = "batch_param_id")
	private BatchParam batchParam;

	// bi-directional many-to-one association to BatchProcessTask
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "batch_process_task_id")
	private BatchProcessTask batchProcessTask;

	// bi-directional many-to-one association to FileErrorLog
	@JsonIgnore
	@OneToMany(mappedBy = "batchParamValue")
	private List<FileErrorLog> fileErrorLogs;

}