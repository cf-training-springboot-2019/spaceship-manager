package com.training.springboot.spaceover.spaceship.manager.domain.response.outbound;

import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSpaceShipResponse {

    private Long id;

    private String name;

    private SpaceShipStatus status;

    private BigInteger maxOccupancy;

    private SpaceShipType type;

}
