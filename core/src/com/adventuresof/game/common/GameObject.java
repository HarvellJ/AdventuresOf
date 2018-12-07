package com.adventuresof.game.common;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;

/**
 * Represents a game object that exists in a game world
 */
public abstract class GameObject {

	protected boolean canDispose;
	protected Circle hitBox; // a mesh used to detect collisions with the character

	
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
