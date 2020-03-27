package com.trinet.benefits.oe.mapper;

import java.util.ArrayList;
import java.util.List;

import com.trinet.benefits.oe.entity.TaskItem;
import com.trinet.benefits.oe.model.TaskItemModel;

public class TaskItemMapper {
	
	private TaskItemMapper(){}
	
	public static List<TaskItemModel> transformToListOfTaskItemModel(List<TaskItem> taskItems){
		
		List<TaskItemModel> listOfTaskItemModel = new ArrayList<>(); 
		
		for(TaskItem item : taskItems) {
			
			TaskItemModel taskItemModel = new TaskItemModel();
			
			taskItemModel.setId(item.getId());
			taskItemModel.setItemId(item.getItemId());
			taskItemModel.setItemIdDesc(item.getItemIdDesc());
			taskItemModel.setStatus(item.getStatus());
			taskItemModel.setWorkerId(item.getWorkerId());
			
			listOfTaskItemModel.add(taskItemModel);
		}
		
		return listOfTaskItemModel;
		
	}

}
