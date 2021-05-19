package com.training.springboot.spaceover.spaceship.manager.enums;

public enum SpaceShipStatus {

    ACTIVE, INACTIVE;

    public static SpaceShipStatus fromName(String name) {
        for (SpaceShipStatus e : values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }

}
