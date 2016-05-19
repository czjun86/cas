package com.complaint.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.complaint.security.Authority;
import com.complaint.dao.UserDao;
import com.complaint.model.User;

public class LoginService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = this.userDao.queryUserByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("该用户名不存在!");
		Authority authority = new Authority();
		authority.setAuthority(user.getRolename());
		user.getAuthorities().add(authority);
		return user;
	}
}
