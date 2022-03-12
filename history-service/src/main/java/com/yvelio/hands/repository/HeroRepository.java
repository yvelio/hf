package com.yvelio.hands.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Transactional(readOnly = true)
public interface HeroRepository extends JpaRepository<Hero, Long> {

}
