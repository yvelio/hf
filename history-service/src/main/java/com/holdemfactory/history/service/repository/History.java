package com.holdemfactory.history.service.repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Represents a log file from hero's local directory.
 * 
 * Users/anlev/pfad/to/yvel310/HH20201111 Clematis - $0.01-$0.02 - USD No Limit Hold'em.txt
 * 
 * Users/anlev/pfad/to/yvel310/HH20210217 Chimaera V - $0.01-$0.02 - USD No Limit Hold'em.txt
 * ...
 * 
 * 
 * Use POST /histories/1/hands to createHand()
 * 
 * @author anlev
 *
 */
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="history_id")
	private Long historyId;
    
    private String fileName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "history", orphanRemoval = true)
	@JsonManagedReference
	private Set<Hand> hands = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "history", orphanRemoval = true)
	@JsonManagedReference(value="history-hero")
	private Hero hero;

	public void addToHands(Hand hand) {
		this.hands.add(hand);
		hand.setHistory(this);
	}
	
	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public Set<Hand> getHands() {
		return hands;
	}

	public void setHands(Set<Hand> hands) {
		this.hands = hands;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(fileName, historyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		History other = (History) obj;
		return Objects.equals(fileName, other.fileName) && Objects.equals(historyId, other.historyId);
	}

	@Override
	public String toString() {
		return "History [historyId=" + (historyId != null ? historyId : null) + ", fileName=" + fileName + ", hands=" + hands + ", hero=" + hero
				+ "]";
	}


}
