package com.adventuresof.screens;

import com.adventuresof.game.GameCharacter;
import com.adventuresof.game.NPC;
import com.adventuresof.game.Player;
import com.adventuresof.gameworld.GameRenderer;
import com.adventuresof.gameworld.GameWorld;
import com.adventuresof.helpers.InputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
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
import com.mygdx.game.AdventuresOfGame;

import javafx.util.Pair;

public class MainGameScreen  implements Screen {

	private GameWorld gameWorld;
	private GameRenderer gameRenderer;
	
	// Collision code
	int objectLayerId = 1; //layer number on which game objects exist
	int accessibleMapLayerID = 1;
	int inaccessibleMapLayerID = 2; //layer number on which tiles exist that cannot be moved onto, e.g. water, lava
	MapLayer collisionObjectLayer; //Actually contains the collision object layer
	MapObjects objects ;
	TiledMapTileLayer accessibleMapLayer; //Actually contains the collision object layer

	public MainGameScreen() {		
		this.gameWorld = new GameWorld();
		this.gameRenderer = new GameRenderer(gameWorld);
		Gdx.input.setInputProcessor(new InputHandler(gameWorld, gameRenderer));
	}

	@Override
	public void show() {
	
	}

	@Override
	public void render(float delta) {
		this.gameWorld.update();
		this.gameRenderer.render();
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
