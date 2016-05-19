package com.complaint.action;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.Resource;
import com.complaint.model.TFtpConfig;
import com.complaint.page.PageBean;
import com.complaint.service.Ftp4gService;
import com.complaint.utils.Constant;
import com.complaint.utils.ConstantUtil;
import com.complaint.utils.RoleResourceLoader;
import com.complaint.utils.SecretUtil;

@Controller
@RequestMapping("/ftp4g")
public class Ftp4gController {
	private static final Logger logger = LoggerFactory.getLogger(Ftp4gController.class);
	@Autowired
	private Ftp4gService ftp4gService;
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	/**
	 * 跳转FTP主页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/updateFtp")
	public ModelAndView updateFtp(HttpServletRequest request,String name,Integer id){
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		ModelAndView mv = new ModelAndView("/ftp4g/updateftp");
		PageBean pb = this.ftp4gService.getPageBean(name);
		mv.addObject("buttons",buttons);
		mv.addObject("pb",pb);
		mv.addObject("name",name);
		return mv;
	}
	@RequestMapping(value="/updateFtp/template")
	public String Ftplist(Model model,HttpServletRequest request,String name,Integer pageIndex){
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		List<TFtpConfig> list = this.ftp4gService.getTFtpConfig(name,pageIndex,Constant.PAGESIZE);
		model.addAttribute("buttons",buttons);
		model.addAttribute("list",list);
		return "/ftp4g/childlist";
	}
	
	/**
	 * 跳转add页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addftp", method = RequestMethod.GET)
	public ModelAndView addFtp(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/ftp4g/addFtp");
		mv.addObject("type",0);//0表示是新增
		return mv;
	}
	
	@RequestMapping(value="/addftp", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> addFtp(@ModelAttribute TFtpConfig tf,HttpServletRequest request,HttpServletResponse response){
    int msg = 0;//验证返回结果
     /*String loip ="";
	 try {
		loip = getMyIp(request);//获取服务器IP
	 } catch (Exception e) {
		logger.error("get myself web ip" ,e);
	 }*/
	 SecretUtil st=new SecretUtil();
	 tf.setEncrypt_pwd(st.get3DESEncrypt(tf.getPwd(),ConstantUtil.KEY));//密码加密
	 tf.setFtp_type(1);//设置默认的网络类型，预留字段
	 
	 try {
		msg=this.ftp4gService.updateFtp(tf , 0);
	} catch (Exception e) {
		logger.error("add ftp",e);
		msg = 11;
	}//0表示新增
     Map<String, Integer> map = new HashMap<String, Integer>();
	 map.put("msg", msg);
	 map.put("statuNull", this.ftp4gService.statuNull());
	 return map;
	}
	/**
	 * 跳转修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editftp", method = RequestMethod.GET)
	public ModelAndView editFtp(HttpServletRequest request,Integer id){
		TFtpConfig tf = this.ftp4gService.searchById(id);
		ModelAndView mv = new ModelAndView("/ftp4g/addFtp");
		mv.addObject("type",1);//1表示是修改
		mv.addObject("tf",tf);
		return mv;
	}
	/**
	 * 修改信息保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editftp", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> editSaveFtp(HttpServletRequest request,HttpSession session,@ModelAttribute TFtpConfig tf){
		SecretUtil st=new SecretUtil();
		tf.setEncrypt_pwd(st.get3DESEncrypt(tf.getPwd(),ConstantUtil.KEY));//密码加密
		int msg =0;
		try {
			msg = this.ftp4gService.updateFtp(tf , 1);//1表示修改
		} catch (Exception e) {
			logger.error("add ftp",e);
			msg = 11;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("msg", msg);
		map.put("statuNull", this.ftp4gService.statuNull());
		return map;
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delftp", method = RequestMethod.POST)
	public @ResponseBody Map<String,Integer> delFtp(HttpServletRequest request,String ids){
		Map<String,Integer> map = new HashMap<String,Integer>();
		int msg = 0;
		try {
			msg = this.ftp4gService.delFtp(ids);
		} catch (Exception e) {
			logger.error("ftp delete",e);
		}
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value="/staUse",method = RequestMethod.POST)
	public @ResponseBody Map<String,Integer> staUse(HttpServletRequest request,Integer id){
		Map<String,Integer> map = new HashMap<String,Integer>();
		int msg = 0;
		try {
			msg = this.ftp4gService.staUse(id);
		} catch (Exception e) {
			logger.error("FTP status on",e);
		}
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value="/staOff",method = RequestMethod.POST)
	public @ResponseBody Map<String,Integer> staOff(HttpServletRequest request,Integer id){
		Map<String,Integer> map = new HashMap<String,Integer>();
		int msg =0;
		try {
			msg = this.ftp4gService.staOff(id);
		} catch (Exception e) {
			logger.error("FTP status off",e);
		}
		map.put("msg", msg);
		return map;
	}
	/**
	 * 获取本机IP
	 * @throws UnknownHostException 
	 */
	public String getMyIp(HttpServletRequest request) throws Exception{
		String ip = "";
		if (isWindowsOS()) {//windows
            ip = InetAddress.getLocalHost().getHostAddress().toString();
        }else{//linux
        	boolean flag = false;
        	Enumeration<NetworkInterface> ea = NetworkInterface.getNetworkInterfaces();
        	while(ea.hasMoreElements()){
        		if(flag){//找到ip的第二次跳出循环
        			break;
        		}
        		NetworkInterface ni = (NetworkInterface) ea.nextElement();
        		Enumeration<InetAddress> ips = ni.getInetAddresses();
        		while(ips.hasMoreElements()){
        			InetAddress ipia = ips.nextElement();
                    if (ipia.isSiteLocalAddress() && !ipia.isLoopbackAddress() && ipia.getHostAddress().indexOf(":") == -1) {
                    	flag = true;
                        ip = ipia.toString();
                    }
        		}
        	}
        }
		return ip;
	}
	/**
	 * 判断否是windows系统
	 */
	public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }
}
