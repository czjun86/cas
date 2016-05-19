/**
 * 地图评价报表
 */

package com.complaint.action;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zhugefubin.maptool.ConverterTool;
import cn.zhugefubin.maptool.Point;

import com.complaint.action.vo.CellVo;
import com.complaint.action.vo.VoBean;
import com.complaint.model.Area;
import com.complaint.model.AreaBean;
import com.complaint.model.BaseStation;
import com.complaint.model.CenterZoom;
import com.complaint.model.Gisgrad;
import com.complaint.model.RateColor;
import com.complaint.model.ReportCells;
import com.complaint.model.Resource;
import com.complaint.model.Scene;
import com.complaint.model.TCasCell;
import com.complaint.service.AreaService;
import com.complaint.service.BaseStationService;
import com.complaint.service.GisgradExcelDownLoadService;
import com.complaint.service.GisgradService;
import com.complaint.service.SceneService;
import com.complaint.service.SysConfigService;
import com.complaint.utils.Constant;
import com.complaint.utils.ConstantUtil;
import com.complaint.utils.DateUtils;
import com.complaint.utils.RoleResourceLoader;

@Controller
@RequestMapping("/gisgrad")
public class GisGradController {

	private static final Logger logger = LoggerFactory.getLogger(GisGradController.class);
	/**
	 * 
	
	 * @Title: rolelist
	
	 * @Description: 跳转到地图评价页面
	
	 * @param request
	 * @return
	
	 * @return: ModelAndView
	 */
	
