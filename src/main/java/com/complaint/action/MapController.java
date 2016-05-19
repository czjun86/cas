package com.complaint.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.service.MapTypeService;

@Controller
@RequestMapping("/map")
public class MapController {
	private static final Logger logger = LoggerFactory
			.getLogger(MapController.class);
	@Autowired
	private MapTypeService mapTypeService;
	@RequestMapping(value="/mapType")
	public ModelAndView mapType(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/map/map");
		String mapType = mapTypeService.getType();
		mv.addObject("mapType" ,mapType);
		return mv;
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public @ResponseBody Map update(HttpServletRequest request,String mapType){
		Map map = new HashMap();
		int i= 0;
		
		try {
			mapTypeService.update(mapType);
			i = 1;
		} catch (Exception e) {
			logger.error("update Map type error" ,e);
		}
		map.put("msg", i);
		return map;
	}
}
