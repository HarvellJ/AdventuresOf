package com.adventuresof.game.world;

import com.adventuresof.game.animation.StaticMapItemAnimation;
import com.adventuresof.game.common.entities.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Animated Map Object Class
 * 
 */
public class AnimatedMapObject extends GameObject{

	protected StaticMapItemAnimation animation;
	
	public AnimatedMapObject(
			float locationX, float locationY,
			StaticMapItemAnimation animation) {
		
		this.animation = animation;
		this.stateTime = 0f;	

		// instantiate objects' current position as a blank vector3
		currentPosition = new Vector3(locationX, locationY, 0);
	}
	@Override
	public void update() {
		stateTime += Gdx.graphics.getDeltaTime(); // increment state time
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		TextureRegion currentAnimationFrame;
		currentAnimationFrame = this.animation.getAnimation().getKeyFrame(stateTime, true);
		spriteBatch.draw(currentAnimationFrame, currentPosition.x, currentPosition.y); // Draw current frame ()
	}

}
