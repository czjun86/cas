package com.complaint.model;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class Vision implements java.io.Serializable,JSONStreamAware{
public Short isup;
public String url;
public String declare;
public String vision;
public Short getIsup() {
	return isup;
}
public void setIsup(Short isup) {
	this.isup = isup;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getDeclare() {
	return declare;
}
public void setDeclare(String declare) {
	this.declare = declare;
}
public String getVision() {
	return vision;
}
public void setVision(String vision) {
	this.vision = vision;
}
@Override
public void writeJSONString(Writer out) throws IOException {
	// TODO Auto-generated method stub
	Map<String,Object> obj = new HashMap<String,Object>();
	obj.put("isup", this.isup);
	obj.put("url", this.url);
	obj.put("de", this.declare);
	obj.put("vi", this.vision);
	JSONValue.writeJSONString(obj, out);
}

}
