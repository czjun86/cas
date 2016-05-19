package com.complaint.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class KpiIntervalValue implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8016350507771412500L;

	private Short netType;

    private Short kpi;

    private Short serialNum;

    private BigDecimal kpiValue;

    private String kpiName;
    
    private Short scene_type;

    public Short getScene_type() {
		return scene_type;
	}

	public void setScene_type(Short scene_type) {
		this.scene_type = scene_type;
	}

	public Short getNetType() {
        return netType;
    }

    public void setNetType(Short netType) {
        this.netType = netType;
    }

    public Short getKpi() {
        return kpi;
    }

    public void setKpi(Short kpi) {
        this.kpi = kpi;
    }

    public Short getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Short serialNum) {
        this.serialNum = serialNum;
    }

    public BigDecimal getKpiValue() {
        return kpiValue;
    }

    public void setKpiValue(BigDecimal kpiValue) {
        this.kpiValue = kpiValue;
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName == null ? null : kpiName.trim();
    }
}
