package com.complaint.model;

import java.io.Serializable;

public class KpiForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7052677059325263290L;
//	室外属性
//	private String[] txPowerfor2g;
	private String[] rxlev;
	private String[] rxQual;
	private String[] ci;
//	private String[] mos;
	private String[] rscp;
	private String[] ecno;
	private String[] txPowerfor3g;
	private String[] rsrp;
	private String[] rsrq;
	private String[] sinr;
	private String[] ftpUp;
	private String[] ftpDown;
	private String[] pingLoss;
	private String[] pingMaxDelay;
	private String[] pingMinDelay;
	private String[] pingAvgDelay;
	private String[] httpMaxResponseTime;
	private String[] httpMinResponseTime;
	private String[] httpAvgResponseTime;
	private String[] httpMaxDownloadSpeed;
	private String[] httpMinDownloadSpeed;
	private String[] httpAvgDownloadSpeed;
	//4G数据
	private String[] ftpUp4G;
	private String[] ftpDown4G;
	private String[] pingLoss4G;
	private String[] pingMaxDelay4G;
	private String[] pingMinDelay4G;
	private String[] pingAvgDelay4G;
	private String[] httpMaxResponseTime4G;
	private String[] httpMinResponseTime4G;
	private String[] httpAvgResponseTime4G;
	private String[] httpMaxDownloadSpeed4G;
	private String[] httpMinDownloadSpeed4G;
	private String[] httpAvgDownloadSpeed4G;
//	室内属性
//	private String[] txPowerfor2gInside;
	private String[] rxlevInside;
	private String[] rxQualInside;
	private String[] ciInside;
