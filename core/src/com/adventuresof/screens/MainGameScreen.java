package com.adventuresof.screens;

import com.adventuresof.game.character.GameCharacter;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.world.GameRenderer;
import com.adventuresof.game.world.GameWorld;
import com.adventuresof.helpers.InputController;
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
import com.mygdx.UI.PlayerHUD;
import com.mygdx.game.AdventuresOfGame;

import javafx.util.Pair;

public class MainGameScreen  implements Screen {

	private GameWorld gameWorld;
	private GameRenderer gameRenderer;
	
	 private OrthographicCamera _hudCamera = null; 
	 private InputMultiplexer _multiplexer;  
	 private PlayerHUD _playerHUD;
	
	public MainGameScreen() {		
		this.gameWorld = new GameWorld();
		this.gameRenderer = new GameRenderer(gameWorld);
		
		 _hudCamera = new OrthographicCamera();  
		 _hudCamera.setToOrtho(false,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		 _playerHUD = new PlayerHUD(_hudCamera);
		 _multiplexer = new InputMultiplexer();  
		 _multiplexer.addProcessor(_playerHUD.getStage());  
		 _multiplexer.addProcessor(new InputController(gameWorld, gameRenderer)); 
		 Gdx.input.setInputProcessor(_multiplexer); 
		 //Gdx.input.setInputProcessor(new InputController(gameWorld, gameRenderer));
	}

	@Override
	public void show() {
	
	}

	@Override
	public void render(float delta) {
		this.gameWorld.update();
		this.gameRenderer.render();
		this._playerHUD.render(delta);
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

}
