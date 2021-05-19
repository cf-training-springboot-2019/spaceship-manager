package com.training.springboot.spaceover.spaceship.manager.controller;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.APPLICATION_JSON_PATCH;
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
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.NAME_FIELD;
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
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.STATUS_FIELD;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.TYPE_FIELD;

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
import com.training.springboot.spaceover.spaceship.manager.utils.annotatations.ServiceOperation;
import com.training.springboot.spaceover.spaceship.manager.utils.assemblers.PaginationModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(SPACESHIPS_URI)
@Tag(name = SPACESHIPS, description = SPACESHIP_API_DESCRIPTION)
public class SpaceOverSpaceShipController extends SpaceOverGenericController implements SpaceShipController {

	private final SpaceShipService spaceShipService;

	private final ModelMapper modelMapper;

	private final PagedResourcesAssembler<SpaceShip> pagedModelAssembler;

	private final PaginationModelAssembler modelAssembler;

	@Override
	@GetMapping
	@PageableAsQueryParam
	@ServiceOperation(GET_SPACESHIPS_SERVICE_OPERATION)
	@Operation(summary = GET_SPACESHIPS_SERVICE_OPERATION, description = GET_SPACESHIPS_SERVICE_OPERATION_DESCRIPTION)
	public ResponseEntity<PagedModel<GetSpaceShipResponse>> getSpaceShips(@Parameter(hidden = true) Pageable pageable,
			@RequestParam(name = NAME_FIELD, required = false) String name,
			@RequestParam(name = STATUS_FIELD, required = false) String status,
			@RequestParam(name = TYPE_FIELD, required = false) String type) {
		log.trace(GET_SPACESHIPS_REQUEST_MSG);
		SpaceShip spaceShipSample = SpaceShip.builder()
				.name(name)
				.status(SpaceShipStatus.fromName(status))
				.type(SpaceShipType.fromName(type))
				.build();
		Page<SpaceShip> spaceShipPage = spaceShipService.findAll(spaceShipSample, pageable);
		PagedModel<GetSpaceShipResponse> response = pagedModelAssembler.toModel(spaceShipPage, modelAssembler);
		log.info(GET_SPACESHIPS_RESULT_MSG, spaceShipPage.getNumberOfElements(), spaceShipPage.getTotalElements());
		return ResponseEntity.ok(response);
	}


	@Override
	@GetMapping(ID_URI)
	@ServiceOperation(GET_SPACESHIP_SERVICE_OPERATION)
	@Operation(summary = GET_SPACESHIP_SERVICE_OPERATION, description = GET_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
	public ResponseEntity<GetSpaceShipResponse> getSpaceShip(@PathVariable("id") Long id) {
		log.trace(GET_SPACESHIP_REQUEST_MSG, id);
		GetSpaceShipResponse response = modelMapper.map(spaceShipService.findBydId(id), GetSpaceShipResponse.class);
		log.info(GET_SPACESHIP_RESULT_MSG, response.getId());
		return ResponseEntity.ok(response);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ServiceOperation(CREATE_SPACESHIP_SERVICE_OPERATION)
	@Operation(summary = CREATE_SPACESHIP_SERVICE_OPERATION, description = CREATE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
	public ResponseEntity createSpaceShip(@RequestBody @Valid CreateSpaceShipRequest request) {
		log.trace(CREATE_SPACESHIP_REQUEST_MSG);
		SpaceShip spaceShip = spaceShipService.save(modelMapper.map(request, SpaceShip.class));
		log.info(CREATE_SPACESHIP_RESULT_MSG, spaceShip.getId());
		return ResponseEntity.created(getResourceUri(spaceShip.getId())).build();
	}

	@Override
	@PatchMapping(value = ID_URI, consumes = APPLICATION_JSON_PATCH)
	@ServiceOperation(PATCH_SPACESHIP_SERVICE_OPERATION)
	@Operation(summary = PATCH_SPACESHIP_SERVICE_OPERATION, description = PATCH_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
	public ResponseEntity<PatchSpaceShipResponse> patchSpaceShip(@PathVariable("id") Long id,
			@RequestBody JsonPatch patch) {
		log.trace(PATCH_SPACESHIP_REQUEST_MSG, id);
		SpaceShip entity = spaceShipService.findBydId(id);
		entity = spaceShipService.update(applyPatch(patch, entity));
		log.info(PATCH_SPACESHIP_RESULT_MSG, id);
		return ResponseEntity.ok(modelMapper.map(entity, PatchSpaceShipResponse.class));
	}

	@Override
	@PutMapping(ID_URI)
	@ServiceOperation(PUT_SPACESHIP_SERVICE_OPERATION)
	@Operation(summary = PUT_SPACESHIP_SERVICE_OPERATION, description = PUT_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
	public ResponseEntity<PutSpaceShipResponse> putSpaceShip(@PathVariable("id") Long id,
			@RequestBody @Valid PutSpaceShipRequest request) {
		log.trace(PUT_SPACESHIP_REQUEST_MSG, id);
		request.setId(id);
		SpaceShip entity = spaceShipService.update(modelMapper.map(request, SpaceShip.class));
		log.info(PUT_SPACESHIP_RESULT_MSG, id);
		return ResponseEntity.ok(modelMapper.map(entity, PutSpaceShipResponse.class));
	}

	@Override
	@DeleteMapping(ID_URI)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ServiceOperation(DELETE_SPACESHIP_SERVICE_OPERATION)
	@Operation(summary = DELETE_SPACESHIP_SERVICE_OPERATION, description = DELETE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION)
	public ResponseEntity deleteSpaceCrewMember(@PathVariable("id") Long id) {
		log.trace(DELETE_SPACESHIP_REQUEST_MSG, id);
		spaceShipService.deleteById(id);
		log.trace(DELETE_SPACESHIP_RESULT_MSG, id);
		return ResponseEntity.noContent().build();
	}


}
