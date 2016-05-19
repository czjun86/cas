package com.complaint.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.BaseDao;
import com.complaint.dao.TaskConfigDao;
import com.complaint.model.AreaBean;
import com.complaint.model.TaskOrder;
import com.complaint.model.User;
import com.complaint.page.PageBean;
import com.complaint.utils.SecretUtil;


@Service("taskConfigService")
public class TaskConfigService {
	@Autowired
	private TaskConfigDao taskConfigDao;
	@Autowired
	private BaseDao baseDao;
	
	public PageBean countTask(String val ,Integer isverify ,Integer validstate  ,Integer timeType ,
			int pageIndex, int pageSize ,int userid){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("val", val);
		param.put("validstate", validstate);
		param.put("isverify", isverify);
		Map<String ,String> map = this.getTime(timeType);
		param.put("endTime", map.get("endTime"));
		param.put("startTime", map.get("startTime"));
		param.put("userid", userid);
		
		PageBean pb = new PageBean();
		
		int count = this.taskConfigDao.countTask(param);
		pb.setPageIndex(pageIndex);
		pb.setPageSize(pageSize);
		pb.setTotalPage(count);
		return pb;
	}
	
	public PageBean getTaskList(String val ,Integer isverify ,Integer validstate ,Integer timeType ,
			int pageIndex, int pageSize ,int userid){
		int lbound = (pageIndex - 1) * pageSize;
		int mbound = pageIndex * pageSize;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("val", val);
		param.put("validstate", validstate);
		param.put("isverify", isverify);
		Map<String ,String> map = this.getTime(timeType);
		param.put("endTime", map.get("endTime"));
		param.put("startTime", map.get("startTime"));
		param.put("lbound", lbound);
		param.put("mbound", mbound);
		param.put("userid", userid);
		
		PageBean pb = new PageBean();
		List<TaskOrder> list = this.taskConfigDao.queryList(param);
		pb.setList(list);
		return pb;
	}
	/**
	 * 获取用户对应区域
	 */
	public List<AreaBean> getAreaList(Integer userid){
		List<AreaBean> areas = new ArrayList<AreaBean>();
		areas = this.taskConfigDao.getAreaList(userid);
		return areas;
	}
	
	/**
	 * 保存新增工单
	 * @param areaid
	 * @param net_type
	 * @param break_flag
	 * @param test_address
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveAdd(Integer[] areaid,
			Integer[] nettype,Integer[] breakflag ,String [] testaddress,User user) throws Exception{
		List<TaskOrder> list = new ArrayList<TaskOrder>();
		TaskOrder to = null;
		//工单集合防止工单重复
		List<String> serialnos = new ArrayList<String>();
		for(int i = 0;i<areaid.length;i++){
			//获取工单
			String sb = nosame(serialnos);
			serialnos.add(sb);
			
			to = new TaskOrder();
			to.setAreaid(areaid[i]);
			to.setBreakflag(breakflag[i]);
			//to.setCreate_date();
			to.setHandler(user.getUserid());
			to.setIsverify(0);//默认为0未审核
			to.setValidstate(0);//默认为0有效状态
			to.setNetworktype(getNetworktype(nettype[i]));
			//to.setNum();
			to.setId(sb.toString());
			to.setSerialno(sb.toString());
			to.setTest_address(testaddress[i]);
			list.add(to);
		}
		this.baseDao.batchInsert(TaskConfigDao.class, list, 200);
	}
	
	public String getNetworktype(Integer i){
		String nwt ="";
		switch (i) {
		case 1:
			nwt = "2g数据";
			break;
		case 2:
			nwt = "2g语音";
			break;
		case 3:
			nwt = "3g数据";
			break;
		case 4:
			nwt = "3g语音";
			break;

		default:
			nwt = "未知";
			break;
		}
		return nwt;
	}
	
	/**
	 * 预防重复工单
	 * @throws NoSuchAlgorithmException 
	 */
	public String nosame(List<String> serialnos) throws NoSuchAlgorithmException{
		boolean flag = false;
		String sb = null;
		do{
			flag = false;
			sb = getSerialno();
			if(serialnos.size()>0){
				for(String serialno:serialnos){
					if(sb.equals(serialno)){
						flag = true;
					}
				}
			}
		}while(flag);
		return sb;
	}
	/**
	 * 生成工单
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public String getSerialno() throws NoSuchAlgorithmException{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		UUID uuid = UUID.randomUUID();
		String md = getMD5(uuid.toString());
		String str = df.format(date);
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		sb.append(md.toUpperCase());
		/*Random random = new Random();
		for(int i=0;i<20-str.length();i++){
			sb.append(random.nextInt(10));
		}*/
		return sb.toString();
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
	
	public TaskOrder getSerialnoInfo(String id){
		TaskOrder to = this.taskConfigDao.querySerialno(id);
		return to;
	}
	
	/**
	 * 修改工单
	 * @param to
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveUpdate(TaskOrder to){
		this.taskConfigDao.updateSerialno(to);
	}

	/**
	 * 根据时间类型获取时间
	 * @param timeType
	 * @return
	 */
	public Map<String ,String> getTime(Integer timeType){
		Map<String ,String> map = new HashMap<String ,String>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		SimpleDateFormat sf2=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date endtime = new Date();
		map.put("endTime", sf.format(endtime));
		
		
		if(timeType==null ||"".equals(timeType)){
		   map.put("startTime", "");
		}else if(timeType == 1){//7天
		   Calendar calendar=Calendar.getInstance(); 
	       calendar.setTime(endtime); 
	       calendar.add(Calendar.DATE,-6);  
	       Date starttime=calendar.getTime();
	       map.put("startTime", sf2.format(starttime));
		}else if(timeType == 2){//1个月
		   Calendar calendar=Calendar.getInstance(); 
	       calendar.setTime(endtime); 
	       calendar.add(Calendar.DATE,-29);  
	       Date starttime=calendar.getTime();
	       map.put("startTime", sf2.format(starttime));
		}else if(timeType == 3){//3个月
		   Calendar calendar=Calendar.getInstance(); 
	       calendar.setTime(endtime); 
	       calendar.add(Calendar.DATE,-89);  
	       Date starttime=calendar.getTime();
	       map.put("startTime", sf2.format(starttime));
		}else if(timeType == -1){
			map.put("startTime", "");
		}else{
			map.put("startTime", "");
		}
		return map;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void setValidstate(TaskOrder to)throws Exception{
		this.taskConfigDao.setValidstate(to);
	}
}
