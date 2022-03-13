package com.yvelio.hands.repository;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
@Path("/heros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HeroResource {
	@Inject
	HeroRepository heroRepository;

	@GET
	public List<Hero> allHeros() {
		return heroRepository.listAll();
	}

	@GET
	@Path("/{playerName}")
	public Hero getHero(@PathParam("playerName") String playerName) {
		Hero hero = heroRepository.findByPlayerName(playerName);

		if (hero == null) {
			throw new WebApplicationException("Hero with " + playerName + " does not exist.", 404);
		}

		return hero;
	}

	@POST
	@Transactional
	public Response createHero(Hero hero) {
		if (hero.getId() != null) {
			throw new WebApplicationException("Id was invalidly set on request.", 400);
		}
		
		heroRepository.persist(hero);
		return Response.status(201).entity(hero).build();
	}
}
