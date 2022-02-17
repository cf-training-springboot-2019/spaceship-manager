package com.training.springboot.spaceover.spaceship.manager.service;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIP;

import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.repository.SpaceShipRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//LT#2 Implement SpaceShipService
@Service
@RequiredArgsConstructor
public class SpaceOverSpaceShipService implements SpaceShipService {

    private final SpaceShipRepository spaceShipRepository;

    @Override
    public Page<SpaceShip> findAll(SpaceShip entitySample, Pageable pageRequest) {
        return null;
    }

    @Override
    //LT#2-3 Implement SpaceShipService findAll method
    public List<SpaceShip> findAll() {
        return spaceShipRepository.findAll(); //LT#3-3 Integrate with SpaceShipRepository findAll method
    }

    @Override
    //LT#2-2 Implement SpaceShipService findById method
    public SpaceShip findBydId(Long id) {
        return spaceShipRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, SPACESHIP, id))); // LT#3-2 Integrate with SpaceShipRepository findById method
    }

    @Override
    //LT#2-1 Implement SpaceShipService save method
    public SpaceShip save(SpaceShip entity) {
        entity.setStatus(SpaceShipStatus.ACTIVE);
        return spaceShipRepository.save(entity); //LT#3-1 Integrate with SpaceShipRepository save method
    }

    @Override
    //LT#2-4 Implement SpaceShipService update method
    public SpaceShip update(SpaceShip entity) {
        return spaceShipRepository.save(entity); //LT#3-1 Integrate with SpaceShipRepository save method
    }

    @Override
    //LT#2-5 Implement SpaceShipService delete method
    public void deleteById(Long id) {
        spaceShipRepository.deleteById(id);
        //LT#3-4 Integrate with SpaceShipRepository deleteById method
    }
}
