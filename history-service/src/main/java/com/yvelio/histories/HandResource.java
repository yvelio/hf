package com.yvelio.histories;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Path("/histories")
public class HandResource {
	Set<Hand> histories = new HashSet<>();

	@PostConstruct
	public void setup() {
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
}
