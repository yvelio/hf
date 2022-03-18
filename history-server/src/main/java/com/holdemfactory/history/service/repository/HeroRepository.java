package com.holdemfactory.history.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HeroRepository extends JpaRepository<Hero, Long> {

//	@Query(value = "SELECT h FROM Hero h WHERE player_id = ?1")
//	Optional<Hero> findById(Long playerId);

}
