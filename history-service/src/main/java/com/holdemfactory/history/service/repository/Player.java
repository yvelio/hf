package com.holdemfactory.history.service.repository;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.holdemfactory.history.service.enums.PokerSite;

@Entity
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="player_id")
	private Long playerId;

	private String playerName;
	private PokerSite site;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="hand_id")
	@JsonBackReference
	private Hand hand;

	@OneToOne(cascade=CascadeType.PERSIST, mappedBy = "player")
	@JsonManagedReference(value = "player-hero")
	private Hero hero;


	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	@Override
	public String toString() {
		return "Player [playerId=" + (playerId != null ? playerId : null) + ", playerName=" + playerName + ", site=" + site 
				+ ", hero=" + (hero != null ? hero.getHeroId()+"/"+hero.getPlayer().getPlayerName() : null) + "]";
	}

}
