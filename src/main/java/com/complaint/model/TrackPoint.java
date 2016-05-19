 package com.complaint.model;
/***
 * 轨迹(室内外)点图
 * @author Administrator
 *
 */
public class TrackPoint {
//点信息
	private String id;
	private Double x;//x轴
	private Double y;//y轴
	private Double lat_modi;//修正纬度
	private Double lng_modi;//修正经度
	private Double lat;//纬度
	private Double lng;//经度
	private Integer psc;
    private Integer bcch;//bcch,psc,pci是值，其他指标是颜色值
    private Integer pci;
    private Integer rscp;
    private Integer ecno;
//颜色值
	private Integer txpower;
    private Integer ftpSpeed;

    private Integer ftpType;
    private Integer rxlev;
    private Integer rxqual;
    private Integer ci;
    private Integer mos;
    private Integer ta;
    //4G
    private Integer rsrp;
    private Integer rsrq;
    private Integer snr;

	private Double snr_;
	private Double rscp_;
    private Double ecno_;

	private Double txpower_;
    private Double ftpSpeed_;
    private Double rxlev_;
    private Double rxqual_;
    private Double ci_;
    private Double mos_;
    private Double ta_;
    
    //4G
    private Double rsrp_;
    private Double rsrq_;
    
	private Integer inside;//室内外
	private String eptime;
	private String flowid;
	private Integer realnet_type;
	public Integer getInside() {
		return inside;
	}
	public void setInside(Integer inside) {
		this.inside = inside;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public Integer getBcch() {
		return bcch;
	}
	public void setBcch(Integer bcch) {
		this.bcch = bcch;
	}
	public Integer getRscp() {
		return rscp;
	}
	public void setRscp(Integer rscp) {
		this.rscp = rscp;
	}
	public Integer getEcno() {
		return ecno;
	}
	public void setEcno(Integer ecno) {
		this.ecno = ecno;
	}
	public Integer getTxpower() {
		return txpower;
	}
	public void setTxpower(Integer txpower) {
		this.txpower = txpower;
	}
	public Integer getFtpSpeed() {
		return ftpSpeed;
	}
	public void setFtpSpeed(Integer ftpSpeed) {
		this.ftpSpeed = ftpSpeed;
	}
	public Integer getRxlev() {
		return rxlev;
	}
	public void setRxlev(Integer rxlev) {
		this.rxlev = rxlev;
	}
	public Integer getRxqual() {
		return rxqual;
	}
	public void setRxqual(Integer rxqual) {
		this.rxqual = rxqual;
	}
	public Integer getCi() {
		return ci;
	}
	public void setCi(Integer ci) {
		this.ci = ci;
	}
	public Integer getMos() {
		return mos;
	}
	public void setMos(Integer mos) {
		this.mos = mos;
	}
    
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public Integer getTa() {
		return ta;
	}
	public void setTa(Integer ta) {
		this.ta = ta;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Integer getPsc() {
		return psc;
	}
	public void setPsc(Integer psc) {
		this.psc = psc;
	}
	public Double getLat_modi() {
		return lat_modi;
	}
	public void setLat_modi(Double lat_modi) {
		this.lat_modi = lat_modi;
	}
	public Double getLng_modi() {
		return lng_modi;
	}
	public void setLng_modi(Double lng_modi) {
		this.lng_modi = lng_modi;
	}

    public String getEptime() {
		return eptime;
	}
	public void setEptime(String eptime) {
		this.eptime = eptime;
	}
	public String getFlowid() {
		return flowid;
	}
	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
	public Double getRscp_() {
		return rscp_;
	}
	public void setRscp_(Double rscp_) {
		this.rscp_ = rscp_;
	}
	public Double getEcno_() {
		return ecno_;
	}
	public void setEcno_(Double ecno_) {
		this.ecno_ = ecno_;
	}
	public Double getTxpower_() {
		return txpower_;
	}
	public void setTxpower_(Double txpower_) {
		this.txpower_ = txpower_;
	}
	public Double getFtpSpeed_() {
		return ftpSpeed_;
	}
	public void setFtpSpeed_(Double ftpSpeed_) {
		this.ftpSpeed_ = ftpSpeed_;
	}
	public Double getRxlev_() {
		return rxlev_;
	}
	public void setRxlev_(Double rxlev_) {
		this.rxlev_ = rxlev_;
	}
	public Double getRxqual_() {
		return rxqual_;
	}
	public void setRxqual_(Double rxqual_) {
		this.rxqual_ = rxqual_;
	}
	public Double getCi_() {
		return ci_;
	}
	public void setCi_(Double ci_) {
		this.ci_ = ci_;
	}
	public Double getMos_() {
		return mos_;
	}
	public void setMos_(Double mos_) {
		this.mos_ = mos_;
	}
	public Integer getFtpType() {
		return ftpType;
	}
	public void setFtpType(Integer ftpType) {
		this.ftpType = ftpType;
	}
	public Double getTa_() {
		return ta_;
	}
	public void setTa_(Double ta_) {
		this.ta_ = ta_;
	}
	public Integer getRealnet_type() {
		return realnet_type;
	}
	public void setRealnet_type(Integer realnet_type) {
		this.realnet_type = realnet_type;
	}
	public Integer getPci() {
		return pci;
	}
	public void setPci(Integer pci) {
		this.pci = pci;
	}
	public Integer getRsrp() {
		return rsrp;
	}
	public void setRsrp(Integer rsrp) {
		this.rsrp = rsrp;
	}
	public Integer getRsrq() {
		return rsrq;
	}
	public void setRsrq(Integer rsrq) {
		this.rsrq = rsrq;
	}
	public Integer getSnr() {
		return snr;
	}
	public void setSnr(Integer snr) {
		this.snr = snr;
	}
	public Double getSnr_() {
		return snr_;
	}
	public void setSnr_(Double snr_) {
		this.snr_ = snr_;
	}
	public Double getRsrp_() {
		return rsrp_;
	}
	public void setRsrp_(Double rsrp_) {
		this.rsrp_ = rsrp_;
	}
	public Double getRsrq_() {
		return rsrq_;
	}
	public void setRsrq_(Double rsrq_) {
		this.rsrq_ = rsrq_;
	}

}
