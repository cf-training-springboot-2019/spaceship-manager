package com.training.springboot.spaceover.spaceship.manager.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSpaceShipRequest {

    @JsonIgnore
    private Long id;

    @NotEmpty
    private String name;

    private SpaceShipStatus status;

    @PositiveOrZero
    private BigInteger maxOccupancy;

    private SpaceShipType type;

}
