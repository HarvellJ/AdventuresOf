package com.adventuresof.game.spell;

import com.adventuresof.game.animation.DirectionalProjectileAnimation;
import com.adventuresof.game.character.Direction;
import com.adventuresof.game.combat.Projectile;
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
public class Arrow extends Projectile {

	// animation stuff
	protected DirectionalProjectileAnimation arrowAnimation;
	
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

	// spell damage
	private static final int SPELL_DAMAGE = 20;
		    
	// collision stuff
	protected int characterHeight; // the character's height - used to draw the bounding rectangle (hit box)
	protected int characterWidth; // the character's width - used to draw the bounding rectangle (hit box)

	public Arrow(
			TiledMapTileLayer accessibleTiles,
			float startX, float startY, float endX, float endY) {

		super(startX, startY);

		this.pointToMoveTo = new Vector3(endX, endY, 0);

		
		this.arrowAnimation = new DirectionalProjectileAnimation(MOVEMENT_SPRITE_SHEET,
				MOVEMENT_SPRITE_SHEET_ROWS, MOVEMENT_SPRITE_SHEET_COLS,
				MOVE_LEFT_START_FRAME, MOVE_LEFT_FRAMES,
				MOVE_RIGHT_START_FRAME, MOVE_RIGHT_FRAMES,
				MOVE_DOWN_START_FRAME, MOVE_DOWN_FRAMES,
				MOVE_UP_START_FRAME, MOVE_UP_FRAMES);
		
		this.canDispose = false;

		this.damage = SPELL_DAMAGE;
		this.speed = 400.0f;
		
		this.accessibleTiles = accessibleTiles;

		// create objects required for collision logic
		this.projectileHeight = 16;
		this.projectileWidth = 16;
		
		this.accessibleTiles = accessibleTiles;
	}
	public Vector3 getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Vector3 currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Rectangle getHitBox() {
		return (Rectangle) hitBox;
	}	

	public void setHitBox(Rectangle boundingRectangle) {
		this.hitBox = boundingRectangle;
	}

	protected void createAnimations() {

	}

	/**
	 * Renders the up-to-date character to the screen
	 * Called every screen following the call to "update"
	 */
	public void render(SpriteBatch spriteBatch) {

		// get the relevant animation frame (based on current character direction)
		TextureRegion currentAnimationFrame;
		currentAnimationFrame = this.getRelevantDirectionAnimationFrame();
		spriteBatch.draw(currentAnimationFrame, currentPosition.x -15, currentPosition.y - 10); // Draw current frame ()
	}

	/**
	 * Sets the target location to a given vector3 value
	 * @param co-ordinates for the to which to move the player
	 */
	public void setTargetLocation(Vector3 pointToMoveTo) {
		this.pointToMoveTo = pointToMoveTo;
	}

	/**
	 * gets the next animation frame for the direction the character is facing
	 * @return
	 */
	private TextureRegion getRelevantDirectionAnimationFrame() {
		// if we reach here, player not performing attack, return standard movement animation frame
		if(currentProjectileDirection == Direction.up) {
			return this.arrowAnimation.getRunUpAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentProjectileDirection == Direction.down) {
			return this.arrowAnimation.getRunDownAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentProjectileDirection == Direction.right) {
			return this.arrowAnimation.getRunRightAnimation().getKeyFrame(stateTime, true);
		}
		else {
			return this.arrowAnimation.getRunLeftAnimation().getKeyFrame(stateTime, true);
		}
	}
	@Override
	public void updateHitBox() {
		this.getHitBox().set(this.currentPosition.x, this.currentPosition.y, this.characterHeight, this.characterWidth);		
	}   
}
