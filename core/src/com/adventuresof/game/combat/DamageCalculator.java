package com.adventuresof.game.combat;

import java.util.Random;

import com.adventuresof.game.character.entities.GameCharacter;

public class DamageCalculator {

	public static Random random = new Random();
	
	public static int calculateDamage(int maxDamage, GameCharacter inflictedBy, GameCharacter inflictedOn) {
		// total up the total damage points (for attacker) and add to max damage
		int totalDamage = maxDamage + inflictedBy.getDamageBonusPoints() + inflictedBy.getTemporaryDamageBonusPoints();
		// total up total defence and health points of defender
		int totalDefencePoints = inflictedOn.getDefenceBonusPoints();
		// calculate damage amount - generate a number between 1-100 representing a % of the max damage to deal
		double calculatedRandomDamage = totalDamage * ((double)random.nextInt(100)/100);
		
		//33% chance of blocking 
		if(random.nextInt(3)>1) {
			if(totalDefencePoints > calculatedRandomDamage) {
				return 0;				
			}else {
				return (int) calculatedRandomDamage - totalDefencePoints;
			}
		}
		else {
			return (int) calculatedRandomDamage;
		}
	}
	
}
