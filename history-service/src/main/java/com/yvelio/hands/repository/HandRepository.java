package com.yvelio.hands.repository;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class HandRepository implements PanacheRepository<Hand> {

	public Hand findByHandNumber(Long handNumber) {
		return find("handNumber = ?1", handNumber).firstResult();
	}
}
