package com.complaint.service;
/**
 * 综合阈值数据事务处理
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.IntegrationThresholdDao;
import com.complaint.model.EvaluateRule;
import com.complaint.model.IntegrationThresholdForm;
import com.complaint.model.RevisRule;
import com.complaint.model.ScoreRule;
@Service("integrationThresholdService")

public class IntegrationThresholdService {
	@Autowired
	private IntegrationThresholdDao integrationThresholdDao;
	
	private static final Logger logger = LoggerFactory.getLogger(IntegrationThresholdService.class);
	
	
	
	/**
	 * 初始化综合页面值查询,计分规则,修正规则,评分规则
	 * @return
	 */
	public List<ScoreRule> getScroeRule(){
		return integrationThresholdDao.getScoreRule();
	}
	public List<RevisRule> getRevisRule(){
		return integrationThresholdDao.getRevisRule();
	}
	public List<EvaluateRule> getEvaluateRule(){
		return integrationThresholdDao.getEvaluateRule();
	}
	/**
	 * 初始化综合页面阈值
	 * @return
	 */
	public Map getIntegrationThreshold(){
		Map map = new HashMap();
		map.put("scoreRule", this.getScroeRule());
		map.put("revisRule", this.getRevisRule());
		map.put("evaluateRule", this.splitEvaluateRule(this.getEvaluateRule()));
		return map;
	}
	/**
	 * 将查询的数据按等级符号分组
	 */
	public Map splitEvaluateRule(List<EvaluateRule> evaluateRules){
		Map map = new HashMap();
		List<EvaluateRule> a = new ArrayList<EvaluateRule>();
		List<EvaluateRule> b = new ArrayList<EvaluateRule>();
		List<EvaluateRule> c = new ArrayList<EvaluateRule>();
		for(EvaluateRule evaluateRule:evaluateRules){
			int i = evaluateRule.getRank_code_sub();
//			i==1为+
			if(i==1){
				a.add(evaluateRule);
			}
//			i==2为无符号
			if(i==2){
				b.add(evaluateRule);
			}
//			i==3为负号
			if(i==3){
				c.add(evaluateRule);
			}
		}
		map.put("A", a);
		map.put("B", b);
		map.put("C", c);
		return map;
	}
	/**
	 * 进行数据修改
	 * @param form
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String updateIntegrationValue(IntegrationThresholdForm form) throws Exception{
		int rank =0;
				rank = this.updateScoreRule(form.getRank_score());
			if(rank !=1){
				//return "计分规则数据保存失败";
				return "b";
			}
		int Evaluate = 0;
				Evaluate = this.updateRevisRule(form.getRevis_left(), form.getRevis_right(), form.getRevis_level());
			if(Evaluate !=1){
				//return "修正规则数据保存失败";	
				return "b";
			}
		int revis =0;
				revis = this.updateEvaluateRule(form.getEvaluate_scoreA(),form.getEvaluate_scoreB(),form.getEvaluate_scoreC());
			if(revis !=1){
				//return "评分规则数据保存失败";
				return "b";
			}
		//return "数据保存成功";
		return "a";
	}
	
	/**
	 * 进行计分规则的修改
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateScoreRule(int[] rank_score)throws Exception{
		ScoreRule scoreRule = null;
			for(int i = 0;i<rank_score.length;i++){
//				对ScoreRule数据放好，准备修改
				scoreRule = new ScoreRule();
				scoreRule.setRank_code(i+1);
				scoreRule.setRank_score(rank_score[i]);
//				没有分室内为，存放默认为室外
				scoreRule.setScene(0);
//				综合阈值type为2
				scoreRule.setType(2);
				
//				进行修改
				integrationThresholdDao.updateScoreRule(scoreRule);
			}
		return 1;
	}
	
	/**
	 * 进行修正规则的修改
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateRevisRule(int[] revis_left,int[] revis_right,int[] revis_level)throws Exception{
		RevisRule revisRule = null;
		for(int i=0;i<revis_right.length;i++){
			revisRule =new RevisRule();
			revisRule.setId(i+1);
			revisRule.setLeft_rate(revis_left[i]);
			revisRule.setRight_rate(revis_right[i]);
			revisRule.setRevis_level(revis_level[i]);
			if(i<2){
			revisRule.setRevis_type(1);
			}else{
			revisRule.setRevis_type(2);
			}
				//revisRule.setRank_code(rank_code);
				//综合模式type为2
			revisRule.setNet_type(2);
			
			integrationThresholdDao.updateRevisRule(revisRule);
			
		}
		return 1;
	}
	
	/**
	 * 进行评分规则的修改
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateEvaluateRule(int[] evaluate_scoreA,int[] evaluate_scoreB,int[] evaluate_scoreC)throws Exception{
		EvaluateRule evaluateRule = null;
			//+类型
		for(int i = 0;i<4;i++){
			evaluateRule = new EvaluateRule();
			evaluateRule.setRank_code(i+1);
			evaluateRule.setRank_code_sub(1);
			evaluateRule.setRank_score(evaluate_scoreA[i]);
				//室外暂时存放都默认为0
			evaluateRule.setScene(0);
				//综合阈值为2
			evaluateRule.setType(2);
			integrationThresholdDao.updateEvaluateRule(evaluateRule);
		}
			//无符号类型
		for(int i = 0;i<4;i++){
			evaluateRule = new EvaluateRule();
			evaluateRule.setRank_code(i+1);
			evaluateRule.setRank_code_sub(2);
			evaluateRule.setRank_score(evaluate_scoreB[i]);
				//室外暂时存放都默认为0
			evaluateRule.setScene(0);
				//综合阈值为2
			evaluateRule.setType(2);
			integrationThresholdDao.updateEvaluateRule(evaluateRule);
		}
			//-类型
		for(int i = 0;i<4;i++){
			evaluateRule = new EvaluateRule();
			evaluateRule.setRank_code(i+1);
			evaluateRule.setRank_code_sub(3);
			evaluateRule.setRank_score(evaluate_scoreC[i]);
				//室外暂时存放都默认为0
			evaluateRule.setScene(0);
				//综合阈值为2
			evaluateRule.setType(2);
			integrationThresholdDao.updateEvaluateRule(evaluateRule);
		}
		return 1;
	}
}
