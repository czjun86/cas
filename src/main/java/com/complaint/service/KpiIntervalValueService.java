package com.complaint.service;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.complaint.dao.KpiIntervalValueDao;
import com.complaint.io.ObjectSerializableUtil;
import com.complaint.model.ConfigColor;
import com.complaint.model.Kpi;
import com.complaint.model.KpiBean;
import com.complaint.model.KpiForm;
import com.complaint.model.KpiIntervalValue;
import com.complaint.utils.PatternUtil;

@Service("kpiIntervalValueService")
public class KpiIntervalValueService {
	@Autowired
	private KpiIntervalValueDao kpiIntervalValueDao;
	private static final Logger logger = LoggerFactory.getLogger(KpiIntervalValueService.class);
	
	private static int[] kpifor2g = {22,6,7,8,9};
	private static int[] kpifor3g = {1,2,3,4,5,10,11,12,13,14,15,16,17,18,19};
	private static int[] kpifor4g = {23,24,25 ,4,5,10,11,12,13,14,15,16,17,18,19};
	//降序的kpi
	private static int[] kpifordesc = {22,7,3};
	/**
	 * 查询所有的配置
	 * @return
	 */
	public List<KpiIntervalValue> getAllKipIntervalValues(){
		return kpiIntervalValueDao.queryAllKipIntervalValues();
	}
	/**
	 * 获取kpi
	 * @return
	 */
	public Map<Integer,Kpi> getKpi(){
		List<KpiIntervalValue> kpis = this.getAllKipIntervalValues();
		Map<String ,Object> valueMap = initKpiMap(kpis);
		Map<Integer ,Kpi> map = new HashMap<Integer, Kpi>();
		Kpi kpiOutSide = initKpiOutside((Map<Integer, List<KpiIntervalValue>>)valueMap.get("kpiOutsideMap"));
		Kpi kpiInside = initKpiInside((Map<Integer, List<KpiIntervalValue>>)valueMap.get("kpiInsideMap"));
		
//		0外，1内
		map.put(0, kpiOutSide);
		map.put(1, kpiInside);
		return map;
	}

