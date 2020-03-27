package com.trinet.benefits.oe.entity;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BATCH_PROCESS database table.
 * 
 */
@Entity
@Table(name="BATCH_PROCESS")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BatchProcess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="appId")
	private String appId;

	@Column(name="description")
	private String description;

	@Column(name="name")
	private String name;

	//bi-directional many-to-one association to BatchSchedule
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="SCHEDULEID")
	private BatchSchedule batchSchedule;

	//bi-directional many-to-one association to BatchProcessTask

	@JsonIgnore
	@OneToMany(mappedBy="batchProcess", fetch= FetchType.LAZY)
	private List<BatchProcessTask> batchProcessTasks;

	@JsonManagedReference(value="batchProcessStep-batchProcess")
	@OneToMany(mappedBy="batchProcess")
	private List<BatchProcessStep> batchProcessStep;
}