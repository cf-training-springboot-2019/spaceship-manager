package com.training.springboot.spaceover.spaceship.manager.controller;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.APPLICATION_JSON_PATCH;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.CREATE_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.DELETE_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIPS_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.GET_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PATCH_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.PUT_SPACESHIP_SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SERVICE_OPERATION_HEADER;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SPACESHIP;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.TRACE_ID_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.training.springboot.spaceover.spaceship.manager.utils.interceptors.HttpHeaderEnrichmentInterceptor;
import com.training.springboot.spaceover.spaceship.manager.utils.interceptors.MdcInitInterceptor;
import com.training.springboot.spaceover.spaceship.manager.utils.properties.SpaceShipManagerProperties;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

@WebMvcTest
@ActiveProfiles("test")
@AutoConfigureMockMvc()
class SpaceOverSpaceShipControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SpaceShipService spaceShipService;

	@MockBean
	private ModelMapper modelMapper;

	@MockBean
	private PagedResourcesAssembler<SpaceShip> pagedModelAssembler;

	@MockBean
	private PaginationModelAssembler modelAssembler;

	@MockBean
	private SpaceShipManagerProperties spaceShipManagerProperties;

	@Autowired
	private HttpHeaderEnrichmentInterceptor httpHeaderEnrichmentInterceptor;

	@Autowired
	private MdcInitInterceptor mdcInitInterceptor;

	@Value("classpath:samples/requests/createSpaceShip201.json")
	private Resource createSpaceShip201Request;

	@Value("classpath:samples/requests/createSpaceShip400.json")
	private Resource createSpaceShip400Request;

	@Value("classpath:samples/requests/createSpaceShip409.json")
	private Resource createSpaceShip409Request;


	@Value("classpath:samples/requests/patchSpaceShip200.json")
	private Resource patchSpaceShip200Request;

	@Value("classpath:samples/requests/putSpaceShip200.json")
	private Resource putSpaceShip200Request;

	@Value("classpath:samples/requests/putSpaceShip400.json")
	private Resource putSpaceShip400Request;

	@Test
	@SneakyThrows
	void getSpaceShipsOk() {

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

		GetSpaceShipResponse spaceShipOneResponse = GetSpaceShipResponse.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		GetSpaceShipResponse spaceShipTwoResponse = GetSpaceShipResponse.builder()
				.id(2L)
				.name("Red Fox")
				.maxOccupancy(BigInteger.ONE)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.BATTLE_CRUISER)
				.build();

		GetSpaceShipResponse spaceShipThreeResponse = GetSpaceShipResponse.builder()
				.id(3L)
				.name("Death Star")
				.maxOccupancy(BigInteger.valueOf(10000L))
				.status(SpaceShipStatus.INACTIVE)
				.type(SpaceShipType.COMBAT_CRUISER)
				.build();

		List<SpaceShip> spaceShipList = Arrays.asList(spaceShipOne, spaceShipTwo, spaceShipThree);
		List<GetSpaceShipResponse> spaceShipResponseList = Arrays
				.asList(spaceShipOneResponse, spaceShipTwoResponse, spaceShipThreeResponse);

		SpaceShip spaceShipSample = SpaceShip.builder().build();

		Pageable pageRequest = PageRequest.of(0, 20);

		Page<SpaceShip> spaceShipPage = new PageImpl<>(spaceShipList, pageRequest, 5);

		PagedModel<GetSpaceShipResponse> response = PagedModel.of(spaceShipResponseList, new PageMetadata(20, 0, 3, 1));
		when(spaceShipService.findAll(spaceShipSample, pageRequest)).thenReturn(spaceShipPage);
		when(pagedModelAssembler.toModel(eq(spaceShipPage), any(PaginationModelAssembler.class))).thenReturn(response);

		mockMvc.perform(get("/spaceships")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, GET_SPACESHIPS_SERVICE_OPERATION))
				.andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[0].id").value(1))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[0].name").value("Millennium Falcon"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[0].status").value("ACTIVE"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[0].type").value("STAR_CRUISER"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[0].maxOccupancy").value(10))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[1].id").value(2))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[1].name").value("Red Fox"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[1].status").value("ACTIVE"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[1].type").value("BATTLE_CRUISER"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[1].maxOccupancy").value(1))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[2].id").value(3))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[2].name").value("Death Star"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[2].status").value("INACTIVE"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[2].type").value("COMBAT_CRUISER"))
				.andExpect(jsonPath("$._embedded.getSpaceShipResponses[2].maxOccupancy").value(10000))
				.andExpect(jsonPath("$.page.number").value(0))
				.andExpect(jsonPath("$.page.size").value(20))
				.andExpect(jsonPath("$.page.totalElements").value(3))
				.andExpect(jsonPath("$.page.totalPages").value(1));

	}

	@Test
	@SneakyThrows
	void getSpaceShipOk() {

		SpaceShip spaceShip = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		GetSpaceShipResponse response = GetSpaceShipResponse.builder()
				.id(1L)
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		when(spaceShipService.findBydId(1L)).thenReturn(spaceShip);
		when(modelMapper.map(spaceShip, GetSpaceShipResponse.class)).thenReturn(response);

		mockMvc.perform(get("/spaceships/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, GET_SPACESHIP_SERVICE_OPERATION))
				.andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Millennium Falcon"))
				.andExpect(jsonPath("$.status").value("ACTIVE"))
				.andExpect(jsonPath("$.type").value("STAR_CRUISER"))
				.andExpect(jsonPath("$.maxOccupancy").value(10));

	}

	@Test
	@SneakyThrows
	void getSpaceShipNotFound() {

		when(spaceShipService.findBydId(1L))
				.thenThrow(new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, SPACESHIP, 1L)));

		mockMvc.perform(get("/spaceships/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, GET_SPACESHIP_SERVICE_OPERATION))
				.andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
				.andExpect(jsonPath("$.reason").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
				.andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
				.andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
				.andExpect(jsonPath("$.message").value(String.format(ENTITY_NOT_FOUND_MSG, SPACESHIP, 1L)));

	}

	@Test
	@SneakyThrows
	void createSpaceShipCreated() {

		CreateSpaceShipRequest request = CreateSpaceShipRequest.builder()
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

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

		String responseContent = FileCopyUtils.copyToString(new FileReader(createSpaceShip201Request.getFile()));

		when(modelMapper.map(request, SpaceShip.class)).thenReturn(spaceShip);
		when(spaceShipService.save(spaceShip)).thenReturn(persistedSpaceShip);

		mockMvc.perform(post("/spaceships")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(responseContent))
				.andExpect(status().isCreated())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, CREATE_SPACESHIP_SERVICE_OPERATION))
				.andExpect(header().string(LOCATION, "http://localhost/spaceships/1"));

	}

	@Test
	@SneakyThrows
	void createSpaceShipBadRequest() {

		String responseContent = FileCopyUtils.copyToString(new FileReader(createSpaceShip400Request.getFile()));

		mockMvc.perform(post("/spaceships")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(responseContent))
				.andExpect(status().isBadRequest())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, CREATE_SPACESHIP_SERVICE_OPERATION))
				.andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
				.andExpect(jsonPath("$.reason").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
				.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath("$.message").value("name must not be null; name must not be empty"));

	}

	@Test
	@SneakyThrows
	void createSpaceShipConflict() {

		CreateSpaceShipRequest request = CreateSpaceShipRequest.builder()
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		SpaceShip spaceShip = SpaceShip.builder()
				.name("Millennium Falcon")
				.maxOccupancy(BigInteger.TEN)
				.type(SpaceShipType.STAR_CRUISER)
				.build();

		when(modelMapper.map(request, SpaceShip.class)).thenReturn(spaceShip);
		when(spaceShipService.save(spaceShip)).thenThrow(new DataIntegrityViolationException("Conflict"));

		String responseContent = FileCopyUtils.copyToString(new FileReader(createSpaceShip409Request.getFile()));

		mockMvc.perform(post("/spaceships")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(responseContent))
				.andExpect(status().isConflict())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, CREATE_SPACESHIP_SERVICE_OPERATION))
				.andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
				.andExpect(jsonPath("$.reason").value(HttpStatus.CONFLICT.getReasonPhrase()))
				.andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
				.andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
				.andExpect(jsonPath("$.message").value("Conflict"));

	}

	@Test
	@SneakyThrows
	void patchSpaceShipOk() {

		SpaceShip spaceShip = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.status(SpaceShipStatus.INACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.maxOccupancy(BigInteger.TEN)
				.build();

		PatchSpaceShipResponse response = PatchSpaceShipResponse.builder()
				.id(1L)
				.name("Millennium Falcon")
				.status(SpaceShipStatus.INACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.maxOccupancy(BigInteger.TEN)
				.build();

		String responseContent = FileCopyUtils.copyToString(new FileReader(patchSpaceShip200Request.getFile()));

		when(spaceShipService.findBydId(1L)).thenReturn(spaceShip);
		when(spaceShipService.update(spaceShip)).thenReturn(spaceShip);
		when(modelMapper.map(spaceShip, PatchSpaceShipResponse.class)).thenReturn(response);

		mockMvc.perform(patch("/spaceships/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(APPLICATION_JSON_PATCH)
				.content(responseContent))
				.andExpect(status().isOk())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, PATCH_SPACESHIP_SERVICE_OPERATION))
				.andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Millennium Falcon"))
				.andExpect(jsonPath("$.status").value("INACTIVE"))
				.andExpect(jsonPath("$.type").value("STAR_CRUISER"))
				.andExpect(jsonPath("$.maxOccupancy").value(10));

	}

	@Test
	@SneakyThrows
	void putSpaceShipOk() {

		PutSpaceShipRequest request = PutSpaceShipRequest.builder()
				.id(1L)
				.name("Millennium Falcon")
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.maxOccupancy(BigInteger.TEN)
				.build();

		SpaceShip spaceShip = SpaceShip.builder()
				.id(1L)
				.name("Millennium Falcon")
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.maxOccupancy(BigInteger.TEN)
				.build();

		PutSpaceShipResponse response = PutSpaceShipResponse.builder()
				.id(1L)
				.name("Millennium Falcon")
				.status(SpaceShipStatus.ACTIVE)
				.type(SpaceShipType.STAR_CRUISER)
				.maxOccupancy(BigInteger.TEN)
				.build();

		String responseContent = FileCopyUtils.copyToString(new FileReader(putSpaceShip200Request.getFile()));

		when(modelMapper.map(request, SpaceShip.class)).thenReturn(spaceShip);
		when(spaceShipService.update(spaceShip)).thenReturn(spaceShip);
		when(modelMapper.map(spaceShip, PutSpaceShipResponse.class)).thenReturn(response);

		mockMvc.perform(put("/spaceships/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(responseContent))
				.andExpect(status().isOk())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, PUT_SPACESHIP_SERVICE_OPERATION))
				.andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Millennium Falcon"))
				.andExpect(jsonPath("$.status").value("ACTIVE"))
				.andExpect(jsonPath("$.type").value("STAR_CRUISER"))
				.andExpect(jsonPath("$.maxOccupancy").value(10));

	}

	@Test
	@SneakyThrows
	void putSpaceShipBadRequest() {

		String responseContent = FileCopyUtils.copyToString(new FileReader(putSpaceShip400Request.getFile()));

		mockMvc.perform(put("/spaceships/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(responseContent))
				.andExpect(status().isBadRequest())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, PUT_SPACESHIP_SERVICE_OPERATION))
				.andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
				.andExpect(jsonPath("$.reason").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
				.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath("$.message").value("name must not be empty"));

	}

	@Test
	@SneakyThrows
	void deleteSpaceCrewMemberNoContent() {

		mockMvc.perform(delete("/spaceships/1"))
				.andExpect(status().isNoContent())
				.andExpect(header().exists(TRACE_ID_HEADER))
				.andExpect(header().string(SERVICE_OPERATION_HEADER, DELETE_SPACESHIP_SERVICE_OPERATION));

	}
}