package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.Role;

public interface RoleDao extends BatchDao{
	
	void insertRole(Role role);

	void deleteRole(int roleid);

	List<Role> queryRoleList(Map<String, Object> map);
	
	int countRoles(@Param(value="rolename") String rolename);
	int countRoleByname(@Param(value="rolename") String rolename);
	
	Role queryRoleById(int roleid);
	
	void updateRole(Role role);
	
	/**
	 * 删除角色后，把相关角色的用户设置成普通用户角色  
	 * 管理员的角色id为1
	 * 普通角色的id为2
	 * @param id
	 */
	void updateUserRoleId(int roleid);
	
	List<Role> queryRoles();
}
