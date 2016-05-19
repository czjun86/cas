package com.complaint.service;

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
import com.complaint.action.ReportConfigController;
import com.complaint.dao.ReportConfigDao;
import com.complaint.model.AreaBean;
import com.complaint.model.Group;
import com.complaint.model.Personnel;
import com.complaint.model.QualityBasicConfig;
import com.complaint.page.PageBean;

@Service("reportConfigService")
public class ReportConfigService {

	private static final Logger logger = LoggerFactory
			.getLogger(ReportConfigService.class);
	@Autowired
	private ReportConfigDao reportConfigDao;

	public int getGroupSeq() {
		return this.reportConfigDao.queryGroupSeq();
	}

	/**
	 * 添加分公司
	 * 
	 * @param user
	 * @return
	 */
	public int addGroup(Integer groupid, String groupname) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("groupid", groupid);
		param.put("groupname", groupname);
		try {
			this.reportConfigDao.insertGroup(param);
			return 1;
		} catch (Exception e) {
			logger.error("", e);
			return 0;
		}
	}

	/**
	 * 删除分公司
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteGroup(String ids) throws Exception {
		String[] idStrs = ids.split(",");
		for (String id : idStrs) {
			this.reportConfigDao.deleteGroup(Integer.parseInt(id));
			this.reportConfigDao.deleteGroupRelation(Integer.parseInt(id));
			this.reportConfigDao.deleteStepByGroupid(Integer.parseInt(id));
		}

	}

	/**
	 * 更新分公司归属
	 * 
	 * @param groups
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateGuishu(String groups) throws Exception {
		JSONArray arr = JSON.parseArray(groups);
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < arr.size(); i++) {
			Integer groupid = Integer.parseInt(arr.getJSONObject(i)
					.get("groupid").toString());
			param.put("groupid", groupid);
			this.reportConfigDao.deleteGroupRelation(groupid);
			List<String> list = (List<String>) arr.getJSONObject(i)
					.get("areas");
			for (String str : list) {
				if (!str.equals("")) {
					param.put("areaid", Integer.parseInt(str));
					this.reportConfigDao.insertGroupRelation(param);
				}
			}
		}

	}

	/**
	 * 修改分公司
	 * 
	 * @param groups
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void editGroup(String groups) throws Exception {
		JSONArray arr = JSON.parseArray(groups);
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < arr.size(); i++) {
			Integer groupid = Integer.parseInt(arr.getJSONObject(i)
					.get("groupid").toString());
			String groupname = arr.getJSONObject(i).get("groupname").toString();
			param.put("groupid", groupid);
			param.put("groupname", groupname);
			this.reportConfigDao.updateGroup(param);
		}
	}

	/**
	 * 查询分公司对应区域信息
	 * 
	 * @param groupid
	 * @return
	 */
	public List<Group> getGroupById(Integer groupid) {
		return this.reportConfigDao.queryGroupById(groupid);
	}

	/**
	 * 查询不在分公司的区域
	 * 
	 * @return
	 */
	public List<AreaBean> getNotInGroupArea() {
		return this.reportConfigDao.queryNotInGroupArea();
	}

	/**
	 * 分页查询分公司
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean getGroupsList(int pageIndex, int pageSize) {
		int lbound = (pageIndex - 1) * pageSize;
		int mbound = pageIndex * pageSize;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lbound", lbound);
		param.put("mbound", mbound);
		List<Group> list = reportConfigDao.queryGrouplist(param);
		PageBean pb = new PageBean();
		pb.setList(list);
		return pb;
	}

	/**
	 * 分页查询分公司
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean countGroups(int pageIndex, int pageSize) {
		int count = this.reportConfigDao.countGroups();
		PageBean pb = new PageBean();
		pb.setPageIndex(pageIndex);
		pb.setPageSize(pageSize);
		pb.setTotalPage(count);
		return pb;
	}

	/**
	 * 查询人员对应区域信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Personnel> getPersonnelById(Integer id) {
		return this.reportConfigDao.queryPersonnelById(id);
	}

	public boolean isExsit(String groupname) {
		int count = this.reportConfigDao.countGroupname(groupname);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 分公司质量报表配置
	 * 
	 * @return
	 */
	public List<Group> getGroupAndQualityConfigRelationl() {
		return this.reportConfigDao.queryGroupAndQualityConfigRelation();
	}

	/**
	 * 查询质量报表基本配置
	 */
	public List<QualityBasicConfig> getQualityBasicConfig() {
		return this.reportConfigDao.queryQualityBasicConfig();
	}

	/**
	 * 修改步长设置
	 * 
	 * @param json
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateStep(String json) throws Exception {
		JSONArray arr = JSON.parseArray(json);
		Map<String, Object> param = null;
		for (int i = 0; i < arr.size(); i++) {
			param = new HashMap<String, Object>();
			Integer id = Integer.parseInt(arr.getJSONObject(i).get("id")
					.toString());
			Integer type = Integer.parseInt(arr.getJSONObject(i).get("type")
					.toString());
			param.put("id", id);
			// 判断操作类型 1 步长配置 2 基本配置
			if (type.equals(1)) {
				Double svg_step = Double.parseDouble(arr.getJSONObject(i)
						.get("svg_step").toString());
				Double annular_step = Double.parseDouble(arr.getJSONObject(i)
						.get("annular_step").toString());
				param.put("svg_step", svg_step);
				param.put("annular_step", annular_step);
				if (id.equals(-1)) {
					Integer groupid = Integer.parseInt(arr.getJSONObject(i)
							.get("groupid").toString());
					Integer kpi = Integer.parseInt(arr.getJSONObject(i)
							.get("kpi").toString());
					param.put("groupid", groupid);
					param.put("kpi", kpi);
					// 添加步长配置
					reportConfigDao.insertStep(param);
				} else {
					reportConfigDao.updateStep(param);
				}
			} else {
				String val = arr.getJSONObject(i).get("val").toString();
				param.put("val", val);
				// 修改基本配置
				reportConfigDao.updateBasics(param);
			}
		}
	}
	
	/**
	 * 根据公司id查询对应的区域
	 * @param groupid
	 * @return
	 */
	public  List<AreaBean> getAreaByGroupId(Integer groupid){
		return this.reportConfigDao.queryAreaByGroupId(groupid);
	}
}
