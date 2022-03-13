package com.yvelio.hands.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HandRepository extends JpaRepository<Hand, Long> {

	Hand findByHandNumber(Long handNumber);

}
