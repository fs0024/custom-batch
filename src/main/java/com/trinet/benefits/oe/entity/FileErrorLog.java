package com.trinet.benefits.oe.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


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
	
	
	@Column(name="ITEM_ID")
	@JsonProperty("itemId")
	private String itemId;
	
	@Column(name="VALUE_SEQUENCE")
	@JsonProperty("valueSequence")
	private String valueSequence;
	
	@Column(name="FILE_VALUES")
	@JsonProperty("fileValues")
	private String fileValues;
	
	
	@Column(name="ROW_NUMBER")
	@JsonProperty("rowNumber")
	private Long rowNumber;
	
	
	//bi-directional many-to-one association to BatchParamValue
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="batch_param_value_id")
	private BatchParamValue batchParamValue;

}