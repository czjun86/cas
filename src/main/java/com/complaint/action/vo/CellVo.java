package com.complaint.action.vo;

/**
 * 
 * 
 * @ClassName: CellVo
 * 
 * @Description: 地图评价小区前台参数VO
 * 
 * @author: czj
 * 
 * @date: 2013-11-25 下午1:56:04
 */
public class CellVo {
	private String areas;// 区域
	private String indoor;// 0－室外，1－室内
	private String modetypes;// 网络状态
	private String cellBands;// 小区频段（2G才有）
	private int type;// 0 小区加载 1 小区导出
	private String lac_cid;//lac_cid
	private String cellrel;//小区关系 0 同频 1异频 2 同频  "0,1,2"
	private int report_type;//导出类型  0 框选导出 1 小区导出 2 点击邻区导出 3 加载小区导出
	private double minlat;//框选极限坐标
	private double maxlat;//框选极限坐标
	private double minlng;//框选极限坐标
	private double maxlng;//框选极限坐标
	
	public double getMinlat() {
		return minlat;
	}

	public void setMinlat(double minlat) {
		this.minlat = minlat;
	}

	public double getMaxlat() {
		return maxlat;
	}

	public void setMaxlat(double maxlat) {
		this.maxlat = maxlat;
	}

	public double getMinlng() {
		return minlng;
	}

	public void setMinlng(double minlng) {
		this.minlng = minlng;
	}

	public double getMaxlng() {
		return maxlng;
	}

	public void setMaxlng(double maxlng) {
		this.maxlng = maxlng;
	}

	public int getReport_type() {
		return report_type;
	}

	public void setReport_type(int report_type) {
		this.report_type = report_type;
	}

	public String getLac_cid() {
		return lac_cid;
	}

	public void setLac_cid(String lac_cid) {
		this.lac_cid = lac_cid;
	}

	public String getCellrel() {
		return cellrel;
	}

	public void setCellrel(String cellrel) {
		this.cellrel = cellrel;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getIndoor() {
		return indoor;
	}

	public void setIndoor(String indoor) {
		this.indoor = indoor;
	}

	public String getModetypes() {
		return modetypes;
	}

	public void setModetypes(String modetypes) {
		this.modetypes = modetypes;
	}

	public String getCellBands() {
		return cellBands;
	}

	public void setCellBands(String cellBands) {
		this.cellBands = cellBands;
	}

}
