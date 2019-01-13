package com.adventuresof.game.world;

import com.adventuresof.game.animation.StaticMapItemAnimation;

/**
 * Animated Campfire Class
 * 
 */
public class Campfire extends AnimatedMapObject{

	public Campfire(float locationX, float locationY) {
		super(locationX, locationY, new StaticMapItemAnimation("map//animatedObjects//campfireAnimation.png", 1, 5, 0, 4));
	}

}
