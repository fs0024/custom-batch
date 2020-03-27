package com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dshah
 *
 * 
 */
@SqlResultSetMapping(name = "getClientNAICSCodeModel", entities = @EntityResult(entityClass = GetClientNAICSCodeModel.class, fields = {
		@FieldResult(name = "id", column = "id"),
		@FieldResult(name = "lifeBandCd", column = "T2_LIFE_BAND_CD"),
		@FieldResult(name = "disBandCd", column = "T2_DIS_BAND_CD") }))
@Entity
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GetClientNAICSCodeModel {
	
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="T2_LIFE_BAND_CD")
	private String lifeBandCd;
	
	@Column(name="T2_DIS_BAND_CD")
	private String disBandCd;

}
