package com.complaint.model;

import java.io.Serializable;
import java.util.Date;

public class WorkOrderExport implements Serializable{
	
	private String serialno; //工单号
	
	private Integer areaId;//区域
	
	private String areaName;//区域
	
	private String netWorktype; //投诉网络
	
	private String usersBrand;//重要性分类

	private Date submitDatetime; //投诉时间
	
	private String netType;//网络类型 
	
	private String lat;//维度
	
	private String lng;//经度
	
	private String testTime;//测试时间
	
	private String cell;//小区
	
	private Double maxRxlev;
	
	private Double avgRxlev;
	
	private Double minRxlev;
	
	private Double maxRxqual;
	
	private Double avgRxqual;
	
	private Double minRxqual;
	
	private Double maxRscp;
	
	private Double avgRscp;
	
	private Double minRscp;
	
	private Double maxEcno;
	
	private Double avgEcno;
	
	private Double minEcno;
	
	private Double maxRsrp;
	
	private Double avgRsrp;
	
	private Double minRsrp;
	
	private Double maxRsrq;
	
	private Double avgRsrq;
	
	private Double minRsrq;
	
	private Double maxSnr;
	
	private Double avgSnr;
	
	private Double minSnr;

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getNetWorktype() {
		return netWorktype;
	}

	public void setNetWorktype(String netWorktype) {
		this.netWorktype = netWorktype;
	}

	public String getUsersBrand() {
		return usersBrand;
	}

	public void setUsersBrand(String usersBrand) {
		this.usersBrand = usersBrand;
	}

	public Date getSubmitDatetime() {
		return submitDatetime;
	}

	public void setSubmitDatetime(Date submitDatetime) {
		this.submitDatetime = submitDatetime;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public Double getMaxRxlev() {
		return maxRxlev;
	}

	public void setMaxRxlev(Double maxRxlev) {
		this.maxRxlev = maxRxlev;
	}

	public Double getAvgRxlev() {
		return avgRxlev;
	}

	public void setAvgRxlev(Double avgRxlev) {
		this.avgRxlev = avgRxlev;
	}

	public Double getMinRxlev() {
		return minRxlev;
	}

	public void setMinRxlev(Double minRxlev) {
		this.minRxlev = minRxlev;
	}

	public Double getMaxRxqual() {
		return maxRxqual;
	}

	public void setMaxRxqual(Double maxRxqual) {
		this.maxRxqual = maxRxqual;
	}

	public Double getAvgRxqual() {
		return avgRxqual;
	}

	public void setAvgRxqual(Double avgRxqual) {
		this.avgRxqual = avgRxqual;
	}

	public Double getMinRxqual() {
		return minRxqual;
	}

	public void setMinRxqual(Double minRxqual) {
		this.minRxqual = minRxqual;
	}

	public Double getMaxRscp() {
		return maxRscp;
	}

	public void setMaxRscp(Double maxRscp) {
		this.maxRscp = maxRscp;
	}

	public Double getAvgRscp() {
		return avgRscp;
	}

	public void setAvgRscp(Double avgRscp) {
		this.avgRscp = avgRscp;
	}

	public Double getMinRscp() {
		return minRscp;
	}

	public void setMinRscp(Double minRscp) {
		this.minRscp = minRscp;
	}

	public Double getMaxEcno() {
		return maxEcno;
	}

	public void setMaxEcno(Double maxEcno) {
		this.maxEcno = maxEcno;
	}

	public Double getAvgEcno() {
		return avgEcno;
	}

	public void setAvgEcno(Double avgEcno) {
		this.avgEcno = avgEcno;
	}

	public Double getMinEcno() {
		return minEcno;
	}

	public void setMinEcno(Double minEcno) {
		this.minEcno = minEcno;
	}

	public Double getMaxRsrp() {
		return maxRsrp;
	}

	public void setMaxRsrp(Double maxRsrp) {
		this.maxRsrp = maxRsrp;
	}

	public Double getAvgRsrp() {
		return avgRsrp;
	}

	public void setAvgRsrp(Double avgRsrp) {
		this.avgRsrp = avgRsrp;
	}

	public Double getMinRsrp() {
		return minRsrp;
	}

	public void setMinRsrp(Double minRsrp) {
		this.minRsrp = minRsrp;
	}

	public Double getMaxRsrq() {
		return maxRsrq;
	}

	public void setMaxRsrq(Double maxRsrq) {
		this.maxRsrq = maxRsrq;
	}

	public Double getAvgRsrq() {
		return avgRsrq;
	}

	public void setAvgRsrq(Double avgRsrq) {
		this.avgRsrq = avgRsrq;
	}

	public Double getMinRsrq() {
		return minRsrq;
	}

	public void setMinRsrq(Double minRsrq) {
		this.minRsrq = minRsrq;
	}

	public Double getMaxSnr() {
		return maxSnr;
	}

	public void setMaxSnr(Double maxSnr) {
		this.maxSnr = maxSnr;
	}

	public Double getAvgSnr() {
		return avgSnr;
	}

	public void setAvgSnr(Double avgSnr) {
		this.avgSnr = avgSnr;
	}

	public Double getMinSnr() {
		return minSnr;
	}

	public void setMinSnr(Double minSnr) {
		this.minSnr = minSnr;
	}

	
}
