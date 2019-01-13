package com.adventuresof.game.common.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public abstract class MoveableObject extends GameObject {
	
	// movement variables
	protected Vector3 pointToMoveTo; // used to move the character to a point
	
	protected float speed;
	protected Vector3 spawnLocation; // used for things such as calculating distance travelled and respawn points 
	
	// collision stuff
	protected TiledMapTileLayer accessibleTiles; // represents the tiles that are accessible by the character
	  
	public MoveableObject(float startX, float startY) {
		this.speed = 200.0f; // default speed to 200
		this.stateTime = 0f;		

		// instantiate object's current position
		currentPosition = new Vector3(startX, startY, 0);
		this.spawnLocation = new Vector3(startX, startY, 0);

	}

	public abstract void updateHitBox();
	
	public abstract void moveObject();
}
