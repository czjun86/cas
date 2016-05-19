package com.complaint.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.complaint.dao.EpinfoDao;
import com.complaint.model.AreaBean;
import com.complaint.model.Epinfo;
import com.complaint.utils.Constant;

@Service("epinfoExcelService")
public class EpinfoExcelService {
	@Autowired
	private EpinfoDao epinfoDao;
	@Autowired
	private EpinfoService epinfoService;
	private static final Logger logger = LoggerFactory.getLogger(EpinfoExcelService.class);
	private List<Epinfo> el;
	private String errorname;//错误文件名字
	/**
	 * 查出area信息
	 */
	public List<AreaBean> getAreaBean(){
		return epinfoDao.getAreaBean();
	}
	/**
	 * 查出终端信息
	 */
	public List<Epinfo> getInformation(){
		return epinfoDao.getAllEpinfoList();
	}
	/**
	 * 生成终端信息excel
	 */
	public void createExcel(String path){
		List<Epinfo> list = getInformation();
		//创建excel和sheet
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("终端信息");
		
		//第一行 ,有就直接调用，没有就创建
		XSSFRow rowTop = null;
		if(sheet.getRow(0) != null){
			rowTop = sheet.getRow(0);
		}else{
			rowTop = sheet.createRow(0);
		}
		//获取列名样式
		CellStyle topStyle = getColumnStyle(wb);
			//第一列 区域
			XSSFCell areaTop = rowTop.createCell(0);
			areaTop.setCellValue("区域");
			areaTop.setCellStyle(topStyle);
			//第二列 UUID
			XSSFCell uuidTop = rowTop.createCell(1);
			uuidTop.setCellValue("UUID");
			uuidTop.setCellStyle(topStyle);
			//第三列 责任人
			XSSFCell functionaryTop = rowTop.createCell(2);
			functionaryTop.setCellValue("责任人");
			functionaryTop.setCellStyle(topStyle);
			//第四列 联系电话
			XSSFCell teltphoneTop = rowTop.createCell(3);
			teltphoneTop.setCellValue("联系电话");
			teltphoneTop.setCellStyle(topStyle);
			//第五列 授权状态
			XSSFCell islockTop = rowTop.createCell(4);
			islockTop.setCellValue("授权状态");
			islockTop.setCellStyle(topStyle);
			//第六列 更新时间
			XSSFCell updatetimeTop = rowTop.createCell(5);
			updatetimeTop.setCellValue("更新时间");
			updatetimeTop.setCellStyle(topStyle);
		//填充类容
			CellStyle style = getNormalStyle(wb);
			for(int i = 0;i<list.size();i++){
				XSSFRow row = null;
				if(sheet.getRow(i+1)!=null){
					row = sheet.getRow(i+1);
				}else{
					row = sheet.createRow(i+1);
				}
				//第一列 区域
				XSSFCell cellArea = row.createCell(0);
				cellArea.setCellValue(list.get(i).getAreaname()==null?"暂无":list.get(i).getAreaname());
				cellArea.setCellStyle(style);
				//第二列 uuid
				XSSFCell celluuid = row.createCell(1);
				celluuid.setCellValue(list.get(i).getUuid());
				celluuid.setCellStyle(style);
				//第三列 责任人
				XSSFCell cellFunctionary = row.createCell(2);
				cellFunctionary.setCellValue(list.get(i).getFunctionary() == null?"暂无":list.get(i).getFunctionary());
				cellFunctionary.setCellStyle(style);
				//第四列 联系电话
				XSSFCell cellteltphone = row.createCell(3);
				cellteltphone.setCellValue(list.get(i).getTeltphone()==null?"暂无":list.get(i).getTeltphone());
				cellteltphone.setCellStyle(style);
				//第五列 授权状态
				XSSFCell cellislock = row.createCell(4);
				cellislock.setCellValue(list.get(i).getIslock()==1?"已授权":"未授权");
				cellislock.setCellStyle(style);
				//第六列 更新时间
				XSSFCell cellupdatetime = row.createCell(5);
					//格式化时间
					String fmtime = timeFormat(list.get(i).getUpdatetime());
				cellupdatetime.setCellValue(fmtime);
				cellupdatetime.setCellStyle(style);
			}
		//单元格自动适应内容长度
			for(int i = 0; i < 6; i++){
				sheet.autoSizeColumn(i, true);
			}
			try {
				// 获取一个输出流
				FileOutputStream fileOut = new FileOutputStream(path);
				// 生成Excel
				wb.write(fileOut);
				// 关闭流
				fileOut.close();
			} catch (Exception e) {
				logger.error("close inputStream",e);
			}
	}
	/**
	 * 创建Excel每列title样式
	 */
	public XSSFCellStyle getColumnStyle(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		XSSFDataFormat format = wb.createDataFormat();
		// 加边框线
		//style.setBorderBottom(CellStyle.BORDER_THIN);// 下方加边框
		//style.setBottomBorderColor(IndexedColors.BLACK.getIndex());// 下方边框样式
		//style.setBorderLeft(CellStyle.BORDER_THIN);// 左方加边框
		//style.setLeftBorderColor(IndexedColors.BLACK.getIndex());// 左方边框样式
		//style.setBorderRight(CellStyle.BORDER_THIN);// 右方加边框
		//style.setRightBorderColor(IndexedColors.BLACK.getIndex());// 右方边框样式
		//style.setBorderTop(CellStyle.BORDER_THIN);// 上方加边框
		//style.setTopBorderColor(IndexedColors.BLACK.getIndex());// 上方边框样式
		// 对齐方式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置前端颜色
		style.setFillForegroundColor(new XSSFColor(new Color(0, 150, 255)));
		// 设置填充模式
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// 字体样式
		XSSFFont font = wb.createFont();// 创建字体对象
		font.setFontHeightInPoints((short) 15);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
		font.setFontName("宋体");// 设置为黑体字
		style.setFont(font);// 将字体加入到样式对象
		style.setDataFormat(format.getFormat("@"));
		return style;
	}
	/**
	 * 创建Excel数据一般样式
	 */
	public XSSFCellStyle getNormalStyle(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		XSSFDataFormat format = wb.createDataFormat();
		// 加边框线
		//style.setBorderBottom(CellStyle.BORDER_THIN);// 下方加边框
		//style.setBottomBorderColor(IndexedColors.BLACK.getIndex());// 下方边框样式
		//style.setBorderLeft(CellStyle.BORDER_THIN);// 左方加边框
		//style.setLeftBorderColor(IndexedColors.BLACK.getIndex());// 左方边框样式
		//style.setBorderRight(CellStyle.BORDER_THIN);// 右方加边框
		//style.setRightBorderColor(IndexedColors.BLACK.getIndex());// 右方边框样式
		//style.setBorderTop(CellStyle.BORDER_THIN);// 上方加边框
		//style.setTopBorderColor(IndexedColors.BLACK.getIndex());// 上方边框样式
		// 对齐方式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

		// 字体样式
		XSSFFont font = wb.createFont();// 创建字体对象
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
		font.setFontName("宋体");// 设置为黑体字
		style.setFont(font);// 将字体加入到样式对象
		style.setDataFormat(format.getFormat("@"));
		return style;
	}
	
