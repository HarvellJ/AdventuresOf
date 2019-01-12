package com.adventuresof.screens;


import com.adventuresof.helpers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.AdventuresOfGame;

public class SettingsScreen extends MainMenuScreen implements Screen {

	private AdventuresOfGame owner;
	private Stage stage;
	private Skin skin;
	private Slider musicSlider;
	private CheckBox musicCheckBox;
	private Label titleLabel;
	private Label volumeMusicLabel;
	private Label musicOnOffLabel;
	private TextButton backButton;
	
	public SettingsScreen(AdventuresOfGame game) {
		owner = game;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("skins/star-soldier-ui.json"));
		musicSlider = new Slider(0f, 1f, 0.1f, false, skin);
		musicSlider.setValue(owner.getPreferences().getMusicVolume());
		musicCheckBox = new CheckBox(null, skin);
		musicCheckBox.setChecked(owner.getPreferences().isMusicEnabled());
		titleLabel = new Label("Settings", skin);
		volumeMusicLabel = new Label("Music Volume", skin);
		musicOnOffLabel = new Label("Music", skin);
		backButton = new TextButton("Back", skin);
		Gdx.input.setInputProcessor(stage);
		addListeners();
	}
	
	@Override
	public void show() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.add(titleLabel);
		table.row().pad(10,0,0,10);
		table.add(volumeMusicLabel);
		table.add(musicSlider);
		table.row().pad(10,0,0,10);
		table.add(musicOnOffLabel);
		table.add(musicCheckBox);
		table.row().pad(10,0,0,10);
		table.add(backButton);
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
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	public void addListeners() {
		musicSlider.addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				owner.getPreferences().setMusicVolume(musicSlider.getValue());
				SoundManager.setMusicVolume(musicSlider.getValue());
				return false;
			}
		});

		musicCheckBox.addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				owner.getPreferences().setMusicEnabled(musicCheckBox.isChecked());
				if(musicCheckBox.isChecked()) {
					SoundManager.toggleMusic(true);
				} else {
					SoundManager.toggleMusic(false);
				}
				return false;
			}
		});

		backButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				owner.changeScreen(ScreenType.MAINMENU);
			}
		});
	}
}
