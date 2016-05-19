package com.complaint.model;

import java.io.Serializable;

public class Resource implements Serializable {
	private static final long serialVersionUID = -8372064861714036746L;
	private Integer resourceid;
	private String resourcename;
	private String url;
	private Integer parentid; 
	private String rolename;
	private String memo; //备注
	private Integer type; //0:菜单  1:按钮
	private String btntype;//add,update,delete
	private String css;
	private Integer nepotismid;//裙带关系，例选了导出或编辑，则查看就该沟选
	
	public Integer getNepotismid() {
		return nepotismid;
	}
	public void setNepotismid(Integer nepotismid) {
		this.nepotismid = nepotismid;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public Integer getResourceid() {
		return resourceid;
	}
	public void setResourceid(Integer resourceid) {
		this.resourceid = resourceid;
	}
	public String getResourcename() {
		return resourcename;
	}
	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getBtntype() {
		return btntype;
	}
	public void setBtntype(String btntype) {
		this.btntype = btntype;
	}
	
}
