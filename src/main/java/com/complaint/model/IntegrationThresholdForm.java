package com.complaint.model;

public class IntegrationThresholdForm {
	private int [] rank_score;
//	按+ , , -分为ABC
	private int [] evaluate_scoreA;
	private int [] evaluate_scoreB;
	private int [] evaluate_scoreC;
	private int [] revis_left;
	private int [] revis_right;
	private int [] revis_level;
	public int[] getRank_score() {
		return rank_score;
	}
	public void setRank_score(int[] rank_score) {
		this.rank_score = rank_score;
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
	
}
