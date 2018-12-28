package com.adventuresof.game.character;

import com.adventuresof.game.animation.CharacterAnimation;
import com.adventuresof.game.common.MovementSpeedEnum;
import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PlayerCompanion extends NPC {

	// texture sheets
	private static final String MOVEMENT_SPRITE_SHEET = "characters//companion.png";
	private static final String DEATH_SPRITE_SHEET = "characters//companion.png";
	private static final String COMBAT_SPRITE_SHEET = "characters//companion.png";

	// texture sheet sizes
	private static final int MOVEMENT_SPRITE_SHEET_COLS = 4;
	private static final int MOVEMENT_SPRITE_SHEET_ROWS = 4;
	private static final int DEATH_SPRITE_SHEET_COLS = 4;
	private static final int DEATH_SPRITE_SHEET_ROWS = 4;
	private static final int COMBAT_SPRITE_SHEET_COLS = 4;
	private static final int COMBAT_SPRITE_SHEET_ROWS = 4;
	// movement values
	private static final int MOVE_LEFT_START_FRAME = 5;
	private static final int MOVE_LEFT_FRAMES = 3;
	private static final int MOVE_RIGHT_START_FRAME = 9;
	private static final int MOVE_RIGHT_FRAMES = 3;
	private static final int MOVE_DOWN_START_FRAME = 0;
	private static final int MOVE_DOWN_FRAMES = 3;
	private static final int MOVE_UP_START_FRAME = 13;
	private static final int MOVE_UP_FRAMES = 3;

	// idle values
	private static final int IDLE_LEFT_START_FRAME = 5;
	private static final int IDLE_RIGHT_START_FRAME = 9;
	private static final int IDLE_DOWN_START_FRAME = 0;
	private static final int IDLE_UP_START_FRAME = 13;

	// combat values
	private static final int ATTACK_LEFT_START_FRAME = 5;
	private static final int ATTCK_LEFT_FRAMES = 3;
	private static final int ATTACK_RIGHT_START_FRAME = 9;
	private static final int ATTACK_RIGHT_FRAMES = 3;
	private static final int ATTACK_DOWN_START_FRAME = 0;
	private static final int ATTACK_DOWN_FRAMES = 3;
	private static final int ATTACK_UP_START_FRAME = 13;
	private static final int ATTACK_UP_FRAMES = 3;

	// death values
	private static final int DEATH_ANIMATION_START_FRAME = 0;
	private static final int DEATH_ANIMATION_FRAMES = 3;

	public PlayerCompanion(TiledMapTileLayer accessibleTiles, GameCharacter characterToFollow) {
		super(
				accessibleTiles,
				characterToFollow.getCurrentPosition().x + 50, characterToFollow.getCurrentPosition().y,
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
						), MovementSpeedEnum.NORMAL_MEDIUM, "Companion", false, null, CharacterClass.hybrid);
		// when companion is created, send welcome message to player
		this.addMessageToMessageQueue("Hello there!");
		this.addMessageToMessageQueue("Here is some guidance for your adventure:");
		this.addMessageToMessageQueue("To move, Right click and you will move to the selected location");
		this.addMessageToMessageQueue("To cast spells, press 1 - for tornado - or 2 - for freeze spell - and then left click where you wish to cast it");
		this.addMessageToMessageQueue("You can talk to characters by right clicking on them - try it - see what they have to say!");
		this.addMessageToMessageQueue("Some will have quests for you to do");
		this.addMessageToMessageQueue("You need to survive and make it to the next town. Good luck!");

	}
}
