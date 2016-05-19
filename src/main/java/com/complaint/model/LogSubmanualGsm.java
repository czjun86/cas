package com.complaint.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.complaint.action.JsonDateSerializer;

public class LogSubmanualGsm {
    private String gsmid;

    private String flowid;

    private String uuid;
    
    private Integer areaid;

    private Short inside;

    private Date epTime;

    private BigDecimal longitude;

    private BigDecimal latitude;
    
    private BigDecimal longitudeBmap;

    private BigDecimal latitudeBmap;

    private BigDecimal longitudeModify;

    private BigDecimal latitudeModify;

    private BigDecimal positionX;

    private BigDecimal positionY;

    private Short realnetType;
    
    private Date testtime;

    private Long lac;

    private Long cid;

    private Long bsic;

    private Long bcch;

    private Long rxlevSub;

    private BigDecimal rxqualSub;

    private BigDecimal cI;

    private BigDecimal txpower;

    private Long mos;

    private Long bsic1;

    private Long bcch1;

    private Long rxlev1;

    private Long bsic2;

    private Long bcch2;

    private Long rxlev2;

    private Long bsic3;

    private Long bcch3;

    private Long rxlev3;

    private Long bsic4;

    private Long bcch4;

    private Long rxlev4;

    private Long bsic5;

    private Long bcch5;

    private Long rxlev5;

    private Long bsic6;

    private Long bcch6;

    private Long rxlev6;

    private Short gpsType;

    private Integer ta;

    private Long rxlevFull;

    private BigDecimal rxqualFull;

    private Integer c1;

    private Integer c2;

    private Integer c11;

    private Integer c21;

    private Integer c12;

    private Integer c22;

    private Integer c13;

    private Integer c23;

    private Integer c14;

    private Integer c24;

    private Integer c15;

    private Integer c25;

    private Integer c16;

    private Integer c26;

    
    public Date getTesttime() {
		return testtime;
	}

	public void setTesttime(Date testtime) {
		this.testtime = testtime;
	}

	public String getGsmid() {
        return gsmid;
    }

