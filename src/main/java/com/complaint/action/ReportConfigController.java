package com.complaint.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.AreaBean;
import com.complaint.model.Group;
import com.complaint.model.QualityBasicConfig;
import com.complaint.page.PageBean;
import com.complaint.service.ReportConfigService;
import com.complaint.utils.Constant;
import com.complaint.utils.ExcelUtil;

@Controller
@RequestMapping("/reportconfig")
public class ReportConfigController {

	private static final Logger logger = LoggerFactory
			.getLogger(ReportConfigController.class);
	@Autowired
	private ReportConfigService reportConfigService;

	/**
	 * 分公司配置
	 * 
	 * @return
	 */
	@RequestMapping(value = "/grouplist", method = RequestMethod.GET)
	public ModelAndView getGroupInfo(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/reportconfig/grouplist");
		PageBean pb = this.reportConfigService
				.countGroups(1, Constant.PAGESIZE);
		mv.addObject("pb", pb);
		return mv;
	}

	@RequestMapping(value = "/grouplist/template")
	public String rolelistJson(Model model, String rolename, Integer pageIndex,
			HttpServletRequest request) {
		// List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		try {
			PageBean pb = this.reportConfigService.getGroupsList(pageIndex,
					Constant.PAGESIZE);
			List<Group> list = (List<Group>) pb.getList();
			model.addAttribute("contextPath", request.getContextPath());
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return "/reportconfig/groupchildlist";
	}

	/**
	 * 分公司管理
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/groupmanager", method = RequestMethod.GET)
	public ModelAndView groupedit(HttpServletRequest request, Integer type) {
		ModelAndView mv = new ModelAndView("/reportconfig/groupManager");
		List<Group> list = reportConfigService.getGroupById(null);
		List<AreaBean> unlist = reportConfigService.getNotInGroupArea();
		int len = 0;
		for(int i=0;i<list.size();i++){
			Group group = list.get(i);
			if(i == 0){
				len = ExcelUtil.getStrLength(group.getGroupname());
			}else{
				if(len < ExcelUtil.getStrLength(group.getGroupname())){
					len = ExcelUtil.getStrLength(group.getGroupname());
				}
			}
		}
		mv.addObject("len", len);
		mv.addObject("groups", list);
		mv.addObject("unlist", unlist);
		mv.addObject("type", type);
		return mv;
	}

	@RequestMapping(value = "/guishugroup", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Integer> groupGuishu(HttpServletRequest request, String groups) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = 0;
		try {
			reportConfigService.updateGuishu(groups);
			msg = 1;
		} catch (Exception e) {
			logger.error("", e);
			msg = 0;
		}
		map.put("msg", msg);
		return map;
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addgroup", method = RequestMethod.GET)
	public String groupAdd(HttpServletRequest request) {
		return "/reportconfig/addGroup";
	}

	@RequestMapping(value = "/addgroup", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Integer> groupAdd(HttpServletRequest request, String groupname) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int groupid = reportConfigService.getGroupSeq();
		int msg = reportConfigService.addGroup(groupid, groupname);
		map.put("groupid", groupid);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 删除分公司
	 * 
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deletegroup", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Integer> groupDelete(HttpServletRequest request, String ids) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = 0;
		try {
			reportConfigService.deleteGroup(ids);
			msg = 1;
		} catch (Exception e) {
			logger.error("", e);
			msg = 0;
		}
		map.put("msg", msg);
		return map;
	}

	/**
	 * 修改分公司
	 * 
	 * @param request
	 * @param groups
	 * @return
	 */
	@RequestMapping(value = "/updategroup", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Integer> updateGroup(HttpServletRequest request, String groups) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = 0;
		try {
			reportConfigService.editGroup(groups);
			msg = 1;
		} catch (Exception e) {
			logger.error("", e);
			msg = 0;
		}
		map.put("msg", msg);
		return map;
	}

	/**
	 * 质量报表配置
	 * 
	 * @param request
	 * @param groups
	 * @return
	 */
	@RequestMapping(value = "/qualityconfig", method = RequestMethod.GET)
	public ModelAndView qualityConfig(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/reportconfig/qualityConfig");
		List<Group> list = reportConfigService
				.getGroupAndQualityConfigRelationl();
		List<QualityBasicConfig> basics = reportConfigService
				.getQualityBasicConfig();
		mv.addObject("list", list);
		mv.addObject("basics", basics);

		return mv;
	}

	@RequestMapping(value = "/updatestep", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Integer> updateStep(HttpServletRequest request, String groups) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = 0;
		try {
			reportConfigService.updateStep(groups);
			msg = 1;
		} catch (Exception e) {
			logger.error("", e);
			msg = 0;
		}
		map.put("msg", msg);
		return map;
	}

	/**
	 * 判断公司名称是否重复
	 * 
	 * @param groupname
	 * @return
	 */
	@RequestMapping(value = "/isExsit", method = RequestMethod.POST)
	public @ResponseBody
	boolean isExsit(String groupname) {
		boolean bool = this.reportConfigService.isExsit(groupname);
		return !bool;
	}
}
