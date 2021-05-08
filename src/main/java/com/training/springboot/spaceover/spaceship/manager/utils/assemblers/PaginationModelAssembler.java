package com.training.springboot.spaceover.spaceship.manager.utils.assemblers;

import com.training.springboot.spaceover.spaceship.manager.controller.SpaceOverSpaceShipController;
import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import com.training.springboot.spaceover.spaceship.manager.domain.response.outbound.GetSpaceShipResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class PaginationModelAssembler implements RepresentationModelAssembler<SpaceShip, GetSpaceShipResponse> {

    private final ModelMapper modelMapper;

    @Override
    public GetSpaceShipResponse toModel(SpaceShip entity) {
        GetSpaceShipResponse getSpaceShipResponse = modelMapper.map(entity, GetSpaceShipResponse.class);
        getSpaceShipResponse.add(linkTo(methodOn(SpaceOverSpaceShipController.class).getSpaceShip(entity.getId()))
                .withSelfRel());
        return getSpaceShipResponse;
    }

    @Override
    public CollectionModel<GetSpaceShipResponse> toCollectionModel(Iterable<? extends SpaceShip> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

}