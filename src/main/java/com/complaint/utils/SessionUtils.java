package com.complaint.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import com.complaint.model.User;

public class SessionUtils {
	public static User getUserByRequest(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		return user;
	}
	
	public static User getUserBySession(HttpSession session){
		User user = (User)session.getAttribute("user");
		return user;
	}
	
	public static String getUsernameByRequest(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null)
			return "";
		return user.getName();
	}
	
	public static String getUsernameBySession(HttpSession session){
		User user = (User)session.getAttribute("user");
		if(user == null)
			return "";
		return user.getName();
	}
	
	public static String getRolenameByRequest(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null)
			return "";
		return user.getRolename();
	}
	
	public static Integer getRoleidByRequest(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null)
			return null;
		return user.getRoleid();
	}
	
	public static String getRolenameBySession(HttpSession session){
		User user = (User)session.getAttribute("user");
		if(user == null)
			return "";
		return user.getRolename();
	}
}
