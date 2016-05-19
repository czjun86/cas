package com.complaint.model;
/***
 * 封装3G与2G梳头图数据
 * @author Administrator
 *
 */
public class WcdmaGsmData {
     private String flowid;
     private Integer inside;//1－室内，0－室外
     private Integer netType;//1 联通gsm锁频,2 wcdma锁频,3 wcdma 自由模式, 4 lte锁频
     private Integer RSRP;//1-显示,0-不显示
     private Double  RSRP_1;
     private Double  RSRP_2;
     private Double  RSRP_3;
     private Double  RSRP_4;
     private Double  RSRP_5;
     private Double  RSRP_6;
     
     private Integer SNR;//1-显示,0-不显示
     private Double  SNR_1;
     private Double  SNR_2;
     private Double  SNR_3;
     private Double  SNR_4;
     private Double  SNR_5;
     private Double  SNR_6;
     
     private Integer RSRQ;//1-显示,0-不显示
     private Double  RSRQ_1;
     private Double  RSRQ_2;
     private Double  RSRQ_3;
     private Double  RSRQ_4;
     private Double  RSRQ_5;
     private Double  RSRQ_6;
     
     private Integer RSCP;//1-显示,0-不显示
     private Double  RSCP_1;
     private Double  RSCP_2;
     private Double  RSCP_3;
     private Double  RSCP_4;
     private Double  RSCP_5;
     private Double  RSCP_6;
     
     private Integer EC_NO;//1-显示,0-不显示
     private Double EC_NO_1;
     private Double EC_NO_2;
     private Double EC_NO_3;
     private Double EC_NO_4;
     private Double EC_NO_5;
     private Double EC_NO_6;
     
     private Integer txpower;//1-显示,0-不显示
     private Double txpower_1;
     private Double txpower_2;
     private Double txpower_3;
     private Double txpower_4;
     private Double txpower_5;
     private Double txpower_6;
     
     private Integer FTP_SPEED_UP;//1-显示,0-不显示
     private Double FTP_SPEED_UP_1;
     private Double FTP_SPEED_UP_2;
     private Double FTP_SPEED_UP_3;
     private Double FTP_SPEED_UP_4;
     private Double FTP_SPEED_UP_5;
     private Double FTP_SPEED_UP_6;
     
     
     private Integer FTP_SPEED_DOWN;//1-显示,0-不显示
     private Double FTP_SPEED_DOWN_1;
     private Double FTP_SPEED_DOWN_2;
     private Double FTP_SPEED_DOWN_3;
     private Double FTP_SPEED_DOWN_4;
     private Double FTP_SPEED_DOWN_5;
     private Double FTP_SPEED_DOWN_6;
     
     
     private Integer RXLEV_Sub;//1-显示,0-不显示
     private Double RXLEV_Sub_1;
     private Double RXLEV_Sub_2;
     private Double RXLEV_Sub_3;
     private Double RXLEV_Sub_4;
     private Double RXLEV_Sub_5;
     private Double RXLEV_Sub_6;
     
