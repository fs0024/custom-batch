package com.trinet.bnftnwbandbatchprocessor.dao;

import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetClientNAICSCodeModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetNewLifeDisPlansModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetNewPlansModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetOptn2Model;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.InsertNewOptnModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.QtrPlanYrStartDateModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PeopleSoftDaoImpl implements PeopleSoftDao {

	@PersistenceContext(unitName = "psPersistenceUnit")
	@Autowired
	private EntityManager psEntityManager;

	@Autowired
	private PeopleSoftQueries queries;

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPFClients(String quarter, int startIndex, int endIndex) {

		String query = "SELECT PF_CLIENT \n"
				+ "FROM(SELECT DISTINCT PF_CLIENT, ROW_NUMBER() OVER (ORDER BY PF_CLIENT) R FROM\n"
				+ " PS_T2_CLIENTOPTION WHERE  PF_CORP IN ('PEOUS', 'PEO01', 'PEO02', 'PEO03',\n"
				+ "'PEO04', 'PEO05', 'PEO06','PEO07') AND T2_PLAN_DT_START IS NOT NULL\n"
				+ "AND T2_COMP_TERM_DT IS NULL and t2_oe_quarter= :quarter AND company not in (\n" + 
				"'001',\n" + 
				"'002',\n" + 
				"'003',\n" + 
				"'007',\n" + 
				"'008',\n" + 
				"'009',\n" + 
				"'00A',\n" + 
				"'00B',\n" + 
				"'00C',\n" + 
				"'020',\n" + 
				"'024',\n" + 
				"'DVP',\n" + 
				"'E42',\n" + 
				"'NWH',\n" + 
				"'PAZ',\n" + 
				"'PEJ',\n" + 
				"'SQU')) \n"
				+ "                where R>= :startIndex AND R <= :endIndex";

		return psEntityManager.createNativeQuery(query).setParameter("quarter", quarter)
				.setParameter("startIndex", startIndex).setParameter("endIndex", endIndex).getResultList();

	}

	@Override
	public Integer getPFClientsCount(String quarter) {

		String query = "SELECT DISTINCT count(PF_CLIENT) FROM\n"
				+ "            PS_T2_CLIENTOPTION WHERE  PF_CORP IN ('PEOUS', 'PEO01', 'PEO02', 'PEO03',\n"
				+ "            'PEO04', 'PEO05', 'PEO06','PEO07') AND T2_PLAN_DT_START IS NOT NULL\n"
				+ "				AND T2_COMP_TERM_DT IS NULL and t2_oe_quarter = :quarter AND company not in (\n" + 
				"'001',\n" + 
				"'002',\n" + 
				"'003',\n" + 
				"'007',\n" + 
				"'008',\n" + 
				"'009',\n" + 
				"'00A',\n" + 
				"'00B',\n" + 
				"'00C',\n" + 
				"'020',\n" + 
				"'024',\n" + 
				"'DVP',\n" + 
				"'E42',\n" + 
				"'NWH',\n" + 
				"'PAZ',\n" + 
				"'PEJ',\n" + 
				"'SQU')";

		return ((BigDecimal) psEntityManager.createNativeQuery(query).setParameter("quarter", quarter)
				.getSingleResult()).intValue();
	}

	@Override
	public InsertNewOptnModel getInsertNewOptns(String pfClient, String effdt) {
		return (InsertNewOptnModel) psEntityManager.createNativeQuery(queries.getInsertNewOptns(), "insertNewOptnsMap")
				.setParameter("pfClient", pfClient).setParameter("effdt", effdt).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GetOptn2Model> getOptn2(String company, String pfClient) {
		try {
			return (List<GetOptn2Model>) psEntityManager.createNativeQuery(queries.getGetOptn2(), "getOptn2Map")
					.setParameter("company", company).setParameter("pfClient", pfClient).getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>();
		}

	}

	@Override
	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void insertOptn2(String company, String pfClient, String effdt) {
		psEntityManager.createNativeQuery(queries.getInsertOptn2()).setParameter("pfClient", pfClient)
				.setParameter("company", company).setParameter("effdt", effdt).executeUpdate();
	}

	@Override
	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void insertClientOption2A(String company, String pfClient, String effdt, String benefitProgram,
			LocalDate effdtFromOptn2) {
		psEntityManager.createNativeQuery(queries.getInsertClientOption2A()).setParameter("pfClient", pfClient)
				.setParameter("effdt", effdt).setParameter("company", company)
				.setParameter("benefitProgram", benefitProgram).setParameter("effdtFromOptn2", effdtFromOptn2)
				.executeUpdate();
	}

	@Override
	public String findNewPlanBandcd(String company, String pfClient, String cloneCompany, String benefitProgram,
			LocalDate effdtFromOptn2, String planTypeLoc) {

		try {
			return psEntityManager.createNativeQuery(queries.getFindNewPlanBandcd()).setParameter("pfClient", pfClient)
					.setParameter("company", company).setParameter("cloneCompany", cloneCompany)
					.setParameter("benefitProgram", benefitProgram).setParameter("effdtFromOptn2", effdtFromOptn2)
					.setParameter("planTypeLoc", planTypeLoc).getSingleResult().toString();

		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GetNewPlansModel> getNewPlans(LocalDate qtrPlanYearStartDate, String company, String pfClient,
			String cloneCompany, String benefitProgram, String effdt) {
		return (List<GetNewPlansModel>) psEntityManager
				.createNativeQuery(queries.getGetNewPlans(), "getNewPlansModelMap")
				.setParameter("qtrPlanYearStartDate", qtrPlanYearStartDate).setParameter("pfClient", pfClient)
				.setParameter("company", company).setParameter("cloneCompany", cloneCompany)
				.setParameter("benefitProgram", benefitProgram).setParameter("effdt", effdt).getResultList();
	}

	@Override
	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void insertOptn3(String company, String pfClient, String effdt, String benefitProgram, String planType,
			String benefitPlan, String bandCode, boolean flushFlag) {
		if (flushFlag) {
			psEntityManager.flush();
		}
		psEntityManager.createNativeQuery(queries.getInsertOptn3()).setParameter("bandCode", bandCode)
				.setParameter("pfClient", pfClient).setParameter("company", company).setParameter("effdt", effdt)
				.setParameter("planType", planType).setParameter("benefitProgram", benefitProgram)
				.setParameter("benefitPlan", benefitPlan).executeUpdate();
	}

	@Override
	public QtrPlanYrStartDateModel getQuarterPlanYrStartDate(String quarter, String cloneProgEffDt) {
		return (QtrPlanYrStartDateModel) psEntityManager
				.createNativeQuery(queries.getGetQuarterPlanYrStartDate(), "qtrPlanYrStartDate")
				.setParameter("quarter", quarter).setParameter("cloneBenefitProgEffDate", cloneProgEffDt)
				.getSingleResult();
	}

	@Override
	public String findNewPlanBandcdLifeDis(String company, String pfClient, String cloneCompany, String benefitProgram,
			LocalDate effdtFromOptn2, String planType) {
		try {
			return psEntityManager.createNativeQuery(queries.getFindNewPlanBandcdForLifeDis())
					.setParameter("pfClient", pfClient).setParameter("company", company)
					.setParameter("cloneCompany", cloneCompany).setParameter("benefitProgram", benefitProgram)
					.setParameter("effdtFromOptn2", effdtFromOptn2).setParameter("planType", planType).getSingleResult()
					.toString();
		} catch (Exception e) {
			return " ";
		}
	}

	@Override
	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void UpdateOPTN3BandCd(String bandCode, String company, String effdt, LocalDate planYrStartDate,
			String cloneCompany, String bandLoc) {
		psEntityManager.createNativeQuery(queries.getUpdateOPTN3BandCd()).setParameter("newBandCode", bandCode)
				.setParameter("company", company).setParameter("effdt", effdt)
				.setParameter("planYrStartDate", planYrStartDate).setParameter("cloneCompany", cloneCompany)
				.setParameter("bandLoc", bandLoc).executeUpdate();

	}

	@Override
	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void UpdateOPTN3HMOPPOBandCd(String aetnaHMOBandCode, String aetnaPPOBandCode, String aetnaBandCode,
			String effdt, String cloneCompany, String pfClient, String company) {
		psEntityManager.createNativeQuery(queries.getUpdateOPTN3HMOPPOBandCode())
				.setParameter("aetnaHMOBandCode", aetnaHMOBandCode).setParameter("aetnaPPOBandCode", aetnaPPOBandCode)
				.setParameter("aetnaBandCode", aetnaBandCode).setParameter("effdt", effdt)
				.setParameter("cloneCompany", cloneCompany).setParameter("pfClient", pfClient)
				.setParameter("company", company).executeUpdate();
	}

	@Override
	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void updateBandCdforBCBS2(String bcbsBandCode, String company, String effdt, LocalDate planYrStartDate,
			String cloneCompany) {
		psEntityManager.createNativeQuery(queries.getUpdateBandCdforBCBS2()).setParameter("bcbsBandCode", bcbsBandCode)
				.setParameter("company", company).setParameter("effdt", effdt)
				.setParameter("planYrStartDate", planYrStartDate).setParameter("cloneCompany", cloneCompany)
				.executeUpdate();
	}

	@Override
	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public Integer updateHealthBandCd2(String pfClient, String company, String effdt, Map<Integer, String> bandCodes) {
		return psEntityManager.createNativeQuery(queries.getUpdateHealthBandCd2()).setParameter("pfClient", pfClient).setParameter("company", company)
				.setParameter("effdt", effdt).setParameter("otherBandCode", bandCodes.get(12))
				.setParameter("bcbsBandCode", bandCodes.get(13)).setParameter("kaisCABandCode", bandCodes.get(6))
				.setParameter("kaisCOBandCode", bandCodes.get(7)).setParameter("bsoCABandCode", bandCodes.get(8))
				.setParameter("aetnaBandCode", bandCodes.get(9)).setParameter("tuftsBandCode", bandCodes.get(10))
				.setParameter("uhcBandCode", bandCodes.get(11)).setParameter("bcbsNCBandCode", bandCodes.get(14))
				.setParameter("bcoIDBandCode", bandCodes.get(15)).setParameter("bcbsMNBandCode", bandCodes.get(16))
				.setParameter("kaisMidAtlBandCode", bandCodes.get(17)).setParameter("kaisHIBandCode", bandCodes.get(18))
				.setParameter("aetnaHMOBandCode", bandCodes.get(19)).setParameter("aetnaPPOBandCode", bandCodes.get(20))
				.setParameter("empireNYBandCode", bandCodes.get(21)).executeUpdate();
	}

	@Override
	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void updateRateupdFundChgFlags(String pfClient, String company, String effdt) {
		psEntityManager.createNativeQuery(queries.getUpdateRateupdFundChgFlags()).setParameter("company", company)
				.setParameter("pfClient", pfClient).setParameter("effdt", effdt).executeUpdate();

	}

	@Override
	public String checkOptn2(String pfClient, String effdt) {
		try {
			return (String) psEntityManager.createNativeQuery(queries.getCheckOptn2())
					.setParameter("pfClient", pfClient).setParameter("effdt", effdt).getSingleResult();
		} catch (Exception e) {
			return " ";
		}
	}

	@Override
	public void insertClOptnEfdt(String company, String pfClient, String effdt, LocalDate effdtFromOptn2) {
		psEntityManager.createNativeQuery(queries.getInsertClOptnEfdt()).setParameter("company", company)
				.setParameter("pfClient", pfClient).setParameter("effdt", effdt)
				.setParameter("effdtFromOptn2", effdtFromOptn2).executeUpdate();
	}

	// update life and disability banding code

	@Override
	public GetClientNAICSCodeModel getClientNAICSCode(String company) {
		return (GetClientNAICSCodeModel) psEntityManager
				.createNativeQuery(queries.getGetClientNAICSCode(), "getClientNAICSCodeModel")
				.setParameter("company", company).getSingleResult();
	}

	@Override
	public void updClientOptn2LifeDisBandCd(String lifeBandCode, String disBandCode, String pfClient, String company,
			String effdt) {
		psEntityManager.createNativeQuery(queries.getUpdClientOptn2LifeDisBandCd())
				.setParameter("lifeBandCode", lifeBandCode).setParameter("disBandCode", disBandCode)
				.setParameter("pfClient", pfClient).setParameter("company", company).setParameter("effdt", effdt)
				.executeUpdate();
	}

	@Override
	public void updateClientOption3LifeBandCode(String lifeBandCode, String pfClient, String company, String effdt) {
		psEntityManager.createNativeQuery(queries.getUpdateClientOption3LifeBandCode())
				.setParameter("lifeBandCode", lifeBandCode).setParameter("pfClient", pfClient)
				.setParameter("company", company).setParameter("effdt", effdt).executeUpdate();
	}

	@Override
	public void updateClientOption3DisBandCode(String disBandCode, String pfClient, String company, String effdt) {
		psEntityManager.createNativeQuery(queries.getUpdateClientOption3DisBandCode())
				.setParameter("disBandCode", disBandCode).setParameter("pfClient", pfClient)
				.setParameter("company", company).setParameter("effdt", effdt).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GetNewLifeDisPlansModel> getNewLifeDisPlans(String pfClient, String benefitProgram, String company,
			String effdt, String cloneCompany, LocalDate planYrStartDate, String lifeBandCode, String disBandCode) {
		return (List<GetNewLifeDisPlansModel>) psEntityManager
				.createNativeQuery(queries.getGetNewLifeDisPlans(), "getNewLifeDisPlansModel")
				.setParameter("pfClient", pfClient).setParameter("benefitProgram", benefitProgram)
				.setParameter("company", company).setParameter("effdt", effdt)
				.setParameter("qtrPlanYearStartDate", planYrStartDate).setParameter("lifeBandCode", lifeBandCode)
				.setParameter("disBandCode", disBandCode).setParameter("cloneCompany", cloneCompany).getResultList();
	}

	@Override
	public Integer checkOptn3(String pfClient, String benefitProgram, String company, String effdt, String bandCode,
			String benefitPlan, String planType) {
		return (Integer) psEntityManager.createNativeQuery(queries.getCheckOptn3()).setParameter("pfClient", pfClient)
				.setParameter("benefitProgram", benefitProgram).setParameter("company", company)
				.setParameter("effdt", effdt).setParameter("bandCode", bandCode)
				.setParameter("benefitPlan", benefitPlan).setParameter("planType", planType).getSingleResult();
	}

	@Override
	public void newUpdateOptn3(String company, String cloneCompany, String effdt, LocalDate planYrStartDate) {
		psEntityManager.createNativeQuery(queries.getNewUpdateOptn3()).setParameter("company", company)
				.setParameter("cloneCompany", cloneCompany).setParameter("effdtStr", effdt)
				.setParameter("planYrStartDate", planYrStartDate).executeUpdate();
	}

	@Override
	public void newInsertOptn3(String pfClient, String cloneCompany, String effdt, LocalDate planYrStartDate) {
		
		psEntityManager.createNativeQuery(queries.getNewInsertOptn3()).setParameter("cloneCompany", cloneCompany)
		.setParameter("pfClient", pfClient).setParameter("effdt", effdt).setParameter("planYrStartDate", planYrStartDate).executeUpdate();
		
	}
}
