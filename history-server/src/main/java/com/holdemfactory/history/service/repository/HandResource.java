package com.holdemfactory.history.service.repository;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Get (hands -> players -> hero) without histories.
 * 
 * @author anlev
 *
 */
@RestController
@RequestMapping(path = "/hands")
public class HandResource {
	private HandRepository handRepository;
	

	public HandResource(HandRepository handRepository) {
		this.handRepository = handRepository;
	}

	@GetMapping() 
	public List<Hand> allHands() {
		return handRepository.findAll();
	}

	@GetMapping("/{handNumber}") 
	public Hand getHand(@PathVariable("handNumber") Long handNumber) {
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
		
		System.out.println("################################");
		System.out.println("About to add new hand");
		System.out.println(hand);
		System.out.println("################################");
		
		
		handRepository.save(hand);
		return Response.status(201).entity(hand).build();
	}
}
