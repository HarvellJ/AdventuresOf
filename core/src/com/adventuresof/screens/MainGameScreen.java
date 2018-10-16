package com.adventuresof.screens;

import com.adventuresof.game.GameCharacter;
import com.adventuresof.game.NPC;
import com.adventuresof.game.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AdventuresOfGame;

import javafx.util.Pair;

public class MainGameScreen  implements Screen, InputProcessor {

	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	ShapeRenderer shapeRenderer;

	AdventuresOfGame game; 
	Player player;
	NPC NPCTest;
	
	// the object layer ID
	int objectLayerId = 1;

	public MainGameScreen(AdventuresOfGame game) {
		this.game = game;		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);

		// called when screen first appears
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("TestMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		// object renderer
		shapeRenderer = new ShapeRenderer();
		this.player = new Player();
		this.NPCTest = new NPC();
	}

	@Override
	public void render(float delta) {
		// called every frame 
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		this.renderGameObjects();		
		
		game.spriteBatch.setProjectionMatrix(camera.combined);
		game.spriteBatch.begin();
		player.update();
		player.render(game.spriteBatch);
		NPCTest.move();
		NPCTest.update();
		NPCTest.render(game.spriteBatch);
		game.spriteBatch.end();
		
	}		

	private void renderGameObjects() {
		// render any map objects 
				shapeRenderer.setProjectionMatrix(camera.combined);
				for (MapObject object : tiledMap.getLayers().get("collisionLayer").getObjects()) {
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
	
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.LEFT)
			camera.translate(-45,0);
		if(keycode == Input.Keys.RIGHT)
			camera.translate(45, 0);
		if(keycode == Input.Keys.UP)
			camera.translate(0,45);
		if(keycode == Input.Keys.DOWN)
			camera.translate(0,-45);
		if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
		Vector3 newPosition = camera.unproject(clickCoordinates);
		this.player.setTargetLocation(newPosition);
		return false;
	}

	
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
