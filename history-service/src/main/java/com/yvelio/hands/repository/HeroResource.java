package com.yvelio.hands.repository;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents a log file from hero's local directory.
 * 
 * Users/anlev/pfad/to/yvel310/HH20201111 Clematis - $0.01-$0.02 - USD No Limit Hold'em.txt
 * 
 * Users/anlev/pfad/to/yvel310/HH20210217 Chimaera V - $0.01-$0.02 - USD No Limit Hold'em.txt
 * ...
 * 
 * @author anlev
 *
 */
@RestController
@RequestMapping(path = "/heros")
public class HeroResource {
	private	HeroRepository heroRepository;

	public HeroResource(HeroRepository repository) {
		this.heroRepository = repository;
	}
	
	@GetMapping
	public List<Hero> allHeros() {
		return heroRepository.findAll();
	}

	@GetMapping("/{playerName}") 
	//TODO: no session and settings disallow loading outside the Session
	//@Transactional
	public Hero getHero(@PathVariable("playerName") String playerName) {
		Hero hero = heroRepository.findByPlayerName(playerName);

		if (hero == null) {
			throw new WebApplicationException("Hero with " + playerName + " does not exist.", 404);
		}

		return hero;
	}

	@PostMapping() 
	@Transactional
	public Response createHero(@RequestBody Hero hero) {
		if (hero.getHeroId() != null) {
			throw new WebApplicationException("Id was invalidly set on request.", 400);
		}
		
		heroRepository.save(hero);
		return Response.status(201).entity(hero).build();
	}
}
