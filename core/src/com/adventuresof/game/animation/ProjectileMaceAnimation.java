
package com.adventuresof.game.animation;

/**
 * Extends the spell animation class to create an ArrowAnimation. 
 * Contains the data specific to the ArrowAnimation 
 */
public class ProjectileMaceAnimation extends SpellAnimation{

	// texture sheets
	private static final String MOVEMENT_UP_SPRITE_SHEET = "spells//projectiles.png";
	private static final String MOVEMENT_DOWN_SPRITE_SHEET = "spells//projectiles.png";
	private static final String MOVEMENT_LEFT_SPRITE_SHEET = "spells//projectiles.png";
	private static final String MOVEMENT_RIGHT_SPRITE_SHEET ="spells//projectiles.png";
	// texture sheet sizes
	private static final int MOVEMENT_SPRITE_SHEET_COLS = 8;
	private static final int MOVEMENT_SPRITE_SHEET_ROWS = 3;

	private static final int MOVE_LEFT_START_FRAME = 17;
	private static final int MOVE_LEFT_FRAMES = 3;
	private static final int MOVE_RIGHT_START_FRAME = 17;
	private static final int MOVE_RIGHT_FRAMES = 3;
	private static final int MOVE_DOWN_START_FRAME = 17;
	private static final int MOVE_DOWN_FRAMES = 3;
	private static final int MOVE_UP_START_FRAME = 17;
	private static final int MOVE_UP_FRAMES = 3;

	public ProjectileMaceAnimation() {
		super(MOVEMENT_UP_SPRITE_SHEET, MOVEMENT_DOWN_SPRITE_SHEET, MOVEMENT_LEFT_SPRITE_SHEET, MOVEMENT_RIGHT_SPRITE_SHEET,
				MOVEMENT_SPRITE_SHEET_ROWS, MOVEMENT_SPRITE_SHEET_COLS,
				MOVE_LEFT_START_FRAME, MOVE_LEFT_FRAMES,
				MOVE_RIGHT_START_FRAME, MOVE_RIGHT_FRAMES,
				MOVE_DOWN_START_FRAME, MOVE_DOWN_FRAMES,
				MOVE_UP_START_FRAME, MOVE_UP_FRAMES);
	}
}


