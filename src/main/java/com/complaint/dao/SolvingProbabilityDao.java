package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.SolvingProbability;

/**
 * 问题解决率
 * @author peng
 *
 */
public interface SolvingProbabilityDao {

	public List<SolvingProbability> getSolvingProbability(Map<String, Object> map);
	
}
