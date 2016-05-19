package com.complaint.action;

import java.util.HashMap;
import java.util.List;
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

import com.complaint.model.RateAndIntegrationForm;
import com.complaint.model.RateColor;
import com.complaint.model.RateForm;
import com.complaint.model.RateProperty;
import com.complaint.service.IntegrationThresholdService;
import com.complaint.service.RateColorCodeService;
import com.complaint.service.RateIntervalValueService;

@Controller
@RequestMapping(value="/rateconfig")
public class RateIntervalValueController {
	@Autowired
	private RateIntervalValueService rateIntervalValueService;
	@Autowired
	private RateColorCodeService rateColorCodeService;
	@Autowired
	private IntegrationThresholdService integrationThresholdService;
	
	private static final Logger logger = LoggerFactory.getLogger(RateIntervalValueController.class);
	/**
	 * 跳转到评级阈值，查出所有显示值，将分为室内室外，并查出颜色
	 * @return
	 */
	@RequestMapping(value="/rateconfig", method = RequestMethod.GET)
	public ModelAndView toSetRate(){
		ModelAndView mv = new ModelAndView("/rateconfig/rateconfig");
		Map<Integer,RateProperty> map = new HashMap<Integer,RateProperty>();
		map = this.rateIntervalValueService.getRate();
		RateProperty rateOutside = map.get(0);
		RateProperty rateInside = map.get(1);
		
//		查出颜色
		List<RateColor> rateColors = this.rateColorCodeService.getColoursWithNoPoundSign();
		
//		查出页面数据值
		
		Map map1 = integrationThresholdService.getIntegrationThreshold();
//		将map中的计分规则，修正规则，评分规则分别取出绑定进ModelAndView
		mv.addObject("scoreRule",map1.get("scoreRule"));
		mv.addObject("revisRule",map1.get("revisRule"));
		mv.addObject("evaluateRule",map1.get("evaluateRule"));
		mv.addObject("colors",rateColors);
		mv.addObject("rateOutside",rateOutside);
		mv.addObject("rateInside",rateInside);
		return mv;
	}
	
	
	/**
	 * 将页面参数一表单形式提交，并修改,按属性名封成数组，每个数组前四为室外后四为室内，按优良中差顺序
	 * @param rateForm
	 * @return
	 */
	@RequestMapping(value="/rateconfig", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> setRate(RateForm rateForm){
		Map<String,String> map = new HashMap<String,String>();
		String str = "b";
		try {
			str = this.rateIntervalValueService.updateRateInterval(rateForm);
		} catch (Exception e) {
			logger.error("set rate",e);
		}
		map.put("msg",str);
		return map;
	}
	
	
	/**
	 * 修改颜色
	 * @param colors
	 * @return
	 */
	@RequestMapping(value="/colorconfig", method = RequestMethod.POST)
	public @ResponseBody Map<String,Integer> setColor(String[] colors,HttpServletRequest request){
		Map<String ,Integer> map = new HashMap<String ,Integer>();
		int msg =0;
		try {
			msg = this.rateColorCodeService.saveColor(colors);
		} catch (Exception e) {
			logger.error("rate  saveColor",e);
		}
		if(msg==1){
//		获取生成图片保存路径
			String completionPath = request.getSession().getServletContext().getRealPath("");
			rateColorCodeService.createImages(completionPath,colors);
			map.put("msg", msg);
		}
			return map;
	}
	
	/**
	 * 表单修改
	 */
	@RequestMapping(value="/saveAll", method = RequestMethod.POST)
	public @ResponseBody Map<String,Integer> saveAllForm(RateAndIntegrationForm rateAndIntegrationForm,HttpServletRequest request){
		Map<String,Integer> map = new HashMap<String,Integer>();
//		保存所有内容
		int msg = -1;
		try {
			msg = this.rateIntervalValueService.saveAll(rateAndIntegrationForm,request);
		} catch (Exception e) {
			logger.error("save rate form",e);
		}
		map.put("msg", msg);
		return map;
	}

}
