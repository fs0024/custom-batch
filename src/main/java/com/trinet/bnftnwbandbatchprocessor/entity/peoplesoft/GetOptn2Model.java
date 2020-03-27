package com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author dshah
 *
 * 
 */
@SqlResultSetMapping(name = "getOptn2Map", entities = @EntityResult(entityClass = GetOptn2Model.class, fields = {
		@FieldResult(name = "id", column = "id"),
		@FieldResult(name = "benefitProgram", column = "benefit_program"),
		@FieldResult(name = "effdtFromOptn2", column = "effdtFromOptn2"),
		@FieldResult(name = "bandCode", column = "T2_BAND_CD"),
		@FieldResult(name = "lifeBandCode", column = "T2_LIFE_BAND_CD"),
		@FieldResult(name = "disBandCode", column = "T2_DIS_BAND_CD"),
		@FieldResult(name = "bcbsBandCode", column = "T2_BCBS_BAND_CD"),
		@FieldResult(name = "bcbsncBandCode", column = "T2_BCBSNC_BAND_CD"),
		@FieldResult(name = "kaisaCABandCode", column = "T2_KAISCA_BAND_CD"),
		@FieldResult(name = "kaisaCOBandCode", column = "T2_KAISCO_BAND_CD"),
		@FieldResult(name = "bsoCABandCode", column = "T2_BSOFCA_BAND_CD"),
		@FieldResult(name = "aetnaBandCode", column = "T2_AETNA_BAND_CD"),
		@FieldResult(name = "tuftsBandCode", column = "T2_TUFTS_BAND_CD"),
		@FieldResult(name = "uhcBandCode", column = "T2_UHC_BAND_CD"),
		@FieldResult(name = "bcoIDBandCode", column = "T2_BCOFID_BAND_CD"),
		@FieldResult(name = "bcoMNBandCode", column = "T2_BCBSMN_BAND_CD"),
		@FieldResult(name = "kaisaMIBandCode", column = "T2_KAIMIDA_BAND_CD"),
		@FieldResult(name = "kaisaHIBandCode", column = "T2_KAISHI_BAND_CD"),
		@FieldResult(name = "empireNYBandCode", column = "T2_EMP_NY_BAND_CD"),
		@FieldResult(name = "aetnaHMOBandCode", column = "T2_AET_HMO_BAND_CD"),
		@FieldResult(name = "aetnaPPOBandCode", column = "T2_AET_PPO_BAND_CD")
		
}
))
@Entity
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GetOptn2Model {
	
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name = "benefit_program")
	private String benefitProgram;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	@Column(name = "effdtFromOptn2")
	private LocalDate effdtFromOptn2;
	
	@Column(name = "T2_BAND_CD")
	private String bandCode;
	
	@Column(name = "T2_LIFE_BAND_CD")
	private String lifeBandCode;
	
	@Column(name = "T2_DIS_BAND_CD")
	private String disBandCode;
	
	@Column(name="T2_BCBS_BAND_CD")
	private String bcbsBandCode;
	
	@Column(name = "T2_BCBSNC_BAND_CD")
	private String bcbsncBandCode;
	
	@Column(name = "T2_KAISCA_BAND_CD")
	private String kaisaCABandCode;
	
	@Column(name = "T2_KAISCO_BAND_CD")
	private String kaisaCOBandCode;
	
	@Column(name = "T2_BSOFCA_BAND_CD")
	private String bsoCABandCode;
	
	@Column(name = "T2_AETNA_BAND_CD")
	private String aetnaBandCode;
	
	@Column(name = "T2_TUFTS_BAND_CD")
	private String tuftsBandCode;
	
	@Column(name = "T2_UHC_BAND_CD")
	private String uhcBandCode;
	
	@Column(name = "T2_BCOFID_BAND_CD")
	private String bcoIDBandCode;
	
	@Column(name = "T2_BCBSMN_BAND_CD")
	private String bcoMNBandCode;
	
	@Column(name = "T2_KAIMIDA_BAND_CD")
	private String kaisaMIBandCode;
	
	@Column(name = "T2_KAISHI_BAND_CD")
	private String kaisaHIBandCode;
	
	@Column(name = "T2_EMP_NY_BAND_CD")
	private String empireNYBandCode;
	
	@Column(name = "T2_AET_HMO_BAND_CD")
	private String aetnaHMOBandCode;
	
	@Column(name = "T2_AET_PPO_BAND_CD")
	private String aetnaPPOBandCode;

}
