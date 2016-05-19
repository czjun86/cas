package com.complaint.action.vo;


/**
 * 

 * @ClassName: VoBean

 * @Description: 地图评价前台参数VO

 * @author: czj

 * @date: 2013-8-20 下午3:26:02
 */
public class VoBean {
  private String areaids;
  private String areatext;
  private String senceids;
  private String senctext;
  private String startTime;
  private String endTime;
  private String datatype;//1-测试时间，2－接单时间
  private String inside;//0－室外，1－室内
  private String nettype;//－1全部，1－WCDMA，2－GSM
  private String testnet;//测试网络
  private String testnetName;//测试网络名称
  private String testtype;
  private String testtypeText;
  private String grad;
  private String sernos;
  private String jobtype;//-1全部，1－投诉工单
  private String kpi;
  private String isFirst;//是否二次查询
  private String secendSerno;//二次查询工单号
  private Integer isDeal;
  private String testphone;
  private Integer verify;//审核状态
  private double minlat;
  private double maxlat;
  private double minlng;
  private double maxlng;
  //V1.01.10添加字段
  private Integer breakFlag;  //测试环节
  private String testAddr;	//测试地址
  private Integer userid;  //用户编号(判定区域权限)
  private String workerOrderNet;  //工单网络
  private String workerOrderNetName;  //工单网络



public Integer getVerify() {
	return verify;
}

public void setVerify(Integer verify) {
	this.verify = verify;
}

public double getMinlat() {
	return minlat;
}

public void setMinlat(double minlat) {
	this.minlat = minlat;
}

public double getMaxlat() {
	return maxlat;
}

public void setMaxlat(double maxlat) {
	this.maxlat = maxlat;
}

public double getMinlng() {
	return minlng;
}

public void setMinlng(double minlng) {
	this.minlng = minlng;
}

public double getMaxlng() {
	return maxlng;
}

public void setMaxlng(double maxlng) {
	this.maxlng = maxlng;
}

public String getAreatext() {
	return areatext;
}

public void setAreatext(String areatext) {
	this.areatext = areatext;
}

public String getAreaids() {
	return areaids;
}

public void setAreaids(String areaids) {
	this.areaids = areaids;
}

public String getSenceids() {
	return senceids;
}

public void setSenceids(String senceids) {
	this.senceids = senceids;
}

public String getSenctext() {
	return senctext;
}

public void setSenctext(String senctext) {
	this.senctext = senctext;
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



public String getInside() {
	return inside;
}

public void setInside(String inside) {
	this.inside = inside;
}

public String getNettype() {
	return nettype;
}

public void setNettype(String nettype) {
	this.nettype = nettype;
}

public String getTesttype() {
	return testtype;
}

public void setTesttype(String testtype) {
	this.testtype = testtype;
}

public String getTesttypeText() {
	return testtypeText;
}

public String getDatatype() {
	return datatype;
}

public void setDatatype(String datatype) {
	this.datatype = datatype;
}

public void setTesttypeText(String testtypeText) {
	this.testtypeText = testtypeText;
}

public String getGrad() {
	return grad;
}

public void setGrad(String grad) {
	this.grad = grad;
}

public String getSernos() {
	return sernos;
}

public void setSernos(String sernos) {
	this.sernos = sernos;
}

public String getJobtype() {
	return jobtype;
}

public void setJobtype(String jobtype) {
	this.jobtype = jobtype;
}

public String getTestnet() {
	return testnet;
}

public void setTestnet(String testnet) {
	this.testnet = testnet;
}

public String getTestnetName() {
	return testnetName;
}

public void setTestnetName(String testnetName) {
	this.testnetName = testnetName;
}

public String getKpi() {
	return kpi;
}

public void setKpi(String kpi) {
	this.kpi = kpi;
}

public String getIsFirst() {
	return isFirst;
}

public void setIsFirst(String isFirst) {
	this.isFirst = isFirst;
}

public String getSecendSerno() {
	return secendSerno;
}

public void setSecendSerno(String secendSerno) {
	this.secendSerno = secendSerno;
}

public Integer getIsDeal() {
	return isDeal;
}

public void setIsDeal(Integer isDeal) {
	this.isDeal = isDeal;
}

public String getTestphone() {
	return testphone;
}

public void setTestphone(String testphone) {
	this.testphone = testphone;
}

public Integer getBreakFlag() {
	return breakFlag;
}

public void setBreakFlag(Integer breakFlag) {
	this.breakFlag = breakFlag;
}

public String getTestAddr() {
	return testAddr;
}

public void setTestAddr(String testAddr) {
	this.testAddr = testAddr;
}

public Integer getUserid() {
	return userid;
}

public void setUserid(Integer userid) {
	this.userid = userid;
}

public String getWorkerOrderNet() {
	return workerOrderNet;
}

public void setWorkerOrderNet(String workerOrderNet) {
	this.workerOrderNet = workerOrderNet;
}

public String getWorkerOrderNetName() {
	return workerOrderNetName;
}

public void setWorkerOrderNetName(String workerOrderNetName) {
	this.workerOrderNetName = workerOrderNetName;
}

}
