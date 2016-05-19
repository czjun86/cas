package com.complaint.dao;

import com.complaint.model.WcdmsTrackLog;

public interface WcdmsTrackLogDao extends BatchDao{
	void insert(WcdmsTrackLog wcdmsTrackLog);
	void delWcdmaByFlowid(String flowid);
}
