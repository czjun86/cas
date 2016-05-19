package com.complaint.model;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class TFtpConfig implements java.io.Serializable,JSONStreamAware{
	private Integer id;
	private String username;//用户名
	private String pwd;//密码
	private String encrypt_pwd;
	private String ip;//ip地址
	private Integer port;//端口
	private String file_name;//下行文件
	private Integer ftp_type;//业务类型
	private String key;
	private Integer status;//是否禁用
	private String file_size;//包大小
	private String file_dir;//上行路径
	private String server_name;//名称
	private String server_num;//编号
	private String remarker;//备注
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public String getServer_num() {
		return server_num;
	}
	public void setServer_num(String server_num) {
		this.server_num = server_num;
	}
	public String getRemarker() {
		return remarker;
	}
	public void setRemarker(String remarker) {
		this.remarker = remarker;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEncrypt_pwd() {
		return encrypt_pwd;
	}
	public void setEncrypt_pwd(String encrypt_pwd) {
		this.encrypt_pwd = encrypt_pwd;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public Integer getFtp_type() {
		return ftp_type;
	}
	public void setFtp_type(Integer ftp_type) {
		this.ftp_type = ftp_type;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getFile_dir() {
		return file_dir;
	}
	public void setFile_dir(String file_dir) {
		this.file_dir = file_dir;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	
	@Override
	public void writeJSONString(Writer out) throws IOException {
		// TODO Auto-generated method stub
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("name", this.username);
		obj.put("pwd", this.encrypt_pwd);
		obj.put("key", this.key);
		obj.put("ip", this.ip);
		obj.put("port", this.port);
		obj.put("port", this.port);
		obj.put("fname", this.file_name);
		obj.put("dir", this.file_dir);
		obj.put("size", this.file_size);
		JSONValue.writeJSONString(obj, out);
	}
}
