package com.complaint.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.complaint.action.JsonDateSerializer;

public class TestMasterlog implements Serializable{
    /**
	 * 
	 */ 
	
	private static final long serialVersionUID = -8837051198459997910L;

	private String flowid;

    private String serialno;

    private Short orders;

    private Date testtime;
    
    private String id;
    
    private Integer areaid;


	private String imei;

    private Short inside;

    private Short sceneid;

    private Short density;

    private Short obstruct;

    private String description;

    private Short isindoor;
    
    private BigDecimal longitudeBmap;

    private BigDecimal latitudeBmap;

    private BigDecimal longitudeModify;

    private BigDecimal latitudeModify;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Short netsystem;

    private Short teststatus;

    private Short gpsType;
	private Date testendtime;
	private BigDecimal duration;
	private BigDecimal space;
	private String callphone;
	
    private String  scenName;
    private int isRepair;
    private String version; 
    private Integer verify;
    private Integer breakFlag;
    
    
    
	public Integer getBreakFlag() {
		return breakFlag;
	}

	public void setBreakFlag(Integer breakFlag) {
		this.breakFlag = breakFlag;
	}

	public Integer getVerify() {
		return verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

	private Integer room_type;//0表示其他, 1表示2G室分, 2表示3G室分,3 表示 2g+3g室分
    
		public Integer getRoom_type() {
		return room_type;
	}

	public void setRoom_type(Integer room_type) {
		this.room_type = room_type;
	}

		public String getScenName() {
			return scenName;
		}

		public void setScenName(String scenName) {
			this.scenName = scenName;
		}
	
	public Date getTestendtime() {
		return testendtime;
	}

	public void setTestendtime(Date testendtime) {
		this.testendtime = testendtime;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public BigDecimal getSpace() {
		return space;
	}

	public void setSpace(BigDecimal space) {
		this.space = space;
	}

	public String getCallphone() {
		return callphone;
	}

	public void setCallphone(String callphone) {
		this.callphone = callphone;
	}

	public Short getGpsType() {
		return gpsType;
	}

	public void setGpsType(Short gpsType) {
		this.gpsType = gpsType;
	}

	public Short getNetsystem() {
		return netsystem;
	}

	public void setNetsystem(Short netsystem) {
		this.netsystem = netsystem;
	}

	public Short getTeststatus() {
		return teststatus;
	}

	public void setTeststatus(Short teststatus) {
		this.teststatus = teststatus;
	}

	private Short calltype;

    private Short ftpUpdown;

    private BigDecimal ftpMaxSpeed;

    private BigDecimal ftpMinSpeed;

    private BigDecimal ftpAvgSpeed;
    
    private BigDecimal ftpMaxSpeedLte;

    private BigDecimal ftpMinSpeedLte;

    private BigDecimal ftpAvgSpeedLte;
    
    private String testphone;
    
    private String testaddress;
    
    private String failure;
    
    private Date updatetime;
    
    

	private Double pinglo;//ping丢包
	private Double pingdmax;//ping最大延时
	private Double pingdmix;//ping最小延时
	private Double pingdavg;//ping平均延时
	private Double httptmax;//HTTP最大响应时间
	private Double httptmix;//HTTP最小响应时间
	private Double httptavg;//HTTP平均响应时间
	private Double httpsmax;//HTTP最大下载速度
	private Double httpsmin;//HTTP最小下载速度
	private Double httpsavg;//HTTP平均下载速度
	
	private Integer plostate;//ping丢包状态
	private Integer pdmaxstate;//ping最大延时状态
	private Integer pdminstate;//ping最小延时状态
	private Integer pdavgstate;//ping平均延时状态
	private Integer htmaxstate;//HTTP最大响应时间状态
	private Integer htminstate;//HTTP最小响应时间状态
	private Integer htavgstate;//HTTP平均响应时间状态
	private Integer hsmaxstate;//HTTP最大下载速度状态
	private Integer hsminstate;//HTTP最小下载速度状态
	private Integer hsavgstate;//HTTP平均下载速度状态
	
	
	//工单相关信息
	//系统接单时间
	 private Date submitDatetime;
//派单员工号
	    private String acceptHuman;
//受理路径

	    private String admissiblePath;
//业务产品分类
	    private String productName;
	    //模板内容
	    private String templateContent;
	    //投诉内容
	    private String complaint;

//详细投诉地址
	    private String problemsAddress;
	    //网络类型
	    private String netWorktype;


		///受理号码
	    private String acceptanceNumber;
///用户品牌
	    private String usersBrand;
	    private String customerContact;
//区域
	    private String areaname;

///测试次数
	    private Integer testNumber;
	    
	    
	    private Short orderType;  //工单类型
	    private Date createDate;  //工单创建时间
	    private Short breakflag;  //测试环节


    public String getCustomerContact() {
			return customerContact;
		}

		public void setCustomerContact(String customerContact) {
			this.customerContact = customerContact;
		}

	public Date getSubmitDatetime() {
			return submitDatetime;
		}

		public void setSubmitDatetime(Date submitDatetime) {
			this.submitDatetime = submitDatetime;
		}

		public String getAcceptHuman() {
			return acceptHuman;
		}

		public void setAcceptHuman(String acceptHuman) {
			this.acceptHuman = acceptHuman;
		}

		public String getAdmissiblePath() {
			return admissiblePath;
		}

		public void setAdmissiblePath(String admissiblePath) {
			this.admissiblePath = admissiblePath;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getTemplateContent() {
			return templateContent;
		}

		public void setTemplateContent(String templateContent) {
			this.templateContent = templateContent;
		}

		public String getComplaint() {
			return complaint;
		}

		public void setComplaint(String complaint) {
			this.complaint = complaint;
		}

		public String getProblemsAddress() {
			return problemsAddress;
		}

		public void setProblemsAddress(String problemsAddress) {
			this.problemsAddress = problemsAddress;
		}

		public String getAcceptanceNumber() {
			return acceptanceNumber;
		}

		public void setAcceptanceNumber(String acceptanceNumber) {
			this.acceptanceNumber = acceptanceNumber;
		}

		public String getUsersBrand() {
			return usersBrand;
		}

		public void setUsersBrand(String usersBrand) {
			this.usersBrand = usersBrand;
		}


		public String getAreaname() {
			return areaname;
		}

		public void setAreaname(String areaname) {
			this.areaname = areaname;
		}

		public Integer getTestNumber() {
			return testNumber;
		}

		public void setTestNumber(Integer testNumber) {
			this.testNumber = testNumber;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	public Short getOrders() {
        return orders;
    }

    public void setOrders(Short orders) {
        this.orders = orders;
    }
    @JsonSerialize(using = JsonDateSerializer.class) 
    public Date getTesttime() {
        return testtime;
    }

    public void setTesttime(Date testtime) {
        this.testtime = testtime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Short getInside() {
        return inside;
    }

    public void setInside(Short inside) {
        this.inside = inside;
    }

    public Short getSceneid() {
        return sceneid;
    }

    public void setSceneid(Short sceneid) {
        this.sceneid = sceneid;
    }

    public Short getDensity() {
        return density;
    }

    public void setDensity(Short density) {
        this.density = density;
    }

    public Short getObstruct() {
        return obstruct;
    }

    public void setObstruct(Short obstruct) {
        this.obstruct = obstruct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Short getIsindoor() {
        return isindoor;
    }

    public void setIsindoor(Short isindoor) {
        this.isindoor = isindoor;
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



    public Short getCalltype() {
        return calltype;
    }

    public void setCalltype(Short calltype) {
        this.calltype = calltype;
    }

    public Short getFtpUpdown() {
        return ftpUpdown;
    }

    public void setFtpUpdown(Short ftpUpdown) {
        this.ftpUpdown = ftpUpdown;
    }

    public BigDecimal getFtpMaxSpeed() {
        return ftpMaxSpeed;
    }

    public void setFtpMaxSpeed(BigDecimal ftpMaxSpeed) {
        this.ftpMaxSpeed = ftpMaxSpeed;
    }

    public BigDecimal getFtpMinSpeed() {
        return ftpMinSpeed;
    }

    public void setFtpMinSpeed(BigDecimal ftpMinSpeed) {
        this.ftpMinSpeed = ftpMinSpeed;
    }

    public BigDecimal getFtpAvgSpeed() {
        return ftpAvgSpeed;
    }

    public void setFtpAvgSpeed(BigDecimal ftpAvgSpeed) {
        this.ftpAvgSpeed = ftpAvgSpeed;
    }



	public Double getPinglo() {
		return pinglo;
	}

	public void setPinglo(Double pinglo) {
		this.pinglo = pinglo;
	}

	public Double getPingdmax() {
		return pingdmax;
	}

	public void setPingdmax(Double pingdmax) {
		this.pingdmax = pingdmax;
	}

	public Double getPingdmix() {
		return pingdmix;
	}

	public void setPingdmix(Double pingdmix) {
		this.pingdmix = pingdmix;
	}

	public Double getPingdavg() {
		return pingdavg;
	}

	public void setPingdavg(Double pingdavg) {
		this.pingdavg = pingdavg;
	}

	public Double getHttptmax() {
		return httptmax;
	}

	public void setHttptmax(Double httptmax) {
		this.httptmax = httptmax;
	}

	public Double getHttptmix() {
		return httptmix;
	}

	public void setHttptmix(Double httptmix) {
		this.httptmix = httptmix;
	}

	public Double getHttptavg() {
		return httptavg;
	}

	public void setHttptavg(Double httptavg) {
		this.httptavg = httptavg;
	}

	public Double getHttpsmax() {
		return httpsmax;
	}

	public void setHttpsmax(Double httpsmax) {
		this.httpsmax = httpsmax;
	}

	public Double getHttpsmin() {
		return httpsmin;
	}

	public void setHttpsmin(Double httpsmin) {
		this.httpsmin = httpsmin;
	}

	public Double getHttpsavg() {
		return httpsavg;
	}

	public void setHttpsavg(Double httpsavg) {
		this.httpsavg = httpsavg;
	}

	public String getTestphone() {
		return testphone;
	}

	public void setTestphone(String testphone) {
		this.testphone = testphone;
	}

	public String getTestaddress() {
		return testaddress;
	}

	public void setTestaddress(String testaddress) {
		this.testaddress = testaddress;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

    public String getNetWorktype() {
			return netWorktype;
		}

		public void setNetWorktype(String netWorktype) {
			this.netWorktype = netWorktype;
		}

		public Integer getPlostate() {
			return plostate;
		}

		public void setPlostate(Integer plostate) {
			this.plostate = plostate;
		}

		public Integer getPdmaxstate() {
			return pdmaxstate;
		}

		public void setPdmaxstate(Integer pdmaxstate) {
			this.pdmaxstate = pdmaxstate;
		}

		

		public Integer getPdminstate() {
			return pdminstate;
		}

		public void setPdminstate(Integer pdminstate) {
			this.pdminstate = pdminstate;
		}

		public Integer getPdavgstate() {
			return pdavgstate;
		}

		public void setPdavgstate(Integer pdavgstate) {
			this.pdavgstate = pdavgstate;
		}

		public Integer getHtmaxstate() {
			return htmaxstate;
		}

		public void setHtmaxstate(Integer htmaxstate) {
			this.htmaxstate = htmaxstate;
		}


		public Integer getHtavgstate() {
			return htavgstate;
		}

		public void setHtavgstate(Integer htavgstate) {
			this.htavgstate = htavgstate;
		}

		public Integer getHsmaxstate() {
			return hsmaxstate;
		}

		public void setHsmaxstate(Integer hsmaxstate) {
			this.hsmaxstate = hsmaxstate;
		}

		public Integer getHsminstate() {
			return hsminstate;
		}

		public void setHsminstate(Integer hsminstate) {
			this.hsminstate = hsminstate;
		}

		public Integer getHsavgstate() {
			return hsavgstate;
		}

		public void setHsavgstate(Integer hsavgstate) {
			this.hsavgstate = hsavgstate;
		}

		public Integer getHtminstate() {
			return htminstate;
		}

		public void setHtminstate(Integer htminstate) {
			this.htminstate = htminstate;
		}

		public int getIsRepair() {
			return isRepair;
		}

		public void setIsRepair(int isRepair) {
			this.isRepair = isRepair;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
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

		public Short getOrderType() {
			return orderType;
		}

		public void setOrderType(Short orderType) {
			this.orderType = orderType;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public Short getBreakflag() {
			return breakflag;
		}

		public void setBreakflag(Short breakflag) {
			this.breakflag = breakflag;
		}

		public BigDecimal getFtpMaxSpeedLte() {
			return ftpMaxSpeedLte;
		}

		public void setFtpMaxSpeedLte(BigDecimal ftpMaxSpeedLte) {
			this.ftpMaxSpeedLte = ftpMaxSpeedLte;
		}

		public BigDecimal getFtpMinSpeedLte() {
			return ftpMinSpeedLte;
		}

		public void setFtpMinSpeedLte(BigDecimal ftpMinSpeedLte) {
			this.ftpMinSpeedLte = ftpMinSpeedLte;
		}

		public BigDecimal getFtpAvgSpeedLte() {
			return ftpAvgSpeedLte;
		}

		public void setFtpAvgSpeedLte(BigDecimal ftpAvgSpeedLte) {
			this.ftpAvgSpeedLte = ftpAvgSpeedLte;
		}

		
}
