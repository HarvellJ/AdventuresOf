package com.adventuresof.game.character;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;

/**
 *
 */
public abstract class NPC extends GameCharacter {

	public NPC(TiledMapTileLayer accessibleTiles, String animationSheetName, int animationSheetCols,
			int animationSheetRows, float startX, float startY, boolean isStatic) {
		super(accessibleTiles, animationSheetName, animationSheetCols, animationSheetRows, startX, startY);
		this.isStatic = isStatic;
		r = new Random();
	}

	private Random r;
		
	/**
	 * Algorithm to move the NPC randomly
	 * Works by randomly setting the NPC's target location
	 */
	public void move() {
		if(!isStatic) {
			if(super.target != null) {
				//move to target location
				super.setTargetLocation(new Vector3((float)target.getCurrentPosition().x - 40, (float)target.getCurrentPosition().y + 40, 0));
			}
			else {
				if(this.pointToMoveTo == null) {		
					int moveThisFrame = r.nextInt(1000);
					if (moveThisFrame <= 5) {
						boolean isPositiveX = r.nextBoolean();
						boolean isPositiveY = r.nextBoolean();

						int moveX = r.nextInt(300);
						int moveY = r.nextInt(300);

						if (isPositiveX) {
							if (isPositiveY) {
								this.setTargetLocation(new Vector3(currentPosition.x + moveX, currentPosition.y + moveY, 0));
							}else {
								this.setTargetLocation(new Vector3(currentPosition.x + moveX, currentPosition.y - moveY, 0));

							}
						}else {
							if (isPositiveY) {
								this.setTargetLocation(new Vector3(currentPosition.x - moveX, currentPosition.y + moveY, 0));
							}else {
								this.setTargetLocation(new Vector3(currentPosition.x - moveX, currentPosition.y - moveY, 0));
							}
						}
					}		
				}
			}
		}
	}	
}
