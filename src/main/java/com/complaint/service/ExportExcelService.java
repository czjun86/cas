package com.complaint.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.complaint.dao.ExportExcelDao;
import com.complaint.dao.SysConfigDao;
import com.complaint.model.ComplainEvaluate;
import com.complaint.model.ComplainStatistics;
import com.complaint.model.Group;
import com.complaint.model.MostTestRate;
import com.complaint.model.QualTopAndLast;
import com.complaint.model.QualtyReport;
import com.complaint.model.RateColor;
import com.complaint.model.WorkOrder;
import com.complaint.utils.Constant;
import com.complaint.utils.DateUtils;
import com.complaint.utils.ExcelUtil;
import com.complaint.utils.ReflectUtil;

@Service("exportExcelService")
public class ExportExcelService {

	@Autowired
	private ExportExcelDao exportExcelDao;
	@Autowired
	private StatAndEvalReportService statAndEvalReportService;
	@Autowired
	private SysConfigDao sysConfigDao;
	public void writeInExcel(WebApplicationContext ctx, int type,
			String starttime, String endtime, String areaids, String filePath,
			String name) throws Exception {
		String tempPath = filePath + Constant.CAS_TEMPLATE_PATH;
		filePath = filePath + Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH;
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
		mapParam.put("type", type);
		mapParam.put("starttime", starttime);
		mapParam.put("endtime", endtime);
		mapParam.put("queryAreaIds", areaids);
		Map messMap = statAndEvalReportService.getAllStatAndEval(ctx, mapParam);
		ComplainStatistics statistics = (ComplainStatistics) messMap
				.get("stat");
		ComplainEvaluate evaluate = (ComplainEvaluate) messMap.get("eval");
		// 评分类
		MostTestRate mostTestRate = (MostTestRate) messMap.get("mostTestRate");
		// 取值
		int size = statistics.getArea_id().length;
		// 模板名称
		String fileName = "";
		// 标题
		String title = "";
		// 标题下一行,|主要为了能找到区域值得起止位置
		String content = "月实测量最多的区域是|" + mostTestRate.getCurr_max_test() + "，"
				+ "最少的区域是|" + mostTestRate.getCurr_min_test() + "；"
				+ "累计实测率最高的区域是|" + mostTestRate.getTotal_max_test() + "，"
				+ "最低的区域是|" + mostTestRate.getTotal_min_test() + "；"
				+ "月3G综合评价最好的区域是|" + mostTestRate.getComp_eval_3g_max() + "，"
				+ "最差的区域是|" + mostTestRate.getComp_eval_3g_min() + "；"
				+ "月2G综合评价最好的区域是|" + mostTestRate.getComp_eval_2g_max() + "，"
				+ "最差的区域是|" + mostTestRate.getComp_eval_2g_min() + "。";

		String[] strarArr = null;
		String titleVal = "";
		if (starttime != null &&  !"".equals(starttime) && endtime != null
				&&  !"".equals(endtime)) {
			strarArr = starttime.split("-");
		}
		// 设置标题内容
		if (type == 1) {
			if (strarArr != null && strarArr.length > 0) {
				titleVal = DateUtils.dateFormat(starttime);
			}
			fileName = "template_day.xlsx";
			title = "移动网络投诉测试日报 - (" + titleVal + ")";
			content = content.replaceAll("月", "日");
		} else if (type == 2) {
			if (strarArr != null && strarArr.length > 0) {
				titleVal = DateUtils.dateFormat(starttime) + "-"
						+ DateUtils.dateFormat(endtime);
			}
			fileName = "template_week.xlsx";
			title = "移动网络投诉测试周报-(" + titleVal + ")";
			content = content.replaceAll("月", "周").replace("。", "；")
					+ "周3G综合改善最大的区域是|" + mostTestRate.getComp_impr_3g_max()
					+ "，" + "最小的区域是|" + mostTestRate.getComp_impr_3g_min()
					+ "；" + "周2G综合改善最大的区域是|"
					+ mostTestRate.getComp_impr_2g_max() + "，" + "最小的区域是|"
					+ mostTestRate.getComp_impr_2g_min() + "；"
					+ "周测试及时率最好的区域是|" + mostTestRate.getTimely_rate_max() + "，"
					+ "最差的区域是|" + mostTestRate.getTimely_rate_min() + "。";
		} else {
			if (strarArr != null && strarArr.length > 0) {
				titleVal = strarArr[0] + "年" + strarArr[1] + "月";
			}
			fileName = "template_month.xlsx";
			title = "移动网络投诉测试月报-(" + titleVal + ")";
			content = content.replace("。", "；") + "月3G综合改善最大的区域是|"
					+ mostTestRate.getComp_impr_3g_max() + "，" + "最小的区域是|"
					+ mostTestRate.getComp_impr_3g_min() + "；"
					+ "月2G综合改善最大的区域是|" + mostTestRate.getComp_impr_2g_max()
					+ "，" + "最小的区域是|" + mostTestRate.getComp_impr_2g_min()
					+ "；" + "月测试及时率最好的区域是|" + mostTestRate.getTimely_rate_max()
					+ "，" + "最差的区域是|" + mostTestRate.getTimely_rate_min() + "。";
		}
		// 获取累计起始时间
		String defDate = sysConfigDao.queryData(Constant.REPORTDATE);
		// 模板路径
		String temp = tempPath + fileName;
		copyFile(temp, filePath, name);
		// 读取Excel
		FileInputStream file = new FileInputStream(filePath + name);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		// 第一个sheet
		XSSFSheet sheet = workbook.getSheetAt(0);
		// 设置标题 以及标题
		setTitle(workbook, title, size + 3, defDate);
		// 设置top
		setTopVal(workbook,content,4,size + 3,0);
		// 获取颜色装入数组
		List<RateColor> colorsList = exportExcelDao.queryColors();
		String[] colors = new String[colorsList.size()];
		for (int i = 0; i < colorsList.size(); i++) {
			RateColor rc = colorsList.get(i);
			colors[rc.getRank_code() - 1] = rc.getRank_color();
		}
		// 设置指标优良中差的背景颜色
		if (type == 1) {
			ExcelUtil.setKpiBGColor(workbook, sheet, 15, 54, 2, colors);
		} else {
			ExcelUtil.setKpiBGColor(workbook, sheet, 17, 56, 2, colors);
		}
		String green = "#00FF00", red = "#FF0000";
		// 边框样式
		XSSFCellStyle style = ExcelUtil.setBackColorByCustom(workbook, "");
		XSSFCellStyle greenStyle = ExcelUtil.setBackColorByCustom(workbook,
				green);
		XSSFCellStyle redStyle = ExcelUtil.setBackColorByCustom(workbook, red);
		// 依次填充值
		int count = 4;
		// 区域名称
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getArea_name(), 4,0);
		// 累积工单量
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getTotal_workorder(), 4,0);
		// 当前工单量
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getCurr_workorder(), 4,0);
		// 累积实测量
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getTotal_test(), 0,0);
		// 当前实测量
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getCurr_test(), 0,0);
		// 当前实测排名
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getCurr_test_rank(), 1,0);
		// 累积实测率
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getTotal_test_rate(), 0,0);
		// 累积实测排名
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getTotal_test_rate_rank(), 1,0);
		if (type != 1) {
			// 及时率
			setValues(sheet, style, greenStyle, redStyle, count++,
					evaluate.getTimely_rate(), 0,0);
			// 及时率排名
			setValues(sheet, style, greenStyle, redStyle, count++,
					evaluate.getTimely_rate_rank(), 1,0);
			// ExcelUtil.setValues(sheet, style, greenStyle, redStyle, count++,
			// evaluate.getTimely_rate_rank(),1,0);
		}
		// 当前室内测试量
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getCurr_in_test(), 4,0);
		// 当前室外测试量
		setValues(sheet, style, greenStyle, redStyle, count++,
				statistics.getCurr_out_test(), 4,0);
		// rscp 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRscp_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRscp_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRscp_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRscp_d(), 4,0);
		// ecno 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getEc_no_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getEc_no_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getEc_no_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getEc_no_d(), 4,0);
		// Txpower 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getTxpower_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getTxpower_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getTxpower_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getTxpower_d(), 4,0);
		// FTP上行 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getFtp_up_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getFtp_up_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getFtp_up_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getFtp_up_d(), 4,0);
		// FTP下行 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getFtp_down_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getFtp_down_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getFtp_down_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getFtp_down_d(), 4,0);
		// Rxlev 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRxlev_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRxlev_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRxlev_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRxlev_d(), 4,0);
		// Rxqual 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRxqual_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRxqual_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRxqual_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getRxqual_d(), 4,0);
		// Ci 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getCi_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getCi_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getCi_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getCi_d(), 4,0);
		// 3g综合评价 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getComp_eval_3g_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getComp_eval_3g_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getComp_eval_3g_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getComp_eval_3g_d(), 4,0);
		// 2g综合评价 优良中差
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getComp_eval_2g_a(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getComp_eval_2g_b(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count++,
				evaluate.getComp_eval_2g_c(), 4,0);
		setValues(sheet, style, greenStyle, redStyle, count,
				evaluate.getComp_eval_2g_d(), 4,0);
		if (type != 1) {
			count++;
			// 3G综合改善得分
			setValues(sheet, style, greenStyle, redStyle, count++,
					evaluate.getComp_impr_value_3g(), 4,0);
			// 2G综合改善得分
			setValues(sheet, style, greenStyle, redStyle, count++,
					evaluate.getComp_impr_value_2g(), 4,0);
			// 3G改善比例
			setValues(sheet, style, greenStyle, redStyle, count++,
					evaluate.getComp_impr_ratio_3g(), 4,0);
			// 2G改善比例
			setValues(sheet, style, greenStyle, redStyle, count++,
					evaluate.getComp_impr_ratio_2g(), 4,0);
		}
		// 写入excel
		FileOutputStream os = new FileOutputStream(filePath + name);
		workbook.write(os);
		os.flush();
		os.close();

	}

	/**
	 * 
	 */
	private void setTitle(XSSFWorkbook workbook, String title,
			int lastcol, String defDate) {
		XSSFSheet sheet = workbook.getSheetAt(0);
		int firstrow = 1, lastrow = 1, firstcol = 0;
		if (lastcol < 7) {
			lastcol = 7;
		}
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(firstrow, lastrow, firstcol,
				lastcol - 1));

		// 设置标题
		XSSFRow row = sheet.createRow(1);
		XSSFCell cell = row.createCell(0);
		XSSFCellStyle style = ExcelUtil.setBackColorByCustom(workbook,
				"#009400");
		XSSFFont[] fonts = new XSSFFont[2];
		CreationHelper helper = workbook.getCreationHelper();
		RichTextString rts = helper.createRichTextString(title);
		fonts[0] = workbook.createFont();
		fonts[1] = workbook.createFont();
		// 设置字体大小
		fonts[0].setFontHeightInPoints((short) 16);
		// 设置字体
		fonts[0].setFontName("宋体");
		// 加粗
		fonts[0].setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		fonts[1].setFontHeightInPoints((short) 14);
		fonts[1].setFontName("宋体");
		fonts[1].setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		rts.applyFont(0, title.indexOf("(") - 1, fonts[0]);
		rts.applyFont(title.indexOf("(") - 1, title.length(), fonts[1]);
		// 水平居中
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);
		// 垂直居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 设置高
		row.setHeightInPoints(24);// 设置行高24像素
		// style.setFont(font[0]);
		// 设置背景颜色
		// ExcelUtil.setBackColorByCustom(style, "#009400");
		// 设置边框线
		ExcelUtil.setBorder(lastcol - 1, 0, row, workbook);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cell.setCellValue(rts);
		cell.setCellStyle(style);
		// 设置累计起始时间
		sheet.addMergedRegion(new CellRangeAddress(firstrow + 1, lastrow + 1,
				firstcol, lastcol - 1));
		XSSFRow row_defDate = sheet.createRow(2);
		XSSFCell cell_defDate = row_defDate.createCell(0);
		XSSFCellStyle style_defDate = ExcelUtil.setBackColorByCustom(workbook,
				"#009400");
		;
		XSSFFont font = workbook.createFont();
		// ExcelUtil.setBackColorByCustom(style_defDate, "#009400");
		String defDateVal = "累计起始时间-" + defDate;
		// 设置边框线
		ExcelUtil.setBorder(lastcol - 1, 0, row_defDate, workbook);
		style_defDate.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style_defDate.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style_defDate.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style_defDate.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);
		style_defDate.setFont(font);
		row_defDate.setHeightInPoints(24);
		cell_defDate.setCellStyle(style_defDate);
		cell_defDate.setCellValue(defDateVal);
		

	}
	/**
	 * 设置最好最差
	 * @param workbook
	 * @param content
	 * @param lastcol
	 */
	private void setTopVal(XSSFWorkbook workbook, String content,
			int firstrow,int lastcol,int sheetIndex){
		XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		if(sheet == null){
			sheet = workbook.createSheet("top5");
		}
		if (lastcol < 7) {
			lastcol = 7;
		}
		CreationHelper helper = workbook.getCreationHelper();
		// 设置标题下一行
		sheet.addMergedRegion(new CellRangeAddress(firstrow - 1, firstrow -1,
				0, lastcol - 1));
		String text = content.replace("|", "");
		String green = "#00FF00", red = "#FF0000";
		RichTextString rts1 = helper.createRichTextString(text);

		XSSFFont[] fonts1 = new XSSFFont[2];
		fonts1[0] = workbook.createFont();
		fonts1[1] = workbook.createFont();
		fonts1[0] = ExcelUtil.setFontColor(fonts1[0], green);
		fonts1[1] = ExcelUtil.setFontColor(fonts1[1], red);
		int len = ExcelUtil.countCharNum(content, '|');
		for (int i = 0; i < len; i++) {
			int start = ExcelUtil.getIndex(content, i + 1, '|') - i;
			int end = 0;
			if (i == (len - 1)) {
				end = text.length() - 1;
			} else {
				if (i % 2 == 0) {
					end = ExcelUtil.getIndex(text, (i + 1) / 2 + 1, '，');
				} else {
					end = ExcelUtil.getIndex(text, i / 2 + 1, '；');
				}
			}
			rts1.applyFont(start, end, fonts1[i % 2]);
		}

		XSSFRow row1 = sheet.createRow(firstrow-1);
		XSSFCell cell1 = row1.createCell(0);
		cell1.setCellValue(rts1);
		XSSFCellStyle style1 = workbook.createCellStyle();
		// 自动换行
		style1.setWrapText(true);
		// 水平居左
		style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 垂直居中
		// style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		float width = 0;
		for (int i = 0; i < lastcol; i++) {
			width += sheet.getColumnWidth(i) / 256 - 0.625;
		}
		int strLength = ExcelUtil.getStrLength(text);
		if (width != 0) {
			row1.setHeightInPoints((float) Math.ceil((float) strLength / width) * 15 + 2);
		}
		style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		ExcelUtil.setBorder(lastcol - 1, 0, row1, workbook);
		style1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		cell1.setCellStyle(style1);
	}
	/**
	 * 填充值
	 * 
	 * @param sheet
	 * @param rownum
	 *            行
	 * @param strVal
	 *            值
	 * @param num 
	 * 			 分公司及全网不列入颜色标识中
	 * @param sort
	 *           0越大越好 1越小越好（0、1最好最差都有） 2越大越差 3、越小越差 （2、3只有最差）  4不操作
	 */
	private void setValues(XSSFSheet sheet, XSSFCellStyle style,
			XSSFCellStyle greenStyle, XSSFCellStyle redStyle, int rownum,
			String[] strVal, int sort,int num) {
		Float max = null, min = null;
		if (sort != 4) {
			String str = strVal[0].replace("%", "");
			max = Float.parseFloat(str);
			min = Float.parseFloat(str);
			// 获取最大最小值
			if (strVal.length - num > 1) {
				for (int i = 3; i < strVal.length + 3 - num; i++) {
					String temp = strVal[i - 3].replace("%", "");
					if (max < Float.parseFloat(temp)) {
						max = Float.parseFloat(temp);
					}
					if (min > Float.parseFloat(temp)) {
						min = Float.parseFloat(temp);
					}
				}
			} else {
				max = null;
				min = null;
			}
		}
		// 得到行
		XSSFRow row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		int len = strVal.length;
		// 给单元格赋值并给相应的单元格填充颜色
		for (int i = 3; i < len + 3; i++) {
			XSSFCell cell = row.getCell(i);
			if (null == cell) {
				cell = row.createCell(i);
			}
			XSSFCellStyle setStyle = style;
			cell.setCellValue((strVal[i - 3]==null || strVal[i - 3].equals(""))?"-":strVal[i - 3]);
			if(i < len + 3 - num && max != null && min != null){
				if (sort != 4 && (strVal[i - 3]!=null || !strVal[i - 3].equals(""))) {
					int t = ExcelUtil.getBgColor(Float.parseFloat(strVal[i - 3].replace("%", "")), max, min, sort);
					if(t == 0){
						setStyle = greenStyle;
					}else if(t == 1){
						setStyle = redStyle;
					}
				}
			}
			cell.setCellStyle(setStyle);
		}
	}

	/**
	 * 
	 * @param workbook
	 * @param sheet
	 * @param rownum
	 *            行数
	 * @param intVal
	 * @param sort 越大越好 1越小越好（0、1最好最差都有） 2越大越差 3、越小越差 （2、3只有最差）  4不操作
	 */
	private void setValues(XSSFSheet sheet, XSSFCellStyle style,
			XSSFCellStyle greenStyle, XSSFCellStyle redStyle, int rownum,
			Integer[] intVal, int sort,int num) {
		Integer max = null, min = null;
		// 填充背景颜色
		if (intVal.length - num > 1) {
			max = ExcelUtil.getMax(intVal,num);
			min = ExcelUtil.getMin(intVal,num);
		}
		XSSFRow row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		int len = intVal.length;
		for (int i = 3; i < len + 3; i++) {
			XSSFCell cell = row.getCell(i);
			if (null == cell) {
				cell = row.createCell(i);
			}
			if(intVal[i - 3]==null){
				cell.setCellValue("-");
			}else{
				cell.setCellValue(intVal[i - 3]);
			}
			XSSFCellStyle setStyle = style;
			if(i < len + 3 - num && max != null && min != null){
				if (sort != 4 && intVal[i - 3]!=null) {
					int t = ExcelUtil.getBgColor(Float.valueOf(intVal[i - 3]), Float.valueOf(max), Float.valueOf(min), sort);
					if(t == 0){
						setStyle = greenStyle;
					}else if(t == 1){
						setStyle = redStyle;
					}
				}
			}
			cell.setCellStyle(setStyle);
		}
	}
	
	/**
	 * 
	 * @param workbook
	 * @param sheet
	 * @param rownum
	 *            行数
	 * @param intVal
	 * @param sort 越大越好 1越小越好（0、1最好最差都有） 2越大越差 3、越小越差 （2、3只有最差）  4不操作
	 */
	private void setValues(XSSFSheet sheet, XSSFCellStyle style,
			XSSFCellStyle greenStyle, XSSFCellStyle redStyle, int rownum,
			Double[] intVal, int sort,int num) {
		Double max = null, min = null;
		// 填充背景颜色
		if (intVal.length - num > 1) {
			max = ExcelUtil.getMax(intVal,num);
			min = ExcelUtil.getMin(intVal,num);
		}
		XSSFRow row = sheet.getRow(rownum);
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		int len = intVal.length;
		for (int i = 3; i < len + 3; i++) {
			XSSFCell cell = row.getCell(i);
			if (null == cell) {
				cell = row.createCell(i);
			}
			if(intVal[i - 3]==null){
				cell.setCellValue("-");
			}else{
				cell.setCellValue(intVal[i - 3]);
			}
			XSSFCellStyle setStyle = style;
			if(i < len + 3 - num && max != null && min != null){
				if (sort != 4 && intVal[i - 3]!=null) {
					int t = ExcelUtil.getBgColor(intVal[i - 3], max, min, sort);
					if(t == 0){
						setStyle = greenStyle;
					}else if(t == 1){
						setStyle = redStyle;
					}
				}
			}
			cell.setCellStyle(setStyle);
		}
	}
	/**
	 * 得到优良中差颜色
	 * 
	 * @return
	 */
	public List<RateColor> getColors() {
		return exportExcelDao.queryColors();
	}

	/**
	 * 拷贝模板到指定位子
	 * 
	 * @throws IOException
	 */
	public void copyFile(String inputpath, String outpath, String name)
			throws IOException {
		File path = new File(outpath);
		if (!path.exists() && !path.isDirectory()) {
			path.mkdirs();
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		fis = new FileInputStream(inputpath);
		fos = new FileOutputStream(outpath + name);
		IOUtils.copy(fis, fos);
		fos.flush();
		fos.close();
	}
	/**
	 * 创建质量报表excel
	 * @param ctx
	 * @param type
	 * @param starttime
	 * @param endtime
	 * @param areaids
	 * @param filePath
	 * @param name
	 * @throws Exception
	 */
	public void writeQualExcel(WebApplicationContext ctx, int type,
			String starttime, String endtime, String areaids, String filePath,
			String name) throws Exception {
		
		String tempPath = filePath + Constant.CAS_TEMPLATE_PATH;
		filePath = filePath + Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH;
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("type", type);
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		Map<String,QualTopAndLast> map = statAndEvalReportService.getTopAndLast(ctx, param);
		QualTopAndLast top1 = map.get("top1");
		QualTopAndLast top5 = map.get("top5");
		// 模板名称
		String fileName = "";
		// 标题
		String title = "";
		String[] strarArr = null;
		String titleVal = "";
		if (starttime != null &&  !"".equals(starttime) && endtime != null
				&&  !"".equals(endtime)) {
			strarArr = starttime.split("-");
		}
		// 设置标题内容
		if (type == 1) {
			if (strarArr != null && strarArr.length > 0) {
				titleVal = DateUtils.dateFormat(starttime);
			}
			fileName = "template_qual_day.xlsx";
			title = "移动网络投诉处理质量日报 - (" + titleVal + ")";
		} else if (type == 2) {
			if (strarArr != null && strarArr.length > 0) {
				titleVal = DateUtils.dateFormat(starttime) + "-"
						+ DateUtils.dateFormat(endtime);
			}
			fileName = "template_qual_week.xlsx";
			title = "移动网络投诉处理质量周报-(" + titleVal + ")";
					
		} else {
			if (strarArr != null && strarArr.length > 0) {
				titleVal = strarArr[0] + "年" + strarArr[1] + "月";
			}
			fileName = "template_qual_month.xlsx";
			title = "移动网络投诉处理质量月报-(" + titleVal + ")";
		}
		
		// 获取累计起始时间
		String defDate = sysConfigDao.queryData(Constant.RP_QUALITY_CUMULATIVE_START_TIME);
		// 模板路径
		String temp = tempPath + fileName;
		copyFile(temp, filePath, name);
		// 读取Excel
		FileInputStream file = new FileInputStream(filePath + name);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		// 第一个sheet
		XSSFSheet sheet = workbook.getSheetAt(0);
		param.put("area_id", areaids);
		//获取质量报表数据
		QualtyReport qr = statAndEvalReportService.getQualityReportData(ctx, param);
		//获取数据的列数
		int size = qr.getAreaid().length;
		
		// 设置标题 以及标题的下一行内容
		setTitle(workbook, title, size + 3, defDate);
		// 设置标题
		setTopVal(workbook, getContent(top1, type,0),4, size + 3,0);
		//设置top5
		setTopVal(workbook, getContent(top5, type,1),1, 15,1);
		
		String green = "#00FF00", red = "#FF0000";
		// 边框样式
		XSSFCellStyle style = ExcelUtil.setBackColorByCustom(workbook, "");
		XSSFCellStyle greenStyle = ExcelUtil.setBackColorByCustom(workbook,
				green);
		XSSFCellStyle redStyle = ExcelUtil.setBackColorByCustom(workbook, red);
		// 依次填充值
		int count = 4;
		int groupnum = this.countGroup();
		//设置区域分类
		setGroupCell(workbook,sheet,count++,groupnum,qr.getAreaid(),qr.getAreaname());
		// 区域名称
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getAreaname(), 4,groupnum);
		//		投诉处理质量综合评分
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getComp_score(), 4,groupnum);
		//		投诉处理质量综合排名		
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getComp_score_rank(), 1,groupnum);
		//		移动网络服务考核得分
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getAssess_score(), 4,groupnum);
		//		累计需实测工单量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_workorder(), 4,groupnum);
		//		月需实测工单量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_workorder(), 4,groupnum);
		//		累计实测量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_test(), 4,groupnum);
		//		月实测量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_test(), 4,groupnum);
		//		累计实测率	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_test_rate(), 0,groupnum);
		if(type != 1){
			//		月测试及时率	
			setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_test_timely(), 4,groupnum);
		}
		//		累计网络投诉工单量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_serialno(), 4,groupnum);
		//		累计解决工单量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_solve(), 4,groupnum);
		//		累计解决率	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_solve_rate(), 0,groupnum);
		//		月网络投诉工单量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_serialno(), 4,groupnum);
		//		月解决工单量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_solve(), 4,groupnum);
		//		累计优化解决量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_major_solve(), 4,groupnum);
		//		累计优化解决比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_major_solve_rate(), 4,groupnum);
		//		月优化解决量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_major_solve(), 4,groupnum);
		//		月优化解决比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_major_solve_rate(), 4,groupnum);
		//		累计建设解决量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_build_solve(), 4,groupnum);
		//		累计建设解决比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_build_solve_rate(), 4,groupnum);
		//		月建设解决量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_build_solve(), 4,groupnum);
		//		月建设解决比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_build_solve_rate(), 4,groupnum);
		//		累计维护解决量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_maintain_solve(), 4,groupnum);
		//		累计维护解决比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_maintain_solve_rate(), 4,groupnum);
		//		月维护解决量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_maintain_solve(), 4,groupnum);
		//		月维护解决比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_maintain_solve_rate(), 4,groupnum);
		//		累计其它真正解决量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_other_solve(), 4,groupnum);
		//		累计其它真正解决比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_other_solve_rate(), 4,groupnum);
		//	 月其它真正解决量
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_other_solve(), 4,groupnum);
		//		 月其它真正解决比
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_other_solve_rate(), 4,groupnum);
		//		累计工单滞留量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_delay(), 4,groupnum);
		//		累计工单滞留率	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_delay_rate(), 1,groupnum);
		//		累计优化工单滞留量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_major_delay(), 4,groupnum);
		//		累计优化工单滞留比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_major_delay_rate(), 4,groupnum);
		//		累计建设工单滞留量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_build_delay(), 4,groupnum);
		//		累计建设工单滞留比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_build_delay_rate(), 4,groupnum);
		//		累计维护工单滞留量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_maintain_delay(), 4,groupnum);
		//		累计维护工单滞留比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_maintain_delay_rate(), 4,groupnum);
		//		累计工单驳回量
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_reject(), 4,groupnum);
		//		月工单驳回量
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_reject(), 4,groupnum);
		//		累计工单驳回率	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_reject_rate(), 2,groupnum);
		//		累计优化工单驳回量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_major_reject(), 4,groupnum);
		//		累计优化工单驳回比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_major_reject_rate(), 4,groupnum);
		//		月优化工单驳回量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_major_reject(), 4,groupnum);
		//		月优化工单驳回比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_major_reject_rate(), 4,groupnum);
		//		累计建设工单驳回量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_build_reject(), 4,groupnum);
		//		累计建设工单驳回比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_build_reject_rate(), 4,groupnum);
		//		月建设工单驳回量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_build_reject(), 4,groupnum);
		//		月建设工单驳回比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_build_reject_rate(), 4,groupnum);
		//		累计维护工单驳回量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_maintain_reject(), 4,groupnum);
		//		累计维护工单驳回比	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_maintain_reject_rate(), 4,groupnum);
		//		月维护工单驳回量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_maintain_reject(), 4,groupnum);
		//		月维护工单驳回比
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_maintain_reject_rate(), 4,groupnum);
		//		累计工单超时量
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_over(), 4,groupnum);
		//		月工单超时量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_over(), 4,groupnum);
		//		累计工单超时率	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_over_rate(), 2,groupnum);
		//		累计工单重派量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_send(), 4,groupnum);
		//		月工单重派量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_send(), 4,groupnum);
		//		累计工单重派率	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_send_rate(), 2,groupnum);
		//		累计工单重复投诉量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_complaint(), 4,groupnum);
		//		月工单重复投诉量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_complaint(), 4,groupnum);
		//		累计重复投诉率	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_complaint_rate(), 2,groupnum);
		
		//		累计工单升级量	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_upgrade(), 4,groupnum);
		//		月工单升级量
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_upgrade(), 4,groupnum);
		//		 累计工单升级率	
		setValues(sheet, style,greenStyle,redStyle, count++, qr.getTotal_upgrade_rate(), 2,groupnum);
		
		if(type !=1 ){
			//		 周3G综合改善评分 	
			setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_wcdma_impr(), 4,groupnum);
			//		 周2G综合改善评分 	
			setValues(sheet, style,greenStyle,redStyle, count++, qr.getCurr_gsm_impr(), 4,groupnum);
			//		 周3G综合改善比例
			setValues(sheet, style,greenStyle,redStyle, count++, qr.getRatio_wcdma_impr(), 4,groupnum);
			//		 周2G综合改善比例	
			setValues(sheet, style,greenStyle,redStyle, count++, qr.getRatio_gsm_impr(), 4,groupnum);
		}

		FileOutputStream os = new FileOutputStream(filePath + name);
		workbook.write(os);
		os.flush();
		os.close();
		
	}
	/**
	 * 
	 * @param workbook
	 * @param sheet
	 * @param rownum
	 * @param groupnum
	 * @param areaid
	 * @param areaname
	 */
	private  void setGroupCell(XSSFWorkbook workbook,XSSFSheet sheet,int rownum,int groupnum,Integer[] areaid,String[] areaname){
		//样式
		XSSFCellStyle style = workbook.createCellStyle();
		//设置边框
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		// 水平居中
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);
		// 垂直居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		List<Group> groups =this.getAreaInGroup(); 
		XSSFRow row = sheet.getRow(rownum);
		int index = 3;
		if (null == row) {
			row = sheet.createRow(rownum);
		}
		Group group = null;
		int count = 0;
		for (int i = 0; i < groups.size(); i++) {
			group = groups.get(i);
			int num = 0;
			for (int j = 0; j < areaid.length; j++) {
				if(j < areaid.length - groupnum && group.getAreas() != null && group.getAreas().indexOf(areaid[j].toString()) >= 0){
					num ++;
				}
			}
			if(num > 0){
				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, count + index,
						count + num - 1 + index));
				XSSFCell cell = row.getCell(count + index);
				if(null == cell){
					cell = row.createCell(count + index);
				}
				cell.setCellValue(group.getGroupname());
				cell.setCellStyle(style);
			}
			count += num;
		}
		if(count + groupnum != areaid.length){
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, count + index,
					areaid.length - groupnum + index - 1));
			XSSFCell cell = row.getCell(count + index);
			if(null == cell){
				cell = row.createCell(count + index);
			}
			cell.setCellValue("未归属区域");
			cell.setCellStyle(style);
		}
		for (int i = areaname.length - groupnum; i < areaname.length; i++) {
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + 1, i + index,
					i + index));
			XSSFCell cell = row.getCell(i + index);
			if(null == cell){
				cell = row.createCell(i + index);
			}
			cell.setCellValue(areaname[i]);
			cell.setCellStyle(style);
		}

	}
	private String getContent(QualTopAndLast top,int type,int issort){
		String content = "投诉处理质量综合评分最高|" +  top.getComp_score_max() + "，"
				+ "最低|" +  top.getComp_score_min() + "；"
				+"累计实测率最高|" + top.getTotal_max_rate() + "，"
				+ "最低|" + top.getTotal_min_rate() + "；"
				+ "累计真正解决率最高|" + top.getTotal_solve_rate_max() + "，"
				+ "最低|" + top.getTotal_solve_rate_min() + "；"
				+ "累计工单滞留率最低|" + sortDesc(top.getTotal_delay_rate_min(),issort) + "，"
				+ "最高|" + top.getTotal_delay_rate_max() + "；"
				+ "累计工单驳回率最低|" + sortDesc(top.getTotal_reject_rate_min(),issort) + "，"
				+ "最高|" + top.getTotal_reject_rate_max() + "；"
				+ "累计工单超时率最低|" + sortDesc(top.getTotal_over_rate_min(),issort) + "，"
				+ "最高|" + top.getTotal_over_rate_max() + "；"
				+ "累计工单重派率最低|" + sortDesc(top.getTotal_send_rate_min(),issort) + "，"
				+ "最高|" + top.getTotal_send_rate_max() + "；"
				+ "累计工单重复投诉率最低|" + sortDesc(top.getTotal_complaint_rate_min(),issort) + "，"
				+ "最高|" + top.getTotal_complaint_rate_max() + "；"
				+ "累计工单升级率最低|" + sortDesc(top.getTotal_upgrade_rate_min(),issort) + "，"
				+ "最高|" + top.getTotal_upgrade_rate_max() + "。";
		if(type == 2){
			content = content.replace("。", "；") + "周测试及时率最高|"
					+ top.getCurr_test_timely_max() + "，" + "最低|"
					+ top.getCurr_test_timely_min() + "。";
		}else if(type == 3){
			content = content.replace("。", "；") + "月测试及时率最高|"
					+ top.getCurr_test_timely_max() + "，" + "最低|"
					+ top.getCurr_test_timely_min() + "。";
		}
		return content.replaceAll("null", "无");
	}
	/**
	 * 
	 * @param kpi
	 * @param type
	 * @return
	 */
	private String sortDesc(String kpi,int issort){
		String str = "";
		if(issort == 1){
			if(!"".equals(kpi)){
				String[] arr = kpi.split("、");
				for(int i = arr.length - 1; i >= 0; i --){
					str += arr[i] + "、";
				}
				if(!"".equals(str)){
					str = str.substring(0, str.length() - 1);
				}
			}
		}else{
			str = kpi;
		}
		return str;
	}
	/**
	 * 查询分公司数量
	 * @return
	 */
	public int countGroup(){
		return exportExcelDao.countGroupNum() + 1;
	}
	public List<Group> getAreaInGroup(){
		return exportExcelDao.queryAreaInGroup();
	}
