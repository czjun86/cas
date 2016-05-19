package com.complaint.service;

import java.io.StringWriter;
import java.util.List;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.SceneDao;
import com.complaint.model.Scene;

@Service("sceneService")
public class SceneService {
	
	private static final Logger logger = LoggerFactory.getLogger(SceneService.class);
	
	@Autowired
	private SceneDao sceneDao;

	@SuppressWarnings("unchecked")
	public String queryAll(){
		
		String rtnMsg = "";
		try
		{
			List<Scene> sceneList = this.sceneDao.queryAll();
			JSONArray json = new JSONArray();
			json.addAll(sceneList);
		    StringWriter out = new StringWriter();
		    json.writeJSONString(out);
		    rtnMsg = out.toString();
		    logger.debug("返回场景信息:" + rtnMsg);
		  System.out.println(out.toString());
		}catch(Exception ex){
			logger.error("",ex);
		}
		return rtnMsg;
	}
	
	public List<Scene> queryScene(){
		return this.sceneDao.queryAll();
		
	}
}
