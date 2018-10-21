package com.adventuresof.game.world;

import com.adventuresof.game.character.Direction;
import com.adventuresof.game.character.Enemy;
import com.adventuresof.game.character.Guard;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.character.PlayerCompanion;
import com.adventuresof.game.inventory.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.AdventuresOfGame;

public class GameWorld {

	// The map for this game world
	private Map map; 

	private Player player;
	private PlayerCompanion playerCompanion;
	private Guard startZoneGuard1;
	private Guard startZoneGuard2;
	public Guard itemZoneGuard1;
	public Guard itemZoneGuard2;
	public Guard itemZoneGuard3;
	public Guard itemZoneGuard4;
	
	public Enemy enemy1;
	public Enemy enemy2;
	public Enemy enemy3;
	public Enemy enemy4;
	public Enemy enemy5;

	
	private Item[] items;// an array of in-game items - representing the items that exist in the world
	
	public GameWorld() {

		// load the map
		map = new Map("Map.tmx");
		
		// load the players
		this.setPlayer(new Player(map.getAccessibleMapLayer()));
		
		// load the guard NPCs
		this.loadGuardNPCs();
		
		this.loadEnemyNPCs();
		
		this.setPlayerCompanion(new PlayerCompanion(map.getAccessibleMapLayer(), this.player));
		
		// load map items
		this.setItems(new Item[] {Item.values()[0], Item.values()[1], Item.values()[2]});		
	}	
	
	public Guard getStartZoneGuard1() {
		return startZoneGuard1;
	}

	public void setStartZoneGuard1(Guard startZoneGuard1) {
		this.startZoneGuard1 = startZoneGuard1;
	}

	public Guard getStartZoneGuard2() {
		return startZoneGuard2;
	}

	public void setStartZoneGuard2(Guard startZoneGuard2) {
		this.startZoneGuard2 = startZoneGuard2;
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
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
	
	public void update(float delta) {
		//this.detectObjectCollisions();
		// move player
		player.update();
		// move player companion
		playerCompanion.move();
		playerCompanion.update();
		// move guard NPCs
		startZoneGuard1.update();
		startZoneGuard2.update();
		itemZoneGuard1.update();
		itemZoneGuard2.update();
		itemZoneGuard3.update();
		itemZoneGuard4.update();
		// move enemies
		enemy1.move();
		enemy2.move();
		enemy3.move();
		enemy4.move();
		enemy5.move();
		enemy1.update();
		enemy2.update();
		enemy3.update();
		enemy4.update();
		enemy5.update();

		this.detectCollectionOfItemObjects();
		this.detectCollisionWithTriggers();
	}

	private void loadEnemyNPCs() {
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
				if (rectangleObject.getName().equals("enemySpawn1")) {
					this.enemy1 = new Enemy(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y);
				}
				else if(rectangleObject.getName().equals("enemySpawn2")) {
					this.enemy2 = new Enemy(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y);
				}	
				else if(rectangleObject.getName().equals("enemySpawn3")) {
					this.enemy3 = new Enemy(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y);
				}	
				else if(rectangleObject.getName().equals("enemySpawn4")) {
					this.enemy4 = new Enemy(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y);
				}	
				else if(rectangleObject.getName().equals("enemySpawn5")) {
					this.enemy5 = new Enemy(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y);
				}	
		}
	}
	
