package com.complaint.model;

import java.io.Serializable;

public class RateColor implements Serializable {
//	rank_code等级类型优良中差 
	private int rank_code;
	private String rank_color;
	private int scene;
	public int getRank_code() {
		return rank_code;
	}
	public void setRank_code(int rank_code) {
		this.rank_code = rank_code;
	}
	public String getRank_color() {
		return rank_color;
	}
	public void setRank_color(String rank_color) {
		this.rank_color = rank_color;
	}
	public int getScene() {
		return scene;
	}
	public void setScene(int scene) {
		this.scene = scene;
	}
	
}
