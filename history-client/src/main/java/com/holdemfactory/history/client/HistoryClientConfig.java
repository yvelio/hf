package com.holdemfactory.history.client;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;

@Configuration
public class HistoryClientConfig {
	@Autowired
	private DiffMessageTransformer diffMessageTransformer;
	
	@Bean
	public IntegrationFlow fromDiffToMessage() {
		return IntegrationFlows.from(repositoryReader(), 
				sourcePollingChannelAdapterSpec -> sourcePollingChannelAdapterSpec.poller(Pollers.fixedDelay(500)))
				.transform(diffMessageTransformer, "toString")
				.handle(HistoryService.class, "createHistory")
				.get();
	}

	private DiffReadingMessageSource repositoryReader() {
		DiffReadingMessageSource source = new DiffReadingMessageSource(new File("/opt/hh/yvel310"));
		return source;
	}
}