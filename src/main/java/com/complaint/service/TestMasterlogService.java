package com.complaint.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.BaseDao;
import com.complaint.dao.GsmOwnLogDao;
import com.complaint.dao.GsmTaskLogDao;
import com.complaint.dao.LogSubmanualGsmDao;
import com.complaint.dao.LteTrackLogDao;
import com.complaint.dao.TestMasterlogDao;
import com.complaint.dao.WcdmsOwnLogDao;
import com.complaint.dao.WcdmsTaskLogDao;
import com.complaint.dao.WcdmsTrackLogDao;
import com.complaint.dao.WorkOrderDao;
import com.complaint.mina.MinaServerHandler;
import com.complaint.model.LogSubmanualGsm;
import com.complaint.model.LogSubmanualLte;
import com.complaint.model.MyLLOffset;
import com.complaint.model.TCasCell;
import com.complaint.model.TestMasterlog;
import com.complaint.model.WcdmsTrackLog;
import com.complaint.model.WorkOrder;
import com.complaint.utils.DateUtils;
import com.complaint.utils.MapUtil;

import cn.zhugefubin.maptool.ConverterTool;
import cn.zhugefubin.maptool.Point;

@Service("testMasterlogService")
public class TestMasterlogService {
	
	private static final Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);
	
	private static Integer sequence;
	
	private static Map<Integer, String> netTypeMap;
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private TestMasterlogDao testMasterlogDao;
	@Autowired
	private WorkOrderDao workOrderDao;
	@Autowired
	private LogSubmanualGsmDao logSubmanualGsmDao;
	@Autowired
	private WcdmsTrackLogDao wcdmsTrackLogDao;
	@Autowired
	private LteTrackLogDao lteTrackLogDao;
	@Autowired
	private GsmTaskLogDao gsmTaskLogDao;
	@Autowired
	private WcdmsTaskLogDao wcdmsTaskLogDao;
	@Autowired
	private GsmOwnLogDao gsmOwnLogDao;
	@Autowired
	private WcdmsOwnLogDao wcdmsOwnLogDao;
	
	
