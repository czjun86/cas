package com.complaint.dao;

import com.complaint.model.LLOffset;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LLOffsetDao {


    int insert(LLOffset lLOffset);

    int insertSelective(LLOffset lLOffset);

    List<LLOffset> queryAll();
    
    int countAll();
    
    List<LLOffset> queryBypage(Map<String, Object> map);
}
