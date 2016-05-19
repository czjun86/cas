package com.complaint.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.StaffAssessDao;
import com.complaint.dao.SysConfigDao;
import com.complaint.model.GroupManager;
import com.complaint.model.GroupScore;
import com.complaint.model.StaffAreas;
import com.complaint.model.StaffAssess;
import com.complaint.model.Sysconfig;
import com.complaint.utils.Constant;

/**
 * 人员考核环比
 * @author peng
 *
 */
@Service("staffAssessService")
public class StaffAssessService {

	private static final Logger logger = LoggerFactory.getLogger(StaffAssessService.class);
	
	@Autowired
	private  StaffAssessDao staffAssessDao;
	@Autowired
	private SysConfigDao sysConfigDao;
	
	public List<StaffAssess> getStaffAssess(Map<String, Object> map){
		this.staffAssessDao.getStaffAssess(map);
		List<StaffAssess> st=(List<StaffAssess>) map.get("depts");
		//List<StaffAssess> st = Test.getdata();
		return st;
	}
	/**
	 * 创建EXCEL
	 * @param path
	 */
	public void CreateExcel(String path,Map<String, Object> map ,String type){
		SXSSFWorkbook swb = new SXSSFWorkbook(100);
		//第一个sheet页
		Sheet sheet = swb.createSheet("投诉总量环比加扣分");
		CellStyle style = getTitleStyle(swb);
		
		List<StaffAssess> list = getStaffAssess(map);//查出信息
		
		Row row = sheet.createRow(0);//第一行创建标题
		row.setHeight((short)600);
		String[] titles = null;
		if(type.equals("2")){
			titles = new String[]{"大组","小组","累计实测率","累计实测率得分","月测试及时率","月测试及时率得分","累计解决率","累计解决率得分"
					,"累计工单滞留率","累计工单滞留率得分","累计工单驳回率","累计工单驳回率得分","累计工单超时率","累计工单超时率得分","累计工单重派率","累计工单重派率得分","累计重复投诉率","累计重复投诉率得分","累计工单升级率","累计工单升级率得分"
					,"月网络投诉工单量","月网络投诉工单量得分","小组KPI考核得分","小组其它加减分","小组综合得分","小组排名","其它加减分原因"};
		}else{
			titles = new String[]{"大组","小组","累计实测率","累计实测率得分","测试及时率","测试及时率得分","累计解决率","累计解决率得分"
					,"累计工单滞留率","累计工单滞留率得分","累计工单驳回率","累计工单驳回率得分","累计工单超时率","累计工单超时率得分","累计工单重派率","累计工单重派率得分","累计重复投诉率","累计重复投诉率得分","累计工单升级率","累计工单升级率得分"
					,"网络投诉工单量","网络投诉工单量得分","小组KPI考核得分","小组其它加减分","小组综合得分","小组排名","其它加减分原因"};
		}
		for(int i=0;i<titles.length;i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
		}
		endowValue(sheet,swb,list);//单元格赋值
		//第二个sheet页
		Sheet sheet1 = swb.createSheet("人员与区域统计数据");
		Map<String,Object> samap = new HashMap<String, Object>();
		if(type.equals("2")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar ca = Calendar.getInstance();   
			ca.set(Calendar.YEAR,Integer.parseInt(map.get("m_time").toString().split("-")[0]));
			ca.set(Calendar.MONTH,Integer.parseInt(map.get("m_time").toString().split("-")[1]) - 1);
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
			String e_time = format.format(ca.getTime());
			samap.put("s_time", map.get("m_time").toString() + "-01");
			samap.put("e_time", e_time);
		}else{
			samap.put("s_time", map.get("s_time"));
			samap.put("e_time", map.get("e_time"));
		}
		String acctime = this.sysConfigDao.queryData(Constant.RP_CENTER_CUMULATIVE_START_TIME);
		samap.put("acctime", acctime);
		List<StaffAreas> sa = this.staffAssessDao.getStaffAreas(samap);
		String[] ts = new String[]{"人员","区域","总量","升级投诉","超时","按时办结率","重复派单","重复派单率","重复投诉","重复投诉率","真正解决量"};
		Row row1 = sheet1.createRow(0);//第一行创建标题
		//创建标题
		for(int i=0;i<ts.length;i++){
			Cell cell = row1.createCell(i);
			cell.setCellValue(ts[i]);
			cell.setCellStyle(style);
		}
		CellStyle style1 = getStyle(swb);
		//人员与区域统计数据数据填充
		setStaffAreasValue(sheet1,sa,style1);
		try {
			FileOutputStream out = new FileOutputStream(path);
			swb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			logger.error("StaffAssessService get FileOutputStream",e);
		} catch (IOException e) {
			logger.error("StaffAssessService close FileOutputStream",e);
		}
	}
	/**
	 * 人员与区域统计数据数据填充
	 * @param sheet
	 * @param sa
	 */
	public static void setStaffAreasValue(Sheet sheet,List<StaffAreas> sa,CellStyle style){
		int count = 1;
		
		for(int i = 0; i < sa.size(); i ++){
			Row row = sheet.createRow(count);
			StaffAreas staff = sa.get(i);
			List<StaffAreas> list = staff.getList();
			int size = list.size();
			//合并单元格 
			sheet.addMergedRegion(new CellRangeAddress(count, count + size - 1, 0,0));
			//创建人员单元格 
			Cell cell = row.getCell(0);
			if(null == cell){
				cell = row.createCell(0);
			}
			cell.setCellValue(staff.getName());
			if(i == sa.size() - 1){
				Row last = sheet.createRow(count + size - 1);
				Cell lastCell = last.createCell(0);
				lastCell.setCellStyle(style);
			}
			cell.setCellStyle(style);
			//填充kpi值
			for (int j = 0; j < list.size(); j++) {
				int num = 1;
				StaffAreas sta = list.get(j);
				Row r = sheet.getRow(count + j);
				if(null == r){
					r = sheet.createRow(count + j);
				}
				Cell cell1 = r.createCell(num ++);//区域
				cell1.setCellValue(sta.getAreaname());
				cell1.setCellStyle(style);
				
				Cell cell2 = r.createCell(num ++);//总量
				cell2.setCellValue(sta.getCurr_serialno());
				cell2.setCellStyle(style);
				
				Cell cell3 = r.createCell(num ++);//升级投诉
				cell3.setCellValue(sta.getCurr_upgrade());
				cell3.setCellStyle(style);
				
				Cell cell4 = r.createCell(num ++);//超时
				cell4.setCellValue(sta.getCurr_over());
				cell4.setCellStyle(style);
				
				//(投诉网络总量-超时量)/投诉网络总量
				Cell cell5 = r.createCell(num ++);//按时办结率
				BigDecimal ontime_rate =  new BigDecimal(sta.getCurr_serialno().equals(0)?0:Float.valueOf(sta.getCurr_serialno() - sta.getCurr_over())/Float.valueOf(sta.getCurr_serialno())*100);
				cell5.setCellValue((ontime_rate.equals(BigDecimal.ZERO)?"0":ontime_rate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + "%");
				cell5.setCellStyle(style);
				
				Cell cell6 = r.createCell(num ++);//重复派单
				cell6.setCellValue(sta.getCurr_send());
				cell6.setCellStyle(style);
				
				Cell cell7 = r.createCell(num ++);//重复派单率
				BigDecimal curr_send_rate =  new BigDecimal(sta.getCurr_serialno().equals(0)?0:Float.valueOf(sta.getCurr_send())/Float.valueOf(sta.getCurr_serialno())*100);
				cell7.setCellValue((curr_send_rate.equals(BigDecimal.ZERO)?"0":curr_send_rate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + "%");
				cell7.setCellStyle(style);
				
				Cell cell8 = r.createCell(num ++);//重复投诉
				cell8.setCellValue(sta.getCurr_complaint());
				cell8.setCellStyle(style);
				
				Cell cell9 = r.createCell(num ++);//重复投诉率
				BigDecimal curr_complaint_rate =  new BigDecimal(sta.getCurr_serialno().equals(0)?0:Float.valueOf(sta.getCurr_complaint())/Float.valueOf(sta.getCurr_serialno())*100);
				cell9.setCellValue((curr_complaint_rate.equals(BigDecimal.ZERO)?"0":curr_complaint_rate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) + "%");
				cell9.setCellStyle(style);
				
				Cell cell10 = r.createCell(num ++);//真正解决量
				cell10.setCellValue(sta.getCurr_solve());
				cell10.setCellStyle(style);
			}
			
			count += size;
		}
	}
	/**
	 * Excel标题样式
	 */
	public CellStyle getTitleStyle(SXSSFWorkbook swb){
		CellStyle style = swb.createCellStyle();
		
		style.setWrapText(true);//自动换行
		
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
	 * 给单元格赋值
	 */
	public void endowValue(Sheet sheet,SXSSFWorkbook swb ,List<StaffAssess> list){
		CellStyle style = getStyle(swb);//一般样式
		
		for(int i =0 ;i<list.size() ;i++){
			StaffAssess sa =list.get(i);
			Row row = sheet.createRow(i+1);
			int count = 0;
			Cell cell0 = row.createCell(count++);
			cell0.setCellValue(sa.getGroup_big_name());//大组
			cell0.setCellStyle(style);
			
			Cell cell1 = row.createCell(count++);
			cell1.setCellValue(sa.getGroup_small_name());//小组
			cell1.setCellStyle(style);
			
			Cell cell2 = row.createCell(count++);
			cell2.setCellValue(sa.getTotal_test_rate()==null?"-":sa.getTotal_test_rate());//累计实测率
			cell2.setCellStyle(style);
			
			Cell cell2_s = row.createCell(count++);
			cell2_s.setCellValue(sa.getTest_score());//累计实测率得分
			cell2_s.setCellStyle(style);
			
			Cell cell3 = row.createCell(count++);
			cell3.setCellValue(sa.getCurr_test_timely()==null?"-":sa.getCurr_test_timely());//月测试及时率
			cell3.setCellStyle(style);
			
			Cell cell3_s = row.createCell(count++);
			cell3_s.setCellValue(sa.getOntime_score());//月测试及时率得分
			cell3_s.setCellStyle(style);
			
			Cell cell4 = row.createCell(count++);
			cell4.setCellValue(sa.getTotal_solve_rate()==null?"-":sa.getTotal_solve_rate());//累计解决率
			cell4.setCellStyle(style);
			
			Cell cell4_s = row.createCell(count++);
			cell4_s.setCellValue(sa.getSolve_score());//累计解决率得分
			cell4_s.setCellStyle(style);
			
			Cell cell5 = row.createCell(count++);
			cell5.setCellValue(sa.getTotal_delay_rate()==null?"-":sa.getTotal_delay_rate());//累计工单滞留率
			cell5.setCellStyle(style);
			
			Cell cell5_s = row.createCell(count++);
			cell5_s.setCellValue(sa.getDelay_score());//累计工单滞留率得分
			cell5_s.setCellStyle(style);
			
			Cell cell6 = row.createCell(count++);
			cell6.setCellValue(sa.getTotal_reject_rate()==null?"-":sa.getTotal_reject_rate());//累计工单驳回率
			cell6.setCellStyle(style);
			
			Cell cell6_s = row.createCell(count++);
			cell6_s.setCellValue(sa.getReject_score());//累计工单驳回率得分
			cell6_s.setCellStyle(style);
			
			Cell cell7 = row.createCell(count++);
			cell7.setCellValue(sa.getTotal_over_rate()==null?"-":sa.getTotal_over_rate());//累计工单超时率
			cell7.setCellStyle(style);
			
			Cell cell7_s = row.createCell(count++);
			cell7_s.setCellValue(sa.getOver_score());//累计工单超时率得分
			cell7_s.setCellStyle(style);
			
			Cell cell8 = row.createCell(count++);
			cell8.setCellValue(sa.getTotal_send_rate()==null?"-":sa.getTotal_send_rate());//累计工单重派率
			cell8.setCellStyle(style);
			
			Cell cell8_s = row.createCell(count++);
			cell8_s.setCellValue(sa.getSend_score());//累计工单重派率得分
			cell8_s.setCellStyle(style);
			
			Cell cell9 = row.createCell(count++);
			cell9.setCellValue(sa.getTotal_complaint_rate()==null?"-":sa.getTotal_complaint_rate());//累计重复投诉率
			cell9.setCellStyle(style);
			
			Cell cell9_s = row.createCell(count++);
			cell9_s.setCellValue(sa.getComplaint_score());//累计重复投诉率得分
			cell9_s.setCellStyle(style);
			
			Cell cell10 = row.createCell(count++);
			cell10.setCellValue(sa.getTotal_upgrade_rate()==null?"-":sa.getTotal_upgrade_rate());//累计工单升级率
			cell10.setCellStyle(style);
			
			Cell cell10_s = row.createCell(count++);
			cell10_s.setCellValue(sa.getUpgrade_score());//累计工单升级率得分
			cell10_s.setCellStyle(style);
			
			Cell cell11 = row.createCell(count++);
			cell11.setCellValue(sa.getComplaint()==null?"-":sa.getComplaint());//月网络投诉工单量
			cell11.setCellStyle(style);
			
			Cell cell11_s = row.createCell(count++);
			cell11_s.setCellValue(sa.getSerialno_score());//月网络投诉工单量得分
			cell11_s.setCellStyle(style);
			
			Cell cell12 = row.createCell(count++);
			cell12.setCellValue(sa.getGroup_kpi_score()==null?"-":sa.getGroup_kpi_score());//小组kpi得分
			cell12.setCellStyle(style);
			
			Cell cell13 = row.createCell(count++);
			cell13.setCellValue(sa.getGroup_plusMinus_score());//小组加减得分
			cell13.setCellStyle(style);
			
			Cell cell14 = row.createCell(count++);
			cell14.setCellValue(sa.getGroup_synthesis_score()==null?"-":sa.getGroup_synthesis_score());//小组综合得分
			cell14.setCellStyle(style);
			
			String str = null;
			if(sa.getGroup_rank()==null){
				str = "-";
			}else{
				str = sa.getGroup_rank().toString();
			}
			Cell cell15 = row.createCell(count++);
			cell15.setCellValue(str);//小组排名
			cell15.setCellStyle(style);
			
			Cell cell16 = row.createCell(count);
			cell16.setCellValue(sa.getGroup_plusMinus_cause()==null?"":sa.getGroup_plusMinus_cause());//小组加减分原因
			cell16.setCellStyle(style);
		}
	}
	
	public CellStyle getStyle(SXSSFWorkbook swb){
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
	
	public List<StaffAssess> getinfo(Map<String, Object> map){
		return getStaffAssess(map);
	}
	
	/**
	 * 查出累积起始时间
	 * @return
	 */
	public String getReportdate(){
		Sysconfig sysconfig =sysConfigDao.getAngleconfig(Constant.RP_CENTER_CUMULATIVE_START_TIME);
		return sysconfig.getConfigvalue();
	}
	
	/**
	 * 设置累计起始时间
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateDate(String time)throws Exception{
		Map map = new HashMap();
		map.put("configvalue", time);
		map.put("configkey", Constant.RP_CENTER_CUMULATIVE_START_TIME);
		sysConfigDao.saveAngleconfig(map);
		return 0;
	}
	
	/**
	 * 查出大组对应,小组对应分数
	 */
	public Map<String ,Object> getScore(){
		List<GroupManager> gms = staffAssessDao.getGroupScore();
		List<GroupScore> gss = new ArrayList<GroupScore>();
		int totalSize =0;
		GroupScore gsbig = null;//外层循环
		List<GroupScore> list = null;//存放每个大组中的小组
		
		GroupScore gssmall = null;//内层循环
		
		for(GroupManager gm:gms){
			boolean flag = false;//相同大组,不再加入集合
			for(GroupScore g:gss){
				if(g.getGroupId()==(gm.getGroup_big_id()==null?-1:gm.getGroup_big_id())){
					flag = true;
					break;
				}
			}
			if(flag){
				continue;
			}
			
			list = new ArrayList<GroupScore>();
			gsbig = new GroupScore();
			gsbig.setGroupId(gm.getGroup_big_id()==null?-1:gm.getGroup_big_id());
			String str = "";
			if(gm.getGroup_big_name()==null){
				str = "未归属大组";
			}else{
				str = gm.getGroup_big_name();
			}
			gsbig.setGroupName(str);
				for(GroupManager gmr:gms){
					if((gmr.getGroup_big_id()==null?-1:gmr.getGroup_big_id()) == (gm.getGroup_big_id()==null?-1:gm.getGroup_big_id())){
						gssmall = new GroupScore();
						gssmall.setGroupId(gmr.getGroup_small_id());
						gssmall.setGroupName(gmr.getGroup_small_name());
						gssmall.setScore(gmr.getScore()==null?0:gmr.getScore());
						list.add(gssmall);
					}
				}
			gsbig.setList(list);
			gsbig.setScore((double)list.size());//算大组格子宽度
			totalSize += list.size();//算总共格子个数
			gss.add(gsbig);
		}
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("size", totalSize);
		map.put("scoreMap",gss);
		return map;
	}
	
	/**
	 * 修改加减分
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateScore(String[] groupids,String[] scores)throws Exception{
		int msg = 1;
		if(groupids.length==scores.length){
			Map<String, Object> map=null;
			staffAssessDao.deleteScore();
			for(int i =0;i<groupids.length;i++){
				map = new HashMap<String, Object>();
				int groupid = Integer.parseInt(groupids[i]);
				double modified = getDoubleNum(scores[i]);
				map.put("groupid", groupid);
				map.put("modified", modified);
				staffAssessDao.updateScore(map);
				msg = 0;//判断数据有修改，并成功
			}
		}
		return msg;
	}
	
	/**
	 * 获取中心内部考核信息
	 */
	public Map<String ,List<Map>> getConfig(){
		Map<String ,List<Map>> map = new HashMap<String ,List<Map>>();
		List<Map> steps = staffAssessDao.getCenterStepConfig();//内部考核步长配置
		List<Map> report = staffAssessDao.getCenterReportConfig();//内部考核基本配置
		map.put("steps", steps);
		map.put("report", report);
		return map;
	}
	
	/**
	 * 保存配置信息
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer saveConfig(String svgstep ,String annstep ,String basic ,String kpi)throws Exception{
		int msg = 0;
		Map map =null;
			String [] svgsteps = svgstep.split(",");
			String [] annsteps = annstep.split(",");
			String [] kpis =kpi.split(",");
			for(int i=0;i<svgsteps.length;i++){
				double sv = getDoubleNum(svgsteps[i]);
				double an = getDoubleNum(annsteps[i]);
				Integer kpiid = Integer.parseInt(kpis[i]);
				map = new HashMap();
				map.put("svg_step", sv);
				map.put("annular_step", an);
				map.put("kpi", kpiid);
				staffAssessDao.saveCenterStepConfig(map);
			}
			Map oldmap = new HashMap();
			List<Map> report = staffAssessDao.getCenterReportConfig();//内部考核基本配置
			String [] basics = basic.split(",");
			for(int i=0;i<basics.length;i++){
				double db = getDoubleNum(basics[i]);//转化数字报错则数据有错
				String bs =String.valueOf(db);
				oldmap = report.get(i);//获取ID值
				map = new HashMap();
				map.put("val", bs);
				map.put("id", oldmap.get("ID"));
				staffAssessDao.saveCenterReportConfig(map);
			}
		return msg;
	}
	
	/**
	 * String转double保留两位小数
	 */
	public double getDoubleNum(String str)throws Exception{
		double d = Double.parseDouble(str);
		BigDecimal dd = new BigDecimal(d);
		d = dd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		return d;
	}
}