//	@Autowired
//	private WcdmsTrackLogDao wcdmsTrackLogDao;
	
	public void initNetTypeList(){
		netTypeMap = new HashMap<Integer, String>();
		netTypeMap.put(0, "3G");
		netTypeMap.put(1, "2G");
		netTypeMap.put(2, "2G");
		netTypeMap.put(3, "3G");
		netTypeMap.put(4, "2G");
		netTypeMap.put(5, "3G");
		netTypeMap.put(6, "3G");
		netTypeMap.put(7, "3G");
		netTypeMap.put(8, "3G");
		netTypeMap.put(9, "3G");
		netTypeMap.put(10, "3G");
		netTypeMap.put(11, "3G");
		netTypeMap.put(12, "3G");
		netTypeMap.put(13, "4G");//LTE为4G
		netTypeMap.put(14, "3G");
		netTypeMap.put(15, "3G");
	}
    
    private String getSerialNo(){
    	return DateUtils.getNowByFormat("yyyyMMddHHmmss") + this.querySequence();
    }
    
    private synchronized int querySequence(){
    	if(sequence == null || sequence % 100 == 0){
    		sequence = this.testMasterlogDao.querySequence();
    		sequence = sequence * 100 + 1;
    	}else{
    		sequence++;
    	}
    	return sequence;
    }
    
    @Transactional
    public void addTestReport(JSONObject json) throws RuntimeException{
    	logger.debug("get in =====>addTestReport");
    		//插入测试头部信息
    		TestMasterlog master = new TestMasterlog();
    		master = init(json, master);
			this.addReportHead(master);
	    	//插入测试明细信息
			this.addReportBody(json,master);
			//修改投诉工单表里的测试次数
			if(json.get("stype")==null||json.get("stype").toString().equals("1")){
				this.modifyOrderstatus(master);
	    	}
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		logger.debug("ReciveUpTestData====>id:"+master.getId()+"|serialno:"+master.getSerialno()+"|flowid:"+master.getFlowid()+"|test_num:"+
    		master.getTestNumber()+"|uuid:"+master.getImei()+"|test_phone:"+master.getTestphone()+
    		"|test_adress:"+master.getTestaddress()+"|test_time:"+sdf.format(master.getTesttime())+"|test_endtime:"+sdf.format(master.getTestendtime()));
    }
    
    private TestMasterlog init(JSONObject json,TestMasterlog master){
    	master.setFlowid(this.getSerialNo()); //测试流水号
    	String sno = json.get("sno").toString();
    	logger.debug("当前测试工单号:" + sno);
    	master.setSerialno(sno);//工单号
    	master.setTestphone(json.get("pid")+""); //测试手机号码
    	master.setId(json.get("id")==null?"":json.get("id").toString());//工单流水号
    	master.setAreaid(Integer.parseInt(json.get("areaid")==null?"":json.get("areaid").toString()));//测试区域
    	master.setOrderType(Short.parseShort(json.get("stype")!=null?json.get("stype").toString():"1")); //工单类型
    	
    	//判断上报测试工单的类型(1投诉工单 2 任务工单 3自主测试工单)
    	if(json.get("stype")==null||json.get("stype").toString().equals("1")){
    		master.setOrders((short)(this.testMasterlogDao.queryOrderNum(sno) + 1));//当前第几次测试
    	}else if(json.get("stype").toString().equals("2")){
    		master.setOrders((short)(this.testMasterlogDao.queryTaskOrderNum(sno) + 1));//当前第几次测试
    	}else if(json.get("stype").toString().equals("3")){
    		master.setOrders((short)(this.testMasterlogDao.queryOwnOrderNum(sno) + 1));//当前第几次测试
    		//取出工单创建时间和测试环节
    		master.setCreateDate(getDateByLong(Long.parseLong(json.get("scti").toString())));
    		master.setBreakflag(Short.parseShort(json.get("breakflag").toString()));
    	}
    	
    	master.setTesttime(getDateByLong(Long.parseLong(json.get("ti").toString())));//测试时间
    	master.setImei(json.get("imei").toString()); //唯一标识
    	master.setFailure(json.get("fail")+""); //故障
    	short inside = Short.parseShort(json.get("is").toString());
    	master.setInside(inside);//1室内   0室外
    	master.setTestaddress(json.get("tsa")+""); //测试地址
    	master.setSceneid(Short.parseShort(json.get("si").toString()));//情景id
    	master.setDensity(Short.parseShort(json.get("ds").toString()));//0占位    1 密集   2稀疏
    	master.setIsindoor(Short.parseShort(json.get("idr").toString())); //室分，室内专用   0占位   1有   2无
    	master.setObstruct(Short.parseShort(json.get("ob").toString()));//0 无阻挡     1 有阻挡
    	master.setDescription(json.get("de") == null ? null : json.get("de").toString());//描述
    	master.setIsRepair(json.get("isRepair") == null?-1:Integer.parseInt(json.get("isRepair").toString()));//是否修复
    	master.setVersion(json.get("version") == null?"1.2.0":json.get("version").toString());//版本号
    	String dtp = null;
    	try {
			dtp = json.get("dtp").toString();
		} catch (Exception e) {
			dtp = "0";
		}
    	master.setRoom_type(Integer.parseInt(dtp));//0表示其他, 1表示2G室分, 2表示3G室分,3 表示 2g+3g室分,4表示4g,5表示2g+3g+4g
    	Object o = json.get("ltgt");
    	if(o != null){
    		master.setGpsType(Short.parseShort(o.toString()));//经纬度类型   1  百度   0 google
        	BigDecimal lng = new BigDecimal(NumberUtils.toDouble(json.get("lng")+"")); //经度
    		BigDecimal lat = new BigDecimal(NumberUtils.toDouble(json.get("lat")+"")); //纬度
        	MyLLOffset myLLOffset = MapUtil.getMyLLOffset(master.getGpsType(), lng, lat);
        	master.setLongitudeModify(myLLOffset.getNewLng()); //纠偏后经度
        	master.setLatitudeModify(myLLOffset.getNewLat()); //纠偏后纬度
        	if(master.getLatitudeModify()!=null&&master.getLongitudeModify()!=null){
        		ConverterTool ctl=new ConverterTool();
        		Point point = ctl.GG2BD(master.getLongitudeModify().doubleValue(), master.getLatitudeModify().doubleValue());
        		master.setLongitudeBmap(new BigDecimal(point.getLongitude())); //百度纠偏后经度
            	master.setLatitudeBmap(new BigDecimal(point.getLatitude())); //百度纠偏后纬度

        	}
        	master.setLongitude(myLLOffset.getOldLng()); //室外原始经度
        	master.setLatitude(myLLOffset.getOldLat());  //室外原始纬度
    	}
    	master.setNetsystem(Short.parseShort(json.get("ns").toString())); //网络制式:1 联通gsm锁频  2 wcdma锁频 3 wcdma 自由模式 4 lte锁频 5 lte自由模式
    	master.setTeststatus(Short.parseShort(json.get("ts").toString())); //手机状态:1表示Idle,2表示语音服务,3表示数据业务
    	master.setCalltype(Short.parseShort(json.get("ct").toString())); //长呼叫, 0占位   1 短呼   2 长呼
    	short ftptype = Short.parseShort(json.get("fud").toString());  //ftp上行或下行,  1 上行  2 下行  0 未测试数据业务
    	master.setFtpUpdown(ftptype); 
    	//有ftp业务时
    	if(ftptype != 0){
    		master.setFtpMaxSpeed(BigDecimal.valueOf(Double.parseDouble(json.get("fma").toString()))); //ftp最大速度
    		master.setFtpMinSpeed(BigDecimal.valueOf(Double.parseDouble(json.get("fmi").toString()))); //ftp最小速度
    		master.setFtpAvgSpeed(BigDecimal.valueOf(Double.parseDouble(json.get("fvg").toString()))); //ftp平均速度
    		master.setFtpMaxSpeedLte(BigDecimal.valueOf(json.get("fmat")!=null?Double.parseDouble(json.get("fmat").toString()):-9999)); //LTE ftp最大速度
    		master.setFtpMinSpeedLte(BigDecimal.valueOf(json.get("fmit")!=null?Double.parseDouble(json.get("fmit").toString()):-9999)); //LTE ftp最小速度
    		master.setFtpAvgSpeedLte(BigDecimal.valueOf(json.get("fvgt")!=null?Double.parseDouble(json.get("fvgt").toString()):-9999)); //LTE ftp平均速度
    		master.setPinglo(Double.parseDouble(json.get("pl").toString())); //PING丢包率
        	master.setPingdmax(Double.parseDouble(json.get("pma").toString())); //ping最大延迟
        	master.setPingdmix(Double.parseDouble(json.get("pmi").toString())); //ping最小延迟
        	master.setPingdavg(Double.parseDouble(json.get("pav").toString())); //ping平均延迟
        	master.setHttptmax(Double.parseDouble(json.get("hmac").toString())); //HTTP最大响应时间
        	master.setHttptmix(Double.parseDouble(json.get("hmic").toString())); //HTTP最小响应时间
        	master.setHttptavg(Double.parseDouble(json.get("havc").toString())); //HTTP平均响应时间
        	master.setHttpsmax(Double.parseDouble(json.get("hmad").toString())); //HTTP最大下载速度
        	master.setHttpsmin(Double.parseDouble(json.get("hmid").toString())); //HTTP最小下载速度
        	master.setHttpsavg(Double.parseDouble(json.get("havd").toString())); //HTTP平均下载速度
    	}
    	
    	master.setTestphone(json.get("pid").toString());  //测试手机号码
    	master.setTestendtime(getDateByLong(Long.parseLong(json.get("tedt").toString())));
    	if(master.getCalltype() != 0){
    		master.setCallphone(json.get("cph").toString());
    		if(master.getCalltype() == 1){
    			//短呼才有下面   1为短呼
    	    	master.setSpace(new BigDecimal(Double.parseDouble(json.get("spce").toString())));
    	    	master.setDuration(new BigDecimal(Double.parseDouble(json.get("dura").toString())));
    		}
    	}
    	return master;
    }
    
    private void addReportHead(TestMasterlog master) throws RuntimeException {
    	logger.debug("========>ready to insert master");
    	try {
    		//判断不同类型的工单测试入不同的测试总表
    		if(master.getOrderType()==1){//投诉工单
    			this.testMasterlogDao.insert(master);
    		}else if(master.getOrderType()==2){//任务工单
    			this.testMasterlogDao.insertTask(master);
    		}else if(master.getOrderType()==3){//自主工单
    			//如果是自主工单的第一次上报,先把上报的自主工单存入自主工单表
    			if(master.getOrders()==1){
    				workOrderDao.insertOwnWorkOrder(master);
    			}
    			
    			//自主工单测试总表
    			this.testMasterlogDao.insertOwn(master);
    		}
			
		} catch (Exception e) {
			logger.error("addReportHead invoke error。"+e.getMessage(), e);
			
			//判断不同类型的工单，执行不同的回退方式
    		if(master.getOrderType()==1){//投诉工单
    			this.deleteDataByFlowid(master.getFlowid());
    		}else if(master.getOrderType()==2){//任务工单
    			this.deleteTaskByFlowid(master.getFlowid());
    		}else if(master.getOrderType()==3){//自主工单
    			this.deleteOwnByFlowid(master.getFlowid());
    		}
			throw new RuntimeException(e);
		}
    }
    private void modifyOrderstatus(TestMasterlog master) throws RuntimeException {
    	try {
			WorkOrder wo = new WorkOrder();
			wo.setSerialno(master.getSerialno());
			wo.setTestNumber(1);
			wo.setIsDeal((short)1);
			wo.setAreaId(master.getAreaid());
			wo.setId(master.getId());
			workOrderDao.updateWOTestNumber(wo);
		} catch (Exception e) {
			logger.error("modifyOrderstatus invoke error。"+e.getMessage(), e);
			this.deleteDataByFlowid(master.getFlowid());
			throw new RuntimeException(e);
		}
    }
    
    private void addReportBody(JSONObject json,TestMasterlog master) throws RuntimeException{
    	JSONArray array = (JSONArray)json.get("ctt");
    	JSONObject subJson = null;
    	List<LogSubmanualGsm> gsmList = new ArrayList<LogSubmanualGsm>();
    	List<WcdmsTrackLog> wcdmList = new ArrayList<WcdmsTrackLog>();
    	List<LogSubmanualLte> lteList = new ArrayList<LogSubmanualLte>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	for(int i=0;i<array.size();i++){
    		subJson = (JSONObject)array.get(i);
    		if("2G".equals(this.checkNetType(Integer.parseInt(subJson.get("nt").toString())))){
    			this.addGsmToList(gsmList,subJson,master);
    		}else if(subJson.get("nt").toString().equals("-1")){
		    			if(subJson.get("rsr")!=null&&subJson.get("rsr").toString().equals("-9998")){
		    				this.addLteToList(lteList,subJson,master);
		    			}else if(subJson.get("rscp")!=null&&subJson.get("rscp").toString().equals("-9998")){
		    				this.addWcdmsToList(wcdmList,subJson,master,map);
		    			}else if(subJson.get("rlf")!=null&&subJson.get("rlf").toString().equals("-9998")){
		    				this.addGsmToList(gsmList,subJson,master);
		    			}
    		}else if("4G".equals(this.checkNetType(Integer.parseInt(subJson.get("nt").toString())))){
    			//4G数据入库	
    			this.addLteToList(lteList,subJson,master);
    		}else{
    			this.addWcdmsToList(wcdmList,subJson,master,map);
    		}
    	}
    	/*for (int i = 0; i < gsmList.size(); i++) {
    	//if(gsmList.size()>0){
    		logSubmanualGsmDao.insert(gsmList.get(i));
    	//}
		}
    	for (int i = 0; i < wcdmList.size(); i++) {
			wcdmsTrackLogDao.insert(wcdmList.get(i));
		}*/
    	try {
    		//根据不同的工单类型判断不同的批量插入
    		if(master.getOrderType()==1){//投诉工单
    			baseDao.batchInsert(LogSubmanualGsmDao.class, gsmList, 300);
    			baseDao.batchInsert(WcdmsTrackLogDao.class, wcdmList, 300);
    			baseDao.batchInsert(LteTrackLogDao.class, lteList, 300);
    		}else if(master.getOrderType()==2){//任务工单
    			baseDao.batchInsert(GsmTaskLogDao.class, gsmList, 300);
    			baseDao.batchInsert(WcdmsTaskLogDao.class, wcdmList, 300);
    		}else if(master.getOrderType()==3){//自主工单
    			baseDao.batchInsert(GsmOwnLogDao.class, gsmList, 300);
    			baseDao.batchInsert(WcdmsOwnLogDao.class, wcdmList, 300);
    		}
		} catch (Exception e) {
			logger.error("batchInsert error!message["+e.getMessage()+"]", e);
			String flowid = "";
			WcdmsTrackLog wcdmaLog = wcdmList.get(0);
			if(wcdmaLog == null){
				LogSubmanualGsm gsmLog = gsmList.get(0);
				flowid = gsmLog == null ? "" : gsmLog.getFlowid();
			}else{
				flowid = wcdmaLog.getFlowid();
			}
			if(!"".equals(flowid)){
				//判断不同类型的工单，执行不同的回退方式
	    		if(master.getOrderType()==1){//投诉工单
	    			this.deleteDataByFlowid(master.getFlowid());
	    		}else if(master.getOrderType()==2){//任务工单
	    			this.deleteTaskByFlowid(master.getFlowid());
	    		}else if(master.getOrderType()==3){//自主工单
	    			this.deleteOwnByFlowid(master.getFlowid());
	    		}
			}
			throw new RuntimeException(e.getMessage());
		}
    	logger.debug("===>insert success!");
    }
    
    //投诉工单的回滚
    @Transactional
    private void deleteDataByFlowid(String flowid){
    	if(flowid != null && !"".equals(flowid)){
    		wcdmsTrackLogDao.delWcdmaByFlowid(flowid);
    		logSubmanualGsmDao.delGsmByFlowid(flowid);
    	}
    }
    
    //任务工单的回滚
    @Transactional
    private void deleteTaskByFlowid(String flowid){
    	if(flowid != null && !"".equals(flowid)){
    		wcdmsTaskLogDao.delTaskWcdmaByFlowid(flowid);
    		gsmTaskLogDao.delTaskGsmByFlowid(flowid);
    	}
    }
    
    //自主工单的回滚
    @Transactional
    private void deleteOwnByFlowid(String flowid){
    	if(flowid != null && !"".equals(flowid)){
    		wcdmsOwnLogDao.delOwnWcdmaByFlowid(flowid);
    		gsmOwnLogDao.delOwnGsmByFlowid(flowid);
    	}
    }
    
    private String checkNetType(Integer typeid){
    	if(netTypeMap == null)
    		this.initNetTypeList();
    	return netTypeMap.get(typeid);
    }
    
    private void addGsmToList(List<LogSubmanualGsm> gsmList,JSONObject json,TestMasterlog master){
    	LogSubmanualGsm gsm = new LogSubmanualGsm();
    	gsm.setFlowid(master.getFlowid()); //当前测试流水号
    	gsm.setUuid(master.getImei());  //终端唯一标识
    	gsm.setInside(master.getInside()); //室内/室外
    	gsm.setEpTime(getDateByLong(NumberUtils.toLong(json.get("td").toString()))); //终端时间
    	gsm.setTesttime(master.getTesttime());//测试时间与测试总表一致
    	gsm.setAreaid(master.getAreaid());
    	if(master.getInside() == 0){
    		//0为室外  室外才有
    		BigDecimal lng = new BigDecimal((json.get("lng").toString())); //室外 原始经度
    		BigDecimal lat = new BigDecimal((json.get("lat").toString())); //室外 原始纬度
    		short gpsType = Short.parseShort(json.get("ltgt").toString());
    		gsm.setGpsType(gpsType);
    		MyLLOffset myLLOffset = MapUtil.getMyLLOffset(gpsType, lng, lat);
    		gsm.setLongitudeModify(myLLOffset.getNewLng()); //室外 纠正经度
    		gsm.setLatitudeModify(myLLOffset.getNewLat());  // 室外  纠正纬度
    		if(gsm.getLatitudeModify()!=null&&gsm.getLongitudeModify()!=null){
        		ConverterTool ctl=new ConverterTool();
        		Point point = ctl.GG2BD(gsm.getLongitudeModify().doubleValue(), gsm.getLatitudeModify().doubleValue());
        		gsm.setLongitudeBmap(new BigDecimal(point.getLongitude())); //百度纠偏后经度
        		gsm.setLatitudeBmap(new BigDecimal(point.getLatitude())); //百度纠偏后纬度
        	}
        	gsm.setLongitude(myLLOffset.getOldLng()); //室外原始经度
        	gsm.setLatitude(myLLOffset.getOldLat());  //室外原始纬度
    	}
    	else if(master.getInside() == 1){    		
    		//室内才有   1为室内
    		gsm.setPositionX(new BigDecimal(Double.parseDouble(json.get("px").toString())));  //室内  x轴坐标
    		gsm.setPositionY(new BigDecimal(Double.parseDouble(json.get("py").toString())));  //室内  y轴坐标
    	}
		gsm.setRealnetType(Short.parseShort(json.get("nt").toString()));  //实际网络
		gsm.setLac(Long.parseLong(json.get("lac").toString()));  //lac
		gsm.setCid(Long.parseLong(json.get("cid").toString()));  //cid
		gsm.setBsic((Long)json.get("bsic")); //bsic
		gsm.setBcch((Long)json.get("bcch")); //bcch
		gsm.setRxlevSub((Long)json.get("rls")); //rxLev_sub
		gsm.setRxlevFull((Long)json.get("rlf")); //rxLev_full	
		if(json.get("rqs") != null){			
			gsm.setRxqualSub(new BigDecimal(Double.parseDouble(json.get("rqs").toString()))); //rxQual_sub
		}
		if(json.get("rqf") != null){			
			gsm.setRxqualFull(new BigDecimal(Double.parseDouble(json.get("rqf").toString()))); //rxQual_full
		}
		if(json.get("ci") != null){			
			gsm.setcI(new BigDecimal(Double.parseDouble(json.get("ci").toString()))); //c/i
		}
		if(json.get("tp") != null){			
			gsm.setTxpower(new BigDecimal(Double.parseDouble(json.get("tp").toString()))); //txpower
		}
		gsm.setMos((Long)json.get("mos")); //mos
		gsm.setTa(json.get("ta") == null ? null : Integer.parseInt(json.get("ta").toString()));//ta
		gsm.setBsic1((Long)json.get("bs1")); //bsic_1
		gsm.setBsic2((Long)json.get("bs2")); //bsic_2
		gsm.setBsic3((Long)json.get("bs3")); //bsic_3
		gsm.setBsic4((Long)json.get("bs4")); //bsic_4
		gsm.setBsic5((Long)json.get("bs5")); //bsic_5
		gsm.setBsic6((Long)json.get("bs6")); //bsic_6
		
		gsm.setBcch1((Long)json.get("bc1")); //bcch_1
		gsm.setBcch2((Long)json.get("bc2")); //bcch_2
		gsm.setBcch3((Long)json.get("bc3")); //bcch_3
		gsm.setBcch4((Long)json.get("bc4")); //bcch_4
		gsm.setBcch5((Long)json.get("bc5")); //bcch_5
		gsm.setBcch6((Long)json.get("bc6")); //bcch_6
		
		gsm.setRxlev1((Long)json.get("rx1")); //rxlev_1
		gsm.setRxlev2((Long)json.get("rx2")); //rxlev_2
		gsm.setRxlev3((Long)json.get("rx3")); //rxlev_3
		gsm.setRxlev4((Long)json.get("rx4")); //rxlev_4
		gsm.setRxlev5((Long)json.get("rx5")); //rxlev_5
		gsm.setRxlev6((Long)json.get("rx6")); //rxlev_6
		gsm.setC1(json.get("c1") == null ? null : Integer.parseInt(json.get("c1").toString()));//c1
		gsm.setC2(json.get("c2") == null ? null : Integer.parseInt(json.get("c2").toString()));//c2
		gsm.setC11(json.get("c1_1") == null ? null : Integer.parseInt(json.get("c1_1").toString()));//c11
		gsm.setC21(json.get("c2_1") == null ? null : Integer.parseInt(json.get("c2_1").toString()));//c21
		gsm.setC12(json.get("c1_2") == null ? null : Integer.parseInt(json.get("c1_2").toString()));//c12
		gsm.setC22(json.get("c2_2") == null ? null : Integer.parseInt(json.get("c2_2").toString()));//c22
		gsm.setC13(json.get("c1_3") == null ? null : Integer.parseInt(json.get("c1_3").toString()));//c13
		gsm.setC23(json.get("c2_3") == null ? null : Integer.parseInt(json.get("c2_3").toString()));//c23
		gsm.setC14(json.get("c1_4") == null ? null : Integer.parseInt(json.get("c1_4").toString()));//c14
		gsm.setC24(json.get("c2_4") == null ? null : Integer.parseInt(json.get("c2_4").toString()));//c24
		gsm.setC15(json.get("c1_5") == null ? null : Integer.parseInt(json.get("c1_5").toString()));//c15
		gsm.setC25(json.get("c2_5") == null ? null : Integer.parseInt(json.get("c2_5").toString()));//c25
		gsm.setC16(json.get("c1_6") == null ? null : Integer.parseInt(json.get("c1_6").toString()));//c16
		gsm.setC26(json.get("c2_6") == null ? null : Integer.parseInt(json.get("c2_6").toString()));//c26
		
		gsmList.add(gsm);
    }
    private void addWcdmsToList(List<WcdmsTrackLog> wcdmList,JSONObject json,TestMasterlog master,Map map){
    	short inside = master.getInside();
    	//找到前一个CID与LAC
    	TCasCell cell=null;
    	//当业务态下要看前一条数据计算CID、LAC
    	WcdmsTrackLog wcdm = new WcdmsTrackLog();
    	WcdmsTrackLog wt=null;
    	if(wcdmList!=null&&wcdmList.size()>0&&json.get("tp") != null){
    		map.put("lac", wcdmList.get(wcdmList.size()-1).getLac());
    		map.put("cid", wcdmList.get(wcdmList.size()-1).getCid());
    		map.put("psc", Integer.parseInt(json.get("psc").toString()));
        	cell=this.testMasterlogDao.updateCidLac(map);
        	if(cell!=null){
        		
        		map.put("celllat", cell.getCelllat());
        		map.put("celllng", cell.getCelllng());
        		if(inside == 0){
            		map.put("lat", json.get("lat").toString());
            		map.put("lng", json.get("lng").toString());
        		}else{
        			map.put("lat", master.getLatitude());
            		map.put("lng", master.getLongitude());
        		}
        		 wt=this.testMasterlogDao.updateCidLacBylatlng(map);
        		 if(wt!=null&&cell.getLac().equals(wt.getLac())&&cell.getCellId().equals(wt.getCid())){
        			 wcdm.setIsequal((short) 0);
        		 }else{
        			 wcdm.setIsequal((short) 1);
        		 }
        	}else{
        		wcdm.setIsequal((short) 0);
        	}
        	wcdm.setLac_1(cell==null?Long.parseLong(json.get("lac").toString()):cell.getLac());  //lac
         	wcdm.setCid_1(cell==null?Long.parseLong(json.get("cid").toString()):cell.getCellId());  //cid
     		wcdm.setLac_2(wt==null?Long.parseLong(json.get("lac").toString()):wt.getLac());  //lac
         	wcdm.setCid_2(wt==null?Long.parseLong(json.get("cid").toString()):wt.getCid());  //cid
         	wcdm.setRange(wt==null?0:wt.getRange());
    	}else{
    		wcdm.setLac_1(Long.parseLong(json.get("lac").toString()));  //lac
        	wcdm.setCid_1(Long.parseLong(json.get("cid").toString()));  //cid
    		wcdm.setLac_2(Long.parseLong(json.get("lac").toString()));  //lac
        	wcdm.setCid_2(Long.parseLong(json.get("cid").toString()));  //cid
        	wcdm.setRange((long) 0);
        	wcdm.setIsequal((short) 0);
    	}
    	
    	wcdm.setFlowid(master.getFlowid());//当前测试流水号
    	wcdm.setUuid(master.getImei()); //终端唯一标识
    	wcdm.setAreaid(master.getAreaid());
    	wcdm.setInside(inside); //室内/室外
    	wcdm.setFtptype(master.getFtpUpdown()); //ftp上传下载
    	wcdm.setEpTime(getDateByLong(NumberUtils.toLong(json.get("td").toString()))); //终端时间
    	wcdm.setTesttime(master.getTesttime());//测试时间与总表一致
		//0室外
		if(inside == 0){
			// 1  百度   0 google
			short gpsType = Short.parseShort(json.get("ltgt").toString());
			wcdm.setGpsType(gpsType);
			BigDecimal lng = new BigDecimal(Double.parseDouble(json.get("lng").toString())); //室外 原始经度
			BigDecimal lat = new BigDecimal(Double.parseDouble(json.get("lat").toString())); //室外 原始纬度
			MyLLOffset myLLOffset = MapUtil.getMyLLOffset(gpsType, lng, lat);
			wcdm.setLongitudeModify(myLLOffset.getNewLng()); //室外 纠正经度
			wcdm.setLatitudeModify(myLLOffset.getNewLat());  // 室外  纠正纬度
			if(wcdm.getLatitudeModify()!=null&&wcdm.getLongitudeModify()!=null){
        		ConverterTool ctl=new ConverterTool();
        		Point point = ctl.GG2BD(wcdm.getLongitudeModify().doubleValue(), wcdm.getLatitudeModify().doubleValue());
        		wcdm.setLongitudeBmap(new BigDecimal(point.getLongitude())); //百度纠偏后经度
        		wcdm.setLatitudeBmap(new BigDecimal(point.getLatitude())); //百度纠偏后纬度
        	}
	    	wcdm.setLongitude(myLLOffset.getOldLng()); //室外原始经度
        	wcdm.setLatitude(myLLOffset.getOldLat());  //室外原始纬度
		}else{
			wcdm.setPositionX(new BigDecimal(Double.parseDouble(json.get("px").toString())));  //室内  x轴坐标
	    	wcdm.setPositionY(new BigDecimal(Double.parseDouble(json.get("py").toString())));  //室内  y轴坐标
		}
    	wcdm.setRealnetType(Short.parseShort(json.get("nt").toString()));  //实际网络
    	wcdm.setLac(Long.parseLong(json.get("lac").toString()));  //lac
        wcdm.setCid(Long.parseLong(json.get("cid").toString()));  //cid
    	wcdm.setPsc(Integer.parseInt(json.get("psc").toString()));  //psc
    	wcdm.setFrequl(Integer.parseInt(json.get("afup").toString())); //freq_ul
    	wcdm.setFreqdl(Integer.parseInt(json.get("afdn").toString())); //freq_dl
    	wcdm.setRscp(Integer.parseInt(json.get("rscp").toString())); //rscp
    	wcdm.setEcNo(Integer.parseInt(json.get("ecno").toString())); //ecno
    	if(json.get("tp") != null){    		
    		wcdm.setTxpower(new BigDecimal(Double.parseDouble(json.get("tp").toString()))); //txpower
    	}
    	if(json.get("fs") != null){    		
    		wcdm.setFtpSpeed(new BigDecimal(Double.parseDouble(json.get("fs").toString())));  //ftp_speed
    	}
    	wcdm.setType(json.get("ltp")!=null?Short.parseShort(json.get("ltp").toString()):0); //0-正常3G测试数据    1- LTE模式获取不完整测试数据类型
    	wcdm.setaPsc1(json.get("pa1") == null? null : Integer.parseInt(json.get("pa1").toString())); //a_psc_1
    	wcdm.setaPsc2(json.get("pa2") == null? null : Integer.parseInt(json.get("pa2").toString())); //a_psc_2
    	wcdm.setaPsc3(json.get("pa3") == null? null : Integer.parseInt(json.get("pa3").toString())); //a_psc_3
    	wcdm.setmPsc1(json.get("pm1") == null? null : Integer.parseInt(json.get("pm1").toString())); //m_psc_1
    	wcdm.setmPsc2(json.get("pm2") == null? null : Integer.parseInt(json.get("pm2").toString())); //m_psc_2
    	wcdm.setmPsc3(json.get("pm3") == null? null : Integer.parseInt(json.get("pm3").toString())); //m_psc_3
    	wcdm.setmPsc4(json.get("pm4") == null? null : Integer.parseInt(json.get("pm4").toString())); //m_psc_4
    	wcdm.setmPsc5(json.get("pm5") == null? null : Integer.parseInt(json.get("pm5").toString())); //m_psc_5
    	wcdm.setmPsc6(json.get("pm6") == null? null : Integer.parseInt(json.get("pm6").toString())); //m_psc_6
    	wcdm.setdPsc1(json.get("pd1") == null? null : Integer.parseInt(json.get("pd1").toString())); //d_psc_1
    	wcdm.setdPsc2(json.get("pd2") == null? null : Integer.parseInt(json.get("pd2").toString())); //d_psc_2
    	
    	wcdm.setaRscp1(json.get("ra1") == null? null : Integer.parseInt(json.get("ra1").toString())); //a_rscp_1
    	wcdm.setaRscp2(json.get("ra2") == null? null : Integer.parseInt(json.get("ra2").toString())); //a_rscp_2
    	wcdm.setaRscp3(json.get("ra3") == null? null : Integer.parseInt(json.get("ra3").toString())); //a_rscp_3
    	wcdm.setmRscp1(json.get("rm1") == null? null : Integer.parseInt(json.get("rm1").toString())); //m_rscp_1
    	wcdm.setmRscp2(json.get("rm2") == null? null : Integer.parseInt(json.get("rm2").toString())); //m_rscp_2
    	wcdm.setmRscp3(json.get("rm3") == null? null : Integer.parseInt(json.get("rm3").toString())); //m_rscp_3
    	wcdm.setmRscp4(json.get("rm4") == null? null : Integer.parseInt(json.get("rm4").toString())); //m_rscp_4
    	wcdm.setmRscp5(json.get("rm5") == null? null : Integer.parseInt(json.get("rm5").toString())); //m_rscp_5
    	wcdm.setmRscp6(json.get("rm6") == null? null : Integer.parseInt(json.get("rm6").toString())); //m_rscp_6
    	wcdm.setdRscp1(json.get("rd1") == null? null : Integer.parseInt(json.get("rd1").toString())); //d_rscp_1
    	wcdm.setdRscp2(json.get("rd2") == null? null : Integer.parseInt(json.get("rd2").toString())); //d_rscp_2
    	
    	wcdm.setaEcNo1(json.get("ea1") == null? null : Integer.parseInt(json.get("ea1").toString())); //a_ec/no_1
    	wcdm.setaEcNo2(json.get("ea2") == null? null : Integer.parseInt(json.get("ea2").toString())); //a_ec/no_2
    	wcdm.setaEcNo3(json.get("ea3") == null? null : Integer.parseInt(json.get("ea3").toString())); //a_ec/no_3
    	wcdm.setmEcNo1(json.get("em1") == null? null : Integer.parseInt(json.get("em1").toString())); //m_ec_no_1
    	wcdm.setmEcNo2(json.get("em2") == null? null : Integer.parseInt(json.get("em2").toString())); //m_ec_no_2
    	wcdm.setmEcNo3(json.get("em3") == null? null : Integer.parseInt(json.get("em3").toString())); //m_ec_no_3
    	wcdm.setmEcNo4(json.get("em4") == null? null : Integer.parseInt(json.get("em4").toString())); //m_ec_no_4
    	wcdm.setmEcNo5(json.get("em5") == null? null : Integer.parseInt(json.get("em5").toString())); //m_ec_no_5
    	wcdm.setmEcNo6(json.get("em6") == null? null : Integer.parseInt(json.get("em6").toString())); //m_ec_no_6
    	wcdm.setdEcNo1(json.get("ed1") == null? null : Integer.parseInt(json.get("ed1").toString())); //d_ec_no_1
    	wcdm.setdEcNo2(json.get("ed2") == null? null : Integer.parseInt(json.get("ed2").toString())); //d_ec_no_2
    	
    	wcdm.setaArfcn1(json.get("aa1") == null? null : Integer.parseInt(json.get("aa1").toString())); //a_arfcn_1
    	wcdm.setaArfcn2(json.get("aa2") == null? null : Integer.parseInt(json.get("aa2").toString())); //a_arfcn_2
    	wcdm.setaArfcn3(json.get("aa3") == null? null : Integer.parseInt(json.get("aa3").toString())); //a_arfcn_3
    	wcdm.setmArfcn1(json.get("am1") == null? null : Integer.parseInt(json.get("am1").toString())); //m_arfcn_1
    	wcdm.setmArfcn2(json.get("am2") == null? null : Integer.parseInt(json.get("am2").toString())); //m_arfcn_2
    	wcdm.setmArfcn3(json.get("am3") == null? null : Integer.parseInt(json.get("am3").toString())); //m_arfcn_3
    	wcdm.setmArfcn4(json.get("am4") == null? null : Integer.parseInt(json.get("am4").toString())); //m_arfcn_4
    	wcdm.setmArfcn5(json.get("am5") == null? null : Integer.parseInt(json.get("am5").toString())); //m_arfcn_5
    	wcdm.setmArfcn6(json.get("am6") == null? null : Integer.parseInt(json.get("am6").toString())); //m_arfcn_6
    	wcdm.setdArfcn1(json.get("ad1") == null? null : Integer.parseInt(json.get("ad1").toString())); //d_arfcn_1
    	wcdm.setdArfcn2(json.get("ad2") == null? null : Integer.parseInt(json.get("ad2").toString())); //d_arfcn_2
    	
    	wcdmList.add(wcdm);
    }
    
    /**
     * 4G数据组装
     * @param lteList
     * @param json
     * @param master
     * @param map
     */
    private void addLteToList(List<LogSubmanualLte> lteList,JSONObject json,TestMasterlog master){
    	LogSubmanualLte lte = new LogSubmanualLte();
    	lte.setFlowid(master.getFlowid()); //当前测试流水号
    	lte.setUuid(master.getImei());  //终端唯一标识
    	lte.setInside(master.getInside()); //室内/室外
    	lte.setEpTime(getDateByLong(NumberUtils.toLong(json.get("td").toString()))); //终端时间
    	lte.setTesttime(master.getTesttime());//测试时间与测试总表一致
    	lte.setAreaid(master.getAreaid());
    	if(master.getInside() == 0){
    		//0为室外  室外才有
    		BigDecimal lng = new BigDecimal((json.get("lng").toString())); //室外 原始经度
    		BigDecimal lat = new BigDecimal((json.get("lat").toString())); //室外 原始纬度
    		short gpsType = Short.parseShort(json.get("ltgt").toString());
    		lte.setGpsType(gpsType);
    		MyLLOffset myLLOffset = MapUtil.getMyLLOffset(gpsType, lng, lat);
    		lte.setLongitudeModify(myLLOffset.getNewLng()); //室外 纠正经度
    		lte.setLatitudeModify(myLLOffset.getNewLat());  // 室外  纠正纬度
    		if(lte.getLatitudeModify()!=null&&lte.getLongitudeModify()!=null){
        		ConverterTool ctl=new ConverterTool();
        		Point point = ctl.GG2BD(lte.getLongitudeModify().doubleValue(), lte.getLatitudeModify().doubleValue());
        		lte.setLongitudeBmap(new BigDecimal(point.getLongitude())); //百度纠偏后经度
        		lte.setLatitudeBmap(new BigDecimal(point.getLatitude())); //百度纠偏后纬度
        	}
    		lte.setLongitude(myLLOffset.getOldLng()); //室外原始经度
    		lte.setLatitude(myLLOffset.getOldLat());  //室外原始纬度
    	}
    	else if(master.getInside() == 1){    		
    		//室内才有   1为室内
    		lte.setPositionX(new BigDecimal(Double.parseDouble(json.get("px").toString())));  //室内  x轴坐标
    		lte.setPositionY(new BigDecimal(Double.parseDouble(json.get("py").toString())));  //室内  y轴坐标
    	}
    	lte.setRealnetType(Short.parseShort(json.get("nt").toString()));  //实际网络
    	lte.setTac(Long.parseLong(json.get("tac").toString()));  //tac
    	lte.setCid(Long.parseLong(json.get("cid").toString()));  //cid
    	if(json.get("rsr") != null){			
			lte.setRsrp(new BigDecimal(Double.parseDouble(json.get("rsr").toString()))); //rsrp
		}
    	lte.setRsrq((Long)json.get("rrq")); //Rsrq
    	if(json.get("snr") != null){			
			lte.setSnr(new BigDecimal(Double.parseDouble(json.get("snr").toString()))); //sinr
		}
    	lte.setCqi((Long)json.get("cqi")); //cqi
    	lte.setPci((Long)json.get("pci")); //rxLev_full	
    	lte.setEbid((Long)json.get("ebd")); //ebid	
    	lte.setFtptype(master.getFtpUpdown()); //ftp上传下载
    	if(json.get("fs") != null){    		
    		lte.setFtpSpeed(new BigDecimal(Double.parseDouble(json.get("fs").toString())));  //ftp_speed
    	}
		lteList.add(lte);
    }
    
    private static Date getDateByLong(long timeForMilli){
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(timeForMilli);
    	return cal.getTime();
    }
    
    public void testNonTransactional(){
    	logger.debug("------>nonTransactional");
    }
    public int updateAdress(TestMasterlog testMasterlog){
    	return this.testMasterlogDao.updateAdress(testMasterlog);
    }
    
    public TestMasterlog selectByPrimaryKey(String flowid){
    	return this.testMasterlogDao.selectByPrimaryKey(flowid);
    }
}
