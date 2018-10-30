package com.adventuresof.game.character;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Guard extends NPC{

	public Guard(TiledMapTileLayer accessibleTiles, String textureSheet, float startX, float startY, Direction startingDirection) {
		super(accessibleTiles, textureSheet, 8, 5, startX, startY, true);
		this.setCharacterDirection(startingDirection);
	}

	protected void createAnimations() {
		this.runRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.runLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.runDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.runUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);

		this.attackDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.attackUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.attackRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.attackLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		
		this.idleUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 29, 0.08f);
		this.idleDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.idleLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 23, 0.08f);
		this.idleRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 14, 0.08f);
	}    
	
}
