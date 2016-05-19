package com.complaint.dao;

import com.complaint.model.TestMasterlog;
import com.complaint.model.TestWorkOrder;
import com.complaint.model.WorkOrderForList;

import java.util.List;
import java.util.Map;



public interface IndependentDao {
	
	List<TestWorkOrder> queryWorkOrderList(Map<String, Object> map);
	
	TestWorkOrder queryWorkOrderBySerialno(Map<String, Object> map);
	
	Integer countWorkOrderList(Map<String, Object> map);
	
	void updateWOTestNumber(TestWorkOrder TestWorkOrder);
	
	TestWorkOrder queryWorkOrderForDetail(String serialno);
	
	List<TestWorkOrder> queryAllArea(Map<String, Object> map);
	
	List<WorkOrderForList> queryWorkOrderForPhoneList(String phone);
	//插入自主工单表
	void insertOwnWorkOrder(TestMasterlog master);
}
