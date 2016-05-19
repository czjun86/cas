package com.complaint.dao;

import com.complaint.model.LogSubmanualLte;

public interface LteTrackLogDao extends BatchDao{
	void insert(LogSubmanualLte logSubmanualLte);
	void delLteByFlowid(String flowid);
}