	/**
	 * 格式化时间
	 */
	public String timeFormat(Date date){
		DateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = fm.format(date);
		return time;
	}
	
	/**
	 * 导入控制
	 * @param inp
	 * @return -1 文件类型错误，0成功
	 */
	public Map<String,Object> leadSerice(MultipartFile file,String filePath){
		Map<String,Object> mr = new HashMap<String,Object>();
		mr.put("result", 0);
		mr.put("add", 0);
		mr.put("update", 0);
		mr.put("unchange", 0);
		List<Epinfo> list = new ArrayList<Epinfo>();
		List<Epinfo> errorList = new ArrayList<Epinfo>();
		List<AreaBean> ab = this.getAreaBean();//查出所有areaid对应areaname
		InputStream inp = null;
		Map<String ,Object> leadMap = new HashMap<String ,Object>(0);
		int startnum = 0;
		try {
			inp = file.getInputStream();
			leadMap = this.getExcelInformation(inp);//解析excel
			list = (List<Epinfo>) leadMap.get("list");//解析excel
			startnum = (Integer) leadMap.get("startnum");
		} catch (IOException e) {
			logger.error("lead excel", e);
			mr.put("result", 1);
			return mr;
		} finally {
			try {
				if(inp!=null){
					inp.close();
				}
			} catch (IOException e) {
				logger.error("close inputStream", e);
			}
		}
			List<Integer> errornumm = new ArrayList<Integer>();
			for(int i=0;i<list.size();i++){
				boolean flag = false;
				//验证手机格式
				if(!this.isNum(list.get(i).getTeltphone(),i)){
					flag = true;
					errorList.add(list.get(i));
					errornumm.add(i+startnum);
					continue;
				}
				//验证负责人
				if(!flag){
					if(!this.isChinese(list.get(i).getFunctionary(),i)){
						flag = true;
						errorList.add(list.get(i));
						errornumm.add(i+startnum);
						continue;
					}
				}
				//验证区域
				if(!flag){
					int areaid = this.getAreaId(ab,list.get(i).getAreaname());
					if(areaid == 0){
						flag = true;
						errorList.add(list.get(i));
						errornumm.add(i+startnum);
						continue;
					}else{
						list.get(i).setAreaid(areaid);
					}
				}
				//判断UUID是否正确
				if(!flag){
					if(!judgeuuid(list,list.get(i).getUuid(), i)){
						flag = true;
						errorList.add(list.get(i));
						errornumm.add(i+startnum);
						continue;
					}
				}
			}
			InputStream inp2 = null;
			try {
				inp2 = file.getInputStream();
			} catch (IOException e) {
				logger.error("get io",e);
			}
			if(errornumm.size()>0){//如果有错误的行
					try {
						String errorname = this.createError(inp2,errornumm,filePath);
						mr.put("errorname", errorname);
					} catch (IOException e) {
						logger.error("create error template",e);
					}
			}
			try {
				if(inp2!=null){
					inp2.close();
				}
			} catch (IOException e) {
				logger.error("close io",e);
			}
			if(errornumm.size()<=0){
				Map<String ,List<Epinfo>> map = this.classifyUUID(list);
				//根据UUID分为新增或修改
					if(map.get("insert").size()!=0){
						if(this.insert(map.get("insert"))!=0){//保存新增数据
							mr.put("result", 2);
							return mr;
						}
					}
					if(map.get("update").size()!=0){
						for(Epinfo ep:map.get("update")){//修改已有的uuid
							try {
								epinfoService.updateEpinfo(ep);//保存修改数据
							} catch (Exception e) {
								logger.error("update epinfo",e);
								mr.put("result", 2);
								return mr;
							}
						}
					}
					mr.put("unchange", map.get("unchange").size());
					mr.put("add", map.get("insert").size());
					mr.put("update",map.get("update").size());
				}else{
					if(errornumm.size()>0){
						mr.put("result", 3);
					}
					mr.put("unchange",0);
					mr.put("add",0);
					mr.put("update",0);
				}
		return mr;
	}
	/**
	 * 解析excel
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public Map<String ,Object> getExcelInformation(InputStream inp) throws IOException{
		List<Epinfo> list = new ArrayList<Epinfo>();
		Map<String ,Object> map = new HashMap<String ,Object>();
		int startnum =0;//excel起始解析行
		//根据input流生成excel
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		//获得excel的第一条
		XSSFSheet sheet = wb.getSheetAt(0);
		    //获取行数   st.getLastRowNum();  
		    //遍历第一张表的所有行  

	    	boolean flag = false;//找到第一个返回“区域的行”
	    	int rownum =0;//只检查前10行
		    for(int i=0;i<sheet.getLastRowNum()+1;i++){
		    	Epinfo epinfo = new Epinfo();
		    	//获取行
		    	XSSFRow row = sheet.getRow(i);
		    	if(!flag){
		    		XSSFCell cell =null;
		    		cell= row.getCell(0);
		    		String value = getValue(cell);
		    		if("区域".equals(value)){
		    			flag = true;
		    			startnum = i;
		    		}else{
		    			rownum++;
		    		}
		    		if(rownum<10){
		    			continue;//前十行没找到数据就继续循环
		    		}else{
		    			break;//十行之后没找到数据就跳出
		    		}
		    	}
		    	//遇到第一个区域为空的就直接跳出
		    	String stra = null;
		    	boolean v0 = false;//判断区域是否为空
		    	boolean v1 = false;//判断UUID是否为空
		    	boolean v2 = false;//判断责任人是否为空
		    	boolean v3 = false;//判断联系电话是否为空
		    	for(int j=0;j<5;j++){
		    		XSSFCell cell = null;//如果输入一行中漏一项则捕获异常
						try {
							cell= row.getCell(j);
						} catch (Exception e) {
							logger.error("lead have no data of row",e);
							break;
						}
						String value = getValue(cell);
			    		if(j==0){//区域
			    			if(value == null){
			    				v0 = true;
			    			}
			    			epinfo.setAreaname(value);
			    		}else if(j==1){
			    			if(value == null){//UUID
			    				v1 = true;
			    			}
			    			epinfo.setUuid(value);
			    		}else if(j==2){//责任人
			    			if(value == null){
			    				v2 = true;
			    			}
			    			epinfo.setFunctionary(value);
			    		}else if(j==3){//联系电话
			    			if(value == null){
			    				v3 = true;
			    			}
			    			epinfo.setTeltphone(value);
			    		}else if(j==4){//授权
			    			if(v0&&v1&&v2&&v3){
			    				break;
			    			}
			    			int islock =0;
				    			if(value == null||value.equals("未授权")){
				    				islock =0;
				    			}else if(value.equals("已授权")){
				    				islock =1;
				    			}else{
				    				break;
				    			}
			    			epinfo.setIslock(islock);
			    		}
		    	}
		    	if(v0&&v1&&v2&&v3){//当四个属性全为空的时候，跳出循环,并不 
		    		break;
		    	}
		    		list.add(epinfo);
		    } 
		    map.put("list", list);
		    map.put("startnum", startnum);
		return map;
	}
	/**
	 * 获取单元格内容
	 */
	public String getValue(XSSFCell cell){
		String value = null;
		if(cell==null){
			return value;
		}
		//查出单元格类型
		try {
			switch(cell.getCellType()){
			case XSSFCell.CELL_TYPE_STRING://字符串--文本类型
					value = cell.getRichStringCellValue().toString().trim();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC://数字类型
				if (DateUtil.isCellDateFormatted(cell)){//时间类型
					value = cell.getDateCellValue().toString();
				}else{
					DecimalFormat df = new DecimalFormat("0");
					String str = String.valueOf(df.format(cell.getNumericCellValue())).trim();
					if(str!=null&&!"".equals(str)){
						String [] strArray =str.split(".");
						if(strArray!=null&&strArray.length>1){
							value = strArray[0];
						}else{
							value = str;
						}
					}
				}
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN://boolean类型
					value = String.valueOf(cell.getBooleanCellValue()).trim();
				break;
			case XSSFCell.CELL_TYPE_FORMULA://公式类型
					value = cell.getCellFormula().trim();
				break;
			}
		} catch (Exception e) {
			logger.error("get lead excel null",e);
		}
		return value;
	}
	/**
	 * 判断电话内容全是数字
	 */
	public boolean isNum(String str ,Integer i){
		if(str == null){
			return false;
		}
		String param = "^(([0-9]{3}-[0-9]{8})|([0-9]{4}-[0-9]{7})|([0-9]{8})|([0-9]{11})|[0-9]{7}){1}$";
			Pattern pattern = Pattern.compile(param);
		    Matcher  matcher = pattern.matcher( str );
		    if(!matcher.matches()){
		    	return false;
		    }
		return true;
	}
	
