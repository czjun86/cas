package com.complaint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KpiBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 795247819143183103L;
	private short kpi;
	private short type; //1-2g 2-3g
	private short scene_type;
	private List<KpiIntervalValue> kpiValues = new ArrayList<KpiIntervalValue>();
	
	
	public short getScene_type() {
		return scene_type;
	}
	public void setScene_type(short scene_type) {
		this.scene_type = scene_type;
	}
	public short getKpi() {
		return kpi;
	}
	public void setKpi(short kpi) {
		this.kpi = kpi;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public List<KpiIntervalValue> getKpiValues() {
		return kpiValues;
	}
	public void setKpiValues(List<KpiIntervalValue> kpiValues) {
		this.kpiValues = kpiValues;
	}
	
	
}
