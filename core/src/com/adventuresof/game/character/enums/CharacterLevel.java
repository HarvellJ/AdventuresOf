package com.adventuresof.game.character.enums;

public enum CharacterLevel {
	low(40, 1, 2), // used for standard NPCs
	normal(100, 2, 5), // used for average power characters - such as player
	high(120, 5, 8), // used for stronger NPCs
	powerful(200, 10, 10), // demi bosses etc
	boss(250, 40, 15); // the big bosses
	
	private int baseHealth;
	private int baseDamageBonus;
	private int baseDefenceBonus;
	
	CharacterLevel(int baseHealth, int baseDamageBonus, int baseDefenceBonus) {
		this.baseHealth = baseHealth;
		this.baseDamageBonus = baseDamageBonus;
		this.baseDefenceBonus = baseDefenceBonus;
	}

	public int getBaseHealth() {
		return baseHealth;
	}

	/**
	 * @return the baseDamageBonus
	 */
	public int getBaseDamageBonus() {
		return baseDamageBonus;
	}

	/**
	 * @param baseDamageBonus the baseDamageBonus to set
	 */
	public void setBaseDamageBonus(int baseDamageBonus) {
		this.baseDamageBonus = baseDamageBonus;
	}

	/**
	 * @return the baseDefenceBonus
	 */
	public int getBaseDefenceBonus() {
		return baseDefenceBonus;
	}

	/**
	 * @param baseDefenceBonus the baseDefenceBonus to set
	 */
	public void setBaseDefenceBonus(int baseDefenceBonus) {
		this.baseDefenceBonus = baseDefenceBonus;
	}

}
