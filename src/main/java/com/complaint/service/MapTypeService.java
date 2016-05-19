package com.complaint.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.SysConfigDao;
import com.complaint.utils.Constant;

@Service("mapTypeService")
public class MapTypeService {
	
	@Autowired
	private SysConfigDao sysConfigDao;
	private static final Logger logger = LoggerFactory
			.getLogger(MapTypeService.class);
	
	public String getType(){
		String type = sysConfigDao.queryData(Constant.MAPTYPE);
		return type;
	}
	
	/**
	 * 修改使用地图类型
	 * @param MapTye
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void update(String MapTye) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("configvalue", MapTye);
		param.put("configkey", Constant.MAPTYPE);
		sysConfigDao.updateData(param);
	}
}
