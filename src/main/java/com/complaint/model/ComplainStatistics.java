package com.complaint.model;

public class ComplainStatistics {
	/**保留值*/
//	测试时间段
	private String[] query_date;
//	区域id
	private Integer[] area_id;
	/**使用值*/
//	区域名称
	private String[] area_name;
//	累积工单量
	private Integer[] total_workorder;
//	当前工单量
	private Integer[] curr_workorder;
//	累积实测量
	private Integer[] total_test;
//	当前实测量
	private Integer[] curr_test;
//	累积实测率
	private String[] total_test_rate;
//	当前实测排名
	private Integer[] curr_test_rank;
//	累积实测排名
	private Integer[] total_test_rate_rank;
//	当前室内测试量
	private Integer[] curr_in_test;
//	当前室外测试量
	private Integer[] curr_out_test;
	public String[] getQuery_date() {
		return query_date;
	}
	public void setQuery_date(String[] query_date) {
		this.query_date = query_date;
	}
	public Integer[] getArea_id() {
		return area_id;
	}
	public void setArea_id(Integer[] area_id) {
		this.area_id = area_id;
	}
	public String[] getArea_name() {
		return area_name;
	}
	public void setArea_name(String[] area_name) {
		this.area_name = area_name;
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
	public Integer[] getCurr_test_rank() {
		return curr_test_rank;
	}
	public void setCurr_test_rank(Integer[] curr_test_rank) {
		this.curr_test_rank = curr_test_rank;
	}
	public Integer[] getTotal_test_rate_rank() {
		return total_test_rate_rank;
	}
	public void setTotal_test_rate_rank(Integer[] total_test_rate_rank) {
		this.total_test_rate_rank = total_test_rate_rank;
	}
	public Integer[] getCurr_in_test() {
		return curr_in_test;
	}
	public void setCurr_in_test(Integer[] curr_in_test) {
		this.curr_in_test = curr_in_test;
	}
	public Integer[] getCurr_out_test() {
		return curr_out_test;
	}
	public void setCurr_out_test(Integer[] curr_out_test) {
		this.curr_out_test = curr_out_test;
	}

	
}