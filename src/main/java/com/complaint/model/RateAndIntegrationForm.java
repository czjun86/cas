package com.complaint.model;

public class RateAndIntegrationForm {
	private String[] id;
//	rxlev
	private String [] rxlev_arithmetic;
	private String [] rxlev_value;
	private String [] rxlev_ratio;
//	RxQua
	private String [] rxqual_arithmetic;
	private String [] rxqual_value;
	private String [] rxqual_ratio;
//	C/I
	private String [] ci_arithmetic;
	private String [] ci_value;
	private String [] ci_ratio;
//	RSCP
	private String [] rscp_arithmetic;
	private String [] rscp_value;
	private String [] rscp_ratio;
//	ecno
	private String [] ecno_arithmetic;
	private String [] ecno_value;
	private String [] ecno_ratio;
//	TxPower
	private String [] txpower_arithmetic;
	private String [] txpower_value;
	private String [] txpower_ratio;
//	RSRP
	private String [] rsrp_arithmetic;
	private String [] rsrp_value;
	private String [] rsrp_ratio;
//	RSRQ
	private String [] rsrq_arithmetic;
	private String [] rsrq_value;
	private String [] rsrq_ratio;
//	SINR
	private String [] sinr_arithmetic;
	private String [] sinr_value;
	private String [] sinr_ratio;
//	ftpup
	private String [] ftpup_avg;
	private String [] ftpup_arithmetic;
	private String [] ftpup_value;
	private String [] ftpup_ratio;
//	ftpdown
	private String [] ftpdown_avg;
	private String [] ftpdown_arithmetic;
	private String [] ftpdown_value;
	private String [] ftpdown_ratio;
//	ftpup4G
	private String [] ftpup4G_avg;
	private String [] ftpup4G_arithmetic;
	private String [] ftpup4G_value;
	private String [] ftpup4G_ratio;
//	ftpdown4G
	private String [] ftpdown4G_avg;
	private String [] ftpdown4G_arithmetic;
	private String [] ftpdown4G_value;
	private String [] ftpdown4G_ratio;
	private int [] rank_score;
//	按+ , , -分为ABC
	private int [] evaluate_scoreA;
	private int [] evaluate_scoreB;
	private int [] evaluate_scoreC;
	private int [] revis_left;
	private int [] revis_right;
	private int [] revis_level;
	
	private String [] colors;

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public String[] getRxlev_arithmetic() {
		return rxlev_arithmetic;
	}

	public void setRxlev_arithmetic(String[] rxlev_arithmetic) {
		this.rxlev_arithmetic = rxlev_arithmetic;
	}

	public String[] getRxlev_value() {
		return rxlev_value;
	}

	public void setRxlev_value(String[] rxlev_value) {
		this.rxlev_value = rxlev_value;
	}

	public String[] getRxlev_ratio() {
		return rxlev_ratio;
	}

	public void setRxlev_ratio(String[] rxlev_ratio) {
		this.rxlev_ratio = rxlev_ratio;
	}

	public String[] getRxqual_arithmetic() {
		return rxqual_arithmetic;
	}

	public void setRxqual_arithmetic(String[] rxqual_arithmetic) {
		this.rxqual_arithmetic = rxqual_arithmetic;
	}

	public String[] getRxqual_value() {
		return rxqual_value;
	}

	public void setRxqual_value(String[] rxqual_value) {
		this.rxqual_value = rxqual_value;
	}

	public String[] getRxqual_ratio() {
		return rxqual_ratio;
	}

	public void setRxqual_ratio(String[] rxqual_ratio) {
		this.rxqual_ratio = rxqual_ratio;
	}

	public String[] getCi_arithmetic() {
		return ci_arithmetic;
	}

	public void setCi_arithmetic(String[] ci_arithmetic) {
		this.ci_arithmetic = ci_arithmetic;
	}

	public String[] getCi_value() {
		return ci_value;
	}

	public void setCi_value(String[] ci_value) {
		this.ci_value = ci_value;
	}

	public String[] getCi_ratio() {
		return ci_ratio;
	}

	public void setCi_ratio(String[] ci_ratio) {
		this.ci_ratio = ci_ratio;
	}

	public String[] getRscp_arithmetic() {
		return rscp_arithmetic;
	}

