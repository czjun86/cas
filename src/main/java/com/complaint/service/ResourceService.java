package com.complaint.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.complaint.dao.ResourceDao;
import com.complaint.model.Resource;

@Service("resourceService")
public class ResourceService {

	@Autowired
	private ResourceDao resourceDao;
	
	public List<Resource> getAllResources(){
		return this.resourceDao.getAllResources();
	}
	public List<Resource> getResourcesByroleId(Integer roleid){
		return this.resourceDao.getResourcesByRole(roleid);
	}
}
