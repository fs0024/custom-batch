package com.trinet.bnftnwbandbatchprocessor.dao;

import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetClientNAICSCodeModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetNewLifeDisPlansModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetNewPlansModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetOptn2Model;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.InsertNewOptnModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.QtrPlanYrStartDateModel;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface PeopleSoftDao {
	public List<String> getPFClients(String quarter, int startIndex, int endIndex);

	public Integer getPFClientsCount(String quarter);

	// clone forward
	public InsertNewOptnModel getInsertNewOptns(String pfClient, String effdt);

	public String checkOptn2(String pfClient, String effdt);

	public List<GetOptn2Model> getOptn2(String company, String pfClient);

	public void insertClOptnEfdt(String company, String pfClient, String effdt, LocalDate effdtFromOptn2);

	public void insertOptn2(String company, String pfClient, String effdt);

	public void insertClientOption2A(String company, String pfClient, String effdt, String benefitProgram,
			LocalDate effdtFromOptn2);

	public String findNewPlanBandcd(String company, String pfClient, String cloneCompany, String benefitProgram,
			LocalDate effdtFromOptn2, String planTypeLoc);

	public String findNewPlanBandcdLifeDis(String company, String pfClient, String cloneCompany, String benefitProgram,
			LocalDate effdtFromOptn2, String planType);

	public List<GetNewPlansModel> getNewPlans(LocalDate qtrPlanYearStartDate, String company, String pfClient,
			String cloneCompany, String benefitProgram, String effdt);

	public void insertOptn3(String company, String pfClient, String effdt, String benefitProgram, String planType,
			String benefitPlan, String bandCode, boolean flushFlag);

	public QtrPlanYrStartDateModel getQuarterPlanYrStartDate(String quarter, String cloneProgEffDt);

	// update banding code

	public void UpdateOPTN3BandCd(String bandCode, String company, String effdt, LocalDate planYrStartDate,
			String cloneCompany, String bandLoc);

	public void UpdateOPTN3HMOPPOBandCd(String aetnaHMOBandCode, String aetnaPPOBandCode, String aetnaBandCode,
			String effdt, String cloneCompany, String pfClient, String company);

	public void updateBandCdforBCBS2(String bcbsBandCode, String company, String effdt, LocalDate planYrStartDate,
			String cloneCompany);

	public Integer updateHealthBandCd2(String pfClient, String company, String effdt, Map<Integer, String> bandCodes);

	public void updateRateupdFundChgFlags(String pfClient, String company, String effdt);

	// update life and disability banding code
	public GetClientNAICSCodeModel getClientNAICSCode(String company);

	public void updClientOptn2LifeDisBandCd(String lifeBandCode, String disBandCode, String pfClient, String company,
			String effdt);

	public void updateClientOption3LifeBandCode(String lifeBandCode, String pfClient, String company, String effdt);

	public void updateClientOption3DisBandCode(String disBandCode, String pfClient, String company, String effdt);

	public List<GetNewLifeDisPlansModel> getNewLifeDisPlans(String pfClient, String benefitProgram, String company,
			String effdt, String cloneCompany, LocalDate planYrStartDate, String lifeBandCode, String disBandCode);

	public Integer checkOptn3(String pfClient, String benefitProgram, String company, String effdt, String bandCode,
			String benefitPlan, String planType);
	
	public void newUpdateOptn3(String company, String cloneCompany, String effdt, LocalDate planYrStartDate);
	
	public void newInsertOptn3(String pfClient, String cloneCompany, String effdt, LocalDate planYrStartDate);

}
