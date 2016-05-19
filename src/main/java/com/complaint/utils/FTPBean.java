package com.complaint.utils;

/**
 * <p>
 * Title: FTPBean.java
 * </p>
 * 
 * @author tian.bo
 * @version 1.0
 */
public class FTPBean {

	/**
	 * ftp服务器的ip地址
	 */
	private String host;

	/**
	 * ftp服务器的端口
	 */
	private int port;

	/**
	 * ftp用户名
	 */
	private String username;

	/**
	 * ftp密码
	 */
	private String password;

	public FTPBean() {
		
	}

	public FTPBean(String host, int port, String username, String password) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
