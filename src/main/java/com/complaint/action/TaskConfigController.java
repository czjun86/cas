package com.complaint.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.AreaBean;
import com.complaint.model.Resource;
import com.complaint.model.TaskOrder;
import com.complaint.model.User;
import com.complaint.page.PageBean;
import com.complaint.service.TaskConfigService;
import com.complaint.service.TaskExcelService;
import com.complaint.utils.Constant;
import com.complaint.utils.RoleResourceLoader;
import com.complaint.utils.SessionUtils;

@Controller
@RequestMapping("/taskconfig")
public class TaskConfigController {
	private static final Logger logger = LoggerFactory.getLogger(TaskConfigController.class);
	@Autowired
	private TaskConfigService taskConfigService;
	@Autowired
	private TaskExcelService taskExcelService;
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	
	
	@RequestMapping(value = "/taskconfig")
	public ModelAndView taskconfig(HttpServletRequest request ,String val ,Integer isverify ,Integer validstate ,Integer timeType){
		User user = SessionUtils.getUserByRequest(request);
		ModelAndView mv = new ModelAndView("/taskconfig/taskconfig");
		
		PageBean pb = this.taskConfigService.countTask(val ,isverify ,validstate , timeType,1,Constant.PAGESIZE,user.getUserid());
		List<AreaBean> regions = this.taskConfigService.getAreaList(user.getUserid());
		mv.addObject("regions",regions);
		mv.addObject("pb", pb);
		mv.addObject("val",val);
		mv.addObject("isverify",isverify);
		mv.addObject("validstate",validstate);
		mv.addObject("timeType",timeType);
		mv.addObject("userid",user.getUserid());
		return mv;
	}
	
	@RequestMapping(value = "/taskconfig/template")
	public String epinfolistJson(String val ,Integer isverify ,Integer validstate ,Integer timeType ,Integer userid
			,Integer pageIndex,HttpServletRequest request,Model model){

		try {
			PageBean pb = this.taskConfigService.getTaskList(val,isverify,validstate,timeType,pageIndex,Constant.PAGESIZE,userid);
			
			List<TaskOrder> list = (List<TaskOrder>) pb.getList();
			List<Resource> buttons = this.roleResourceLoader.getButtons(request);
			
			
			model.addAttribute("buttons", buttons);
			model.addAttribute("list", list);
			model.addAttribute("contextPath", request.getContextPath());
		} catch (Exception e) {
			logger.error("get epinfo menu",e);
		} 
		return "/taskconfig/childlist";
	}
	
	/**
	 * 
	 * 打开新增工单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addTask")
	public ModelAndView addTask(HttpServletRequest request,Integer userid){
		ModelAndView mv = new ModelAndView("/taskconfig/addTask");
		List<AreaBean> arealist = this.taskConfigService.getAreaList(userid);
		mv.addObject("arealist",arealist);
		return mv;
	}
	
	/**
	 * 保存新增工单
	 */
	@RequestMapping(value = "/saveAddTask")
	public @ResponseBody Integer saveAddTask(HttpServletRequest request,Integer[] areaid,
			Integer[] nettype,Integer[] breakflag ,String [] testaddress){
		User user = SessionUtils.getUserByRequest(request);
		int msg = 0;
		try {
			this.taskConfigService.saveAdd(areaid,nettype,breakflag,testaddress,user);
			msg = 1;
		} catch (Exception e) {
			logger.error("insert task serialno" ,e);
		}
		return msg;
	}
	
