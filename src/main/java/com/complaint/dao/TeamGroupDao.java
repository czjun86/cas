package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.AreaBean;
import com.complaint.model.GroupManager;
import com.complaint.model.Personnel;
import com.complaint.model.TeamGroup;

public interface TeamGroupDao {
	/**
	 * 分页统计页面
	 */
	Integer countGroup(Map<String, Object> map);

	/**
	 * 查出对应信息
	 */
	List<GroupManager> groupInfo(Map<String, Object> map);

	/**
	 * 大组与小组
	 * 
	 * @return
	 */
	List<TeamGroup> queryBigAndSmallRelation();

	/**
	 * 查询不在大组中的小组
	 * 
	 * @return
	 */
	List<TeamGroup> queryNotInBig();

	/**
	 * 小组与人员
	 * 
	 * @return
	 */
	List<TeamGroup> querySmallAndPersonnelRelation();

	/**
	 * 查询不在小组中的人员
	 * 
	 * @return
	 */
	List<Personnel> queryNotInSmall();

	/**
	 * 查询不是大组组长对应小组人员
	 * 
	 * @param groupid
	 * @return
	 */
	List<Personnel> queryNotInBigPersonnel(
			@Param(value = "groupid") Integer groupid);
	
	/**
	 * 查询不是小组组长的小组人员
	 * 
	 * @param groupid
	 * @return
	 */
	List<Personnel> queryNotInSmallPersonnel(
			@Param(value = "groupid") Integer groupid);
	/**
	 * 查询大组组长
	 * 
	 * @param groupid
	 * @return
	 */
	Personnel queryGreatLeader(@Param(value = "groupid") Integer groupid);

	/**
	 * 查询小组组长
	 * 
	 * @param groupid
	 * @return
	 */
	Personnel queryLeader(@Param(value = "groupid") Integer groupid);
	/**
	 * 人员与区域
	 * 
	 * @return
	 */
	List<Personnel> queryPersonneAndAreaRelation();

	/**
	 * 查询不在人员的区域
	 * 
	 * @return
	 */
	List<AreaBean> queryNotInPersonne();

	/**
	 * 
	 * @param groupname
	 * @return
	 */
	int countGroupname(@Param(value = "groupname") String groupname);

	/**
	 * 添加大组小组
	 */
	void insertTeam(Map<String, Object> map);

	/**
	 * 修改大组小组
	 * 
	 * @param map
	 */
	void updateTeam(Map<String, Object> map);

	/**
	 * 删除大组小组
	 * 
	 * @param groupid
	 */
	void deleteTeam(@Param(value = "groupid") String groupid);
	/**
	 * 删除小组加减分
	 * 
	 * @param groupid
	 */
	void deleteTeamScore(@Param(value = "groupid") String groupid);

	/**
	 * 删除大组与小组关系
	 * 
	 * @param groupid
	 */
	void deleteTeamBigRelation(@Param(value = "groupid") String groupid);
	
	void deleteTeamMa(@Param(value = "groupid") String groupid);

	/**
	 * 添加大组与小组关系
	 * 
	 * @param map
	 */
	void insertTeamBigRelation(Map<String, Object> map);

	/**
	 * 删除小组与人员关系
	 */
	void deleteTeamSmallRelation(@Param(value = "groupid") Integer groupid);

	/**
	 * 添加小组人员关系
	 * 
	 * @param map
	 */
	void insertTeamSmallRelation(Map<String, Object> map);

	/**
	 * 添加人员
	 * 
	 * @param map
	 */
	void insertPersonnel(Map<String, Object> map);

	/**
	 * 修改人员信息
	 * 
	 * @param map
	 */
	void updatePersonnel(Map<String, Object> map);

	/**
	 * 删除人员
	 * 
	 * @param groupid
	 */
	void deletePersonnel(@Param(value = "id") String id);

	/**
	 * 删除人员与区域关系
	 * 
	 * @param id
	 */
	void deletePersonnelRelation(@Param(value = "id") String id);
	/**
	 * 删除人员与小组关系
	 * 
	 * @param id
	 */
	void deletePersonTim(@Param(value = "id") String id);
	/**
	 * 修改组长关系
	 * 
	 * @param id
	 */
	void updateTeamer(Map<String, Object> map);

	/**
	 * 添加人员与区域关系
	 * 
	 * @param map
	 */
	void insertPersonnelRelation(Map<String, Object> map);

	/**
	 * 取消小组组长
	 * 
	 * @param id
	 */
	void cancelLeader();
	/**
	 * 取消大组组长
	 * 
	 * @param id
	 */
	void cancelGreatLeader();
	/**
	 * 设置小组组长
	 * 
	 * @param map
	 */
	void setLeader(@Param(value = "id") Integer id);
	/**
	 * 设置大组组长
	 * 
	 * @param map
	 */
	void setGreatLeader(@Param(value = "id") Integer id);
}
