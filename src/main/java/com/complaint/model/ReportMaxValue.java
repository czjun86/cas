package com.complaint.model;

import java.util.List;

public class ReportMaxValue {
//	累积工单量
	private List<Integer> max_total_workorder;
//	当前工单量
	private List<Integer> max_curr_workorder;
//	累积实测量
	private List<Integer> max_total_test;
//	当前实测量
	private List<Integer> max_curr_test;
//	累积实测率
	private List<Integer> max_total_test_rate;
//	当前实测排名
	private List<Integer> max_curr_test_rank;
//	累积实测排名
	private List<Integer> max_total_test_rate_rank;
//	当前室内测试量
	private List<Integer> max_curr_in_test;
//	当前室内测试量
	private List<Integer> max_curr_out_test;
//	rscp优良中差对应abcd
	private List<Integer> max_rscp_a;
	private List<Integer> max_rscp_b;
	private List<Integer> max_rscp_c;
	private List<Integer> max_rscp_d;
//	ecno优良中差对应abcd
	private List<Integer> max_ec_no_a;
	private List<Integer> max_ec_no_b;
	private List<Integer> max_ec_no_c;
	private List<Integer> max_ec_no_d;
//	txpower优良中差对应abcd
	private List<Integer> max_txpower_a;
	private List<Integer> max_txpower_b;
	private List<Integer> max_txpower_c;
	private List<Integer> max_txpower_d;
//	ftp上行优良中差对应abcd
	private List<Integer> max_ftp_up_a;
	private List<Integer> max_ftp_up_b;
	private List<Integer> max_ftp_up_c;
	private List<Integer> max_ftp_up_d;
//	ftp下行优良中差对应abcd
	private List<Integer> max_ftp_down_a;
	private List<Integer> max_ftp_down_b;
	private List<Integer> max_ftp_down_c;
	private List<Integer> max_ftp_down_d;
//	rxlev优良中差对应abcd
	private List<Integer> max_rxlev_a;
	private List<Integer> max_rxlev_b;
	private List<Integer> max_rxlev_c;
	private List<Integer> max_rxlev_d;
//	qxqual优良中差对应abcd
	private List<Integer> max_rxqual_a;
	private List<Integer> max_rxqual_b;
	private List<Integer> max_rxqual_c;
	private List<Integer> max_rxqual_d;
//	综合评价优良中差对应abcd
	private List<Integer> max_comp_eval_a;
	private List<Integer> max_comp_eval_b;
	private List<Integer> max_comp_eval_c;
	private List<Integer> max_comp_eval_d;
//	综合改善优良中差对应abcd
	private List<Integer> max_comp_impr_a;
	private List<Integer> max_comp_impr_b;
	private List<Integer> max_comp_impr_c;
	private List<Integer> max_comp_impr_d;
//	综合改善得分
	private List<Integer> max_comp_impr_value;
//	及时率
	private List<Integer> max_timely_rate;
	
//	累积工单量
	private List<Integer> min_total_workorder;
//	当前工单量
	private List<Integer> min_curr_workorder;
//	累积实测量
	private List<Integer> min_total_test;
//	当前实测量
	private List<Integer> min_curr_test;
//	累积实测率
	private List<Integer> min_total_test_rate;
//	当前实测排名
	private List<Integer> min_curr_test_rank;
//	累积实测排名
	private List<Integer> min_total_test_rate_rank;
//	当前室内测试量
	private List<Integer> min_curr_in_test;
//	当前室内测试量
	private List<Integer> min_curr_out_test;
//	rscp优良中差对应abcd
	private List<Integer> min_rscp_a;
	private List<Integer> min_rscp_b;
	private List<Integer> min_rscp_c;
	private List<Integer> min_rscp_d;
//	ecno优良中差对应abcd
	private List<Integer> min_ec_no_a;
	private List<Integer> min_ec_no_b;
	private List<Integer> min_ec_no_c;
	private List<Integer> min_ec_no_d;
//	txpower优良中差对应abcd
	private List<Integer> min_txpower_a;
	private List<Integer> min_txpower_b;
	private List<Integer> min_txpower_c;
	private List<Integer> min_txpower_d;
//	ftp上行优良中差对应abcd
	private List<Integer> min_ftp_up_a;
	private List<Integer> min_ftp_up_b;
	private List<Integer> min_ftp_up_c;
	private List<Integer> min_ftp_up_d;
//	ftp下行优良中差对应abcd
	private List<Integer> min_ftp_down_a;
	private List<Integer> min_ftp_down_b;
	private List<Integer> min_ftp_down_c;
	private List<Integer> min_ftp_down_d;
//	rxlev优良中差对应abcd
	private List<Integer> min_rxlev_a;
	private List<Integer> min_rxlev_b;
	private List<Integer> min_rxlev_c;
	private List<Integer> min_rxlev_d;
//	qxqual优良中差对应abcd
	private List<Integer> min_rxqual_a;
	private List<Integer> min_rxqual_b;
	private List<Integer> min_rxqual_c;
	private List<Integer> min_rxqual_d;
//	综合评价优良中差对应abcd
	private List<Integer> min_comp_eval_a;
	private List<Integer> min_comp_eval_b;
	private List<Integer> min_comp_eval_c;
	private List<Integer> min_comp_eval_d;
//	综合改善优良中差对应abcd
	private List<Integer> min_comp_impr_a;
	private List<Integer> min_comp_impr_b;
	private List<Integer> min_comp_impr_c;
	private List<Integer> min_comp_impr_d;
//	综合改善得分
	private List<Integer> min_comp_impr_value;
//	及时率
	private List<Integer> min_timely_rate;
	public List<Integer> getMax_total_workorder() {
		return max_total_workorder;
	}
	public void setMax_total_workorder(List<Integer> max_total_workorder) {
		this.max_total_workorder = max_total_workorder;
	}
	public List<Integer> getMax_curr_workorder() {
		return max_curr_workorder;
	}
	public void setMax_curr_workorder(List<Integer> max_curr_workorder) {
		this.max_curr_workorder = max_curr_workorder;
	}
	public List<Integer> getMax_total_test() {
		return max_total_test;
	}
	public void setMax_total_test(List<Integer> max_total_test) {
		this.max_total_test = max_total_test;
	}
	public List<Integer> getMax_curr_test() {
		return max_curr_test;
	}
	public void setMax_curr_test(List<Integer> max_curr_test) {
		this.max_curr_test = max_curr_test;
	}
	public List<Integer> getMax_total_test_rate() {
		return max_total_test_rate;
	}
	public void setMax_total_test_rate(List<Integer> max_total_test_rate) {
		this.max_total_test_rate = max_total_test_rate;
	}
	public List<Integer> getMax_curr_test_rank() {
		return max_curr_test_rank;
	}
	public void setMax_curr_test_rank(List<Integer> max_curr_test_rank) {
		this.max_curr_test_rank = max_curr_test_rank;
	}
	public List<Integer> getMax_total_test_rate_rank() {
		return max_total_test_rate_rank;
	}
	public void setMax_total_test_rate_rank(List<Integer> max_total_test_rate_rank) {
		this.max_total_test_rate_rank = max_total_test_rate_rank;
	}
	public List<Integer> getMax_curr_in_test() {
		return max_curr_in_test;
	}
	public void setMax_curr_in_test(List<Integer> max_curr_in_test) {
		this.max_curr_in_test = max_curr_in_test;
	}
	public List<Integer> getMax_curr_out_test() {
		return max_curr_out_test;
	}
	public void setMax_curr_out_test(List<Integer> max_curr_out_test) {
		this.max_curr_out_test = max_curr_out_test;
	}
	public List<Integer> getMax_rscp_a() {
		return max_rscp_a;
	}
	public void setMax_rscp_a(List<Integer> max_rscp_a) {
		this.max_rscp_a = max_rscp_a;
	}
	public List<Integer> getMax_rscp_b() {
		return max_rscp_b;
	}
	public void setMax_rscp_b(List<Integer> max_rscp_b) {
		this.max_rscp_b = max_rscp_b;
	}
	public List<Integer> getMax_rscp_c() {
		return max_rscp_c;
	}
	public void setMax_rscp_c(List<Integer> max_rscp_c) {
		this.max_rscp_c = max_rscp_c;
	}
	public List<Integer> getMax_rscp_d() {
		return max_rscp_d;
	}
	public void setMax_rscp_d(List<Integer> max_rscp_d) {
		this.max_rscp_d = max_rscp_d;
	}
	public List<Integer> getMax_ec_no_a() {
		return max_ec_no_a;
	}
	public void setMax_ec_no_a(List<Integer> max_ec_no_a) {
		this.max_ec_no_a = max_ec_no_a;
	}
	public List<Integer> getMax_ec_no_b() {
		return max_ec_no_b;
	}
	public void setMax_ec_no_b(List<Integer> max_ec_no_b) {
		this.max_ec_no_b = max_ec_no_b;
	}
	public List<Integer> getMax_ec_no_c() {
		return max_ec_no_c;
	}
	public void setMax_ec_no_c(List<Integer> max_ec_no_c) {
		this.max_ec_no_c = max_ec_no_c;
	}
	public List<Integer> getMax_ec_no_d() {
		return max_ec_no_d;
	}
	public void setMax_ec_no_d(List<Integer> max_ec_no_d) {
		this.max_ec_no_d = max_ec_no_d;
	}
	public List<Integer> getMax_txpower_a() {
		return max_txpower_a;
	}
	public void setMax_txpower_a(List<Integer> max_txpower_a) {
		this.max_txpower_a = max_txpower_a;
	}
	public List<Integer> getMax_txpower_b() {
		return max_txpower_b;
	}
	public void setMax_txpower_b(List<Integer> max_txpower_b) {
		this.max_txpower_b = max_txpower_b;
	}
	public List<Integer> getMax_txpower_c() {
		return max_txpower_c;
	}
	public void setMax_txpower_c(List<Integer> max_txpower_c) {
		this.max_txpower_c = max_txpower_c;
	}
	public List<Integer> getMax_txpower_d() {
		return max_txpower_d;
	}
	public void setMax_txpower_d(List<Integer> max_txpower_d) {
		this.max_txpower_d = max_txpower_d;
	}
	public List<Integer> getMax_ftp_up_a() {
		return max_ftp_up_a;
	}
	public void setMax_ftp_up_a(List<Integer> max_ftp_up_a) {
		this.max_ftp_up_a = max_ftp_up_a;
	}
	public List<Integer> getMax_ftp_up_b() {
		return max_ftp_up_b;
	}
	public void setMax_ftp_up_b(List<Integer> max_ftp_up_b) {
		this.max_ftp_up_b = max_ftp_up_b;
	}
	public List<Integer> getMax_ftp_up_c() {
		return max_ftp_up_c;
	}
	public void setMax_ftp_up_c(List<Integer> max_ftp_up_c) {
		this.max_ftp_up_c = max_ftp_up_c;
	}
	public List<Integer> getMax_ftp_up_d() {
		return max_ftp_up_d;
	}
	public void setMax_ftp_up_d(List<Integer> max_ftp_up_d) {
		this.max_ftp_up_d = max_ftp_up_d;
	}
	public List<Integer> getMax_ftp_down_a() {
		return max_ftp_down_a;
	}
	public void setMax_ftp_down_a(List<Integer> max_ftp_down_a) {
		this.max_ftp_down_a = max_ftp_down_a;
	}
	public List<Integer> getMax_ftp_down_b() {
		return max_ftp_down_b;
	}
	public void setMax_ftp_down_b(List<Integer> max_ftp_down_b) {
		this.max_ftp_down_b = max_ftp_down_b;
	}
	public List<Integer> getMax_ftp_down_c() {
		return max_ftp_down_c;
	}
	public void setMax_ftp_down_c(List<Integer> max_ftp_down_c) {
		this.max_ftp_down_c = max_ftp_down_c;
	}
	public List<Integer> getMax_ftp_down_d() {
		return max_ftp_down_d;
	}
	public void setMax_ftp_down_d(List<Integer> max_ftp_down_d) {
		this.max_ftp_down_d = max_ftp_down_d;
	}
	public List<Integer> getMax_rxlev_a() {
		return max_rxlev_a;
	}
	public void setMax_rxlev_a(List<Integer> max_rxlev_a) {
		this.max_rxlev_a = max_rxlev_a;
	}
	public List<Integer> getMax_rxlev_b() {
		return max_rxlev_b;
	}
	public void setMax_rxlev_b(List<Integer> max_rxlev_b) {
		this.max_rxlev_b = max_rxlev_b;
	}
	public List<Integer> getMax_rxlev_c() {
		return max_rxlev_c;
	}
	public void setMax_rxlev_c(List<Integer> max_rxlev_c) {
		this.max_rxlev_c = max_rxlev_c;
	}
	public List<Integer> getMax_rxlev_d() {
		return max_rxlev_d;
	}
	public void setMax_rxlev_d(List<Integer> max_rxlev_d) {
		this.max_rxlev_d = max_rxlev_d;
	}
	public List<Integer> getMax_rxqual_a() {
		return max_rxqual_a;
	}
	public void setMax_rxqual_a(List<Integer> max_rxqual_a) {
		this.max_rxqual_a = max_rxqual_a;
	}
	public List<Integer> getMax_rxqual_b() {
		return max_rxqual_b;
	}
	public void setMax_rxqual_b(List<Integer> max_rxqual_b) {
		this.max_rxqual_b = max_rxqual_b;
	}
	public List<Integer> getMax_rxqual_c() {
		return max_rxqual_c;
	}
	public void setMax_rxqual_c(List<Integer> max_rxqual_c) {
		this.max_rxqual_c = max_rxqual_c;
	}
	public List<Integer> getMax_rxqual_d() {
		return max_rxqual_d;
	}
	public void setMax_rxqual_d(List<Integer> max_rxqual_d) {
		this.max_rxqual_d = max_rxqual_d;
	}
	public List<Integer> getMax_comp_eval_a() {
		return max_comp_eval_a;
	}
	public void setMax_comp_eval_a(List<Integer> max_comp_eval_a) {
		this.max_comp_eval_a = max_comp_eval_a;
	}
	public List<Integer> getMax_comp_eval_b() {
		return max_comp_eval_b;
	}
	public void setMax_comp_eval_b(List<Integer> max_comp_eval_b) {
		this.max_comp_eval_b = max_comp_eval_b;
	}
	public List<Integer> getMax_comp_eval_c() {
		return max_comp_eval_c;
	}
	public void setMax_comp_eval_c(List<Integer> max_comp_eval_c) {
		this.max_comp_eval_c = max_comp_eval_c;
	}
	public List<Integer> getMax_comp_eval_d() {
		return max_comp_eval_d;
	}
	public void setMax_comp_eval_d(List<Integer> max_comp_eval_d) {
		this.max_comp_eval_d = max_comp_eval_d;
	}
	public List<Integer> getMax_comp_impr_a() {
		return max_comp_impr_a;
	}
	public void setMax_comp_impr_a(List<Integer> max_comp_impr_a) {
		this.max_comp_impr_a = max_comp_impr_a;
	}
	public List<Integer> getMax_comp_impr_b() {
		return max_comp_impr_b;
	}
	public void setMax_comp_impr_b(List<Integer> max_comp_impr_b) {
		this.max_comp_impr_b = max_comp_impr_b;
	}
	public List<Integer> getMax_comp_impr_c() {
		return max_comp_impr_c;
	}
	public void setMax_comp_impr_c(List<Integer> max_comp_impr_c) {
		this.max_comp_impr_c = max_comp_impr_c;
	}
	public List<Integer> getMax_comp_impr_d() {
		return max_comp_impr_d;
	}
	public void setMax_comp_impr_d(List<Integer> max_comp_impr_d) {
		this.max_comp_impr_d = max_comp_impr_d;
	}
	public List<Integer> getMin_total_workorder() {
		return min_total_workorder;
	}
	public void setMin_total_workorder(List<Integer> min_total_workorder) {
		this.min_total_workorder = min_total_workorder;
	}
	public List<Integer> getMin_curr_workorder() {
		return min_curr_workorder;
	}
	public void setMin_curr_workorder(List<Integer> min_curr_workorder) {
		this.min_curr_workorder = min_curr_workorder;
	}
	public List<Integer> getMin_total_test() {
		return min_total_test;
	}
	public void setMin_total_test(List<Integer> min_total_test) {
		this.min_total_test = min_total_test;
	}
	public List<Integer> getMin_curr_test() {
		return min_curr_test;
	}
	public void setMin_curr_test(List<Integer> min_curr_test) {
		this.min_curr_test = min_curr_test;
	}
	public List<Integer> getMin_total_test_rate() {
		return min_total_test_rate;
	}
	public void setMin_total_test_rate(List<Integer> min_total_test_rate) {
		this.min_total_test_rate = min_total_test_rate;
	}
	public List<Integer> getMin_curr_test_rank() {
		return min_curr_test_rank;
	}
	public void setMin_curr_test_rank(List<Integer> min_curr_test_rank) {
		this.min_curr_test_rank = min_curr_test_rank;
	}
	public List<Integer> getMin_total_test_rate_rank() {
		return min_total_test_rate_rank;
	}
	public void setMin_total_test_rate_rank(List<Integer> min_total_test_rate_rank) {
		this.min_total_test_rate_rank = min_total_test_rate_rank;
	}
	public List<Integer> getMin_curr_in_test() {
		return min_curr_in_test;
	}
	public void setMin_curr_in_test(List<Integer> min_curr_in_test) {
		this.min_curr_in_test = min_curr_in_test;
	}
	public List<Integer> getMin_curr_out_test() {
		return min_curr_out_test;
	}
	public void setMin_curr_out_test(List<Integer> min_curr_out_test) {
		this.min_curr_out_test = min_curr_out_test;
	}
	public List<Integer> getMin_rscp_a() {
		return min_rscp_a;
	}
	public void setMin_rscp_a(List<Integer> min_rscp_a) {
		this.min_rscp_a = min_rscp_a;
	}
	public List<Integer> getMin_rscp_b() {
		return min_rscp_b;
	}
	public void setMin_rscp_b(List<Integer> min_rscp_b) {
		this.min_rscp_b = min_rscp_b;
	}
	public List<Integer> getMin_rscp_c() {
		return min_rscp_c;
	}
	public void setMin_rscp_c(List<Integer> min_rscp_c) {
		this.min_rscp_c = min_rscp_c;
	}
	public List<Integer> getMin_rscp_d() {
		return min_rscp_d;
	}
	public void setMin_rscp_d(List<Integer> min_rscp_d) {
		this.min_rscp_d = min_rscp_d;
	}
	public List<Integer> getMin_ec_no_a() {
		return min_ec_no_a;
	}
	public void setMin_ec_no_a(List<Integer> min_ec_no_a) {
		this.min_ec_no_a = min_ec_no_a;
	}
	public List<Integer> getMin_ec_no_b() {
		return min_ec_no_b;
	}
	public void setMin_ec_no_b(List<Integer> min_ec_no_b) {
		this.min_ec_no_b = min_ec_no_b;
	}
	public List<Integer> getMin_ec_no_c() {
		return min_ec_no_c;
	}
	public void setMin_ec_no_c(List<Integer> min_ec_no_c) {
		this.min_ec_no_c = min_ec_no_c;
	}
	public List<Integer> getMin_ec_no_d() {
		return min_ec_no_d;
	}
	public void setMin_ec_no_d(List<Integer> min_ec_no_d) {
		this.min_ec_no_d = min_ec_no_d;
	}
	public List<Integer> getMin_txpower_a() {
		return min_txpower_a;
	}
	public void setMin_txpower_a(List<Integer> min_txpower_a) {
		this.min_txpower_a = min_txpower_a;
	}
	public List<Integer> getMin_txpower_b() {
		return min_txpower_b;
	}
	public void setMin_txpower_b(List<Integer> min_txpower_b) {
		this.min_txpower_b = min_txpower_b;
	}
	public List<Integer> getMin_txpower_c() {
		return min_txpower_c;
	}
	public void setMin_txpower_c(List<Integer> min_txpower_c) {
		this.min_txpower_c = min_txpower_c;
	}
	public List<Integer> getMin_txpower_d() {
		return min_txpower_d;
	}
	public void setMin_txpower_d(List<Integer> min_txpower_d) {
		this.min_txpower_d = min_txpower_d;
	}
	public List<Integer> getMin_ftp_up_a() {
		return min_ftp_up_a;
	}
	public void setMin_ftp_up_a(List<Integer> min_ftp_up_a) {
		this.min_ftp_up_a = min_ftp_up_a;
	}
	public List<Integer> getMin_ftp_up_b() {
		return min_ftp_up_b;
	}
	public void setMin_ftp_up_b(List<Integer> min_ftp_up_b) {
		this.min_ftp_up_b = min_ftp_up_b;
	}
	public List<Integer> getMin_ftp_up_c() {
		return min_ftp_up_c;
	}
	public void setMin_ftp_up_c(List<Integer> min_ftp_up_c) {
		this.min_ftp_up_c = min_ftp_up_c;
	}
	public List<Integer> getMin_ftp_up_d() {
		return min_ftp_up_d;
	}
	public void setMin_ftp_up_d(List<Integer> min_ftp_up_d) {
		this.min_ftp_up_d = min_ftp_up_d;
	}
	public List<Integer> getMin_ftp_down_a() {
		return min_ftp_down_a;
	}
	public void setMin_ftp_down_a(List<Integer> min_ftp_down_a) {
		this.min_ftp_down_a = min_ftp_down_a;
	}
	public List<Integer> getMin_ftp_down_b() {
		return min_ftp_down_b;
	}
	public void setMin_ftp_down_b(List<Integer> min_ftp_down_b) {
		this.min_ftp_down_b = min_ftp_down_b;
	}
	public List<Integer> getMin_ftp_down_c() {
		return min_ftp_down_c;
	}
	public void setMin_ftp_down_c(List<Integer> min_ftp_down_c) {
		this.min_ftp_down_c = min_ftp_down_c;
	}
	public List<Integer> getMin_ftp_down_d() {
		return min_ftp_down_d;
	}
	public void setMin_ftp_down_d(List<Integer> min_ftp_down_d) {
		this.min_ftp_down_d = min_ftp_down_d;
	}
	public List<Integer> getMin_rxlev_a() {
		return min_rxlev_a;
	}
	public void setMin_rxlev_a(List<Integer> min_rxlev_a) {
		this.min_rxlev_a = min_rxlev_a;
	}
	public List<Integer> getMin_rxlev_b() {
		return min_rxlev_b;
	}
	public void setMin_rxlev_b(List<Integer> min_rxlev_b) {
		this.min_rxlev_b = min_rxlev_b;
	}
	public List<Integer> getMin_rxlev_c() {
		return min_rxlev_c;
	}
	public void setMin_rxlev_c(List<Integer> min_rxlev_c) {
		this.min_rxlev_c = min_rxlev_c;
	}
	public List<Integer> getMin_rxlev_d() {
		return min_rxlev_d;
	}
	public void setMin_rxlev_d(List<Integer> min_rxlev_d) {
		this.min_rxlev_d = min_rxlev_d;
	}
	public List<Integer> getMin_rxqual_a() {
		return min_rxqual_a;
	}
	public void setMin_rxqual_a(List<Integer> min_rxqual_a) {
		this.min_rxqual_a = min_rxqual_a;
	}
	public List<Integer> getMin_rxqual_b() {
		return min_rxqual_b;
	}
	public void setMin_rxqual_b(List<Integer> min_rxqual_b) {
		this.min_rxqual_b = min_rxqual_b;
	}
	public List<Integer> getMin_rxqual_c() {
		return min_rxqual_c;
	}
	public void setMin_rxqual_c(List<Integer> min_rxqual_c) {
		this.min_rxqual_c = min_rxqual_c;
	}
	public List<Integer> getMin_rxqual_d() {
		return min_rxqual_d;
	}
	public void setMin_rxqual_d(List<Integer> min_rxqual_d) {
		this.min_rxqual_d = min_rxqual_d;
	}
	public List<Integer> getMin_comp_eval_a() {
		return min_comp_eval_a;
	}
	public void setMin_comp_eval_a(List<Integer> min_comp_eval_a) {
		this.min_comp_eval_a = min_comp_eval_a;
	}
	public List<Integer> getMin_comp_eval_b() {
		return min_comp_eval_b;
	}
	public void setMin_comp_eval_b(List<Integer> min_comp_eval_b) {
		this.min_comp_eval_b = min_comp_eval_b;
	}
	public List<Integer> getMin_comp_eval_c() {
		return min_comp_eval_c;
	}
	public void setMin_comp_eval_c(List<Integer> min_comp_eval_c) {
		this.min_comp_eval_c = min_comp_eval_c;
	}
	public List<Integer> getMin_comp_eval_d() {
		return min_comp_eval_d;
	}
	public void setMin_comp_eval_d(List<Integer> min_comp_eval_d) {
		this.min_comp_eval_d = min_comp_eval_d;
	}
	public List<Integer> getMin_comp_impr_a() {
		return min_comp_impr_a;
	}
	public void setMin_comp_impr_a(List<Integer> min_comp_impr_a) {
		this.min_comp_impr_a = min_comp_impr_a;
	}
	public List<Integer> getMin_comp_impr_b() {
		return min_comp_impr_b;
	}
	public void setMin_comp_impr_b(List<Integer> min_comp_impr_b) {
		this.min_comp_impr_b = min_comp_impr_b;
	}
	public List<Integer> getMin_comp_impr_c() {
		return min_comp_impr_c;
	}
	public void setMin_comp_impr_c(List<Integer> min_comp_impr_c) {
		this.min_comp_impr_c = min_comp_impr_c;
	}
	public List<Integer> getMin_comp_impr_d() {
		return min_comp_impr_d;
	}
	public void setMin_comp_impr_d(List<Integer> min_comp_impr_d) {
		this.min_comp_impr_d = min_comp_impr_d;
	}
	public List<Integer> getMax_comp_impr_value() {
		return max_comp_impr_value;
	}
	public void setMax_comp_impr_value(List<Integer> max_comp_impr_value) {
		this.max_comp_impr_value = max_comp_impr_value;
	}
	public List<Integer> getMax_timely_rate() {
		return max_timely_rate;
	}
	public void setMax_timely_rate(List<Integer> max_timely_rate) {
		this.max_timely_rate = max_timely_rate;
	}
	public List<Integer> getMin_comp_impr_value() {
		return min_comp_impr_value;
	}
	public void setMin_comp_impr_value(List<Integer> min_comp_impr_value) {
		this.min_comp_impr_value = min_comp_impr_value;
	}
	public List<Integer> getMin_timely_rate() {
		return min_timely_rate;
	}
	public void setMin_timely_rate(List<Integer> min_timely_rate) {
		this.min_timely_rate = min_timely_rate;
	}
	
}
