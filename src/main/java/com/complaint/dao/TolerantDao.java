package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.WorkOrder;

public interface TolerantDao extends BatchDao {
	public int getTolerantPage(Map<String ,Object> map);
	
	public List<WorkOrder> getList(Map<String ,Object> map);
	
	public List<Integer> getInfo(Map<String ,Object> map);
	
	void deleteInfo(Map<String ,Object> map);
	
}
