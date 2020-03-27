package com.trinet.benefits.oe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trinet.benefits.oe.entity.BatchParam;
import com.trinet.benefits.oe.entity.BatchProcess;
import com.trinet.benefits.oe.entity.BatchProcessParamMap;
import com.trinet.benefits.oe.entity.BatchProcessStep;

public interface BatchProcessParamMapRepository  extends JpaRepository<BatchProcessParamMap, Integer> {

    public List<BatchProcessParamMap> findByBatchProcessStep(BatchProcessStep batchProcessStep) ;
    
    public List<BatchProcessParamMap> findByBatchParam(BatchParam batchParam);
    
    public List<BatchProcessParamMap> findByBatchProcess(BatchProcess batchProcess);
    
    

}
