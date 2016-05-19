package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.AreaBean;
import com.complaint.model.Group;
import com.complaint.model.Personnel;
import com.complaint.model.QualityBasicConfig;

public interface ReportConfigDao {

	/**
	 * 查询分公司ID查询区域ID
	 * 
	 * @param groupid
	 * @return
	 */
	List<AreaBean> queryAreaByGroupId(@Param(value = "groupid") Integer groupid);

	int countGroups();

	List<AreaBean> queryAreaCondition(Map<String, Object> params);

	int queryGroupSeq();

	void insertGroup(Map<String, Object> params);

	void deleteGroup(@Param(value = "groupid") Integer groupid);

	void updateGroup(Map<String, Object> map);

	/**
	 * 删除分公司关系
	 * 
	 * @param groupid
	 */
	void deleteGroupRelation(@Param(value = "groupid") Integer groupid);

	/**
	 * 添加分公司关系
	 * 
	 * @param params
	 */
	void insertGroupRelation(Map<String, Object> params);

	/**
	 * 查询分公司对应的区域
	 */
	List<Group> queryGroupById(@Param(value = "groupid") Integer groupid);

	/**
	 * 分公司列表
	 * 
	 * @param groupid
	 * @return
	 */
	List<Group> queryGrouplist(Map<String, Object> params);

	/**
	 * 查询不在分公司的区域
	 * 
	 * @return
	 */
	List<AreaBean> queryNotInGroupArea();

	/**
	 * 查询人员对应区域
	 * 
	 * @param id
	 * @return
	 */
	List<Personnel> queryPersonnelById(@Param(value = "id") Integer id);

	/**
	 * 添加人员
	 * 
	 * @param params
	 */
	void insertPersonnel(Personnel personnel);

	/**
	 * 万人投诉查询对应区域及公司
	 */
	List<AreaBean> queryAreaGroup();

	/**
	 * 查询分公司是否存在
	 * 
	 * @param groupname
	 * @return
	 */
	int countGroupname(@Param(value = "groupname") String groupname);

	/**
	 * 分公司质量报表配置
	 */
	List<Group> queryGroupAndQualityConfigRelation();

	/**
	 * 查询质量报表基本配置
	 */
	List<QualityBasicConfig> queryQualityBasicConfig();

	/**
	 * 添加步长
	 * 
	 * @param params
	 */
	void insertStep(Map<String, Object> params);

	/**
	 * 修改步长
	 * 
	 * @param params
	 */
	void updateStep(Map<String, Object> params);
	/**
	 * 根据分公司删除步长
	 * @param groupid
	 */
	void deleteStepByGroupid(@Param(value = "groupid") Integer groupid);

	/**
	 * 修改基本配置
	 * 
	 * @param params
	 */
	void updateBasics(Map<String, Object> params);
}
