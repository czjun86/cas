package com.complaint.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 

 * @ClassName: GradeBean

 * @Description: 等级评测实体

 * @author: czj

 * @date: 2013-8-2 上午10:25:08
 */
public class GradeBean {
	private Double free3;//自由模式３Ｇ百分比
	private int isf; //是否第一次查询
	private String area;//区域
	private String sence;//场景
	private String time;//测试时间
	private String jtime;//接单时间
	private String path;//受理路径
	private Double lat_y;//原始纬度
	private Double lng_y;//原始经度
	private Double talkaround;//脱网率
	private Double pocent2;//2G占比
	private String sumc;//测试总条数
	private String flowid;//流水号
	private String phone;//投诉电话
	private String net_worktype;//工单网络类型
	private int test_type;
	private int call_type;
	private int ftp_type;
	private Double lat_m;
	private Double lng_m;
	private String serialno;
	private String problemsAddress;
	private String nettype;//1-2G,2-3G,3-3G自由,4-2G自由
	private String inside;
	private String RSCP_g;
	private String rscp_color;
	private String ECNO_g;
	private String ecno_color;
	private String txpower_g;
	private String tx_color;
	private String fu_g;
	private String fu_color;
	private String fd_g;
	private String fd_color;
	private String fu_g_4g;
	private String fu_color_4g;
	private String fd_g_4g;
	private String fd_color_4g;
	private String rx_g;
	private String rx_color;
	private String rq_g;
	private String rq_color;
	private String ci_g;
	private String ci_color;
	private String RSRP_g;
	private String rsrp_color;
	private String RSRQ_g;
	private String rsrq_color;
	private String SNR_g;
	private String snr_color;
	//单个流水号的总评价
	private String comp_eval_3g_g;
	private String comp_eval_3g_color;
	private String comp_eval_2g_g;
	private String comp_eval_2g_color;
	private String comp_eval_4g_g;
	private String comp_eval_4g_color;
    private Double  RSCP_1;
    private Double  RSCP_2;
    private Double  RSCP_3;
    private Double  RSCP_4;
    
    private Double EC_NO_1;
    private Double EC_NO_2;
    private Double EC_NO_3;
    private Double EC_NO_4;
    
    private Double txpower_1;
    private Double txpower_2;
    private Double txpower_3;
    private Double txpower_4;
    
    private Double FTP_SPEED_UP_1;
    private Double FTP_SPEED_UP_2;
    private Double FTP_SPEED_UP_3;
    private Double FTP_SPEED_UP_4;

    
    private Double FTP_SPEED_DOWN_1;
    private Double FTP_SPEED_DOWN_2;
    private Double FTP_SPEED_DOWN_3;
    private Double FTP_SPEED_DOWN_4;

    
    private Double RXLEV_Sub_1;
    private Double RXLEV_Sub_2;
    private Double RXLEV_Sub_3;
    private Double RXLEV_Sub_4;

    private Double RXQUAL_Sub_1;
    private Double RXQUAL_Sub_2;
    private Double RXQUAL_Sub_3;
    private Double RXQUAL_Sub_4;
    
    private Double ci_1;
    private Double ci_2;
    private Double ci_3;
    private Double ci_4;
    private Double rsrp_1;
    private Double rsrp_2;
    private Double rsrp_3;
    private Double rsrp_4;
    private Double rsrq_1;
    private Double rsrq_2;
    private Double rsrq_3;
    private Double rsrq_4;
    private Double snr_1;
    private Double snr_2;
    private Double snr_3;
    private Double snr_4;
	
