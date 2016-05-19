package com.complaint.model;

import java.util.List;

public class RateIntervalValue {
	private int id;
	private String kpi_name;
	private int kpi_code;
	private int net_type;
	private int scene;
	private List<RateValue> rateValues;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKpi_name() {
		return kpi_name;
	}
	public void setKpi_name(String kpi_name) {
		this.kpi_name = kpi_name;
	}
	public int getKpi_code() {
		return kpi_code;
	}
	public void setKpi_code(int kpi_code) {
		this.kpi_code = kpi_code;
	}
	public int getNet_type() {
		return net_type;
	}
	public void setNet_type(int net_type) {
		this.net_type = net_type;
	}
	public int getScene() {
		return scene;
	}
	public void setScene(int scene) {
		this.scene = scene;
	}
	public List<RateValue> getRateValues() {
		return rateValues;
	}
	public void setRateValues(List<RateValue> rateValues) {
		this.rateValues = rateValues;
	}
	
}
