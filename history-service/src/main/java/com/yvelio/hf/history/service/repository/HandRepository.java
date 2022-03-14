package com.yvelio.hf.history.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HandRepository extends JpaRepository<Hand, Long> {

	@Query(value = "SELECT h FROM Hand h WHERE handNumber = ?1")
	Hand findByHandNumber(Long handNumber);

}
