package com.adventuresof.game.common.enums;

public enum MovementSpeed {
	VERY_FAST(250.0F), FAST(200.0F), NORMAL_FAST(150.0F),NORMAL_MEDIUM(120.0F), NORMAL_SLOW(100.0F), SLOW(80.0F), VERY_SLOW(60.0F);
		
	private final float speed;
	
	private MovementSpeed(float speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}

}
