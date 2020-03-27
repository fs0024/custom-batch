package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


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
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="SCHEDULEID")
	private BatchSchedule batchSchedule;

	//bi-directional many-to-one association to BatchProcessTask
	@OneToMany(mappedBy="batchProcess", fetch=FetchType.LAZY)
	private List<BatchProcessTask> batchProcessTasks;
	
	@OneToMany(mappedBy="batchProcess", fetch=FetchType.LAZY)
	private List<BatchParam> batchParams;

	@OneToMany(mappedBy="batchProcess", fetch=FetchType.LAZY)
	private List<BatchProcessStep> batchProcessStep;
}