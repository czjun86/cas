package com.complaint.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;
import org.springframework.security.core.userdetails.UserDetails;

import com.complaint.security.Authority;

public class User implements java.io.Serializable,UserDetails,JSONStreamAware{

	private static final long serialVersionUID = 1L;

	private Integer userid;
	private String userName;
	private Integer sex;
	private String password;
	private String name;
	private Integer roleid;
	private Integer islock;
	private String email;
	private String phone;
	private String rolename;
	private List<Authority> authorities = new ArrayList<Authority>();

	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public User() {
		super();
	}
	public User(Integer userid, String userName, Integer sex, String password,
			String name, Integer roleid, Integer islock, String email,
			String phone, String rolename) {
		super();
		this.userid = userid;
		this.userName = userName;
		this.sex = sex;
		this.password = password;
		this.name = name;
		this.roleid = roleid;
		this.islock = islock;
		this.email = email;
		this.phone = phone;
		this.rolename = rolename;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public Integer getIslock() {
		return islock;
	}

	public void setIslock(Integer islock) {
		this.islock = islock;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<Authority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if(this.islock == null || this.islock == 1){
			return false;
		}
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public int hashCode(){
		return this.userid.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		User user = (User)obj;
		return this.userid.equals(user.userid);
	}
	@Override
	public void writeJSONString(Writer out) throws IOException {
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("id", this.userid);
		obj.put("email", this.email);
		JSONValue.writeJSONString(obj, out);
	}
}
