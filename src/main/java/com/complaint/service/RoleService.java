package com.complaint.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.RoleDao;
import com.complaint.model.Role;
import com.complaint.page.PageBean;
@Service("roleService")
public class RoleService {
	@Autowired
	private RoleDao roleDao;

	public Role getRoleById(Integer roleid) {
		return this.roleDao.queryRoleById(roleid);
	}

	public int addRole(Role role) {
		try {
			this.roleDao.insertRole(role);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	@Transactional(rollbackFor=Exception.class)
	public int deleteRole(String ids)throws Exception {
		String[] idStrs = ids.split(",");
		for (String id : idStrs) {
			this.roleDao.updateUserRoleId(Integer.parseInt(id));
			this.roleDao.deleteRole(Integer.parseInt(id));
		}
		return 1;
	}

	public int updateRole(Role role) {
		try {
			this.roleDao.updateRole(role);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public PageBean getRoleList(String rolename, int pageIndex, int pageSize) {
		int lbound = (pageIndex - 1) * pageSize;
		int mbound = pageIndex * pageSize;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rolename", rolename);
		param.put("lbound", lbound);
		param.put("mbound", mbound);
		List<Role> list = this.roleDao.queryRoleList(param);
		PageBean pb = new PageBean();
		pb.setList(list);
		return pb;
	}

	public PageBean countRoles(String rolename, int pageIndex, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rolename", rolename);
		int count = this.roleDao.countRoles(rolename);
		PageBean pb = new PageBean();
		pb.setPageIndex(pageIndex);
		pb.setPageSize(pageSize);
		pb.setTotalPage(count);
		return pb;
	}
	
	public List<Role> queryRoles(){
		return this.roleDao.queryRoles();
	}
	public boolean isExsit(String rolename){
		int count = this.roleDao.countRoleByname(rolename);
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}
}
