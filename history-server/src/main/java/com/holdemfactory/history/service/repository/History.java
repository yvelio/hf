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

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
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
		return "History [historyId=" + (historyId != null ? historyId : null) + ", fileName=" + fileName + ", hands=" + hands
				+ "]";
	}

}
