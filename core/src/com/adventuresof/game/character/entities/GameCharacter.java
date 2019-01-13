package com.adventuresof.game.character.entities;

import java.util.ArrayList;
import java.util.Random;

import com.adventuresof.game.animation.CharacterAnimation;
import com.adventuresof.game.character.enums.CharacterClass;
import com.adventuresof.game.character.enums.CharacterLevel;
import com.adventuresof.game.combat.entities.InstantCastAbility;
import com.adventuresof.game.combat.entities.Projectile;
import com.adventuresof.game.combat.enums.Spell;
import com.adventuresof.game.combat.enums.SpellType;
import com.adventuresof.game.combat.logic.DamageCalculator;
import com.adventuresof.game.common.entities.GameObject;
import com.adventuresof.game.common.entities.MoveableObject;
import com.adventuresof.game.common.enums.Direction;
import com.adventuresof.game.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Vector3;

/**
 *
 */
public abstract class GameCharacter extends MoveableObject {

	//================================================================================
	// Properties 
	//================================================================================
	private String name; // characters name
	protected CharacterClass characterClass; // used to determine the abilities available to this character
	protected GameWorld gameWorld; // the world to which they belong

	// movement variables
	protected boolean isStatic; // used to determine if the character is static on the map or whether they move around


	// animation stuff
	protected CharacterAnimation characterAnimation;
	private boolean isIdle;
	private Direction currentCharacterDirection;

	// display
	private ArrayList<String> messageQueue; // used to queue messages that will be displayed in game by the character
	private float timeLastMessageDisplayed;
	private ArrayList<String> damageMessageQueue;
	private float timeLastDamagedMessageDisplayed;
	protected Sprite hitSplt; 
	private HealthBar healthBar;
	BitmapFont font;
	private Vector3 namePosition;
	// display
	private ArrayList<String> buffMessageQueue; // used to queue buff messages that will be displayed in game by the character
	private float timeLastBuffMessageDisplayed;
	private float buffYPosition; // used for scrolling effect

	// collision stuff
	private TiledMapTileLayer accessibleTiles; // represents the tiles that are accessible by the character
	protected int characterHeight; // the character's height - used to draw the bounding rectangle (hit box)
	protected int characterWidth; // the character's width - used to draw the bounding rectangle (hit box)

	// combat stuff
	private int health;
	private int maxHealth = 100;
	private boolean isFrozen;
	private float frozenTime;
	protected long abilityOneLastActivated;
	protected long abilityTwoLastActivated;
	protected long abilityThreeLastActivated;
	protected long abilityFourLastActivated;
	private float attackInterval = 2; // a time interval representing the attack speed of this character - defaults to 2
	private float lungeForwardPerformed;
	private float lungeBackwardPerformed;
	protected GameCharacter target; // the characters current target. This can be friendly or hostile
	protected boolean isHostile; // toggles whether the character can be attacked or is passive
	private CharacterLevel baseLevel; // the character level, used to determine their power

	private int healthBonusPoints;
	private int defenceBonusPoints;
	private int damageBonusPoints;
	private int temporaryHealthBonusPoints;
	private int temporaryDefenceBonusPoints;
	private int temporaryDamageBonusPoints;
	private long timeOfTemporaryBuff;

	// death stuff
	protected boolean isDying;
	private float stateTimeOfDeath; // used to calculate time required for death animation
	protected boolean canRespawn;
	protected boolean isDead;
	protected float startTime;


	//================================================================================
	// Constructors 
	//================================================================================
	/**
	 * @param gameWorld The world in which the character exists
	 * @param accessibleTiles The tiles that a character can move to
	 * @param startX The starting X co-ordinate for this character
	 * @param startY The starting Y co-ordinate for this character
	 * @param isHostile A value indicating where this character is hostile (whether they can engage in combat)
	 * @param characterWidth The character's width - used for things like hitboxes
	 * @param characterHeight The character's height 
	 * @param characterAnimation - The CharacterAnimation object for this character
	 * @param speed The speed at which this character can move
	 * @param canRespawn A boolean indicating whether or not this character can respawn upon death
	 * @param name The name of this character
	 * @param characterClass The class of this character - determines things such as starting health and abilities
	 * @param baseLevel The base level of this character
	 * Constructor method for GameCharacter
	 */
	public GameCharacter(
			GameWorld gameWorld,
			TiledMapTileLayer accessibleTiles,
			float startX, float startY,
			boolean isHostile,
			int characterWidth, int characterHeight,
			CharacterAnimation characterAnimation,
			float speed, boolean canRespawn, String name, CharacterClass characterClass,
			CharacterLevel baseLevel) {

		super(startX, startY);
		this.startTime = System.currentTimeMillis();
		this.setBaseLevel(baseLevel);
		this.gameWorld = gameWorld;

		this.characterClass = characterClass;

		this.characterAnimation = characterAnimation;
		this.speed = speed;
		this.canRespawn = canRespawn;
		this.stateTime = 0f;	

		hitSplt = new Sprite(new Texture("hitsplat.png"));
		hitSplt.setScale(0.4f);

		this.name = name;
		// instantiate characters' current position as a blank vector3
		currentPosition = new Vector3(startX, startY, 0);
		this.spawnLocation = new Vector3(startX, startY, 0);
		this.namePosition =  new Vector3(startX, startY, 0);
		// they are alive...
		this.isDying = false;
		this.canDispose = false;

		// create objects required for collision logic
		this.characterHeight = characterHeight;
		this.characterWidth = characterWidth;
		this.hitBox = new Rectangle(); 	
		((Rectangle) this.hitBox).set(startX, startY, this.characterWidth, this.characterHeight);
		this.accessibleTiles = accessibleTiles;

		// Instantiate the message queue
		this.messageQueue = new ArrayList<String>();
		this.damageMessageQueue = new ArrayList<String>();
		this.buffMessageQueue = new ArrayList<String>();
		// Default the character to idle state (prevents them running on the spot)
		this.isIdle = true;

		// set the target's hostility
		this.isHostile = isHostile;

		// default health to 100
		this.health = baseLevel.getBaseHealth();
		this.maxHealth = baseLevel.getBaseHealth();
		this.damageBonusPoints = baseLevel.getBaseDamageBonus();
		this.defenceBonusPoints = baseLevel.getBaseDefenceBonus();
		this.isFrozen = false;
		this.frozenTime = 0;
		font = new BitmapFont(); 
		healthBar = new HealthBar(this, new Texture("healthBackground.png"),
				new Texture("healthForeground.png"));
	}