     private Integer RXQUAL_Sub;//1-显示,0-不显示
     private Double RXQUAL_Sub_1;
     private Double RXQUAL_Sub_2;
     private Double RXQUAL_Sub_3;
     private Double RXQUAL_Sub_4;
     private Double RXQUAL_Sub_5;
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
	public Double getRXLEV_Sub_5() {
		return RXLEV_Sub_5;
	}
	public void setRXLEV_Sub_5(Double rXLEV_Sub_5) {
		RXLEV_Sub_5 = rXLEV_Sub_5;
	}
	public Double getRXLEV_Sub_6() {
		return RXLEV_Sub_6;
	}
	public void setRXLEV_Sub_6(Double rXLEV_Sub_6) {
		RXLEV_Sub_6 = rXLEV_Sub_6;
	}
	public void setRXLEV_Sub(Integer rXLEV_Sub) {
		RXLEV_Sub = rXLEV_Sub;
	}
	private Double RXQUAL_Sub_6;
     
     
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
	public Double getRXQUAL_Sub_5() {
		return RXQUAL_Sub_5;
	}
	public void setRXQUAL_Sub_5(Double rXQUAL_Sub_5) {
		RXQUAL_Sub_5 = rXQUAL_Sub_5;
	}
	public Double getRXQUAL_Sub_6() {
		return RXQUAL_Sub_6;
	}
	public void setRXQUAL_Sub_6(Double rXQUAL_Sub_6) {
		RXQUAL_Sub_6 = rXQUAL_Sub_6;
	}
	public void setRXQUAL_Sub(Integer rXQUAL_Sub) {
		RXQUAL_Sub = rXQUAL_Sub;
	}
	public void setRXQUAL_Sub_1(Double rXQUAL_Sub_1) {
		RXQUAL_Sub_1 = rXQUAL_Sub_1;
	}
	private Integer C_I;//1-显示,0-不显示
     private Double C_I_1;
     private Double C_I_2;
     private Double C_I_3;
     private Double C_I_4;
     private Double C_I_5;
     private Double C_I_6;
     
     
     private Integer MOS;//1-显示,0-不显示
     private Double MOS_1;
     public String getFlowid() {
		return flowid;
	}
	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
	public Integer getRSCP() {
		return RSCP;
	}
	public void setRSCP(Integer rSCP) {
		RSCP = rSCP;
	}
	public Double getRSCP_1() {
		return RSCP_1;
	}
	public void setRSCP_1(Double rSCP_1) {
		RSCP_1 = rSCP_1;
	}
	public Double getRSCP_2() {
		return RSCP_2;
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
	public Double getRSCP_5() {
		return RSCP_5;
	}
	public void setRSCP_5(Double rSCP_5) {
		RSCP_5 = rSCP_5;
	}
	public Double getRSCP_6() {
		return RSCP_6;
	}
	public void setRSCP_6(Double rSCP_6) {
		RSCP_6 = rSCP_6;
	}
	public Integer getEC_NO() {
		return EC_NO;
	}
	public void setEC_NO(Integer eC_NO) {
		EC_NO = eC_NO;
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
	public Double getEC_NO_5() {
		return EC_NO_5;
	}
	public void setEC_NO_5(Double eC_NO_5) {
		EC_NO_5 = eC_NO_5;
	}
	public Double getEC_NO_6() {
		return EC_NO_6;
	}
	public void setEC_NO_6(Double eC_NO_6) {
		EC_NO_6 = eC_NO_6;
	}
	public Integer gettxpower() {
		return txpower;
	}

	public Integer getTxpower() {
		return txpower;
	}
	public void setTxpower(Integer txpower) {
		this.txpower = txpower;
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
	public Double getTxpower_5() {
		return txpower_5;
	}
	public void setTxpower_5(Double txpower_5) {
		this.txpower_5 = txpower_5;
	}
	public Double getTxpower_6() {
		return txpower_6;
	}
	public void setTxpower_6(Double txpower_6) {
		this.txpower_6 = txpower_6;
	}
	public Integer getFTP_SPEED_UP() {
		return FTP_SPEED_UP;
	}
	public void setFTP_SPEED_UP(Integer fTP_SPEED_UP) {
		FTP_SPEED_UP = fTP_SPEED_UP;
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
	public Double getFTP_SPEED_UP_5() {
		return FTP_SPEED_UP_5;
	}
	public void setFTP_SPEED_UP_5(Double fTP_SPEED_UP_5) {
		FTP_SPEED_UP_5 = fTP_SPEED_UP_5;
	}
	public Double getFTP_SPEED_UP_6() {
		return FTP_SPEED_UP_6;
	}
	public void setFTP_SPEED_UP_6(Double fTP_SPEED_UP_6) {
		FTP_SPEED_UP_6 = fTP_SPEED_UP_6;
	}
	public Integer getFTP_SPEED_DOWN() {
		return FTP_SPEED_DOWN;
	}
	public void setFTP_SPEED_DOWN(Integer fTP_SPEED_DOWN) {
		FTP_SPEED_DOWN = fTP_SPEED_DOWN;
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
	public Double getFTP_SPEED_DOWN_5() {
		return FTP_SPEED_DOWN_5;
	}
	public void setFTP_SPEED_DOWN_5(Double fTP_SPEED_DOWN_5) {
		FTP_SPEED_DOWN_5 = fTP_SPEED_DOWN_5;
	}
	public Double getFTP_SPEED_DOWN_6() {
		return FTP_SPEED_DOWN_6;
	}
	public void setFTP_SPEED_DOWN_6(Double fTP_SPEED_DOWN_6) {
		FTP_SPEED_DOWN_6 = fTP_SPEED_DOWN_6;
	}
	public Integer getRXLEV_Sub() {
		return RXLEV_Sub;
	}
	
	public Integer getRXQUAL_Sub() {
		return RXQUAL_Sub;
	}
	
	public Double getRXQUAL_Sub_1() {
		return RXQUAL_Sub_1;
	}

	public Integer getC_I() {
		return C_I;
	}
	public void setC_I(Integer c_I) {
		C_I = c_I;
	}
	public Double getC_I_1() {
		return C_I_1;
	}
	public void setC_I_1(Double c_I_1) {
		C_I_1 = c_I_1;
	}
	public Double getC_I_2() {
		return C_I_2;
	}
	public void setC_I_2(Double c_I_2) {
		C_I_2 = c_I_2;
	}
	public Double getC_I_3() {
		return C_I_3;
	}
	public void setC_I_3(Double c_I_3) {
		C_I_3 = c_I_3;
	}
	public Double getC_I_4() {
		return C_I_4;
	}
	public void setC_I_4(Double c_I_4) {
		C_I_4 = c_I_4;
	}
	public Double getC_I_5() {
		return C_I_5;
	}
	public void setC_I_5(Double c_I_5) {
		C_I_5 = c_I_5;
	}
	public Double getC_I_6() {
		return C_I_6;
	}
	public void setC_I_6(Double c_I_6) {
		C_I_6 = c_I_6;
	}
	public Integer getMOS() {
		return MOS;
	}
	public void setMOS(Integer mOS) {
		MOS = mOS;
	}
	public Double getMOS_1() {
		return MOS_1;
	}
	public void setMOS_1(Double mOS_1) {
		MOS_1 = mOS_1;
	}
	public Double getMOS_2() {
		return MOS_2;
	}
	public void setMOS_2(Double mOS_2) {
		MOS_2 = mOS_2;
	}
	public Double getMOS_3() {
		return MOS_3;
	}
	public void setMOS_3(Double mOS_3) {
		MOS_3 = mOS_3;
	}
	public Double getMOS_4() {
		return MOS_4;
	}
	public void setMOS_4(Double mOS_4) {
		MOS_4 = mOS_4;
	}
	public Double getMOS_5() {
		return MOS_5;
	}
	public void setMOS_5(Double mOS_5) {
		MOS_5 = mOS_5;
	}
	public Double getMOS_6() {
		return MOS_6;
	}
	public void setMOS_6(Double mOS_6) {
		MOS_6 = mOS_6;
	}
	private Double MOS_2;
     private Double MOS_3;
     private Double MOS_4;
     private Double MOS_5;
     private Double MOS_6;
	public Integer getInside() {
		return inside;
	}
	public void setInside(Integer inside) {
		this.inside = inside;
	}
	public Integer getRSRP() {
		return RSRP;
	}
	public void setRSRP(Integer rSRP) {
		RSRP = rSRP;
	}
	public Double getRSRP_1() {
		return RSRP_1;
	}
	public void setRSRP_1(Double rSRP_1) {
		RSRP_1 = rSRP_1;
	}
	public Double getRSRP_2() {
		return RSRP_2;
	}
	public void setRSRP_2(Double rSRP_2) {
		RSRP_2 = rSRP_2;
	}
	public Double getRSRP_3() {
		return RSRP_3;
	}
	public void setRSRP_3(Double rSRP_3) {
		RSRP_3 = rSRP_3;
	}
	public Double getRSRP_4() {
		return RSRP_4;
	}
	public void setRSRP_4(Double rSRP_4) {
		RSRP_4 = rSRP_4;
	}
	public Double getRSRP_5() {
		return RSRP_5;
	}
	public void setRSRP_5(Double rSRP_5) {
		RSRP_5 = rSRP_5;
	}
	public Double getRSRP_6() {
		return RSRP_6;
	}
	public void setRSRP_6(Double rSRP_6) {
		RSRP_6 = rSRP_6;
	}
	public Integer getSNR() {
		return SNR;
	}
	public void setSNR(Integer sNR) {
		SNR = sNR;
	}
	public Double getSNR_1() {
		return SNR_1;
	}
	public void setSNR_1(Double sNR_1) {
		SNR_1 = sNR_1;
	}
	public Double getSNR_2() {
		return SNR_2;
	}
	public void setSNR_2(Double sNR_2) {
		SNR_2 = sNR_2;
	}
	public Double getSNR_3() {
		return SNR_3;
	}
	public void setSNR_3(Double sNR_3) {
		SNR_3 = sNR_3;
	}
	public Double getSNR_4() {
		return SNR_4;
	}
	public void setSNR_4(Double sNR_4) {
		SNR_4 = sNR_4;
	}
	public Double getSNR_5() {
		return SNR_5;
	}
	public void setSNR_5(Double sNR_5) {
		SNR_5 = sNR_5;
	}
	public Double getSNR_6() {
		return SNR_6;
	}
	public void setSNR_6(Double sNR_6) {
		SNR_6 = sNR_6;
	}
	public Integer getRSRQ() {
		return RSRQ;
	}
	public void setRSRQ(Integer rSRQ) {
		RSRQ = rSRQ;
	}
	public Double getRSRQ_1() {
		return RSRQ_1;
	}
	public void setRSRQ_1(Double rSRQ_1) {
		RSRQ_1 = rSRQ_1;
	}
	public Double getRSRQ_2() {
		return RSRQ_2;
	}
	public void setRSRQ_2(Double rSRQ_2) {
		RSRQ_2 = rSRQ_2;
	}
	public Double getRSRQ_3() {
		return RSRQ_3;
	}
	public void setRSRQ_3(Double rSRQ_3) {
		RSRQ_3 = rSRQ_3;
	}
	public Double getRSRQ_4() {
		return RSRQ_4;
	}
	public void setRSRQ_4(Double rSRQ_4) {
		RSRQ_4 = rSRQ_4;
	}
	public Double getRSRQ_5() {
		return RSRQ_5;
	}
	public void setRSRQ_5(Double rSRQ_5) {
		RSRQ_5 = rSRQ_5;
	}
	public Double getRSRQ_6() {
		return RSRQ_6;
	}
	public void setRSRQ_6(Double rSRQ_6) {
		RSRQ_6 = rSRQ_6;
	}
	public Integer getNetType() {
		return netType;
	}
	public void setNetType(Integer netType) {
		this.netType = netType;
	}
	
	
}
