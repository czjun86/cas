package com.complaint.dao;

import com.complaint.model.WnmsCell;
import com.complaint.model.WnmsCellKey;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WnmsCellDao {


    int deleteByPrimaryKey(WnmsCellKey key);

    int insert(WnmsCell wnmsCell);
    
    int countAll();
    
    List<WnmsCell> queryByPage(Map<String, Object> map);
    
    int insertSelective(WnmsCell wnmsCell);


    WnmsCell selectByPrimaryKey(WnmsCellKey key);



    int updateByPrimaryKeySelective(WnmsCell wnmsCell);

    int updateByPrimaryKey(WnmsCell wnmsCell);
}
