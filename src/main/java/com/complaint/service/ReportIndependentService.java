package com.complaint.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.zhugefubin.maptool.ConverterTool;
import cn.zhugefubin.maptool.Point;

import com.complaint.dao.GradMapper;
import com.complaint.dao.ReportIndependentDao;
import com.complaint.dao.SysConfigDao;
import com.complaint.model.BaseStation;
import com.complaint.model.ColourCode;
import com.complaint.model.EvalSet;
import com.complaint.model.GradeBean;
import com.complaint.model.KpiFlshData;
import com.complaint.model.KpiIntervalValue;
import com.complaint.model.MaxMinIntvel;
import com.complaint.model.Percent;
import com.complaint.model.Rate;
import com.complaint.model.RateColor;
import com.complaint.model.Sysconfig;
import com.complaint.model.TCasCell;
import com.complaint.model.TestMasterlog;
import com.complaint.model.WcdmaGsmData;
import com.complaint.utils.Constant;
import com.complaint.utils.SpringStoredProce;

@Service("reportIndependentService")
public class ReportIndependentService {
	@Autowired
	private ReportIndependentDao reportIndependentDao;
	@Autowired
	private GradMapper gradMapper;
	@Autowired
	private SysConfigDao sysConfigDao;
	
	/**
	 * isSingle 1-单次，2-多次
	
	 * @Title: getData
	
	 * @Description: 查询报表比例数据
	
	 * @param flowid
	 * @param areaid
	 * @param isSingle
	 * @return
	
	 * @return: List<KpiFlshData>
	 */
	public Map<String,Object> getData(String flowid,String areaid,int isSingle,HttpServletRequest request) {
		String firstFlowid = null;
		String[] flows = flowid.split(",");
		// 取第一个流水号
		if (flows.length > 0) {
			firstFlowid = flows[0];
		}
		GradeBean gb=null;
		//单次测试时调用等级评价方法
		if(flows.length==1&&isSingle==1){
			//gb=this.showGradKpi(flowid,areaid);
			// 初始化查询参数数据源
			WebApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(request.getSession()
							.getServletContext());
			gb=this.getGradKpi(ctx,areaid,flowid);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		String[] flowids = flowid.split(",");
		List<String> idlist = Arrays.asList(flowids);
		map.put("flowid", idlist);
		map.put("areaid",areaid);
		map.put("firstflowid", firstFlowid);
		// 室外区间阈值查询
		List<KpiIntervalValue> rlist = this.reportIndependentDao.getRptKpiByOut();
		// 室内区间阈值查询
		List<KpiIntervalValue> rlist_indoor = this.reportIndependentDao.getRptKpi();
		// 3G比例数据
		List<WcdmaGsmData> list3g = this.reportIndependentDao.getWcdmaData(map);
		// 2G比例数据
		List<WcdmaGsmData> list2g = this.reportIndependentDao.getGsmData(map);

		// FTP数据最大最小值
		List<MaxMinIntvel> intvel = this.reportIndependentDao.queryMaxMin(map);
		// 区间FLASH梳头图多个
		List<KpiFlshData> flist = new ArrayList<KpiFlshData>();
		KpiFlshData fd = null;
		List<String> xlist = null;
		List<String> indoor_xlist = null;
		List<Percent> plist = null;
		
		//单个flowid总体评价
		String comp_eval_3g_g = null;
	 	String comp_eval_3g_color = null;
	 	String comp_eval_2g_g = null;
	 	String comp_eval_2g_color = null;
		//flowid 2g总评价
		if(gb!=null){
			comp_eval_2g_g = gb.getComp_eval_2g_g();
			comp_eval_2g_color = gb.getComp_eval_2g_color();
		}
		//flowid 3g总评价
		if(gb!=null){
			comp_eval_3g_g = gb.getComp_eval_3g_g();
			comp_eval_3g_color = gb.getComp_eval_3g_color();
		}
		List<String> getCompEval = new ArrayList<String>();
		getCompEval.add(comp_eval_2g_color);
		getCompEval.add(comp_eval_2g_g);
		getCompEval.add(comp_eval_3g_color);
		getCompEval.add(comp_eval_3g_g);
		/**
		 * 根据流水号查询psc,bcch数据
		 * 并且封装成PSC、BCCH指标的柱状数据
		 */
		List<Percent> psclist = this.reportIndependentDao.getpsc(map);

		if (psclist != null && psclist.size() > 0) {

			List<String> pscxlist = new ArrayList<String>();
			List<String> bcchxlist = new ArrayList<String>();

			List<Percent> pscylist = new ArrayList<Percent>();
			List<Percent> bcchylist = new ArrayList<Percent>();

			for (int h = 0; h < psclist.size(); h++) {
				Percent p = psclist.get(h);
				// PSC－Ｘ轴
				if (p.getKpi().equals("20")) {
					p.setType("2");
					p.setKpiname("PSC");
					// 不是主选择流水号

					if (pscxlist.contains(p.getPscbcch()) == false) {
						
						pscxlist.add(p.getPscbcch());
						
					}
					pscylist.add(p);

				} else if (p.getKpi().equals("21")) {
					// BCCH－Ｘ轴
					p.setType("1");
					p.setKpiname("BCCH");
					// 不是主选择流水号

					if (bcchxlist.contains(p.getPscbcch()) == false) {
						bcchxlist.add(p.getPscbcch());
					}
					bcchylist.add(p);

				}
			}

			KpiFlshData f1 = new KpiFlshData();
			f1.setNetType("2");
			f1.setKpiId("20");
			f1.setKpiname("PSC");
			f1.setX(pscxlist);
			f1.setIndoor_x(pscxlist);
			f1.setY(pscylist);
			flist.add(f1);
			KpiFlshData f2 = new KpiFlshData();
			f2.setNetType("1");
			f2.setKpiId("21");
			f2.setKpiname("BCCH");
			f2.setX(bcchxlist);
			f2.setIndoor_x(bcchxlist);
			f2.setY(bcchylist);
			flist.add(f2);
		}

		// 其他指标
		for (int i = 0; i < rlist.size(); i++) {
			KpiIntervalValue rpt = rlist.get(i);
			KpiIntervalValue rpt_o = rlist_indoor.get(i);
			switch (rpt.getKpi()) {
			case 1:
				// RSCP
				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();
					fd.setMax(intvel.get(0).getMax_rscp());
					fd.setMin(intvel.get(0).getMin_rscp());
					if(gb!=null){
						fd.setGradName(gb.getRSCP_g());
						fd.setGradColor(gb.getRscp_color());
					}
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list3g.size(); j++) {
						WcdmaGsmData w = list3g.get(j);
						if (w.getRSCP() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("1");
							p.setType("2");
							p.setOne(w.getRSCP_1());
							p.setTwo(w.getRSCP_2());
							p.setThree(w.getRSCP_3());
							p.setFour(w.getRSCP_4());
							p.setFive(w.getRSCP_5());
							p.setSix(w.getRSCP_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}

						}
					}

				}
				
				break;
			case 2:
				// ec_no

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();

					fd.setMax(intvel.get(0).getMax_ec_no());
					fd.setMin(intvel.get(0).getMin_ec_no());
					if(gb!=null){
						fd.setGradName(gb.getECNO_g());
						fd.setGradColor(gb.getEcno_color());
					}
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list3g.size(); j++) {
						WcdmaGsmData w = list3g.get(j);
						if (w.getEC_NO() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("2");
							p.setType("2");
							p.setOne(w.getEC_NO_1());
							p.setTwo(w.getEC_NO_2());
							p.setThree(w.getEC_NO_3());
							p.setFour(w.getEC_NO_4());
							p.setFive(w.getEC_NO_5());
							p.setSix(w.getEC_NO_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;

			case 3:
				// TXPOWER

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();

					fd.setMax(intvel.get(0).getMax_txpower());
					fd.setMin(intvel.get(0).getMin_txpower());
					if(gb!=null){
						fd.setGradName(gb.getTxpower_g());
						fd.setGradColor(gb.getTx_color());
					}
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list3g.size(); j++) {
						WcdmaGsmData w = list3g.get(j);
						if (w.gettxpower() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("3");
							p.setType("2");
							p.setOne(w.getTxpower_1());
							p.setTwo(w.getTxpower_2());
							p.setThree(w.getTxpower_3());
							p.setFour(w.getTxpower_4());
							p.setFive(w.getTxpower_5());
							p.setSix(w.getTxpower_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;
			case 4:
				// ftp

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();
					fd.setMax(intvel.get(0).getMax_ftp());
					fd.setMin(intvel.get(0).getMin_ftp());
					fd.setAvg(intvel.get(0).getAvg_ftp());
					if(gb!=null){
						fd.setGradName(gb.getFu_g());
						fd.setGradColor(gb.getFu_color());
					}
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					// 3g
					for (int j = 0; j < list3g.size(); j++) {
						WcdmaGsmData w = list3g.get(j);
						if (w.getFTP_SPEED_UP() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("4");
							p.setType("2");
							p.setOne(w.getFTP_SPEED_UP_1());
							p.setTwo(w.getFTP_SPEED_UP_2());
							p.setThree(w.getFTP_SPEED_UP_3());
							p.setFour(w.getFTP_SPEED_UP_4());
							p.setFive(w.getFTP_SPEED_UP_5());
							p.setSix(w.getFTP_SPEED_UP_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;
			case 5:
				// ftp

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();

					fd.setMax(intvel.get(0).getMax_ftp());
					fd.setMin(intvel.get(0).getMin_ftp());
					fd.setAvg(intvel.get(0).getAvg_ftp());
					if(gb!=null){
						fd.setGradName(gb.getFd_g());
						fd.setGradColor(gb.getFd_color());
					}
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list3g.size(); j++) {
						WcdmaGsmData w = list3g.get(j);
						if (w.getFTP_SPEED_DOWN() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("5");
							p.setType("2");
							p.setOne(w.getFTP_SPEED_DOWN_1());
							p.setTwo(w.getFTP_SPEED_DOWN_2());
							p.setThree(w.getFTP_SPEED_DOWN_3());
							p.setFour(w.getFTP_SPEED_DOWN_4());
							p.setFive(w.getFTP_SPEED_DOWN_5());
							p.setSix(w.getFTP_SPEED_DOWN_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;

			case 6:
				// RXLEV_Subl

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();

					fd.setMax(intvel.get(0).getMax_rxlev());
					fd.setMin(intvel.get(0).getMin_rxlev());
					if(gb!=null){
						fd.setGradName(gb.getRx_g());
						fd.setGradColor(gb.getRx_color());
					}
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list2g.size(); j++) {
						WcdmaGsmData w = list2g.get(j);
						if (w.getRXLEV_Sub() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("6");
							p.setType("1");
							p.setOne(w.getRXLEV_Sub_1());
							p.setTwo(w.getRXLEV_Sub_2());
							p.setThree(w.getRXLEV_Sub_3());
							p.setFour(w.getRXLEV_Sub_4());
							p.setFive(w.getRXLEV_Sub_5());
							p.setSix(w.getRXLEV_Sub_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;
			case 7:
				// RXQUAL_Sub

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();

					fd.setMax(intvel.get(0).getMax_rxqual());
					fd.setMin(intvel.get(0).getMin_rxqual());
					if(gb!=null){
						fd.setGradName(gb.getRq_g());
						fd.setGradColor(gb.getRq_color());
					}
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list2g.size(); j++) {
						WcdmaGsmData w = list2g.get(j);
						if (w.getRXQUAL_Sub() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("7");
							p.setType("1");
							p.setOne(w.getRXQUAL_Sub_1());
							p.setTwo(w.getRXQUAL_Sub_2());
							p.setThree(w.getRXQUAL_Sub_3());
							p.setFour(w.getRXQUAL_Sub_4());
							p.setFive(w.getRXQUAL_Sub_5());
							p.setSix(w.getRXQUAL_Sub_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;
			case 8:
				// C_I

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();
					fd.setMax(intvel.get(0).getMax_ci());
					fd.setMin(intvel.get(0).getMin_ci());
					if(gb!=null){
						fd.setGradName(gb.getCi_g());
						fd.setGradColor(gb.getCi_color());
					}
					/*fd.setGradName("差");
					fd.setGradColor("#F20A19");*/
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list2g.size(); j++) {
						WcdmaGsmData w = list2g.get(j);
						if (w.getC_I() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("8");
							p.setType("1");
							p.setOne(w.getC_I_1());
							p.setTwo(w.getC_I_2());
							p.setThree(w.getC_I_3());
							p.setFour(w.getC_I_4());
							p.setFive(w.getC_I_5());
							p.setSix(w.getC_I_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;

			case 9:
				// MOS

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list2g.size(); j++) {
						WcdmaGsmData w = list2g.get(j);
						if (w.getMOS() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("9");
							p.setType("1");
							p.setOne(w.getMOS_1());
							p.setTwo(w.getMOS_2());
							p.setThree(w.getMOS_3());
							p.setFour(w.getMOS_4());
							p.setFive(w.getMOS_5());
							p.setInside(w.getInside());
							p.setSix(w.getMOS_6());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;

			case 22:
				// TXPOWER

				if (rpt.getSerialNum() == 1) {
					fd = new KpiFlshData();
					xlist = new ArrayList<String>();
					indoor_xlist = new ArrayList<String>();
					// 循环查出的指标的百分比
					plist = new ArrayList<Percent>();
					for (int j = 0; j < list2g.size(); j++) {
						WcdmaGsmData w = list2g.get(j);
						if (w.gettxpower() == 1) {
							Percent p = new Percent();
							p.setFlowid(w.getFlowid());
							p.setKpi("22");
							p.setType("1");
							p.setOne(w.getTxpower_1());
							p.setTwo(w.getTxpower_2());
							p.setThree(w.getTxpower_3());
							p.setFour(w.getTxpower_4());
							p.setFive(w.getTxpower_5());
							p.setSix(w.getTxpower_6());
							p.setInside(w.getInside());
							if (firstFlowid.equals(w.getFlowid())
									&& flows.length > 0) {
								plist.add(0, p);
							} else {
								plist.add(p);
							}
						}
					}

				}

				break;
			default:
				break;

			}
			// 拼装字符串设置X轴数据格式"-95,-90"
			if (rpt.getSerialNum() == 1) {
				xlist.add(rpt.getKpiValue() + "");
				indoor_xlist.add(rpt_o.getKpiValue() + "");
			} else if (rpt.getSerialNum() == 5) {
				if (rpt.getKpi() == 3 || rpt.getKpi() == 7
						|| rpt.getKpi() == 22) {
					xlist.add(rpt.getKpiValue() + ","
							+ rlist.get(i - 1).getKpiValue());
					indoor_xlist.add(rpt_o.getKpiValue() + ","
							+ rlist_indoor.get(i - 1).getKpiValue());
				} else {
					xlist.add(rlist.get(i - 1).getKpiValue() + ","
							+ rpt.getKpiValue());
					indoor_xlist.add(rlist_indoor.get(i - 1).getKpiValue()
							+ "," + rpt_o.getKpiValue());
				}

				xlist.add(rpt.getKpiValue() + "");
				indoor_xlist.add(rpt_o.getKpiValue() + "");
			} else {
				if (rpt.getKpi() == 3 || rpt.getKpi() == 7
						|| rpt.getKpi() == 22) {
					xlist.add(rpt.getKpiValue() + ","
							+ rlist.get(i - 1).getKpiValue());
					indoor_xlist.add(rpt_o.getKpiValue() + ","
							+ rlist_indoor.get(i - 1).getKpiValue());
				} else {
					xlist.add(rlist.get(i - 1).getKpiValue() + ","
							+ rpt.getKpiValue());
					indoor_xlist.add(rlist_indoor.get(i - 1).getKpiValue()
							+ "," + rpt_o.getKpiValue());
				}
			}
			if (rpt.getSerialNum() == 5) {
				fd.setNetType(rpt.getNetType() + "");
				fd.setKpiId(rpt.getKpi() + "");
				fd.setKpiname(rpt.getKpiName());
				fd.setX(xlist);
				fd.setIndoor_x(indoor_xlist);
				fd.setY(plist);
				flist.add(fd);
			}
		}
		//查询FTP数据业务曲线数据生成拆线图
		map.put("ftpType", 1);
		List<Percent> lpup = this.reportIndependentDao.getFtp(map);
		map.put("ftpType", 2);
		List<Percent> lpdown = this.reportIndependentDao.getFtp(map);
		for (int j = 0; j < flist.size(); j++) {
			KpiFlshData k = flist.get(j);
			if (k.getKpiId().equals("4")) {
				k.setFtp(lpup);
			}
			if (k.getKpiId().equals("5")) {
				k.setFtp(lpdown);
			}
		}

		// for(int j=0;j<flist.size();j++){
		// KpiFlshData k=flist.get(j);
		// if(k.getKpiId().equals("20")){
		// List<String> x=k.getX();
		// List<Percent> y=k.getFtp();
		// System.out.println("kpiid============"+k.getKpiname());
		// System.out.println("y长============"+y.size());
		// for(int t=0;t<y.size();t++){
		// Percent p=y.get(t);
		// System.out.println("yPercent============"+p.getPercent());
		// }
		// }
		//
		// }
		
		
		Map<String,Object> flistMap = new HashMap<String,Object>();
		flistMap.put("getCompEval", getCompEval);
		flistMap.put("flist", flist);
		return flistMap;
	}

	/**
	 * 根据工单号查询流水号 id:工单号
	 * */
	public List<TestMasterlog> getserialno(String serialno, String flowid,
			String netType, String testType,String areaid,String s_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialno", serialno);
		map.put("flowid", flowid);
		map.put("netType", netType);
		map.put("testType", testType);
		map.put("areaid",areaid);
		map.put("s_id",s_id);
		return this.reportIndependentDao.getserialno(map);
	}

	/**
	 * 根据流水号查询计算应用（HTTP,PING）数据,只有业务才有 map:流水号
	 * */
	public List<TestMasterlog> getHPF(String flowid,String areaid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowid", flowid);
		map.put("areaid",areaid);
		return this.reportIndependentDao.getHPF(map);
	}

	/**
	 * 根据流水号查询测试结果 map:流水号
	 * */
	public List<TestMasterlog> queryResult(String serialno, String flowid,
			String netType, String testType,String areaid,String s_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowid", flowid);
		map.put("netType", netType);
		map.put("testType", testType);
		map.put("serialno", serialno);
		map.put("areaid", areaid);
		map.put("s_id", s_id);
		return this.reportIndependentDao.queryResult(map);
	}

	/***
	 * 颜色查询
	 * 
	 * @return
	 */
	public List<ColourCode> queryColor() {
		return this.reportIndependentDao.queryColor();
	}
	/***
	 * 根据id统计条数
	 */
	public int countNumById(String id){
		return this.reportIndependentDao.countNumById(id);
	}
	
	
	/**
	 * 
	
	 * @Title: showGradKpi
	
	 * @Description: 查询出些流水室内外类型KPI区间值、占比、等级设置属性
	 * 当做参数查询出2G与3G的百分比并比较出等级
	
	 * @param flowid
	 * @param areaid
	 * @return
	
	 * @return: GradeBean
	 */
	public GradeBean showGradKpi(String flowid,String areaid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowid", flowid);
		map.put("areaid", areaid);
		List<Rate> listr=this.gradMapper.showGradKpi(map);
		for(int i=0;i<listr.size();i++){
			Rate r=listr.get(i);
			if(r.getKpi_code()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("rscp_s_1", r.getRank_arithmetic());
					map.put("rscp_v_1", r.getRank_value());
					map.put("rscp_io_1", r.getRank_ratio());
					break;
				case 2:
					map.put("rscp_s_2", r.getRank_arithmetic());
					map.put("rscp_v_2", r.getRank_value());
					map.put("rscp_io_2", r.getRank_ratio());
					break;
				case 4:
					map.put("rscp_s_4", r.getRank_arithmetic());
					map.put("rscp_v_4", r.getRank_value());
					map.put("rscp_io_4", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			if(r.getKpi_code()==2){
				switch (r.getRank_code()) {
				case 1:
					map.put("ecno_s_1", r.getRank_arithmetic());
					map.put("ecno_v_1", r.getRank_value());
					map.put("ecno_io_1", r.getRank_ratio());
					break;
				case 2:
					map.put("ecno_s_2", r.getRank_arithmetic());
					map.put("ecno_v_2", r.getRank_value());
					map.put("ecno_io_2", r.getRank_ratio());
					break;
				
				case 4:
					map.put("ecno_s_4", r.getRank_arithmetic());
					map.put("ecno_v_4", r.getRank_value());
					map.put("ecno_io_4", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			if(r.getKpi_code()==3){
				switch (r.getRank_code()) {
				case 1:
					map.put("tx_s_1", r.getRank_arithmetic());
					map.put("tx_v_1", r.getRank_value());
					map.put("tx_io_1", r.getRank_ratio());
					break;
				case 2:
					map.put("tx_s_2", r.getRank_arithmetic());
					map.put("tx_v_2", r.getRank_value());
					map.put("tx_io_2", r.getRank_ratio());
					break;
				
				case 4:
					map.put("tx_s_4", r.getRank_arithmetic());
					map.put("tx_v_4", r.getRank_value());
					map.put("tx_io_4", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			if(r.getKpi_code()==4){
				switch (r.getRank_code()) {
				case 1:
					map.put("fu_s_1", r.getRank_arithmetic());
					map.put("fu_v_1", r.getRank_value());
					map.put("fu_io_1", r.getRank_ratio());
					//map=setAbb(map,r.getRank_avg(),"fu_av",1);
					map.put("fu_av_1", r.getRank_avg());
					break;
				case 2:
					map.put("fu_s_2", r.getRank_arithmetic());
					map.put("fu_v_2", r.getRank_value());
					map.put("fu_io_2", r.getRank_ratio());
					map.put("fu_av_2", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fu_av",2);
					break;
				
				case 4:
					map.put("fu_s_4", r.getRank_arithmetic());
					map.put("fu_v_4", r.getRank_value());
					map.put("fu_io_4", r.getRank_ratio());
					map.put("fu_av_4", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fu_av",4);
					break;
				default:
					break;
				}
			}
			
			if(r.getKpi_code()==5){
				switch (r.getRank_code()) {
				case 1:
					map.put("fd_s_1", r.getRank_arithmetic());
					map.put("fd_v_1", r.getRank_value());
					map.put("fd_io_1", r.getRank_ratio());
					map.put("fd_av_1", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fd_av",1);
					break;
				case 2:
					map.put("fd_s_2", r.getRank_arithmetic());
					map.put("fd_v_2", r.getRank_value());
					map.put("fd_io_2", r.getRank_ratio());
					map.put("fd_av_2", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fd_av",2);
					break;
				
				case 4:
					map.put("fd_s_4", r.getRank_arithmetic());
					map.put("fd_v_4", r.getRank_value());
					map.put("fd_io_4", r.getRank_ratio());
					map.put("fd_av_4", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fd_av",4);
					break;
				default:
					break;
				}
			}
			if(r.getKpi_code()==6){
				switch (r.getRank_code()) {
				case 1:
					map.put("rxl_s_1", r.getRank_arithmetic());
					map.put("rxl_v_1", r.getRank_value());
					map.put("rxl_io_1", r.getRank_ratio());
					break;
				case 2:
					map.put("rxl_s_2", r.getRank_arithmetic());
					map.put("rxl_v_2", r.getRank_value());
					map.put("rxl_io_2", r.getRank_ratio());
					break;
				
				case 4:
					map.put("rxl_s_4", r.getRank_arithmetic());
					map.put("rxl_v_4", r.getRank_value());
					map.put("rxl_io_4", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			if(r.getKpi_code()==7){
				switch (r.getRank_code()) {
				case 1:
					map.put("rxq_s_1", r.getRank_arithmetic());
					map.put("rxq_v_1", r.getRank_value());
					map.put("rxq_io_1", r.getRank_ratio());
					break;
				case 2:
					map.put("rxq_s_2", r.getRank_arithmetic());
					map.put("rxq_v_2", r.getRank_value());
					map.put("rxq_io_2", r.getRank_ratio());
					break;
				
				case 4:
					map.put("rxq_s_4", r.getRank_arithmetic());
					map.put("rxq_v_4", r.getRank_value());
					map.put("rxq_io_4", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
		}
		//等级
		List<RateColor> colist=this.gradMapper.showGradColor();
		String g_1=null,g_2=null,g_3=null,g_4=null;
		 if(colist!=null&&colist.size()>0){
			 g_1=colist.get(0).getRank_color();
			 g_2=colist.get(1).getRank_color();
			 g_3=colist.get(2).getRank_color();
			 g_4=colist.get(3).getRank_color();
		 }
		GradeBean gg=new GradeBean();
		List<GradeBean> l2g=this.gradMapper.showGradBySingleGsm(map);
		for (int g=0;g<l2g.size();g++){
			GradeBean gb=l2g.get(g);
			String ss_1=(String) map.get("rxl_io_1");
			String ss_2=(String) map.get("rxl_io_2");
			String ss_4=(String) map.get("rxl_io_4");
		     if(findBigSmall(gb.getRXLEV_Sub_4(),ss_1)){
		    	 gg.setRx_g("优");
		    	 gg.setRx_color(g_1);
		     }else if (findBigSmall(gb.getRXLEV_Sub_3(),ss_2)){
		    	 gg.setRx_g("良");
		    	 gg.setRx_color(g_2);
		     }else if (findBigSmall(gb.getRXLEV_Sub_1(),ss_4)){
		    	 gg.setRx_g("差");
		    	 gg.setRx_color(g_4);
		     }else{
		    	 gg.setRx_g("中");
		    	 gg.setRx_color(g_3);
		     }
		     String qq_1=(String) map.get("rxq_io_1");
		     String qq_2=(String) map.get("rxq_io_2");
		     String qq_4=(String) map.get("rxq_io_4");
		     if(findBigSmall(gb.getRXQUAL_Sub_4(),qq_1)){
		    	 gg.setRq_g("优");
		    	 gg.setRq_color(g_1);
		     }else if (findBigSmall(gb.getRXQUAL_Sub_3(),qq_2)){
		    	 gg.setRq_g("良");
		    	 gg.setRq_color(g_2);
		     }else if (findBigSmall(gb.getRXQUAL_Sub_1(),qq_4)){
		    	 gg.setRq_g("差");
		    	 gg.setRq_color(g_4);
		     }else{
			    	 gg.setRq_g("中");
			    	 gg.setRq_color(g_3);
		     }
		
		}
		List<GradeBean> l3g=this.gradMapper.showGradBySingleWcdma(map);
        for (int w=0;w<l3g.size();w++){
        	GradeBean wb=l3g.get(w);
        	 String rr_1=(String) map.get("rscp_io_1");
        	 String rr_2=(String) map.get("rscp_io_2");
        	 String rr_4=(String) map.get("rscp_io_4");
		     if(findBigSmall(wb.getRSCP_4(),rr_1)){
		    	 gg.setRSCP_g("优");
		    	 gg.setRscp_color(g_1);
		     }else if (findBigSmall(wb.getRSCP_3(),rr_2)){
		    	 gg.setRSCP_g("良");
		    	 gg.setRscp_color(g_2);
		     }else if (findBigSmall(wb.getRSCP_1(),rr_4)){
		    	 gg.setRSCP_g("差");
		    	 gg.setRscp_color(g_4);
		     }else{
		    	 gg.setRSCP_g("中");
		    	 gg.setRscp_color(g_3);
		     }
		     String ee_1=(String) map.get("ecno_io_1");
        	 String ee_2=(String) map.get("ecno_io_2");
        	 String ee_4=(String) map.get("ecno_io_4");
		     if(findBigSmall(wb.getEC_NO_4(),ee_1)){
		    	 gg.setECNO_g("优");
		    	 gg.setEcno_color(g_1);
		     }else if (findBigSmall(wb.getEC_NO_3(),ee_2)){
		    	 gg.setECNO_g("良");
		    	 gg.setEcno_color(g_2);
		     }else if (findBigSmall(wb.getEC_NO_1(),ee_4)){
		    	 gg.setECNO_g("差");
		    	 gg.setEcno_color(g_4);
		     }else{
		    	 gg.setECNO_g("中");
		    	 gg.setEcno_color(g_3);
		     }
		     String tx_1=(String) map.get("tx_io_1");
        	 String tx_2=(String) map.get("tx_io_2");
        	 String tx_4=(String) map.get("tx_io_4");
		     if(findBigSmall(wb.getTxpower_4(),tx_1)){
		    	 gg.setTxpower_g("优");
		    	 gg.setTx_color(g_1);
		     }else if (findBigSmall(wb.getTxpower_3(),tx_2)){
		    	 gg.setTxpower_g("良");
		    	 gg.setTx_color(g_2);
		     }else if (findBigSmall(wb.getTxpower_1(),tx_4)){
		    	 gg.setTxpower_g("差");
		    	 gg.setTx_color(g_4);
		     }else{
		    	 gg.setTxpower_g("中");
		    	 gg.setTx_color(g_3);
		     }
		     
		     String fu_1=(String) map.get("fu_io_1");
        	 String fu_2=(String) map.get("fu_io_2");
        	 String fu_4=(String) map.get("fu_io_4");
		     if(findBigSmall(wb.getFTP_SPEED_UP_4(),fu_1)&&findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fu_av_1"))){
		    	 gg.setFu_g("优");
		    	 gg.setFu_color(g_1);
		     }else if (findBigSmall(wb.getFTP_SPEED_UP_3(),fu_2)&&findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fu_av_2"))){
		    	 gg.setFu_g("良");
		    	 gg.setFu_color(g_2);
		     }else if (findBigSmall(wb.getFTP_SPEED_UP_1(),fu_4)||findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fu_av_4"))){
		    	 gg.setFu_g("差");
		    	 gg.setFu_color(g_4);
		     }else{
		    	 gg.setFu_g("中");
		    	 gg.setFu_color(g_3);
		     }
		     
		     String fd_1=(String) map.get("fd_io_1");
        	 String fd_2=(String) map.get("fd_io_2");
        	 String fd_4=(String) map.get("fd_io_4");
		     if(findBigSmall(wb.getFTP_SPEED_DOWN_4(),fd_1)&&findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fd_av_1"))){
		    	 gg.setFd_g("优");
		    	 gg.setFd_color(g_1);
		     }else if (findBigSmall(wb.getFTP_SPEED_DOWN_3(),fd_2)&&findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fd_av_2"))){
		    	 gg.setFd_g("良");
		    	 gg.setFd_color(g_2);
		     }else if (findBigSmall(wb.getFTP_SPEED_DOWN_1(),fd_4)||findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fd_av_4"))){
		    	 gg.setFd_g("差");
		    	 gg.setFd_color(g_4);
		     }else{
		    	 gg.setFd_g("中");
		    	 gg.setFd_color(g_3);
		     }
		}
		return gg;
		
	}
	
	/**
	 * 判断百分比在哪个等级区间
	
	 * @Title: findBigSmall
	
	 * @Description: TODO
	
	 * @param value
	 * @param str
	 * @return
	
	 * @return: Boolean
	 */
	public Boolean findBigSmall(Double value,String str){
		if(str!=null){
			String s1=str.substring(0,1);
			String s2=str.substring(str.length()-1,str.length());
			String s3=str.substring(1,str.length()-1);
			
			List<String> li = new ArrayList<String>();  
			for(String t : s3.split(",")){  
			        li.add(t);  
			}  
	        if(s3.contains("∞")){
	        	if(li.size()>1){
	        		if(li.get(0).contains("∞")&&li.get(1).contains("∞")){
	        			return true;
	        		}else if(li.get(0).contains("∞")&&!li.get(1).contains("∞")){
	        			if(s2.equals(")")){
	        				if(value<Double.parseDouble(li.get(1))){
	    						return true;
	    					}
	        			}
	        			if(s2.equals("]")){
	        				if(value<=Double.parseDouble(li.get(1))){
	    						return true;
	    					}
	        			}
	        		}else if(!li.get(0).contains("∞")&&li.get(1).contains("∞")){
	        			if(s1.equals("(")){
	        				if(value>Double.parseDouble(li.get(0))){
	    						return true;
	    					}
	        			}
	        			if(s1.equals("[")){
	        				if(value>=Double.parseDouble(li.get(0))){
	    						return true;
	    					}
	        			}
	        		}
	        	}
	        	else if(li.size()==1){
	        		return true;
	        	}
	        }else{
			if (li.size()>1){
				if(s1.equals("(")&&s2.equals(")")){
					if(value>Double.parseDouble(li.get(0))&&value<Double.parseDouble(li.get(1))){
						return true;
					}
				}
				if(s1.equals("(")&&s2.equals("]")){
					if(value>Double.parseDouble(li.get(0))&&value<=Double.parseDouble(li.get(1))){
						return true;
					}
				}
				if(s1.equals("[")&&s2.equals(")")){
					if(value>=Double.parseDouble(li.get(0))&&value<Double.parseDouble(li.get(1))){
						return true;
					}
				}
				if(s1.equals("[")&&s2.equals("]")){
					if(value>=Double.parseDouble(li.get(0))&&value<=Double.parseDouble(li.get(1))){
						return true;
					}
				}
			}else if(li.size()==1){
				if(s1.equals("(")){
					if(value>Double.parseDouble(li.get(0))){
						return true;
					}
				}
				if(s1.equals("[")){
					if(value>=Double.parseDouble(li.get(0))){
						return true;
					}
				}
			}
	        }
		}
		return false;
	}
	
	/**
	 * 
	
	 * @Title: setAbb
	
	 * @Description: 拆分区间字符并设置属性，根据等级、KPI命名
	
	 * @param map
	 * @param ss
	 * @param abb
	 * @param i
	 * @return
	
	 * @return: Map<String,Object>
	 
	public Map<String, Object> setAbb(Map map,String ss,String abb,int i){
		
		if(ss!=null&&ss.length()>1){
			String s1=ss.substring(0,1);
			String s2=ss.substring(ss.length()-1,ss.length());
			String s3=ss.substring(1,ss.length()-1);
			String st[]=s3.split(",");
			map.put(abb+"_s_"+i, s1);
			map.put(abb+"_e_"+i, s2);
			if(st.length>1){
				map.put(abb+i+"_0", st[0]);
				map.put(abb+i+"_1", st[1]);
			}else if(st.length==1){
				map.put(abb+i+"_0", st[0]);
				map.put(abb+i+"_1", null);
			}

		}
		return map;
		
	}*/
	/**
	 * 调用存储过程
	 */
	public ResultSet getStoreProce(WebApplicationContext ctx ,String storedProcName ,Map<String,Object> params){
		DataSource ds = ctx.getBean("dataSource", DataSource.class);
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
	 * 判断评价等级
	 */
	public String getLevel(Integer i){
		String level=null;
		if(i==1){
			level="优";
		}
		if(i==2){
			level="良";
		}
		if(i==3){
			level="中";
		}
		if(i==4){
			level="差";
		}
		return level;
	}
	/**
	 * 调用存储过程查询评价
	 */
	public GradeBean getGradKpi(WebApplicationContext ctx,String areaid,String flowid){
		//查出颜色
		List<RateColor> colist=this.gradMapper.showGradColor();
		//注意参数顺序
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("areaid", Integer.parseInt(areaid));
		params.put("flowid", flowid);
		params.put("tlog", "t_log_manual_own");
		params.put("twcdma", "t_log_own_wcdma");
		params.put("tgsm", "t_log_own_gsm");
		GradeBean gb = new GradeBean();
		this.reportIndependentDao.getEval(params);
		List<EvalSet> st=(List<EvalSet>) params.get("depts");
		int i=0;
		//调存储过程
		for(EvalSet es:st){
			//判断没想属性值如果为0则不存在，不为零就放入值
			if(es.getRscp()!=0){
				//System.out.println(result.getInt("rscp"));
				i =es.getRscp();
				gb.setRSCP_g(this.getLevel(i));
				gb.setRscp_color(colist.get(i-1).getRank_color());
			}else{
				gb.setRSCP_g(null);
				gb.setRscp_color(null);
			}
			if(es.getEcno()!=0){
				i =es.getEcno();
				gb.setECNO_g(this.getLevel(i));
				gb.setEcno_color(colist.get(i-1).getRank_color());
			}else{
				gb.setECNO_g(null);
				gb.setEcno_color(null);
			}
			if(es.getTxpower()!=0){
				i =es.getTxpower();
				gb.setTxpower_g(this.getLevel(i));
				gb.setTx_color(colist.get(i-1).getRank_color());
			}else{
				gb.setTxpower_g(null);
				gb.setTx_color(null);
			}
			if(es.getFtpup()!=0){
				i =es.getFtpup();
				gb.setFu_g(this.getLevel(i));
				gb.setFu_color(colist.get(i-1).getRank_color());
			}else{
				gb.setFu_g(null);
				gb.setFu_color(null);
			}
			if(es.getFtpdown()!=0){
				i =es.getFtpdown();
				gb.setFd_g(this.getLevel(i));
				gb.setFd_color(colist.get(i-1).getRank_color());
			}else{
				gb.setFd_g(null);
				gb.setFd_color(null);
			}
			if(es.getRxlev()!=0){
				i =es.getRxlev();
				gb.setRx_g(this.getLevel(i));
				gb.setRx_color(colist.get(i-1).getRank_color());
			}else{
				gb.setRx_g(null);
				gb.setRx_color(null);
			}
			if(es.getRxqual()!=0){
				i =es.getRxqual();
				gb.setRq_g(this.getLevel(i));
				gb.setRq_color(colist.get(i-1).getRank_color());
			}else{
				gb.setRq_g(null);
				gb.setRq_color(null);
			}
			if(es.getCi()!=0){
				i =es.getCi();
				gb.setCi_g(this.getLevel(i));
				gb.setCi_color(colist.get(i-1).getRank_color());
			}else{
				gb.setCi_g(null);
				gb.setCi_color(null);
			}
			if(es.getCompeval3g()!=0){
				i =es.getCompeval3g();
				gb.setComp_eval_3g_g(this.getLevel(i));
				gb.setComp_eval_3g_color(colist.get(i-1).getRank_color());
			}else{
				gb.setComp_eval_3g_g(null);
				gb.setComp_eval_3g_color(null);
			}
			if(es.getCompeval2g()!=0){
				i =es.getCompeval2g();
				gb.setComp_eval_2g_g(this.getLevel(i));
				gb.setComp_eval_2g_color(colist.get(i-1).getRank_color());
			}else{
				gb.setComp_eval_2g_g(null);
				gb.setComp_eval_2g_color(null);
			}
			
		}
		return gb;
	}
	
	/**
	 * 根据flowid获得原始经纬度
	 * 百度地图使用的是原始经纬度
	 * @param flowid
	 * @return
	 */
	public Map getlatlng(String flowid){
		Map map = this.reportIndependentDao.getlatlng(flowid);
		return map;
	}
	
	/**
	 * 根据flowid查出关联小区
	 */
	public Map<String ,Object> getCell(String flowid){
		Map pattem = new HashMap();
		pattem.put("flowid",flowid);
		List<TCasCell> cell = this.reportIndependentDao.getCell(pattem);
		Sysconfig sysconfig = this.sysConfigDao.getAngleconfig(Constant.CELL_ANGLE_CONFIG);
		Sysconfig typeMap = this.sysConfigDao.getAngleconfig(Constant.MAPTYPE);
		if((typeMap.getConfigvalue()).equals("baidu")){
			for(int i =0;i<cell.size();i++){
				ConverterTool ctl=new ConverterTool();
        		Point point = ctl.GG2BD(cell.get(i).getCelllng().doubleValue(), cell.get(i).getCelllat().doubleValue());
        		cell.get(i).setCelllng(new BigDecimal(point.getLongitude())); //百度纠偏后经度
        		cell.get(i).setCelllat(new BigDecimal(point.getLatitude())); //百度纠偏后纬度
			}
		}
		String[] str = sysconfig.getConfigvalue().split("\\|");
		String type = (str[0].split("="))[1];
		String angle = (str[1].split("="))[1];
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("cell", cell);
		map.put("type", type);
		map.put("angle", angle);
		
		List<String> bids = new ArrayList<String>();
		for(TCasCell cas : cell){
			String bid = cas.getBid();
			boolean flag = false;
			for(String id : bids){
				if(id.equals(bid)){
					flag = true;
					break;
				}
			}
			if(!flag){
				bids.add(bid);
			}
		}
		
		List<BaseStation> list = new ArrayList<BaseStation>();
		for(String bid : bids){
			BaseStation bs = new BaseStation();
			List<TCasCell> tcc = new ArrayList<TCasCell>();
			for(TCasCell cas : cell){
				if(cas.getBid().equals(bid)){
					tcc.add(cas);
				}
			}
			bs.setBid(bid);
			bs.setBsList(tcc);
			list.add(bs);
		}
		map.put("list", list);
		return map;
	}
	/**
	 * 修改审核状态
	 * @param serialno
	 * @param val
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateVerify(String id,Integer val) throws Exception{
		int flag =1;
		
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("val", val);
		map.put("id", id);
		this.reportIndependentDao.updateVerify(map);
		
		return flag;
		
	}
	
	/**
	 * 修改地址
	 * @param testMasterlog
	 * @return
	 */
	public int updateAdress(TestMasterlog testMasterlog){
    	return this.reportIndependentDao.updateAdress(testMasterlog);
    }
}
