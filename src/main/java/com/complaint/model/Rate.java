package com.complaint.model;

public class Rate {
	private String kpi_name;
	private int kpi_code;
//	网络类型2g-0 3g-1
	private short net_type;
//	室内-0 室外-1
	private short scene;
//	rank_code等级类型优良中差 
	private short rank_code;
//	等级符号> >= < <= 1-2-3-4
	private short rank_arithmetic;
	private int rank_value;
	private String rank_ratio;
	private String rank_avg;
	private String rank_color;
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
	public short getNet_type() {
		return net_type;
	}
	public void setNet_type(short net_type) {
		this.net_type = net_type;
	}
	public short getScene() {
		return scene;
	}
	public void setScene(short scene) {
		this.scene = scene;
	}
	public short getRank_code() {
		return rank_code;
	}
	public void setRank_code(short rank_code) {
		this.rank_code = rank_code;
	}
	public short getRank_arithmetic() {
		return rank_arithmetic;
	}
	public void setRank_arithmetic(short rank_arithmetic) {
		this.rank_arithmetic = rank_arithmetic;
	}
	public int getRank_value() {
		return rank_value;
	}
	public void setRank_value(int rank_value) {
		this.rank_value = rank_value;
	}
	public String getRank_ratio() {
		return rank_ratio;
	}
	public void setRank_ratio(String rank_ratio) {
		this.rank_ratio = rank_ratio;
	}
	public String getRank_avg() {
		return rank_avg;
	}
	public void setRank_avg(String rank_avg) {
		this.rank_avg = rank_avg;
	}
	public String getRank_color() {
		return rank_color;
	}
	public void setRank_color(String rank_color) {
		this.rank_color = rank_color;
	}
	
	
}
