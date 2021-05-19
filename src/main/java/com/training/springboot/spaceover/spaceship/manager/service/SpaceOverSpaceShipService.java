package com.training.springboot.spaceover.spaceship.manager.service;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIP;

import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.repository.SpaceShipRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceOverSpaceShipService implements SpaceShipService {

	private final SpaceShipRepository spaceShipRepository;

	@Override
	public Page<SpaceShip> findAll(SpaceShip entitySample, Pageable pageRequest) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.exact());
		return spaceShipRepository.findAll(Example.of(entitySample, exampleMatcher), pageRequest);
	}

	@Override
	public List<SpaceShip> findAll() {
		return spaceShipRepository.findAll();
	}

	@Override
	public SpaceShip findBydId(Long id) {
		return spaceShipRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, SPACESHIP, id)));
	}

	@Override
	public SpaceShip save(SpaceShip entity) {
		entity.setStatus(SpaceShipStatus.ACTIVE);
		return spaceShipRepository.save(entity);
	}

	@Override
	public SpaceShip update(SpaceShip entity) {
		return spaceShipRepository.save(entity);
	}

	@Override
	public void deleteById(Long id) {
		spaceShipRepository.deleteById(id);
	}
}
