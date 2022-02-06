package com.training.springboot.spaceover.spaceship.manager.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface SimplifiedSpaceShipController {

	ResponseEntity<List<SpaceShip>> getSpaceShips();

	ResponseEntity<SpaceShip> getSpaceShip(Long id);

	ResponseEntity createSpaceShip(SpaceShip request);

	ResponseEntity<SpaceShip> patchSpaceShip(Long id, JsonPatch request);

	ResponseEntity<SpaceShip> putSpaceShip(Long id, SpaceShip request);

	ResponseEntity deleteSpaceship(Long id);

}
