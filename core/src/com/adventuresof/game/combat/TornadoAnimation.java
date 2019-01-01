package com.adventuresof.game.combat;

import com.adventuresof.game.animation.DirectionalProjectileAnimation;
/**
*
*/
public class TornadoAnimation extends DirectionalProjectileAnimation{

	// texture sheets
	// texture sheets
	private static final String MOVEMENT_SPRITE_SHEET = "spells//whirlwind.png";

	// texture sheet sizes
	private static final int MOVEMENT_SPRITE_SHEET_COLS = 4;
	private static final int MOVEMENT_SPRITE_SHEET_ROWS = 1;

	private static final int MOVE_LEFT_START_FRAME = 0;
	private static final int MOVE_LEFT_FRAMES = 3;
	private static final int MOVE_RIGHT_START_FRAME = 0;
	private static final int MOVE_RIGHT_FRAMES = 3;
	private static final int MOVE_DOWN_START_FRAME = 0;
	private static final int MOVE_DOWN_FRAMES = 3;
	private static final int MOVE_UP_START_FRAME = 0;
	private static final int MOVE_UP_FRAMES = 3;
	
		public TornadoAnimation() {
			super(MOVEMENT_SPRITE_SHEET,
					MOVEMENT_SPRITE_SHEET_ROWS, MOVEMENT_SPRITE_SHEET_COLS,
					MOVE_LEFT_START_FRAME, MOVE_LEFT_FRAMES,
					MOVE_RIGHT_START_FRAME, MOVE_RIGHT_FRAMES,
					MOVE_DOWN_START_FRAME, MOVE_DOWN_FRAMES,
					MOVE_UP_START_FRAME, MOVE_UP_FRAMES);
		}
}