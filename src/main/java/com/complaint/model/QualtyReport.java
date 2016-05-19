package com.complaint.model;

public class QualtyReport {
	private Integer[] areaid;// 区域ID
	private String[] areaname;// 区域名称

	private String[] comp_score; // 投诉处理质量综合评分
	private Integer[] comp_score_rank; // 投诉处理质量综合排名
	private String[] assess_score; // 移动网络服务考核得分

	private Integer[] total_workorder; // 累计需实测工单量
	private Integer[] curr_workorder; // 月需实测工单量

	private Integer[] total_test; // 累计实测量
	private Integer[] curr_test; // 月实测量
	private String[] total_test_rate; // 累计实测率

	private String[] curr_test_timely; // 月测试及时率

	private Integer[] total_serialno; // 累计网络投诉工单量

	private Integer[] total_solve; // 累计真正解决工单量
	private String[] total_solve_rate; // 累计真正解决率
	private Integer[] curr_serialno;// 月网络投诉工单量
	private Integer[] curr_solve; // 月真正解决工单量

	private Integer[] total_major_solve; // 累计优化真正解决量
	private String[] total_major_solve_rate; // 累计优化真正解决比
	private Integer[] curr_major_solve; // 月优化真正解决量
	private String[] curr_major_solve_rate;// 月优化真正解决比

	private Integer[] total_build_solve; // 累计建设真正解决量
	private String[] total_build_solve_rate; // 累计建设真正解决比
	private Integer[] curr_build_solve; // 月建设真正解决量
	private String[] curr_build_solve_rate;// 月建设真正解决比

	private Integer[] total_maintain_solve; // 累计维护真正解决量
	private String[] total_maintain_solve_rate; // 累计维护真正解决比
	private Integer[] curr_maintain_solve; // 月维护真正解决量
	private String[] curr_maintain_solve_rate;// 月维护真正解决比

	private Integer[] total_other_solve; // 累计其它真正解决量
	private String[] total_other_solve_rate; // 累计其它真正解决比
	private Integer[] curr_other_solve; // 月其它真正解决量
	private String[] curr_other_solve_rate;// 月其它真正解决比

	private Integer[] total_delay; // 累计工单滞留量
	private String[] total_delay_rate; // 累计工单滞留率

	private Integer[] total_major_delay; // 累计优化工单滞留量
	private String[] total_major_delay_rate;// 累计优化工单滞留比

	private Integer[] total_build_delay; // 累计建设工单滞留量
	private String[] total_build_delay_rate;// 累计建设工单滞留比

	private Integer[] total_maintain_delay; // 累计维护工单滞留量
	private String[] total_maintain_delay_rate;// 累计维护工单滞留比

	private Integer[] total_reject; // 累计工单驳回量
	private Integer[] curr_reject; // 月工单驳回量
	private String[] total_reject_rate;// 累计工单驳回率
	private String[] curr_reject_rate; // 月工单驳回率

	private Integer[] total_major_reject; // 累计优化工单驳回量
	private String[] total_major_reject_rate;// 累计优化工单驳回比
	private Integer[] curr_major_reject; // 月优化工单驳回量
	private String[] curr_major_reject_rate;// 月优化工单驳回比

	private Integer[] total_build_reject; // 累计建设工单驳回量
	private String[] total_build_reject_rate;// 累计建设工单驳回比
	private Integer[] curr_build_reject; // 月建设工单驳回量
	private String[] curr_build_reject_rate;// 月建设工单驳回比

	private Integer[] total_maintain_reject; // 累计维护工单驳回量
	private String[] total_maintain_reject_rate;// 累计维护工单驳回比
	private Integer[] curr_maintain_reject; // 月维护工单驳回量
	private String[] curr_maintain_reject_rate;// 月维护工单驳回比

	private Integer[] total_over; // 累计工单超时量
	private Integer[] curr_over; // 月工单超时量
	private String[] total_over_rate; // 累计工单超时率

