package com.training.springboot.spaceover.spaceship.manager.domain.response.outbound;

import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipStatus;
import com.training.springboot.spaceover.spaceship.manager.enums.SpaceShipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSpaceShipResponse extends RepresentationModel<GetSpaceShipResponse> {

    private Long id;

    private String name;

    private SpaceShipStatus status;

    private BigInteger maxOccupancy;

    private SpaceShipType type;

}