	/**
	 * 验证名字为中文
	 */
	public boolean isChinese(String str ,Integer i){
		if(str == null){
			return false;
		}
		if(str!=null){
			String param = "^[\u4e00-\u9fa5]+$";
				Pattern pattern = Pattern.compile(param);
			    Matcher  matcher = pattern.matcher(str);
			    if(!matcher.matches()){
			    	return false;
			    }
		}
		return true;
	}
	
	/**
	 * 验证根据区域查出并赋值区域id
	 */
	public Integer getAreaId(List<AreaBean> ab,String str){
		int areaid = 0;
		if(str == null){
			return areaid;
		}
		for(int j=0;j<ab.size();j++){
			if(ab.get(j).getAreaname().equals(str)){
				areaid = ab.get(j).getAreaid();
			}
		}
		return areaid;
	}
	
	/**
	 * 将UUID按照新增和修改，错误分类
	 */
	public Map<String,List<Epinfo>> classifyUUID(List<Epinfo> list){
		Map<String,List<Epinfo>> map = new HashMap<String,List<Epinfo>>();
		List<Epinfo> insertEP = new ArrayList<Epinfo>();//存放数据库没有的
		List<Epinfo> updateEP = new ArrayList<Epinfo>();//存放数据库已有的
		List<Epinfo> errorEP = new ArrayList<Epinfo>();//存放数据库已有的
		List<Epinfo> unchange = new ArrayList<Epinfo>();//为改变的
		List<Epinfo> alluuid = this.getInformation();//查出数据库所有的信息
		for(int i=0;i<list.size();i++){
			String leadep = list.get(i).getUuid();
			boolean flag = false;//用来判断在某部是否验证通过加入list，是就跳出当前循环
			
			for(Epinfo epinfo:alluuid){//判断uuid在数据库中是否存在
				if(epinfo.getUuid().equals(leadep)){
					if((epinfo.getFunctionary()==null?"":epinfo.getFunctionary()).equals(list.get(i).getFunctionary()) &&
							(epinfo.getAreaid()==null?"":epinfo.getAreaid()).equals(list.get(i).getAreaid()) &&
							(epinfo.getTeltphone()==null?"":epinfo.getTeltphone()).equals(list.get(i).getTeltphone()) &&
							(epinfo.getIslock()==null?0:epinfo.getIslock()) == list.get(i).getIslock()){//如果完全相等，不做处理
						flag = true;
						unchange.add(list.get(i));
						break;
					}
					list.get(i).setId(epinfo.getId());
					updateEP.add(list.get(i));//存在就将epinfo加入insertEP
					flag = true;
					break;
					
				}
			}
			if(flag == true){continue;}//epinfo判断是否加入insertEP，是就跳出当前这次
			
			if(!this.uuidFormat(leadep)){//判断uuid是否格式正确，不正确就加入错误集合
				errorEP.add(list.get(i));
				continue;//加入错误集合后跳出本次循环
			}
			
			insertEP.add(list.get(i));//数据库不存在，格式又正确，加入新增集合
			
		}
		map.put("insert", insertEP);
		map.put("update", updateEP);
		map.put("error", errorEP);
		map.put("unchange", unchange);
		return map;
	}
	
