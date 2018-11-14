package com.adventuresof.game.character;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PlayerCompanion extends NPC {

	// texture sheets
	private static final String MOVEMENT_SPRITE_SHEET = "characters//knight.png";
	private static final String DEATH_SPRITE_SHEET = "characters//knight.png";
	private static final String COMBAT_SPRITE_SHEET = "characters//knight.png";

	// texture sheet sizes
	private static final int MOVEMENT_SPRITE_SHEET_COLS = 8;
	private static final int MOVEMENT_SPRITE_SHEET_ROWS = 5;
	private static final int DEATH_SPRITE_SHEET_COLS = 8;
	private static final int DEATH_SPRITE_SHEET_ROWS = 5;
	private static final int COMBAT_SPRITE_SHEET_COLS = 8;
	private static final int COMBAT_SPRITE_SHEET_ROWS = 5;
	// movement values
	private static final int MOVE_LEFT_START_FRAME = 21;
	private static final int MOVE_LEFT_FRAMES = 5;
	private static final int MOVE_RIGHT_START_FRAME = 15;
	private static final int MOVE_RIGHT_FRAMES = 5;
	private static final int MOVE_DOWN_START_FRAME = 5;
	private static final int MOVE_DOWN_FRAMES = 4;
	private static final int MOVE_UP_START_FRAME = 10;
	private static final int MOVE_UP_FRAMES = 4;

	// idle values
	private static final int IDLE_LEFT_START_FRAME = 23;
	private static final int IDLE_RIGHT_START_FRAME = 14;
	private static final int IDLE_DOWN_START_FRAME = 1;
	private static final int IDLE_UP_START_FRAME = 29;

	// combat values
	private static final int ATTACK_LEFT_START_FRAME = 36;
	private static final int ATTCK_LEFT_FRAMES = 3;
	private static final int ATTACK_RIGHT_START_FRAME = 33;
	private static final int ATTACK_RIGHT_FRAMES = 3;
	private static final int ATTACK_DOWN_START_FRAME = 27;
	private static final int ATTACK_DOWN_FRAMES = 3;
	private static final int ATTACK_UP_START_FRAME = 30;
	private static final int ATTACK_UP_FRAMES = 3;

	// death values
	private static final int DEATH_ANIMATION_START_FRAME = 27;
	private static final int DEATH_ANIMATION_FRAMES = 3;

	public PlayerCompanion(TiledMapTileLayer accessibleTiles, GameCharacter characterToFollow) {
		super(
				accessibleTiles,
				1700, 1300,
				false,
				false, 
				50, 50,
				new CharacterAnimation(MOVEMENT_SPRITE_SHEET, COMBAT_SPRITE_SHEET, DEATH_SPRITE_SHEET,
						MOVEMENT_SPRITE_SHEET_ROWS, MOVEMENT_SPRITE_SHEET_COLS,
						COMBAT_SPRITE_SHEET_ROWS, COMBAT_SPRITE_SHEET_COLS,
						DEATH_SPRITE_SHEET_ROWS, DEATH_SPRITE_SHEET_COLS,
						MOVE_LEFT_START_FRAME, MOVE_LEFT_FRAMES,
						MOVE_RIGHT_START_FRAME, MOVE_RIGHT_FRAMES,
						MOVE_DOWN_START_FRAME, MOVE_DOWN_FRAMES,
						MOVE_UP_START_FRAME, MOVE_UP_FRAMES,
						IDLE_LEFT_START_FRAME, IDLE_RIGHT_START_FRAME, IDLE_DOWN_START_FRAME, IDLE_UP_START_FRAME,
						ATTACK_LEFT_START_FRAME, ATTCK_LEFT_FRAMES, 
						ATTACK_RIGHT_START_FRAME, ATTACK_RIGHT_FRAMES,
						ATTACK_DOWN_START_FRAME, ATTACK_DOWN_FRAMES,
						ATTACK_UP_START_FRAME, ATTACK_UP_FRAMES,
						DEATH_ANIMATION_START_FRAME, DEATH_ANIMATION_FRAMES
						), CharacterSpeed.NORMAL_MEDIUM, "Companion", false, null);
		this.setTarget(characterToFollow);
		// when companion is created, send welcome message to player
		this.addMessageToMessageQueue("Hello there! Welcome to the tutorial");
		this.addMessageToMessageQueue("I am here to guide you through your adventures.");
		this.addMessageToMessageQueue("Lets start by moving around. Right click anywhere on the island to move to that location");
		this.addMessageToMessageQueue("And dont worry, you can't fall off into the ocean (I know you was wondering).");
	}
}
