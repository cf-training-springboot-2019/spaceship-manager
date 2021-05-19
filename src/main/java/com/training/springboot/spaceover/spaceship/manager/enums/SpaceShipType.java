package com.training.springboot.spaceover.spaceship.manager.enums;

public enum SpaceShipType {

	ATTACK_CRUISER, BATTLE_CRUISER, COMBAT_CRUISER, HEAVY_CRUISER, LIGHT_CRUISER, STAR_CRUISER;

	public static SpaceShipType fromName(String name) {
		for (SpaceShipType e : values()) {
			if (e.name().equals(name)) {
				return e;
			}
		}
		return null;
	}

}
