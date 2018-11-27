package com.adventuresof.screens;

import java.util.HashMap;

import com.adventuresof.game.character.CharacterRecord;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Resources {

	public BitmapFont gamefont;
	public Sprite player;
	public Sprite player2;
	public HashMap<String, Sprite> playerSprites;

	public Resources() {
		gamefont = new BitmapFont(Gdx.files.internal("gamefont.fnt"), Gdx.files.internal("gamefont.png"), false);
		player = new Sprite(new Texture("characterSelection/" + CharacterRecord.CHAR_NAME_ELF + ".png"));
		player2 = new Sprite(new Texture("characterSelection/" + CharacterRecord.CHAR_NAME_MAGE + ".png"));

		playerSprites = new HashMap<String, Sprite>();
		playerSprites.put(CharacterRecord.CHAR_NAME_ELF, player);
		playerSprites.put(CharacterRecord.CHAR_NAME_MAGE, player2);

	}

	public void dispose() {
	}
}
