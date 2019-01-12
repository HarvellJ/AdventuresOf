package com.adventuresof.screens;

import com.adventuresof.game.world.GameRenderer;
import com.adventuresof.game.world.TutorialIsland;
import com.adventuresof.helpers.PlayerController;
import com.adventuresof.helpers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

//import javafx.util.Pair;

public class MainGameScreen implements Screen {

	private TutorialIsland gameWorld;
	private GameRenderer gameRenderer;
	
	// for inventory
	private OrthographicCamera hudCamera = null;    
    private InputMultiplexer multiplexer;  
	private PlayerHUD playerHUD;
	
	public MainGameScreen() {		
		this.gameWorld = new TutorialIsland();
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