	public void setRscp_arithmetic(String[] rscp_arithmetic) {
		this.rscp_arithmetic = rscp_arithmetic;
	}

	public String[] getRscp_value() {
		return rscp_value;
	}

	public void setRscp_value(String[] rscp_value) {
		this.rscp_value = rscp_value;
	}

	public String[] getRscp_ratio() {
		return rscp_ratio;
	}

	public void setRscp_ratio(String[] rscp_ratio) {
		this.rscp_ratio = rscp_ratio;
	}

	public String[] getEcno_arithmetic() {
		return ecno_arithmetic;
	}

	public void setEcno_arithmetic(String[] ecno_arithmetic) {
		this.ecno_arithmetic = ecno_arithmetic;
	}

	public String[] getEcno_value() {
		return ecno_value;
	}

	public void setEcno_value(String[] ecno_value) {
		this.ecno_value = ecno_value;
	}

	public String[] getEcno_ratio() {
		return ecno_ratio;
	}

	public void setEcno_ratio(String[] ecno_ratio) {
		this.ecno_ratio = ecno_ratio;
	}

	public String[] getTxpower_arithmetic() {
		return txpower_arithmetic;
	}

	public void setTxpower_arithmetic(String[] txpower_arithmetic) {
		this.txpower_arithmetic = txpower_arithmetic;
	}

	public String[] getTxpower_value() {
		return txpower_value;
	}

	public void setTxpower_value(String[] txpower_value) {
		this.txpower_value = txpower_value;
	}

	public String[] getTxpower_ratio() {
		return txpower_ratio;
	}

	public void setTxpower_ratio(String[] txpower_ratio) {
		this.txpower_ratio = txpower_ratio;
	}

	public String[] getFtpup_avg() {
		return ftpup_avg;
	}

	public void setFtpup_avg(String[] ftpup_avg) {
		this.ftpup_avg = ftpup_avg;
	}

	public String[] getFtpup_arithmetic() {
		return ftpup_arithmetic;
	}

	public void setFtpup_arithmetic(String[] ftpup_arithmetic) {
		this.ftpup_arithmetic = ftpup_arithmetic;
	}

	public String[] getFtpup_value() {
		return ftpup_value;
	}

	public void setFtpup_value(String[] ftpup_value) {
		this.ftpup_value = ftpup_value;
	}

	public String[] getFtpup_ratio() {
		return ftpup_ratio;
	}

	public void setFtpup_ratio(String[] ftpup_ratio) {
		this.ftpup_ratio = ftpup_ratio;
	}

	public String[] getFtpdown_avg() {
		return ftpdown_avg;
	}

	public void setFtpdown_avg(String[] ftpdown_avg) {
		this.ftpdown_avg = ftpdown_avg;
	}

	public String[] getFtpdown_arithmetic() {
		return ftpdown_arithmetic;
	}

	public void setFtpdown_arithmetic(String[] ftpdown_arithmetic) {
		this.ftpdown_arithmetic = ftpdown_arithmetic;
	}

	public String[] getFtpdown_value() {
		return ftpdown_value;
	}

	public void setFtpdown_value(String[] ftpdown_value) {
		this.ftpdown_value = ftpdown_value;
	}

	public String[] getFtpdown_ratio() {
		return ftpdown_ratio;
	}

	public void setFtpdown_ratio(String[] ftpdown_ratio) {
		this.ftpdown_ratio = ftpdown_ratio;
	}

	public int[] getRank_score() {
		return rank_score;
	}

	public void setRank_score(int[] rank_score) {
		this.rank_score = rank_score;
	}

	public int[] getEvaluate_scoreA() {
		return evaluate_scoreA;
	}

	public void setEvaluate_scoreA(int[] evaluate_scoreA) {
		this.evaluate_scoreA = evaluate_scoreA;
	}

	public int[] getEvaluate_scoreB() {
		return evaluate_scoreB;
	}

	public void setEvaluate_scoreB(int[] evaluate_scoreB) {
		this.evaluate_scoreB = evaluate_scoreB;
	}

	public int[] getEvaluate_scoreC() {
		return evaluate_scoreC;
	}

	public void setEvaluate_scoreC(int[] evaluate_scoreC) {
		this.evaluate_scoreC = evaluate_scoreC;
	}

