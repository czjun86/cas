package com.complaint.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.CollectionUtils;

import com.complaint.dao.LLOffsetDao;
import com.complaint.dao.ResourceDao;
import com.complaint.listener.CustomServletContextListener;
import com.complaint.model.LLOffset;
import com.complaint.model.Resource;
import com.complaint.utils.MapUtil;

public class SecurityMetadataSource extends CustomServletContextListener implements FilterInvocationSecurityMetadataSource{

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    @Autowired
    private ResourceDao resourceDao;

	@Autowired
	private LLOffsetDao llOffsetDao;
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    public boolean supports(Class<?> clazz) {  
        // TODO Auto-generated method stub  
        return true;  
    }  
    //加载所有资源与权限的关系 
    private void loadResourceDefine(){
    	//如果要使用数据库纠偏则需初始化加载地图的偏移量，则需放开该注释
    	if(CollectionUtils.isEmpty(MapUtil.getOffsetMap())){
    		//initLLMap();    		
    	}
    	try
    	{
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            List<Resource> resources = this.resourceDao.getAllResources();
            List<String> rolenames = null;
            for (Resource resource : resources) {  
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                ConfigAttribute configAttribute = null;
                rolenames = this.resourceDao.getRolenames(resource.getResourceid());
                for(String rolename : rolenames)
                {
                	configAttribute = new SecurityConfig(rolename);
                	configAttributes.add(configAttribute);
                }
                resourceMap.put(resource.getUrl(), configAttributes);  
            }  
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }  
    //返回所请求资源所需要的权限  
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {  
          
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        requestUrl = removeUrlParams(requestUrl);
        if(resourceMap == null) {  
            loadResourceDefine();  
        }
        return resourceMap.get(requestUrl);  
    }
    
    public void refreshResourceDefine(){
    	this.loadResourceDefine();

    	
    }
    /**
     * 初始化地图经纬度信息
     */
    public void initLLMap(){
		int count = llOffsetDao.countAll();
		int pageSize = 2000;
		int totalPage = count%pageSize ==0 ? count/pageSize : count/pageSize +1;
		Map<String, Object> map = null;
		List<LLOffset> list = new ArrayList<LLOffset>();
		for (int i = 1; i <= totalPage; i++) {
			map = new HashMap<String, Object>();
			map.put("lbound", (i-1)*pageSize);
			map.put("mbound", i*pageSize);
			list.addAll(llOffsetDao.queryBypage(map));
		}
		Map<String, LLOffset> llmap = new HashMap<String, LLOffset>();
		for (LLOffset llOffset : list) {
			String ll = String.valueOf(llOffset.getLng().doubleValue())+"X"+String.valueOf(llOffset.getLat().doubleValue());
			if(!llmap.containsKey(ll)){
				llmap.put(ll, llOffset);
			}
		}
		MapUtil.setOffsetMap(llmap);
	}
    private String removeUrlParams(String requestUrl){
    	if(StringUtils.isEmpty(requestUrl))
    		return StringUtils.EMPTY;
    	else if(requestUrl.indexOf("?") == -1)
    		return requestUrl;
    	else
    		return requestUrl.substring(0,requestUrl.indexOf("?"));
    }
    
}
