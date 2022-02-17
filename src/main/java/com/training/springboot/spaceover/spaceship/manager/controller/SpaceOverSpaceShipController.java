package com.training.springboot.spaceover.spaceship.manager.controller;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.CREATE_SPACESHIP_REQUEST_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.CREATE_SPACESHIP_RESULT_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.CREATE_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.CREATE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.DELETE_SPACESHIP_REQUEST_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.DELETE_SPACESHIP_RESULT_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.DELETE_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.DELETE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIPS_REQUEST_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIPS_RESULT_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIPS_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIPS_SERVICE_OPERATION_DESCRIPTION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIP_REQUEST_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIP_RESULT_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIP_SERVICE_OPERATION_DESCRIPTION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_REQUEST_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_RESULT_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_SERVICE_OPERATION_DESCRIPTION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_REQUEST_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_RESULT_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_SERVICE_OPERATION_DESCRIPTION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIPS;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIP_API_DESCRIPTION;

import com.github.fge.jsonpatch.JsonPatch;
import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.CreateSpaceShipRequest;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.PatchSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.service.SpaceShipService;
import com.training.springboot.spaceover.spaceship.manager.utils.annotatations.ServiceOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
//LT#1 Implement SimplifiedSpaceShipController
@Tag(name = SPACESHIPS, description = SPACESHIP_API_DESCRIPTION)
public class SpaceOverSpaceShipController extends SpaceOverGenericController {

    private SpaceShipService spaceShipService;

    private ModelMapper modelMapper;

    @ServiceOperation(GET_SPACESHIPS_SERVICE_OPERATION)
    //LT#1-3 Implement SimplifiedSpaceController getSpaceShips method
    @Operation(summary = GET_SPACESHIPS_SERVICE_OPERATION, description = GET_SPACESHIPS_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<List<SpaceShip>> getSpaceShips() {
        log.trace(GET_SPACESHIPS_REQUEST_MSG);
        List<SpaceShip> spaceShips = null; //LT#2-3 Implement GetSpaceShips Service
        log.info(GET_SPACESHIPS_RESULT_MSG, spaceShips.size(), spaceShips.size());
        return ResponseEntity.ok(spaceShips);
    }


    @ServiceOperation(GET_SPACESHIP_SERVICE_OPERATION)
    //LT#1-2 Implement SimplifiedSpaceController getSpaceShip method
    @Operation(summary = GET_SPACESHIP_SERVICE_OPERATION, description = GET_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<SpaceShip> getSpaceShip(Long id) {
        log.trace(GET_SPACESHIP_REQUEST_MSG, id);
        SpaceShip response = null; //LT#2-2 Implement SpaceShipService findBydId method
        log.info(GET_SPACESHIP_RESULT_MSG, response.getId());
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    //LT#1-1 Implement SimplifiedSpaceController createSpaceShip method
    @ServiceOperation(CREATE_SPACESHIP_SERVICE_OPERATION)
    @Operation(summary = CREATE_SPACESHIP_SERVICE_OPERATION, description = CREATE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity createSpaceShip(SpaceShip request) {
        log.trace(CREATE_SPACESHIP_REQUEST_MSG);
        SpaceShip spaceShip = null;//LT#2-1 Implement SpaceShipService save method
            log.info(CREATE_SPACESHIP_RESULT_MSG, spaceShip.getId());
        return ResponseEntity.created(getResourceUri(spaceShip.getId())).build();
    }

    //LT#1-6 Implement SimplifiedSpaceController patchSpaceShip method
    @ServiceOperation(PATCH_SPACESHIP_SERVICE_OPERATION)
    @Operation(summary = PATCH_SPACESHIP_SERVICE_OPERATION, description = PATCH_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<SpaceShip> patchSpaceShip(Long id, JsonPatch patch) {
        log.trace(PATCH_SPACESHIP_REQUEST_MSG, id);
        SpaceShip entity = null; //LT#2-2 Implement SpaceShipService findBydId method
        entity = applyPatch(patch, entity);    
        entity = null; //LT#2-4 Implement SpaceShipService update method
        log.info(PATCH_SPACESHIP_RESULT_MSG, id);
        return ResponseEntity.ok(entity);
    }

    //LT#1-4 Implement SimplifiedSpaceController putSpaceShip method
    @ServiceOperation(PUT_SPACESHIP_SERVICE_OPERATION)
    @Operation(summary = PUT_SPACESHIP_SERVICE_OPERATION, description = PUT_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<SpaceShip> putSpaceShip(Long id, SpaceShip request) {
        log.trace(PUT_SPACESHIP_REQUEST_MSG, id);
        request.setId(id);
        SpaceShip entity = null; //LT#2-4 Implement SpaceShipService update method
        log.info(PUT_SPACESHIP_RESULT_MSG, id);
        return ResponseEntity.ok(entity);
    }

    //LT#1-5 Implement SimplifiedSpaceController deleteSpaceShip method
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ServiceOperation(DELETE_SPACESHIP_SERVICE_OPERATION)
    @Operation(summary = DELETE_SPACESHIP_SERVICE_OPERATION, description = DELETE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity deleteSpaceship(Long id) {
        log.trace(DELETE_SPACESHIP_REQUEST_MSG, id);
        //LT#2-5 Implement SpaceShipService delete method
        log.trace(DELETE_SPACESHIP_RESULT_MSG, id);
        return ResponseEntity.noContent().build();
    }


}
