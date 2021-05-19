package com.training.springboot.spaceover.spaceship.manager.domain.request.inbound;

import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import java.math.BigInteger;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSpaceShipRequest {

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	private SpaceShipType type;

	@NotNull
	@PositiveOrZero
	private BigInteger maxOccupancy;

}
