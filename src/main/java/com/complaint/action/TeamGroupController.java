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
import com.complaint.model.GroupManager;
import com.complaint.model.Personnel;
import com.complaint.model.TeamGroup;
import com.complaint.page.PageBean;
import com.complaint.service.TeamGroupService;
import com.complaint.utils.ExcelUtil;

@Controller
@RequestMapping("/teamgroup")
public class TeamGroupController {

	@Autowired
	private TeamGroupService teamGroupService;

	private static final Logger logger = LoggerFactory
			.getLogger(TeamGroupController.class);

	@RequestMapping(value = "/teamgroup")
	public ModelAndView GroupList(HttpServletRequest request, String name,
			Integer id) {
		ModelAndView mv = new ModelAndView("/teamgroup/teamgroup");

		PageBean pb = teamGroupService.getPageBean(name);// 查询分页

		mv.addObject("pb", pb);
		mv.addObject("name", name);
		return mv;
	}

	@RequestMapping(value = "/teamgroup/template")
	public String Ftplist(Model model, HttpServletRequest request, String name) {
		List<GroupManager> groupManager = teamGroupService.getInfo(name);
		model.addAttribute("groupManager", groupManager);
		return "/teamgroup/childlist";
	}

	@RequestMapping(value = "/teammanager", method = RequestMethod.GET)
	public ModelAndView teammanager(HttpServletRequest request, Integer type) {
		ModelAndView mv = new ModelAndView("/teamgroup/teamManager");
		//大组
		List<TeamGroup> bigs = teamGroupService.getBigTeams();
		//小组
		List<TeamGroup> smalls = teamGroupService.getSmallTeams();
		//人员
		List<Personnel> personnels = teamGroupService.getPersonnels();
		//不在大组的小组
		List<TeamGroup> notBigs = teamGroupService.getNotInBig();
		//不在小组的人员
		List<Personnel> notSmalls = teamGroupService.getNotInSmall();
		//不在人员的区域
		List<AreaBean> notPersonnels = teamGroupService.getNotInPersonne();
		//大组名称最长字节
		int biglen = 0;
		for (int i = 0; i < bigs.size(); i++) {
			TeamGroup group = bigs.get(i);
			if (i == 0) {
				biglen = ExcelUtil.getStrLength(group.getGroupname());
			} else {
				if (biglen < ExcelUtil.getStrLength(group.getGroupname())) {
					biglen = ExcelUtil.getStrLength(group.getGroupname());
				}
			}
		}
		// 小组名称最长字节
		int smalllen = 0;
		for (int i = 0; i < smalls.size(); i++) {
			TeamGroup group = smalls.get(i);
			if (i == 0) {
				smalllen = ExcelUtil.getStrLength(group.getGroupname());
			} else {
				if (smalllen < ExcelUtil.getStrLength(group.getGroupname())) {
					smalllen = ExcelUtil.getStrLength(group.getGroupname());
				}
			}
		}
		// 人员名称最长字节
		int personnellen = 0;
		for (int i = 0; i < personnels.size(); i++) {
			Personnel ps = personnels.get(i);
			if (i == 0) {
				personnellen = ExcelUtil.getStrLength(ps.getName());
			} else {
				if (personnellen < ExcelUtil.getStrLength(ps.getName())) {
					personnellen = ExcelUtil.getStrLength(ps.getName());
				}
			}
		}
		mv.addObject("biglen", biglen);
		mv.addObject("smalllen", smalllen);
		mv.addObject("personnellen", personnellen);
		mv.addObject("bigs", bigs);
		mv.addObject("smalls", smalls);
		mv.addObject("personnels", personnels);
		mv.addObject("notbigs", notBigs);
		mv.addObject("notsmalls", notSmalls);
		mv.addObject("notpersonnels", notPersonnels);
		mv.addObject("type", type);
		return mv;
	}

	/**
	 * 进入添加页面
	 * 
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/addteam", method = RequestMethod.GET)
	public ModelAndView addTeam(HttpServletRequest request, Integer type) {
		ModelAndView mv = null;
		if (type.equals(1) || type.equals(2)) {
			mv = new ModelAndView("/teamgroup/addTeam");
		} else {
			mv = new ModelAndView("/teamgroup/addPersonnel");
		}
		mv.addObject("type", type);
		return mv;
	}

	/**
	 * 添加 大组、小组、人员
	 * 
	 * @param request
	 * @param type
	 * @param groupname
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> add(HttpServletRequest request, Integer type,
			String groupname, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		int msg = 0;
		if (type.equals(1) || type.equals(2)) {
			msg = teamGroupService.addTeam(type, groupname);
		} else {
			msg = teamGroupService.addPersonnel(groupname, phone);
		}
		map.put("msg", msg);
		map.put("type", type);
		return map;
	}

	/**
	 * 删除 大组、小组、人员
	 * 
	 * @param request
	 * @param type
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> delete(HttpServletRequest request, Integer type,
			String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		int msg = 0;
		try {
			// type 1 大组 2小组 3人员
			if (type.equals(1) || type.equals(2)) {
				teamGroupService.deleteTeam(type, ids);
			} else {
				teamGroupService.deletePersonnel(ids);
			}
			msg = 1;
		} catch (Exception e) {
			logger.error("", e);
		}
		map.put("msg", msg);
		map.put("type", type);
		return map;
	}

	/**
	 * 修改大组、小组、人员
	 * 
	 * @param request
	 * @param groups
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> update(HttpServletRequest request, Integer type,
			String groups) {
		Map<String, Object> map = new HashMap<String, Object>();
		int msg = 0;
		try {
			// type 0 大组 1小组 2 人员
			if (type.equals(1) || type.equals(2)) {
				teamGroupService.updateTeam(groups);
			} else {
				teamGroupService.updatePersonnel(groups);
			}
			msg = 1;
		} catch (Exception e) {
			logger.error("", e);
		}
		map.put("msg", msg);
		map.put("type", type);
		return map;
	}

	/**
	 * 修改关系集合
	 * 
	 * @param groups
	 * @return
	 */
	@RequestMapping(value = "/updatelist", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updatelist(HttpServletRequest request, Integer type,
			String groups) {
		Map<String, Object> map = new HashMap<String, Object>();
		int msg = 0;
		try {

			// type 4 大组与小组 5小组与区域 6 人员与区域 7 大组组长配置 8 小组组长配置
			if (type.equals(4)) {
				teamGroupService.updateBigRelations(groups);
			} else if (type.equals(5)) {
				teamGroupService.updateSmallRelations(groups);
			} else if (type.equals(6)) {
				teamGroupService.updatePersonnelRelations(groups);
			} else if (type.equals(7) || type.equals(8)) {
				teamGroupService.updateLeader(groups, type);
			}
			msg = 1;
		} catch (Exception e) {
			logger.error("", e);
		}
		map.put("msg", msg);
		return map;
	}

	@RequestMapping(value = "/isExsit", method = RequestMethod.POST)
	public @ResponseBody
	boolean isExsit(String groupname) {
		boolean bool = this.teamGroupService.isExsit(groupname);
		return !bool;
	}
}
