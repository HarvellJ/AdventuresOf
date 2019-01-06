package com.adventuresof.game.character;

public enum CharacterLevel {
	low(40), // used for standard NPCs
	normal(100), // used for average power characters - such as player
	high(120), // used for stronger NPCs
	powerful(200), // demi bosses etc
	boss(500); // the big bosses
	
	private int baseHealth;
	
	CharacterLevel(int baseHealth) {
		this.baseHealth = baseHealth;
	}

	public int getBaseHealth() {
		return baseHealth;
	}

}
