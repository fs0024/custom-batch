package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="BATCH_PROCESS_ID")
	private BatchProcess batchProcess;

	// bi-directional many-to-one association to TaskStep
	@OneToMany(mappedBy = "batchProcessStep",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<TaskStep> taskSteps;
	
	@OneToMany(mappedBy = "batchProcessStep",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<BatchParam> batchParams;

}