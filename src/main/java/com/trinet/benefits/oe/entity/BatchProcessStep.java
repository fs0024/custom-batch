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
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the BATCH_PROCESS_STEPS database table.
 * 
 */
@Entity
@Table(name = "BATCH_PROCESS_STEPS")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BatchProcessStep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "description")
	private String description;

	@Column(name = "name")
	private String name;
	
	@Column(name = "BATCH_IDENTIFIER")
	private String batchIdentifire;

	@JsonBackReference(value="batchProcessStep-batchProcess")
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="BATCH_PROCESS_ID")
	private BatchProcess batchProcess;

	// bi-directional many-to-one association to TaskStep
	//@JsonManagedReference(value="batchProcessStep-taskSteps")
	@JsonIgnore
	@OneToMany(mappedBy = "batchProcessStep",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<TaskStep> taskSteps;

}