package com.complaint.model;
/**
 * 修正规则
 * @author peng
 *
 */
public class RevisRule {
//	修正模式	1为项目修正		2为自由模式
	private int revis_type;
	private int left_rate;
	private int right_rate;
	private int revis_level;
//	code按优良中差对应1234
	private int rank_code;
	private int net_type;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRevis_type() {
		return revis_type;
	}
	public void setRevis_type(int revis_type) {
		this.revis_type = revis_type;
	}
	public int getLeft_rate() {
		return left_rate;
	}
	public void setLeft_rate(int left_rate) {
		this.left_rate = left_rate;
	}
	public int getRight_rate() {
		return right_rate;
	}
	public void setRight_rate(int right_rate) {
		this.right_rate = right_rate;
	}
	public int getRank_code() {
		return rank_code;
	}
	public void setRank_code(int rank_code) {
		this.rank_code = rank_code;
	}
	public int getNet_type() {
		return net_type;
	}
	public void setNet_type(int net_type) {
		this.net_type = net_type;
	}
	public int getRevis_level() {
		return revis_level;
	}
	public void setRevis_level(int revis_level) {
		this.revis_level = revis_level;
	}
	
}
