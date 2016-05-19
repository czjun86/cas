package com.complaint.model;

import java.math.BigDecimal;

public class TCasCell {
	private Long lac;
	private Long cellId;
	private BigDecimal celllat;
	private BigDecimal celllng;
	private String bid;// 基站id
	private BigDecimal ant_azimuth;//原始 方位角
	private BigDecimal self_azimuth;//修正 方位角
	private int flag;
	private Integer upfreq;//上行频点
	private Integer downfreq;//下行频点
	private String nears;//邻区
	private int neartype;//邻区关系 1、正向 2、反向 3、双向
	private int nearrel;//邻区类型 1、同频2、异频3、异网络
	private int modetype;//网络状态
	private String adminRegion;//行政区
	private String rncname;//RNC名称
	private String baseName;//基站名称
	private String cellName;//小区名称
	private String neType;//网元类型
	private int psc;
	private BigDecimal antElectAngle;//电子倾角
	private BigDecimal antMathAngle;//机械倾角
	private int tilt;//下倾角
	private String antType;//天线类型
	private String sharedAnt;//天线共用情况
	private int indoor;//0室外 1室内
	
	private String sharedPlatform;//共用平台
	private String devType;//基站设备类型
	private int bsic;
	private String coverRange;//覆盖范围
	private String bscName;//BSC
	private Integer cellBand;//小区频段
	public BigDecimal getAnt_azimuth() {
		return ant_azimuth;
	}

	public void setAnt_azimuth(BigDecimal ant_azimuth) {
		this.ant_azimuth = ant_azimuth;
	}

	public BigDecimal getSelf_azimuth() {
		return self_azimuth;
	}

	public void setSelf_azimuth(BigDecimal self_azimuth) {
		this.self_azimuth = self_azimuth;
	}

	public int getIndoor() {
		return indoor;
	}

	public void setIndoor(int indoor) {
		this.indoor = indoor;
	}

	public int getModetype() {
		return modetype;
	}

	public void setModetype(int modetype) {
		this.modetype = modetype;
	}

	public int getNearrel() {
		return nearrel;
	}

	public void setNearrel(int nearrel) {
		this.nearrel = nearrel;
	}

	public String getNears() {
		return nears;
	}

	public void setNears(String nears) {
		this.nears = nears;
	}

	public Integer getUpfreq() {
		return upfreq;
	}

	public void setUpfreq(Integer upfreq) {
		this.upfreq = upfreq;
	}

	public Integer getDownfreq() {
		return downfreq;
	}

	public void setDownfreq(Integer downfreq) {
		this.downfreq = downfreq;
	}

	public int getNeartype() {
		return neartype;
	}

	public void setNeartype(int neartype) {
		this.neartype = neartype;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Long getLac() {
		return lac;
	}

	public void setLac(Long lac) {
		this.lac = lac;
	}

	public Long getCellId() {
		return cellId;
	}

	public void setCellId(Long cellId) {
		this.cellId = cellId;
	}

	public BigDecimal getCelllat() {
		return celllat;
	}

	public void setCelllat(BigDecimal celllat) {
		this.celllat = celllat;
	}

	public BigDecimal getCelllng() {
		return celllng;
	}

	public void setCelllng(BigDecimal celllng) {
		this.celllng = celllng;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getAdminRegion() {
		return adminRegion;
	}

	public void setAdminRegion(String adminRegion) {
		this.adminRegion = adminRegion;
	}

	public String getRncname() {
		return rncname;
	}

	public void setRncname(String rncname) {
		this.rncname = rncname;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getNeType() {
		return neType;
	}

	public void setNeType(String neType) {
		this.neType = neType;
	}

	public int getPsc() {
		return psc;
	}

	public void setPsc(int psc) {
		this.psc = psc;
	}



	public Integer getTilt() {
		return tilt;
	}

	public void setTilt(Integer tilt) {
		this.tilt = tilt;
	}

	public String getAntType() {
		return antType;
	}

	public void setAntType(String antType) {
		this.antType = antType;
	}

	public String getSharedAnt() {
		return sharedAnt;
	}

	public void setSharedAnt(String sharedAnt) {
		this.sharedAnt = sharedAnt;
	}

	public BigDecimal getAntElectAngle() {
		return antElectAngle;
	}

	public void setAntElectAngle(BigDecimal antElectAngle) {
		this.antElectAngle = antElectAngle;
	}

	public BigDecimal getAntMathAngle() {
		return antMathAngle;
	}

	public void setAntMathAngle(BigDecimal antMathAngle) {
		this.antMathAngle = antMathAngle;
	}

	public void setTilt(int tilt) {
		this.tilt = tilt;
	}

	public String getSharedPlatform() {
		return sharedPlatform;
	}

	public void setSharedPlatform(String sharedPlatform) {
		this.sharedPlatform = sharedPlatform;
	}

	public int getBsic() {
		return bsic;
	}

	public void setBsic(int bsic) {
		this.bsic = bsic;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public String getCoverRange() {
		return coverRange;
	}

	public void setCoverRange(String coverRange) {
		this.coverRange = coverRange;
	}

	public String getBscName() {
		return bscName;
	}

	public void setBscName(String bscName) {
		this.bscName = bscName;
	}

	public Integer getCellBand() {
		return cellBand;
	}

	public void setCellBand(Integer cellBand) {
		this.cellBand = cellBand;
	}

}
