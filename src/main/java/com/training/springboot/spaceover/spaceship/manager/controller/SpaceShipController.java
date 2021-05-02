package com.training.springboot.spaceover.spaceship.manager.controller;

import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.CreateSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.UpdateSpaceShipRequest;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.GetSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.UpdateSpaceShipResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpaceShipController {

    ResponseEntity<Page<GetSpaceShipResponse>> getSpaceCrewMembers(Pageable page, String name, String status, List<Long> ids);

    ResponseEntity<GetSpaceShipResponse> getSpaceCrewMember(Long id);

    ResponseEntity createSpaceCrewMember(CreateSpaceShipResponse request);

    ResponseEntity<UpdateSpaceShipResponse> UpdateSpaceCrewMember(UpdateSpaceShipRequest request);

    ResponseEntity deleteSpaceCrewMember(Long id);

}
