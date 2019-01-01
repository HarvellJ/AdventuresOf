package com.adventuresof.game.combat;

import com.adventuresof.game.animation.DirectionalProjectileAnimation;
import com.adventuresof.game.character.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Vector3;

/**
 *
 */
public class ArrowAnimation extends DirectionalProjectileAnimation{

	// texture sheets
		private static final String MOVEMENT_SPRITE_SHEET = "spells//projectiles.png";

		// texture sheet sizes
		private static final int MOVEMENT_SPRITE_SHEET_COLS = 8;
		private static final int MOVEMENT_SPRITE_SHEET_ROWS = 3;

		private static final int MOVE_LEFT_START_FRAME = 0;
		private static final int MOVE_LEFT_FRAMES = 1;
		private static final int MOVE_RIGHT_START_FRAME = 4;
		private static final int MOVE_RIGHT_FRAMES = 1;
		private static final int MOVE_DOWN_START_FRAME = 6;
		private static final int MOVE_DOWN_FRAMES = 1;
		private static final int MOVE_UP_START_FRAME = 2;
		private static final int MOVE_UP_FRAMES = 1;
		
		public ArrowAnimation() {
			super(MOVEMENT_SPRITE_SHEET,
					MOVEMENT_SPRITE_SHEET_ROWS, MOVEMENT_SPRITE_SHEET_COLS,
					MOVE_LEFT_START_FRAME, MOVE_LEFT_FRAMES,
					MOVE_RIGHT_START_FRAME, MOVE_RIGHT_FRAMES,
					MOVE_DOWN_START_FRAME, MOVE_DOWN_FRAMES,
					MOVE_UP_START_FRAME, MOVE_UP_FRAMES);
		}
}