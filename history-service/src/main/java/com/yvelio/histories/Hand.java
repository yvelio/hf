package com.yvelio.histories;

import java.util.Objects;

public class Hand {
	public Long handNumber;
	public String tableName;
	public HandSite handSite;

	public Hand() {

	}

	public Hand(Long handNumber, String tableName, HandSite handSite) {
		this.handNumber = handNumber;
		this.tableName = tableName;
		this.handSite = handSite;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hand hand = (Hand) o;
		return handNumber.equals(hand.handNumber) &&
				tableName.equals(hand.tableName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(handNumber,tableName);
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
