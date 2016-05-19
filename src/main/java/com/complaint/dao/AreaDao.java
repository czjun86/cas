package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.AreaBean;
import com.complaint.model.Group;
import com.complaint.model.Personnel;

public interface AreaDao {
	/**
	 * 查询所有区域
	 * @return
	 */
	List<AreaBean> queryAllArea();
	/**
	 * 查询所有分公司
	 * @return
	 */
	List<AreaBean> queryAllGroup();
	List<AreaBean> queryAreaCondition(Map<String,Object> params);
	/**
	 * 删除分公司关系
	 * @param groupid
	 */
	void deleteGroupRelation(@Param(value="groupid") Integer groupid);
}
