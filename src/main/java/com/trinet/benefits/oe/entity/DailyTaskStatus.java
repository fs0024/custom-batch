package com.trinet.benefits.oe.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the DAILY_TASK_STATUS database table.
 * 
 */
@Entity
@Table(name="DAILY_TASK_STATUS")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DailyTaskStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date")
	private Date date;

	@Column(name="items_process_id_end")
	private int itemsProcessIdEnd;

	@Column(name="items_processed_id_start")
	private int itemsProcessedIdStart;

	//bi-directional many-to-one association to TaskStep
	@ManyToOne
	@JoinColumn(name="batch_step_id")
	private TaskStep taskStep;
	
}