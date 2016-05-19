package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.AreaBean;
import com.complaint.model.TaskOrder;

public interface TaskConfigDao extends BatchDao{
	/**查询总页数**/
	public Integer countTask(Map map);
	/**查询当页内容**/
	public List<TaskOrder> queryList(Map map);
	/**获取角色对应区域**/
	public List<AreaBean> getAreaList(Integer userid);
	/**查询对应工单信息**/
	public TaskOrder querySerialno(String id);
	/**修改工单信息**/
	public void updateSerialno(TaskOrder to);
	/**报表信息**/
	public List<TaskOrder> createExcel(TaskOrder to);
	/**修改状态**/
	public void setValidstate(TaskOrder to);
	/**查出已有工单**/
	public List<String> getSameSerialno(Map map);
	/**查出已有工单**/
	public void updateTaskSerialno(TaskOrder to);
}
