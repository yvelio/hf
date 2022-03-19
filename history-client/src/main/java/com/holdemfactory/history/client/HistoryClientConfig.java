package com.holdemfactory.history.client;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileWritingMessageHandler;

//@Configuration
@ConfigurationProperties
public class HistoryClientConfig {
	@Autowired
	private DiffMessageTransformer diffMessageTransformer;
	
	@Bean
	public IntegrationFlow fromDiffToMessage() {
		return IntegrationFlows.from(repositoryReader(), 
				sourcePollingChannelAdapterSpec -> sourcePollingChannelAdapterSpec.poller(Pollers.fixedDelay(500)))
				.transform(diffMessageTransformer, "toString")
				.handle(fileWriter())
				.get();
	}

	private DiffReadingMessageSource repositoryReader() {
		DiffReadingMessageSource source = new DiffReadingMessageSource(new File("/home/ec2-user/hh/yvel310"));
		return source;
	}
	

	private FileWritingMessageHandler fileWriter() {
		FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/home/ec2-user/hh/temp"));
		handler.setExpectReply(false);
		
		return handler;
	}
}
