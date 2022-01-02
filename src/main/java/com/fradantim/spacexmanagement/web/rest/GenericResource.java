package com.fradantim.spacexmanagement.web.rest;

import org.springframework.web.bind.annotation.RestController;

import com.fradantim.spacexmanagement.dto.GenericRequest;

import reactor.core.publisher.Mono;

@RestController
public class GenericResource {

	public Mono<String> createItem(GenericRequest request) {
		return Mono.just("");
	}
}
