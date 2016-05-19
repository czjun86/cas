package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.AreaBean;
import com.complaint.model.Epinfo;
import com.complaint.model.EpinfoArea;

public interface EpinfoDao extends BatchDao{
	
	Epinfo queryUuid(String uuid);
	
	List<Epinfo> queryEpinfoList(Map<String, Object> map);
	
	int countEpinfo(@Param(value="uuid") String uuid);
	
	Epinfo queryById(Integer id);
	
	void insertEpInfo(Epinfo epinfo);
	
	void updateEpInfo(Epinfo epinfo);
	
	void deleteEpInfo(Integer id);
	
	List<Epinfo> getAllEpinfoList();
	
	List<AreaBean> getAreaBean();
	
	//根据测试手机号码,查询测试手机对应的权限区域
	List<EpinfoArea> getEpinfoArea(String phoneNum);
}
