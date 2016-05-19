package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.BaseStation;
import com.complaint.model.CenterZoom;
import com.complaint.model.GwCasCell;
import com.complaint.model.TCasCell;
import com.complaint.model.ReportCells;

public interface BaseStationDao {
	List<BaseStation> queryBaseStationData(Map<String, Object> map);
	CenterZoom queryCenter(Map<String, Object> map);
	BaseStation queryBaseStationById(@Param(value="type") String type,@Param(value="bid") Integer bid);
	List<TCasCell> queryNearCellOther(Map<String, Object> map);
	List<TCasCell> queryNearCell(Map<String, Object> map);
	TCasCell queryCellById(Map<String, Object> map);
	TCasCell queryCellByIdOriginal(Map<String, Object> map);
	TCasCell queryCell(Map<String, Object> map);
	List<Map<String,Object>> queryAreaPosition(Map<String, Object> params);
	void setAreaCenter(Map<String, Object> params);
	List<TCasCell> queryCellInfos();
	List<GwCasCell> queryRegiondown(Map<String, Object> params);
	List<GwCasCell> queryReportNearCellOther(Map<String, Object> params);
	List<GwCasCell> queryReportNearCell(Map<String, Object> map);
	GwCasCell queryReportCellById(Map<String, Object> map);
	Integer countRegiondown(Map<String, Object> params);
	List<ReportCells> queryReportLoadCells(Map<String, Object> map);
}
