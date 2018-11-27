package com.adventuresof.game.character;

public class CharacterRecord {

	public final String name;
	
	public CharacterRecord(String _name) {
		name = _name;
	}
	
    public static String CHAR_NAME_ELF = "bloodelf";
    public static String CHAR_NAME_MAGE = "blueWizard";
    
    public static CharacterRecord CHARACTERS[] =
    {
        new CharacterRecord(CHAR_NAME_ELF),
        new CharacterRecord(CHAR_NAME_MAGE)
    };
}