	private void loadGuardNPCs() {
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
				if (rectangleObject.getName().equals("guardSpawn1_StartZone")) {
					this.startZoneGuard1 = new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.left);
				}
				else if(rectangleObject.getName().equals("guardSpawn2_StartZone")) {
					this.startZoneGuard2 = new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.left);
				}		
				else if (rectangleObject.getName().equals("guardSpawn1_StartZone")) {
					this.startZoneGuard1 = new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.left);
				}
				else if(rectangleObject.getName().equals("guardSpawn1_ItemZone")) {
					this.itemZoneGuard1 = new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.down);
				}
				else if(rectangleObject.getName().equals("guardSpawn2_ItemZone")) {
					this.itemZoneGuard2 = new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.down);
				}
				else if(rectangleObject.getName().equals("guardSpawn3_ItemZone")) {
					this.itemZoneGuard3 = new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.right);
				}
				else if(rectangleObject.getName().equals("guardSpawn4_ItemZone")) {
					this.itemZoneGuard4 = new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.right);
				}
		}
	}
	
	private void detectCollisionWithTriggers() {
		for (RectangleMapObject rectangleObject : this.map.getTriggerObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
				if (rectangleObject.getName().equals("startZoneMovementTrigger")) {
					// render guidance text to screen
					if(!this.player.hasDiscoveredZone(GameZone.STARTZONE)) {
						this.player.addDiscoveredZone(GameZone.STARTZONE);
						this.playerCompanion.addMessageToMessageQueue("Good. Now lets move east.");
					}
				}
				else if (rectangleObject.getName().equals("itemZoneTrigger")) {
					// render guidance text to screen
					if(!this.player.hasDiscoveredZone(GameZone.ITEMZONE)) {
						this.player.addDiscoveredZone(GameZone.ITEMZONE);
						this.playerCompanion.addMessageToMessageQueue("On this part of the island, you will find items to collect.");
						this.playerCompanion.addMessageToMessageQueue("To collect an item, run over it. It will then appear in your inventory.");
						this.playerCompanion.addMessageToMessageQueue("You can move the inventory around the screen by clicking and dragging it.");
					}
				}
				else if (rectangleObject.getName().equals("itemZoneCompleteTrigger")) {
					// render guidance text to screen
					if(!this.player.hasDiscoveredZone(GameZone.ITEMZONECOMPLETIONZONE)) {
						this.player.addDiscoveredZone(GameZone.ITEMZONECOMPLETIONZONE);
						this.playerCompanion.addMessageToMessageQueue("Great, you've found all of the items. When you're ready, move north to the combat arena.");
					}
				}
				else if (rectangleObject.getName().equals("combatZoneTrigger")) {
					// render guidance text to screen
					if(!this.player.hasDiscoveredZone(GameZone.COMBATARENA)) {
						this.player.addDiscoveredZone(GameZone.COMBATARENA);
						this.playerCompanion.addMessageToMessageQueue("This is the combat arena. Here you can practice your combat skills.");
						this.playerCompanion.addMessageToMessageQueue("For melee attack, press 1. For range attack, press 2");

					}
				}
			}
		}
	}
	
	// detect player collision with items. If detection occurs, move from world inventory into player inventory
	private void detectCollectionOfItemObjects() {
		for (RectangleMapObject rectangleObject : this.map.getSpawnPointObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
				// check each of the spawns
				if(rectangleObject.getName().equals("shieldSpawn")){
					// check if the item exists in the game world
					for(int i = 0; i < items.length; i++) {
						if(items[i] != null && items[i].equals(Item.SHIELD)) {
							// add it to inventory and remove from game world
							player.addItemToInventory(items[i]);
							items[i] = null;
						}				
					}
				}
				else if(rectangleObject.getName().equals("armorSpawn")){
					// check if the item exists in the game world
					for(int i = 0; i < items.length; i++) {
						if(items[i] != null && items[i].equals(Item.ARMOR)) {
							// add it to inventory and remove from game world
							player.addItemToInventory(items[i]);
							items[i] = null;
						}
					}
				}
				else if(rectangleObject.getName().equals("swordSpawn")){
					// check if the item exists in the game world
					for(int i = 0; i < items.length; i++) {
						if(items[i] != null && items[i].equals(Item.SWORD)) {
							// add it to inventory and remove from game world
							player.addItemToInventory(items[i]);
							items[i] = null;
						}
					}
				}
			}	 
		}	    
	}
	
	private void detectImpassibleObjectCollisions() {
		for (RectangleMapObject rectangleObject : map.getImpassibleObjects().getByType(RectangleMapObject.class)) {

			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, player.getBoundingRectangle())) {
				if(rectangleObject.getName().equals("shieldSpawn")){
					for(int i = 0; i < items.length; i++) {
						if(items[i].equals(Item.SHIELD)) {
							items[i] = null;
						}
					}
				}
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
