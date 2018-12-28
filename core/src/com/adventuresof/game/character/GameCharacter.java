package com.adventuresof.game.character;

import java.util.ArrayList;
import java.util.Random;

import com.adventuresof.game.animation.CharacterAnimation;
import com.adventuresof.game.common.GameObject;
import com.adventuresof.game.common.MoveableObject;
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
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Vector3;

/**
 *
 */
public abstract class GameCharacter extends MoveableObject {
	
	private String name; // characters name
	protected CharacterClass characterClass; // used to determine the abilities available to this character
	
	// movement variables
	protected boolean isStatic; // used to determine if the character is static on the map or whether they move around

	protected boolean canRespawn;
	private Direction currentCharacterDirection;

	// death stuff
	private boolean isDying;
	private float stateTimeOfDeath; // used to calculate time required for death animation

	// animation stuff
	protected CharacterAnimation characterAnimation;

	private boolean isIdle;

	private ArrayList<String> messageQueue; // used to queue messages that will be displayed in game by the character
	private float timeLastMessageDisplayed;
	private ArrayList<String> damageMessageQueue;
	private float timeLastDamagedMessageDisplayed;

	protected GameCharacter target; // the characters current target. This can be friendly or hostile
	protected boolean isHostile; // toggles whether the character can be attacked or is passive

	// collision stuff
	private TiledMapTileLayer accessibleTiles; // represents the tiles that are accessible by the character
	protected int characterHeight; // the character's height - used to draw the bounding rectangle (hit box)
	protected int characterWidth; // the character's width - used to draw the bounding rectangle (hit box)

	// combat stuff
	private int health;
	private int maxHealth = 100;
	private boolean isFrozen;
	private float frozenTime;

	private float attackInterval = 2; // a time interval representing the attack speed of this character - defaults to 2
	private float lungeForwardPerformed;
	private float lungeBackwardPerformed;

	Sprite hitSplt; 

	//health bar
	private HealthBar healthBar;

	public GameCharacter(
			TiledMapTileLayer accessibleTiles,
			float startX, float startY,
			boolean isHostile,
			int characterWidth, int characterHeight,
			CharacterAnimation characterAnimation,
			float speed, boolean canRespawn, String name, CharacterClass characterClass) {

		super(startX, startY);
		
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
		// Default the character to idle state (prevents them running on the spot)
		this.isIdle = true;

		// set the target's hostility
		this.isHostile = isHostile;

		// default health to 100
		this.health = 100;
		this.isFrozen = false;
		this.frozenTime = 0;


		healthBar = new HealthBar(this, new Texture("healthBackground.png"),
				new Texture("healthForeground.png"));
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

	protected void createAnimations() {

	}

	public void addMessageToMessageQueue(String message) {
		this.messageQueue.add(message);
	}

	public void addMessageToDamageQueue(String message) {
		this.damageMessageQueue.add(message);
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
	
	public void update() {    	
		stateTime += Gdx.graphics.getDeltaTime(); // increment state time

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
							// target is within distance, attack
							this.performAttack();
						}else {
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
			}
		}

		healthBar.update();
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

		healthBar.render(spriteBatch);
	}

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
	
	public void renderAdditionalAnimations(ShapeRenderer shapeRenderer) {
		// see if character is frozen (if so draw transparent shape to indicate ice block)
		if(isFrozen) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(new Color(1, 1f, 1f, 0.5f));				
			shapeRenderer.rect(this.getHitBox().x - this.getHitBox().width/2, this.getHitBox().y - 20, this.getHitBox().width, this.getHitBox().height + 50);
			shapeRenderer.end();	
		}
	}

	/**
	 * Sets the target location to a given vector3 value
	 * @param co-ordinates for the to which to move the player
	 */
	public void setTargetLocation(Vector3 pointToMoveTo) {
		this.pointToMoveTo = pointToMoveTo;
	}

	/**
	 * Set the character direction
	 * @param direction
	 */
	public void setCharacterDirection(Direction direction) {
		currentCharacterDirection = direction;
	}

	public int generateRandomDamageAmount() {
		// TODO - Make this damage based on some sort of input parameters. Right now its just randomly choosing between 0 and 100.
		Random r = new Random();
		return r.nextInt(30);
	}

	public void freeze() {
		if(this.isHostile) {
			this.isFrozen = true;
			this.frozenTime = stateTime;
		}	
	}

	public void damage(int damage) {
		if(this.isHostile) {
			this.inflictDamage(damage);
			this.addMessageToDamageQueue(Integer.toString(damage));
			if(this.health <= 0) {
				this.isDying = true;
				this.stateTimeOfDeath = this.stateTime;
			}
		}
	}

	public void updateHitBox() {
		this.getHitBox().set(this.currentPosition.x, this.currentPosition.y, this.characterHeight, this.characterWidth);
	}

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

	private void inflictDamage(int amount) {
		this.health -= amount;
	}
	
	/**
	 * Calculates the character's current direction based on movement
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

	private void performAttack() {
		if(lungeForwardPerformed - this.stateTime <= 0) {
			this.lungeForward();	
			this.lungeForwardPerformed = this.stateTime + attackInterval;
			this.target.damage(10);
		}
		else if(lungeBackwardPerformed - this.stateTime <= 0) {
			this.lungeBackward();	
			this.lungeBackwardPerformed = this.stateTime + attackInterval;
		}
	}

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
				font.draw(spriteBatch, messageQueue.get(0), currentPosition.x - messageQueue.get(0).length()*6/2, currentPosition.y + 60);
			}
		}   	
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

		public void render(SpriteBatch spriteBatch) {
			// only show health bars for hostile characters - its used an indicator for the player to know whether this character is "hostile"
			if(isHostile) {
				healthBackground.draw(spriteBatch);
				healthForeground.draw(spriteBatch);
			}
		}
	}
}