	public int[] getRevis_left() {
		return revis_left;
	}

	public void setRevis_left(int[] revis_left) {
		this.revis_left = revis_left;
	}

	public int[] getRevis_right() {
		return revis_right;
	}

	public void setRevis_right(int[] revis_right) {
		this.revis_right = revis_right;
	}

	public int[] getRevis_level() {
		return revis_level;
	}

	public void setRevis_level(int[] revis_level) {
		this.revis_level = revis_level;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public String[] getRsrp_arithmetic() {
		return rsrp_arithmetic;
	}

	public void setRsrp_arithmetic(String[] rsrp_arithmetic) {
		this.rsrp_arithmetic = rsrp_arithmetic;
	}

	public String[] getRsrp_value() {
		return rsrp_value;
	}

	public void setRsrp_value(String[] rsrp_value) {
		this.rsrp_value = rsrp_value;
	}

	public String[] getRsrp_ratio() {
		return rsrp_ratio;
	}

	public void setRsrp_ratio(String[] rsrp_ratio) {
		this.rsrp_ratio = rsrp_ratio;
	}

	public String[] getRsrq_arithmetic() {
		return rsrq_arithmetic;
	}

	public void setRsrq_arithmetic(String[] rsrq_arithmetic) {
		this.rsrq_arithmetic = rsrq_arithmetic;
	}

	public String[] getRsrq_value() {
		return rsrq_value;
	}

	public void setRsrq_value(String[] rsrq_value) {
		this.rsrq_value = rsrq_value;
	}

	public String[] getRsrq_ratio() {
		return rsrq_ratio;
	}

	public void setRsrq_ratio(String[] rsrq_ratio) {
		this.rsrq_ratio = rsrq_ratio;
	}

	public String[] getSinr_arithmetic() {
		return sinr_arithmetic;
	}

	public void setSinr_arithmetic(String[] sinr_arithmetic) {
		this.sinr_arithmetic = sinr_arithmetic;
	}

	public String[] getSinr_value() {
		return sinr_value;
	}

	public void setSinr_value(String[] sinr_value) {
		this.sinr_value = sinr_value;
	}

	public String[] getSinr_ratio() {
		return sinr_ratio;
	}

	public void setSinr_ratio(String[] sinr_ratio) {
		this.sinr_ratio = sinr_ratio;
	}

	public String[] getFtpup4G_avg() {
		return ftpup4G_avg;
	}

	public void setFtpup4G_avg(String[] ftpup4g_avg) {
		ftpup4G_avg = ftpup4g_avg;
	}

	public String[] getFtpup4G_arithmetic() {
		return ftpup4G_arithmetic;
	}

	public void setFtpup4G_arithmetic(String[] ftpup4g_arithmetic) {
		ftpup4G_arithmetic = ftpup4g_arithmetic;
	}

	public String[] getFtpup4G_value() {
		return ftpup4G_value;
	}

	public void setFtpup4G_value(String[] ftpup4g_value) {
		ftpup4G_value = ftpup4g_value;
	}

	public String[] getFtpup4G_ratio() {
		return ftpup4G_ratio;
	}

	public void setFtpup4G_ratio(String[] ftpup4g_ratio) {
		ftpup4G_ratio = ftpup4g_ratio;
	}

	public String[] getFtpdown4G_avg() {
		return ftpdown4G_avg;
	}

	public void setFtpdown4G_avg(String[] ftpdown4g_avg) {
		ftpdown4G_avg = ftpdown4g_avg;
	}

	public String[] getFtpdown4G_arithmetic() {
		return ftpdown4G_arithmetic;
	}

	public void setFtpdown4G_arithmetic(String[] ftpdown4g_arithmetic) {
		ftpdown4G_arithmetic = ftpdown4g_arithmetic;
	}

	public String[] getFtpdown4G_value() {
		return ftpdown4G_value;
	}

	public void setFtpdown4G_value(String[] ftpdown4g_value) {
		ftpdown4G_value = ftpdown4g_value;
	}

	public String[] getFtpdown4G_ratio() {
		return ftpdown4G_ratio;
	}

	public void setFtpdown4G_ratio(String[] ftpdown4g_ratio) {
		ftpdown4G_ratio = ftpdown4g_ratio;
	}
	
}
