package com.mygdx.game;

import com.adventuresof.helpers.ApplicationPreferences;
import com.adventuresof.helpers.SoundManager;
import com.adventuresof.screens.CharacterSelectionScreen;
import com.adventuresof.screens.MainGameScreen;
import com.adventuresof.screens.MainMenuScreen;
import com.adventuresof.screens.Resources;
import com.adventuresof.screens.ScreenType;
import com.adventuresof.screens.SettingsScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.maps.objects.PolygonMapObject;
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

public class AdventuresOfGame extends Game {
	
	private MainMenuScreen menuScreen;
	private MainGameScreen mainScreen;
	private SettingsScreen settingsScreen;
	private ApplicationPreferences preferences;
	private CharacterSelectionScreen characterSelectionScreen;
    public Resources res;

	@Override public void create () {
        res = new Resources();
		preferences = new ApplicationPreferences();
		if(preferences.isMusicEnabled() == true) {
			SoundManager.toggleMusic(true);
			SoundManager.setMusicVolume(preferences.getMusicVolume());
		} else {
			SoundManager.toggleMusic(false);
			SoundManager.setMusicVolume(preferences.getMusicVolume());
		}		
		menuScreen = new MainMenuScreen(this);
		this.setScreen(menuScreen);
//		this.setScreen(new MainGameScreen());
	}

	public void render () {
		super.render();
	}
	
	public void changeScreen(ScreenType s) {
		switch (s) {
		case MAINMENU:
			if (menuScreen == null) menuScreen = new MainMenuScreen(this);
			this.setScreen(menuScreen);
			break;
		case CHARACTERSELECTION:
			if (characterSelectionScreen == null) characterSelectionScreen = new CharacterSelectionScreen(this);
			this.setScreen(characterSelectionScreen);
			break;
		case MAINGAMESCREEN:
			if(mainScreen == null) mainScreen = new MainGameScreen();
			this.setScreen(mainScreen);
			break;
		case SETTINGS:
			if (settingsScreen == null) settingsScreen = new SettingsScreen(this);
			this.setScreen(settingsScreen);
			break;
		default: 
		}
	}
	
	public ApplicationPreferences getPreferences() {
		return preferences;
	}
	
    @Override
    public void dispose () {
        res.dispose();
    }
}

