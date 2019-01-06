package com.adventuresof.game.combat;

import com.adventuresof.game.animation.SpellAnimation;
import com.adventuresof.game.character.GameCharacter;
import com.adventuresof.game.common.MovementSpeedEnum;

public enum SpellEnum {
	// basic level abilities
	Tornado(new TornadoAnimation(),  50, 50, 10, MovementSpeedEnum.FAST, "audio/effects/windSpell.wav", SpellType.projectile, CoolDownEnum.Short, "spells//air-burst-air-1.png", true),

	// secondary level abilities
	Arrow(new ArrowAnimation(), 50, 50, 20, MovementSpeedEnum.VERY_FAST, "audio/effects/Bow.wav", SpellType.projectile, CoolDownEnum.Medium,  "spells//slice-sky-3.png", true),
	Firelion(new FireballAnimation(), 50, 50, 20, MovementSpeedEnum.VERY_FAST, "audio/effects/fireLionSpell.wav", SpellType.projectile, CoolDownEnum.Medium,  "spells//rip-sky-3.png", true),

	// powerful level abilities
	IceBarrier(new IceBarrierAnimation(), 50, 50, 20, MovementSpeedEnum.VERY_FAST, "audio/effects/magicShield.wav", SpellType.buff, CoolDownEnum.Medium,  "spells//shielding-eerie-3.png", false),
	FireBarrier(new FireBarrierAnimation(), 50, 50, 20, MovementSpeedEnum.VERY_FAST, "audio/effects/magicShield.wav", SpellType.buff, CoolDownEnum.Medium,  "spells//shielding-fire-3.png", false, 80),

	// ultimate abilities
	IceSpell(new IceSpellAnimation(), 50, 50, 30, MovementSpeedEnum.VERY_FAST, "audio/effects/ice.wav", SpellType.instantCast, CoolDownEnum.Medium,  "spells//needles-blue-3.png", true, 120),
	LightningClaw(new LightningClawAnimation(), 50, 50, 20, MovementSpeedEnum.VERY_FAST, "audio/effects/lightning.wav", SpellType.instantCast, CoolDownEnum.Medium,  "spells//shielding-eerie-3.png", false, 120);


	private SpellAnimation animation;
	private String actionBarImage;
	private float width;
	private float height;
	private int damage;
	private float movementSpeed; // Applicable for projectile abilities
	private String soundEffect;
	private CoolDownEnum coolDown;
	private SpellType spellType;
	private boolean isHostile;
	private int areaOfAffect; // represents the size of the spell targeting zone (especially useful sfor things such as AOE abilities)
	
	SpellEnum(SpellAnimation animation, float width, float height, int damage, MovementSpeedEnum movementSpeed, 
			String soundEffect, SpellType spellType, CoolDownEnum coolDown, String actionBarImage, boolean isHostile, int areaOfAffect) {
		this.animation = animation;
		this.actionBarImage = actionBarImage;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.movementSpeed = movementSpeed.getSpeed();
		this.soundEffect = soundEffect;
		this.spellType = spellType;
		this.coolDown = coolDown;
		this.isHostile = isHostile;
		this.areaOfAffect = areaOfAffect;
	}
	
	SpellEnum(SpellAnimation animation, float width, float height, int damage, MovementSpeedEnum movementSpeed, 
			String soundEffect, SpellType spellType, CoolDownEnum coolDown, String actionBarImage, boolean isHostile) {
		this(animation, width, height, damage, movementSpeed, soundEffect, spellType, coolDown, actionBarImage, isHostile, 20);
	}

	public int getAreaOfAffect() {
		return areaOfAffect;
	}

	public boolean isHostile() {
		return isHostile;
	}

	public String getActionBarImage() {
		return actionBarImage;
	}

	public CoolDownEnum getCoolDown() {
		return coolDown;
	}

	public SpellType getSpellType() {
		return spellType;
	}

	public SpellAnimation getAnimation() {
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
