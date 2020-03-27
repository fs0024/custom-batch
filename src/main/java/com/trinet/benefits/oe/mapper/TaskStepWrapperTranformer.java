/**
 * 
 */
package com.trinet.benefits.oe.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.trinet.benefits.oe.model.BatchProcessTaskStepDetails;
import com.trinet.benefits.oe.model.TaskStepModel;
import com.trinet.benefits.oe.model.wrapper.ProcessTaskStepWrapper;

/**
 * @author imistry1
 *
 * 
 */
@Component
public class TaskStepWrapperTranformer {
	
	
	private TaskStepWrapperTranformer() {};
	
	public List<ProcessTaskStepWrapper> convertToWrapperObject(List<BatchProcessTaskStepDetails> dbResultList) {
		
		Map<Integer,ProcessTaskStepWrapper> map = new HashMap<>();
		
		
		for(BatchProcessTaskStepDetails data: dbResultList) {
			
			if(!map.containsKey(data.getBatchProcessTaskId())){
				ProcessTaskStepWrapper record = new ProcessTaskStepWrapper();
				record.setBatchProcessId(data.getBatchProcessId());
				record.setBatchProcessTaskId(data.getBatchProcessTaskId());
				record.setFinalStatus(data.getFinalStatus());
				record.setStartDate(data.getStartDate());
				record.setTrackingNumber(data.getTrackingNumber());
				
				TaskStepModel stepDetail = new TaskStepModel();
				stepDetail.setBatchProcessStepId(data.getBatchProcessStepId());
				stepDetail.setIsStepPrepared(data.getStepPrepared());
				stepDetail.setStepDescription(data.getStepDescription());
				stepDetail.setStepName(data.getStepName());
				stepDetail.setStepStatus(data.getTaskStepStatus());
				
				record.setTaskStepsList(new ArrayList<>());
				record.getTaskStepsList().add(stepDetail);
				
				map.put(data.getBatchProcessTaskId(), record);
				
			}else {
				ProcessTaskStepWrapper currentRecord = map.get(data.getBatchProcessTaskId());
				TaskStepModel stepDetail = new TaskStepModel();
				stepDetail.setBatchProcessStepId(data.getBatchProcessStepId());
				stepDetail.setIsStepPrepared(data.getStepPrepared());
				stepDetail.setStepDescription(data.getStepDescription());
				stepDetail.setStepName(data.getStepName());
				stepDetail.setStepStatus(data.getTaskStepStatus());
				
				currentRecord.getTaskStepsList().add(stepDetail);
			}
			
			
		}
		
		
		
		return convertMapToList(map);
	}
	
	
	private List<ProcessTaskStepWrapper> convertMapToList(Map<Integer,ProcessTaskStepWrapper> map){
		List<ProcessTaskStepWrapper> resultList = new ArrayList<>();
		
		for(Map.Entry<Integer, ProcessTaskStepWrapper> entry : map.entrySet()) {
			resultList.add(entry.getValue());
		}
		
		
		return resultList;
				
		
	}

}
