package com.adventuresof.game.world;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.character.PlayerCompanion;
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
	private Map map; 

	private Player player;
	private PlayerCompanion playerCompanion;
	private NPC NPCTest;
	
	public GameWorld() {

		// load the map
		map = new Map("testMap1.tmx");
		
		// load the players
		this.setPlayer(new Player(map.getAccessibleMapLayer()));
		this.NPCTest = new NPC(map.getAccessibleMapLayer());
		this.setPlayerCompanion(new PlayerCompanion(map.getAccessibleMapLayer(), this.player));
	}	
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
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
		playerCompanion.move();
		playerCompanion.update();
	}

	private void detectImpassibleObjectCollisions() {
		for (RectangleMapObject rectangleObject : map.getImpassibleObjects().getByType(RectangleMapObject.class)) {

			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
				// collision occurred
				break;
			}	     
		}	  	
	}

	public PlayerCompanion getPlayerCompanion() {
		return playerCompanion;
	}

	public void setPlayerCompanion(PlayerCompanion playerCompanion) {
		this.playerCompanion = playerCompanion;
	}

	
}
