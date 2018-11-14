package com.adventuresof.game.character;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpellAnimation {

	// animation stuff
	// movement
	private Animation<TextureRegion> moveAnimation;	

	// texture sheets
	String movementTextureSheet;

	// texture sheet sizes
	int movementTextureSheetCols, movementTextureSheetRows;

	// movement values
	int moveStart, moveFrames;

	// movement, attack and death animations - for when all animations are on same sheet
	public SpellAnimation(
			String textureSheet, int sheetRows, int sheetCols,
			int moveStart, int moveFrames
			) 
	{
		this.movementTextureSheet = textureSheet;
		this.movementTextureSheetRows = sheetRows;
		this.movementTextureSheetCols = sheetCols;	
		this.moveStart = moveStart;
		this.moveFrames = moveFrames;

		this.initializeAnimations();
	}


	private void initializeAnimations() {
		this.createMovementAnimations();		
	}		

	private void createMovementAnimations() {
		this.setMoveAnimation(AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveFrames, this.moveStart, 0.08f));
	}


	public Animation<TextureRegion> getMoveAnimation() {
		return moveAnimation;
	}


	public void setMoveAnimation(Animation<TextureRegion> moveAnimation) {
		this.moveAnimation = moveAnimation;
	}

}
