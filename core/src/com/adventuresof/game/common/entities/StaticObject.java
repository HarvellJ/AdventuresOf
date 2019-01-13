package com.adventuresof.game.common.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Static Object Class
 * 
 */
public abstract class StaticObject extends GameObject{
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(SpriteBatch spriteBatch);
}
