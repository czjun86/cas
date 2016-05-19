package com.complaint.model;

public class ComplainEvaluate {
	// 区域id
	private Integer[] area_id;
	// rscp优良中差对应abcd
	private Integer[] rscp_a;
	private Integer[] rscp_b;
	private Integer[] rscp_c;
	private Integer[] rscp_d;
	// ecno优良中差对应abcd
	private Integer[] ec_no_a;
	private Integer[] ec_no_b;
	private Integer[] ec_no_c;
	private Integer[] ec_no_d;
	// txpower优良中差对应abcd
	private Integer[] txpower_a;
	private Integer[] txpower_b;
	private Integer[] txpower_c;
	private Integer[] txpower_d;
	// ftp上行优良中差对应abcd
	private Integer[] ftp_up_a;
	private Integer[] ftp_up_b;
	private Integer[] ftp_up_c;
	private Integer[] ftp_up_d;
	// ftp下行优良中差对应abcd
	private Integer[] ftp_down_a;
	private Integer[] ftp_down_b;
	private Integer[] ftp_down_c;
	private Integer[] ftp_down_d;
	// rxlev优良中差对应abcd
	private Integer[] rxlev_a;
	private Integer[] rxlev_b;
	private Integer[] rxlev_c;
	private Integer[] rxlev_d;
	// qxqual优良中差对应abcd
	private Integer[] rxqual_a;
	private Integer[] rxqual_b;
	private Integer[] rxqual_c;
	private Integer[] rxqual_d;
	// C/I优良中差对应abcd
	private Integer[] ci_a;
	private Integer[] ci_b;
	private Integer[] ci_c;
	private Integer[] ci_d;
	// 综合评价优良中差对应abcd ,3g/2g
	private Integer[] comp_eval_3g_a;
	private Integer[] comp_eval_3g_b;
	private Integer[] comp_eval_3g_c;
	private Integer[] comp_eval_3g_d;

	private Integer[] comp_eval_2g_a;
	private Integer[] comp_eval_2g_b;
	private Integer[] comp_eval_2g_c;
	private Integer[] comp_eval_2g_d;
	// 综合改善优良中差对应abcd
	private Integer[] comp_impr_a;
	private Integer[] comp_impr_b;
	private Integer[] comp_impr_c;
	private Integer[] comp_impr_d;
	// 综合改善得分 3g/2g
	private Double[] comp_impr_value_3g;
	private Double[] comp_impr_value_2g;
	// 综合改善比例 3g/2g
	private String[] comp_impr_ratio_3g;
	private String[] comp_impr_ratio_2g;
	// 及时率
	private String[] timely_rate;
	// 及时率排名
	private Integer[] timely_rate_rank;

	
	public String[] getComp_impr_ratio_3g() {
		return comp_impr_ratio_3g;
	}

	public void setComp_impr_ratio_3g(String[] comp_impr_ratio_3g) {
		this.comp_impr_ratio_3g = comp_impr_ratio_3g;
	}

	public String[] getComp_impr_ratio_2g() {
		return comp_impr_ratio_2g;
	}

	public void setComp_impr_ratio_2g(String[] comp_impr_ratio_2g) {
		this.comp_impr_ratio_2g = comp_impr_ratio_2g;
	}

	public Integer[] getCi_a() {
		return ci_a;
	}

	public void setCi_a(Integer[] ci_a) {
		this.ci_a = ci_a;
	}

	public Integer[] getCi_b() {
		return ci_b;
	}

	public void setCi_b(Integer[] ci_b) {
		this.ci_b = ci_b;
	}

	public Integer[] getCi_c() {
		return ci_c;
	}

	public void setCi_c(Integer[] ci_c) {
		this.ci_c = ci_c;
	}

	public Integer[] getCi_d() {
		return ci_d;
	}

	public void setCi_d(Integer[] ci_d) {
		this.ci_d = ci_d;
	}

	public Integer[] getArea_id() {
		return area_id;
	}

	public void setArea_id(Integer[] area_id) {
		this.area_id = area_id;
	}

	public Integer[] getRscp_a() {
		return rscp_a;
	}

	public void setRscp_a(Integer[] rscp_a) {
		this.rscp_a = rscp_a;
	}

	public Integer[] getRscp_b() {
		return rscp_b;
	}

	public void setRscp_b(Integer[] rscp_b) {
		this.rscp_b = rscp_b;
	}

