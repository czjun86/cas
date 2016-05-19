package com.complaint.model;

import java.math.BigDecimal;

public class LLOffset {
    private BigDecimal lng;

    private BigDecimal lat;

    private BigDecimal offsetX;

    private BigDecimal offsetY;

    private BigDecimal offsetLng;

    private BigDecimal offsetLat;

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(BigDecimal offsetX) {
        this.offsetX = offsetX;
    }

    public BigDecimal getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(BigDecimal offsetY) {
        this.offsetY = offsetY;
    }

    public BigDecimal getOffsetLng() {
        return offsetLng;
    }

    public void setOffsetLng(BigDecimal offsetLng) {
        this.offsetLng = offsetLng;
    }

    public BigDecimal getOffsetLat() {
        return offsetLat;
    }

    public void setOffsetLat(BigDecimal offsetLat) {
        this.offsetLat = offsetLat;
    }
}
