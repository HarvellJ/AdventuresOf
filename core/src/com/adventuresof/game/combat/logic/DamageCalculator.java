package com.adventuresof.game.combat.logic;

import java.util.Random;

import com.adventuresof.game.character.entities.GameCharacter;

public class DamageCalculator {

	public static Random random = new Random();

	public static int calculateDamage(int maxDamage, int bonusDamagePoints, int temporaryBonusDamagePoints, int bonusDefencePoints, int temporaryBonusDefencePoints) {
		// total up the total damage points (for attacker) and add to max damage
		int totalDamage = maxDamage + bonusDamagePoints + temporaryBonusDamagePoints;
		// total up total defence and health points of defender
		int totalDefencePoints = bonusDefencePoints + temporaryBonusDefencePoints;
		// calculate damage amount - generate a number between 1-100 representing a % of the max damage to deal
		double calculatedRandomDamage = totalDamage * ((double)DamageCalculator.random.nextInt(100)/100);;

		if(totalDefencePoints > calculatedRandomDamage) {
			totalDefencePoints = (int)calculatedRandomDamage; // there is a chance of blocking entire amount				
		}

		double retVal = calculatedRandomDamage;

		if(totalDefencePoints > 0) {
			// block a random % of defence points if they are > 0
			retVal = calculatedRandomDamage - (totalDefencePoints * ((double)DamageCalculator.random.nextInt(100)/100));
		}

		return (int)retVal;

	}

}