	public Integer[] getRscp_c() {
		return rscp_c;
	}

	public void setRscp_c(Integer[] rscp_c) {
		this.rscp_c = rscp_c;
	}

	public Integer[] getRscp_d() {
		return rscp_d;
	}

	public void setRscp_d(Integer[] rscp_d) {
		this.rscp_d = rscp_d;
	}

	public Integer[] getEc_no_a() {
		return ec_no_a;
	}

	public void setEc_no_a(Integer[] ec_no_a) {
		this.ec_no_a = ec_no_a;
	}

	public Integer[] getEc_no_b() {
		return ec_no_b;
	}

	public void setEc_no_b(Integer[] ec_no_b) {
		this.ec_no_b = ec_no_b;
	}

	public Integer[] getEc_no_c() {
		return ec_no_c;
	}

	public void setEc_no_c(Integer[] ec_no_c) {
		this.ec_no_c = ec_no_c;
	}

	public Integer[] getEc_no_d() {
		return ec_no_d;
	}

	public void setEc_no_d(Integer[] ec_no_d) {
		this.ec_no_d = ec_no_d;
	}

	public Integer[] getTxpower_a() {
		return txpower_a;
	}

	public void setTxpower_a(Integer[] txpower_a) {
		this.txpower_a = txpower_a;
	}

	public Integer[] getTxpower_b() {
		return txpower_b;
	}

	public void setTxpower_b(Integer[] txpower_b) {
		this.txpower_b = txpower_b;
	}

	public Integer[] getTxpower_c() {
		return txpower_c;
	}

	public void setTxpower_c(Integer[] txpower_c) {
		this.txpower_c = txpower_c;
	}

	public Integer[] getTxpower_d() {
		return txpower_d;
	}

	public void setTxpower_d(Integer[] txpower_d) {
		this.txpower_d = txpower_d;
	}

	public Integer[] getFtp_up_a() {
		return ftp_up_a;
	}

	public void setFtp_up_a(Integer[] ftp_up_a) {
		this.ftp_up_a = ftp_up_a;
	}

	public Integer[] getFtp_up_b() {
		return ftp_up_b;
	}

	public void setFtp_up_b(Integer[] ftp_up_b) {
		this.ftp_up_b = ftp_up_b;
	}

	public Integer[] getFtp_up_c() {
		return ftp_up_c;
	}

	public void setFtp_up_c(Integer[] ftp_up_c) {
		this.ftp_up_c = ftp_up_c;
	}

	public Integer[] getFtp_up_d() {
		return ftp_up_d;
	}

	public void setFtp_up_d(Integer[] ftp_up_d) {
		this.ftp_up_d = ftp_up_d;
	}

	public Integer[] getFtp_down_a() {
		return ftp_down_a;
	}

	public void setFtp_down_a(Integer[] ftp_down_a) {
		this.ftp_down_a = ftp_down_a;
	}

	public Integer[] getFtp_down_b() {
		return ftp_down_b;
	}

	public void setFtp_down_b(Integer[] ftp_down_b) {
		this.ftp_down_b = ftp_down_b;
	}

	public Integer[] getFtp_down_c() {
		return ftp_down_c;
	}

	public void setFtp_down_c(Integer[] ftp_down_c) {
		this.ftp_down_c = ftp_down_c;
	}

	public Integer[] getFtp_down_d() {
		return ftp_down_d;
	}

	public void setFtp_down_d(Integer[] ftp_down_d) {
		this.ftp_down_d = ftp_down_d;
	}

	public Integer[] getRxlev_a() {
		return rxlev_a;
	}

	public void setRxlev_a(Integer[] rxlev_a) {
		this.rxlev_a = rxlev_a;
	}

	public Integer[] getRxlev_b() {
		return rxlev_b;
	}

	public void setRxlev_b(Integer[] rxlev_b) {
		this.rxlev_b = rxlev_b;
	}

	public Integer[] getRxlev_c() {
		return rxlev_c;
	}

	public void setRxlev_c(Integer[] rxlev_c) {
		this.rxlev_c = rxlev_c;
	}

	public Integer[] getRxlev_d() {
		return rxlev_d;
	}

	public void setRxlev_d(Integer[] rxlev_d) {
		this.rxlev_d = rxlev_d;
	}

	public Integer[] getRxqual_a() {
		return rxqual_a;
	}

