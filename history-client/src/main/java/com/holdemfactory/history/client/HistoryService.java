package com.holdemfactory.history.client;

import java.util.List;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.holdemfactory.history.client.domain.History;

@Path("/histories")
//@Path("/accounts")
@RegisterRestClient
@ClientHeaderParam(name = "class-level-param", value = "HistoryService-interface")
@RegisterClientHeaders
@RegisterProvider(HistoryRequestFilter.class)
@RegisterProvider(HistoryExceptionMapper.class)
@Produces(MediaType.APPLICATION_JSON)
public interface HistoryService {
//	@GET
//	@Path("/{acctNumber}/balance")
//	BigDecimal getBalance(@PathParam("acctNumber") Long accountNumber);

	@POST
//	@Path("{playerName}")
	Map<String, List<String>> createHistory(History history) throws HistoryNotFoundException;

//	@POST
//	@Path("{accountNumber}/transaction")
//	@ClientHeaderParam(name = "method-level-param", value = "{generateValue}")
//	CompletionStage<Map<String, List<String>>> transactAsync(@PathParam("accountNumber") Long accountNumber, BigDecimal amount);

	default String generateValue() {
		return "Value generated in method for async call";
	}
}
