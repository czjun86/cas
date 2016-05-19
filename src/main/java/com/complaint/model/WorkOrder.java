package com.complaint.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class WorkOrder implements Serializable,JSONStreamAware{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3645692841300902395L;
	
	private String id;
	
	private String serialno; //工单号

	private Date submitDatetime; //投诉时间

    private Date requestDatetime; //要求回复时间

    private String acceptHuman;

    private String complaintChannels;

    private String admissiblePath;

    private String productName;

    private String complaint;

    private String templateContent;

    private String channelProblems;

    private String problemsAddress; //投诉地址

    private String attribution;

    private String accountAttribution;

    private String acceptanceNumber; //投诉电话

    private String usersBrand;

    private String customerContact;

    private Integer areaId;

    private String netWorktype; //投诉网络

    private Integer testNumber;

    private Short processStatus;

    private Short isDeal;

    private Date updateTime;

    private Date testtime;
    
    private Integer toleType;
    
    
    
    public Integer getToleType() {
		return toleType;
	}

	public void setToleType(Integer toleType) {
		this.toleType = toleType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	private String testphone;
    
    private String areaname;
    
    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno == null ? null : serialno.trim();
    }

    public Date getSubmitDatetime() {
        return submitDatetime;
    }

    public void setSubmitDatetime(Date submitDatetime) {
        this.submitDatetime = submitDatetime;
    }

    public Date getRequestDatetime() {
        return requestDatetime;
    }

    public void setRequestDatetime(Date requestDatetime) {
        this.requestDatetime = requestDatetime;
    }

    public String getAcceptHuman() {
        return acceptHuman;
    }

    public void setAcceptHuman(String acceptHuman) {
        this.acceptHuman = acceptHuman == null ? null : acceptHuman.trim();
    }

    public String getComplaintChannels() {
        return complaintChannels;
    }

    public void setComplaintChannels(String complaintChannels) {
        this.complaintChannels = complaintChannels == null ? null : complaintChannels.trim();
    }

    public String getAdmissiblePath() {
        return admissiblePath;
    }

    public void setAdmissiblePath(String admissiblePath) {
        this.admissiblePath = admissiblePath == null ? null : admissiblePath.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint == null ? null : complaint.trim();
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent == null ? null : templateContent.trim();
    }

    public String getChannelProblems() {
        return channelProblems;
    }

    public void setChannelProblems(String channelProblems) {
        this.channelProblems = channelProblems == null ? null : channelProblems.trim();
    }

    public String getProblemsAddress() {
        return problemsAddress;
    }

    public void setProblemsAddress(String problemsAddress) {
        this.problemsAddress = problemsAddress == null ? null : problemsAddress.trim();
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution == null ? null : attribution.trim();
    }

    public String getAccountAttribution() {
        return accountAttribution;
    }

    public void setAccountAttribution(String accountAttribution) {
        this.accountAttribution = accountAttribution == null ? null : accountAttribution.trim();
    }

    public String getAcceptanceNumber() {
        return acceptanceNumber;
    }

    public void setAcceptanceNumber(String acceptanceNumber) {
        this.acceptanceNumber = acceptanceNumber == null ? null : acceptanceNumber.trim();
    }

    public String getUsersBrand() {
        return usersBrand;
    }

    public void setUsersBrand(String usersBrand) {
        this.usersBrand = usersBrand == null ? null : usersBrand.trim();
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact == null ? null : customerContact.trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getNetWorktype() {
        return netWorktype;
    }

    public void setNetWorktype(String netWorktype) {
        this.netWorktype = netWorktype == null ? null : netWorktype.trim();
    }

    public Integer getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber) {
        this.testNumber = testNumber;
    }

    public Short getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Short processStatus) {
        this.processStatus = processStatus;
    }

    public Short getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Short isDeal) {
        this.isDeal = isDeal;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Date getTesttime() {
		return testtime;
	}

	public void setTesttime(Date testtime) {
		this.testtime = testtime;
	}

	public String getTestphone() {
		return testphone;
	}

	public void setTestphone(String testphone) {
		this.testphone = testphone;
	}

	@Override
	public void writeJSONString(Writer out) throws IOException {
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("no", this.serialno);
		obj.put("tt", this.testNumber);
		obj.put("ti", this.submitDatetime.getTime());
		obj.put("nw", this.netWorktype);
		obj.put("lv", this.usersBrand);
		obj.put("cp", this.customerContact);
		obj.put("aw", this.admissiblePath);
		obj.put("adr", this.problemsAddress);
		obj.put("ctt", this.complaint);
		obj.put("tc", this.templateContent);
		obj.put("ts", this.isDeal);
		obj.put("id", this.id);
		obj.put("areaid", this.areaId);
		JSONValue.writeJSONString(obj, out);
	}
}
