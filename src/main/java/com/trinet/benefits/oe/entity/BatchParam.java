package com.trinet.benefits.oe.entity;

import java.io.Serializable;
import java.util.List;

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

import lombok.Getter;
import lombok.Setter;

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
public class BatchParam implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@JsonBackReference
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
	// @JsonManagedReference(value="batchParam-batchParamValues")
	@JsonIgnore
	@OneToMany(mappedBy = "batchParam", fetch = FetchType.LAZY)
	private List<BatchParamValue> batchParamValues;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BatchParam other = (BatchParam) obj;
		if (id != other.id)
			return false;
		return true;
	}
}