package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.CenterZoom;
import com.complaint.model.EvaluateRule;
import com.complaint.model.GradeBean;
import com.complaint.model.Rate;
import com.complaint.model.RateColor;
import com.complaint.model.RevisRule;
import com.complaint.model.ScoreRule;
import com.complaint.model.TCasCell;

public interface GisgradMapper {
	public List<GradeBean> noqueryindoor2(Map<String, Object> map);
	public List<GradeBean> noqueryindoor3(Map<String, Object> map);
	public List<GradeBean> noqueryoutdoor2(Map<String, Object> map);
	public List<GradeBean> noqueryoutdoor3(Map<String, Object> map);
	public List<Rate> showGradKpi();
	public List<RateColor> showGradColor();
	public List<EvaluateRule>  queryProject(Map<String, Object> map);
    public List<ScoreRule> queryKpi();
    public List<RevisRule>  queryProDrop();
    public CenterZoom  queryCenter(Map<String, Object> map);
    public CenterZoom  querydistence(Map<String, Object> map);
    /**
     * 
    
     * @Title: queryCells
    
     * @Description: 根据流水号查询测试连接的所有小区 
    
     * @param map
     * @return
    
     * @return: List<TWnmsCell>
     */
    public List<TCasCell> queryCells(Map<String, Object> map);
}
