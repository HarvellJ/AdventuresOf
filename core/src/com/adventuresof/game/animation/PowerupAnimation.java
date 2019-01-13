package com.adventuresof.game.animation;

public class PowerupAnimation extends SpellAnimation{

	// texture sheets
	private static final String MOVEMENT_UP_SPRITE_SHEET = "spells//powerup.png";
	private static final String MOVEMENT_DOWN_SPRITE_SHEET = "spells//powerup.png";
	private static final String MOVEMENT_LEFT_SPRITE_SHEET = "spells//powerup.png";
	private static final String MOVEMENT_RIGHT_SPRITE_SHEET = "spells//powerup.png";

	// texture sheet sizes
	private static final int MOVEMENT_SPRITE_SHEET_COLS = 4;
	private static final int MOVEMENT_SPRITE_SHEET_ROWS = 4;

	private static final int MOVE_LEFT_START_FRAME = 0;
	private static final int MOVE_LEFT_FRAMES = 16;
	private static final int MOVE_RIGHT_START_FRAME = 0;
	private static final int MOVE_RIGHT_FRAMES = 16;
	private static final int MOVE_DOWN_START_FRAME = 0;
	private static final int MOVE_DOWN_FRAMES = 16;
	private static final int MOVE_UP_START_FRAME = 0;
	private static final int MOVE_UP_FRAMES = 16;


	public PowerupAnimation() {
		super(MOVEMENT_UP_SPRITE_SHEET, MOVEMENT_DOWN_SPRITE_SHEET, MOVEMENT_LEFT_SPRITE_SHEET, MOVEMENT_RIGHT_SPRITE_SHEET,
				MOVEMENT_SPRITE_SHEET_ROWS, MOVEMENT_SPRITE_SHEET_COLS,
				MOVE_LEFT_START_FRAME, MOVE_LEFT_FRAMES,
				MOVE_RIGHT_START_FRAME, MOVE_RIGHT_FRAMES,
				MOVE_DOWN_START_FRAME, MOVE_DOWN_FRAMES,
				MOVE_UP_START_FRAME, MOVE_UP_FRAMES);
	}
}

