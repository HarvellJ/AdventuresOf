package com.adventuresof.game.animation;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Creates and holds all of the animation data for a given spell.
 */
public abstract class SpellAnimation {
	//================================================================================
	// Variables
	//================================================================================
	// movement
	private Animation<TextureRegion> RightAnimation;
	private Animation<TextureRegion> LeftAnimation;
	private Animation<TextureRegion> UpAnimation;
	private Animation<TextureRegion> DownAnimation;

	// texture sheets
	protected String movementTextureSheet_up;
	protected String movementTextureSheet_down;
	protected String movementTextureSheet_left;
	protected String movementTextureSheet_right;
	
	// texture sheet sizes
	int movementTextureSheetCols, movementTextureSheetRows;

	// movement values
	int moveLeftStart, moveLeftFrames;
	int moveRightStart, moveRightFrames;
	int moveDownStart, moveDownFrames;
	int moveUpStart, moveUpFrames;

	
	//================================================================================
	// Constructors
	//================================================================================
	/**
	 * @param movementTextureSheet_up The texture sheet containing sprites for when spell is moving upwards
	 * @param movementTextureSheet_down The texture sheet containing sprites for when spell is moving downwards
	 * @param movementTextureSheet_left The texture sheet containing sprites for when spell is moving left
	 * @param movementTextureSheet_right The texture sheet containing sprites for when spell is moving right
	 * @param sheetRows The amount of rows within each animation sheets, assumes each are in same format i.e. 4 rows for each direction sheet
	 * @param sheetCols The amount of columns within each animation sheets, assumes each are in same format i.e. 4 columns for each direction sheet
	 * @param moveLeftStart The starting frame within animation sheet of movement in direction of left
	 * @param moveLeftFrames The frames representing the full duration of the animation
	 * @param moveRightStart The starting frame within animation sheet of movement in direction of right
	 * @param moveRightFrames The frames representing the full duration of the animation
	 * @param moveDownStart The starting frame within animation sheet of movement in direction of down
	 * @param moveDownFrames The frames representing the full duration of the animation
	 * @param moveUpStart The starting frame within animation sheet of movement in direction of up
	 * @param moveUpFrames The frames representing the full duration of the animation
	 */
	public SpellAnimation(
			String movementTextureSheet_up, 
			String movementTextureSheet_down, 
			String movementTextureSheet_left, 
			String movementTextureSheet_right, 
			int sheetRows, int sheetCols,
			int moveLeftStart, int moveLeftFrames,
			int moveRightStart, int moveRightFrames,
			int moveDownStart, int moveDownFrames,
			int moveUpStart, int moveUpFrames			
			) 
	{	
		this.movementTextureSheetRows = sheetRows;
		this.movementTextureSheetCols = sheetCols;
		
		// assign movement values
		this.movementTextureSheet_up = movementTextureSheet_up;
		this.movementTextureSheet_down = movementTextureSheet_down;
		this.movementTextureSheet_left = movementTextureSheet_left;
		this.movementTextureSheet_right = movementTextureSheet_right;

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
	
	//================================================================================
	// Properties
	//================================================================================
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

	//================================================================================
	// Methods
	//================================================================================	

	/**
	 * initializes the animations
	 */
	private void initializeAnimations() {
		this.createMovementAnimations();
	}		

	/**
	 * creates the movement animations 
	 */
	private void createMovementAnimations() {
		this.LeftAnimation = AnimationFactory.createAnimation(this.movementTextureSheet_left, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveLeftFrames, this.moveLeftStart, 0.08f);
		this.RightAnimation = AnimationFactory.createAnimation(this.movementTextureSheet_right, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveRightFrames, this.moveRightStart, 0.08f);
		this.DownAnimation = AnimationFactory.createAnimation(this.movementTextureSheet_down, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveDownFrames, this.moveDownStart, 0.08f);
		this.UpAnimation = AnimationFactory.createAnimation(this.movementTextureSheet_up, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveUpFrames, this.moveUpStart, 0.08f);			
	}
}
