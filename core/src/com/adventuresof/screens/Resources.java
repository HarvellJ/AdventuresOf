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
	public Sprite player3;
	public HashMap<String, Sprite> playerSprites;

	public Resources() {
		gamefont = new BitmapFont(Gdx.files.internal("gamefont.fnt"), Gdx.files.internal("gamefont.png"), false);
		player = new Sprite(new Texture("characterSelection/" + CharacterRecord.CHAR_NAME_KNIGHT + ".png"));
		player2 = new Sprite(new Texture("characterSelection/" + CharacterRecord.CHAR_NAME_MAGE + ".png"));
		player3 = new Sprite(new Texture("characterSelection/" + CharacterRecord.CHAR_NAME_ARCHER + ".png"));


		playerSprites = new HashMap<String, Sprite>();
		playerSprites.put(CharacterRecord.CHAR_NAME_KNIGHT, player);
		playerSprites.put(CharacterRecord.CHAR_NAME_MAGE, player2);
		playerSprites.put(CharacterRecord.CHAR_NAME_ARCHER, player3);


	}

	public void dispose() {
	}
}
