package com.complaint.action;
/***
 * 工单下单次、多次测试流水报表
 * @author czj
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.complaint.model.CenterZoom;
import com.complaint.model.ColourCode;
import com.complaint.model.KpiFlshData;
import com.complaint.model.LogSubmanualGsm;
import com.complaint.model.LogSubmanualLte;
import com.complaint.model.ReportBean;
import com.complaint.model.Resource;
import com.complaint.model.TCasCell;
import com.complaint.model.TestMasterlog;
import com.complaint.model.TrackPoint;
import com.complaint.model.WcdmsTrackLog;
import com.complaint.service.BaseStationService;
import com.complaint.service.ReportExcelService;
import com.complaint.service.ReportService;
import com.complaint.service.SysConfigService;
import com.complaint.service.TestMasterlogService;
import com.complaint.service.TrackService;
import com.complaint.utils.Constant;
import com.complaint.utils.PatternUtil;
import com.complaint.utils.RoleResourceLoader;

import freemarker.template.Template;



@Controller
@RequestMapping("/report")
public class ReportController {
	private static final Logger logger = LoggerFactory
			.getLogger(ReportController.class);
	@Autowired
	private ReportService reportService;

	@Autowired
	private TrackService trackService;
	
	@Autowired
	private TestMasterlogService testMasterlogService;
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	@Autowired
	private BaseStationService baseStationService;
	
	@Autowired
	private ReportExcelService reportExcelService;
	@Autowired
	private SysConfigService sysConfigService;
	/***
	 * 查询数据判断地图类型进入数据分析页面
	 * @param serialno工单号
	 * @param s_id工单号与SID组合才唯一
	 * @param flowid流水号
	 * @param netType网络状态
	 * @param testType业务状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reportlist", method = RequestMethod.GET)
	public ModelAndView rolelist(String serialno, String flowid,
			String netType, String testType, HttpServletRequest request,String s_id,String areaid) {
		String ss = (String) request.getSession().getAttribute("serialno");
		if (ss != null && !ss.equals("")) {
			request.getSession().removeAttribute("serialno");
			request.getSession().removeAttribute("s_id");
			request.getSession().removeAttribute("areaid");
		}
		//判断地图
		ModelAndView mv=null;
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		if(value.equals("baidu")){
			mv= new ModelAndView("/report/reportlist_baidu");
		}else{
			 mv = new ModelAndView("/report/reportlist");
		}
		// 查询测试
		List<TestMasterlog> list = this.reportService.getserialno(serialno, "",
				"", "",areaid,s_id);
		List<TestMasterlog> flowlist = this.reportService.queryResult(serialno,
				"", netType, testType,areaid,s_id);
		List<ColourCode> colorlist = null;
		// HTTP,PING相关数据业务下
		if (flowlist != null && flowlist.size() > 0) {
			colorlist = this.reportService.queryColor();
		}
		// 颜色
		mv.addObject("colorlist", colorlist);
		// 流水
		mv.addObject("serlist", list);
		// 工单
		mv.addObject("flowlist", flowlist);
        mv.addObject("areaid", areaid);
        mv.addObject("s_id", s_id);
		// 工单号
		mv.addObject("serialno", serialno);
		mv.addObject("netType", NumberUtils.toInt(netType));
		mv.addObject("testType", NumberUtils.toInt(testType));
		return mv;
	}

	@RequestMapping(value = "/changelist", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, List<TestMasterlog>> changelist(String serialno,String areaid,String s_id,
			String netType, String testType, HttpServletRequest request) {
		Map<String, List<TestMasterlog>> map = new HashMap<String, List<TestMasterlog>>();
		List<TestMasterlog> flowlist = this.reportService.queryResult(serialno,
				"", netType, testType,areaid,s_id);
		map.put("flowlist", flowlist);
		return map;
	}

	/**
	 * serialno:多个流水号用,隔开
	 */
	@RequestMapping(value = "/reportlist", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> reportlist(String flowid, HttpServletRequest request,String areaid,String s_id) {
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		String content = "";
		Map<String, Object> map = new HashMap<String, Object>();

		Template template = null;
		// 测试结果
		List<TestMasterlog> sernolist = null;
		List<TestMasterlog> list2 = null;
		Map<String, Object> listmap = new HashMap<String, Object>();
		Map<String,Object> fmap = this.reportService.getData(flowid,areaid,1,request);
		List<KpiFlshData> list = (List<KpiFlshData>) fmap.get("flist");
		List<ColourCode> colorlist = this.reportService.queryColor();
		if (!flowid.equals("") && flowid.split(",").length == 1) {
			sernolist = this.reportService.queryResult("", flowid, "", "",areaid,s_id);
			list2 = this.reportService.getHPF(flowid,areaid);
			listmap.put("hplist", list2);
			listmap.put("colorlist", colorlist);
		}
		try {
			template = freemarkerConfig.getConfiguration().getTemplate(
					"/report/http_ping.ftl");

			content = FreeMarkerTemplateUtils.processTemplateIntoString(
					template, listmap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取单个流水号总评价
		List<String> compEval = (List<String>) fmap.get("getCompEval");
		map.put("compEval", compEval);
		map.put("colorlist", colorlist);
		map.put("flashlist", list);
		map.put("sernolist", sernolist);
		map.put("buttons", buttons);
		// http、ping
		map.put("contenthp", content);
		return map;
	}

	// 选择工单号下的流水号
	@RequestMapping(value = "/findFlowid")
	public ModelAndView findFlowid(String serialno, String flowid,
			HttpServletRequest request,String areaid,String s_id) {
		try {
			serialno = new String(serialno.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("/report/findFlowid");
		mv.addObject("serialno", serialno);
		mv.addObject("s_id", s_id);
		mv.addObject("areaid", areaid);
		mv.addObject("flowid", flowid);
		return mv;

	}
/**
 * 报表共用的选择流水号树形菜单封装*/
	@RequestMapping(value = "/choose")
	public @ResponseBody
	List<ReportBean> initReport(HttpServletRequest request, String serialno,String s_id,
			String id, String flowid,String areaid) {
		List<ReportBean> reportBeans = new ArrayList<ReportBean>();
		if (id == null || id == "") {
			List<TestMasterlog> list = this.reportService.queryResult(serialno,
					"", "", "", areaid,s_id);
			// Map<String, List<ReportBean>> map = new HashMap<String,
			// List<ReportBean>>();
			String[] flows = flowid.split(",");
			ReportBean report1 = new ReportBean();
			report1.setId("1");
			report1.setText("WCDMA");
			report1.setChecked(false);
			report1.setState("open");
			ReportBean report2 = new ReportBean();
			report2.setId("2");
			report2.setText("GSM");
			report2.setChecked(false);
			report2.setState("open");
			ReportBean report3 = new ReportBean();
			report3.setId("3");
			report3.setText("LTE");
			report3.setChecked(false);
			report3.setState("open");
			List<ReportBean> reportBeans1 = new ArrayList<ReportBean>();
			List<ReportBean> reportBeans2 = new ArrayList<ReportBean>();
			List<ReportBean> reportBeans3 = new ArrayList<ReportBean>();
			TestMasterlog tml = null;
			ReportBean report = null;
			for (int i = 0; i < list.size(); i++) {
				report = new ReportBean();
				tml = list.get(i);
				report.setId(tml.getFlowid());
				String floname = "";
				if (tml.getFlowid() != null && tml.getFlowid().length() > 14) {
					SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss");
					floname +=f.format(tml.getTesttime());
				}
				if (tml.getNetsystem() == 1) {
					// report.setpId("2");
					floname += "_GSM";
				} else if (tml.getNetsystem() == 2 || tml.getNetsystem() == 3) {
					// report.setpId("1");
					if (tml.getNetsystem() == 2) {
						floname += "_WCDMA_锁频";
					} else {
						floname += "_WCDMA_自由模式";
					}
				}else if (tml.getNetsystem() == 4||tml.getNetsystem() == 5) {
					// report.setpId("1");
					if (tml.getNetsystem() == 4) {
						floname += "_LTE_锁频";
					} else {
						floname += "_LTE_自由模式";
					}
				}
				if (tml.getTeststatus() == 1) {

					floname += "_IDLE";
				} else if (tml.getTeststatus() == 2) {

					floname += "_CS";
				} else if (tml.getTeststatus() == 3) {

					floname += "_PS";
				}
				if (tml.getCalltype() != null && tml.getCalltype() != 0) {
					if (tml.getCalltype() == 1) {
						floname += "_短呼";
					} else if (tml.getCalltype() == 2) {
						floname += "_长呼";
					}
				}
				if (tml.getFtpUpdown() != null && tml.getFtpUpdown() != 0) {
					if (tml.getFtpUpdown() == 1) {
						floname += "_上行";
					} else if (tml.getFtpUpdown() == 2) {
						floname += "_下行";
					}
				}
				if (tml.getInside() != null) {
					if (tml.getInside() == 0) {
						floname += "(室外)";
					} else if (tml.getInside() == 1) {
						floname += "(室内)";
					}
				}
				report.setText(floname);
				for (int j = 0; j < flows.length; j++) {
					if (report.getId().equals(flows[j])) {
						report.setChecked(true);
					}
				}
				if (tml.getNetsystem() == 1) {
					reportBeans2.add(report);
				} else if (tml.getNetsystem() == 2 || tml.getNetsystem() == 3) {
					reportBeans1.add(report);
				}
				else if (tml.getNetsystem() == 4||tml.getNetsystem() == 5) {
					reportBeans3.add(report);
				}
			}
			if (reportBeans1 != null && reportBeans1.size() > 0) {
				report1.setChildren(reportBeans1);
				reportBeans.add(report1);
			}
			if (reportBeans2 != null && reportBeans2.size() > 0) {
				report2.setChildren(reportBeans2);
				reportBeans.add(report2);
			}
			if (reportBeans3 != null && reportBeans3.size() > 0) {
				report3.setChildren(reportBeans3);
				reportBeans.add(report3);
			}

		}

		// map.put("reports",reportBeans);
		return reportBeans;
	}

	/**
	 * 查询轨迹点数据
	 * 
	 * @param flowid流水号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showPoint", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> showPoint(String flowid, String type,
			HttpServletRequest request,String areaid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TrackPoint> gsmlist = new ArrayList<TrackPoint>();
		List<TrackPoint> wcdmalist = new ArrayList<TrackPoint>();
		List<TrackPoint> ltelist = new ArrayList<TrackPoint>();
		if (type != null && ("1".equals(type) || "3".equals(type)||"5".equals(type))) {
			gsmlist = this.trackService.queryPointGsm(flowid,areaid);
		}
		if (type != null && ("2".equals(type) || "3".equals(type)||"5".equals(type))) {
			wcdmalist = this.trackService.queryPointWcdma(flowid,areaid);
		}
		if (type != null && ("4".equals(type)||"5".equals(type))) {
			ltelist = this.trackService.queryPointLte(flowid,areaid);
		}
		//List<CenterZoom> center = this.trackService.queryCenter(flowid,areaid);
		map.put("gsmlist", gsmlist);
		map.put("wcdmalist", wcdmalist);
		map.put("ltelist", ltelist);
	//	map.put("center", center);
		List<ColourCode> colorlist = this.reportService.queryColor();
		map.put("colorlist", colorlist);
		return map;
	}

	/***
	 * 点击轨迹点转wcdma或是gsm页面(可返回多个页面content)
	 * 
	 * @param type
	 *            表标识
	 * @return
	 */
	@RequestMapping(value = "/wcdmaOrGsm", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> wcdmaOrGsm(String type, String id,String areaid,
			HttpServletRequest request) {
		String content = "";
		String contentMax = "";
		Map<String, Object> map = new HashMap<String, Object>();
		List<WcdmsTrackLog> list3g = null;
		List<LogSubmanualGsm> list2g = null;
		List<LogSubmanualLte> list4g = null;
		try {
			Template template = null;
			Template template2 = null;
			Map<String, Object> listmap = new HashMap<String, Object>();
			if (type!=null&&"1".equals(type)) {
				template = freemarkerConfig.getConfiguration().getTemplate(
						"/report/gsm.ftl");
				template2 = freemarkerConfig.getConfiguration().getTemplate(
						"/report/gsm_max.ftl");

				list2g = this.trackService.queryGsm(id,areaid);
				listmap.put("list", list2g);
			} else if(type!=null&&"2".equals(type)){
				template = freemarkerConfig.getConfiguration().getTemplate(
						"/report/wcdma.ftl");
				template2 = freemarkerConfig.getConfiguration().getTemplate(
						"/report/wcdma_max.ftl");
				list3g = this.trackService.queryWcdma(id,areaid);
				listmap.put("list", list3g);
			}
			//加入4G功能
			else if(type!=null&&"3".equals(type)){
				template = freemarkerConfig.getConfiguration().getTemplate(
						"/report/lte.ftl");
				template2 = freemarkerConfig.getConfiguration().getTemplate(
						"/report/lte_max.ftl");
				list4g = this.trackService.queryLte(id,areaid);
				listmap.put("list", list4g);
			}
			
			
			
			
			
			content = FreeMarkerTemplateUtils.processTemplateIntoString(
					template, listmap);
			contentMax = FreeMarkerTemplateUtils.processTemplateIntoString(
					template2, listmap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		map.put("content", content);
		map.put("contentMax", contentMax);
		return map;

	}
	/**
	 * 判断地图类型进入加入对比 flowid:多个流水号用,隔开
	 */
	@RequestMapping(value = "/reportCompare", method = RequestMethod.GET)
	public ModelAndView reportCompare(String serialno, String flowid,
			String flowname, HttpServletRequest request,String areaid,String s_id) {
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		ModelAndView mv =null;
		if(value.equals("baidu")){
			mv= new ModelAndView("/report/reportCompare_baidu");
		}else{
			mv= new ModelAndView("/report/reportCompare");
		}
		
		mv.addObject("flowid", flowid);
		try {
			mv.addObject("flowname", new String(flowname.getBytes("iso8859-1"),
					"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mv.addObject("serialno", serialno);
		mv.addObject("areaid", areaid);
		mv.addObject("s_id", s_id);
		return mv;
	}

	/**
	 * 加入对比 flowid:多个流水号用,隔开
	 */
	@RequestMapping(value = "/reportCompare", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> reportCompareData(String flowid,String areaid,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 柱状图数据
		Map<String,Object> fmap = this.reportService.getData(flowid,areaid,2,request);
		List<KpiFlshData> list = (List<KpiFlshData>) fmap.get("flist");
		// 颜色
		List<ColourCode> colorlist = this.reportService.queryColor();
		// 2G轨迹点
		List<TrackPoint> gsmlist = new ArrayList<TrackPoint>();
		// 3G轨迹点
		List<TrackPoint> wcdmalist = new ArrayList<TrackPoint>();
        //4G轨迹点
		List<TrackPoint> ltelist = new ArrayList<TrackPoint>();
		//List<CenterZoom> center = this.trackService.queryCenter(flowid,areaid);

		gsmlist = this.trackService.queryPointGsm(flowid,areaid);
		wcdmalist = this.trackService.queryPointWcdma(flowid,areaid);
		ltelist = this.trackService.queryPointLte(flowid,areaid);
		map.put("colorlist", colorlist);
		map.put("flashlist", list);
		map.put("gsmlist", gsmlist);
		map.put("wcdmalist", wcdmalist);
		map.put("ltelist", ltelist);
		map.put("center", null);
		return map;
	}
	/**
	 * 判断地图类型室内点击经纬度调用地图展示入口位置
	
	 * @Title: indoorMap
	
	 * @Description: TODO
	
	 * @param request
	 * @return
	
	 * @return: ModelAndView
	 */
	@RequestMapping(value="/indoorMap", method = RequestMethod.GET)
	public ModelAndView indoorMap(String lat,String lng ,String flowid ,HttpServletRequest request){
		//判断是谷歌还是百度
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		ModelAndView mv =null;
		if(value.equals("baidu")){
			 mv = new ModelAndView("/report/indoorMap_baidu");
		}else{
			 mv = new ModelAndView("/report/indoorMap");
		}
		
		mv.addObject("flowid_",flowid);
		mv.addObject("lat", lat);
		mv.addObject("lng", lng);
		return mv;
	}
	@RequestMapping(value="/getCell", method = RequestMethod.POST)
	public @ResponseBody Map<String ,Object> getCell(String flowid ,HttpServletRequest request){
		Map<String ,Object> map = this.reportService.getCell(flowid );                                                                                                
		return map;
	}
	/**
	 * 显示小区详情
	 */
	@RequestMapping(value = "/showContent", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> showContent(Long lac,Long cid,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		TCasCell cell=this.baseStationService.queryCellByIdOriginal(lac, cid);
		map.put("cell", cell);
		return map;
	}
	/**
	 * 跳转修改地址
	
	 * @Title: updateAdd
	
	 * @Description: TODO
	
	 * @param address
	 * @param request
	 * @return
	
	 * @return: ModelAndView
	 */
	@RequestMapping(value="/showAddress", method = RequestMethod.GET)
	public String showAddress(HttpServletRequest request){
		//TestMasterlog tt=this.testMasterlogService.selectByPrimaryKey(flowid);
		return "/report/updateAddress";
	}
	
	/**
	 * 单次测试中修改测试结果地址
	
	 * @Title: updateRole
	
	 * @Description: TODO
	
	 * @param address
	 * @param request
	 * @return
	
	 * @return: Map
	 */
	@RequestMapping(value="/updateAddress", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> updateAddress(String address,String areaid,String flowid,HttpServletRequest request){
		TestMasterlog t=new TestMasterlog();
		t.setFlowid(flowid);
		t.setTestaddress(PatternUtil.formate(address));
		int it=this.testMasterlogService.updateAdress(t);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("msg", it);
		return map;
	}
	/**
	 * 生成excel
	 * @param value
	 * @param graph
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/reportExcel", method = RequestMethod.POST)
	public  @ResponseBody String reportExcel(String value,String graph ,String eval,String flowid ,String ping,HttpServletRequest request){
		//工单信息
		JSONObject jsonObject = JSONObject.fromObject(value);
		Iterator keyIter = jsonObject.keys();
		Map valueMap = new HashMap();
		String key;
		Object values;
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			values = ((String)jsonObject.get(key)).replaceAll("!_!", "\"");
			valueMap.put(key, values);
		}
		//柱状图信息
		Map valueMapgraph = new HashMap();
		List<String> keys = new ArrayList<String>();
		if(graph.length()>3){
			JSONObject jsonObjectgraph = JSONObject.fromObject(graph);
			Iterator keyItergraph = jsonObjectgraph.keys();
			String keygraph;
			Object valuesgraph;
			while (keyItergraph.hasNext()) {
				keygraph = (String) keyItergraph.next();
				keys.add(keygraph);
				valuesgraph = jsonObjectgraph.get(keygraph);
				valueMapgraph.put(keygraph, valuesgraph);
			}
		}
		
		//评价内容
		JSONObject jsonEval = JSONObject.fromObject(eval);
		Iterator IterEval = jsonEval.keys();
		Map evalMap = new HashMap();
		List<String> keyEvals = new ArrayList<String>();
		String keyEval;
		Object valEval;
		while (IterEval.hasNext()) {
			keyEval = (String) IterEval.next();
			keyEvals.add(keyEval);
			valEval = ((String)jsonEval.get(keyEval));
			evalMap.put(keyEval, valEval);
		}
		
		//ping值内容
		JSONObject pingObject = JSONObject.fromObject(ping);
		Iterator keyPings = pingObject.keys();
		Map pingMap = new HashMap();
		String keyPing;
		Object valuePing;
		while (keyPings.hasNext()) {
			keyPing = (String) keyPings.next();
			valuePing = ((String)pingObject.get(keyPing));
			pingMap.put(keyPing, valuePing);
		}
		//模板路径
		String tempPath = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_TEMPLATE_PATH+"reportExcel.xlsx";
		
		//生成文件存放于服务器的路径
		String str = String.valueOf(System.currentTimeMillis());
		String filename = str+".xlsx";
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH;
		//查看路径是否存在，没得就创建
		File filePath = new File(path);
		if(!filePath.exists()&&!filePath.isDirectory())  {
			filePath.mkdirs(); 
		}
		//合并文件和文件名
		path = path+filename;
		reportExcelService.excelService(valueMap , flowid, tempPath ,path ,valueMapgraph ,keys ,keyEvals ,evalMap , pingMap);
		return str;
	}
	/**
	 * 下载生成的Excel
	 * @param Name
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/downLoad", method = RequestMethod.GET)
	public  ModelAndView downLoad(String name ,HttpServletRequest request ,HttpServletResponse response){
		ModelAndView mv = null;
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH+name+".xlsx";
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
		String fileName = "report.xlsx";//路径加文件名
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
	@RequestMapping(value="/updateVerify", method = RequestMethod.POST)
	public @ResponseBody  Map updateVerify(String id ,Integer val ,HttpServletRequest request ,HttpServletResponse response){
		Map<String,Integer> map =new HashMap<String ,Integer>();
		int flag = 0;
		try {
			flag = reportService.updateVerify(id,val);
		} catch (Exception e) {
			logger.error("update verify",e);
		}
		map.put("flag", flag);
		return map;
	}
	
}
