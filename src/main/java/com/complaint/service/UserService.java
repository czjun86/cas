package com.complaint.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.BaseDao;
import com.complaint.dao.EpinfoDao;
import com.complaint.dao.UserDao;
import com.complaint.model.User;
import com.complaint.page.PageBean;

@Service("userSerivce")
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private BaseDao baseDao;
	
	public int addUser(User user){
		try {
			this.userDao.insertUser(user);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	public int deleteUser(String ids){
		try {
			String[] idStrs = ids.split(",");
			for (String id : idStrs) {				
				this.userDao.deleteUser(Integer.parseInt(id));
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	public PageBean getUserList(int pageSize,int pageIndex,String name){
		PageBean pb = new PageBean();
		
		return pb;
	}
	public PageBean getUsers(String name,int pageIndex,int pageSize){
		int lbound = (pageIndex-1)*pageSize;
		int mbound = pageIndex*pageSize;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("lbound", lbound);
		param.put("mbound", mbound);
		List<User> list = this.userDao.queryUserList(param);
		int count = this.userDao.queryUserListCount(name);
		PageBean pb = new PageBean();
		pb.setList(list);
		pb.setPageIndex(pageIndex);
		pb.setPageSize(pageSize);
		pb.setTotalPage(count);
		return pb;
	}
	public PageBean getUserList(String name,int pageIndex,int pageSize){
		int lbound = (pageIndex-1)*pageSize;
		int mbound = pageIndex*pageSize;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("lbound", lbound);
		param.put("mbound", mbound);
		List<User> list = this.userDao.queryUserList(param);
		PageBean pb = new PageBean();
		pb.setList(list);
		return pb;
	}
	public PageBean getUsersCount(String name,int pageIndex,int pageSize){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		int count = this.userDao.queryUserListCount(name);
		PageBean pb = new PageBean();
		pb.setPageIndex(pageIndex);
		pb.setPageSize(pageSize);
		pb.setTotalPage(count);
		return pb;
	}
	public User getUserById(Integer id){
		return this.userDao.queryUserById(id);
	}
	public int updateUser(User user){
		try {
			this.userDao.updateUser(user);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int updateUserInfo(User user){
		try {
			this.userDao.updateUserInfo(user);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int updateUserPsw(Integer id,String password){
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			param.put("password", password);
			this.userDao.updateUserPsw(param);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	public User getUserByUserName(String userName){
		return this.userDao.queryUserByUsername(userName);
	}
	public List<User> getUsersByUserName(String userName){
		return this.userDao.queryUsersByUserName(userName);
	}
	
	/**
	 * 获取用户对应的区域
	 */
	public String getAreas(User user){
		return this.userDao.getAreas(user);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateArea(User user ,String areas) throws Exception{
		this.userDao.deleteAreas(user);
		String [] areaids = areas.split(",");
		List<Map> list = new ArrayList<Map>();
		Map map = null;
		for(String areaid : areaids){
			if(areaid!=null && !("".equals(areaid))){
				map = new HashMap();
				map.put("userid", user.getUserid());
				map.put("areaid", areaid);
				list.add(map);
			}
		}
		this.baseDao.batchInsert(UserDao.class, list, 200);
	}
}