	/**
	 * 批量修改配置
	 * @param kpiForm
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String updateKpiIntervals(KpiForm kpiForm, Integer ischange) throws Exception{
			if(ischange >= 1){
//			室外属性
//			update(kpiForm.getTxPowerfor2g(), (short)1, (short)22, (short)0 );
			update(kpiForm.getRxlev(), (short)1, (short)6, (short)0 );
			update(kpiForm.getRxQual(), (short)1, (short)7, (short)0 );
			update(kpiForm.getCi(), (short)1, (short)8, (short)0 );
//			update(kpiForm.getMos(), (short)1, (short)9, (short)0 );
			update(kpiForm.getRscp(), (short)2,(short)1, (short)0 );
			update(kpiForm.getEcno(), (short)2,(short)2, (short)0 );
			update(kpiForm.getTxPowerfor3g(), (short)2,(short)3, (short)0 );
			update(kpiForm.getFtpUp(), (short)2,(short)4, (short)0 );
			update(kpiForm.getFtpDown(), (short)2,(short)5, (short)0 );
			update(kpiForm.getPingLoss(), (short)2,(short)10, (short)0 );
			update(kpiForm.getPingMaxDelay(), (short)2,(short)11, (short)0 );
			update(kpiForm.getPingMinDelay(), (short)2,(short)12, (short)0 );
			update(kpiForm.getPingAvgDelay(), (short)2,(short)13, (short)0 );
			update(kpiForm.getHttpMaxResponseTime(), (short)2,(short)14, (short)0 );
			update(kpiForm.getHttpMinResponseTime(), (short)2,(short)15, (short)0 );
			update(kpiForm.getHttpAvgResponseTime(), (short)2,(short)16, (short)0 );
			update(kpiForm.getHttpMaxDownloadSpeed(), (short)2,(short)17, (short)0 );
			update(kpiForm.getHttpMinDownloadSpeed(), (short)2,(short)18, (short)0 );
			update(kpiForm.getHttpAvgDownloadSpeed(), (short)2,(short)19, (short)0 );
			
			update(kpiForm.getRsrp(), (short)3,(short)23, (short)0 );
			update(kpiForm.getRsrq(), (short)3,(short)24, (short)0 );
			update(kpiForm.getSinr(), (short)3,(short)25, (short)0 );
			update(kpiForm.getFtpUp4G(), (short)3,(short)4, (short)0 );
			update(kpiForm.getFtpDown4G(), (short)3,(short)5, (short)0 );
			update(kpiForm.getPingLoss4G(), (short)3,(short)10, (short)0 );
			update(kpiForm.getPingMaxDelay4G(), (short)3,(short)11, (short)0 );
			update(kpiForm.getPingMinDelay4G(), (short)3,(short)12, (short)0 );
			update(kpiForm.getPingAvgDelay4G(), (short)3,(short)13, (short)0 );
			update(kpiForm.getHttpMaxResponseTime4G(), (short)3,(short)14, (short)0 );
			update(kpiForm.getHttpMinResponseTime4G(), (short)3,(short)15, (short)0 );
			update(kpiForm.getHttpAvgResponseTime4G(), (short)3,(short)16, (short)0 );
			update(kpiForm.getHttpMaxDownloadSpeed4G(), (short)3,(short)17, (short)0 );
			update(kpiForm.getHttpMinDownloadSpeed4G(), (short)3,(short)18, (short)0 );
			update(kpiForm.getHttpAvgDownloadSpeed4G(), (short)3,(short)19, (short)0 );
//			室内属性
//			update(kpiForm.getTxPowerfor2gInside(), (short)1, (short)22, (short)1 );
			update(kpiForm.getRxlevInside(), (short)1, (short)6, (short)1 );
			update(kpiForm.getRxQualInside(), (short)1, (short)7, (short)1 );
			update(kpiForm.getCiInside(), (short)1, (short)8, (short)1 );
//			update(kpiForm.getMosInside(), (short)1, (short)9, (short)1 );
			update(kpiForm.getRscpInside(), (short)2,(short)1, (short)1 );
			update(kpiForm.getEcnoInside(), (short)2,(short)2, (short)1 );
			update(kpiForm.getTxPowerfor3gInside(), (short)2,(short)3, (short)1 );
			update(kpiForm.getFtpUpInside(), (short)2,(short)4, (short)1 );
			update(kpiForm.getFtpDownInside(), (short)2,(short)5, (short)1 );
			update(kpiForm.getPingLossInside(), (short)2,(short)10, (short)1 );
			update(kpiForm.getPingMaxDelayInside(), (short)2,(short)11, (short)1 );
			update(kpiForm.getPingMinDelayInside(), (short)2,(short)12, (short)1 );
			update(kpiForm.getPingAvgDelayInside(), (short)2,(short)13, (short)1 );
			update(kpiForm.getHttpMaxResponseTimeInside(), (short)2,(short)14, (short)1 );
			update(kpiForm.getHttpMinResponseTimeInside(), (short)2,(short)15, (short)1 );
			update(kpiForm.getHttpAvgResponseTimeInside(), (short)2,(short)16, (short)1 );
			update(kpiForm.getHttpMaxDownloadSpeedInside(), (short)2,(short)17, (short)1 );
			update(kpiForm.getHttpMinDownloadSpeedInside(), (short)2,(short)18, (short)1 );
			update(kpiForm.getHttpAvgDownloadSpeedInside(), (short)2,(short)19, (short)1 );
			//4G数据
			update(kpiForm.getRsrpInside(), (short)3,(short)23, (short)1 );
			update(kpiForm.getRsrqInside(), (short)3,(short)24, (short)1 );
			update(kpiForm.getSinrInside(), (short)3,(short)25, (short)1 );
			update(kpiForm.getFtpUpInside4G(), (short)3,(short)4, (short)1 );
			update(kpiForm.getFtpDownInside4G(), (short)3,(short)5, (short)1 );
			update(kpiForm.getPingLossInside4G(), (short)3,(short)10, (short)1 );
			update(kpiForm.getPingMaxDelayInside4G(), (short)3,(short)11, (short)1 );
			update(kpiForm.getPingMinDelayInside4G(), (short)3,(short)12, (short)1 );
			update(kpiForm.getPingAvgDelayInside4G(), (short)3,(short)13, (short)1 );
			update(kpiForm.getHttpMaxResponseTimeInside4G(), (short)3,(short)14, (short)1 );
			update(kpiForm.getHttpMinResponseTimeInside4G(), (short)3,(short)15, (short)1 );
			update(kpiForm.getHttpAvgResponseTimeInside4G(), (short)3,(short)16, (short)1 );
			update(kpiForm.getHttpMaxDownloadSpeedInside4G(), (short)3,(short)17, (short)1 );
			update(kpiForm.getHttpMinDownloadSpeedInside4G(), (short)3,(short)18, (short)1 );
			update(kpiForm.getHttpAvgDownloadSpeedInside4G(), (short)3,(short)19, (short)1 );
			
			String filePath = this.getClass().getClassLoader().getResource("").getPath()+"/colorvision.txt";
			ConfigColor color = ObjectSerializableUtil.readObject(filePath);
			if(color == null){
				color = new ConfigColor();
				color.setVision("1");
			}else{
				color.setVision((Integer.parseInt(color.getVision())+1)+"");
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("vision", color.getVision());
			StringWriter out = new StringWriter();
				jsonObject.writeJSONString(out);
				ObjectSerializableUtil.write(filePath, out.toString());
				out.close();
				
		}
		return "修改成功!";	
	}
	/**
	 * 修改配置
	 * @param strs
	 * @param netType
	 * @param kpi
	 * @throws Exception
	 */
	private void update(String[] strs,short netType,short kpi, short scene_type) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(strs != null && strs.length > 0){
			int sort = 0;
			if (isDesc(kpi)) {
				sort = 1;
			}
			if(compareStr(strs,sort)){
				if(isFloat(strs)){
					for (int i = 0; i < strs.length; i++) {
						try {
							map.put("nettype", netType);
							map.put("kpi", kpi);
							map.put("serialnum", i+1);
							map.put("scene_type", scene_type);
							map.put("kpivalue", new BigDecimal(strs[i].trim()));
							this.kpiIntervalValueDao.update(map);
						} catch (Exception e) {
							throw new Exception("系统异常！");
						}
					}
				}else{
					throw new Exception("请输入浮点数！");
				}
			}else{
				List<KpiIntervalValue> kpis = this.getAllKipIntervalValues();
				String msg ="";
				if(netType == 1){
					if(scene_type == 0){
						msg += "室外的2G数据中";
					}else{
						msg += "室内的2G数据中";
					}
				}else if(netType == 2){
					if(scene_type == 0){
						msg += "室外的3G数据中";
					}else{
						msg += "室内的3G数据中";
					}
				}else{
					if(scene_type == 0){
						msg += "室外的4G数据中";
					}else{
						msg += "室内的4G数据中";
					}
				}
				msg += getNameByKpi(kpis).get(kpi);
				if(sort == 1){
					msg += "请按从大到小的顺序填写！";
				}else{
					msg += "请按从小到大的顺序填写！";
				}
				throw new Exception(msg);
			}
		}else{
			throw new Exception("数据不能为空！");
		}
	}
	/**
	 * 是否是浮点数
	 * @param strs
	 * @return
	 */
	private boolean isFloat(String[] strs){
		for (int i = 0; i < strs.length; i++) {
			if(!PatternUtil.isFloat(strs[i].trim())){
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断是否按照顺序排序
	 * @param strs
	 * @param sort 0--升序  1--降序
	 * @return
	 */
	private boolean compareStr(String[] strs,int sort){
		if(strs.length <= 1){
			return true;
		}else{
			for (int i = 0; i < strs.length; i++) {
				if(i<strs.length-1){
					BigDecimal temp1 =new BigDecimal(strs[i].trim());
					BigDecimal temp2 =new BigDecimal(strs[i+1].trim());
					if(sort == 1){
//						temp2>=temp1
						if( temp2.compareTo(temp1) > 0 ){
							return false;
						}
					}else{
//						temp1>=temp2
						if( temp1.compareTo(temp2) > 0 ){
							return false;
						}
					}
				}
			}
			return true;
		}
		
	}
	/**
	 * 是否属于倒序
	 * @param kpi
	 * @return
	 */
	private boolean isDesc(short kpi){
		for (int j = 0; j < kpifordesc.length; j++) {
			if(kpi == (short)kpifordesc[j]){
				 return true;
			}
		}
		return false;
	}
	/**
	 * 初始化kpimap
	 * @param kpis
	 */
	private Map<String ,Object> initKpiMap(List<KpiIntervalValue> kpis){
		Map<String ,Object> map = new HashMap<String ,Object>();
//		原kpimap分为kpiInsideMap和kpiOutsideMap
		Map<Integer, List<KpiIntervalValue>> kpiInsideMap = new HashMap<Integer, List<KpiIntervalValue>>();
		Map<Integer, List<KpiIntervalValue>> kpiOutsideMap = new HashMap<Integer, List<KpiIntervalValue>>();
//		分为2G室内、外，3G室内、外
		List<KpiIntervalValue> interval2GInsideValues = null;
		List<KpiIntervalValue> interval2GOutsideValues = null;
		List<KpiIntervalValue> interval3GInsideValues = null;
		List<KpiIntervalValue> interval3GOutsideValues = null;
		List<KpiIntervalValue> interval4GInsideValues = null;
		List<KpiIntervalValue> interval4GOutsideValues = null;
		if(!CollectionUtils.isEmpty(kpis)){
			interval2GInsideValues = new ArrayList<KpiIntervalValue>();
			interval2GOutsideValues = new ArrayList<KpiIntervalValue>();
			interval3GInsideValues = new ArrayList<KpiIntervalValue>();
			interval3GOutsideValues = new ArrayList<KpiIntervalValue>();
			interval4GInsideValues = new ArrayList<KpiIntervalValue>();
			interval4GOutsideValues = new ArrayList<KpiIntervalValue>();
			for (KpiIntervalValue kpiIntervalValue : kpis) {
				//netType 1:2g 2:3g 3:4g
				if(kpiIntervalValue.getNetType().intValue() == 1){
//					0外，1内
					if(kpiIntervalValue.getScene_type().intValue() == 0){
						interval2GOutsideValues.add(kpiIntervalValue);
					}else if(kpiIntervalValue.getScene_type().intValue() == 1){
						interval2GInsideValues.add(kpiIntervalValue);
					}else{
//						暂无
					}
				}else if(kpiIntervalValue.getNetType().intValue() == 2){
//					0外，1内
					if(kpiIntervalValue.getScene_type().intValue() == 0){
						interval3GOutsideValues.add(kpiIntervalValue);
					}else if(kpiIntervalValue.getScene_type().intValue() == 1){
						interval3GInsideValues.add(kpiIntervalValue);
					}else{
//						暂无
					}
				}else if(kpiIntervalValue.getNetType().intValue() == 3){
					if(kpiIntervalValue.getScene_type().intValue() == 0){
						interval4GOutsideValues.add(kpiIntervalValue);
					}else if(kpiIntervalValue.getScene_type().intValue() == 1){
						interval4GInsideValues.add(kpiIntervalValue);
					}else{
//						暂无
					}
				}else{
					//暂无
				}
			}
		}
		kpiOutsideMap.put(1, interval2GOutsideValues);
		kpiOutsideMap.put(2, interval3GOutsideValues);
		kpiOutsideMap.put(3, interval4GOutsideValues);
		kpiInsideMap.put(1, interval2GInsideValues);
		kpiInsideMap.put(2, interval3GInsideValues);
		kpiInsideMap.put(3, interval4GInsideValues);
		map.put("kpiOutsideMap", kpiOutsideMap);
		map.put("kpiInsideMap", kpiInsideMap);
		return map;
	}
	/**
	 * 根据kpi获取name
	 * @param kpis
	 * @return
	 */
	private Map<Short,String> getNameByKpi(List<KpiIntervalValue> kpis){
		Map<Short, String> map = new HashMap<Short, String>();
		for (KpiIntervalValue kpiIntervalValue : kpis) {
			if(!map.containsKey(kpiIntervalValue.getKpi())){
				map.put(kpiIntervalValue.getKpi(), kpiIntervalValue.getKpiName());
			}
		}
		return map;
	}
	/**
	 * 通过kpiMap查询对应名称的kpiBean集合
	 * @param netType
	 * @param kpiMap
	 * @return
	 */
	private KpiBean getKpiBeanByKpiMap(int netType, short kpi,Map<Integer, List<KpiIntervalValue>> kpiMap){
		List<KpiIntervalValue> kpis = kpiMap.get(netType);
		if (!CollectionUtils.isEmpty(kpis)) {
			KpiBean kpiBean = new KpiBean();
			Short kpiTemp = null;
			for (KpiIntervalValue kpiIntervalValue : kpis) {
				kpiTemp = kpiIntervalValue.getKpi();
				if(kpiTemp != null && kpiTemp.equals(kpi)){
					kpiBean.setKpi(kpiTemp);
					kpiBean.setType(kpiIntervalValue.getNetType());
					kpiBean.setScene_type(kpiIntervalValue.getScene_type());
					kpiBean.getKpiValues().add(kpiIntervalValue);
				}
			}
			return kpiBean;
		}
		return null;
	}
	/**
	 * 初始化kpiInside
	 * @return
	 */
	private Kpi initKpiInside(Map<Integer, List<KpiIntervalValue>> kpiInsideMap){
		Kpi kpi = new Kpi();
		for (int i = 0; i < kpifor2g.length; i++) {
			KpiBean temp = getKpiBeanByKpiMap(1, (short)kpifor2g[i], kpiInsideMap);
			switch (kpifor2g[i]) {
			case 22:
				kpi.setTxPowerfor2g(temp);
				break;
			case 6:
				kpi.setRxlev(temp);
				break;
			case 7:
				kpi.setRxQual(temp);
				break;
			case 8:
				kpi.setCi(temp);
				break;
			case 9:
				kpi.setMos(temp);
				break;
			default:
				break;
			}
		}
		for (int i = 0; i < kpifor3g.length; i++) {
			KpiBean temp = getKpiBeanByKpiMap(2, (short)kpifor3g[i], kpiInsideMap);
			switch (kpifor3g[i]) {
			case 1:
				kpi.setRscp(temp);
				break;
			case 2:
				kpi.setEcno(temp);
				break;
			case 3:
				kpi.setTxPowerfor3g(temp);
				break;
			case 4:
				kpi.setFtpUp(temp);
				break;
			case 5:
				kpi.setFtpDown(temp);
				break;
			case 10:
				kpi.setPingLoss(temp);
				break;
			case 11:
				kpi.setPingMaxDelay(temp);
				break;
			case 12:
				kpi.setPingMinDelay(temp);
				break;
			case 13:
				kpi.setPingAvgDelay(temp);
				break;
			case 14:
				kpi.setHttpMaxResponseTime(temp);
				break;
			case 15:
				kpi.setHttpMinResponseTime(temp);
				break;
			case 16:
				kpi.setHttpAvgResponseTime(temp);
				break;
			case 17:
				kpi.setHttpMaxDownloadSpeed(temp);
				break;
			case 18:
				kpi.setHttpMinDownloadSpeed(temp);
				break;
			case 19:
				kpi.setHttpAvgDownloadSpeed(temp);
				break;
			default:
				break;
			}
		}
		for (int i = 0; i < kpifor4g.length; i++) {
			KpiBean temp = getKpiBeanByKpiMap(3, (short)kpifor4g[i], kpiInsideMap);
			switch (kpifor4g[i]) {
			case 23:
				kpi.setRsrp(temp);
				break;
			case 24:
				kpi.setRsrq(temp);
				break;
			case 25:
				kpi.setSinr(temp);
				break;
			case 4:
				kpi.setFtpUp4G(temp);
				break;
			case 5:
				kpi.setFtpDown4G(temp);
				break;
			case 10:
				kpi.setPingLoss4G(temp);
				break;
			case 11:
				kpi.setPingMaxDelay4G(temp);
				break;
			case 12:
				kpi.setPingMinDelay4G(temp);
				break;
			case 13:
				kpi.setPingAvgDelay4G(temp);
				break;
			case 14:
				kpi.setHttpMaxResponseTime4G(temp);
				break;
			case 15:
				kpi.setHttpMinResponseTime4G(temp);
				break;
			case 16:
				kpi.setHttpAvgResponseTime4G(temp);
				break;
			case 17:
				kpi.setHttpMaxDownloadSpeed4G(temp);
				break;
			case 18:
				kpi.setHttpMinDownloadSpeed4G(temp);
				break;
			case 19:
				kpi.setHttpAvgDownloadSpeed4G(temp);
				break;
			default:
				break;
			}
		}
		return kpi;
	}
	/**
	 * 初始化kpioutside
	 * @return
	 */
	private Kpi initKpiOutside(Map<Integer, List<KpiIntervalValue>> kpiOutsideMap){
		Kpi kpi = new Kpi();
		for (int i = 0; i < kpifor2g.length; i++) {
			KpiBean temp = getKpiBeanByKpiMap(1, (short)kpifor2g[i], kpiOutsideMap);
			switch (kpifor2g[i]) {
			case 22:
				kpi.setTxPowerfor2g(temp);
				break;
			case 6:
				kpi.setRxlev(temp);
				break;
			case 7:
				kpi.setRxQual(temp);
				break;
			case 8:
				kpi.setCi(temp);
				break;
			case 9:
				kpi.setMos(temp);
				break;
			default:
				break;
			}
		}
		for (int i = 0; i < kpifor3g.length; i++) {
			KpiBean temp = getKpiBeanByKpiMap(2, (short)kpifor3g[i], kpiOutsideMap);
			switch (kpifor3g[i]) {
			case 1:
				kpi.setRscp(temp);
				break;
			case 2:
				kpi.setEcno(temp);
				break;
			case 3:
				kpi.setTxPowerfor3g(temp);
				break;
			case 4:
				kpi.setFtpUp(temp);
				break;
			case 5:
				kpi.setFtpDown(temp);
				break;
			case 10:
				kpi.setPingLoss(temp);
				break;
			case 11:
				kpi.setPingMaxDelay(temp);
				break;
			case 12:
				kpi.setPingMinDelay(temp);
				break;
			case 13:
				kpi.setPingAvgDelay(temp);
				break;
			case 14:
				kpi.setHttpMaxResponseTime(temp);
				break;
			case 15:
				kpi.setHttpMinResponseTime(temp);
				break;
			case 16:
				kpi.setHttpAvgResponseTime(temp);
				break;
			case 17:
				kpi.setHttpMaxDownloadSpeed(temp);
				break;
			case 18:
				kpi.setHttpMinDownloadSpeed(temp);
				break;
			case 19:
				kpi.setHttpAvgDownloadSpeed(temp);
				break;
			default:
				break;
			}
		}
		for (int i = 0; i < kpifor4g.length; i++) {
			KpiBean temp = getKpiBeanByKpiMap(3, (short)kpifor4g[i], kpiOutsideMap);
			switch (kpifor4g[i]) {
			case 23:
				kpi.setRsrp(temp);
				break;
			case 24:
				kpi.setRsrq(temp);
				break;
			case 25:
				kpi.setSinr(temp);
				break;
			case 4:
				kpi.setFtpUp4G(temp);
				break;
			case 5:
				kpi.setFtpDown4G(temp);
				break;
			case 10:
				kpi.setPingLoss4G(temp);
				break;
			case 11:
				kpi.setPingMaxDelay4G(temp);
				break;
			case 12:
				kpi.setPingMinDelay4G(temp);
				break;
			case 13:
				kpi.setPingAvgDelay4G(temp);
				break;
			case 14:
				kpi.setHttpMaxResponseTime4G(temp);
				break;
			case 15:
				kpi.setHttpMinResponseTime4G(temp);
				break;
			case 16:
				kpi.setHttpAvgResponseTime4G(temp);
				break;
			case 17:
				kpi.setHttpMaxDownloadSpeed4G(temp);
				break;
			case 18:
				kpi.setHttpMinDownloadSpeed4G(temp);
				break;
			case 19:
				kpi.setHttpAvgDownloadSpeed4G(temp);
				break;
			default:
				break;
			}
		}
		return kpi;
	}
//	排序成升序排列
//	private List<KpiIntervalValue> getOrder(List<KpiIntervalValue> kpiValues){
//		BigDecimal t;
//		for(int i = 0;i<kpiValues.size()-1;i++){
//			for(int j=0;j<kpiValues.size()-i-1;j++){
//				if(kpiValues.get(j+1).getKpiValue().compareTo(kpiValues.get(j).getKpiValue())!=1){
//					t=kpiValues.get(j+1).getKpiValue();
//					kpiValues.get(j+1).setKpiValue(kpiValues.get(j).getKpiValue());
//					kpiValues.get(j).setKpiValue(t);
//				}
//			}
//		}
//		return kpiValues;
//	}
}
