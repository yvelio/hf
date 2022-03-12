package com.yvelio.hands.repository;

import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yvelio.enums.PokerSite;

@Entity
public class Hand {
	@Id
	@GeneratedValue
	private Long handId;

	private Long handNumber;
	private String tableName;
	private PokerSite site;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "hero_id") 
	@JsonBackReference
//	@JsonbTransient
	private Hero hero;
	
    
	public Long getHandId() {
		return handId;
	}

	public void setHandId(Long handId) {
		this.handId = handId;
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

	public PokerSite getSite() {
		return site;
	}

	public void setSite(PokerSite site) {
		this.site = site;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hand hand = (Hand) o;
		return handNumber.equals(hand.getHandNumber()) &&
				tableName.equals(hand.getTableName()) && site.equals(hand.getSite());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(handNumber,tableName);
	}

	@Override
	public String toString() {
		return "Hand [handId=" + handId + ", handNumber=" + handNumber + ", tableName=" + tableName + ", site=" + site
				+ ", hero=" + hero.getPlayerName() + "]";
	}
	
	
}
