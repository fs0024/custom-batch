package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the FILE_ERROR_LOG database table.
 * 
 */
@Entity
@Table(name="FILE_ERROR_LOG")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileErrorLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	
	private String error;

	//bi-directional many-to-one association to BatchParamValue
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="batch_param_value_id")
	private BatchParamValue batchParamValue;

}