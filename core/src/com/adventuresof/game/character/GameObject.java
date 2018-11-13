package com.adventuresof.game.character;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Represents a game object that exists in a game world
 */
public abstract class GameObject {

	protected boolean canDispose;
	
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
