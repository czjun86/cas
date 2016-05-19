package com.complaint.model;

import java.io.Serializable;

public class Sysconfig implements Serializable {
	 private String configname;
	 private String configkey;
	 private String configvalue;
	 private Integer status;
	public String getConfigname() {
		return configname;
	}
	public void setConfigname(String configname) {
		this.configname = configname;
	}
	public String getConfigkey() {
		return configkey;
	}
	public void setConfigkey(String configkey) {
		this.configkey = configkey;
	}
	public String getConfigvalue() {
		return configvalue;
	}
	public void setConfigvalue(String configvalue) {
		this.configvalue = configvalue;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	 
}
