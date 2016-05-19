package com.complaint.dao;

import com.complaint.model.KpiIntervalValue;
import java.util.List;
import java.util.Map;


public interface KpiIntervalValueDao {


    int insert(KpiIntervalValue kpiIntervalValue);

    int insertSelective(KpiIntervalValue kpiIntervalValue);
    
    List<KpiIntervalValue> queryAllKipIntervalValues();
    List<KpiIntervalValue> queryAllKpi();
    
    void update(Map<String, Object> map);
}