	//================================================================================
	// Accessor methods
	//================================================================================
	public boolean isDead() {
		return isDead;
	}

	/**
	 * @return the temporaryDamageBonusPoints
	 */
	public int getTemporaryDamageBonusPoints() {
		return temporaryDamageBonusPoints;
	}



	/**
	 * @param temporaryDamageBonusPoints the temporaryDamageBonusPoints to set
	 */
	public void setTemporaryDamageBonusPoints(int temporaryDamageBonusPoints) {
		this.temporaryDamageBonusPoints = temporaryDamageBonusPoints;
	}



	public int getTemporaryDefenceBonusPoints() {
		return temporaryDefenceBonusPoints;
	}



	public void setTemporaryDefenceBonusPoints(int temporaryDefenceBonusPoints) {
		this.temporaryDefenceBonusPoints = temporaryDefenceBonusPoints;
	}



	public int getDamageBonusPoints() {
		return damageBonusPoints;
	}



	public int getTemporaryHealthBonusPoints() {
		return temporaryHealthBonusPoints;
	}



	public void setTemporaryHealthBonusPoints(int temporaryHealthBonusPoints) {
		this.temporaryHealthBonusPoints = temporaryHealthBonusPoints;
	}



	public void setDamageBonusPoints(int damageBonusPoints) {
		this.damageBonusPoints = damageBonusPoints;
	}



	public int getDefenceBonusPoints() {
		return defenceBonusPoints;
	}



	public void setDefenceBonusPoints(int defenceBonusPoints) {
		this.defenceBonusPoints = defenceBonusPoints;
	}



	public int getHealthBonusPoints() {
		return healthBonusPoints;
	}



	public void setHealthBonusPoints(int healthBonusPoints) {
		this.healthBonusPoints = healthBonusPoints;
	}



	public CharacterLevel getBaseLevel() {
		return baseLevel;
	}



	public void setBaseLevel(CharacterLevel baseLevel) {
		this.baseLevel = baseLevel;
	}

	public CharacterClass getCharacterClass() {
		return characterClass;
	}

	public boolean isDying() {
		return isDying;
	}