	@Autowired
	private GisgradService gisgradService;
	@Autowired
	private SceneService sceneService;
	@Autowired
	private BaseStationService baseStationService;
	@Autowired
	private GisgradExcelDownLoadService gisgradExcelDownLoadService;
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/gisgrad", method = RequestMethod.GET)
	public ModelAndView gisgrad1(@ModelAttribute VoBean vo,HttpServletRequest request) {
		if(vo.getAreaids() == null){
			vo.setAreaids("-1");
		}
		if(vo.getSenceids() == null){
			vo.setSenceids("-1");
		}
		if(vo.getTesttype()== null){
			vo.setTesttype("-1");
		}
		if(vo.getStartTime()== null){
			vo.setStartTime(DateUtils.getDayTime(new Date(), -1));
			
		}
		if(vo.getEndTime()== null){
			vo.setEndTime(DateUtils.getDayTime(new Date(), 0));
		}
		if(vo.getNettype()==null){
			vo.setNettype("-1");
		}
		if(vo.getGrad()==null){
			vo.setGrad("-1");
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
		if(vo.getKpi()==null){
			vo.setKpi("-1");
		}
		
		//判断查看和导出权限
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		String download ="";
		String search = "";
		for(Resource resource:buttons){
			if(resource.getUrl().equals("/gisgrad/download")){
				download = "download";
			}else if(resource.getUrl().equals("/gisgrad/search")){
				search = "search";
			}
		}
		
		//获取颜色更新版本
		String vi = (String)request.getSession().getServletContext().getAttribute(ConstantUtil.SYS_RATE_COLOR_VERSION);
		vi = vi==null?"":vi;
		String sctype = sysConfigService.getAngleType();
		
	
		ModelAndView mv = null;
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		if(value.equals("baidu")){
			mv= new ModelAndView("/gisgrad/gisgrad_baidu");
		}else{
			 mv = new ModelAndView("/gisgrad/gisgrad");
		}
		mv.addObject("allowdownload", download);
		mv.addObject("allowsearch", search);
		mv.addObject("buttons",buttons);
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
		mv.addObject("testnetName", vo.getTestnetName());
		mv.addObject("kpi", vo.getKpi());
		mv.addObject("colorversion", vi);
		mv.addObject("sctype", sctype);
		return mv;
	}
	
	/**
	 * 根据条件查询数据
	 */
	@RequestMapping(value = "/gisgrad", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> gisgrad2(@ModelAttribute VoBean vo,HttpServletRequest request) {
		String colorversion = request.getParameter("colorversion");
		String currversion = (String)request.getSession().getServletContext().getAttribute(ConstantUtil.SYS_RATE_COLOR_VERSION);
		currversion = currversion==null ? "" : currversion;
		String content = "";
		//0-没有更新，1-更新
		Integer ischange = currversion.equals(colorversion) ? 0 : 1;
		vo.setStartTime(vo.getStartTime()+" 00:00:00");
		vo.setEndTime(vo.getEndTime()+" 23:59:59");
		Map<String , Object> mm = this.gisgradService.showGrad(vo ,"search");
		Map<List<Integer>, List<Gisgrad>> li=(Map<List<Integer>, List<Gisgrad>>) mm.get("all");
		List<Integer> count=null;
		List<Gisgrad> ll=null;
		for(List<Integer> key:li.keySet()){
			  count=key;
			 ll=li.get(key);
        }
		List<RateColor> colorlist=this.gisgradService.showColor();
		//CenterZoom  center=this.gisgradService.queryCenter(vo);
		CenterZoom  center=(CenterZoom) mm.get("center");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", ll);
		map.put("count", count);
		map.put("color", colorlist);
		map.put("center", center);
		map.put("ischange", ischange);
		return map;
	}
	
	@RequestMapping(value = "/sencelist")
	public ModelAndView sencelist(@ModelAttribute VoBean vo,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/gisgrad/sencelist");
		mv.addObject("senceids", vo.getSenceids());
		mv.addObject("inside", vo.getInside());
		return mv;
	}
	@RequestMapping(value = "/getscene")
	public @ResponseBody List<Area> getscene(String senceids,String inside,HttpServletRequest request){
		List<Area> arealist = new ArrayList<Area>();
		List<Scene> list = this.sceneService.queryScene();
		List<Area> arealist1 = new ArrayList<Area>();
		List<Area> arealist_out= new ArrayList<Area>();
		List<Area> arealist_in = new ArrayList<Area>();
		
		Area ar = new Area();
		ar.setId(null);
		ar.setText("选择场景");
		ar.setChecked(false);
		ar.setState("open");
		
		String[] ids = senceids.split(",");
		
		Area areaall = new Area();
		areaall.setId(Integer.parseInt("-1"));
		areaall.setText("全部");
		areaall.setState("open");
		
		Scene wo = null;
		Area area = null;
		
		for(int i = 0; i < list.size(); i++){
			area = new Area();
			wo = list.get(i);
			area.setId(Integer.parseInt(wo.getSceneid().toString()));
			area.setText(wo.getName());
			area.setState("open");
			for(String id:ids){
				if(Long.parseLong(id) == wo.getSceneid()||id.equals("-1")){
					area.setChecked(true);
					break;
				}
			}
			if(wo.getInside()==0){
				arealist_out.add(area);
			}
			if(wo.getInside()==1){
				arealist_in.add(area);
			}
		}
		Area areaall_in = new Area();
		Area areaall_out = new Area();
		areaall_in.setId(Integer.parseInt("-2"));
		areaall_in.setText("室内");
		areaall_in.setState("open");
		areaall_out.setId(Integer.parseInt("0"));
		areaall_out.setText("室外");
		areaall_out.setState("open");
		areaall_in.setChildren(arealist_in);
		areaall_out.setChildren(arealist_out);
		if(senceids!=null&&senceids.equals("-1")){
			areaall_in.setChecked(true);
			areaall_out.setChecked(true);
		}
		if(inside.equals("1")){
			arealist1.add(areaall_in);
		}else if(inside.equals("0")){
			arealist1.add(areaall_out);
		}else{
			arealist1.add(areaall_in);
			arealist1.add(areaall_out);
		}
		areaall.setChildren(arealist1);
		arealist.add(areaall);
		return arealist;
	}
	//业务状态多选 
	@RequestMapping(value = "/testtypelist")
	public ModelAndView testtypelist(@ModelAttribute VoBean vo,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/gisgrad/testtypelist");
		mv.addObject("testtype", vo.getTesttype());
		mv.addObject("nettype", vo.getNettype());
		return mv;
	}
	
	//业务状态
	
	@RequestMapping(value = "/gettesttype")
	public @ResponseBody List<Area> gettesttype(String testtype,String nettype,HttpServletRequest request){
		List<Area> arealist = new ArrayList<Area>();
		List<Area> list = new ArrayList<Area>();
		List<Area> list1 = new ArrayList<Area>();
		List<Area> list2 = new ArrayList<Area>();
		Area a1=new Area();
		a1.setId(2);
		a1.setText("语音");
		Area a2=new Area();
		a2.setId(3);
		a2.setText("数据");
		Area a3=new Area();
		a3.setId(4);
		a3.setText("长呼");
		Area a4=new Area();
		a4.setId(5);
		a4.setText("短呼");
		Area a5=new Area();
		a5.setId(6);
		a5.setText("FTP上行");
		Area a6=new Area();
		a6.setId(7);
		a6.setText("FTP下行");
		list1.add(a3);
		list1.add(a4);
		list2.add(a5);
		list2.add(a6);
		a1.setChildren(list1);
		a2.setChildren(list2);
		Area a7=new Area();
		a7.setId(1);
		a7.setText("IDLE");
		list.add(a7);
		if(nettype!=null&&nettype.equals("2")){
			list.add(a1);
		}else{
			list.add(a1);
			list.add(a2);
		}

		List<Area> arealist1 = new ArrayList<Area>();
		
		Area ar = new Area();
		ar.setId(null);
		ar.setText("选择业务类型");
		ar.setChecked(false);
		ar.setState("open");
		
		String[] ids = testtype.split(",");
		
		Area areaall = new Area();
		areaall.setId(Integer.parseInt("-1"));
		areaall.setText("全部");
		areaall.setState("open");
		
		Area wo = null;
		Area area = null;
		Area wo1 = null;
		Area area1 = null;
		List<Area> chid=null;
		for(int i = 0; i < list.size(); i++){
			area = new Area();
			wo = list.get(i);
			area.setId(wo.getId());
			area.setText(wo.getText());
			area.setState("open");
			for(String id:ids){
				if(Long.parseLong(id) == wo.getId()||id.equals("-1")){
					area.setChecked(true);
					break;
				}
			}
			chid=wo.getChildren();
			List<Area> ali = new ArrayList<Area>();
			if(chid!=null){
				for(int j = 0; j < chid.size(); j++){
					
					area1 = new Area();
					wo1 = chid.get(j);
					area1.setId(wo1.getId());
					area1.setText(wo1.getText());
					area1.setState("open");
					for(String id:ids){
						if(Long.parseLong(id) == wo1.getId()||id.equals("-1")){
							area1.setChecked(true);
							break;
						}
					}
					
					ali.add(area1);
				}
			}
			area.setChildren(ali);
			arealist1.add(area);
		}
		areaall.setChildren(arealist1);
		arealist.add(areaall);
		return arealist;
	}
	@RequestMapping(value = "/testNetlist")
	public ModelAndView testNetlist(@ModelAttribute VoBean vo,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/gisgrad/testNetlist");
		mv.addObject("testnet", vo.getTestnet());
		mv.addObject("nettype", vo.getNettype());
		return mv;
	}
	//网络制式
	@RequestMapping(value = "/gettestNet")
	public @ResponseBody List<Area> gettestNet(String nettype,String testnet,HttpServletRequest request){
		List<Area> arealist = new ArrayList<Area>();
		List<Area> list = new ArrayList<Area>();
		Area a1=new Area();
		a1.setId(3);
		a1.setText("WCDMA自由模式");
		Area a2=new Area();
		a2.setId(2);
		a2.setText("WCDMA锁频模式");
		Area a4=new Area();
		a4.setId(1);
		a4.setText("GSM锁频模式");
		if(nettype!=null&&nettype.equals("-1")){
			list.add(a1);
			list.add(a2);
			list.add(a4);
		}else if(nettype!=null&&nettype.equals("1")){
			list.add(a1);
			list.add(a2);
		}else if(nettype!=null&&nettype.equals("2")){
			list.add(a1);
			list.add(a4);
		}
		List<Area> arealist1 = new ArrayList<Area>();
		
		
		String[] ids = testnet.split(",");
		
		Area areaall = new Area();
		areaall.setId(Integer.parseInt("-1"));
		areaall.setText("全部");
		areaall.setState("open");
		Area wo = null;
		Area area = null;
		
		for(int i = 0; i < list.size(); i++){
			area = new Area();
			wo = list.get(i);
			area.setId(wo.getId());
			area.setText(wo.getText());
			area.setState("open");
			for(String id:ids){
				if(Long.parseLong(id) == wo.getId()||id.equals("-1")){
					area.setChecked(true);
					break;
				}
			}
			
			arealist1.add(area);
		}
		areaall.setChildren(arealist1);
		arealist.add(areaall);
		return arealist;
	}
	


	
	
	/**
	 * 下载报表
	 * @throws IOException 
	 */
	@RequestMapping(value = "/download")
	public ResponseEntity<byte[]> download(@ModelAttribute VoBean vo,HttpServletRequest request){
		vo.setStartTime(vo.getStartTime()+" 00:00:00");
		vo.setEndTime(vo.getEndTime()+" 23:59:59");
		Map<String ,Object> mm = this.gisgradService.showGrad(vo ,"download");
		Map<List<Integer>, List<Gisgrad>> li=(Map<List<Integer>, List<Gisgrad>>) mm.get("all");
		List<Gisgrad> ll=null;
		for(List<Integer> key:li.keySet()){
			 ll=li.get(key);
        }
		List<Gisgrad> gisgrads =ll;
		Long name = System.currentTimeMillis();
		//工程根路径
		String filePath = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_GIS_TEMPLATE_EXPORT_PATH;
		//判断路径是否存在,不存在就创建
		File fl=new File(filePath);
			if(!fl.exists()&&!fl.isDirectory())  {
				fl.mkdirs(); 
			}
		//文件存放于服务器的路径
		String path = filePath+String.valueOf(name)+".xlsx";
		gisgradExcelDownLoadService.createExcel(gisgrads,path,vo);
//		下载文件的名字
		String fileName = "gradReport.xlsx";
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		header.setContentDispositionFormData("attachment",encodeFilename(fileName, request));
		
		File file = new File(path);
			byte[] bytes = null;
			try {
				bytes = FileCopyUtils.copyToByteArray(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("", e);
			}
			return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}
	/**
	 * 设置下载文件中文件的名称
	 * 
	 * @param filename
	 * @param request
	 * @return
	 */
	public static String encodeFilename(String filename,
			HttpServletRequest request) {

		String agent = request.getHeader("USER-AGENT");
		try {
			if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
				String newFileName = URLEncoder.encode(filename, "UTF-8");
				newFileName = StringUtils.replace(newFileName, "+", "%20");
				if (newFileName.length() > 150) {
					newFileName = new String(filename.getBytes("GB2312"),
							"ISO8859-1");
					newFileName = StringUtils.replace(newFileName, " ", "%20");
				}
				return newFileName;
			}
			if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
				return MimeUtility.encodeText(filename, "UTF-8", "B");

			return filename;
		} catch (Exception ex) {
			return filename;
		}
	}
	/**
	 * 查询基站
	 *  如果是百度地图小区转换经纬度
	 */
	@RequestMapping(value = "/showCell")
	public @ResponseBody Map<String, Object> test(@ModelAttribute CellVo vo,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseStation> list = baseStationService.getAllCell(vo);
		//CenterZoom center = baseStationService.getCenter(vo.getAreas());
		String[] areas = vo.getAreas().split(",");
		if(areas != null && areas.length > 0){
			List<Map<String,Object>> paramsList = new ArrayList<Map<String,Object>>();
			Map<String,Object> params = new HashMap<String, Object>();
			for(int i=0,len=areas.length; i<len; i++){
				Map<String,Object> item = new HashMap<String, Object>();
				item.put("areaid", areas[i]);
				paramsList.add(item);
			}
			params.put("areasList", paramsList);
			params.put("size", 1);
			List<Map<String,Object>> positionList = baseStationService.queryAreaPosition(params);
			if(positionList != null && !positionList.isEmpty()){
				map.put("position", positionList);
			}
		}
		//转换百度经纬度
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		if(value.equals("baidu")){
			ConverterTool ct=new ConverterTool();
			for(int i=0;i<list.size();i++){
				List<TCasCell>  blist=list.get(i).getBsList();
				List<TCasCell> bcell=new ArrayList<TCasCell>();
				for(int j=0;j<blist.size();j++){
					Point p = ct.GG2BD(blist.get(j).getCelllng().doubleValue(),blist.get(j).getCelllat().doubleValue());
					blist.get(j).setCelllng(BigDecimal.valueOf(p.getLongitude()));
					blist.get(j).setCelllat(BigDecimal.valueOf(p.getLatitude()));
					bcell.add(blist.get(j));
				}
				list.get(i).setBsList(bcell);
			}
		}
		
		
		map.put("data", list);
		//map.put("center", center);
		return map;
	}
	/**
	 *  根据LAC CID查询邻区
	 *  如果是百度地图小区转换经纬度
	 */
	@RequestMapping(value = "/getnearcell")
	public @ResponseBody Map<String, Object> getnearcell(@ModelAttribute CellVo vo,HttpServletRequest request){
		Map<String, Object> map = baseStationService.getNearCell(vo);
		List<TCasCell> cell=(List<TCasCell>) map.get("list");
		TCasCell tcc=(TCasCell) map.get("tcc");
		List<TCasCell> cc1=new ArrayList<TCasCell>();
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		if(value.equals("baidu")){
			ConverterTool ct=new ConverterTool();
			for(int i=0;i<cell.size();i++){
				Point p = ct.GG2BD(cell.get(i).getCelllng().doubleValue(),cell.get(i).getCelllat().doubleValue());
				cell.get(i).setCelllng(BigDecimal.valueOf(p.getLongitude()));
				cell.get(i).setCelllat(BigDecimal.valueOf(p.getLatitude()));
				cc1.add(cell.get(i));
			}
			map.put("list", cc1);
			Point p1 = ct.GG2BD(tcc.getCelllng().doubleValue(),tcc.getCelllat().doubleValue());
			tcc.setCelllng(BigDecimal.valueOf(p1.getLongitude()));
			tcc.setCelllat(BigDecimal.valueOf(p1.getLatitude()));
			map.put("tcc", tcc);
		}
		
		return map;
	}
	/**
	 * 查询单个基站
	 * 如果是百度地图小区转换经纬度
	 */
	@RequestMapping(value = "/getbasestation")
	public @ResponseBody Map<String, Object> getBaseStation(Integer bid,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		BaseStation bs = baseStationService.getBaseStationById(bid);
		List<TCasCell> cell=bs.getBsList();
		List<TCasCell> cc1=new ArrayList<TCasCell>();
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		if(value.equals("baidu")){
			ConverterTool ct=new ConverterTool();
			for(int i=0;i<cell.size();i++){
				Point p = ct.GG2BD(cell.get(i).getCelllng().doubleValue(),cell.get(i).getCelllat().doubleValue());
				cell.get(i).setCelllng(BigDecimal.valueOf(p.getLongitude()));
				cell.get(i).setCelllat(BigDecimal.valueOf(p.getLatitude()));

				System.out.println(cell.get(i).getCelllng());
				cc1.add(cell.get(i));
			}
			bs.setBsList(cc1);
		}
		map.put("bs", bs);
		return map;
	}
	/**
	 * 根据流水点击点查询小区
	 * 如果是百度地图小区转换经纬度
	 */
	@RequestMapping(value = "/giscell", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> giscell(String areaids,String flowid,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TCasCell> cell=this.gisgradService.queryCells(flowid,areaids);
		List<TCasCell> cc1=new ArrayList<TCasCell>();
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		if(value.equals("baidu")){
			ConverterTool ct=new ConverterTool();
			for(int i=0;i<cell.size();i++){
				Point p = ct.GG2BD(cell.get(i).getCelllng().doubleValue(),cell.get(i).getCelllat().doubleValue());
				cell.get(i).setCelllng(BigDecimal.valueOf(p.getLongitude()));
				cell.get(i).setCelllat(BigDecimal.valueOf(p.getLatitude()));
				cc1.add(cell.get(i));
			}
			map.put("cell", cc1);
		}else{
			map.put("cell", cell);
		}
		return map;
	}
	
	/**
	 * GIS角度配置
	 */
	@RequestMapping(value = "/angleconfig", method = RequestMethod.GET)
	public ModelAndView getAngleConfig(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/gisgrad/angleconfig");
		Map<String ,String> map = gisgradService.getAngleconfig();
		String type = map.get("type");
		String angle = map.get("angle");
		mav.addObject("typeangle",type);
		mav.addObject("angle",angle);
		return mav;
	}
	/**
	 * 保存GIS角度配置修改
	 */
	@RequestMapping(value = "/saveangle", method = RequestMethod.POST)
	public @ResponseBody Integer getAngleConfig(String angletype ,String angle ,HttpServletRequest request){
		int result = 0;
		if(angletype.equals("0")){
			result = gisgradService.saveAngleconfig(angletype,angle);
		}else if(angletype.equals("1")){
			result = gisgradService.saveAngleconfig(angletype,"30");
		}
		return result;
	}
	
	/**
	 * 显示小区详情
	 * 如果是百度地图小区转换经纬度
	 */
	@RequestMapping(value = "/showContent", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> showContent(Long lac,Long cid,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		TCasCell ccg=this.baseStationService.queryCellById(lac, cid);
		map.put("ccg", ccg);
		String value=sysConfigService.getValue(Constant.MAPTYPE);
		TCasCell cell =new TCasCell();
		if(value.equals("baidu")){
			ConverterTool ct=new ConverterTool();
				Point p = ct.GG2BD(ccg.getCelllng().doubleValue(),ccg.getCelllat().doubleValue());
				cell.setCelllng(BigDecimal.valueOf(p.getLongitude()));
				cell.setCelllat(BigDecimal.valueOf(p.getLatitude()));
		}
		map.put("cell", cell);
		return map;
	}
	
	/**
	 * 设置当前区域中心经纬度
	 */
	@RequestMapping(value = "/setAreaCenter", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> setAreaCenter(String lat,String lng,String areaids,String address,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("suc", 1);
		try {
			BigDecimal bigLat = new BigDecimal(lat, new MathContext(10));
			BigDecimal biglng = new BigDecimal(lng,new MathContext(11));
			String[] areas = areaids.split(",");
			if(areas != null && areas.length > 0){
				List<Map<String,Object>> paramsList = new ArrayList<Map<String,Object>>();
				Map<String,Object> params = new HashMap<String, Object>();
				for(int i=0,len=areas.length; i<len; i++){
					Map<String,Object> item = new HashMap<String, Object>();
					item.put("areaid", areas[i]);
					paramsList.add(item);
				}
				params.put("areasList", paramsList);
				params.put("size", 1);
				List<AreaBean> areaList = areaService.queryAreaCondition(params);
				if(areaList != null && !areaList.isEmpty()){
					for(AreaBean item : areaList){
						if(address.contains(item.getAreaname())){
							this.baseStationService.setAreaCenter(item.getAreaid().toString(),bigLat,biglng);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("suc", 0);
		}
		return map;
	}
	/**
	 *  导出小区信息
	 * @param vo
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadcellinfo")
	public ResponseEntity<byte[]> downloadCellInfo(@ModelAttribute CellVo vo,HttpServletRequest request){
		Long name = System.currentTimeMillis();
		//工程根路径
		String filePath = request.getSession().getServletContext().getRealPath("/");
			//判断路径是否存在,不存在就创建
			File path=new File(filePath.replace("\\", "/")+Constant.CAS_GIS_TEMPLATE_EXPORT_PATH);
				if(!path.exists()&&!path.isDirectory())  {
					path.mkdirs(); 
				}
			//文件存放于服务器的路径
			filePath = filePath.replace("\\", "/")+Constant.CAS_GIS_TEMPLATE_EXPORT_PATH+String.valueOf(name)+".xlsx";
		//type 0 框选导出 1小区导出 2 点击邻区导出 3导出小区加载
		//if(vo.getReport_type() == 1 || vo.getReport_type() == 0 ){
			if(vo.getReport_type() == 3){
				List<ReportCells> list = baseStationService.getReportLoadCells(vo);
				gisgradExcelDownLoadService.createReportLoadCell(list, filePath);
			}else{
				gisgradExcelDownLoadService.createCellInfoExcel(vo,filePath);
			}
		/*}else if(vo.getReport_type() == 2){
			List<GwCasCell> list =  baseStationService.getReportNearCell(vo);
			gisgradExcelDownLoadService.createCellInfoExcel(list, filePath ,0);
		}else if(vo.getReport_type() == 0){
			baseStationService.getRegiondownCell(vo,filePath);
		}else if(vo.getReport_type() == 3){
			List<GwCasCell> list = baseStationService.getReportLoadCell(vo);
			gisgradExcelDownLoadService.createReportLoadCell(list, filePath);
		}
			gisgradExcelDownLoadService.createNearCellInfoExcel(list, filePath);
		}*/
		
		//下载文件的名字
		String fileName = "cellInfos.xlsx";
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		header.setContentDispositionFormData("attachment",encodeFilename(fileName, request));
		
		File file = new File(filePath);
		byte[] bytes = null;
		try {
			bytes = FileCopyUtils.copyToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/searchCell", method = RequestMethod.GET)
	public ModelAndView searchCell(@ModelAttribute CellVo vo,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/gisgrad/searchCell");
		List<AreaBean> areas = areaService.getAllArea();
		mv.addObject("areas", areas);
		mv.addObject("cellVo", vo);
		return mv;
	}
	
	@RequestMapping(value = "/graphlist")
	public ModelAndView graphlist(@ModelAttribute VoBean vb,Integer openType,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/gisgrad/graphlist");
		mv.addObject("inside",vb.getInside()==null?"-1":vb.getInside());
		mv.addObject("grad",vb.getGrad());
		if(vb.getStartTime()== null||vb.getStartTime()==""){
			mv.addObject("startTime",DateUtils.getDayTime(new Date(), -1));
			
		}else{
			mv.addObject("startTime",vb.getStartTime());
		}
		if(vb.getEndTime()== null||vb.getEndTime()==""){
			mv.addObject("endTime",DateUtils.getDayTime(new Date(), 0));
		}else{
			mv.addObject("endTime",vb.getEndTime());
		}
		mv.addObject("areaids",vb.getAreaids());
		try {
			mv.addObject("areatext",new String(vb.getAreatext().getBytes("ISO-8859-1"),"UTF-8"));
			mv.addObject("testtypeText",new String(vb.getTesttypeText().getBytes("ISO-8859-1"),"UTF-8"));
			mv.addObject("testnetName",new String(vb.getTestnetName().getBytes("ISO-8859-1"),"UTF-8"));
			mv.addObject("senctext",new String(vb.getSenctext().getBytes("ISO-8859-1"),"UTF-8"));
		} catch (Exception e) {
			logger.error("Transcoding histogram conditions",e);
		}
		mv.addObject("senceids",vb.getSenceids());
		mv.addObject("testtype",vb.getTesttype());
		mv.addObject("nettype",vb.getNettype());
		mv.addObject("datatype",vb.getDatatype());
		mv.addObject("jobtype",vb.getJobtype());
		mv.addObject("kpi",vb.getKpi());
		mv.addObject("sernos",vb.getSernos());
		mv.addObject("testnet",vb.getTestnet());
		mv.addObject("openType",openType);
		return mv;
	}
}
