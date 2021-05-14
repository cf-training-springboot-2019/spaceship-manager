package com.training.springboot.spaceover.spaceship.manager.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.CreateSpaceShipRequest;
import com.training.springboot.spaceover.spaceship.manager.domain.request.inbound.PutSpaceShipRequest;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.GetSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.PatchSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.PutSpaceShipResponse;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import com.training.springboot.spaceover.spaceship.manager.service.SpaceShipService;
import com.training.springboot.spaceover.spaceship.manager.utils.assemblers.PaginationModelAssembler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(SPACESHIPS_URI)
public class SpaceOverSpaceShipController extends SpaceOverGenericController implements SpaceShipController {

    private final SpaceShipService spaceShipService;

    private final ModelMapper modelMapper;

    private final PagedResourcesAssembler<SpaceShip> pagedModelAssembler;

    private final PaginationModelAssembler modelAssembler;


    @Override
    @GetMapping
    public ResponseEntity<PagedModel<GetSpaceShipResponse>> getSpaceShips(Pageable pageable,
                                                                          @RequestParam(name = NAME_FIELD, required = false) String name,
                                                                          @RequestParam(name = STATUS_FIELD, required = false) String status,
                                                                          @RequestParam(name = TYPE_FIELD, required = false) String type) {
        SpaceShip spaceShipSample = SpaceShip.builder()
                .name(name)
                .status(SpaceShipStatus.fromName(status))
                .type(SpaceShipType.fromName(type))
                .build();
        Page<SpaceShip> spaceShipPage = spaceShipService.findAll(spaceShipSample, pageable);
        PagedModel<GetSpaceShipResponse> response = pagedModelAssembler.toModel(spaceShipPage, modelAssembler);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(ID_URI)
    public ResponseEntity<GetSpaceShipResponse> getSpaceShip(@PathVariable("id") Long id) {
        GetSpaceShipResponse response = modelMapper.map(spaceShipService.findBydId(id), GetSpaceShipResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    public ResponseEntity createSpaceShip(@RequestBody @Valid CreateSpaceShipRequest request) {
        SpaceShip spaceShip = spaceShipService.save(modelMapper.map(request, SpaceShip.class));
        return ResponseEntity.created(getResourceUri(spaceShip.getId())).build();
    }

    @Override
    @PatchMapping(value = ID_URI, consumes = APPLICATION_JSON_PATCH)
    public ResponseEntity<PatchSpaceShipResponse> patchSpaceShip(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        SpaceShip entity = spaceShipService.findBydId(id);
        entity = spaceShipService.update(applyPatch(patch, entity));
        return ResponseEntity.ok(modelMapper.map(entity, PatchSpaceShipResponse.class));
    }

    @Override
    @PutMapping(ID_URI)
    public ResponseEntity<PutSpaceShipResponse> putSpaceShip(@PathVariable("id") Long id, @RequestBody @Valid PutSpaceShipRequest request) {
        request.setId(id);
        SpaceShip entity = spaceShipService.update(modelMapper.map(request, SpaceShip.class));
        return ResponseEntity.ok(modelMapper.map(entity, PutSpaceShipResponse.class));
    }

    @Override
    @DeleteMapping(ID_URI)
    public ResponseEntity deleteSpaceCrewMember(@PathVariable("id") Long id) {
        spaceShipService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
