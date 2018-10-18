package com.adventuresof.gameworld;

import com.adventuresof.game.NPC;
import com.adventuresof.game.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.AdventuresOfGame;

public class GameWorld {

	// The map for this game world
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;

	private Player player;
	NPC NPCTest;
	
	// Collision code
	int objectLayerId = 1; //layer number on which game objects exist
	int accessibleMapLayerID = 1;
	int inaccessibleMapLayerID = 2; //layer number on which tiles exist that cannot be moved onto, e.g. water, lava
	MapLayer collisionObjectLayer; //Actually contains the collision object layer
	MapObjects objects ;
	TiledMapTileLayer accessibleMapLayer; //Actually contains the collision object layer
	
	public GameWorld() {

		// load the map
		tiledMap = new TmxMapLoader().load("testMap1.tmx");
		setTiledMapRenderer(new OrthogonalTiledMapRenderer(tiledMap));

		// store collision objects and layers
	    collisionObjectLayer = (MapLayer)tiledMap.getLayers().get(objectLayerId);
	    objects = collisionObjectLayer.getObjects();
		accessibleMapLayer = (TiledMapTileLayer)tiledMap.getLayers().get(accessibleMapLayerID);
		
		// load the players
		this.setPlayer(new Player(accessibleMapLayer));
		this.NPCTest = new NPC(accessibleMapLayer);
	}
	
	public TiledMap getTiledMap() {
		return tiledMap;
	}

	public void setTiledMap(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
	}
	
	public TiledMapRenderer getTiledMapRenderer() {
		return tiledMapRenderer;
	}

	public void setTiledMapRenderer(TiledMapRenderer tiledMapRenderer) {
		this.tiledMapRenderer = tiledMapRenderer;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void update() {
		//this.detectObjectCollisions();
		player.update();
	 	NPCTest.move();
		NPCTest.update();
	}

	private void detectObjectCollisions() {
		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
				// collision occurred
				break;
			}	     
		}	  	
	}

	
}