//	private String[] mosInside;
	private String[] rscpInside;
	private String[] ecnoInside;
	private String[] txPowerfor3gInside;
	private String[] rsrpInside;
	private String[] rsrqInside;
	private String[] sinrInside;
	private String[] ftpUpInside;
	private String[] ftpDownInside;
	private String[] pingLossInside;
	private String[] pingMaxDelayInside;
	private String[] pingMinDelayInside;
	private String[] pingAvgDelayInside;
	private String[] httpMaxResponseTimeInside;
	private String[] httpMinResponseTimeInside;
	private String[] httpAvgResponseTimeInside;
	private String[] httpMaxDownloadSpeedInside;
	private String[] httpMinDownloadSpeedInside;
	private String[] httpAvgDownloadSpeedInside;
	//4G数据
	private String[] ftpUpInside4G;
	private String[] ftpDownInside4G;
	private String[] pingLossInside4G;
	private String[] pingMaxDelayInside4G;
	private String[] pingMinDelayInside4G;
	private String[] pingAvgDelayInside4G;
	private String[] httpMaxResponseTimeInside4G;
	private String[] httpMinResponseTimeInside4G;
	private String[] httpAvgResponseTimeInside4G;
	private String[] httpMaxDownloadSpeedInside4G;
	private String[] httpMinDownloadSpeedInside4G;
	private String[] httpAvgDownloadSpeedInside4G;
	
	
	public String[] getRxlevInside() {
		return rxlevInside;
	}
	public void setRxlevInside(String[] rxlevInside) {
		this.rxlevInside = rxlevInside;
	}
	public String[] getRxQualInside() {
		return rxQualInside;
	}
	public void setRxQualInside(String[] rxQualInside) {
		this.rxQualInside = rxQualInside;
	}
	
	public String[] getRscpInside() {
		return rscpInside;
	}
	public void setRscpInside(String[] rscpInside) {
		this.rscpInside = rscpInside;
	}
	public String[] getEcnoInside() {
		return ecnoInside;
	}
	public void setEcnoInside(String[] ecnoInside) {
		this.ecnoInside = ecnoInside;
	}
	public String[] getTxPowerfor3gInside() {
		return txPowerfor3gInside;
	}
	public void setTxPowerfor3gInside(String[] txPowerfor3gInside) {
		this.txPowerfor3gInside = txPowerfor3gInside;
	}
	public String[] getFtpUpInside() {
		return ftpUpInside;
	}
	public void setFtpUpInside(String[] ftpUpInside) {
		this.ftpUpInside = ftpUpInside;
	}
	public String[] getFtpDownInside() {
		return ftpDownInside;
	}
	public void setFtpDownInside(String[] ftpDownInside) {
		this.ftpDownInside = ftpDownInside;
	}
	public String[] getPingLossInside() {
		return pingLossInside;
	}
	public void setPingLossInside(String[] pingLossInside) {
		this.pingLossInside = pingLossInside;
	}
	public String[] getPingMaxDelayInside() {
		return pingMaxDelayInside;
	}
	public void setPingMaxDelayInside(String[] pingMaxDelayInside) {
		this.pingMaxDelayInside = pingMaxDelayInside;
	}
	public String[] getPingMinDelayInside() {
		return pingMinDelayInside;
	}
	public void setPingMinDelayInside(String[] pingMinDelayInside) {
		this.pingMinDelayInside = pingMinDelayInside;
	}
	public String[] getPingAvgDelayInside() {
		return pingAvgDelayInside;
	}
	public void setPingAvgDelayInside(String[] pingAvgDelayInside) {
		this.pingAvgDelayInside = pingAvgDelayInside;
	}
	public String[] getHttpMaxResponseTimeInside() {
		return httpMaxResponseTimeInside;
	}
	public void setHttpMaxResponseTimeInside(String[] httpMaxResponseTimeInside) {
		this.httpMaxResponseTimeInside = httpMaxResponseTimeInside;
	}
	public String[] getHttpMinResponseTimeInside() {
		return httpMinResponseTimeInside;
	}
	public void setHttpMinResponseTimeInside(String[] httpMinResponseTimeInside) {
		this.httpMinResponseTimeInside = httpMinResponseTimeInside;
	}
	public String[] getHttpAvgResponseTimeInside() {
		return httpAvgResponseTimeInside;
	}
	public void setHttpAvgResponseTimeInside(String[] httpAvgResponseTimeInside) {
		this.httpAvgResponseTimeInside = httpAvgResponseTimeInside;
	}
	public String[] getHttpMaxDownloadSpeedInside() {
		return httpMaxDownloadSpeedInside;
	}
	public void setHttpMaxDownloadSpeedInside(String[] httpMaxDownloadSpeedInside) {
		this.httpMaxDownloadSpeedInside = httpMaxDownloadSpeedInside;
	}
	public String[] getHttpMinDownloadSpeedInside() {
		return httpMinDownloadSpeedInside;
	}
	public void setHttpMinDownloadSpeedInside(String[] httpMinDownloadSpeedInside) {
		this.httpMinDownloadSpeedInside = httpMinDownloadSpeedInside;
	}
	public String[] getHttpAvgDownloadSpeedInside() {
		return httpAvgDownloadSpeedInside;
	}
	public void setHttpAvgDownloadSpeedInside(String[] httpAvgDownloadSpeedInside) {
		this.httpAvgDownloadSpeedInside = httpAvgDownloadSpeedInside;
	}
	public String[] getRxlev() {
		return rxlev;
	}
	public void setRxlev(String[] rxlev) {
		this.rxlev = rxlev;
	}
	public String[] getRxQual() {
		return rxQual;
	}
	public void setRxQual(String[] rxQual) {
		this.rxQual = rxQual;
	}
	
	public String[] getRscp() {
		return rscp;
	}
	public void setRscp(String[] rscp) {
		this.rscp = rscp;
	}
	public String[] getEcno() {
		return ecno;
	}
	public void setEcno(String[] ecno) {
		this.ecno = ecno;
	}
	public String[] getTxPowerfor3g() {
		return txPowerfor3g;
	}
	public void setTxPowerfor3g(String[] txPowerfor3g) {
		this.txPowerfor3g = txPowerfor3g;
	}
	public String[] getFtpUp() {
		return ftpUp;
	}
	public void setFtpUp(String[] ftpUp) {
		this.ftpUp = ftpUp;
	}
	public String[] getFtpDown() {
		return ftpDown;
	}
	public void setFtpDown(String[] ftpDown) {
		this.ftpDown = ftpDown;
	}
	public String[] getPingLoss() {
		return pingLoss;
	}
	public void setPingLoss(String[] pingLoss) {
		this.pingLoss = pingLoss;
	}
	public String[] getPingMaxDelay() {
		return pingMaxDelay;
	}
	public void setPingMaxDelay(String[] pingMaxDelay) {
		this.pingMaxDelay = pingMaxDelay;
	}
	public String[] getPingMinDelay() {
		return pingMinDelay;
	}
	public void setPingMinDelay(String[] pingMinDelay) {
		this.pingMinDelay = pingMinDelay;
	}
	public String[] getPingAvgDelay() {
		return pingAvgDelay;
	}
	public void setPingAvgDelay(String[] pingAvgDelay) {
		this.pingAvgDelay = pingAvgDelay;
	}
	public String[] getHttpMaxResponseTime() {
		return httpMaxResponseTime;
	}
	public void setHttpMaxResponseTime(String[] httpMaxResponseTime) {
		this.httpMaxResponseTime = httpMaxResponseTime;
	}
	public String[] getHttpMinResponseTime() {
		return httpMinResponseTime;
	}
	public void setHttpMinResponseTime(String[] httpMinResponseTime) {
		this.httpMinResponseTime = httpMinResponseTime;
	}
	public String[] getHttpAvgResponseTime() {
		return httpAvgResponseTime;
	}
	public void setHttpAvgResponseTime(String[] httpAvgResponseTime) {
		this.httpAvgResponseTime = httpAvgResponseTime;
	}
	public String[] getHttpMaxDownloadSpeed() {
		return httpMaxDownloadSpeed;
	}
	public void setHttpMaxDownloadSpeed(String[] httpMaxDownloadSpeed) {
		this.httpMaxDownloadSpeed = httpMaxDownloadSpeed;
	}
	public String[] getHttpMinDownloadSpeed() {
		return httpMinDownloadSpeed;
	}
	public void setHttpMinDownloadSpeed(String[] httpMinDownloadSpeed) {
		this.httpMinDownloadSpeed = httpMinDownloadSpeed;
	}
	public String[] getHttpAvgDownloadSpeed() {
		return httpAvgDownloadSpeed;
	}
	public void setHttpAvgDownloadSpeed(String[] httpAvgDownloadSpeed) {
		this.httpAvgDownloadSpeed = httpAvgDownloadSpeed;
	}
	public String[] getCi() {
		return ci;
	}
	public void setCi(String[] ci) {
		this.ci = ci;
	}
	public String[] getCiInside() {
		return ciInside;
	}
	public void setCiInside(String[] ciInside) {
		this.ciInside = ciInside;
	}
	public String[] getRsrp() {
		return rsrp;
	}
	public void setRsrp(String[] rsrp) {
		this.rsrp = rsrp;
	}
	public String[] getRsrq() {
		return rsrq;
	}
	public void setRsrq(String[] rsrq) {
		this.rsrq = rsrq;
	}
	public String[] getSinr() {
		return sinr;
	}
	public void setSinr(String[] sinr) {
		this.sinr = sinr;
	}
	public String[] getRsrpInside() {
		return rsrpInside;
	}
	public void setRsrpInside(String[] rsrpInside) {
		this.rsrpInside = rsrpInside;
	}
	public String[] getRsrqInside() {
		return rsrqInside;
	}
	public void setRsrqInside(String[] rsrqInside) {
		this.rsrqInside = rsrqInside;
	}
	public String[] getSinrInside() {
		return sinrInside;
	}
	public void setSinrInside(String[] sinrInside) {
		this.sinrInside = sinrInside;
	}
	public String[] getFtpUpInside4G() {
		return ftpUpInside4G;
	}
	public void setFtpUpInside4G(String[] ftpUpInside4G) {
		this.ftpUpInside4G = ftpUpInside4G;
	}
	public String[] getFtpDownInside4G() {
		return ftpDownInside4G;
	}
	public void setFtpDownInside4G(String[] ftpDownInside4G) {
		this.ftpDownInside4G = ftpDownInside4G;
	}
	public String[] getPingLossInside4G() {
		return pingLossInside4G;
	}
	public void setPingLossInside4G(String[] pingLossInside4G) {
		this.pingLossInside4G = pingLossInside4G;
	}
	public String[] getPingMaxDelayInside4G() {
		return pingMaxDelayInside4G;
	}
	public void setPingMaxDelayInside4G(String[] pingMaxDelayInside4G) {
		this.pingMaxDelayInside4G = pingMaxDelayInside4G;
	}
	public String[] getPingMinDelayInside4G() {
		return pingMinDelayInside4G;
	}
	public void setPingMinDelayInside4G(String[] pingMinDelayInside4G) {
		this.pingMinDelayInside4G = pingMinDelayInside4G;
	}
	public String[] getPingAvgDelayInside4G() {
		return pingAvgDelayInside4G;
	}
	public void setPingAvgDelayInside4G(String[] pingAvgDelayInside4G) {
		this.pingAvgDelayInside4G = pingAvgDelayInside4G;
	}
	public String[] getHttpMaxResponseTimeInside4G() {
		return httpMaxResponseTimeInside4G;
	}
	public void setHttpMaxResponseTimeInside4G(String[] httpMaxResponseTimeInside4G) {
		this.httpMaxResponseTimeInside4G = httpMaxResponseTimeInside4G;
	}
	public String[] getHttpMinResponseTimeInside4G() {
		return httpMinResponseTimeInside4G;
	}
	public void setHttpMinResponseTimeInside4G(String[] httpMinResponseTimeInside4G) {
		this.httpMinResponseTimeInside4G = httpMinResponseTimeInside4G;
	}
	public String[] getHttpAvgResponseTimeInside4G() {
		return httpAvgResponseTimeInside4G;
	}
	public void setHttpAvgResponseTimeInside4G(String[] httpAvgResponseTimeInside4G) {
		this.httpAvgResponseTimeInside4G = httpAvgResponseTimeInside4G;
	}
	public String[] getHttpMaxDownloadSpeedInside4G() {
		return httpMaxDownloadSpeedInside4G;
	}
	public void setHttpMaxDownloadSpeedInside4G(String[] httpMaxDownloadSpeedInside4G) {
		this.httpMaxDownloadSpeedInside4G = httpMaxDownloadSpeedInside4G;
	}
	public String[] getHttpMinDownloadSpeedInside4G() {
		return httpMinDownloadSpeedInside4G;
	}
	public void setHttpMinDownloadSpeedInside4G(String[] httpMinDownloadSpeedInside4G) {
		this.httpMinDownloadSpeedInside4G = httpMinDownloadSpeedInside4G;
	}
	public String[] getHttpAvgDownloadSpeedInside4G() {
		return httpAvgDownloadSpeedInside4G;
	}
	public void setHttpAvgDownloadSpeedInside4G(String[] httpAvgDownloadSpeedInside4G) {
		this.httpAvgDownloadSpeedInside4G = httpAvgDownloadSpeedInside4G;
	}
	public String[] getFtpUp4G() {
		return ftpUp4G;
	}
	public void setFtpUp4G(String[] ftpUp4G) {
		this.ftpUp4G = ftpUp4G;
	}
	public String[] getFtpDown4G() {
		return ftpDown4G;
	}
	public void setFtpDown4G(String[] ftpDown4G) {
		this.ftpDown4G = ftpDown4G;
	}
	public String[] getPingLoss4G() {
		return pingLoss4G;
	}
	public void setPingLoss4G(String[] pingLoss4G) {
		this.pingLoss4G = pingLoss4G;
	}
	public String[] getPingMaxDelay4G() {
		return pingMaxDelay4G;
	}
	public void setPingMaxDelay4G(String[] pingMaxDelay4G) {
		this.pingMaxDelay4G = pingMaxDelay4G;
	}
	public String[] getPingMinDelay4G() {
		return pingMinDelay4G;
	}
	public void setPingMinDelay4G(String[] pingMinDelay4G) {
		this.pingMinDelay4G = pingMinDelay4G;
	}
	public String[] getPingAvgDelay4G() {
		return pingAvgDelay4G;
	}
	public void setPingAvgDelay4G(String[] pingAvgDelay4G) {
		this.pingAvgDelay4G = pingAvgDelay4G;
	}
	public String[] getHttpMaxResponseTime4G() {
		return httpMaxResponseTime4G;
	}
	public void setHttpMaxResponseTime4G(String[] httpMaxResponseTime4G) {
		this.httpMaxResponseTime4G = httpMaxResponseTime4G;
	}
	public String[] getHttpMinResponseTime4G() {
		return httpMinResponseTime4G;
	}
	public void setHttpMinResponseTime4G(String[] httpMinResponseTime4G) {
		this.httpMinResponseTime4G = httpMinResponseTime4G;
	}
	public String[] getHttpAvgResponseTime4G() {
		return httpAvgResponseTime4G;
	}
	public void setHttpAvgResponseTime4G(String[] httpAvgResponseTime4G) {
		this.httpAvgResponseTime4G = httpAvgResponseTime4G;
	}
	public String[] getHttpMaxDownloadSpeed4G() {
		return httpMaxDownloadSpeed4G;
	}
	public void setHttpMaxDownloadSpeed4G(String[] httpMaxDownloadSpeed4G) {
		this.httpMaxDownloadSpeed4G = httpMaxDownloadSpeed4G;
	}
	public String[] getHttpMinDownloadSpeed4G() {
		return httpMinDownloadSpeed4G;
	}
	public void setHttpMinDownloadSpeed4G(String[] httpMinDownloadSpeed4G) {
		this.httpMinDownloadSpeed4G = httpMinDownloadSpeed4G;
	}
	public String[] getHttpAvgDownloadSpeed4G() {
		return httpAvgDownloadSpeed4G;
	}
	public void setHttpAvgDownloadSpeed4G(String[] httpAvgDownloadSpeed4G) {
		this.httpAvgDownloadSpeed4G = httpAvgDownloadSpeed4G;
	}
	
}
