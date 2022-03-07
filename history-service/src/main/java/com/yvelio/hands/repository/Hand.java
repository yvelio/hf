package com.yvelio.hands.repository;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hand {
	@Id
	@GeneratedValue
	private Long id;

	private Long handNumber;
	private String tableName;
	private HandSite handSite;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hand hand = (Hand) o;
		return handNumber.equals(hand.handNumber) &&
				tableName.equals(hand.tableName);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		return Objects.hash(handNumber,tableName);
	}




}