/**
 * 根据区间时间导出实测量
 * @param area_id
 * @param starttime
 * @param endtime
 * @param type 报表类型 1－测试报告 2－质量报告
 * @param islei 是否累计 1－累计 2－非累计
 * @param groupId组ID ，－1为全网  
 * @return 
 */
	public Map<String,String> excelSerialno(String area_id,String groupid,String starttime,
			String endtime,String systime,Integer type,Integer islei,String filePath){
		Map<String, String> rpt = new HashMap<String, String>();
		rpt.put("sucFlag", "成功");
		rpt.put("testType", islei.toString());
		rpt.put("reportType", type.toString());
		String path="";
		try {
			String key="";
			if(type==1)
	        key="reportdate";
			if(type==2)
			key="rp_quality_cumulative_start_time";
			//String systime=exportExcelDao.queryDefDate(key);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("area_id", area_id);
			map.put("groupid", groupid);
			map.put("endtime", endtime);
			map.put("reportType", type);
			if(islei==1){
				map.put("starttime", systime);
			}else{
				map.put("starttime", starttime);
			}
			map.put("systime", systime);
			List<WorkOrder> list=this.exportExcelDao.excelSerialno(map);
			
			//创建excel准备
			//文件夹路径
			String fp =filePath+Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH;
			//生成文件名称
			path =String.valueOf(System.currentTimeMillis())+(int)(Math.random()*99999);
			//生成的excel路径
			String excelPath=fp+path+".xlsx";
			//生成excel
			//列标题
			String[] colTitles = new String[]{"工单号","投诉时间","要求回复时间","投诉地址","投诉电话","投诉网络"};
			//取值方法名
			String[] methodNames = new String[]{"getSerialno","getSubmitDatetime","getRequestDatetime","getProblemsAddress","getAcceptanceNumber","getNetWorktype"};
			SXSSFWorkbook wbk= new SXSSFWorkbook();
			Sheet sheet = wbk.createSheet("report");
			CellStyle style = getStyle(wbk);
			//设置表标题
			Row row =sheet.createRow(0);
			for(int i=0;i<colTitles.length;i++){
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(colTitles[i]);
			}
			
			//填充数据
			for(int k=0;k<list.size();k++){
				WorkOrder wo = list.get(k);
				Row r =sheet.createRow(k+1);
				for(int z=0;z<colTitles.length;z++){
					Cell c = r.createCell(z);
					c.setCellStyle(style);
					if(methodNames[z].equals("getSubmitDatetime")||methodNames[z].equals("getRequestDatetime")){
						//处理时间字段
						Date d = (Date)ReflectUtil.invokeMethodName(methodNames[z], wo, null);
						if(d!=null){
							c.setCellValue(DateUtils.date2Str(d, "yyyy.MM.dd HH:mm:ss"));
						}
					}else{
						Object obj = ReflectUtil.invokeMethodName(methodNames[z], wo, null);
						if(obj!=null){
							c.setCellValue(obj.toString());
						}
					}
					
				}
			}
			//自适应单元格宽度
			for(int i=0;i<colTitles.length&&list.size()>0;i++){
				if(i!=3&&i!=5){
					sheet.autoSizeColumn(i);
				}
			}
			//判断文件夹路径是否存在
			File file =new File(fp);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}

			FileOutputStream out = new FileOutputStream(excelPath);
			wbk.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			rpt.put("sucFlag", "失败");
		}
		rpt.put("fname", path);
		return rpt;
	}
	
	/**
	 * 一般样式
	 */
	public CellStyle getStyle(SXSSFWorkbook xwb){
		CellStyle style = xwb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		return style;
	}
	
}
