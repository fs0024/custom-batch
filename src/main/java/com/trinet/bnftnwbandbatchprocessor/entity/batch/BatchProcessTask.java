package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the BATCH_PROCESS_TASK database table.
 * 
 */
@Entity
@Table(name = "BATCH_PROCESS_TASK")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BatchProcessTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "final_status")
	private String finalStatus;

	@Column(name = "start_date")
	private String startDate;

	@Column(name = "tracking_number")
	private String trackingNumber;

	// bi-directional many-to-one association to BatchParamValue
	@OneToMany(mappedBy = "batchProcessTask", cascade = CascadeType.ALL)
	private List<BatchParamValue> batchParamValues;

	// bi-directional many-to-one association to TaskSteps
	@OneToMany(mappedBy = "batchProcessTask", cascade = CascadeType.ALL)
	private List<TaskStep> taskSteps;

	// bi-directional many-to-one association to BatchProcess
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batch_process_id")
	private BatchProcess batchProcess;

}