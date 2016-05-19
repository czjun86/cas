package com.complaint.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import com.complaint.model.WorkOrder;

import flex.messaging.util.URLEncoder;

public class ExcelUtil {
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
	 * 设置优良中差背景颜色
	 * 
	 * @param startRow开始行
	 * @param endRow
	 *            结束行
	 * @param col
	 *            第几列
	 */
	public static void setKpiBGColor(XSSFWorkbook workbook, XSSFSheet sheet,
			int startRow, int endRow, int col, String[] colors) {
		int count = 0;
		for (int i = startRow - 1; i < endRow; i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell cell = row.getCell(col);
			XSSFCellStyle style = setBackColorByCustom(workbook, colors[count]);
			cell.setCellStyle(style);
			if (count == 3) {
				count = -1;
			}
			count++;
		}
	}


	/**
	 * 合并后添加边框线
	 * 
	 * @param endCol
	 * @param startCol
	 * @param row
	 * @param workbook
	 */
	public static void setBorder(int endCol, int startCol, XSSFRow row,
			XSSFWorkbook workbook) {
		for (int i = startCol; i <= endCol; i++) {
			XSSFCell cell = row.getCell(i);
			if (cell == null) {
				cell = row.createCell(i);
			}
			XSSFCellStyle styleborder = workbook.createCellStyle();
			styleborder.setBorderTop(XSSFCellStyle.BORDER_THIN);
			styleborder.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			if (i == endCol) {
				styleborder.setBorderRight(XSSFCellStyle.BORDER_THIN);
			}
			cell.setCellStyle(styleborder);
		}
	}

