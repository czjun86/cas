package com.complaint.dao;

import com.complaint.model.LogSubmanualGsm;

public interface GsmOwnLogDao extends BatchDao{
	int deleteByPrimaryKey(String gsmid);

    int insertSelective(LogSubmanualGsm logSubmanualGsm);
    
    void insert(LogSubmanualGsm logSubmanualGsm);
    
    LogSubmanualGsm selectByPrimaryKey(String gsmid);



    int updateByPrimaryKeySelective(LogSubmanualGsm logSubmanualGsm);

    int updateByPrimaryKey(LogSubmanualGsm logSubmanualGsm);
    
    void delOwnGsmByFlowid(String flowid);
}
