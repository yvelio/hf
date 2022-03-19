package com.holdemfactory.history.client;

import java.io.IOException;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class DiffMessageTransformer {
	
	public String toString(Message<String> message) throws IOException {
		System.err.println("DiffMessageTransformer:transform the message with header: "+message.getHeaders());
//		System.err.println("DiffMessageTransformer:transform the message with payload: "+message.getPayload());

//		String content = new String(Files.readAllBytes(Paths.get(message.getPayload()/*.getPath()*/)));
//		System.out.println("DiffMessageTransformer.transform()####### CONTENT OF MESSAGE ########");
//		System.out.println(content);
//		System.out.println("###################################");
	

		String[] splits = message.getPayload().split("\\n");

		StringBuilder sb = new StringBuilder();
		for(String split : splits) {
			if(split.trim().startsWith("+")) {
				sb.append(split.substring(1)).append("\n");
			}
		}

		if (sb.length() > 0) {
//			System.err.println("DiffMessageTransformer:TRANSFOMED: "+sb.toString());
			return sb.toString();
		} else {
			//No transformation needed
			return message.getPayload();
		}
	}
}
