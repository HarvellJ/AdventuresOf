package com.adventuresof.game.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.adventuresof.game.character.entities.BloodElf;
import com.adventuresof.game.character.entities.Commander;
import com.adventuresof.game.character.entities.Dragon;
import com.adventuresof.game.character.entities.FlyingDragon;
import com.adventuresof.game.character.entities.HellHound;
import com.adventuresof.game.character.entities.Horse;
import com.adventuresof.game.character.entities.HorseKnight;
import com.adventuresof.game.character.entities.Knight;
import com.adventuresof.game.character.entities.KnightGold;
import com.adventuresof.game.character.entities.LavaBoss;
import com.adventuresof.game.character.entities.LavaCastleGuard;
import com.adventuresof.game.character.entities.LavaRanger;
import com.adventuresof.game.character.entities.MudCastleGuard;
import com.adventuresof.game.character.entities.NPC;
import com.adventuresof.game.character.entities.NightElfFemale;
import com.adventuresof.game.character.entities.NightElfMale;
import com.adventuresof.game.character.entities.Phoenix;
import com.adventuresof.game.character.entities.PlayerCompanion;
import com.adventuresof.game.character.entities.ShadowKnight;
import com.adventuresof.game.character.entities.StarterCastleGuard;
import com.adventuresof.game.character.entities.TwoHeadedDragon;
import com.adventuresof.game.common.enums.Direction;
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
		this.spawnAnimatedMapObjects();
	}

	public void update(float delta) {
		super.update(delta, this.NPCs);
		// move player companion
		//playerCompanion.move(this.player);
		playerCompanion.update();
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

	protected void spawnAnimatedMapObjects() {
		for (RectangleMapObject rectangleObject : this.map.getCampFireSpawnPointObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			this.animatedMapObjects.add(new Campfire(rectangle.x, rectangle.y));							
		}
	}

	protected void updateAnimatedMapObjects() {
		for(AnimatedMapObject animatedMapObject : this.animatedMapObjects) {
			animatedMapObject.update();
		}
	}

	/**
	 * Instantiates the enemy NPCs into the game
	 */
	private void spawnHostileNPCs() {
		this.spawnNPCsJungleZone();
		this.spawnNPCsMudZone();
		this.spawnNPCsLavaZone();
	}

	private void spawnNPCsJungleZone() {
		// spawn low level NPCs
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnJungleLowObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new HellHound(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "HellHound", false, null, true));	
		}
		// spawn med level NPCs
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnJungleZoneMedObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new BloodElf(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "BloodElf", false, null, true));	
		}
		// spawn high level NPCs
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnJungleZoneHighObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new Phoenix(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Phoenix", false, null, true));	
		}
		
		// spawn boss level NPCs
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnJungleZoneBossObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new TwoHeadedDragon(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "JungleZoneBoss", false, null, true));	
		}
	}

	private void spawnNPCsMudZone() {
		// low levels
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnMudZoneLowObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
				this.NPCs.add(new Dragon(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Dragon", false, null, true));	
			
		}
		// med levels
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnMudZoneMedObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
				this.NPCs.add(new Dragon(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Dragon", false, null, true));	
		}
		// high levels
		
		// boss
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnMudZoneBossObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
				this.NPCs.add(new FlyingDragon(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "MudZoneBoss", false, null, true));				
		}
	}

	private void spawnNPCsLavaZone() {
		// low levels
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLavaCastleLowObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();	
				this.NPCs.add(new Knight(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Knight", false, null, true));							
		}
		// med levels
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLavaZoneMedObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
				this.NPCs.add(new LavaRanger(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "LavaRanger", false, null, true));	
		}
		// high levels
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLavaZoneHighObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new KnightGold(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "GildedKnight", false, null, true));							
		}
		// boss
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLavaZoneBossObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new LavaBoss(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "LavaZoneBoss", false, null, true));							
		}
	}

	/**
	 * Instantiates the guard NPC's into the game
	 */
	private void spawnFriendlyNPCs() {
		Random r = new Random();
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnLavaCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			this.NPCs.add(new LavaCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y,"Geoff", false, null, true, "Slayer Quest"));							
		}
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnMudBaseCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			this.NPCs.add(new MudCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", false, null, true, "Collection Quest"));							
		}
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if(r.nextInt(2) > 0) {
				this.NPCs.add(new StarterCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", false, null, false));	
			}else {
				this.NPCs.add(new StarterCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", false, null, false));	
			}
		}	
		for (RectangleMapObject rectangleObject : this.map.getVillagerSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if(r.nextInt(2) > 0) {
				this.NPCs.add(new NightElfMale(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", false, null, false));	
			}else {
				this.NPCs.add(new NightElfFemale(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", false, null, false));	
			}
		}
		for (RectangleMapObject rectangleObject : this.map.getStaticGuardSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			StarterCastleGuard staticGuard = new StarterCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", false, null, true);
			staticGuard.setCharacterDirection(Direction.left);
			this.NPCs.add(staticGuard);				
		}		
		for (RectangleMapObject rectangleObject : this.map.getStaticShadowKnightSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			ShadowKnight staticShadowKnight = new ShadowKnight(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Luke", false, null, true);
			staticShadowKnight.setCharacterDirection(Direction.left);
			this.NPCs.add(staticShadowKnight);				
		}	
		for (RectangleMapObject rectangleObject : this.map.getStaticCommanderSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			Commander staticCommander = new Commander(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Commander", false, null, true);
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
