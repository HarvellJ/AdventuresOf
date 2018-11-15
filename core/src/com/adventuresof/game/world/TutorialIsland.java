package com.adventuresof.game.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.adventuresof.game.character.BloodElf;
import com.adventuresof.game.character.Dragon;
import com.adventuresof.game.character.HorseKnight;
import com.adventuresof.game.character.Knight;
import com.adventuresof.game.character.KnightGold;
import com.adventuresof.game.character.LavaCastleGuard;
import com.adventuresof.game.character.MudCastleGuard;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Phoenix;
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
		//playerCompanion.move(this.player);
		playerCompanion.update();
	}

	/**
	 * Instantiates the enemy NPCs into the game
	 */
	private void spawnEnemies() {

		ArrayList<String> conversation = new ArrayList<String>(Arrays.asList("They have sent you to kill us haven't they...", "Well you won't get away with this without a fight..."));
		Random r = new Random();
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLevel1Objects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			if(r.nextInt(5) >= 2) {
				this.NPCs.add(new Knight(map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Bandit", false, null, true));							
			}		
			else {
				this.NPCs.add(new KnightGold(map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Bandit", false, null, true));							

			}

		}
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLeve12Objects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			if(r.nextInt(5) >= 2) {
				this.NPCs.add(new Dragon(map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Knight", false, conversation, true));	
			}
			else {
				this.NPCs.add(new KnightGold(map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Knight", true, conversation, true));							
			}
		}
		for (RectangleMapObject rectangleObject : this.map.setEnemySpawnLevel3Objects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			if(r.nextInt(5) >= 2) {
				this.NPCs.add(new Phoenix(map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Knight", false, null, true));	
			}else {
				this.NPCs.add(new BloodElf(map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Knight", false, null, true));	
			}

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
					}
				}
				else if (rectangleObject.getName().equals("itemZoneTrigger")) {
					// render guidance text to screen
					if(!this.player.hasDiscoveredZone(GameZone.ITEMZONE)) {
						this.player.addDiscoveredZone(GameZone.ITEMZONE);
						
					}
				}
				else if (rectangleObject.getName().equals("itemZoneCompleteTrigger")) {
					// render guidance text to screen
					if(!this.player.hasDiscoveredZone(GameZone.ITEMZONECOMPLETIONZONE)) {
						this.player.addDiscoveredZone(GameZone.ITEMZONECOMPLETIONZONE);
					}
				}
				else if (rectangleObject.getName().equals("combatZoneTrigger")) {
					// render guidance text to screen
					if(!this.player.hasDiscoveredZone(GameZone.COMBATARENA)) {
						this.player.addDiscoveredZone(GameZone.COMBATARENA);
					
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
