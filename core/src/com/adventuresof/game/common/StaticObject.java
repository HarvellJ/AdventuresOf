package com.adventuresof.game.common;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class StaticObject extends GameObject{
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(SpriteBatch spriteBatch);
}
