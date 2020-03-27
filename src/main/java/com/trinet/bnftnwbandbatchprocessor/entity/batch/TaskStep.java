package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "batch_process_step_id")
	private BatchProcessStep batchProcessStep;

	// bi-directional many-to-one association to BatchProcessTask
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "batch_process_task_id")
	private BatchProcessTask batchProcessTask;

	@Column(name = "is_prepared")
	private Character isPrepared;

	@Column(name = "status")
	private String status;

	// bi-directional many-to-one association to DailyTaskStatus
	@OneToMany(mappedBy = "taskStep", fetch=FetchType.LAZY)
	private List<DailyTaskStatus> dailyTaskStatuses;

	// bi-directional many-to-one association to TaskItem
	@OneToMany(mappedBy = "taskStep", fetch=FetchType.LAZY)
	private List<TaskItem> taskItems;

}