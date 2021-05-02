package com.training.springboot.spaceover.spaceship.manager.repository;

import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceShipRepository extends JpaRepository<SpaceShip, Long> {
}
