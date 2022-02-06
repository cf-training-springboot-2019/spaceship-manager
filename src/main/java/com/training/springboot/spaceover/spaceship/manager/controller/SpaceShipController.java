package com.training.springboot.spaceover.spaceship.manager.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.CreateSpaceShipRequest;
import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.PutSpaceShipRequest;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.GetSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.PatchSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.PutSpaceShipResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public interface SpaceShipController {

    ResponseEntity<List<GetSpaceShipResponse>> getSpaceShips();

    ResponseEntity<GetSpaceShipResponse> getSpaceShip(Long id);

    ResponseEntity createSpaceShip(CreateSpaceShipRequest request);

    ResponseEntity<PatchSpaceShipResponse> patchSpaceShip(Long id, JsonPatch request);

    ResponseEntity<PutSpaceShipResponse> putSpaceShip(Long id, PutSpaceShipRequest request);

    ResponseEntity deleteSpaceship(Long id);

}
