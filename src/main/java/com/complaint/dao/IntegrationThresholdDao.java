package com.complaint.dao;

import java.util.List;

import com.complaint.model.EvaluateRule;
import com.complaint.model.RevisRule;
import com.complaint.model.ScoreRule;

public interface IntegrationThresholdDao {
	/**
	 * 页面初始化时查询
	 * @return
	 */
//	计分规则
	List<ScoreRule> getScoreRule();
//	修正规则
	List<RevisRule> getRevisRule();
//	评分规则
	List<EvaluateRule> getEvaluateRule();
	
	/**
	 * 数据修改
	 */
//	计分规则修改
	void updateScoreRule(ScoreRule scoreRule);
//	修正规则修改
	void updateRevisRule(RevisRule revisRule);
//	评分规则修改
	void updateEvaluateRule(EvaluateRule evaluateRule);
}