	private Integer[] total_send; // 累计工单重复量
	private Integer[] curr_send; // 月工单重复量
	private String[] total_send_rate; // 累计工单重派率

	private Integer[] total_complaint; // 累计工单重复投诉量
	private Integer[] curr_complaint; // 月工单重复投诉量
	private String[] total_complaint_rate; // 累计重复投诉率
	private Integer[] total_upgrade; // 累计工单升级量
	private Integer[] curr_upgrade; // 月工单升级量
	private String[] total_upgrade_rate; // 累计工单升级率

	private Double[] curr_wcdma_impr; // 月3G综合改善评分
	private Double[] curr_gsm_impr; // 月2G综合改善评分
	private String[] ratio_wcdma_impr; // 月3G综合改善比例
	private String[] ratio_gsm_impr; // 月2G综合改善比例

	
	public String[] getRatio_wcdma_impr() {
		return ratio_wcdma_impr;
	}

	public void setRatio_wcdma_impr(String[] ratio_wcdma_impr) {
		this.ratio_wcdma_impr = ratio_wcdma_impr;
	}

	public String[] getRatio_gsm_impr() {
		return ratio_gsm_impr;
	}

	public void setRatio_gsm_impr(String[] ratio_gsm_impr) {
		this.ratio_gsm_impr = ratio_gsm_impr;
	}

	public Integer[] getTotal_other_solve() {
		return total_other_solve;
	}

	public void setTotal_other_solve(Integer[] total_other_solve) {
		this.total_other_solve = total_other_solve;
	}

	public String[] getTotal_other_solve_rate() {
		return total_other_solve_rate;
	}

	public void setTotal_other_solve_rate(String[] total_other_solve_rate) {
		this.total_other_solve_rate = total_other_solve_rate;
	}

	public Integer[] getCurr_other_solve() {
		return curr_other_solve;
	}

	public void setCurr_other_solve(Integer[] curr_other_solve) {
		this.curr_other_solve = curr_other_solve;
	}

	public String[] getCurr_other_solve_rate() {
		return curr_other_solve_rate;
	}

	public void setCurr_other_solve_rate(String[] curr_other_solve_rate) {
		this.curr_other_solve_rate = curr_other_solve_rate;
	}

