package com.trinet.bnftnwbandbatchprocessor.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dshah
 *
 * 
 */
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloneForwardModel {
	
	@JsonProperty("pf_client")
	private String pfClient;
	
	@JsonProperty("company")
	private String company;
	
	@JsonProperty("effdt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private String effdt;
	
	@JsonProperty("benefit_program")
	private String benefitProgram;
	
	@JsonProperty("effdtFromOptn2")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private String effdtFromOptn2;
	
	@JsonProperty("PLAN_TYPE")
	private String planType;
	
	@JsonProperty("T2_LIFE_BAND_CD")
	private int lifeBandCode;
	
	@JsonProperty("T2_DIS_BAND_CD")
	private int disBandCode;
	
	@JsonProperty("T2_PLNYR_START_DT")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private String qtrPlanYearStartDate;

	@JsonProperty("BENEFIT_PLAN")
	private String benefitPlan;
	
	@JsonProperty("t2_band_cd")
	private String bandCode;
}
