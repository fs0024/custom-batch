package com.trinet.benefits.oe.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskItemDao {


	public Integer countUnPartionedRecs(Integer taskStepId);

	public List<Integer> getItemsInRange(Integer taskStepId, int startIndex, int endIndex);

	public Integer updateTaskItemPartion(List<Integer> itemIds , String worker);

	public Integer countRecsForStatus(Integer taskStepId, String status);
}
