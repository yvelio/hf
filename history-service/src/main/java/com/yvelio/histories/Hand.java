package com.yvelio.histories;

public class Hand {
	public Long handNumber;
	public String tableName;
	public HandSite handSite;

	public Hand(Long handNumber, String tableName, HandSite handSite) {
		this.handNumber = handNumber;
		this.tableName = tableName;
		this.handSite = handSite;
	}

	public Long getHandNumber() {
		return handNumber;
	}

	public void setHandNumber(Long handNumber) {
		this.handNumber = handNumber;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public HandSite getHandSite() {
		return handSite;
	}

	public void setHandSite(HandSite handSite) {
		this.handSite = handSite;
	}



}
