package com.complaint.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.action.vo.VoBean;
import com.complaint.dao.GisgradMapper;
import com.complaint.dao.SysConfigDao;
import com.complaint.model.CenterZoom;
import com.complaint.model.EvaluateRule;
import com.complaint.model.Gisgrad;
import com.complaint.model.GradeBean;
import com.complaint.model.Rate;
import com.complaint.model.RateColor;
import com.complaint.model.RevisRule;
import com.complaint.model.ScoreRule;
import com.complaint.model.Sysconfig;
import com.complaint.model.TCasCell;

@Service("gisgradService")
public class GisgradService {
	@Autowired
	private GisgradMapper gisgradMapper;
	@Autowired
	private SysConfigDao sysConfigDao;
	private static final Logger logger = LoggerFactory.getLogger(GisgradService.class);
	/**
	 * 
	
	 * @Title: showGrad
	
	 * @Description: 查询出地图等级里的数据 
	
	 * @param VoBean
	 * @return
	
	 * @return: Gisgrad
	 */
	public Map showGrad(VoBean vo ,String visitType){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Rate> listr=this.gisgradMapper.showGradKpi();
		 map=this.setKpi(listr, map, vo);
		 map.put("iscenter", "1");
		 List<EvaluateRule> er=this.gisgradMapper.queryProject(map);
	     List<ScoreRule> rr=this.gisgradMapper.queryKpi();
	     List<RevisRule>  drop=this.gisgradMapper.queryProDrop();
	     //室内2G
	     List<GradeBean> li=this.gisgradMapper.noqueryindoor2(map);
	     Map<String,Object> m1 = this.getGisgrad(map, li, rr, 0, er, drop, vo.getKpi(),vo.getGrad());
	     Map<List<Integer>, List<Gisgrad>> gl=(Map<List<Integer>, List<Gisgrad>>) m1.get("mm");
	     
	     List<GradeBean> l2=this.gisgradMapper.noqueryoutdoor2(map);
	     Map<String,Object> m2 = this.getGisgrad(map, l2, rr, 0, er, drop, vo.getKpi(),vo.getGrad());
	     Map<List<Integer>, List<Gisgrad>> g2=(Map<List<Integer>, List<Gisgrad>>) m2.get("mm");
	     
	     List<GradeBean> l3=this.gisgradMapper.noqueryindoor3(map);
	     Map<String,Object> m3 = this.getGisgrad(map, l3, rr, 0, er, drop, vo.getKpi(),vo.getGrad());
	     Map<List<Integer>, List<Gisgrad>> g3=(Map<List<Integer>, List<Gisgrad>>) m3.get("mm");
	     
	     List<GradeBean> l4=this.gisgradMapper.noqueryoutdoor3(map);
	     Map<String,Object> m4 = this.getGisgrad(map, l4, rr, 0, er, drop, vo.getKpi(),vo.getGrad());
	     Map<List<Integer>, List<Gisgrad>> g4=(Map<List<Integer>, List<Gisgrad>>) m4.get("mm");
	     List<Gisgrad> neall=new ArrayList<Gisgrad>();
	     List<Integer> neyy=new ArrayList<Integer>();
	     List<Integer> yy1=new ArrayList<Integer>();
	     for(List<Integer> key:gl.keySet()){
	    	 yy1=key;
			  neall.addAll(gl.get(key));
        }
	     
	     List<Integer> yy2=new ArrayList<Integer>();
	     for(List<Integer> key:g2.keySet()){
	    	 yy2=key;
			  neall.addAll(g2.get(key));
        }
	     
	     List<Integer> yy3=new ArrayList<Integer>();
	     for(List<Integer> key:g3.keySet()){
	    	 yy3=key;
			  neall.addAll(g3.get(key));
        }
	     
	     List<Integer> yy4=new ArrayList<Integer>();
	     for(List<Integer> key:g4.keySet()){
	    	 yy4=key;
			  neall.addAll(g4.get(key));
        }
		    List<Integer> tt=null;
		    if(yy1.size()>0)tt=yy1;
		    if(yy2.size()>0)tt=yy2;
		    if(yy3.size()>0)tt=yy3;
		    if(yy4.size()>0)tt=yy4;
	    	 for(int k=0;k<tt.size();k++){
	    		 int jk=0;
	    		 if(yy1.size()>0)jk+=yy1.get(k);
	    		 if(yy2.size()>0)jk+=yy2.get(k);
	    		 if(yy3.size()>0)jk+=yy3.get(k);
	    		 if(yy4.size()>0)jk+=yy4.get(k);
	    		 neyy.add(jk);
	    	 }
	    
	     Map<List<Integer>, List<Gisgrad>> all=new HashMap<List<Integer>, List<Gisgrad>>();
	     all.put(neyy, neall);

	     Map<String , Object> mm = new HashMap<String , Object>();
	     mm.put("all", all);//查询点的数据
	     
	     if(visitType.equals("search")){
	    	 //查出对应点的最大最下坐标
		     List<Double> latM = new ArrayList<Double>();
		     List<Double> lngM = new ArrayList<Double>();
		     
		     Map map1 = (Map) m1.get("lat");
		     latM.addAll((List) map1.get("latM"));
		     lngM.addAll((List) map1.get("lngM"));
		     
		     Map map2 = (Map) m2.get("lat");
		     latM.addAll((List) map2.get("latM"));
		     lngM.addAll((List) map2.get("lngM"));
		     
		     Map map3 = (Map) m3.get("lat");
		     latM.addAll((List) map3.get("latM"));
		     lngM.addAll((List) map3.get("lngM"));
		     
		     Map map4 = (Map) m4.get("lat");
		     latM.addAll((List) map4.get("latM"));
		     lngM.addAll((List) map4.get("lngM"));
		     
		     Object[] lat = latM.toArray();
		     Object[] lng = lngM.toArray();
		     Arrays.sort(lat);
		     Arrays.sort(lng);
		     
		     CenterZoom  center = null;
		     if(lat.length>0 && lng.length>0){
		    	 center = new CenterZoom();
			     center.setMax_lat((Double)lat[lat.length-1]);
			     center.setMin_lat((Double)lat[0]);
			     center.setMax_lng((Double)lng[lng.length-1]);
			     center.setMin_lng((Double)lng[0]);
		     }
		     mm.put("center", center);//查询点的最大最小坐标
	     }
		 return mm;
	}
	/**
	 * 设置室内外区间变量VO的参数值
	
	 * @Title: setKpi
	
	 * @Description: TODO
	
	 * @param list
	 * @param map
	 * @param vo
	 * @return
	
	 * @return: Map<String,Object>
	 */
	public Map<String, Object> setKpi(List<Rate> list,Map<String, Object> map,VoBean vo){
		map.put("kpi",vo.getKpi());
		map.put("senceids",vo.getSenceids());
		map.put("startTime",vo.getStartTime());
		map.put("endTime",vo.getEndTime());
		map.put("areaids",vo.getAreaids());
		map.put("datatype",vo.getDatatype());
		map.put("sernos",vo.getSernos());
		map.put("testtype",vo.getTesttype());
		map.put("nettype",vo.getNettype());
		map.put("testnet",vo.getTestnet());
		map.put("inside",vo.getInside());
		map.put("isfirst", vo.getIsFirst());
		map.put("secendSerno", vo.getSecendSerno());
		map.put("minlatregion",vo.getMinlat()==0?-1:vo.getMinlat());
		map.put("maxlatregion",vo.getMaxlat()==0?-1:vo.getMaxlat());
		map.put("minlngregion",vo.getMinlng()==0?-1:vo.getMinlng());
		map.put("maxlngregion",vo.getMaxlng()==0?-1:vo.getMaxlng());
		if(vo.getSecendSerno()!=null&&!vo.getSecendSerno().trim().equals("")){
			String arrss[]=vo.getSecendSerno().replaceAll("，", ",").split(",");
			map.put("arrss", arrss);
		}
		//拆分区域
		if(vo.getAreaids()!=null&&vo.getAreaids().indexOf("-1")>=0){
			map.put("aatype", "-1");
		}
		//拆分场景
		if(vo.getSenceids()!=null&&vo.getSenceids().indexOf("-1")>=0){
			map.put("stype", "-1");
		}
		//拆分业务类型
		
		if(vo.getTesttype()!=null&&vo.getTesttype().indexOf("-1")<0)
		{
			String str[]=vo.getTesttype().split(",");
			List strlist=Arrays.asList(str);
			map.put("bustype", "1");
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
			map.put("tt", tt);
			//长短呼
			String yy=null;
			if(strlist.contains("4")){
				yy=",2";
			}
			if(strlist.contains("5")){
				yy+=",1";			
						}
			//上下行
			map.put("yy", yy);
			String ff=null;
			if(strlist.contains("6")){
				ff=",1";
			}
			if(strlist.contains("7")){
				ff+=",2";			
						}
			map.put("ff", ff);
		}else{
			map.put("bustype", "-1");
		}
		for (int i=0;i<list.size();i++){
			Rate r=list.get(i);
			//室外RSCP
			if(r.getKpi_code()==1&&r.getScene()==0){
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
			//室内RSCP
			if(r.getKpi_code()==1&&r.getScene()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("rscp_s_1_in", r.getRank_arithmetic());
					map.put("rscp_v_1_in", r.getRank_value());
					map.put("rscp_io_1_in", r.getRank_ratio());
					break;
				case 2:
					map.put("rscp_s_2_in", r.getRank_arithmetic());
					map.put("rscp_v_2_in", r.getRank_value());
					map.put("rscp_io_2_in", r.getRank_ratio());
					break;
				case 4:
					map.put("rscp_s_4_in", r.getRank_arithmetic());
					map.put("rscp_v_4_in", r.getRank_value());
					map.put("rscp_io_4_in", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			//室外ECNO
			if(r.getKpi_code()==2&&r.getScene()==0){
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
			//室内ECNO
			if(r.getKpi_code()==2&&r.getScene()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("ecno_s_1_in", r.getRank_arithmetic());
					map.put("ecno_v_1_in", r.getRank_value());
					map.put("ecno_io_1_in", r.getRank_ratio());
					break;
				case 2:
					map.put("ecno_s_2_in", r.getRank_arithmetic());
					map.put("ecno_v_2_in", r.getRank_value());
					map.put("ecno_io_2_in", r.getRank_ratio());
					break;
				
				case 4:
					map.put("ecno_s_4_in", r.getRank_arithmetic());
					map.put("ecno_v_4_in", r.getRank_value());
					map.put("ecno_io_4_in", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			//室外TXPOWER
			if(r.getKpi_code()==3&&r.getScene()==0){
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
			//室内TXPOWER
			if(r.getKpi_code()==3&&r.getScene()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("tx_s_1_in", r.getRank_arithmetic());
					map.put("tx_v_1_in", r.getRank_value());
					map.put("tx_io_1_in", r.getRank_ratio());
					break;
				case 2:
					map.put("tx_s_2_in", r.getRank_arithmetic());
					map.put("tx_v_2_in", r.getRank_value());
					map.put("tx_io_2_in", r.getRank_ratio());
					break;
				
				case 4:
					map.put("tx_s_4_in", r.getRank_arithmetic());
					map.put("tx_v_4_in", r.getRank_value());
					map.put("tx_io_4_in", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			//室外FTP上行
			if(r.getKpi_code()==4&&r.getScene()==0){
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
			//室内FTP上行
			if(r.getKpi_code()==4&&r.getScene()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("fu_s_1_in", r.getRank_arithmetic());
					map.put("fu_v_1_in", r.getRank_value());
					map.put("fu_io_1_in", r.getRank_ratio());
					//map=setAbb(map,r.getRank_avg(),"fu_av",1);
					map.put("fu_av_1_in", r.getRank_avg());
					break;
				case 2:
					map.put("fu_s_2_in", r.getRank_arithmetic());
					map.put("fu_v_2_in", r.getRank_value());
					map.put("fu_io_2_in", r.getRank_ratio());
					map.put("fu_av_2_in", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fu_av",2);
					break;
				
				case 4:
					map.put("fu_s_4_in", r.getRank_arithmetic());
					map.put("fu_v_4_in", r.getRank_value());
					map.put("fu_io_4_in", r.getRank_ratio());
					map.put("fu_av_4_in", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fu_av",4);
					break;
				default:
					break;
				}
			}
			//室外下行
			if(r.getKpi_code()==5&&r.getScene()==0){
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
			//室内下行
			if(r.getKpi_code()==5&&r.getScene()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("fd_s_1_in", r.getRank_arithmetic());
					map.put("fd_v_1_in", r.getRank_value());
					map.put("fd_io_1_in", r.getRank_ratio());
					map.put("fd_av_1_in", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fd_av",1);
					break;
				case 2:
					map.put("fd_s_2_in", r.getRank_arithmetic());
					map.put("fd_v_2_in", r.getRank_value());
					map.put("fd_io_2_in", r.getRank_ratio());
					map.put("fd_av_2_in", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fd_av",2);
					break;
				
				case 4:
					map.put("fd_s_4_in", r.getRank_arithmetic());
					map.put("fd_v_4_in", r.getRank_value());
					map.put("fd_io_4_in", r.getRank_ratio());
					map.put("fd_av_4_in", r.getRank_avg());
					//map=setAbb(map,r.getRank_avg(),"fd_av",4);
					break;
				default:
					break;
				}
			}
			//室外RXLEV
			if(r.getKpi_code()==6&&r.getScene()==0){
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
			//室外RXLEV
			if(r.getKpi_code()==6&&r.getScene()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("rxl_s_1_in", r.getRank_arithmetic());
					map.put("rxl_v_1_in", r.getRank_value());
					map.put("rxl_io_1_in", r.getRank_ratio());
					break;
				case 2:
					map.put("rxl_s_2_in", r.getRank_arithmetic());
					map.put("rxl_v_2_in", r.getRank_value());
					map.put("rxl_io_2_in", r.getRank_ratio());
					break;
				
				case 4:
					map.put("rxl_s_4_in", r.getRank_arithmetic());
					map.put("rxl_v_4_in", r.getRank_value());
					map.put("rxl_io_4_in", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			//室外RXQUAL
			if(r.getKpi_code()==7&&r.getScene()==0){
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
			//室外RXQUAL
			if(r.getKpi_code()==7&&r.getScene()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("rxq_s_1_in", r.getRank_arithmetic());
					map.put("rxq_v_1_in", r.getRank_value());
					map.put("rxq_io_1_in", r.getRank_ratio());
					break;
				case 2:
					map.put("rxq_s_2_in", r.getRank_arithmetic());
					map.put("rxq_v_2_in", r.getRank_value());
					map.put("rxq_io_2_in", r.getRank_ratio());
					break;
				
				case 4:
					map.put("rxq_s_4_in", r.getRank_arithmetic());
					map.put("rxq_v_4_in", r.getRank_value());
					map.put("rxq_io_4_in", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			//室外C/I
			if(r.getKpi_code()==8&&r.getScene()==0){
				switch (r.getRank_code()) {
				case 1:
					map.put("ci_s_1", r.getRank_arithmetic());
					map.put("ci_v_1", r.getRank_value());
					map.put("ci_io_1", r.getRank_ratio());
					break;
				case 2:
					map.put("ci_s_2", r.getRank_arithmetic());
					map.put("ci_v_2", r.getRank_value());
					map.put("ci_io_2", r.getRank_ratio());
					break;
				
				case 4:
					map.put("ci_s_4", r.getRank_arithmetic());
					map.put("ci_v_4", r.getRank_value());
					map.put("ci_io_4", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
			//室内C/I
			if(r.getKpi_code()==8&&r.getScene()==1){
				switch (r.getRank_code()) {
				case 1:
					map.put("ci_s_1_in", r.getRank_arithmetic());
					map.put("ci_v_1_in", r.getRank_value());
					map.put("ci_io_1_in", r.getRank_ratio());
					break;
				case 2:
					map.put("ci_s_2_in", r.getRank_arithmetic());
					map.put("ci_v_2_in", r.getRank_value());
					map.put("ci_io_2_in", r.getRank_ratio());
					break;
				
				case 4:
					map.put("ci_s_4_in", r.getRank_arithmetic());
					map.put("ci_v_4_in", r.getRank_value());
					map.put("ci_io_4_in", r.getRank_ratio());
					break;
				default:
					break;
				}
			}
		}
		return map;
		
	}
	/**
	 * 
	
	 * @Title: getGradeBean
	
	 * @Description: TODO
	
	 * @param map参数
	 * @param list查询出的指标百分比
	 * @param rr指标统计值
	 * @param isfree是否自己模式1-是,0-非
	 * @param er项目评分
	 * @param drop下降项目档
	 * @param kpi选择的路测指标
	 * @param grad查询等级
	 * @return
	
	 * @return: List<GradeBean>
	 */
	public Map<String,Object> getGisgrad(Map map,List<GradeBean> list,List<ScoreRule> rr,
			int isfree,List<EvaluateRule> er,List<RevisRule> drop,String kpi,String grad){

		List<Double> latM = new ArrayList<Double>();
		List<Double> lngM = new ArrayList<Double>();
		DecimalFormat df = new DecimalFormat("0.##");
		List li=new ArrayList();
		Gisgrad gg=null;
		//综合等级数量
		int yy=0,ll=0,zz=0,cc=0;
		int yjia=0,ljia=0,zjia=0,cjia=0;
		int yjian=0,ljian=0,zjian=0,cjian=0;
		//指标等级数量
		int kpiy=0,kpil=0,kpiz=0,kpic=0;
		for (int i=0;i<list.size();i++){
			 Double satte=0.0;//最后得分状态
			//取得每个指标的等级得分
			gg=new Gisgrad();
			GradeBean	wb=list.get(i);

			latM.add(wb.getLat_m());
			lngM.add(wb.getLng_m());
			//装每个指标得分；
			List<Integer> kpilist=new ArrayList<Integer>();
			String ss_1=(String) map.get("rxl_io_1");
			String ss_2=(String) map.get("rxl_io_2");
			String ss_4=(String) map.get("rxl_io_4");
			String ss_1_in=(String) map.get("rxl_io_1_in");
			String ss_2_in=(String) map.get("rxl_io_2_in");
			String ss_4_in=(String) map.get("rxl_io_4_in");
			//判断指标是否测试并且是否选择查询当前指标
			if(wb.getInside().equals("1")){
				ss_1=ss_1_in;
				ss_2=ss_2_in;
				ss_4=ss_4_in;
			}
			if(wb.getRXLEV_Sub_1()!=null&&(kpi.equals("-1")||kpi.equals("6"))){
			    if(findBigSmall(wb.getRXLEV_Sub_4(),ss_1)){
			    	 wb.setRx_g("优");
			    	 kpilist.add(rr.get(0).getRank_score());
			    	 if(!kpi.equals("-1"))kpiy++;
			     }else if (findBigSmall(wb.getRXLEV_Sub_3(),ss_2)){
			    	 wb.setRx_g("良");
			    	 kpilist.add(rr.get(1).getRank_score());
			    	 if(!kpi.equals("-1"))kpil++;
			     }else if (findBigSmall(wb.getRXLEV_Sub_1(),ss_4)){
			    	 wb.setRx_g("差");
			    	 kpilist.add(rr.get(3).getRank_score());
			    	 if(!kpi.equals("-1"))kpic++;
			     }else{
			    	 wb.setRx_g("中");
			    	 kpilist.add(rr.get(2).getRank_score());
			    	 if(!kpi.equals("-1"))kpiz++;
			     }
			}
		     String qq_1=(String) map.get("rxq_io_1");
		     String qq_2=(String) map.get("rxq_io_2");
		     String qq_4=(String) map.get("rxq_io_4");
			String qq_1_in=(String) map.get("rxq_io_1_in");
			String qq_2_in=(String) map.get("rxq_io_2_in");
			String qq_4_in=(String) map.get("rxq_io_4_in");
			//判断指标是否测试并且是否选择查询当前指标
			if(wb.getInside().equals("1")){
				qq_1=qq_1_in;
				qq_2=qq_2_in;
				qq_4=qq_4_in;
			}
		     if(wb.getRXQUAL_Sub_1()!=null&&(kpi.equals("-1")||kpi.equals("7"))){
		    	 //rxqual不加入综合评分计算，所以注释
		    	 if(findBigSmall(wb.getRXQUAL_Sub_4(),qq_1)){
			    	 wb.setRq_g("优");
			    	 //kpilist.add(rr.get(0).getRank_score());
			    	 if(!kpi.equals("-1"))kpiy++;
			     }else if (findBigSmall(wb.getRXQUAL_Sub_3(),qq_2)){
			    	 wb.setRq_g("良");
			    	 //kpilist.add(rr.get(1).getRank_score());
			    	 if(!kpi.equals("-1"))kpil++;
			     }else if (findBigSmall(wb.getRXQUAL_Sub_1(),qq_4)){
			    	 wb.setRq_g("差");
			    	 //kpilist.add(rr.get(3).getRank_score());
			    	 if(!kpi.equals("-1"))kpic++;
			     }else{
				    wb.setRq_g("中");
				   //kpilist.add(rr.get(2).getRank_score());
				    if(!kpi.equals("-1"))kpiz++;
			     }
		     }
		     
		     //  C/I
		    String cc_1=(String) map.get("ci_io_1");
			String cc_2=(String) map.get("ci_io_2");
			String cc_4=(String) map.get("ci_io_4");
			String cc_1_in=(String) map.get("ci_io_1_in");
			String cc_2_in=(String) map.get("ci_io_2_in");
			String cc_4_in=(String) map.get("ci_io_4_in");
			//判断指标是否测试并且是否选择查询当前指标
			if(wb.getInside().equals("1")){
				cc_1=cc_1_in;
				cc_2=cc_2_in;
				cc_4=cc_4_in;
			}
			if(wb.getCi_1()!=null&&(kpi.equals("-1")||kpi.equals("8"))){
			    if(findBigSmall(wb.getCi_4(),cc_1)){
			    	 wb.setCi_g("优");
			    	 kpilist.add(rr.get(0).getRank_score());
			    	 if(!kpi.equals("-1"))kpiy++;
			     }else if (findBigSmall(wb.getCi_3(),cc_2)){
			    	 wb.setCi_g("良");
			    	 kpilist.add(rr.get(1).getRank_score());
			    	 if(!kpi.equals("-1"))kpil++;
			     }else if (findBigSmall(wb.getCi_1(),cc_4)){
			    	 wb.setCi_g("差");
			    	 kpilist.add(rr.get(3).getRank_score());
			    	 if(!kpi.equals("-1"))kpic++;
			     }else{
			    	 wb.setCi_g("中");
			    	 kpilist.add(rr.get(2).getRank_score());
			    	 if(!kpi.equals("-1"))kpiz++;
			     }
			}
				
				
			 String rr_1=(String) map.get("rscp_io_1");
        	 String rr_2=(String) map.get("rscp_io_2");
        	 String rr_4=(String) map.get("rscp_io_4");
        	 String rr_1_in=(String) map.get("rscp_io_1_in");
        	 String rr_2_in=(String) map.get("rscp_io_2_in");
        	 String rr_4_in=(String) map.get("rscp_io_4_in");
        	 if(wb.getInside().equals("1")){
 				rr_1=rr_1_in;
 				rr_2=rr_2_in;
 				rr_4=rr_4_in;
 			}
        	 if(wb.getRSCP_4()!=null&&(kpi.equals("-1")||kpi.equals("1"))){
    		     if(findBigSmall(wb.getRSCP_4(),rr_1)){
    		    	 wb.setRSCP_g("优");
    		    	 kpilist.add(rr.get(0).getRank_score());
    		    	 if(!kpi.equals("-1"))kpiy++;
    		     }else if (findBigSmall(wb.getRSCP_3(),rr_2)){
    		    	 wb.setRSCP_g("良");
    		    	 kpilist.add(rr.get(1).getRank_score());
    		    	 if(!kpi.equals("-1"))kpil++;
    		     }else if (findBigSmall(wb.getRSCP_1(),rr_4)){
    		    	 wb.setRSCP_g("差");
    		    	 kpilist.add(rr.get(3).getRank_score());
    		    	 if(!kpi.equals("-1"))kpic++;
    		     }else{
    		    	 wb.setRSCP_g("中");
    		    	 kpilist.add(rr.get(2).getRank_score());
    		    	 if(!kpi.equals("-1"))kpiz++;
    		     }
        	 }
		     String ee_1=(String) map.get("ecno_io_1");
        	 String ee_2=(String) map.get("ecno_io_2");
        	 String ee_4=(String) map.get("ecno_io_4");
        	 String ee_1_in=(String) map.get("ecno_io_1_in");
        	 String ee_2_in=(String) map.get("ecno_io_2_in");
        	 String ee_4_in=(String) map.get("ecno_io_4_in");
        	 if(wb.getInside().equals("1")){
  				ee_1=ee_1_in;
  				ee_2=ee_2_in;
  				ee_4=ee_4_in;
  			}
        	 if(wb.getEC_NO_4()!=null&&(kpi.equals("-1")||kpi.equals("2"))){
        		  if(findBigSmall(wb.getEC_NO_4(),ee_1)){
     		    	 wb.setECNO_g("优");
     		    	 kpilist.add(rr.get(0).getRank_score());
     		    	 if(!kpi.equals("-1"))kpiy++;
     		     }else if (findBigSmall(wb.getEC_NO_3(),ee_2)){
     		    	 wb.setECNO_g("良");
     		    	kpilist.add(rr.get(1).getRank_score());
    		    	 if(!kpi.equals("-1"))kpil++;
     		     }else if (findBigSmall(wb.getEC_NO_1(),ee_4)){
     		    	 wb.setECNO_g("差");
     		    	kpilist.add(rr.get(3).getRank_score());
    		    	 if(!kpi.equals("-1"))kpic++;
     		     }else{
     		    	 wb.setECNO_g("中");
     		    	kpilist.add(rr.get(2).getRank_score());
    		    	 if(!kpi.equals("-1"))kpiz++;
     		     }
        	 }
		     String tx_1=(String) map.get("tx_io_1");
        	 String tx_2=(String) map.get("tx_io_2");
        	 String tx_4=(String) map.get("tx_io_4");
        	 String tx_1_in=(String) map.get("tx_io_1_in");
          	 String tx_2_in=(String) map.get("tx_io_2_in");
          	 String tx_4_in=(String) map.get("tx_io_4_in");
          	 if(wb.getInside().equals("1")){
   				tx_1=tx_1_in;
   				tx_2=tx_2_in;
   				tx_4=tx_4_in;
   			}
        	 if(wb.getTxpower_4()!=null&&(kpi.equals("-1")||kpi.equals("3"))){
        		   if(findBigSmall(wb.getTxpower_4(),tx_1)){
      		    	 wb.setTxpower_g("优");
      		    	 kpilist.add(rr.get(0).getRank_score());
      		    	 if(!kpi.equals("-1"))kpiy++;
      		     }else if (findBigSmall(wb.getTxpower_3(),tx_2)){
      		    	 wb.setTxpower_g("良");
      		    	 if(!kpi.equals("-1"))kpil++;
      		    	 kpilist.add(rr.get(1).getRank_score());
      		     }else if (findBigSmall(wb.getTxpower_1(),tx_4)){
      		    	 wb.setTxpower_g("差");
      		    	 if(!kpi.equals("-1"))kpic++;
      		    	 kpilist.add(rr.get(3).getRank_score());
      		     }else{
      		    	 wb.setTxpower_g("中");
      		    	 kpilist.add(rr.get(2).getRank_score());
      		    	 if(!kpi.equals("-1"))kpiz++;
      		     }
        	 }
		     
		     String fu_1=(String) map.get("fu_io_1");
        	 String fu_2=(String) map.get("fu_io_2");
        	 String fu_4=(String) map.get("fu_io_4");
        	 String fu_1_in=(String) map.get("fu_io_1_in");
        	 String fu_2_in=(String) map.get("fu_io_2_in");
        	 String fu_4_in=(String) map.get("fu_io_4_in");
        	 if(wb.getInside().equals("1")){
    				fu_1=fu_1_in;
    				fu_2=fu_2_in;
    				fu_4=fu_4_in;
    			}
        	 if(wb.getFTP_SPEED_UP_4()!=null&&wb.getFtp_avg_speed()!=null&&(kpi.equals("-1")||kpi.equals("4"))){
        	     if(findBigSmall(wb.getFTP_SPEED_UP_4(),fu_1)&&findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fu_av_1"))){
    		    	 wb.setFu_g("优");
    		    	 kpilist.add(rr.get(0).getRank_score());
    		    	 if(!kpi.equals("-1"))kpiy++;
    		     }else if (findBigSmall(wb.getFTP_SPEED_UP_3(),fu_2)&&findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fu_av_2"))){
    		    	 wb.setFu_g("良");
    		    	 kpilist.add(rr.get(1).getRank_score());
    		    	 if(!kpi.equals("-1"))kpil++;
    		     }else if (findBigSmall(wb.getFTP_SPEED_UP_1(),fu_4)||findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fu_av_4"))){
    		    	 wb.setFu_g("差");
    		    	 kpilist.add(rr.get(3).getRank_score());
    		    	 if(!kpi.equals("-1"))kpic++;
    		     }else{
    		    	 wb.setFu_g("中");
    		    	 kpilist.add(rr.get(2).getRank_score());
    		    	 if(!kpi.equals("-1"))kpiz++;
    		     }
        	 }
		     String fd_1=(String) map.get("fd_io_1");
        	 String fd_2=(String) map.get("fd_io_2");
        	 String fd_4=(String) map.get("fd_io_4");
        	 String fd_1_in=(String) map.get("fd_io_1_in");
        	 String fd_2_in=(String) map.get("fd_io_2_in");
        	 String fd_4_in=(String) map.get("fd_io_4_in");
        	 if(wb.getInside().equals("1")){
 				fd_1=fd_1_in;
 				fd_2=fd_2_in;
 				fd_4=fd_4_in;
 			}
        	 if(wb.getFTP_SPEED_DOWN_4()!=null&&wb.getFtp_avg_speed()!=null&&(kpi.equals("-1")||kpi.equals("5")))
        	 {
        		   if(findBigSmall(wb.getFTP_SPEED_DOWN_4(),fd_1)&&findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fd_av_1"))){
      		    	 wb.setFd_g("优");
      		    	 kpilist.add(rr.get(0).getRank_score());
      		    	 if(!kpi.equals("-1"))kpiy++;
      		     }else if (findBigSmall(wb.getFTP_SPEED_DOWN_3(),fd_2)&&findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fd_av_2"))){
      		    	 wb.setFd_g("良");
      		    	 kpilist.add(rr.get(1).getRank_score());
      		    	 if(!kpi.equals("-1"))kpil++;
      		     }else if (findBigSmall(wb.getFTP_SPEED_DOWN_1(),fd_4)||findBigSmall(wb.getFtp_avg_speed(),(String) map.get("fd_av_4"))){
      		    	 wb.setFd_g("差");
      		    	 kpilist.add(rr.get(3).getRank_score());
      		    	 if(!kpi.equals("-1"))kpic++;
      		     }else{
      		    	 wb.setFd_g("中");
      		    	 kpilist.add(rr.get(2).getRank_score());
      		    	 if(!kpi.equals("-1"))kpiz++;
      		     }
        	 }
        	 
        	 //计算等级项目得分
        	 //进行讲挡计算（自由模式与非自己模式）
        	
    		 for(int j=0;j<kpilist.size();j++){
    			 satte+=kpilist.get(j);
    		 }
    		 if(kpilist.size()>0){
        		 satte=Math.ceil(satte/kpilist.size());
    		 }
        	 if(wb.getNettype().equals("3")){
        		 //自由模式降档
        		 for(int k=2;k<drop.size();k++){
        			 String ss="["+drop.get(k).getLeft_rate()+","+drop.get(k).getRight_rate()+"]";
        			 //在降档敬意内并且降档等级大于0
        			 if(findBigSmall(wb.getFree3(),ss)==true&&drop.get(k).getRevis_level()>0){
        				 for(int h=0;h<er.size();h++){
        	            		//优良降档
        		           		 if(satte==er.get(h).getRank_score()){
        		           			 if(h+drop.get(k).getRevis_level()<er.size()){
        		               			 satte=(double) er.get(h+drop.get(k).getRevis_level()).getRank_score();
        		           			 }else{
        		           				satte=(double) er.get(er.size()-1).getRank_score();
        		           			 }
        		           			break;
        		           		 }
        	        		}
        				 break;
        			 }
        		 }
        	 }else{
        		 //非自由模式
        		 //判断状态是否是优+-、或良+-并且是否有一荐指标为差
        			//项目优的封装
        		 int cha=rr.get(rr.size()-1).getRank_score();
        		for(int h=0;h<er.size();h++){
            		//优良降档
	           		 if(satte==er.get(h).getRank_score()&&(er.get(h).getRank_code()==1||er.get(h).getRank_code()==2)&&kpilist.contains(cha)){
	           			 if(h+drop.get(er.get(h).getRank_code()-1).getRevis_level()<er.size()){
	               			 satte=(double) er.get(h+drop.get(er.get(h).getRank_code()-1).getRevis_level()).getRank_score();
	           			 }else{
	           				satte=(double) er.get(er.size()-1).getRank_score();
	           			 }
	           			 break;
	           		 }
        		}
        	 }
        	 gg.setLat_m(wb.getLat_m());
        	 gg.setLng_m(wb.getLng_m());
        	 gg.setSer(wb.getSerialno());
        	 gg.setAdd(wb.getProblemsAddress());
        	 gg.setRscp(wb.getRSCP_g());
        	 gg.setEcno(wb.getECNO_g());
        	 gg.setTx(wb.getTxpower_g());
        	 gg.setFu(wb.getFu_g());
        	 gg.setFd(wb.getFd_g());
        	 gg.setRx(wb.getRx_g());
        	 gg.setRq(wb.getRq_g());
        	 gg.setCi(wb.getCi_g());
        	 gg.setNet(wb.getNettype());
        	 gg.setInside(wb.getInside());
        	 gg.setIsf(wb.getIsf()+"");
        	 gg.setTitle(this.getTitle(wb));
        	 gg.setArea(wb.getArea());
        	 gg.setSence(wb.getSence());
        	 gg.setTime(String.valueOf(wb.getTime()));
        	 gg.setJtime(String.valueOf(wb.getJtime()));
        	 gg.setType(wb.getTest_type()+"");
        	 gg.setPath(wb.getPath());
        	 gg.setFlowid(wb.getFlowid());
        	 gg.setPocent3(df.format(wb.getFree3()));
        	 gg.setPocent2(df.format(wb.getPocent2()));
        	 gg.setTalkaround(df.format(wb.getTalkaround()));
        	 gg.setSumc(wb.getSumc());
        	 gg.setNetwork(wb.getNet_worktype());
        	 gg.setLat(wb.getLat_y());
        	 gg.setLng(wb.getLng_y());
        	 gg.setCall_type(wb.getCall_type());
        	 gg.setFtp_type(wb.getFtp_type());
        	 gg.setPhone(wb.getPhone());
        	 EvaluateRule ek=null;
        	 for (int h=0;h<er.size();h++){
        		 ek=er.get(h);
        		 if(ek.getRank_score()==satte){
        			 break;
        		 }
        	 }
        	 if(ek==null){
        		 gg.setColor("差");//设置等级分值
        		 gg.setRealgrad("差-");
        	 }else{
        		 switch (ek.getRank_code()) {
				case 1:
					gg.setColor("优");
					if(ek.getRank_code_sub()==1){
					gg.setRealgrad("优+");
					yjia++;
					}else if(ek.getRank_code_sub()==3){
					gg.setRealgrad("优-");
					yjian++;
					}else{
					gg.setRealgrad("优");
					yy++;
					}
					if(grad.equals("1")){li.add(gg);} 
					
					break;
                case 2:
                	gg.setColor("良");
                	if(ek.getRank_code_sub()==1){
    					gg.setRealgrad("良+");
    					ljia++;
    					}else if(ek.getRank_code_sub()==3){
    					gg.setRealgrad("良-");
    					ljian++;
    					}else{
    					gg.setRealgrad("良");
    					ll++;
    					}
                	if(grad.equals("2")){li.add(gg);} 
                	
					break;	
                case 3:
                	gg.setColor("中");
                	if(ek.getRank_code_sub()==1){
    					gg.setRealgrad("中+");
    					zjia++;
    					}else if(ek.getRank_code_sub()==3){
    					gg.setRealgrad("中-");
    					zjian++;
    					}else{
    					gg.setRealgrad("中");
    					zz++;
    					}
                	if(grad.equals("3")){li.add(gg);}
                	
					break;
                case 4:
                	 gg.setColor("差");
                	if(ek.getRank_code_sub()==1){
    					gg.setRealgrad("差+");
    					cjia++;
    					}else if(ek.getRank_code_sub()==3){
    					gg.setRealgrad("差-");
    					cjian++;
    					}else{
    					gg.setRealgrad("差");
    					cc++;
    					}
                	if(grad.equals("4")){li.add(gg);} 
                	
					break;

				default:
					break;
				}
        	 }
            if(grad.equals("-1")){li.add(gg);} 
		}
		 List<Integer> ylzc=new ArrayList<Integer>();
		
		 if(!kpi.equals("-1")){
			 if(grad.equals("-1")||grad.equals("1"))ylzc.add(kpiy);
			 if(grad.equals("-1")||grad.equals("2"))ylzc.add(kpil);
			 if(grad.equals("-1")||grad.equals("3"))ylzc.add(kpiz);
			 if(grad.equals("-1")||grad.equals("4"))ylzc.add(kpic);
		 }else{
			 if(grad.equals("-1")||grad.equals("1")){
				 ylzc.add(yjia);ylzc.add(yy);ylzc.add(yjian);
			 }
			 if(grad.equals("2")||grad.equals("-1")){
				 ylzc.add(ljia);ylzc.add(ll);ylzc.add(ljian);
			 }
			 if(grad.equals("3")||grad.equals("-1")){
				 ylzc.add(zjia);ylzc.add(zz);ylzc.add(zjian);
			 }
			 if(grad.equals("4")||grad.equals("-1")){
				 ylzc.add(cjia);ylzc.add(cc);ylzc.add(cjian);
			 }
		 }
		//li.add(s);
		 Map<List<Integer>, List<Gisgrad>> mm = new HashMap<List<Integer>, List<Gisgrad>>();
		 mm.put(ylzc, li);
		 
		 Map<String,List<Double>> ap = new HashMap<String,List<Double>>();
		 ap.put("lngM",lngM);
		 ap.put("latM",latM);
		 
		 Map<String,Object> rm = new HashMap<String,Object>();
		 rm.put("mm", mm);
		 rm.put("lat", ap);
		return rm;
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
		if(str!=null&&value!=null){
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
	 * 查询等级颜色
	
	 * @Title: showColor
	
	 * @Description: TODO
	
	 * @return
	
	 * @return: List<RateColor>
	 */
public List<RateColor> showColor(){
	List<RateColor> colorlist=this.gisgradMapper.showGradColor();
	return colorlist;
}
public String getTitle(GradeBean wb){
	String temp="";
	if (wb.getNettype() != null) {
		String netstr = "";
		switch (Integer.parseInt(wb.getNettype())) {
		case 1:
			netstr = "GSM";
			break;
		case 2:
			netstr = "WCDMA_锁频";
			break;
		case 3:
			netstr = "WCDMA_自由模式";
			break;
		case 4:
			netstr = "WCDMA_自由模式";
			break;
		default:
			break;
		}
		temp += netstr;
	}
	if (wb.getTest_type()!= 0) {
		String teststr = "";
		switch (wb.getTest_type()) {
		case 1:
			teststr = "_IDLE";
			break;
		case 2:
			teststr = "_CS";
			break;
		case 3:
			teststr = "_PS";
			break;
		default:
			break;
		}
		temp += teststr;
	}
	if (wb.getCall_type() != 0) {
		String callstr = "";
		if (wb.getCall_type() == 1) {
			callstr = "_短呼";
		} else if (wb.getCall_type() == 2) {
			callstr = "_长呼";
		} else {
		}
		temp += callstr;
	}
	if (wb.getFtp_type()!=0) {
		String ftpstr = "";
		if (wb.getFtp_type() == 1) {
			ftpstr = "_上行";
		} else if (wb.getFtp_type()== 2) {
			ftpstr = "_下行";
		} else {
		}
		temp += ftpstr;
	}
	return temp;
}
/***
 * 查询中心经纬度

 * @Title: queryCenter

 * @Description: TODO

 * @return

 * @return: CenterZoom
 */
	public CenterZoom  queryCenter(VoBean vo){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Rate> listr=this.gisgradMapper.showGradKpi();
		map=this.setKpi(listr, map, vo);
		CenterZoom center=this.gisgradMapper.queryCenter(map);
//		if(center!=null){
//			map.put("maxlat", center.getMax_lat());
//			map.put("minlat", center.getMin_lat());
//			map.put("maxlng", center.getMax_lng());
//			map.put("minlng", center.getMin_lng());
//			CenterZoom dd=this.gisgradMapper.querydistence(map);
//			center.setDistance(dd.getDistance());
//		}
		return center;
	}
	
	/**
     * 
    
     * @Title: queryCells
    
     * @Description: 点击GIS点根据流水号查询测试连接的所有小区 
    
     * @param map
     * @return
    
     * @return: List<TCasCell>
     */
    public List<TCasCell> queryCells(String flowid,String areaids){
    	Map<String, Object> map = new HashMap<String, Object>();
    	Sysconfig sysconfig = new Sysconfig();
		try {
			sysconfig = this.sysConfigDao.getAngleconfig("cell_angle_config");
		} catch (Exception e) {
			logger.error("cell_angle_config",e);
		}
    	String[] str = sysconfig.getConfigvalue().split("=");
    	String type = str[1].substring(0,1);
    	map.put("type", type);
    	map.put("flowid", flowid);
    	map.put("areaids", areaids);
    	return this.gisgradMapper.queryCells(map);
    }
    /**
     * 查询角度配置
     * @return
     */
    public Map<String ,String> getAngleconfig(){
    	Sysconfig sysconfig = new Sysconfig();
		try {
			sysconfig = this.sysConfigDao.getAngleconfig("cell_angle_config");
		} catch (Exception e) {
			logger.error("cell_angle_config",e);
		}
    	String[] str = sysconfig.getConfigvalue().split("=");
    	String type = str[1].substring(0,1);
    	String angle = str[2];
    	Map<String ,String> map = new HashMap<String ,String>();
    	map.put("type", type);
    	map.put("angle", angle);
    	return map;
    }
    /**
     * 保存角度配置
     */
    public Integer saveAngleconfig(String type ,String angle){
    	String configvalue = "type="+type+"|angle="+angle;
    	try {
    		Map map = new HashMap();
    		map.put("configvalue", configvalue);
    		map.put("configkey", "cell_angle_config");
			this.sysConfigDao.saveAngleconfig(map);
		} catch (Exception e) {
			logger.error("save angleconfig",e);
			return 2;
		}
    	return 1;
    }
}
