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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.Area;
import com.complaint.model.ComplainProbability;
import com.complaint.model.Resource;
import com.complaint.model.WorkOrder;
import com.complaint.service.AreaService;
import com.complaint.service.ComplainStatisticsService;
import com.complaint.service.WorkOrderService;
import com.complaint.utils.Constant;
import com.complaint.utils.DateUtils;
import com.complaint.utils.ExcelUtil;
import com.complaint.utils.RoleResourceLoader;

/**
 * 投诉率与投诉统计控制器
 * @author peng
 *
 */
@Controller
@RequestMapping("/complain")
public class ComplainStatisticsController {
	
	@Autowired
	private ComplainStatisticsService complainStatisticsService;
	
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	
	@Autowired
	private WorkOrderService workOrderService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ComplainStatisticsController.class);
	
	@RequestMapping(value="/complain")
	public ModelAndView getComplain(HttpServletRequest request ,String area_id ,String queryids,String s_time ,String e_time ,String m_time ,String t_type ,String areatext){
		ModelAndView mv = new ModelAndView("/complain/complain");
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		List<ComplainProbability> list =null;
		
		//获取当前时间
		Map time = DateUtils.getToday();
		
		try {
			if(t_type!=null){
				list = new ArrayList<ComplainProbability>();
				if(s_time!=null&&e_time!=null&&Integer.parseInt(t_type)==1){//判断开始时间和结束时间是否有
						list=searchinfo(area_id ,s_time ,e_time ,m_time ,t_type);
				}else if(m_time!=null&&Integer.parseInt(t_type)==2){//判断月份时间是否有
						list=searchinfo(area_id ,s_time ,e_time ,m_time ,t_type);
				}
			}
		} catch (Exception e) {
			logger.error("",e);
			mv.addObject("msg",1);
		} finally{
			mv.addObject("t_type",t_type==null?"2":t_type);
			mv.addObject("m_time",m_time==null?time.get("m_time"):m_time);
			mv.addObject("s_time",s_time==null?time.get("s_time"):s_time);
			mv.addObject("e_time",e_time==null?time.get("e_time"):e_time);
			mv.addObject("area_id",area_id==null?"-1":area_id);
			mv.addObject("areatext",areatext==null?"全部":areatext);
			mv.addObject("queryids",queryids==null?"-1":queryids);
			
		}
		
		mv.addObject("buttons",buttons);
		if(list!=null&&list.size()>0){
			mv.addObject("complain",list);
		}
		return mv;
	}
	public List<ComplainProbability> searchinfo(String area_id ,String s_time ,String e_time ,String m_time ,String t_type){
		
		List<WorkOrder> areas = this.workOrderService.getAllArea();
		area_id = ExcelUtil.getSearchAreas(area_id,areas);//获取查询区域
		
		//查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area_id" ,area_id);
		map.put("t_type", t_type);
		map.put("s_time", s_time==null?"":s_time+" 00:00:00");
		map.put("e_time", e_time==null?"":e_time+" 23:59:59");
		map.put("m_time", m_time==null?"":m_time);
		
		List<ComplainProbability> list = complainStatisticsService.getinfo(map);
		return list;
	}
	@RequestMapping(value = "/download")
	public ModelAndView download(HttpServletRequest request ,HttpServletResponse response ,String area_id ,String s_time ,String e_time ,String m_time ,String t_type){
		ModelAndView mv = null;
		
		List<WorkOrder> areas = this.workOrderService.getAllArea();
		area_id = ExcelUtil.getSearchAreas(area_id,areas);//获取查询区域
		
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
		map.put("area_id" ,area_id);
		map.put("t_type", t_type);
		map.put("s_time", s_time==null?"":s_time+" 00:00:00");
		map.put("e_time", e_time==null?"":e_time+" 23:59:59");
		map.put("m_time", m_time==null?"":m_time);
		
		//模板路径
		String tempPath = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_TEMPLATE_PATH+"AnalyzeComplain.xlsx";
		
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			
			complainStatisticsService.CreateExcel(path,map,tempPath);//生成EXCEL
		
		String fileName = "complain.xlsx";//路径加文件名
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
			logger.error("", e);
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
	
	@RequestMapping(value = "/arealist")
	public ModelAndView areaList(String areaids, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/complain/arealist");
		mv.addObject("areaids", areaids);
		return mv;
	}
	
	/*@RequestMapping(value = "/getarea")
	public @ResponseBody List<Area> getList(String areaids, HttpServletRequest request) {
		//返回区域list
		List<Area> arealist = new ArrayList<Area>();
		String[] s = areaids.split(",");
		
		//全部
		boolean flag = false;//判断全选是否被选定
		Area area = new Area();
		area.setId(Integer.parseInt("-1"));
		area.setText("全部");
		area.setState("open");
		area.setAttributes("");
		if(s.length>0){
			for(String str:s){
				if (Long.parseLong(str) == -1) {
					area.setChecked(true);
					flag = true;
				}
			}
		}
		
		List<WorkOrder> areas =this.workOrderService.getAllArea();
		Area ga = null;
		
		List<Area> la = new ArrayList<Area>();
		for(WorkOrder wo:areas){
			ga = new Area();
			ga.setId(wo.getAreaId());
			ga.setText(wo.getAreaname());
			ga.setState("open");
			if(s.length>0&&!flag){
				for(String str:s){
					if (Long.parseLong(str) == wo.getAreaId()) {
						ga.setChecked(true);
						break;
					}
				}
			}else{
				ga.setChecked(false);
			}
			la.add(ga);
		}
		area.setChildren(la);
		arealist.add(area);
		
		return arealist;
	}*/
	
}
