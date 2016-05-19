package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.Sysconfig;

public interface SysConfigDao {
	String queryData(String configkey);
	void updateData(Map<String, Object> map);
	//查询角度配置
	Sysconfig getAngleconfig(String configkey);
	//保存角度配置
	void saveAngleconfig(Map map);
	//查询累计网络投诉工单量
	public List<Map> getTotalSer(Map map);
}
