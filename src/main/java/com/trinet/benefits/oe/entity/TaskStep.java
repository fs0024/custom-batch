package com.trinet.benefits.oe.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the TASK_STEPS database table.
 * 
 */
@Entity
@Table(name = "TASK_STEPS")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskStep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	// bi-directional many-to-one association to BatchProcessStep
	//@JsonBackReference(value="batchProcessStep-taskSteps")
	@ManyToOne
	@JoinColumn(name = "batch_process_step_id")
	private BatchProcessStep batchProcessStep;

	// bi-directional many-to-one association to BatchProcessTask
	@JsonBackReference(value="batchProcessTask-steps")
	@ManyToOne
	@JoinColumn(name = "batch_process_task_id")
	private BatchProcessTask batchProcessTask;

	@Column(name = "is_prepared")
	private Character isPrepared;

	@Column(name = "status")
	private String status;
	
	@Column(name = "FINISH_TIME")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss a")
	private String finishTime;

	// bi-directional many-to-one association to DailyTaskStatus
	@JsonIgnore
	@OneToMany(mappedBy = "taskStep", fetch=FetchType.LAZY)
	private List<DailyTaskStatus> dailyTaskStatuses;

	// bi-directional many-to-one association to TaskItem
	@JsonIgnore
	//@JsonManagedReference(value="taskItems-taskSteps")
	@OneToMany(mappedBy = "taskStep", fetch=FetchType.LAZY)
	private List<TaskItem> taskItems;

}