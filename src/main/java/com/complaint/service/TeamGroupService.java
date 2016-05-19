package com.complaint.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.complaint.action.ComplainStatisticsController;
import com.complaint.dao.TeamGroupDao;
import com.complaint.model.AreaBean;
import com.complaint.model.GroupManager;
import com.complaint.model.Personnel;
import com.complaint.model.TeamGroup;
import com.complaint.page.PageBean;
import com.complaint.utils.Constant;

@Service("teamGroupService")
public class TeamGroupService {

	private static final Logger logger = LoggerFactory
			.getLogger(TeamGroupService.class);

	@Autowired
	private TeamGroupDao teamGroupDao;

	/**
	 * 查询分页总页数
	 */
	public PageBean getPageBean(String name) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);

		int count = teamGroupDao.countGroup(param);

		PageBean pb = new PageBean();
		pb.setTotalPage((count % Constant.PAGESIZE == 0) ? (count / Constant.PAGESIZE)
				: (count / Constant.PAGESIZE + 1));
		return pb;
	}

	/**
	 * 查出当前页信息
	 */
	public List<GroupManager> getInfo(String name) {
		List<GroupManager> list = new ArrayList<GroupManager>();

		Map map = new HashMap();
		map.put("name", name == null ? "" : name);
		list = teamGroupDao.groupInfo(map);
		return list;
	}

	/**
	 * 查询大组信息
	 * 
	 * @return
	 */
	public List<TeamGroup> getBigTeams() {
		List<TeamGroup> list = teamGroupDao.queryBigAndSmallRelation();
		for (TeamGroup tg : list) {
			tg.setPlist(teamGroupDao.queryNotInBigPersonnel(tg.getGroupid()));
			tg.setPsl(teamGroupDao.queryGreatLeader(tg.getGroupid()));
		}
		return list;
	}

	/**
	 * 查询小组信息
	 * 
	 * @return
	 */
	public List<TeamGroup> getSmallTeams() {
		List<TeamGroup> list = teamGroupDao.querySmallAndPersonnelRelation();
		for (TeamGroup tg : list) {
			tg.setNotlarders(teamGroupDao.queryNotInSmallPersonnel(tg.getGroupid()));
			tg.setPsl(teamGroupDao.queryLeader(tg.getGroupid()));
		}
		return list;
	}

	/**
	 * 得到人员信息
	 * 
	 * @return
	 */
	public List<Personnel> getPersonnels() {
		return teamGroupDao.queryPersonneAndAreaRelation();
	}

	/**
	 * 未分配的小组
	 * 
	 * @return
	 */
	public List<TeamGroup> getNotInBig() {
		return teamGroupDao.queryNotInBig();
	}

	/**
	 * 未分配的人员
	 * 
	 * @return
	 */
	public List<Personnel> getNotInSmall() {
		return teamGroupDao.queryNotInSmall();
	}

	/**
	 * 未分配的区域
	 * 
	 * @return
	 */
	public List<AreaBean> getNotInPersonne() {
		return teamGroupDao.queryNotInPersonne();
	}

	/**
	 * 大组小组添加
	 * 
	 * @param type
	 * @param groupname
	 * @return
	 */
	public int addTeam(Integer type, String groupname) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type - 1);
		param.put("groupname", groupname);
		try {
			this.teamGroupDao.insertTeam(param);
			return 1;
		} catch (Exception e) {
			logger.error("add TeamGroup error:" + e);
			return 0;
		}
	}

	/**
	 * 修改大组小组信息
	 * 
	 * @param groupid
	 * @param groupname
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateTeam(String json) throws Exception {
		JSONArray arr = JSON.parseArray(json);
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < arr.size(); i++) {
			Integer groupid = Integer.parseInt(arr.getJSONObject(i)
					.get("groupid").toString());
			String groupname = arr.getJSONObject(i).get("groupname").toString();
			param.put("groupid", groupid);
			param.put("groupname", groupname);
			this.teamGroupDao.updateTeam(param);
		}
	}

	/**
	 * 删除大小组
	 * 
	 * @param type
	 * @param groupids
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteTeam(Integer type, String groupids) throws Exception {
		String[] idStrs = groupids.split(",");
		//删除组
		this.teamGroupDao.deleteTeam(groupids);
		//删除大小组对应关系
		this.teamGroupDao.deleteTeamBigRelation(groupids);
		//删除组与人员关系
		this.teamGroupDao.deleteTeamMa(groupids);
		//删除小组加减分
		this.teamGroupDao.deleteTeamScore(groupids);
		//修改大或小组组长
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("groupid", groupids);
		this.teamGroupDao.updateTeamer(param);
//		for (String id : idStrs) {
//			Integer groupid = Integer.parseInt(id);
//			this.teamGroupDao.deleteTeam(groupid);
//			if (type.equals(0)) {
//				this.teamGroupDao.deleteTeamBigRelation(groupid);
//			} else {
//				this.teamGroupDao.deleteTeamSmallRelation(groupid);
//			}
//		}

	}

	/**
	 * 修改大组与小组的关系
	 * 
	 * @param json
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateBigRelations(String json) throws Exception {
		JSONArray arr = JSON.parseArray(json);
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < arr.size(); i++) {
			String groupid = arr.getJSONObject(i).get("id")
					.toString();
			//删除大小组对应关系
			this.teamGroupDao.deleteTeamBigRelation(groupid);
			//修改大或小组组长
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 1);
			map.put("groupid", groupid);
			this.teamGroupDao.updateTeamer(map);
			List<String> list = (List<String>) arr.getJSONObject(i).get("list");
			param.put("big", groupid);
			for (String str : list) {
				if (!"".equals(str)) {
					param.put("small", Integer.parseInt(str));
					this.teamGroupDao.insertTeamBigRelation(param);
				}
			}
		}
	}

	/**
	 * 修改小组与人员的关系
	 * 
	 * @param json
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateSmallRelations(String json) throws Exception {
		JSONArray arr = JSON.parseArray(json);
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < arr.size(); i++) {
			String groupid = arr.getJSONObject(i).get("id")
					.toString();
			//删除组与人员关系
			this.teamGroupDao.deleteTeamMa(groupid);
			//修改大或小组组长
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type",2);
			map.put("groupid", groupid);
			this.teamGroupDao.updateTeamer(map);
			List<String> list = (List<String>) arr.getJSONObject(i).get("list");
			param.put("groupid", groupid);
			for (String str : list) {
				if (!"".equals(str)) {
					param.put("id", Integer.parseInt(str));
					this.teamGroupDao.insertTeamSmallRelation(param);
				}
			}
		}
	}

	/**
	 * 修改人员与区域的关系
	 * 
	 * @param json
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updatePersonnelRelations(String json) throws Exception {
		JSONArray arr = JSON.parseArray(json);
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < arr.size(); i++) {
			String id = arr.getJSONObject(i).get("id")
					.toString();
			//删除人员与区域关系
			this.teamGroupDao.deletePersonnelRelation(id);
			List<String> list = (List<String>) arr.getJSONObject(i).get("list");
			param.put("id", id);
			for (String str : list) {
				if (!"".equals(str)) {
					param.put("areaid", Integer.parseInt(str));
					this.teamGroupDao.insertPersonnelRelation(param);
				}
			}
		}
	}

	/**
	 * 设置大、小组组长
	 * 
	 * @param json
	 * @param type
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateLeader(String json, Integer type) throws Exception {
		JSONArray arr = JSON.parseArray(json);
		if (type.equals(7)) {
			// 取消大组组长
			teamGroupDao.cancelGreatLeader();
		} else {
			// 取消小组组长
			teamGroupDao.cancelLeader();
		}
		// 取消组长
		for (int i = 0; i < arr.size(); i++) {
			List<String> list = (List<String>) arr.getJSONObject(i)
					.get("list");

			for (String str : list) {
				if (!"".equals(str)) {
					if (type.equals(7)) {
						//设置大组组长
						teamGroupDao.setGreatLeader(Integer.parseInt(str));
					} else {
						// 设置小组组长
						teamGroupDao.setLeader(Integer.parseInt(str));
					}
				}
			}
		}
	}

	/**
	 * 添加人员
	 * 
	 * @param name
	 * @param phone
	 * @return
	 */
	public int addPersonnel(String name, String phone) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone", phone);
		param.put("name", name);
		try {
			this.teamGroupDao.insertPersonnel(param);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 修改人员信息
	 * 
	 * @param name
	 * @param phone
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updatePersonnel(String json) throws Exception {
		JSONArray arr = JSON.parseArray(json);
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < arr.size(); i++) {
			Integer id = Integer.parseInt(arr.getJSONObject(i).get("groupid")
					.toString());
			String name = arr.getJSONObject(i).get("groupname").toString();
			String phone = arr.getJSONObject(i).get("phone").toString();
			param.put("id", id);
			param.put("name", name);
			param.put("phone", phone);
			this.teamGroupDao.updatePersonnel(param);
		}

	}

	/**
	 * 删除人员信息
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deletePersonnel(String ids) throws Exception {
		//删除人员
		this.teamGroupDao.deletePersonnel(ids);
		//删除人员与组关系
		this.teamGroupDao.deletePersonTim(ids);
		//删除人员与区域关系
		this.teamGroupDao.deletePersonnelRelation(ids);
//		String[] idStrs = ids.split(",");
//		for (String pid : idStrs) {
//			Integer id = Integer.parseInt(pid);
//			this.teamGroupDao.deletePersonnel(id);
//			this.teamGroupDao.deletePersonnelRelation(id);
//		}
	}

	/**
	 * 判断大组小组名是否重复
	 * 
	 * @param groupname
	 * @return
	 */
	public boolean isExsit(String groupname) {
		int count = this.teamGroupDao.countGroupname(groupname);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
