package com.adventuresof.game.animation;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Creates and holds all of the animation data for a given character.
 */
public class CharacterAnimation {

	//================================================================================
	// Variables
	//================================================================================
	// movement
	private Animation<TextureRegion> runRightAnimation;
	private Animation<TextureRegion> runLeftAnimation;
	private Animation<TextureRegion> runUpAnimation;
	private Animation<TextureRegion> runDownAnimation;
	// combat
	private Animation<TextureRegion> attackDownAnimation;
	private Animation<TextureRegion> attackUpAnimation;
	private Animation<TextureRegion> attackLeftAnimation;
	private Animation<TextureRegion> attackRightAnimation;
	// idle
	private Animation<TextureRegion> idleUpAnimation;
	private Animation<TextureRegion> idleDownAnimation;
	private Animation<TextureRegion> idleRightAnimation;
	private Animation<TextureRegion> idleLeftAnimation;
	// death
	private Animation<TextureRegion> deathUpAnimation;
	private Animation<TextureRegion> deathDownAnimation;
	private Animation<TextureRegion> deathRightAnimation;
	private Animation<TextureRegion> deathLeftAnimation;

	// texture sheets
	String movementTextureSheet;
	String combatTextureSheet;
	String deathTextureSheet;
	
	// texture sheet sizes
	int movementTextureSheetCols, movementTextureSheetRows;
	int combatTextureSheetCols, combatTextureSheetRows;
	int deathTextureSheetCols, deathTextureSheetRows;

	// movement values
	int moveLeftStart, moveLeftFrames;
	int moveRightStart, moveRightFrames;
	int moveDownStart, moveDownFrames;
	int moveUpStart, moveUpFrames;
	// idle values
	int idleLeftStart, idleRightStart, idleDownStart, idleUpStart;
	// combat values
	int attackLeftStart, attackLeftFrames;
	int attackRightStart, attackRightFrames;
	int attackDownStart, attackDownFrames;
	int attackUpStart, attackUpFrames;
	// death values
	int deathAnimationStart, deathAnimationFrames;

