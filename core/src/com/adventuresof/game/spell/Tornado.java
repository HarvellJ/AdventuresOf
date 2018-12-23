package com.adventuresof.game.spell;

import com.adventuresof.game.animation.SpellAnimation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class Tornado extends Spell{
	
	// texture sheets
	private static final String MOVEMENT_SPRITE_SHEET = "characters//whirlwind.png";

	// texture sheet sizes
	private static final int MOVEMENT_SPRITE_SHEET_COLS = 4;
	private static final int MOVEMENT_SPRITE_SHEET_ROWS = 1;

	// movement values
	private static final int MOVE_START_FRAME = 0;
	private static final int MOVE_FRAMES = 3;

	// spell damage
	private static final int SPELL_DAMAGE = 10;
	
	// spell radius
	private static final float SPELL_RADIUS = 10f;
	
	public Tornado(
			TiledMapTileLayer accessibleTiles,
			float startX, float startY, float endX, float endY) {
		
		super(startX, startY);
		
		this.pointToMoveTo = new Vector3(endX, endY, 0);
		
		this.spellAnimation = 
				new SpellAnimation(
						MOVEMENT_SPRITE_SHEET,
						MOVEMENT_SPRITE_SHEET_ROWS,
						MOVEMENT_SPRITE_SHEET_COLS,
						MOVE_START_FRAME,
						MOVE_FRAMES
						);
		
		this.canDispose = false;

		this.damage = SPELL_DAMAGE;
		
		// create objects required for collision logic
		this.radius = SPELL_RADIUS;
		this.hitBox = new Circle(); 	
		((Circle) this.hitBox).set(startX, startY, this.radius);

		this.accessibleTiles = accessibleTiles;

	}
}
