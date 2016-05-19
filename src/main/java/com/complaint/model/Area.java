package com.complaint.model;

import java.util.List;

public class Area {
	private Integer id;
	private String text;
	private String state; // open or closed
	private boolean checked;
	private String attributes;
	private List<Area> children;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Area> getChildren() {
		return children;
	}
	public void setChildren(List<Area> area) {
		this.children = area;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
}