	//================================================================================
	// Constructors
	//================================================================================
	/**
	 * @param textureSheet The path to texture sheet containing all sprite animations
	 * @param movementSheetRows The rows within the movement animation sheet
	 * @param movementSheetCols The columns within the movement animation sheet
	 * @param combatSheetRows The rows within the combat animation sheet
	 * @param combatSheetCols The columns within the combat animation sheet
	 * @param deathSheetRows The rows within the death animation sheet
	 * @param deathSheetCols The columns within the death animation sheet
	 * @param moveLeftStart The starting frame within animation sheet of movement in direction of left
	 * @param moveLeftFrames  The frames representing the full duration of the animation
	 * @param moveRightStart The starting frame within animation sheet of movement in direction of right
	 * @param moveRightFrames The frames representing the full duration of the animation
	 * @param moveDownStart The starting frame within animation sheet of movement in direction of down
	 * @param moveDownFrames The frames representing the full duration of the animation
	 * @param moveUpStart The starting frame within animation sheet of movement in direction of up
	 * @param moveUpFrames The frames representing the full duration of the animation
	 * @param idleLeftStart The starting frame within idle animation sheet in direction of left
	 * @param idleRightStart The starting frame within idle animation sheet in direction of right
	 * @param idleDownStart The starting frame within idle animation sheet in direction of down
	 * @param idleUpStart The starting frame within idle animation sheet in direction of up
	 * @param attackLeftStart The starting frame within attack animation sheet in direction of left
	 * @param attackLeftFrames The frames representing the full duration of the animation
	 * @param attackRightStart The starting frame within attack animation sheet in direction of right
	 * @param attackRightFrames The frames representing the full duration of the animation
	 * @param attackDownStart The starting frame within attack animation sheet in direction of down
	 * @param attackDownFrames The frames representing the full duration of the animation
	 * @param attackUpStart The starting frame within attack animation sheet in direction of up
	 * @param attackUpFrames The frames representing the full duration of the animation
	 * @param deathAnimationStart The starting frame within death animation sheet
	 * @param deathAnimationFrames The frames representing the full duration of the animation
	 * Constructor used when all types of animation are on the same sheet
	 */	public CharacterAnimation(
			String textureSheet, int sheetRows, int sheetCols,
			int moveLeftStart, int moveLeftFrames,
			int moveRightStart, int moveRightFrames,
			int moveDownStart, int moveDownFrames,
			int moveUpStart, int moveUpFrames,
			int idleLeftStart, int idleRightStart, int idleDownStart, int idleUpStart,
			int attackLeftStart, int attackLeftFrames,
			int attackRightStart, int attackRightFrames,
			int attackDownStart, int attackDownFrames,
			int attackUpStart, int attackUpFrames,
			int deathAnimationStart, int deathAnimationFrames
			) 
	{
		this.combatTextureSheetRows = sheetRows;
		this.deathTextureSheetRows = sheetRows;
		this.movementTextureSheetRows = sheetRows;
		this.combatTextureSheetCols = sheetCols;
		this.deathTextureSheetCols = sheetCols;
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

		// assign idle values
		this.idleLeftStart = idleLeftStart;
		this.idleRightStart = idleRightStart;
		this.idleDownStart = idleDownStart;
		this.idleUpStart = idleUpStart;

		// as no combat/death animations have been provided, assign most relevant
		this.combatTextureSheet = textureSheet;
		this.attackLeftStart = attackLeftStart;
		this.attackLeftFrames = attackLeftFrames;
		this.attackRightStart = attackRightStart;
		this.attackRightFrames = attackRightFrames;
		this.attackDownStart = attackDownStart;
		this.attackDownFrames = attackDownFrames;
		this.attackUpStart = attackUpStart;
		this.attackUpFrames = attackUpFrames;

		this.deathTextureSheet = textureSheet;
		this.deathAnimationStart = deathAnimationStart;
		this.deathAnimationFrames = deathAnimationFrames;

		this.initializeAnimations();
	}

