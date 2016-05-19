package com.complaint.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.complaint.dao.ResourceDao;
import com.complaint.model.Resource;
import com.complaint.model.Role;
import com.complaint.security.SecurityMetadataSource;

public class RoleResourceLoader {
	
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private SecurityMetadataSource securityMetadataSourceTemplate;
	
	private Map<String, List<Resource>> roleResources = new HashMap<String, List<Resource>>();
	
	public void loadResources(){
		List<Role> roles = resourceDao.getAllRoles();
		List<Resource> resources = null;
		for(Role role : roles){
			resources = this.resourceDao.getResourcesByRole(role.getRoleid());
			roleResources.put(role.getRolename(), resources);
		}
	}
	public List<Resource> getRoleResources(String rolename) {
		if(CollectionUtils.isEmpty(roleResources)){			
			this.loadResources();
		}
		return this.roleResources.get(rolename);
	}
	
	public void refreshRoleResource(){
		this.loadResources();
		this.securityMetadataSourceTemplate.refreshResourceDefine();
	}
	
	public Integer getTopMenuIdByUrl(String rolename,String url){
		List<Resource> resources = this.getRoleResources(rolename);
		for(Resource resource : resources){
			if(url.contains(resource.getUrl())){				
				return resource.getResourceid();
			}
		}
		return null;
	}
	public List<Resource> getButtonsByUrl(String rolename,String url){
		List<Resource> resourceList = new ArrayList<Resource>();
		List<Resource> resources = this.getRoleResources(rolename);
		Integer id = null; 
		if(resources!= null && resources.size() > 0){
			for(Resource resource : resources){			
				if(url.contains(resource.getUrl())){				
					id = resource.getResourceid();
					break;
				}
			}
			if(id != null){
				for (Resource temp : resources) {
					if(temp.getParentid().intValue() == id.intValue()){				
						resourceList.add(temp);
					}
				}
			}
		}
		return resourceList;
	}
	
	public List<Resource> getButtons(HttpServletRequest request){
		String rolename = SessionUtils.getRolenameByRequest(request);
		List<Resource> buttons = getButtonsByUrl(rolename, request.getRequestURI());
		return buttons;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int updateAccess(Integer roleId,String ids) throws Exception{
		this.resourceDao.deleteRoleById(roleId);
		if(StringUtils.isNotEmpty(ids)){
			String[] idsStr = ids.split(",");
			Map<String, Object> params = null;
			for (int i = 0; i < idsStr.length; i++) {
				params = new HashMap<String, Object>();
				params.put("roleid", roleId);
				params.put("resourceid", Integer.parseInt(idsStr[i]));
				this.resourceDao.insertRoleToResource(params);
			}
		}
		return 1;
	}
}
