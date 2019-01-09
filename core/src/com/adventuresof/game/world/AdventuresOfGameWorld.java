package com.adventuresof.game.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.adventuresof.game.character.BloodElf;
import com.adventuresof.game.character.Commander;
import com.adventuresof.game.character.Direction;
import com.adventuresof.game.character.Dragon;
import com.adventuresof.game.character.Horse;
import com.adventuresof.game.character.HorseKnight;
import com.adventuresof.game.character.Knight;
import com.adventuresof.game.character.KnightGold;
import com.adventuresof.game.character.LavaCastleGuard;
import com.adventuresof.game.character.MudCastleGuard;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.NightElfFemale;
import com.adventuresof.game.character.NightElfMale;
import com.adventuresof.game.character.Phoenix;
import com.adventuresof.game.character.PlayerCompanion;
import com.adventuresof.game.character.ShadowKnight;
import com.adventuresof.game.character.StarterCastleGuard;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class AdventuresOfGameWorld extends GameWorld{

	protected PlayerCompanion playerCompanion;

	public AdventuresOfGameWorld() {
		super("map//MainWorld.tmx");

		this.setPlayerCompanion(new PlayerCompanion(this, map.getAccessibleMapLayer(), this.player));

	}	

	protected void spawnNPCs() {
		this.NPCs = new ArrayList<NPC>();
		this.spawnHostileNPCs();
		this.spawnFriendlyNPCs();
		this.spawnAnimalNPCs();
	}

	public void update(float delta) {
		super.update(delta, this.NPCs);
		// move player companion
		//playerCompanion.move(this.player);
		playerCompanion.update();

		// quest related stuff
		if(this.checkBandits(NPCs) == null) {
			for (NPC npc : NPCs) {
				if (npc.getName().equals("Guard")) {

					ArrayList<String> conversation = new ArrayList<String>(Arrays.asList("Well done! You have killed the bandits and completed the quest!"));  

					npc.setConversation(conversation);
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

	public NPC checkBandits(ArrayList<NPC> NPCs) {

		for (NPC npc : NPCs) {
			if (npc.getName().equals("Bandit")) {
				return npc;
			}
		}
		return null;
	}

	/**
	 * Instantiates the enemy NPCs into the game
	 */
	private void spawnHostileNPCs() {

		ArrayList<String> conversation = new ArrayList<String>(Arrays.asList("They have sent you to kill us haven't they...", "Well you won't get away with this without a fight..."));
		Random r = new Random();
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLevel1Objects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			if(r.nextInt(5) >= 2) {
				this.NPCs.add(new Knight(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Knight", true, conversation, false));							
			}		
			else {
				this.NPCs.add(new KnightGold(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Knight", true, conversation, false));							

			}

		}
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLeve12Objects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			if(r.nextInt(5) >= 2) {
				this.NPCs.add(new Dragon(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Dragon", false, null, true));	
			}
			else {
				this.NPCs.add(new KnightGold(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Knight", false, null, true));							
			}
		}
		for (RectangleMapObject rectangleObject : this.map.setEnemySpawnLevel3Objects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			if(r.nextInt(5) >= 2) {
				this.NPCs.add(new Phoenix(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Phoenix", false, null, true));	
			}else {
				this.NPCs.add(new BloodElf(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Blood Elf", false, null, true));	
			}

		}
	}

	/**
	 * Instantiates the guard NPC's into the game
	 */
	private void spawnFriendlyNPCs() {
		Random r = new Random();
		ArrayList<String> conversation = new ArrayList<String>( Arrays.asList("Your mission...", "should you choose to accept it...", "Is to kill all of the bandits outside of this castle...", "You will find them to the South-West."));
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnLavaCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			this.NPCs.add(new LavaCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y,"Geoff", true, conversation, true, "Slayer Quest"));							
		}
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnMudBaseCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			this.NPCs.add(new MudCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", true, conversation, true, "Collection Quest"));							
		}
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if(r.nextInt(2) > 0) {
				this.NPCs.add(new StarterCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", true, conversation, false));	
			}else {
				this.NPCs.add(new StarterCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", true, conversation, false));	
			}
		}	
		for (RectangleMapObject rectangleObject : this.map.getVillagerSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if(r.nextInt(2) > 0) {
				this.NPCs.add(new NightElfMale(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", true, conversation, false));	
			}else {
				this.NPCs.add(new NightElfFemale(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", true, conversation, false));	
			}
		}
		for (RectangleMapObject rectangleObject : this.map.getStaticGuardSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			StarterCastleGuard staticGuard = new StarterCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", true, conversation, true);
			staticGuard.setCharacterDirection(Direction.left);
			this.NPCs.add(staticGuard);				
		}		
		for (RectangleMapObject rectangleObject : this.map.getStaticShadowKnightSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			ShadowKnight staticShadowKnight = new ShadowKnight(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", true, conversation, true);
			staticShadowKnight.setCharacterDirection(Direction.left);
			this.NPCs.add(staticShadowKnight);				
		}	
		for (RectangleMapObject rectangleObject : this.map.getStaticCommanderSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			Commander staticCommander = new Commander(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", true, conversation, true);
			staticCommander.setCharacterDirection(Direction.right);
			this.NPCs.add(staticCommander);				
		}		
	}

	/**
	 * Instantiates the guard NPC's into the game
	 */
	private void spawnAnimalNPCs() {
		for (RectangleMapObject rectangleObject : this.map.getAnimalSpawnPointObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// if statement used because direction of guards depends on spawn zone			
			this.NPCs.add(new Horse(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y,"", false, null, false));							
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
}
