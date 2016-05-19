package com.complaint.service;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.complaint.dao.ColourCodeDao;
import com.complaint.dao.KpiIntervalValueDao;
import com.complaint.io.ObjectSerializableUtil;
import com.complaint.model.ColourCode;
import com.complaint.model.ConfigColor;
import com.complaint.model.KpiIntervalValue;
import com.complaint.model.Kpico;
import com.complaint.model.Vision;

@Service("kPIColorService")
public class KPIColorService {
	@Autowired
	private ColourCodeDao colourCodeDao;
	@Autowired
	private KpiIntervalValueDao kpiIntervalValueDao;
	
	private static final Logger logger = LoggerFactory.getLogger(KPIColorService.class);

	
	public String queryKpicolor(JSONObject json){
		String op = json.get("op") == null ? "" : json.get("op").toString();
		String vi = json.get("vi") == null ? "" : json.get("vi").toString();
		if("0".equals(op)){
			//检查是否需要更新
			try {
				JSONObject jsonObject = new JSONObject();
				StringWriter out = new StringWriter();
				ConfigColor cc = (ConfigColor)ObjectSerializableUtil.readObject(this.getClass().getClassLoader().getResource("").getPath()+"/colorvision.txt");
				if(cc == null){
					cc = new ConfigColor();
					cc.setVision("1");
				}
				if(vi != null && !"".equals(vi)) {
					if("-1".equals(vi)){
						jsonObject.put("op", "1");
						jsonObject.put("vi", cc.getVision());
					}else{
						if(vi.equals(cc.getVision())) {
							jsonObject.put("op", "0");
							jsonObject.put("vi", vi);
						}else{
							jsonObject.put("op", "1");
							jsonObject.put("vi", cc.getVision());
						}
					}
				}
				jsonObject.writeJSONString(out);
				String result = out.toString();
				out.close();
				return result;
			} catch (IOException e) {
				logger.error("",e);
			}
		}else if("1".equals(op)){
			return queryKpicolor();
		}
		return "";
	}
	
	
	public String queryKpicolor(){
		String rtnMsg="";
		StringWriter out = new StringWriter();
		Kpico kc=this.queryKpico();
		try {
			JSONValue.writeJSONString(kc, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
	    rtnMsg = out.toString();
		return rtnMsg;
	}
	
	public Kpico queryKpico(){
		Kpico kc=new Kpico();
		List<ColourCode> cos=this.colourCodeDao.queryColourCodes();
		List<KpiIntervalValue> kps=this.kpiIntervalValueDao.queryAllKpi();
		String color="";
		//颜色处理
		for (int i=0;i<cos.size();i++){
		 String cc=cos.get(i).getColourcode();
		 String r="",g="",b="";
		 String rgb="";
		 int nr=0,ng=0,nb=0;
		 if(cc.indexOf("#")>=0){
				cc=cc.substring(1);
		  }
		 if(cc.length()==6){
			 r=cc.substring(0, 2);
			 g=cc.substring(2, 4);
			 b=cc.substring(4);
			 nr=Integer.parseInt(r, 16);
			 ng=Integer.parseInt(g, 16);
			 nb=Integer.parseInt(b, 16);
			 rgb=nr+","+ng+","+nb;
		 }
		 color+=rgb;
		 if(i<cos.size()-1){
			 color+="|";
		 }
		 
		}
		kc.setColor(color);
		String rscp="",ecno="",tp3="",fu="",fd="";
		String rscp_2="",ecno_2="",tp3_2="",fu_2="",fd_2="";
		String tp2="",ci="",mos="",rl="",rq="";
		String tp2_2="",ci_2="",mos_2="",rl_2="",rq_2="";
		String rsrp="",rsrq="",snr="",lfu="",lfd="";
		String rsrp_2="",rsrq_2="",snr_2="",lfu_2="",lfd_2="";
		//阈值
		for(int j=0;j<kps.size();j++){
			KpiIntervalValue kpi=kps.get(j);
			switch (kpi.getKpi()) {
			case 1:
				if(kpi.getScene_type() == 1){
					rscp+=kpi.getKpiValue();
					rscp+=",";
				}else{
					rscp_2+=kpi.getKpiValue();
					rscp_2+=",";
				}
				break;
			case 2:
				if(kpi.getScene_type() == 1){
					ecno+=kpi.getKpiValue();
					ecno+=",";
				}else{
					ecno_2+=kpi.getKpiValue();
					ecno_2+=",";
				}
				break;
			case 3:
				if(kpi.getScene_type() == 1){
					tp3+=kpi.getKpiValue();
					tp3+=",";
				}else{
					tp3_2+=kpi.getKpiValue();
					tp3_2+=",";
				}
				break;
			case 4:
				if(kpi.getScene_type() == 1){
					if(kpi.getNetType() == 2){
						fu+=kpi.getKpiValue();
						fu+=",";
					}else if(kpi.getNetType() == 3){
						lfu+=kpi.getKpiValue();
						lfu+=",";
					}
				}else{
					if(kpi.getNetType() == 2){
						fu_2+=kpi.getKpiValue();
						fu_2+=",";	
					}else if(kpi.getNetType() == 3){
						lfu_2+=kpi.getKpiValue();
						lfu_2+=",";	
					}
				}
				break;
			case 5:
				if(kpi.getScene_type() == 1){
					if(kpi.getNetType() == 2){
						fd+=kpi.getKpiValue();
						fd+=",";
					}else if(kpi.getNetType() == 3){
						lfd+=kpi.getKpiValue();
						lfd+=",";
					}
				}else{
					if(kpi.getNetType() == 2){
						fd_2+=kpi.getKpiValue();
						fd_2+=",";
					}else if(kpi.getNetType() == 3){
						lfd_2+=kpi.getKpiValue();
						lfd_2+=",";
					}
				}
				break;
			case 6:
				if(kpi.getScene_type() == 1){
					rl+=kpi.getKpiValue();
					rl+=",";
				}else{
					rl_2+=kpi.getKpiValue();
					rl_2+=",";
				}
				break;
			case 7:
				if(kpi.getScene_type() == 1){
					rq+=kpi.getKpiValue();
					rq+=",";
				}else{
					rq_2+=kpi.getKpiValue();
					rq_2+=",";
				}
				break;
			case 8:
				if(kpi.getScene_type() == 1){
					ci+=kpi.getKpiValue();
					ci+=",";
				}else{
					ci_2+=kpi.getKpiValue();
					ci_2+=",";
				}
				
				break;
			case 9:
				if(kpi.getScene_type() == 1){
					mos+=kpi.getKpiValue();
					mos+=",";
				}else{
					mos_2+=kpi.getKpiValue();
					mos_2+=",";
				}
				break;
			case 22:
				if(kpi.getScene_type() == 1){
					tp2+=kpi.getKpiValue();
					tp2+=",";
				}else{
					tp2_2+=kpi.getKpiValue();
					tp2_2+=",";
				}
				break;
			case 23:
				if(kpi.getScene_type() == 1){
					rsrp+=kpi.getKpiValue();
					rsrp+=",";
				}else{
					rsrp_2+=kpi.getKpiValue();
					rsrp_2+=",";
				}
				break;
			case 24:
				if(kpi.getScene_type() == 1){
					rsrq+=kpi.getKpiValue();
					rsrq+=",";
				}else{
					rsrq_2+=kpi.getKpiValue();
					rsrq_2+=",";
				}
				break;
			case 25:
				if(kpi.getScene_type() == 1){
					snr+=kpi.getKpiValue();
					snr+=",";
				}else{
					snr_2+=kpi.getKpiValue();
					snr_2+=",";
				}
				break;
			default:
				break;
			}
		}
		//室内
		kc.setRscp(rscp.length()>0?rscp.substring(0, rscp.length()-1):null);
		kc.setEcno(ecno.length()>0?ecno.substring(0, ecno.length()-1):null);
		kc.setTp3(tp3.length()>0?tp3.substring(0, tp3.length()-1):null);
		kc.setFu(fu.length()>0?fu.substring(0, fu.length()-1):null);
		kc.setFd(fd.length()>0?fd.substring(0, fd.length()-1):null);
		kc.setRxlev(rl.length()>0?rl.substring(0, rl.length()-1):null);
		kc.setRxqual(rq.length()>0?rq.substring(0, rq.length()-1):null);
		kc.setCi(ci.length()>0?ci.substring(0, ci.length()-1):null);
		kc.setMos(mos.length()>0?mos.substring(0, mos.length()-1):null);
		kc.setTp2(tp2.length()>0?tp2.substring(0, tp2.length()-1):null);
		
		kc.setRsrp(rsrp.length()>0?rsrp.substring(0, rsrp.length()-1):null);
		kc.setRsrq(rsrq.length()>0?rsrq.substring(0, rsrq.length()-1):null);
		kc.setSnr(snr.length()>0?snr.substring(0, snr.length()-1):null);
		kc.setLtefu(lfu.length()>0?lfu.substring(0, lfu.length()-1):null);
		kc.setLtefd(lfd.length()>0?lfd.substring(0, lfd.length()-1):null);
		//室外
		kc.setRscp_2(rscp_2.length()>0?rscp_2.substring(0, rscp_2.length()-1):null);
		kc.setEcno_2(ecno_2.length()>0?ecno_2.substring(0, ecno_2.length()-1):null);
		kc.setTp3_2(tp3_2.length()>0?tp3_2.substring(0, tp3_2.length()-1):null);
		kc.setFu_2(fu_2.length()>0?fu_2.substring(0, fu_2.length()-1):null);
		kc.setFd_2(fd_2.length()>0?fd_2.substring(0, fd_2.length()-1):null);
		kc.setRxlev_2(rl_2.length()>0?rl_2.substring(0, rl_2.length()-1):null);
		kc.setRxqual_2(rq_2.length()>0?rq_2.substring(0, rq_2.length()-1):null);
		kc.setCi_2(ci_2.length()>0?ci_2.substring(0, ci_2.length()-1):null);
		kc.setMos_2(mos_2.length()>0?mos_2.substring(0, mos_2.length()-1):null);
		kc.setTp2_2(tp2_2.length()>0?tp2_2.substring(0, tp2_2.length()-1):null);
		
		kc.setRsrp_2(rsrp_2.length()>0?rsrp_2.substring(0, rsrp_2.length()-1):null);
		kc.setRsrq_2(rsrq_2.length()>0?rsrq_2.substring(0, rsrq_2.length()-1):null);
		kc.setSnr_2(snr_2.length()>0?snr_2.substring(0, snr_2.length()-1):null);
		kc.setLtefu_2(lfu_2.length()>0?lfu_2.substring(0, lfu_2.length()-1):null);
		kc.setLtefd_2(lfd_2.length()>0?lfd_2.substring(0, lfd_2.length()-1):null);
		
		return kc;
	}
	public String queryVision(String vision){
		String rtnMsg="";
		StringWriter out = new StringWriter();
		Vision vi=new Vision();
		String vv="",url="",declare="";
		try{
			SAXReader   reader = new SAXReader();
			Document document = reader.read(new File(this.getClass().getClassLoader().getResource("").getPath()+"/vision.xml"));
			//获取根节点
			Element root = document.getRootElement();
			Element emmt = root.element("vision");
			if(emmt.getText()!=null &&  emmt.getText().length()>0){
				vv = emmt.getText();
			}
			Element emmt1 = root.element("url");
			if(emmt1.getText()!=null &&  emmt1.getText().length()>0){
				url = emmt1.getText();
			}
			Element emmt2 = root.element("declare");
			if(emmt2.getText()!=null &&  emmt2.getText().length()>0){
				declare = emmt2.getText();
			}
		}catch(Exception e){
			logger.error("",e);
		}
		if(vision!=null&&!vision.equals("")){
			if(vision.equals(vv)){
				vi.setIsup((short) 0);
			}else{
				vi.setIsup((short)1);
				vi.setUrl(url);
				vi.setDeclare(declare);
				vi.setVision(vv);
			}
		}
		try {
			JSONValue.writeJSONString(vi, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
	    rtnMsg = out.toString();
		return rtnMsg;
	}

	/**
	 * 4G版本比对
	 * @param vision
	 * @return
	 */
	public String queryVisionLte(String vision){
		String rtnMsg="";
		StringWriter out = new StringWriter();
		Vision vi=new Vision();
		String vv="",url="",declare="";
		try{
			SAXReader   reader = new SAXReader();
			Document document = reader.read(new File(this.getClass().getClassLoader().getResource("").getPath()+"/vision.xml"));
			//获取根节点
			Element root = document.getRootElement();
			Element emmt = root.element("visionLte");
			if(emmt.getText()!=null &&  emmt.getText().length()>0){
				vv = emmt.getText();
			}
			Element emmt1 = root.element("urlLte");
			if(emmt1.getText()!=null &&  emmt1.getText().length()>0){
				url = emmt1.getText();
			}
			Element emmt2 = root.element("declareLte");
			if(emmt2.getText()!=null &&  emmt2.getText().length()>0){
				declare = emmt2.getText();
			}
		}catch(Exception e){
			logger.error("",e);
		}
		if(vision!=null&&!vision.equals("")){
			if(vision.equals(vv)){
				vi.setIsup((short) 0);
			}else{
				vi.setIsup((short)1);
				vi.setUrl(url);
				vi.setDeclare(declare);
				vi.setVision(vv);
			}
		}
		try {
			JSONValue.writeJSONString(vi, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
	    rtnMsg = out.toString();
		return rtnMsg;
	}
}
