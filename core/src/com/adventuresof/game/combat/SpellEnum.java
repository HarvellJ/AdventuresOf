package com.adventuresof.game.combat;

import com.adventuresof.game.animation.DirectionalProjectileAnimation;
import com.adventuresof.game.common.MovementSpeedEnum;

public enum SpellEnum {
	// basic level abilities
	Tornado(new TornadoAnimation(),  16, 16, 10, MovementSpeedEnum.FAST, "audio/effects/windSpell.wav", SpellType.projectile, CoolDownEnum.Short),
	Arrow(new ArrowAnimation(), 16, 16, 20, MovementSpeedEnum.VERY_FAST, "audio/effects/Bow.wav", SpellType.projectile, CoolDownEnum.Medium);
	// secondary level abilities
	// powerful level abilities
	// ultimate abilities
	private DirectionalProjectileAnimation animation;
	private float width;
	private float height;
	private int damage;
	private float movementSpeed; // Applicable for projectile abilities
	private String soundEffect;
	private CoolDownEnum coolDown;
	private SpellType spellType;
	
	SpellEnum(DirectionalProjectileAnimation animation, float width, float height, int damage, MovementSpeedEnum movementSpeed, 
			String soundEffect, SpellType spellType, CoolDownEnum coolDown) {
		this.animation = animation;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.movementSpeed = movementSpeed.getSpeed();
		this.soundEffect = soundEffect;
		this.spellType = spellType;
		this.coolDown = coolDown;
	}
	
	public CoolDownEnum getCoolDown() {
		return coolDown;
	}

	public SpellType getSpellType() {
		return spellType;
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

	public String getSoundEffect() {
		return soundEffect;
	}
}
