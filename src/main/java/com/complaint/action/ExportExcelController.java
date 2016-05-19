package com.complaint.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.Area;
import com.complaint.model.AreaBean;
import com.complaint.model.ComplainEvaluate;
import com.complaint.model.ComplainStatistics;
import com.complaint.model.Group;
import com.complaint.model.MostTestRate;
import com.complaint.model.QualTopAndLast;
import com.complaint.model.QualtyReport;
import com.complaint.model.RateColor;
import com.complaint.model.Resource;
import com.complaint.service.AreaService;
import com.complaint.service.ExportExcelService;
import com.complaint.service.ReportConfigService;
import com.complaint.service.StatAndEvalReportService;
import com.complaint.service.SysConfigService;
import com.complaint.utils.Constant;
import com.complaint.utils.DateUtils;
import com.complaint.utils.RoleResourceLoader;

@Controller
@RequestMapping("/export")
public class ExportExcelController {
	private static final Logger logger = LoggerFactory
			.getLogger(ExportExcelController.class);
	@Autowired
	private ExportExcelService exportExcelService;

	@Autowired
	private StatAndEvalReportService statAndEvalReportService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	@Autowired
	private ReportConfigService reportConfigService;

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView exportView(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/export/exportReport");
		//获取颜色
		List<RateColor> colors = exportExcelService.getColors();
		//累计起始时间
		String reportdate = sysConfigService.getValue(Constant.REPORTDATE);
		//及时率默认时间
		String timelyrate = sysConfigService.getValue(Constant.TIMELYRATE);
		//按钮权限
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		mv.addObject("buttons", buttons);
		mv.addObject("colors", colors);
		mv.addObject("show", 0);
		mv.addObject("type", 1);
		mv.addObject("msg", "0");
		mv.addObject("reportdate",reportdate);
		mv.addObject("systime",reportdate);
		mv.addObject("timelyrate",timelyrate);
		return mv;
	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public ModelAndView searchData(HttpServletRequest request, String areatext,
			String areaids,String queryids, int type, String starttime, String endtime,
			String day, String month, String weekstart, String weekend) {
		ModelAndView mv = new ModelAndView("/export/exportReport");
		String reportdate = sysConfigService.getValue(Constant.REPORTDATE);
		//及时率默认时间
		String timelyrate = sysConfigService.getValue(Constant.TIMELYRATE);
		//按钮权限
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		ComplainStatistics statistics = null;
		ComplainEvaluate evaluate = null;
		MostTestRate mostTestRate = null;
		List<RateColor> colors = null;
		String msg = "0";
		String queryAreaIds = "";
		try {
			// 获取颜色
			colors = exportExcelService.getColors();
			// 初始化查询参数
			WebApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(request.getSession()
							.getServletContext());
			Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
			mapParam.put("type", type);
			mapParam.put("starttime", starttime);
			mapParam.put("endtime", endtime);
			
			if (queryids.indexOf("-1") > -1) {
				queryAreaIds = "-1";
			}/* else if (queryids.indexOf("-2") > -1) {
				queryAreaIds = "863401,863407,863412,863414,863415,863416,863420,863422,863424,863428,863435,863436,863402"
						+ queryids.replace("-2", "");
			}*/ else {
				queryAreaIds = queryids;
			}
			mapParam.put("queryAreaIds", queryAreaIds);
			Map messMap = statAndEvalReportService.getAllStatAndEval(ctx,
					mapParam);
			statistics = (ComplainStatistics) messMap.get("stat");
			evaluate = (ComplainEvaluate) messMap.get("eval");
			// 评分类
			mostTestRate = (MostTestRate) messMap.get("mostTestRate");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
			msg = "1";
		}
		String typeVal = "";
		String titleVal = "";
		if (type == 1) {
			typeVal = "日";
			if (day != null && !"".equals(day)) {
				titleVal = "(" + DateUtils.dateFormat(day) + ")";
			}
		} else if (type == 2) {
			typeVal = "周";
			if (weekend != null && !"".equals(weekend) && weekstart != null
					&& !"".equals(weekstart)) {
				titleVal = "(" + DateUtils.dateFormat(weekstart) + "-"
						+ DateUtils.dateFormat(weekend) + ")";
			}
		} else {
			typeVal = "月";
			if (month != null &&   !"".equals(month)) {
				String[] montharr = month.split("-");
				titleVal = "(" + montharr[0] + "年" + montharr[1] + "月" + ")";
			}
		}
		mv.addObject("show", 1);
		mv.addObject("typeVal", typeVal);
		mv.addObject("titleVal", titleVal);
		mv.addObject("areatext", areatext);
		mv.addObject("type", type);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
		mv.addObject("areaids", areaids);
		mv.addObject("queryids", queryids);
		mv.addObject("day", day);
		mv.addObject("month", month);
		mv.addObject("weekstart", weekstart);
		mv.addObject("weekend", weekend);
		mv.addObject("colors", colors);
		mv.addObject("statistics", statistics);
		mv.addObject("evaluate", evaluate);
		mv.addObject("mostTestRate", mostTestRate);
		mv.addObject("msg", msg);
		mv.addObject("buttons", buttons);
		mv.addObject("reportdate",reportdate);
		mv.addObject("systime",reportdate);
		mv.addObject("timelyrate",timelyrate);
		return mv;
	}
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ModelAndView downloads(Integer type, String areaids,String queryids,
			String starttime, String endtime, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = null;
		// 初始化查询参数
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession()
						.getServletContext());
		String queryAreaIds = "";
		if (queryids.indexOf("-1") > -1) {
			queryAreaIds = "-1";
		}/* else if (queryids.indexOf("-2") > -1) {
			queryAreaIds = "863401,863407,863412,863414,863415,863416,863420,863422,863424,863428,863435,863436,863402"
					+ queryids.replace("-2", "");
		}*/ else {
			queryAreaIds = queryids;
		}
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 移动网络投诉测试每日通报 - (2013年9月5日)
			String fileName = "";
			String[] start = starttime.split("-");
			if (type == 1) {
				fileName = "移动网络投诉测试日报-(" + DateUtils.dateFormat(starttime)
						+ ").xlsx";
			} else if (type == 2) {
				fileName = "移动网络投诉测试周报-(" + DateUtils.dateFormat(starttime)
						+ "-" + DateUtils.dateFormat(endtime) + ").xlsx";
			} else {
				fileName = "移动网络投诉测试月报-(" + start[0] + "年" + start[1]
						+ "月).xlsx";
			}
			//获取文件路径
			String name = String.valueOf(System.currentTimeMillis())+".xlsx";
			String filePath = request.getSession().getServletContext().getRealPath("/");
				filePath = filePath.replace("\\", "/");
			// 写入excel
			exportExcelService.writeInExcel(ctx, type, starttime, endtime,
					queryAreaIds ,filePath ,name);

			filePath = filePath+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH+name;//路径加文件名
			fileName = URLEncoder.encode(fileName, "GB2312");
			fileName = URLDecoder.decode(fileName, "ISO8859_1");
			File f = new File(filePath);
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset(); // 非常重要
			response.addHeader("Content-Disposition", "attachment;filename=\""
					+ fileName + "\"");
			out = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(out);

			while (-1 != (len = bis.read(buf, 0, buf.length))) {
				bos.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv = new ModelAndView("export/error");
			logger.error("", e);
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
				}
		}
		return mv;
	}

	@RequestMapping(value = "/arealist")
	public ModelAndView areaList(String areaids, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/export/arealist");
		mv.addObject("areaids", areaids);
		return mv;
	}

	@RequestMapping(value = "/getarea")
	public @ResponseBody
	List<Area> getList(String areaids, HttpServletRequest request) {
		//返回区域list
		List<Area> arealist = new ArrayList<Area>();
		//查询所有区域
		List<AreaBean> list = this.areaService.getAllArea();
		//查询所有公司
		List<AreaBean> glist = this.areaService.getAllGroup();
		List<Area> arealist1 = new ArrayList<Area>();
		
		//添加选择区域
		Area ar = new Area();
		ar.setId(null);
		ar.setText("选择区域");
		ar.setChecked(false);
		ar.setState("open");
		//添加全部
		Area areaall = new Area();
		areaall.setId(Integer.parseInt("-1"));
		areaall.setText("全部");
		areaall.setState("open");
		areaall.setAttributes("");
		if (areaids.indexOf("-1") > -1) {
			areaall.setChecked(true);
		}
		arealist1.add(areaall);
		String[] ids = areaids.split(",");
		//默认区域
		/*Area areadef = new Area();
		areadef.setId(Integer.parseInt("-2"));
		areadef.setText("13个区域");
		areadef.setState("open");
		areadef.setAttributes("");
		if (areaids.indexOf("-2") > -1) {
			areadef.setChecked(true);
		}
		arealist1.add(areadef);*/
		
		AreaBean group = null;
		Area garea = null;
		//添加公司
		for (int i = 0; i < glist.size(); i++) {
			garea = new Area();
			group = glist.get(i);
			List<AreaBean> gl = reportConfigService.getAreaByGroupId(group.getGroupid());
			String as = "";
			for(AreaBean ab:gl){
				as += ab.getAreaid() +",";
			}
			garea.setId(group.getGroupid());
			garea.setText(group.getGroupname());
			garea.setState("open");
			if(as.length()>1){
				garea.setAttributes(as.substring(0,as.length()-1));
			}else{
				garea.setAttributes("0");//公司下没有区域的时候赋值0，用于标记此项为公司
			}
			if(ids.length > 0){
				for (String id : ids) {
					if (Long.parseLong(id) == group.getGroupid()) {
						garea.setChecked(true);
						break;
					}
				}
			}else{
				if(group.getGroupid() == 1){
					garea.setChecked(true);
				}
			}
			arealist1.add(garea);
		}
		AreaBean ab = null;
		Area area = null;
		
		for (int i = 0; i < list.size(); i++) {
			area = new Area();
			ab = list.get(i);
			area.setId(ab.getAreaid());
			area.setText(ab.getAreaname());
			area.setState("open");
			area.setAttributes("");
			for (String id : ids) {
				if (Long.parseLong(id) == ab.getAreaid()) {
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
	/**
	 * 测试报表默认起始时间、及时率修改
	 * @param timelyrate
	 * @param reportdate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateTest", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Integer> updateTest(String timelyrate,String reportdate,
			HttpServletRequest request) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = 0;
		try {
			sysConfigService.updateTest(reportdate, timelyrate);
			msg = 1;
		} catch (Exception e) {
			logger.error("",e);
			 msg = 0;
		}
		map.put("msg", msg);
		return map;
	}
	/**
	 * 进入质量报表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/qualityreport", method = RequestMethod.GET)
	public ModelAndView quealtyView(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/export/qualityReport");
		//按钮权限
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		//质量报表起始时间
		String qualdate = sysConfigService.getValue(Constant.RP_QUALITY_CUMULATIVE_START_TIME);
		//及时率默认时间
		String timelyrate = sysConfigService.getValue(Constant.TIMELYRATE);
		mv.addObject("buttons", buttons);
		mv.addObject("qualdate", qualdate);
		mv.addObject("systime", qualdate);
		mv.addObject("timelyrate", timelyrate);
		mv.addObject("type", 1);
		mv.addObject("show", 0);
		return mv;
	}
	/**
	 * 质量报表查询
	 * @param request
	 * @param areatext
	 * @param areaids
	 * @param queryids
	 * @param type
	 * @param starttime
	 * @param endtime
	 * @param day
	 * @param month
	 * @param weekstart
	 * @param weekend
	 * @return
	 */
	@RequestMapping(value = "/qualityreport", method = RequestMethod.POST)
	public ModelAndView searchQualityData(HttpServletRequest request, String areatext,
			String areaids,String queryids, int type, String starttime, String endtime,
			String day, String month, String weekstart, String weekend) {
		ModelAndView mv = new ModelAndView("/export/qualityReport");
		//质量报表起始时间
		String qualdate = sysConfigService.getValue(Constant.RP_QUALITY_CUMULATIVE_START_TIME);
		//及时率默认时间
		String timelyrate = sysConfigService.getValue(Constant.TIMELYRATE);
		//按钮权限
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		int groupnum = exportExcelService.countGroup();
		List<Group> groups = exportExcelService.getAreaInGroup();
		String msg = "0";
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession()
						.getServletContext());
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
		mapParam.put("type", type);
		mapParam.put("starttime", starttime);
		mapParam.put("endtime", endtime);
		String queryAreaIds = "-1";
		//处理查询区域id
		if (queryids.indexOf("-1") > -1) {
			queryAreaIds = "-1";
		} else if (queryids.indexOf("-2") > -1) {
			queryAreaIds = "863401,863407,863412,863414,863415,863416,863420,863422,863424,863428,863435,863436,863402"
					+ queryids.replace("-2", "");
		} else {
			queryAreaIds = queryids;
		}
		QualtyReport qr = null;
		QualTopAndLast top1 = null;
		try {
			Map<String, QualTopAndLast> map = statAndEvalReportService.getTopAndLast(ctx, mapParam);
			top1 = map.get("top1");
			mapParam.put("area_id", queryAreaIds);
			qr = statAndEvalReportService.getQualityReportData(ctx, mapParam);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
			msg = "1";
		}
		String typeVal = "";
		String titleVal = "";
		if (type == 1) {
			typeVal = "日";
			if (day != null) {
				titleVal = "(" + DateUtils.dateFormat(day) + ")";
			}
		} else if (type == 2) {
			typeVal = "周";
			if (weekend != null &&  !"".equals(weekend) && weekstart != null
					&&  !"".equals(weekstart)) {
				titleVal = "(" + DateUtils.dateFormat(weekstart) + "-"
						+ DateUtils.dateFormat(weekend) + ")";
			}
		} else {
			typeVal = "月";
			if (month != null &&  !"".equals(month)) {
				String[] montharr = month.split("-");
				titleVal = "(" + montharr[0] + "年" + montharr[1] + "月" + ")";
			}
		}
		mv.addObject("show", 1);
		mv.addObject("groupnum", groupnum);
		mv.addObject("groups", groups);
		mv.addObject("typeVal", typeVal);
		mv.addObject("titleVal", titleVal);
		mv.addObject("areatext", areatext);
		mv.addObject("type", type);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
		mv.addObject("areaids", areaids);
		mv.addObject("queryids", queryids);
		mv.addObject("day", day);
		mv.addObject("month", month);
		mv.addObject("weekstart", weekstart);
		mv.addObject("weekend", weekend);
		mv.addObject("msg", msg);
		mv.addObject("buttons", buttons);
		mv.addObject("timelyrate",timelyrate);
		mv.addObject("qualdate",qualdate);
		mv.addObject("systime",qualdate);
		mv.addObject("qr",qr);
		mv.addObject("top1",top1);
		mv.addObject("msg",msg);
		return mv;
	}
	/**
	 * 质量报表下载
	 * @param type
	 * @param areaids
	 * @param queryids
	 * @param starttime
	 * @param endtime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadqual", method = RequestMethod.GET)
	public ModelAndView downloadqual(Integer type, String areaids,String queryids,
			String starttime, String endtime, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = null;
		// 初始化查询参数
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession()
						.getServletContext());
		String queryAreaIds = "";
		if (queryids.indexOf("-1") > -1) {
			queryAreaIds = "-1";
		} else if (queryids.indexOf("-2") > -1) {
			queryAreaIds = "863401,863407,863412,863414,863415,863416,863420,863422,863424,863428,863435,863436,863402"
					+ queryids.replace("-2", "");
		} else {
			queryAreaIds = queryids;
		}
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 移动网络投诉测试每日通报 - (2013年9月5日)
			String fileName = "";
			String[] start = starttime.split("-");
			if (type == 1) {
				fileName = "移动网络投诉处理质量日报-(" + DateUtils.dateFormat(starttime)
						+ ").xlsx";
			} else if (type == 2) {
				fileName = "移动网络投诉处理质量周报-(" + DateUtils.dateFormat(starttime)
						+ "-" + DateUtils.dateFormat(endtime) + ").xlsx";
			} else {
				fileName = "移动网络投诉处理质量月报-(" + start[0] + "年" + start[1]
						+ "月).xlsx";
			}
			//获取文件路径
			String name = String.valueOf(System.currentTimeMillis())+".xlsx";
			String filePath = request.getSession().getServletContext().getRealPath("/");
				filePath = filePath.replace("\\", "/");
			// 写入excel
			exportExcelService.writeQualExcel(ctx, type, starttime, endtime,
					queryAreaIds ,filePath ,name);

			filePath = filePath+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH+name;//路径加文件名
			fileName = URLEncoder.encode(fileName, "GB2312");
			fileName = URLDecoder.decode(fileName, "ISO8859_1");
			File f = new File(filePath);
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset(); // 非常重要
			response.addHeader("Content-Disposition", "attachment;filename=\""
					+ fileName + "\"");
			out = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(f));
			bos = new BufferedOutputStream(out);

			while (-1 != (len = bis.read(buf, 0, buf.length))) {
				bos.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv = new ModelAndView("export/error");
			logger.error("", e);
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
				}
		}
		return mv;
	}
	/**
	 * 质量报表默认起始时间、及时率修改
	 * @param timelyrate
	 * @param reportdate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateQual", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Integer> updateQual(String timelyrate,String qualdate,
			HttpServletRequest request) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg = 0;
		try {
			sysConfigService.updateQual(qualdate, timelyrate);
			msg = 1;
		} catch (Exception e) {
			logger.error("",e);
			 msg = 0;
		}
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 累积实测量和日实测量导出
	 * @param starttime 开始时间
	 * @param endtime	结束时间
	 * @param areaid	区域ID
	 * @param testType  是否累计 1－累计 2－非累计 
	 * @param reportType   报表类型 1－测试报告 2－质量报告
	 * @return
	 * 
	 */
	@RequestMapping(value = "/detailsExcel", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> detailsExcel(String starttime,String endtime,String systime,String areaid,String groupid,
			Integer testType,Integer reportType,HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			//部署路径
			String filePath = request.getSession().getServletContext().getRealPath("/");
			//String p =this.getClass().getClassLoader().getResource("").getPath(); //编译根路径
			filePath = filePath.replace("\\", "/");
			//处理开始时间和结束时间
			starttime = starttime.replace('-','.') + " 00:00:00";
			endtime = endtime.replace('-','.') + " 23:59:59";
			map=exportExcelService.excelSerialno(areaid,groupid,starttime, endtime,systime, reportType, testType,filePath);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("sucFlag", "失败");
		}
		return map;
	}
	
	/**
	 * 下载生成的Excel
	 * @param Name
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/downLoadExcel", method = RequestMethod.GET)
	public  ModelAndView downLoad(String name ,Integer testType,Integer reportType,HttpServletRequest request ,HttpServletResponse response){
		ModelAndView mv = null;
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH+name+".xlsx";
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			//文件名称
			String fname=(reportType==1?"测试报告的":"质量报告的")
			+(testType==1?"累积实测量":"实测量")+"报表.xlsx";
			fname = URLEncoder.encode(fname, "GB2312");
			fname = URLDecoder.decode(fname, "ISO8859_1");
		File file = new File(path);
		byte[] buf = new byte[1024];
		int len = 0;
		response.reset(); // 非常重要
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ fname + "\"");
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
	
}
