package com.holdemfactory.history.service.repository;

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
 * Get heros should be only playerName and site without back references.
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

	@GetMapping("/{heroId}") 
	public Hero getHero(@PathVariable("heroId") Long heroId) {
		Hero hero = heroRepository.findById(heroId).get();

		if (hero == null) {
			throw new WebApplicationException("Hero with " + heroId + " does not exist.", 404);
		}

		return hero;
	}

	@PostMapping() 
	@Transactional
	public Response createHero(@RequestBody Hero hero) {
		if (hero.getHeroId() != null) {
			throw new WebApplicationException("Id was invalidly set on request.", 400);
		}
		
		System.out.println("About to save new hero: "+hero);
		Hero heroSaved = heroRepository.save(hero);
		System.out.println("----------------------");
		System.out.println("	Saved  hero: "+heroSaved);
		
		
		return Response.status(201).entity(hero).build();
	}
}
