package com.yvelio.hands.repository;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
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
	@Inject
	HeroRepository heroRepository;

	@GetMapping
	public List<Hero> allHeros() {
		return heroRepository.findAll();// listAll();
	}

	@GetMapping("/{playerName}")
//	@Path("/{playerName}")
	public Hero getHero(@PathParam("playerName") String playerName) {
		Hero hero = heroRepository.findAll().stream().filter( h -> h.getPlayerName().equals(playerName)).findFirst().get();

		if (hero == null) {
			throw new WebApplicationException("Hero with " + playerName + " does not exist.", 404);
		}

		return hero;
	}

	@PostMapping(value="/new")
	@Transactional
	public Response createHero(@RequestBody Hero hero) {
		if (hero.getHeroId() != null) {
			throw new WebApplicationException("Id was invalidly set on request.", 400);
		}

		
		System.err.println("About to save new hero: "+hero.getPlayerName()+"/"+hero.getSite());
		System.err.println("with hands: "+hero.getHands().size());
		System.err.println(hero);
		
		
//		heroRepository.persist(hero);
		heroRepository.save(hero);
		return Response.status(201).entity(hero).build();
	}
}
