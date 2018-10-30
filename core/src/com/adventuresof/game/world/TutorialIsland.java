package com.adventuresof.game.world;

import java.util.ArrayList;

import com.adventuresof.game.character.Direction;
import com.adventuresof.game.character.Enemy;
import com.adventuresof.game.character.Guard;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.character.PlayerCompanion;
import com.adventuresof.game.inventory.Item;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class TutorialIsland extends GameWorld{

	protected PlayerCompanion playerCompanion;
	
	public TutorialIsland() {
		super("Map.tmx");
		
		// load the players
		this.setPlayer(new Player(map.getAccessibleMapLayer()));
		
		// load the guard NPCs
		this.spawnNPCs();
		
		this.setPlayerCompanion(new PlayerCompanion(map.getAccessibleMapLayer(), this.player));
		
		// add map items
		this.items.add(Item.values()[0]);
		this.items.add(Item.values()[1]);
		this.items.add(Item.values()[2]);
	}	
	
	public void update(float delta) {
		//this.detectObjectCollisions();
		// move player
		player.update();
		// move player companion
		playerCompanion.move();
		playerCompanion.update();
		// move guard NPCs
		this.updateGuards();
		// move and update enemies
		this.moveNPCs();
		this.updateGuards();
		// now check for any collisions following update calls to characters
		this.detectCollectionOfItemObjects();
		this.detectCollisionWithTriggers();
	}
	
	private void moveNPCs() {
		for (NPC npc : this.NPCs) {
			npc.move();
		}
	}
	
	private void updateGuards() {
		for (NPC npc : this.NPCs) {
			npc.update();
		}
	}

	protected void spawnNPCs() {
		this.NPCs = new ArrayList<NPC>();
		this.spawnEnemies();
		this.spawnGuards();
	}
	
	/**
	 * Instantiates the enemy NPCs into the game
	 */
	private void spawnEnemies() {
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new Enemy(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y));							
		}
	}
	
	/**
	 * Instantiates the guard NPC's into the game
	 */
	private void spawnGuards() {
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
				// if statement used because direction of guards depends on spawn zone
				if (rectangleObject.getName().equals("guardSpawn1_StartZone") || rectangleObject.getName().equals("guardSpawn2_StartZone")
						|| rectangleObject.getName().equals("guardSpawn1_StartZone")) {
					this.NPCs.add(new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.left));
				}	
				else if(rectangleObject.getName().equals("guardSpawn1_ItemZone") || rectangleObject.getName().equals("guardSpawn2_ItemZone")) {
					this.NPCs.add(new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.down));
				}			
				else if(rectangleObject.getName().equals("guardSpawn3_ItemZone") || rectangleObject.getName().equals("guardSpawn4_ItemZone")) {
					this.NPCs.add(new Guard(map.getAccessibleMapLayer(), "knight_animation_sheet.png", rectangle.x, rectangle.y, Direction.right));
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
						this.playerCompanion.addMessageToMessageQueue("For melee attack, press 1.");

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
					for(int i = 0; i < items.size(); i++) {
						if(items.get(i) != null && items.get(i).equals(Item.SHIELD)) {
							// add it to inventory and remove from game world
							player.addItemToInventory(items.get(i));
							items.remove(i);
						}				
					}
				}
				else if(rectangleObject.getName().equals("armorSpawn")){
					// check if the item exists in the game world
					for(int i = 0; i < items.size(); i++) {
						if(items.get(i) != null && items.get(i).equals(Item.ARMOR)) {
							// add it to inventory and remove from game world
							player.addItemToInventory(items.get(i));
							items.remove(i);
						}
					}
				}
				else if(rectangleObject.getName().equals("swordSpawn")){
					// check if the item exists in the game world
					for(int i = 0; i < items.size(); i++) {
						if(items.get(i) != null && items.get(i).equals(Item.SWORD)) {
							// add it to inventory and remove from game world
							player.addItemToInventory(items.get(i));
							items.remove(i);
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
					for(int i = 0; i < items.size(); i++) {
						if(items.get(i).equals(Item.SHIELD)) {
							items.remove(i);
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