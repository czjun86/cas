package com.complaint.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.action.vo.CellVo;
import com.complaint.dao.BaseStationDao;
import com.complaint.dao.SysConfigDao;
import com.complaint.model.BaseStation;
import com.complaint.model.CenterZoom;
import com.complaint.model.GwCasCell;
import com.complaint.model.Sysconfig;
import com.complaint.model.TCasCell;
import com.complaint.model.ReportCells;
import com.complaint.utils.Constant;

@Service("baseStationService")
public class BaseStationService {
	@Autowired
	private GisgradExcelDownLoadService gisgradExcelDownLoadService;
	@Autowired
	private BaseStationDao baseStationDao;
	@Autowired
	private SysConfigDao sysConfigDao;
	private static final Logger logger = LoggerFactory
			.getLogger(BaseStationService.class);

	public List<BaseStation> getAllCell(CellVo vo) {
		Sysconfig sysconfig = new Sysconfig();
		try {
			sysconfig = this.sysConfigDao.getAngleconfig("cell_angle_config");
		} catch (Exception e) {
			logger.error("cell_angle_config", e);
		}
		String[] str = sysconfig.getConfigvalue().split("=");
		String type = str[1].substring(0, 1);
		String angle = str[2];
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("areaids", vo.getAreas());
		param.put("modetypes", vo.getModetypes());
		param.put("cellBands", vo.getCellBands());
		param.put("indoor", vo.getIndoor());
		List<BaseStation> bsList = baseStationDao.queryBaseStationData(param);
		return bsList;
	}

	public CenterZoom getCenter(String areaids) {
		Map<String, Object> param = new HashMap<String, Object>();
		Integer areaid = Integer.parseInt(areaids.split(",")[0]);
		param.put("areaid", areaid);
		return baseStationDao.queryCenter(param);
	}

	public BaseStation getBaseStationById(Integer bid) {
		Sysconfig sysconfig = new Sysconfig();
		try {
			sysconfig = this.sysConfigDao.getAngleconfig("cell_angle_config");
		} catch (Exception e) {
			logger.error("cell_angle_config", e);
		}
		String[] str = sysconfig.getConfigvalue().split("=");
		String type = str[1].substring(0, 1);
		return baseStationDao.queryBaseStationById(type, bid);
	}

	public Map<String, Object> getNearCell(CellVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lac", Integer.parseInt(vo.getLac_cid().split("_")[0]));
		param.put("cid", Integer.parseInt(vo.getLac_cid().split("_")[1]));
		param.put("areaids", vo.getAreas());
		param.put("indoor", vo.getIndoor());
		param.put("modetypes", vo.getModetypes());
		param.put("cellBands", vo.getCellBands());
		param.put("lac_cid", vo.getLac_cid());
		List<TCasCell> list = new ArrayList<TCasCell>();
		TCasCell tccell = baseStationDao.queryCell(param);
		List<TCasCell> nearlist = baseStationDao.queryNearCell(param);
		List<TCasCell> otherlist = baseStationDao.queryNearCellOther(param);
		for (TCasCell tcc : nearlist) {
			tcc.setNeartype(1);
			// 判断网络
			if (tccell.getModetype() == tcc.getModetype()) {
				// 3G根据上下行频点判断同频、异频
				if (tccell.getModetype() == 0) {
					if (tccell.getUpfreq() != null
							&& tccell.getDownfreq() != null
							&& tccell.getUpfreq().equals(tcc.getUpfreq())
							&& tccell.getDownfreq().equals(tcc.getDownfreq())) {
						// 同频
						tcc.setNearrel(1);
					} else {
						// 异频
						tcc.setNearrel(2);
					}
				} else {
					//2G根据频段判断同频、异频
					if (tccell.getCellBand() != null
							&& tccell.getCellBand().equals(tcc.getCellBand())) {
						// 同频
						tcc.setNearrel(1);
					}else {
						// 异频
						tcc.setNearrel(2);
					}
				}
			} else {
				// 异网络
				tcc.setNearrel(3);
			}
			list.add(tcc);
		}
		for (TCasCell tccother : otherlist) {
			boolean flag = false;
			for (TCasCell tcc : list) {
				// 判断是否是双向
				if (tccother.getLac().equals(tcc.getLac())
						&& tccother.getCellId().equals(tcc.getCellId())) {
					tcc.setNeartype(3);
					flag = true;
					break;
				}
			}
			if (!flag) {
				tccother.setNeartype(2);
				if (tccell.getModetype() == tccother.getModetype()) {
					// 3G根据上下行频点判断同频、异频
					if (tccell.getModetype() == 0) {
						if (tccell.getUpfreq() != null
								&& tccell.getDownfreq() != null
								&& tccell.getUpfreq().equals(tccother.getUpfreq())
								&& tccell.getDownfreq().equals(tccother.getDownfreq())) {
							// 同频
							tccother.setNearrel(1);
						} else {
							// 异频
							tccother.setNearrel(2);
						}
					} else {
						//2G根据频段判断同频、异频
						if (tccell.getCellBand() != null
								&& tccell.getCellBand().equals(tccother.getCellBand())) {
							// 同频
							tccother.setNearrel(1);
						}else {
							// 异频
							tccother.setNearrel(2);
						}
					}
				} else {
					tccother.setNearrel(3);
				}
				list.add(tccother);
			}
		}
		map.put("list", list);
		map.put("tcc", tccell);
		return map;
	}

