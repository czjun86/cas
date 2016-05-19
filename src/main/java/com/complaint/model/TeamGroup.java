package com.complaint.model;

import java.util.ArrayList;
import java.util.List;

public class TeamGroup {
	private Integer groupid;// 大组小组ID
	private String groupname;// 大组小组名称
	private List<TeamGroup> list = new ArrayList<TeamGroup>();// 大组对应小组集合
	private List<Personnel> plist = new ArrayList<Personnel>();// 大组对应的未分配人员或小组对应人员集合
	private List<Personnel> notlarders = new ArrayList<Personnel>();// 不是小组组长的人员集合
	private Personnel psl = new Personnel();

	public List<Personnel> getNotlarders() {
		return notlarders;
	}

	public void setNotlarders(List<Personnel> notlarders) {
		this.notlarders = notlarders;
	}

	public Personnel getPsl() {
		return psl;
	}

	public void setPsl(Personnel psl) {
		this.psl = psl;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public List<TeamGroup> getList() {
		return list;
	}

	public void setList(List<TeamGroup> list) {
		this.list = list;
	}

	public List<Personnel> getPlist() {
		return plist;
	}

	public void setPlist(List<Personnel> plist) {
		this.plist = plist;
	}

}
