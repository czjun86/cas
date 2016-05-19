package com.complaint.model;

public class ColourCode {
    private int serialNum;

    private String colourcode;

    public int getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public String getColourcode() {
        return colourcode;
    }

    public void setColourcode(String colourcode) {
        this.colourcode = colourcode == null ? null : colourcode.trim();
    }
}
