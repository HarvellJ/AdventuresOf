package com.adventuresof.game.world;

import java.util.ArrayList;
import java.util.Arrays;

import com.adventuresof.game.character.Enemy;
import com.adventuresof.game.character.LavaCastleGuard;
import com.adventuresof.game.character.MudCastleGuard;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.PlayerCompanion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class TutorialIsland extends GameWorld{

	protected PlayerCompanion playerCompanion;
	private int count;
	
	public TutorialIsland() {
		super("map//MainWorld.tmx");
		
		this.setPlayerCompanion(new PlayerCompanion(map.getAccessibleMapLayer(), this.player));
		
	}	
	

	protected void spawnNPCs() {
		this.NPCs = new ArrayList<NPC>();
		this.spawnEnemies();
		this.spawnGuards();
	}
	
	public void update(float delta) {
		super.update(delta, this.NPCs);
		// move player companion
		//playerCompanion.move();
		//playerCompanion.update();
	}
	
	/**
	 * Instantiates the enemy NPCs into the game
	 */
	private void spawnEnemies() {
		
		ArrayList<String> conversation = new ArrayList<String>(Arrays.asList("They have sent you to kill us haven't they...", "Well you won't get away with this without a fight..."));
		
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLevel1Objects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new Enemy(map.getAccessibleMapLayer(), "characters//knight.png", rectangle.x, rectangle.y, "Bandit", true, conversation, false));							
		}
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLeve12Objects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new Enemy(map.getAccessibleMapLayer(), "characters//knight.png", rectangle.x, rectangle.y, "Rogue", false, null, true));							
		}
	}
	
	/**
	 * Instantiates the guard NPC's into the game
	 */
	private void spawnGuards() {
		
		ArrayList<String> conversation = new ArrayList<String>( Arrays.asList("Your mission...", "should you choose to accept it...", "Is to kill all of the bandits outside of this castle...", "You will find them to the South-West."));
		
		
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnStartCasleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
				// if statement used because direction of guards depends on spawn zone			
					this.NPCs.add(new LavaCastleGuard(map.getAccessibleMapLayer(), rectangle.x, rectangle.y,"Guard", true, conversation));							
		}
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnMudBaseCasleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
				// if statement used because direction of guards depends on spawn zone			
					this.NPCs.add(new MudCastleGuard(map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Civilian", true, conversation));							
		}
	}
	
	protected void detectCollisionWithTriggers() {
		for (RectangleMapObject rectangleObject : this.map.getTriggerObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, player.getHitBox())) {
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
	
	private void detectImpassibleObjectCollisions() {
		for (RectangleMapObject rectangleObject : map.getImpassibleObjects().getByType(RectangleMapObject.class)) {

			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, player.getHitBox())) {
				// do something
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