	/**
	 * @param textureSheetMovement The path to texture sheet containing movement sprites
	 * @param textureSheetCombat The path to texture sheet containing combat sprites
	 * @param textureSheetDeath The path to texture sheet containing death sprites
	 * @param movementSheetRows The rows within the movement animation sheet
	 * @param movementSheetCols The columns within the movement animation sheet
	 * @param combatSheetRows The rows within the combat animation sheet
	 * @param combatSheetCols The columns within the combat animation sheet
	 * @param deathSheetRows The rows within the death animation sheet
	 * @param deathSheetCols The columns within the death animation sheet
	 * @param moveLeftStart The starting frame within animation sheet of movement in direction of left
	 * @param moveLeftFrames  The frames representing the full duration of the animation
	 * @param moveRightStart The starting frame within animation sheet of movement in direction of right
	 * @param moveRightFrames The frames representing the full duration of the animation
	 * @param moveDownStart The starting frame within animation sheet of movement in direction of down
	 * @param moveDownFrames The frames representing the full duration of the animation
	 * @param moveUpStart The starting frame within animation sheet of movement in direction of up
	 * @param moveUpFrames The frames representing the full duration of the animation
	 * @param idleLeftStart The starting frame within idle animation sheet in direction of left
	 * @param idleRightStart The starting frame within idle animation sheet in direction of right
	 * @param idleDownStart The starting frame within idle animation sheet in direction of down
	 * @param idleUpStart The starting frame within idle animation sheet in direction of up
	 * @param attackLeftStart The starting frame within attack animation sheet in direction of left
	 * @param attackLeftFrames The frames representing the full duration of the animation
	 * @param attackRightStart The starting frame within attack animation sheet in direction of right
	 * @param attackRightFrames The frames representing the full duration of the animation
	 * @param attackDownStart The starting frame within attack animation sheet in direction of down
	 * @param attackDownFrames The frames representing the full duration of the animation
	 * @param attackUpStart The starting frame within attack animation sheet in direction of up
	 * @param attackUpFrames The frames representing the full duration of the animation
	 * @param deathAnimationStart The starting frame within death animation sheet
	 * @param deathAnimationFrames The frames representing the full duration of the animation
	 * Constructor used when death animations are on separate animation sheet
	 */
	public CharacterAnimation(
			String textureSheetMovement, String textureSheetCombat, String textureSheetDeath, 
			int movementSheetRows, int movementSheetCols,
			int combatSheetRows, int combatSheetCols,
			int deathSheetRows, int deathSheetCols,
			int moveLeftStart, int moveLeftFrames,
			int moveRightStart, int moveRightFrames,
			int moveDownStart, int moveDownFrames,
			int moveUpStart, int moveUpFrames,
			int idleLeftStart, int idleRightStart, int idleDownStart, int idleUpStart,
			int attackLeftStart, int attackLeftFrames,
			int attackRightStart, int attackRightFrames,
			int attackDownStart, int attackDownFrames,
			int attackUpStart, int attackUpFrames,
			int deathAnimationStart, int deathAnimationFrames
			) 
	{
		this.combatTextureSheetRows = combatSheetRows;
		this.deathTextureSheetRows = deathSheetRows;
		this.movementTextureSheetRows = movementSheetRows;
		this.combatTextureSheetCols = combatSheetCols;
		this.deathTextureSheetCols = deathSheetCols;
		this.movementTextureSheetCols = movementSheetCols;
		
		
		// assign movement values
		this.movementTextureSheet = textureSheetMovement;
		this.moveLeftStart = moveLeftStart;
		this.moveLeftFrames = moveLeftFrames;
		this.moveRightStart = moveRightStart;
		this.moveRightFrames = moveRightFrames;
		this.moveDownStart = moveDownStart;
		this.moveDownFrames = moveDownFrames;
		this.moveUpStart = moveUpStart;
		this.moveUpFrames = moveUpFrames;

		// assign idle values
		this.idleLeftStart = idleLeftStart;
		this.idleRightStart = idleRightStart;
		this.idleDownStart = idleDownStart;
		this.idleUpStart = idleUpStart;

		// as no combat/death animations have been provided, assign most relevant
		this.combatTextureSheet = textureSheetCombat;
		this.attackLeftStart = attackLeftStart;
		this.attackLeftFrames = attackLeftFrames;
		this.attackRightStart = attackRightStart;
		this.attackRightFrames = attackRightFrames;
		this.attackDownStart = attackDownStart;
		this.attackDownFrames = attackDownFrames;
		this.attackUpStart = attackUpStart;
		this.attackUpFrames = attackUpFrames;

		this.deathTextureSheet = textureSheetDeath;
		this.deathAnimationStart = deathAnimationStart;
		this.deathAnimationFrames = deathAnimationFrames;

		this.initializeAnimations();
	}


	//================================================================================
	// Properties
	//================================================================================
	public Animation<TextureRegion> getRunRightAnimation() {
		return runRightAnimation;
	}

	public Animation<TextureRegion> getRunLeftAnimation() {
		return runLeftAnimation;
	}

	public Animation<TextureRegion> getRunUpAnimation() {
		return runUpAnimation;
	}

	public Animation<TextureRegion> getRunDownAnimation() {
		return runDownAnimation;
	}

	public Animation<TextureRegion> getAttackDownAnimation() {
		return attackDownAnimation;
	}

	public Animation<TextureRegion> getAttackUpAnimation() {
		return attackUpAnimation;
	}

	public Animation<TextureRegion> getAttackLeftAnimation() {
		return attackLeftAnimation;
	}

	public Animation<TextureRegion> getAttackRightAnimation() {
		return attackRightAnimation;
	}

	public Animation<TextureRegion> getIdleUpAnimation() {
		return idleUpAnimation;
	}

