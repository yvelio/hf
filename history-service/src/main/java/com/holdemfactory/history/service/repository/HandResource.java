package com.holdemfactory.history.service.repository;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(path = "/hands")
public class HandResource {
	@Inject
	HandRepository handRepository;

	@GetMapping() 
	public List<Hand> allHands() {
		return handRepository.findAll();
	}

	@GetMapping("/{handNumber}") 
	public Hand getHand(@PathParam("handNumber") Long handNumber) {
		Hand hand = handRepository.findByHandNumber(handNumber);

		if (hand == null) {
			throw new WebApplicationException("Hand with " + handNumber + " does not exist.", 404);
		}

		return hand;
	}

	@PostMapping() 
	@Transactional
	public Response createHand(Hand hand) {
		if (hand.getHandId() != null) {
			throw new WebApplicationException("Id was invalidly set on request.", 400);
		}
		
		System.out.println("About to add new hand");
		System.out.println(hand);

		handRepository.save(hand);
		return Response.status(201).entity(hand).build();
	}
}
