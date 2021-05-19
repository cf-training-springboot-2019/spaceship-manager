package com.training.springboot.spaceover.spaceship.manager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
@Sql("/scripts/import_spaceships.sql")
class SpaceShipRepositoryTest {

	@Autowired
	private SpaceShipRepository spaceShipRepository;

	@Test
	void findAllList() {

		List<SpaceShip> spaceShipList = spaceShipRepository.findAll();

		//Assert collection
		assertNotNull(spaceShipList);
		assertEquals(3, spaceShipList.size());

		//Assert first spaceship
		assertNotNull(spaceShipList.get(0));
		assertEquals(1L, spaceShipList.get(0).getId());
		assertEquals("Millennium Falcon", spaceShipList.get(0).getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShipList.get(0).getStatus());
		assertEquals(SpaceShipType.STAR_CRUISER, spaceShipList.get(0).getType());
		assertEquals(BigInteger.TEN, spaceShipList.get(0).getMaxOccupancy());

		//Assert second spaceship
		assertNotNull(spaceShipList.get(1));
		assertEquals(2L, spaceShipList.get(1).getId());
		assertEquals("Red Fox", spaceShipList.get(1).getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShipList.get(1).getStatus());
		assertEquals(SpaceShipType.BATTLE_CRUISER, spaceShipList.get(1).getType());
		assertEquals(BigInteger.ONE, spaceShipList.get(1).getMaxOccupancy());

		//Assert third spaceship
		assertNotNull(spaceShipList.get(2));
		assertEquals(3L, spaceShipList.get(2).getId());
		assertEquals("Death Star", spaceShipList.get(2).getName());
		assertEquals(SpaceShipStatus.INACTIVE, spaceShipList.get(2).getStatus());
		assertEquals(SpaceShipType.COMBAT_CRUISER, spaceShipList.get(2).getType());
		assertEquals(BigInteger.valueOf(10000L), spaceShipList.get(2).getMaxOccupancy());

	}

	@Test
	void findAllPage() {

		SpaceShip spaceShipSample = SpaceShip.builder().build();

		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.exact());

		Pageable pageRequest = PageRequest.of(1, 1);

		Page<SpaceShip> spaceShipPage = spaceShipRepository
				.findAll(Example.of(spaceShipSample, exampleMatcher), pageRequest);

		//Assert collection
		assertNotNull(spaceShipPage);
		assertEquals(1, spaceShipPage.getNumberOfElements());
		assertEquals(3, spaceShipPage.getTotalElements());
		assertNotNull(spaceShipPage.getContent());
		assertTrue(spaceShipPage.hasPrevious());
		assertTrue(spaceShipPage.hasNext());

		//Assert spaceship
		assertNotNull(spaceShipPage.getContent().get(0));
		assertEquals(2L, spaceShipPage.getContent().get(0).getId());
		assertEquals("Red Fox", spaceShipPage.getContent().get(0).getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShipPage.getContent().get(0).getStatus());
		assertEquals(SpaceShipType.BATTLE_CRUISER, spaceShipPage.getContent().get(0).getType());
		assertEquals(BigInteger.ONE, spaceShipPage.getContent().get(0).getMaxOccupancy());

	}

	@Test
	void findById() {

		Optional<SpaceShip> spaceShip = spaceShipRepository.findById(1L);

		//Assert spaceship
		assertTrue(spaceShip.isPresent());
		assertNotNull(spaceShip.get());
		assertEquals(1L, spaceShip.get().getId());
		assertEquals("Millennium Falcon", spaceShip.get().getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShip.get().getStatus());
		assertEquals(SpaceShipType.STAR_CRUISER, spaceShip.get().getType());
		assertEquals(BigInteger.TEN, spaceShip.get().getMaxOccupancy());

	}

	@Test
	void findByIdReturnsOptional() {

		Optional<SpaceShip> spaceShip = spaceShipRepository.findById(10L);

		//Assert spaceship
		assertFalse(spaceShip.isPresent());

	}

	@Test
	void save() {

		SpaceShip spaceShip = SpaceShip.builder()
				.name("Blue Mongoose")
				.maxOccupancy(BigInteger.ONE)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.BATTLE_CRUISER)
				.build();

		spaceShip = spaceShipRepository.save(spaceShip);

		//Assert spaceship
		assertNotNull(spaceShip);
		assertEquals(4L, spaceShip.getId());
		assertEquals("Blue Mongoose", spaceShip.getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShip.getStatus());
		assertEquals(SpaceShipType.BATTLE_CRUISER, spaceShip.getType());
		assertEquals(BigInteger.ONE, spaceShip.getMaxOccupancy());

		//Assert collection
		assertEquals(4, spaceShipRepository.count());

	}

	@Test
	void saveThrowsDataIntegrityViolationException() {

		SpaceShip spaceShip = SpaceShip.builder()
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.INACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		// assert exception
		assertThrows(DataIntegrityViolationException.class, () -> spaceShipRepository.save(spaceShip));

	}

	@Test
	void saveUpdate() {

		SpaceShip spaceShip = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.INACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		spaceShip = spaceShipRepository.save(spaceShip);

		//Assert spaceship
		assertNotNull(spaceShip);
		assertEquals(1L, spaceShip.getId());
		assertEquals("Millennium Falcon", spaceShip.getName());
		assertEquals(SpaceShipStatus.INACTIVE, spaceShip.getStatus());
		assertEquals(SpaceShipType.STAR_CRUISER, spaceShip.getType());
		assertEquals(BigInteger.TEN, spaceShip.getMaxOccupancy());

		//Assert collection
		assertEquals(3, spaceShipRepository.count());

	}

	@Test
	void delete() {

		spaceShipRepository.deleteById(1L);

		//Assert collection
		assertEquals(2, spaceShipRepository.count());

	}

}