package com.complaint.service;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.action.vo.VoBean;
import com.complaint.dao.TaskDao;
import com.complaint.model.TestWorkOrder;
import com.complaint.model.WorkOrderForList;
import com.complaint.page.PageBean;

@Service("taskOrderService")
public class TaskOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskOrderService.class);
	
	@Autowired
	private TaskDao taskDao;

	public PageBean getWorkOrderList(int pageSize, int pageIndex,
			VoBean vo,String s_id,Integer userid ,Integer breakflag) {
		int lbound = (pageIndex-1)*pageSize;
		int mbound = pageIndex*pageSize;
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
		param.put("breakflag", breakflag);
		param.put("s_id", s_id);
		param.put("lbound", lbound);
		param.put("mbound", mbound);

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
		param.put("breakflag", breakflag);
		//用户id
		param.put("userid", userid);
		List<TestWorkOrder> list = this.taskDao.queryWorkOrderList(param);
		PageBean pb = new PageBean();
		pb.setList(list);
		return pb;
	}
	public PageBean countWorkOrderList(int pageSize, int pageIndex,VoBean vo,String s_id,Integer userid ,Integer breakflag) {
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
		param.put("inside",vo.getInside());
		param.put("endTime", vo.getEndTime()+" 23:59:59");
		param.put("testnet", vo.getTestnet());
		param.put("nettype", vo.getNettype());
		param.put("verify", vo.getVerify());
		//拆分场景
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
			String tt="";
			if(strlist.contains("1")){
				tt+="1";
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
				yy="2";
			}
			if(strlist.contains("5")){
				yy+=",1";			
						}
			//上下行
			param.put("yy", yy);
			String ff=null;
			if(strlist.contains("6")){
				ff="1";
			}
			if(strlist.contains("7")){
				ff+=",2";			
						}
			param.put("ff", ff);
		}else{
			param.put("bustype", "-1");
		}
		param.put("breakflag", breakflag);
		//用户id
		param.put("userid", userid);
		param.put("s_id", s_id);
		int count = this.taskDao.countWorkOrderList(param);
		PageBean pb = new PageBean();
		pb.setTotalPage(count);
		pb.setPageIndex(pageIndex);
		pb.setPageSize(pageSize);
		return pb;
	}
	public List<TestWorkOrder> getAllArea(Integer userid){
		return this.taskDao.queryAllArea(userid);
	}
	
}
