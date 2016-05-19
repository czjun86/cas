package com.complaint.service;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.SolvingProbabilityDao;
import com.complaint.model.SolvingProbability;
import com.complaint.utils.PatternUtil;

/**
 * 问题解决率
 * @author peng
 *
 */
@Service("solvingProbabilityService")
public class SolvingProbabilityService {
	@Autowired
	private SolvingProbabilityDao solvingProbabilityDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SolvingProbabilityService.class);
	
	public List<SolvingProbability> getSolvingProbability(Map<String, Object> map){
		this.solvingProbabilityDao.getSolvingProbability(map);
		List<SolvingProbability> st=(List<SolvingProbability>) map.get("depts");
		return st;
	}
	
	public void CreateExcel(String path ,Map<String, Object> map){
		SXSSFWorkbook swb = new SXSSFWorkbook(100);
		List<SolvingProbability> list = this.getSolvingProbability(map);
		createSheetF(swb,list);//创建第一页
		
		createSheetS(swb,list);//创建第二页
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			swb.write(out);
		} catch (Exception e) {
			logger.error(" ",e);
		}finally{
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
	 * 第一个sheet页，“区域-专业问题解决率”
	 */
	public void createSheetF(SXSSFWorkbook swb ,List<SolvingProbability> list){
		Sheet sheet = swb.createSheet("区域-专业问题解决率");
		String[] titls = new String[]{
				"区域名称",
				"归属专业",
				"投诉量",
				"问题解决量",
				"问题解决率",
				"真正解决量",
				"真正解决率",
		};
		createTitle(swb ,sheet ,titls);//创建第一页标题
		
		endowSheetF(sheet,swb,list);//填充第一页内容
	}
	
	/**
	 * 第二个sheet页，“区域问题解决”
	 */
	public void createSheetS(SXSSFWorkbook swb ,List<SolvingProbability> list){
		Sheet sheet = swb.createSheet("区域问题解决");
		String[] titls = new String[]{
				"区域名称",
				"投诉量",
				"问题解决量",
				"问题解决率",
				"真正解决量",
				"真正解决率",
		};
		createTitle(swb ,sheet ,titls);//创建第二页标题
		
		endowSheetS(sheet,swb,list);//填充第二页内容
	}
	
	/**
	 * sheet的标题
	 */
	public void createTitle(SXSSFWorkbook swb,Sheet sheet ,String[] str){
		Row row = sheet.createRow(0);
		CellStyle style = getTitleStyle(swb);
		for(int i =0;i<str.length;i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(str[i]);
			cell.setCellStyle(style);
		}
	}
	/**
	 * 标题样式
	 */
	public CellStyle getTitleStyle(SXSSFWorkbook swb){
		XSSFCellStyle style = (XSSFCellStyle) swb.createCellStyle();
		
		//加边框
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		
		style.setFillForegroundColor(new XSSFColor(new Color(59,124,196)));// 设置前端颜色
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置填充模式
		
		Font font = swb.createFont();//字体设置
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		style.setFont(font);//字体样式加入总样式中
		return style;
	}
	
	/**
	 * 第一页内容填充
	 */
	public void endowSheetF(Sheet sheet ,SXSSFWorkbook swb ,List<SolvingProbability> list){
		CellStyle style1 = percentStyle(swb,2);//保留两位小数
		CellStyle style = valueStyle(swb);//一般样式
		
		for(int i= 0;i<list.size();i++){
			Row row = sheet.createRow(i+1);
			
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(list.get(i).getAreaname() == null?"":list.get(i).getAreaname());//区域名称
			cell0.setCellStyle(style);
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(list.get(i).getSpecialName() == null?"":list.get(i).getSpecialName());//归属专业名称
			cell1.setCellStyle(style);
			
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(list.get(i).getComplainl());//投诉量
			cell2.setCellStyle(style);
			
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(list.get(i).getSolving());//问题解决量
			cell3.setCellStyle(style);
			
			Cell cell4 = row.createCell(4);
			cell4.setCellFormula("IF(ISERROR(D"+(i+2)+"/C"+(i+2)+"),0,D"+(i+2)+"/C"+(i+2)+")");//问题解决率
			cell4.setCellStyle(style1);
			
			Cell cell5 = row.createCell(5);
			cell5.setCellValue(list.get(i).getSolved());//真正解决量
			cell5.setCellStyle(style);
			
			Cell cell6 = row.createCell(6);
			cell6.setCellFormula("IF(ISERROR(F"+(i+2)+"/C"+(i+2)+"),0,F"+(i+2)+"/C"+(i+2)+")");//真正解决率
			cell6.setCellStyle(style1);
		}
		if(list.size()>0){
			Row row = sheet.createRow(list.size()+1);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue("汇总");//区域名称
			cell0.setCellStyle(style);
			
			Cell cell1 = row.createCell(1);
			cell1.setCellFormula("=SUM(C2：C"+(list.size()+1)+")");
			cell1.setCellStyle(style);
			
			Cell cell2 = row.createCell(2);
			cell2.setCellFormula("=SUM(D2：D"+(list.size()+1)+")");
			cell2.setCellStyle(style);
			
			Cell cell3 = row.createCell(3);
			cell3.setCellFormula("=C"+(list.size()+2)+"/B"+(list.size()+2)+"");
			cell3.setCellStyle(style1);
			
			Cell cell4 = row.createCell(4);
			cell4.setCellFormula("=SUM(F2：F"+(list.size()+1)+")");
			cell4.setCellStyle(style);
			
			Cell cell5 = row.createCell(5);
			cell5.setCellFormula("=(E"+(list.size()+2)+"/B"+(list.size()+2)+")");
			cell5.setCellStyle(style1);
		}
		sheet.setForceFormulaRecalculation(true);
	}
	/**
	 * 第二页内容填充
	 */
	public void endowSheetS(Sheet sheet ,SXSSFWorkbook swb ,List<SolvingProbability> list){
		CellStyle style1 = percentStyle(swb,2);//保留两位小数
		CellStyle style = valueStyle(swb);//一般样式
		List<String> listArea = new ArrayList<String>();
		boolean flag = false;
		//获取去重之后的区域名字
		for(SolvingProbability sp:list){
			String name = sp.getAreaname();
			if(listArea.size()>0){
				flag = false;
				for(String str :listArea){
					if(str.equals(name)){
						flag = true;
					}
				}
				if(!flag){
					listArea.add(name);
				}
			}else{
				listArea.add(name);
			}
		}
		//区域名字写入excel
		for(int j= 0;j<listArea.size();j++){
			Row row = sheet.createRow(j+1);
			
			Cell cell0=row.createCell(0);
			cell0.setCellValue(listArea.get(j) == null?"":listArea.get(j));//区域名称
			cell0.setCellStyle(style);
			
			Cell cell1=row.createCell(1);
			cell1.setCellFormula("SUMIF('区域-专业问题解决率'!A:A,A"+(j+2)+",'区域-专业问题解决率'!C:C)");//投诉量
			cell1.setCellStyle(style);
			
			Cell cell2=row.createCell(2);
			cell2.setCellFormula("SUMIF('区域-专业问题解决率'!A:A,A"+(j+2)+",'区域-专业问题解决率'!D:D)");//问题解决量
			cell2.setCellStyle(style);
			
			Cell cell3=row.createCell(3);
			cell3.setCellFormula("IF(ISERROR(C"+(j+2)+"/B"+(j+2)+"),0,C"+(j+2)+"/B"+(j+2)+")");//问题解决率
			cell3.setCellStyle(style1);
			
			Cell cell4=row.createCell(4);
			cell4.setCellFormula("SUMIF('区域-专业问题解决率'!A:A,A"+(j+2)+",'区域-专业问题解决率'!F:F)");//真正解决量
			cell4.setCellStyle(style);
			
			Cell cell5=row.createCell(5);
			cell5.setCellFormula("IF(ISERROR(E"+(j+2)+"/B"+(j+2)+"),0,E"+(j+2)+"/B"+(j+2)+")");//真正解决率
			cell5.setCellStyle(style1);
			
		}
		sheet.setForceFormulaRecalculation(true);
	}
	/**
	 * 百分比格式,保留小数位数
	 */
	public CellStyle percentStyle(SXSSFWorkbook swb,Integer i){
		CellStyle style = swb.createCellStyle();//百分比样式
		DataFormat dataFormat= swb.createDataFormat();//设置成百分比形式
		if(i == 0){
			style.setDataFormat(dataFormat.getFormat("0%"));
		}else if(i == 2){
			style.setDataFormat(dataFormat.getFormat("0.00%"));
		}
		//加边框
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		return style;
	}
	
	/**
	 * 一般单元格样式加边框线
	 * @return
	 */
	public CellStyle valueStyle(SXSSFWorkbook swb){
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
	 * 页面查询信息
	 */
	public List<SolvingProbability> getinfo(Map<String, Object> map){
		List<SolvingProbability> list =getSolvingProbability(map);
		for(SolvingProbability sp:list){
			if(sp.getComplainl()!=0){
				sp.setSolvingRate(PatternUtil.getSolvRate(sp.getSolving(),sp.getComplainl()));
				//sp.setSolvedRate(
						//Math.round(sp.getSolved()/sp.getComplainl()*10000)/100);
				sp.setSolvedRate(PatternUtil.getSolvRate(sp.getSolved(),sp.getComplainl()));
			}else{
				sp.setSolvingRate(0.00);//问题解决率
				sp.setSolvedRate(0.00);//真正解决率
			}
		}
		return list;
	}
	public Map<String,String> getTotal(List<SolvingProbability> list){
		Map<String,String> map = new HashMap<String ,String>();
		int solving =0;
		int soled =0;
		int totalComplainl = 0;
		for(SolvingProbability sp:list){
			totalComplainl +=sp.getComplainl(); 
			solving+=sp.getSolving();
			soled+=sp.getSolved();
		}
		double solvingRate = 0;
		double solvedRate = 0;
		if(totalComplainl>0){
			solvingRate = PatternUtil.getSolvRate(solving,totalComplainl);
			solvedRate = PatternUtil.getSolvRate(soled,totalComplainl);
		}
		map.put("solving", String.valueOf(solving));
		map.put("soled", String.valueOf(soled));
		map.put("totalComplainl", String.valueOf(totalComplainl));
		map.put("solvingRate", String.valueOf(solvingRate)+"%");
		map.put("solvedRate", String.valueOf(solvedRate)+"%");
		return map;
	}
}
