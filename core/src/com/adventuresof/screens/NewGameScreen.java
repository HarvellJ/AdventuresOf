package com.adventuresof.screens;

import com.adventuresof.game.world.GameProgress;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.AdventuresOfGame;

/**
 * The New Game Screen
 * 
 */
public class NewGameScreen implements Screen {

	public Stage stage;
	private AdventuresOfGame _game;
	private TextField _profileText;
	private Skin skin;
	private Dialog _overwriteDialog;
	protected AdventuresOfGame parent;
	
	private TextButton startButton;
	private Label profileName;
	private TextButton cancelButton;
	private TextButton backButton;
	
	public NewGameScreen(AdventuresOfGame game){
		parent = game;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skins/star-soldier-ui.json"));
		profileName = new Label("Enter Profile Name: ", skin);
		startButton = new TextButton("Start", skin);
		backButton = new TextButton("Back", skin);
		cancelButton = new TextButton("Cancel", skin);
		
		_profileText  = new TextField("",skin);
		_profileText.setMaxLength(20);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Gdx.input.setInputProcessor(stage);
		addListeners();
		
	}
	
	public void show() {
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		table.add(profileName).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(_profileText).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(startButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(backButton).fillX().uniformX();

		stage.addActor(table);
	
	}
		
		public void addListeners() {
	//Listeners
			cancelButton.addListener(new ClickListener() {

										 @Override
										 public boolean touchDown(InputEvent event, float x, float y, int pointer, int button ){
											 return true;
										 }

										 @Override
										 public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
											 _overwriteDialog.hide();
										 }
									 }
			);

			startButton.addListener(new ClickListener() {

										@Override
										public boolean touchDown(InputEvent event, float x, float y, int pointer, int button ){
											return true;
										}

										@Override
										public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
												   GameProgress.characterName = _profileText.getText();
												   parent.changeScreen(ScreenType.CHARACTERSELECTION);
										}
										});
			

			backButton.addListener(new ClickListener() {

									   @Override
									   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
										   return true;
									   }

									   @Override
									   public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
										   parent.changeScreen(ScreenType.MAINMENU);
									   }
								   }
			);

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

		@Override
		public void hide() {
			_profileText.setText("");
		}

		@Override
		public void pause() {
		}

		@Override
		public void resume() {
		}

		@Override
		public void dispose() {
			stage.clear();
			stage.dispose();
		}
		
	
}
