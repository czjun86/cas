package com.complaint.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.action.vo.VoBean;
import com.complaint.model.Area;
import com.complaint.model.TestWorkOrder;
import com.complaint.model.User;
import com.complaint.page.PageBean;
import com.complaint.service.TaskOrderService;
import com.complaint.utils.Constant;
import com.complaint.utils.DateUtils;
import com.complaint.utils.SessionUtils;

@Controller
@RequestMapping("/task")
public class TaskController {
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	@Autowired
	private TaskOrderService taskOrderService;
	
	@RequestMapping(value = "/tasklist")
	public ModelAndView tasklist(@ModelAttribute VoBean vo,String s_id ,Integer breakflag ,HttpServletRequest request){
		User user = SessionUtils.getUserByRequest(request);
		ModelAndView mv = new ModelAndView("/task/tasklist");
		Date[] dates = getDates(vo.getStartTime(),vo.getEndTime());
		if(vo.getIsDeal()== null){
			vo.setIsDeal(-1);
		}
		if(vo.getAreaids() == null){
			vo.setAreaids("-1");
		}
		if(vo.getSenceids() == null){
			vo.setSenceids("-1");
		}
		if(vo.getTesttype()== null){
			vo.setTesttype("-1");
		}
		if(vo.getNettype()==null){
			vo.setNettype("-1");
		}
		if(vo.getInside()==null){
			vo.setInside("-1");
		}
		if(vo.getDatatype()==null){
			vo.setDatatype("1");
		}
		if(vo.getJobtype()==null){
			vo.setJobtype("-1");
		}
		if(vo.getTestnet()==null){
			vo.setTestnet("-1");
		}
		if(vo.getStartTime()== null){
			vo.setStartTime(DateUtils.getDayTime(new Date(), -1));
			
		}
		if(vo.getEndTime()== null){
			vo.setEndTime(DateUtils.getDayTime(new Date(), 0));
		}
		if(vo.getVerify() == null){
			vo.setVerify(-1);
		}
		if(breakflag == null){
			breakflag = -1;
		}
		PageBean pb = taskOrderService.countWorkOrderList(Constant.PAGESIZE, 1, vo,s_id,user.getUserid() ,breakflag);
		mv.addObject("pb", pb);
		mv.addObject("sernos", vo.getSernos());
		mv.addObject("isDeal", vo.getIsDeal());
		mv.addObject("testphone", vo.getTestphone());
		mv.addObject("areaids", vo.getAreaids());
		mv.addObject("areatext", vo.getAreatext());
		mv.addObject("senceids", vo.getSenceids());
		mv.addObject("senctext", vo.getSenctext());
		mv.addObject("testtype", vo.getTesttype());
		mv.addObject("testtypeText", vo.getTesttypeText());
		mv.addObject("datatype", vo.getDatatype());
		mv.addObject("jobtype", vo.getJobtype());
		mv.addObject("startTime", vo.getStartTime());
		mv.addObject("endTime", vo.getEndTime());
		mv.addObject("testnet", vo.getTestnet());
		mv.addObject("inside", vo.getInside());
		mv.addObject("nettype", vo.getNettype());
		mv.addObject("testnetName", vo.getTestnetName());
		mv.addObject("s_id", s_id);
		mv.addObject("verify", vo.getVerify());
		mv.addObject("breakflag" ,breakflag);
		return mv;
	}
	@RequestMapping(value = "/tasklist/template")
	public String workOrderJson(Model model,Integer pageIndex,VoBean vo,String s_id ,Integer breakflag ,HttpServletRequest request){
		try {
			User user = SessionUtils.getUserByRequest(request);
			PageBean pb = this.taskOrderService.getWorkOrderList(Constant.PAGESIZE, pageIndex,vo,s_id,user.getUserid(),breakflag);
			List<TestWorkOrder> list = (List<TestWorkOrder>) pb.getList();
			model.addAttribute("list", list);
			model.addAttribute("contextPath", request.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		return "/task/childlist";
	}
	private Date[] getDates(String startTime,String endTime){
		Date startDate = null;
		Date endDate = null;
		if(StringUtils.isNotEmpty(startTime)){
			try {
				startTime = startTime + " 00:00:00";
				startDate = sdf.parse(startTime.replaceAll("-", "/"));
			} catch (ParseException e) {
				logger.debug(e.getMessage());
			}
		} 
		if(StringUtils.isNotEmpty(endTime)){
			try {
				endTime = endTime + " 23:59:59";
				endDate = sdf.parse(endTime.replaceAll("-", "/"));
			} catch (ParseException e) {
				logger.debug(e.getMessage());
			}
		}
		return new Date[]{startDate,endDate};
	}
	@RequestMapping(value = "/arealist")
	public ModelAndView areaList(String areaids,Integer type,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/task/arealist");
		mv.addObject("areaids", areaids);
		mv.addObject("type", type);
		return mv;
	}
	@RequestMapping(value = "/getarea")
	public @ResponseBody List<Area> getList(String areaids,Integer type,HttpServletRequest request){
		User user = SessionUtils.getUserByRequest(request);
		List<Area> arealist = new ArrayList<Area>();
		List<TestWorkOrder> list = this.taskOrderService.getAllArea(user.getUserid());
		List<Area> arealist1 = new ArrayList<Area>();
		
		Area ar = new Area();
		ar.setId(null);
		ar.setText("选择区域");
		ar.setChecked(false);
		ar.setState("open");
		if(areaids == null || areaids.equals("")){
			areaids = "-1";
		}
		String[] ids = areaids.split(",");
		if(type.equals(0)){
			Area areaall = new Area();
			areaall.setId(Integer.parseInt("-1"));
			areaall.setText("全部");
			areaall.setState("open");
			arealist1.add(areaall);
		}
		TestWorkOrder wo = null;
		Area area = null;
		
		for(int i = 0; i < list.size(); i++){
			area = new Area();
			wo = list.get(i);
			area.setId(wo.getAreaid());
			area.setText(wo.getAreaname());
			area.setState("open");
			for(String id:ids){
				if(Long.parseLong(id) == wo.getAreaid()){
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
