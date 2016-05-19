package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.TFtpConfig;

public interface Ftp4gMapper {
	
	public List<TFtpConfig> queryFtp();
	
	public void insertFtp(TFtpConfig tf);
	
	public Integer countPage(Map map);
	public List<TFtpConfig> getTFtpConfig(Map map);
	public List<TFtpConfig> getQueryFtpByNum(String num);
	
	public void deleteftp(Integer id);
	public TFtpConfig searchById(Integer id);
	public void updateFtp(TFtpConfig tf);
	public void staUse(Integer id);
	public void staClose();
	public void staOff(Integer id);
}
