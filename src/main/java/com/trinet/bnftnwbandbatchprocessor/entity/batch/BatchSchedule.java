package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the BATCH_SCHEDULE database table.
 * 
 */
@Entity
@Table(name="BATCH_SCHEDULE")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BatchSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="value")
	private String value;

	//bi-directional many-to-one association to BatchProcess
	@OneToMany(mappedBy="batchSchedule",fetch=FetchType.LAZY)
	private List<BatchProcess> batchProcesses;

}