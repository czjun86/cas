package com.complaint.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.RateIntervalValueDao;
import com.complaint.model.IntegrationThresholdForm;
import com.complaint.model.Rate;
import com.complaint.model.RateAndIntegrationForm;
import com.complaint.model.RateForm;
import com.complaint.model.RateIntervalValue;
import com.complaint.model.RateProperty;
import com.complaint.model.RateValue;
import com.complaint.model.Ratekpi;
import com.complaint.utils.ConstantUtil;
@Service("rateIntervalValueService")

public class RateIntervalValueService {
	@Autowired
	private RateIntervalValueDao rateIntervalValueDao;
	@Autowired
	private RateColorCodeService rateColorCodeService;
	@Autowired
	private IntegrationThresholdService integrationThresholdService;
	
	private static final Logger logger = LoggerFactory.getLogger(RateIntervalValueService.class);
	
/**
 * 获取初始化rate给类值
 * @return
 */
	public Map<Integer,RateProperty> getRate(){
		Map<String ,Object> rateMap = initRateMap();
		Map<Integer ,RateProperty> map = new HashMap<Integer ,RateProperty>();
		map = initRate(rateMap);
		return map;
	}
	
/**
 * 查出指标表和参数表所有内容，存入变量
 */
	public Map<String ,Object> initRateMap(){
		List<Ratekpi> ratekpis = rateIntervalValueDao.getAllRatekpi();
		List<RateValue> rateValues = rateIntervalValueDao.getAllRateValue();
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("ratekpis", ratekpis);
		map.put("rateValues", rateValues);
		return map;
	}
	
/**
 * 室内外分开	
 * @return
 */
	public Map<Integer ,RateProperty> initRate(Map<String ,Object> rateMap){
		Map<Integer ,RateProperty> map = new HashMap<Integer ,RateProperty>();
		RateProperty ratePropertyInside =new RateProperty();
		RateProperty ratePropertyOutside =new RateProperty();
		
		for(Ratekpi ratekpi:(List<Ratekpi>)rateMap.get("ratekpis")){
			RateIntervalValue rateIntervalValue = new RateIntervalValue();
//			将查出的数据拿室内书外分开
			if(ratekpi.getScene()==0){
				rateIntervalValue = initRateIntervalValue(ratekpi ,(List<RateValue>)rateMap.get("rateValues"));
//				根据属性名rscp等等对应的kpi—code对应放入
				switch(ratekpi.getKpi_code()){
				case 1:
					ratePropertyOutside.setRscp(rateIntervalValue);
					break;
				case 2:
					ratePropertyOutside.setEcno(rateIntervalValue);
					break;
				case 3:
					ratePropertyOutside.setTxpower(rateIntervalValue);
					break;
				case 4:
					if(ratekpi.getNet_type() == 1){
						ratePropertyOutside.setFtpup(rateIntervalValue);
					}else if(ratekpi.getNet_type() == 3){//4G数据
						ratePropertyOutside.setFtpup4G(rateIntervalValue);
					}
					break;
				case 5:
					if(ratekpi.getNet_type() == 1){
						ratePropertyOutside.setFtpdown(rateIntervalValue);
					}else if(ratekpi.getNet_type() == 3){//4G数据
						ratePropertyOutside.setFtpdown4G(rateIntervalValue);
					}
					break;
				case 6:
					ratePropertyOutside.setRxlev(rateIntervalValue);
					break;
				case 7:
					ratePropertyOutside.setRxqual(rateIntervalValue);
					break;
				case 8:
					ratePropertyOutside.setCi(rateIntervalValue);
				case 23:
					ratePropertyOutside.setRsrp(rateIntervalValue);
					break;
				case 24:
					ratePropertyOutside.setRsrq(rateIntervalValue);
					break;
				case 25:
					ratePropertyOutside.setSinr(rateIntervalValue);
					break;
				}
			}else if(ratekpi.getScene()==1){
				rateIntervalValue = new RateIntervalValue();
				rateIntervalValue = initRateIntervalValue(ratekpi , (List<RateValue>)rateMap.get("rateValues"));
				
				switch(ratekpi.getKpi_code()){
				case 1:
					ratePropertyInside.setRscp(rateIntervalValue);
					break;
				case 2:
					ratePropertyInside.setEcno(rateIntervalValue);
					break;
				case 3:
					ratePropertyInside.setTxpower(rateIntervalValue);
					break;
				case 4:
					if(ratekpi.getNet_type() == 1){
						ratePropertyInside.setFtpup(rateIntervalValue);
					}else if(ratekpi.getNet_type() == 3){//4G数据
						ratePropertyInside.setFtpup4G(rateIntervalValue);
					}
					break;
				case 5:
					if(ratekpi.getNet_type() == 1){
						ratePropertyInside.setFtpdown(rateIntervalValue);
					}else if(ratekpi.getNet_type() == 3){//4G数据
						ratePropertyInside.setFtpdown4G(rateIntervalValue);
					}
					break;
				case 6:
					ratePropertyInside.setRxlev(rateIntervalValue);
					break;
				case 7:
					ratePropertyInside.setRxqual(rateIntervalValue);
					break;
				case 8:
					ratePropertyInside.setCi(rateIntervalValue);
					break;
				case 23:
					ratePropertyInside.setRsrp(rateIntervalValue);
					break;
				case 24:
					ratePropertyInside.setRsrq(rateIntervalValue);
					break;
				case 25:
					ratePropertyInside.setSinr(rateIntervalValue);
					break;
				}
			}else{
//				暂无出室内外0，1意外情况
			}
		}
		map.put(0, ratePropertyOutside);
		map.put(1, ratePropertyInside);
		return map;
	}
	
/**
 * 室内外划分时放入关联数据
 */
	public RateIntervalValue initRateIntervalValue(Ratekpi ratekpi,List<RateValue> rateValues){
		RateIntervalValue rateIntervalValue = new RateIntervalValue();
		
		rateIntervalValue.setId(ratekpi.getId());
		rateIntervalValue.setKpi_code(ratekpi.getKpi_code());
		rateIntervalValue.setKpi_name(ratekpi.getKpi_name());
		rateIntervalValue.setNet_type(ratekpi.getNet_type());
		rateIntervalValue.setScene(ratekpi.getScene());
		List<RateValue> list = new ArrayList<RateValue>();
			for(RateValue ratevalue:rateValues){
				if(ratekpi.getId()==ratevalue.getId()){
					list.add(ratevalue);
				}
			}
		rateIntervalValue.setRateValues(list);
		return rateIntervalValue;
	}

	
	
/**
 * 页面值修改分组模型
 */
	@Transactional(rollbackFor=Exception.class)
	public String updateRateInterval(RateForm rateForm) throws Exception{
		//将每个属性的id,平均速度（没有的手动设置为空）,符号值,等级值,占比,属性名传入update（）方法
		update(rateForm.getId(),null,rateForm.getRxlev_arithmetic() ,rateForm.getRxlev_value() ,rateForm.getRxlev_ratio() ,"rxlev");
		update(rateForm.getId(),null,rateForm.getRxqual_arithmetic() ,rateForm.getRxqual_value() ,rateForm.getRxqual_ratio() ,"rxqual");
		update(rateForm.getId(),null,rateForm.getCi_arithmetic() ,rateForm.getCi_value() ,rateForm.getCi_ratio() ,"ci");
		update(rateForm.getId(),null,rateForm.getRscp_arithmetic() ,rateForm.getRscp_value() ,rateForm.getRscp_ratio() ,"rscp");
		update(rateForm.getId(),null,rateForm.getEcno_arithmetic() ,rateForm.getEcno_value() ,rateForm.getEcno_ratio() ,"ecno");
		update(rateForm.getId(),null,rateForm.getTxpower_arithmetic() ,rateForm.getTxpower_value() ,rateForm.getTxpower_ratio() ,"txpower");
		update(rateForm.getId(),rateForm.getFtpup_avg() ,rateForm.getFtpup_arithmetic() ,rateForm.getFtpup_value() ,rateForm.getFtpup_ratio() ,"ftpup");
		update(rateForm.getId(),rateForm.getFtpdown_avg() ,rateForm.getFtpdown_arithmetic() ,rateForm.getFtpdown_value() ,rateForm.getFtpdown_ratio() ,"ftpdown");
		update(rateForm.getId(),null,rateForm.getRsrp_arithmetic() ,rateForm.getRsrp_value() ,rateForm.getRsrp_ratio() ,"rsrp");
		update(rateForm.getId(),null,rateForm.getRsrq_arithmetic() ,rateForm.getRsrq_value() ,rateForm.getRsrq_ratio() ,"rsrq");
		update(rateForm.getId(),null,rateForm.getSinr_arithmetic() ,rateForm.getSinr_value() ,rateForm.getSinr_ratio() ,"sinr");
		update(rateForm.getId(),rateForm.getFtpup4G_avg() ,rateForm.getFtpup4G_arithmetic() ,rateForm.getFtpup4G_value() ,rateForm.getFtpup4G_ratio() ,"ftpup4G");
		update(rateForm.getId(),rateForm.getFtpdown4G_avg() ,rateForm.getFtpdown4G_arithmetic() ,rateForm.getFtpdown4G_value() ,rateForm.getFtpdown4G_ratio() ,"ftpdown4G");
		//return "评价阈值数据保存成功";
		return "a";
		//return "评价阈值数据保存失败";
		//return "b";
	}
	
/**
 * 数值进行修改，将传入属性按前3室外，后3室内分开，再按优良中差分开，封成RateValue的形式，进入数据库修改
 */
	@Transactional(rollbackFor=Exception.class)
	public void update(String[] idstr,String[] avg ,String[] arithmetic ,String[] value ,String[] ratio ,String type)throws Exception{
		RateValue rateValue = null;
		for(int i =0;i<6;i++){
			//封成RateValue准备读入数据库
			rateValue = new RateValue();
				if(avg!=null){
					rateValue.setRank_avg(avg[i]);
				}else{
					rateValue.setRank_avg("");
				}
				//按优良中差分割
				switch(i%3){
					case 0:
						rateValue.setRank_code(1);
						break;
					case 1:
						rateValue.setRank_code(2);
						break;
						//原本为优良中差对应1234，现在为优良差对应124
					//case 2:
						//rateValue.setRank_code(3);
						//break;
					case 2:
						rateValue.setRank_code(4);
						break;
				}
			rateValue.setRank_arithmetic(Integer.parseInt(arithmetic[i]));
			rateValue.setRank_value(Integer.parseInt(value[i]));
			rateValue.setRank_ratio(ratio[i]);
			//按数组前3室外，后3室内，进行室内外分割
			//后3为室内
			if(i>=3&&i<6){
				for(String str:idstr){
					String tempstr = str.substring(str.indexOf("_")+1, str.length());
					if(tempstr.equals("1_"+type)){
						int id = Integer.parseInt(str.substring(0,str.indexOf("_")));
						rateValue.setId(id);
						rateIntervalValueDao.update(rateValue);
						break;
					}
				}
				//rateValue.setId(i);
			}
			//前3为室外
			else if(i<3){
				for(String str:idstr){
					String tempstr = str.substring(str.indexOf("_")+1, str.length());
					if(tempstr.equals("0_"+type)){
						int id = Integer.parseInt(str.substring(0,str.indexOf("_")));
						rateValue.setId(id);
						rateIntervalValueDao.update(rateValue);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 保存全部表单内容
	 */
	@Transactional(rollbackFor=Exception.class)
	public int saveAll(RateAndIntegrationForm rateAndIntegrationForm,HttpServletRequest request)throws Exception{
        //保存颜色，成功返回1出错返回0
		int msg1=0;
			msg1 = this.rateColorCodeService.saveColor(rateAndIntegrationForm.getColors());
		if(msg1==1){
			String colorChangeFlag = request.getParameter("colorChangein")==null?"0":request.getParameter("colorChangein");
			if("1".equals(colorChangeFlag))	{
				int version = 1;
				ServletContext app = request.getSession().getServletContext();
				String vi = (String)app.getAttribute(ConstantUtil.SYS_RATE_COLOR_VERSION);
				if(StringUtils.isNotEmpty(vi)){
					version = Integer.valueOf(vi);
					if(version >= Integer.MAX_VALUE){
						version = 1;
					}else{
						version++;
					}					
				}
				app.setAttribute(ConstantUtil.SYS_RATE_COLOR_VERSION, String.valueOf(version));
			}		
            //获取生成图片保存路径
			String completionPath = request.getSession().getServletContext().getRealPath("");
			rateColorCodeService.createImages(completionPath,rateAndIntegrationForm.getColors());
		}
		
        //保存指标阈值，成功返回1出错返回0
		RateForm rateForm = this.getRateForm(rateAndIntegrationForm);
		String str1 = this.updateRateInterval(rateForm);
		int msg2 =0;
		if(str1.equals("a")){
			msg2 =1;
		}else{
			msg2 = 0;
		}
		
		
//		保存综合阈值，成功返回1出错返回0
		IntegrationThresholdForm integrationThresholdForm = getIntegrationThresholdForm(rateAndIntegrationForm);
		String str2 = this.integrationThresholdService.updateIntegrationValue(integrationThresholdForm);
		int msg3 =0;
		if(str2.equals("a")){
			msg3 =1;
		}else{
			msg3 = 0;
		}
		
//		三个保存那个出错就不会返回0
		if(msg1!=1){
			return 1;
		}
		if(msg2!=1){
			return 2;
		}
		if(msg3!=1){
			return 3;
		}
		return 0;
	}
	/**
	 * 从RateAndIntegrationFrom中获取RateForm
	 */
	public RateForm getRateForm(RateAndIntegrationForm raif){
		RateForm rateForm = new RateForm();
		rateForm.setId(raif.getId());
//		rxlev
		rateForm.setRxlev_arithmetic(raif.getRxlev_arithmetic());
		rateForm.setRxlev_value(raif.getRxlev_value());
		rateForm.setRxlev_ratio(raif.getRxlev_ratio());
//		RxQua
		rateForm.setRxqual_arithmetic(raif.getRxqual_arithmetic());
		rateForm.setRxqual_value(raif.getRxqual_value());
		rateForm.setRxqual_ratio(raif.getRxqual_ratio());
//		C/I
		rateForm.setCi_arithmetic(raif.getCi_arithmetic());
		rateForm.setCi_value(raif.getCi_value());
		rateForm.setCi_ratio(raif.getCi_ratio());
//		RSCP
		rateForm.setRscp_arithmetic(raif.getRscp_arithmetic());
		rateForm.setRscp_value(raif.getRscp_value());
		rateForm.setRscp_ratio(raif.getRscp_ratio());
//		ecno
		rateForm.setEcno_arithmetic(raif.getEcno_arithmetic());
		rateForm.setEcno_value(raif.getEcno_value());
		rateForm.setEcno_ratio(raif.getEcno_ratio());
//		TxPower
		rateForm.setTxpower_arithmetic(raif.getTxpower_arithmetic());
		rateForm.setTxpower_value(raif.getTxpower_value());
		rateForm.setTxpower_ratio(raif.getTxpower_ratio());
//		ftpup
		rateForm.setFtpup_avg(raif.getFtpup_avg());
		rateForm.setFtpup_arithmetic(raif.getFtpup_arithmetic());
		rateForm.setFtpup_value(raif.getFtpup_value());
		rateForm.setFtpup_ratio(raif.getFtpup_ratio());
//		ftpdown
		rateForm.setFtpdown_avg(raif.getFtpdown_avg());
		rateForm.setFtpdown_arithmetic(raif.getFtpdown_arithmetic());
		rateForm.setFtpdown_value(raif.getFtpdown_value());
		rateForm.setFtpdown_ratio(raif.getFtpdown_ratio());
//		RSRP
		rateForm.setRsrp_arithmetic(raif.getRsrp_arithmetic());
		rateForm.setRsrp_value(raif.getRsrp_value());
		rateForm.setRsrp_ratio(raif.getRsrp_ratio());
//		RSRQ
		rateForm.setRsrq_arithmetic(raif.getRsrq_arithmetic());
		rateForm.setRsrq_value(raif.getRsrq_value());
		rateForm.setRsrq_ratio(raif.getRsrq_ratio());
//		SINR
		rateForm.setSinr_arithmetic(raif.getSinr_arithmetic());
		rateForm.setSinr_value(raif.getSinr_value());
		rateForm.setSinr_ratio(raif.getSinr_ratio());
//		ftpup4G
		rateForm.setFtpup4G_avg(raif.getFtpup4G_avg());
		rateForm.setFtpup4G_arithmetic(raif.getFtpup4G_arithmetic());
		rateForm.setFtpup4G_value(raif.getFtpup4G_value());
		rateForm.setFtpup4G_ratio(raif.getFtpup4G_ratio());
//		ftpdown4G
		rateForm.setFtpdown4G_avg(raif.getFtpdown4G_avg());
		rateForm.setFtpdown4G_arithmetic(raif.getFtpdown4G_arithmetic());
		rateForm.setFtpdown4G_value(raif.getFtpdown4G_value());
		rateForm.setFtpdown4G_ratio(raif.getFtpdown4G_ratio());
		
		return rateForm;
	}
	/**
	 * 从RateAndIntegrationFrom中获取IntegrationThresholdForm
	 */
	public IntegrationThresholdForm getIntegrationThresholdForm(RateAndIntegrationForm raif){
		IntegrationThresholdForm itf = new IntegrationThresholdForm();
		itf.setRank_score(raif.getRank_score());
//		按+ , , -分为ABC
		itf.setEvaluate_scoreA(raif.getEvaluate_scoreA());
		itf.setEvaluate_scoreB(raif.getEvaluate_scoreB());
		itf.setEvaluate_scoreC(raif.getEvaluate_scoreC());
		itf.setRevis_left(raif.getRevis_left());
		itf.setRevis_right(raif.getRevis_right());
		itf.setRevis_level(raif.getRevis_level());
		
		return itf;
	}
}
