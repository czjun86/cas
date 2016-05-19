package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.TestWorkOrder;

public interface TaskDao {
	
	List<TestWorkOrder> queryWorkOrderList(Map<String, Object> map);
	
	Integer countWorkOrderList(Map<String, Object> map);
	
	List<TestWorkOrder> queryAllArea(Integer userid);
}
