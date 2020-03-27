package com.trinet.benefits.oe.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.repository.BatchProcessRepository;
import com.trinet.benefits.oe.service.BatchProcessService;

@Service
public class BatchProcessServiceImpl implements BatchProcessService {

	@Autowired
	private BatchProcessRepository batchProcessRepository;

	@Override
	public Optional<BatchProcess> getBatchProcessById(String id) {

		Optional<BatchProcess> response;
		List<BatchProcess> process = batchProcessRepository.findByAppId(id);
		if (process.size() > 0) {
			response = Optional.of(process.get(0));
			return response;
		}

		return Optional.empty();
	}

	@Override
	public Optional<BatchProcess> getBatchProcessById(Integer id) {
		return batchProcessRepository.findById(id);
	}

	@Override
	public BatchProcess registerProcess(BatchProcess batchProcess) {
		return batchProcessRepository.save(batchProcess);

	}

	@Override
	public List<BatchProcess> getAllBatchProcess() {
		return batchProcessRepository.findAll();
	}

}
