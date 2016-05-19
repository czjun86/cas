package com.complaint.service;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.GradMapper;
import com.complaint.dao.ReportMapper;
import com.complaint.model.RateColor;
import com.complaint.model.TrackPoint;
import com.complaint.utils.ExcelUtil;
@Service("reportExcelService")
public class ReportExcelService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ReportExcelService.class);
	
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private GradMapper gradMapper;
	/**
	 * 获取excel模板
	 * @param value
	 * @param tempPath
	 */
	public void excelService(Map value ,String flowid,String tempPath ,String path ,
			Map valueMapgraph ,List<String> keys ,List<String> evalKeys , Map eval ,Map pingMap){
		FileInputStream file = null;
		XSSFWorkbook wb = null;
		try {
			file = new FileInputStream(tempPath);
			wb = new XSSFWorkbook(file);
		} catch (Exception e) {
			logger.error("",e);
		}
		// 2G轨迹点
		List<TrackPoint> gsmlist = new ArrayList<TrackPoint>();
		gsmlist = reportMapper.getGsmByFlowid(flowid);
		// 3G轨迹点
		List<TrackPoint> wcdmalist = new ArrayList<TrackPoint>();
		wcdmalist = reportMapper.getWcdmaByFlowid(flowid);
		// 4G轨迹点
		List<TrackPoint> ltelist = new ArrayList<TrackPoint>();
		ltelist = reportMapper.getLteByFlowid(flowid);
		//查出颜色
		List<RateColor> colist=this.gradMapper.showGradColor();
		//第一页
		XSSFSheet sheet1 = wb.getSheet("工单详情");
		firstSheetContent(sheet1,value,wb,colist ,pingMap);
		
		//第二页
		XSSFSheet sheet2 = wb.getSheet("实测明细");
		secondSheetContent(sheet2,gsmlist,wcdmalist,ltelist,wb);
		
		//第三页
		XSSFSheet sheet3 = wb.getSheet("测试评价");
		threeSheetContent(sheet3,value,evalKeys,eval ,wb ,colist);
		
		//第四页
		if(keys.size()>0){
		XSSFSheet sheet4 = wb.getSheet("Sheet4");
		forthSheetContent(sheet4,valueMapgraph,keys,wb ,evalKeys ,wcdmalist,ltelist);
		sheet4.setForceFormulaRecalculation(true);
		}
		sheet2.setForceFormulaRecalculation(true); 
		sheet1.setForceFormulaRecalculation(true); 
		
		FileOutputStream out =null;
		try {
			out =  new FileOutputStream(path);
			wb.write(out);
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
	 * sheet1填入工单流水信息
	 */
	public void firstSheetContent(XSSFSheet sheet,Map value,XSSFWorkbook wb ,List<RateColor> colist ,Map pingMap){
		CellStyle style = getStyle(wb);
		workerOrder(sheet,value,style);//工单信息插入
		testResult(sheet,value,style,colist,wb);//测试结果插入
		pingValue(wb,sheet,pingMap);//ping值插入
		
	}
	
	/**
	 * sheet1的工单信息
	 */
	public void workerOrder(XSSFSheet sheet,Map value,CellStyle style){
		Row row3 = sheet.getRow(2);//第3行
		Row row4 = sheet.getRow(3);//第4行
		Row row5 = sheet.getRow(4);//第5行
		Row row6 = sheet.getRow(5);//第6行
		Row row7 = sheet.getRow(6);//第7行
		Row row8 = sheet.getRow(7);//第8行
		Row row9 = sheet.getRow(8);//第9行
		Row row10 = sheet.getRow(9);//第10行
		Row row11 = sheet.getRow(10);//第11行
		
		
		Cell cellb3 = row3.getCell(1);//工单流水号
		cellb3.setCellValue((String)value.get("serialnoFlow"));
		cellb3.setCellStyle(style);
		
		Cell cellf3 = row3.getCell(5);//系统接单时间
		StringBuilder submitDateTime=new StringBuilder((String)value.get("submitDateTime"));
		submitDateTime.insert(10," ");
		cellf3.setCellValue(submitDateTime.toString());
		cellf3.setCellStyle(style);
		
		Cell cellb4 = row4.getCell(1);//网络类型
		cellb4.setCellValue((String)value.get("netType"));
		cellb4.setCellStyle(style);
		
		Cell cellf4 = row4.getCell(5);//受理路径
		cellf4.setCellValue((String)value.get("dealPath"));
		cellf4.setCellStyle(style);
		
		Cell cellb5 = row5.getCell(1);//投诉区域
		cellb5.setCellValue((String)value.get("area"));
		cellb5.setCellStyle(style);
		
		Cell cellf5 = row5.getCell(5);//最后测试时间
		StringBuilder testDateTime=new StringBuilder((String)value.get("testDateTime"));
		testDateTime.insert(10," ");
		cellf5.setCellValue(testDateTime.toString());
		cellf5.setCellStyle(style);
		
		Cell cellb6 = row6.getCell(1);//派单员工号
		cellb6.setCellValue((String)value.get("workerid"));
		cellb6.setCellStyle(style);
		
		Cell cellf6 = row6.getCell(5);//业务产品分类
		cellf6.setCellValue((String)value.get("operationClassify"));
		cellf6.setCellStyle(style);
		
		Cell cellb7 = row7.getCell(1);//受理号码
		cellb7.setCellValue((String)value.get("acceptance"));
		cellb7.setCellStyle(style);
		
		Cell cellf7 = row7.getCell(5);//用户品牌
		cellf7.setCellValue((String)value.get("clientid"));
		cellf7.setCellStyle(style);
		
		Cell cellb8 = row8.getCell(1);//客户联系电话
		cellb8.setCellValue((String)value.get("customer"));
		cellb8.setCellStyle(style);
		
		Cell cellf8 = row8.getCell(5);//测试次数
		cellf8.setCellValue((String)value.get("testTime"));
		cellf8.setCellStyle(style);
		
		Cell cellb9 = row9.getCell(1);//详细投诉地址
		cellb9.setCellValue((String)value.get("url"));
		cellb9.setCellStyle(style);
		
		Cell cellb10 = row10.getCell(1);//投诉内容
		cellb10.setCellValue((String)value.get("contentrs"));
		cellb10.setCellStyle(style);
		
		Cell cellb11 = row11.getCell(1);//模块内容
		cellb11.setCellValue((String)value.get("module"));
		cellb11.setCellStyle(style);
	}
	
	
	/**
	 * sheet1的测试结果插入
	 */
	public void testResult(XSSFSheet sheet,Map value,CellStyle style,List<RateColor> colist,XSSFWorkbook wb){
		Row row17 = sheet.getRow(16);//第17行
		Row row18 = sheet.getRow(17);//第18行
		Row row19 = sheet.getRow(18);//第19行
		Row row20 = sheet.getRow(19);//第20行
		Row row21 = sheet.getRow(20);//第21行
		Row row22 = sheet.getRow(21);//第22行
		Row row23 = sheet.getRow(22);//第23行
		Row row26 = sheet.getRow(25);//第26行
		
		Cell cellb17 = row17.getCell(1);//工单号
		String str = (String)value.get("serialno");
		StringBuilder serialno=new StringBuilder(str);
		serialno.insert((str.length()-8)," ");
		cellb17.setCellValue(serialno.toString());
		cellb17.setCellStyle(style);
		
		Cell cellf17 = row17.getCell(5);//室内/外
		cellf17.setCellValue((String)value.get("indoor"));
		cellf17.setCellStyle(style);
		
		Cell cella18 = row18.getCell(0);//室分/阻碍  名称
		cella18.setCellValue((String)value.get("roomName"));
		cella18.setCellStyle(style);
		
		Cell cellb18 = row18.getCell(1);//室分/阻碍
		cellb18.setCellValue((String)value.get("room"));
		cellb18.setCellStyle(style);
		
		Cell celle18 = row18.getCell(4);//GPS/区域 名称
		celle18.setCellValue((String)value.get("gpsName"));
		celle18.setCellStyle(style);
		
		Cell cellf18 = row18.getCell(5);//GPS/
		cellf18.setCellValue((String)value.get("gps"));
		cellf18.setCellStyle(style);
		
		Cell cellb19 = row19.getCell(1);//场景
		cellb19.setCellValue((String)value.get("scene"));
		cellb19.setCellStyle(style);
		
		Cell cellf19 = row19.getCell(5);//业务状态
		cellf19.setCellValue((String)value.get("operationType"));
		cellf19.setCellStyle(style);
		
		Cell cellb20 = row20.getCell(1);//3G占比
		cellb20.setCellValue((String)value.get("wcdmaRatio"));
		cellb20.setCellStyle(style);
		
		Cell cellf20 = row20.getCell(5);//2G占比
		cellf20.setCellValue((String)value.get("gsmRatio"));
		cellf20.setCellStyle(style);
		
		Cell cellb21_1 = row21.getCell(1);//4G占比
		cellb21_1.setCellValue((String)value.get("lteRatio"));
		cellb21_1.setCellStyle(style);
		
		Cell cellb21_2 = row21.getCell(5);//无服务占比
		cellb21_2.setCellValue((String)value.get("noService"));
		cellb21_2.setCellStyle(style);
		
		Cell cellf22 = row22.getCell(1);//本次综合评价
		writeEval((String)value.get("evaluate"),cellf22,wb,colist);
		cellf22.setCellStyle(style);
		
		Cell cellb23 = row23.getCell(1);//实测位置
		cellb23.setCellValue((String)value.get("testUrl"));
		cellb23.setCellStyle(style);
		
		/*Cell cella25 = row25.getCell(1);//指标类型
		cella25.setCellValue((String)value.get("targetType"));
		cella25.setCellStyle(style);*/
		
		Cell cellh26 = row26.getCell(7);//测试点数量
		cellh26.setCellValue((String)value.get("point"));
		cellh26.setCellStyle(style);
	}
	
	/**
	 * sheet1的ping值
	 */
	public void pingValue(XSSFWorkbook wb ,XSSFSheet sheet,Map pingMap){
		CellStyle style = getStyle(wb);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		
		Row row = sheet.getRow(147);
		
		Cell cell1 = row.getCell(1);//丢包率
		cell1.setCellValue(pingMap.get("pinglo")==null?"":String.valueOf(pingMap.get("pinglo")));
		cell1.setCellStyle(style);
		
		Cell cell2 = row.getCell(2);//最大延迟
		cell2.setCellValue(pingMap.get("pingdmax")==null?"":String.valueOf(pingMap.get("pingdmax")));
		cell2.setCellStyle(style);
		
		Cell cell3 = row.getCell(3);//最小延迟
		cell3.setCellValue(pingMap.get("pingdmix")==null?"":String.valueOf(pingMap.get("pingdmix")));
		cell3.setCellStyle(style);
		
		Cell cell4 = row.getCell(4);//平均延迟
		cell4.setCellValue(pingMap.get("pingdavg")==null?"":String.valueOf(pingMap.get("pingdavg")));
		cell4.setCellStyle(style);
		
		Cell cell6 = row.getCell(6);//最大下载速度
		cell6.setCellValue(pingMap.get("httpsmax")==null?"":String.valueOf(pingMap.get("httpsmax")));
		cell6.setCellStyle(style);
		
		Cell cell7 = row.getCell(7);//最小下载速度
		cell7.setCellValue(pingMap.get("httpsmin")==null?"":String.valueOf(pingMap.get("httpsmin")));
		cell7.setCellStyle(style);
		
		Cell cell8 = row.getCell(8);//平均下载速度
		cell8.setCellValue(pingMap.get("httpsavg")==null?"":String.valueOf(pingMap.get("httpsavg")));
		cell8.setCellStyle(style);
		
		Cell cell9 = row.getCell(9);//最大响应时间
		cell9.setCellValue(pingMap.get("httptmax")==null?"":String.valueOf(pingMap.get("httptmax")));
		cell9.setCellStyle(style);
		
		Cell cell10 = row.getCell(10);//最小响应时间
		cell10.setCellValue(pingMap.get("httptmix")==null?"":String.valueOf(pingMap.get("httptmix")));
		cell10.setCellStyle(style);
		
		Cell cell11 = row.getCell(11);//平均响应时间
		cell11.setCellValue(pingMap.get("httptavg")==null?"":String.valueOf(pingMap.get("httptavg")));
		cell11.setCellStyle(style);
		
	}
	/**
	 * 第二sheet页插入数据
	 */
	public void secondSheetContent(XSSFSheet sheet,List<TrackPoint> gsmlist ,List<TrackPoint> wcdmalist ,List<TrackPoint> ltelist ,XSSFWorkbook wb){
		XSSFCellStyle style= getStyle(wb);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		style.setFillForegroundColor(new XSSFColor(new Color(59,124,196)));// 设置前端颜色
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		int count = 0;
		//放入2G数据
		int gs = gsmlist.size();
		int ws = wcdmalist.size();
		int ls = ltelist.size();
		count = gs;
		if(count<ws){
			count = ws;
		}
		if(count<ls){
			count = ls;
		}
		
		if(count>0){
			int wsg = 0;//判断时候有2G数据,没有3g就从0开始放
			int lsg = 0;//判断时候有2G数据,没有3g就从0开始放
			Row row = sheet.createRow(0);
			int title=0;
			if(gs>0){
				Cell cell00_1 = row.createCell(title++);
				cell00_1.setCellValue("2G:");
				
				Cell cell00 = row.createCell(title++);
				sheet.setColumnWidth(title-1,21*256);
				cell00.setCellValue("time");
				cell00.setCellStyle(style);
				
				Cell cell01 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell01.setCellValue("Rxlev");
				cell01.setCellStyle(style);
				
				Cell cell02 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell02.setCellValue("RxQual");
				cell02.setCellStyle(style);
				
				Cell cell03 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell03.setCellValue("C/I");
				cell03.setCellStyle(style);
			}
			
			if(ws>0){
				if(title>0){
					title += 2;
				}
				Cell cell00_1 = row.createCell(title++);
				cell00_1.setCellValue("3G:");
				wsg = title;
				
				Cell cell00 = row.createCell(title++);
				sheet.setColumnWidth(title-1,21*256);
				cell00.setCellValue("time");
				cell00.setCellStyle(style);
				
				Cell cell01 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell01.setCellValue("RSCP");
				cell01.setCellStyle(style);
				
				Cell cell02 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell02.setCellValue("Ec/No");
				cell02.setCellStyle(style);
				
				Cell cell03 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell03.setCellValue("Txpower");
				cell03.setCellStyle(style);
				
				Cell cell04 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell04.setCellValue("FTP");
				cell04.setCellStyle(style);
			}
			
			if(ls>0){
				if(title>0){
					title += 2;
				}
				Cell cell00_1 = row.createCell(title++);
				cell00_1.setCellValue("4G:");
				lsg = title;
				
				Cell cell00 = row.createCell(title++);
				sheet.setColumnWidth(title-1,21*256);
				cell00.setCellValue("time");
				cell00.setCellStyle(style);
				
				Cell cell01 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell01.setCellValue("RSRP");
				cell01.setCellStyle(style);
				
				Cell cell02 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell02.setCellValue("RSRQ");
				cell02.setCellStyle(style);
				
				Cell cell03 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell03.setCellValue("SINR");
				cell03.setCellStyle(style);
				
				Cell cell04 = row.createCell(title++);
				sheet.setColumnWidth(title-1,10*256);
				cell04.setCellValue("FTP");
				cell04.setCellStyle(style);
			}
			
			
			for(int i=0;i<count;i++){
				Row rows = sheet.createRow(i+1);
				if(i < gs){
					
					Cell celltime = rows.createCell(1);//time
					celltime.setCellValue(gsmlist.get(i).getEptime()==null?"":gsmlist.get(i).getEptime());
					
					Cell cellrx = rows.createCell(2);//rxlev
					cellrx.setCellValue((gsmlist.get(i).getRxlev_()==null||gsmlist.get(i).getRxlev_()==-9999)?"":gsmlist.get(i).getRxlev_()==-9998?"No Service":String.valueOf(gsmlist.get(i).getRxlev_()));
					
					Cell cellrq = rows.createCell(3);//rxqual
					cellrq.setCellValue((gsmlist.get(i).getRxqual_()==null||gsmlist.get(i).getRxqual_()==-9999)?"":gsmlist.get(i).getRxqual_()==-9998?"No Service":String.valueOf(gsmlist.get(i).getRxqual_()));
					
					Cell cellci = rows.createCell(4);//ci
					cellci.setCellValue((gsmlist.get(i).getCi_()==null||gsmlist.get(i).getCi_()==-9999)?"":gsmlist.get(i).getCi_()==-9998?"No Service":String.valueOf(gsmlist.get(i).getCi_()));
				}
				
				if(i < ws){
					Cell cellftptime = rows.createCell(wsg);//time
					cellftptime.setCellValue(wcdmalist.get(i).getEptime()==null?"":wcdmalist.get(i).getEptime());
					
					Cell cellrscp = rows.createCell(wsg+1);//RSCP
					cellrscp.setCellValue((wcdmalist.get(i).getRscp_()==null||wcdmalist.get(i).getRscp_()==-9999)?"":wcdmalist.get(i).getRscp_()==-9998?"No Service":String.valueOf(wcdmalist.get(i).getRscp_()));
					
					Cell cellecno = rows.createCell(wsg+2);//Ec/No
					cellecno.setCellValue((wcdmalist.get(i).getEcno_()==null||wcdmalist.get(i).getEcno_()==-9999)?"":wcdmalist.get(i).getEcno_()==-9998?"No Service":String.valueOf(wcdmalist.get(i).getEcno_()));
					
					Cell celltxpower = rows.createCell(wsg+3);//Txpower
					celltxpower.setCellValue((wcdmalist.get(i).getTxpower_()==null||wcdmalist.get(i).getTxpower_()==-9999)?"":wcdmalist.get(i).getTxpower_()==-9998?"No Service":String.valueOf(wcdmalist.get(i).getTxpower_()));
					
					Cell cellftp = rows.createCell(wsg+4);//ftpSpeed
					cellftp.setCellValue((wcdmalist.get(i).getFtpSpeed_()==null||wcdmalist.get(i).getFtpSpeed_()==-9999)?"":wcdmalist.get(i).getFtpSpeed_()==-9998?"No Service":String.valueOf(wcdmalist.get(i).getFtpSpeed_()));
				}
				
				if(i < ls){
					Cell cellftptime = rows.createCell(lsg);//time
					cellftptime.setCellValue(ltelist.get(i).getEptime()==null?"":ltelist.get(i).getEptime());
					
					Cell cellrscp = rows.createCell(lsg+1);//RSCP
					cellrscp.setCellValue((ltelist.get(i).getRsrp_()==null||ltelist.get(i).getRsrp_()==-9999)?"":ltelist.get(i).getRsrp_()==-9998?"No Service":String.valueOf(ltelist.get(i).getRsrp_()));
					
					Cell cellecno = rows.createCell(lsg+2);//RSRQ
					cellecno.setCellValue((ltelist.get(i).getRsrq_()==null||ltelist.get(i).getRsrq_()==-9999)?"":ltelist.get(i).getRsrq_()==-9998?"No Service":String.valueOf(ltelist.get(i).getRsrq_()));
					
					Cell celltxpower = rows.createCell(lsg+3);//SNR
					celltxpower.setCellValue((ltelist.get(i).getSnr_()==null||ltelist.get(i).getSnr_()==-9999)?"":ltelist.get(i).getSnr_()==-9998?"No Service":String.valueOf(ltelist.get(i).getSnr_()));
					
					Cell cellftp = rows.createCell(lsg+4);//ftpSpeed
					cellftp.setCellValue((ltelist.get(i).getFtpSpeed_()==null||ltelist.get(i).getFtpSpeed_()==-9999)?"":ltelist.get(i).getFtpSpeed_()==-9998?"No Service":String.valueOf(ltelist.get(i).getFtpSpeed_()));
				}
			}
		}
	}
	
	/**
	 * sheet3数据评价展示
	 * @param evalKeys
	 * @param eval
	 */
	public void threeSheetContent(XSSFSheet sheet,Map value ,List<String> evalKeys,Map eval ,XSSFWorkbook wb ,List<RateColor> colist){
		CreationHelper helper = wb.getCreationHelper();
		XSSFFont[] fonts = new XSSFFont[colist.size()];
		for(int i = 0;i<colist.size();i++){
			fonts[i] = wb.createFont();
			fonts[i] = ExcelUtil.setFontColor(fonts[i], colist.get(i).getRank_color());
		}
		Row row0 = sheet.createRow(0);
		Cell cell00 = row0.createCell(0);
		cell00.setCellValue("实测点个数");
		
		Cell cell01 = row0.createCell(1);
		cell01.setCellValue((String)value.get("point"));
		
		Row row1 = sheet.createRow(1);
		Cell cell10 = row1.createCell(0);
		cell10.setCellValue("测试评价");
		
		Cell cell11 = row1.createCell(1);
		String compEval = "";
		//测试总评价
		List<Integer> list = new ArrayList<Integer>();
		for(String key:evalKeys){
			if("gsm".equals(key)){
				compEval += "2G:"+eval.get(key);
				list.add(getColorNum((String) eval.get(key)));
			}
			if("wcdma".equals(key)){
				compEval += "3G:"+eval.get(key);
				list.add(getColorNum((String) eval.get(key)));
			}
			if("lte".equals(key)){
				compEval += "4G:"+eval.get(key);
				list.add(getColorNum((String) eval.get(key)));
			}
			if("noservice".equals(key)){
				compEval += "无";
			}
		}
		
		RichTextString rts = helper.createRichTextString(compEval);
		if(list.size()>0){
			if(list.size()==1||list.size()==2){
				rts.applyFont(3, 4, fonts[list.get(0)]);
			}
			if(list.size()==2){
				rts.applyFont(7, 8, fonts[list.get(1)]);
			}
		}
		cell11.setCellValue(rts);
		//指标评价
		int count = 2;
		for(String valuekey: evalKeys){
			int a = 0;
				a = valuekey.indexOf("_");//判断没有"_c"的key
			if(a==-1){
				if(!(("gsm".equals(valuekey))
							||("wcdma".equals(valuekey))
							||("lte".equals(valuekey))
							||("noservice".equals(valuekey)))){
					
					Row row = sheet.createRow(count);
					
					Cell cell0 = row.createCell(0);
					cell0.setCellValue(valuekey);
					
					Cell cell1 = row.createCell(1);
					String indicator = (String)eval.get(valuekey);
					int color = getColorNum(indicator);
					RichTextString rtsVal = helper.createRichTextString(indicator);
					rtsVal.applyFont(0, 1, fonts[color]);
					cell1.setCellValue(rtsVal);
					
					count++;
				}
			}
		}
	}
	
	/**
	 * sheet4柱状图放入数据
	 */
	public void forthSheetContent(XSSFSheet sheet ,Map map ,List<String> keys,XSSFWorkbook wb ,List<String> evalKeys ,List<TrackPoint> wcdmalist ,List<TrackPoint> ltelist){
		XSSFCellStyle style = wb.createCellStyle();
		XSSFDataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("0.00%"));
		List<String> key = getKey(keys ,evalKeys);
		for(String str:key){
			int num = getRowNum(str);
			if(num == 0){
				continue;
			}
			Row row1 = sheet.getRow(num);//x的值
			Row row2 = sheet.getRow(num+1);//y的值
			JSONArray xstr = (JSONArray) map.get(str+"_x");
			JSONArray ystr = (JSONArray) map.get(str+"_y");
			
			//X轴放入值
			if(num == 2|| num==14 || num==29){//PSC PCI BCCH没有大小之分
				Cell cell11 = row1.getCell(1);
				cell11.setCellValue((String)xstr.get(0));
				
				Cell cell12 = row1.getCell(2);
				cell12.setCellValue((String)xstr.get(1));
				
				Cell cell13 = row1.getCell(3);
				cell13.setCellValue((String)xstr.get(2));
				
				Cell cell14 = row1.getCell(4);
				cell14.setCellValue((String)xstr.get(3));
				
				Cell cell15 = row1.getCell(5);
				cell15.setCellValue((String)xstr.get(4));
				
				Cell cell16 = row1.getCell(6);
				cell16.setCellValue((String)xstr.get(5));
			}else if(num == 8|| num==20){//EC/NO  RxQual 由大到小
				Cell cell11 = row1.getCell(1);
				cell11.setCellValue("<"+(String)xstr.get(5));
				
				Cell cell12 = row1.getCell(2);
				cell12.setCellValue("["+(String)xstr.get(4)+")");
				
				Cell cell13 = row1.getCell(3);
				cell13.setCellValue("["+(String)xstr.get(3)+")");
				
				Cell cell14 = row1.getCell(4);
				cell14.setCellValue("["+(String)xstr.get(2)+")");
				
				Cell cell15 = row1.getCell(5);
				cell15.setCellValue("["+(String)xstr.get(1)+")");
				
				Cell cell16 = row1.getCell(6);
				cell16.setCellValue(">="+(String)xstr.get(0));
			}else{//由小到大
				Cell cell11 = row1.getCell(1);
				cell11.setCellValue("<"+(String)xstr.get(0));
				
				Cell cell12 = row1.getCell(2);
				cell12.setCellValue("["+(String)xstr.get(1)+")");
				
				Cell cell13 = row1.getCell(3);
				cell13.setCellValue("["+(String)xstr.get(2)+")");
				
				Cell cell14 = row1.getCell(4);
				cell14.setCellValue("["+(String)xstr.get(3)+")");
				
				Cell cell15 = row1.getCell(5);
				cell15.setCellValue("["+(String)xstr.get(4)+")");
				
				Cell cell16 = row1.getCell(6);
				cell16.setCellValue(">="+(String)xstr.get(5));
			}
			
			if(num == 8|| num==20){
				//Y轴放入值
				Cell cell21 = row2.getCell(1);
				String str21 = (String) ystr.get(5);
				cell21.setCellValue("".equals(str21)?0:Double.parseDouble(str21)*0.01);
				cell21.setCellStyle(style);
				
				Cell cell22 = row2.getCell(2);
				String str22 = (String) ystr.get(4);
				cell22.setCellValue("".equals(str22)?0:Double.parseDouble(str22)*0.01);
				cell22.setCellStyle(style);
				
				Cell cell23 = row2.getCell(3);
				String str23 = (String) ystr.get(3);
				cell23.setCellValue("".equals(str23)?0:Double.parseDouble(str23)*0.01);
				cell23.setCellStyle(style);
				
				Cell cell24 = row2.getCell(4);
				String str24 = (String) ystr.get(2);
				cell24.setCellValue("".equals(str24)?0:Double.parseDouble(str24)*0.01);
				cell24.setCellStyle(style);
				
				Cell cell25 = row2.getCell(5);
				String str25 = (String) ystr.get(1);
				cell25.setCellValue("".equals(str25)?0:Double.parseDouble(str25)*0.01);
				cell25.setCellStyle(style);
				
				Cell cell26 = row2.getCell(6);
				String str26 = (String) ystr.get(0);
				cell26.setCellValue("".equals(str26)?0:Double.parseDouble(str26)*0.01);
				cell26.setCellStyle(style);
			}else{
				//Y轴放入值
				Cell cell21 = row2.getCell(1);
				String str21 = (String) ystr.get(0);
				cell21.setCellValue("".equals(str21)?0:Double.parseDouble(str21)*0.01);
				cell21.setCellStyle(style);
				
				Cell cell22 = row2.getCell(2);
				String str22 = (String) ystr.get(1);
				cell22.setCellValue("".equals(str22)?0:Double.parseDouble(str22)*0.01);
				cell22.setCellStyle(style);
				
				Cell cell23 = row2.getCell(3);
				String str23 = (String) ystr.get(2);
				cell23.setCellValue("".equals(str23)?0:Double.parseDouble(str23)*0.01);
				cell23.setCellStyle(style);
				
				Cell cell24 = row2.getCell(4);
				String str24 = (String) ystr.get(3);
				cell24.setCellValue("".equals(str24)?0:Double.parseDouble(str24)*0.01);
				cell24.setCellStyle(style);
				
				Cell cell25 = row2.getCell(5);
				String str25 = (String) ystr.get(4);
				cell25.setCellValue("".equals(str25)?0:Double.parseDouble(str25)*0.01);
				cell25.setCellStyle(style);
				
				Cell cell26 = row2.getCell(6);
				String str26 = (String) ystr.get(5);
				cell26.setCellValue("".equals(str26)?0:Double.parseDouble(str26)*0.01);
				cell26.setCellStyle(style);
			}
		}
		
		//3G-FTP折线图
		if(wcdmalist.size()>0){
			int count = 500;
			if(wcdmalist.size()<=500){
				count = wcdmalist.size();
			}
			for(int i=0;i<count;i++){
				TrackPoint tp = wcdmalist.get(i);
				Row row = null;
				if(sheet.getRow(i) == null){
					row = sheet.createRow(i);
				}else{
					row = sheet.getRow(i);
				}
				Cell cell = null;
				if(row.getCell(8)==null){
					cell = row.createCell(8);
				}else{
					cell = row.getCell(8);
				}
				if(!(tp.getFtpSpeed_()==null||tp.getFtpSpeed_()==-9999||tp.getFtpSpeed_()==-9998)){
					cell.setCellValue(tp.getFtpSpeed_());
				}
			}
		}
		//4G-FTP折线图
		if(ltelist.size()>0){
			int count = 500;
			if(ltelist.size()<=500){
				count = ltelist.size();
			}
			for(int i=0;i<count;i++){
				TrackPoint tp = ltelist.get(i);
				Row row = null;
				if(sheet.getRow(i) == null){
					row = sheet.createRow(i);
				}else{
					row = sheet.getRow(i);
				}
				Cell cell = null;
				if(row.getCell(9)==null){
					cell = row.createCell(9);
				}else{
					cell = row.getCell(9);
				}
				if(!(tp.getFtpSpeed_()==null||tp.getFtpSpeed_()==-9999||tp.getFtpSpeed_()==-9998)){
					cell.setCellValue(tp.getFtpSpeed_());
				}
			}
		}
	}
	/**
	 * 计算对应key值数据放入第几行
	 */
	public Integer getRowNum(String key){
		int num = 0;
		if("PSC".equals(key)){
			num = 14;
		}else if("Ec/No".equals(key)){
			num = 20;
		}else if("TxPower".equals(key)){
			num = 23;
		}else if("3GFTP下行".equals(key)||"3GFTP上行".equals(key)){
			num = 26;
		}else if("BCCH".equals(key)){
			num = 2;
		}else if("RxLev".equals(key)){
			num = 5;
		}else if("RxQual".equals(key)){
			num = 8;
		}else if("C/I".equals(key)){
			num = 11;
		}else if("RSCP".equals(key)){
			num = 17;
		}else if("PCI".equals(key)){
			num = 29;
		}else if("RSRP".equals(key)){
			num = 32;
		}else if("RSRQ".equals(key)){
			num = 35;
		}else if("SINR".equals(key)){
			num = 38;
		}else if("4GFTP下行".equals(key)||"4GFTP上行".equals(key)){
			num = 41;
		}
		return num;
	}
	/**
	 * 计算有哪些参数的柱状图
	 * @param keys
	 * @return
	 */
	public List<String> getKey(List<String> keys ,List<String> evalKeys){
		List<String> key = new ArrayList<String>();
		for(String str:keys){
			boolean flag = true;
			String nowStr = str.split("_")[0];
			if(key.size()>0){
				for(String oldStr:key){
					if(oldStr.equals(nowStr)){
						flag =false;
					}
				}
			}
			if(flag){
				if("BCCH".equals(nowStr)||"PSC".equals(nowStr)||"PCI".equals(nowStr)){
					key.add(nowStr);
				}else{
					//利用评价参数，判断该展现的柱状图
					boolean flagEval = true;
					for(String eval:evalKeys){
						if(nowStr.equals(eval)){
							flagEval = false;
						}
					}
					if(!flagEval){
						key.add(nowStr);
					}
				}
			}
		}
		return key;
	}
	/**
	 * 通用样式
	 */
	public XSSFCellStyle getStyle(XSSFWorkbook wb){
		XSSFCellStyle style = wb.createCellStyle();
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
	 * 测试评价带颜色写入
	 */
	public void writeEval(String str,Cell cell ,XSSFWorkbook wb ,List<RateColor> colist){
		//设置优良中差颜色
		CreationHelper helper = wb.getCreationHelper();
		StringBuilder strb = new StringBuilder(str);
		if(str.length()>=3){
			strb.insert(4," ");
		}
		RichTextString rts = helper.createRichTextString(strb.toString());
		XSSFFont[] fonts = new XSSFFont[colist.size()];
		for(int i = 0;i<colist.size();i++){
			fonts[i] = wb.createFont();
			fonts[i] = ExcelUtil.setFontColor(fonts[i], colist.get(i).getRank_color());
		}
		if(!("无".equals(str)) && str != null){
			String[] value =str.split("：");
			if(value.length==2){
				String eval = (value[1]).trim();
				int i = getColorNum(eval);
				rts.applyFont(3, 4, fonts[i]);
			}else if(value.length==3){
				String eval1 = ((value[1]).trim()).substring(0,1);
				int i = getColorNum(eval1);
				rts.applyFont(3, 4, fonts[i]);
				
				String eval2 = (value[2]).trim();
				int j = getColorNum(eval2);
				rts.applyFont(8, 9, fonts[j]);
			}
			cell.setCellValue(rts);
		}else{
			cell.setCellValue("无");
		}
	}
	public Integer getColorNum(String eval){
		int i  = 3;
		if("优".equals(eval)){
			i  = 0;
		}else if("良".equals(eval)){
			i  = 1;
		}else if("中".equals(eval)){
			i  = 2;
		}else if("差".equals(eval)){
			i  = 3;
		}
		return i;
		
	}
}
