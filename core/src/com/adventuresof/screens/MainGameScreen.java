package com.adventuresof.screens;

import com.adventuresof.game.world.AdventuresOfGameWorld;
import com.adventuresof.game.world.GameRenderer;
import com.adventuresof.helpers.PlayerController;
import com.adventuresof.helpers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.AdventuresOfGame;

/**
 * The Main Game Screen
 * 
 */
public class MainGameScreen implements Screen {

	protected AdventuresOfGame parent;
	private AdventuresOfGameWorld gameWorld;
	private GameRenderer gameRenderer;

	// for inventory
	private OrthographicCamera hudCamera = null;    
	private InputMultiplexer multiplexer;  
	private PlayerHUD playerHUD;

	public MainGameScreen(AdventuresOfGame game) {	
		this.parent = game;
		this.initiateGame();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		if(!gameWorld.isGameComplete()) {
			if(gameWorld.getPlayer().isDead()) {
				// restart the game
				this.initiateGame();
			}else {
				this.gameWorld.update(delta);
				this.gameRenderer.render();
				this.playerHUD.render(delta);
			}
		}else {
			parent.showEndScreen(System.currentTimeMillis() - this.gameWorld.getPlayer().getStartTime());
		}
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

	private void initiateGame() {
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
		SoundManager.playMusic("audio/music/noMoreMagic.mp3");
	}

}
