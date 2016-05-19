package com.complaint.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private Integer groupid;// 分公司ID
	private String groupname;// 分公司名称
	private List<AreaBean> list = new ArrayList<AreaBean>();// 已经归属的区域
	private List<AreaBean> unlist = new ArrayList<AreaBean>();// 待归属的区域
	private List<QualityConfig> quals = new ArrayList<QualityConfig>();// 分公司对应的步长
	private String areas;// 区域名称集合

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public List<QualityConfig> getQuals() {
		return quals;
	}

	public void setQuals(List<QualityConfig> quals) {
		this.quals = quals;
	}

	public List<AreaBean> getUnlist() {
		return unlist;
	}

	public void setUnlist(List<AreaBean> unlist) {
		this.unlist = unlist;
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

	public List<AreaBean> getList() {
		return list;
	}

	public void setList(List<AreaBean> list) {
		this.list = list;
	}
}
