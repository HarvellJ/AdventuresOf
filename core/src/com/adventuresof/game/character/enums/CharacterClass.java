package com.adventuresof.game.character.enums;

import com.adventuresof.game.combat.enums.Spell;

/**
 * Information regarding the chosen Character. 
 * Deals with Abilities and Power ups.
 */
public enum CharacterClass {
	melee(Spell.ThrowingMace, Spell.Powerup, Spell.FireBarrier, Spell.IceSpikes, 150),
	ranger(Spell.Arrow, Spell.ArrowMulti, Spell.Powerup, Spell.LightningClaw, 0), 
	mage(Spell.Tornado, Spell.Firelion, Spell.IceBarrier, Spell.IceSpell, 0),
	hybrid(Spell.Tornado, Spell.Arrow, Spell.Tornado, Spell.Tornado, 0),
	// NPC classes
	lavaZoneNPC_Ranger(Spell.Arrow_NPC, Spell.Arrow_NPC, Spell.Arrow_NPC, Spell.Arrow_NPC, 0), 
	lavaZoneNPC_Boss(Spell.Arrow_Boss, Spell.Arrow_Boss, Spell.Arrow_Boss, Spell.Arrow_Boss, 0), 

	mudZoneNPC(Spell.SpikeSpell, Spell.SpikeSpell, Spell.SpikeSpell, Spell.SpikeSpell, 0),
	mudZoneBoss(Spell.SpikeSpell_Boss, Spell.SpikeSpell_Boss, Spell.SpikeSpell_Boss, Spell.SpikeSpell_Boss, 0),

	jungleZoneNPC_Mage(Spell.Firelion, Spell.Firelion, Spell.Firelion, Spell.Firelion, 0),
	jungleZoneNPC_Melee(Spell.FireBarrier_NPC, Spell.FireBarrier_NPC, Spell.FireBarrier_NPC, Spell.FireBarrier_NPC, 0),
	jungleZoneBoss(Spell.FireSpell_Boss, Spell.FireSpell_Boss, Spell.FireSpell_Boss, Spell.FireSpell_Boss, 0);

	// abilities get stronger in ascending order
	// i.e. first ability is the character's basic ability, the fourth would be their ultimate ability
	private Spell abilityOne; // first ability
	private Spell abilityTwo; // secondary ability
	private Spell abilityThree; // third ability
	private Spell abilityFour; // fourth ability

	private int healthBoost;
	
	CharacterClass(Spell abilityOne, Spell abilityTwo, Spell abilityThree, Spell abilityFour, int healthBoost) {
		this.abilityOne = abilityOne;
		this.abilityTwo = abilityTwo;
		this.abilityThree = abilityThree;
		this.abilityFour = abilityFour;
		this.healthBoost = healthBoost;
	}

	public Spell getAbilityOne() {
		return abilityOne;
	}

	public Spell getAbilityTwo() {
		return abilityTwo;
	}

	public Spell getAbilityThree() {
		return abilityThree;
	}

	public Spell getAbilityFour() {
		return abilityFour;
	}

	public int getHealthBoost() {
		return healthBoost;
	}
}
