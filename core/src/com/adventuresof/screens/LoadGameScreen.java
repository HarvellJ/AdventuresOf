package com.adventuresof.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.AdventuresOfGame;
import com.adventuresof.game.profile.ProfileManager;

public class LoadGameScreen extends MainGameScreen implements Screen {

	private Stage stage;
	private AdventuresOfGame _game;
	private List _listItems;
	protected AdventuresOfGame parent;
	private Skin skin;
	private TextButton loadButton;
	private TextButton backButton;
	private ScrollPane scrollPane;

	public LoadGameScreen(AdventuresOfGame game) {
		parent = game;
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skins/star-soldier-ui.json"));

		loadButton = new TextButton("Load", skin);
		backButton = new TextButton("Back", skin);

		ProfileManager.getInstance().storeAllProfiles();
		_listItems = new List(skin);
		Array<String> list = ProfileManager.getInstance().getProfileList();
		_listItems.setItems(list);
		scrollPane = new ScrollPane(_listItems);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		addListeners();
	}

	public void show() {

		Array<String> list = ProfileManager.getInstance().getProfileList();
		_listItems.setItems(list);
		Gdx.input.setInputProcessor(stage);

		scrollPane.setOverscroll(false, false);
		scrollPane.setFadeScrollBars(false);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setScrollbarsOnTop(true);

		Table table = new Table();

		table.center();
		table.setFillParent(true);
		table.add(scrollPane).center();
		table.row().pad(10, 0, 10, 0);
		table.add(loadButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(backButton).fillX().uniformX();
		;

		stage.addActor(table);

	}

	public void addListeners() {
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

		loadButton.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (_listItems.getSelected() == null)
					return;
				String fileName = _listItems.getSelected().toString();
				if (fileName != null && !fileName.isEmpty()) {
					FileHandle file = ProfileManager.getInstance().getProfileFile(fileName);
					if (file != null) {
						ProfileManager.getInstance().setCurrentProfile(fileName);
						parent.changeScreen(ScreenType.MAINGAMESCREEN);
					}
				}
			}

		});
	}

	@Override
	public void render(float delta) {
		if (delta == 0) {
			return;
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}

	// @Override
	// public void show() {
	// Array<String> list = ProfileManager.getInstance().getProfileList();
	// _listItems.setItems(list);
	// Gdx.input.setInputProcessor(_stage);
	// }

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
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
