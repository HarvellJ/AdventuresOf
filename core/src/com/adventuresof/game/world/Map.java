package com.adventuresof.game.world;

import com.adventuresof.game.character.entities.NPC;
import com.adventuresof.game.character.entities.Player;
import com.adventuresof.game.character.entities.PlayerCompanion;
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
	
	// Item object layer
	private MapLayer itemSpawnPointObjectLayer; // Contains the objects representing item spawn points on the map
	private MapObjects itemSpawnPointObjects; // Contains the objects representing item spawn points on the map
	private MapLayer rareItemSpawnPointObjectLayer; // Contains the objects representing item spawn points on the map
	private MapObjects rareItemSpawnPointObjects; // Contains the objects representing item spawn points on the map
	
	// Animated map items object layer
	private MapLayer campFireSpawnObjectLayer; // Contains the objects representing item spawn points on the map
	private MapObjects campFireSpawnPointObjects; // Contains the objects representing item spawn points on the map
	
	// Animal spawn point object layer
	private MapLayer animalSpawnPointObjectLayer; // Contains the objects representing animal spawn points on the map
	private MapObjects animalSpawnPointObjects; // Contains the objects representing animal spawn points on the map
	
	// Trigger object layer
	private MapLayer triggerObjectLayer; // Contains the objects representing trigger areas on the map
	private MapObjects triggerObjects; // Contains the objects representing triggers
	
	// Guard spawn object layer
	private MapLayer guardSpawnLavaCastleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects guardSpawnLavaCastleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapLayer guardSpawnMudBaseCastleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects guardSpawnMudBaseCastleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	
	// Starter area
	private MapLayer guardSpawnStarterCastleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects guardSpawnStarterCastleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapLayer staticGuardSpawnStarterCastleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects staticGuardSpawnStarterCastleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapLayer staticShadowKnightSpawnStarterCastleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects staticCommanderSpawnStarterCastleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapLayer staticCommanderSpawnStarterCastleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	
	private MapObjects staticShadowKnightSpawnStarterCastleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapLayer villagerSpawnStarterCastleObjectLayer; // Contains the objects representing spawn areas for guard NPCs on the map
	private MapObjects villagerSpawnStarterCastleSpawnObjects; // Contains the objects representing spawn areas for guard NPCs on the map
	
	// Enemy spawn object layer 
    //lava zone
    private MapLayer enemySpawnLavaZoneLowObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnLavaZoneLowObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnLavaZoneMedObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnLavaZoneMedObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnLavaZoneHighObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnLavaZoneHighObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnLavaZoneBossObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnLavaZoneBossObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
	//mud area
    private MapLayer enemySpawnMudZoneLowObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnMudZoneLowObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnMudZoneMedObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnMudZoneMedObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnMudZoneHighObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnMudZoneHighObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnMudZoneBossObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnMudZoneBossObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    //jungle area
    private MapLayer enemySpawnJungleZoneLowObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
	private MapObjects enemySpawnJungleZoneLowObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnJungleZoneMedObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnJungleZoneMedObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnJungleZoneHighObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnJungleZoneHighObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapLayer enemySpawnJungleZoneBossObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects enemySpawnJungleZoneBossObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    // Guard spawn object layer
    private MapLayer playerSpawnObjectLayer; // Contains the objects representing spawn areas for enemy NPCs on the map
    private MapObjects playerSpawnObjects; // Contains the objects representing spawn areas for enemy NPCs on the map
    
	private TiledMapTileLayer accessibleMapLayer; // Contains accessible tiles
	
	// Map layers
	private final static int ENEMY_SPAWNS_LAVACASTLE_BOSS = 29; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_LAVACASTLE_HIGH = 28; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_LAVACASTLE_MED = 27; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_MUDZONE_BOSS = 26; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_MUDZONE_HIGH = 25; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_MUDZONE_MED = 24; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_JUNGLE_BOSS = 23; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_JUNGLE_HIGH = 22; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_JUNGLE_MED = 21; // The layer containing spawn points for the enemy NPC
	private final static int CAMPFIRE_SPAWN_LAYER = 20; //layer number on which game objects exist
	private final static int RARE_ITEM_SPAWN_LAYER = 19; //layer number on which game objects exist
	private final static int STATIC_COMMANDER_SPAWNS_START_CASTLE = 18; //layer number on which game objects exist
	private final static int STATIC_SHADOWKNIGHT_SPAWNS_START_CASTLE = 17; //layer number on which game objects exist
	private final static int STATIC_GUARD_SPAWNS_START_CASTLE = 16; //layer number on which game objects exist
	private final static int VILLAGER_SPAWNS_START_CASTLE = 15; //layer number on which game objects exist
	private final static int GUARD_SPAWNS_START_CASTLE = 14; //layer number on which game objects exist
	private final static int ANIMAL_SPAWN_LAYER = 13; //layer number on which game objects exist
	private final static int ITEM_SPAWN_LAYER = 12; //layer number on which game objects exist
	private final static int ENEMY_SPAWNS_JUNGLE_LOW = 11; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_MUDZONE_LOW = 10; // The layer containing spawn points for the enemy NPC
	private final static int ENEMY_SPAWNS_LAVACASTLE_LOW = 9; // The layer containing spawn points for the enemy NPC
	private final static int GUARD_SPAWNS_MUD_BASE = 8; // The layer containing spawn points for the guard NPC
	private final static int GUARD_SPAWNS_LAVA_CASTLE = 7; // The layer containing spawn points for the guard NPC
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
	    setRareItemSpawnPointObjectLayer((MapLayer)tiledMap.getLayers().get(RARE_ITEM_SPAWN_LAYER));
	    setRareItemSpawnPointObjects(rareItemSpawnPointObjectLayer.getObjects());	
	    
	    // store animated map object spawn point objects and layers
	    setCampFireSpawnObjectLayer((MapLayer)tiledMap.getLayers().get(CAMPFIRE_SPAWN_LAYER));
	    setCampFireSpawnPointObjects(campFireSpawnObjectLayer.getObjects());	
	    
		// store animal spawn point objects and layers
	    setAnimalSpawnPointObjectLayer((MapLayer)tiledMap.getLayers().get(ANIMAL_SPAWN_LAYER));
	    setAnimalSpawnPointObjects(animalSpawnPointObjectLayer.getObjects());	   
	    
	    // store trigger objects and layers
	    setTriggerObjectLayer((MapLayer)tiledMap.getLayers().get(TRIGGER_LAYER));
	    setTriggerObjects(triggerObjectLayer.getObjects());	
	    	    
	    // store guard spawn objects and layers
	    setGuardSpawnMudBaseCastleObjectLayer((MapLayer)tiledMap.getLayers().get(GUARD_SPAWNS_MUD_BASE));
	    setGuardSpawnMudBaseCastleSpawnObjects(guardSpawnMudBaseCastleObjectLayer.getObjects());	
	    
	    // starter castle guards
	    setGuardSpawnStarterCastleObjectLayer((MapLayer)tiledMap.getLayers().get(GUARD_SPAWNS_START_CASTLE));
	    setGuardSpawnStarterCastleSpawnObjects(guardSpawnStarterCastleObjectLayer.getObjects());	
	    
	    // starter castle static guards
	    setStaticGuardSpawnStarterCastleObjectLayer((MapLayer)tiledMap.getLayers().get(STATIC_GUARD_SPAWNS_START_CASTLE));
	    setStaticGuardSpawnStarterCastleSpawnObjects(staticGuardSpawnStarterCastleObjectLayer.getObjects());	
	    setStaticShadowKnightSpawnStarterCastleObjectLayer((MapLayer)tiledMap.getLayers().get(STATIC_SHADOWKNIGHT_SPAWNS_START_CASTLE));
	    setStaticShadowKnightSpawnStarterCastleSpawnObjects(staticShadowKnightSpawnStarterCastleObjectLayer.getObjects());	
	    setStaticCommanderSpawnStarterCastleObjectLayer((MapLayer)tiledMap.getLayers().get(STATIC_COMMANDER_SPAWNS_START_CASTLE));
	    setStaticCommanderSpawnStarterCastleSpawnObjects(staticCommanderSpawnStarterCastleObjectLayer.getObjects());	
	    
	    // starter castle villagers
	    setVillagerSpawnStarterCastleObjectLayer((MapLayer)tiledMap.getLayers().get(VILLAGER_SPAWNS_START_CASTLE));
	    setVillagerSpawnStarterCastleSpawnObjects(villagerSpawnStarterCastleObjectLayer.getObjects());	
	    
	    // lava castle guards
	    setGuardSpawnLavaCastleObjectLayer((MapLayer)tiledMap.getLayers().get(GUARD_SPAWNS_LAVA_CASTLE));
	    setGuardSpawnLavaCastleSpawnObjects(guardSpawnLavaCastleObjectLayer.getObjects());	
	     
	    
	    // store enemy spawn objects and layers
	    setEnemySpawnLavaCastleLowObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_LAVACASTLE_LOW));
	    setEnemySpawnLavaCastleLowObjects(enemySpawnLavaZoneLowObjectLayer.getObjects());	 
	    setEnemySpawnLavaZoneMedObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_LAVACASTLE_MED));
	    setEnemySpawnLavaZoneMedObjects(enemySpawnLavaZoneLowObjectLayer.getObjects());	 
	    setEnemySpawnLavaZoneHighObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_LAVACASTLE_HIGH));
	    setEnemySpawnLavaZoneHighObjects(enemySpawnLavaZoneLowObjectLayer.getObjects());	 
	    setEnemySpawnLavaZoneBossObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_LAVACASTLE_BOSS));
	    setEnemySpawnLavaZoneBossObjects(enemySpawnLavaZoneLowObjectLayer.getObjects());	 
	    
	    setEnemySpawnMudZoneLowObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_MUDZONE_LOW));
	    setEnemySpawnMudZoneLowObjects(enemySpawnMudZoneLowObjectLayer.getObjects());	 
	    setEnemySpawnMudZoneMedObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_MUDZONE_MED));
	    setEnemySpawnMudZoneMedObjects(enemySpawnMudZoneMedObjectLayer.getObjects());	 
	    setEnemySpawnMudZoneHighObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_MUDZONE_HIGH));
	    setEnemySpawnMudZoneHighObjects(enemySpawnMudZoneHighObjectLayer.getObjects());
	    setEnemySpawnMudZoneBossObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_MUDZONE_BOSS));
	    setEnemySpawnMudZoneBossObjects(enemySpawnMudZoneBossObjectLayer.getObjects());	 
	    
	    setEnemySpawnJungleLowObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_JUNGLE_LOW));
	    setEnemySpawnJungleLowObjects(enemySpawnJungleZoneLowObjectLayer.getObjects());	
	    setEnemySpawnJungleZoneMedObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_JUNGLE_MED));
	    setEnemySpawnJungleZoneMedObjects(enemySpawnJungleZoneMedObjectLayer.getObjects());	
	    setEnemySpawnJungleZoneHighObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_JUNGLE_HIGH));
	    setEnemySpawnJungleZoneHighObjects(enemySpawnJungleZoneHighObjectLayer.getObjects());	
	    setEnemySpawnJungleZoneBossObjectLayer((MapLayer)tiledMap.getLayers().get(ENEMY_SPAWNS_JUNGLE_BOSS));
	    setEnemySpawnJungleZoneBossObjects(enemySpawnJungleZoneBossObjectLayer.getObjects());	
	    
	    // store enemy spawn objects and layers
	    setPlayerSpawnObjectLayer((MapLayer)tiledMap.getLayers().get(PLAYER_SPAWNS));
	    setPlayerSpawnObjects(playerSpawnObjectLayer.getObjects());	
	}
	
	public MapLayer getEnemySpawnLavaZoneMedObjectLayer() {
		return enemySpawnLavaZoneMedObjectLayer;
	}

	public void setEnemySpawnLavaZoneMedObjectLayer(MapLayer enemySpawnLavaZoneMedObjectLayer) {
		this.enemySpawnLavaZoneMedObjectLayer = enemySpawnLavaZoneMedObjectLayer;
	}

	public MapObjects getEnemySpawnLavaZoneMedObjects() {
		return enemySpawnLavaZoneMedObjects;
	}

	public void setEnemySpawnLavaZoneMedObjects(MapObjects enemySpawnLavaZoneMedObjects) {
		this.enemySpawnLavaZoneMedObjects = enemySpawnLavaZoneMedObjects;
	}

	public MapLayer getEnemySpawnLavaZoneHighObjectLayer() {
		return enemySpawnLavaZoneHighObjectLayer;
	}

	public void setEnemySpawnLavaZoneHighObjectLayer(MapLayer enemySpawnLavaZoneHighObjectLayer) {
		this.enemySpawnLavaZoneHighObjectLayer = enemySpawnLavaZoneHighObjectLayer;
	}

	public MapObjects getEnemySpawnLavaZoneHighObjects() {
		return enemySpawnLavaZoneHighObjects;
	}

	public void setEnemySpawnLavaZoneHighObjects(MapObjects enemySpawnLavaZoneHighObjects) {
		this.enemySpawnLavaZoneHighObjects = enemySpawnLavaZoneHighObjects;
	}

	public MapLayer getEnemySpawnLavaZoneBossObjectLayer() {
		return enemySpawnLavaZoneBossObjectLayer;
	}

	public void setEnemySpawnLavaZoneBossObjectLayer(MapLayer enemySpawnLavaZoneBossObjectLayer) {
		this.enemySpawnLavaZoneBossObjectLayer = enemySpawnLavaZoneBossObjectLayer;
	}

	public MapObjects getEnemySpawnLavaZoneBossObjects() {
		return enemySpawnLavaZoneBossObjects;
	}

	public void setEnemySpawnLavaZoneBossObjects(MapObjects enemySpawnLavaZoneBossObjects) {
		this.enemySpawnLavaZoneBossObjects = enemySpawnLavaZoneBossObjects;
	}
	
	public MapLayer getEnemySpawnMudZoneMedObjectLayer() {
		return enemySpawnMudZoneMedObjectLayer;
	}

	public void setEnemySpawnMudZoneMedObjectLayer(MapLayer enemySpawnMudZoneMedObjectLayer) {
		this.enemySpawnMudZoneMedObjectLayer = enemySpawnMudZoneMedObjectLayer;
	}

	public MapObjects getEnemySpawnMudZoneMedObjects() {
		return enemySpawnMudZoneMedObjects;
	}

	public void setEnemySpawnMudZoneMedObjects(MapObjects enemySpawnMudZoneMedObjects) {
		this.enemySpawnMudZoneMedObjects = enemySpawnMudZoneMedObjects;
	}

	public MapLayer getEnemySpawnMudZoneHighObjectLayer() {
		return enemySpawnMudZoneHighObjectLayer;
	}

	public void setEnemySpawnMudZoneHighObjectLayer(MapLayer enemySpawnMudZoneHighObjectLayer) {
		this.enemySpawnMudZoneHighObjectLayer = enemySpawnMudZoneHighObjectLayer;
	}

	public MapObjects getEnemySpawnMudZoneHighObjects() {
		return enemySpawnMudZoneHighObjects;
	}

	public void setEnemySpawnMudZoneHighObjects(MapObjects enemySpawnMudZoneHighObjects) {
		this.enemySpawnMudZoneHighObjects = enemySpawnMudZoneHighObjects;
	}

	public MapLayer getEnemySpawnMudZoneBossObjectLayer() {
		return enemySpawnMudZoneBossObjectLayer;
	}

	public void setEnemySpawnMudZoneBossObjectLayer(MapLayer enemySpawnMudZoneBossObjectLayer) {
		this.enemySpawnMudZoneBossObjectLayer = enemySpawnMudZoneBossObjectLayer;
	}

	public MapObjects getEnemySpawnMudZoneBossObjects() {
		return enemySpawnMudZoneBossObjects;
	}

	public void setEnemySpawnMudZoneBossObjects(MapObjects enemySpawnMudZoneBossObjects) {
		this.enemySpawnMudZoneBossObjects = enemySpawnMudZoneBossObjects;
	}
	
	public MapLayer getEnemySpawnJungleZoneMedObjectLayer() {
		return enemySpawnJungleZoneMedObjectLayer;
	}

	public void setEnemySpawnJungleZoneMedObjectLayer(MapLayer enemySpawnJungleZoneMedObjectLayer) {
		this.enemySpawnJungleZoneMedObjectLayer = enemySpawnJungleZoneMedObjectLayer;
	}

	public MapObjects getEnemySpawnJungleZoneMedObjects() {
		return enemySpawnJungleZoneMedObjects;
	}

	public void setEnemySpawnJungleZoneMedObjects(MapObjects enemySpawnJungleZoneMedObjects) {
		this.enemySpawnJungleZoneMedObjects = enemySpawnJungleZoneMedObjects;
	}

	public MapLayer getEnemySpawnJungleZoneHighObjectLayer() {
		return enemySpawnJungleZoneHighObjectLayer;
	}

	public void setEnemySpawnJungleZoneHighObjectLayer(MapLayer enemySpawnJungleZoneHighObjectLayer) {
		this.enemySpawnJungleZoneHighObjectLayer = enemySpawnJungleZoneHighObjectLayer;
	}

	public MapObjects getEnemySpawnJungleZoneHighObjects() {
		return enemySpawnJungleZoneHighObjects;
	}

	public void setEnemySpawnJungleZoneHighObjects(MapObjects enemySpawnJungleZoneHighObjects) {
		this.enemySpawnJungleZoneHighObjects = enemySpawnJungleZoneHighObjects;
	}

	public MapLayer getEnemySpawnJungleZoneBossObjectLayer() {
		return enemySpawnJungleZoneBossObjectLayer;
	}

	public void setEnemySpawnJungleZoneBossObjectLayer(MapLayer enemySpawnJungleZoneBossObjectLayer) {
		this.enemySpawnJungleZoneBossObjectLayer = enemySpawnJungleZoneBossObjectLayer;
	}

	public MapObjects getEnemySpawnJungleZoneBossObjects() {
		return enemySpawnJungleZoneBossObjects;
	}

	public void setEnemySpawnJungleZoneBossObjects(MapObjects enemySpawnJungleZoneBossObjects) {
		this.enemySpawnJungleZoneBossObjects = enemySpawnJungleZoneBossObjects;
	}

	public MapObjects getCampFireSpawnPointObjects() {
		return campFireSpawnPointObjects;
	}

	public void setCampFireSpawnPointObjects(MapObjects campFireSpawnPointObjects) {
		this.campFireSpawnPointObjects = campFireSpawnPointObjects;
	}

	public MapLayer getCampFireSpawnObjectLayer() {
		return campFireSpawnObjectLayer;
	}

	public void setCampFireSpawnObjectLayer(MapLayer campFireSpawnObjectLayer) {
		this.campFireSpawnObjectLayer = campFireSpawnObjectLayer;
	}

	public MapObjects getRareItemSpawnPointObjects() {
		return rareItemSpawnPointObjects;
	}

	public void setRareItemSpawnPointObjects(MapObjects rareItemSpawnPointObjects) {
		this.rareItemSpawnPointObjects = rareItemSpawnPointObjects;
	}

	public MapLayer getRareItemSpawnPointObjectLayer() {
		return rareItemSpawnPointObjectLayer;
	}

	public void setRareItemSpawnPointObjectLayer(MapLayer rareItemSpawnPointObjectLayer) {
		this.rareItemSpawnPointObjectLayer = rareItemSpawnPointObjectLayer;
	}

	public MapLayer getStaticCommanderSpawnStarterCastleObjectLayer() {
		return staticCommanderSpawnStarterCastleObjectLayer;
	}

	public void setStaticCommanderSpawnStarterCastleObjectLayer(MapLayer staticCommanderSpawnStarterCastleObjectLayer) {
		this.staticCommanderSpawnStarterCastleObjectLayer = staticCommanderSpawnStarterCastleObjectLayer;
	}

	public MapObjects getStaticCommanderSpawnStarterCastleSpawnObjects() {
		return staticCommanderSpawnStarterCastleSpawnObjects;
	}

	public void setStaticCommanderSpawnStarterCastleSpawnObjects(
			MapObjects staticCommanderSpawnStarterCastleSpawnObjects) {
		this.staticCommanderSpawnStarterCastleSpawnObjects = staticCommanderSpawnStarterCastleSpawnObjects;
	}

	public MapLayer getStaticShadowKnightSpawnStarterCastleObjectLayer() {
		return staticShadowKnightSpawnStarterCastleObjectLayer;
	}

	public void setStaticShadowKnightSpawnStarterCastleObjectLayer(
			MapLayer staticShadowKnightSpawnStarterCastleObjectLayer) {
		this.staticShadowKnightSpawnStarterCastleObjectLayer = staticShadowKnightSpawnStarterCastleObjectLayer;
	}

	public MapObjects getStaticShadowKnightSpawnStarterCastleSpawnObjects() {
		return staticShadowKnightSpawnStarterCastleSpawnObjects;
	}

	public void setStaticShadowKnightSpawnStarterCastleSpawnObjects(
			MapObjects staticShadowKnightSpawnStarterCastleSpawnObjects) {
		this.staticShadowKnightSpawnStarterCastleSpawnObjects = staticShadowKnightSpawnStarterCastleSpawnObjects;
	}

	public MapObjects getStaticGuardSpawnStarterCastleSpawnObjects() {
		return staticGuardSpawnStarterCastleSpawnObjects;
	}

	public void setStaticGuardSpawnStarterCastleSpawnObjects(MapObjects staticGuardSpawnStarterCastleSpawnObjects) {
		this.staticGuardSpawnStarterCastleSpawnObjects = staticGuardSpawnStarterCastleSpawnObjects;
	}

	public MapLayer getStaticGuardSpawnStarterCastleObjectLayer() {
		return staticGuardSpawnStarterCastleObjectLayer;
	}

	public void setStaticGuardSpawnStarterCastleObjectLayer(MapLayer staticGuardSpawnStarterCastleObjectLayer) {
		this.staticGuardSpawnStarterCastleObjectLayer = staticGuardSpawnStarterCastleObjectLayer;
	}

	public MapObjects getVillagerSpawnStarterCastleSpawnObjects() {
		return villagerSpawnStarterCastleSpawnObjects;
	}

	public void setVillagerSpawnStarterCastleSpawnObjects(MapObjects villagerSpawnStarterCastleSpawnObjects) {
		this.villagerSpawnStarterCastleSpawnObjects = villagerSpawnStarterCastleSpawnObjects;
	}

	public MapLayer getVillagerSpawnStarterCastleObjectLayer() {
		return villagerSpawnStarterCastleObjectLayer;
	}

	public void setVillagerSpawnStarterCastleObjectLayer(MapLayer villagerSpawnStarterCastleObjectLayer) {
		this.villagerSpawnStarterCastleObjectLayer = villagerSpawnStarterCastleObjectLayer;
	}

	public MapObjects getGuardSpawnStarterCastleSpawnObjects() {
		return guardSpawnStarterCastleSpawnObjects;
	}

	public void setGuardSpawnStarterCastleSpawnObjects(MapObjects guardSpawnStarterCastleSpawnObjects) {
		this.guardSpawnStarterCastleSpawnObjects = guardSpawnStarterCastleSpawnObjects;
	}

	public MapLayer getGuardSpawnStarterCastleObjectLayer() {
		return guardSpawnStarterCastleObjectLayer;
	}

	public void setGuardSpawnStarterCastleObjectLayer(MapLayer guardSpawnStarterCastleObjectLayer) {
		this.guardSpawnStarterCastleObjectLayer = guardSpawnStarterCastleObjectLayer;
	}

	public MapLayer getAnimalSpawnPointObjectLayer() {
		return animalSpawnPointObjectLayer;
	}

	public void setAnimalSpawnPointObjectLayer(MapLayer animalSpawnPointObjectLayer) {
		this.animalSpawnPointObjectLayer = animalSpawnPointObjectLayer;
	}

	
	public MapObjects getAnimalSpawnPointObjects() {
		return animalSpawnPointObjects;
	}

	public void setAnimalSpawnPointObjects(MapObjects animalSpawnPointObjects) {
		this.animalSpawnPointObjects = animalSpawnPointObjects;
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

	public MapLayer getGuardSpawnLavaCastleObjectLayer() {
		return guardSpawnLavaCastleObjectLayer;
	}

	public void setGuardSpawnLavaCastleObjectLayer(MapLayer guardSpawnLavaCasleObjectLayer) {
		this.guardSpawnLavaCastleObjectLayer = guardSpawnLavaCasleObjectLayer;
	}

	public MapObjects getGuardSpawnLavaCastleSpawnObjects() {
		return guardSpawnLavaCastleSpawnObjects;
	}

	public void setGuardSpawnLavaCastleSpawnObjects(MapObjects guardSpawnStartCastleSpawnObjects) {
		this.guardSpawnLavaCastleSpawnObjects = guardSpawnStartCastleSpawnObjects;
	}

	public MapLayer getGuardSpawnMudBaseCastleObjectLayer() {
		return guardSpawnMudBaseCastleObjectLayer;
	}

	public void setGuardSpawnMudBaseCastleObjectLayer(MapLayer guardSpawnMudBaseCastleObjectLayer) {
		this.guardSpawnMudBaseCastleObjectLayer = guardSpawnMudBaseCastleObjectLayer;
	}

	public MapObjects getGuardSpawnMudBaseCastleSpawnObjects() {
		return guardSpawnMudBaseCastleSpawnObjects;
	}

	public void setGuardSpawnMudBaseCastleSpawnObjects(MapObjects guardSpawnMudBaseCastleSpawnObjects) {
		this.guardSpawnMudBaseCastleSpawnObjects = guardSpawnMudBaseCastleSpawnObjects;
	}

	public MapLayer getEnemySpawnLavaCastleLowObjectLayer() {
		return enemySpawnLavaZoneLowObjectLayer;
	}

	public void setEnemySpawnLavaCastleLowObjectLayer(MapLayer enemySpawnLavaCastleLowObjectLayer) {
		this.enemySpawnLavaZoneLowObjectLayer = enemySpawnLavaCastleLowObjectLayer;
	}

	public MapObjects getEnemySpawnLavaCastleLowObjects() {
		return enemySpawnLavaZoneLowObjects;
	}

	public void setEnemySpawnLavaCastleLowObjects(MapObjects enemySpawnLavaCastleLowObjects) {
		this.enemySpawnLavaZoneLowObjects = enemySpawnLavaCastleLowObjects;
	}

	public MapLayer getEnemySpawnMudZoneLowObjectLayer() {
		return enemySpawnMudZoneLowObjectLayer;
	}

	public void setEnemySpawnMudZoneLowObjectLayer(MapLayer enemySpawnMudZoneLowObjectLayer) {
		this.enemySpawnMudZoneLowObjectLayer = enemySpawnMudZoneLowObjectLayer;
	}

	public MapObjects getEnemySpawnMudZoneLowObjects() {
		return enemySpawnMudZoneLowObjects;
	}

	public void setEnemySpawnMudZoneLowObjects(MapObjects enemySpawnMudZoneLowObjects) {
		this.enemySpawnMudZoneLowObjects = enemySpawnMudZoneLowObjects;
	}

	public MapObjects getEnemySpawnJungleLowObjects() {
		return enemySpawnJungleZoneLowObjects;
	}

	public void setEnemySpawnJungleLowObjects(MapObjects enemySpawnJungleLowObjects) {
		this.enemySpawnJungleZoneLowObjects = enemySpawnJungleLowObjects;
	}

	public MapLayer getEnemySpawnJungleLowObjectLayer() {
		return enemySpawnJungleZoneLowObjectLayer;
	}

	public void setEnemySpawnJungleLowObjectLayer(MapLayer enemySpawnJungleLowObjectLayer) {
		this.enemySpawnJungleZoneLowObjectLayer = enemySpawnJungleLowObjectLayer;
	}
	
}
