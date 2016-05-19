package com.complaint.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MyLLOffset implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1319347753248990280L;

	private Integer type; //类型
	
	private BigDecimal oldLng; // 旧的经度

    private BigDecimal oldLat; // 旧的纬度

    private BigDecimal newLng; // 新的经度

    private BigDecimal newLat; // 新的纬度

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getOldLng() {
		return oldLng;
	}

	public void setOldLng(BigDecimal oldLng) {
		this.oldLng = oldLng;
	}

	public BigDecimal getOldLat() {
		return oldLat;
	}

	public void setOldLat(BigDecimal oldLat) {
		this.oldLat = oldLat;
	}

	public BigDecimal getNewLng() {
		return newLng;
	}

	public void setNewLng(BigDecimal newLng) {
		this.newLng = newLng;
	}

	public BigDecimal getNewLat() {
		return newLat;
	}

	public void setNewLat(BigDecimal newLat) {
		this.newLat = newLat;
	}

    
}
