package com.adventuresof.game.world;

public class GameProgress {
	
    public static int currentCharacter = 0;
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
