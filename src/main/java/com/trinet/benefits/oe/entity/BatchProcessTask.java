package com.trinet.benefits.oe.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private Integer id;

	@Column(name = "final_status")
	private String finalStatus;

	@Column(name = "start_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
	private LocalDate startDate;
	

	@Column(name = "tracking_number")
	private Integer trackingNumber;
	
	
	@Column(name = "FINISH_TIME")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss a")
	private String finishTime;
	

	// bi-directional many-to-one association to BatchParamValue
	@JsonIgnore
	@OneToMany(mappedBy = "batchProcessTask", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<BatchParamValue> batchParamValues;

	// bi-directional many-to-one association to TaskSteps
	@JsonManagedReference(value="batchProcessTask-steps")
	@OneToMany(mappedBy = "batchProcessTask", cascade=CascadeType.ALL)
	private List<TaskStep> taskSteps;

	// bi-directional many-to-one association to BatchProcess
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "batch_process_id")
	private BatchProcess batchProcess;

}