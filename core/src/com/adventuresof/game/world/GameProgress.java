package com.adventuresof.game.world;

/**
 * Information regarding the progress of the game
 * Contains the chosen character name
 */
public class GameProgress {
	
    public static int currentCharacter = 0;
    public static String characterName = "";
    public static int stages[]; // how far the character has progressed


    public static void Reset(boolean resetProgress) {
        if (resetProgress)
        {
            stages[currentCharacter] -= 5;
            if (stages[currentCharacter] < 0)
            {
                stages[currentCharacter] = 0;
            }
        }

    }

}
