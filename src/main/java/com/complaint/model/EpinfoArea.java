package com.complaint.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class EpinfoArea  implements Serializable,JSONStreamAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3033040760605464879L;
	
	private Integer areaid;//区域id
	private String areaname;//区域名称
	
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	@Override
	public void writeJSONString(Writer out) throws IOException {
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("areaname", this.areaname);
		obj.put("areaid", this.areaid);
		JSONValue.writeJSONString(obj, out);
	}
	
	
}
