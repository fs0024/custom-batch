package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the BATCH_PARAM database table.
 * 
 */
@Entity
@Table(name = "BATCH_PARAM")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BatchParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "batch_process_id")
	private BatchProcess batchProcess;
	
	@ManyToOne()
	@JoinColumn(name = "batch_process_step_id", nullable = true, updatable = true)
	private BatchProcessStep batchProcessStep;

	@ManyToOne
	@JoinColumn(name = "file_validator_id")
	private FileValidator fileValidator;

	@Column(name = "description")
	private String description;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "validation")
	private String validation;

	// bi-directional many-to-one association to BatchParamValue
	@OneToMany(mappedBy = "batchParam", fetch=FetchType.LAZY)
	private List<BatchParamValue> batchParamValues;

}