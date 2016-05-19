package com.complaint.model;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

public class Scene implements java.io.Serializable,JSONStreamAware{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Short sceneid;

    private Short inside;

    private Short type;
    
    private String insidetype;

    private String name;
    

	public Short getSceneid() {
		return sceneid;
	}

	public void setSceneid(Short sceneid) {
		this.sceneid = sceneid;
	}

	public Short getInside() {
		return inside;
	}

	public void setInside(Short inside) {
		this.inside = inside;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getInsidetype() {
		return insidetype;
	}

	public void setInsidetype(String insidetype) {
		this.insidetype = insidetype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void writeJSONString(Writer out) throws IOException {
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("id", this.sceneid);
		obj.put("is", this.inside);
		obj.put("cg", this.type);
		obj.put("it", this.insidetype);
		obj.put("sc", this.name);
		JSONValue.writeJSONString(obj, out);
	}
}
