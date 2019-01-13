package com.adventuresof.game.combat.enums;

/**
 * Stores the types of spells in game
 * 
 */
public enum SpellType {
	projectile, // represents ranged abilities that the character can fire
	buff, // represents abilities the character can cast on themselves
	melee, // represents melee abilities, that they must be within melee range to cast
	instantCast, // represents abilities the character can instant cast on enemies
	multiProjectile; // represents abilities that fire multiple projectiles in one go
}
