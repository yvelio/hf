package com.holdemfactory.history.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.holdemfactory.history.client.domain.History;

/**
 * 
 * A value class representing a change to a file.
 * 
 * ChangeType:
 * 
 * Add a new file to the project
 * ADD,
 * 
 * Modify an existing file in the project (content and/or mode)
 * MODIFY
 *  
 * Delete an existing file from the project
 * DELETE
 *  
 * Rename an existing file to a new location 
 * RENAME
 *  
 * Copy an existing file to a new location, keeping the original 
 * COPY;
 * 
 * @author anlev
 *
 */
//@Path("/transactions")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class DiffEntryResource {
	@Inject
	@RestClient
	HistoryService historyService;

	@ConfigProperty(name = "history.service", defaultValue = "http://52.58.213.253:30540")
	String historyServiceUrl;

	//	@POST
	//	@Path("/{acctNumber}")
	//	public Map<String, List<String>> newTransaction(@PathParam("acctNumber") Long accountNumber, BigDecimal amount) {
	public Map<String, List<String>> newTransaction(History history) {
		try {
			return historyService.createHistory(history);
		} catch (Throwable t) {
			t.printStackTrace();
			Map<String, List<String>> response = new HashMap<>();
			response.put("EXCEPTION - " + t.getClass(), Collections.singletonList(t.getMessage()));
			return response;
		}
	}
	//
	//	@POST
	//	@Path("/async/{acctNumber}")
	//	public CompletionStage<Map<String, List<String>>> newTransactionAsync(@PathParam("acctNumber") Long accountNumber, BigDecimal amount) {
	//		return historyService.transactAsync(accountNumber, amount);
	//	}
	//
	//	@POST
	//	@Path("/api/{acctNumber}")
	//	public Response newTransactionWithApi(@PathParam("acctNumber") Long accountNumber, BigDecimal amount) throws MalformedURLException {
	//		AccountServiceProgrammatic acctService =
	//				RestClientBuilder.newBuilder()
	//				.baseUrl(new URL(accountServiceUrl))
	//				.connectTimeout(500, TimeUnit.MILLISECONDS)
	//				.readTimeout(1200, TimeUnit.MILLISECONDS)
	//				.build(AccountServiceProgrammatic.class);
	//
	//		acctService.transact(accountNumber, amount);
	//		return Response.ok().build();
	//	}
	//
	//	@POST
	//	@Path("/api/async/{acctNumber}")
	//	public CompletionStage<Void> newTransactionWithApiAsync(@PathParam("acctNumber") Long accountNumber, BigDecimal amount) throws MalformedURLException {
	//		AccountServiceProgrammatic acctService =
	//				RestClientBuilder.newBuilder()
	//				.baseUrl(new URL(accountServiceUrl))
	//				.build(AccountServiceProgrammatic.class);
	//
	//		return acctService.transactAsync(accountNumber, amount);
	//	}
}
