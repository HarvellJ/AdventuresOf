package com.adventuresof.game.spell;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

	public Tornado(
			TiledMapTileLayer accessibleTiles,
			float startX, float startY, float endX, float endY) {

		this.spellAnimation = 
				new SpellAnimation(
						MOVEMENT_SPRITE_SHEET,
						MOVEMENT_SPRITE_SHEET_ROWS,
						MOVEMENT_SPRITE_SHEET_COLS,
						MOVE_START_FRAME,
						MOVE_FRAMES
						);
		this.speed = 200.0f;
		this.stateTime = 0f;		

		// instantiate characters' current position as a blank vector3
		currentPosition = new Vector3(startX, startY, 0);
		pointToMoveTo = new Vector3(endX, endY, 0);

		// they are alive...
		this.canDispose = false;

		this.damage = 10;
		
		// create objects required for collision logic
		this.radius = 10f;
		this.hitBox = new Circle(); 	
		this.hitBox.set(startX, startY, this.radius);

		this.accessibleTiles = accessibleTiles;

	}

	@Override
	public void update() {
		this.stateTime += Gdx.graphics.getDeltaTime(); // increment state time
		// check the point to move to value is set, if not, there is no need to move this frame.
		if(pointToMoveTo != null) {

			// Algorithm for performing (gradual) character movement
			double destinationX = pointToMoveTo.x - currentPosition.x;
			double destinationY = pointToMoveTo.y - currentPosition.y;

			double distanceToTravel = Math.sqrt(destinationX * destinationX + destinationY * destinationY);
			destinationX = destinationX / distanceToTravel;
			destinationY = destinationY / distanceToTravel;

			double nextX = destinationX * speed * Gdx.graphics.getDeltaTime();
			double nextY = destinationY * speed * Gdx.graphics.getDeltaTime();

			double distanceTravelled = Math.sqrt(nextX * nextX + nextY * nextY);

			// check if the character has arrived at desired location
			if ( distanceTravelled > distanceToTravel )
			{
				// stop them moving and set them to idle
				pointToMoveTo = null;
				this.setCanDispose(true);
			}
			else
			{
				// check if the next position is an accessible cell, if so, move there. If not, stop moving, character at edge of accessible layer.
				// the / 32 is dividing the current position co-ordinates by the tile sizes
				if(accessibleTiles.getCell(((int) currentPosition.x + (int) nextX) / 16, ((int) currentPosition.y + (int) nextY) / 16) == null) {
					pointToMoveTo = null;
					this.setCanDispose(true);
				}else {
					currentPosition.x = currentPosition.x + (float) nextX;
					currentPosition.y = currentPosition.y + (float) nextY;   
				}    			
			} 			

		}
		this.updateHitBox();

	}
	
	@Override
	public void render(SpriteBatch spriteBatch) {
		// get the relevant animation frame (based on current character direction)
				TextureRegion currentAnimationFrame;
				currentAnimationFrame = this.getSpellAnimationFrame();			

				// Draw the character frame at the current position
				spriteBatch.draw(currentAnimationFrame, currentPosition.x -15, currentPosition.y - 10); // Draw current frame ()

	}
	
	private void updateHitBox() {
		this.hitBox.set(this.currentPosition.x, this.currentPosition.y, this.radius);
	}
	
	private TextureRegion getSpellAnimationFrame() {
		return this.spellAnimation.getMoveAnimation().getKeyFrame(stateTime, true);
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