	/**
	 * 自定以背景颜色并设置边框
	 * 
	 * @param workbook
	 * @param cell
	 * @param bgcolor
	 */
	public static XSSFCellStyle  setBackColorByCustom(Workbook workbook, String bgcolor) {
		XSSFCellStyle style = (XSSFCellStyle)workbook.createCellStyle();
		//设置边框
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		if(bgcolor != null &&  !"".equals(bgcolor)){
			int red = convertHexToNumber(bgcolor.substring(1, 3));
			int green = convertHexToNumber(bgcolor.substring(3, 5));
			int blue = convertHexToNumber(bgcolor.substring(5, 7));
			// 设置前端颜色
			style.setFillForegroundColor(new XSSFColor(new Color(red, green, blue)));
			// 设置填充模式
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		return style;
	}

	/**
	 * 自定义字体颜色
	 */
	public static XSSFFont setFontColor(XSSFFont font, String color) {
		int red = convertHexToNumber(color.substring(1, 3));
		int green = convertHexToNumber(color.substring(3, 5));
		int blue = convertHexToNumber(color.substring(5, 7));
		font.setColor(new XSSFColor(new Color(red, green, blue)));
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		return font;
	}

	/**
	 * 将颜色值转换为10进制的数据
	 * 
	 * @param hex
	 * @return
	 */
	public static int convertHexToNumber(String hex) {
		int num = 0;
		try {
			num = Integer.parseInt(hex, 16);
		} catch (Throwable t) {
			return 10;// 红色
		}
		return num;
	}

	/**
	 * 得到数组的最大值
	 * 
	 * @param arr
	 * @return
	 */

	public static Integer getMax(Integer[] arr,int num) {
		int count = 0;
		Integer max = null;
		for(int i = 0; i < arr.length - num; i ++){
			if(arr[i] != null){
				max = arr[i];
				count = i;
				break;
			}
		}
		if(max != null){
			for (int i = count + 1; i < arr.length - num; i++) {
				if(arr[i] != null){
					if (max < arr[i]) {
						max = arr[i];
					}
				}
			}
		}
		return max;
	}
	public static Double getMax(Double[] arr,int num) {
		int count = 0;
		Double max = null;
		for(int i = 0; i < arr.length - num; i ++){
			if(arr[i] != null){
				max = arr[i];
				count = i;
				break;
			}
		}
		if(max != null){
			for (int i = count + 1; i < arr.length - num; i++) {
				if(arr[i] != null){
					if (max < arr[i]) {
						max = arr[i];
					}
				}
			}
		}
		return max;
	}
	
	/**
	 * 得到数组的最小值
	 * 
	 * @param arr
	 * @return
	 */
	public static Integer getMin(Integer[] arr,int num) {
		int count = 0;
		Integer min = null;
		for(int i = 0; i < arr.length - num; i ++){
			if(arr[i] != null){
				min = arr[i];
				count = i;
				break;
			}
		}
		if(min != null){
			for (int i = count + 1; i < arr.length - num; i++) {
				if(arr[i] != null){
					if (min > arr[i]) {
						min = arr[i];
					}
				}
			}
		}
		return min;
	}
	
	public static Double getMin(Double[] arr,int num) {
		int count = 0;
		Double min = null;
		for(int i = 0; i < arr.length - num; i ++){
			if(arr[i] != null){
				min = arr[i];
				count = i;
				break;
			}
		}
		if(min != null){
			for (int i = count + 1; i < arr.length - num; i++) {
				if(arr[i] != null){
					if (min > arr[i]) {
						min = arr[i];
					}
				}
			}
		}
		return min;
	}
	/**
	 * 
	 * @param sort越大越好 1越小越好（0、1最好最差都有） 2越大越差 3、越小越差 （2、3只有最差）  4不操作
	 * @param max
	 * @param min
	 * @return 1 红色  0 绿色
	 */
	public static int getBgColor(Float val, Float max, Float min, int sort) {
		if(max != null && min !=null){
			if (sort == 0) {
				if (max.equals(min)) {
					return 1;
				} else {
					if (max.equals(val)) {
						return 0;
					}
					if (min.equals(val)) {
						return 1;
					}
				}
			} else if(sort == 1){
				if (max.equals(min)) {
					return 0;
				} else {
					if (max.equals(val)) {
						return 1;
					}
					if (min.equals(val)) {
						return 0;
					}
				}
			}else if(sort == 2){
				if(max == min){
					return -1;
				}else{
					if (max.equals(val)) {
						return 1;
					}
				}
			}else if(sort == 3){
				if(max == min){
					return -1;
				}else{
					if (min.equals(val)) {
						return 0;
					}
				}
			}
		}
		return -1;
	}
	
	public static int getBgColor(Double val, Double max, Double min, int sort) {
		if(max != null && min !=null){
			if (sort == 0) {
				if (max.equals(min)) {
					return 1;
				} else {
					if (max.equals(val)) {
						return 0;
					}
					if (min.equals(val)) {
						return 1;
					}
				}
			} else if(sort == 1){
				if (max.equals(min)) {
					return 0;
				} else {
					if (max.equals(val)) {
						return 1;
					}
					if (min.equals(val)) {
						return 0;
					}
				}
			}else if(sort == 2){
				if(max == min){
					return -1;
				}else{
					if (max.equals(val)) {
						return 1;
					}
				}
			}else if(sort == 3){
				if(max == min){
					return -1;
				}else{
					if (min.equals(val)) {
						return 0;
					}
				}
			}
		}
		return -1;
	}
	/**
	 * 得到字符串出现的位置
	 * 
	 * @param str
	 * @param num
	 *            第几个
	 * @param c
	 *            索引的字符
	 * @return
	 */
	public static int getIndex(String str, int num, char c) {
		int count = 0;
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == c) {
				index++;
				if (index == num) {
					count = i;
					break;
				}
			}
		}
		return count;
	}
	/**
	 * 统计字符出现了几次
	 * @param str 字符串
	 * @param c 索引的字符
	 * @return
	 */
	public static int countCharNum(String str,char c){
		int count = 0;
		for(int i= 0;i<str.length();i++){
			char ch = str.charAt(i);
			if(ch == c){
				count ++;
			}
		}
		return count;
	}
	/**
	 * 计算字符串长度
	 * @param str
	 * @return
	 */
	public static int getStrLength(String str) {
		int sum = 0;
		for (int i = 0; i < str.length(); i++) {
			String charStr = String.valueOf(str.charAt(i));
			if (charStr.equals(" ")
					|| Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr)
							.matches()) {
				sum += 1;
			} else if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr)
					.matches()
					|| Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
				sum += 2;
			}
		}
		return sum;
	}
	
	/**
	 * 区域查询条件用来处理全选
	 * @param area_id
	 * @return
	 */
	public static String getSearchAreas(String area_id ,List<WorkOrder> areas){
		StringBuilder sbuf = null;
		if (area_id.indexOf("-1") > -1) {//判断有没有全选
			sbuf = new StringBuilder();
			//List<WorkOrder> areas = this.workOrderService.getAllArea();
			for(WorkOrder wo:areas){
				sbuf.append(wo.getAreaId()+",");
			}
		}else{
			sbuf = new StringBuilder();
			/*if (area_id.indexOf("-2") > -1) {
				area_id = area_id.replace("-2", "863401,863407,863412,863414,863415,863416,863420,863422,863424,863428,863435,863436,863402");
			}*/
			String[] s=area_id.split(",");
			List list = new ArrayList();
			
			for(String str :s){//区域去重
				if(list.size()>0){//获取最终areaid
					boolean flag = false;
					for(int i=0;i<list.size();i++){
						if(list.get(i).equals(str)){
							flag=true;
							break;
						}
					}
					if(!flag){
						list.add(str);
					}
				}else{
					list.add(str);
				}
			}
			
			for(int i=0;i<list.size();i++){//拼接区域id
				sbuf.append(list.get(i)+",");
			}
		}
		if(sbuf!=null&&sbuf.length()>1){
			area_id = sbuf.toString();
			area_id = area_id.substring(0,area_id.length()-1);
		}
		return area_id;
	}
}
