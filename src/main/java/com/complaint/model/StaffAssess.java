package com.complaint.model;

import java.io.Serializable;

/**
 * 中心内部考核
 * 
 * @author peng
 * 
 */
public class StaffAssess implements Serializable {
	private Integer group_big_id;// 大组id
	private String group_big_name;// 大组name
	private Integer group_small_id;// 小组id
	private String group_small_name;// 小组name
	private String total_test_rate;// 累计实测率
	private double test_score;// 累计实测率得分
	private String curr_test_timely;// 月测试及时率
	private double ontime_score;// 月测试及时率得分
	private String total_solve_rate;// 累计解决率
	private double solve_score;// 累计解决率得分
	private String total_delay_rate;// 累计工单滞留率
	private double delay_score;// 累计工单滞留率得分
	private String total_reject_rate;// 累计工单驳回率
	private double reject_score;// 累计工单驳回率得分
	private String total_over_rate;// 累计工单超时率
	private double over_score;// 累计工单超时率得分
	private String total_send_rate;// 累计工单重派率
	private double send_score;// 累计工单重派率得分
	private String total_complaint_rate;// 累计重复投诉率
	private double complaint_score;// 累计重复投诉率得分
	private String total_upgrade_rate;// 累计工单升级率
	private double upgrade_score;// 累计工单升级率得分
	private String complaint;// 月网络投诉工单量
	private double serialno_score;// 月网络投诉工单量得分
	private String group_kpi_score;// 小组kpi得分
	private double group_plusMinus_score;// 小组加减得分
	private String group_synthesis_score;// 小组综合得分
	private String group_rank;// 小组排名
	private String group_plusMinus_cause;// 小组加减分原因

	public double getTest_score() {
		return test_score;
	}

	public void setTest_score(double test_score) {
		this.test_score = test_score;
	}

	public double getOntime_score() {
		return ontime_score;
	}

	public void setOntime_score(double ontime_score) {
		this.ontime_score = ontime_score;
	}

	public double getSolve_score() {
		return solve_score;
	}

	public void setSolve_score(double solve_score) {
		this.solve_score = solve_score;
	}

	public double getDelay_score() {
		return delay_score;
	}

	public void setDelay_score(double delay_score) {
		this.delay_score = delay_score;
	}

	public String getTotal_reject_rate() {
		return total_reject_rate;
	}

	public void setTotal_reject_rate(String total_reject_rate) {
		this.total_reject_rate = total_reject_rate;
	}

	public double getReject_score() {
		return reject_score;
	}

	public void setReject_score(double reject_score) {
		this.reject_score = reject_score;
	}

	public double getOver_score() {
		return over_score;
	}

	public void setOver_score(double over_score) {
		this.over_score = over_score;
	}

	public double getSend_score() {
		return send_score;
	}

	public void setSend_score(double send_score) {
		this.send_score = send_score;
	}

	public double getComplaint_score() {
		return complaint_score;
	}

	public void setComplaint_score(double complaint_score) {
		this.complaint_score = complaint_score;
	}

	public double getUpgrade_score() {
		return upgrade_score;
	}

	public void setUpgrade_score(double upgrade_score) {
		this.upgrade_score = upgrade_score;
	}

	public double getSerialno_score() {
		return serialno_score;
	}

	public void setSerialno_score(double serialno_score) {
		this.serialno_score = serialno_score;
	}

	public Integer getGroup_big_id() {
		return group_big_id;
	}

	public void setGroup_big_id(Integer group_big_id) {
		this.group_big_id = group_big_id;
	}

	public String getGroup_big_name() {
		return group_big_name;
	}

	public void setGroup_big_name(String group_big_name) {
		this.group_big_name = group_big_name;
	}

	public Integer getGroup_small_id() {
		return group_small_id;
	}

	public void setGroup_small_id(Integer group_small_id) {
		this.group_small_id = group_small_id;
	}

	public String getGroup_small_name() {
		return group_small_name;
	}

	public void setGroup_small_name(String group_small_name) {
		this.group_small_name = group_small_name;
	}

	public String getTotal_test_rate() {
		return total_test_rate;
	}

	public void setTotal_test_rate(String total_test_rate) {
		this.total_test_rate = total_test_rate;
	}

	public String getCurr_test_timely() {
		return curr_test_timely;
	}

	public void setCurr_test_timely(String curr_test_timely) {
		this.curr_test_timely = curr_test_timely;
	}

	public String getTotal_solve_rate() {
		return total_solve_rate;
	}

	public void setTotal_solve_rate(String total_solve_rate) {
		this.total_solve_rate = total_solve_rate;
	}

	public String getTotal_delay_rate() {
		return total_delay_rate;
	}

	public void setTotal_delay_rate(String total_delay_rate) {
		this.total_delay_rate = total_delay_rate;
	}

	public String getTotal_over_rate() {
		return total_over_rate;
	}

	public void setTotal_over_rate(String total_over_rate) {
		this.total_over_rate = total_over_rate;
	}

	public String getTotal_send_rate() {
		return total_send_rate;
	}

	public void setTotal_send_rate(String total_send_rate) {
		this.total_send_rate = total_send_rate;
	}

	public String getTotal_upgrade_rate() {
		return total_upgrade_rate;
	}

	public void setTotal_upgrade_rate(String total_upgrade_rate) {
		this.total_upgrade_rate = total_upgrade_rate;
	}

	public String getTotal_complaint_rate() {
		return total_complaint_rate;
	}

	public void setTotal_complaint_rate(String total_complaint_rate) {
		this.total_complaint_rate = total_complaint_rate;
	}

	public String getComplaint() {
		return complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public String getGroup_kpi_score() {
		return group_kpi_score;
	}

	public void setGroup_kpi_score(String group_kpi_score) {
		this.group_kpi_score = group_kpi_score;
	}

	public double getGroup_plusMinus_score() {
		return group_plusMinus_score;
	}

	public void setGroup_plusMinus_score(double group_plusMinus_score) {
		this.group_plusMinus_score = group_plusMinus_score;
	}

	public String getGroup_synthesis_score() {
		return group_synthesis_score;
	}

	public void setGroup_synthesis_score(String group_synthesis_score) {
		this.group_synthesis_score = group_synthesis_score;
	}

	public String getGroup_rank() {
		return group_rank;
	}

	public void setGroup_rank(String group_rank) {
		this.group_rank = group_rank;
	}

	public String getGroup_plusMinus_cause() {
		return group_plusMinus_cause;
	}

	public void setGroup_plusMinus_cause(String group_plusMinus_cause) {
		this.group_plusMinus_cause = group_plusMinus_cause;
	}

}
