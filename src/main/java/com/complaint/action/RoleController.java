package com.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.AssignAccess;
import com.complaint.model.Resource;
import com.complaint.model.Role;
import com.complaint.page.PageBean;
import com.complaint.service.ResourceService;
import com.complaint.service.RoleService;
import com.complaint.utils.Constant;
import com.complaint.utils.RoleResourceLoader;
import com.complaint.utils.TokenProcessor;

@Controller
@RequestMapping("/role")
public class RoleController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleSerivce;
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	@Autowired
	private ResourceService resourceService;
	@RequestMapping(value = "/rolelist")
	public ModelAndView rolelist(String rolename,HttpServletRequest request){
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		ModelAndView mv = new ModelAndView("/role/rolelist");
		PageBean pb = this.roleSerivce.countRoles(rolename,1,Constant.PAGESIZE);
		mv.addObject("rolename", rolename);
		mv.addObject("pb", pb);
		mv.addObject("buttons", buttons);
		return mv;
	}
	@RequestMapping(value = "/rolelist/template")
	public String rolelistJson(Model model,String rolename,Integer pageIndex,HttpServletRequest request){
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		try {
			PageBean pb = this.roleSerivce.getRoleList(rolename,pageIndex,Constant.PAGESIZE);
			List<Role> list = (List<Role>) pb.getList();
			model.addAttribute("contextPath", request.getContextPath());
			model.addAttribute("list", list);
			model.addAttribute("buttons", buttons);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		return "/role/childlist";
	}
	@RequestMapping(value="/addRole", method = RequestMethod.GET)
	public ModelAndView addRole(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/role/addRole");
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
	    tokenProcessor.mvAddToken(mv, request);
		return mv;
	}
	@RequestMapping(value="/updateRole", method = RequestMethod.GET)
	public ModelAndView updateRole(HttpServletRequest request,Integer roleid){
		ModelAndView mv = new ModelAndView("/role/updateRole");
		Role role = this.roleSerivce.getRoleById(roleid);
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
	    tokenProcessor.mvAddToken(mv, request);
		mv.addObject("role", role);
		return mv;
	}
	@RequestMapping(value="/updateRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> updateRole(@ModelAttribute Role role,HttpServletRequest request){
		Map<String, Integer> map = new HashMap<String, Integer>();
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
		int msg = 0;
		if(!tokenProcessor.isTokenValid(request)){
            msg = Constant.TOKEN_RESUBMIT;
        }else{ 
        	Role temp = this.roleSerivce.getRoleById(role.getRoleid());
        	if(temp.getRolename().equals(role.getRolename())){
        		msg = 1;
        	}else{     
        		boolean msgbol =  this.roleSerivce.isExsit(role.getRolename());
				if(msgbol){
					msg = -1;
				}else{					
					msg = this.roleSerivce.updateRole(role);
					tokenProcessor.reset(request);//清楚session中的标识
					roleResourceLoader.refreshRoleResource();
				}
        	}
        }
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value="/addRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> addRole(@ModelAttribute Role role,HttpServletRequest request){
		Map<String, Integer> map = new HashMap<String, Integer>();
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
		int msg = 0;
		if(!tokenProcessor.isTokenValid(request)){
            msg = Constant.TOKEN_RESUBMIT;
        }else{   
        	boolean msgbol =  this.roleSerivce.isExsit(role.getRolename());
			if(msgbol){
				msg = -1;
			}else{				
				msg = this.roleSerivce.addRole(role);
				tokenProcessor.reset(request);//清楚session中的标识
				roleResourceLoader.refreshRoleResource();
			}
        }
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping(value="/deleteRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> deleteRole(String ids){
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg =0;
		try {
			msg = this.roleSerivce.deleteRole(ids);
		} catch (Exception e) {
			logger.error("deleteRole",e);
		}
		map.put("msg", msg);
		return map;
	} 
	@RequestMapping(value="/assign")
	public @ResponseBody  List<AssignAccess> assign(HttpServletRequest request,Integer roleid){
		List<Resource> resources = this.resourceService.getResourcesByroleId(roleid);
		List<Resource> allresources =  this.resourceService.getAllResources();
		List<AssignAccess> assignAccesses = getChildrens(allresources, resources, 0);
		return assignAccesses;
	}
	private List<AssignAccess> getChildrens( List<Resource> allresources, List<Resource> resources, Integer parentid){
		List<AssignAccess> accesses = new ArrayList<AssignAccess>();
		AssignAccess access = null;
		if(allresources != null && allresources.size() > 0){
			for (int i = 0; i < allresources.size(); i++) {
				Resource resource = allresources.get(i);
				if(resource.getParentid().intValue() == parentid){
					access = new AssignAccess();
					access.setId(resource.getResourceid());
					access.setText(resource.getResourcename());
					access.setState("open");
					if(resource.getNepotismid()!=null){
					access.setAttributes(resource.getNepotismid());
					}
					if(resources != null && resources.size() >0){
						for (int j = 0; j < resources.size(); j++) {
							if(resource.getResourceid().intValue() == resources.get(j).getResourceid().intValue()){
								if(resource.getParentid()>0){
									access.setChecked(true);
									break;
								}
							}
						}
					}
					accesses.add(access);
				}
			}
			if(accesses.size() > 0){
				for (AssignAccess assignAccess : accesses) {
					assignAccess.setChildren(getChildrens(allresources, resources, assignAccess.getId()));
					for(AssignAccess aa:assignAccess.getChildren()){
						if(aa.isChecked()==false){
							assignAccess.setChecked(false);
						}
					}
				}
			}
		}
		
		return accesses;
	}
	@RequestMapping(value="/updateAccess")
	public @ResponseBody Map<String, Integer> updateAccess(Integer roleid,String ids,HttpServletRequest request){
		int msg =0;
		try {
			msg = roleResourceLoader.updateAccess(roleid, ids);
		} catch (Exception e) {
			logger.error("updateAccess",e);
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("msg",msg);
		roleResourceLoader.refreshRoleResource();
		return map;
	}
	@RequestMapping(value="/assignAccess/{roleid}",method = RequestMethod.GET)
	public ModelAndView assignAccess(@PathVariable("roleid") Integer roleid){
		ModelAndView mv = new ModelAndView("/role/assignAccess");
		mv.addObject("roleid",roleid);
		return mv;
	}
	@RequestMapping(value="/isExsit", method = RequestMethod.POST)
	public @ResponseBody boolean isExsit(String rolename){
		boolean bool = this.roleSerivce.isExsit(rolename);
		return !bool;
	}
}
