package com.complaint.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.ColourCode;
import com.complaint.model.Kpi;
import com.complaint.model.KpiForm;
import com.complaint.service.ColourCodeService;
import com.complaint.service.EpinfoExcelService;
import com.complaint.service.KpiIntervalValueService;

@Controller
@RequestMapping(value="/kpi")
public class KpiIntervalValueController {
	@Autowired
	private KpiIntervalValueService kpiIntervalValueService;
	@Autowired
	private ColourCodeService colourCodeService;
	private static final Logger logger = LoggerFactory.getLogger(EpinfoExcelService.class);
	@RequestMapping(value="/kipconfig", method = RequestMethod.GET)
	public ModelAndView toSetKpi(){
		ModelAndView mv = new ModelAndView("/kpiconfig/kpiconfig");
//		由于分室内室外，原返回的Kpi换成Map
		Map<Integer ,Kpi> map = new HashMap<Integer, Kpi>();
		map = this.kpiIntervalValueService.getKpi();
		Kpi kpiOutside = map.get(0);
		Kpi kpiInside = map.get(1);
		
		List<ColourCode> colourCodes = this.colourCodeService.getColoursWithNoPoundSign();
		mv.addObject("colors", colourCodes);
		mv.addObject("kpiOutside",kpiOutside);
		mv.addObject("kpiInside", kpiInside);
		return mv;
	}
	@RequestMapping(value="/kipconfig", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> setKpi(@ModelAttribute KpiForm kpiForm, Integer ischange){
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(kpiForm.getRscpInside()[4]);
		String msg = "修改失败";
		try {
			msg = this.kpiIntervalValueService.updateKpiIntervals(kpiForm,ischange);
		} catch (Exception e) {
			logger.error("invoke method setKpi error.",e);
		}
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value="/colorconfig", method = RequestMethod.POST)
	public @ResponseBody Map<String,Integer> setColor(String[] color,HttpServletRequest request){
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = 0;
		try {
			msg = this.colourCodeService.updateColour(color);
		} catch (Exception e) {
			logger.error("",e);
		}
		if(msg==1){
//			获取生成图片保存路径
				String completionPath = request.getSession().getServletContext().getRealPath("");
				colourCodeService.createImages(completionPath,color);
				map.put("msg", msg);
			}
		map.put("msg", msg);
		return map;
	}
	

}
