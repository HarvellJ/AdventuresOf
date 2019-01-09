package com.adventuresof.game.world;

import com.adventuresof.game.animation.StaticMapItemAnimation;

public class Campfire extends AnimatedMapObject{

	public Campfire(float locationX, float locationY) {
		super(locationX, locationY, new StaticMapItemAnimation("map//animatedObjects//campfireAnimation.png", 1, 5, 0, 4));
	}

}
