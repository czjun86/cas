package com.complaint.model;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class WorkOrderForList implements java.io.Serializable,JSONStreamAware{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serialno;
	
	private String id;
	
	private Integer areaid;
	
    private Date submitDatetime;

	private Integer testNumber;

    private Short isDeal;
    
    private String address;

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno == null ? null : serialno.trim();
    }

    public Date getSubmitDatetime() {
        return submitDatetime;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
    
    public void setSubmitDatetime(Date submitDatetime) {
        this.submitDatetime = submitDatetime;
    }

    public Integer getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber) {
        this.testNumber = testNumber;
    }

    public Short getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Short isDeal) {
        this.isDeal = isDeal;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public void writeJSONString(Writer out) throws IOException {
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("no", this.serialno);
		obj.put("done", this.isDeal);
		obj.put("ti", this.submitDatetime.getTime());
		obj.put("tn", this.testNumber);
		obj.put("id", this.id);
		obj.put("areaid", this.areaid);
		obj.put("adr", this.address);
		JSONValue.writeJSONString(obj, out);
	}
    
}
