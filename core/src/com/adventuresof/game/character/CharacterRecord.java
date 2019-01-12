package com.adventuresof.game.character;

public class CharacterRecord {

	public final String name;
	
	public CharacterRecord(String _name) {
		name = _name;
	}
	
    public static String CHAR_NAME_KNIGHT = "knightSprite";
    public static String CHAR_NAME_MAGE = "mageSprite";
    public static String CHAR_NAME_ARCHER = "archerSprite";

    
    public static CharacterRecord CHARACTERS[] =
    {
        new CharacterRecord(CHAR_NAME_KNIGHT),
        new CharacterRecord(CHAR_NAME_MAGE),
        new CharacterRecord(CHAR_NAME_ARCHER)
    };
}
