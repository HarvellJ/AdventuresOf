package com.adventuresof.game.character;

import java.util.ArrayList;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 *
 */
public abstract class GameCharacter extends GameObject {
	
	// movement variables
	protected Vector3 pointToMoveTo; // used to move the character to a point
	protected Vector3 currentPosition; // stores current position of character
	protected boolean isStatic; // used to determine if the character is static on the map or whether they move around
    
    //private Sprite sprite;
    private Direction currentCharacterDirection;
    
    // animation stuff
    // movement
    protected Animation<TextureRegion> runRightAnimation;
    protected Animation<TextureRegion> runLeftAnimation;
    protected Animation<TextureRegion> runUpAnimation;
    protected Animation<TextureRegion> runDownAnimation;
    // combat
    protected Animation<TextureRegion> attackDownAnimation;
    protected Animation<TextureRegion> attackUpAnimation;
    protected Animation<TextureRegion> attackLeftAnimation;
    protected Animation<TextureRegion> attackRightAnimation;
    // idle
    protected Animation<TextureRegion> idleUpAnimation;
    protected Animation<TextureRegion> idleDownAnimation;
    protected Animation<TextureRegion> idleRightAnimation;
    protected Animation<TextureRegion> idleLeftAnimation;
    
    private boolean performAttack = false;
    private float attackStartedTime; // stores the state time of when the attack animation was initiated
    private Direction attackDirection; // locks the player in their current direction for the duration of the attack duration (avoids animation complications)
    
    private boolean isIdle;
    protected String animationSheetName;
    protected int frameCols, frameRows;
    protected float stateTime;
  
    private ArrayList<String> messageQueue; // used to queue messages that will be displayed in game by the character
    private float timeLastMessageDisplayed;
    
	protected GameCharacter target; // the characters current target
    
    // collision stuff
    private Rectangle boundingRectangle; // a mesh used to detect collisions with the character
    private TiledMapTileLayer accessibleTiles; // represents the tiles that are accessible by the character
    
