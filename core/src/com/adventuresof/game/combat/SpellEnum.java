package com.adventuresof.game.combat;

import com.adventuresof.game.animation.DirectionalProjectileAnimation;
import com.adventuresof.game.common.MovementSpeedEnum;

public enum SpellEnum {
	Tornado(new TornadoAnimation(),  16, 16, 20, MovementSpeedEnum.FAST), Arrow(new ArrowAnimation(), 16, 16, 20, MovementSpeedEnum.VERY_FAST);
	
	private DirectionalProjectileAnimation animation;
	private float width;
	private float height;
	private int damage;
	private float movementSpeed;
	
	SpellEnum(DirectionalProjectileAnimation animation, float width, float height, int damage, MovementSpeedEnum movementSpeed) {
		this.animation = animation;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.movementSpeed = movementSpeed.getSpeed();
	}
	
	public DirectionalProjectileAnimation getAnimation() {
		return animation;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public int getDamage() {
		return damage;
	}
}
