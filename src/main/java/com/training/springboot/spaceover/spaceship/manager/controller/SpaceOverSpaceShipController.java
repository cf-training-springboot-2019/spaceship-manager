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
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.ID_URI;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_REQUEST_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_RESULT_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_SERVICE_OPERATION_DESCRIPTION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_REQUEST_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_RESULT_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_SERVICE_OPERATION_DESCRIPTION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIPS;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIPS_URI;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIP_API_DESCRIPTION;

import com.github.fge.jsonpatch.JsonPatch;
import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.CreateSpaceShipRequest;
import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.PutSpaceShipRequest;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.GetSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.PatchSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.PutSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.service.SpaceShipService;
import com.training.springboot.spaceover.spaceship.manager.utils.annotatations.ServiceOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//LT#1 Implement SimplifiedSpaceController
@RestController
@RequiredArgsConstructor
@RequestMapping(SPACESHIPS_URI)
@Tag(name = SPACESHIPS, description = SPACESHIP_API_DESCRIPTION)
public class SpaceOverSpaceShipController extends SpaceOverGenericController implements SpaceShipController {

    private final SpaceShipService spaceShipService;

    private final ModelMapper modelMapper;


    @ServiceOperation(GET_SPACESHIPS_SERVICE_OPERATION)
    @GetMapping
    //LT#1-3 Implement SimplifiedSpaceController getSpaceShips method
    @Operation(summary = GET_SPACESHIPS_SERVICE_OPERATION, description = GET_SPACESHIPS_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<List<GetSpaceShipResponse>> getSpaceShips() {
        List<SpaceShip> spaceShips = spaceShipService.findAll(); //LT#2-3 Implement GetSpaceShips Service
        List<GetSpaceShipResponse> response = spaceShips.stream()
            .map(s -> modelMapper.map(s, GetSpaceShipResponse.class))
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


    @ServiceOperation(GET_SPACESHIP_SERVICE_OPERATION)
    @GetMapping(ID_URI)
    //LT#1-2 Implement SimplifiedSpaceController getSpaceShip method
    @Operation(summary = GET_SPACESHIP_SERVICE_OPERATION, description = GET_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<GetSpaceShipResponse> getSpaceShip(@PathVariable("id") Long id) {
        SpaceShip response = spaceShipService.findBydId(id); //LT#2-2 Implement SpaceShipService findBydId method
        return ResponseEntity.ok(modelMapper.map(response, GetSpaceShipResponse.class));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    //LT#1-1 Implement SimplifiedSpaceController createSpaceShip method
    @ServiceOperation(CREATE_SPACESHIP_SERVICE_OPERATION)
    @Operation(summary = CREATE_SPACESHIP_SERVICE_OPERATION, description = CREATE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity createSpaceShip(@RequestBody CreateSpaceShipRequest request) {
        SpaceShip spaceShip = spaceShipService.save(modelMapper.map(request, SpaceShip.class));//LT#2-1 Implement SpaceShipService save method
        return ResponseEntity.created(getResourceUri(spaceShip.getId())).build();
    }

    //LT#1-6 Implement SimplifiedSpaceController patchSpaceShip method
    @ServiceOperation(PATCH_SPACESHIP_SERVICE_OPERATION)
    @PatchMapping(ID_URI)
    @Operation(summary = PATCH_SPACESHIP_SERVICE_OPERATION, description = PATCH_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PatchSpaceShipResponse> patchSpaceShip(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        SpaceShip entity = spaceShipService.findBydId(id); //LT#2-2 Implement SpaceShipService findBydId method
        entity = applyPatch(patch, entity);    
        entity = spaceShipService.update(entity); //LT#2-4 Implement SpaceShipService update method
        return ResponseEntity.ok(modelMapper.map(entity, PatchSpaceShipResponse.class));
    }

    //LT#1-4 Implement SimplifiedSpaceController putSpaceShip method
    @ServiceOperation(PUT_SPACESHIP_SERVICE_OPERATION)
    @PutMapping(ID_URI)
    @Operation(summary = PUT_SPACESHIP_SERVICE_OPERATION, description = PUT_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PutSpaceShipResponse> putSpaceShip(@PathVariable("id") Long id, @RequestBody PutSpaceShipRequest request) {
        request.setId(id);
        SpaceShip entity = spaceShipService.update(modelMapper.map(request, SpaceShip.class)); //LT#2-4 Implement SpaceShipService update method
        return ResponseEntity.ok(modelMapper.map(entity, PutSpaceShipResponse.class));
    }

    //LT#1-5 Implement SimplifiedSpaceController deleteSpaceShip method
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ServiceOperation(DELETE_SPACESHIP_SERVICE_OPERATION)
    @DeleteMapping(ID_URI)
    @Operation(summary = DELETE_SPACESHIP_SERVICE_OPERATION, description = DELETE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity deleteSpaceship(@PathVariable("id") Long id) {
        //LT#2-5 Implement SpaceShipService delete method
        spaceShipService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
