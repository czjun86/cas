package com.complaint.model;

public class QualityConfig {
	private Integer id;// id
	private Double svgstep;// 均值步长
	private Double annularstep;// 环比步长
	private Integer kpi;// 指标

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getSvgstep() {
		return svgstep;
	}

	public void setSvgstep(Double svgstep) {
		this.svgstep = svgstep;
	}

	public Double getAnnularstep() {
		return annularstep;
	}

	public void setAnnularstep(Double annularstep) {
		this.annularstep = annularstep;
	}

	public Integer getKpi() {
		return kpi;
	}

	public void setKpi(Integer kpi) {
		this.kpi = kpi;
	}

}
