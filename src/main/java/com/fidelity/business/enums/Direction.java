package com.fidelity.business.enums;

public enum Direction {
	BUY ("BUY"),
	SELL ("SELL"),;

	private final String direction;

	Direction(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}
	
}
