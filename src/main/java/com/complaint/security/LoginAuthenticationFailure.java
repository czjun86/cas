package com.complaint.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

public class LoginAuthenticationFailure implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter printOut = response.getWriter();
		try
		{
		if(exception instanceof UsernameNotFoundException)
			printOut.write("{\"status\":0,\"msg\":\"该用户名不存在\"}");
		else if(exception instanceof BadCredentialsException)
			printOut.write("{\"status\":0,\"msg\":\"用户名或密码错误!\"}");
		else if(exception instanceof LockedException)
			printOut.write("{\"status\":0,\"msg\":\"当前用户账号已经被锁定!\"}");
		else if(exception instanceof SessionAuthenticationException)
			printOut.write("{\"status\":0,\"msg\":\"当前用户已登陆,登陆失败!\"}");
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			printOut.flush();
			printOut.close();
		}
	}
}
