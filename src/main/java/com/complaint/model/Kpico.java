package com.complaint.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class Kpico implements Serializable, JSONStreamAware {
	private String color;
	private String ci;
	private String mos;
	private String tp2;
	private String tp3;
	private String rscp;
	private String ecno;
	private String rxlev;
	private String rxqual;
	private String fu;
	private String fd;
	private String rsrp;
	private String rsrq;
	private String snr;
	private String ltefu;
	private String ltefd;
	
	
	private String ci_2;
	private String mos_2;
	private String tp2_2;
	private String tp3_2;
	private String rscp_2;
	private String ecno_2;
	private String rxlev_2;
	private String rxqual_2;
	private String fu_2;
	private String fd_2;
	private String rsrp_2;
	private String rsrq_2;
	private String snr_2;
	private String ltefu_2;
	private String ltefd_2;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getMos() {
		return mos;
	}

	public void setMos(String mos) {
		this.mos = mos;
	}

	public String getRscp() {
		return rscp;
	}

	public void setRscp(String rscp) {
		this.rscp = rscp;
	}

	public String getTp2() {
		return tp2;
	}

	public void setTp2(String tp2) {
		this.tp2 = tp2;
	}

	public String getTp3() {
		return tp3;
	}

	public void setTp3(String tp3) {
		this.tp3 = tp3;
	}

	public String getEcno() {
		return ecno;
	}

	public void setEcno(String ecno) {
		this.ecno = ecno;
	}

	public String getRxlev() {
		return rxlev;
	}

	public void setRxlev(String rxlev) {
		this.rxlev = rxlev;
	}

	public String getRxqual() {
		return rxqual;
	}

	public void setRxqual(String rxqual) {
		this.rxqual = rxqual;
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

	public String getCi_2() {
		return ci_2;
	}

	public void setCi_2(String ci_2) {
		this.ci_2 = ci_2;
	}

	public String getMos_2() {
		return mos_2;
	}

	public void setMos_2(String mos_2) {
		this.mos_2 = mos_2;
	}

	public String getTp2_2() {
		return tp2_2;
	}

	public void setTp2_2(String tp2_2) {
		this.tp2_2 = tp2_2;
	}

	public String getTp3_2() {
		return tp3_2;
	}

	public void setTp3_2(String tp3_2) {
		this.tp3_2 = tp3_2;
	}

	public String getRscp_2() {
		return rscp_2;
	}

	public void setRscp_2(String rscp_2) {
		this.rscp_2 = rscp_2;
	}

	public String getEcno_2() {
		return ecno_2;
	}

	public void setEcno_2(String ecno_2) {
		this.ecno_2 = ecno_2;
	}

	public String getRxlev_2() {
		return rxlev_2;
	}

	public void setRxlev_2(String rxlev_2) {
		this.rxlev_2 = rxlev_2;
	}

	public String getRxqual_2() {
		return rxqual_2;
	}

	public void setRxqual_2(String rxqual_2) {
		this.rxqual_2 = rxqual_2;
	}

	public String getFu_2() {
		return fu_2;
	}

	public void setFu_2(String fu_2) {
		this.fu_2 = fu_2;
	}

	public String getFd_2() {
		return fd_2;
	}

	public void setFd_2(String fd_2) {
		this.fd_2 = fd_2;
	}

	public String getRsrp() {
		return rsrp;
	}

	public void setRsrp(String rsrp) {
		this.rsrp = rsrp;
	}

	public String getRsrq() {
		return rsrq;
	}

	public void setRsrq(String rsrq) {
		this.rsrq = rsrq;
	}

	public String getSnr() {
		return snr;
	}

	public void setSnr(String snr) {
		this.snr = snr;
	}

	public String getLtefu() {
		return ltefu;
	}

	public void setLtefu(String ltefu) {
		this.ltefu = ltefu;
	}

	public String getLtefd() {
		return ltefd;
	}

	public void setLtefd(String ltefd) {
		this.ltefd = ltefd;
	}

	public String getRsrp_2() {
		return rsrp_2;
	}

	public void setRsrp_2(String rsrp_2) {
		this.rsrp_2 = rsrp_2;
	}

	public String getRsrq_2() {
		return rsrq_2;
	}

	public void setRsrq_2(String rsrq_2) {
		this.rsrq_2 = rsrq_2;
	}

	public String getSnr_2() {
		return snr_2;
	}

	public void setSnr_2(String snr_2) {
		this.snr_2 = snr_2;
	}

	public String getLtefu_2() {
		return ltefu_2;
	}

	public void setLtefu_2(String ltefu_2) {
		this.ltefu_2 = ltefu_2;
	}

	public String getLtefd_2() {
		return ltefd_2;
	}

	public void setLtefd_2(String ltefd_2) {
		this.ltefd_2 = ltefd_2;
	}

	@Override
	public void writeJSONString(Writer out) throws IOException {
		Map<String, Object> obj = new HashMap<String, Object>();
		Map<String, Object> obj2 = new HashMap<String, Object>();
		Map<String, Object> obj3 = new HashMap<String, Object>();
		Map<String, Object> obj4 = new HashMap<String, Object>();
		Map<String, Object> obj5 = new HashMap<String, Object>();
		Map<String, Object> obj6 = new HashMap<String, Object>();
		Map<String, Object> obj7 = new HashMap<String, Object>();
		obj2.put("ci", this.ci);
		obj2.put("mos", this.mos);
		obj2.put("tp", this.tp2);
		obj2.put("rl", this.rxlev);
		obj2.put("rq", this.rxqual);
		obj3.put("rscp", this.rscp);
		obj3.put("ecno", this.ecno);
		obj3.put("fu", this.fu);
		obj3.put("fd", this.fd);
		obj3.put("tp", this.tp3);
		obj4.put("ci", this.ci_2);
		obj4.put("mos", this.mos_2);
		obj4.put("tp", this.tp2_2);
		obj4.put("rl", this.rxlev_2);
		obj4.put("rq", this.rxqual_2);
		obj5.put("rscp", this.rscp_2);
		obj5.put("ecno", this.ecno_2);
		obj5.put("fu", this.fu_2);
		obj5.put("fd", this.fd_2);
		obj5.put("tp", this.tp3_2);
		
		obj6.put("rsr", this.rsrp);
		obj6.put("rrq", this.rsrq);
		obj6.put("snr", this.snr);
		obj6.put("fu", this.ltefu);
		obj6.put("fd", this.ltefd);
		
		obj7.put("rsr", this.rsrp_2);
		obj7.put("rrq", this.rsrq_2);
		obj7.put("snr", this.snr_2);
		obj7.put("fu", this.ltefu_2);
		obj7.put("fd", this.ltefd_2);
		
		obj.put("g2", obj2);
		obj.put("g3", obj3);
		obj.put("wg2", obj4);
		obj.put("wg3", obj5);
		obj.put("lt", obj6);
		obj.put("wlt", obj7);
		obj.put("co", this.color);
		JSONValue.writeJSONString(obj, out);
	}
}
