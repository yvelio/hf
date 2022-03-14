package com.yvelio.hf.history.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HeroRepository extends JpaRepository<Hero, Long> {

	@Query(value = "SELECT h FROM Hero h WHERE playerName = ?1")
	Hero findByPlayerName(String playerName);

}
