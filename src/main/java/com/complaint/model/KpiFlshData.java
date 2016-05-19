package com.complaint.model;

import java.util.ArrayList;
import java.util.List;

public class KpiFlshData {
   private List<String> x =new ArrayList<String>();//室外X轴
   private List<String> indoor_x =new ArrayList<String>();//室内X轴
   private List<Percent> y=new ArrayList<Percent>();
   private String kpiId;
   private String netType;
   private String kpiname;
   private Double max;
   private Double min;
   private Double avg;
   private String gradName;//评级名称优、良、中、差
   private String gradColor;//评级对应的颜色值
   private List<Percent> ftp=new ArrayList<Percent>();//ftp上下行单独查询
   
   public List<Percent> getFtp() {
	return ftp;
}
public void setFtp(List<Percent> ftp) {
	this.ftp = ftp;
}
public String getKpiname() {
	return kpiname;
}
public void setKpiname(String kpiname) {
	this.kpiname = kpiname;
}
public String getNetType() {
	return netType;
}
public List<String> getX() {
	return x;
}
public void setX(List<String> x) {
	this.x = x;
}
public List<Percent> getY() {
	return y;
}
public void setY(List<Percent> y) {
	this.y = y;
}
public void setNetType(String netType) {
	this.netType = netType;
}

public String getKpiId() {
	return kpiId;
}
public void setKpiId(String kpiId) {
	this.kpiId = kpiId;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
private String type;

public Double getMax() {
	return max;
}
public void setMax(Double max) {
	this.max = max;
}
public Double getMin() {
	return min;
}
public void setMin(Double min) {
	this.min = min;
}
public Double getAvg() {
	return avg;
}
public void setAvg(Double avg) {
	this.avg = avg;
}
public List<String> getIndoor_x() {
	return indoor_x;
}
public void setIndoor_x(List<String> indoor_x) {
	this.indoor_x = indoor_x;
}
public String getGradName() {
	return gradName;
}
public void setGradName(String gradName) {
	this.gradName = gradName;
}
public String getGradColor() {
	return gradColor;
}
public void setGradColor(String gradColor) {
	this.gradColor = gradColor;
}
}
