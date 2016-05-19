package com.complaint.dao;

import com.complaint.model.WcdmsTrackLog;

public interface WcdmsTaskLogDao extends BatchDao{
	void insert(WcdmsTrackLog wcdmsTrackLog);
	void delTaskWcdmaByFlowid(String flowid);
}
