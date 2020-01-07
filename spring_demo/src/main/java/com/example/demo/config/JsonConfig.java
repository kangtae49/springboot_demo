package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JsonConfig {
	@Bean("objectMapper")
	public ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder.json()
//				.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
//				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).modules(new JavaTimeModule())
				.build();
	}
}
