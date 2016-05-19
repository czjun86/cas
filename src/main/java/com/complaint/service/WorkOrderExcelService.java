package com.complaint.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.action.vo.VoBean;
import com.complaint.dao.WorkOrderDao;
import com.complaint.model.WorkOrderExport;

@Service("workOrderExcelService")
public class WorkOrderExcelService {

private static final Logger logger = LoggerFactory.getLogger(WorkOrderExcelService.class);
	
	@Autowired
	private WorkOrderDao workOrderDao;
	
	public void getWorkExport(VoBean vo,String s_id,String path) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("serialno", vo.getSernos());
		param.put("isDeal", vo.getIsDeal());
		param.put("testphone", vo.getTestphone());
		param.put("areaids", vo.getAreaids());
		param.put("senceids", vo.getSenceids());
		param.put("testtype", vo.getTesttype());
		param.put("datatype", vo.getDatatype());
		param.put("jobtype", vo.getJobtype());
		param.put("startTime", vo.getStartTime()+" 00:00:00");
		param.put("endTime", vo.getEndTime()+" 23:59:59");
		param.put("testnet", vo.getTestnet());
		param.put("inside",vo.getInside());
		param.put("nettype", vo.getNettype());
		param.put("verify", vo.getVerify());
		param.put("s_id", s_id);
		param.put("workerOrderNetName", vo.getWorkerOrderNetName());

		if(vo.getSenceids()!=null&&vo.getSenceids().indexOf("-1")>=0){
			param.put("stype", "-1");
		}
		//拆分测试网络
		if(vo.getTestnet()!=null&&vo.getTestnet().indexOf("-1")>=0){
			param.put("testnet", "-1");
		}
		//拆分业务类型
		
