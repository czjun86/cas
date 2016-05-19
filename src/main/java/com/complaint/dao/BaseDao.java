package com.complaint.dao;

import java.util.List;

public interface BaseDao {
	
	public void batchInsert(Class<?> type,List<?> list,Integer batchNum) throws Exception;
}
