package com.complaint.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.ComplainProbability;
import com.complaint.model.GroupScore;
import com.complaint.model.Resource;
import com.complaint.model.StaffAssess;
import com.complaint.service.StaffAssessService;
import com.complaint.utils.Constant;
import com.complaint.utils.DateUtils;
import com.complaint.utils.RoleResourceLoader;

/**
 * 中心内部考核环比控制器
 * @author peng
 *
 */
@Controller
@RequestMapping("/staffAssess")
public class StaffAssessController {
	
	@Autowired
	private StaffAssessService staffAssessService;
	
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	
	private static final Logger logger = LoggerFactory.getLogger(StaffAssessController.class);
	
	@RequestMapping(value="/staffAssess")
	public ModelAndView getStaffAssess(HttpServletRequest request ,String s_time ,String e_time ,String m_time ,String t_type){
		ModelAndView mv = new ModelAndView("/staffAssess/staffAssess");
		
		//获取当前时间
		Map time = DateUtils.getToday();
		
		List<StaffAssess> list = null;
		try {
			if(t_type!=null){
				list = new ArrayList<StaffAssess>();
				if(s_time!=null&&e_time!=null&&Integer.parseInt(t_type)==1){//判断开始时间和结束时间是否有
					list=searchinfo(s_time ,e_time ,m_time ,t_type);
				}else if(m_time!=null&&Integer.parseInt(t_type)==2){//判断月份时间是否有
					list=searchinfo(s_time ,e_time ,m_time ,t_type);
				}
			}
		} catch (Exception e) {
			logger.error("get assess infomation",e);
			mv.addObject("msg",1);
		} finally{
			mv.addObject("t_type",t_type==null?"2":t_type);
			mv.addObject("m_time",m_time==null?time.get("m_time"):m_time);
			mv.addObject("s_time",s_time==null?time.get("s_time"):s_time);
			mv.addObject("e_time",e_time==null?time.get("e_time"):e_time);
		}
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		mv.addObject("buttons",buttons);
		if(list!=null&&list.size()>0){
			mv.addObject("staff",list);
		}
		
		//查出累积起始时间
		String reportdate = staffAssessService.getReportdate();
		mv.addObject("reportdate",reportdate);
		
		//小组分数
		Map<String ,Object> scoreMap = staffAssessService.getScore();
		List<GroupScore> groupScore = (List<GroupScore>) scoreMap.get("scoreMap");
		mv.addObject("groupScore",groupScore);
		
		mv.addObject("size",(Integer)scoreMap.get("size"));
		return mv;
	}
	
	public List<StaffAssess> searchinfo(String s_time ,String e_time ,String m_time ,String t_type){
		List<StaffAssess> list = new ArrayList<StaffAssess>();
		
		//查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t_type", Integer.parseInt(t_type));
		map.put("s_time", s_time==null?"":s_time);
		map.put("e_time", e_time==null?"":e_time);
		map.put("m_time", m_time==null?"":m_time);
		
		list = staffAssessService.getinfo(map);
		
		return list;
	}
	
	/**
	 * 导出报表
	 * @param request
	 * @param manager
	 * @param time
	 * @return
	 */
	@RequestMapping(value = "/download")
	public ModelAndView leadExcel(HttpServletRequest request ,HttpServletResponse response ,String s_time ,String e_time ,String m_time ,String t_type){
		ModelAndView mv = null;
		
		
		//生成文件存放于服务器的路径
		String filename = String.valueOf(System.currentTimeMillis())+".xlsx";
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH;
		//查看路径是否存在，没得就创建
		File filePath = new File(path);
		if(!filePath.exists()&&!filePath.isDirectory())  {
			filePath.mkdirs(); 
		}
		//合并文件和文件名
		path = path+filename;
		
		//查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t_type", Integer.parseInt(t_type));
		map.put("s_time", s_time==null?"":s_time);
		map.put("e_time", e_time==null?"":e_time);
		map.put("m_time", m_time==null?"":m_time);
		
		
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			
			staffAssessService.CreateExcel(path,map,t_type);//生成EXCEL
		
		String fileName = "staffAssess.xlsx";//路径加文件名
		fileName = URLEncoder.encode(fileName, "GB2312");
		fileName = URLDecoder.decode(fileName, "ISO8859_1");
		File file = new File(path);
		byte[] buf = new byte[1024];
		int len = 0;
		response.reset(); // 非常重要
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ fileName + "\"");
		out = response.getOutputStream();
		bis = new BufferedInputStream(new FileInputStream(file));
		bos = new BufferedOutputStream(out);

		while (-1 != (len = bis.read(buf, 0, buf.length))) {
			bos.write(buf, 0, len);
		}
		} catch (Exception e) {
			mv = new ModelAndView("/export/error");
			logger.error("lead exxcel of assess", e);
		}finally{
			if (bis != null){
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
			if (bos != null){
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
		}
		return mv;
	}
	
	/**
	 * 设置累积起始时间
	 */
	@RequestMapping(value = "/updateDate")
	public @ResponseBody Integer updateDate(String time,HttpServletRequest request){
		int msg= -1;
		try {
			msg = staffAssessService.updateDate(time);
		} catch (Exception e) {
			logger.error("set start time of assess " , e);
		}
		return msg;
	}
	
	/**
	 * 设置加减分
	 */
	@RequestMapping(value = "/updateScore")
	public @ResponseBody Integer updateScore(HttpServletRequest request,String groupid ,String score){
		String[] groupids = groupid.split(",");
		String[] scores = score.split(",");
		int msg = 1;
		try {
			msg = staffAssessService.updateScore(groupids,scores);
		} catch (Exception e) {
			logger.error("save add or subtraction score",e);
		}
		return msg;
	}
	
	/**
	 * 中心内部考核配置跳转
	 */
	@RequestMapping(value="/assessConfig")
	public ModelAndView assessConfig(HttpServletRequest request){
		ModelAndView mv = new  ModelAndView("/staffAssess/assessConfig");
		Map<String ,List<Map>> maps = staffAssessService.getConfig();
		
		mv.addObject("steps",maps.get("steps"));
		for(Map map:maps.get("report")){
			mv.addObject("basic"+map.get("ID"),map.get("VAL"));
		}
		return mv;
	}
	
	/**
	 * 保存配置信息
	 */
	@RequestMapping(value="/saveConfig")
	public  @ResponseBody Integer saveConfig(HttpServletRequest request ,String svgstep ,String annstep ,String basic ,String kpi){
		int msg = 1;
		try {
			msg = staffAssessService.saveConfig(svgstep,annstep,basic,kpi);
		} catch (Exception e) {
			logger.error("save staffAssess config",e);
		}
		return msg;
	}
}
