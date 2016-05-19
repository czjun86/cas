package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.Resource;
import com.complaint.model.Role;

public interface ResourceDao{
	
	public List<Resource> getAllResources();
	
	public List<String> getRolenames(int resourceId);
	
	public List<Role> getAllRoles();
	
	public List<Resource> getResourcesByRole(int roleid);
	
	void deleteRoleById(int roleId);
	
	void insertRoleToResource(Map<String, Object> params);
}