	public Animation<TextureRegion> getIdleDownAnimation() {
		return idleDownAnimation;
	}


	public Animation<TextureRegion> getIdleRightAnimation() {
		return idleRightAnimation;
	}

	public Animation<TextureRegion> getIdleLeftAnimation() {
		return idleLeftAnimation;
	}

	public Animation<TextureRegion> getDeathUpAnimation() {
		return deathUpAnimation;
	}

	public Animation<TextureRegion> getDeathDownAnimation() {
		return deathDownAnimation;
	}

	public Animation<TextureRegion> getDeathRightAnimation() {
		return deathRightAnimation;
	}

	public Animation<TextureRegion> getDeathLeftAnimation() {
		return deathLeftAnimation;
	}

	

	//================================================================================
	// Methods
	//================================================================================

	/**
	 * initialized the different types of animations
	 */
	private void initializeAnimations() {
		this.createMovementAnimations();
		this.createIdleAnimations();
		this.createCombatAnimmations();
		this.createDeathAnimations();
	}		

	/**
	 * creates the movement animations 
	 */
	private void createMovementAnimations() {
		this.runLeftAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveLeftFrames, this.moveLeftStart, 0.08f);
		this.runRightAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveRightFrames, this.moveRightStart, 0.08f);
		this.runDownAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveDownFrames, this.moveDownStart, 0.08f);
		this.runUpAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, this.moveUpFrames, this.moveUpStart, 0.08f);			
	}

	/**
	 * creates the idle animations 
	 */
	private void createIdleAnimations() {
		this.idleLeftAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, 1, this.idleLeftStart, 0.08f);
		this.idleRightAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, 1, this.idleRightStart, 0.08f);
		this.idleDownAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, 1, this.idleDownStart, 0.08f);
		this.idleUpAnimation = AnimationFactory.createAnimation(this.movementTextureSheet, this.movementTextureSheetCols, this.movementTextureSheetRows, 1, this.idleUpStart, 0.08f);
	}

	/**
	 * creates the combat animations 
	 */
	private void createCombatAnimmations() {
		this.attackLeftAnimation = AnimationFactory.createAnimation(this.combatTextureSheet, this.combatTextureSheetCols, this.combatTextureSheetRows, this.attackLeftFrames, this.attackLeftStart, 0.08f);
		this.attackRightAnimation = AnimationFactory.createAnimation(this.combatTextureSheet, this.combatTextureSheetCols, this.combatTextureSheetRows, this.attackRightFrames, this.attackRightStart, 0.08f);
		this.attackDownAnimation = AnimationFactory.createAnimation(this.combatTextureSheet, this.combatTextureSheetCols, this.combatTextureSheetRows, this.attackDownFrames, this.attackDownStart, 0.08f);
		this.attackUpAnimation = AnimationFactory.createAnimation(this.combatTextureSheet, this.combatTextureSheetCols, this.combatTextureSheetRows, this.attackUpFrames, this.attackUpStart, 0.08f);
	}

	/**
	 * creates the death animations 
	 */
	private void createDeathAnimations() {
		this.deathLeftAnimation = AnimationFactory.createAnimation(this.deathTextureSheet, this.deathTextureSheetCols, this.deathTextureSheetRows, this.deathAnimationFrames, this.deathAnimationStart, 0.08f);
		this.deathRightAnimation = AnimationFactory.createAnimation(this.deathTextureSheet, this.deathTextureSheetCols, this.deathTextureSheetRows, this.deathAnimationFrames, this.deathAnimationStart, 0.08f);
		this.deathDownAnimation = AnimationFactory.createAnimation(this.deathTextureSheet, this.deathTextureSheetCols, this.deathTextureSheetRows, this.deathAnimationFrames, this.deathAnimationStart, 0.08f);
		this.deathUpAnimation = AnimationFactory.createAnimation(this.deathTextureSheet, this.deathTextureSheetCols, this.deathTextureSheetRows, this.deathAnimationFrames, this.deathAnimationStart, 0.08f);
	}
}
