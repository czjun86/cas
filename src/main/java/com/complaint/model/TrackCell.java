package com.complaint.model;

import java.util.List;

/***
 * 轨迹(室内外)基站小区图
 * @author Administrator
 *小区角度差变量默认15
 */
public class TrackCell {
	
	private Double lat;//纬度
	private Double lng;//经度
	private Double position;//方位角
	private Integer psc;
    private Double min;//比例开始
    private Double max;//比例结束
    private Integer nettype;
	private String laccidname;//小区显示
	private Integer bcch;
	private Integer isindoor;//是否室分
	private String wmtid;//基站ID
	private Double latcenter;//入口纬度
	private Double lngcenter;//入口经度
	
	public String getLaccidname() {
		return laccidname;
	}
	public void setLaccidname(String laccidname) {
		this.laccidname = laccidname;
	}
	public Integer getBcch() {
		return bcch;
	}
	public void setBcch(Integer bcch) {
		this.bcch = bcch;
	}
	public Integer getIsindoor() {
		return isindoor;
	}
	public void setIsindoor(Integer isindoor) {
		this.isindoor = isindoor;
	}
	public String getWmtid() {
		return wmtid;
	}
	public void setWmtid(String wmtid) {
		this.wmtid = wmtid;
	}
	public Double getLatcenter() {
		return latcenter;
	}
	public void setLatcenter(Double latcenter) {
		this.latcenter = latcenter;
	}
	public Double getLngcenter() {
		return lngcenter;
	}
	public void setLngcenter(Double lngcenter) {
		this.lngcenter = lngcenter;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getPosition() {
		return position;
	}
	public void setPosition(Double position) {
		this.position = position;
	}
	public Integer getPsc() {
		return psc;
	}
	public void setPsc(Integer psc) {
		this.psc = psc;
	}

	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public Integer getNettype() {
		return nettype;
	}
	public void setNettype(Integer nettype) {
		this.nettype = nettype;
	}



}
