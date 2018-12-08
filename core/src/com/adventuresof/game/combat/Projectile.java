package com.adventuresof.game.combat;

import com.adventuresof.game.common.GameObject;
import com.adventuresof.game.common.MoveableObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public abstract class Projectile extends MoveableObject{
	
    protected float radius;
	
	public Projectile(float startX, float startY, float endX, float endY) {
		super(startX, startY, endX, endY);
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
	
	
	protected abstract void updateHitBox();
	
}
