package com.yvelio.hands.repository;

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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yvelio.enums.PokerSite;

/**
 * User -> Heros == Player -> Hands (imported)
 * 
 * @author anlev
 *
 */
@Entity
public class Hero {
    @Id
    @GeneratedValue
    @Column(name="hero_id")
	private Long heroId;

	private String playerName;
	private PokerSite site;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hero", orphanRemoval = true)
	@JsonManagedReference
	private Set<Hand> hands  = new HashSet<>();

	public Set<Hand> getHands() {
		return hands;
	}

	public void setHands(Set<Hand> hands) {
		this.hands = hands;
	}


	public void addToHands(Hand hand) {
		this.hands.add(hand);
		hand.setHero(this);
	}


	public Long getHeroId() {
		return heroId;
	}

	public void setHeroId(Long heroId) {
		this.heroId = heroId;
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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hero hero = (Hero) o;
		return playerName.equals(hero.getPlayerName()) && site.equals(hero.getSite());
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerName, site);
	}

	@Override
	public String toString() {
		return "Hero [id=" + heroId + ", playerName=" + playerName + ", site=" + site + ", hands=" + hands.size()+ "]";
	}
	
	

}
