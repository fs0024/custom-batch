package com.trinet.benefits.oe.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the TASK_ITEMS database table.
 * 
 */
@Entity
@Table(name = "TASK_ITEMS")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "deleted")
	private Character deleted;

	@Column(name = "delete_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate deleteDate;

	@Column(name = "item_id")
	private String itemId;

	@Column(name = "item_id_desc")
	private String itemIdDesc;

	@Column(name = "processed_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate processedDate;

	@Column(name = "status")
	private String status;

	@Column(name = "worker_id")
	private String workerId;

	// bi-directional many-to-one association to TaskStep
	//@JsonBackReference(value = "taskItems-taskSteps")
	@ManyToOne
	@JoinColumn(name = "TASK_STEP_ID")
	private TaskStep taskStep;

	@OneToOne(mappedBy = "taskItem",cascade = CascadeType.ALL)
	private FileItems fileItems;

}