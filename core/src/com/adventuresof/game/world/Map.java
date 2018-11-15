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
	private MapLayer itemSpawnPointObjectLayer; // Contains the objects representing item spawn points on the map
	private MapObjects itemSpawnPointObjects; // Contains the objects representing item spawn points on the map
	
	// Trigger object layer
	private MapLayer triggerObjectLayer; // Contains the objects representing trigger areas on the map
	private MapObjects triggerObjects; // Contains the objects representing triggers
	
	// Guard spawn object layer
	private MapLayer guardSpawnStartCasleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects guardSpawnStartCasleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapLayer guardSpawnMudBaseCasleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects guardSpawnMudBaseCasleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	
	// Enemy spawn object layer
    private MapLayer enemySpawnLevel1ObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnLevel1Objects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnLevel2ObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnLeve12Objects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnLevel3ObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnLevel3Objects; // Contains the objects representing spawn areas for enemy NPCs on the map
    
    // Guard spawn object layer
    private MapLayer playerSpawnObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects playerSpawnObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    
	private TiledMapTileLayer accessibleMapLayer; // Contains accessible tiles
	
	
	// Map layers
	private final static int ITEM_SPAWN_LAYER = 12; //layer number on which game objects exist
	private final static int ENEMY_SPAWNS_LEVEL_3 = 11; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_LEVEL_2 = 10; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_LEVEL_1 = 9; // The layer containing spawn points for the enemy NPC
	private final static int GUARD_SPAWNS_MUD_BASE = 8; // The layer containing spawn points for the guard NPC
	private final static int GUARD_SPAWNS_START_CASTLE = 7; // The layer containing spawn points for the guard NPC
	private final static int TRIGGER_LAYER = 6; // layer number on which game objects exist
	private final static int MAP_OBJECT_LAYER = 5; //layer number on which game objects exist
	private final static int PLAYER_SPAWNS = 4; //layer number on which game objects exist

	private int ACCESSIBLE_MAP_LAYER = 0;
	private int IN_ACCESSIBLE_MAP_LAYER = 1; //layer number on which tiles exist that cannot be moved onto, e.g. water, lava
		
		
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
	    setItemSpawnPointObjectLayer((MapLayer)tiledMap.getLayers().get(ITEM_SPAWN_LAYER));
	    setItemSpawnPointObjects(itemSpawnPointObjectLayer.getObjects());	   
	    
	    // store trigger objects and layers
	    setTriggerObjectLayer((MapLayer)tiledMap.getLayers().get(TRIGGER_LAYER));
	    setTriggerObjects(triggerObjectLayer.getObjects());	
	    	    
	    // store guard spawn objects and layers
	    setGuardSpawnMudBaseCasleObjectLayer((MapLayer)tiledMap.getLayers().get(GUARD_SPAWNS_MUD_BASE));
	    setGuardSpawnMudBaseCasleSpawnObjects(guardSpawnMudBaseCasleObjectLayer.getObjects());	
	    
	    setGuardSpawnStartCasleObjectLayer((MapLayer)tiledMap.getLayers().get(GUARD_SPAWNS_START_CASTLE));
	    setGuardSpawnStartCasleSpawnObjects(guardSpawnStartCasleObjectLayer.getObjects());	
	    
	    // store enemy spawn objects and layers
	    setEnemySpawnLevel1ObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_LEVEL_1));
	    setEnemySpawnLevel1Objects(enemySpawnLevel1ObjectLayer.getObjects());	 
	    
	    setEnemySpawnLevel2ObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_LEVEL_2));
	    setEnemySpawnLeve12Objects(enemySpawnLevel2ObjectLayer.getObjects());	 
	    
	    setEnemySpawnLevel3ObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_LEVEL_3));
	    setEnemySpawnLevel3Objects(enemySpawnLevel3ObjectLayer.getObjects());	
	    
	    // store enemy spawn objects and layers
	    setPlayerSpawnObjectLayer((MapLayer)tiledMap.getLayers().get(PLAYER_SPAWNS));
	    setPlayerSpawnObjects(playerSpawnObjectLayer.getObjects());	
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

	public MapLayer getItemSpawnPointObjectLayer() {
		return itemSpawnPointObjectLayer;
	}


	public void setItemSpawnPointObjectLayer(MapLayer spawnPointObjectLayer) {
		this.itemSpawnPointObjectLayer = spawnPointObjectLayer;
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


	public MapObjects getItemSpawnPointObjects() {
		return itemSpawnPointObjects;
	}


	public void setItemSpawnPointObjects(MapObjects spawnPointObjects) {
		this.itemSpawnPointObjects = spawnPointObjects;
	}

	public MapLayer getPlayerSpawnObjectLayer() {
		return playerSpawnObjectLayer;
	}

	public void setPlayerSpawnObjectLayer(MapLayer playerSpawnObjectLayer) {
		this.playerSpawnObjectLayer = playerSpawnObjectLayer;
	}

	public MapObjects getPlayerSpawnObjects() {
		return playerSpawnObjects;
	}

	public void setPlayerSpawnObjects(MapObjects playerSpawnObjects) {
		this.playerSpawnObjects = playerSpawnObjects;
	}

	public MapLayer getGuardSpawnStartCasleObjectLayer() {
		return guardSpawnStartCasleObjectLayer;
	}

	public void setGuardSpawnStartCasleObjectLayer(MapLayer guardSpawnStartCasleObjectLayer) {
		this.guardSpawnStartCasleObjectLayer = guardSpawnStartCasleObjectLayer;
	}

	public MapObjects getGuardSpawnStartCasleSpawnObjects() {
		return guardSpawnStartCasleSpawnObjects;
	}

	public void setGuardSpawnStartCasleSpawnObjects(MapObjects guardSpawnStartCasleSpawnObjects) {
		this.guardSpawnStartCasleSpawnObjects = guardSpawnStartCasleSpawnObjects;
	}

	public MapLayer getGuardSpawnMudBaseCasleObjectLayer() {
		return guardSpawnMudBaseCasleObjectLayer;
	}

	public void setGuardSpawnMudBaseCasleObjectLayer(MapLayer guardSpawnMudBaseCasleObjectLayer) {
		this.guardSpawnMudBaseCasleObjectLayer = guardSpawnMudBaseCasleObjectLayer;
	}

	public MapObjects getGuardSpawnMudBaseCasleSpawnObjects() {
		return guardSpawnMudBaseCasleSpawnObjects;
	}

	public void setGuardSpawnMudBaseCasleSpawnObjects(MapObjects guardSpawnMudBaseCasleSpawnObjects) {
		this.guardSpawnMudBaseCasleSpawnObjects = guardSpawnMudBaseCasleSpawnObjects;
	}

	public MapLayer getEnemySpawnLevel1ObjectLayer() {
		return enemySpawnLevel1ObjectLayer;
	}

	public void setEnemySpawnLevel1ObjectLayer(MapLayer enemySpawnLevel1ObjectLayer) {
		this.enemySpawnLevel1ObjectLayer = enemySpawnLevel1ObjectLayer;
	}

	public MapObjects getEnemySpawnLevel1Objects() {
		return enemySpawnLevel1Objects;
	}

	public void setEnemySpawnLevel1Objects(MapObjects enemySpawnLevel1Objects) {
		this.enemySpawnLevel1Objects = enemySpawnLevel1Objects;
	}

	public MapLayer getEnemySpawnLevel2ObjectLayer() {
		return enemySpawnLevel2ObjectLayer;
	}

	public void setEnemySpawnLevel2ObjectLayer(MapLayer enemySpawnLevel2ObjectLayer) {
		this.enemySpawnLevel2ObjectLayer = enemySpawnLevel2ObjectLayer;
	}

	public MapObjects getEnemySpawnLeve12Objects() {
		return enemySpawnLeve12Objects;
	}

	public void setEnemySpawnLeve12Objects(MapObjects enemySpawnLeve12Objects) {
		this.enemySpawnLeve12Objects = enemySpawnLeve12Objects;
	}

	public MapObjects setEnemySpawnLevel3Objects() {
		return enemySpawnLevel3Objects;
	}

	public void setEnemySpawnLevel3Objects(MapObjects enemySpawnLevel3Objects) {
		this.enemySpawnLevel3Objects = enemySpawnLevel3Objects;
	}

	public MapLayer getEnemySpawnLevel3ObjectLayer() {
		return enemySpawnLevel3ObjectLayer;
	}

	public void setEnemySpawnLevel3ObjectLayer(MapLayer enemySpawnLevel3ObjectLayer) {
		this.enemySpawnLevel3ObjectLayer = enemySpawnLevel3ObjectLayer;
	}
	
}
