package com.adventuresof.game.character.enums;

import com.adventuresof.game.combat.SpellEnum;

public enum CharacterClass {
	melee(SpellEnum.Tornado, SpellEnum.Tornado, SpellEnum.FireBarrier, SpellEnum.Tornado, 150),
	ranger(SpellEnum.Arrow, SpellEnum.Arrow, SpellEnum.Arrow, SpellEnum.LightningClaw, 0), 
	mage(SpellEnum.Tornado, SpellEnum.Firelion, SpellEnum.IceBarrier, SpellEnum.IceSpell, 0),
	hybrid(SpellEnum.Tornado, SpellEnum.Arrow, SpellEnum.Tornado, SpellEnum.Tornado, 0),
	dragonBoss(SpellEnum.FireSpell_Boss, SpellEnum.FireSpell_Boss, SpellEnum.FireSpell_Boss, SpellEnum.FireSpell_Boss, 0);

	// abilities get stronger in ascending order
	// i.e. first ability is the character's basic ability, the fourth would be their ultimate ability
	private SpellEnum abilityOne; // first ability
	private SpellEnum abilityTwo; // secondary ability
	private SpellEnum abilityThree; // third ability
	private SpellEnum abilityFour; // fourth ability

	private int healthBoost;
	
	CharacterClass(SpellEnum abilityOne, SpellEnum abilityTwo, SpellEnum abilityThree, SpellEnum abilityFour, int healthBoost) {
		this.abilityOne = abilityOne;
		this.abilityTwo = abilityTwo;
		this.abilityThree = abilityThree;
		this.abilityFour = abilityFour;
		this.healthBoost = healthBoost;
	}

	public SpellEnum getAbilityOne() {
		return abilityOne;
	}

	public SpellEnum getAbilityTwo() {
		return abilityTwo;
	}

	public SpellEnum getAbilityThree() {
		return abilityThree;
	}

	public SpellEnum getAbilityFour() {
		return abilityFour;
	}

	public int getHealthBoost() {
		return healthBoost;
	}
}
