package com.training.springboot.spaceover.spaceship.manager.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import java.math.BigInteger;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutSpaceShipRequest {

    @JsonIgnore
    private Long id;

    @NotEmpty
    private String name;

    private SpaceShipStatus status;

    @PositiveOrZero
    private BigInteger maxOccupancy;

    private SpaceShipType type;

}
