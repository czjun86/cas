package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.ColourCode;
import com.complaint.model.EvalSet;
import com.complaint.model.KpiIntervalValue;
import com.complaint.model.MaxMinIntvel;
import com.complaint.model.Percent;
import com.complaint.model.TCasCell;
import com.complaint.model.TestMasterlog;
import com.complaint.model.TrackPoint;
import com.complaint.model.WcdmaGsmData;

public interface ReportIndependentDao {
	//查询报表比例数据(3g除psc,bcch)
		public List<WcdmaGsmData> getWcdmaData(Map<String, Object> map);
		
		//查询报表比例数据(2g,psc,bcch)
		public List<WcdmaGsmData> getGsmData(Map<String, Object> map);

		//室外X轴
		public List<KpiIntervalValue>  getRptKpiByOut();
		//室内X轴
		public List<KpiIntervalValue>  getRptKpi();
		
		/**
		 * 根据工单号查询流水号
		 * id:工单号
		 * */
		public List<TestMasterlog> getserialno(Map<String, Object> map);
		
		
		/**
		 * 根据流水号查询计算应用（HTTP,PING）数据,只有业务才有
		 * map:流水号
		 * */
		public List<TestMasterlog> getHPF(Map<String, Object> map);
		
		/**
		 * 根据流水号查询psc,bcch数据*/
		
		public List<Percent> getpsc(Map<String, Object> map);
		
		
	    public List<Percent> getFtp(Map<String, Object> map);
		/**
		 * 根据流水号查询测试结果
		 * id:流水号
		 * */
		public List<TestMasterlog> queryResult(Map<String, Object> map);
		
		/***
		 * 颜色查询
		 * @return
		 */
		public List<ColourCode> queryColor();
		/***
		 * 查询最大最小值
		 * @param map
		 * @return
		 */
		public List<MaxMinIntvel> queryMaxMin(Map<String, Object> map);
		
		public int countNumById(@Param(value="id")String id);
		
		/**
		 * 根据flowid获得原始经纬度
		 * 百度地图使用的是原始经纬度
		 */
		public Map getlatlng(String flowid);
		
		/**
		 * 根据流水号查出小区信息
		 */
		public List<TCasCell> getCell(Map pattem);
		
		/**
		 * 根据flowid查出gsm数据
		 */
		public List<TrackPoint> getGsmByFlowid(String flowid);
		/**
		 * 根据flowid查出gsm数据
		 */
		public List<TrackPoint> getWcdmaByFlowid(String flowid);

		/**
		 * 修改审核状态
		 */
		public void updateVerify(Map<String ,Object> map);
		
		/**修改地址**/
		int updateAdress(TestMasterlog testMasterlog);
		
		/**获取评分**/
		EvalSet getEval(Map map);
}
