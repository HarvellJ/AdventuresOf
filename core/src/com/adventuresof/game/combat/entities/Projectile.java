package com.adventuresof.game.combat.entities;

import com.adventuresof.game.animation.SpellAnimation;
import com.adventuresof.game.character.entities.GameCharacter;
import com.adventuresof.game.combat.enums.Spell;
import com.adventuresof.game.common.entities.GameObject;
import com.adventuresof.game.common.entities.MoveableObject;
import com.adventuresof.game.common.enums.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Projectile extends MoveableObject{
	
	private Spell spellType;
	private SpellAnimation directionalProjectileAnimation;
    protected float projectileWidth;
    protected float projectileHeight;	
    
	protected int damage;
    
    protected Direction currentProjectileDirection;

    private GameCharacter firedBy; // stored whom the projectile was fired by to avoid them from hitting themselves with their own projectile
    private String soundEffect; // stores the path to the sound effect for this projectile
    
	public Projectile(TiledMapTileLayer accessibleTiles,
			float startX, float startY, float endX, float endY, Spell spellType, GameCharacter firedBy) {
		super(startX - 57, startY - 25); // adjustments in start positions just to account for differences in sprite sizes
		this.firedBy = firedBy;
		this.spellType = spellType;
		this.projectileHeight = spellType.getHeight();
		this.projectileWidth = spellType.getWidth();
		this.directionalProjectileAnimation = spellType.getAnimation();
		this.soundEffect = spellType.getSoundEffect();
		this.pointToMoveTo = new Vector3(endX, endY, 0);

		this.canDispose = false;

		this.damage = spellType.getDamage();
		this.speed = spellType.getMovementSpeed();
		
		this.accessibleTiles = accessibleTiles;
		
		this.hitBox = new Rectangle(); 	
		((Rectangle) this.hitBox).set(startX, startY, this.projectileWidth, this.projectileHeight);
	}
	
	public String getSoundEffect() {
		return soundEffect;
	}
	
	public GameCharacter getFiredBy() {
		return firedBy;
	}

	public void setFiredBy(GameCharacter firedBy) {
		this.firedBy = firedBy;
	}

	@Override
	public void update() {
	this.stateTime += Gdx.graphics.getDeltaTime(); // increment state time
	this.moveObject();
	this.updateHitBox();
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Rectangle getHitBox() {
		return (Rectangle)hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}
	
	public void moveObject() {
		// check the point to move to value is set, if not, there is no need to move this frame.
		if(pointToMoveTo != null) {
			this.calculateProjectileDirection();   
			
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
	}
	
	
	/**
	 * Set the character direction
	 * @param direction
	 */
	public void setCharacterDirection(Direction direction) {
		currentProjectileDirection = direction;
	}
	
	/**
	 * Sets the target location to a given vector3 value
	 * @param co-ordinates for the to which to move the player
	 */
	public void setTargetLocation(Vector3 pointToMoveTo) {
		this.pointToMoveTo = pointToMoveTo;
	}

	/**
	 * Calculates the character's current direction based on movement
	 */
	private void calculateProjectileDirection() {
		double xDistanceToTravel;
		double yDistanceToTravel;
		Direction directionX;
		Direction directionY;

		//calculate x distance
		if(currentPosition.x > pointToMoveTo.x) {
			xDistanceToTravel = currentPosition.x - pointToMoveTo.x;
			directionX = Direction.left;
		}else {
			xDistanceToTravel = pointToMoveTo.x - currentPosition.x;
			directionX = Direction.right;
		}
		//calculate y distance
		if(currentPosition.y > pointToMoveTo.y) {
			yDistanceToTravel = currentPosition.y - pointToMoveTo.y;
			directionY = Direction.down;
		}else {
			yDistanceToTravel = pointToMoveTo.y - currentPosition.y  ;
			directionY = Direction.up;
		}

		if(xDistanceToTravel > yDistanceToTravel) {
			this.setCharacterDirection(directionX);

		}else {
			this.setCharacterDirection(directionY);
		}   

	}
		
	// abstracted as projectile's hitbox's may take different types, e.g. rectangle/circle
	public void updateHitBox() {
		this.getHitBox().set(this.currentPosition.x, this.currentPosition.y, this.projectileHeight, this.projectileWidth);		
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		// get the relevant animation frame (based on current character direction)
		TextureRegion currentAnimationFrame;
		currentAnimationFrame = this.getRelevantDirectionAnimationFrame();
		spriteBatch.draw(currentAnimationFrame, currentPosition.x -15, currentPosition.y - 10); // Draw current frame ()
	}   
	
	/**
	 * gets the next animation frame for the direction the character is facing
	 * @return
	 */
	private TextureRegion getRelevantDirectionAnimationFrame() {
		// if we reach here, player not performing attack, return standard movement animation frame
		if(currentProjectileDirection == Direction.up) {
			return this.directionalProjectileAnimation.getUpAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentProjectileDirection == Direction.down) {
			return this.directionalProjectileAnimation.getDownAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentProjectileDirection == Direction.right) {
			return this.directionalProjectileAnimation.getRightAnimation().getKeyFrame(stateTime, true);
		}
		else {
			return this.directionalProjectileAnimation.getLeftAnimation().getKeyFrame(stateTime, true);
		}
	}
}
