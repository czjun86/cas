package com.complaint.model;

import java.util.List;

public class BaseStation {
	private String bid;
	private List<TCasCell> bsList;

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public List<TCasCell> getBsList() {
		return bsList;
	}

	public void setBsList(List<TCasCell> bsList) {
		this.bsList = bsList;
	}

}
