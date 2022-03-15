//package com.holdemfactory.history.service.repository;
//
//import java.util.List;
//
//import javax.transaction.Transactional;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.Response;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Get heros should be only playerName and site without back references.
// * 
// * @author anlev
// *
// */
//@RestController
//@RequestMapping(path = "/heros")
//public class HeroResource {
//	private	HeroRepository heroRepository;
//
//	public HeroResource(HeroRepository repository) {
//		this.heroRepository = repository;
//	}
//	
//	@GetMapping
//	public List<Hero> allHeros() {
//		return heroRepository.findAll();
//	}
//
//	@GetMapping("/{playerName}") 
//	public Hero getHero(@PathVariable("playerName") String playerName) {
//		Hero hero = heroRepository.findByPlayerName(playerName);
//
//		if (hero == null) {
//			throw new WebApplicationException("Hero with " + playerName + " does not exist.", 404);
//		}
//
//		return hero;
//	}
//
//	@PostMapping() 
//	@Transactional
//	public Response createHero(@RequestBody Hero hero) {
//		if (hero.getHeroId() != null) {
//			throw new WebApplicationException("Id was invalidly set on request.", 400);
//		}
//		
//		heroRepository.save(hero);
//		return Response.status(201).entity(hero).build();
//	}
//}
