package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.GroupManager;

public interface GroupDao {
	/**
	 * 分页统计页面
	 */
	Integer countGroup(Map<String, Object> map);
	
	/**
	 * 查出对应信息
	 */
	List<GroupManager> groupInfo(Map map);
}
