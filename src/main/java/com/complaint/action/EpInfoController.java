package com.complaint.action;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.model.AreaBean;
import com.complaint.model.Epinfo;
import com.complaint.model.Resource;
import com.complaint.page.PageBean;
import com.complaint.service.EpinfoExcelService;
import com.complaint.service.EpinfoService;
import com.complaint.utils.Constant;
import com.complaint.utils.RoleResourceLoader;
import com.complaint.utils.TokenProcessor;
@Controller
@RequestMapping("/epinfo")
public class EpInfoController {
	private static final Logger logger = LoggerFactory.getLogger(EpInfoController.class);
	@Autowired
	private EpinfoService epinfoService;
	@Autowired
	private RoleResourceLoader roleResourceLoader;
	@Autowired
	private EpinfoExcelService epinfoExcelService;
	@RequestMapping(value = "/epinfolist")
	public ModelAndView epinfolist(String uuid,HttpServletRequest request,String errorname){
		ModelAndView mv = new ModelAndView("/epinfo/epinfolist");
		PageBean pb = this.epinfoService.countEqinfos(uuid ,1,Constant.PAGESIZE);
		List<Resource> buttons = this.roleResourceLoader.getButtons(request);
		String epdown ="";
		String eplead = "";
		for(Resource resource:buttons){
			if(resource.getUrl().equals("/epdown")){
				epdown = "epdown";
			}else if(resource.getUrl().equals("/eplead")){
				eplead = "eplead";
			}
		}
		mv.addObject("errorname",errorname);
		mv.addObject("epdown",epdown);
		mv.addObject("eplead",eplead);
		mv.addObject("buttons", buttons);
		mv.addObject("uuid", uuid);
		mv.addObject("pb", pb);
		return mv;
	}
	@RequestMapping(value = "/epinfolist/template")
	public String epinfolistJson(Model model,String uuid,Integer pageIndex,HttpServletRequest request){

		try {PageBean pb = this.epinfoService.getEpinfoList(uuid,pageIndex,Constant.PAGESIZE);
			List<Epinfo> list = (List<Epinfo>) pb.getList();
			List<Resource> buttons = this.roleResourceLoader.getButtons(request);
			model.addAttribute("buttons", buttons);
			model.addAttribute("list", list);
			model.addAttribute("contextPath", request.getContextPath());
		} catch (Exception e) {
			logger.error("get epinfo menu",e);
		} 
		return "/epinfo/childlist";
	}
	@RequestMapping(value="/addEpinfo", method = RequestMethod.GET)
	public ModelAndView addEpinfo(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/epinfo/addEpinfo");
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
	    tokenProcessor.mvAddToken(mv, request);
	    List<AreaBean> list= epinfoExcelService.getAreaBean();
	    mv.addObject("areas",list);
		return mv;
	}
	@RequestMapping(value="/updateEpinfo", method = RequestMethod.GET)
	public ModelAndView updateEpinfo(HttpServletRequest request,Integer id){
		ModelAndView mv = new ModelAndView("/epinfo/updateEpinfo");
		Epinfo epinfo = this.epinfoService.getEpinfoById(id);
		List<AreaBean> list = epinfoExcelService.getAreaBean();
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
	    tokenProcessor.mvAddToken(mv, request);
	    mv.addObject("areas",list);
		mv.addObject("epinfo", epinfo);
		return mv;
	}
	@RequestMapping(value="/updateEpinfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> updateRole(@ModelAttribute Epinfo epinfo,HttpServletRequest request){
		Map<String, Integer> map = new HashMap<String, Integer>();
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
		int msg = 0;
		if(!tokenProcessor.isTokenValid(request)){
            msg = Constant.TOKEN_RESUBMIT;
        }else{ 
        	Epinfo temp = this.epinfoService.getEpinfoById(epinfo.getId());
        	if(temp.getUuid().equals(epinfo.getUuid())){
        		try {
					msg = this.epinfoService.updateEpinfo(epinfo);
				} catch (Exception e) {
					logger.error("update EpInfo error.",e);
					msg = 0;
				}
        		tokenProcessor.reset(request);
        	}else{        		
        		boolean msgbol =  this.epinfoService.isExsit(epinfo.getUuid());
        		if(msgbol){
					msg = -1;
				}else{	
        			try {
						msg = this.epinfoService.updateEpinfo(epinfo);
					} catch (Exception e) {
						logger.error("update EpInfo error.",e);
						msg = 0;
					}
        			tokenProcessor.reset(request);
				}
        	}
        }
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value="/addEpinfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addEpinfo(String[] uuid,Integer[] areaid,String[] functionary,String[] teltphone,HttpServletRequest request){
		TokenProcessor tokenProcessor=TokenProcessor.getInstance();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer[] islock = new Integer[uuid.length];
		for(int i = 0 ;i<uuid.length;i++){
			islock[i] = 0;
		}
		if(!tokenProcessor.isTokenValid(request)){
            map.put("msg", Constant.TOKEN_RESUBMIT);
        }else{ 
		    try {
				map = this.epinfoService.addEpinfo(uuid,areaid,functionary,teltphone,islock);
			} catch (Exception e) {
				logger.error("batch add EpInfo error.",e);
				map.put("msg", 0);
			}
		    if(map.get("msg") != null && (Integer.parseInt(map.get("msg").toString()) != -1 && Integer.parseInt(map.get("msg").toString()) != -2)){		    	
		    	tokenProcessor.reset(request);
		    }
        }
		return map;
	}
	