	public Double getCi_1() {
		return ci_1;
	}
	public void setCi_1(Double ci_1) {
		this.ci_1 = ci_1;
	}
	public Double getCi_2() {
		return ci_2;
	}
	public void setCi_2(Double ci_2) {
		this.ci_2 = ci_2;
	}
	public Double getCi_3() {
		return ci_3;
	}
	public void setCi_3(Double ci_3) {
		this.ci_3 = ci_3;
	}
	public Double getCi_4() {
		return ci_4;
	}
	public void setCi_4(Double ci_4) {
		this.ci_4 = ci_4;
	}
	public String getComp_eval_3g_g() {
		return comp_eval_3g_g;
	}
	public void setComp_eval_3g_g(String comp_eval_3g_g) {
		this.comp_eval_3g_g = comp_eval_3g_g;
	}
	public String getComp_eval_3g_color() {
		return comp_eval_3g_color;
	}
	public void setComp_eval_3g_color(String comp_eval_3g_color) {
		this.comp_eval_3g_color = comp_eval_3g_color;
	}
	public String getComp_eval_2g_g() {
		return comp_eval_2g_g;
	}
	public void setComp_eval_2g_g(String comp_eval_2g_g) {
		this.comp_eval_2g_g = comp_eval_2g_g;
	}
	public String getComp_eval_2g_color() {
		return comp_eval_2g_color;
	}
	public void setComp_eval_2g_color(String comp_eval_2g_color) {
		this.comp_eval_2g_color = comp_eval_2g_color;
	}
	public String getJtime() {
		return jtime;
	}
	public void setJtime(String jtime) {
		this.jtime = jtime;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSence() {
		return sence;
	}
	public void setSence(String sence) {
		this.sence = sence;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getLat_m() {
		return lat_m;
	}
	public void setLat_m(Double lat_m) {
		this.lat_m = lat_m;
	}
	public Double getLng_m() {
		return lng_m;
	}
	public void setLng_m(Double lng_m) {
		this.lng_m = lng_m;
	}
	
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public String getProblemsAddress() {
		return problemsAddress;
	}
	public void setProblemsAddress(String problemsAddress) {
		this.problemsAddress = problemsAddress;
	}

	private Double ftp_avg_speed;
	public Double getRSCP_1() {
		return RSCP_1;
	}
	public void setRSCP_1(Double rSCP_1) {
		RSCP_1 = rSCP_1;
	}
	public Double getRSCP_2() {
		return RSCP_2;
	}
	public String getNettype() {
		return nettype;
	}
	public void setNettype(String nettype) {
		this.nettype = nettype;
	}
	public void setRSCP_2(Double rSCP_2) {
		RSCP_2 = rSCP_2;
	}
	public Double getRSCP_3() {
		return RSCP_3;
	}
	public void setRSCP_3(Double rSCP_3) {
		RSCP_3 = rSCP_3;
	}
	public Double getRSCP_4() {
		return RSCP_4;
	}
	public void setRSCP_4(Double rSCP_4) {
		RSCP_4 = rSCP_4;
	}
	public Double getEC_NO_1() {
		return EC_NO_1;
	}
	public void setEC_NO_1(Double eC_NO_1) {
		EC_NO_1 = eC_NO_1;
	}
	public Double getEC_NO_2() {
		return EC_NO_2;
	}
	public void setEC_NO_2(Double eC_NO_2) {
		EC_NO_2 = eC_NO_2;
	}
	public Double getEC_NO_3() {
		return EC_NO_3;
	}
	public void setEC_NO_3(Double eC_NO_3) {
		EC_NO_3 = eC_NO_3;
	}
	public Double getEC_NO_4() {
		return EC_NO_4;
	}
	public void setEC_NO_4(Double eC_NO_4) {
		EC_NO_4 = eC_NO_4;
	}
	public Double getTxpower_1() {
		return txpower_1;
	}
	public void setTxpower_1(Double txpower_1) {
		this.txpower_1 = txpower_1;
	}
	public Double getTxpower_2() {
		return txpower_2;
	}
	public void setTxpower_2(Double txpower_2) {
		this.txpower_2 = txpower_2;
	}
	public Double getTxpower_3() {
		return txpower_3;
	}
	public void setTxpower_3(Double txpower_3) {
		this.txpower_3 = txpower_3;
	}
	public Double getTxpower_4() {
		return txpower_4;
	}
	public void setTxpower_4(Double txpower_4) {
		this.txpower_4 = txpower_4;
	}
	public Double getFTP_SPEED_UP_1() {
		return FTP_SPEED_UP_1;
	}
	public void setFTP_SPEED_UP_1(Double fTP_SPEED_UP_1) {
		FTP_SPEED_UP_1 = fTP_SPEED_UP_1;
	}
	public Double getFTP_SPEED_UP_2() {
		return FTP_SPEED_UP_2;
	}
	public void setFTP_SPEED_UP_2(Double fTP_SPEED_UP_2) {
		FTP_SPEED_UP_2 = fTP_SPEED_UP_2;
	}
	public Double getFTP_SPEED_UP_3() {
		return FTP_SPEED_UP_3;
	}
	public void setFTP_SPEED_UP_3(Double fTP_SPEED_UP_3) {
		FTP_SPEED_UP_3 = fTP_SPEED_UP_3;
	}
	public Double getFTP_SPEED_UP_4() {
		return FTP_SPEED_UP_4;
	}
	public void setFTP_SPEED_UP_4(Double fTP_SPEED_UP_4) {
		FTP_SPEED_UP_4 = fTP_SPEED_UP_4;
	}
	public Double getFTP_SPEED_DOWN_1() {
		return FTP_SPEED_DOWN_1;
	}
	public void setFTP_SPEED_DOWN_1(Double fTP_SPEED_DOWN_1) {
		FTP_SPEED_DOWN_1 = fTP_SPEED_DOWN_1;
	}
	public Double getFTP_SPEED_DOWN_2() {
		return FTP_SPEED_DOWN_2;
	}
	public void setFTP_SPEED_DOWN_2(Double fTP_SPEED_DOWN_2) {
		FTP_SPEED_DOWN_2 = fTP_SPEED_DOWN_2;
	}
	public Double getFTP_SPEED_DOWN_3() {
		return FTP_SPEED_DOWN_3;
	}
	public void setFTP_SPEED_DOWN_3(Double fTP_SPEED_DOWN_3) {
		FTP_SPEED_DOWN_3 = fTP_SPEED_DOWN_3;
	}
	public Double getFTP_SPEED_DOWN_4() {
		return FTP_SPEED_DOWN_4;
	}
	public void setFTP_SPEED_DOWN_4(Double fTP_SPEED_DOWN_4) {
		FTP_SPEED_DOWN_4 = fTP_SPEED_DOWN_4;
	}
	public Double getRXLEV_Sub_1() {
		return RXLEV_Sub_1;
	}
	public void setRXLEV_Sub_1(Double rXLEV_Sub_1) {
		RXLEV_Sub_1 = rXLEV_Sub_1;
	}
	public Double getRXLEV_Sub_2() {
		return RXLEV_Sub_2;
	}
	public void setRXLEV_Sub_2(Double rXLEV_Sub_2) {
		RXLEV_Sub_2 = rXLEV_Sub_2;
	}
	public Double getRXLEV_Sub_3() {
		return RXLEV_Sub_3;
	}
	public void setRXLEV_Sub_3(Double rXLEV_Sub_3) {
		RXLEV_Sub_3 = rXLEV_Sub_3;
	}
	public Double getRXLEV_Sub_4() {
		return RXLEV_Sub_4;
	}
	public void setRXLEV_Sub_4(Double rXLEV_Sub_4) {
		RXLEV_Sub_4 = rXLEV_Sub_4;
	}
	public Double getRXQUAL_Sub_1() {
		return RXQUAL_Sub_1;
	}
	public void setRXQUAL_Sub_1(Double rXQUAL_Sub_1) {
		RXQUAL_Sub_1 = rXQUAL_Sub_1;
	}
	public Double getRXQUAL_Sub_2() {
		return RXQUAL_Sub_2;
	}
	public void setRXQUAL_Sub_2(Double rXQUAL_Sub_2) {
		RXQUAL_Sub_2 = rXQUAL_Sub_2;
	}
	public Double getRXQUAL_Sub_3() {
		return RXQUAL_Sub_3;
	}
	public void setRXQUAL_Sub_3(Double rXQUAL_Sub_3) {
		RXQUAL_Sub_3 = rXQUAL_Sub_3;
	}
	public Double getRXQUAL_Sub_4() {
		return RXQUAL_Sub_4;
	}
	public void setRXQUAL_Sub_4(Double rXQUAL_Sub_4) {
		RXQUAL_Sub_4 = rXQUAL_Sub_4;
	}
	public String getRSCP_g() {
		return RSCP_g;
	}
	public void setRSCP_g(String rSCP_g) {
		RSCP_g = rSCP_g;
	}
	public String getECNO_g() {
		return ECNO_g;
	}
	public void setECNO_g(String eCNO_g) {
		ECNO_g = eCNO_g;
	}
	public String getTxpower_g() {
		return txpower_g;
	}
	public void setTxpower_g(String txpower_g) {
		this.txpower_g = txpower_g;
	}
	public String getFu_g() {
		return fu_g;
	}
	public void setFu_g(String fu_g) {
		this.fu_g = fu_g;
	}
	public String getFd_g() {
		return fd_g;
	}
	public void setFd_g(String fd_g) {
		this.fd_g = fd_g;
	}
	public String getRx_g() {
		return rx_g;
	}
	public void setRx_g(String rx_g) {
		this.rx_g = rx_g;
	}
	public String getRq_g() {
		return rq_g;
	}
	public void setRq_g(String rq_g) {
		this.rq_g = rq_g;
	}
	public String getRscp_color() {
		return rscp_color;
	}
	public void setRscp_color(String rscp_color) {
		this.rscp_color = rscp_color;
	}
	public String getEcno_color() {
		return ecno_color;
	}
	public void setEcno_color(String ecno_color) {
		this.ecno_color = ecno_color;
	}
	public String getTx_color() {
		return tx_color;
	}
	public void setTx_color(String tx_color) {
		this.tx_color = tx_color;
	}
	public String getFu_color() {
		return fu_color;
	}
	public void setFu_color(String fu_color) {
		this.fu_color = fu_color;
	}
	public String getFd_color() {
		return fd_color;
	}
	public void setFd_color(String fd_color) {
		this.fd_color = fd_color;
	}
	public String getRx_color() {
		return rx_color;
	}
	public void setRx_color(String rx_color) {
		this.rx_color = rx_color;
	}
	public String getRq_color() {
		return rq_color;
	}
	public void setRq_color(String rq_color) {
		this.rq_color = rq_color;
	}
	public Double getFtp_avg_speed() {
		return ftp_avg_speed;
	}
	public void setFtp_avg_speed(Double ftp_avg_speed) {
		this.ftp_avg_speed = ftp_avg_speed;
	}

	public String getInside() {
		return inside;
	}
	public void setInside(String inside) {
		this.inside = inside;
	}
	public int getIsf() {
		return isf;
	}
	public void setIsf(int isf) {
		this.isf = isf;
	}
	public int getTest_type() {
		return test_type;
	}
	public void setTest_type(int test_type) {
		this.test_type = test_type;
	}
	public int getCall_type() {
		return call_type;
	}
	public void setCall_type(int call_type) {
		this.call_type = call_type;
	}
	public int getFtp_type() {
		return ftp_type;
	}
	public void setFtp_type(int ftp_type) {
		this.ftp_type = ftp_type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public Double getLat_y() {
		return lat_y;
	}
	public void setLat_y(Double lat_y) {
		this.lat_y = lat_y;
	}
	public Double getLng_y() {
		return lng_y;
	}
	public void setLng_y(Double lng_y) {
		this.lng_y = lng_y;
	}
	public Double getTalkaround() {
		return talkaround;
	}
	public void setTalkaround(Double talkaround) {
		this.talkaround = talkaround;
	}
	public Double getPocent2() {
		return pocent2;
	}
	public void setPocent2(Double pocent2) {
		this.pocent2 = pocent2;
	}
	public String getFlowid() {
		return flowid;
	}
	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getFree3() {
		return free3;
	}
	public void setFree3(Double free3) {
		this.free3 = free3;
	}
	public String getSumc() {
		return sumc;
	}
	public void setSumc(String sumc) {
		this.sumc = sumc;
	}
	public String getNet_worktype() {
		return net_worktype;
	}
	public void setNet_worktype(String net_worktype) {
		this.net_worktype = net_worktype;
	}
	public String getCi_g() {
		return ci_g;
	}
	public void setCi_g(String ci_g) {
		this.ci_g = ci_g;
	}
	public String getCi_color() {
		return ci_color;
	}
	public void setCi_color(String ci_color) {
		this.ci_color = ci_color;
	}
	public String getComp_eval_4g_g() {
		return comp_eval_4g_g;
	}
	public void setComp_eval_4g_g(String comp_eval_4g_g) {
		this.comp_eval_4g_g = comp_eval_4g_g;
	}
	public String getComp_eval_4g_color() {
		return comp_eval_4g_color;
	}
	public void setComp_eval_4g_color(String comp_eval_4g_color) {
		this.comp_eval_4g_color = comp_eval_4g_color;
	}
	public Double getRsrp_1() {
		return rsrp_1;
	}
	public void setRsrp_1(Double rsrp_1) {
		this.rsrp_1 = rsrp_1;
	}
	public Double getRsrp_2() {
		return rsrp_2;
	}
	public void setRsrp_2(Double rsrp_2) {
		this.rsrp_2 = rsrp_2;
	}
	public Double getRsrp_3() {
		return rsrp_3;
	}
	public void setRsrp_3(Double rsrp_3) {
		this.rsrp_3 = rsrp_3;
	}
	public Double getRsrp_4() {
		return rsrp_4;
	}
	public void setRsrp_4(Double rsrp_4) {
		this.rsrp_4 = rsrp_4;
	}
	public Double getRsrq_1() {
		return rsrq_1;
	}
	public void setRsrq_1(Double rsrq_1) {
		this.rsrq_1 = rsrq_1;
	}
	public Double getRsrq_2() {
		return rsrq_2;
	}
	public void setRsrq_2(Double rsrq_2) {
		this.rsrq_2 = rsrq_2;
	}
	public Double getRsrq_3() {
		return rsrq_3;
	}
	public void setRsrq_3(Double rsrq_3) {
		this.rsrq_3 = rsrq_3;
	}
	public Double getRsrq_4() {
		return rsrq_4;
	}
	public void setRsrq_4(Double rsrq_4) {
		this.rsrq_4 = rsrq_4;
	}
	public Double getSnr_1() {
		return snr_1;
	}
	public void setSnr_1(Double snr_1) {
		this.snr_1 = snr_1;
	}
	public Double getSnr_2() {
		return snr_2;
	}
	public void setSnr_2(Double snr_2) {
		this.snr_2 = snr_2;
	}
	public Double getSnr_3() {
		return snr_3;
	}
	public void setSnr_3(Double snr_3) {
		this.snr_3 = snr_3;
	}
	public Double getSnr_4() {
		return snr_4;
	}
	public void setSnr_4(Double snr_4) {
		this.snr_4 = snr_4;
	}
	public String getRSRP_g() {
		return RSRP_g;
	}
	public void setRSRP_g(String rSRP_g) {
		RSRP_g = rSRP_g;
	}
	public String getRsrp_color() {
		return rsrp_color;
	}
	public void setRsrp_color(String rsrp_color) {
		this.rsrp_color = rsrp_color;
	}
	public String getRSRQ_g() {
		return RSRQ_g;
	}
	public void setRSRQ_g(String rSRQ_g) {
		RSRQ_g = rSRQ_g;
	}
	public String getRsrq_color() {
		return rsrq_color;
	}
	public void setRsrq_color(String rsrq_color) {
		this.rsrq_color = rsrq_color;
	}
	public String getSNR_g() {
		return SNR_g;
	}
	public void setSNR_g(String sNR_g) {
		SNR_g = sNR_g;
	}
	public String getSnr_color() {
		return snr_color;
	}
	public void setSnr_color(String snr_color) {
		this.snr_color = snr_color;
	}
	public String getFu_g_4g() {
		return fu_g_4g;
	}
	public void setFu_g_4g(String fu_g_4g) {
		this.fu_g_4g = fu_g_4g;
	}
	public String getFu_color_4g() {
		return fu_color_4g;
	}
	public void setFu_color_4g(String fu_color_4g) {
		this.fu_color_4g = fu_color_4g;
	}
	public String getFd_g_4g() {
		return fd_g_4g;
	}
	public void setFd_g_4g(String fd_g_4g) {
		this.fd_g_4g = fd_g_4g;
	}
	public String getFd_color_4g() {
		return fd_color_4g;
	}
	public void setFd_color_4g(String fd_color_4g) {
		this.fd_color_4g = fd_color_4g;
	}

}
