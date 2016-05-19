package com.complaint.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.AreaDao;
import com.complaint.model.AreaBean;
import com.complaint.model.Group;
import com.complaint.model.Personnel;

@Service("areaService")
public class AreaService {
	@Autowired
	private AreaDao areaDao;
	/**
	 * 查询所有区域
	 * @return
	 */
	public List<AreaBean> getAllArea(){
		return this.areaDao.queryAllArea();
	}
	/**
	 * 查询所有公司
	 * @return
	 */
	public List<AreaBean> getAllGroup(){
		return this.areaDao.queryAllGroup();
	}
	
   /**
	* @Title: queryAreaCondition 
	* @param @param params
	* @return List<AreaBean> 
	* @throws
	*/
	public List<AreaBean> queryAreaCondition(Map<String,Object> params){
		return this.areaDao.queryAreaCondition(params);
	}
}
