package com.complaint.model;

import java.util.List;

public class GroupManager {
	
	private Integer group_big_id;//大组id
	private String group_big_name;//大组名称
	private Integer group_small_id;//小组id
	private String group_small_name;//小组名称
	private String manager_name;//小组人员名称
	private String area_name;//区域人员名称
	private Double score;//得分
	
	public Integer getGroup_big_id() {
		return group_big_id;
	}
	public void setGroup_big_id(Integer group_big_id) {
		this.group_big_id = group_big_id;
	}
	public String getGroup_big_name() {
		return group_big_name;
	}
	public void setGroup_big_name(String group_big_name) {
		this.group_big_name = group_big_name;
	}
	public Integer getGroup_small_id() {
		return group_small_id;
	}
	public void setGroup_small_id(Integer group_small_id) {
		this.group_small_id = group_small_id;
	}
	public String getGroup_small_name() {
		return group_small_name;
	}
	public void setGroup_small_name(String group_small_name) {
		this.group_small_name = group_small_name;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
	
	
}
