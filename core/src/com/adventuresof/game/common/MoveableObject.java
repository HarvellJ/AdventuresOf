package com.adventuresof.game.common;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public abstract class MoveableObject extends GameObject {
	
	// movement variables
	protected Vector3 pointToMoveTo; // used to move the character to a point
	protected Vector3 currentPosition; // stores current position of character
	protected float speed;
	
	protected float stateTime;
	
	// collision stuff
	protected TiledMapTileLayer accessibleTiles; // represents the tiles that are accessible by the character
	  
	public MoveableObject(float startX, float startY, float endX, float endY) {
		this.speed = 200.0f;
		this.stateTime = 0f;		

		// instantiate spells' current position as a blank vector3
		currentPosition = new Vector3(startX, startY, 0);
		pointToMoveTo = new Vector3(endX, endY, 0);

	}
	
	public Circle getHitBox() {
		return (Circle)hitBox;
	}

	public void setHitBox(Circle hitBox) {
		this.hitBox = hitBox;
	}

}
