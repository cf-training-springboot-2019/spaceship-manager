package com.training.springboot.spaceover.spaceship.manager.domain.response.outbound;

import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchSpaceShipResponse {

	private Long id;

	private String name;

	private SpaceShipStatus status;

	private BigInteger maxOccupancy;

	private SpaceShipType type;

}
