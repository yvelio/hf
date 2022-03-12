package com.yvelio.hands.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

@Entity
public class History {
	@Id
	private Long historyId;
	
	@Type(type="text")
	private String transcript;
	
	@OneToOne
	@JoinColumn(name = "hand_id")
//	@JsonBackReference
	private Hand hand;
	
	
	@OneToOne
	@JoinColumn(name = "hero_id")
//	@JsonBackReference
	private Hero hero;
}
