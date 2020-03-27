package com.trinet.benefits.oe.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessTask;

public interface BatchProcessTaskRepository extends JpaRepository<BatchProcessTask, Integer> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<BatchProcessTask> findByBatchProcessAndFinalStatusAndStartDateLessThanEqual(BatchProcess process,
			String status, LocalDate date);

	@Query(value = "Select MAX(TRACKING_NUMBER) from BATCH_PROCESS_TASK", nativeQuery = true)
	public Integer findMaxTrackingNumber();

	public List<BatchProcessTask> findBatchProcessTaskByBatchProcessOrderByStartDateDesc(BatchProcess batchProcess);

}
