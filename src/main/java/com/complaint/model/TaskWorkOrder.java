package com.complaint.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class TaskWorkOrder implements Serializable,JSONStreamAware{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3645692841300902395L;
	
	private String id;
	
	private String serialno; //工单号
	
	private Integer areaid; //所属区域编号
	
	private String networktype; //测试网络(2g数据，2g语音，3g数据，3g语音)
	
	private Integer breakflag; //测试环节(1-优化, 2-建设, 3-维护, 4-其它)

    private String testAddress; //测试地址
    
    private Integer testNumber;  //测试次数

    private Date createDate; //创建时间


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

	public String getTestAddress() {
		return testAddress;
	}

	public void setTestAddress(String testAddress) {
		this.testAddress = testAddress;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getTestNumber() {
		return testNumber;
	}

	public void setTestNumber(Integer testNumber) {
		this.testNumber = testNumber;
	}

	@Override
	public void writeJSONString(Writer out) throws IOException {
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("id", this.id);
		obj.put("no", this.serialno);
		obj.put("areaid", this.areaid);
		obj.put("nw", this.networktype);
		obj.put("bl", this.breakflag);
		obj.put("adr", this.testAddress);
		obj.put("tn", this.testNumber);
		obj.put("ti", this.createDate.getTime());
		JSONValue.writeJSONString(obj, out);
	}
}
