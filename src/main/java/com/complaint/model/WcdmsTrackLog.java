package com.complaint.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.complaint.action.JsonDateSerializer;

public class WcdmsTrackLog {
    private String wcdmaid;

    private String flowid;

    private String uuid;
    
    private Integer areaid;

    private Short inside;

    private Date epTime;
    
    private Date testtime;
    
    private Short ftptype;

    private BigDecimal longitude;

    private BigDecimal latitude;
    
    private BigDecimal longitudeBmap;

    private BigDecimal latitudeBmap;

    private BigDecimal longitudeModify;

    private BigDecimal latitudeModify;

    private BigDecimal positionX;

    private BigDecimal positionY;

    private Short realnetType;

    private Long lac;

    private Long cid;

    private Integer psc;

    private Integer frequl;
    
    private Integer freqdl;
    
    private Integer rscp;

    private Integer ecNo;

    private BigDecimal txpower;

    private BigDecimal ftpSpeed;

    private Integer aPsc1;

    private Integer aRscp1;

    private Integer aEcNo1;

    private Integer aArfcn1;

    private Integer aPsc2;

    private Integer aRscp2;

    private Integer aEcNo2;

    private Integer aArfcn2;

    private Integer aPsc3;

    private Integer aRscp3;

    private Integer aEcNo3;

    private Integer aArfcn3;

    private Integer mPsc1;

    private Integer mRscp1;

    private Integer mEcNo1;

    private Integer mArfcn1;

    private Integer mPsc2;

    private Integer mRscp2;

    private Integer mEcNo2;

    private Integer mArfcn2;

    private Integer mPsc3;

    private Integer mRscp3;

    private Integer mEcNo3;

    private Integer mArfcn3;

    private Integer mPsc4;

    private Integer mRscp4;

    private Integer mEcNo4;

    private Integer mArfcn4;

    private Integer mPsc5;

    private Integer mRscp5;

    private Integer mEcNo5;

    private Integer mArfcn5;

    private Integer mPsc6;

    private Integer mRscp6;

    private Integer mEcNo6;

    private Integer mArfcn6;

    private Integer dPsc1;

    private Integer dRscp1;

    private Integer dEcNo1;

    private Integer dArfcn1;

    private Integer dPsc2;

    private Integer dRscp2;

    private Integer dEcNo2;

    private Integer dArfcn2;

    private Short gpsType;
	
    private Long lac_1;

    private Long cid_1;
    private Long lac_2;

    private Long cid_2;
    private Short arithmeticType;
    private Long range;

    private Short isequal;
    
    private Short type;
    
    
   	public Date getTesttime() {
		return testtime;
	}

	public void setTesttime(Date testtime) {
		this.testtime = testtime;
	}

	public Short getGpsType() {
   		return gpsType;
   	}

   	public void setGpsType(Short gpsType) {
   		this.gpsType = gpsType;
   	}
    
    public String getWcdmaid() {
        return wcdmaid;
    }

