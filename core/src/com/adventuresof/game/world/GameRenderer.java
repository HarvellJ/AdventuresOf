package com.adventuresof.game.world;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.helpers.InputController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class GameRenderer {
	
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;

	private SpriteBatch spriteBatch;
	
	private GameWorld gameWorld;
	
	public GameRenderer(GameWorld gameWorld) {
		
		this.gameWorld = gameWorld;
		
		this.spriteBatch = new SpriteBatch();
		
		// object renderer
		shapeRenderer = new ShapeRenderer();
		
		// called when screen first appears
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		camera.position.set(gameWorld.getPlayer().getCurrentPosition().x, gameWorld.getPlayer().getCurrentPosition().y, 0f);


		// object renderer
		shapeRenderer = new ShapeRenderer();
		
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void render() {
		// called every frame 
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.set(gameWorld.getPlayer().getCurrentPosition().x, gameWorld.getPlayer().getCurrentPosition().y, 0f);
		camera.update();
		gameWorld.getMap().getTiledMapRenderer().setView(camera);
		gameWorld.getMap().getTiledMapRenderer().render();

		//this.renderGameObjects();		
		
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		gameWorld.getPlayer().render(spriteBatch);
		gameWorld.getPlayerCompanion().render(spriteBatch);

		//NPCTest.render(spriteBatch);
		
		spriteBatch.end();		
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
