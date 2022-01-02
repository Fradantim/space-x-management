package com.fradantim.spacexmanagement.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fradantim.spacexmanagement.dto.GenericRequest;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

@RestController
public class GenericResource {

	@Operation(description = "${generic-resource.description}")
	@PostMapping
	public Mono<String> createItem(GenericRequest request) {
		return Mono.just("");
	}
}
