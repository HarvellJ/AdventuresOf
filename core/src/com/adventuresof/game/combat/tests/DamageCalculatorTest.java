package com.adventuresof.game.combat.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.adventuresof.game.character.entities.GameCharacter;
import com.adventuresof.game.combat.logic.DamageCalculator;

public class DamageCalculatorTest {

	// test damage - max damage vs defence:
	// 20 max damage
	// 0 bonus damage
	// 0 temporary bonus damage 
	// 0 defence
	// 0 temporary defence damage 
	// result should be equal to max damage * a random % value) 
	@Test
	public void calculateDamage_damageWithNoDefenceValue_calculated() {
		// Seed the random number so we can predict expected results
		DamageCalculator.random = new Random(1);
		
		int maxDamage = 20;
		int bonusDamage = 0;
		int tempBonusDamage = 0;
		int defence = 0;
		int tempDefence = 0;
		
		int result = DamageCalculator.calculateDamage(maxDamage, bonusDamage, tempBonusDamage, defence, tempDefence);
		
		double calculatedDamage = maxDamage * ((double)DamageCalculator.random.nextInt(100)/100);
		
		assertEquals((int)calculatedDamage, result);
	}

	// test damage - max and bonus damage vs 0 defence:
	// 20 max damage
	// 10 bonus damage
	// 0 temporary bonus damage 
	// 0 defence
	// 0 temporary defence
	// result should be equal to (max damage + bonus damage) * a random % value) 

	
	// test damage - max, bonus and temp damage vs 0 defence:
	// 20 max damage
	// 10 bonus damage
	// 10 temporary bonus damage 
	// 0 defence
	// 0 temporary defence
	// result should be equal to (((max damage + bonus damage) + temp bonus damage) * a random % value) 




	// test damage - max damage vs defence:
	// 20 max damage
	// 0 bonus damage
	// 0 temporary bonus damage 
	// 10 defence
	// 0 temporary defence
	// result should be equal to (((max damage + bonus damage) + temp bonus damage) * a random % value) - defence points * random % value 

	// test damage - max & bonus damage vs defence:
	// 20 max damage
	// 10 bonus damage
	// 0 temporary bonus damage 
	// 10 defence
	// 0 temporary defence
	// result should be equal to ((max damage + bonus damage) * a random % value) - defence points * random % value 

	// test damage -  max & bonus damage & temp bonus damage vs defence:
	// 20 max damage
	// 10 bonus damage
	// 10 temporary bonus damage 
	// 10 defence
	// 0 temporary defence
	// result should be equal to (((max damage + bonus damage) + temp bonus damage) * a random % value) - defence points * random % value 




	// test damage - max damage vs defence & bonus defence:
	// 20 max damage
	// 0 bonus damage
	// 0 temporary bonus damage 
	// 10 defence
	// 10 temporary defence
	// result should be equal to (((max damage + bonus damage) + temp bonus damage) * a random % value) - (defence points + bonus defence points) * random % value 

	// test damage - max & bonus damage vs defence & bonus defence::
	// 20 max damage
	// 10 bonus damage
	// 0 temporary bonus damage 
	// 10 defence
	// 10 temporary defence
	// result should be equal to ((max damage + bonus damage) * a random % value) - (defence points + bonus defence points) * random % value 

	// test damage -  max & bonus damage & temp bonus damage vs defence & bonus defence::
	// 20 max damage
	// 10 bonus damage
	// 10 temporary bonus damage 
	// 10 defence
	// 10 temporary defence
	// result should be equal to (((max damage + bonus damage) + temp bonus damage) * a random % value) - (defence points + bonus defence points) * random % value 



}
