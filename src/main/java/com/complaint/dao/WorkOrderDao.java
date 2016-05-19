package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.TaskWorkOrder;
import com.complaint.model.TestMasterlog;
import com.complaint.model.WorkOrder;
import com.complaint.model.WorkOrderExport;
import com.complaint.model.WorkOrderForList;


public interface WorkOrderDao {
	
	List<WorkOrder> queryWorkOrderList(Map<String, Object> map);
	
	List<WorkOrderExport> queryWorkOrderExport(Map<String, Object> map);
	
	WorkOrder queryWorkOrderBySerialno(Map<String, Object> map);
	
	Integer countWorkOrderList(Map<String, Object> map);
	
	void insertWorkOrder(WorkOrder workOrder);
	
	void updateWorkOrder(WorkOrder workOrder);
	
	void updateWOTestNumber(WorkOrder workOrder);
	
	List<WorkOrderForList> queryWorkOrderForPhoneList(String phone);
	
	List<WorkOrderForList> queryLteWorkOrderForPhoneList(String phone);
	
	WorkOrder queryWorkOrderForDetail(String serialno);
	
	List<WorkOrder> queryAllArea();
	
	//查询手机终端对应区域下的所有任务工单
	List<TaskWorkOrder> queryTaskWorkOrder(String phoneNum);
	
	//插入自主工单表
	void insertOwnWorkOrder(TestMasterlog master);

	List<Map<String, Object>> queryWorkerOrderNetList();
}
