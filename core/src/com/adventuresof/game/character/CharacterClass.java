package com.adventuresof.game.character;

import com.adventuresof.game.combat.SpellEnum;

public enum CharacterClass {
	melee(SpellEnum.Tornado, SpellEnum.Tornado, SpellEnum.Tornado, SpellEnum.Tornado),
	ranger(SpellEnum.Arrow, SpellEnum.Arrow, SpellEnum.Arrow, SpellEnum.Arrow), 
	mage(SpellEnum.Tornado, SpellEnum.Firelion, SpellEnum.IceBarrier, SpellEnum.IceSpell),
	hybrid(SpellEnum.Tornado, SpellEnum.Arrow, SpellEnum.Tornado, SpellEnum.Tornado);
	
	// abilities get stronger in ascending order
	// i.e. first ability is the character's basic ability, the fourth would be their ultimate ability
	private SpellEnum abilityOne; // first ability
	private SpellEnum abilityTwo; // secondary ability
	private SpellEnum abilityThree; // third ability
	private SpellEnum abilityFour; // fourth ability

	CharacterClass(SpellEnum abilityOne, SpellEnum abilityTwo, SpellEnum abilityThree, SpellEnum abilityFour) {
		this.abilityOne = abilityOne;
		this.abilityTwo = abilityTwo;
		this.abilityThree = abilityThree;
		this.abilityFour = abilityFour;
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
}
