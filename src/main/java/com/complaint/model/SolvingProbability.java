package com.complaint.model;

import java.io.Serializable;

/**
 * 问题解决率
 * @author peng
 *昨天
 */
public class SolvingProbability implements Serializable {
	private Integer areaid;//区域id
	private String areaname;//区域名称
	private Integer special;//归属专业id
	private String specialName;//归属专业名称
	private long complainl;//投诉量
	private long solving;//问题解决量
	private long solved;//真正解决量
	private double solvingRate;//问题解决率
	private double solvedRate;//问题解决率
	
	
	public double getSolvingRate() {
		return solvingRate;
	}
	public void setSolvingRate(double solvingRate) {
		this.solvingRate = solvingRate;
	}
	public double getSolvedRate() {
		return solvedRate;
	}
	public void setSolvedRate(double solvedRate) {
		this.solvedRate = solvedRate;
	}
	public String getSpecialName() {
		return specialName;
	}
	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public Integer getSpecial() {
		return special;
	}
	public void setSpecial(Integer special) {
		this.special = special;
	}
	public long getComplainl() {
		return complainl;
	}
	public void setComplainl(long complainl) {
		this.complainl = complainl;
	}
	public long getSolving() {
		return solving;
	}
	public void setSolving(long solving) {
		this.solving = solving;
	}
	public long getSolved() {
		return solved;
	}
	public void setSolved(long solved) {
		this.solved = solved;
	}
	
	
}
