package com.holdemfactory.history.service.repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Hand {
    @Id
    @Column(name="hand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long handId;
   
	private Long handNumber;
	private String tableName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hand", orphanRemoval = true)
	@JsonManagedReference
	private Set<Player> players  = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "hand", orphanRemoval = true)
	@JsonManagedReference(value="hand-hero")
	private Hero hero;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="history_id")
	@JsonBackReference
	private History history;
	
	public void addToPlayers(Player player) {
		this.players.add(player);
		player.setHand(this);
	}
	
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

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
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
				tableName.equals(hand.getTableName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(handNumber,tableName);
	}

	
	@Override
	public String toString() {
		return "Hand [handId=" + (handId != null ? handId : null)  + ", handNumber=" + handNumber + ", tableName=" + tableName + ", players="
				+ players + ", hero=" + hero + ", history=" + history + "]";
	}
}
