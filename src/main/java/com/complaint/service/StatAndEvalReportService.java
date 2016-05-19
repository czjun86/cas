package com.complaint.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.complaint.model.ComplainEvaluate;
import com.complaint.model.ComplainStatistics;
import com.complaint.model.QualTopAndLast;
import com.complaint.model.MostTestRate;
import com.complaint.model.QualtyReport;
import com.complaint.utils.Constant;
import com.complaint.utils.PatternUtil;
import com.complaint.utils.SpringStoredProce;
@Service("statAndEvalReportService")
public class StatAndEvalReportService {
	private static final Logger logger = LoggerFactory
			.getLogger(StatAndEvalReportService.class);
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 调用存储过程 需要传入WebApplicationContext,storedProcName,param
	 * WebApplicationContext根据request得到
	 * storedProcName存储过程的包和类名
	 * param参数，由1,2,3---日周月  起始时间格式2013-8-15，结束时间格式2013-8-16
	 * @param ctx
	 * @param param
	 * @return
	 */
	public ResultSet getStoreProce(WebApplicationContext ctx ,String storedProcName ,Map<String,Object> params){
//		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		DataSource ds = ctx.getBean("dataSource", DataSource.class);
//		final String storedProcName = "gradekpi_package.gradekpi_proc"; 
		SpringStoredProce ssp = new SpringStoredProce();
		ResultSet rs=null;
		try {
			rs = ssp.execute(storedProcName, params, ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 处理传入数据，调用准备存储过程
	 */
	public  Map getAllStatAndEval(WebApplicationContext ctx ,Map<String,Object> params){
			//初始化筛选类
			MostTestRate mostTestRate = new MostTestRate();
			Map map1 = this.top(ctx, params);
			Map map2 = this.bottom(ctx, params);
			ComplainStatistics stat = (ComplainStatistics) map1.get("stat");
			ComplainEvaluate eval = (ComplainEvaluate) map2.get("eval");
			
			//查询测试报告累计时间
			String reportdate = sysConfigService.getValue(Constant.REPORTDATE);
			//查询累计网络工单投诉量

	    	Map<String, Object> param = new HashMap<String, Object>();
			param.put("systime", reportdate);
			param.put("endtime", params.get("endtime")+" 23:59:59");
			param.put("p_area_id", params.get("queryAreaIds"));
			List<Map> list3=sysConfigService.getTotalSer(param);
			//新增改善比例
				LinkedList<String> gsmRank = new LinkedList<String>();
				LinkedList<String> wcdmaRank = new LinkedList<String>();
				for(int i = 0;i<eval.getArea_id().length;i++){
					int total = 0;
					for(int j=0;j<list3.size();j++)
					{
						if(eval.getArea_id()[i]== ((BigDecimal)list3.get(j).get("AREA_ID")).intValue()){
							total=((BigDecimal)list3.get(j).get("TOTAL_WORKORDER")).intValue();
						}
					}
					if(total!=0){
						double val2g = eval.getComp_impr_value_2g()[i];
						double val3g = eval.getComp_impr_value_3g()[i];
						gsmRank.add(PatternUtil.getDouble((double)(val2g*100000/total)/1000));
						wcdmaRank.add(PatternUtil.getDouble((double)(val3g*100000/total)/1000));
					}else{
						gsmRank.add("0%");
						wcdmaRank.add("0%");
					}
				}
				wcdmaRank.toArray(eval.getComp_impr_ratio_3g());
				gsmRank.toArray(eval.getComp_impr_ratio_2g());
		if(stat.getArea_id()!=null&&eval.getArea_id()!=null){
			if(eval.getArea_id().length == stat.getArea_id().length){//防止上下两个存储过程长度不一样，导致下方判断最大最下值时下标越界
//				当月实测量最大的区域是xx；累计测试率最高的区域是yy；当月实测量最少的区域是zz；累计实测率最低的区域是mm
//			当月实测量最大
				List<Integer> currTestMax = this.getMax(stat.getCurr_test());
				if(currTestMax.size()>1){
					mostTestRate.setCurr_max_test(stat.getArea_name()[this.LastMax(stat.getTotal_test(),currTestMax)]);
				}else if(currTestMax.size()==1){
					mostTestRate.setCurr_max_test(stat.getArea_name()[currTestMax.get(0)]);
				}
	//			累计测试率最高
				List<Integer> totalTestMax = this.getMax(stat.getTotal_test_rate());
				if(totalTestMax.size()>0){
					mostTestRate.setTotal_max_test(stat.getArea_name()[totalTestMax.get(0)]);
				}
	//			当月测试量最少
				List<Integer> currTestMin = this.getMin(stat.getCurr_test());
				if(currTestMin.size()>1){
					mostTestRate.setCurr_min_test(stat.getArea_name()[this.LastMin(stat.getTotal_test(),currTestMin)]);
				}else if(currTestMin.size()>0){
					mostTestRate.setCurr_min_test(stat.getArea_name()[currTestMin.get(0)]);
				}
	//			累积测试率最小
				List<Integer> totalTestMin = this.getMin(stat.getTotal_test_rate());
				if(totalTestMin.size()>0){
					mostTestRate.setTotal_min_test(stat.getArea_name()[totalTestMin.get(0)]);
				}
//				综合评价最好的区域是uu；综合改善最大的区域是tt；综合评价最差的区域是ww；综合改善最低的区域是bb

//			评价最好3g
				List<Integer> comp_eval_3g_max = this.evalMaxValue(eval.getComp_eval_3g_a(),eval.getComp_eval_3g_b(),eval.getComp_eval_3g_c(),eval.getComp_eval_3g_d());
				if(comp_eval_3g_max.size()>0){
					mostTestRate.setComp_eval_3g_max(stat.getArea_name()[comp_eval_3g_max.get(0)]);
				}
//			评价最好2g
				List<Integer> comp_eval_2g_max = this.evalMaxValue(eval.getComp_eval_2g_a(),eval.getComp_eval_2g_b(),eval.getComp_eval_2g_c(),eval.getComp_eval_2g_d());
				if(comp_eval_2g_max.size()>0){
					mostTestRate.setComp_eval_2g_max(stat.getArea_name()[comp_eval_2g_max.get(0)]);
				}
//			评价最差3g
				List<Integer> comp_eval_3g_min = this.evalMinValue(eval.getComp_eval_3g_a(),eval.getComp_eval_3g_b(),eval.getComp_eval_3g_c(),eval.getComp_eval_3g_d());
				if(comp_eval_3g_min.size()>0){
					mostTestRate.setComp_eval_3g_min(stat.getArea_name()[comp_eval_3g_min.get(0)]);
				}
//			评价最差2g
				List<Integer> comp_eval_2g_min = this.evalMinValue(eval.getComp_eval_2g_a(),eval.getComp_eval_2g_b(),eval.getComp_eval_2g_c(),eval.getComp_eval_2g_d());
				if(comp_eval_2g_min.size()>0){
					mostTestRate.setComp_eval_2g_min(stat.getArea_name()[comp_eval_2g_min.get(0)]);
				}
//			综合改善最好3g
				List<Integer> comp_impr_3g_max = this.getMax(eval.getComp_impr_value_3g());
				if(comp_impr_3g_max.size()>0){
					mostTestRate.setComp_impr_3g_max(stat.getArea_name()[comp_impr_3g_max.get(0)]);
				}
//			综合改善最好2g
				List<Integer> comp_impr_2g_max = this.getMax(eval.getComp_impr_value_2g());
				if(comp_impr_2g_max.size()>0){
					mostTestRate.setComp_impr_2g_max(stat.getArea_name()[comp_impr_2g_max.get(0)]);
				}
//			综合改善最差3g
				List<Integer> comp_impr_3g_min = this.getMin(eval.getComp_impr_value_3g());
				if(comp_impr_3g_min.size()>0){
					mostTestRate.setComp_impr_3g_min(stat.getArea_name()[comp_impr_3g_min.get(0)]);
				}
//			综合改善最差2g
				List<Integer> comp_impr_2g_min = this.getMin(eval.getComp_impr_value_2g());
				if(comp_impr_2g_min.size()>0){
					mostTestRate.setComp_impr_2g_min(stat.getArea_name()[comp_impr_2g_min.get(0)]);
				}
//			最好及时率
				List<Integer> time_rate_max = this.getMax(eval.getTimely_rate());
				if(time_rate_max.size()>0){
					mostTestRate.setTimely_rate_max(stat.getArea_name()[time_rate_max.get(0)]);
				}
//			最差及时率
				List<Integer> time_rate_min = this.getMin(eval.getTimely_rate());
				if(time_rate_min.size()>0){
					mostTestRate.setTimely_rate_min(stat.getArea_name()[time_rate_min.get(0)]);
				}
			}else{
				mostTestRate.setTotal_min_test("无");
				mostTestRate.setCurr_min_test("无");
				mostTestRate.setTotal_max_test("无");
				mostTestRate.setCurr_max_test("无");
				mostTestRate.setComp_eval_3g_max("无");
				mostTestRate.setComp_eval_2g_max("无");
				mostTestRate.setComp_eval_3g_min("无");
				mostTestRate.setComp_eval_2g_min("无");
				mostTestRate.setComp_impr_3g_max("无");
				mostTestRate.setComp_impr_2g_max("无");
				mostTestRate.setComp_impr_3g_min("无");
				mostTestRate.setComp_impr_2g_min("无");
				mostTestRate.setTimely_rate_max("无");
				mostTestRate.setTimely_rate_min("无");
			}
		}
//		获取所有最大值
//		ReportMaxValue reportMaxValue = getAllMaxValue(stat,eval);
		Map lastMap = new HashMap();
//		lastMap.put("reportMaxValue", reportMaxValue);
		lastMap.put("mostTestRate", mostTestRate);
		lastMap.put("stat", stat);
		lastMap.put("eval", eval);
		return lastMap;
}
	
	/**
	 * 查出最大值下标
	 */
	public List<Integer> getMax(Integer [] num){
		List<Integer> list = new ArrayList<Integer>();
		int res =0;
		int index = 0;
		for(int i =0;i<num.length;i++){
			if(i==0){
				res=num[i];
			}else{
				if(res<num[i]){
					res=num[i];
				}
			}
		}
		for(int j =0;j<num.length;j++){
			if(num[j]==res){
				index =j;
				list.add(index);
			}
		}
		return list;
	}
	public List<Integer> getMax(Double [] num){
		List<Integer> list = new ArrayList<Integer>();
		double res =0;
		int index = 0;
		for(int i =0;i<num.length;i++){
			if(i==0){
				res=num[i];
			}else{
				if(res<num[i]){
					res=num[i];
				}
			}
		}
		for(int j =0;j<num.length;j++){
			if(num[j]==res){
				index =j;
				list.add(index);
			}
		}
		return list;
	}
	/**
	 * String类型查出最大值下标
	 */
	public List<Integer> getMax(String [] num){
		List<Integer> list = new ArrayList<Integer>();
		double res =0;
		int index = 0;
		double curr_num=0;
		for(int i =0;i<num.length;i++){
			if(!(num[i].startsWith("%"))){
				curr_num=Double.parseDouble(num[i].substring(0,num[i].length()-1));	
			}else{
				curr_num=0;
			}
			if(i==0){
				res=curr_num;
			}else{
				if(compare(res,curr_num)==1){
					res=curr_num;
				}
			}
		}
		for(int j =0;j<num.length;j++){
			curr_num=0;
			String tem =num[j];
			if(!tem.startsWith("%")){
				curr_num = Double.parseDouble(tem.substring(0,tem.length()-1));
			}else{
				curr_num=0;
			}
			if(compare(curr_num,res)==0){
				index =j;
				list.add(index);
			}
		}
		return list;
	}
	/**
	 * 在最大值并列的情况下，传入下标和数组，在比较其他项
	 */
	public Integer LastMax(Integer[] num,List<Integer> list){
		int index = 0;
		int currNum =0;
		for(int i =0;i<list.size();i++){
			if(i==0){
				index = list.get(i);
				currNum =num[index];
			}else{
				if(currNum<num[list.get(i)]){
					index = list.get(i);
					currNum=num[index];
				}
			}
		}
		return index;
	}
	/**
	 * 查出最小值下标
	 */
	public List<Integer> getMin(Integer [] num){
		List<Integer> list = new ArrayList<Integer>();
		int res =0;
		int index =0;
		for(int i =0;i<num.length;i++){
			if(i==0){
				res=num[i];
			}else{
				if(res>num[i]){
					res=num[i];
				}
			}
		}
		for(int j =0;j<num.length;j++){
			if(num[j]==res){
				index =j;
				list.add(index);
			}
		}
		return list;
	}
	
	public List<Integer> getMin(Double [] num){
		List<Integer> list = new ArrayList<Integer>();
		double res =0;
		int index =0;
		for(int i =0;i<num.length;i++){
			if(i==0){
				res=num[i];
			}else{
				if(res>num[i]){
					res=num[i];
				}
			}
		}
		for(int j =0;j<num.length;j++){
			if(num[j]==res){
				index =j;
				list.add(index);
			}
		}
		return list;
	}
	/**
	 * 查出最小值下标
	 */
	public List<Integer> getMin(String [] num){
		List<Integer> list = new ArrayList<Integer>();
		double res =0;
		int index =0;
		double curr_num=0;
		for(int i =0;i<num.length;i++){
			if(!(num[i].startsWith("%"))){
				curr_num=Double.parseDouble(num[i].substring(0,num[i].length()-1));	
			}else{
				curr_num=0;
			}
			if(i==0){
				res=curr_num;
			}else{
				if(compare(res,curr_num)==2){
					res=curr_num;
				}
			}
		}
		for(int j =0;j<num.length;j++){
			curr_num = 0;
			String tem = num[j];
			if(!tem.startsWith("%")){
				curr_num=Double.parseDouble(tem.substring(0,tem.length()-1));
			}else{
				curr_num = 0;
			}
			if(compare(curr_num,res)==0){
				index =j;
				list.add(index);
			}
		}
		return list;
	}
	/**
	 * 在最小值并列的情况下，传入下标和数组，在比较其他项
	 */
	public Integer LastMin(Integer[] num,List<Integer> list){
		int index = 0;
		int currNum =0;
		for(int i =0;i<list.size();i++){
			if(i==0){
				index = list.get(i);
				currNum = num[index];
			}else{
				if(currNum>num[list.get(i)]){
					index = list.get(i);
					currNum=num[index];
				}
			}
		}
		return index;
	}

	/**
	 * 评价值求最大
	 */
	public List<Integer> evalMaxValue(Integer[] a,Integer[] b,Integer[] c,Integer[] d){
		List<Integer> max = new ArrayList<Integer>();
		List<Integer> list = new ArrayList<Integer>();
		int num=0;
		int maxNum=0;
		for(int i =0;i<a.length;i++){
			num = a[i]*4+b[i]*2+c[i]*1+d[i]*(-4);
//			所有最大值集合
			list.add(num);
//			算出最大值maxNum
			if(i!=0){
				if(num>maxNum){
					maxNum = num;
				}
			}else{
				maxNum = num;
			}
		}
		for(int j =0;j<list.size();j++){
			if(maxNum==list.get(j)){
				max.add(j);
			}
		}
		return max;
	}
	
	/**
	 * 评价值求最小
	 */
	public List<Integer> evalMinValue(Integer[] a,Integer[] b,Integer[] c,Integer[] d){
		List<Integer> min = new ArrayList<Integer>();
		List<Integer> list = new ArrayList<Integer>();
		int num=0;
		int minNum=0;
		for(int i =0;i<a.length;i++){
			num = a[i]*4+b[i]*2+c[i]*1+d[i]*(-4);
//			所有最小值集合
			list.add(num);
//			算出最小值maxNum
			if(i!=0){
				if(num<minNum){
					minNum = num;
				}
			}else{
				minNum = num;
			}
		}
		for(int j =0;j<list.size();j++){
			if(minNum==list.get(j)){
				min.add(j);
			}
		}
		return min;
	}
	
	
	/**
	 * double类型大小比较a，b比较大小a=b返回0，a<b返回1，a>b返回2
	 */
	public int compare(double a,double b){
		BigDecimal val1 = new BigDecimal(a);
		BigDecimal val2 = new BigDecimal(b);
		int result =0;
		if (val1.compareTo(val2) == 0) {
//			等于返回0
			result = 0;
		}
		if (val1.compareTo(val2) < 0) {
//			小于返回1
			result = 1;
		}
		if (val1.compareTo(val2) > 0) {
//			大于返回2
			result = 2;
		}
		return result;
	}
	/**
	 * 处理测试率只有%而不是0%
	 */
	public String TotalRate(String totalRate){
		String currRate=totalRate;
		if(totalRate.startsWith("%")){
			currRate="0%";
		}
		return currRate;
	}
	
	/**
	 * 报表上端
	 * @param ctx
	 * @param storedProcName
	 * @param params
	 * @return
	 */
	public Map top(WebApplicationContext ctx ,Map<String,Object> params){
		Map map = new HashMap();
//		查出统计数据
		final String storedProcNameStat = "pro_cas_stat";
		ResultSet setStat = this.getStoreProce(ctx,storedProcNameStat,params);
		//ResultSet countStat = setStat;
		ComplainStatistics stat = new ComplainStatistics();
		
		
//		创建list接受结果
		LinkedList<String> statQuery_date = new LinkedList<String>();
		LinkedList<Integer> statArea_id = new LinkedList<Integer>();
		LinkedList<String> statArea_name = new LinkedList<String>();
		LinkedList<Integer> statTotal_workorder = new LinkedList<Integer>();
		LinkedList<Integer> statCurr_workorder = new LinkedList<Integer>();
		LinkedList<Integer> statTotal_test = new LinkedList<Integer>();
		LinkedList<Integer> statCurr_test = new LinkedList<Integer>();
		LinkedList<String> statTotal_test_rate = new LinkedList<String>();
		LinkedList<Integer> statCurr_test_rank = new LinkedList<Integer>();
		LinkedList<Integer> statTotal_test_rate_rank = new LinkedList<Integer>();
		LinkedList<Integer> statCurr_in_test = new LinkedList<Integer>();
		LinkedList<Integer> statCurr_out_test = new LinkedList<Integer>();
		


//		遍历实例装入对象
			try {
				while(setStat.next()){
					statQuery_date.add(setStat.getString("query_date"));
					statArea_id.add(setStat.getInt("area_id"));
					statArea_name.add(setStat.getString("areaname"));
					statTotal_workorder.add(setStat.getInt("total_workorder"));
					statCurr_workorder.add(setStat.getInt("curr_workorder"));
					statTotal_test.add(setStat.getInt("total_test"));
					statCurr_test.add(setStat.getInt("curr_test"));
					statTotal_test_rate.add(TotalRate(setStat.getString("total_test_rate")));
					statCurr_test_rank.add(setStat.getInt("curr_test_rank"));
					statTotal_test_rate_rank.add(setStat.getInt("total_test_rate_rank"));
					statCurr_in_test.add(setStat.getInt("curr_in_test"));
					statCurr_out_test.add(setStat.getInt("curr_out_test"));
				}
//				初始化eval的数组
				int statSize = statArea_id.size();
					stat.setQuery_date(new String[statSize]);
					stat.setArea_id(new Integer[statSize]);
					stat.setArea_name(new String[statSize]);
					stat.setTotal_workorder(new Integer[statSize]);
					stat.setCurr_workorder(new Integer[statSize]);
					stat.setTotal_test(new Integer[statSize]);
					stat.setCurr_test(new Integer[statSize]);
					stat.setTotal_test_rate(new String[statSize]);
					stat.setCurr_test_rank(new Integer[statSize]);
					stat.setTotal_test_rate_rank(new Integer[statSize]);
					stat.setCurr_in_test(new Integer[statSize]);
					stat.setCurr_out_test(new Integer[statSize]);
					
//				eval数组中放入数据
					statQuery_date.toArray(stat.getQuery_date());
					statArea_id.toArray(stat.getArea_id());
					statArea_name.toArray(stat.getArea_name());
					statTotal_workorder.toArray(stat.getTotal_workorder());
					statCurr_workorder.toArray(stat.getCurr_workorder());
					statTotal_test.toArray(stat.getTotal_test());
					statCurr_test.toArray(stat.getCurr_test());
					statTotal_test_rate.toArray(stat.getTotal_test_rate());
					statCurr_test_rank.toArray(stat.getCurr_test_rank());
					statTotal_test_rate_rank.toArray(stat.getTotal_test_rate_rank());
					statCurr_in_test.toArray(stat.getCurr_in_test());
					statCurr_out_test.toArray(stat.getCurr_out_test());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			map.put("stat", stat);
			try {
				setStat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return map;
	}
	
	public Map bottom(WebApplicationContext ctx ,Map<String,Object> params){
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
		mapParam.put("type", params.get("type"));
		mapParam.put("starttime", params.get("starttime"));
		String reportdate = sysConfigService.getValue(Constant.REPORTDATE);
		mapParam.put("v_sys_time", reportdate);
		mapParam.put("endtime", params.get("endtime"));
		mapParam.put("queryAreaIds", params.get("queryAreaIds"));
		Map map = new HashMap();
//		查出评价数据
		final String storedProcNameEval = "pro_cas_eval";
		ResultSet setEval = this.getStoreProce(ctx,storedProcNameEval,mapParam);
		//ResultSet countEval = setEval;
		ComplainEvaluate eval = new ComplainEvaluate();
		
//		创建list对象
		LinkedList<Integer> evalArea_id = new LinkedList<Integer>();
		LinkedList<Integer> evalRscp_a = new LinkedList<Integer>();
		LinkedList<Integer> evalRscp_b = new LinkedList<Integer>();
		LinkedList<Integer> evalRscp_c = new LinkedList<Integer>();
		LinkedList<Integer> evalRscp_d = new LinkedList<Integer>();
		LinkedList<Integer> evalEc_no_a = new LinkedList<Integer>();
		LinkedList<Integer> evalEc_no_b = new LinkedList<Integer>();
		LinkedList<Integer> evalEc_no_c = new LinkedList<Integer>();
		LinkedList<Integer> evalEc_no_d = new LinkedList<Integer>();
		LinkedList<Integer> evalTxpower_a = new LinkedList<Integer>();
		LinkedList<Integer> evalTxpower_b = new LinkedList<Integer>();
		LinkedList<Integer> evalTxpower_c = new LinkedList<Integer>();
		LinkedList<Integer> evalTxpower_d = new LinkedList<Integer>();
		LinkedList<Integer> evalFtp_up_a = new LinkedList<Integer>();
		LinkedList<Integer> evalFtp_up_b = new LinkedList<Integer>();
		LinkedList<Integer> evalFtp_up_c = new LinkedList<Integer>();
		LinkedList<Integer> evalFtp_up_d = new LinkedList<Integer>();
		LinkedList<Integer> evalFtp_down_a = new LinkedList<Integer>();
		LinkedList<Integer> evalFtp_down_b = new LinkedList<Integer>();
		LinkedList<Integer> evalFtp_down_c = new LinkedList<Integer>();
		LinkedList<Integer> evalFtp_down_d = new LinkedList<Integer>();
		LinkedList<Integer> evalRxlev_a = new LinkedList<Integer>();
		LinkedList<Integer> evalRxlev_b = new LinkedList<Integer>();
		LinkedList<Integer> evalRxlev_c = new LinkedList<Integer>();
		LinkedList<Integer> evalRxlev_d = new LinkedList<Integer>();
		LinkedList<Integer> evalRxqual_a = new LinkedList<Integer>();
		LinkedList<Integer> evalRxqual_b = new LinkedList<Integer>();
		LinkedList<Integer> evalRxqual_c = new LinkedList<Integer>();
		LinkedList<Integer> evalRxqual_d = new LinkedList<Integer>();
		LinkedList<Integer> evalCi_a = new LinkedList<Integer>();
		LinkedList<Integer> evalCi_b = new LinkedList<Integer>();
		LinkedList<Integer> evalCi_c = new LinkedList<Integer>();
		LinkedList<Integer> evalCi_d = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_eval_3g_a = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_eval_3g_b = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_eval_3g_c = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_eval_3g_d = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_eval_2g_a = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_eval_2g_b = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_eval_2g_c = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_eval_2g_d = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_impr_a = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_impr_b = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_impr_c = new LinkedList<Integer>();
		LinkedList<Integer> evalComp_impr_d = new LinkedList<Integer>();
		LinkedList<Double> evalComp_impr_value_3g = new LinkedList<Double>();
		LinkedList<Double> evalComp_impr_value_2g = new LinkedList<Double>();
		LinkedList<String> evalTimely_rate = new LinkedList<String>();
		LinkedList<Integer> evalTimely_rate_rank = new LinkedList<Integer>();
		
//		遍历实例装入对象
			try {
				while(setEval.next()){
					evalArea_id.add(setEval.getInt("area_id"));
					evalRscp_a.add(setEval.getInt("rscp_a"));
					evalRscp_b.add(setEval.getInt("rscp_b"));
					evalRscp_c.add(setEval.getInt("rscp_c"));
					evalRscp_d.add(setEval.getInt("rscp_d"));
					evalEc_no_a.add(setEval.getInt("ec_no_a"));
					evalEc_no_b.add(setEval.getInt("ec_no_b"));
					evalEc_no_c.add(setEval.getInt("ec_no_c"));
					evalEc_no_d.add(setEval.getInt("ec_no_d"));
					evalTxpower_a.add(setEval.getInt("txpower_a"));
					evalTxpower_b.add(setEval.getInt("txpower_b"));
					evalTxpower_c.add(setEval.getInt("txpower_c"));
					evalTxpower_d.add(setEval.getInt("txpower_d"));
					evalFtp_up_a.add(setEval.getInt("ftp_up_a"));
					evalFtp_up_b.add(setEval.getInt("ftp_up_b"));
					evalFtp_up_c.add(setEval.getInt("ftp_up_c"));
					evalFtp_up_d.add(setEval.getInt("ftp_up_d"));
					evalFtp_down_a.add(setEval.getInt("ftp_down_a"));
					evalFtp_down_b.add(setEval.getInt("ftp_down_b"));
					evalFtp_down_c.add(setEval.getInt("ftp_down_c"));
					evalFtp_down_d.add(setEval.getInt("ftp_down_d"));
					evalRxlev_a.add(setEval.getInt("rxlev_a"));
					evalRxlev_b.add(setEval.getInt("rxlev_b"));
					evalRxlev_c.add(setEval.getInt("rxlev_c"));
					evalRxlev_d.add(setEval.getInt("rxlev_d"));
					evalRxqual_a.add(setEval.getInt("rxqual_a"));
					evalRxqual_b.add(setEval.getInt("rxqual_b"));
					evalRxqual_c.add(setEval.getInt("rxqual_c"));
					evalRxqual_d.add(setEval.getInt("rxqual_d"));
					evalCi_a.add(setEval.getInt("c_i_a"));
					evalCi_b.add(setEval.getInt("c_i_b"));
					evalCi_c.add(setEval.getInt("c_i_c"));
					evalCi_d.add(setEval.getInt("c_i_d"));
					evalComp_eval_3g_a.add(setEval.getInt("comp_eval_3g_a"));
					evalComp_eval_3g_b.add(setEval.getInt("comp_eval_3g_b"));
					evalComp_eval_3g_c.add(setEval.getInt("comp_eval_3g_c"));
					evalComp_eval_3g_d.add(setEval.getInt("comp_eval_3g_d"));
					evalComp_eval_2g_a.add(setEval.getInt("comp_eval_2g_a"));
					evalComp_eval_2g_b.add(setEval.getInt("comp_eval_2g_b"));
					evalComp_eval_2g_c.add(setEval.getInt("comp_eval_2g_c"));
					evalComp_eval_2g_d.add(setEval.getInt("comp_eval_2g_d"));
					evalComp_impr_a.add(setEval.getInt("comp_impr_a"));
					evalComp_impr_b.add(setEval.getInt("comp_impr_b"));
					evalComp_impr_c.add(setEval.getInt("comp_impr_c"));
					evalComp_impr_d.add(setEval.getInt("comp_impr_d"));
					evalComp_impr_value_3g.add(setEval.getDouble("comp_impr_3g_value"));
					evalComp_impr_value_2g.add(setEval.getDouble("comp_impr_2g_value"));
					evalTimely_rate.add(setEval.getString("timely_rate"));
					evalTimely_rate_rank.add(setEval.getInt("timely_rate_rank"));
					}
//				根据areaid长度初始化数组
				int evalSize = evalArea_id.size();
					eval.setArea_id(new Integer[evalSize]);
					eval.setRscp_a(new Integer[evalSize]);
					eval.setRscp_b(new Integer[evalSize]);
					eval.setRscp_c(new Integer[evalSize]);
					eval.setRscp_d(new Integer[evalSize]);
					eval.setEc_no_a(new Integer[evalSize]);
					eval.setEc_no_b(new Integer[evalSize]);
					eval.setEc_no_c(new Integer[evalSize]);
					eval.setEc_no_d(new Integer[evalSize]);
					eval.setTxpower_a(new Integer[evalSize]);
					eval.setTxpower_b(new Integer[evalSize]);
					eval.setTxpower_c(new Integer[evalSize]);
					eval.setTxpower_d(new Integer[evalSize]);
					eval.setFtp_up_a(new Integer[evalSize]);
					eval.setFtp_up_b(new Integer[evalSize]);
					eval.setFtp_up_c(new Integer[evalSize]);
					eval.setFtp_up_d(new Integer[evalSize]);
					eval.setFtp_down_a(new Integer[evalSize]);
					eval.setFtp_down_b(new Integer[evalSize]);
					eval.setFtp_down_c(new Integer[evalSize]);
					eval.setFtp_down_d(new Integer[evalSize]);
					eval.setRxlev_a(new Integer[evalSize]);
					eval.setRxlev_b(new Integer[evalSize]);
					eval.setRxlev_c(new Integer[evalSize]);
					eval.setRxlev_d(new Integer[evalSize]);
					eval.setRxqual_a(new Integer[evalSize]);
					eval.setRxqual_b(new Integer[evalSize]);
					eval.setRxqual_c(new Integer[evalSize]);
					eval.setRxqual_d(new Integer[evalSize]);
					eval.setCi_a(new Integer[evalSize]);
					eval.setCi_b(new Integer[evalSize]);
					eval.setCi_c(new Integer[evalSize]);
					eval.setCi_d(new Integer[evalSize]);
					eval.setComp_eval_3g_a(new Integer[evalSize]);
					eval.setComp_eval_3g_b(new Integer[evalSize]);
					eval.setComp_eval_3g_c(new Integer[evalSize]);
					eval.setComp_eval_3g_d(new Integer[evalSize]);
					eval.setComp_eval_2g_a(new Integer[evalSize]);
					eval.setComp_eval_2g_b(new Integer[evalSize]);
					eval.setComp_eval_2g_c(new Integer[evalSize]);
					eval.setComp_eval_2g_d(new Integer[evalSize]);
					eval.setComp_impr_a(new Integer[evalSize]);
					eval.setComp_impr_b(new Integer[evalSize]);
					eval.setComp_impr_c(new Integer[evalSize]);
					eval.setComp_impr_d(new Integer[evalSize]);
					eval.setComp_impr_value_3g(new Double[evalSize]);
					eval.setComp_impr_ratio_2g(new String[evalSize]);
					eval.setComp_impr_ratio_3g(new String[evalSize]);
					eval.setComp_impr_value_2g(new Double[evalSize]);
					eval.setTimely_rate(new String[evalSize]);
					eval.setTimely_rate_rank(new Integer[evalSize]);
					
//					list 对象放入数组
					evalArea_id.toArray(eval.getArea_id());
					evalRscp_a.toArray(eval.getRscp_a());
					evalRscp_b.toArray(eval.getRscp_b());
					evalRscp_c.toArray(eval.getRscp_c());
					evalRscp_d.toArray(eval.getRscp_d());
					evalEc_no_a.toArray(eval.getEc_no_a());
					evalEc_no_b.toArray(eval.getEc_no_b());
					evalEc_no_c.toArray(eval.getEc_no_c());
					evalEc_no_d.toArray(eval.getEc_no_d());
					evalTxpower_a.toArray(eval.getTxpower_a());
					evalTxpower_b.toArray(eval.getTxpower_b());
					evalTxpower_c.toArray(eval.getTxpower_c());
					evalTxpower_d.toArray(eval.getTxpower_d());
					evalFtp_up_a.toArray(eval.getFtp_up_a());
					evalFtp_up_b.toArray(eval.getFtp_up_b());
					evalFtp_up_c.toArray(eval.getFtp_up_c());
					evalFtp_up_d.toArray(eval.getFtp_up_d());
					evalFtp_down_a.toArray(eval.getFtp_down_a());
					evalFtp_down_b.toArray(eval.getFtp_down_b());
					evalFtp_down_c.toArray(eval.getFtp_down_c());
					evalFtp_down_d.toArray(eval.getFtp_down_d());
					evalRxlev_a.toArray(eval.getRxlev_a());
					evalRxlev_b.toArray(eval.getRxlev_b());
					evalRxlev_c.toArray(eval.getRxlev_c());
					evalRxlev_d.toArray(eval.getRxlev_d());
					evalRxqual_a.toArray(eval.getRxqual_a());
					evalRxqual_b.toArray(eval.getRxqual_b());
					evalRxqual_c.toArray(eval.getRxqual_c());
					evalRxqual_d.toArray(eval.getRxqual_d());
					evalCi_a.toArray(eval.getCi_a());
					evalCi_b.toArray(eval.getCi_b());
					evalCi_c.toArray(eval.getCi_c());
					evalCi_d.toArray(eval.getCi_d());
					evalComp_eval_3g_a.toArray(eval.getComp_eval_3g_a());
					evalComp_eval_3g_b.toArray(eval.getComp_eval_3g_b());
					evalComp_eval_3g_c.toArray(eval.getComp_eval_3g_c());
					evalComp_eval_3g_d.toArray(eval.getComp_eval_3g_d());
					evalComp_eval_2g_a.toArray(eval.getComp_eval_2g_a());
					evalComp_eval_2g_b.toArray(eval.getComp_eval_2g_b());
					evalComp_eval_2g_c.toArray(eval.getComp_eval_2g_c());
					evalComp_eval_2g_d.toArray(eval.getComp_eval_2g_d());
					evalComp_impr_a.toArray(eval.getComp_impr_a());
					evalComp_impr_b.toArray(eval.getComp_impr_b());
					evalComp_impr_c.toArray(eval.getComp_impr_c());
					evalComp_impr_d.toArray(eval.getComp_impr_d());
					evalComp_impr_value_3g.toArray(eval.getComp_impr_value_3g());
					evalComp_impr_value_2g.toArray(eval.getComp_impr_value_2g());
					evalTimely_rate.toArray(eval.getTimely_rate());
					evalTimely_rate_rank.toArray(eval.getTimely_rate_rank());
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					try {
						if(null != setEval){
							setEval.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			
		map.put("eval", eval);
		return map;
	}
	/**
	 * 获取质量报表数据
	 * @param ctx
	 * @param params
	 * @return
	 */
	public QualtyReport getQualityReportData(WebApplicationContext ctx,
			Map<String, Object> params) {
		// 调用存储过程名称
		final String storedProcName = "pr_wo_qual";
		params.put("pistop", 0);
		QualtyReport qr = new QualtyReport();
		ResultSet qual = this.getStoreProce(ctx, storedProcName, params);
		LinkedList<Integer> areaid = new LinkedList<Integer>();
		LinkedList<String> areaname = new LinkedList<String>();
		LinkedList<String> comp_score = new LinkedList<String>();
		LinkedList<Integer> comp_score_rank = new LinkedList<Integer>(); // 投诉处理质量综合排名
		LinkedList<String> assess_score = new LinkedList<String>(); // 移动网络服务考核得分
		LinkedList<Integer> total_workorder = new LinkedList<Integer>(); // 累计需实测工单量
		LinkedList<Integer> curr_workorder = new LinkedList<Integer>(); // 月需实测工单量
		LinkedList<Integer> total_test = new LinkedList<Integer>(); // 累计实测量
		LinkedList<Integer> curr_test = new LinkedList<Integer>(); // 月实测量
		LinkedList<String> total_test_rate = new LinkedList<String>(); // 累计实测率
		LinkedList<String> curr_test_timely = new LinkedList<String>(); // 月测试及时率
		LinkedList<Integer> total_serialno = new LinkedList<Integer>(); // 累计网络投诉工单量
		LinkedList<Integer> total_solve = new LinkedList<Integer>(); // 累计真正解决工单量
		LinkedList<String> total_solve_rate = new LinkedList<String>(); // 累计真正解决率
		LinkedList<Integer> curr_serialno = new LinkedList<Integer>();// 月网络投诉工单量
		LinkedList<Integer> curr_solve = new LinkedList<Integer>();// 月真正解决工单量
		LinkedList<Integer> total_major_solve = new LinkedList<Integer>(); // 累计优化真正解决量
		LinkedList<String> total_major_solve_rate = new LinkedList<String>(); // 累计优化真正解决比
		LinkedList<Integer> curr_major_solve = new LinkedList<Integer>(); // 月优化真正解决量
		LinkedList<String> curr_major_solve_rate = new LinkedList<String>();// 月优化真正解决比
		LinkedList<Integer> total_build_solve = new LinkedList<Integer>(); // 累计建设真正解决量
		LinkedList<String> total_build_solve_rate = new LinkedList<String>(); // 累计建设真正解决比
		LinkedList<Integer> curr_build_solve = new LinkedList<Integer>(); // 月建设真正解决量
		LinkedList<String> curr_build_solve_rate = new LinkedList<String>();// 月建设真正解决比
		LinkedList<Integer> total_maintain_solve = new LinkedList<Integer>(); // 累计维护真正解决量
		LinkedList<String> total_maintain_solve_rate = new LinkedList<String>(); // 累计维护真正解决比
		LinkedList<Integer> curr_maintain_solve = new LinkedList<Integer>(); // 月维护真正解决量
		LinkedList<String> curr_maintain_solve_rate = new LinkedList<String>();// 月维护真正解决比
		
		LinkedList<Integer> total_other_solve = new LinkedList<Integer>(); // 累计其它真正解决量
		LinkedList<String> total_other_solve_rate = new LinkedList<String>(); ; // 累计其它真正解决比
		LinkedList<Integer> curr_other_solve = new LinkedList<Integer>(); ; // 月其它真正解决量
		LinkedList<String> curr_other_solve_rate = new LinkedList<String>(); ;// 月其它真正解决比
		
		LinkedList<Integer> total_delay = new LinkedList<Integer>(); // 累计工单滞留量
		LinkedList<String> total_delay_rate = new LinkedList<String>(); // 累计工单滞留率
		LinkedList<Integer> total_major_delay = new LinkedList<Integer>(); // 累计优化工单滞留量
		LinkedList<String> total_major_delay_rate = new LinkedList<String>();// 累计优化工单滞留比
		LinkedList<Integer> total_build_delay = new LinkedList<Integer>(); // 累计建设工单滞留量
		LinkedList<String> total_build_delay_rate = new LinkedList<String>();// 累计建设工单滞留比
		LinkedList<Integer> total_maintain_delay = new LinkedList<Integer>(); // 累计维护工单滞留量
		LinkedList<String> total_maintain_delay_rate = new LinkedList<String>();// 累计维护工单滞留比
		LinkedList<Integer> total_reject = new LinkedList<Integer>(); // 累计工单驳回量
		LinkedList<Integer> curr_reject = new LinkedList<Integer>(); // 月工单驳回量
		LinkedList<String> total_reject_rate = new LinkedList<String>();// 累计工单驳回率
//		LinkedList<String> curr_reject_rate = new LinkedList<String>(); // 月工单驳回率
		LinkedList<Integer> total_major_reject = new LinkedList<Integer>(); // 累计优化工单驳回量
		LinkedList<String> total_major_reject_rate = new LinkedList<String>();// 累计优化工单驳回比
		LinkedList<Integer> curr_major_reject = new LinkedList<Integer>(); // 月优化工单驳回量
		LinkedList<String> curr_major_reject_rate = new LinkedList<String>();// 月优化工单驳回比
		LinkedList<Integer> total_build_reject = new LinkedList<Integer>(); // 累计建设工单驳回量
		LinkedList<String> total_build_reject_rate = new LinkedList<String>();// 累计建设工单驳回比
		LinkedList<Integer> curr_build_reject = new LinkedList<Integer>(); // 月建设工单驳回量
		LinkedList<String> curr_build_reject_rate = new LinkedList<String>();// 月建设工单驳回比
		LinkedList<Integer> total_maintain_reject = new LinkedList<Integer>(); // 累计维护工单驳回量
		LinkedList<String> total_maintain_reject_rate = new LinkedList<String>();// 累计维护工单驳回比
		LinkedList<Integer> curr_maintain_reject = new LinkedList<Integer>(); // 月维护工单驳回量
		LinkedList<String> curr_maintain_reject_rate = new LinkedList<String>();// 月维护工单驳回比
		LinkedList<Integer> total_over = new LinkedList<Integer>(); // 累计工单超时量
		LinkedList<Integer> curr_over = new LinkedList<Integer>(); // 月工单超时量
		LinkedList<String> total_over_rate = new LinkedList<String>(); // 累计工单超时率
		LinkedList<Integer> total_send = new LinkedList<Integer>(); // 累计工单重复量
		LinkedList<Integer> curr_send = new LinkedList<Integer>(); // 月工单重复量
		LinkedList<String> total_send_rate = new LinkedList<String>(); // 累计工单重派率
		LinkedList<Integer> total_upgrade = new LinkedList<Integer>(); // 累计工单升级量
		LinkedList<Integer> curr_upgrade = new LinkedList<Integer>(); // 月工单升级量
		LinkedList<String> total_upgrade_rate = new LinkedList<String>(); // 累计重复投诉率
		LinkedList<Integer> total_complaint = new LinkedList<Integer>(); // 累计工单重复投诉量
		LinkedList<Integer> curr_complaint = new LinkedList<Integer>(); // 月工单重复投诉量
		LinkedList<String> total_complaint_rate = new LinkedList<String>(); // 累计工单升级率
		LinkedList<Double> curr_wcdma_impr = new LinkedList<Double>(); // 月3G综合改善评分
		LinkedList<Double> curr_gsm_impr = new LinkedList<Double>(); // 月2G综合改善评分
		LinkedList<String> ratio_wcdma_impr = new LinkedList<String>(); // 月3G综合改善评分
		LinkedList<String> ratio_gsm_impr = new LinkedList<String>(); // 月2G综合改善评分

		// 遍历实例装入对象
		try {
			while (qual.next()) {
				areaid.add(qual.getInt("area_id"));// 区域ID
				areaname.add(qual.getString("area_name"));// 区域名称
				comp_score.add(qual.getString("comp_score")); // 投诉处理质量综合评分
				comp_score_rank.add(qual.getString("comp_score_rank")==null?null:Integer.parseInt(qual.getString("comp_score_rank"))); // 投诉处理质量综合排名
				assess_score.add(qual.getString("assess_score")); // 移动网络服务考核得分
				total_workorder.add(qual.getInt("total_workorder")); // 累计需实测工单量
				curr_workorder.add(qual.getInt("curr_workorder")); // 月需实测工单量
				total_test.add(qual.getInt("total_test")); // 累计实测量
				curr_test.add(qual.getInt("curr_test")); // 月实测量
				total_test_rate.add(qual.getString("total_test_rate")); // 累计实测率
				curr_test_timely.add(qual.getString("curr_test_timely")); // 月测试及时率
				total_serialno.add(qual.getInt("total_serialno")); // 累计网络投诉工单量
				total_solve.add(qual.getInt("total_solve")); // 累计真正解决工单量
				total_solve_rate.add(qual.getString("total_solve_rate")); // 累计真正解决率
				curr_serialno.add(qual.getInt("curr_serialno"));// 月网络投诉工单量
				curr_solve.add(qual.getInt("curr_solve")); // 月真正解决工单量
				total_major_solve.add(qual.getInt("total_major_solve")); // 累计优化真正解决量
				total_major_solve_rate.add(qual.getString("total_major_solve_rate")); // 累计优化真正解决比
				curr_major_solve.add(qual.getInt("curr_major_solve")); // 月优化真正解决量
				curr_major_solve_rate.add(qual
						.getString("curr_major_solve_rate"));// 月优化真正解决比
				total_build_solve.add(qual.getInt("total_build_solve")); // 累计建设真正解决量
				total_build_solve_rate.add(qual
						.getString("total_build_solve_rate")); // 累计建设真正解决比
				curr_build_solve.add(qual.getInt("curr_build_solve")); // 月建设真正解决量
				curr_build_solve_rate.add(qual
						.getString("curr_build_solve_rate"));// 月建设真正解决比
				total_maintain_solve.add(qual.getInt("total_maintain_solve")); // 累计维护真正解决量
				total_maintain_solve_rate.add(qual
						.getString("total_maintain_solve_rate")); // 累计维护真正解决比
				curr_maintain_solve.add(qual.getInt("curr_maintain_solve")); // 月维护真正解决量
				curr_maintain_solve_rate.add(qual
						.getString("curr_maintain_solve_rate"));// 月维护真正解决比
				total_other_solve.add(qual.getInt("total_other_solve")); // 累计其它真正解决量
				total_other_solve_rate.add(qual.getString("total_other_solve_rate")); // 累计其它真正解决比
				curr_other_solve.add(qual.getInt("curr_other_solve")); // 月其它真正解决量
				curr_other_solve_rate.add(qual.getString("curr_other_solve_rate"));// 月其它真正解决比
				total_delay.add(qual.getInt("total_delay")); // 累计工单滞留量
				total_delay_rate.add(qual.getString("total_delay_rate")); // 累计工单滞留率
				total_major_delay.add(qual.getInt("total_major_delay")); // 累计优化工单滞留量
				total_major_delay_rate.add(qual
						.getString("total_major_delay_rate"));// 累计优化工单滞留比
				total_build_delay.add(qual.getInt("total_build_delay")); // 累计建设工单滞留量
				total_build_delay_rate.add(qual
						.getString("total_build_delay_rate"));// 累计建设工单滞留比
				total_maintain_delay.add(qual.getInt("total_maintain_delay")); // 累计维护工单滞留量
				total_maintain_delay_rate.add(qual
						.getString("total_maintain_delay_rate"));// 累计维护工单滞留比
				total_reject.add(qual.getInt("total_reject")); // 累计工单驳回量
				curr_reject.add(qual.getInt("curr_reject")); // 月工单驳回量
				total_reject_rate.add(qual.getString("total_reject_rate"));// 累计工单驳回率
//				curr_reject_rate.add(qual.getString("curr_reject_rate")); // 月工单驳回率
				total_major_reject.add(qual.getInt("total_major_reject")); // 累计优化工单驳回量
				total_major_reject_rate.add(qual
						.getString("total_major_reject_rate"));// 累计优化工单驳回比
				curr_major_reject.add(qual.getInt("curr_major_reject")); // 月优化工单驳回量
				curr_major_reject_rate.add(qual
						.getString("curr_major_reject_rate"));// 月优化工单驳回比
				total_build_reject.add(qual.getInt("total_build_reject")); // 累计建设工单驳回量
				total_build_reject_rate.add(qual
						.getString("total_build_reject_rate"));// 累计建设工单驳回比
				curr_build_reject.add(qual.getInt("curr_build_reject")); // 月建设工单驳回量
				curr_build_reject_rate.add(qual
						.getString("curr_build_reject_rate"));// 月建设工单驳回比
				total_maintain_reject.add(qual.getInt("total_maintain_reject")); // 累计维护工单驳回量
				total_maintain_reject_rate.add(qual
						.getString("total_maintain_reject_rate"));// 累计维护工单驳回比
				curr_maintain_reject.add(qual.getInt("curr_maintain_reject")); // 月维护工单驳回量
				curr_maintain_reject_rate.add(qual
						.getString("curr_maintain_reject_rate"));// 月维护工单驳回比
				total_over.add(qual.getInt("total_over")); // 累计工单超时量
				curr_over.add(qual.getInt("curr_over")); // 月工单超时量
				total_over_rate.add(qual.getString("total_over_rate")); // 累计工单超时率
				total_send.add(qual.getInt("total_send")); // 累计工单重复量
				curr_send.add(qual.getInt("curr_send")); // 月工单重复量
				total_send_rate.add(qual.getString("total_send_rate")); // 累计工单重派率
				total_upgrade.add(qual.getInt("total_upgrade")); // 累计工单升级量
				curr_upgrade.add(qual.getInt("curr_upgrade")); // 月工单升级量
				total_upgrade_rate.add(qual.getString("total_upgrade_rate")); // 累计重复投诉率
				total_complaint.add(qual.getInt("total_complaint")); // 累计工单重复投诉量
				curr_complaint.add(qual.getInt("curr_complaint")); // 月工单重复投诉量
				total_complaint_rate
						.add(qual.getString("total_complaint_rate")); // 累计工单升级率
				curr_wcdma_impr.add(qual.getDouble("curr_wcdma_impr")); // 月3G综合改善评分
				curr_gsm_impr.add(qual.getDouble("curr_gsm_impr")); // 月2G综合改善评分
				if(qual.getInt("total_serialno")>0){
					double val3g = qual.getDouble("curr_wcdma_impr");
					double val2g = qual.getDouble("curr_gsm_impr");
					ratio_wcdma_impr.add(PatternUtil.getDouble((double)((val3g*100000/qual.getInt("total_serialno"))/1000)));
					ratio_gsm_impr.add(PatternUtil.getDouble((double)((val2g*100000/qual.getInt("total_serialno"))/1000)));
				}else{
					ratio_wcdma_impr.add("0%");
					ratio_gsm_impr.add("0%");
				}
			}
			// 根据areaid长度初始化数组
			int size = areaid.size();
			qr.setAreaid(new Integer[size]);
			qr.setAreaname(new String[size]);// 区域名称
			qr.setComp_score(new String[size]); // 投诉处理质量综合评分
			qr.setComp_score_rank(new Integer[size]); // 投诉处理质量综合排名
			qr.setAssess_score(new String[size]); // 移动网络服务考核得分
			qr.setTotal_workorder(new Integer[size]); // 累计需实测工单量
			qr.setCurr_workorder(new Integer[size]); // 月需实测工单量
			qr.setTotal_test(new Integer[size]); // 累计实测量
			qr.setCurr_test(new Integer[size]); // 月实测量
			qr.setTotal_test_rate(new String[size]); // 累计实测率
			qr.setCurr_test_timely(new String[size]); // 月测试及时率
			qr.setTotal_serialno(new Integer[size]); // 累计网络投诉工单量
			qr.setTotal_solve(new Integer[size]); // 累计真正解决工单量
			qr.setTotal_solve_rate(new String[size]); // 累计真正解决率
			qr.setCurr_serialno(new Integer[size]);// 月网络投诉工单量
			qr.setCurr_solve(new Integer[size]); // 月真正解决工单量
			qr.setTotal_major_solve(new Integer[size]); // 累计优化真正解决量
			qr.setTotal_major_solve_rate(new String[size]); // 累计优化真正解决比
			qr.setCurr_major_solve(new Integer[size]); // 月优化真正解决量
			qr.setCurr_major_solve_rate(new String[size]);// 月优化真正解决比
			qr.setTotal_build_solve(new Integer[size]); // 累计建设真正解决量
			qr.setTotal_build_solve_rate(new String[size]); // 累计建设真正解决比
			qr.setCurr_build_solve(new Integer[size]); // 月建设真正解决量
			qr.setCurr_build_solve_rate(new String[size]);// 月建设真正解决比
			qr.setTotal_maintain_solve(new Integer[size]); // 累计维护真正解决量
			qr.setTotal_maintain_solve_rate(new String[size]); // 累计维护真正解决比
			qr.setCurr_maintain_solve(new Integer[size]); // 月维护真正解决量
			qr.setCurr_maintain_solve_rate(new String[size]);// 月维护真正解决比
			qr.setTotal_other_solve(new Integer[size]); // 累计其它真正解决量
			qr.setTotal_other_solve_rate(new String[size]); // 累计其它真正解决比
			qr.setCurr_other_solve(new Integer[size]);// 月其它真正解决量
			qr.setCurr_other_solve_rate(new String[size]);// 月其它真正解决比
			qr.setTotal_delay(new Integer[size]); // 累计工单滞留量
			qr.setTotal_delay_rate(new String[size]); // 累计工单滞留率
			qr.setTotal_major_delay(new Integer[size]); // 累计优化工单滞留量
			qr.setTotal_major_delay_rate(new String[size]);// 累计优化工单滞留比
			qr.setTotal_build_delay(new Integer[size]); // 累计建设工单滞留量
			qr.setTotal_build_delay_rate(new String[size]);// 累计建设工单滞留比
			qr.setTotal_maintain_delay(new Integer[size]); // 累计维护工单滞留量
			qr.setTotal_maintain_delay_rate(new String[size]);// 累计维护工单滞留比
			qr.setTotal_reject(new Integer[size]); // 累计工单驳回量
			qr.setCurr_reject(new Integer[size]); // 月工单驳回量
			qr.setTotal_reject_rate(new String[size]);// 累计工单驳回率
			qr.setCurr_reject_rate(new String[size]); // 月工单驳回率
			qr.setTotal_major_reject(new Integer[size]); // 累计优化工单驳回量
			qr.setTotal_major_reject_rate(new String[size]);// 累计优化工单驳回比
			qr.setCurr_major_reject(new Integer[size]); // 月优化工单驳回量
			qr.setCurr_major_reject_rate(new String[size]);// 月优化工单驳回比
			qr.setTotal_build_reject(new Integer[size]); // 累计建设工单驳回量
			qr.setTotal_build_reject_rate(new String[size]);// 累计建设工单驳回比
			qr.setCurr_build_reject(new Integer[size]); // 月建设工单驳回量
			qr.setCurr_build_reject_rate(new String[size]);// 月建设工单驳回比
			qr.setTotal_maintain_reject(new Integer[size]); // 累计维护工单驳回量
			qr.setTotal_maintain_reject_rate(new String[size]);// 累计维护工单驳回比
			qr.setCurr_maintain_reject(new Integer[size]); // 月维护工单驳回量
			qr.setCurr_maintain_reject_rate(new String[size]);// 月维护工单驳回比
			qr.setTotal_over(new Integer[size]); // 累计工单超时量
			qr.setCurr_over(new Integer[size]); // 月工单超时量
			qr.setTotal_over_rate(new String[size]); // 累计工单超时率
			qr.setTotal_send(new Integer[size]); // 累计工单重复量
			qr.setCurr_send(new Integer[size]); // 月工单重复量
			qr.setTotal_send_rate(new String[size]); // 累计工单重派率
			qr.setTotal_upgrade(new Integer[size]); // 累计工单升级量
			qr.setCurr_upgrade(new Integer[size]); // 月工单升级量
			qr.setTotal_upgrade_rate(new String[size]); // 累计重复投诉率
			qr.setTotal_complaint(new Integer[size]); // 累计工单重复投诉量
			qr.setCurr_complaint(new Integer[size]); // 月工单重复投诉量
			qr.setTotal_complaint_rate(new String[size]); // 累计工单升级率
			qr.setCurr_wcdma_impr(new Double[size]); // 月3G综合改善评分
			qr.setCurr_gsm_impr(new Double[size]); // 月2G综合改善评分
			qr.setRatio_wcdma_impr(new String[size]); // 月3G综合改善比例
			qr.setRatio_gsm_impr(new String[size]); // 月2G综合改善比例
			// 数据转换
			areaid.toArray(qr.getAreaid());// 区域id
			areaname.toArray(qr.getAreaname());// 区域名称
			comp_score.toArray(qr.getComp_score()); // 投诉处理质量综合评分
			comp_score_rank.toArray(qr.getComp_score_rank()); // 投诉处理质量综合排名
			assess_score.toArray(qr.getAssess_score()); // 移动网络服务考核得分
			total_workorder.toArray(qr.getTotal_workorder()); // 累计需实测工单量
			curr_workorder.toArray(qr.getCurr_workorder()); // 月需实测工单量
			total_test.toArray(qr.getTotal_test()); // 累计实测量
			curr_test.toArray(qr.getCurr_test()); // 月实测量
			total_test_rate.toArray(qr.getTotal_test_rate()); // 累计实测率
			curr_test_timely.toArray(qr.getCurr_test_timely()); // 月测试及时率
			total_serialno.toArray(qr.getTotal_serialno()); // 累计网络投诉工单量
			total_solve.toArray(qr.getTotal_solve()); // 累计真正解决工单量
			total_solve_rate.toArray(qr.getTotal_solve_rate()); // 累计真正解决率
			curr_serialno.toArray(qr.getCurr_serialno());// 月网络投诉工单量
			curr_solve.toArray(qr.getCurr_solve()); // 月真正解决工单量
			total_major_solve.toArray(qr.getTotal_major_solve()); // 累计优化真正解决量
			total_major_solve_rate.toArray(qr.getTotal_major_solve_rate()); // 累计优化真正解决比
			curr_major_solve.toArray(qr.getCurr_major_solve()); // 月优化真正解决量
			curr_major_solve_rate.toArray(qr.getCurr_major_solve_rate());// 月优化真正解决比
			total_build_solve.toArray(qr.getTotal_build_solve()); // 累计建设真正解决量
			total_build_solve_rate.toArray(qr.getTotal_build_solve_rate()); // 累计建设真正解决比
			curr_build_solve.toArray(qr.getCurr_build_solve()); // 月建设真正解决量
			curr_build_solve_rate.toArray(qr.getCurr_build_solve_rate());// 月建设真正解决比
			total_maintain_solve.toArray(qr.getTotal_maintain_solve()); // 累计维护真正解决量
			total_maintain_solve_rate
					.toArray(qr.getTotal_maintain_solve_rate()); // 累计维护真正解决比
			curr_maintain_solve.toArray(qr.getCurr_maintain_solve()); // 月维护真正解决量
			curr_maintain_solve_rate.toArray(qr.getCurr_maintain_solve_rate());// 月维护真正解决比
			total_other_solve.toArray(qr.getTotal_other_solve());// 累计其它真正解决量
			total_other_solve_rate.toArray(qr.getTotal_other_solve_rate());// 累计其它真正解决比
			curr_other_solve.toArray(qr.getCurr_other_solve()); // 月其它真正解决量
			curr_other_solve_rate.toArray(qr.getCurr_other_solve_rate());// 月其它真正解决比
			total_delay.toArray(qr.getTotal_delay()); // 累计工单滞留量
			total_delay_rate.toArray(qr.getTotal_delay_rate()); // 累计工单滞留率
			total_major_delay.toArray(qr.getTotal_major_delay()); // 累计优化工单滞留量
			total_major_delay_rate.toArray(qr.getTotal_major_delay_rate());// 累计优化工单滞留比
			total_build_delay.toArray(qr.getTotal_build_delay()); // 累计建设工单滞留量
			total_build_delay_rate.toArray(qr.getTotal_build_delay_rate());// 累计建设工单滞留比
			total_maintain_delay.toArray(qr.getTotal_maintain_delay()); // 累计维护工单滞留量
			total_maintain_delay_rate.toArray(qr.getTotal_maintain_delay_rate());// 累计维护工单滞留比
			total_reject.toArray(qr.getTotal_reject()); // 累计工单驳回量
			curr_reject.toArray(qr.getCurr_reject()); // 月工单驳回量
			total_reject_rate.toArray(qr.getTotal_reject_rate());// 累计工单驳回率
//			curr_reject_rate.toArray(qr.getCurr_reject_rate()); // 月工单驳回率
			total_major_reject.toArray(qr.getTotal_major_reject()); // 累计优化工单驳回量
			total_major_reject_rate.toArray(qr.getTotal_major_reject_rate());// 累计优化工单驳回比
			curr_major_reject.toArray(qr.getCurr_major_reject()); // 月优化工单驳回量
			curr_major_reject_rate.toArray(qr.getCurr_major_reject_rate());// 月优化工单驳回比
			total_build_reject.toArray(qr.getTotal_build_reject()); // 累计建设工单驳回量
			total_build_reject_rate.toArray(qr.getTotal_build_reject_rate());// 累计建设工单驳回比
			curr_build_reject.toArray(qr.getCurr_build_reject()); // 月建设工单驳回量
			curr_build_reject_rate.toArray(qr.getCurr_build_reject_rate());// 月建设工单驳回比
			total_maintain_reject.toArray(qr.getTotal_maintain_reject()); // 累计维护工单驳回量
			total_maintain_reject_rate.toArray(qr
					.getTotal_maintain_reject_rate());// 累计维护工单驳回比
			curr_maintain_reject.toArray(qr.getCurr_maintain_reject()); // 月维护工单驳回量
			curr_maintain_reject_rate
					.toArray(qr.getCurr_maintain_reject_rate());// 月维护工单驳回比
			total_over.toArray(qr.getTotal_over()); // 累计工单超时量
			curr_over.toArray(qr.getCurr_over()); // 月工单超时量
			total_over_rate.toArray(qr.getTotal_over_rate()); // 累计工单超时率
			total_send.toArray(qr.getTotal_send()); // 累计工单重复量
			curr_send.toArray(qr.getCurr_send()); // 月工单重复量
			total_send_rate.toArray(qr.getTotal_send_rate()); // 累计工单重派率
			total_upgrade.toArray(qr.getTotal_upgrade()); // 累计工单升级量
			curr_upgrade.toArray(qr.getCurr_upgrade()); // 月工单升级量
			total_upgrade_rate.toArray(qr.getTotal_upgrade_rate()); // 累计重复投诉率
			total_complaint.toArray(qr.getTotal_complaint()); // 累计工单重复投诉量
			curr_complaint.toArray(qr.getCurr_complaint()); // 月工单重复投诉量
			total_complaint_rate.toArray(qr.getTotal_complaint_rate()); // 累计工单升级率
			curr_wcdma_impr.toArray(qr.getCurr_wcdma_impr()); // 月3G综合改善评分
			curr_gsm_impr.toArray(qr.getCurr_gsm_impr()); // 月2G综合改善评分
			ratio_wcdma_impr.toArray(qr.getRatio_wcdma_impr()); // 月3G综合改善比例
			ratio_gsm_impr.toArray(qr.getRatio_gsm_impr()); // 月2G综合改善比例
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("", e);
		}finally{
			try {
				if(null != qual){
					qual.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("", e);
			}
		}
		return qr;
	}
	/**
	 * 查询top last 
	 * @param ctx
	 * @param params
	 * @return
	 */
	public Map<String,QualTopAndLast> getTopAndLast(WebApplicationContext ctx,
			Map<String, Object> params){
		QualTopAndLast top5 = new QualTopAndLast();
		QualTopAndLast top1 = new QualTopAndLast();
		final String storedProcName = "pr_wo_qual_top";
		ResultSet rs = this.getStoreProce(ctx, storedProcName, params);
		int tid = 0;
		try {
			while (rs.next()) {
				tid = rs.getInt("tid");
				switch (tid) {
				    case 0:
//				    	comp_score_max
				    	top5.setComp_score_max(rs.getString("top5"));//投诉处理质量综合评分top5
						top5.setComp_score_min(rs.getString("last5"));//投诉处理质量综合评分top5
						top1.setComp_score_max(rs.getString("top1"));//投诉处理质量综合评分top1
						top1.setComp_score_min(rs.getString("last1"));//投诉处理质量综合评分top1
						break;
					case 1:
						top5.setTotal_max_rate(rs.getString("top5"));//累计实测率top5
						top5.setTotal_min_rate(rs.getString("last5"));//累计实测率top5
						top1.setTotal_max_rate(rs.getString("top1"));//累计实测率top1
						top1.setTotal_min_rate(rs.getString("last1"));//累计实测率top1
						break;
					case 2:
						top5.setCurr_test_timely_max(rs.getString("top5"));//测试及时率top5
						top5.setCurr_test_timely_min(rs.getString("last5"));//测试及时率top5
						top1.setCurr_test_timely_max(rs.getString("top1"));//测试及时率top1
						top1.setCurr_test_timely_min(rs.getString("last1"));//测试及时率top1
						break;
					case 3:
						top5.setTotal_solve_rate_max(rs.getString("top5"));//累计解决率top5
						top5.setTotal_solve_rate_min(rs.getString("last5"));//累计解决率top5
						top1.setTotal_solve_rate_max(rs.getString("top1"));//累计解决率top1
						top1.setTotal_solve_rate_min(rs.getString("last1"));//累计解决率top1
						break;
					case 4:
						top5.setTotal_delay_rate_max(rs.getString("top5"));//累计工单滞留率top5
						top5.setTotal_delay_rate_min(rs.getString("last5"));//累计工单滞留率top5
						top1.setTotal_delay_rate_max(rs.getString("top1"));//累计工单滞留率top1
						top1.setTotal_delay_rate_min(rs.getString("last1"));//累计工单滞留率top1
						break;
					case 5:
						top5.setTotal_reject_rate_max(rs.getString("top5"));//累计工单驳回率top5
						top5.setTotal_reject_rate_min(rs.getString("last5"));//累计工单驳回率top5
						top1.setTotal_reject_rate_max(rs.getString("top1"));//累计工单驳回率top1
						top1.setTotal_reject_rate_min(rs.getString("last1"));//累计工单驳回率top1
						break;
					case 6:
						top5.setTotal_over_rate_max(rs.getString("top5"));//累计工单超时率top5
						top5.setTotal_over_rate_min(rs.getString("last5"));//累计工单超时率top5
						top1.setTotal_over_rate_max(rs.getString("top1"));//累计工单超时率top1
						top1.setTotal_over_rate_min(rs.getString("last1"));//累计工单超时率top1
						break;
					case 7:
						top5.setTotal_send_rate_max(rs.getString("top5"));//累计工单重派率top5
						top5.setTotal_send_rate_min(rs.getString("last5"));//累计工单重派率top5
						top1.setTotal_send_rate_max(rs.getString("top1"));//累计工单重派率top1
						top1.setTotal_send_rate_min(rs.getString("last1"));//累计工单重派率top1
						break;
					case 8:
						top5.setTotal_complaint_rate_max(rs.getString("top5"));//累计重复投诉率top5
						top5.setTotal_complaint_rate_min(rs.getString("last5"));//累计重复投诉率top5
						top1.setTotal_complaint_rate_max(rs.getString("top1"));//累计重复投诉率top1
						top1.setTotal_complaint_rate_min(rs.getString("last1"));//累计重复投诉率top1
						break;
					case 9:
						top5.setTotal_upgrade_rate_max(rs.getString("top5"));//累计工单升级率top5
						top5.setTotal_upgrade_rate_min(rs.getString("last5"));//累计工单升级率top5
						top1.setTotal_upgrade_rate_max(rs.getString("top1"));//累计工单升级率top1
						top1.setTotal_upgrade_rate_min(rs.getString("last1"));//累计工单升级率top1
						break;
					default:
						break;
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		Map<String,QualTopAndLast> map = new HashMap<String, QualTopAndLast>();
		map.put("top5", top5);
		map.put("top1", top1);
		return map;
	}
}
