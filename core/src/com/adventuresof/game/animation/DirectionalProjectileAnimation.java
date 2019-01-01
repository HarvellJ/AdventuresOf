package com.adventuresof.game.animation;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DirectionalProjectileAnimation {

	// movement
	private Animation<TextureRegion> RightAnimation;
	private Animation<TextureRegion> LeftAnimation;
	private Animation<TextureRegion> UpAnimation;
	private Animation<TextureRegion> DownAnimation;

	// texture sheets
	String movementTextureSheet;
	
	// texture sheet sizes
	int movementTextureSheetCols, movementTextureSheetRows;

	// movement values
	int moveLeftStart, moveLeftFrames;
	int moveRightStart, moveRightFrames;
	int moveDownStart, moveDownFrames;
	int moveUpStart, moveUpFrames;
	// idle values

	// movement, attack and death animations - for when all animations are on same sheet
	public DirectionalProjectileAnimation(
			String textureSheet, int sheetRows, int sheetCols,
			int moveLeftStart, int moveLeftFrames,
			int moveRightStart, int moveRightFrames,
			int moveDownStart, int moveDownFrames,
			int moveUpStart, int moveUpFrames			
			) 
	{	
		this.movementTextureSheetRows = sheetRows;
		this.movementTextureSheetCols = sheetCols;
		
		// assign movement values
		this.movementTextureSheet = textureSheet;
		this.moveLeftStart = moveLeftStart;
		this.moveLeftFrames = moveLeftFrames;
		this.moveRightStart = moveRightStart;
		this.moveRightFrames = moveRightFrames;
		this.moveDownStart = moveDownStart;
		this.moveDownFrames = moveDownFrames;
		this.moveUpStart = moveUpStart;
		this.moveUpFrames = moveUpFrames;

		this.initializeAnimations();
	}


	public Animation<TextureRegion> getRightAnimation() {
		return RightAnimation;
	}

	public Animation<TextureRegion> getLeftAnimation() {
		return LeftAnimation;
	}

	public Animation<TextureRegion> getUpAnimation() {
		return UpAnimation;
	}

	public Animation<TextureRegion> getDownAnimation() {
		return DownAnimation;
	}

	private void initializeAnimations() {
		this.createMovementAnimations();
	}		

	private void createMovementAnimations() {
		this.LeftAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveLeftFrames, this.moveLeftStart, 0.08f);
		this.RightAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveRightFrames, this.moveRightStart, 0.08f);
		this.DownAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveDownFrames, this.moveDownStart, 0.08f);
		this.UpAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveUpFrames, this.moveUpStart, 0.08f);			
	}
}
