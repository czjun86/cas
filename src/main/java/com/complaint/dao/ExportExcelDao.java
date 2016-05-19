package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.Group;
import com.complaint.model.RateColor;
import com.complaint.model.WorkOrder;

public interface ExportExcelDao extends BatchDao{
	List<RateColor> queryColors();
	String queryDefDate(@Param(value="key") String key);
	int countGroupNum();
	List<Group> queryAreaInGroup();
	
	List<WorkOrder> excelSerialno(Map map);
}
