package com.trinet.benefits.oe.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the FILE_VALIDATOR database table.
 * 
 */
@Entity
@Table(name="FILE_VALIDATOR")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileValidator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="name")
	private String name;

	//bi-directional many-to-one association to BatchParamValue
	@JsonBackReference
	@OneToMany(mappedBy="fileValidator")
	private List<BatchParam> batchParams;

	/*//bi-directional many-to-one association to ValidatorDef
	@OneToMany(mappedBy="fileValidator", fetch=FetchType.LAZY)

	private List<ValidatorDef> validatorDefs;*/

}