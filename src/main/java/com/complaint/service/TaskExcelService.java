package com.complaint.service;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.complaint.dao.BaseDao;
import com.complaint.dao.TaskConfigDao;
import com.complaint.model.AreaBean;
import com.complaint.model.TaskOrder;
import com.complaint.utils.StringUtils;

@Service("taskExcelService")
public class TaskExcelService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskExcelService.class);
	
	@Autowired
	private TaskConfigDao taskConfigDao;
	@Autowired
	private TaskConfigService taskConfigService;
	@Autowired
	private BaseDao baseDao;
	
	public static final Integer[] tempInteger= new Integer[]{0,1,2,3};
	public static final Integer[] reportInteger= new Integer[]{2,3,4,5,0};
	
	/**
	 * 生成模版
	 * @param path
	 * @param userid
	 * @throws Exception
	 */
	public void getTemp(String path ,Integer userid)throws Exception{
		List<AreaBean> areas = new ArrayList<AreaBean>();
		areas = this.taskConfigDao.getAreaList(userid);
		
		XSSFWorkbook wb = new XSSFWorkbook(); 
		XSSFSheet sheet = wb.createSheet("sheet1");
		
		XSSFCellStyle style = wb.createCellStyle();
		XSSFDataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("@"));  
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		
		
		Row row = sheet.createRow(0);
		
		XSSFCellStyle styleTitle = wb.createCellStyle();
		styleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
		styleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		styleTitle.setFillForegroundColor(new XSSFColor(new Color(59,124,196)));// 设置前端颜色
		styleTitle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置填充模式
		//加边框
		styleTitle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		String[] title = new String[]{"区域","测试网络","测试环节","测试地址"};

		Map<Integer , Integer> sizeMap = new HashMap<Integer , Integer>();
		for(int i=0;i<title.length;i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(styleTitle);
			sizeMap.put(i, StringUtils.strByte(title[i]));
		}
		
		//下拉类容
		List<String> networktype = new ArrayList<String>();
		List<String> breakflag = new ArrayList<String>();
		
		String [] areaname =new String[areas.size()];
		String [] nwt = {"2g数据","2g语音","3g数据","3g语音"};
		String [] bf = {"优化","建设","维护","其他"};
		for(int i = 0;i<areas.size();i++){
			AreaBean ab = areas.get(i);
			areaname[i] = ab.getAreaname();
		}
		
		Row row2 = sheet.createRow(1);
		Cell cell1 = row2.createCell(0);
		if(areaname.length>0){
			cell1.setCellValue(areaname[0]);
		}
		cell1.setCellStyle(style);
		
		Cell cell2 = row2.createCell(1);
		cell2.setCellValue("2g数据");
		cell2.setCellStyle(style);
		
		Cell cell3 = row2.createCell(2);
		cell3.setCellValue("优化");
		cell3.setCellStyle(style);
		
		//生成区域下拉框
		DataValidation validate1 =setValidate(sheet ,(short)1 ,(short) 1,(short) 0,(short)0 ,areaname);
		//生成测试网络下拉框
		DataValidation validate2 =setValidate(sheet ,(short)1 ,(short) 1,(short)1,(short)1 ,nwt);
		//生成测试环节下拉框
		DataValidation validate3 =setValidate(sheet ,(short)1 ,(short) 1,(short) 2,(short)2 ,bf);
		
		// 设定规则  
        sheet.addValidationData(validate1);
        sheet.addValidationData(validate2);
        sheet.addValidationData(validate3);
		
		for(int i=0;i<title.length;i++){
			sheet.setColumnWidth(i,(8+2)*256);
		}
		

		FileOutputStream out = new FileOutputStream(path);
		try {
			wb.write(out);
		} catch (Exception e) {
			logger.error("Create Excel temp",e);
		}finally{
			out.close();
		}
	}
	
	/**
	 * 生成下拉框
	 */
	public static DataValidation setValidate(XSSFSheet sheet, short beginRow,  
	        short endRow, short beginCol, short endCol ,String[] textList) {  
	    XSSFDataValidationHelper helper = new XSSFDataValidationHelper(sheet);  
	    DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);  
	    
	    CellRangeAddressList regions = new CellRangeAddressList(beginRow,  
	             endRow, beginCol ,endCol);  
	    return helper.createValidation(constraint, regions);  
	} 
	
	/**
	 * 根据条件创建报表
	 * @param path
	 * @param userid
	 * @param to
	 * @throws Exception
	 */
	public void createExcel(String path ,TaskOrder to)throws Exception{
		List<AreaBean> areas = new ArrayList<AreaBean>();
		areas = this.taskConfigDao.getAreaList(to.getHandler());
		String [] areaname =new String[areas.size()];
		String [] nwt = {"2g数据","2g语音","3g数据","3g语音"};
		String [] bf = {"优化","建设","维护","其他"};
		for(int i = 0;i<areas.size();i++){
			AreaBean ab = areas.get(i);
			areaname[i] = ab.getAreaname();
		}
		
		List<TaskOrder> tasks = this.taskConfigDao.createExcel(to);
		XSSFWorkbook wb = new XSSFWorkbook(); 
		XSSFSheet sheet = wb.createSheet("sheet1");
		
		XSSFCellStyle style = wb.createCellStyle();
		XSSFDataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("@"));  
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		
		Row rowtitle = sheet.createRow(0);
		XSSFCellStyle styleTitle = wb.createCellStyle();
		styleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
		styleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		styleTitle.setFillForegroundColor(new XSSFColor(new Color(59,124,196)));// 设置前端颜色
		styleTitle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置填充模式
		//加边框
		styleTitle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		String[] title = new String[]{"工单号","创建时间","区域","测试网络","测试环节",
				"测试地址","测试状态","审核状态","有效状态"};
		Map<Integer , Integer> sizeMap = new HashMap<Integer , Integer>();
		for(int i=0;i<title.length;i++){
			Cell cell = rowtitle.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(styleTitle);
			sizeMap.put(i, StringUtils.strByte(title[i]));
		}
		
		int count = 0;
		for(int i =0;i<tasks.size();i++){
			count = 0;
			TaskOrder task = tasks.get(i);
			Row row = sheet.createRow(i+1);
			
			setSizeMap(task.getSerialno()!=null?task.getSerialno():"",count ,sizeMap);
			Cell cell0 = row.createCell(count++);
			cell0.setCellValue(task.getSerialno()!=null?task.getSerialno():"");
			cell0.setCellStyle(style);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			setSizeMap(task.getCreate_date()!=null?df.format(task.getCreate_date()):"",count ,sizeMap);
			Cell cell1 = row.createCell(count++);
			cell1.setCellValue(task.getCreate_date()!=null?df.format(task.getCreate_date()):"");
			cell1.setCellStyle(style);
			
			setSizeMap(task.getAreaname()!=null?task.getAreaname():"",count ,sizeMap);
			Cell cell2 = row.createCell(count++);
			cell2.setCellValue(task.getAreaname()!=null?task.getAreaname():"");
			cell2.setCellStyle(style);
			
			setSizeMap(task.getNetworktype()!=null?task.getNetworktype():"",count ,sizeMap);
			Cell cell3 = row.createCell(count++);
			cell3.setCellValue(task.getNetworktype()!=null?task.getNetworktype():"");
			cell3.setCellStyle(style);
			
			setSizeMap(task.getBreakflag()!=null?getBreakflag(task.getBreakflag()):"",count ,sizeMap);
			Cell cell4 = row.createCell(count++);
			cell4.setCellValue(task.getBreakflag()!=null?getBreakflag(task.getBreakflag()):"");
			cell4.setCellStyle(style);
			
			setSizeMap(task.getTest_address()!=null?task.getTest_address():"",count ,sizeMap);
			Cell cell5 = row.createCell(count++);
			cell5.setCellValue(task.getTest_address()!=null?task.getTest_address():"");
			cell5.setCellStyle(style);
			
			setSizeMap(task.getNum()!=null?(task.getNum()>0?"已测试":"未测试"):"未测试",count ,sizeMap);
			Cell cell6 = row.createCell(count++);
			cell6.setCellValue(task.getNum()!=null?(task.getNum()>0?"已测试":"未测试"):"未测试");
			cell6.setCellStyle(style);
			
			setSizeMap(task.getIsverify()!=null?getIsverify(task.getIsverify()):"",count ,sizeMap);
			Cell cell7 = row.createCell(count++);
			cell7.setCellValue(task.getIsverify()!=null?getIsverify(task.getIsverify()):"");
			cell7.setCellStyle(style);
			
			setSizeMap(task.getValidstate()!=null?(task.getValidstate()==1?"已失效":"有效"):"有效",count ,sizeMap);
			Cell cell8 = row.createCell(count++);
			cell8.setCellValue(task.getValidstate()!=null?(task.getValidstate()==1?"已失效":"有效"):"有效");
			cell8.setCellStyle(style);
			
			//生成区域下拉框
			DataValidation validate1 =setValidate(sheet ,(short)(i+1) ,(short) (i+1),(short) 2,(short)2 ,areaname);
			//生成测试网络下拉框
			DataValidation validate2 =setValidate(sheet ,(short)(i+1) ,(short) (i+1),(short) 3,(short)3 ,nwt);
			//生成测试环节下拉框
			DataValidation validate3 =setValidate(sheet ,(short)(i+1) ,(short) (i+1),(short) 4,(short)4 ,bf);
			
			// 设定规则  
	        sheet.addValidationData(validate1);
	        sheet.addValidationData(validate2);
	        sheet.addValidationData(validate3);
		}
		
		for(int i=0;i<title.length;i++){
			sheet.setColumnWidth(i,(sizeMap.get(i)+2)*256);
		}
		
		FileOutputStream out = new FileOutputStream(path);
		try {
			wb.write(out);
		} catch (Exception e) {
			logger.error("Create Excel for report",e);
		}finally{
			out.close();
		}
	}
	
	/**
	 * 审核状态
	 * @param i
	 * @return
	 */
	public String getIsverify(Integer i){
		String str ="";
		switch(i){
		case 0:
			str ="未审核";
			break;
		case 1:
			str ="已通过";
			break;
		case 2:
			str ="未通过";
			break;
		default:
			str ="未审核";
				break;
		}
		return str;
	}
	/**
	 * 测试环节
	 * @param i
	 * @return
	 */
	public String getBreakflag(Integer i){
		String str ="";
		switch(i){
		case 1:
			str ="优化";
			break;
		case 2:
			str ="建设";
			break;
		case 3:
			str ="维护";
			break;
		case 4:
			str ="其他";
			break;
		default:
			str ="";
				break;
		}
		return str;
	}
	/**
	 * 测试网络
	 * @param i
	 * @return
	 */
	public String getNetworktype(Integer i){
		String nwt ="";
		switch (i) {
		case 1:
			nwt = "2g数据";
			break;
		case 2:
			nwt = "2g语音";
			break;
		case 3:
			nwt = "3g数据";
			break;
		case 4:
			nwt = "3g语音";
			break;

		default:
			nwt = "未知";
			break;
		}
		return nwt;
	}
	
	/**
	 * 判断当前内容长度大于存放长度就加入map
	 */
	public void setSizeMap(String val ,Integer index ,Map<Integer ,Integer> map){
		try {
			if(val != null){
				int oldlength = map.get(index);
				int newlength = StringUtils.strByte(val);
				if(newlength>oldlength){
					map.put(index, newlength);
				}
			}
		} catch (Exception e) {
			logger.error("excel report count title length" ,e);
		}
	}
	
	/**
	 * 解析模板
	 * @param file
	 * @param filePath
	 * @throws IOException 
	 */
	public String analysisExcel(MultipartFile file,String filePath ,Integer userid,String name) throws Exception{
		String msg = "1";
		//用户拥有的区域
		List<AreaBean> areas = new ArrayList<AreaBean>();
		areas = this.taskConfigDao.getAreaList(userid);
		
		InputStream inp = null;
		Map<String, Object> map;

		try {
			inp = file.getInputStream();
			map = getInformation(inp);
		} catch (Exception e) {
			throw new Exception();
		}finally{
			if(inp!=null){
				inp.close();
			}
		}
		List<TaskOrder> tos = new ArrayList<TaskOrder>();
		
		//解析内容
		List<TaskOrder> tasks = new ArrayList<TaskOrder>();
		tasks = (List<TaskOrder>) map.get("tos");
		
		//错误行列
		List<Integer> rowerror = new ArrayList<Integer>();
		List<Integer> cellerror = new ArrayList<Integer>();
		
		Integer[] order =null;
		String typeExcel = (String) map.get("type");
		if("temp".equals(typeExcel)){//模板类型
			order = tempInteger;
		}else if("report".equals(typeExcel)){//下载报表类型
			order = reportInteger;
		}
		//解析行数
		int count = (Integer) map.get("count");
		boolean flag = false;
		String area = null;
		String netType =null;
		String breakflag = null;
		String address = null;
		for(int i=0;i<count;i++){
			TaskOrder to = new TaskOrder();
			TaskOrder task = tasks.get(i);
			
			//判断放入区域
			flag = false;
			area =task.getAreaname();
			for(AreaBean ab:areas){
				if((ab.getAreaname()).equals(area)){
					to.setAreaid(ab.getAreaid());
					flag =true;
				}
			}
			if(!flag){//有错误就标记
				rowerror.add(i+1);
				cellerror.add(order[0]);
			}
			
			//判断测试网络
			flag = false;
			netType =task.getNetworktype();
			String[] totalnet = new String[]{"2g数据","2g语音","3g数据","3g语音"};
			for(String net:totalnet){
				if(net.equals(netType)){
					to.setNetworktype(netType);
					flag =true;
				}
			}
			if(!flag){//有错误就标记
				rowerror.add(i+1);
				cellerror.add(order[1]);
			}
			
			//判断测试环节
			flag = false;
			breakflag =task.getBreakflagName();
			String[] breakflags = new String[]{"优化","建设","维护","其他"};
			for(int j =0; j<breakflags.length;j++){
				String bfs = breakflags[j];
				if(bfs.equals(breakflag)){
					to.setBreakflag(j+1);
					flag =true;
				}
			}
			if(!flag){//有错误就标记
				rowerror.add(i+1);
				cellerror.add(order[2]);
			}
			
			//测试地址
			address = task.getTest_address();
			to.setTest_address(address);
			if(address==null||"".equals(address)||address.length()>100){
				rowerror.add(i+1);
				cellerror.add(order[3]);
			}
			
			if(!"temp".equals(typeExcel)){//模板类型
				to.setSerialno(task.getSerialno());
			}
			to.setHandler(userid);
			to.setIsverify(0);//默认为0未审核
			to.setValidstate(0);//默认为0有效状态
			tos.add(to);
		}
		
		if(rowerror.size()<=0){
			//没有错误就分类写入数据库
			writeIn(tos ,userid,typeExcel);
		}else{
			//出现错误就生成标记后的excel导出
			InputStream inp2 = null;
			try {
				inp2 = file.getInputStream();
				createErrorExcel(inp2,filePath,rowerror,cellerror);
				msg = name;
			} catch (Exception e) {
				throw new Exception();
			}finally{
				if(inp2!=null){
					inp2.close();
				}
			}
		}
		return msg;
	}
	
	/**
	 * 获取模板值
	 * @throws Exception 
	 */
	public Map<String ,Object> getInformation(InputStream inp) throws Exception{
		Map<String ,Object> map = new HashMap<String ,Object>();
		//根据input流生成excel
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		//获得excel的第一条
		XSSFSheet sheet = wb.getSheetAt(0);
		Row rowTitle = sheet.getRow(0);
		//更具row(0)cell(0)内容判断是模板导入，还是下载的报表重新导入
		String tempType = getCellValue(rowTitle.getCell(0));
		
		List<TaskOrder> tos = new ArrayList<TaskOrder>();
		TaskOrder to = null;
		
		
		Integer[] order = null;
		if("区域".equals(tempType)){//模板类型
			order = tempInteger;
			map.put("type","temp");
		}else if("工单号".equals(tempType)){//下载报表类型
			order = reportInteger;
			map.put("type","report");
		}
		/*boolean areaeExist = false;
		boolean netExist = false;
		boolean flagExist = false;
		boolean addressExist = false;*/
		//int count = 0;
		int count = sheet.getLastRowNum();
		int rowCount = sheet.getLastRowNum();
		for(int j=1;j<rowCount+1;j++){
			/*if(areaeExist&&netExist&&flagExist&&addressExist){
				break;
			}else{
				areaeExist = false;
				netExist = false;
				flagExist = false;
				addressExist = false;
			}
			count++;*/
			to = new TaskOrder();
			Row row = null;
			if(sheet.getRow(j)==null){
				row = sheet.createRow(j);
			}else{
				row = sheet.getRow(j);
			}
			for(int i=0;i<order.length;i++){
				Cell cell = row.getCell(order[i]);
				switch (i) {
					case 0:
						/*if(cell==null){
							to.setAreaname("");
							areaeExist = true;
						}else if("".equals(getCellValue(cell).trim())){
							to.setAreaname("");
							areaeExist = true;
						}else{
							to.setAreaname(getCellValue(cell));
						}*/
						to.setAreaname(cell==null?"":getCellValue(cell));
						break;
					case 1:
						/*if(cell==null){
							to.setNetworktype("");
							netExist = true;
						}else if("".equals(getCellValue(cell).trim())){
							to.setNetworktype("");
							netExist = true;
						}else{
							to.setNetworktype(getCellValue(cell));
						}*/
						to.setNetworktype(cell==null?"":getCellValue(cell));
						break;
					case 2:
						/*if(cell==null){
							to.setBreakflagName("");
							flagExist = true;
						}else if("".equals(getCellValue(cell).trim())){
							to.setBreakflagName("");
							flagExist = true;
						}else{
							to.setBreakflagName(getCellValue(cell));
						}*/
						to.setBreakflagName(cell==null?"":getCellValue(cell));
						break;
					case 3:
						/*if(cell==null){
							to.setTest_address("");
							addressExist = true;
						}else if("".equals(getCellValue(cell).trim())){
							to.setTest_address("");
							addressExist = true;
						}else{
							to.setTest_address(getCellValue(cell));
						}*/
						to.setTest_address(cell==null?"":getCellValue(cell));
						break;
					case 4:
						to.setSerialno(cell==null?"":getCellValue(cell));
						break;
					default:
						break;
				}
			}
			tos.add(to);
		}
		map.put("count", count);
		map.put("tos", tos);
		return map;
	}
	
	/**
	 * 获取cell中的值
	 * @param cell
	 * @return
	 */
	public String getCellValue(Cell cell){
		String value ="";
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
		return value;
	}
	
	/**
	 * 生成错误标记的excel
	 * @param inp
	 * @param filePath
	 * @param rowerror
	 * @param cellerror
	 * @throws Exception 
	 */
	public void createErrorExcel(InputStream inp,String filePath,List<Integer> rowerror,List<Integer> cellerror) throws Exception{
		//获取EXCEL
		XSSFWorkbook wb = new XSSFWorkbook(inp);
		//获得excel的第一条
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFCellStyle style = wb.createCellStyle();
		//背景红色
		style.setFillForegroundColor(new XSSFColor(new Color(255,0,0)));// 设置前端颜色
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置填充模式
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		
		for(int i=0;i<rowerror.size();i++){
			Row row = null;
			if(sheet.getRow(rowerror.get(i))!=null){
				row = sheet.getRow(rowerror.get(i));
			}else{
				row = sheet.createRow(rowerror.get(i));
			}
			
			Cell cell =null;
			if(row.getCell(cellerror.get(i))!=null){
				cell = row.getCell(cellerror.get(i));
			}else{
				cell = row.createCell(cellerror.get(i));
			}
			cell.setCellStyle(style);
		}
		
		//生成文件
		FileOutputStream out = new FileOutputStream(filePath);
		try {
			wb.write(out);
		} catch (Exception e) {
			logger.error("Create Excel temp",e);
		}finally{
			out.close();
		}
	}
	
	/**
	 * Excel导入写入数据库
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void writeIn(List<TaskOrder> tos ,Integer userid ,String typeExcel) throws Exception{
		if("temp".equals(typeExcel)){//模板类型
			//工单集合防止工单重复
			List<String> serialnos = new ArrayList<String>();
			for(TaskOrder to:tos){
				//获取工单
				String sb = taskConfigService.nosame(serialnos);
				serialnos.add(sb);
				to.setId(sb.toString());
				to.setSerialno(sb.toString());
			}
			this.baseDao.batchInsert(TaskConfigDao.class, tos, 200);
		}else if("report".equals(typeExcel)){//下载报表类型
			//工单集合防止工单重复
			List<String> snos = new ArrayList<String>();
			Map map = new HashMap();
			map.put("tos", tos);
			map.put("userid", userid);
			snos = taskConfigDao.getSameSerialno(map);
			
			List<TaskOrder> inserts = new ArrayList<TaskOrder>();
			List<TaskOrder> updates = new ArrayList<TaskOrder>();
			boolean flag = false;//判断是否加入修改
			List<String> nosames = new ArrayList<String>();
			for(TaskOrder task:tos){
				flag = false;
				
				for(String sno:snos){
					if(sno.equals(task.getSerialno())){//判断为修改的工单
						task.setId(task.getSerialno());
						updates.add(task);
						flag=true;
						break;
					}
				}
				
				if(!flag){
					//获取工单
					String sb = taskConfigService.nosame(nosames);
					nosames.add(sb);
					task.setId(sb.toString());
					task.setSerialno(sb.toString());
					inserts.add(task);
				}
				
			}
			if(inserts.size()>0){//批量插入
				this.baseDao.batchInsert(TaskConfigDao.class, inserts, 200);
			}
			if(updates.size()>0){//修改
				for(TaskOrder update:updates){
					taskConfigDao.updateTaskSerialno(update);
				}
			}
		}
	}
}
