package com.holdemfactory.history.client;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileWritingMessageHandler;

@Configuration
public class HistoryClientConfig {
	@Autowired
	private DiffMessageTransformer diffMessageTransformer;

	@Bean(name = "capitalizeFunction")
	public StringFunction capitalizer() {
		return String::toUpperCase;
	}

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

	public DiffMessageTransformer getDiffMessageTransformer() {
		return diffMessageTransformer;
	}

	public void setDiffMessageTransformer(DiffMessageTransformer diffMessageTransformer) {
		this.diffMessageTransformer = diffMessageTransformer;
	}

}
