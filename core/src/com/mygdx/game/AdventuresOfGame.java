package com.mygdx.game;

import com.adventuresof.screens.MainGameScreen;
import com.adventuresof.screens.MainMenuScreen;
import com.adventuresof.screens.ScreenType;
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


	@Override public void create () {
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
		case MAINGAMESCREEN:
			if(mainScreen == null) mainScreen = new MainGameScreen();
			this.setScreen(mainScreen);
			break;
		default: 
		}
	}
}

