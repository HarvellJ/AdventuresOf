package com.adventuresof.game.character;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PlayerCompanion extends NPC {

	public PlayerCompanion(TiledMapTileLayer accessibleTiles, GameCharacter characterToFollow) {
		super(accessibleTiles, "animation_sheet.png", 8, 5, 1700, 1300, false, false, 50, 50);
		this.setTarget(characterToFollow);
		// when companion is created, send welcome message to player
		this.addMessageToMessageQueue("Hello there! Welcome to the tutorial");
		this.addMessageToMessageQueue("I am here to guide you through your adventures.");
		this.addMessageToMessageQueue("Lets start by moving around. Right click anywhere on the island to move to that location");
		this.addMessageToMessageQueue("And dont worry, you can't fall off into the ocean (I know you was wondering).");
	}

	protected void createAnimations() {
		this.runRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 5, 15, 0.08f);
		this.runLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 5, 21, 0.08f);
		this.runDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 4, 5, 0.08f);
		this.runUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 4, 10, 0.08f);

		this.attackDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 27, 1f);
		this.attackUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 30, 1f);
		this.attackRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 33, 1f);
		this.attackLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 36, 1f);
		
		this.idleUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 29, 0.08f);
		this.idleDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.idleLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 23, 0.08f);
		this.idleRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 14, 0.08f);
	}    
	
}
