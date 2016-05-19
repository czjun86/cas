package com.complaint.service;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.action.vo.CellVo;
import com.complaint.action.vo.VoBean;
import com.complaint.dao.BaseStationDao;
import com.complaint.model.Gisgrad;
import com.complaint.model.GwCasCell;
import com.complaint.model.ReportCells;
import com.complaint.utils.Constant;
import com.complaint.utils.ExcelUtil;

@Service("gisgradExcelDownLoadService")
public class GisgradExcelDownLoadService {
	@Autowired
	private BaseStationService baseStationService;
	@Autowired
	private BaseStationDao baseStationDao;
	private static final Logger logger = LoggerFactory.getLogger(GisgradExcelDownLoadService.class);
	private static final int EXCELNUM=10000;
	/**
	 * 创建Excel表格
	 * 
	 * @param gisgrads
	 */
	public void createExcel(List<Gisgrad> gisgrads, String filePath,VoBean vo) {
		// 创建excel和第一页
		Workbook wb = new SXSSFWorkbook(100);
		int dex=gisgrads.size()/EXCELNUM;
		if(dex==0){dex+=1;}
		if(gisgrads.size()/EXCELNUM>0&&gisgrads.size()%EXCELNUM>0){dex+=1;}
		Sheet sheet = null;
		for(int k=0;k<dex;k++){
			sheet = wb.createSheet("地图评价报表"+k);

		/* ----------------第一行设计列名----------------------- */
		Row rowTop = null;
		if (sheet.getRow(0) != null) {
			rowTop = sheet.getRow(0);
		} else {
			rowTop = sheet.createRow(0);
		}
		CellStyle styleTop = getNormalStyle(wb, "#00ccFF");
		//1开始时间
		Cell stTop = rowTop.createCell(0);
		stTop.setCellValue("开始时间");
		stTop.setCellStyle(styleTop);
		//2结束时间
		Cell endTop = rowTop.createCell(1);
		endTop.setCellValue("结束时间");
		endTop.setCellStyle(styleTop);
		//3时间类型
		Cell timesTop = rowTop.createCell(2);
		timesTop.setCellValue("时间类型");
		timesTop.setCellStyle(styleTop);
		
		// 第4列,区域
		Cell areaTop = rowTop.createCell(3);
		areaTop.setCellValue("区域");
		areaTop.setCellStyle(styleTop);
		// 第5列,室内外,inside
		Cell cellInsideTop = rowTop.createCell(4);
		cellInsideTop.setCellValue("室内/室外");
		cellInsideTop.setCellStyle(styleTop);
		// 第6列 ,工间，ser
		Cell cellSerTop = rowTop.createCell(5);
		cellSerTop.setCellValue("工单号");
		cellSerTop.setCellStyle(styleTop);
		
		// 第7列 ,投诉文件名
		Cell flowidTop = rowTop.createCell(6);
		flowidTop.setCellValue("测试文件名");
		flowidTop.setCellStyle(styleTop);
		//第8列 ,投诉类型
		Cell workTop = rowTop.createCell(7);
		workTop.setCellValue("投诉类型");
		workTop.setCellStyle(styleTop);
		//第9列 ,投诉电话
		Cell phoneTop = rowTop.createCell(8);
		phoneTop.setCellValue("投诉电话");
		phoneTop.setCellStyle(styleTop);
		// 第10列 ,受理路径，ser
		Cell pathTop = rowTop.createCell(9);
		pathTop.setCellValue("受理路径");
		pathTop.setCellStyle(styleTop);
		// 第11列,网络
		Cell cellNetTop = rowTop.createCell(10);
		cellNetTop.setCellValue("网络");
		cellNetTop.setCellStyle(styleTop);
		// 第12列,测试方式
		Cell netTypeTop = rowTop.createCell(11);
		netTypeTop.setCellValue("测试方式");
		netTypeTop.setCellStyle(styleTop);
		// 第13列,业务类型
		Cell typeTop = rowTop.createCell(12);
		typeTop.setCellValue("业务类型");
		typeTop.setCellStyle(styleTop);
		// 第14列,3G网络占比
		Cell G3Top = rowTop.createCell(13);
		G3Top.setCellValue("3G网络占比");
		G3Top.setCellStyle(styleTop);
		// 第15列,2G网络占比
		Cell G2Top = rowTop.createCell(14);
		G2Top.setCellValue("2G网络占比");
		G2Top.setCellStyle(styleTop);
		// 第16列,2G网络占比
		Cell nosTop = rowTop.createCell(15);
		nosTop.setCellValue("脱网率");
		nosTop.setCellStyle(styleTop);
		// 第17列,指标等级,rscp
		Cell cellRscpTop = rowTop.createCell(16);
		cellRscpTop.setCellValue("Rscp");
		cellRscpTop.setCellStyle(styleTop);
		// 第18列,指标等级,ecno
		Cell cellEcnoTop = rowTop.createCell(17);
		cellEcnoTop.setCellValue("Ec/No");
		cellEcnoTop.setCellStyle(styleTop);
		// 第19列,指标等级,tx
		Cell cellTxTop = rowTop.createCell(18);
		cellTxTop.setCellValue("TxPower");
		cellTxTop.setCellStyle(styleTop);
		// 第20列,指标等级,fu
		Cell cellFuTop = rowTop.createCell(19);
		cellFuTop.setCellValue("FtpUp");
		cellFuTop.setCellStyle(styleTop);
		// 第21列,指标等级,fd
		Cell cellFdTop = rowTop.createCell(20);
		cellFdTop.setCellValue("FtpDown");
		cellFdTop.setCellStyle(styleTop);
		// 第22列,指标等级,rx
		Cell cellRxTop = rowTop.createCell(21);
		cellRxTop.setCellValue("RxLev");
		cellRxTop.setCellStyle(styleTop);
		// 第23列,指标等级,rq
		Cell cellRqTop = rowTop.createCell(22);
		cellRqTop.setCellValue("Rxqual");
		cellRqTop.setCellStyle(styleTop);
		// 第23列,指标等级,ci
		Cell cellCiTop = rowTop.createCell(23);
		cellCiTop.setCellValue("C/I");
		cellCiTop.setCellStyle(styleTop);
		// 第24列,真正等级如:优+，优-
		Cell cellrealgradTop = rowTop.createCell(24);
		cellrealgradTop.setCellValue("综合评价");
		cellrealgradTop.setCellStyle(styleTop);
		// 第25列,场景
		Cell secnTop = rowTop.createCell(25);
		secnTop.setCellValue("场景");
		secnTop.setCellStyle(styleTop);
		// 第26列,接单时间
		Cell jtimeTop = rowTop.createCell(26);
		jtimeTop.setCellValue("接单时间");
		jtimeTop.setCellStyle(styleTop);

		// 第27列,测试时间
		Cell testTop = rowTop.createCell(27);
		testTop.setCellValue("测试时间");
		testTop.setCellStyle(styleTop);
		// 第28列,经度
		Cell lngTop = rowTop.createCell(28);
		lngTop.setCellValue("经度");
		lngTop.setCellStyle(styleTop);
		// 第29列,纬度
		Cell latTop = rowTop.createCell(29);
		latTop.setCellValue("纬度");
		latTop.setCellStyle(styleTop);

		// 第30列,地址,add
		Cell cellAddTop = rowTop.createCell(30);
		cellAddTop.setCellValue("地址");
		cellAddTop.setCellStyle(styleTop);
		// 第31列,任务类型
		Cell jobTop = rowTop.createCell(31);
		jobTop.setCellValue("任务类型");
		jobTop.setCellStyle(styleTop);
		// 第32列,测试总数
		Cell sumTop = rowTop.createCell(32);
		sumTop.setCellValue("测试log的点数");
		sumTop.setCellStyle(styleTop);
		

		// 使单元格自动适应内容长度
		for (int i = 0; i < 33; i++) {
			sheet.autoSizeColumn(i, true);
		}
		/* ----------------进行内容填充----------------------- */
		// 当第i行时，没有行就创建，有就直接使用
		int num=(k+1)*EXCELNUM;
		if(num>gisgrads.size()){num=gisgrads.size();}
		for (int i = k*EXCELNUM; i < num; i++) {
			Row row = null;
			if (sheet.getRow(i%EXCELNUM + 1) != null) {
				row = sheet.getRow(i%EXCELNUM  + 1);
			} else {
				row = sheet.createRow(i%EXCELNUM  + 1);
			}
			// 更具颜色生成一般单元格样式
			CellStyle style = getBodyStyle(wb, gisgrads.get(i).getColor());
			// 对该行单元格内容填充
			
			// 第1列,开始时间
			Cell cellstime = row.createCell(0);
			cellstime.setCellValue(vo.getStartTime()==null?"":vo.getStartTime());
			cellstime.setCellStyle(style);
			// 第2列,结束时间
			Cell cellend = row.createCell(1);
			cellend.setCellValue(vo.getEndTime()==null?"":vo.getEndTime());
			cellend.setCellStyle(style);
			// 第3列,时间类型
			String timetype="";
			if(vo!=null&&vo.getDatatype().equals("1"))timetype="测试时间";
			if(vo!=null&&vo.getDatatype().equals("2"))timetype="接单时间";
			Cell celltimeType = row.createCell(2);
			celltimeType.setCellValue(timetype);
			celltimeType.setCellStyle(style);
						
			// 第4列,区域
			String area = "";
			if (gisgrads.get(i).getArea() != null) {
				area = gisgrads.get(i).getArea();
			}
			Cell cellarea = row.createCell(3);
			cellarea.setCellValue(area);
			cellarea.setCellStyle(style);
			// 第5列,室内外,inside
			String inside = "";
			if (gisgrads.get(i).getInside().equals("0")) {
				inside = "室外";
			} else if (gisgrads.get(i).getInside().equals("1")) {
				inside = "室内";
			}
			Cell cellInside = row.createCell(4);
			cellInside.setCellValue(inside);
			cellInside.setCellStyle(style);
			// 第6列 ,工单，ser
			Cell cellSer = row.createCell(5);
			cellSer.setCellValue(gisgrads.get(i).getSer()==null?"":gisgrads.get(i).getSer());
			cellSer.setCellStyle(style);
			// 第7列 ,流水
			Cell cellflowid = row.createCell(6);
			String flowid=gisgrads.get(i).getSer();
			if(gisgrads.get(i).getFlowid()!=null){
				flowid+="-"+gisgrads.get(i).getFlowid().substring(0,8);
				flowid+="-"+gisgrads.get(i).getFlowid().substring(8,14);
				
			}
			cellflowid.setCellValue(flowid);
			cellflowid.setCellStyle(style);
			// 第8列 ,投诉网络
			Cell workphone = row.createCell(7);
			workphone.setCellValue(gisgrads.get(i).getNetwork()==null?"":gisgrads.get(i).getNetwork());
			workphone.setCellStyle(style);
			// 第9列 ,投诉电话
			Cell cellphone = row.createCell(8);
			cellphone.setCellValue(gisgrads.get(i).getPhone()==null?"":gisgrads.get(i).getPhone());
			cellphone.setCellStyle(style);
			// 第10列 ,受理路径
			Cell cellpath = row.createCell(9);
			cellpath.setCellValue(gisgrads.get(i).getPath()==null?"":gisgrads.get(i).getPath());
			cellpath.setCellStyle(style);
			// 第11列,网络制式
		
			String netType = "";
			if (gisgrads.get(i).getNet().equals("1")||gisgrads.get(i).getNet().equals("4")) {
				netType = "GSM";
			} else if (gisgrads.get(i).getNet().equals("2")||gisgrads.get(i).getNet().equals("3")) {
				netType = "WCDMA";
			}
			Cell cellNet = row.createCell(10);
			cellNet.setCellValue(netType);
			cellNet.setCellStyle(style);
			// 第12列,测试网络
			String testnet = "";
			if (gisgrads.get(i).getNet().equals("1")) {
				testnet = "GSM锁频";
			} else if (gisgrads.get(i).getNet().equals("3")||gisgrads.get(i).getNet().equals("4")) {
				testnet = "WCDMA自由模式";
			} else if (gisgrads.get(i).getNet().equals("2")) {
				testnet = "WCDMA锁频";
			}
			Cell cellNettype = row.createCell(11);
			cellNettype.setCellValue(testnet);
			cellNettype.setCellStyle(style);

			// 第13列,业务类型
			String testtype = "";
			if (gisgrads.get(i).getType() != null) {
				if (gisgrads.get(i).getType().equals("1"))
					testtype = "IDLE";
				if (gisgrads.get(i).getType().equals("2"))
				{testtype = "语音";
					if (gisgrads.get(i).getCall_type() == 1) {
						testtype+= "短呼";
					} else if (gisgrads.get(i).getCall_type() == 2) {
						testtype += "长呼";
					} 
				}
					
				if (gisgrads.get(i).getType().equals("3")){
					testtype = "数据";
					if (gisgrads.get(i).getFtp_type() == 1) {
						testtype += "上行";
					} else if (gisgrads.get(i).getFtp_type()== 2) {
						testtype += "下行";
					}
				}
			}
			Cell celltype = row.createCell(12);
			celltype.setCellValue(testtype);
			celltype.setCellStyle(style);

			//14 3G占比
			Cell cellg3 = row.createCell(13);
			cellg3.setCellValue(gisgrads.get(i).getPocent3()+"%");
			cellg3.setCellStyle(style);
			//15 2G占比
			Cell cellg2 = row.createCell(14);
			cellg2.setCellValue(gisgrads.get(i).getPocent2()+"%");
			cellg2.setCellStyle(style);

			//16  脱网占比
			Cell cellgtalk = row.createCell(15);
			cellgtalk.setCellValue(gisgrads.get(i).getTalkaround()+"%");
			cellgtalk.setCellStyle(style);

			// 第17列,指标等级,rscp
			String Rscp = "";
			if (gisgrads.get(i).getRscp() != null) {
				Rscp = gisgrads.get(i).getRscp();
			}
			Cell cellRscp = row.createCell(16);
			cellRscp.setCellValue(Rscp);
			cellRscp.setCellStyle(style);
			// 第18列,指标等级,ecno
			String ecno = "";
			if (gisgrads.get(i).getEcno() != null) {
				ecno = gisgrads.get(i).getEcno();
			}
			Cell cellEcno = row.createCell(17);
			cellEcno.setCellValue(ecno);
			cellEcno.setCellStyle(style);
			// 第19列,指标等级,tx
			String tx = "";
			if (gisgrads.get(i).getTx() != null) {
				tx = gisgrads.get(i).getTx();
			}
			Cell cellTx = row.createCell(18);
			cellTx.setCellValue(tx);
			cellTx.setCellStyle(style);
			// 第20列,指标等级,fu
			String fu = "";
			if (gisgrads.get(i).getFu() != null) {
				fu = gisgrads.get(i).getFu();
			}
			Cell cellFu = row.createCell(19);
			cellFu.setCellValue(fu);
			cellFu.setCellStyle(style);
			// 第21列,指标等级,fd
			String fd = "";
			if (gisgrads.get(i).getFd() != null) {
				fd = gisgrads.get(i).getFd();
			}
			Cell cellFd = row.createCell(20);
			cellFd.setCellValue(fd);
			cellFd.setCellStyle(style);
			// 第22列,指标等级,rx
			String rx = "";
			if (gisgrads.get(i).getRx() != null) {
				rx = gisgrads.get(i).getRx();
			}
			Cell cellRx = row.createCell(21);
			cellRx.setCellValue(rx);
			cellRx.setCellStyle(style);
			// 第23列,指标等级,rq
			String rq = "";
			if (gisgrads.get(i).getRq() != null) {
				rq = gisgrads.get(i).getRq();
			}
			Cell cellRq = row.createCell(22);
			cellRq.setCellValue(rq);
			cellRq.setCellStyle(style);
			
			// 第24列,指标等级,ci
			String ci = "";
			if (gisgrads.get(i).getCi() != null) {
				ci = gisgrads.get(i).getCi();
			}
			Cell cellCi = row.createCell(23);
			cellCi.setCellValue(ci);
			cellCi.setCellStyle(style);
			
			// 第25列,真正等级如:优+，优-
			Cell cellrealgrad = row.createCell(24);
			cellrealgrad.setCellValue(gisgrads.get(i).getRealgrad()==null?"":gisgrads.get(i).getRealgrad());
			cellrealgrad.setCellStyle(style);
			// 第26列,场景
			String sen = "";
			if (gisgrads.get(i).getSence() != null) {
				sen = gisgrads.get(i).getSence();
			}
			Cell cellSen = row.createCell(25);
			cellSen.setCellValue(sen);
			cellSen.setCellStyle(style);

			// 第27列,接单时间
			String jtime = "";
			if (gisgrads.get(i).getJtime()!= null&&!gisgrads.get(i).getJtime().equals("null")) {
				jtime = gisgrads.get(i).getJtime();
			}
			Cell celljtime = row.createCell(26);
			celljtime.setCellValue(jtime);
			celljtime.setCellStyle(style);

			// 第28列,测试时间
			String testtime = "";
			if (gisgrads.get(i).getTime() != null&&!gisgrads.get(i).getTime().equals("null")) {
				testtime = gisgrads.get(i).getTime();
			}
			Cell celltime = row.createCell(27);
			celltime.setCellValue(testtime);
			celltime.setCellStyle(style);
			// 第29列,经度
			Cell celllng = row.createCell(28);
			celllng.setCellValue(gisgrads.get(i).getLng()==null?0:gisgrads.get(i).getLng());
			celllng.setCellStyle(style);
			// 第30列,纬度 
			Cell celllat = row.createCell(29);
			celllat.setCellValue(gisgrads.get(i).getLat()==null?0:gisgrads.get(i).getLat());
			celllat.setCellStyle(style);
			// 第31列,地址,add
			Cell cellAdd = row.createCell(30);
			cellAdd.setCellValue(gisgrads.get(i).getAdd()==null?"":gisgrads.get(i).getAdd());
			cellAdd.setCellStyle(style);
			// 第32列,任务类型
			Cell celljob = row.createCell(31);
			celljob.setCellValue("投诉工单");
			celljob.setCellStyle(style);
			// 第33列,测试总数
			Cell sumjob = row.createCell(32);
			sumjob.setCellValue(gisgrads.get(i).getSumc()==null?"":gisgrads.get(i).getSumc());
			sumjob.setCellStyle(style);

		}

		
		}
		try {
			// 获取一个输出流
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// 生成Excel
			wb.write(fileOut);
			// 关闭流
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 创建Excel一般样式
	 */
	public CellStyle getNormalStyle(Workbook wb, String bgcolor) {
		CellStyle style = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		// 加边框线
		style.setBorderBottom(CellStyle.BORDER_THIN);// 下方加边框
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());// 下方边框样式
		style.setBorderLeft(CellStyle.BORDER_THIN);// 左方加边框
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());// 左方边框样式
		style.setBorderRight(CellStyle.BORDER_THIN);// 右方加边框
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());// 右方边框样式
		style.setBorderTop(CellStyle.BORDER_THIN);// 上方加边框
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());// 上方边框样式

		// 设置颜色
		// 16进制颜色转化成RGB
		// int red = Integer.valueOf(bgcolor.substring(1, 3),16);
		// int green = Integer.valueOf(bgcolor.substring(3, 5),16);
		// int blue = Integer.valueOf(bgcolor.substring(5, 7),16);
		// style.setFillForegroundColor(new XSSFColor(new Color(red, green,
		// blue)));// 设置前端颜色
		// style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置填充模式
		//
		// 对齐方式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

		// 字体样式
		Font font = wb.createFont();// 创建字体对象
		font.setFontHeightInPoints((short) 15);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
		font.setFontName("宋体");// 设置为黑体字
		style.setFont(font);// 将字体加入到样式对象
		style.setDataFormat(format.getFormat("@"));
		return style;
	}
	
	/**
	 * 创建Excel正文样式
	 */
	public CellStyle getBodyStyle(Workbook wb, String bgcolor) {
		CellStyle style = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		// 加边框线
		style.setBorderBottom(CellStyle.BORDER_THIN);// 下方加边框
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());// 下方边框样式
		style.setBorderLeft(CellStyle.BORDER_THIN);// 左方加边框
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());// 左方边框样式
		style.setBorderRight(CellStyle.BORDER_THIN);// 右方加边框
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());// 右方边框样式
		style.setBorderTop(CellStyle.BORDER_THIN);// 上方加边框
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());// 上方边框样式

		// 设置颜色
		// 16进制颜色转化成RGB
		// int red = Integer.valueOf(bgcolor.substring(1, 3),16);
		// int green = Integer.valueOf(bgcolor.substring(3, 5),16);
		// int blue = Integer.valueOf(bgcolor.substring(5, 7),16);
		// style.setFillForegroundColor(new XSSFColor(new Color(red, green,
		// blue)));// 设置前端颜色
		// style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置填充模式
		//
		// 对齐方式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

		// 字体样式
		Font font = wb.createFont();// 创建字体对象
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
		font.setFontName("宋体");// 设置为黑体字
		style.setFont(font);// 将字体加入到样式对象
		style.setDataFormat(format.getFormat("@"));
		return style;
	}
	/***
	 * 创建小区信息excel
	 * 
	 * @param cells
	 * @param filePath
	 */
	public void createNearCellInfoExcel(List<GwCasCell>  cells, String filePath) {
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sheet = wb.createSheet("小区信息报表");
		Row topRow = sheet.createRow(0);
		// 标题样式
		CellStyle topStyle = getNormalStyle(wb, "#00ccFF");
		int count = 0;
		// 创建标题
		createTitle(topRow, topStyle, "城市名称", count ++);
		createTitle(topRow, topStyle, "行政区域", count ++);
		createTitle(topRow, topStyle, "区域id", count ++);
		createTitle(topRow, topStyle, "小区cid", count ++);
		createTitle(topRow, topStyle, "基站id", count ++);
		createTitle(topRow, topStyle, "基站名称", count ++);
		createTitle(topRow, topStyle, "小区名称", count ++);
		createTitle(topRow, topStyle, "经度", count ++);
		createTitle(topRow, topStyle, "纬度", count ++);
		createTitle(topRow, topStyle, "psc/bcch", count ++);
		createTitle(topRow, topStyle, "网络模式", count ++);
		createTitle(topRow, topStyle, "室内外", count ++);
		createTitle(topRow, topStyle, "天线类型", count ++);
		createTitle(topRow, topStyle, "天线方位角", count ++);
		createTitle(topRow, topStyle, "天线高度", count ++);
		createTitle(topRow, topStyle, "天线电子倾角", count ++);
		createTitle(topRow, topStyle, "天线机械倾角", count ++);
		createTitle(topRow, topStyle, "共用天线", count ++);
		createTitle(topRow, topStyle, "基站站址", count ++);
		createTitle(topRow, topStyle, "海拔高度", count ++);
		createTitle(topRow, topStyle, "设备厂商", count ++);
		createTitle(topRow, topStyle, "塔桅类型", count ++);
		createTitle(topRow, topStyle, "设备类型", count ++);
		createTitle(topRow, topStyle, "基站配置", count ++);
		createTitle(topRow, topStyle, "工程进展", count ++);
		createTitle(topRow, topStyle, "覆盖范围", count ++);
		createTitle(topRow, topStyle, "覆盖类型", count ++);
		createTitle(topRow, topStyle, "是否rru小区", count ++);
		createTitle(topRow, topStyle, "小区编号", count ++);
		createTitle(topRow, topStyle, "直放站数量", count ++);
		createTitle(topRow, topStyle, "共天馈小区", count ++);
		createTitle(topRow, topStyle, "共天馈属性", count ++);
		createTitle(topRow, topStyle, "共站名称", count ++);
		createTitle(topRow, topStyle, "共站属性", count ++);
		createTitle(topRow, topStyle, "网管运行状态", count ++);
		createTitle(topRow, topStyle, "行政区域类型", count ++);
		createTitle(topRow, topStyle, "上行频点", count ++);
		createTitle(topRow, topStyle, "下行频点", count ++);
		createTitle(topRow, topStyle, "rnc名称", count ++);
		createTitle(topRow, topStyle, "网元类型", count ++);
		createTitle(topRow, topStyle, "天线发射功率", count ++);
		createTitle(topRow, topStyle, "下倾角", count ++);
		createTitle(topRow, topStyle, "是否可以电调", count ++);
		createTitle(topRow, topStyle, "基站类型", count ++);
		createTitle(topRow, topStyle, "rru数量", count ++);
		createTitle(topRow, topStyle, "逻辑小区级别", count ++);
		createTitle(topRow, topStyle, "扇区配置的无线容量", count ++);
		createTitle(topRow, topStyle, "e1数量", count ++);
		createTitle(topRow, topStyle, "fe数量", count ++);
		createTitle(topRow, topStyle, "传输方式", count ++);
		createTitle(topRow, topStyle, "重庆区域覆盖", count ++);
		createTitle(topRow, topStyle, "设计无线容量", count ++);
		createTitle(topRow, topStyle, "基站最大发射功率", count ++);
		createTitle(topRow, topStyle, "内置下倾角", count ++);
		createTitle(topRow, topStyle, "退服时间", count ++);
		createTitle(topRow, topStyle, "小区的最大下行发射功率", count ++);
		createTitle(topRow, topStyle, "一级场景", count ++);
		createTitle(topRow, topStyle, "二级场景", count ++);
		createTitle(topRow, topStyle, "是否城区", count ++);
		createTitle(topRow, topStyle, "是否支持hsdpa", count ++);
		createTitle(topRow, topStyle, "hsdpa功能状态", count ++);
		createTitle(topRow, topStyle, "hs-pdsch代码", count ++);
		createTitle(topRow, topStyle, "小区中广播信道的功率", count ++);
		createTitle(topRow, topStyle, "是否支持hsupa", count ++);
		createTitle(topRow, topStyle, "hsupa功能状态", count ++);
		createTitle(topRow, topStyle, "是否支持mbms", count ++);
		createTitle(topRow, topStyle, "mbms功能状态", count ++);
		createTitle(topRow, topStyle, "mich信道数", count ++);
		createTitle(topRow, topStyle, "sf=16的码字数", count ++);
		createTitle(topRow, topStyle, "sf=128的码字数", count ++);
		createTitle(topRow, topStyle, "e-agch信道的初始数目", count ++);
		createTitle(topRow, topStyle, "e-rgch/e-hich信道的初始数目", count ++);
		createTitle(topRow, topStyle, "pcpich信道最大发射功率", count ++);
		createTitle(topRow, topStyle, "pcpich信道最小发射功率", count ++);
		createTitle(topRow, topStyle, "小区中使用的主导频信道的功率", count ++);
		createTitle(topRow, topStyle, "小区中主同步信道的下行功率", count ++);
		createTitle(topRow, topStyle, "单载频容纳最大用户数", count ++);
		createTitle(topRow, topStyle, "小区配置的载频发射功率", count ++);
		createTitle(topRow, topStyle, "配置的上行ce容量", count ++);
		createTitle(topRow, topStyle, "配置的下行ce容量", count ++);
		createTitle(topRow, topStyle, "iub接口atm端口的配置带宽", count ++);
		createTitle(topRow, topStyle, "iub接口ip端口的配置带宽", count ++);
		createTitle(topRow, topStyle, "iu接口atm层配置带宽", count ++);
		createTitle(topRow, topStyle, "iu接口ip层配置带宽", count ++);
		createTitle(topRow, topStyle, "iur接口atm层配置带宽", count ++);
		createTitle(topRow, topStyle, "iur接口ip层配置带宽", count ++);
		createTitle(topRow, topStyle, "备注", count ++);
		createTitle(topRow, topStyle, "最后更新时间", count ++);
		createTitle(topRow, topStyle, "rac", count ++);
		createTitle(topRow, topStyle, "sac", count ++);
		createTitle(topRow, topStyle, "rnc编号", count ++);
		createTitle(topRow, topStyle, "基站编号", count ++);
		createTitle(topRow, topStyle, "ura编号", count ++);
		createTitle(topRow, topStyle, "是否安装塔顶放大器", count ++);
		createTitle(topRow, topStyle, "扇区编号", count ++);
		createTitle(topRow, topStyle, "入网时间", count ++);
		createTitle(topRow, topStyle, "vip站", count ++);
		createTitle(topRow, topStyle, "室内室外", count ++);
		createTitle(topRow, topStyle, "bsc名称", count ++);
		createTitle(topRow, topStyle, "省份", count ++);
		createTitle(topRow, topStyle, "基站设备型号", count ++);
		createTitle(topRow, topStyle, "机柜类型", count ++);
		createTitle(topRow, topStyle, "载波类型", count ++);
		createTitle(topRow, topStyle, "共用馈线", count ++);
		createTitle(topRow, topStyle, "共用平台", count ++);
		createTitle(topRow, topStyle, "小区频段", count ++);
		createTitle(topRow, topStyle, "小区标识", count ++);
		createTitle(topRow, topStyle, "小区bsic", count ++);
		createTitle(topRow, topStyle, "tch频点", count ++);
		createTitle(topRow, topStyle, "配置载频数", count ++);
		createTitle(topRow, topStyle, "可用载频数", count ++);
		createTitle(topRow, topStyle, "载频最大发射功率", count ++);
		createTitle(topRow, topStyle, "配置控制信道数", count ++);
		createTitle(topRow, topStyle, "配置业务信道数", count ++);
		createTitle(topRow, topStyle, "sdcch可用数", count ++);
		createTitle(topRow, topStyle, "天线俯仰角", count ++);
		createTitle(topRow, topStyle, "跳频模式", count ++);
		createTitle(topRow, topStyle, "是否开通半速率", count ++);
		createTitle(topRow, topStyle, "gprs开通情况", count ++);
		createTitle(topRow, topStyle, "edge开通情况", count ++);
		createTitle(topRow, topStyle, "增强全速率开通情况", count ++);
		createTitle(topRow, topStyle, "网络制式", count ++);
		/*createTitle(topRow, topStyle, "3G邻区1", count ++);
		createTitle(topRow, topStyle, "3G邻区2", count ++);
		createTitle(topRow, topStyle, "3G邻区3", count ++);
		createTitle(topRow, topStyle, "3G邻区4", count ++);
		createTitle(topRow, topStyle, "3G邻区5", count ++);
		createTitle(topRow, topStyle, "3G邻区6", count ++);
		createTitle(topRow, topStyle, "3G邻区7", count ++);
		createTitle(topRow, topStyle, "3G邻区8", count ++);
		createTitle(topRow, topStyle, "3G邻区9", count ++);
		createTitle(topRow, topStyle, "3G邻区10", count ++);
		createTitle(topRow, topStyle, "3G邻区11", count ++);
		createTitle(topRow, topStyle, "3G邻区12", count ++);
		createTitle(topRow, topStyle, "3G邻区13", count ++);
		createTitle(topRow, topStyle, "3G邻区14", count ++);
		createTitle(topRow, topStyle, "3G邻区15", count ++);
		createTitle(topRow, topStyle, "3G邻区16", count ++);
		createTitle(topRow, topStyle, "3G邻区17", count ++);
		createTitle(topRow, topStyle, "3G邻区18", count ++);
		createTitle(topRow, topStyle, "3G邻区19", count ++);
		createTitle(topRow, topStyle, "3G邻区20", count ++);
		createTitle(topRow, topStyle, "3G邻区21", count ++);
		createTitle(topRow, topStyle, "3G邻区22", count ++);
		createTitle(topRow, topStyle, "3G邻区23", count ++);
		createTitle(topRow, topStyle, "3G邻区24", count ++);
		createTitle(topRow, topStyle, "3G邻区25", count ++);
		createTitle(topRow, topStyle, "3G邻区26", count ++);
		createTitle(topRow, topStyle, "3G邻区27", count ++);
		createTitle(topRow, topStyle, "3G邻区28", count ++);
		createTitle(topRow, topStyle, "3G邻区29", count ++);
		createTitle(topRow, topStyle, "3G邻区30", count ++);
		createTitle(topRow, topStyle, "3G邻区31", count ++);
		createTitle(topRow, topStyle, "3G邻区32", count ++);
		createTitle(topRow, topStyle, "3G邻区33", count ++);
		createTitle(topRow, topStyle, "3G邻区34", count ++);
		createTitle(topRow, topStyle, "3G邻区35", count ++);
		createTitle(topRow, topStyle, "3G邻区36", count ++);
		createTitle(topRow, topStyle, "3G邻区37", count ++);
		createTitle(topRow, topStyle, "3G邻区38", count ++);
		createTitle(topRow, topStyle, "3G邻区39", count ++);
		createTitle(topRow, topStyle, "3G邻区40", count ++);
		createTitle(topRow, topStyle, "3G邻区41", count ++);
		createTitle(topRow, topStyle, "3G邻区42", count ++);
		createTitle(topRow, topStyle, "3G邻区43", count ++);
		createTitle(topRow, topStyle, "3G邻区44", count ++);
		createTitle(topRow, topStyle, "3G邻区45", count ++);
		createTitle(topRow, topStyle, "3G邻区46", count ++);
		createTitle(topRow, topStyle, "3G邻区47", count ++);
		createTitle(topRow, topStyle, "3G邻区48", count ++);
		createTitle(topRow, topStyle, "3G邻区49", count ++);
		createTitle(topRow, topStyle, "3G邻区50", count ++);
		createTitle(topRow, topStyle, "3G邻区51", count ++);
		createTitle(topRow, topStyle, "3G邻区52", count ++);
		createTitle(topRow, topStyle, "3G邻区53", count ++);
		createTitle(topRow, topStyle, "3G邻区54", count ++);
		createTitle(topRow, topStyle, "3G邻区55", count ++);
		createTitle(topRow, topStyle, "3G邻区56", count ++);
		createTitle(topRow, topStyle, "3G邻区57", count ++);
		createTitle(topRow, topStyle, "3G邻区58", count ++);
		createTitle(topRow, topStyle, "3G邻区59", count ++);
		createTitle(topRow, topStyle, "3G邻区60", count ++);
		createTitle(topRow, topStyle, "3G邻区61", count ++);
		createTitle(topRow, topStyle, "3G邻区62", count ++);
		createTitle(topRow, topStyle, "3G邻区63", count ++);
		createTitle(topRow, topStyle, "3G邻区64", count ++);
		createTitle(topRow, topStyle, "2G邻区1", count ++);
		createTitle(topRow, topStyle, "2G邻区2", count ++);
		createTitle(topRow, topStyle, "2G邻区3", count ++);
		createTitle(topRow, topStyle, "2G邻区4", count ++);
		createTitle(topRow, topStyle, "2G邻区5", count ++);
		createTitle(topRow, topStyle, "2G邻区6", count ++);
		createTitle(topRow, topStyle, "2G邻区7", count ++);
		createTitle(topRow, topStyle, "2G邻区8", count ++);
		createTitle(topRow, topStyle, "2G邻区9", count ++);
		createTitle(topRow, topStyle, "2G邻区10", count ++);
		createTitle(topRow, topStyle, "2G邻区11", count ++);
		createTitle(topRow, topStyle, "2G邻区12", count ++);
		createTitle(topRow, topStyle, "2G邻区13", count ++);
		createTitle(topRow, topStyle, "2G邻区14", count ++);
		createTitle(topRow, topStyle, "2G邻区15", count ++);
		createTitle(topRow, topStyle, "2G邻区16", count ++);
		createTitle(topRow, topStyle, "2G邻区17", count ++);
		createTitle(topRow, topStyle, "2G邻区18", count ++);
		createTitle(topRow, topStyle, "2G邻区19", count ++);
		createTitle(topRow, topStyle, "2G邻区20", count ++);
		createTitle(topRow, topStyle, "2G邻区21", count ++);
		createTitle(topRow, topStyle, "2G邻区22", count ++);
		createTitle(topRow, topStyle, "2G邻区23", count ++);
		createTitle(topRow, topStyle, "2G邻区24", count ++);
		createTitle(topRow, topStyle, "2G邻区25", count ++);
		createTitle(topRow, topStyle, "2G邻区26", count ++);
		createTitle(topRow, topStyle, "2G邻区27", count ++);
		createTitle(topRow, topStyle, "2G邻区28", count ++);
		createTitle(topRow, topStyle, "2G邻区29", count ++);
		createTitle(topRow, topStyle, "2G邻区30", count ++);
		createTitle(topRow, topStyle, "2G邻区31", count ++);
		createTitle(topRow, topStyle, "2G邻区32", count ++);*/
		for(int i = 0; i < cells.size(); i ++){
			setValue(i+1,cells.get(i),sheet);
		}
		// 使单元格自动适应内容长度
		for (int i = 0; i < 122; i++) {
			sheet.autoSizeColumn(i, true);
		}
		try {
			// 获取一个输出流
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// 生成Excel
			wb.write(fileOut);
			// 关闭流
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/***
	 * 创建小区信息excel
	 * 
	 * @param cells
	 * @param filePath
	 */
	public void createCellInfoExcel(CellVo vo,String filePath) {
		Workbook wb =new SXSSFWorkbook(100);
		
//<<<<<<< .mine
//		if(index == 0){
//			wb = new XSSFWorkbook();
//			sheet = wb.createSheet("小区信息报表");
//			XSSFRow topRow = sheet.createRow(0);
//			// 标题样式
//			CellStyle topStyle = getNormalStyle(wb, "#00ccFF");
//			int count = 0;
//			// 创建标题
//			createTitle(topRow, topStyle, "城市名称", count ++);
//			createTitle(topRow, topStyle, "行政区域", count ++);
//			createTitle(topRow, topStyle, "区域id", count ++);
//			createTitle(topRow, topStyle, "小区cid", count ++);
//			createTitle(topRow, topStyle, "基站id", count ++);
//			createTitle(topRow, topStyle, "基站名称", count ++);
//			createTitle(topRow, topStyle, "小区名称", count ++);
//			createTitle(topRow, topStyle, "经度", count ++);
//			createTitle(topRow, topStyle, "纬度", count ++);
//			createTitle(topRow, topStyle, "psc/bcch", count ++);
//			createTitle(topRow, topStyle, "网络模式", count ++);
//			createTitle(topRow, topStyle, "室内外", count ++);
//			createTitle(topRow, topStyle, "天线类型", count ++);
//			createTitle(topRow, topStyle, "天线方位角", count ++);
//			createTitle(topRow, topStyle, "天线高度", count ++);
//			createTitle(topRow, topStyle, "天线电子倾角", count ++);
//			createTitle(topRow, topStyle, "天线机械倾角", count ++);
//			createTitle(topRow, topStyle, "共用天线", count ++);
//			createTitle(topRow, topStyle, "基站站址", count ++);
//			createTitle(topRow, topStyle, "海拔高度", count ++);
//			createTitle(topRow, topStyle, "设备厂商", count ++);
//			createTitle(topRow, topStyle, "塔桅类型", count ++);
//			createTitle(topRow, topStyle, "设备类型", count ++);
//			createTitle(topRow, topStyle, "基站配置", count ++);
//			createTitle(topRow, topStyle, "工程进展", count ++);
//			createTitle(topRow, topStyle, "覆盖范围", count ++);
//			createTitle(topRow, topStyle, "覆盖类型", count ++);
//			createTitle(topRow, topStyle, "是否rru小区", count ++);
//			createTitle(topRow, topStyle, "小区编号", count ++);
//			createTitle(topRow, topStyle, "直放站数量", count ++);
//			createTitle(topRow, topStyle, "共天馈小区", count ++);
//			createTitle(topRow, topStyle, "共天馈属性", count ++);
//			createTitle(topRow, topStyle, "共站名称", count ++);
//			createTitle(topRow, topStyle, "共站属性", count ++);
//			createTitle(topRow, topStyle, "网管运行状态", count ++);
//			createTitle(topRow, topStyle, "行政区域类型", count ++);
//			createTitle(topRow, topStyle, "上行频点", count ++);
//			createTitle(topRow, topStyle, "下行频点", count ++);
//			createTitle(topRow, topStyle, "rnc名称", count ++);
//			createTitle(topRow, topStyle, "网元类型", count ++);
//			createTitle(topRow, topStyle, "天线发射功率", count ++);
//			createTitle(topRow, topStyle, "下倾角", count ++);
//			createTitle(topRow, topStyle, "是否可以电调", count ++);
//			createTitle(topRow, topStyle, "基站类型", count ++);
//			createTitle(topRow, topStyle, "rru数量", count ++);
//			createTitle(topRow, topStyle, "逻辑小区级别", count ++);
//			createTitle(topRow, topStyle, "扇区配置的无线容量", count ++);
//			createTitle(topRow, topStyle, "e1数量", count ++);
//			createTitle(topRow, topStyle, "fe数量", count ++);
//			createTitle(topRow, topStyle, "传输方式", count ++);
//			createTitle(topRow, topStyle, "重庆区域覆盖", count ++);
//			createTitle(topRow, topStyle, "设计无线容量", count ++);
//			createTitle(topRow, topStyle, "基站最大发射功率", count ++);
//			createTitle(topRow, topStyle, "内置下倾角", count ++);
//			createTitle(topRow, topStyle, "退服时间", count ++);
//			createTitle(topRow, topStyle, "小区的最大下行发射功率", count ++);
//			createTitle(topRow, topStyle, "一级场景", count ++);
//			createTitle(topRow, topStyle, "二级场景", count ++);
//			createTitle(topRow, topStyle, "是否城区", count ++);
//			createTitle(topRow, topStyle, "是否支持hsdpa", count ++);
//			createTitle(topRow, topStyle, "hsdpa功能状态", count ++);
//			createTitle(topRow, topStyle, "hs-pdsch代码", count ++);
//			createTitle(topRow, topStyle, "小区中广播信道的功率", count ++);
//			createTitle(topRow, topStyle, "是否支持hsupa", count ++);
//			createTitle(topRow, topStyle, "hsupa功能状态", count ++);
//			createTitle(topRow, topStyle, "是否支持mbms", count ++);
//			createTitle(topRow, topStyle, "mbms功能状态", count ++);
//			createTitle(topRow, topStyle, "mich信道数", count ++);
//			createTitle(topRow, topStyle, "sf=16的码字数", count ++);
//			createTitle(topRow, topStyle, "sf=128的码字数", count ++);
//			createTitle(topRow, topStyle, "e-agch信道的初始数目", count ++);
//			createTitle(topRow, topStyle, "e-rgch/e-hich信道的初始数目", count ++);
//			createTitle(topRow, topStyle, "pcpich信道最大发射功率", count ++);
//			createTitle(topRow, topStyle, "pcpich信道最小发射功率", count ++);
//			createTitle(topRow, topStyle, "小区中使用的主导频信道的功率", count ++);
//			createTitle(topRow, topStyle, "小区中主同步信道的下行功率", count ++);
//			createTitle(topRow, topStyle, "单载频容纳最大用户数", count ++);
//			createTitle(topRow, topStyle, "小区配置的载频发射功率", count ++);
//			createTitle(topRow, topStyle, "配置的上行ce容量", count ++);
//			createTitle(topRow, topStyle, "配置的下行ce容量", count ++);
//			createTitle(topRow, topStyle, "iub接口atm端口的配置带宽", count ++);
//			createTitle(topRow, topStyle, "iub接口ip端口的配置带宽", count ++);
//			createTitle(topRow, topStyle, "iu接口atm层配置带宽", count ++);
//			createTitle(topRow, topStyle, "iu接口ip层配置带宽", count ++);
//			createTitle(topRow, topStyle, "iur接口atm层配置带宽", count ++);
//			createTitle(topRow, topStyle, "iur接口ip层配置带宽", count ++);
//			createTitle(topRow, topStyle, "备注", count ++);
//			createTitle(topRow, topStyle, "最后更新时间", count ++);
//			createTitle(topRow, topStyle, "rac", count ++);
//			createTitle(topRow, topStyle, "sac", count ++);
//			createTitle(topRow, topStyle, "rnc编号", count ++);
//			createTitle(topRow, topStyle, "基站编号", count ++);
//			createTitle(topRow, topStyle, "ura编号", count ++);
//			createTitle(topRow, topStyle, "是否安装塔顶放大器", count ++);
//			createTitle(topRow, topStyle, "扇区编号", count ++);
//			createTitle(topRow, topStyle, "入网时间", count ++);
//			createTitle(topRow, topStyle, "vip站", count ++);
//			createTitle(topRow, topStyle, "室内室外", count ++);
//			createTitle(topRow, topStyle, "bsc名称", count ++);
//			createTitle(topRow, topStyle, "省份", count ++);
//			createTitle(topRow, topStyle, "基站设备型号", count ++);
//			createTitle(topRow, topStyle, "机柜类型", count ++);
//			createTitle(topRow, topStyle, "载波类型", count ++);
//			createTitle(topRow, topStyle, "共用馈线", count ++);
//			createTitle(topRow, topStyle, "共用平台", count ++);
//			createTitle(topRow, topStyle, "小区频段", count ++);
//			createTitle(topRow, topStyle, "小区标识", count ++);
//			createTitle(topRow, topStyle, "小区bsic", count ++);
//			createTitle(topRow, topStyle, "tch频点", count ++);
//			createTitle(topRow, topStyle, "配置载频数", count ++);
//			createTitle(topRow, topStyle, "可用载频数", count ++);
//			createTitle(topRow, topStyle, "载频最大发射功率", count ++);
//			createTitle(topRow, topStyle, "配置控制信道数", count ++);
//			createTitle(topRow, topStyle, "配置业务信道数", count ++);
//			createTitle(topRow, topStyle, "sdcch可用数", count ++);
//			createTitle(topRow, topStyle, "天线俯仰角", count ++);
//			createTitle(topRow, topStyle, "跳频模式", count ++);
//			createTitle(topRow, topStyle, "是否开通半速率", count ++);
//			createTitle(topRow, topStyle, "gprs开通情况", count ++);
//			createTitle(topRow, topStyle, "edge开通情况", count ++);
//			createTitle(topRow, topStyle, "增强全速率开通情况", count ++);
//			createTitle(topRow, topStyle, "网络制式", count ++);
//			/*createTitle(topRow, topStyle, "3G邻区1", count ++);
//			createTitle(topRow, topStyle, "3G邻区2", count ++);
//			createTitle(topRow, topStyle, "3G邻区3", count ++);
//			createTitle(topRow, topStyle, "3G邻区4", count ++);
//			createTitle(topRow, topStyle, "3G邻区5", count ++);
//			createTitle(topRow, topStyle, "3G邻区6", count ++);
//			createTitle(topRow, topStyle, "3G邻区7", count ++);
//			createTitle(topRow, topStyle, "3G邻区8", count ++);
//			createTitle(topRow, topStyle, "3G邻区9", count ++);
//			createTitle(topRow, topStyle, "3G邻区10", count ++);
//			createTitle(topRow, topStyle, "3G邻区11", count ++);
//			createTitle(topRow, topStyle, "3G邻区12", count ++);
//			createTitle(topRow, topStyle, "3G邻区13", count ++);
//			createTitle(topRow, topStyle, "3G邻区14", count ++);
//			createTitle(topRow, topStyle, "3G邻区15", count ++);
//			createTitle(topRow, topStyle, "3G邻区16", count ++);
//			createTitle(topRow, topStyle, "3G邻区17", count ++);
//			createTitle(topRow, topStyle, "3G邻区18", count ++);
//			createTitle(topRow, topStyle, "3G邻区19", count ++);
//			createTitle(topRow, topStyle, "3G邻区20", count ++);
//			createTitle(topRow, topStyle, "3G邻区21", count ++);
//			createTitle(topRow, topStyle, "3G邻区22", count ++);
//			createTitle(topRow, topStyle, "3G邻区23", count ++);
//			createTitle(topRow, topStyle, "3G邻区24", count ++);
//			createTitle(topRow, topStyle, "3G邻区25", count ++);
//			createTitle(topRow, topStyle, "3G邻区26", count ++);
//			createTitle(topRow, topStyle, "3G邻区27", count ++);
//			createTitle(topRow, topStyle, "3G邻区28", count ++);
//			createTitle(topRow, topStyle, "3G邻区29", count ++);
//			createTitle(topRow, topStyle, "3G邻区30", count ++);
//			createTitle(topRow, topStyle, "3G邻区31", count ++);
//			createTitle(topRow, topStyle, "3G邻区32", count ++);
//			createTitle(topRow, topStyle, "3G邻区33", count ++);
//			createTitle(topRow, topStyle, "3G邻区34", count ++);
//			createTitle(topRow, topStyle, "3G邻区35", count ++);
//			createTitle(topRow, topStyle, "3G邻区36", count ++);
//			createTitle(topRow, topStyle, "3G邻区37", count ++);
//			createTitle(topRow, topStyle, "3G邻区38", count ++);
//			createTitle(topRow, topStyle, "3G邻区39", count ++);
//			createTitle(topRow, topStyle, "3G邻区40", count ++);
//			createTitle(topRow, topStyle, "3G邻区41", count ++);
//			createTitle(topRow, topStyle, "3G邻区42", count ++);
//			createTitle(topRow, topStyle, "3G邻区43", count ++);
//			createTitle(topRow, topStyle, "3G邻区44", count ++);
//			createTitle(topRow, topStyle, "3G邻区45", count ++);
//			createTitle(topRow, topStyle, "3G邻区46", count ++);
//			createTitle(topRow, topStyle, "3G邻区47", count ++);
//			createTitle(topRow, topStyle, "3G邻区48", count ++);
//			createTitle(topRow, topStyle, "3G邻区49", count ++);
//			createTitle(topRow, topStyle, "3G邻区50", count ++);
//			createTitle(topRow, topStyle, "3G邻区51", count ++);
//			createTitle(topRow, topStyle, "3G邻区52", count ++);
//			createTitle(topRow, topStyle, "3G邻区53", count ++);
//			createTitle(topRow, topStyle, "3G邻区54", count ++);
//			createTitle(topRow, topStyle, "3G邻区55", count ++);
//			createTitle(topRow, topStyle, "3G邻区56", count ++);
//			createTitle(topRow, topStyle, "3G邻区57", count ++);
//			createTitle(topRow, topStyle, "3G邻区58", count ++);
//			createTitle(topRow, topStyle, "3G邻区59", count ++);
//			createTitle(topRow, topStyle, "3G邻区60", count ++);
//			createTitle(topRow, topStyle, "3G邻区61", count ++);
//			createTitle(topRow, topStyle, "3G邻区62", count ++);
//			createTitle(topRow, topStyle, "3G邻区63", count ++);
//			createTitle(topRow, topStyle, "3G邻区64", count ++);
//			createTitle(topRow, topStyle, "2G邻区1", count ++);
//			createTitle(topRow, topStyle, "2G邻区2", count ++);
//			createTitle(topRow, topStyle, "2G邻区3", count ++);
//			createTitle(topRow, topStyle, "2G邻区4", count ++);
//			createTitle(topRow, topStyle, "2G邻区5", count ++);
//			createTitle(topRow, topStyle, "2G邻区6", count ++);
//			createTitle(topRow, topStyle, "2G邻区7", count ++);
//			createTitle(topRow, topStyle, "2G邻区8", count ++);
//			createTitle(topRow, topStyle, "2G邻区9", count ++);
//			createTitle(topRow, topStyle, "2G邻区10", count ++);
//			createTitle(topRow, topStyle, "2G邻区11", count ++);
//			createTitle(topRow, topStyle, "2G邻区12", count ++);
//			createTitle(topRow, topStyle, "2G邻区13", count ++);
//			createTitle(topRow, topStyle, "2G邻区14", count ++);
//			createTitle(topRow, topStyle, "2G邻区15", count ++);
//			createTitle(topRow, topStyle, "2G邻区16", count ++);
//			createTitle(topRow, topStyle, "2G邻区17", count ++);
//			createTitle(topRow, topStyle, "2G邻区18", count ++);
//			createTitle(topRow, topStyle, "2G邻区19", count ++);
//			createTitle(topRow, topStyle, "2G邻区20", count ++);
//			createTitle(topRow, topStyle, "2G邻区21", count ++);
//			createTitle(topRow, topStyle, "2G邻区22", count ++);
//			createTitle(topRow, topStyle, "2G邻区23", count ++);
//			createTitle(topRow, topStyle, "2G邻区24", count ++);
//			createTitle(topRow, topStyle, "2G邻区25", count ++);
//			createTitle(topRow, topStyle, "2G邻区26", count ++);
//			createTitle(topRow, topStyle, "2G邻区27", count ++);
//			createTitle(topRow, topStyle, "2G邻区28", count ++);
//			createTitle(topRow, topStyle, "2G邻区29", count ++);
//			createTitle(topRow, topStyle, "2G邻区30", count ++);
//			createTitle(topRow, topStyle, "2G邻区31", count ++);
//			createTitle(topRow, topStyle, "2G邻区32", count ++);*/
//		}else{
//			Long d1=new Date().getTime();
//			FileInputStream fis = null;
//			try {
//				fis = new FileInputStream(filePath);
//			} catch (FileNotFoundException e) {
//				logger.error("Gis download when get Excel",e);
//=======
		
		// 标题样式
		CellStyle topStyle = getNormalStyle(wb, "#00ccFF");
		Sheet sheet = null;
		List<GwCasCell> cells = null;
		//type 0 框选导出 1小区导出 2 邻区导出
		int index =0;//每次开始写入行数
		int num = baseStationService.getPage(vo);
		int nowR =0;
		if(vo.getReport_type() == 1){
			for(int i = 0;i<num;i++){
				if(nowR == 0){//当前行数为0就创建新页并创建titl
					sheet = wb.createSheet("小区信息报表"+(index/Constant.SHEET_ROW==0?1:index/Constant.SHEET_ROW+1));
					Row topRow =  sheet.createRow(0);
					getTitle(topRow,topStyle ,sheet);
					for (int j = 0; j < 122; j++) {
						sheet.autoSizeColumn(j, true);
					}
					nowR++;
				}
				cells = new ArrayList<GwCasCell>();
				cells = baseStationService.getRegiondown(vo,filePath,i);
				
				for(int j = nowR; j < cells.size()+nowR; j++){
					setValue(j,cells.get(j-nowR),sheet);
				}
				
				index =(i+1)*Constant.READ_ROW;
				nowR += Constant.READ_ROW;
				
				if((nowR-1)%Constant.SHEET_ROW ==0 && i !=0){
					nowR =0;
				}
			}
		}else if(vo.getReport_type() == 0){
			for(int i = 0;i<num;i++){
				if(nowR == 0){//当前行数为0就创建新页并创建titl
					sheet = wb.createSheet("小区信息报表"+(index/Constant.SHEET_ROW==0?1:index/Constant.SHEET_ROW+1));
					Row topRow =  sheet.createRow(0);
					getTitle(topRow,topStyle ,sheet);
					for (int j = 0; j < 122; j++) {
						sheet.autoSizeColumn(j, true);
					}
					nowR++;
				}
				cells = new ArrayList<GwCasCell>();
				cells = baseStationService.getRegiondownCell(vo,filePath ,i);

				for(int j = nowR; j < cells.size()+nowR; j++){
					setValue(j,cells.get(j-nowR),sheet);
				}
				
				index =(i+1)*Constant.READ_ROW;
				nowR += Constant.READ_ROW;
				
				if((nowR-1)%Constant.SHEET_ROW ==0 && i !=0){
					nowR =0;
				}
			}
			//System.out.println(new Date().getTime()-d1+"==========="+cells.size());
		}else if(vo.getReport_type() == 2){
			sheet = wb.createSheet("小区信息报表");
			Row topRow =  sheet.createRow(0);
			getTitle(topRow,topStyle ,sheet);
			for (int j = 0; j < 122; j++) {
				sheet.autoSizeColumn(j, true);
			}
			cells = baseStationService.getReportNearCell(vo);
			for(int j = index; j < cells.size()+index; j++){
				GwCasCell gw = cells.get(j-index);
				setValue(j+1,cells.get(j-index),sheet);
				
				if(gw.getColor_type() != null && (gw.getColor_type().equals("0")||gw.getColor_type().equals("1")||
						gw.getColor_type().equals("2")||gw.getColor_type().equals("3"))){
					XSSFCellStyle style = ExcelUtil.setBackColorByCustom(wb, gw.getColor());
					Row row = sheet.getRow(j+1);
					Cell cell = null;
					for(int k = 0;k < 122;k ++){
						cell = row.getCell(k);
						cell.setCellStyle(style);
					}
				}
			}
		}
		
		
		//System.out.println(sheet+"=========");
		
		try {
			// 获取一个输出流
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// 生成Excel
			wb.write(fileOut);
			// 关闭流
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 创建标题
	 */
	private void createTitle(Row topRow, CellStyle topStyle, String value,
			int index) {
		Cell cell = topRow.createCell(index);
		cell.setCellValue(value);
		cell.setCellStyle(topStyle);
	}
	/**
	 *  单元格赋值
	 * @param rownum
	 * @param data
	 * @param sheet
	 */
	private void setValue(int rownum,GwCasCell data,Sheet sheet){
		Row row = sheet.createRow(rownum);
		int count = 0;
		//城市名称
		Cell cell1 = row.createCell(count++);
		cell1.setCellValue(data.getCityName()==null?"":data.getCityName());
		//行政区域
		Cell cell2 = row.createCell(count++);
		cell2.setCellValue(data.getAdminRegion()==null?"":data.getAdminRegion());
		//区域id
		Cell cell3 = row.createCell(count++);
		cell3.setCellValue(data.getAreaId()==null?"":data.getAreaId().toString());
		//小区cid
		Cell cell4 = row.createCell(count++);
		cell4.setCellValue(data.getCid()==null?"":data.getCid().toString());
		//基站id
		Cell cell5 = row.createCell(count++);
		cell5.setCellValue(data.getBid()==null?"":data.getBid().toString());
		//基站名称
		Cell cell6 = row.createCell(count++);
		cell6.setCellValue(data.getBaseName()==null?"":data.getBaseName());
		//小区名称
		Cell cell7 = row.createCell(count++);
		cell7.setCellValue(data.getCellName()==null?"":data.getCellName());
		//经度
		Cell cell8 = row.createCell(count++);
		cell8.setCellValue(data.getLongitude()==null?"":data.getLongitude().toString());
		//纬度
		Cell cell9 = row.createCell(count++);
		cell9.setCellValue(data.getLatitude()==null?"":data.getLatitude().toString());
		//扰码psc
		Cell cell10 = row.createCell(count++);
		cell10.setCellValue(data.getPsc().toString()==null?"":data.getPsc().toString());
		//网络模式
		Cell cell11 = row.createCell(count++);
		cell11.setCellValue(data.getModeType()==(short)0?"3G":"2G");
		//室内外
		Cell cell12 = row.createCell(count++);
		cell12.setCellValue(data.getIndoor()==(short)0?"室外":"室内");
		//天线类型
		Cell cell13 = row.createCell(count++);
		cell13.setCellValue(data.getAntType()==null?"":data.getAntType());
		//天线方位角
		Cell cell14 = row.createCell(count++);
		cell14.setCellValue(data.getAntAzimuth()==null?"":data.getAntAzimuth().toString());
		//天线高度
		Cell cell15 = row.createCell(count++);
		cell15.setCellValue(data.getAntHigh()==null?"":data.getAntHigh().toString());
		//天线电子倾角
		Cell cell16 = row.createCell(count++);
		cell16.setCellValue(data.getAntElectAngle()==null?"":data.getAntElectAngle().toString());
		//天线机械倾角
		Cell cell17 = row.createCell(count++);
		cell17.setCellValue(data.getAntMachAngle()==null?"":data.getAntMachAngle().toString());
		//共用天线
		Cell cell18 = row.createCell(count++);
		cell18.setCellValue(data.getSharedAnt()==null?"":data.getSharedAnt());
		//基站站址
		Cell cell19 = row.createCell(count++);
		cell19.setCellValue(data.getBaseAddress()==null?"":data.getBaseAddress());
		//海拔高度
		Cell cell20 = row.createCell(count++);
		cell20.setCellValue(data.getAltitude()==null?"":data.getAltitude().toString());
		//设备厂商
		Cell cell21 = row.createCell(count++);
		cell21.setCellValue(data.getDevVendor()==null?"":data.getDevVendor());
		//塔桅类型
		Cell cell22 = row.createCell(count++);
		cell22.setCellValue(data.getTowerMastType()==null?"":data.getTowerMastType());
		//设备类型
		Cell cell23 = row.createCell(count++);
		cell23.setCellValue(data.getDevType()==null?"":data.getDevType());
		//基站配置
		Cell cell24 = row.createCell(count++);
		cell24.setCellValue(data.getBaseConf()==null?"":data.getBaseConf());
		//工程进展
		Cell cell25 = row.createCell(count++);
		cell25.setCellValue(data.getProgress()==null?"":data.getProgress());
		//覆盖范围
		Cell cell26 = row.createCell(count++);
		cell26.setCellValue(data.getCoverRange()==null?"":data.getCoverRange());
		//覆盖类型
		Cell cell27 = row.createCell(count++);
		cell27.setCellValue(data.getCoverType()==null?"":data.getCoverType());
		//是否rru小区
		Cell cell28 = row.createCell(count++);
		cell28.setCellValue(data.getRruCellBool()==null?"":data.getRruCellBool());
		//小区编号
		Cell cell29 = row.createCell(count++);
		cell29.setCellValue(data.getCellId()==null?"":data.getCellId().toString());
		//直放站数量
		Cell cell30 = row.createCell(count++);
		cell30.setCellValue(data.getRepeaterCnt()==null?"":data.getRepeaterCnt().toString());
		//共天馈小区
		Cell cell31 = row.createCell(count++);
		cell31.setCellValue(data.getSharedAntCell()==null?"":data.getSharedAntCell());
		//共天馈属性
		Cell cell32 = row.createCell(count++);
		cell32.setCellValue(data.getSharedAntProp()==null?"":data.getSharedAntProp());
		//共站名称
		Cell cell33 = row.createCell(count++);
		cell33.setCellValue(data.getSharedBaseName()==null?"":data.getSharedBaseName());
		//共站属性
		Cell cell34 = row.createCell(count++);
		cell34.setCellValue(data.getSharedBaseProp()==null?"":data.getSharedBaseProp());
		//网管运行状态
		Cell cell35 = row.createCell(count++);
		cell35.setCellValue(data.getNetmanStat()==null?"":data.getNetmanStat());
		//行政区域类型
		Cell cell36 = row.createCell(count++);
		cell36.setCellValue(data.getAdminRegionType()==null?"":data.getAdminRegionType());
		//上行频点
		Cell cell37 = row.createCell(count++);
		cell37.setCellValue(data.getUpFreq()==null?"":String.valueOf(data.getUpFreq()));
		//下行频点
		Cell cell38 = row.createCell(count++);
		cell38.setCellValue(data.getDownFreq()==null?"":String.valueOf(data.getDownFreq()));
		//rnc名称
		Cell cell39 = row.createCell(count++);
		cell39.setCellValue(data.getRncName()==null?"":data.getRncName());
		//网元类型
		Cell cell40 = row.createCell(count++);
		cell40.setCellValue(data.getNeType()==null?"":data.getNeType());
		//天线发射功率
		Cell cell41 = row.createCell(count++);
		cell41.setCellValue(data.getAntPower()==null?"":data.getAntPower().toString());
		//下倾角
		Cell cell42 = row.createCell(count++);
		cell42.setCellValue(data.getTilt()==null?"":data.getTilt().toString());
		//是否可以电调
		Cell cell43 = row.createCell(count++);
		cell43.setCellValue(data.getEscBool()==null?"":data.getEscBool());
		//基站类型
		Cell cell44 = row.createCell(count++);
		cell44.setCellValue(data.getBaseType()==null?"":data.getBaseType());
		//rru数量
		Cell cell45 = row.createCell(count++);
		cell45.setCellValue(data.getRruCnt()==null?"":data.getRruCnt().toString());
		//逻辑小区级别
		Cell cell46 = row.createCell(count++);
		cell46.setCellValue(data.getLogicCellLevel()==null?"":data.getLogicCellLevel());
		//扇区配置的无线容量
		Cell cell47 = row.createCell(count++);
		cell47.setCellValue(data.getSectorWifiCapac()==null?"":data.getSectorWifiCapac().toString());
		//e1数量
		Cell cell48 = row.createCell(count++);
		cell48.setCellValue(data.getE1Cnt()==null?"":data.getE1Cnt().toString());
		//fe数量
		Cell cell49 = row.createCell(count++);
		cell49.setCellValue(data.getFeCnt()==null?"":data.getFeCnt().toString());
		//传输方式
		Cell cell50 = row.createCell(count++);
		cell50.setCellValue(data.getTransmission()==null?"":data.getTransmission());
		//重庆区域覆盖
		Cell cell51 = row.createCell(count++);
		cell51.setCellValue(data.getCqAreaCover()==null?"":data.getCqAreaCover());
		//设计无线容量
		Cell cell52 = row.createCell(count++);
		cell52.setCellValue(data.getWifiCapac()==null?"":data.getWifiCapac().toString());
		//基站最大发射功率
		Cell cell53 = row.createCell(count++);
		cell53.setCellValue(data.getBaseMaxPower()==null?"":data.getBaseMaxPower().toString());
		//内置下倾角
		Cell cell54 = row.createCell(count++);
		cell54.setCellValue(data.getInnerTilt()==null?"":data.getInnerTilt().toString());
		//退服时间
		Cell cell55 = row.createCell(count++);
		cell55.setCellValue(data.getOffServiceTime()==null?"":this.getTime(data.getOffServiceTime()));
		//小区的最大下行发射功率
		Cell cell56 = row.createCell(count++);
		cell56.setCellValue(data.getDownMaxPower()==null?"":data.getDownMaxPower().toString());
		//一级场景
		Cell cell57 = row.createCell(count++);
		cell57.setCellValue(data.getSceneA()==null?"":data.getSceneA());
		//二级场景
		Cell cell58 = row.createCell(count++);
		cell58.setCellValue(data.getSceneB()==null?"":data.getSceneB());
		//是否城区
		Cell cell59 = row.createCell(count++);
		cell59.setCellValue(data.getTownBool()==null?"":data.getTownBool());
		//是否支持hsdpa
		Cell cell60 = row.createCell(count++);
		cell60.setCellValue(data.getHsdpaBool()==null?"":data.getHsdpaBool());
		//hsdpa功能状态
		Cell cell61 = row.createCell(count++);
		cell61.setCellValue(data.getHsdpaStat()==null?"":data.getHsdpaStat());
		//hs-pdsch代码
		Cell cell62 = row.createCell(count++);
		cell62.setCellValue(data.getHsPdschCode()==null?"":data.getHsPdschCode());
		//小区中广播信道的功率
		Cell cell63 = row.createCell(count++);
		cell63.setCellValue(data.getBroadcastChannPower()==null?"":data.getBroadcastChannPower().toString());
		//是否支持hsupa
		Cell cell64 = row.createCell(count++);
		cell64.setCellValue(data.getHsupaBool()==null?"":data.getHsupaBool());
		//hsupa功能状态
		Cell cell65 = row.createCell(count++);
		cell65.setCellValue(data.getHsupaStat()==null?"":data.getHsupaStat());
		//是否支持mbms
		Cell cell66 = row.createCell(count++);
		cell66.setCellValue(data.getMbmsBool()==null?"":data.getMbmsBool());
		//mbms功能状态
		Cell cell67 = row.createCell(count++);
		cell67.setCellValue(data.getMbmsStat()==null?"":data.getMbmsStat());
		//mich信道数
		Cell cell68 = row.createCell(count++);
		cell68.setCellValue(data.getMichChannCnt()==null?"":data.getMichChannCnt().toString());
		//sf=16的码字数
		Cell cell69 = row.createCell(count++);
		cell69.setCellValue(data.getSf16CodeCnt()==null?"":data.getSf16CodeCnt().toString());
		//sf=128的码字数
		Cell cell70= row.createCell(count++);
		cell70.setCellValue(data.getSf128CodeCnt()==null?"":data.getSf128CodeCnt().toString());
		//e-agch信道的初始数目
		Cell cell71= row.createCell(count++);
		cell71.setCellValue(data.geteAgchChannCnt()==null?"":data.geteAgchChannCnt().toString());
		//e-rgch/e-hich信道的初始数目
		Cell cell72= row.createCell(count++);
		cell72.setCellValue(data.geteRgchEHichChannCnt()==null?"":data.geteRgchEHichChannCnt().toString());
		//pcpich信道最大发射功率
		Cell cell73= row.createCell(count++);
		cell73.setCellValue(data.getPcpichChannMaxPower()==null?"":data.getPcpichChannMaxPower().toString());
		//pcpich信道最小发射功率
		Cell cell74= row.createCell(count++);
		cell74.setCellValue(data.getPcpichChannMinPower()==null?"":data.getPcpichChannMinPower().toString());
		//小区中使用的主导频信道的功率
		Cell cell75= row.createCell(count++);
		cell75.setCellValue(data.getDominFreqChannPower()==null?"":data.getDominFreqChannPower().toString());
		//小区中主同步信道的下行功率
		Cell cell76= row.createCell(count++);
		cell76.setCellValue(data.getSyncChannDownPower()==null?"":data.getSyncChannDownPower().toString());
		//单载频容纳最大用户数
		Cell cell77= row.createCell(count++);
		cell77.setCellValue(data.getSingleCarrFreqMaxUser()==null?"":data.getSingleCarrFreqMaxUser().toString());
		//小区配置的载频发射功率
		Cell cell78= row.createCell(count++);
		cell78.setCellValue(data.getCarrFreqMaxPower()==null?"":data.getCarrFreqMaxPower().toString());
		//配置的上行ce容量
		Cell cell79= row.createCell(count++);
		cell79.setCellValue(data.getUpCeCapac()==null?"":data.getUpCeCapac().toString());
		//配置的下行ce容量
		Cell cell80= row.createCell(count++);
		cell80.setCellValue(data.getDownCeCapac()==null?"":data.getDownCeCapac().toString());
		//iub接口atm端口的配置带宽
		Cell cell81= row.createCell(count++);
		cell81.setCellValue(data.getIubAtmBandwidth()==null?"":data.getIubAtmBandwidth().toString());
		//iub接口ip端口的配置带宽
		Cell cell82= row.createCell(count++);
		cell82.setCellValue(data.getIubIpBandwidth()==null?"":data.getIubIpBandwidth().toString());
		//iu接口atm层配置带宽
		Cell cell83= row.createCell(count++);
		cell83.setCellValue(data.getIuAtmBandwidth()==null?"":data.getIuAtmBandwidth().toString());
		//iu接口ip层配置带宽
		Cell cell84= row.createCell(count++);
		cell84.setCellValue(data.getIuIpBandwidth()==null?"":data.getIuIpBandwidth().toString());
		//iur接口atm层配置带宽
		Cell cell85= row.createCell(count++);
		cell85.setCellValue(data.getIurAtmBandwidth()==null?"":data.getIurAtmBandwidth().toString());
		//iur接口ip层配置带宽
		Cell cell86= row.createCell(count++);
		cell86.setCellValue(data.getIurIpBandwidth()==null?"":data.getIurIpBandwidth().toString());
		//备注
		Cell cell87= row.createCell(count++);
		cell87.setCellValue(data.getRemark()==null?"":data.getRemark());
		//最后更新时间
		Cell cell88= row.createCell(count++);
		cell88.setCellValue(data.getLastUpdateTime()==null?"":this.getTime(data.getLastUpdateTime()));
		//rac
		Cell cell89= row.createCell(count++);
		cell89.setCellValue(data.getRac()==null?"":data.getRac().toString());
		//sac
		Cell cell90= row.createCell(count++);
		cell90.setCellValue(data.getSac()==null?"":data.getSac().toString());
		//rnc编号
		Cell cell91= row.createCell(count++);
		cell91.setCellValue(data.getRncId()==null?"":data.getRncId().toString());
		//基站编号
		Cell cell92= row.createCell(count++);
		cell92.setCellValue(data.getBaseId()==null?"":data.getBaseId().toString());
		//ura编号
		Cell cell93= row.createCell(count++);
		cell93.setCellValue(data.getUraId()==null?"":data.getUraId().toString());
		//是否安装塔顶放大器
		Cell cell94= row.createCell(count++);
		cell94.setCellValue(data.getTowerAmpliBool()==null?"":data.getTowerAmpliBool());
		//扇区编号
		Cell cell95= row.createCell(count++);
		cell95.setCellValue(data.getSectorId()==null?"":data.getSectorId().toString());
		//入网时间
		Cell cell96= row.createCell(count++);
		cell96.setCellValue(data.getNetworkTime()==null?"":this.getTime(data.getNetworkTime()));
		//vip站
		Cell cell97= row.createCell(count++);
		cell97.setCellValue(data.getVipBase()==null?"":data.getVipBase());
		//室内室外
		Cell cell98= row.createCell(count++);
		cell98.setCellValue(data.getInside()==null?"":data.getInside());
		//bsc名称
		Cell cell99= row.createCell(count++);
		cell99.setCellValue(data.getBscName()==null?"":data.getBscName());
		//省份
		Cell cell100= row.createCell(count++);
		cell100.setCellValue(data.getProvince()==null?"":data.getProvince());
		//基站设备型号
		Cell cell101= row.createCell(count++);
		cell101.setCellValue(data.getDevModel()==null?"":data.getDevModel());
		//机柜类型
		Cell cell102= row.createCell(count++);
		cell102.setCellValue(data.getCabinetType()==null?"":data.getCabinetType());
		//载波类型
		Cell cell103= row.createCell(count++);
		cell103.setCellValue(data.getCarrierType()==null?"":data.getCarrierType());
		//共用馈线
		Cell cell104= row.createCell(count++);
		cell104.setCellValue(data.getSharedFeeder()==null?"":data.getSharedFeeder());
		//共用平台
		Cell cell105= row.createCell(count++);
		cell105.setCellValue(data.getSharedPlatform()==null?"":data.getSharedPlatform());
		//小区频段
		Cell cell106= row.createCell(count++);
		cell106.setCellValue(data.getCellBand()==null?"":data.getCellBand().toString());
		//小区标识
		Cell cell107= row.createCell(count++);
		cell107.setCellValue(data.getCellMark()==null?"":data.getCellMark());
		//小区bsic
		Cell cell108= row.createCell(count++);
		cell108.setCellValue(data.getBsic()==null?"":data.getBsic().toString());
		//tch频点
		Cell cell109= row.createCell(count++);
		cell109.setCellValue(data.getTchFreq()==null?"":data.getTchFreq());
		//配置载频数
		Cell cell110= row.createCell(count++);
		cell110.setCellValue(data.getCarrFreqCnt()==null?"":data.getCarrFreqCnt().toString());
		//可用载频数
		Cell cell111= row.createCell(count++);
		cell111.setCellValue(data.getCarrFreqAvailCnt()==null?"":data.getCarrFreqAvailCnt().toString());
		//载频最大发射功率
		Cell cell112= row.createCell(count++);
		cell112.setCellValue(data.getCarrFreqMaxPower()==null?"":data.getCarrFreqMaxPower().toString());
		//配置控制信道数
		Cell cell113= row.createCell(count++);
		cell113.setCellValue(data.getCtrlChannCnt()==null?"":data.getCtrlChannCnt().toString());
		//配置业务信道数
		Cell cell114= row.createCell(count++);
		cell114.setCellValue(data.getBusiChannCnt()==null?"":data.getBusiChannCnt().toString());
		//sdcch可用数
		Cell cell115= row.createCell(count++);
		cell115.setCellValue(data.getSdcchAvailCnt()==null?"":data.getSdcchAvailCnt().toString());
		//天线俯仰角
		Cell cell116= row.createCell(count++);
		cell116.setCellValue(data.getAntElevAngle()==null?"":data.getAntElevAngle().toString());
		//跳频模式
		Cell cell117= row.createCell(count++);
		cell117.setCellValue(data.getHoppingPattern()==null?"":data.getHoppingPattern());
		//是否开通半速率
		Cell cell118= row.createCell(count++);
		cell118.setCellValue(data.getHalfRateBool()==null?"":data.getHalfRateBool());
		//gprs开通情况
		Cell cell119= row.createCell(count++);
		cell119.setCellValue(data.getGprsBool()==null?"":data.getGprsBool());
		//edge开通情况
		Cell cell120= row.createCell(count++);
		cell120.setCellValue(data.getEdgeBool()==null?"":data.getEdgeBool());
		//增强全速率开通情况
		Cell cell121= row.createCell(count++);
		cell121.setCellValue(data.getEnfullRateBool()==null?"":data.getEnfullRateBool());
		//网络制式
		Cell cell122= row.createCell(count++);
		cell122.setCellValue(data.getModeType()==(short)0?"3G":"2G");
		//3G邻区1-64
		//2G邻区1-32
	}
	
	
	public void createReportLoadCell(List<ReportCells>  cells, String filePath ){
		Workbook wb =new SXSSFWorkbook(100);;
		Sheet sheet = wb.createSheet("加载小区邻区信息报表");
		Row topRow = sheet.createRow(0);
		// 标题样式
		CellStyle topStyle = getNormalStyle(wb, "#00ccFF");
		int count = 0;
		// 创建标题
		createTitle(topRow, topStyle, "Lac", count++);
		createTitle(topRow, topStyle, "Cid", count++);
		createTitle(topRow, topStyle, "区域", count++);
		createTitle(topRow, topStyle, "小区名称", count++);
		createTitle(topRow, topStyle, "PSC/BCCH", count++);
		createTitle(topRow, topStyle, "BSC(2G)", count++);
		createTitle(topRow, topStyle, "BSIC(2G)", count++);
		createTitle(topRow, topStyle, "天线方向角", count++);
		createTitle(topRow, topStyle, "电子倾角", count++);
		createTitle(topRow, topStyle, "经度", count++);
		createTitle(topRow, topStyle, "纬度", count++);
		createTitle(topRow, topStyle, "上行频点", count++);
		createTitle(topRow, topStyle, "下行频点", count++);
		createTitle(topRow, topStyle, "小区频段", count++);
		createTitle(topRow, topStyle, "天线共用情况", count++);
		for(int i = count;i < 96 + count; i++ ){
			createTitle(topRow, topStyle, "邻区", i);
		}
		int cellnum = 96 + count;
		for (int i = 0; i < cellnum; i++) {
			sheet.autoSizeColumn(i, true);
		}
		//设置三种填充excel单元格背景颜色style
		XSSFCellStyle styleLay1 = (XSSFCellStyle)wb.createCellStyle();
		XSSFCellStyle styleLay2=(XSSFCellStyle)wb.createCellStyle();
		XSSFCellStyle styleLay3=(XSSFCellStyle)wb.createCellStyle();
		styleLay1 = ExcelUtil.setBackColorByCustom(wb, "#00c321");
		styleLay2 = ExcelUtil.setBackColorByCustom(wb, "#1499da");
		styleLay3 = ExcelUtil.setBackColorByCustom(wb, "#ffa200");
//		styleLay1.setBorderTop(CellStyle.BORDER_THIN);
//		styleLay1.setBorderBottom(CellStyle.BORDER_THIN);
//		styleLay1.setBorderLeft(CellStyle.BORDER_THIN);
//		styleLay1.setBorderRight(CellStyle.BORDER_THIN);
//		styleLay2.setBorderTop(CellStyle.BORDER_THIN);
//		styleLay2.setBorderBottom(CellStyle.BORDER_THIN);
//		styleLay2.setBorderLeft(CellStyle.BORDER_THIN);
//		styleLay2.setBorderRight(CellStyle.BORDER_THIN);
//		styleLay3.setBorderTop(CellStyle.BORDER_THIN);
//		styleLay3.setBorderBottom(CellStyle.BORDER_THIN);
//		styleLay3.setBorderLeft(CellStyle.BORDER_THIN);
//		styleLay3.setBorderRight(CellStyle.BORDER_THIN);
		//循环填充每行数据
		for(int i = 0; i < cells.size(); i ++){
			ReportCells gw = cells.get(i);
			Row row = sheet.createRow(i+1);
			//调用赋值方法
			setCellRelValue(row,gw,styleLay1,styleLay2,styleLay3,cellnum);
		}
		try {
			// 获取一个输出流
			FileOutputStream fileOut = new FileOutputStream(filePath);
			// 生成Excel
			wb.write(fileOut);
			// 关闭流
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setNearValue(String value,Row row,int count,CellStyle styleLay1,CellStyle styleLay2,CellStyle styleLay3){
		String[] temp = null;
		Cell cell = row.getCell(count);
		if(cell == null){
			cell = row.createCell(count);
		}
		if(value != null && !value.equals("")){
			temp = value.split("_");
			cell.setCellValue(temp[0] + "_" + temp[1]);
			if(temp[2].equals("0")){
				cell.setCellStyle(styleLay1);
			}else if(temp[2].equals("1")){
				cell.setCellStyle(styleLay2);
			}else if(temp[2].equals("2")){
				cell.setCellStyle(styleLay3);
			}
		}else{
			cell.setCellValue("");
		}
	}
	private void setCellRelValue(Row row,ReportCells rc,CellStyle styleLay1,CellStyle styleLay2,CellStyle styleLay3,int cellnum){
		int count = 0;
		//先创建列
		for(int i = 0; i < cellnum; i++){
			 row.createCell(i);
		}
		//lac
		Cell cell1 = row.getCell(count++);
		cell1.setCellValue(rc.getLac());
		//cid
		Cell cell2 = row.getCell(count++);
		cell2.setCellValue(rc.getCid());
		//区域
		Cell cell3 = row.getCell(count++);
		cell3.setCellValue(rc.getAreaname()==null?"":rc.getAreaname());
		//小区名称
		Cell cell4 = row.getCell(count++);
		cell4.setCellValue(rc.getCellname()==null?"":rc.getCellname());
		//PSC/BCCH
		Cell cell5 = row.getCell(count++);
		cell5.setCellValue(rc.getPsc() == null?"":rc.getPsc().toString());
		//BSC名称
		Cell cell6 = row.getCell(count++);
		cell6.setCellValue(rc.getBscname() == null?"":rc.getBscname());
		//BSIC名称
		Cell cell7 = row.getCell(count++);
		cell7.setCellValue(rc.getBsic() == null?"":rc.getBsic().toString());
		//天线方向角
		Cell cell8 = row.getCell(count++);
		cell8.setCellValue(rc.getAnt_azimuth() == null?"":rc.getAnt_azimuth().toString());
		//电子倾角
		Cell cell9 = row.getCell(count++);
		cell9.setCellValue(rc.getAnt_elect_angle() == null?"":rc.getAnt_elect_angle().toString());
		//经度
		Cell cell10 = row.getCell(count++);
		cell10.setCellValue(rc.getLongitude() == null?"":rc.getLongitude().toString());
		//纬度
		Cell cell11 = row.getCell(count++);
		cell11.setCellValue(rc.getLatitude() == null?"":rc.getLatitude().toString());
		//上行频点
		Cell cell12 = row.getCell(count++);
		cell12.setCellValue(rc.getUpfreq() == null?"":rc.getUpfreq().toString());
		//下行频点
		Cell cell13 = row.getCell(count++);
		cell13.setCellValue(rc.getDownfreq() == null?"":rc.getDownfreq().toString());
		//小区频段
		Cell cell14 = row.getCell(count++);
		cell14.setCellValue(rc.getCellband() == null?"":rc.getCellband().toString());
		//天线公用情况
		Cell cell15 = row.getCell(count++);
		cell15.setCellValue(rc.getSharedant() == null?"":rc.getSharedant());
		//邻区赋值
		setNearValue(rc.getA1_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA2_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA3_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA4_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA5_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA6_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA7_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA8_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA9_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA10_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA11_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA12_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA13_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA14_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA15_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA16_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA17_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA18_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA19_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA20_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA21_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA22_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA23_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA24_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA25_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA26_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA27_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA28_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA29_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA30_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA31_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA32_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA33_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA34_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA35_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA36_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA37_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA38_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA39_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA40_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA41_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA42_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA43_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA44_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA45_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA46_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA47_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA48_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA49_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA50_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA51_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA52_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA53_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA54_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA55_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA56_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA57_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA58_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA59_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA60_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA61_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA62_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA63_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA64_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA65_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA66_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA67_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA68_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA69_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA70_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA71_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA72_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA73_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA74_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA75_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA76_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA77_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA78_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA79_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA80_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA81_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA82_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA83_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA84_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA85_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA86_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA87_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA88_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA89_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA90_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA91_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA92_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA93_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA94_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA95_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		setNearValue(rc.getA96_lac_cid(),row,count++,styleLay1,styleLay2,styleLay3);
		/**
		 
		for(GwCasCell gcc : gw.getList()){
			Cell cell = null;
			//XSSFCellStyle style = (XSSFCellStyle)wb.createCellStyle();
			String val = gcc.getLac_cid();
			//System.out.println("w_count" + w_count + "g_count" +g_count);
			if(gcc.getModeType() == 0){
				cell = row.getCell(w_count ++);
			}else{
				cell = row.getCell(g_count ++);
			}
			cell.setCellValue(val);
			//System.out.println(gcc.getNearrel());
			if(gcc.getNearrel() == 0){
				//style = ExcelUtil.setBackColorByCustom(style, "#00c321");
				//styleLay1.setFillForegroundColor(HSSFColor.GREEN.index);
				// 设置填充模式
				//styleLay1.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(styleLay1);
			}else if(gcc.getNearrel() == 1){
				//style = ExcelUtil.setBackColorByCustom(style, "#1499da");
				//styleLay2.setFillForegroundColor(HSSFColor.BLUE.index);
				//styleLay2.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(styleLay2);
			}else if(gcc.getNearrel() == 2){
				//style = ExcelUtil.setBackColorByCustom(style, "#ffa200");
				//styleLay3.setFillForegroundColor(HSSFColor.YELLOW.index);
				//styleLay3.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(styleLay3);
			}
		
			
		}* 
		 */
		//填充单元格边框
//		for(int i = w_count;i < 66; i ++){
//			Cell cell = row.getCell(i);
////			XSSFCellStyle style = (XSSFCellStyle)wb.createCellStyle();
////			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
////			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
////			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
////			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
////			cell.setCellStyle(style);
//		}
//		for(int i = g_count;i < 98; i ++){
//			Cell cell = row.getCell(i);
////			XSSFCellStyle style = (XSSFCellStyle)wb.createCellStyle();
////			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
////			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
////			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
////			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
////			cell.setCellStyle(style);
//		}
	}

	/**
	 * 格式化时间
	 */
	public static String getTime(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
		String str = formatter.format(date);
		return str;
	}
	/**
	 * 创建title
	 */
	public void getTitle(Row topRow,CellStyle topStyle,Sheet sheet){
		// 创建标题
		int count =0;
				createTitle(topRow, topStyle, "城市名称", count ++);
				createTitle(topRow, topStyle, "行政区域", count ++);
				createTitle(topRow, topStyle, "区域id", count ++);
				createTitle(topRow, topStyle, "小区cid", count ++);
				createTitle(topRow, topStyle, "基站id", count ++);
				createTitle(topRow, topStyle, "基站名称", count ++);
				createTitle(topRow, topStyle, "小区名称", count ++);
				createTitle(topRow, topStyle, "经度", count ++);
				createTitle(topRow, topStyle, "纬度", count ++);
				createTitle(topRow, topStyle, "psc/bcch", count ++);
				createTitle(topRow, topStyle, "网络模式", count ++);
				createTitle(topRow, topStyle, "室内外", count ++);
				createTitle(topRow, topStyle, "天线类型", count ++);
				createTitle(topRow, topStyle, "天线方位角", count ++);
				createTitle(topRow, topStyle, "天线高度", count ++);
				createTitle(topRow, topStyle, "天线电子倾角", count ++);
				createTitle(topRow, topStyle, "天线机械倾角", count ++);
				createTitle(topRow, topStyle, "共用天线", count ++);
				createTitle(topRow, topStyle, "基站站址", count ++);
				createTitle(topRow, topStyle, "海拔高度", count ++);
				createTitle(topRow, topStyle, "设备厂商", count ++);
				createTitle(topRow, topStyle, "塔桅类型", count ++);
				createTitle(topRow, topStyle, "设备类型", count ++);
				createTitle(topRow, topStyle, "基站配置", count ++);
				createTitle(topRow, topStyle, "工程进展", count ++);
				createTitle(topRow, topStyle, "覆盖范围", count ++);
				createTitle(topRow, topStyle, "覆盖类型", count ++);
				createTitle(topRow, topStyle, "是否rru小区", count ++);
				createTitle(topRow, topStyle, "小区编号", count ++);
				createTitle(topRow, topStyle, "直放站数量", count ++);
				createTitle(topRow, topStyle, "共天馈小区", count ++);
				createTitle(topRow, topStyle, "共天馈属性", count ++);
				createTitle(topRow, topStyle, "共站名称", count ++);
				createTitle(topRow, topStyle, "共站属性", count ++);
				createTitle(topRow, topStyle, "网管运行状态", count ++);
				createTitle(topRow, topStyle, "行政区域类型", count ++);
				createTitle(topRow, topStyle, "上行频点", count ++);
				createTitle(topRow, topStyle, "下行频点", count ++);
				createTitle(topRow, topStyle, "rnc名称", count ++);
				createTitle(topRow, topStyle, "网元类型", count ++);
				createTitle(topRow, topStyle, "天线发射功率", count ++);
				createTitle(topRow, topStyle, "下倾角", count ++);
				createTitle(topRow, topStyle, "是否可以电调", count ++);
				createTitle(topRow, topStyle, "基站类型", count ++);
				createTitle(topRow, topStyle, "rru数量", count ++);
				createTitle(topRow, topStyle, "逻辑小区级别", count ++);
				createTitle(topRow, topStyle, "扇区配置的无线容量", count ++);
				createTitle(topRow, topStyle, "e1数量", count ++);
				createTitle(topRow, topStyle, "fe数量", count ++);
				createTitle(topRow, topStyle, "传输方式", count ++);
				createTitle(topRow, topStyle, "重庆区域覆盖", count ++);
				createTitle(topRow, topStyle, "设计无线容量", count ++);
				createTitle(topRow, topStyle, "基站最大发射功率", count ++);
				createTitle(topRow, topStyle, "内置下倾角", count ++);
				createTitle(topRow, topStyle, "退服时间", count ++);
				createTitle(topRow, topStyle, "小区的最大下行发射功率", count ++);
				createTitle(topRow, topStyle, "一级场景", count ++);
				createTitle(topRow, topStyle, "二级场景", count ++);
				createTitle(topRow, topStyle, "是否城区", count ++);
				createTitle(topRow, topStyle, "是否支持hsdpa", count ++);
				createTitle(topRow, topStyle, "hsdpa功能状态", count ++);
				createTitle(topRow, topStyle, "hs-pdsch代码", count ++);
				createTitle(topRow, topStyle, "小区中广播信道的功率", count ++);
				createTitle(topRow, topStyle, "是否支持hsupa", count ++);
				createTitle(topRow, topStyle, "hsupa功能状态", count ++);
				createTitle(topRow, topStyle, "是否支持mbms", count ++);
				createTitle(topRow, topStyle, "mbms功能状态", count ++);
				createTitle(topRow, topStyle, "mich信道数", count ++);
				createTitle(topRow, topStyle, "sf=16的码字数", count ++);
				createTitle(topRow, topStyle, "sf=128的码字数", count ++);
				createTitle(topRow, topStyle, "e-agch信道的初始数目", count ++);
				createTitle(topRow, topStyle, "e-rgch/e-hich信道的初始数目", count ++);
				createTitle(topRow, topStyle, "pcpich信道最大发射功率", count ++);
				createTitle(topRow, topStyle, "pcpich信道最小发射功率", count ++);
				createTitle(topRow, topStyle, "小区中使用的主导频信道的功率", count ++);
				createTitle(topRow, topStyle, "小区中主同步信道的下行功率", count ++);
				createTitle(topRow, topStyle, "单载频容纳最大用户数", count ++);
				createTitle(topRow, topStyle, "小区配置的载频发射功率", count ++);
				createTitle(topRow, topStyle, "配置的上行ce容量", count ++);
				createTitle(topRow, topStyle, "配置的下行ce容量", count ++);
				createTitle(topRow, topStyle, "iub接口atm端口的配置带宽", count ++);
				createTitle(topRow, topStyle, "iub接口ip端口的配置带宽", count ++);
				createTitle(topRow, topStyle, "iu接口atm层配置带宽", count ++);
				createTitle(topRow, topStyle, "iu接口ip层配置带宽", count ++);
				createTitle(topRow, topStyle, "iur接口atm层配置带宽", count ++);
				createTitle(topRow, topStyle, "iur接口ip层配置带宽", count ++);
				createTitle(topRow, topStyle, "备注", count ++);
				createTitle(topRow, topStyle, "最后更新时间", count ++);
				createTitle(topRow, topStyle, "rac", count ++);
				createTitle(topRow, topStyle, "sac", count ++);
				createTitle(topRow, topStyle, "rnc编号", count ++);
				createTitle(topRow, topStyle, "基站编号", count ++);
				createTitle(topRow, topStyle, "ura编号", count ++);
				createTitle(topRow, topStyle, "是否安装塔顶放大器", count ++);
				createTitle(topRow, topStyle, "扇区编号", count ++);
				createTitle(topRow, topStyle, "入网时间", count ++);
				createTitle(topRow, topStyle, "vip站", count ++);
				createTitle(topRow, topStyle, "室内室外", count ++);
				createTitle(topRow, topStyle, "bsc名称", count ++);
				createTitle(topRow, topStyle, "省份", count ++);
				createTitle(topRow, topStyle, "基站设备型号", count ++);
				createTitle(topRow, topStyle, "机柜类型", count ++);
				createTitle(topRow, topStyle, "载波类型", count ++);
				createTitle(topRow, topStyle, "共用馈线", count ++);
				createTitle(topRow, topStyle, "共用平台", count ++);
				createTitle(topRow, topStyle, "小区频段", count ++);
				createTitle(topRow, topStyle, "小区标识", count ++);
				createTitle(topRow, topStyle, "小区bsic", count ++);
				createTitle(topRow, topStyle, "tch频点", count ++);
				createTitle(topRow, topStyle, "配置载频数", count ++);
				createTitle(topRow, topStyle, "可用载频数", count ++);
				createTitle(topRow, topStyle, "载频最大发射功率", count ++);
				createTitle(topRow, topStyle, "配置控制信道数", count ++);
				createTitle(topRow, topStyle, "配置业务信道数", count ++);
				createTitle(topRow, topStyle, "sdcch可用数", count ++);
				createTitle(topRow, topStyle, "天线俯仰角", count ++);
				createTitle(topRow, topStyle, "跳频模式", count ++);
				createTitle(topRow, topStyle, "是否开通半速率", count ++);
				createTitle(topRow, topStyle, "gprs开通情况", count ++);
				createTitle(topRow, topStyle, "edge开通情况", count ++);
				createTitle(topRow, topStyle, "增强全速率开通情况", count ++);
				createTitle(topRow, topStyle, "网络制式", count ++);
				/*createTitle(topRow, topStyle, "3G邻区1", count ++);
				createTitle(topRow, topStyle, "3G邻区2", count ++);
				createTitle(topRow, topStyle, "3G邻区3", count ++);
				createTitle(topRow, topStyle, "3G邻区4", count ++);
				createTitle(topRow, topStyle, "3G邻区5", count ++);
				createTitle(topRow, topStyle, "3G邻区6", count ++);
				createTitle(topRow, topStyle, "3G邻区7", count ++);
				createTitle(topRow, topStyle, "3G邻区8", count ++);
				createTitle(topRow, topStyle, "3G邻区9", count ++);
				createTitle(topRow, topStyle, "3G邻区10", count ++);
				createTitle(topRow, topStyle, "3G邻区11", count ++);
				createTitle(topRow, topStyle, "3G邻区12", count ++);
				createTitle(topRow, topStyle, "3G邻区13", count ++);
				createTitle(topRow, topStyle, "3G邻区14", count ++);
				createTitle(topRow, topStyle, "3G邻区15", count ++);
				createTitle(topRow, topStyle, "3G邻区16", count ++);
				createTitle(topRow, topStyle, "3G邻区17", count ++);
				createTitle(topRow, topStyle, "3G邻区18", count ++);
				createTitle(topRow, topStyle, "3G邻区19", count ++);
				createTitle(topRow, topStyle, "3G邻区20", count ++);
				createTitle(topRow, topStyle, "3G邻区21", count ++);
				createTitle(topRow, topStyle, "3G邻区22", count ++);
				createTitle(topRow, topStyle, "3G邻区23", count ++);
				createTitle(topRow, topStyle, "3G邻区24", count ++);
				createTitle(topRow, topStyle, "3G邻区25", count ++);
				createTitle(topRow, topStyle, "3G邻区26", count ++);
				createTitle(topRow, topStyle, "3G邻区27", count ++);
				createTitle(topRow, topStyle, "3G邻区28", count ++);
				createTitle(topRow, topStyle, "3G邻区29", count ++);
				createTitle(topRow, topStyle, "3G邻区30", count ++);
				createTitle(topRow, topStyle, "3G邻区31", count ++);
				createTitle(topRow, topStyle, "3G邻区32", count ++);
				createTitle(topRow, topStyle, "3G邻区33", count ++);
				createTitle(topRow, topStyle, "3G邻区34", count ++);
				createTitle(topRow, topStyle, "3G邻区35", count ++);
				createTitle(topRow, topStyle, "3G邻区36", count ++);
				createTitle(topRow, topStyle, "3G邻区37", count ++);
				createTitle(topRow, topStyle, "3G邻区38", count ++);
				createTitle(topRow, topStyle, "3G邻区39", count ++);
				createTitle(topRow, topStyle, "3G邻区40", count ++);
				createTitle(topRow, topStyle, "3G邻区41", count ++);
				createTitle(topRow, topStyle, "3G邻区42", count ++);
				createTitle(topRow, topStyle, "3G邻区43", count ++);
				createTitle(topRow, topStyle, "3G邻区44", count ++);
				createTitle(topRow, topStyle, "3G邻区45", count ++);
				createTitle(topRow, topStyle, "3G邻区46", count ++);
				createTitle(topRow, topStyle, "3G邻区47", count ++);
				createTitle(topRow, topStyle, "3G邻区48", count ++);
				createTitle(topRow, topStyle, "3G邻区49", count ++);
				createTitle(topRow, topStyle, "3G邻区50", count ++);
				createTitle(topRow, topStyle, "3G邻区51", count ++);
				createTitle(topRow, topStyle, "3G邻区52", count ++);
				createTitle(topRow, topStyle, "3G邻区53", count ++);
				createTitle(topRow, topStyle, "3G邻区54", count ++);
				createTitle(topRow, topStyle, "3G邻区55", count ++);
				createTitle(topRow, topStyle, "3G邻区56", count ++);
				createTitle(topRow, topStyle, "3G邻区57", count ++);
				createTitle(topRow, topStyle, "3G邻区58", count ++);
				createTitle(topRow, topStyle, "3G邻区59", count ++);
				createTitle(topRow, topStyle, "3G邻区60", count ++);
				createTitle(topRow, topStyle, "3G邻区61", count ++);
				createTitle(topRow, topStyle, "3G邻区62", count ++);
				createTitle(topRow, topStyle, "3G邻区63", count ++);
				createTitle(topRow, topStyle, "3G邻区64", count ++);
				createTitle(topRow, topStyle, "2G邻区1", count ++);
				createTitle(topRow, topStyle, "2G邻区2", count ++);
				createTitle(topRow, topStyle, "2G邻区3", count ++);
				createTitle(topRow, topStyle, "2G邻区4", count ++);
				createTitle(topRow, topStyle, "2G邻区5", count ++);
				createTitle(topRow, topStyle, "2G邻区6", count ++);
				createTitle(topRow, topStyle, "2G邻区7", count ++);
				createTitle(topRow, topStyle, "2G邻区8", count ++);
				createTitle(topRow, topStyle, "2G邻区9", count ++);
				createTitle(topRow, topStyle, "2G邻区10", count ++);
				createTitle(topRow, topStyle, "2G邻区11", count ++);
				createTitle(topRow, topStyle, "2G邻区12", count ++);
				createTitle(topRow, topStyle, "2G邻区13", count ++);
				createTitle(topRow, topStyle, "2G邻区14", count ++);
				createTitle(topRow, topStyle, "2G邻区15", count ++);
				createTitle(topRow, topStyle, "2G邻区16", count ++);
				createTitle(topRow, topStyle, "2G邻区17", count ++);
				createTitle(topRow, topStyle, "2G邻区18", count ++);
				createTitle(topRow, topStyle, "2G邻区19", count ++);
				createTitle(topRow, topStyle, "2G邻区20", count ++);
				createTitle(topRow, topStyle, "2G邻区21", count ++);
				createTitle(topRow, topStyle, "2G邻区22", count ++);
				createTitle(topRow, topStyle, "2G邻区23", count ++);
				createTitle(topRow, topStyle, "2G邻区24", count ++);
				createTitle(topRow, topStyle, "2G邻区25", count ++);
				createTitle(topRow, topStyle, "2G邻区26", count ++);
				createTitle(topRow, topStyle, "2G邻区27", count ++);
				createTitle(topRow, topStyle, "2G邻区28", count ++);
				createTitle(topRow, topStyle, "2G邻区29", count ++);
				createTitle(topRow, topStyle, "2G邻区30", count ++);
				createTitle(topRow, topStyle, "2G邻区31", count ++);
				createTitle(topRow, topStyle, "2G邻区32", count ++);*/
				// 使单元格自动适应内容长度
				for (int i = 0; i < 122; i++) {
					sheet.autoSizeColumn(i, true);
				}
	}
}