	/**
	 * 
	 * 修改工单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateTask")
	public ModelAndView updateTask(HttpServletRequest request,String id){
		User user = SessionUtils.getUserByRequest(request);
		ModelAndView mv = new ModelAndView("/taskconfig/update");
		TaskOrder to = this.taskConfigService.getSerialnoInfo(id);
		List<AreaBean> arealist = this.taskConfigService.getAreaList(user.getUserid());
		mv.addObject("arealist",arealist);
		mv.addObject("to",to);
		mv.addObject("user",user.getUserid());
		return mv;
	}
	
	/**
	 * 保存工单修改
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveUpdateTask")
	public @ResponseBody Integer saveUpdateTask(TaskOrder to ,HttpServletRequest request){
		User user = SessionUtils.getUserByRequest(request);
		int msg = 0;
		try {
			this.taskConfigService.saveUpdate(to);
			msg = 1;
		} catch (Exception e) {
			logger.error("Update task serialno" ,e);
		}
		return msg;
	}
	
	/**
	 * 生成模板
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getTemplate")
	public @ResponseBody String getExcel(HttpServletRequest request,HttpServletResponse response){
		User user = SessionUtils.getUserByRequest(request);
		String msg="-1";
		String name = String.valueOf(System.currentTimeMillis());
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH;
		//查看路径是否存在，没得就创建
		File filePath = new File(path);
		if(!filePath.exists()&&!filePath.isDirectory())  {
			filePath.mkdirs(); 
		}
		path += name+".xlsx";
		try {
			taskExcelService.getTemp(path,user.getUserid());
			
			msg = name;
		} catch (Exception e) {
			logger.error("create excel templete" ,e);
		}
		return msg;
	}
	
	
	/**
	 * 导出生成好的报表
	 * @param name
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getExcel")
	public ModelAndView getExcel(String name ,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = null;
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String filePath = request.getSession().getServletContext().getRealPath("/");
				filePath = filePath.replace("\\", "/");
			name +=".xlsx";
			filePath = filePath+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH+name;//路径加文件名
			
			name = URLEncoder.encode(name, "GB2312");
			name = URLDecoder.decode(name, "ISO8859_1");
			File f = new File(filePath);
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset(); // 非常重要
			response.addHeader("Content-Disposition", "attachment;filename=\""
					+ name + "\"");
			out = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(out);

			while (-1 != (len = bis.read(buf, 0, buf.length))) {
				bos.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv = new ModelAndView("export/error");
			logger.error("", e);
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
				}
		}
		return mv;
	}
	
	/**
	 * 打开导出报表条件
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/excelTask")
	public ModelAndView queryCondition(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/taskconfig/excelTask");
		User user = SessionUtils.getUserByRequest(request);
		List<AreaBean> arealist = this.taskConfigService.getAreaList(user.getUserid());
		mv.addObject("arealist",arealist);
		return mv;
	}
	
	/**
	 * 根据条件算出报表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createExcel")
	public @ResponseBody String createExcel(TaskOrder to,HttpServletRequest request){
		String msg = "-1";
		User user = SessionUtils.getUserByRequest(request);
		
		String name = String.valueOf(System.currentTimeMillis());
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH;
		//查看路径是否存在，没得就创建
		File filePath = new File(path);
		if(!filePath.exists()&&!filePath.isDirectory())  {
			filePath.mkdirs(); 
		}
		path += name+".xlsx";
		try {
			to.setHandler(user.getUserid());
			taskExcelService.createExcel(path,to);
			msg = name;
		} catch (Exception e) {
			logger.error("create excel templete" ,e);
		}
		return msg;
	}
	
	/**
	 * 修改有效状态
	 * @param id
	 * @param validstate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setValidstate")
	public @ResponseBody Integer setValidstate(String id,Integer validstate,HttpServletRequest request){
		int msg = 0;
		User user = SessionUtils.getUserByRequest(request);
		TaskOrder to = new TaskOrder();
		to.setId(id);
		to.setValidstate(validstate);
		to.setHandler(user.getUserid());
		try {
			this.taskConfigService.setValidstate(to);
			msg=1;
		} catch (Exception e) {
			logger.error("update the validstate of serialno" ,e);
		}
		return msg;
	}
	
	@RequestMapping(value = "/analysisExcel")
	public ModelAndView analysisExcel(HttpServletRequest request ,@RequestParam("excelFile")MultipartFile file){
		//生成文件存放于服务器的路径
		String name = String.valueOf(System.currentTimeMillis());
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH;
		
		File filePath=new File(path);
		if(!filePath.exists()&&!filePath.isDirectory())  {
			filePath.mkdirs(); 
		}
		path += name+".xlsx";//路径加文件名
		
		User user = SessionUtils.getUserByRequest(request);
		String msg = "1";
		try {
			msg = this.taskExcelService.analysisExcel(file,path,user.getUserid(),name);
		} catch (Exception e) {
			logger.error("analysis Excel",e);
			msg = "-1";
		}
		ModelAndView mv = new ModelAndView("/taskconfig/fileResult");
		mv.addObject("msg",msg);
		return mv;
	}
	
}
