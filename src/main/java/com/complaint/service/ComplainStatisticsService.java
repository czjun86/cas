package com.complaint.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.ComplainStatisticsDao;
import com.complaint.model.ComplainProbability;
import com.complaint.utils.PatternUtil;

/**
 * 投诉率与投诉统计
 * @author peng
 *
 */
@Service("complainStatisticsService")
public class ComplainStatisticsService {
	@Autowired
	private  ComplainStatisticsDao complainStatisticsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ComplainStatisticsService.class);
	
	public List<ComplainProbability> getComplain(Map<String, Object> map){
		this.complainStatisticsDao.getComplain(map);
		List<ComplainProbability> st=(List<ComplainProbability>) map.get("depts");
		return st;
	}
	
	public void CreateExcel(String path,Map<String, Object> map,String tempPath){
		FileInputStream file = null;
		XSSFWorkbook wb = null;
		try {
			file = new FileInputStream(tempPath);
			wb = new XSSFWorkbook(file);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		
		XSSFSheet sheet = null;
		if(wb!=null&&wb.getSheet("分析图表")!=null){
			sheet = wb.getSheet("分析图表");
		}else{
			wb = new XSSFWorkbook();
			sheet = wb.createSheet("分析图表");
		}
		int day = 0;
		if(map.get("t_type").equals("1")){
			day = getDay((String)map.get("s_time"),(String)map.get("e_time"));
		}else if(map.get("t_type").equals("2")){
			day = getDay((String)map.get("m_time"));
		}
		Row dayRow = sheet.getRow(0);
		Cell dayCell = dayRow.createCell(5);
		dayCell.setCellValue(day);
		
		SXSSFWorkbook swb = new SXSSFWorkbook(wb);
		
		List<ComplainProbability> list = getComplain(map);//查出数据
		
		dataComplain(swb,list,"GSMSPEECH");//2G语音投诉量
		
		dataComplain(swb,list,"GSMDATA");//2G数据投诉量
		
		dataComplain(swb,list,"GSM");//2G语音数据投诉量
		
		dataComplain(swb,list,"WCDMASPEECH");//3G语音投诉量
		
		dataComplain(swb,list,"WCDMADATA");//3G数据投诉量
		
		dataComplain(swb,list,"WCDMA");//3G语音数据投诉量
		
		dataComplain(swb,list,"ALL");//全网投诉量
		
		analyzeComplain(swb,list);//分析报表页面
		
		FileOutputStream out =null;
		try {
			out =  new FileOutputStream(path);
			swb.write(out);
		} catch (Exception e) {
			logger.error(" ",e);
		} finally{
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				logger.error(" ",e);
			}
		}
	}
	
	/**
	 * 分析报表页面
	 */
	public void analyzeComplain(SXSSFWorkbook swb,List<ComplainProbability> list){
		CellStyle style = analyzeStyle(swb);
		CellStyle style2 = analyzeStyle(swb);
		DataFormat dataFormat= swb.createDataFormat();//设置成百分比形式
		style2.setDataFormat(dataFormat.getFormat("0.00%"));
		
		Sheet sheet = swb.getSheet("分析图表");
		
		
		for(int i=0 ;i<list.size();i++){
			ComplainProbability comp = list.get(i);
			Row row = null;
			if(sheet.getRow(i+2)==null){
				row = sheet.createRow(i+2);
			}else{
				row = sheet.getRow(i+2);
			}
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(comp.getAreaname()==null?"":comp.getAreaname());//区域
			cell0.setCellStyle(style);
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(comp.getGroupname()==null?"":comp.getGroupname());//公司类型名字
			cell1.setCellStyle(style);
			
			//2,3为本月2G,3G用户，客户自己填
			Cell cell2 = row.createCell(2);
			cell2.setCellValue("");
			cell2.setCellStyle(style);
			Cell cell3 = row.createCell(3);
			cell3.setCellValue("");
			cell3.setCellStyle(style);
			
			Cell cell4 = row.createCell(4);
			cell4.setCellFormula("VLOOKUP(A:A,'2G语音数据投诉量'!A:B,2,0)");//2G投诉量
			cell4.setCellStyle(style);
			
			Cell cell5 = row.createCell(5);
			cell5.setCellFormula("VLOOKUP(A:A,'3G语音数据投诉量'!A:B,2,0)");//3G投诉量
			cell5.setCellStyle(style);
			
			Cell cell6 = row.createCell(6);
			cell6.setCellFormula("VLOOKUP(A:A,'2G语音投诉量'!A:B,2,0)");//2G语音投诉量
			cell6.setCellStyle(style);
			
			Cell cell7 = row.createCell(7);
			cell7.setCellFormula("VLOOKUP(A:A,'3G语音投诉量'!A:B,2,0)");//3G语音投诉量
			cell7.setCellStyle(style);
			
			Cell cell8 = row.createCell(8);
			cell8.setCellFormula("IF(ISERROR(E"+(i+3)+"/C"+(i+3)+"/$F$1),0,E"+(i+3)+"/C"+(i+3)+"/$F$1)");//2G日均万人投诉率
			cell8.setCellStyle(style2);
			
			Cell cell9 = row.createCell(9);
			cell9.setCellFormula("IF(ISERROR(F"+(i+3)+"/D"+(i+3)+"/$F$1),0,F"+(i+3)+"/D"+(i+3)+"/$F$1)");//3G日均万人投诉率
			cell9.setCellStyle(style2);
			
			Cell cell10 = row.createCell(10);
			cell10.setCellFormula("IF(ISERROR((E"+(i+3)+"+F"+(i+3)+")/(C"+(i+3)+"+D"+(i+3)+")/$F$1),0,(E"+(i+3)+"+F"+(i+3)+")/(C"+(i+3)+"+D"+(i+3)+")/$F$1)");//3G日均万人投诉率
			cell10.setCellStyle(style2);
			
			//11,12为2G语音话务量，3G语音话务量客户自己填
			Cell cell11 = row.createCell(11);
			cell11.setCellValue("");
			cell11.setCellStyle(style);
			Cell cell12 = row.createCell(12);
			cell12.setCellValue("");
			cell12.setCellStyle(style);
			
			Cell cell13 = row.createCell(13);
			cell13.setCellFormula("IF(ISERROR(E"+(i+3)+"/L"+(i+3)+"*10000),0,E"+(i+3)+"/L"+(i+3)+"*10000)");//2G投诉话务（万Erl）比
			cell13.setCellStyle(style);
			
			Cell cell14 = row.createCell(14);
			cell14.setCellFormula("IF(ISERROR(F"+(i+3)+"/M"+(i+3)+"*10000),0,F"+(i+3)+"/M"+(i+3)+"*10000)");//3G投诉话务（万Erl）比
			cell14.setCellStyle(style);
			
			Cell cell15 = row.createCell(15);
			cell15.setCellFormula("IF(ISERROR((E"+(i+3)+"+F"+(i+3)+")/(L"+(i+3)+"+M"+(i+3)+")*10000),0,(E"+(i+3)+"+F"+(i+3)+")/(L"+(i+3)+"+M"+(i+3)+")*10000)");//3G日均万人投诉率
			cell15.setCellStyle(style);
			
			Cell cell16 = row.createCell(16);
			cell16.setCellValue(comp.getGsm_inside_uncovered());//2G室内无覆盖投诉量
			cell16.setCellStyle(style);
			
			Cell cell17 = row.createCell(17);
			cell17.setCellValue(comp.getGsm_outside_uncovered());//2G室外无覆盖投诉量
			cell17.setCellStyle(style);
			
			Cell cell18 = row.createCell(18);
			cell18.setCellValue(comp.getWcdma_inside_uncovered());//3G室内无覆盖投诉量
			cell18.setCellStyle(style);
			
			Cell cell19 = row.createCell(19);
			cell19.setCellValue(comp.getWcdma_outside_uncovered());//3G室外无覆盖投诉量
			cell19.setCellStyle(style);
			
			Cell cell20 = row.createCell(20);
			cell20.setCellFormula("IF(ISERROR(Q"+(i+3)+"/(Q"+(i+3)+"+R"+(i+3)+")),0,Q"+(i+3)+"/(Q"+(i+3)+"+R"+(i+3)+"))");//2G室内无覆盖占比
			cell20.setCellStyle(style2);
			
			Cell cell21 = row.createCell(21);
			cell21.setCellFormula("IF(ISERROR(R"+(i+3)+"/(Q"+(i+3)+"+R"+(i+3)+")),0,R"+(i+3)+"/(Q"+(i+3)+"+R"+(i+3)+"))");//2G室外无覆盖占比
			cell21.setCellStyle(style2);
			
			Cell cell22 = row.createCell(22);
			cell22.setCellFormula("IF(ISERROR(S"+(i+3)+"/(S"+(i+3)+"+T"+(i+3)+")),0,S"+(i+3)+"/(S"+(i+3)+"+T"+(i+3)+"))");//3G室内无覆盖占比
			cell22.setCellStyle(style2);
			
			Cell cell23 = row.createCell(23);
			cell23.setCellFormula("IF(ISERROR(T"+(i+3)+"/(S"+(i+3)+"+T"+(i+3)+")),0,T"+(i+3)+"/(S"+(i+3)+"+T"+(i+3)+"))");//3G室内无覆盖占比
			cell23.setCellStyle(style2);
		}

		sheet.setForceFormulaRecalculation(true);
	}
	
	/**
	 * 2G语音数据投诉量,3G语音数据投诉量,全网投诉量，2G语音投诉量,2G数据投诉量,3G语音投诉量,3G数据投诉量   生成对应sheet
	 */
	public void dataComplain(SXSSFWorkbook swb,List<ComplainProbability> list,String type){
		CellStyle left = getLeftStyle(swb);
		CellStyle right = getRightStyle(swb);
		Sheet sheet = null;
		if(type.equals("WCDMA")){
			sheet = swb.createSheet("3G语音数据投诉量");
		}else if(type.equals("GSM")){
			sheet = swb.createSheet("2G语音数据投诉量");
		}else if(type.equals("ALL")){
			sheet = swb.createSheet("全网投诉量");
		}else if(type.equals("GSMSPEECH")){
			sheet = swb.createSheet("2G语音投诉量");
		}else if(type.equals("GSMDATA")){
			sheet = swb.createSheet("2G数据投诉量");
		}else if(type.equals("WCDMASPEECH")){
			sheet = swb.createSheet("3G语音投诉量");
		}else if(type.equals("WCDMADATA")){
			sheet = swb.createSheet("3G数据投诉量");
		}
		if(sheet!=null){
			sheet.setForceFormulaRecalculation(true);
		}
		for(int i = 0;i<list.size();i++){
			Row row = sheet.createRow(i);
			
			Cell cell0 = row.createCell(0);//第一列区域名字
			cell0.setCellValue(list.get(i).getAreaname()==null?"":list.get(i).getAreaname());
			cell0.setCellStyle(left);
			
			Cell cell1 = row.createCell(1);//第二列数据
			if(type.equals("WCDMA")){
				cell1.setCellFormula("VLOOKUP(A:A,'3G语音投诉量'!A:B,2,0)+VLOOKUP(A:A,'3G数据投诉量'!A:B,2,0)");
			}else if(type.equals("GSM")){
				cell1.setCellFormula("VLOOKUP(A:A,'2G语音投诉量'!A:B,2,0)+VLOOKUP(A:A,'2G数据投诉量'!A:B,2,0)");
			}else if(type.equals("ALL")){
				cell1.setCellFormula("VLOOKUP(A:A,'2G语音数据投诉量'!A:B,2,0)+VLOOKUP(A:A,'3G语音数据投诉量'!A:B,2,0)");
			}else if(type.equals("GSMSPEECH")){
				cell1.setCellValue(list.get(i).getGsm_speech_complaint());
			}else if(type.equals("GSMDATA")){
				cell1.setCellValue(list.get(i).getGsm_data_complaint());
			}else if(type.equals("WCDMASPEECH")){
				cell1.setCellValue(list.get(i).getWcdma_speech_complaint());
			}else if(type.equals("WCDMADATA")){
				cell1.setCellValue(list.get(i).getWcdma_data_complaint());
			}
			cell1.setCellStyle(right);
		}
	}
	
	/**
	 * 普通单元格加边框左对齐样式
	 */
	public CellStyle getLeftStyle(SXSSFWorkbook swb){
		CellStyle style = swb.createCellStyle();
		//加边框
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 水平居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		return style;
	}
	/**
	 * 普通单元格加边框右对齐样式
	 */
	public CellStyle getRightStyle(SXSSFWorkbook swb){
		CellStyle style = swb.createCellStyle();
		//加边框
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);// 水平居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		return style;
	}
	
	/**
	 * 分析sheet样式
	 */
	public CellStyle analyzeStyle(SXSSFWorkbook swb){
		CellStyle style = swb.createCellStyle();
		//加边框
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		
		return style;
	}
	
	/**
	 * 获取时间段天数
	 */
	public int getDay(String s ,String e){
		int day =0;
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = sd.parse(s);
			Date d2 = sd.parse(e);
			long quot =d2.getTime()-d1.getTime();
			day = (int) (quot / 1000 / 60 / 60 / 24);
		} catch (Exception e1) {
			logger.error("change date time",e1);
		}
		return day;
	}
	
	public int getDay(String data){
		String[] str1 =data.split("-");
		
		long time=System.currentTimeMillis();
		Date dt = new Date(time);  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String now=sdf.format(dt); 
		String[] str2 = now.split("-");
		
		int cc =0;
		if(Integer.parseInt(str1[0])==Integer.parseInt(str2[0]) && 
				Integer.parseInt(str1[1])==Integer.parseInt(str2[1])){
			cc = Integer.parseInt(str2[2]);
		}else if(Integer.parseInt(str1[0])>Integer.parseInt(str2[0])){
			cc = 0;
		}else if(Integer.parseInt(str1[0])==Integer.parseInt(str2[0]) &&
				Integer.parseInt(str1[1])>Integer.parseInt(str2[1])){
			cc =0;
		}else{
			Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			Date d = new Date();
			try {
				d = sdf2.parse(data);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			aCalendar.setTime(d);
			cc=aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		
		return cc;
	}
	
	public List<ComplainProbability> getinfo(Map<String, Object> map){
		List<ComplainProbability> list = getComplain(map);//查出数据
		for(ComplainProbability cp:list){
			//总投诉量计算
			cp.setGsm_complaint(cp.getGsm_speech_complaint()+cp.getGsm_data_complaint());
			cp.setWcdma_complaint(cp.getWcdma_speech_complaint()+cp.getWcdma_data_complaint());
			
			//计算无覆盖率
			if(cp.getGsm_outside_uncovered()+cp.getGsm_inside_uncovered()!=0){
			cp.setGsm_inside_ratio(
					PatternUtil.getSolvRate(cp.getGsm_inside_uncovered()
							,(cp.getGsm_outside_uncovered()+cp.getGsm_inside_uncovered())
							)
						);
			cp.setGsm_outside_ratio(
					PatternUtil.getSolvRate(cp.getGsm_outside_uncovered()
							,(cp.getGsm_outside_uncovered()+cp.getGsm_inside_uncovered())
							)
						);
			}else{
				cp.setGsm_inside_ratio(0.00);
				cp.setGsm_outside_ratio(0.00);
			}
			if(cp.getWcdma_outside_uncovered()+cp.getWcdma_inside_uncovered()!=0){
				cp.setWcdma_inside_ratio(
						PatternUtil.getSolvRate(cp.getWcdma_inside_uncovered()
								,(cp.getWcdma_outside_uncovered()+cp.getWcdma_inside_uncovered())
								)
							);
				cp.setWcdma_outside_ratio(
						PatternUtil.getSolvRate(cp.getWcdma_outside_uncovered()
								,(cp.getWcdma_outside_uncovered()+cp.getWcdma_inside_uncovered())
								)
							);
			}else{
				cp.setWcdma_inside_ratio(0.00);
				cp.setWcdma_outside_ratio(0.00);
			}
			
		}
		return list;
	}
}
