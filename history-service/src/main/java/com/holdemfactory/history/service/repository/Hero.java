package com.holdemfactory.history.service.repository;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 
 * 
 * @author anlev
 *
 */
@Entity
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hero_id")
	private Long heroId;
	
	@OneToOne
	@JoinColumn(name = "hand_id")
	@JsonBackReference(value = "hand-hero")
	private Hand hand;
	
	@OneToOne
	@JoinColumn(name = "player_id")
	@JsonBackReference(value = "player-hero")
	private Player player;
	
	public Long getHeroId() {
		return heroId;
	}
	
	public void setHeroId(Long heroId) {
		this.heroId = heroId;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		this.player.setHero(this);
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
		this.hand.setHero(this);
	}

	@Override
	public String toString() {
		return "Hero [heroId=" + (heroId != null ? heroId : null) + ", player=" + (player != null ? player.getPlayerName() : null)
				+ ", hand=" + (hand != null ? hand.getHandId() : null) + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(heroId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hero other = (Hero) obj;
		return Objects.equals(heroId, other.heroId);
	}
	
	
}