    public GameCharacter(TiledMapTileLayer accessibleTiles, String animationSheetName, int animationSheetCols, int animationSheetRows, float startX, float startY) {
    	
    	// set all values that are used for animations
    	this.animationSheetName = animationSheetName;
    	this.frameCols = animationSheetCols;
    	this.frameRows = animationSheetRows;
		this.stateTime = 0f;
    	this.createAnimations();
    	
    	// create objects required for collision logic
    	this.boundingRectangle = new Rectangle(); 	
    	this.accessibleTiles = accessibleTiles;

    	// instantiate characters' current position as a blank vector3
    	currentPosition = new Vector3(startX, startY, 0);
    	 	
    	// Instantiate the message queue
    	this.messageQueue = new ArrayList<String>();
    	
    	// Default the character to idle state (prevents them running on the spot)
    	isIdle = true;
    	
    	
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

	public Rectangle getBoundingRectangle() {
		return boundingRectangle;
	}
	
    public boolean isPerformAttack() {
		return performAttack;
	}

	public void setPerformAttack(boolean performAttack) {
		this.performAttack = performAttack;
	}

	public void setBoundingRectangle(Rectangle boundingRectangle) {
		this.boundingRectangle = boundingRectangle;
	}
		
    protected abstract void createAnimations();

	public void addMessageToMessageQueue(String message) {
		this.messageQueue.add(message);
	}
	
    /* (non-Javadoc)
     * @see com.adventuresof.game.character.GameObject#update()
     */
    public void update() {    	
		stateTime += Gdx.graphics.getDeltaTime(); // increment state time
    	
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

    		double nextX = destinationX * 3;
    		double nextY = destinationY * 3;

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
    			if(accessibleTiles.getCell(((int) currentPosition.x + (int) nextX) / 32, ((int) currentPosition.y + (int) nextY) / 32) == null) {
    				pointToMoveTo = null;
        			isIdle = true;
    			}else {
    				currentPosition.x = currentPosition.x + (float) nextX;
        			currentPosition.y = currentPosition.y + (float) nextY;   
        			isIdle = false;
    			}    			
    		} 		
    	}
    	//following any positional moves, update the characters bounding record
    	this.boundingRectangle.setPosition(currentPosition.x, currentPosition.y);
    }
    
    /**
     * Renders the up-to-date character to the screen
     * Called every screen following the call to "update"
     */
    public void render(SpriteBatch spriteBatch) {
    	// get the relevant animation frame (based on current character direction)
    	TextureRegion currentAnimationFrame;
    	if(this.isIdle) {
    		currentAnimationFrame = this.getRelevantIdleTexture();
    	}
    	else {
    		currentAnimationFrame = this.getRelevantDirectionAnimationFrame();
    	}

    	// Draw the character frame at the current position
    	spriteBatch.draw(currentAnimationFrame, currentPosition.x - 40, currentPosition.y); // Draw current frame (-40 is used because character sprite is a box, and without the -40 the corner of the box would move to the desired location and not the character itself)
    	
    	// handle any text
    	this.displayMessage(spriteBatch);
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
    

    /**
     * gets the next animation frame for the direction the character is facing
     * @return
     */
    private TextureRegion getRelevantDirectionAnimationFrame() {
    	// check whether to perform combat animation
    	if (performAttack) {
    		if (this.attackStartedTime == 0) {
    			this.attackStartedTime = stateTime;		  
    			this.attackDirection = currentCharacterDirection;
    		}

    		if (attackStartedTime > stateTime -0.25f) // > stateTime - 0.25f is just the attack animation length{
    			if(attackDirection == Direction.up) {
    				return attackUpAnimation.getKeyFrame(stateTime, true);
    			}
    			else if(attackDirection == Direction.down) {
    				return attackDownAnimation.getKeyFrame(stateTime, true);
    			}
    			else if(attackDirection == Direction.right) {
    				return attackRightAnimation.getKeyFrame(stateTime, true);
    			}
    			else {
    				return attackLeftAnimation.getKeyFrame(stateTime, true);
    			}
    	}
    	else {
    		performAttack = false;
    		attackStartedTime = 0;
    	}

    	// if we reach here, player not performing attack, return standard movement animation frame
    	if(currentCharacterDirection == Direction.up) {
    		return runUpAnimation.getKeyFrame(stateTime, true);
    	}
    	else if(currentCharacterDirection == Direction.down) {
    		return runDownAnimation.getKeyFrame(stateTime, true);
    	}
    	else if(currentCharacterDirection == Direction.right) {
    		return runRightAnimation.getKeyFrame(stateTime, true);
    	}
    	else {
    		return runLeftAnimation.getKeyFrame(stateTime, true);
    	}
    }   

    
	/**
     * Simply returns the relevant texture for when the character is idle, based on their current direction
     * @return The character texture for the current direction
     */
    private TextureRegion getRelevantIdleTexture() {
    	if(currentCharacterDirection == Direction.up) {
    		return idleUpAnimation.getKeyFrame(stateTime, true);
    	}
    	else if(currentCharacterDirection == Direction.down) {
    		return idleDownAnimation.getKeyFrame(stateTime, true);
    	}
    	else if(currentCharacterDirection == Direction.right) {
    		return idleRightAnimation.getKeyFrame(stateTime, true);
    	}
    	else {
    		return idleLeftAnimation.getKeyFrame(stateTime, true);
    	}
    }
    
    /**
     * Calculates the character's current direction based on movement
     */
    private void calculateCharacterDirection() {
    	double xDistanceToTravel;
    	double yDistanceToTravel;
    	Direction directionX;
    	Direction directionY;

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
        	font.draw(spriteBatch, messageQueue.get(0), currentPosition.x - messageQueue.get(0).length()*6/2, currentPosition.y + 100);
    		}
    	}   	
    }
}
