package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.ComplainProbability;

/**
 * 投诉率与投诉统计
 * @author peng
 *
 */
public interface ComplainStatisticsDao {
	
	public List<ComplainProbability> getComplain(Map<String, Object> map);
	
}