	public Integer[] getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer[] areaid) {
		this.areaid = areaid;
	}

	public String[] getAreaname() {
		return areaname;
	}

	public void setAreaname(String[] areaname) {
		this.areaname = areaname;
	}

	public String[] getComp_score() {
		return comp_score;
	}

	public void setComp_score(String[] comp_score) {
		this.comp_score = comp_score;
	}

	public Integer[] getComp_score_rank() {
		return comp_score_rank;
	}

	public void setComp_score_rank(Integer[] comp_score_rank) {
		this.comp_score_rank = comp_score_rank;
	}

	public String[] getAssess_score() {
		return assess_score;
	}

	public void setAssess_score(String[] assess_score) {
		this.assess_score = assess_score;
	}

	public Integer[] getTotal_workorder() {
		return total_workorder;
	}

	public void setTotal_workorder(Integer[] total_workorder) {
		this.total_workorder = total_workorder;
	}

	public Integer[] getCurr_workorder() {
		return curr_workorder;
	}

	public void setCurr_workorder(Integer[] curr_workorder) {
		this.curr_workorder = curr_workorder;
	}

	public Integer[] getTotal_test() {
		return total_test;
	}

	public void setTotal_test(Integer[] total_test) {
		this.total_test = total_test;
	}

	public Integer[] getCurr_test() {
		return curr_test;
	}

	public void setCurr_test(Integer[] curr_test) {
		this.curr_test = curr_test;
	}

	public String[] getTotal_test_rate() {
		return total_test_rate;
	}

	public void setTotal_test_rate(String[] total_test_rate) {
		this.total_test_rate = total_test_rate;
	}

	public String[] getCurr_test_timely() {
		return curr_test_timely;
	}

	public void setCurr_test_timely(String[] curr_test_timely) {
		this.curr_test_timely = curr_test_timely;
	}

	public Integer[] getTotal_serialno() {
		return total_serialno;
	}

	public void setTotal_serialno(Integer[] total_serialno) {
		this.total_serialno = total_serialno;
	}

	public Integer[] getTotal_solve() {
		return total_solve;
	}

	public void setTotal_solve(Integer[] total_solve) {
		this.total_solve = total_solve;
	}

	public String[] getTotal_solve_rate() {
		return total_solve_rate;
	}

	public void setTotal_solve_rate(String[] total_solve_rate) {
		this.total_solve_rate = total_solve_rate;
	}

	public Integer[] getCurr_serialno() {
		return curr_serialno;
	}

	public void setCurr_serialno(Integer[] curr_serialno) {
		this.curr_serialno = curr_serialno;
	}

	public Integer[] getCurr_solve() {
		return curr_solve;
	}

	public void setCurr_solve(Integer[] curr_solve) {
		this.curr_solve = curr_solve;
	}

	public Integer[] getTotal_major_solve() {
		return total_major_solve;
	}

	public void setTotal_major_solve(Integer[] total_major_solve) {
		this.total_major_solve = total_major_solve;
	}

	public String[] getTotal_major_solve_rate() {
		return total_major_solve_rate;
	}

	public void setTotal_major_solve_rate(String[] total_major_solve_rate) {
		this.total_major_solve_rate = total_major_solve_rate;
	}

	public Integer[] getCurr_major_solve() {
		return curr_major_solve;
	}

	public void setCurr_major_solve(Integer[] curr_major_solve) {
		this.curr_major_solve = curr_major_solve;
	}

	public String[] getCurr_major_solve_rate() {
		return curr_major_solve_rate;
	}

	public void setCurr_major_solve_rate(String[] curr_major_solve_rate) {
		this.curr_major_solve_rate = curr_major_solve_rate;
	}

	public Integer[] getTotal_build_solve() {
		return total_build_solve;
	}

	public void setTotal_build_solve(Integer[] total_build_solve) {
		this.total_build_solve = total_build_solve;
	}

	public String[] getTotal_build_solve_rate() {
		return total_build_solve_rate;
	}

	public void setTotal_build_solve_rate(String[] total_build_solve_rate) {
		this.total_build_solve_rate = total_build_solve_rate;
	}

	public Integer[] getCurr_build_solve() {
		return curr_build_solve;
	}

	public void setCurr_build_solve(Integer[] curr_build_solve) {
		this.curr_build_solve = curr_build_solve;
	}

	public String[] getCurr_build_solve_rate() {
		return curr_build_solve_rate;
	}

	public void setCurr_build_solve_rate(String[] curr_build_solve_rate) {
		this.curr_build_solve_rate = curr_build_solve_rate;
	}

	public Integer[] getTotal_maintain_solve() {
		return total_maintain_solve;
	}

	public void setTotal_maintain_solve(Integer[] total_maintain_solve) {
		this.total_maintain_solve = total_maintain_solve;
	}

	public String[] getTotal_maintain_solve_rate() {
		return total_maintain_solve_rate;
	}

	public void setTotal_maintain_solve_rate(String[] total_maintain_solve_rate) {
		this.total_maintain_solve_rate = total_maintain_solve_rate;
	}

	public Integer[] getCurr_maintain_solve() {
		return curr_maintain_solve;
	}

	public void setCurr_maintain_solve(Integer[] curr_maintain_solve) {
		this.curr_maintain_solve = curr_maintain_solve;
	}

	public String[] getCurr_maintain_solve_rate() {
		return curr_maintain_solve_rate;
	}

	public void setCurr_maintain_solve_rate(String[] curr_maintain_solve_rate) {
		this.curr_maintain_solve_rate = curr_maintain_solve_rate;
	}

	public Integer[] getTotal_delay() {
		return total_delay;
	}

	public void setTotal_delay(Integer[] total_delay) {
		this.total_delay = total_delay;
	}

	public String[] getTotal_delay_rate() {
		return total_delay_rate;
	}

	public void setTotal_delay_rate(String[] total_delay_rate) {
		this.total_delay_rate = total_delay_rate;
	}

	public Integer[] getTotal_major_delay() {
		return total_major_delay;
	}

	public void setTotal_major_delay(Integer[] total_major_delay) {
		this.total_major_delay = total_major_delay;
	}

	public String[] getTotal_major_delay_rate() {
		return total_major_delay_rate;
	}

	public void setTotal_major_delay_rate(String[] total_major_delay_rate) {
		this.total_major_delay_rate = total_major_delay_rate;
	}

	public Integer[] getTotal_build_delay() {
		return total_build_delay;
	}

	public void setTotal_build_delay(Integer[] total_build_delay) {
		this.total_build_delay = total_build_delay;
	}

	public String[] getTotal_build_delay_rate() {
		return total_build_delay_rate;
	}

	public void setTotal_build_delay_rate(String[] total_build_delay_rate) {
		this.total_build_delay_rate = total_build_delay_rate;
	}

	public Integer[] getTotal_maintain_delay() {
		return total_maintain_delay;
	}

	public void setTotal_maintain_delay(Integer[] total_maintain_delay) {
		this.total_maintain_delay = total_maintain_delay;
	}

	public String[] getTotal_maintain_delay_rate() {
		return total_maintain_delay_rate;
	}

	public void setTotal_maintain_delay_rate(String[] total_maintain_delay_rate) {
		this.total_maintain_delay_rate = total_maintain_delay_rate;
	}

	public Integer[] getTotal_reject() {
		return total_reject;
	}

	public void setTotal_reject(Integer[] total_reject) {
		this.total_reject = total_reject;
	}

	public Integer[] getCurr_reject() {
		return curr_reject;
	}

	public void setCurr_reject(Integer[] curr_reject) {
		this.curr_reject = curr_reject;
	}

	public String[] getTotal_reject_rate() {
		return total_reject_rate;
	}

	public void setTotal_reject_rate(String[] total_reject_rate) {
		this.total_reject_rate = total_reject_rate;
	}

	public String[] getCurr_reject_rate() {
		return curr_reject_rate;
	}

	public void setCurr_reject_rate(String[] curr_reject_rate) {
		this.curr_reject_rate = curr_reject_rate;
	}

	public Integer[] getTotal_major_reject() {
		return total_major_reject;
	}

	public void setTotal_major_reject(Integer[] total_major_reject) {
		this.total_major_reject = total_major_reject;
	}

	public String[] getTotal_major_reject_rate() {
		return total_major_reject_rate;
	}

	public void setTotal_major_reject_rate(String[] total_major_reject_rate) {
		this.total_major_reject_rate = total_major_reject_rate;
	}

	public Integer[] getCurr_major_reject() {
		return curr_major_reject;
	}

	public void setCurr_major_reject(Integer[] curr_major_reject) {
		this.curr_major_reject = curr_major_reject;
	}

	public String[] getCurr_major_reject_rate() {
		return curr_major_reject_rate;
	}

	public void setCurr_major_reject_rate(String[] curr_major_reject_rate) {
		this.curr_major_reject_rate = curr_major_reject_rate;
	}

	public Integer[] getTotal_build_reject() {
		return total_build_reject;
	}

	public void setTotal_build_reject(Integer[] total_build_reject) {
		this.total_build_reject = total_build_reject;
	}

	public String[] getTotal_build_reject_rate() {
		return total_build_reject_rate;
	}

	public void setTotal_build_reject_rate(String[] total_build_reject_rate) {
		this.total_build_reject_rate = total_build_reject_rate;
	}

	public Integer[] getCurr_build_reject() {
		return curr_build_reject;
	}

	public void setCurr_build_reject(Integer[] curr_build_reject) {
		this.curr_build_reject = curr_build_reject;
	}

	public String[] getCurr_build_reject_rate() {
		return curr_build_reject_rate;
	}

	public void setCurr_build_reject_rate(String[] curr_build_reject_rate) {
		this.curr_build_reject_rate = curr_build_reject_rate;
	}

	public Integer[] getTotal_maintain_reject() {
		return total_maintain_reject;
	}

	public void setTotal_maintain_reject(Integer[] total_maintain_reject) {
		this.total_maintain_reject = total_maintain_reject;
	}

	public String[] getTotal_maintain_reject_rate() {
		return total_maintain_reject_rate;
	}

	public void setTotal_maintain_reject_rate(
			String[] total_maintain_reject_rate) {
		this.total_maintain_reject_rate = total_maintain_reject_rate;
	}

	public Integer[] getCurr_maintain_reject() {
		return curr_maintain_reject;
	}

	public void setCurr_maintain_reject(Integer[] curr_maintain_reject) {
		this.curr_maintain_reject = curr_maintain_reject;
	}

	public String[] getCurr_maintain_reject_rate() {
		return curr_maintain_reject_rate;
	}

	public void setCurr_maintain_reject_rate(String[] curr_maintain_reject_rate) {
		this.curr_maintain_reject_rate = curr_maintain_reject_rate;
	}

	public Integer[] getTotal_over() {
		return total_over;
	}

	public void setTotal_over(Integer[] total_over) {
		this.total_over = total_over;
	}

	public Integer[] getCurr_over() {
		return curr_over;
	}

	public void setCurr_over(Integer[] curr_over) {
		this.curr_over = curr_over;
	}

	public String[] getTotal_over_rate() {
		return total_over_rate;
	}

	public void setTotal_over_rate(String[] total_over_rate) {
		this.total_over_rate = total_over_rate;
	}

	public Integer[] getTotal_send() {
		return total_send;
	}

	public void setTotal_send(Integer[] total_send) {
		this.total_send = total_send;
	}

	public Integer[] getCurr_send() {
		return curr_send;
	}

	public void setCurr_send(Integer[] curr_send) {
		this.curr_send = curr_send;
	}

	public String[] getTotal_send_rate() {
		return total_send_rate;
	}

	public void setTotal_send_rate(String[] total_send_rate) {
		this.total_send_rate = total_send_rate;
	}

	public Integer[] getTotal_upgrade() {
		return total_upgrade;
	}

	public void setTotal_upgrade(Integer[] total_upgrade) {
		this.total_upgrade = total_upgrade;
	}

	public Integer[] getCurr_upgrade() {
		return curr_upgrade;
	}

	public void setCurr_upgrade(Integer[] curr_upgrade) {
		this.curr_upgrade = curr_upgrade;
	}

	public String[] getTotal_upgrade_rate() {
		return total_upgrade_rate;
	}

	public void setTotal_upgrade_rate(String[] total_upgrade_rate) {
		this.total_upgrade_rate = total_upgrade_rate;
	}

	public Integer[] getTotal_complaint() {
		return total_complaint;
	}

	public void setTotal_complaint(Integer[] total_complaint) {
		this.total_complaint = total_complaint;
	}

	public Integer[] getCurr_complaint() {
		return curr_complaint;
	}

	public void setCurr_complaint(Integer[] curr_complaint) {
		this.curr_complaint = curr_complaint;
	}

	public String[] getTotal_complaint_rate() {
		return total_complaint_rate;
	}

	public void setTotal_complaint_rate(String[] total_complaint_rate) {
		this.total_complaint_rate = total_complaint_rate;
	}

	public Double[] getCurr_wcdma_impr() {
		return curr_wcdma_impr;
	}

	public void setCurr_wcdma_impr(Double[] curr_wcdma_impr) {
		this.curr_wcdma_impr = curr_wcdma_impr;
	}

	public Double[] getCurr_gsm_impr() {
		return curr_gsm_impr;
	}

	public void setCurr_gsm_impr(Double[] curr_gsm_impr) {
		this.curr_gsm_impr = curr_gsm_impr;
	}

	

}