	@RequestMapping(value="/deleteEpinfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Integer> deleteEpinfo(String ids){
		Map<String, Integer> map = new HashMap<String, Integer>();
		int msg =0;
		try {
			msg = this.epinfoService.deleteEpinfo(ids);
		} catch (Exception e) {
			logger.error("delete EpInfo error.",e);
		}
		map.put("msg", msg);
		return map;
	} 
	
	@RequestMapping(value="/isExsit", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> isExsit(String uuid){
		Map<String, String> map = new HashMap<String, String>();
		boolean msg =  this.epinfoService.isExsit(uuid);
		String msgstr = "-1";
		if(msg){
			msgstr = "UUID已经存在！";
		}
		map.put("msg", msgstr);
		return map;
	}
	
	@RequestMapping(value="/uuidIsExsit", method = RequestMethod.POST)
	public @ResponseBody boolean uuidIsExsit(String uuid){
		boolean msg =  this.epinfoService.isExsit(uuid);
		return !msg;
	}
	
	
	/**
	 * 下载终端信息报表
	 * @throws IOException 
	 */
	@RequestMapping(value="/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException{
		//生成文件存放于服务器的路径
		String filename = String.valueOf(System.currentTimeMillis())+".xlsx";
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_SYSTEM_TERMINAL_ERROR_PATH;
		//终端信息导出报表名
		String name = "epinfo.xlsx";
		File filePath = new File(path);
		if(!filePath.exists()&&!filePath.isDirectory())  {
			filePath.mkdirs(); 
		}
		epinfoExcelService.createExcel(path+filename);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		header.setContentDispositionFormData("attachment",encodeFilename(name, request));
		File file = new File(path+filename);
			byte[] bytes = FileCopyUtils.copyToByteArray(file);
			return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}
	/**
	 * 下载文件名称
	 * @param name
	 * @param request
	 * @return
	 */
	private String encodeFilename(String name, HttpServletRequest request) {
		String agent = request.getHeader("USER-AGENT");
		try {
			if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
				String fileName = URLEncoder.encode(name, "UTF-8");
				fileName = StringUtils.replace(fileName, "+", "%20");
				if (fileName.length() > 150) {
					fileName = new String(name.getBytes("GB2312"),
							"ISO8859-1");
					fileName = StringUtils.replace(fileName, " ", "%20");
				}
				return fileName;
			}
			if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
				return MimeUtility.encodeText(name, "UTF-8", "B");

			return name;
		} catch (Exception ex) {
			logger.error("down and lead of util in epinfo",ex);
			return name;
		}
	}
	/**
	 * 下载批量导入模板
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/getTemplate")
	public ResponseEntity<byte[]> getTemplate(HttpServletRequest request) throws IOException{
		//生成文件存放于服务器的路径
		String filePath = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_TEMPLATE_PATH;
			//终端信息导出报表名
			String name = "epinfoTemplate.xlsx";
			HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				header.setContentDispositionFormData("attachment",encodeFilename(name, request));
			File file = new File(filePath+name);
			byte[] bytes = FileCopyUtils.copyToByteArray(file);
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}
	/**
	 * 批量导入
	 * @param request
	 * @param response
	 * @return 0成功  1解析失败 2UUID有重复 ,3表示有错误excel要返回
	 * @throws IOException 
	 */
	@RequestMapping(value="leadExcel", method = RequestMethod.POST)
	public  ModelAndView leadExcel(HttpServletRequest request,@RequestParam("excelFile")MultipartFile file) throws IOException{
		//生成文件存放于服务器的路径
		String filePath = request.getSession().getServletContext().getRealPath("/");
		filePath = filePath.replace("\\", "/");
		Map<String,Object> result = epinfoExcelService.leadSerice(file,filePath);
		ModelAndView mv = new ModelAndView("/epinfo/lead");
		if((Integer)result.get("result") == 3){//生成错误集合的excel
			mv.addObject("errorname" ,result.get("errorname"));
		}
		mv.addObject("result", result.get("result"));
		mv.addObject("add", result.get("add"));
		mv.addObject("update", result.get("update"));
		mv.addObject("unchange", result.get("unchange"));
		return mv;
	}
	/**
	 * 导出错误文件
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/getErrorFile")
	public ResponseEntity<byte[]> getErrorFile(HttpServletRequest request,String errorname) throws IOException{
		//生成文件存放于服务器的路径
		String filePath = request.getSession().getServletContext().getRealPath("/");
		filePath = filePath.replace("\\", "/")+Constant.CAS_SYSTEM_TERMINAL_ERROR_PATH+errorname+".xlsx";
		//终端信息导出报表名
		String name = errorname+".xlsx";
		HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentDispositionFormData("attachment",encodeFilename(name, request));
		File file = new File(filePath);
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}
}
