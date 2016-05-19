package com.complaint.model;

import java.io.Serializable;

public class Kpi implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7192177231046344173L;
	private KpiBean txPowerfor2g;
	private KpiBean rxlev;
	private KpiBean rxQual;
	private KpiBean ci;
	private KpiBean mos;
	private KpiBean rscp;
	private KpiBean ecno;
	private KpiBean txPowerfor3g;
	private KpiBean rsrp;
	private KpiBean rsrq;
	private KpiBean sinr;
	private KpiBean ftpUp;//上行
	private KpiBean ftpDown;//下行
	private KpiBean pingLoss;//ping丢包率
	private KpiBean pingMaxDelay;//ping最大延迟
	private KpiBean pingMinDelay;//ping最小延迟
	private KpiBean pingAvgDelay;//ping平均延迟
	private KpiBean httpMaxResponseTime;//HTTP最大响应时间
	private KpiBean httpMinResponseTime;//HTTP最小响应时间
	private KpiBean httpAvgResponseTime;//HTTP平均响应时间
	private KpiBean httpMaxDownloadSpeed;//HTTP最大下载速度
	private KpiBean httpMinDownloadSpeed;//HTTP最小下载速度
	private KpiBean httpAvgDownloadSpeed;//HTTP平均下载速度
	//4G数据指标
	private KpiBean ftpUp4G;//上行
	private KpiBean ftpDown4G;//下行
	private KpiBean pingLoss4G;//ping丢包率
	private KpiBean pingMaxDelay4G;//ping最大延迟
	private KpiBean pingMinDelay4G;//ping最小延迟
	private KpiBean pingAvgDelay4G;//ping平均延迟
	private KpiBean httpMaxResponseTime4G;//HTTP最大响应时间
	private KpiBean httpMinResponseTime4G;//HTTP最小响应时间
	private KpiBean httpAvgResponseTime4G;//HTTP平均响应时间
	private KpiBean httpMaxDownloadSpeed4G;//HTTP最大下载速度
	private KpiBean httpMinDownloadSpeed4G;//HTTP最小下载速度
	private KpiBean httpAvgDownloadSpeed4G;//HTTP平均下载速度
	public KpiBean getTxPowerfor2g() {
		return txPowerfor2g;
	}
	public void setTxPowerfor2g(KpiBean txPowerfor2g) {
		this.txPowerfor2g = txPowerfor2g;
	}
	public KpiBean getRxlev() {
		return rxlev;
	}
	public void setRxlev(KpiBean rxlev) {
		this.rxlev = rxlev;
	}
	public KpiBean getRxQual() {
		return rxQual;
	}
	public void setRxQual(KpiBean rxQual) {
		this.rxQual = rxQual;
	}
	public KpiBean getCi() {
		return ci;
	}
	public void setCi(KpiBean ci) {
		this.ci = ci;
	}
	public KpiBean getMos() {
		return mos;
	}
	public void setMos(KpiBean mos) {
		this.mos = mos;
	}
	public KpiBean getRscp() {
		return rscp;
	}
	public void setRscp(KpiBean rscp) {
		this.rscp = rscp;
	}
	public KpiBean getEcno() {
		return ecno;
	}
	public void setEcno(KpiBean ecno) {
		this.ecno = ecno;
	}
	public KpiBean getTxPowerfor3g() {
		return txPowerfor3g;
	}
	public void setTxPowerfor3g(KpiBean txPowerfor3g) {
		this.txPowerfor3g = txPowerfor3g;
	}
	public KpiBean getFtpUp() {
		return ftpUp;
	}
	public void setFtpUp(KpiBean ftpUp) {
		this.ftpUp = ftpUp;
	}
	public KpiBean getFtpDown() {
		return ftpDown;
	}
	public void setFtpDown(KpiBean ftpDown) {
		this.ftpDown = ftpDown;
	}
	public KpiBean getPingLoss() {
		return pingLoss;
	}
	public void setPingLoss(KpiBean pingLoss) {
		this.pingLoss = pingLoss;
	}
	public KpiBean getPingMaxDelay() {
		return pingMaxDelay;
	}
	public void setPingMaxDelay(KpiBean pingMaxDelay) {
		this.pingMaxDelay = pingMaxDelay;
	}
	public KpiBean getPingMinDelay() {
		return pingMinDelay;
	}
	public void setPingMinDelay(KpiBean pingMinDelay) {
		this.pingMinDelay = pingMinDelay;
	}
	public KpiBean getPingAvgDelay() {
		return pingAvgDelay;
	}
	public void setPingAvgDelay(KpiBean pingAvgDelay) {
		this.pingAvgDelay = pingAvgDelay;
	}
	public KpiBean getHttpMaxResponseTime() {
		return httpMaxResponseTime;
	}
	public void setHttpMaxResponseTime(KpiBean httpMaxResponseTime) {
		this.httpMaxResponseTime = httpMaxResponseTime;
	}
	public KpiBean getHttpMinResponseTime() {
		return httpMinResponseTime;
	}
	public void setHttpMinResponseTime(KpiBean httpMinResponseTime) {
		this.httpMinResponseTime = httpMinResponseTime;
	}
	public KpiBean getHttpAvgResponseTime() {
		return httpAvgResponseTime;
	}
	public void setHttpAvgResponseTime(KpiBean httpAvgResponseTime) {
		this.httpAvgResponseTime = httpAvgResponseTime;
	}
	public KpiBean getHttpMaxDownloadSpeed() {
		return httpMaxDownloadSpeed;
	}
	public void setHttpMaxDownloadSpeed(KpiBean httpMaxDownloadSpeed) {
		this.httpMaxDownloadSpeed = httpMaxDownloadSpeed;
	}
	public KpiBean getHttpMinDownloadSpeed() {
		return httpMinDownloadSpeed;
	}
	public void setHttpMinDownloadSpeed(KpiBean httpMinDownloadSpeed) {
		this.httpMinDownloadSpeed = httpMinDownloadSpeed;
	}
	public KpiBean getHttpAvgDownloadSpeed() {
		return httpAvgDownloadSpeed;
	}
	public void setHttpAvgDownloadSpeed(KpiBean httpAvgDownloadSpeed) {
		this.httpAvgDownloadSpeed = httpAvgDownloadSpeed;
	}
	public KpiBean getRsrp() {
		return rsrp;
	}
	public void setRsrp(KpiBean rsrp) {
		this.rsrp = rsrp;
	}
	public KpiBean getRsrq() {
		return rsrq;
	}
	public void setRsrq(KpiBean rsrq) {
		this.rsrq = rsrq;
	}
	public KpiBean getSinr() {
		return sinr;
	}
	public void setSinr(KpiBean sinr) {
		this.sinr = sinr;
	}
	public KpiBean getFtpUp4G() {
		return ftpUp4G;
	}
	public void setFtpUp4G(KpiBean ftpUp4G) {
		this.ftpUp4G = ftpUp4G;
	}
	public KpiBean getFtpDown4G() {
		return ftpDown4G;
	}
	public void setFtpDown4G(KpiBean ftpDown4G) {
		this.ftpDown4G = ftpDown4G;
	}
	public KpiBean getPingLoss4G() {
		return pingLoss4G;
	}
	public void setPingLoss4G(KpiBean pingLoss4G) {
		this.pingLoss4G = pingLoss4G;
	}
	public KpiBean getPingMaxDelay4G() {
		return pingMaxDelay4G;
	}
	public void setPingMaxDelay4G(KpiBean pingMaxDelay4G) {
		this.pingMaxDelay4G = pingMaxDelay4G;
	}
	public KpiBean getPingMinDelay4G() {
		return pingMinDelay4G;
	}
	public void setPingMinDelay4G(KpiBean pingMinDelay4G) {
		this.pingMinDelay4G = pingMinDelay4G;
	}
	public KpiBean getPingAvgDelay4G() {
		return pingAvgDelay4G;
	}
	public void setPingAvgDelay4G(KpiBean pingAvgDelay4G) {
		this.pingAvgDelay4G = pingAvgDelay4G;
	}
	public KpiBean getHttpMaxResponseTime4G() {
		return httpMaxResponseTime4G;
	}
	public void setHttpMaxResponseTime4G(KpiBean httpMaxResponseTime4G) {
		this.httpMaxResponseTime4G = httpMaxResponseTime4G;
	}
	public KpiBean getHttpMinResponseTime4G() {
		return httpMinResponseTime4G;
	}
	public void setHttpMinResponseTime4G(KpiBean httpMinResponseTime4G) {
		this.httpMinResponseTime4G = httpMinResponseTime4G;
	}
	public KpiBean getHttpAvgResponseTime4G() {
		return httpAvgResponseTime4G;
	}
	public void setHttpAvgResponseTime4G(KpiBean httpAvgResponseTime4G) {
		this.httpAvgResponseTime4G = httpAvgResponseTime4G;
	}
	public KpiBean getHttpMaxDownloadSpeed4G() {
		return httpMaxDownloadSpeed4G;
	}
	public void setHttpMaxDownloadSpeed4G(KpiBean httpMaxDownloadSpeed4G) {
		this.httpMaxDownloadSpeed4G = httpMaxDownloadSpeed4G;
	}
	public KpiBean getHttpMinDownloadSpeed4G() {
		return httpMinDownloadSpeed4G;
	}
	public void setHttpMinDownloadSpeed4G(KpiBean httpMinDownloadSpeed4G) {
		this.httpMinDownloadSpeed4G = httpMinDownloadSpeed4G;
	}
	public KpiBean getHttpAvgDownloadSpeed4G() {
		return httpAvgDownloadSpeed4G;
	}
	public void setHttpAvgDownloadSpeed4G(KpiBean httpAvgDownloadSpeed4G) {
		this.httpAvgDownloadSpeed4G = httpAvgDownloadSpeed4G;
	}
	
	
}
