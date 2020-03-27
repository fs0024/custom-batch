package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
	private int id;

	@Column(name = "validation_flag")
	private Character validationFlag;

	@Column(name = "value")
	private String value;

	// bi-directional many-to-one association to BatchParam
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "batch_param_id")
	private BatchParam batchParam;

	// bi-directional many-to-one association to BatchProcessTask
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "batch_process_task_id")
	private BatchProcessTask batchProcessTask;

	// bi-directional many-to-one association to FileErrorLog
	@OneToMany(mappedBy = "batchParamValue", fetch=FetchType.LAZY)
	private List<FileErrorLog> fileErrorLogs;

}