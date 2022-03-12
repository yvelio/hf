package com.yvelio.hands.repository;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class HeroRepository implements PanacheRepository<Hero> {

	public Hero findByPlayerName(String playerName) {
		return find("playerName = ?1", playerName).firstResult();
	}
}