	public void setDying(boolean isDying) {
		this.isDying = isDying;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public GameCharacter getTarget() {
		return target;
	}

	public void setTarget(GameCharacter target) {
		this.target = target;	
	}

	public Vector3 getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Vector3 currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Rectangle getHitBox() {
		return (Rectangle) hitBox;
	}	

	public void setHitBox(Rectangle boundingRectangle) {
		this.hitBox = boundingRectangle;
	}

	public long getAbilityOneLastActivated() {
		return abilityOneLastActivated;
	}

	public void setAbilityOneLastActivated(long abilityOneLastActivated) {
		this.abilityOneLastActivated = abilityOneLastActivated;
	}

	public long getAbilityTwoLastActivated() {
		return abilityTwoLastActivated;
	}

	public void setAbilityTwoLastActivated(long abilityTwoLastActivated) {
		this.abilityTwoLastActivated = abilityTwoLastActivated;
	}

	public long getAbilityThreeLastActivated() {
		return abilityThreeLastActivated;
	}

	public void setAbilityThreeLastActivated(long abilityThreeLastActivated) {
		this.abilityThreeLastActivated = abilityThreeLastActivated;
	}

	public long getAbilityFourLastActivated() {
		return abilityFourLastActivated;
	}

	public void setAbilityFourLastActivated(long abilityFourLastActivated) {
		this.abilityFourLastActivated = abilityFourLastActivated;
	}

	protected void createAnimations() {

	}

	public void addMessageToMessageQueue(String message) {
		this.messageQueue.add(message);
	}

	public void addMessageToDamageQueue(String message) {
		this.damageMessageQueue.add(message);
	}

	public void addMessageToBuffQueue(String message) {
		this.buffMessageQueue.add(message);
	}

	public float getAttackInterval() {
		return attackInterval;
	}

	public void setAttackInterval(float attackInterval) {
		this.attackInterval = attackInterval;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHostile() {
		return isHostile;
	}

	public void setHostile(boolean isHostile) {
		this.isHostile = isHostile;
	}

	public void setTargetLocation(Vector3 pointToMoveTo) {
		this.pointToMoveTo = pointToMoveTo;
	}

	public void setCharacterDirection(Direction direction) {
		currentCharacterDirection = direction;
	}


	//================================================================================
	// Update/render
	//================================================================================
	public void update() {    	
		stateTime += Gdx.graphics.getDeltaTime(); // increment state time
		this.resetTemporaryBuffs();
		if(isDying) {
			if (stateTimeOfDeath < this.stateTime - 2) {
				if(canRespawn) {				
					this.currentPosition.x = this.spawnLocation.x;
					this.currentPosition.y = this.spawnLocation.y;
					this.updateHitBox();
					this.health = 100;		
					this.isDying = false;
					this.target = null;
					this.damageMessageQueue = new ArrayList<String>();				
				}else {
					this.isDead = true;
					this.canDispose = true;
				}
			}
		}
		else {
			// check for any spells that may block movement
			if(!(isFrozen && this.frozenTime > this.stateTime - 5)) {	
				this.isFrozen = false;
				// if player can move, then do so accordingly
				if(target != null && this.isHostile) {
					if(!target.isDying() && (target.getHealth() > 0)) {
						// check distance from target before attacking and moving to target location
						if(Math.sqrt(Math.pow((Math.max(target.getCurrentPosition().x, this.getCurrentPosition().x) - Math.min(target.getCurrentPosition().x, this.getCurrentPosition().x)), 2) + Math.pow((Math.max(target.getCurrentPosition().y, this.getCurrentPosition().y) - Math.min(target.getCurrentPosition().y, this.getCurrentPosition().y)), 2)) < 50)
						{
							// target is within melee distance, attack
							this.performMeleeAttack();
						}
						else if(Math.sqrt(Math.pow((Math.max(target.getCurrentPosition().x, this.getCurrentPosition().x) - Math.min(target.getCurrentPosition().x, this.getCurrentPosition().x)), 2) + Math.pow((Math.max(target.getCurrentPosition().y, this.getCurrentPosition().y) - Math.min(target.getCurrentPosition().y, this.getCurrentPosition().y)), 2)) > 200
								&& Math.sqrt(Math.pow((Math.max(target.getCurrentPosition().x, this.getCurrentPosition().x) - Math.min(target.getCurrentPosition().x, this.getCurrentPosition().x)), 2) + Math.pow((Math.max(target.getCurrentPosition().y, this.getCurrentPosition().y) - Math.min(target.getCurrentPosition().y, this.getCurrentPosition().y)), 2)) < 400)
						{
							// target is within range distance, perform range attack (if possible)
							this.performAbilityOne(this.target.getCurrentPosition().x, this.target.getCurrentPosition().y);		
							this.setTargetLocation(new Vector3((float)target.getCurrentPosition().x - 45, (float)target.getCurrentPosition().y, 0));
						}						
						else {
							this.setTargetLocation(new Vector3((float)target.getCurrentPosition().x - 45, (float)target.getCurrentPosition().y, 0));
						}
					}else {
						this.target = null;
					}
				}else if (target != null ){
					this.setTargetLocation(new Vector3((float)target.getCurrentPosition().x - 30, (float)target.getCurrentPosition().y, 0));
				}

				this.moveObject();
				//following any positional moves, update the characters bounding record
				this.updateHitBox();
			}else {
				if(target != null && this.isHostile) {
					if(!target.isDying() && (target.getHealth() > 0)) {
						this.performAbilityOne(this.target.getCurrentPosition().x, this.target.getCurrentPosition().y);						
					}
				}
			}
		}

		healthBar.update();
		this.updateNamePosition();
	}

	/**
	 * Renders the up-to-date character to the screen
	 * Called every screen following the call to "update"
	 */
	public void render(SpriteBatch spriteBatch) {

		// get the relevant animation frame (based on current character direction)
		TextureRegion currentAnimationFrame;
		if(this.isDying) {
			currentAnimationFrame = this.getRelevantIdleTexture();
			Sprite test = new Sprite(currentAnimationFrame);
			test.rotate(90f);
			test.setX(currentPosition.x -15);
			test.setY(currentPosition.y - 10);
			test.draw(spriteBatch);
		}
		else if(this.isIdle) {
			currentAnimationFrame = this.getRelevantIdleTexture();
			spriteBatch.draw(currentAnimationFrame, currentPosition.x -15, currentPosition.y - 10); // Draw current frame ()
		}
		else {
			currentAnimationFrame = this.getRelevantDirectionAnimationFrame();
			spriteBatch.draw(currentAnimationFrame, currentPosition.x -15, currentPosition.y - 10); // Draw current frame ()

		}

		// Draw the character frame at the current position

		// handle any text
		this.displayMessage(spriteBatch);
		this.displayDamage(spriteBatch);
		this.displayBuffMessage(spriteBatch);
		this.displayCharacterName(spriteBatch);
		healthBar.render(spriteBatch);
	}




	//================================================================================
	// Animation
	//================================================================================
	/**
	 * gets the next animation frame for the direction the character is facing
	 * @return
	 */
	private TextureRegion getRelevantDirectionAnimationFrame() {
		// if we reach here, player not performing attack, return standard movement animation frame
		if(currentCharacterDirection == Direction.up) {
			return this.characterAnimation.getRunUpAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentCharacterDirection == Direction.down) {
			return this.characterAnimation.getRunDownAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentCharacterDirection == Direction.right) {
			return this.characterAnimation.getRunRightAnimation().getKeyFrame(stateTime, true);
		}
		else {
			return this.characterAnimation.getRunLeftAnimation().getKeyFrame(stateTime, true);
		}
	}   


	/**
	 * Simply returns the relevant texture for when the character is idle, based on their current direction
	 * @return The character texture for the current direction
	 */
	private TextureRegion getRelevantDeathAnimationFrame() {
		if(currentCharacterDirection == Direction.up) {
			return this.characterAnimation.getDeathUpAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentCharacterDirection == Direction.down) {
			return this.characterAnimation.getDeathDownAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentCharacterDirection == Direction.right) {
			return this.characterAnimation.getDeathRightAnimation().getKeyFrame(stateTime, true);
		}
		else {
			return this.characterAnimation.getDeathLeftAnimation().getKeyFrame(stateTime, true);
		}
	}


	/**
	 * Simply returns the relevant texture for when the character is idle, based on their current direction
	 * @return The character texture for the current direction
	 */
	private TextureRegion getRelevantIdleTexture() {
		if(currentCharacterDirection == Direction.up) {
			return this.characterAnimation.getIdleUpAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentCharacterDirection == Direction.down) {
			return this.characterAnimation.getIdleDownAnimation().getKeyFrame(stateTime, true);
		}
		else if(currentCharacterDirection == Direction.right) {
			return this.characterAnimation.getIdleRightAnimation().getKeyFrame(stateTime, true);
		}
		else {
			return this.characterAnimation.getIdleLeftAnimation().getKeyFrame(stateTime, true);
		}
	}



	//================================================================================
	// Movement
	//================================================================================

	/* (non-Javadoc)
	 * @see com.adventuresof.game.common.entities.MoveableObject#moveObject()
	 */
	public void moveObject() {
		// check the point to move to value is set, if not, there is no need to move this frame.
		if(pointToMoveTo != null) {
			// first, work out the direction in which the character should be facing (so we can use relevant animations)
			this.calculateCharacterDirection();    			

			// Algorithm for performing (gradual) character movement
			double destinationX = pointToMoveTo.x - currentPosition.x;
			double destinationY = pointToMoveTo.y - currentPosition.y;

			double distanceToTravel = Math.sqrt(destinationX * destinationX + destinationY * destinationY);
			destinationX = destinationX / distanceToTravel;
			destinationY = destinationY / distanceToTravel;

			double nextX = destinationX * speed * Gdx.graphics.getDeltaTime();
			double nextY = destinationY * speed * Gdx.graphics.getDeltaTime();

			double distanceTravelled = Math.sqrt(nextX * nextX + nextY * nextY);

			// check if the character has arrived at desired location
			if ( distanceTravelled > distanceToTravel )
			{
				// stop them moving and set them to idle
				pointToMoveTo = null;
				isIdle = true;
			}
			else
			{
				// check if the next position is an accessible cell, if so, move there. If not, stop moving, character at edge of accessible layer.
				// the / 32 is dividing the current position co-ordinates by the tile sizes
				if(accessibleTiles.getCell(((int) currentPosition.x + (int) nextX) / 16, ((int) currentPosition.y + (int) nextY) / 16) == null) {
					pointToMoveTo = null;
					isIdle = true;
				}else {
					currentPosition.x = currentPosition.x + (float) nextX;
					currentPosition.y = currentPosition.y + (float) nextY;   
					isIdle = false;
				}    			
			} 		
		}
	}

	/**
	 * Performs the calculation to determine the characters direction based on requested movement
	 * Result: sets the current character direction
	 */
	private void calculateCharacterDirection() {
		double xDistanceToTravel;
		double yDistanceToTravel;
		Direction directionX;
		Direction directionY;

		if(target != null) {
			//calculate x distance
			if(currentPosition.x > this.target.getCurrentPosition().x) {
				xDistanceToTravel = currentPosition.x - this.target.getCurrentPosition().x;
				directionX = Direction.left;
			}else {
				xDistanceToTravel = this.target.getCurrentPosition().x - currentPosition.x;
				directionX = Direction.right;
			}
			//calculate y distance
			if(currentPosition.y > this.target.getCurrentPosition().y) {
				yDistanceToTravel = currentPosition.y - this.target.getCurrentPosition().y;
				directionY = Direction.down;
			}else {
				yDistanceToTravel = this.target.getCurrentPosition().y - currentPosition.y  ;
				directionY = Direction.up;
			}

			if(xDistanceToTravel > yDistanceToTravel) {
				this.setCharacterDirection(directionX);

			}else {
				this.setCharacterDirection(directionY);
			} 
		}else {
			//calculate x distance
			if(currentPosition.x > pointToMoveTo.x) {
				xDistanceToTravel = currentPosition.x - pointToMoveTo.x;
				directionX = Direction.left;
			}else {
				xDistanceToTravel = pointToMoveTo.x - currentPosition.x;
				directionX = Direction.right;
			}
			//calculate y distance
			if(currentPosition.y > pointToMoveTo.y) {
				yDistanceToTravel = currentPosition.y - pointToMoveTo.y;
				directionY = Direction.down;
			}else {
				yDistanceToTravel = pointToMoveTo.y - currentPosition.y  ;
				directionY = Direction.up;
			}

			if(xDistanceToTravel > yDistanceToTravel) {
				this.setCharacterDirection(directionX);

			}else {
				this.setCharacterDirection(directionY);
			}   
		}
	}

	//================================================================================
	// Combat
	//================================================================================

	/**
	 * Resets buffs that were temporary as a result of things like spells. 
	 */
	private void resetTemporaryBuffs() {
		// reset any temporary buffs after 5 seconds
		if(timeOfTemporaryBuff < (System.currentTimeMillis() - 5000)) {
			// dequeue item - display period expired
			this.temporaryDamageBonusPoints = 0;
			this.temporaryDefenceBonusPoints = 0;
			this.temporaryHealthBonusPoints = 0;
		}
	}

	/**
	 * Buffs the character's maximum health value by a given amount
	 * @param amount The amount to buff the health by
	 */
	public void buffHealth(int amount) {
		// buffs the character's health points
		this.maxHealth += amount;
		this.addMessageToBuffQueue("+ " + amount + " Maximum Health");
	}

	/**
	 * Buffs the character's bonus damage points
	 * @param amount The amount to buff by
	 */
	public void buffDamage(int amount) {
		// buffs the character's damage points
		this.damageBonusPoints += amount;
		this.addMessageToBuffQueue("+ " + amount + " Maximum Damage");
	}

	/**
	 * Buffs the character's bonus defence points
	 * @param amount The amount to buff by
	 */
	public void buffDefence(int amount) {
		// buffs the characer's defence points
		this.defenceBonusPoints += amount;
		this.addMessageToBuffQueue("+ " + amount + " Armour");
	}

	/**
	 * Heals the character
	 * @param amount The amount to heal by
	 */
	public void heal(int amount) {
		if(this.health + amount < this.maxHealth) {
			this.health += amount;
		}
		else {
			this.health = 100;
		}

		this.addMessageToBuffQueue("+ " + amount + " Health");
	}

	/**
	 * Temporarily buffs the characters bonus damage points
	 * @param amount The amount to buff by
	 */
	public void TemporarilyBuffDamage(int amount) {
		// buffs the character's damage points
		this.temporaryDamageBonusPoints += amount;
		this.addMessageToBuffQueue("+ " + amount + " Temporary Maximum Damage");
	}

	/**
	 * Temporarily buffs the characters bonus defence points
	 * @param amount The amount to buff by
	 */
	public void TemporarilyBuffDefence(int amount) {
		// buffs the characer's defence points
		this.temporaryDefenceBonusPoints += amount;
		this.addMessageToBuffQueue("+ " + amount + " Temporary Armour");
	}

	/**
	 * Freezes the character, preventing them from moving
	 */
	public void freeze() {
		if(this.isHostile) {
			this.isFrozen = true;
			this.frozenTime = stateTime;
		}	
	}

	/**
	 * Inflicts damage on the character via damage calculations taking into account the maxDamage
	 * @param maxDamage The max damage value that can be inflicted
	 * @param inflictedBy The character who inflicted the damage
	 */
	public void damage(int maxDamage, GameCharacter inflictedBy) {
		if(this.isHostile) {
			int damageAmount = DamageCalculator.calculateDamage(maxDamage,
																inflictedBy.getDamageBonusPoints(), 
																inflictedBy.getTemporaryDamageBonusPoints(),
																this.getDefenceBonusPoints(),
																this.getTemporaryDefenceBonusPoints());
			this.inflictDamage(damageAmount);
			this.addMessageToDamageQueue(Integer.toString(damageAmount));
			if(this.health <= 0) {
				this.isDying = true;
				this.stateTimeOfDeath = this.stateTime;
			}else {
				this.setTarget(inflictedBy); // switch target to character whom inflicted the damage, so NPC's will chase the character
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.adventuresof.game.common.entities.MoveableObject#updateHitBox()
	 */
	public void updateHitBox() {
		this.getHitBox().set(this.currentPosition.x, this.currentPosition.y, this.characterHeight, this.characterWidth);
	}


	/**
	 * Casts the character's first ability on given co-ordinates
	 * @param targetX The X co-ordinate of where to cast the ability
	 * @param targetY The Y co-ordinate of where to cast the ability
	 */
	public void performAbilityOne(float targetX, float targetY) {
		// check cooldown - if cooldown is ok, then cast
		if(this.abilityOneLastActivated < (System.currentTimeMillis() - this.characterClass.getAbilityOne().getCoolDown().getCoolDownDuration())) {
			this.abilityOneLastActivated = System.currentTimeMillis(); // record the time of cast to factor into cooldown time
			if(this.characterClass.getAbilityOne().getSpellType() == SpellType.melee) {
				this.performMeleeAbility(this.characterClass.getAbilityOne());
			}
			else if(this.characterClass.getAbilityOne().getSpellType() == SpellType.projectile) {
				this.performRangeAbility(this.characterClass.getAbilityOne(), targetX, targetY);
			}
			else if(this.characterClass.getAbilityOne().getSpellType() == SpellType.buff){
				this.performBuffAbility(this.characterClass.getAbilityOne());
			}
			else if(this.characterClass.getAbilityOne().getSpellType() == SpellType.instantCast){
				this.performInstantCastAbility(this.characterClass.getAbilityOne(), targetX, targetY);
			}
			else if(this.characterClass.getAbilityOne().getSpellType() == SpellType.multiProjectile) {
				this.performMultiProjectileAbility(this.characterClass.getAbilityOne(), targetX, targetY);
			}
		}
	}

	/**
	 * Casts the character's second ability on given co-ordinates
	 * @param targetX The X co-ordinate of where to cast the ability
	 * @param targetY The Y co-ordinate of where to cast the ability
	 */
	public void performAbilityTwo(float targetX, float targetY) {
		// check cooldown - if cooldown is ok, then cast
		if(this.abilityTwoLastActivated < (System.currentTimeMillis() - this.characterClass.getAbilityTwo().getCoolDown().getCoolDownDuration())) {
			this.abilityTwoLastActivated = System.currentTimeMillis(); // record the time of cast to factor into cooldown time
			if(this.characterClass.getAbilityTwo().getSpellType() == SpellType.melee) {
				this.performMeleeAbility(this.characterClass.getAbilityTwo());
			}
			else if(this.characterClass.getAbilityTwo().getSpellType() == SpellType.projectile) {
				this.performRangeAbility(this.characterClass.getAbilityTwo(), targetX, targetY);
			}
			else if(this.characterClass.getAbilityTwo().getSpellType() == SpellType.buff){
				this.performBuffAbility(this.characterClass.getAbilityTwo());
			}
			else if(this.characterClass.getAbilityTwo().getSpellType() == SpellType.instantCast){
				this.performInstantCastAbility(this.characterClass.getAbilityTwo(), targetX, targetY);
			}
			else if(this.characterClass.getAbilityTwo().getSpellType() == SpellType.multiProjectile) {
				this.performMultiProjectileAbility(this.characterClass.getAbilityTwo(), targetX, targetY);
			}
		}
	}

	/**
	 * Casts the character's third ability on given co-ordinates
	 * @param targetX The X co-ordinate of where to cast the ability
	 * @param targetY The Y co-ordinate of where to cast the ability
	 */
	public void performAbilityThree(float targetX, float targetY) {
		// check cooldown - if cooldown is ok, then cast
		if(this.abilityThreeLastActivated < (System.currentTimeMillis() - this.characterClass.getAbilityThree().getCoolDown().getCoolDownDuration())) {
			this.abilityThreeLastActivated = System.currentTimeMillis(); // record the time of cast to factor into cooldown time
			if(this.characterClass.getAbilityThree().getSpellType() == SpellType.melee) {
				this.performMeleeAbility(this.characterClass.getAbilityThree());
			}
			else if(this.characterClass.getAbilityThree().getSpellType() == SpellType.projectile) {
				this.performRangeAbility(this.characterClass.getAbilityThree(), targetX, targetY);
			}
			else if(this.characterClass.getAbilityThree().getSpellType() == SpellType.buff){
				this.performBuffAbility(this.characterClass.getAbilityThree());
			}
			else if(this.characterClass.getAbilityThree().getSpellType() == SpellType.instantCast){
				this.performInstantCastAbility(this.characterClass.getAbilityThree(), targetX, targetY);
			}
			else if(this.characterClass.getAbilityThree().getSpellType() == SpellType.multiProjectile) {
				this.performMultiProjectileAbility(this.characterClass.getAbilityThree(), targetX, targetY);
			}
		}
	}

	/**
	 * Casts the character's fourth ability on given co-ordinates
	 * @param targetX The X co-ordinate of where to cast the ability
	 * @param targetY The Y co-ordinate of where to cast the ability
	 */
	public void performAbilityFour(float targetX, float targetY) {
		// check cooldown - if cooldown is ok, then cast
		if(this.abilityFourLastActivated < (System.currentTimeMillis() - this.characterClass.getAbilityFour().getCoolDown().getCoolDownDuration())) {
			this.abilityFourLastActivated = System.currentTimeMillis(); // record the time of cast to factor into cooldown time
			if(this.characterClass.getAbilityFour().getSpellType() == SpellType.melee) {
				this.performMeleeAbility(this.characterClass.getAbilityFour());
			}
			else if(this.characterClass.getAbilityFour().getSpellType() == SpellType.projectile) {
				this.performRangeAbility(this.characterClass.getAbilityFour(), targetX, targetY);
			}
			else if(this.characterClass.getAbilityFour().getSpellType() == SpellType.buff){
				this.performBuffAbility(this.characterClass.getAbilityFour());
			}
			else if(this.characterClass.getAbilityFour().getSpellType() == SpellType.instantCast){
				this.performInstantCastAbility(this.characterClass.getAbilityFour(), targetX, targetY);
			}
			else if(this.characterClass.getAbilityFour().getSpellType() == SpellType.multiProjectile) {
				this.performMultiProjectileAbility(this.characterClass.getAbilityFour(), targetX, targetY);
			}
		}
	}

	/**
	 * Casts an instant cast ability on this character
	 * @param ability The ability to cast on the character
	 */
	public void hitWithInstantCastSpell(InstantCastAbility ability) {
		this.damage(ability.getSpell().getDamage(), ability.getCastBy());
		if(ability.getSpell() == Spell.IceSpell) {
			this.freeze();
		}
	}

	/**
	 * Causes this character to perform a melee attack, lunging forward and backwards in quick succession 
	 */
	private void performMeleeAttack() {
		if(lungeForwardPerformed - this.stateTime <= 0) {
			this.lungeForward();	
			this.lungeForwardPerformed = this.stateTime + attackInterval;
			if(this.characterClass == CharacterClass.melee) 
				this.target.damage(25, this);
			else 
				this.target.damage(10, this);
		}
		else if(lungeBackwardPerformed - this.stateTime <= 0) {
			this.lungeBackward();	
			this.lungeBackwardPerformed = this.stateTime + attackInterval;
		}
	}

	/**
	 * Causes the character to perform a melee ability
	 * @param spell The spell to cast
	 */
	private void performMeleeAbility(Spell spell) {

	}

	/**
	 * Causes the character to perform a ranged ability
	 * @param spell The spell to cast
	 */
	private void performRangeAbility(Spell spell, float targetX, float targetY) {
		this.gameWorld.performSpellCast(new Projectile(this.accessibleTiles, this.currentPosition.x, this.currentPosition.y, targetX, targetY, spell, this));
	}

	/**
	 * Causes the character to perform an instant cast ability
	 * @param spell The spell to cast
	 */
	private void performInstantCastAbility(Spell spell, float targetX, float targetY) {
		this.gameWorld.performInstantSpellCast(new Circle(targetX, targetY, spell.getAreaOfAffect()), spell);
	}

	/**
	 * Causes the character to perform a buff ability
	 * @param spell The spell to cast
	 */
	private void performBuffAbility(Spell spell) {
		this.gameWorld.addInstantCastSpell(new InstantCastAbility(spell, this, this));
		this.TemporarilyBuffDamage(spell.getDamage());
		this.TemporarilyBuffDefence(spell.getDamage());
		this.heal((int)(spell.getDamage()*0.25));
		this.timeOfTemporaryBuff = System.currentTimeMillis();
	}

	/**
	 * Causes the character to perform a multi-projectile ability (similar to ranged ability, but fires multiple times in different directions) 
	 * @param spell The spell to cast
	 */
	private void performMultiProjectileAbility(Spell spell, float targetX, float targetY) {
		this.gameWorld.performSpellCast(new Projectile(this.accessibleTiles, this.currentPosition.x, this.currentPosition.y, targetX, targetY, spell, this));
		this.gameWorld.performSpellCast(new Projectile(this.accessibleTiles, this.currentPosition.x, this.currentPosition.y, targetX + 90, targetY + 90, spell, this));
		this.gameWorld.performSpellCast(new Projectile(this.accessibleTiles, this.currentPosition.x, this.currentPosition.y, targetX - 90, targetY - 90, spell, this));
	}

	/**
	 * Causes the character to lunge forward
	 * Used in things such as melee animations
	 */
	private void lungeForward() {    	
		if(currentCharacterDirection == Direction.up) {
			this.currentPosition.y += 20;
		}
		else if(currentCharacterDirection == Direction.down) {
			this.currentPosition.y -= 20;
		}
		else if(currentCharacterDirection == Direction.right) {
			this.currentPosition.x += 20;
		}
		else {
			this.currentPosition.x -= 20;
		}
	}

	/**
	 * Causes the character to lunge backwards
	 * Used in things such as melee animations
	 */
	private void lungeBackward() {

		if(currentCharacterDirection == Direction.up) {
			this.currentPosition.y -= 20;
		}
		else if(currentCharacterDirection == Direction.down) {
			this.currentPosition.y += 20;
		}
		else if(currentCharacterDirection == Direction.right) {
			this.currentPosition.x -= 20;
		}
		else {
			this.currentPosition.x += 20;
		}		
	}

	/**
	 * Inflicts damage on this character
	 * @param amount The amount of damage to inflict
	 */
	private void inflictDamage(int amount) {
		this.health -= amount;
	}

	/**
	 * @param spriteBatch the SpriteBatch object used to render the message to screen
	 * renders the next message in the damage message queue
	 */
	private void displayDamage(SpriteBatch spriteBatch) {
		// check if any messages in queue
		if(damageMessageQueue.size() > 0) {
			// check if a message has been displayed
			if(timeLastDamagedMessageDisplayed == 0) {   			
				timeLastDamagedMessageDisplayed = stateTime;
			}
			// show the message for a duration depending on its length
			if(timeLastDamagedMessageDisplayed < stateTime - 2) {
				// dequeue item - display period expired
				damageMessageQueue.remove(0);
				timeLastDamagedMessageDisplayed = 0;
			}
			// re-check message queue size
			if(damageMessageQueue.size() > 0) {
				BitmapFont font = new BitmapFont(); 

				font.setColor(new Color(Color.WHITE));

				hitSplt.setX(this.getHitBox().x - 60);
				hitSplt.setY(this.getHitBox().y - 50);
				hitSplt.draw(spriteBatch);
				font.draw(spriteBatch, damageMessageQueue.get(0), this.getHitBox().x - 5, this.getHitBox().y + 15);

			}
		}   	
	}

	//================================================================================
	// Character Messaging
	//================================================================================

	/**
	 * Updates the position of the name tag for the character
	 */
	private void updateNamePosition() {
		this.namePosition.x = currentPosition.x - name.length()*7/2;
		this.namePosition.y = currentPosition.y - 10;
	}

	/**
	 * @param spriteBatch the SpriteBatch object used to render the message to screen
	 * renders the next message in the message queue
	 */
	private void displayMessage(SpriteBatch spriteBatch) {
		// check if any messages in queue
		if(messageQueue.size() > 0) {
			// check if a message has been displayed
			if(timeLastMessageDisplayed == 0) {   			
				timeLastMessageDisplayed = stateTime;
			}
			// show the message for a duration depending on its length
			if(timeLastMessageDisplayed < stateTime - messageQueue.get(0).length() * 0.08) {
				// dequeue item - display period expired
				messageQueue.remove(0);
				timeLastMessageDisplayed = 0;
			}
			// re-check message queue size
			if(messageQueue.size() > 0) {
				BitmapFont font = new BitmapFont(); 
				font.draw(spriteBatch, messageQueue.get(0), currentPosition.x - messageQueue.get(0).length()*6/2, currentPosition.y + 80);
			}
		}   	
	}

	/**
	 * Displays the character's name tag
	 * @param spriteBatch The spritebatch used to draw the font
	 */
	private void displayCharacterName(SpriteBatch spriteBatch) {		
		font.draw(spriteBatch, this.name, this.namePosition.x, this.namePosition.y);					  	
	}


	/**
	 * @param spriteBatch the SpriteBatch object used to render the message to screen
	 * renders the next message in the message queue
	 */
	private void displayBuffMessage(SpriteBatch spriteBatch) {
		// check if any messages in queue
		if(buffMessageQueue.size() > 0) {
			// check if a message has been displayed
			if(timeLastBuffMessageDisplayed == 0) {   			
				timeLastBuffMessageDisplayed = stateTime;
			}
			// show the message for a duration depending on its length
			if(timeLastBuffMessageDisplayed < stateTime - 2) {
				// dequeue item - display period expired
				buffMessageQueue.remove(0);
				buffYPosition = 0;
				timeLastBuffMessageDisplayed = 0;
			}
			// re-check message queue size
			if(buffMessageQueue.size() > 0) {
				buffYPosition += 2;
				BitmapFont font = new BitmapFont(); 
				font.setColor(Color.GREEN);
				font.draw(spriteBatch, buffMessageQueue.get(0), currentPosition.x - buffMessageQueue.get(0).length()*6/2, currentPosition.y + buffYPosition);
			}
		}   	
	}



	//================================================================================
	// Health bar
	//================================================================================


	/**
	 * Represents a health bar object 
	 * Used as a display for a character's health.
	 */
	private class HealthBar {
		private Sprite healthBackground;
		private Sprite healthForeground;
		private GameCharacter owner;
		private final short yBuffer = 45;
		private final short xBuffer = -15;

		public HealthBar(GameCharacter owner, Texture healthBG, Texture healthFG) {
			this.owner = owner;

			healthBackground = new Sprite(healthBG);
			healthForeground = new Sprite(healthFG);

			healthBackground.setX(owner.getCurrentPosition().x + xBuffer);
			healthBackground.setY(owner.getCurrentPosition().y + yBuffer);

			healthForeground.setX(owner.getCurrentPosition().x + xBuffer);
			healthForeground.setY(owner.getCurrentPosition().y + yBuffer);
			healthForeground.setOrigin(0, 0);			
		}
		
		/**
		 * Updates the healthbar in terms of things such as location (called every frame)
		 */
		public void update() {
			healthBackground.setX(owner.getCurrentPosition().x + xBuffer);
			healthBackground.setY(owner.getCurrentPosition().y + yBuffer);

			healthForeground.setX(owner.getCurrentPosition().x + xBuffer);
			healthForeground.setY(owner.getCurrentPosition().y + yBuffer);

			float healthValue = owner.health / (float) owner.maxHealth;

			if(healthValue >= 0)
				healthForeground.setScale(healthValue, 1f);
			else
				healthForeground.setScale(0, 1f);

		}

		/**
		 * Renders the healthbar to the screen
		 * @param spriteBatch The spritebatch used to render the healthbar
		 */
		public void render(SpriteBatch spriteBatch) {
			// only show health bars for hostile characters - its used an indicator for the player to know whether this character is "hostile"
			if(isHostile) {
				healthBackground.draw(spriteBatch);
				healthForeground.draw(spriteBatch);
			}
		}
	}
}
