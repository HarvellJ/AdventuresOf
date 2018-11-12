package com.adventuresof.game.character;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemy extends NPC {

	private int width, height;
	private float speed, x, y, health, startHealth;
	private Texture texture, healthBackground, healthForeground, healthBorder;
	
	public Enemy(TiledMapTileLayer accessibleTiles, String textureSheet, float startX, float startY) {
		super(accessibleTiles, textureSheet, 8, 5, startX, startY, false, true, 50, 50);
		this.setCharacterDirection(Direction.left);
		this.health = health;
		this.startHealth = health;
		this.healthBackground = QuickLoad("healthBackground");
		this.healthForeground = QuickLoad("healthForeground");
		this.healthBorder = QuickLoad("healthBorder");
		
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
		
		this.deathDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 27, 1f);
		this.deathUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 30, 1f);
		this.deathRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 33, 1f);
		this.deathLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 36, 1f);
	}   
	
	public void draw() {
		DrawQuadTex(healthBackground x, y, width, height);
	}
	}
	
}
