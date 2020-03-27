package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="batch_step_id")
	private TaskStep taskStep;
	
}