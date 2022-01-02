package com.fradantim.spacexmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

	@Bean("trello.webclient")
	public WebClient buildWebClient(@Autowired WebClient.Builder builder) {
		return builder.build();
	}
}
