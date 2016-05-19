package com.complaint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.complaint.model.User;

public interface UserDao extends BatchDao{
	
	void insertUser(User user);

	void deleteUser(int id);

	List<User> queryUserList(Map<String, Object> map);
	
	int queryUserListCount(@Param(value="name") String name);
	
	User queryUserByUsername(@Param(value="username") String username);
	
	List<User> queryUsersByUserName(@Param(value="username") String username);
	
	User queryUserById(int id);
	
	void updateUser(User user);
	
	void updateUserPsw(Map<String, Object> map);
	
	void updateUserInfo(User user);
	/**查询用户拥有区域**/
	String getAreas(User user);
	/**清空用户拥有区域**/
	void deleteAreas(User user); 
}
