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
@Path("/hands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HandResource {
	@Inject
	HandRepository handRepository;

	@GET
	public List<Hand> allHands() {
		return handRepository.listAll();
	}

	@GET
	@Path("/{handNumber}")
	public Hand getHand(@PathParam("handNumber") Long handNumber) {
		Hand account = handRepository.findByHandNumber(handNumber);

		if (account == null) {
			throw new WebApplicationException("Account with " + handNumber + " does not exist.", 404);
		}

		return account;
	}

	@POST
	@Transactional
	public Response createHand(Hand hand) {
		if (hand.getId() != null) {
			throw new WebApplicationException("Id was invalidly set on request.", 400);
		}

		handRepository.persist(hand);
		return Response.status(201).entity(hand).build();
	}
}
