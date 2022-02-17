package com.training.springboot.spaceover.spaceship.manager.repository;

import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//LT#3 Integrate with SpaceShipRepository
@Repository
public interface SpaceShipRepository extends JpaRepository<SpaceShip, Long> {

}
