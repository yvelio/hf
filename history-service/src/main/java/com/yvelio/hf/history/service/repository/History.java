package com.yvelio.hands.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class History {
	@Id
	private Long historyId;
	
	@Type(type="text")
	private String transcript;
	
	@OneToOne
	@JoinColumn(name = "hand_id")
	@JsonBackReference
	private Hand hand;
	
	
	@OneToOne
	@JoinColumn(name = "hero_id")
	@JsonBackReference
	private Hero hero;


	public Long getHistoryId() {
		return historyId;
	}


	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}


	public String getTranscript() {
		return transcript;
	}


	public void setTranscript(String transcript) {
		this.transcript = transcript;
	}


	public Hand getHand() {
		return hand;
	}


	public void setHand(Hand hand) {
		this.hand = hand;
	}


	public Hero getHero() {
		return hero;
	}


	public void setHero(Hero hero) {
		this.hero = hero;
	}
	
}
