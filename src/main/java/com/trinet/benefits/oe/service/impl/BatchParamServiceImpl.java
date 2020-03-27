package com.trinet.benefits.oe.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchParamValue;
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessParamMap;
import com.trinet.benefits.oe.entity.BatchProcessTask;
import com.trinet.benefits.oe.repository.BatchParamValueRepository;
import com.trinet.benefits.oe.repository.BatchProcessParamMapRepository;
import com.trinet.benefits.oe.service.BatchParamService;

@Service
public class BatchParamServiceImpl implements BatchParamService {


	@Autowired
	private BatchProcessParamMapRepository batchProcessParamMapRepo;
	
	@Autowired
	private BatchParamValueRepository batchParamValueRepository;



	@Override
	public List<BatchParam> getBatchParamsByBatchProcessId(BatchProcess batchProcess) {

		List<BatchProcessParamMap> batchParamProcessMapList = batchProcessParamMapRepo.findByBatchProcess(batchProcess);

		Set<BatchParam> paramSet = new HashSet<>();

		for (BatchProcessParamMap map : batchParamProcessMapList) {
			paramSet.add(map.getBatchParam());
		}

		return new ArrayList<>(paramSet);
	}



	@Override
	public List<BatchParamValue> getBatchParamValuesByBatchTask(BatchProcessTask batchProcessTask) {
		return batchParamValueRepository.findByBatchProcessTask(batchProcessTask);
	}

}
