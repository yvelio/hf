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

@RestController
@RequestMapping(path = "/histories")
public class HistoryResource {
	private HistoryRepository historyRepository;

	public HistoryResource(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}
	
	@GetMapping
	public List<History> allHistories() {
		return historyRepository.findAll();
	}
	

	@GetMapping("/{historyId}") 
	public History getHistory(@PathVariable("historyId") Long historyId) {
		//TODO: Handle no history case 
		History history = historyRepository.findById(historyId).get();

		if (history == null) {
			throw new WebApplicationException("Hero with " + historyId + " does not exist.", 404);
		}

		return history;
	}

	@PostMapping() 
	@Transactional
	public Response createHistory(@RequestBody History history) {
		if (history.getHistoryId() != null) {
			throw new WebApplicationException("Id was invalidly set on request.", 400);
		}
		
		historyRepository.save(history);
		return Response.status(201).entity(history).build();
	}
	
}
