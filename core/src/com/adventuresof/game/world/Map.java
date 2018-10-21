package com.adventuresof.game.world;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.character.PlayerCompanion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {	
	// the map itself
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	
	// collision object layer
	private MapLayer collisionObjectLayer; //Actually contains the collision object layer
	private MapObjects impassibleObjects;
	
	// Spawn point object layer
	private MapLayer spawnPointObjectLayer; // Contains the objects representing item spawn points on the map
	private MapObjects spawnPointObjects; // Contains the objects representing item spawn points on the map
	
	// Trigger object layer
	private MapLayer triggerObjectLayer; // Contains the objects representing trigger areas on the map
	private MapObjects triggerObjects; // Contains the objects representing triggers
	
	// Guard spawn object layer
	private MapLayer guardSpawnObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects guardSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	
	// Guard spawn object layer
    private MapLayer enemySpawnObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
	
	private TiledMapTileLayer accessibleMapLayer; // Contains accessible tiles
	
	
	// Collision layers/ object layers
	private final static int MAP_OBJECT_LAYER = 1; //layer number on which game objects exist
	private final static int ITEM_SPAWN_LAYER = 0; //layer number on which game objects exist
	private final static int TRIGGER_LAYER = 6; // layer number on which game objects exist
	private final static int GUARD_SPAWNS = 7; // The layer containing spawn points for the guard NPC
	private final static int ENEMY_SPAWNS = 8; // The layer containing spawn points for the enemy NPC
	
	private int ACCESSIBLE_MAP_LAYER = 3;
	private int IN_ACCESSIBLE_MAP_LAYER = 2; //layer number on which tiles exist that cannot be moved onto, e.g. water, lava
		
		
	public Map(String mapName) {

		// load the map
		tiledMap = new TmxMapLoader().load(mapName);
		setTiledMapRenderer(new OrthogonalTiledMapRenderer(tiledMap));

		// set the accessible tiles
		setAccessibleMapLayer((TiledMapTileLayer)tiledMap.getLayers().get(ACCESSIBLE_MAP_LAYER));
		
		// store collision objects and layers
	    collisionObjectLayer = (MapLayer)tiledMap.getLayers().get(MAP_OBJECT_LAYER);
	    impassibleObjects = collisionObjectLayer.getObjects();
	
		// store item spawn point objects and layers
	    setSpawnPointObjectLayer((MapLayer)tiledMap.getLayers().get(ITEM_SPAWN_LAYER));
	    setSpawnPointObjects(spawnPointObjectLayer.getObjects());	   
	    
	    // store trigger objects and layers
	    setTriggerObjectLayer((MapLayer)tiledMap.getLayers().get(TRIGGER_LAYER));
	    setTriggerObjects(triggerObjectLayer.getObjects());	
	    	    
	    // store guard spawn objects and layers
	    setGuardSpawnObjectLayer((MapLayer)tiledMap.getLayers().get(GUARD_SPAWNS));
	    setGuardSpawnObjects(guardSpawnObjectLayer.getObjects());	
	    
	    // store enemy spawn objects and layers
	    setEnemySpawnObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS));
	    setEnemySpawnObjects(enemySpawnObjectLayer.getObjects());	
	}

	public MapObjects getEnemySpawnObjects() {
		return enemySpawnObjects;
	}

	public void setEnemySpawnObjects(MapObjects enemySpawnObjects) {
		this.enemySpawnObjects = enemySpawnObjects;
	}

	public MapLayer getEnemySpawnObjectLayer() {
		return enemySpawnObjectLayer;
	}

	public void setEnemySpawnObjectLayer(MapLayer enemySpawnObjectLayer) {
		this.enemySpawnObjectLayer = enemySpawnObjectLayer;
	}

	public MapObjects getGuardSpawnObjects() {
		return guardSpawnObjects;
	}

	public void setGuardSpawnObjects(MapObjects guardSpawnObjects) {
		this.guardSpawnObjects = guardSpawnObjects;
	}

	public MapLayer getGuardSpawnObjectLayer() {
		return guardSpawnObjectLayer;
	}

	public void setGuardSpawnObjectLayer(MapLayer guardSpawnObjectLayer) {
		this.guardSpawnObjectLayer = guardSpawnObjectLayer;
	}

	public MapLayer getTriggerObjectLayer() {
		return triggerObjectLayer;
	}

	public void setTriggerObjectLayer(MapLayer triggerObjectLayer) {
		this.triggerObjectLayer = triggerObjectLayer;
	}

	public MapObjects getTriggerObjects() {
		return triggerObjects;
	}

	public void setTriggerObjects(MapObjects triggerObjects) {
		this.triggerObjects = triggerObjects;
	}

	public MapLayer getSpawnPointObjectLayer() {
		return spawnPointObjectLayer;
	}


	public void setSpawnPointObjectLayer(MapLayer spawnPointObjectLayer) {
		this.spawnPointObjectLayer = spawnPointObjectLayer;
	}

	
	public TiledMapRenderer getTiledMapRenderer() {
		return tiledMapRenderer;
	}


	public void setTiledMapRenderer(TiledMapRenderer tiledMapRenderer) {
		this.tiledMapRenderer = tiledMapRenderer;
	}


	public TiledMap getTiledMap() {
		return tiledMap;
	}


	public void setTiledMap(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
	}


	public TiledMapTileLayer getAccessibleMapLayer() {
		return accessibleMapLayer;
	}


	public void setAccessibleMapLayer(TiledMapTileLayer accessibleMapLayer) {
		this.accessibleMapLayer = accessibleMapLayer;
	}


	public MapObjects getImpassibleObjects() {
		return impassibleObjects;
	}


	public void setImpassibleObjects(MapObjects impassibleObjects) {
		this.impassibleObjects = impassibleObjects;
	}


	public MapObjects getSpawnPointObjects() {
		return spawnPointObjects;
	}


	public void setSpawnPointObjects(MapObjects spawnPointObjects) {
		this.spawnPointObjects = spawnPointObjects;
	}
	
}
