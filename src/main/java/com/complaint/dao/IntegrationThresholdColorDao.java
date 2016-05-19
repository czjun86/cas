package com.complaint.dao;

import java.util.List;

import com.complaint.model.RateColor;

public interface IntegrationThresholdColorDao {
//	初始化页面时，查出颜色数据
	List<RateColor> queryColorCodes();
//	用于保存颜色
	void update(RateColor newColor);
}
