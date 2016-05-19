package com.complaint.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.IntegrationThresholdForm;
import com.complaint.model.RateColor;
import com.complaint.service.IntegrationThresholdColorService;
import com.complaint.service.IntegrationThresholdService;

@Controller
@RequestMapping(value="/integration")
public class IntegrationIntervalValueController {
	@Autowired
	private IntegrationThresholdColorService integrationThresholdColorService;
	@Autowired
	private IntegrationThresholdService integrationThresholdService;
	
	@RequestMapping(value="/integration", method = RequestMethod.GET)
	public ModelAndView toSetIntegration(){
		ModelAndView mv = new ModelAndView("/integrationconfig/integrationconfig");
//		查出color值
		List<RateColor> colors = integrationThresholdColorService.getIntegrationColorsWithNoPoundSign();
//		查出页面数据值
		//ccc
		Map map = integrationThresholdService.getIntegrationThreshold();
//		将map中的计分规则，修正规则，评分规则分别取出绑定进ModelAndView
		mv.addObject("scoreRule",map.get("scoreRule"));
		mv.addObject("revisRule",map.get("revisRule"));
		mv.addObject("evaluateRule",map.get("evaluateRule"));
		mv.addObject("colors",colors);
		return mv;
	}
	
	/**
	 * 进行颜色修改
	 * @param colors
	 * @return
	 */
	@RequestMapping(value="/colorconfig", method = RequestMethod.POST)
	public @ResponseBody Map<String,Integer> setColor(String[] colors,HttpServletRequest request){
		Map<String ,Integer> map = new HashMap<String ,Integer>();
		int msg = this.integrationThresholdColorService.updateColor(colors);
		if(msg==1){
//			获取生成图片保存路径
			String completionPath = request.getSession().getServletContext().getRealPath("");
			this.integrationThresholdColorService.createImages(completionPath,colors);
		}
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 将页面参数一表单形式提交，并修改,按属性名封成数组，每个数组前四为室外后四为室内，按优良中差顺序
	 * @param rateForm
	 * @return
	 */
	@RequestMapping(value="/integration", method = RequestMethod.POST)
	public @ResponseBody Map<String,String> setIntegrationThreshold(IntegrationThresholdForm form){
		Map<String,String> map = new HashMap<String,String>();
		String str ="b";
		try {
			str = this.integrationThresholdService.updateIntegrationValue(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("msg",str);
		return map;
	}
	
}
