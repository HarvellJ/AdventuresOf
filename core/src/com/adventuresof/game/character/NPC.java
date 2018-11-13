package com.adventuresof.game.character;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;

/**
 *
 */
public abstract class NPC extends GameCharacter {

	private Random r;

	public NPC(
			TiledMapTileLayer accessibleTiles, 
			float startX, float startY,
			boolean isStatic,
			boolean isHostile,
			int characterWidth, int characterHeight,
			CharacterAnimation characterAnimation
			)
	{
		super(accessibleTiles, startX, startY, isHostile, characterWidth, characterHeight, characterAnimation);
		this.isStatic = isStatic;
		r = new Random();
	}

	/**
	 * Algorithm to move the NPC randomly
	 * Works by randomly setting the NPC's target location
	 */
	public void move() {
		if(!isStatic) {
			if(super.target == null) {				
				if(this.pointToMoveTo == null) {		
					int moveThisFrame = r.nextInt(1000);
					if (moveThisFrame <= 5) {
						boolean isPositiveX = r.nextBoolean();
						boolean isPositiveY = r.nextBoolean();

						int moveX = r.nextInt(40);
						int moveY = r.nextInt(40);


						if (isPositiveX) {
							if (isPositiveY) {
								// only move if they haven't gone beyond allowed boundary (from spawn)
								if((currentPosition.x + moveX) < (spawnLocation.x + 50) || (currentPosition.y + moveY) < (spawnLocation.y + 50)) {
									this.setTargetLocation(new Vector3(currentPosition.x + moveX, currentPosition.y + moveY, 0));
								}
							}else {
								if((currentPosition.x + moveX) < (spawnLocation.x + 50) || (currentPosition.y - moveY) > (spawnLocation.y - 50)) {
									this.setTargetLocation(new Vector3(currentPosition.x + moveX, currentPosition.y - moveY, 0));
								}
							}
						}else {
							if (isPositiveY) {
								if((currentPosition.x - moveX) > (spawnLocation.x - 50) || (currentPosition.y + moveY) < (spawnLocation.y + 50)) {
									this.setTargetLocation(new Vector3(currentPosition.x - moveX, currentPosition.y + moveY, 0));
								}
							}else {
								if((currentPosition.x - moveX) > (spawnLocation.x - 50) || (currentPosition.y - moveY) < (spawnLocation.y + 50)) {
									this.setTargetLocation(new Vector3(currentPosition.x - moveX, currentPosition.y - moveY, 0));
								}
							}
						}
					}												

				}
			}
		}
	}	
}
