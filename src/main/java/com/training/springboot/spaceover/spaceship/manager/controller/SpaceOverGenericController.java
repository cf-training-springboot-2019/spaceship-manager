package com.training.springboot.spaceover.spaceship.manager.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import java.net.URI;
import lombok.SneakyThrows;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class SpaceOverGenericController {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@SneakyThrows
	protected SpaceShip applyPatch(JsonPatch patch, SpaceShip targetSpaceShip) {
		JsonNode patched = patch.apply(objectMapper.convertValue(targetSpaceShip, JsonNode.class));
		return objectMapper.treeToValue(patched, SpaceShip.class);
	}

	protected URI getResourceUri(Long id) {
		return URI.create(ServletUriComponentsBuilder.fromCurrentRequest().pathSegment(id.toString()).toUriString());
	}

}
