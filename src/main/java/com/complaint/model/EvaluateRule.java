package com.complaint.model;
/**
 * 评分规则
 * @author peng
 *
 */
public class EvaluateRule {
//	按优良中差对应1234
	private int rank_code;
//	评分分为按+1,1，-1分为123
	private int rank_code_sub;
	private int rank_score;
//	室内-1 室外-0
	private int scene;
//	指标阈值-1 综合阈值-2
	private int type;
	public int getRank_code() {
		return rank_code;
	}
	public void setRank_code(int rank_code) {
		this.rank_code = rank_code;
	}
	public int getRank_code_sub() {
		return rank_code_sub;
	}
	public void setRank_code_sub(int rank_code_sub) {
		this.rank_code_sub = rank_code_sub;
	}
	public int getRank_score() {
		return rank_score;
	}
	public void setRank_score(int rank_score) {
		this.rank_score = rank_score;
	}
	public int getScene() {
		return scene;
	}
	public void setScene(int scene) {
		this.scene = scene;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