	/***
	 * 
	 * 
	 * @Title: queryCellById
	 * 
	 * @Description: 根据CID、LAC查询显示该小区信息
	 * 
	 * @param lac
	 * @param cid
	 * @return
	 * 
	 * @return: List<TCasCell>
	 */
	public TCasCell queryCellById(Long lac, Long cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lac", lac);
		map.put("cid", cid);
		TCasCell cell = this.baseStationDao.queryCellById(map);
		return cell;

	}
	
	/***
	 * 
	 * 
	 * @Title: queryCellByIdOriginal
	 * 
	 * @Description: 根据CID、LAC查询显示该小区信息，获取经纬度为原始经纬度
	 * 
	 * @param lac
	 * @param cid
	 * @return
	 * 
	 * @return: List<TCasCell>
	 */
	public TCasCell queryCellByIdOriginal(Long lac, Long cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lac", lac);
		map.put("cid", cid);
		TCasCell cell = this.baseStationDao.queryCellByIdOriginal(map);
		return cell;

	}

	/**
	 * query cell position
	 * 
	 * @param @param params
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> queryAreaPosition(
			Map<String, Object> params) {
		return this.baseStationDao.queryAreaPosition(params);
	}

	/**
	 * @Title: setAreaCenter
	 * @param @param areaid
	 * @param @param bigLat
	 * @param @param biglng
	 * @return void 返回类型
	 * @throws
	 */
	public void setAreaCenter(String areaid, BigDecimal bigLat,
			BigDecimal biglng) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaid", Integer.valueOf(areaid));
		params.put("longitude", bigLat);
		params.put("latitude", biglng);
		this.baseStationDao.setAreaCenter(params);
	}
	/**
	 *  导出小区信息
	 * @param areaids
	 * @return
	 */
	public List<GwCasCell> getRegiondown(CellVo vo,String filePath,Integer page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaids", vo.getAreas());
		params.put("modetypes", vo.getModetypes());
		params.put("cellBands", vo.getCellBands());
		params.put("indoor", vo.getIndoor());
		params.put("lbound", Constant.READ_ROW*page);
		params.put("mbound", Constant.READ_ROW*(page+1));
		List<GwCasCell> list = new ArrayList<GwCasCell>();
		list = this.baseStationDao.queryRegiondown(params);
		return list;
	}
	/**
	 * 导出点击小区
	 * @param vo
	 * @return
	 */
	public List<GwCasCell> getReportNearCell(CellVo vo){
		Long lac = Long.parseLong(vo.getLac_cid().split("_")[0]);
		Long cid = Long.parseLong(vo.getLac_cid().split("_")[1]);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lac", lac);
		param.put("cid", cid);
		param.put("lac_cid", vo.getLac_cid());
		param.put("areaids", vo.getAreas());
		param.put("modetypes", vo.getModetypes());
		param.put("cellBands", vo.getCellBands());
		param.put("indoor", vo.getIndoor());
		//查询正向邻区
		List<GwCasCell> nearlist = this.baseStationDao.queryReportNearCell(param);
		//查询反向邻区
		List<GwCasCell> otherlist = this.baseStationDao.queryReportNearCellOther(param);
		//查询当当前的邻区
		GwCasCell tccell = this.baseStationDao.queryReportCellById(param);
		List<GwCasCell> list = new ArrayList<GwCasCell>();
		tccell.setColor_type("0");
		tccell.setColor("#FF0000");
		String color1 ="#00c321";//同频颜色
		String color2 ="#1499da";//异频颜色
		String color3 ="#ffa200";//异网颜色
		List<GwCasCell> list1 = new ArrayList<GwCasCell>();//同频集合
		List<GwCasCell> list2 = new ArrayList<GwCasCell>();//异频集合
		List<GwCasCell> list3 = new ArrayList<GwCasCell>();//异网络集合
		list.add(tccell);
		for (GwCasCell tcc : nearlist) {
			// 判断网络
			if (tccell.getModeType() == tcc.getModeType()) {
				// 3G根据上下行频点判断同频、异频
				if (tccell.getModeType() == 0) {
					if (tccell.getUpFreq() != null
							&& tccell.getDownFreq() != null
							&& tccell.getUpFreq().equals(tcc.getUpFreq())
							&& tccell.getDownFreq().equals(tcc.getDownFreq())) {
						// 同频
						if (vo.getCellrel().indexOf("0") >= 0) {
							tcc.setColor_type("1");
							tcc.setColor(color1);
							list.add(tcc);
							list1.add(tcc);
						}
					} else {
						// 异频
						if (vo.getCellrel().indexOf("1") >= 0) {
							tcc.setColor_type("2");
							tcc.setColor(color2);
							list.add(tcc);
							list2.add(tcc);
						}
						// tcc.setNearrel(2);
					}
				} else {
					// 2G根据频段判断同频、异频
					if (tccell.getCellBand() != null
							&& tccell.getCellBand().equals(tcc.getCellBand())) {
						// 同频
						if (vo.getCellrel().indexOf("0") >= 0) {
							tcc.setColor_type("1");
							tcc.setColor(color1);
							list.add(tcc);
							list1.add(tcc);
						}
					} else {
						// 异频
						if (vo.getCellrel().indexOf("1") >= 0) {
							tcc.setColor_type("2");
							tcc.setColor(color2);
							list.add(tcc);
							list2.add(tcc);
						}
					}
				}
			} else {
				// 异网络
				if (vo.getCellrel().indexOf("2") >= 0) {
					tcc.setColor_type("3");
					tcc.setColor(color3);
					list.add(tcc);
					list3.add(tcc);
				}
			}
		}
		for (GwCasCell tccother : otherlist) {
			boolean flag = false;
			for (GwCasCell tcc : list) {
				// 判断是否是双向
				if (tccother.getLac().equals(tcc.getLac())
						&& tccother.getCid().equals(tcc.getCid())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				if (tccell.getModeType() == tccother.getModeType()) {
					// 3G根据上下行频点判断同频、异频
					if (tccell.getModeType() == 0) {
						if (tccell.getUpFreq() != null
								&& tccell.getDownFreq() != null
								&& tccell.getUpFreq().equals(
										tccother.getUpFreq())
								&& tccell.getDownFreq().equals(
										tccother.getDownFreq())) {
							// 同频
							// tccother.setNearrel(1);
							if (vo.getCellrel().indexOf("0") >= 0) {
								tccother.setColor_type("1");
								tccother.setColor(color1);
								list1.add(tccother);
							}
						} else {
							// 异频
							if (vo.getCellrel().indexOf("1") >= 0) {
								tccother.setColor_type("2");
								tccother.setColor(color2);
								list2.add(tccother);
							}
						}
					} else {
						// 2G根据频段判断同频、异频
						if (tccell.getCellBand() != null
								&& tccell.getCellBand().equals(
										tccother.getCellBand())) {
							// 同频
							if (vo.getCellrel().indexOf("0") >= 0) {
								tccother.setColor_type("1");
								tccother.setColor(color1);
								list1.add(tccother);
							}
						} else {
							// 异频
							if (vo.getCellrel().indexOf("1") >= 0) {
								tccother.setColor_type("2");
								tccother.setColor(color2);
								list2.add(tccother);
							}
						}
					}
				} else {
					if (vo.getCellrel().indexOf("2") >= 0) {
						tccother.setColor_type("3");
						tccother.setColor(color3);
						list3.add(tccother);
					}
				}
			}
		}
		list = new ArrayList<GwCasCell>();
		list.add(tccell);
		list.addAll(list1);
		list.addAll(list2);
		list.addAll(list3);
		return list;
	}
	/**
	 * 框选导出
	 * @param vo
	 * @return
	 */
	public List<GwCasCell> getRegiondownCell(CellVo vo ,String filePath ,Integer page){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("areaids",vo.getAreas());
		param.put("modetypes", vo.getModetypes());
		param.put("cellBands", vo.getCellBands());
		param.put("maxlat", vo.getMaxlat());
		param.put("minlat", vo.getMinlat());
		param.put("minlng", vo.getMinlng());
		param.put("maxlng", vo.getMaxlng());
		param.put("indoor", vo.getIndoor());
		param.put("lbound", Constant.READ_ROW*page);
		param.put("mbound", Constant.READ_ROW*(page+1));
		List<GwCasCell> list = new ArrayList<GwCasCell>();
		list = this.baseStationDao.queryRegiondown(param);
		/*List<GwCasCell> nearl = new ArrayList<GwCasCell>();
		if(vo.getLac_cid()!=null && vo.getCellrel()!=null){//获取不再当前查询区域的邻区
			nearl = this.getRegionNear(vo.getLac_cid(), vo.getCellrel(), vo.getAreas(),vo.getMaxlat(),vo.getMinlat(),vo.getMaxlng(),vo.getMinlng());
		}
		if(nearl.size()>0){//如果有邻区就加入集合
			list.addAll(nearl);
		}*/
		return list;
	}
	/**
	 * 框选邻区排除当前areaid
	 
	public List<GwCasCell> getRegionNear(String lac_cid ,String cellrel ,String areaid ,double maxlat ,double minlat ,double maxlng ,double minlng){
		String[] areas = areaid.split(",");//多个区域需要分割
		List<GwCasCell> near =  this.getReportNearCell(lac_cid ,cellrel);
		Iterator<GwCasCell> iter = near.iterator();  
		while(iter.hasNext()){
			GwCasCell gcc = iter.next();
			
			boolean flag = false;//判断是否已经移除
			for(String ar:areas){//如果在查询区域内就移除
				if(String.valueOf(gcc.getAreaId()).equals(ar)){   
				    iter.remove();
				    flag =true;
				    break;
				}
			}
			if(flag){//若果已经移除，则跳出当前
				continue;
			}
			if(gcc.getLatitudeModify().compareTo(BigDecimal.valueOf(minlat))==-1||gcc.getLatitudeModify().compareTo(BigDecimal.valueOf(maxlat))==1){
				//纬度不再框选内的移除
				iter.remove();
				continue;
			}
			if(gcc.getLongitudeModify().compareTo(BigDecimal.valueOf(minlng))==-1||gcc.getLongitudeModify().compareTo(BigDecimal.valueOf(maxlng))==1){
				//经度不再框选内的移除
				iter.remove();
				continue;
			}
		}
		return near;
	}*/
	/**
	 * 需要查询的页数
	 */
	public Integer getPage(CellVo vo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaids", vo.getAreas());
		params.put("modetypes", vo.getModetypes());
		params.put("cellBands", vo.getCellBands());
		params.put("indoor", vo.getIndoor());
		int num = this.baseStationDao.countRegiondown(params);
		int page = 0;
		if(num%500!=0){
			page = num/500+1;
		}else{
			page = num/500;
		}
		return page;
	}
	public List<ReportCells> getReportLoadCells(CellVo vo){
		Map<String, Object> param = new HashMap<String, Object>();
		List<ReportCells> list = new ArrayList<ReportCells>();
		param.put("modetypes", vo.getModetypes());
		param.put("cellBands", vo.getCellBands());
		param.put("indoor", vo.getIndoor());
		param.put("nearrel", vo.getCellrel());
		param.put("areaids", vo.getAreas());
		list = this.baseStationDao.queryReportLoadCells(param);

		
		return list;
	}
	public static void main(String[] args) {
		for(int i=1;i<=96;i++){
			System.out.print(i +" as a"+i +",");
			if(i%10==0){
				System.out.println();
			}
		}
	}
}
