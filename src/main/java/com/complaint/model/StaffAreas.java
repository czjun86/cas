package com.complaint.model;

import java.util.ArrayList;
import java.util.List;

public class StaffAreas {
	private Integer id;//人员ID
	private String name;//姓名
	private String areaname;//区域名称
	private Integer curr_serialno; //总量
	private Integer curr_upgrade;//升级量
	private Integer curr_over;//超时量
	private Integer curr_complaint;//重复投诉
	private Integer curr_send;//重派
	private Integer curr_solve;// 真正解决
	private List<StaffAreas> list = new ArrayList<StaffAreas>();
	public List<StaffAreas> getList() {
		return list;
	}

	public void setList(List<StaffAreas> list) {
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public Integer getCurr_serialno() {
		return curr_serialno;
	}

	public void setCurr_serialno(Integer curr_serialno) {
		this.curr_serialno = curr_serialno;
	}

	public Integer getCurr_upgrade() {
		return curr_upgrade;
	}

	public void setCurr_upgrade(Integer curr_upgrade) {
		this.curr_upgrade = curr_upgrade;
	}

	public Integer getCurr_over() {
		return curr_over;
	}

	public void setCurr_over(Integer curr_over) {
		this.curr_over = curr_over;
	}

	public Integer getCurr_complaint() {
		return curr_complaint;
	}

	public void setCurr_complaint(Integer curr_complaint) {
		this.curr_complaint = curr_complaint;
	}

	public Integer getCurr_send() {
		return curr_send;
	}

	public void setCurr_send(Integer curr_send) {
		this.curr_send = curr_send;
	}

	public Integer getCurr_solve() {
		return curr_solve;
	}

	public void setCurr_solve(Integer curr_solve) {
		this.curr_solve = curr_solve;
	}
	
	
}
