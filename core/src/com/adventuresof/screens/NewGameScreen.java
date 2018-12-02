package com.adventuresof.screens;

import com.adventuresof.game.profile.ProfileManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class NewGameScreen extends MainGameScreen implements Screen {

	public Stage stage;
	private AdventuresOfGame _game;
	private TextField _profileText;
	private Skin skin;
	private Dialog _overwriteDialog;
	protected AdventuresOfGame parent;

	private TextButton startButton;
	private Label profileName;
	private Label overwriteLabel;
	private TextButton cancelButton;
	private TextButton overwriteButton;
	private TextButton backButton;

	public NewGameScreen(AdventuresOfGame game) {
		parent = game;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skins/star-soldier-ui.json"));
		profileName = new Label("Enter Profile Name: ", skin);
		startButton = new TextButton("Start", skin);
		backButton = new TextButton("Back", skin);

		_profileText = new TextField("", skin);
		_profileText.setMaxLength(20);

		_overwriteDialog = new Dialog("Overwrite?", skin);
		overwriteLabel = new Label("Overwrite existing profile name?", skin);
		cancelButton = new TextButton("Cancel", skin);

		overwriteButton = new TextButton("Overwrite", skin);
		_overwriteDialog.setKeepWithinStage(true);
		_overwriteDialog.setModal(true);
		_overwriteDialog.setMovable(false);
		_overwriteDialog.text(overwriteLabel);

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
		// Listeners
		cancelButton.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				_overwriteDialog.hide();
			}
		});

		overwriteButton.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				String messageText = _profileText.getText();
				ProfileManager.getInstance().writeProfileToStorage(messageText, "", true);
				ProfileManager.getInstance().setCurrentProfile(messageText);
				ProfileManager.getInstance().setIsNewProfile(true);
				_overwriteDialog.hide();
				parent.changeScreen(ScreenType.CHARACTERSELECTION);
			}

		});

		startButton.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				String messageText = _profileText.getText();
				// check to see if the current profile matches one that already exists
				boolean exists = false;

				exists = ProfileManager.getInstance().doesProfileExist(messageText);

				if (exists) {
					// Pop up dialog for Overwrite
					_overwriteDialog.show(stage);
				} else {
					ProfileManager.getInstance().writeProfileToStorage(messageText, "", false);
					ProfileManager.getInstance().setCurrentProfile(messageText);
					ProfileManager.getInstance().setIsNewProfile(true);
					parent.changeScreen(ScreenType.MAINGAMESCREEN);
				}
			}
		}

		);

		backButton.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				parent.changeScreen(ScreenType.MAINMENU);
			}
		});

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

	// @Override
	// public void show() {
	// _overwriteDialog.hide();
	// _profileText.setText("");
	// Gdx.input.setInputProcessor(_stage);
	// }

	@Override
	public void hide() {
		_overwriteDialog.hide();
		_profileText.setText("");
		//Gdx.input.setInputProcessor(null);
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
