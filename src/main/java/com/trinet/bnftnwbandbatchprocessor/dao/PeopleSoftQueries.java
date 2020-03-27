package com.trinet.bnftnwbandbatchprocessor.dao;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:ps-queries.xml")
@Getter
@lombok.Generated
public class PeopleSoftQueries {
	
	@Value("${checkOptn2}")
	private String checkOptn2;
	
	@Value("${insertNewOptns}")
	private String insertNewOptns;
	
	@Value("${getOptn2}")
	private String getOptn2;
	
	@Value("${insertClOptnEfdt}")
	private String insertClOptnEfdt;	
	
	@Value("${insertOptn2}")
	private String insertOptn2;
	
	@Value("${insertClientOption2A}")
	private String insertClientOption2A;
	
	@Value("${findNewPlanBandcd}")
	private String findNewPlanBandcd;
	
	@Value("${getNewPlans}")
	private String getNewPlans;
	
	@Value("${insertOptn3}")
	private String insertOptn3;
	
	@Value("${getClientNAICSCode}")
	private String getClientNAICSCode;
	
	@Value("${updClientOptn2LifeDisBandCd}")
	private String updClientOptn2LifeDisBandCd;
	
	@Value("${updateClientOption3LifeBandCode}")
	private String updateClientOption3LifeBandCode;
	
	@Value("${updateClientOption3DisBandCode}")
	private String updateClientOption3DisBandCode;
	
	@Value("${getNewLifeDisPlans}")
	private String getNewLifeDisPlans;
	
	@Value("${getQuarterPlanYrStartDate}")
	private String getQuarterPlanYrStartDate;

	@Value("${findNewPlanBandcdForLifeDis}")
	private String findNewPlanBandcdForLifeDis;
	
	@Value("${updateOPTN3BandCd}")
	private String updateOPTN3BandCd;
	
	@Value("${updateOPTN3HMOPPOBandCode}")
	private String updateOPTN3HMOPPOBandCode;
	
	@Value("${updateBandCdforBCBS2}")
	private String updateBandCdforBCBS2;
	
	@Value("${updateHealthBandCd2}")
	private String updateHealthBandCd2;
	
	@Value("${updateRateupdFundChgFlags}")
	private String updateRateupdFundChgFlags;
	
	@Value("${checkOptn3}")
	private String checkOptn3;
	
	@Value("${newUpdateOptn3}")
	private String newUpdateOptn3;
	
	@Value("${newInsertOptn3}")
	private String newInsertOptn3;

}
