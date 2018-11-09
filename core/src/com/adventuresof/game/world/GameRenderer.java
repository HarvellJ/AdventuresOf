package com.adventuresof.game.world;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.inventory.Item;
import com.adventuresof.game.inventory.ItemEnum;
import com.badlogic.gdx.Gdx;
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
	
	private ShapeRenderer shapeRenderer; // renders shapes on the map
	
	private OrthographicCamera camera; // the camera (orthographic renders everything on one pane, regardless of distance)
	private SpriteBatch spriteBatch; // the sprite batch to use for the game
	private TutorialIsland gameWorld; // the game world
		
	public GameRenderer(TutorialIsland gameWorld) {
		
		//game world
		this.gameWorld = gameWorld;		
		this.spriteBatch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();
		
		// get the width/height of the window
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// sort out the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		camera.position.set(gameWorld.getPlayer().getCurrentPosition().x, gameWorld.getPlayer().getCurrentPosition().y, 0f);	 
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void render() {
		// clear graphics to be re-drawn
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
	}
	
	private void renderNPCs() {
		for (NPC npc : this.gameWorld.getNPCs()) {
			npc.render(spriteBatch);
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
	
	private void renderGameObjects() {
		// render any map objects 
		shapeRenderer.setProjectionMatrix(camera.combined);
		for (MapObject object : gameWorld.getMap().getTiledMap().getLayers().get("collisionLayer").getObjects()) {
			if(object instanceof RectangleMapObject) {
				Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.rect(rectangle.x,  rectangle.y,  rectangle.width,   rectangle.height);
				shapeRenderer.end();
			}
			else if(object instanceof CircleMapObject) {
				Circle circle = ((CircleMapObject) object).getCircle();
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.circle(circle.x,  circle.y,  circle.radius);
				shapeRenderer.end();
			}	        	
		}
	}

}
