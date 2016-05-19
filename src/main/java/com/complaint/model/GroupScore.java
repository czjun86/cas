package com.complaint.model;

import java.util.List;

public class GroupScore {
	private String groupName;
	private Integer groupId;
	private Double score;
	private List<GroupScore> list;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public List<GroupScore> getList() {
		return list;
	}
	public void setList(List<GroupScore> list) {
		this.list = list;
	}
	
}
