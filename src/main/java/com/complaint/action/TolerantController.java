package com.complaint.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.User;
import com.complaint.model.WorkOrder;
import com.complaint.page.PageBean;
import com.complaint.service.TolerantService;
import com.complaint.utils.Constant;
import com.complaint.utils.SessionUtils;

@Controller
@RequestMapping("/tolerant")
public class TolerantController {
	
	@Autowired
	private TolerantService tolerantService;
	
	private static final Logger logger = LoggerFactory.getLogger(TolerantController.class);
	
	/**
	 * 进入容差配置页面
	 * @param startTime 开始时间
	 * @param endTime结束时间
	 * @param serialno工单号
	 * @param isTolerant是否根据容差查询
	 * @param areaids查询区域
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/tolerant")
	public ModelAndView getTolerant(String startTime ,String endTime ,String serialno ,String isTolerant ,String areaids  ,String areatext  ,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/tolerant/tolerant");
		
		if(!StringUtils.isNotEmpty(startTime)&&!StringUtils.isNotEmpty(endTime)){
			//获取结束时间
			Date dt=new Date();
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    endTime = sdf.format(dt);
		    //获取开始时间
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.DATE, -7);
		    startTime = sdf.format(calendar.getTime());
		}
		PageBean pb = this.tolerantService.getTolerantPage(startTime,endTime,serialno ,isTolerant , areaids,Constant.PAGESIZE);
		mv.addObject("startTime",startTime);
		mv.addObject("endTime",endTime);
		mv.addObject("serialno",serialno);
		mv.addObject("isTolerant",isTolerant);
		mv.addObject("areaids",areaids);
		mv.addObject("areatext",areatext);
		mv.addObject("pb",pb);
		return mv;
	}
	
	/**
	 * 分页查出数据
	 * @param startTime
	 * @param endTime
	 * @param serialno
	 * @param isTolerant
	 * @param areaids
	 * @param pageIndex
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/tolerant/template")
	public ModelAndView getTolerantTemplate(String startTime ,String endTime ,String serialno ,String isTolerant ,String areaids ,Integer pageIndex ,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/tolerant/child");
		List<WorkOrder> list = this.tolerantService.getList(startTime,endTime,serialno ,isTolerant , areaids ,pageIndex,Constant.PAGESIZE);
		mv.addObject("list" , list);
		return mv;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView editTolerant(String serialno , Integer areaId ,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/tolerant/editTolerant");
		List<Integer> list = this.tolerantService.getTolerantInfo(serialno,areaId);
		mv.addObject("list",list);
		mv.addObject("serialno" ,serialno);
		mv.addObject("areaId" ,areaId);
		return mv;
	}
	
	@RequestMapping(value="/update" , method = RequestMethod.POST)
	public @ResponseBody Map updateTolerant(String serialno , Integer areaId ,String ids ,HttpServletRequest request){
		Map<String ,Object> map = new HashMap<String , Object>();
		int msg = 0;
		User user = SessionUtils.getUserByRequest(request);
		try {
			msg = this.tolerantService.getUpdateInfo(serialno,areaId,ids,user.getUserid());
		} catch (Exception e) {
			logger.error("update tolerant",e);
		}
		map.put("msg", msg);
		return map;
	}

}
