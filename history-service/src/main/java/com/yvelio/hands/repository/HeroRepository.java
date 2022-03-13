package com.yvelio.hands.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Long> {

	Hero findByPlayerName(String playerName);

}
