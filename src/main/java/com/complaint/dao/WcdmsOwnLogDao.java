package com.complaint.dao;

import com.complaint.model.WcdmsTrackLog;

public interface WcdmsOwnLogDao extends BatchDao{
	void insert(WcdmsTrackLog wcdmsTrackLog);
	void delOwnWcdmaByFlowid(String flowid);
}
