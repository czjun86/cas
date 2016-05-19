package com.complaint.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.complaint.model.User;
import com.complaint.service.ResourceService;
import com.complaint.service.RoleService;
import com.complaint.utils.RoleResourceLoader;
import com.complaint.utils.SessionUtils;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value =	{"/login"}, method = RequestMethod.GET)
	public String login(){
		return "redirect:/";
	}
	
	@RequestMapping(value="/accessDenied", method = RequestMethod.GET)
	public void accessDenied(){}
	@RequestMapping(value="/timeout", method = RequestMethod.GET)
	public void timeout(){}
	
//	@RequestMapping(value="/leftMenu", method = RequestMethod.GET)
//	public void leftMenu(){}
	
	@RequestMapping(value="/indexTop", method = RequestMethod.GET)
	public void indexTop(HttpSession session,Model model){
		String rolename = SessionUtils.getRolenameBySession(session);
		model.addAttribute("resources", roleResourceLoader.getRoleResources(rolename));
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public void logout(){}
	
	@RequestMapping(value="/404", method = RequestMethod.GET)
	public void error404(){}
	
	@RequestMapping(value="/center", method = RequestMethod.GET)
	public void center(){}
	
	@RequestMapping(value="/accessDeniedBody", method = RequestMethod.GET)
	public void accessDeniedBody(){}
	
	@RequestMapping(value="/scatter1", method = RequestMethod.GET)
	public String scatterTest(HttpServletRequest request){
		return "login";
	}
	
	@RequestMapping(value="/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downForOne(HttpServletRequest request) throws IOException{
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "zip"));
		header.setContentDispositionFormData("attachment", "12345.zip");
		byte[] bodyBytes = FileCopyUtils.copyToByteArray(new ClassPathResource("/login.zip").getInputStream());
		return new ResponseEntity<byte[]>(bodyBytes,header,HttpStatus.OK);
	}
	
	@RequestMapping(value="/layout",method = RequestMethod.GET)
	public String layoutTest(){
		return "layoutTemplate";
	}
}
