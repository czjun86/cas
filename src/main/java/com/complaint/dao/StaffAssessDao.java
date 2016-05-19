package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.GroupManager;
import com.complaint.model.StaffAreas;
import com.complaint.model.StaffAssess;

/**
 * 人员考核环比
 * @author peng
 *
 */
public interface StaffAssessDao {
	
	public List<StaffAssess> getStaffAssess(Map<String, Object> map);

	/**
	 * 查询小组分数
	 */
	List<GroupManager> getGroupScore();
	/**
	 * 删除所有加减分
	 */
	void deleteScore();
	
	/**
	 * 修改加减得分
	 */
	void updateScore(Map<String, Object> map);
	
	/**
	 * 中心内部考核步长配置
	 */
	List<Map> getCenterStepConfig();
	/**
	 * 设置中心内部考核步长
	 */
	void saveCenterStepConfig(Map<String, Object> map);
	
	/**
	 * 中心内部考核基本配置
	 */
	List<Map> getCenterReportConfig();
	
	/**
	 * 设置中心内部考核基本配置
	 */
	void saveCenterReportConfig(Map<String, Object> map);
	/**
	 * 人员与区域统计数据
	 * @param map
	 * @return
	 */
	List<StaffAreas> getStaffAreas(Map<String, Object> map);
	
	
}