	public void setRxqual_a(Integer[] rxqual_a) {
		this.rxqual_a = rxqual_a;
	}

	public Integer[] getRxqual_b() {
		return rxqual_b;
	}

	public void setRxqual_b(Integer[] rxqual_b) {
		this.rxqual_b = rxqual_b;
	}

	public Integer[] getRxqual_c() {
		return rxqual_c;
	}

	public void setRxqual_c(Integer[] rxqual_c) {
		this.rxqual_c = rxqual_c;
	}

	public Integer[] getRxqual_d() {
		return rxqual_d;
	}

	public void setRxqual_d(Integer[] rxqual_d) {
		this.rxqual_d = rxqual_d;
	}

	public Integer[] getComp_eval_3g_a() {
		return comp_eval_3g_a;
	}

	public void setComp_eval_3g_a(Integer[] comp_eval_3g_a) {
		this.comp_eval_3g_a = comp_eval_3g_a;
	}

	public Integer[] getComp_eval_3g_b() {
		return comp_eval_3g_b;
	}

	public void setComp_eval_3g_b(Integer[] comp_eval_3g_b) {
		this.comp_eval_3g_b = comp_eval_3g_b;
	}

	public Integer[] getComp_eval_3g_c() {
		return comp_eval_3g_c;
	}

	public void setComp_eval_3g_c(Integer[] comp_eval_3g_c) {
		this.comp_eval_3g_c = comp_eval_3g_c;
	}

	public Integer[] getComp_eval_3g_d() {
		return comp_eval_3g_d;
	}

	public void setComp_eval_3g_d(Integer[] comp_eval_3g_d) {
		this.comp_eval_3g_d = comp_eval_3g_d;
	}

	public Integer[] getComp_eval_2g_a() {
		return comp_eval_2g_a;
	}

	public void setComp_eval_2g_a(Integer[] comp_eval_2g_a) {
		this.comp_eval_2g_a = comp_eval_2g_a;
	}

	public Integer[] getComp_eval_2g_b() {
		return comp_eval_2g_b;
	}

	public void setComp_eval_2g_b(Integer[] comp_eval_2g_b) {
		this.comp_eval_2g_b = comp_eval_2g_b;
	}

	public Integer[] getComp_eval_2g_c() {
		return comp_eval_2g_c;
	}

	public void setComp_eval_2g_c(Integer[] comp_eval_2g_c) {
		this.comp_eval_2g_c = comp_eval_2g_c;
	}

	public Integer[] getComp_eval_2g_d() {
		return comp_eval_2g_d;
	}

	public void setComp_eval_2g_d(Integer[] comp_eval_2g_d) {
		this.comp_eval_2g_d = comp_eval_2g_d;
	}

	public Integer[] getComp_impr_a() {
		return comp_impr_a;
	}

	public void setComp_impr_a(Integer[] comp_impr_a) {
		this.comp_impr_a = comp_impr_a;
	}

	public Integer[] getComp_impr_b() {
		return comp_impr_b;
	}

	public void setComp_impr_b(Integer[] comp_impr_b) {
		this.comp_impr_b = comp_impr_b;
	}

	public Integer[] getComp_impr_c() {
		return comp_impr_c;
	}

	public void setComp_impr_c(Integer[] comp_impr_c) {
		this.comp_impr_c = comp_impr_c;
	}

	public Integer[] getComp_impr_d() {
		return comp_impr_d;
	}

	public void setComp_impr_d(Integer[] comp_impr_d) {
		this.comp_impr_d = comp_impr_d;
	}



	public Double[] getComp_impr_value_3g() {
		return comp_impr_value_3g;
	}

	public void setComp_impr_value_3g(Double[] comp_impr_value_3g) {
		this.comp_impr_value_3g = comp_impr_value_3g;
	}

	public Double[] getComp_impr_value_2g() {
		return comp_impr_value_2g;
	}

	public void setComp_impr_value_2g(Double[] comp_impr_value_2g) {
		this.comp_impr_value_2g = comp_impr_value_2g;
	}

	public String[] getTimely_rate() {
		return timely_rate;
	}

	public void setTimely_rate(String[] timely_rate) {
		this.timely_rate = timely_rate;
	}

	public Integer[] getTimely_rate_rank() {
		return timely_rate_rank;
	}

	public void setTimely_rate_rank(Integer[] timely_rate_rank) {
		this.timely_rate_rank = timely_rate_rank;
	}

}