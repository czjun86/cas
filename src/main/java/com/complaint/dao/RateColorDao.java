package com.complaint.dao;

import java.util.List;

import com.complaint.model.RateColor;

public interface RateColorDao {
	List<RateColor> queryRateColors();
	void update(RateColor ratecolor);
}
