package com.adventuresof.game.world;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.inventory.Item;
import com.adventuresof.game.inventory.ItemEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class GameRenderer {
	
	private ShapeRenderer shapeRendererHUD; // renders shapes on the player's HUD
	private ShapeRenderer shapeRendererGameObjects; // renders shapes on the map

	private OrthographicCamera camera; // the camera (orthographic renders everything on one pane, regardless of distance)
	private SpriteBatch spriteBatch; // the sprite batch to use for the game
	private TutorialIsland gameWorld; // the game world
		
	private boolean showTargetCircle;
	private int targetCircleSize; // the size of the circle
	private float targetCircleX;
	private float targetCircleY;
	
	
	public GameRenderer(TutorialIsland gameWorld) {
		
		//game world
		this.gameWorld = gameWorld;		
		this.spriteBatch = new SpriteBatch();
		
		// get the width/height of the window
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// sort out the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		camera.position.set(gameWorld.getPlayer().getCurrentPosition().x, gameWorld.getPlayer().getCurrentPosition().y, 0f);	 
		
		this.shapeRendererHUD = new ShapeRenderer();
		this.shapeRendererGameObjects = new ShapeRenderer();

	}
	
	public float getTargetCircleX() {
		return targetCircleX;
	}

	public void setTargetCircleX(float targetCircleX) {
		this.targetCircleX = targetCircleX;
	}

	public int getTargetCircleSize() {
		return targetCircleSize;
	}

	public void setTargetCircleSize(int targetCircleSize) {
		this.targetCircleSize = targetCircleSize;
	}

	public float getTargetCircleY() {
		return targetCircleY;
	}

	public void setTargetCircleY(float targetCircleY) {
		this.targetCircleY = targetCircleY;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public boolean isShowTargetCircle() {
		return showTargetCircle;
	}

	public void setShowTargetCircle(boolean showTargetCircle, int size) {
		this.showTargetCircle = showTargetCircle;
		this.targetCircleSize = size;
	}

	
	public void render() {
		// clear graphics to be re-drawn
		Gdx.gl.glClearColor( 1, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
	   
		// set camera
		camera.position.set(gameWorld.getPlayer().getCurrentPosition().x, gameWorld.getPlayer().getCurrentPosition().y, 0f);
		camera.update();
		// render the map
		gameWorld.getMap().getTiledMapRenderer().setView(camera);
		gameWorld.getMap().getTiledMapRenderer().render();
		// create the sprite batch
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();		
		
		// render the items in the game world
		this.renderGameItems();
		
		// render the characters
		gameWorld.getPlayer().render(spriteBatch);
		gameWorld.getPlayerCompanion().render(spriteBatch);
		this.renderNPCs();
		
		// dispose of sprite batch
		spriteBatch.end();	
		
		this.renderSpellAnimations();
		
		// if applicable, render cursor targeting, e.g. spell cast zone
		if(showTargetCircle)
		this.renderTargetingCircle();
	
	}
	
	public void renderTargetingCircle() {	
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    shapeRendererHUD.begin(ShapeType.Filled);
	    shapeRendererHUD.setColor(new Color(0, 0.5f, 0.5f, 0.5f));  
	    shapeRendererHUD.circle(targetCircleX, Gdx.graphics.getHeight() - targetCircleY, targetCircleSize);
	    shapeRendererHUD.end();	
	}
	
	public void setTargetingCircleLocation(float x, float y) {
		this.targetCircleX = x;
		this.targetCircleY = y;
	}
	
	private void renderNPCs() {
		for (NPC npc : this.gameWorld.getNPCs()) {
			npc.render(spriteBatch);
		}
	}
	
	private void renderSpellAnimations() {
		for (NPC npc : this.gameWorld.getNPCs()) {	
			this.shapeRendererGameObjects.setProjectionMatrix(camera.combined);
			npc.renderSpellAnimations(this.shapeRendererGameObjects);
		}
	}
	
	private void renderGameItems() {
		for (Item item : gameWorld.getItems()) {
			Rectangle rectangle = item.getHitbox();
			Texture image = new Texture(Gdx.files.internal(item.getItem().getTextureRegion()));
			spriteBatch.draw(image, rectangle.x, rectangle.y); // draw the item at the spawn location
		}	
	}

	
	public void resize(int width, int height) {
		
	}
	
//	private void renderGameObjects() {
//		// render any map objects 
//		shapeRenderer.setProjectionMatrix(camera.combined);
//		for (MapObject object : gameWorld.getMap().getTiledMap().getLayers().get("collisionLayer").getObjects()) {
//			if(object instanceof RectangleMapObject) {
//				Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
//				shapeRenderer.begin(ShapeType.Filled);
//				shapeRenderer.rect(rectangle.x,  rectangle.y,  rectangle.width,   rectangle.height);
//				shapeRenderer.end();
//			}
//			else if(object instanceof CircleMapObject) {
//				Circle circle = ((CircleMapObject) object).getCircle();
//				shapeRenderer.begin(ShapeType.Filled);
//				shapeRenderer.circle(circle.x,  circle.y,  circle.radius);
//				shapeRenderer.end();
//			}	        	
//		}
//	}	

}
