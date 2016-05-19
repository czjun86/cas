package com.complaint.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.action.vo.VoBean;
import com.complaint.model.Area;
import com.complaint.model.WorkOrder;
import com.complaint.page.PageBean;
import com.complaint.service.WorkOrderExcelService;
import com.complaint.service.WorkOrderService;
import com.complaint.utils.Constant;
import com.complaint.utils.DateUtils;
@Controller
@RequestMapping(value="/workorder")
public class WorkOrderController {
	private static final Logger logger = LoggerFactory.getLogger(WorkOrderController.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private WorkOrderExcelService workOrderExcelService;
	
	@RequestMapping(value="/workorderlist")
	public ModelAndView workOrderList(@ModelAttribute VoBean vo,String s_id,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/workorder/workorderlist");
		Date[] dates = getDates(vo.getStartTime(),vo.getEndTime());
		if(vo.getIsDeal()== null){
			vo.setIsDeal(-1);
		}
		if(vo.getAreaids() == null){
			vo.setAreaids("-1");
		}
		if(vo.getSenceids() == null){
			vo.setSenceids("-1");
		}
		if(vo.getTesttype()== null){
			vo.setTesttype("-1");
		}
		if(vo.getNettype()==null){
			vo.setNettype("-1");
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
		if(vo.getStartTime()== null){
			vo.setStartTime(DateUtils.getDayTime(new Date(), -1));
			
		}
		if(vo.getEndTime()== null){
			vo.setEndTime(DateUtils.getDayTime(new Date(), 0));
		}
		if(vo.getVerify() == null){
			vo.setVerify(-1);
		}
		
		if(vo.getWorkerOrderNet() == null){
			vo.setWorkerOrderNet("-1");
		}
		if(vo.getWorkerOrderNetName() == null){
			vo.setWorkerOrderNetName("全网络");
		}
		PageBean pb = workOrderService.countWorkOrderList(Constant.PAGESIZE, 1, vo,s_id);
		mv.addObject("pb", pb);
		mv.addObject("sernos", vo.getSernos());
		mv.addObject("isDeal", vo.getIsDeal());
		mv.addObject("testphone", vo.getTestphone());
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
		mv.addObject("inside", vo.getInside());
		mv.addObject("nettype", vo.getNettype());
		mv.addObject("testnetName", vo.getTestnetName());
		mv.addObject("s_id", s_id);
		mv.addObject("verify", vo.getVerify());
		mv.addObject("workerOrderNet", vo.getWorkerOrderNet());
		mv.addObject("workerOrderNetName", vo.getWorkerOrderNetName());
		return mv;
	}
	@RequestMapping(value = "/workorderlist/template")
	public String workOrderJson(Model model,Integer pageIndex,VoBean vo,String s_id,HttpServletRequest request){
		try {
			PageBean pb = this.workOrderService.getWorkOrderList(Constant.PAGESIZE, pageIndex,vo,s_id);
			List<WorkOrder> list = (List<WorkOrder>) pb.getList();
			model.addAttribute("list", list);
			model.addAttribute("contextPath", request.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		return "/workorder/childlist";
	}
	private Date[] getDates(String startTime,String endTime){
		Date startDate = null;
		Date endDate = null;
		if(StringUtils.isNotEmpty(startTime)){
			try {
				startTime = startTime + " 00:00:00";
				startDate = sdf.parse(startTime.replaceAll("-", "/"));
			} catch (ParseException e) {
				logger.debug(e.getMessage());
			}
		} 
		if(StringUtils.isNotEmpty(endTime)){
			try {
				endTime = endTime + " 23:59:59";
				endDate = sdf.parse(endTime.replaceAll("-", "/"));
			} catch (ParseException e) {
				logger.debug(e.getMessage());
			}
		}
		return new Date[]{startDate,endDate};
	}
	@RequestMapping(value = "/arealist")
	public ModelAndView areaList(String areaids,Integer type,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/workorder/arealist");
		mv.addObject("areaids", areaids);
		mv.addObject("type", type);
		return mv;
	}
	@RequestMapping(value = "/getarea")
	public @ResponseBody List<Area> getList(String areaids,Integer type,HttpServletRequest request){
		List<Area> arealist = new ArrayList<Area>();
		List<WorkOrder> list = this.workOrderService.getAllArea();
		List<Area> arealist1 = new ArrayList<Area>();
		
		Area ar = new Area();
		ar.setId(null);
		ar.setText("选择区域");
		ar.setChecked(false);
		ar.setState("open");
		if(areaids == null || areaids.equals("")){
			areaids = "-1";
		}
		String[] ids = areaids.split(",");
		if(type.equals(0)){
			Area areaall = new Area();
			areaall.setId(Integer.parseInt("-1"));
			areaall.setText("全部");
			areaall.setState("open");
			arealist1.add(areaall);
		}
		WorkOrder wo = null;
		Area area = null;
		
		for(int i = 0; i < list.size(); i++){
			area = new Area();
			wo = list.get(i);
			area.setId(wo.getAreaId());
			area.setText(wo.getAreaname());
			area.setState("open");
			for(String id:ids){
				if(Long.parseLong(id) == wo.getAreaId()){
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
	
	
	//业务状态多选 
		@RequestMapping(value = "/testtypelist")
		public ModelAndView testtypelist(@ModelAttribute VoBean vo,HttpServletRequest request){
			ModelAndView mv = new ModelAndView("/workorder/testtypelist");
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
				list.add(a1);//2G没有数据测试
			}else if(nettype!=null&&nettype.equals("4")){
				list.add(a2);//4G没有语音测试
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
			ModelAndView mv = new ModelAndView("/workorder/testNetlist");
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
			Area a6=new Area();
			a6.setId(5);
			a6.setText("LTE自由模式");
			Area a5=new Area();
			a5.setId(4);
			a5.setText("LTE锁频模式");
			if(nettype!=null&&nettype.equals("-1")){
				list.add(a1);
				list.add(a2);
				list.add(a4);
				list.add(a5);
				list.add(a6);
			}else if(nettype!=null&&nettype.equals("1")){
				list.add(a1);
				list.add(a2);
			}else if(nettype!=null&&nettype.equals("2")){
				list.add(a1);
				list.add(a4);
			}else if(nettype!=null&&nettype.equals("4")){
				list.add(a5);
			}else if(nettype!=null&&nettype.equals("5")){
				list.add(a6);
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
		
		@RequestMapping(value = "/workerOrderNetList")
		public ModelAndView workerOrderNetList( String netIds,HttpServletRequest request){
			ModelAndView mv = new ModelAndView("/workorder/workerOrderNetList");
			mv.addObject("netIds", netIds);
			return mv;
		}
		
		//网络制式
		@RequestMapping(value = "/getworkerOrderNet")
		public @ResponseBody List<Area> getworkerOrderNet(String netIds ,HttpServletRequest request){
			List<Area> list = new ArrayList<Area>();
			List<Map<String ,Object>> nets = workOrderService.getWorkerOrderNetList();
			String[] ids = netIds.split(",");
			
			Area areaall = new Area();
			areaall.setId(Integer.parseInt("-1"));
			areaall.setText("全网络");
			areaall.setState("open");
			for(String id:ids){
				if("-1".equals(id)){
					areaall.setChecked(true);
				}
			}
			
			List<Area> children = new ArrayList<Area>();
			Area area = null;
			for(int i = 0; i < nets.size(); i++){
				Map<String ,Object> net = nets.get(i);
				area = new Area();
				area.setId(i);
				area.setText(net.get("NET_WORKTYPE")!=null?net.get("NET_WORKTYPE").toString():"");
				area.setState("open");
				for(String id:ids){
					if(String.valueOf(i).equals(id)){
						area.setChecked(true);
						break;
					}
				}
				
				children.add(area);
			}
			areaall.setChildren(children);
			list.add(areaall);
			return list;
		}
		
		@RequestMapping(value = "/workorderlist/export")
		public @ResponseBody String workOrderExport(VoBean vo,String s_id,HttpServletRequest request){
			String filename ="-1";
			try {//生成文件存放于服务器的路径
				filename = workOrderExcelService.getMD5(String.valueOf(System.currentTimeMillis())+"workOrder");
				String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_WORKORDER_TEMPLATE_EXPORT_PATH;
				File filePath = new File(path);
				if(!filePath.exists()&&!filePath.isDirectory())  {
					filePath.mkdirs(); 
				}
				this.workOrderExcelService.getWorkExport(vo,s_id,path+filename+".xlsx");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("",e);
			}
			return filename;
		}
		
		/**
		 * 下载生成的Excel
		 * @param Name
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value="/downLoadExcel", method = RequestMethod.GET)
		public  ModelAndView downLoad(String name ,HttpServletRequest request ,HttpServletResponse response){
			ModelAndView mv = null;
			String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_WORKORDER_TEMPLATE_EXPORT_PATH+name+".xlsx";
			ServletOutputStream out = null;
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				//文件名称
				String fname=name+".xlsx";
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
