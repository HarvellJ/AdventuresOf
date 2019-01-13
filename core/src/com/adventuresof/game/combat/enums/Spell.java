package com.adventuresof.game.combat.enums;

import com.adventuresof.game.animation.ArrowAnimation;
import com.adventuresof.game.animation.FireBarrierAnimation;
import com.adventuresof.game.animation.FireballAnimation;
import com.adventuresof.game.animation.IceBarrierAnimation;
import com.adventuresof.game.animation.IceSpellAnimation;
import com.adventuresof.game.animation.LightningClawAnimation;
import com.adventuresof.game.animation.SpellAnimation;
import com.adventuresof.game.animation.TornadoAnimation;
import com.adventuresof.game.animation.MudSpikesAnimation;
import com.adventuresof.game.animation.IceSpikesAnimation;
import com.adventuresof.game.animation.PowerupAnimation;
import com.adventuresof.game.animation.ProjectileMaceAnimation;
import com.adventuresof.game.common.enums.MovementSpeed;

public enum Spell {
	// basic level abilities
	Tornado(new TornadoAnimation(),  50, 50, 10, MovementSpeed.FAST, "audio/effects/windSpell.wav", SpellType.multiProjectile, CoolDownEnum.Short, "spells//air-burst-air-1.png", true),
	SpikeSpell(new MudSpikesAnimation(), 50, 50, 20, MovementSpeed.FAST, "audio/effects/fireLionSpell.wav", SpellType.projectile, CoolDownEnum.Short,  "spells//rip-sky-3.png", true),
	FireBarrier_NPC(new FireBarrierAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/magicShield.wav", SpellType.buff, CoolDownEnum.Long,  "spells//shielding-fire-3.png", false, 80),
	Arrow_NPC(new ArrowAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/Bow.wav", SpellType.projectile, CoolDownEnum.Medium,  "spells//slice-sky-3.png", true),
	Arrow(new ArrowAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/Bow.wav", SpellType.projectile, CoolDownEnum.Short,  "spells//slice-sky-3.png", true),
	Powerup(new PowerupAnimation(), 50, 50, 40, MovementSpeed.VERY_FAST, "audio/effects/magicShield.wav", SpellType.buff, CoolDownEnum.Long,  "spells//wind-grasp-acid-3.png", false, 80),

	// secondary level abilities
	ArrowMulti(new ArrowAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/Bow.wav", SpellType.multiProjectile, CoolDownEnum.Medium,  "spells//slice-orange-3.png", true),
	Firelion(new FireballAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/fireLionSpell.wav", SpellType.projectile, CoolDownEnum.Medium,  "spells//rip-sky-3.png", true),
	ThrowingMace(new ProjectileMaceAnimation(), 50, 50, 30, MovementSpeed.VERY_FAST, "audio/effects/fireLionSpell.wav", SpellType.projectile, CoolDownEnum.Short,  "spells//slice-sky-3.png", false),

	// powerful level abilities
	IceBarrier(new IceBarrierAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/magicShield.wav", SpellType.buff, CoolDownEnum.VeryLong,  "spells//shielding-eerie-3.png", false),
	FireBarrier(new FireBarrierAnimation(), 50, 50, 50, MovementSpeed.VERY_FAST, "audio/effects/magicShield.wav", SpellType.buff, CoolDownEnum.VeryLong,  "spells//shielding-fire-3.png", false, 80),

	// ultimate abilities
	IceSpell(new IceSpellAnimation(), 50, 50, 30, MovementSpeed.VERY_FAST, "audio/effects/ice.wav", SpellType.instantCast, CoolDownEnum.Long,  "spells//needles-blue-3.png", true, 120),
	LightningClaw(new LightningClawAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/lightning.wav", SpellType.instantCast, CoolDownEnum.Medium,  "spells//needles-sky-3.png", false, 120),
	FireSpell_Boss(new FireballAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/fireLionSpell.wav", SpellType.projectile, CoolDownEnum.VeryShort,  "spells//rip-sky-3.png", true),
	SpikeSpell_Boss(new MudSpikesAnimation(), 50, 50, 20, MovementSpeed.FAST, "audio/effects/fireLionSpell.wav", SpellType.multiProjectile, CoolDownEnum.Short,  "spells//rip-sky-3.png", true),
	Arrow_Boss(new ArrowAnimation(), 50, 50, 20, MovementSpeed.VERY_FAST, "audio/effects/Bow.wav", SpellType.multiProjectile, CoolDownEnum.Short,  "spells//slice-sky-3.png", true),
	IceSpikes(new IceSpikesAnimation(), 50, 50, 30, MovementSpeed.VERY_FAST, "audio/effects/fireLionSpell.wav", SpellType.multiProjectile, CoolDownEnum.Long,  "spells//needles-blue-1.png", false);


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
	
	Spell(SpellAnimation animation, float width, float height, int damage, MovementSpeed movementSpeed, 
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
	
	Spell(SpellAnimation animation, float width, float height, int damage, MovementSpeed movementSpeed, 
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
