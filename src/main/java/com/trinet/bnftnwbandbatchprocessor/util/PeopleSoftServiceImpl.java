package com.trinet.bnftnwbandbatchprocessor.util;

import com.trinet.bnftnwbandbatchprocessor.dao.PeopleSoftDao;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.FileItems;
import com.trinet.bnftnwbandbatchprocessor.entity.batch.TaskItem;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetClientNAICSCodeModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.GetOptn2Model;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.InsertNewOptnModel;
import com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft.QtrPlanYrStartDateModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class PeopleSoftServiceImpl {

	@Autowired
	private PeopleSoftDao peopleSoftDao;

	public List<String> getPFClients(String quarter, int startIndex, int endIndex) {
		return peopleSoftDao.getPFClients(quarter, startIndex, endIndex);
	}

	public Integer getPFClientsCount(String quarter) {
		return peopleSoftDao.getPFClientsCount(quarter);
	}

	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void cloneForwardLinear(String pfClient, String cloneCompany, String effdt) throws Exception {

		if (peopleSoftDao.checkOptn2(pfClient, effdt).matches(" ")) {

			log.info("Cloning started for pfclient:" + pfClient);

			InsertNewOptnModel insertNewOptnModel = peopleSoftDao.getInsertNewOptns(pfClient, effdt);

			List<GetOptn2Model> optn2ModelList = peopleSoftDao.getOptn2(insertNewOptnModel.getCompany(), pfClient);

			String benefitCloneDate = DateUtils.getEffectiveDateString(insertNewOptnModel.getQuarter(), effdt);

			QtrPlanYrStartDateModel qtrPlanYearStartDate = peopleSoftDao
					.getQuarterPlanYrStartDate(insertNewOptnModel.getQuarter(), benefitCloneDate);

			if (!optn2ModelList.isEmpty()) {
				try {

					peopleSoftDao.insertClOptnEfdt(insertNewOptnModel.getCompany(), pfClient, effdt,
							optn2ModelList.get(0).getEffdtFromOptn2());

					peopleSoftDao.insertOptn2(insertNewOptnModel.getCompany(), pfClient, effdt);

					for (GetOptn2Model optn2Model : optn2ModelList) {
						
						updateLifeAndDisabilityCode(pfClient, insertNewOptnModel.getCompany(), effdt, cloneCompany,
								optn2Model.getBenefitProgram(), qtrPlanYearStartDate.getPlanYrStartDate());

						peopleSoftDao.insertClientOption2A(insertNewOptnModel.getCompany(), pfClient, effdt,
								optn2Model.getBenefitProgram(), optn2Model.getEffdtFromOptn2());
					}
						
						peopleSoftDao.newInsertOptn3(pfClient, cloneCompany, effdt, qtrPlanYearStartDate.getPlanYrStartDate());


				} catch (Exception e) {
					log.info("Cloning failed for pfclient:" + pfClient);
					log.error(e.toString());
					throw e;
				}

			}
			log.info("Cloning ended for pfclient:" + pfClient);

		}

	}

	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public List<List<TaskItem>> cloneForwardLoop(List<TaskItem> pfClients, String cloneCompany, String effdt) throws Exception {
		List<TaskItem> successList = new ArrayList<>();
		List<TaskItem> failedList = new ArrayList<>();
		List<List<TaskItem>> lists = new ArrayList<>();
		lists.add(0, successList);
		lists.add(1, failedList);
		for (TaskItem pfClient : pfClients) {
			try {
				cloneForwardLinear(pfClient.getItemId(), cloneCompany, effdt);
				successList.add(pfClient);
			}catch (Exception e){
				failedList.add(pfClient);
			}
		}
		return lists;

	}

	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public List<List<TaskItem>> updateHealthLoop(List<TaskItem> listOfItem, String cloneCompany, String effdt)
			throws Exception {
		List<TaskItem> successList = new ArrayList<>();
		List<TaskItem> failedList = new ArrayList<>();
		List<List<TaskItem>> lists = new ArrayList<>();
		lists.add(0, successList);
		lists.add(1, failedList);
		for (TaskItem item : listOfItem) {
			FileItems fileItem = item.getFileItems();

			String[] values = fileItem.getValue().split(",");

			Map<Integer, String> bandCodeMap = new HashMap<>();

			for (Integer i = 2; i <= 21; i++) {
				if (i <= values.length - 1)
					bandCodeMap.put(i, values[i].length() < 1 ? " " : values[i]);
				else
					bandCodeMap.put(i, " ");
			}

			try {
				updateBandCode(values[0], values[1], cloneCompany, effdt, bandCodeMap);
				successList.add(item);
			} catch (Exception e) {

				failedList.add(item);
			}

		}
		return lists;
	}

	@Transactional(value = "psTransactionManager", propagation = Propagation.REQUIRED)
	public void updateBandCode(String pfClient, String company, String cloneCompany, String effdt,
			Map<Integer, String> bandCodes) throws Exception {

		log.info("Update started for pfclient:" + pfClient);

		try {

			InsertNewOptnModel insertNewOptnModel = peopleSoftDao.getInsertNewOptns(pfClient, effdt);

			String benefitCloneDate = DateUtils.getEffectiveDateString(insertNewOptnModel.getQuarter(), effdt);

			QtrPlanYrStartDateModel qtrPlanYearStartDate = peopleSoftDao
					.getQuarterPlanYrStartDate(insertNewOptnModel.getQuarter(), benefitCloneDate);

			Integer updatedRecordCount = peopleSoftDao.updateHealthBandCd2(pfClient, company, effdt, bandCodes);

			if (updatedRecordCount > 0) {
				peopleSoftDao.newUpdateOptn3(company, cloneCompany, effdt, qtrPlanYearStartDate.getPlanYrStartDate());

				peopleSoftDao.updateRateupdFundChgFlags(pfClient, cloneCompany, effdt);

				log.info("Update ended for pfclient:" + pfClient);
			} else {
				throw new Exception("Pfclient and Company not valid combination");
			}

		} catch (Exception e) {

			log.error("Error processing  for pfclient:" + pfClient);
			throw e;
		}

	}

	public void updateLifeAndDisabilityCode(String pfClient, String company, String effdt, String cloneCompany,
			String benefitProgram, LocalDate qtrPlanYearStartDate) {

		GetClientNAICSCodeModel getClientNAICSCodeModel = peopleSoftDao.getClientNAICSCode(company);

		peopleSoftDao.updClientOptn2LifeDisBandCd(getClientNAICSCodeModel.getLifeBandCd(),
				getClientNAICSCodeModel.getDisBandCd(), pfClient, company, effdt);


	}

}
