package com.adventuresof.screens;

import com.adventuresof.game.character.GameCharacter;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.world.GameRenderer;
import com.adventuresof.game.world.AdventuresOfGameWorld;
import com.adventuresof.helpers.PlayerController;
import com.adventuresof.helpers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AdventuresOfGame;

//import javafx.util.Pair;

public class MainGameScreen implements Screen {

	private AdventuresOfGameWorld gameWorld;
	private GameRenderer gameRenderer;
	
	// for inventory
	private OrthographicCamera hudCamera = null;    
    private InputMultiplexer multiplexer;  
	private PlayerHUD playerHUD;
	
	public MainGameScreen() {		
		this.gameWorld = new AdventuresOfGameWorld();
		this.gameRenderer = new GameRenderer(gameWorld);
		
		
		// setup camera
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		playerHUD = new PlayerHUD(hudCamera, gameWorld.getPlayer());
		
		// handle inputs - multiplexer used so HUD and game world can both respond to inputs
		multiplexer = new InputMultiplexer(); 
		multiplexer.addProcessor(playerHUD.stage);
		multiplexer.addProcessor(new PlayerController(gameWorld, gameRenderer, playerHUD)); 
		Gdx.input.setInputProcessor(multiplexer); 
		// Music setup
		SoundManager.playMusic("audio/music/Takeover.mp3");
	}

	@Override
	public void show() {
	
	}

	@Override
	public void render(float delta) {
		this.gameWorld.update(delta);
		this.gameRenderer.render();
		this.playerHUD.render(delta);
	}		
	
	@Override
	public void resize(int width, int height) {
		this.gameRenderer.resize(width, height);
		
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
		SoundManager.dispose();

	}
	
	public PlayerHUD getPlayerHUD () {
		return this.playerHUD;
	}

}
