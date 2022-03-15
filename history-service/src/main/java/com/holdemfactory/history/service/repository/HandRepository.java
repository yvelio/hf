package com.holdemfactory.history.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HandRepository extends JpaRepository<Hand, Long> {
	
//	@Query(value = "SELECT h FROM Hand h WHERE handNumber = ?1")
	Hand findByHandNumber(Long handNumber);

}
