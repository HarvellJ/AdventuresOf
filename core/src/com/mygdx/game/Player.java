package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.screens.MainGameScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private Vector3 pointToMoveTo;
    private Vector3 currentPosition;
    
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

    
    private Texture animationSheet;
    private float stateTime;
    private static final int FRAME_COLS = 8, FRAME_ROWS = 5;
    
    public Player() {
    	// create the sprite textures
    	this.initiateCharacterTextures();

    	currentPosition = new Vector3();
    	
    	this.createAnimations();
    }

    private void createAnimations() {
    	this.createRunRightAnimation();
    	this.createRunLeftAnimation();
    	this.createRunUpAnimation();
    	this.createRunDownAnimation();
    	//this.createAttackAnimation();
    }
    
    private void createRunRightAnimation() {
		stateTime = 0f;
		//how many images to take  from sprite sheet
		int runningImages = 5;
		
    	animationSheet = new Texture(Gdx.files.internal("animation_sheet.png"));

		// Use the split utility method to create a 2D array of TextureRegions. This is 
		// possible because this sprite sheet contains frames of equal size and they are 
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, 
				animationSheet.getWidth() / FRAME_COLS,
				animationSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top 
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] animationFrames = new TextureRegion[runningImages];
		
		int index = 0;
		//image count is used to get only the required images from the sprite sheet
		int currentLoopCount = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				if(currentLoopCount >= 15 && currentLoopCount < 15 + runningImages) {					
						animationFrames[index++] = tmp[i][j];				
				}
			
				currentLoopCount++;
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		runRightAnimation = new Animation<TextureRegion>(0.08f, animationFrames);

		stateTime = 0f;
    }
    
    private void createRunLeftAnimation() {
		stateTime = 0f;
		//how many images to take  from sprite sheet
		int runningImages = 5;
		
    	animationSheet = new Texture(Gdx.files.internal("animation_sheet.png"));

		TextureRegion[][] tmp = TextureRegion.split(animationSheet, 
				animationSheet.getWidth() / FRAME_COLS,
				animationSheet.getHeight() / FRAME_ROWS);

	
		TextureRegion[] animationFrames = new TextureRegion[runningImages];
		
		int index = 0;
		//image count is used to get only the required images from the sprite sheet
		int currentLoopCount = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				if(currentLoopCount >= 21 && currentLoopCount < 21 + runningImages) {					
						animationFrames[index++] = tmp[i][j];				
				}
			
				currentLoopCount++;
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		runLeftAnimation = new Animation<TextureRegion>(0.08f, animationFrames);

		stateTime = 0f;
    }
    
    
    private void createRunDownAnimation() {
		stateTime = 0f;
		//how many images to take  from sprite sheet
		int runningImages = 4;
		
    	animationSheet = new Texture(Gdx.files.internal("animation_sheet.png"));

		TextureRegion[][] tmp = TextureRegion.split(animationSheet, 
				animationSheet.getWidth() / FRAME_COLS,
				animationSheet.getHeight() / FRAME_ROWS);

		TextureRegion[] animationFrames = new TextureRegion[runningImages];
		
		int index = 0;
		//image count is used to get only the required images from the sprite sheet
		int currentLoopCount = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				if(currentLoopCount >= 5 && currentLoopCount < 5 + runningImages) {					
						animationFrames[index++] = tmp[i][j];				
				}
			
				currentLoopCount++;
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		runDownAnimation = new Animation<TextureRegion>(0.08f, animationFrames);

		stateTime = 0f;
    }
    
    private void createRunUpAnimation() {
  		stateTime = 0f;
  		//how many images to take from sprite sheet
  		int runningImages = 4;
  		
      	animationSheet = new Texture(Gdx.files.internal("animation_sheet.png"));
		
  		TextureRegion[][] tmp = TextureRegion.split(animationSheet, 
  				animationSheet.getWidth() / FRAME_COLS,
  				animationSheet.getHeight() / FRAME_ROWS);
 		
  		TextureRegion[] animationFrames = new TextureRegion[runningImages];
  		
  		int index = 0;
  		//image count is used to get only the required images from the sprite sheet
  		int currentLoopCount = 0;
  		for (int i = 0; i < FRAME_ROWS; i++) {
  			for (int j = 0; j < FRAME_COLS; j++) {
  				if(currentLoopCount >= 10 && currentLoopCount < 10 + runningImages) {					
  						animationFrames[index++] = tmp[i][j];				
  				}
  			
  				currentLoopCount++;
  			}
  		}

  		// Initialize the Animation with the frame interval and array of frames
  		runUpAnimation = new Animation<TextureRegion>(0.08f, animationFrames);

  		stateTime = 0f;
      }
     
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
    
    public void update(SpriteBatch spriteBatch) {
    	TextureRegion currentFrame = new TextureRegion(this.getRelevantIdleTexture());
    	
    	if(pointToMoveTo != null) {
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
    		}
    		else
    		{
    			currentPosition.x = currentPosition.x + (float) nextX;
    			currentPosition.y = currentPosition.y + (float) nextY;   			
    			this.calculateSpriteDirection();    			
    		} 		
    		stateTime += Gdx.graphics.getDeltaTime();
    		// Get current frame of animation for the current stateTime
    	    currentFrame = this.getRelevantDirectionAnimationFrame();
    		spriteBatch.draw(currentFrame, currentPosition.x, currentPosition.y); // Draw current frame at (50, 50)
    	}   	
    	spriteBatch.draw(currentFrame, currentPosition.x, currentPosition.y); // Draw current frame at (50, 50)
    }
    
    public void setTargetLocation(Vector3 pointToMoveTo) {
        this.pointToMoveTo = pointToMoveTo;
    }
    
    public void setSpriteDirection(Direction direction) {
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
    
    private void initiateCharacterTextures() {
    	textureBack = new Texture(Gdx.files.internal("knight_back.png"));
    	textureFront = new Texture(Gdx.files.internal("knight_front.png"));
    	textureFacingLeft = new Texture(Gdx.files.internal("knight_face_left.png"));
    	textureFacingRight = new Texture(Gdx.files.internal("knight_face_right.png"));  	
    }
    
    private void calculateSpriteDirection() {

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
    		this.setSpriteDirection(directionX);

    	}else {
    		this.setSpriteDirection(directionY);
    	}   		
    }
}