		if(vo.getTesttype()!=null&&vo.getTesttype().indexOf("-1")<0)
		{
		
			String str[]=vo.getTesttype().split(",");
			List strlist=Arrays.asList(str);
			param.put("bustype", "1");
			//测试类型
			String tt=null;
			if(strlist.contains("1")){
				tt+=",1";
			}
			if(strlist.contains("2")){
				tt+=",2";			
						}
			if(strlist.contains("3")){
				tt+=",3";
			}
			param.put("tt", tt);
			//长短呼
			String yy=null;
			if(strlist.contains("4")){
				yy=",2";
			}
			if(strlist.contains("5")){
				yy+=",1";			
						}
			//上下行
			param.put("yy", yy);
			String ff=null;
			if(strlist.contains("6")){
				ff=",1";
			}
			if(strlist.contains("7")){
				ff+=",2";			
						}
			param.put("ff", ff);
		}else{
			param.put("bustype", "-1");
		}
		List<WorkOrderExport> list = this.workOrderDao.queryWorkOrderExport(param);
		createExcel(list,path);
	}
	
	/**
	 * 生成导出Excel
	 * @param list
	 * @param path
	 */
	private void createExcel(List<WorkOrderExport> list ,String path){
		//创建excel和sheet
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);
		Sheet sheet2G = wb.createSheet("2G");
		Sheet sheet3G = wb.createSheet("3G");
		Sheet sheet4G = wb.createSheet("4G");
		//内容样式
		CellStyle style = cellStyle(wb);
		//每页title
		createTitle(sheet2G ,"2G",style);
		createTitle(sheet3G ,"3G",style);
		createTitle(sheet4G ,"4G",style);
		//各种网络写入行数
		int gsmid =1;
		int wcdmaid =1;
		int lteid =1;
		Row row = null;
		for(WorkOrderExport workOrder:list){
			if("2".equals(workOrder.getNetType())){
				row = sheet2G.createRow(gsmid++);
				fill(row , workOrder ,"2G",style);
			}else if("3".equals(workOrder.getNetType())){
				row = sheet3G.createRow(wcdmaid++);
				fill(row , workOrder ,"3G",style);
			}else if("4".equals(workOrder.getNetType())){
				row = sheet4G.createRow(lteid++);
				fill(row , workOrder ,"4G",style);
			}
		}
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
	 * 创建每页title
	 */
	private void createTitle(Sheet sheet ,String type ,CellStyle style){
		Row row = sheet.createRow(0);
		Map<Integer , Integer> sizeMap = new HashMap<Integer , Integer>();
		String[] normal = {"投诉工单号","区域","网络类型","重要性分类","系统接单时间","Longitude","latitude"};
		String[] col = null;
		int i=0;
		for(String name:normal){
			setSizeMap(name ,i ,sizeMap);
			Cell cell = row.createCell(i++);
			cell.setCellValue(name);
			cell.setCellStyle(style);
		}
		
		if("2G".equals(type)){
			col = new String[]{"LAC、CELLID、BCCH","RxLev(max)","RxLev(min)","RxLev(avg)","RxQual(max)","RxQual(min)","RxQual(avg)"};
		}else if("3G".equals(type)){
			col = new String[]{"LAC、CELLID、PSC","RSCP(max)","RSCP(min)","RSCP(avg)","EC/NO(max)","EC/NO(min)","EC/NO(avg)"};
		}else if("4G".equals(type)){
			col = new String[]{"TAC、CELLID、PCI","RSRP(max)","RSRP(min)","RSRP(avg)","RSRQ(max)","RSRQ(min)","RSRQ(avg)","SINR(max)","SINR(min)","SINR(avg)"};
		}
		
		for(String word:col){
			setSizeMap(word ,i ,sizeMap);
			Cell cell = row.createCell(i++);
			cell.setCellValue(word);
			cell.setCellStyle(style);
		}
		
		// 使单元格自动适应内容长度
		for (int j = 0; j < i; j++) {
			sheet.setColumnWidth(j,(sizeMap.get(j)+11)*256);
		}
	}
	
	private void fill(Row row ,WorkOrderExport workOrder ,String type ,CellStyle style){
		int i = 0;
		Cell serialno = row.createCell(i++);
		serialno.setCellValue(workOrder.getSerialno()!=null?workOrder.getSerialno():"");
		serialno.setCellStyle(style);
		
		Cell areaname = row.createCell(i++);
		areaname.setCellValue(workOrder.getAreaName()!=null?workOrder.getAreaName():"");
		areaname.setCellStyle(style);
		
		Cell netType = row.createCell(i++);
		netType.setCellValue(workOrder.getNetWorktype()!=null?workOrder.getNetWorktype():"");
		netType.setCellStyle(style);
		
		Cell usersBrand = row.createCell(i++);
		usersBrand.setCellValue(workOrder.getUsersBrand()!=null?workOrder.getUsersBrand():"");
		usersBrand.setCellStyle(style);
		
		SimpleDateFormat time=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Cell submitDatetime = row.createCell(i++);
		submitDatetime.setCellValue(workOrder.getSubmitDatetime()!=null?time.format(workOrder.getSubmitDatetime()):"");
		submitDatetime.setCellStyle(style);
		
		Cell lng = row.createCell(i++);
		lng.setCellValue(validate(workOrder.getLng()));
		lng.setCellStyle(style);
		
		Cell lat = row.createCell(i++);
		lat.setCellValue(validate(workOrder.getLat()));
		lat.setCellStyle(style);
		
		String lacCid = workOrder.getCell()!=null?workOrder.getCell():"";
		lacCid = lacCid.replaceAll("@", "、");
		Cell cell = row.createCell(i++);
		cell.setCellValue(lacCid);
		cell.setCellStyle(style);
		
		if("2G".equals(type)){
			Cell maxRxlev = row.createCell(i++);
			maxRxlev.setCellValue(validate(workOrder.getMaxRxlev()));
			maxRxlev.setCellStyle(style);
			
			Cell minRxlev = row.createCell(i++);
			minRxlev.setCellValue(validate(workOrder.getMinRxlev()));
			minRxlev.setCellStyle(style);
			
			Cell avgRxlev = row.createCell(i++);
			avgRxlev.setCellValue(validate(workOrder.getAvgRxlev()));
			avgRxlev.setCellStyle(style);
			
			Cell maxRxqual = row.createCell(i++);
			maxRxqual.setCellValue(validate(workOrder.getMaxRxqual()));
			maxRxqual.setCellStyle(style);
			
			Cell minRxqual = row.createCell(i++);
			minRxqual.setCellValue(validate(workOrder.getMinRxqual()));
			minRxqual.setCellStyle(style);
			
			Cell avgRxqual = row.createCell(i++);
			avgRxqual.setCellValue(validate(workOrder.getAvgRxqual()));
			avgRxqual.setCellStyle(style);
		}else if("3G".equals(type)){
			Cell maxRscp = row.createCell(i++);
			maxRscp.setCellValue(validate(workOrder.getMaxRscp()));
			maxRscp.setCellStyle(style);
			
			Cell minRscp = row.createCell(i++);
			minRscp.setCellValue(validate(workOrder.getMinRscp()));
			minRscp.setCellStyle(style);
			
			Cell avgRscp = row.createCell(i++);
			avgRscp.setCellValue(validate(workOrder.getAvgRscp()));
			avgRscp.setCellStyle(style);
			
			Cell maxEcno = row.createCell(i++);
			maxEcno.setCellValue(validate(workOrder.getMaxEcno()));
			maxEcno.setCellStyle(style);
			
			Cell minEcno = row.createCell(i++);
			minEcno.setCellValue(validate(workOrder.getMinEcno()));
			minEcno.setCellStyle(style);
			
			Cell avgEcno = row.createCell(i++);
			avgEcno.setCellValue(validate(workOrder.getAvgEcno()));
			avgEcno.setCellStyle(style);
		}else if("4G".equals(type)){
			Cell maxRsrp = row.createCell(i++);
			maxRsrp.setCellValue(validate(workOrder.getMaxRsrp()));
			maxRsrp.setCellStyle(style);
			
			Cell minRsrp = row.createCell(i++);
			minRsrp.setCellValue(validate(workOrder.getMinRsrp()));
			minRsrp.setCellStyle(style);
			
			Cell avgRsrp = row.createCell(i++);
			avgRsrp.setCellValue(validate(workOrder.getAvgRsrp()));
			avgRsrp.setCellStyle(style);
			
			Cell maxRsrq = row.createCell(i++);
			maxRsrq.setCellValue(validate(workOrder.getMaxRsrq()));
			maxRsrq.setCellStyle(style);
			
			Cell minRsrq = row.createCell(i++);
			minRsrq.setCellValue(validate(workOrder.getMinRsrq()));
			minRsrq.setCellStyle(style);
			
			Cell avgRsrq = row.createCell(i++);
			avgRsrq.setCellValue(validate(workOrder.getAvgRsrq()));
			avgRsrq.setCellStyle(style);
			
			Cell maxSnr = row.createCell(i++);
			maxSnr.setCellValue(validate(workOrder.getMaxSnr()));
			maxSnr.setCellStyle(style);
			
			Cell minSnr = row.createCell(i++);
			minSnr.setCellValue(validate(workOrder.getMinSnr()));
			minSnr.setCellStyle(style);
			
			Cell avgSnr = row.createCell(i++);
			avgSnr.setCellValue(validate(workOrder.getAvgSnr()));
			avgSnr.setCellStyle(style);
		}
	}
	
	/**
	 * 分析sheet样式
	 */
	public CellStyle cellStyle(SXSSFWorkbook swb){
		CellStyle style = swb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		DataFormat format = swb.createDataFormat();  
		style.setDataFormat(format.getFormat("@"));
		return style;
	}
	
	/**
	 * 生成16位MD5
	 * @param id
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public String getMD5(String str) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");   
		md.update(str.getBytes());
		byte b[] = md.digest();
		int i =0;
		StringBuffer buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if(i<0) i+= 256;
			if(i<16)
			buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().substring(8,24);//16位的加密
	}
	
	/**
	 * 判断当前内容长度大于存放长度就加入map
	 */
	private void setSizeMap(String val ,Integer index ,Map<Integer ,Integer> map){
		try {
			if(val != null){
				Integer oldlength = map.get(index);
				Integer newlength = strByte(val);
				if(oldlength==null || newlength>oldlength){
					map.put(index, newlength);
				}
			}
		} catch (Exception e) {
			logger.error("excel report count title length" ,e);
		}
	}
	
	/**
	 * 计算中英文混合的字节长度
	 * @throws Exception 
	 */
	private Integer strByte(String str) throws Exception{
		if(str!=null&&!("".equals(str))){
			str=new String(str.getBytes("gb2312"),"iso-8859-1");
			return str.length();
		}else{
			return 0;
		}
	}
	
	private String validate(Double a){
		String str ="-";
		if(a!=null && a!=-9999){
			if(a!=-9998){
				str = String.valueOf(a);
			}else{
				str = "N/A";
			}
		}
		return str;
	}
	
	private String validate(String a){
		String str ="-";
		if(a!=null && !"-9999".equals(a)){
			if(!"-9998".equals(a)){
				str = a;
			}else{
				str = "N/A";
			}
		}
		return str;
	}
}