	/**
	 * 判断UUID的格式是否正确
	 */
	public boolean uuidFormat(String str){
		if(str == null){
			return false;
		}
		String param = "^[0-9]{15}$";
		Pattern pattern = Pattern.compile(param);
	    Matcher  matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	
	/**
	 * 整理格式调用原来的存储方式
	 */

	public Integer insert(List<Epinfo> epinfo){
		String[] uuids = new String[epinfo.size()];
		Integer[] areaid = new Integer[epinfo.size()];
		String[] functionary = new String[epinfo.size()];
		String[] teltphone = new String[epinfo.size()];
		Integer[] islock = new Integer[epinfo.size()];
		for(int i = 0 ;i<epinfo.size();i++){
			uuids[i] = epinfo.get(i).getUuid();
			areaid[i] = epinfo.get(i).getAreaid();
			functionary[i] = epinfo.get(i).getFunctionary();
			teltphone[i] = epinfo.get(i).getTeltphone();
			islock[i] = epinfo.get(i).getIslock();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = epinfoService.addEpinfo(uuids,areaid,functionary,teltphone,islock);
		} catch (Exception e) {
			logger.error("batch add EpInfo error.",e);
			map.put("msg", 0);
		}
		if(map.get("msg") != null && (Integer.parseInt(map.get("msg").toString()) == 1)){
			return 0;
	    }else{
	    	return -1;
	    }
	}
	/**
	 * 判断UUID是否正确
	 */
	public boolean judgeuuid(List<Epinfo> list ,String uuid ,Integer num){
		if(uuid!=null && uuidFormat(uuid)){
			for(int i=0;i<list.size();i++){
				if(uuid.equals(list.get(i).getUuid()) && num!=i){
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 生成错误文件
	 * @throws IOException 
	 */
	public String createError(InputStream inp ,List<Integer> errornumm,String filePath) throws IOException{
		//根据input流生成excel
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		//获得excel的第一条
		XSSFSheet sheet = wb.getSheetAt(0);
		//第一行 ,有就直接调用，没有就创建
		XSSFRow row = null;
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(new XSSFColor(new Color(255,0,0)));// 设置前端颜色
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置填充模式
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		for(int i = 0;i<errornumm.size();i++){
			if(sheet.getRow(errornumm.get(i)+1) != null){
				row = sheet.getRow(errornumm.get(i)+1);
			}else{
				row = sheet.createRow(errornumm.get(i)+1);
			}
			for(int j = 0;j<5;j++){
				XSSFCell cell = null;
				if(row.getCell(j) == null){
					cell = row.createCell(j);//如果输入一行中漏一项则捕获异常
				}else{
					cell = row.getCell(j);
				}
				cell.setCellStyle(style);
			}
			//row.setRowStyle(style);
		}

		Date d = new Date();
		long time = d.getTime();
		String errorname = String.valueOf(time);
		try {
			//判断路径是否存在
			File file=new File(filePath+Constant.CAS_SYSTEM_TERMINAL_ERROR_PATH);
			if(!file .exists()&&!file .isDirectory())  {
			file .mkdirs(); 
			}
			// 获取一个输出流
			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(filePath+Constant.CAS_SYSTEM_TERMINAL_ERROR_PATH+errorname+".xlsx");
			} catch (Exception e) {
				logger.error("get error excel fault" , e);
			}
			// 生成Excel
			wb.write(fileOut);
			// 关闭流
			if(fileOut!=null){
				fileOut.close();
			}
		} catch (Exception e) {
			logger.error("close inputStream",e);
		}
		return errorname;
	}
	/**
	 * 获取错误文件名字
	 */
	public String getErrorName(){
		return errorname;
	}
}
