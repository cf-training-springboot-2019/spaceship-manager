package com.training.springboot.spaceover.spaceship.manager.domain.request.inbound;

import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSpaceShipResponse {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private SpaceShipStatus status;

    @NotNull
    private SpaceShipType type;

    @NotNull
    @PositiveOrZero
    private BigInteger maxOccupancy;

}
