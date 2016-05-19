package com.complaint.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 

 * @ClassName: Gisgrad

 * @Description: TODO

 * @author: czj

 * @date: 2013-8-20 下午4:06:05
 */
public class Gisgrad {
private String color;//等级颜色如，优、良、中差
private String realgrad;//真正等级如:优+，优-
private String title;//名称
private Double lat_m;
private Double lng_m;
@JsonIgnore
private Double lat;
@JsonIgnore
private Double lng;
private String ser;//工单
private String add;//地址
private String net;//1－2G，2—3G
private String inside;//1－室内0-室外
private String rscp;//指标等级
@JsonIgnore
private String path;//受理路径
private String ecno;
private String tx;
private String fu;
private String fd;
private String rx;
private String rq;
private String ci;
@JsonIgnore
private String phone;//投诉电话
@JsonIgnore
private String network;//工单网络类型
private String flowid;//流水号
private String isf;//是否二次查询
@JsonIgnore
private String area;//区域
@JsonIgnore
private String sence;//场景
@JsonIgnore
private String time;//测试时间
@JsonIgnore
private String jtime;//接单时间
@JsonIgnore
private String type;//业务类型
@JsonIgnore
private String talkaround;//脱网率
@JsonIgnore
private String pocent3;//3G占比
@JsonIgnore
private String pocent2;//2G占比
@JsonIgnore
private String sumc;//测试总条数
@JsonIgnore
private int call_type;
@JsonIgnore
private int ftp_type;

public String getCi() {
	return ci;
}
public void setCi(String ci) {
	this.ci = ci;
}
public String getJtime() {
	return jtime;
}
public void setJtime(String jtime) {
	this.jtime = jtime;
}
public String getInside() {
	return inside;
}
public void setInside(String inside) {
	this.inside = inside;
}

public String getSer() {
	return ser;
}
public void setSer(String ser) {
	this.ser = ser;
}
public String getAdd() {
	return add;
}
public void setAdd(String add) {
	this.add = add;
}

public String getNet() {
	return net;
}
public void setNet(String net) {
	this.net = net;
}
public String getRscp() {
	return rscp;
}
public void setRscp(String rscp) {
	this.rscp = rscp;
}
public String getEcno() {
	return ecno;
}
public void setEcno(String ecno) {
	this.ecno = ecno;
}
public String getTx() {
	return tx;
}
public void setTx(String tx) {
	this.tx = tx;
}
public String getFu() {
	return fu;
}
public void setFu(String fu) {
	this.fu = fu;
}
public String getFd() {
	return fd;
}
public void setFd(String fd) {
	this.fd = fd;
}
public String getRx() {
	return rx;
}
public void setRx(String rx) {
	this.rx = rx;
}
public String getRq() {
	return rq;
}
public void setRq(String rq) {
	this.rq = rq;
}
public String getColor() {
	return color;
}
public void setColor(String color) {
	this.color = color;
}
public Double getLat_m() {
	return lat_m;
}
public void setLat_m(Double lat_m) {
	this.lat_m = lat_m;
}
public Double getLng_m() {
	return lng_m;
}
public void setLng_m(Double lng_m) {
	this.lng_m = lng_m;
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

public String getRealgrad() {
	return realgrad;
}
public void setRealgrad(String realgrad) {
	this.realgrad = realgrad;
}

public String getIsf() {
	return isf;
}
public void setIsf(String isf) {
	this.isf = isf;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getArea() {
	return area;
}
public void setArea(String area) {
	this.area = area;
}
public String getSence() {
	return sence;
}
public void setSence(String sence) {
	this.sence = sence;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public String getTalkaround() {
	return talkaround;
}
public void setTalkaround(String talkaround) {
	this.talkaround = talkaround;
}
public String getPocent3() {
	return pocent3;
}
public void setPocent3(String pocent3) {
	this.pocent3 = pocent3;
}
public String getPocent2() {
	return pocent2;
}
public void setPocent2(String pocent2) {
	this.pocent2 = pocent2;
}
public String getFlowid() {
	return flowid;
}
public void setFlowid(String flowid) {
	this.flowid = flowid;
}
public int getCall_type() {
	return call_type;
}
public void setCall_type(int call_type) {
	this.call_type = call_type;
}
public int getFtp_type() {
	return ftp_type;
}
public void setFtp_type(int ftp_type) {
	this.ftp_type = ftp_type;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getSumc() {
	return sumc;
}
public void setSumc(String sumc) {
	this.sumc = sumc;
}
public String getNetwork() {
	return network;
}
public void setNetwork(String network) {
	this.network = network;
}

}
