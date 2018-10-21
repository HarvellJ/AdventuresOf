package com.adventuresof.game.character;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemy extends NPC {

	public Enemy(TiledMapTileLayer accessibleTiles, String textureSheet, float startX, float startY) {
		super(accessibleTiles, textureSheet, 4, 2, startX, startY);
		super.setCharacterDirection(Direction.left);
	}

	protected void createAnimations() {
		this.runRightAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 5, 15, 0.08f);
		this.runLeftAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 5, 21, 0.08f);
		this.runDownAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 4, 5, 0.08f);
		this.runUpAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 4, 10, 0.08f);

		this.attackDownAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 3, 27, 1f);
		this.attackUpAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 3, 30, 1f);
		this.attackRightAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 3, 33, 1f);
		this.attackLeftAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 3, 36, 1f);
		
		this.idleUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 29, 0.08f);
		this.idleDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.idleLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 23, 0.08f);
		this.idleRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 14, 0.08f);
	}    
	
}
