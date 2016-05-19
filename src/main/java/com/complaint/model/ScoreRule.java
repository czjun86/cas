package com.complaint.model;
/**
 * 计分规则
 * @author peng
 *
 */
public class ScoreRule {
//	code优良中差对应1234
	private int rank_code;
	private int rank_score;
//	室内是1室外是0
	private int scene;
//	type类型1为指标阈值，2为综合阈值
	private int type;
	public int getRank_code() {
		return rank_code;
	}
	public void setRank_code(int rank_code) {
		this.rank_code = rank_code;
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
