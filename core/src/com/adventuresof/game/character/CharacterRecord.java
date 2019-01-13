package com.adventuresof.game.character;

import com.adventuresof.game.character.enums.CharacterClass;

/**
 * Keeps track of information regarding the players character. 
 * The input name and which character selected.
 */
public class CharacterRecord {

	public final String iconName;
	private String spriteSheet;
	private CharacterClass characterClass;
	
	public CharacterRecord(String _name, String spriteSheet, CharacterClass characterClass) {
		this.iconName = _name;
		this.spriteSheet = spriteSheet;
		this.characterClass = characterClass;
	}
	

	public static String CHAR_NAME_KNIGHT = "knightSprite";
    public static String CHAR_NAME_MAGE = "mageSprite";
    public static String CHAR_NAME_ARCHER = "archerSprite";

    
    public static CharacterRecord CHARACTERS[] =
    {
        new CharacterRecord(CHAR_NAME_KNIGHT, "characters//shadowKnight.png", CharacterClass.melee),
        new CharacterRecord(CHAR_NAME_MAGE, "characters//mageSprite.png", CharacterClass.mage),
        new CharacterRecord(CHAR_NAME_ARCHER, "characters//archerSprite.png", CharacterClass.ranger)
    };
	
    /**
	 * @return the spriteSheet
	 */
	public String getSpriteSheet() {
		return spriteSheet;
	}


	/**
	 * @param spriteSheet the spriteSheet to set
	 */
	public void setSpriteSheet(String spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	/**
	 * @return the characterClass
	 */
	public CharacterClass getCharacterClass() {
		return characterClass;
	}

	/**
	 * @param characterClass the characterClass to set
	 */
	public void setCharacterClass(CharacterClass characterClass) {
		this.characterClass = characterClass;
	}



}
