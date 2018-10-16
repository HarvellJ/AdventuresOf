package com.adventuresof.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents a game object that exists in a game world
 */
public abstract class GameObject {

	/**
	 * Updates the objects values for the current frame
	 */
	public abstract void update();
	
	/**
	 * Renders the objects values to the current frame
	 */
	public abstract void render(SpriteBatch spriteBatch);
	
}
