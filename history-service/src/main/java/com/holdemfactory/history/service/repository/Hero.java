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
	@JoinColumn(name = "history_id")
	@JsonBackReference(value = "history-hero")
	private History history;
	
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
		//object references an unsaved transient instance - save the transient instance before flushing : 
		//com.holdemfactory.history.service.repository.Player.hero -> com.holdemfactory.history.service.repository.Hero
		//this.player.setHero(this);
	}

	public History getHistory() {
		return history;
	}
	
	public void setHistory(History history) {
		this.history = history;
		this.history.setHero(this);
	}

	@Override
	public String toString() {
		return "Hero [heroId=" + (heroId != null ? heroId : null) + ", player=" + (player != null ? player.getPlayerName() : null)
				+ ", history=" + (history != null ? history.getFileName() : null) + "]";
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
