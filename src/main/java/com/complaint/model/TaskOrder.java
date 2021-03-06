package com.complaint.model;

import java.util.Date;

public class TaskOrder {
	private String id;//工单id
	private String serialno;//工单流水号
	private Integer areaid;//所属区域编号
	private String networktype;//测试网络(2g数据，2g语音，3g数据，3g语音)
	private Integer breakflag;//测试环节(1-优化, 2-建设, 3-维护, 4-其它)
	private String test_address;//测试地址
	private Date create_date;//创建时间
	private Integer handler;//操作者(任务工单存入登陆用户编号，自主工单存入手机号)
	private Integer isverify;//审核状态(0-未审核 1-审核通过 2-审核未通过)
	private Integer validstate;//失效状态(0-启用 1-失效)
	private String areaname;//区域名
	private Integer num;//测试次数
	private String startTime;
	private String endTime;
	private String breakflagName;
	
	
	public String getBreakflagName() {
		return breakflagName;
	}
	public void setBreakflagName(String breakflagName) {
		this.breakflagName = breakflagName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getNetworktype() {
		return networktype;
	}
	public void setNetworktype(String networktype) {
		this.networktype = networktype;
	}
	public Integer getBreakflag() {
		return breakflag;
	}
	public void setBreakflag(Integer breakflag) {
		this.breakflag = breakflag;
	}
	public String getTest_address() {
		return test_address;
	}
	public void setTest_address(String test_address) {
		this.test_address = test_address;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	public Integer getHandler() {
		return handler;
	}
	public void setHandler(Integer handler) {
		this.handler = handler;
	}
	public Integer getIsverify() {
		return isverify;
	}
	public void setIsverify(Integer isverify) {
		this.isverify = isverify;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getValidstate() {
		return validstate;
	}
	public void setValidstate(Integer validstate) {
		this.validstate = validstate;
	}
}
