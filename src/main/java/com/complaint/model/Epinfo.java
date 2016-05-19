package com.complaint.model;

import java.util.Date;

/**
 * 终端信息实体类
 * @author liuyu
 *
 */
public class Epinfo {
	private Integer id;
	private String uuid;
	private Integer islock;
	private Date addtime;
	private Date updatetime;
	private Integer areaid;
	private String functionary;
	private String teltphone;
	private String areaname;
	
	
	
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getFunctionary() {
		return functionary;
	}
	public void setFunctionary(String functionary) {
		this.functionary = functionary;
	}
	public String getTeltphone() {
		return teltphone;
	}
	public void setTeltphone(String teltphone) {
		this.teltphone = teltphone;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Integer getIslock() {
		return islock;
	}
	public void setIslock(Integer islock) {
		this.islock = islock;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
