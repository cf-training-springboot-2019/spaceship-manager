package com.training.springboot.spaceover.spaceship.manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.training.springboot.spaceover.spaceship.manager.domain.model.SpaceShip;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import com.training.springboot.spaceover.spaceship.manager.repository.SpaceShipRepository;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class SpaceOverSpaceShipServiceTest {

	@InjectMocks
	private SpaceOverSpaceShipService spaceShipService;

	@Mock
	private SpaceShipRepository spaceShipRepository;


	@Test
	void findAllList() {

		SpaceShip spaceShipOne = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		SpaceShip spaceShipTwo = SpaceShip.builder()
				.id(2L)
				.name("Red Fox")
				.maxOccupancy(BigInteger.ONE)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.BATTLE_CRUISER)
				.build();

		SpaceShip spaceShipThree = SpaceShip.builder()
				.id(3L)
				.name("Death Star")
				.maxOccupancy(BigInteger.valueOf(10000L))
				.status(SpaceShipStatus.INACTIVE)
				.type(SpaceShipType.COMBAT_CRUISER)
				.build();

		when(spaceShipRepository.findAll()).thenReturn(Arrays.asList(spaceShipOne, spaceShipTwo, spaceShipThree));

		List<SpaceShip> spaceShipList = spaceShipService.findAll();

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

		SpaceShip spaceShipOne = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		SpaceShip spaceShipTwo = SpaceShip.builder()
				.id(2L)
				.name("Red Fox")
				.maxOccupancy(BigInteger.ONE)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.BATTLE_CRUISER)
				.build();

		SpaceShip spaceShipThree = SpaceShip.builder()
				.id(3L)
				.name("Death Star")
				.maxOccupancy(BigInteger.valueOf(10000L))
				.status(SpaceShipStatus.INACTIVE)
				.type(SpaceShipType.COMBAT_CRUISER)
				.build();

		List<SpaceShip> spaceShipList = Arrays.asList(spaceShipOne, spaceShipTwo, spaceShipThree);

		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.exact());

		SpaceShip entitySample = SpaceShip.builder().build();
		Example<SpaceShip> spaceShipExample = Example.of(entitySample, exampleMatcher);

		Pageable pageRequest = PageRequest.of(0, 3);
		when(spaceShipRepository.findAll(spaceShipExample, pageRequest))
				.thenReturn(new PageImpl<>(spaceShipList, pageRequest, 5));

		Page<SpaceShip> spaceShipPage = spaceShipService.findAll(entitySample, pageRequest);

		//Assert collection
		assertNotNull(spaceShipPage);
		assertEquals(3, spaceShipPage.getNumberOfElements());
		assertEquals(5, spaceShipPage.getTotalElements());
		assertNotNull(spaceShipPage.getContent());

		//Assert first spaceship
		assertNotNull(spaceShipPage.getContent().get(0));
		assertEquals(1L, spaceShipPage.getContent().get(0).getId());
		assertEquals("Millennium Falcon", spaceShipPage.getContent().get(0).getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShipPage.getContent().get(0).getStatus());
		assertEquals(SpaceShipType.STAR_CRUISER, spaceShipPage.getContent().get(0).getType());
		assertEquals(BigInteger.TEN, spaceShipPage.getContent().get(0).getMaxOccupancy());

		//Assert second spaceship
		assertNotNull(spaceShipPage.getContent().get(1));
		assertEquals(2L, spaceShipPage.getContent().get(1).getId());
		assertEquals("Red Fox", spaceShipPage.getContent().get(1).getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShipPage.getContent().get(1).getStatus());
		assertEquals(SpaceShipType.BATTLE_CRUISER, spaceShipPage.getContent().get(1).getType());
		assertEquals(BigInteger.ONE, spaceShipPage.getContent().get(1).getMaxOccupancy());

		//Assert third spaceship
		assertNotNull(spaceShipPage.getContent().get(2));
		assertEquals(3L, spaceShipPage.getContent().get(2).getId());
		assertEquals("Death Star", spaceShipPage.getContent().get(2).getName());
		assertEquals(SpaceShipStatus.INACTIVE, spaceShipPage.getContent().get(2).getStatus());
		assertEquals(SpaceShipType.COMBAT_CRUISER, spaceShipPage.getContent().get(2).getType());
		assertEquals(BigInteger.valueOf(10000L), spaceShipPage.getContent().get(2).getMaxOccupancy());

	}

	@Test
	void findBydId() {

		SpaceShip spaceShip = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		when(spaceShipRepository.findById(1L)).thenReturn(Optional.of(spaceShip));

		spaceShip = spaceShipService.findBydId(1L);

		//Assert spaceship
		assertNotNull(spaceShip);
		assertEquals(1L, spaceShip.getId());
		assertEquals("Millennium Falcon", spaceShip.getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShip.getStatus());
		assertEquals(SpaceShipType.STAR_CRUISER, spaceShip.getType());
		assertEquals(BigInteger.TEN, spaceShip.getMaxOccupancy());

	}

	@Test
	void findBydIdThrowEntityNotFound() {

		when(spaceShipRepository.findById(1L)).thenThrow(new EntityNotFoundException());

		//Assert exception
		assertThrows(EntityNotFoundException.class, () -> spaceShipService.findBydId(1L));

	}

	@Test
	void save() {

		SpaceShip spaceShip = SpaceShip.builder()
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		SpaceShip persistedSpaceShip = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		when(spaceShipRepository.save(spaceShip)).thenReturn(persistedSpaceShip);

		spaceShip = spaceShipService.save(spaceShip);

		//Assert spaceship
		assertNotNull(spaceShip);
		assertEquals(1L, spaceShip.getId());
		assertEquals("Millennium Falcon", spaceShip.getName());
		assertEquals(SpaceShipStatus.ACTIVE, spaceShip.getStatus());
		assertEquals(SpaceShipType.STAR_CRUISER, spaceShip.getType());
		assertEquals(BigInteger.TEN, spaceShip.getMaxOccupancy());

	}

	@Test
	void update() {

		SpaceShip spaceShip = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.INACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		when(spaceShipRepository.save(spaceShip)).thenReturn(spaceShip);

		spaceShip = spaceShipService.update(spaceShip);

		//Assert spaceship
		assertNotNull(spaceShip);
		assertEquals(1L, spaceShip.getId());
		assertEquals("Millennium Falcon", spaceShip.getName());
		assertEquals(SpaceShipStatus.INACTIVE, spaceShip.getStatus());
		assertEquals(SpaceShipType.STAR_CRUISER, spaceShip.getType());
		assertEquals(BigInteger.TEN, spaceShip.getMaxOccupancy());

	}

	@Test
	void deleteById() {

		spaceShipService.deleteById(1L);

		//verify method call count
		verify(spaceShipRepository, times(1)).deleteById(1L);

	}
}