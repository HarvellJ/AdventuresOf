package com.adventuresof.game.character.enums;

import com.adventuresof.game.combat.SpellEnum;

public enum CharacterClass {
	melee(SpellEnum.Tornado, SpellEnum.Tornado, SpellEnum.FireBarrier, SpellEnum.Tornado, 150),
	ranger(SpellEnum.Arrow, SpellEnum.Arrow, SpellEnum.Arrow, SpellEnum.LightningClaw, 0), 
	mage(SpellEnum.Tornado, SpellEnum.Firelion, SpellEnum.IceBarrier, SpellEnum.IceSpell, 0),
	hybrid(SpellEnum.Tornado, SpellEnum.Arrow, SpellEnum.Tornado, SpellEnum.Tornado, 0),
	// NPC classes
	lavaZoneNPC_Ranger(SpellEnum.Arrow_NPC, SpellEnum.Arrow_NPC, SpellEnum.Arrow_NPC, SpellEnum.Arrow_NPC, 0), 
	lavaZoneNPC_Boss(SpellEnum.Arrow_Boss, SpellEnum.Arrow_Boss, SpellEnum.Arrow_Boss, SpellEnum.Arrow_Boss, 0), 

	mudZoneNPC(SpellEnum.SpikeSpell, SpellEnum.SpikeSpell, SpellEnum.SpikeSpell, SpellEnum.SpikeSpell, 0),
	mudZoneBoss(SpellEnum.SpikeSpell_Boss, SpellEnum.SpikeSpell_Boss, SpellEnum.SpikeSpell_Boss, SpellEnum.SpikeSpell_Boss, 0),

	jungleZoneNPC_Mage(SpellEnum.Firelion, SpellEnum.Firelion, SpellEnum.Firelion, SpellEnum.Firelion, 0),
	jungleZoneNPC_Melee(SpellEnum.FireBarrier_NPC, SpellEnum.FireBarrier_NPC, SpellEnum.FireBarrier_NPC, SpellEnum.FireBarrier_NPC, 0),
	jungleZoneBoss(SpellEnum.FireSpell_Boss, SpellEnum.FireSpell_Boss, SpellEnum.FireSpell_Boss, SpellEnum.FireSpell_Boss, 0);

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
