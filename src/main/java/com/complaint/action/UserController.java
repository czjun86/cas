package com.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.Area;
import com.complaint.model.Password;
import com.complaint.model.Resource;
import com.complaint.model.Role;
import com.complaint.model.User;
import com.complaint.model.WorkOrder;
import com.complaint.page.PageBean;
import com.complaint.security.SaltSourceDetail;
import com.complaint.service.RoleService;
import com.complaint.service.UserService;
import com.complaint.service.WorkOrderService;
import com.complaint.utils.Constant;
import com.complaint.utils.RoleResourceLoader;
import com.complaint.utils.SessionUtils;
import com.complaint.utils.TokenProcessor;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userSerivce;
	@Autowired
	private RoleService roleService;
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	@Autowired
	private SaltSourceDetail saltSourceDetail;
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	@Autowired
	private WorkOrderService workOrderService;
	
	@RequestMapping(value = "/userlist")
	public ModelAndView userlist(String name,HttpServletRequest request){
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		ModelAndView mv = new ModelAndView("/user/userlist");
		PageBean pb = this.userSerivce.getUsersCount(name,1,Constant.PAGESIZE);
		mv.addObject("name", name);
		mv.addObject("pb", pb);
		mv.addObject("buttons", buttons);
		return mv;
	}
	
	@RequestMapping(value = "/userlist/template")
	public String userlistJson(Model model,String name,Integer pageIndex,HttpServletRequest request){
		try {
			PageBean pb = this.userSerivce.getUserList(name,pageIndex,Constant.PAGESIZE);
			List<User> list = (List<User>) pb.getList();
			List<Resource> buttons = this.roleResourceLoader.getButtons(request);
			model.addAttribute("list", list);
			model.addAttribute("buttons", buttons);
			model.addAttribute("contextPath", request.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		} 
		return "/user/childlist";
	}
	@RequestMapping(value="/addUser", method = RequestMethod.GET)
	public ModelAndView addUser(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/user/addUser");
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
	    tokenProcessor.mvAddToken(mv, request);
		List<Role> roles = new ArrayList<Role>();
		roles = this.roleService.queryRoles();
		mv.addObject("roles", roles);
		return mv;
	}
	@RequestMapping(value="/updateUser", method = RequestMethod.GET)
	public ModelAndView updateUser(HttpServletRequest request,Integer id){
		ModelAndView mv = new ModelAndView("/user/updateUser");
		User user = this.userSerivce.getUserById(id);
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
	    tokenProcessor.mvAddToken(mv, request);
		List<Role> roles = new ArrayList<Role>();
		roles = this.roleService.queryRoles();
		mv.addObject("roles", roles);
		mv.addObject("user", user);
		return mv;
	}
	@RequestMapping(value="/updateUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> updateUser(@ModelAttribute User user,HttpServletRequest request){
		Map<String, Integer> map = new HashMap<String, Integer>();
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
		int msg = 0;
		if(!tokenProcessor.isTokenValid(request)){
            msg = Constant.TOKEN_RESUBMIT;
        }else{         	
        	msg = this.userSerivce.updateUser(user);
        	tokenProcessor.reset(request);
        }
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value="/isExsitUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> isExsitUser(String userName){
		Map<String, String> map = new HashMap<String, String>();
		int msg =  isExsit(userName);
		String msgstr = "-1";
		if(msg == 1){
			msgstr = "登录名已经存在！";
		}
		map.put("msg", msgstr);
		return map;
	}
	@RequestMapping(value="/isExsitUserName", method = RequestMethod.POST)
	public  @ResponseBody boolean isExsitUserName(String userName){
		List<User> users = this.userSerivce.getUsersByUserName(userName);
		if(users != null && users.size() > 0){
			return false;
		}else{
			return true;
		}
	}
	
	private int isExsit(String userName){
		List<User> users = this.userSerivce.getUsersByUserName(userName);
		if(users != null && users.size() > 0){
			return 1;
		}else{
			return 0;
		}
	}
	
	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> addUser(@ModelAttribute User user,HttpServletRequest request){
		Map<String, Integer> map = new HashMap<String, Integer>();
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
		int msg = 0;
		if(!tokenProcessor.isTokenValid(request)){
            msg = Constant.TOKEN_RESUBMIT;
        }else{ 
        	user.setPassword(md5PasswordEncoder.encodePassword(Constant.defaultPwd, saltSourceDetail.getSalt(user)));
    		if(isExsit(user.getUserName()) == 1){
    			msg = -1;
    		}else{
    			msg = this.userSerivce.addUser(user);
    			tokenProcessor.reset(request);
    		}
        }
		map.put("msg", msg);		
		return map;
	}
	
	@RequestMapping(value="/deleteUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> deleteUser(String ids){
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = this.userSerivce.deleteUser(ids);
		map.put("msg", msg);
		return map;
	} 
	
	@RequestMapping(value="/resetpsw", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> resetpsw(Integer id){
		Map<String, Integer> map = new HashMap<String, Integer>();
		User user = this.userSerivce.getUserById(id);
		int msg = this.userSerivce.updateUserPsw(id, md5PasswordEncoder.encodePassword("123456", saltSourceDetail.getSalt(user)));
		map.put("msg", msg);
		return map;
	} 
	
	@RequestMapping(value="/accessDenied", method = RequestMethod.GET)
	public String accessDenied(){
		return "/accessDenied";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Model model){
		return "/index";
	}
	/*
	@RequestMapping(value="/system", method = RequestMethod.GET)
	public String system(HttpServletRequest request,Model model){
		String rolename = SessionUtils.getRolenameByRequest(request);
		List<Resource> resources = this.roleResourceLoader.getRoleResources(rolename);
		model.addAttribute("resources", resources);
		model.addAttribute("parentid", this.roleResourceLoader.getTopMenuIdByUrl(rolename, request.getRequestURI()));
		model.addAttribute("linkid",request.getParameter("linkid"));
		return "/center";
	}
	*/
	@RequestMapping(value="/mainMenu", method = RequestMethod.GET)
	public @ResponseBody Map<String, List<Resource>> test(HttpServletRequest request){
		
		Map<String, List<Resource>> map = new HashMap<String, List<Resource>>();
		String rolename = SessionUtils.getRolenameByRequest(request);
		List<Resource> resources = this.roleResourceLoader.getRoleResources(rolename);
		map.put("resources", resources);
		//model.addAttribute("resources", resources);
		//model.addAttribute("parentid", this.roleResourceLoader.getTopMenuIdByUrl(rolename, request.getRequestURI()));
		return map;
	}
	/*
	@RequestMapping(value="/config", method = RequestMethod.GET)
	public String config(HttpServletRequest request,Model model){
		String rolename = SessionUtils.getRolenameByRequest(request);
		List<Resource> resources = this.roleResourceLoader.getRoleResources(rolename);
		model.addAttribute("resources", resources);
		model.addAttribute("parentid", this.roleResourceLoader.getTopMenuIdByUrl(rolename, request.getRequestURI()));
		return "/center";
	}
	*/
	@RequestMapping(value="/updatePsw",method=RequestMethod.GET)
	public ModelAndView toUpdatePsw(){
		ModelAndView mv = new ModelAndView("/user/updatePassword");
		return mv;
	}
	@RequestMapping(value="/updatePsw",method=RequestMethod.POST)
	public @ResponseBody Map<String, Integer> updatePassword(HttpServletRequest request,@ModelAttribute Password password){
		 Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = 0;
		User user = SessionUtils.getUserByRequest(request);
		String old = md5PasswordEncoder.encodePassword(password.getOldpsw(), saltSourceDetail.getSalt(user));
		if(!old.equals(user.getPassword())){
			//旧密码输入错误！
			msg = -1;
		}else{
			if(!password.getPassword().equals(password.getRepsw())){
				//两次输入密码不一致
				msg = -2;
			}else{
				String newPsw = md5PasswordEncoder.encodePassword(password.getPassword(), saltSourceDetail.getSalt(user));
				msg = this.userSerivce.updateUserPsw(user.getUserid(),newPsw);
			}
		}
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value="/updateInfo",method=RequestMethod.GET)
	public ModelAndView toUpdateInfo(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/user/updateUserInfo");
		User user = SessionUtils.getUserByRequest(request);
		user = this.userSerivce.getUserById(user.getUserid());
		mv.addObject("user", user);
		return mv;
	}
	@RequestMapping(value="/updateInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> updateInfo(@ModelAttribute User user){
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = this.userSerivce.updateUserInfo(user);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 保存区域分配
	 * @param user
	 * @param areas
	 * @return
	 */
	@RequestMapping(value="/saveArea", method = RequestMethod.POST)
	public @ResponseBody Integer saveArea(@ModelAttribute User user ,String areas){
		int msg = 0;
		try {
			this.userSerivce.updateArea(user,areas);
			msg = 1;
		} catch (Exception e) {
			logger.error("update areas of user " ,e);
		}
		return msg;
	}
	
	/**
	 * 打开区域dialog
	 * @param user
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/arealist")
	public ModelAndView areaList(@ModelAttribute User user,Integer type,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/user/arealist");
		String areaids ="";
		String str = this.userSerivce.getAreas(user);
		if(str!=null&&!("".equals(str))){
			areaids = str;
		}
		mv.addObject("areaids", areaids);
		mv.addObject("type", type);
		return mv;
	}
	
	/**
	 * dialog获取自己拥有区域
	 * @param areaids
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getarea")
	public @ResponseBody List<Area> getList(String areaids,Integer type,HttpServletRequest request){
		List<Area> arealist = new ArrayList<Area>();
		List<WorkOrder> list = this.workOrderService.getAllArea();
		List<Area> arealist1 = new ArrayList<Area>();
		
		Area ar = new Area();
		ar.setId(-1);
		ar.setText("全部");
		ar.setState("open");
		if(areaids.indexOf("-1")>=0){
			ar.setChecked(true);
		}else{
			ar.setChecked(false);
		}
		
		if(areaids == null || areaids.equals("")){
			areaids = "-2";
		}
		String[] ids = areaids.split(",");
		WorkOrder wo = null;
		Area area = null;
		
		for(int i = 0; i < list.size(); i++){
			area = new Area();
			wo = list.get(i);
			area.setId(wo.getAreaId());
			area.setText(wo.getAreaname());
			area.setState("open");
			for(String id:ids){
				if(Long.parseLong(id) == wo.getAreaId()){
					area.setChecked(true);
					break;
				}
			}
			
			arealist1.add(area);
		}
		ar.setChildren(arealist1);
		arealist.add(ar);
		return arealist;
	}
}
