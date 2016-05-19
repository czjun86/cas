package com.complaint.model;

import java.io.Serializable;

/**
 * 投诉率与投诉统计
 * @author peng
 *
 */
public class ComplainProbability implements Serializable {
	private Integer areaid;//区域id
	private String areaname;//区域名称
	private Integer groupid;//公司分类id
	private String groupname;//公司分类名称
	private long gsm_complaint;//2g投诉量
	private long wcdma_complaint;//3g投诉量
	private long gsm_speech_complaint;//2g语音投诉量
	private long wcdma_speech_complaint;//3g语音投诉量
	private long gsm_data_complaint;//2g数据投诉量
	private long wcdma_data_complaint;//3g数据投诉量
	private long gsm_inside_uncovered;//2g室内无覆盖
	private long gsm_outside_uncovered;//2g室外无覆盖
	private long wcdma_inside_uncovered;//3g室内无覆盖
	private long wcdma_outside_uncovered;//3g室外无覆盖
	private double gsm_inside_ratio;//2g室内无覆盖占比
	private double gsm_outside_ratio;//2g室外无覆盖占比
	private double wcdma_inside_ratio;//3g室内无覆盖占比
	private double wcdma_outside_ratio;//3g室外无覆盖占比
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
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public long getGsm_complaint() {
		return gsm_complaint;
	}
	public void setGsm_complaint(long gsm_complaint) {
		this.gsm_complaint = gsm_complaint;
	}
	public long getWcdma_complaint() {
		return wcdma_complaint;
	}
	public void setWcdma_complaint(long wcdma_complaint) {
		this.wcdma_complaint = wcdma_complaint;
	}
	public long getGsm_speech_complaint() {
		return gsm_speech_complaint;
	}
	public void setGsm_speech_complaint(long gsm_speech_complaint) {
		this.gsm_speech_complaint = gsm_speech_complaint;
	}
	public long getWcdma_speech_complaint() {
		return wcdma_speech_complaint;
	}
	public void setWcdma_speech_complaint(long wcdma_speech_complaint) {
		this.wcdma_speech_complaint = wcdma_speech_complaint;
	}
	public long getGsm_data_complaint() {
		return gsm_data_complaint;
	}
	public void setGsm_data_complaint(long gsm_data_complaint) {
		this.gsm_data_complaint = gsm_data_complaint;
	}
	public long getWcdma_data_complaint() {
		return wcdma_data_complaint;
	}
	public void setWcdma_data_complaint(long wcdma_data_complaint) {
		this.wcdma_data_complaint = wcdma_data_complaint;
	}
	public long getGsm_inside_uncovered() {
		return gsm_inside_uncovered;
	}
	public void setGsm_inside_uncovered(long gsm_inside_uncovered) {
		this.gsm_inside_uncovered = gsm_inside_uncovered;
	}
	public long getGsm_outside_uncovered() {
		return gsm_outside_uncovered;
	}
	public void setGsm_outside_uncovered(long gsm_outside_uncovered) {
		this.gsm_outside_uncovered = gsm_outside_uncovered;
	}
	public long getWcdma_inside_uncovered() {
		return wcdma_inside_uncovered;
	}
	public void setWcdma_inside_uncovered(long wcdma_inside_uncovered) {
		this.wcdma_inside_uncovered = wcdma_inside_uncovered;
	}
	public long getWcdma_outside_uncovered() {
		return wcdma_outside_uncovered;
	}
	public void setWcdma_outside_uncovered(long wcdma_outside_uncovered) {
		this.wcdma_outside_uncovered = wcdma_outside_uncovered;
	}
	public double getGsm_inside_ratio() {
		return gsm_inside_ratio;
	}
	public void setGsm_inside_ratio(double gsm_inside_ratio) {
		this.gsm_inside_ratio = gsm_inside_ratio;
	}
	public double getGsm_outside_ratio() {
		return gsm_outside_ratio;
	}
	public void setGsm_outside_ratio(double gsm_outside_ratio) {
		this.gsm_outside_ratio = gsm_outside_ratio;
	}
	public double getWcdma_inside_ratio() {
		return wcdma_inside_ratio;
	}
	public void setWcdma_inside_ratio(double wcdma_inside_ratio) {
		this.wcdma_inside_ratio = wcdma_inside_ratio;
	}
	public double getWcdma_outside_ratio() {
		return wcdma_outside_ratio;
	}
	public void setWcdma_outside_ratio(double wcdma_outside_ratio) {
		this.wcdma_outside_ratio = wcdma_outside_ratio;
	}
	
	
}
