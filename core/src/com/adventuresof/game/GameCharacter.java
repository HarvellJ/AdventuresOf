package com.adventuresof.game;

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class GameCharacter extends GameObject {
	
	protected Vector3 pointToMoveTo;
	protected Vector3 currentPosition;
    
    //private Sprite sprite;
    private enum Direction { up, down, left, right};
    private Direction currentCharacterOrientation;
    
    // the different character orientations
	private Texture textureBack;
    private Texture textureFront;
    private Texture textureFacingLeft;
    private Texture textureFacingRight;
    
    // animation stuff
    private Animation<TextureRegion> runRightAnimation;
    private Animation<TextureRegion> runLeftAnimation;
    private Animation<TextureRegion> runUpAnimation;
    private Animation<TextureRegion> runDownAnimation;
    private Animation<TextureRegion> attackAnimation;
    private boolean isIdle;
    
    private static final String animationSheetName = "animation_sheet.png";
    private static final int FRAME_COLS = 8, FRAME_ROWS = 5;
    private float stateTime;

    public GameCharacter() {
    	// create the sprite textures
    	this.initiateCharacterTextures();

    	currentPosition = new Vector3();
    	
		stateTime = 0f;
    	this.createAnimations();
    }

  
    public void update() {
    	
    	if(pointToMoveTo != null) {
    		// first, work out the direction in which the character should be facing
			this.calculateCharacterDirection();    			

    		double destinationX = pointToMoveTo.x - currentPosition.x;
    		double destinationY = pointToMoveTo.y - currentPosition.y;

    		double distanceToTravel = Math.sqrt(destinationX * destinationX + destinationY * destinationY);
    		destinationX = destinationX / distanceToTravel;
    		destinationY = destinationY / distanceToTravel;

    		double nextX = destinationX * 3;
    		double nextY = destinationY * 3;

    		double distanceTravelled = Math.sqrt(nextX * nextX + nextY * nextY);

    		if ( distanceTravelled > distanceToTravel )
    		{
    			pointToMoveTo = null;
    			isIdle = true;
    		}
    		else
    		{
    			currentPosition.x = currentPosition.x + (float) nextX;
    			currentPosition.y = currentPosition.y + (float) nextY;   
    			isIdle = false;
    		} 		
    	}
    }
    
    /**
     * Renders the up-to-date character to the screen
     */
    public void render(SpriteBatch spriteBatch) {
    	TextureRegion currentFrame;
    	if(this.isIdle) {
    		currentFrame = new TextureRegion(this.getRelevantIdleTexture());
    	}
    	else {
    		stateTime += Gdx.graphics.getDeltaTime();
        	currentFrame = this.getRelevantDirectionAnimationFrame();
    	}

    	// Draw the frame at the current position
    	spriteBatch.draw(currentFrame, currentPosition.x, currentPosition.y); // Draw current frame 
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
    	if(direction == Direction.up) {
    		// render character sprites up
    		currentCharacterOrientation = Direction.up;
    	}
    	else if(direction == Direction.down) {
    		// render character sprites down
    		currentCharacterOrientation = Direction.down;
    	}
    	else if(direction == Direction.right) {
    		// render character sprites right
    		currentCharacterOrientation = Direction.right;
    	}
    	else if(direction == Direction.left) {
    		// render character sprites left
    		currentCharacterOrientation = Direction.left;
    	}
    }
    
    private void createAnimations() {
    	this.runRightAnimation = AnimationFactory.createAnimation(animationSheetName, FRAME_COLS, FRAME_ROWS, 5, 15, 0.08f);
    	this.runLeftAnimation = AnimationFactory.createAnimation(animationSheetName, FRAME_COLS, FRAME_ROWS, 5, 21, 0.08f);
    	this.runDownAnimation = AnimationFactory.createAnimation(animationSheetName, FRAME_COLS, FRAME_ROWS, 4, 5, 0.08f);
    	this.runUpAnimation = AnimationFactory.createAnimation(animationSheetName, FRAME_COLS, FRAME_ROWS, 4, 10, 0.08f);
   
    	//this.createAttackAnimation();
    }    
     
    
    /**
     * gets the next animation frame for the direction the character is facing
     * @return
     */
    private TextureRegion getRelevantDirectionAnimationFrame() {
    	if(currentCharacterOrientation == Direction.up) {
        	return runUpAnimation.getKeyFrame(stateTime, true);
    	}
    	else if(currentCharacterOrientation == Direction.down) {
        	return runDownAnimation.getKeyFrame(stateTime, true);
    	}
    	else if(currentCharacterOrientation == Direction.right) {
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
    private Texture getRelevantIdleTexture() {
    	if(currentCharacterOrientation == Direction.up) {
        	return textureBack;
    	}
    	else if(currentCharacterOrientation == Direction.left) {
    		return textureFacingLeft;
    	}
    	else if(currentCharacterOrientation == Direction.right) {
        	return textureFacingRight;
    	}
    	else {
    		return textureFront;      
    	}
    }
    
    
    /**
     * Instantiates the character (idle) textures
     */
    private void initiateCharacterTextures() {
    	textureBack = new Texture(Gdx.files.internal("knight_back.png"));
    	textureFront = new Texture(Gdx.files.internal("knight_front.png"));
    	textureFacingLeft = new Texture(Gdx.files.internal("knight_face_left.png"));
    	textureFacingRight = new Texture(Gdx.files.internal("knight_face_right.png"));  	
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

    	if(xDistanceToTravel > yDistanceToTravel ) {
    		this.setCharacterDirection(directionX);

    	}else {
    		this.setCharacterDirection(directionY);
    	}   		
    }
}