    public void setGsmid(String gsmid) {
        this.gsmid = gsmid == null ? null : gsmid.trim();
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

    public Long getBsic() {
        return bsic;
    }

    public void setBsic(Long bsic) {
        this.bsic = bsic;
    }

    public Long getBcch() {
        return bcch;
    }

    public void setBcch(Long bcch) {
        this.bcch = bcch;
    }

    public Long getRxlevSub() {
        return rxlevSub;
    }

    public void setRxlevSub(Long rxlevSub) {
        this.rxlevSub = rxlevSub;
    }

    public BigDecimal getRxqualSub() {
        return rxqualSub;
    }

    public void setRxqualSub(BigDecimal rxqualSub) {
        this.rxqualSub = rxqualSub;
    }

    public BigDecimal getcI() {
        return cI;
    }

    public void setcI(BigDecimal cI) {
        this.cI = cI;
    }

    public BigDecimal getTxpower() {
        return txpower;
    }

    public void setTxpower(BigDecimal txpower) {
        this.txpower = txpower;
    }

    public Long getMos() {
        return mos;
    }

    public void setMos(Long mos) {
        this.mos = mos;
    }

    public Long getBsic1() {
        return bsic1;
    }

    public void setBsic1(Long bsic1) {
        this.bsic1 = bsic1;
    }

    public Long getBcch1() {
        return bcch1;
    }

    public void setBcch1(Long bcch1) {
        this.bcch1 = bcch1;
    }

    public Long getRxlev1() {
        return rxlev1;
    }

    public void setRxlev1(Long rxlev1) {
        this.rxlev1 = rxlev1;
    }

    public Long getBsic2() {
        return bsic2;
    }

    public void setBsic2(Long bsic2) {
        this.bsic2 = bsic2;
    }

    public Long getBcch2() {
        return bcch2;
    }

    public void setBcch2(Long bcch2) {
        this.bcch2 = bcch2;
    }

    public Long getRxlev2() {
        return rxlev2;
    }

    public void setRxlev2(Long rxlev2) {
        this.rxlev2 = rxlev2;
    }

    public Long getBsic3() {
        return bsic3;
    }

    public void setBsic3(Long bsic3) {
        this.bsic3 = bsic3;
    }

    public Long getBcch3() {
        return bcch3;
    }

    public void setBcch3(Long bcch3) {
        this.bcch3 = bcch3;
    }

    public Long getRxlev3() {
        return rxlev3;
    }

    public void setRxlev3(Long rxlev3) {
        this.rxlev3 = rxlev3;
    }

    public Long getBsic4() {
        return bsic4;
    }

    public void setBsic4(Long bsic4) {
        this.bsic4 = bsic4;
    }

    public Long getBcch4() {
        return bcch4;
    }

    public void setBcch4(Long bcch4) {
        this.bcch4 = bcch4;
    }

    public Long getRxlev4() {
        return rxlev4;
    }

    public void setRxlev4(Long rxlev4) {
        this.rxlev4 = rxlev4;
    }

    public Long getBsic5() {
        return bsic5;
    }

    public void setBsic5(Long bsic5) {
        this.bsic5 = bsic5;
    }

    public Long getBcch5() {
        return bcch5;
    }

    public void setBcch5(Long bcch5) {
        this.bcch5 = bcch5;
    }

    public Long getRxlev5() {
        return rxlev5;
    }

    public void setRxlev5(Long rxlev5) {
        this.rxlev5 = rxlev5;
    }

    public Long getBsic6() {
        return bsic6;
    }

    public void setBsic6(Long bsic6) {
        this.bsic6 = bsic6;
    }

    public Long getBcch6() {
        return bcch6;
    }

    public void setBcch6(Long bcch6) {
        this.bcch6 = bcch6;
    }

    public Long getRxlev6() {
        return rxlev6;
    }

    public void setRxlev6(Long rxlev6) {
        this.rxlev6 = rxlev6;
    }

    public Short getGpsType() {
        return gpsType;
    }

    public void setGpsType(Short gpsType) {
        this.gpsType = gpsType;
    }

    public Integer getTa() {
        return ta;
    }

    public void setTa(Integer ta) {
        this.ta = ta;
    }

    public Long getRxlevFull() {
        return rxlevFull;
    }

    public void setRxlevFull(Long rxlevFull) {
        this.rxlevFull = rxlevFull;
    }

    public BigDecimal getRxqualFull() {
        return rxqualFull;
    }

    public void setRxqualFull(BigDecimal rxqualFull) {
        this.rxqualFull = rxqualFull;
    }

    public Integer getC1() {
        return c1;
    }

    public void setC1(Integer c1) {
        this.c1 = c1;
    }

    public Integer getC2() {
        return c2;
    }

    public void setC2(Integer c2) {
        this.c2 = c2;
    }

    public Integer getC11() {
        return c11;
    }

    public void setC11(Integer c11) {
        this.c11 = c11;
    }

    public Integer getC21() {
        return c21;
    }

    public void setC21(Integer c21) {
        this.c21 = c21;
    }

    public Integer getC12() {
        return c12;
    }

    public void setC12(Integer c12) {
        this.c12 = c12;
    }

    public Integer getC22() {
        return c22;
    }

    public void setC22(Integer c22) {
        this.c22 = c22;
    }

    public Integer getC13() {
        return c13;
    }

    public void setC13(Integer c13) {
        this.c13 = c13;
    }

    public Integer getC23() {
        return c23;
    }

    public void setC23(Integer c23) {
        this.c23 = c23;
    }

    public Integer getC14() {
        return c14;
    }

    public void setC14(Integer c14) {
        this.c14 = c14;
    }

    public Integer getC24() {
        return c24;
    }

    public void setC24(Integer c24) {
        this.c24 = c24;
    }

    public Integer getC15() {
        return c15;
    }

    public void setC15(Integer c15) {
        this.c15 = c15;
    }

    public Integer getC25() {
        return c25;
    }

    public void setC25(Integer c25) {
        this.c25 = c25;
    }

    public Integer getC16() {
        return c16;
    }

    public void setC16(Integer c16) {
        this.c16 = c16;
    }

    public Integer getC26() {
        return c26;
    }

    public void setC26(Integer c26) {
        this.c26 = c26;
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
    
}
