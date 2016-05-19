package com.complaint.model;

public class RateValue {
	private int id;
	private int rank_code;
	private int rank_arithmetic;
	private int rank_value;
	private String rank_ratio;
	private String rank_avg;
	private String rank_color;
	public String getRank_color() {
		return rank_color;
	}
	public void setRank_color(String rank_color) {
		this.rank_color = rank_color;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRank_code() {
		return rank_code;
	}
	public void setRank_code(int rank_code) {
		this.rank_code = rank_code;
	}
	public int getRank_arithmetic() {
		return rank_arithmetic;
	}
	public void setRank_arithmetic(int rank_arithmetic) {
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
	
}
