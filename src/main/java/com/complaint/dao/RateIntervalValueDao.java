package com.complaint.dao;

import java.util.List;

import com.complaint.model.RateValue;
import com.complaint.model.Ratekpi;

public interface RateIntervalValueDao {
	List<RateValue> getAllRateValue();
	List<Ratekpi> getAllRatekpi();
	void update(RateValue rateValue);
}
