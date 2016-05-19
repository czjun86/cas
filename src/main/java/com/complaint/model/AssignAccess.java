package com.complaint.model;

import java.util.List;

public class AssignAccess {
	private int id;
	private String text;
	private String state; // open or closed
	private boolean checked;
	private int attributes;
	private List<AssignAccess> children;
	
	
	public int getAttributes() {
		return attributes;
	}
	public void setAttributes(int attributes) {
		this.attributes = attributes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public List<AssignAccess> getChildren() {
		return children;
	}
	public void setChildren(List<AssignAccess> accesses) {
		this.children = accesses;
	}
	
}
