package com.complaint.model;

import java.math.BigDecimal;

public class WnmsCell extends WnmsCellKey {
    private Integer wmtsId;

    private Long psc;

    private String cellArea;

    private Short cellSeqnum;

    private Short cellType;

    private BigDecimal cellLongitude;

    private BigDecimal cellLatitude;

    private String cellName;

    private BigDecimal cellPosition;

    private BigDecimal cellDip;

    private String cellFirm;

    private BigDecimal latOffset;

    private BigDecimal lngOffset;

    private String cellShortName;

    public Integer getWmtsId() {
        return wmtsId;
    }

    public void setWmtsId(Integer wmtsId) {
        this.wmtsId = wmtsId;
    }

    public Long getPsc() {
        return psc;
    }

    public void setPsc(Long psc) {
        this.psc = psc;
    }

    public String getCellArea() {
        return cellArea;
    }

    public void setCellArea(String cellArea) {
        this.cellArea = cellArea == null ? null : cellArea.trim();
    }

    public Short getCellSeqnum() {
        return cellSeqnum;
    }

    public void setCellSeqnum(Short cellSeqnum) {
        this.cellSeqnum = cellSeqnum;
    }

    public Short getCellType() {
        return cellType;
    }

    public void setCellType(Short cellType) {
        this.cellType = cellType;
    }

    public BigDecimal getCellLongitude() {
        return cellLongitude;
    }

    public void setCellLongitude(BigDecimal cellLongitude) {
        this.cellLongitude = cellLongitude;
    }

    public BigDecimal getCellLatitude() {
        return cellLatitude;
    }

    public void setCellLatitude(BigDecimal cellLatitude) {
        this.cellLatitude = cellLatitude;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName == null ? null : cellName.trim();
    }

    public BigDecimal getCellPosition() {
        return cellPosition;
    }

    public void setCellPosition(BigDecimal cellPosition) {
        this.cellPosition = cellPosition;
    }

    public BigDecimal getCellDip() {
        return cellDip;
    }

    public void setCellDip(BigDecimal cellDip) {
        this.cellDip = cellDip;
    }

    public String getCellFirm() {
        return cellFirm;
    }

    public void setCellFirm(String cellFirm) {
        this.cellFirm = cellFirm == null ? null : cellFirm.trim();
    }

    public BigDecimal getLatOffset() {
        return latOffset;
    }

    public void setLatOffset(BigDecimal latOffset) {
        this.latOffset = latOffset;
    }

    public BigDecimal getLngOffset() {
        return lngOffset;
    }

    public void setLngOffset(BigDecimal lngOffset) {
        this.lngOffset = lngOffset;
    }

    public String getCellShortName() {
        return cellShortName;
    }

    public void setCellShortName(String cellShortName) {
        this.cellShortName = cellShortName == null ? null : cellShortName.trim();
    }
}
