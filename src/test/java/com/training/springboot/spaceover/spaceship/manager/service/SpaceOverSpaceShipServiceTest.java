package com.training.springboot.spaceover.spaceship.manager.service;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIP;
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
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Given no arguments, when invoking findAll method, then return Spaceship List")
    void findAllList() {

        //Arrange
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

        //Act
        List<SpaceShip> spaceShipList = spaceShipService.findAll();

        //Assert
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
    @DisplayName("Given a Spaceship and Pageable arguments, when invoking findAll method, then return Spaceship Page")
    void findAllPage() {

        //Arrange
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

        //Act
        when(spaceShipRepository.findAll(spaceShipExample, pageRequest))
                .thenReturn(new PageImpl<>(spaceShipList, pageRequest, 5));

        Page<SpaceShip> spaceShipPage = spaceShipService.findAll(entitySample, pageRequest);

        //Assert
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
    @DisplayName("Given a valid Spaceship identifier argument, when invoking findById method, then return Spaceship")
    void findBydId() {

        //Arrange
        SpaceShip spaceShip = SpaceShip.builder()
                .id(1L)
                .name("Millennium Falcon")
                .maxOccupancy(BigInteger.TEN)
                .status(SpaceShipStatus.ACTIVE)
                .type(SpaceShipType.STAR_CRUISER)
                .build();

        when(spaceShipRepository.findById(1L)).thenReturn(Optional.of(spaceShip));

        //Act
        spaceShip = spaceShipService.findBydId(1L);

        //Assert
        //Assert spaceship
        assertNotNull(spaceShip);
        assertEquals(1L, spaceShip.getId());
        assertEquals("Millennium Falcon", spaceShip.getName());
        assertEquals(SpaceShipStatus.ACTIVE, spaceShip.getStatus());
        assertEquals(SpaceShipType.STAR_CRUISER, spaceShip.getType());
        assertEquals(BigInteger.TEN, spaceShip.getMaxOccupancy());

    }

    @Test
    @DisplayName("Given an invalid Spaceship identifier argument, when invoking findById method, then throw EntityNotFoundException")
    void findBydIdThrowEntityNotFound() {

        //Arrange
        when(spaceShipRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        //Assert exception
        assertThrows(EntityNotFoundException.class, () -> spaceShipService.findBydId(1L), String.format(ENTITY_NOT_FOUND_MSG, SPACESHIP, "1"));

    }

    @Test
    @DisplayName("Given a Spaceship, when invoking save method, then return Spaceship")
    void save() {

        //Arrange
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

        //Act
        spaceShip = spaceShipService.save(spaceShip);

        //Assert
        //Assert spaceship
        assertNotNull(spaceShip);
        assertEquals(1L, spaceShip.getId());
        assertEquals("Millennium Falcon", spaceShip.getName());
        assertEquals(SpaceShipStatus.ACTIVE, spaceShip.getStatus());
        assertEquals(SpaceShipType.STAR_CRUISER, spaceShip.getType());
        assertEquals(BigInteger.TEN, spaceShip.getMaxOccupancy());

    }

    @Test
    @DisplayName("Given a Spaceship, when invoking update method, then return Spaceship")
    void update() {
        //Arrange
        SpaceShip spaceShip = SpaceShip.builder()
                .id(1L)
                .name("Millennium Falcon")
                .maxOccupancy(BigInteger.TEN)
                .status(SpaceShipStatus.INACTIVE)
                .type(SpaceShipType.STAR_CRUISER)
                .build();

        when(spaceShipRepository.save(spaceShip)).thenReturn(spaceShip);
        //Act
        spaceShip = spaceShipService.update(spaceShip);
        //Assert
        //Assert spaceship
        assertNotNull(spaceShip);
        assertEquals(1L, spaceShip.getId());
        assertEquals("Millennium Falcon", spaceShip.getName());
        assertEquals(SpaceShipStatus.INACTIVE, spaceShip.getStatus());
        assertEquals(SpaceShipType.STAR_CRUISER, spaceShip.getType());
        assertEquals(BigInteger.TEN, spaceShip.getMaxOccupancy());

    }

    @Test
    @DisplayName("Given a Spaceship identifier, when invoking delete method, then invoke repository.deleteById")
    void deleteById() {
        //No arrange required
        //Act
        spaceShipService.deleteById(1L);
        //Assert
        //verify method call count
        verify(spaceShipRepository, times(1)).deleteById(1L);

    }
}