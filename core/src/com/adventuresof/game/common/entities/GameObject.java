package com.adventuresof.game.common.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector3;

/**
 * Represents a game object that exists in a game world
 */
public abstract class GameObject {

	protected boolean canDispose;
	protected Shape2D hitBox; // a mesh used to detect collisions with the character
	protected Vector3 currentPosition; // stores the position of the object
	protected float stateTime;

	/**
	 * Updates the objects values for the current frame
	 */
	public abstract void update();
	
	public boolean CanDispose() {
		return canDispose;
	}

	public void setCanDispose(boolean canDispose) {
		this.canDispose = canDispose;
	}

	/**
	 * Renders the objects values to the current frame
	 */
	public abstract void render(SpriteBatch spriteBatch);
	
}
