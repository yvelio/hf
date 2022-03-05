package com.yvelio.histories;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
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
@Path("/histories")
public class HistoryResource {
	Set<Hand> histories = new HashSet<>();

	@PostConstruct
	public void setup() {
		histories.add(new Hand(220273602944L, "Clematis", HandSite.POKERSTARS));
		histories.add(new Hand(220273617252L, "Clematis", HandSite.POKERSTARS));

		histories.add(new Hand(223914234478L, "Chimaera V", HandSite.POKERSTARS));
		histories.add(new Hand(223914243616L, "Chimaera V", HandSite.POKERSTARS));
		histories.add(new Hand(223914253837L, "Chimaera V", HandSite.POKERSTARS));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Hand> allHistories() {
		return histories;
	}

	@GET
	@Path("/{handNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Hand getHistory(@PathParam("handNumber") Long  handNumber) {
		Optional<Hand> response = histories.stream()
				.filter(acct -> acct.getHandNumber().equals(handNumber))
				.findFirst();

		return response.orElseThrow(()
				-> new WebApplicationException("Hand with id of " + handNumber + " does not exist.", 404));
	}
	
	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response createHand(Hand hand) {
	    if (hand.getHandNumber() == null) {
	      throw new WebApplicationException("No Hand number specified.", 400);
	    }

	    histories.add(hand);
	    return Response.status(201).entity(hand).build();
	  }

}