    public void setWcdmaid(String wcdmaid) {
        this.wcdmaid = wcdmaid == null ? null : wcdmaid.trim();
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Integer getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	public Short getInside() {
        return inside;
    }

    public void setInside(Short inside) {
        this.inside = inside;
    }
    public Date getEpTime() {
        return epTime;
    }

    public void setEpTime(Date epTime) {
        this.epTime = epTime;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitudeModify() {
        return longitudeModify;
    }

    public void setLongitudeModify(BigDecimal longitudeModify) {
        this.longitudeModify = longitudeModify;
    }

    public BigDecimal getLatitudeModify() {
        return latitudeModify;
    }

    public void setLatitudeModify(BigDecimal latitudeModify) {
        this.latitudeModify = latitudeModify;
    }

    public BigDecimal getPositionX() {
        return positionX;
    }

    public void setPositionX(BigDecimal positionX) {
        this.positionX = positionX;
    }

    public BigDecimal getPositionY() {
        return positionY;
    }

    public void setPositionY(BigDecimal positionY) {
        this.positionY = positionY;
    }

    public Short getRealnetType() {
        return realnetType;
    }

    public void setRealnetType(Short realnetType) {
        this.realnetType = realnetType;
    }

    public Long getLac() {
        return lac;
    }

    public void setLac(Long lac) {
        this.lac = lac;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getPsc() {
        return psc;
    }

    public void setPsc(Integer psc) {
        this.psc = psc;
    }
    

    public Integer getFrequl() {
		return frequl;
	}

	public void setFrequl(Integer frequl) {
		this.frequl = frequl;
	}

	public Integer getFreqdl() {
		return freqdl;
	}

	public void setFreqdl(Integer freqdl) {
		this.freqdl = freqdl;
	}

	public Integer getRscp() {
        return rscp;
    }

    public void setRscp(Integer rscp) {
        this.rscp = rscp;
    }

    public Integer getEcNo() {
        return ecNo;
    }

    public void setEcNo(Integer ecNo) {
        this.ecNo = ecNo;
    }

    public BigDecimal getTxpower() {
        return txpower;
    }

    public void setTxpower(BigDecimal txpower) {
        this.txpower = txpower;
    }

    public BigDecimal getFtpSpeed() {
        return ftpSpeed;
    }

    public void setFtpSpeed(BigDecimal ftpSpeed) {
        this.ftpSpeed = ftpSpeed;
    }

    public Integer getaPsc1() {
        return aPsc1;
    }

    public void setaPsc1(Integer aPsc1) {
        this.aPsc1 = aPsc1;
    }

    public Integer getaRscp1() {
        return aRscp1;
    }

    public void setaRscp1(Integer aRscp1) {
        this.aRscp1 = aRscp1;
    }

    public Integer getaEcNo1() {
        return aEcNo1;
    }

    public void setaEcNo1(Integer aEcNo1) {
        this.aEcNo1 = aEcNo1;
    }

    public Integer getaArfcn1() {
        return aArfcn1;
    }

    public void setaArfcn1(Integer aArfcn1) {
        this.aArfcn1 = aArfcn1;
    }

    public Integer getaPsc2() {
        return aPsc2;
    }

    public void setaPsc2(Integer aPsc2) {
        this.aPsc2 = aPsc2;
    }

    public Integer getaRscp2() {
        return aRscp2;
    }

    public void setaRscp2(Integer aRscp2) {
        this.aRscp2 = aRscp2;
    }

    public Integer getaEcNo2() {
        return aEcNo2;
    }

    public void setaEcNo2(Integer aEcNo2) {
        this.aEcNo2 = aEcNo2;
    }

    public Integer getaArfcn2() {
        return aArfcn2;
    }

    public void setaArfcn2(Integer aArfcn2) {
        this.aArfcn2 = aArfcn2;
    }

    public Integer getaPsc3() {
        return aPsc3;
    }

    public void setaPsc3(Integer aPsc3) {
        this.aPsc3 = aPsc3;
    }

    public Integer getaRscp3() {
        return aRscp3;
    }

    public void setaRscp3(Integer aRscp3) {
        this.aRscp3 = aRscp3;
    }

    public Integer getaEcNo3() {
        return aEcNo3;
    }

    public void setaEcNo3(Integer aEcNo3) {
        this.aEcNo3 = aEcNo3;
    }

    public Integer getaArfcn3() {
        return aArfcn3;
    }

    public void setaArfcn3(Integer aArfcn3) {
        this.aArfcn3 = aArfcn3;
    }

    public Integer getmPsc1() {
        return mPsc1;
    }

    public void setmPsc1(Integer mPsc1) {
        this.mPsc1 = mPsc1;
    }

    public Integer getmRscp1() {
        return mRscp1;
    }

    public void setmRscp1(Integer mRscp1) {
        this.mRscp1 = mRscp1;
    }

    public Integer getmEcNo1() {
        return mEcNo1;
    }

    public void setmEcNo1(Integer mEcNo1) {
        this.mEcNo1 = mEcNo1;
    }

    public Integer getmArfcn1() {
        return mArfcn1;
    }

    public void setmArfcn1(Integer mArfcn1) {
        this.mArfcn1 = mArfcn1;
    }

    public Integer getmPsc2() {
        return mPsc2;
    }

    public void setmPsc2(Integer mPsc2) {
        this.mPsc2 = mPsc2;
    }

    public Integer getmRscp2() {
        return mRscp2;
    }

    public void setmRscp2(Integer mRscp2) {
        this.mRscp2 = mRscp2;
    }

    public Integer getmEcNo2() {
        return mEcNo2;
    }

    public void setmEcNo2(Integer mEcNo2) {
        this.mEcNo2 = mEcNo2;
    }

    public Integer getmArfcn2() {
        return mArfcn2;
    }

    public void setmArfcn2(Integer mArfcn2) {
        this.mArfcn2 = mArfcn2;
    }

    public Integer getmPsc3() {
        return mPsc3;
    }

    public void setmPsc3(Integer mPsc3) {
        this.mPsc3 = mPsc3;
    }

    public Integer getmRscp3() {
        return mRscp3;
    }

    public void setmRscp3(Integer mRscp3) {
        this.mRscp3 = mRscp3;
    }

    public Integer getmEcNo3() {
        return mEcNo3;
    }

    public void setmEcNo3(Integer mEcNo3) {
        this.mEcNo3 = mEcNo3;
    }

    public Integer getmArfcn3() {
        return mArfcn3;
    }

    public void setmArfcn3(Integer mArfcn3) {
        this.mArfcn3 = mArfcn3;
    }

    public Integer getmPsc4() {
        return mPsc4;
    }

    public void setmPsc4(Integer mPsc4) {
        this.mPsc4 = mPsc4;
    }

    public Integer getmRscp4() {
        return mRscp4;
    }

    public void setmRscp4(Integer mRscp4) {
        this.mRscp4 = mRscp4;
    }

    public Integer getmEcNo4() {
        return mEcNo4;
    }

    public void setmEcNo4(Integer mEcNo4) {
        this.mEcNo4 = mEcNo4;
    }

    public Integer getmArfcn4() {
        return mArfcn4;
    }

    public void setmArfcn4(Integer mArfcn4) {
        this.mArfcn4 = mArfcn4;
    }

    public Integer getmPsc5() {
        return mPsc5;
    }

    public void setmPsc5(Integer mPsc5) {
        this.mPsc5 = mPsc5;
    }

    public Integer getmRscp5() {
        return mRscp5;
    }

    public void setmRscp5(Integer mRscp5) {
        this.mRscp5 = mRscp5;
    }

    public Integer getmEcNo5() {
        return mEcNo5;
    }

    public void setmEcNo5(Integer mEcNo5) {
        this.mEcNo5 = mEcNo5;
    }

    public Integer getmArfcn5() {
        return mArfcn5;
    }

    public void setmArfcn5(Integer mArfcn5) {
        this.mArfcn5 = mArfcn5;
    }

    public Integer getmPsc6() {
        return mPsc6;
    }

    public void setmPsc6(Integer mPsc6) {
        this.mPsc6 = mPsc6;
    }

    public Integer getmRscp6() {
        return mRscp6;
    }

    public void setmRscp6(Integer mRscp6) {
        this.mRscp6 = mRscp6;
    }

    public Integer getmEcNo6() {
        return mEcNo6;
    }

    public void setmEcNo6(Integer mEcNo6) {
        this.mEcNo6 = mEcNo6;
    }

    public Integer getmArfcn6() {
        return mArfcn6;
    }

    public void setmArfcn6(Integer mArfcn6) {
        this.mArfcn6 = mArfcn6;
    }

    public Integer getdPsc1() {
        return dPsc1;
    }

    public void setdPsc1(Integer dPsc1) {
        this.dPsc1 = dPsc1;
    }

    public Integer getdRscp1() {
        return dRscp1;
    }

    public void setdRscp1(Integer dRscp1) {
        this.dRscp1 = dRscp1;
    }

    public Integer getdEcNo1() {
        return dEcNo1;
    }

    public void setdEcNo1(Integer dEcNo1) {
        this.dEcNo1 = dEcNo1;
    }

    public Integer getdArfcn1() {
        return dArfcn1;
    }

    public void setdArfcn1(Integer dArfcn1) {
        this.dArfcn1 = dArfcn1;
    }

    public Integer getdPsc2() {
        return dPsc2;
    }

    public void setdPsc2(Integer dPsc2) {
        this.dPsc2 = dPsc2;
    }

    public Integer getdRscp2() {
        return dRscp2;
    }

    public void setdRscp2(Integer dRscp2) {
        this.dRscp2 = dRscp2;
    }

    public Integer getdEcNo2() {
        return dEcNo2;
    }

    public void setdEcNo2(Integer dEcNo2) {
        this.dEcNo2 = dEcNo2;
    }

    public Integer getdArfcn2() {
        return dArfcn2;
    }

    public void setdArfcn2(Integer dArfcn2) {
        this.dArfcn2 = dArfcn2;
    }

	public Short getFtptype() {
		return ftptype;
	}

	public void setFtptype(Short ftptype) {
		this.ftptype = ftptype;
	}

	public Long getLac_1() {
		return lac_1;
	}

	public void setLac_1(Long lac_1) {
		this.lac_1 = lac_1;
	}

	public Long getCid_1() {
		return cid_1;
	}

	public void setCid_1(Long cid_1) {
		this.cid_1 = cid_1;
	}

	public Long getLac_2() {
		return lac_2;
	}

	public void setLac_2(Long lac_2) {
		this.lac_2 = lac_2;
	}

	public Long getCid_2() {
		return cid_2;
	}

	public void setCid_2(Long cid_2) {
		this.cid_2 = cid_2;
	}

	public Short getArithmeticType() {
		return arithmeticType;
	}

	public void setArithmeticType(Short arithmeticType) {
		this.arithmeticType = arithmeticType;
	}

	public Long getRange() {
		return range;
	}

	public void setRange(Long range) {
		this.range = range;
	}

	public Short getIsequal() {
		return isequal;
	}

	public void setIsequal(Short isequal) {
		this.isequal = isequal;
	}

	public BigDecimal getLongitudeBmap() {
		return longitudeBmap;
	}

	public void setLongitudeBmap(BigDecimal longitudeBmap) {
		this.longitudeBmap = longitudeBmap;
	}

	public BigDecimal getLatitudeBmap() {
		return latitudeBmap;
	}

	public void setLatitudeBmap(BigDecimal latitudeBmap) {
		this.latitudeBmap = latitudeBmap;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
    
}
