package com.complaint.model;

import java.util.ArrayList;
import java.util.List;

public class Personnel {
	private Integer id;// 人员ID
	private String name;// 人员名称
	private String phone;// 人员电话
	private List<AreaBean> list = new ArrayList<AreaBean>();// 对于的区域

	public List<AreaBean> getList() {
		return list;
	}

	public void setList(List<AreaBean> list) {
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
