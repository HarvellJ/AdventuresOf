package com.adventuresof.screens;

import com.adventuresof.helpers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.AdventuresOfGame;

public class MainMenuScreen implements Screen{

	protected AdventuresOfGame parent;
	public Stage stage;
	private Skin skin;
	private TextButton newGame;
	private Label titleLabel;
	private TextButton settings;
	private TextButton exit;
	
	public MainMenuScreen()
	{
		
	}
	
	public MainMenuScreen(AdventuresOfGame game) {
		parent = game;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skins/star-soldier-ui.json"));
		titleLabel = new Label("The Adventures Of ...", skin);
		newGame = new TextButton("New Game", skin);
		settings = new TextButton("Settings", skin);
		exit = new TextButton("Exit", skin);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Gdx.input.setInputProcessor(stage);
		addListeners();
		
		// Music setup
		SoundManager.playMusic("audio/music/Random_Battle.mp3");
	}
	
	@Override
	public void show() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.add(titleLabel);
		table.row().pad(30,0,0,10);
		table.add(newGame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(settings).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX(); 
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {		
		stage.getViewport().update(width, height, true);
	}
	
	public void addListeners() {
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(ScreenType.NEWGAMESCREEN);
			}
		});
		
		settings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(ScreenType.SETTINGS);
			}
		});
		
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				SoundManager.dispose();
				Gdx.app.exit();				
			}
		});
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
		stage.dispose();
	}

}
