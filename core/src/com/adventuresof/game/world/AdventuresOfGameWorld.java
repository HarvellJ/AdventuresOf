package com.adventuresof.game.world;

import java.util.ArrayList;
import java.util.Random;

import com.adventuresof.game.character.entities.Baliff;
import com.adventuresof.game.character.entities.BloodElf;
import com.adventuresof.game.character.entities.Commander;
import com.adventuresof.game.character.entities.Dragon;
import com.adventuresof.game.character.entities.FlyingDragon;
import com.adventuresof.game.character.entities.HellHound;
import com.adventuresof.game.character.entities.Horse;
import com.adventuresof.game.character.entities.KingsAide;
import com.adventuresof.game.character.entities.Knight;
import com.adventuresof.game.character.entities.KnightGold;
import com.adventuresof.game.character.entities.LavaBoss;
import com.adventuresof.game.character.entities.LavaCastleGuard;
import com.adventuresof.game.character.entities.LavaRanger;
import com.adventuresof.game.character.entities.MagicMerchant;
import com.adventuresof.game.character.entities.MudCastleGuard;
import com.adventuresof.game.character.entities.NPC;
import com.adventuresof.game.character.entities.NightElfFemale;
import com.adventuresof.game.character.entities.NightElfMale;
import com.adventuresof.game.character.entities.Phoenix;
import com.adventuresof.game.character.entities.PlayerCompanion;
import com.adventuresof.game.character.entities.Prince;
import com.adventuresof.game.character.entities.Prisoner;
import com.adventuresof.game.character.entities.Queen;
import com.adventuresof.game.character.entities.ShadowKnight;
import com.adventuresof.game.character.entities.StarterCastleGuard;
import com.adventuresof.game.character.entities.TwoHeadedDragon;
import com.adventuresof.game.common.enums.Direction;
import com.adventuresof.game.item.Item;
import com.adventuresof.game.item.ItemEnum;
import com.adventuresof.helpers.SoundManager;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Class contains the information about the world
 * 
 */
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
		this.detectCollisionWithTriggers();
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

	protected void spawnQuestItems() {
		for (RectangleMapObject rectangleObject : this.map.getRareItemSpawnPointObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			if (rectangleObject.getName().equals("gem")) {
				Item item = new Item(ItemEnum.GEM);
				item.setPositionX(rectangle.x);
				item.setPositionY(rectangle.y);
				item.setHitbox(new Rectangle(rectangle.x, rectangle.y, 50f, 50f));
				this.items.add(item);	
			}
			if (rectangleObject.getName().equals("crown")) {
				Item item = new Item(ItemEnum.CROWN);
				item.setPositionX(rectangle.x);
				item.setPositionY(rectangle.y);
				item.setHitbox(new Rectangle(rectangle.x, rectangle.y, 50f, 50f));
				this.items.add(item);	
			}
		}
	}
	
	private void spawnNPCsJungleZone() {
		// spawn low level NPCs
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnJungleLowObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new HellHound(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Hell Hound", false, null, true));	
		}
		// spawn med level NPCs
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnJungleZoneMedObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new BloodElf(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Blood Elf", false, null, true));	
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
			this.NPCs.add(new TwoHeadedDragon(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Twin-headed Dragon", false, null, true));	
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
				this.NPCs.add(new FlyingDragon(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Basilisk", false, null, true));				
		}
	}

	private void spawnNPCsLavaZone() {
		// low levels
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLavaCastleLowObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();	
				this.NPCs.add(new Knight(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Lava Knight", false, null, true));							
		}
		// med levels
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLavaZoneMedObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
				this.NPCs.add(new LavaRanger(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Lava Ranger", false, null, true));	
		}
		// high levels
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLavaZoneHighObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new KnightGold(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Gilded Knight", false, null, true));							
		}
		// boss
		for (RectangleMapObject rectangleObject : this.map.getEnemySpawnLavaZoneBossObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.NPCs.add(new LavaBoss(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Apollo", false, null, true));							
		}
	}

	/**
	 * Instantiates the guard NPC's into the game
	 */
	private void spawnFriendlyNPCs() {
		Random r = new Random();
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnLavaCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			this.NPCs.add(new LavaCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y,"Guard", false, null, false, null));							
		}
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnMudBaseCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			this.NPCs.add(new MudCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Passageway Guard", false, null, true, "Bounty Hunter"));							
		}
		for (RectangleMapObject rectangleObject : this.map.getGuardSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
				this.NPCs.add(new StarterCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Guard", true, null, true, "Dragon Slayer"));			
		}	
		for (RectangleMapObject rectangleObject : this.map.getVillagerSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if(r.nextInt(2) > 0) {
				this.NPCs.add(new NightElfMale(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Villager", false, null, false));	
			}else {
				this.NPCs.add(new NightElfFemale(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Villager", false, null, false));	
			}
		}
		for (RectangleMapObject rectangleObject : this.map.getStaticGuardSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			StarterCastleGuard staticGuard = new StarterCastleGuard(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Guard", false, null, true, null);
			staticGuard.setCharacterDirection(Direction.left);
			this.NPCs.add(staticGuard);				
		}		
		for (RectangleMapObject rectangleObject : this.map.getStaticShadowKnightSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			ShadowKnight staticShadowKnight = new ShadowKnight(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "", false, null, true);
			staticShadowKnight.setCharacterDirection(Direction.left);
			this.NPCs.add(staticShadowKnight);				
		}	
		for (RectangleMapObject rectangleObject : this.map.getStaticCommanderSpawnStarterCastleSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			Commander staticCommander = new Commander(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Commander", false, null, true, "Elven Trouble");
			staticCommander.setCharacterDirection(Direction.right);
			this.NPCs.add(staticCommander);				
		}
		for (RectangleMapObject rectangleObject : this.map.getBaliffSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			Baliff baliff = new Baliff(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Baliff", false, null, true, "Bounty Hunter");
			baliff.setCharacterDirection(Direction.right);
			this.NPCs.add(baliff);				
		}
		for (RectangleMapObject rectangleObject : this.map.getQueenSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			Queen queen = new Queen(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Queen", false, null, true, "Stolen Crown");
			queen.setCharacterDirection(Direction.right);
			this.NPCs.add(queen);				
		}
		for (RectangleMapObject rectangleObject : this.map.getKingsaideSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			KingsAide kingsaide = new KingsAide(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Kings Aide", false, null);
			kingsaide.setCharacterDirection(Direction.right);
			this.NPCs.add(kingsaide);				
		}
		for (RectangleMapObject rectangleObject : this.map.getMagicmerchantSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			MagicMerchant magicmerchant = new MagicMerchant(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Magic Merchant", false, null, true, "The Magic Gem");
			magicmerchant.setCharacterDirection(Direction.right);
			this.NPCs.add(magicmerchant);				
		}
		for (RectangleMapObject rectangleObject : this.map.getPrinceSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			Prince prince = new Prince(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Prince", false, null, true, "Stolen Crown");
			prince.setCharacterDirection(Direction.right);
			this.NPCs.add(prince);				
		}
		for (RectangleMapObject rectangleObject : this.map.getPrisonerSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			Prisoner prisoner = new Prisoner(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, "Prisoner", false, null, true, "Bounty Hunter");
			prisoner.setCharacterDirection(Direction.right);
			this.NPCs.add(prisoner);				
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
				if (rectangleObject.getName().equals("wasteLand")) {
					// render guidance text to screen
					if(SoundManager.currentlyPlaying != "audio/music/wastelandShowdown.mp3")
						SoundManager.playMusic("audio/music/wastelandShowdown.mp3");
				}
				else if (rectangleObject.getName().equals("LavaCastle")) {
					// render guidance text to screen
					if(SoundManager.currentlyPlaying != "audio/music/heroicDemise.mp3")
						SoundManager.playMusic("audio/music/heroicDemise.mp3");
				}
				else if (rectangleObject.getName().equals("Jungle")) {
					// render guidance text to screen
					if(SoundManager.currentlyPlaying != "audio/music/noMoreMagic.mp3")
						SoundManager.playMusic("audio/music/noMoreMagic.mp3");
				}
			}
		}
	}
}
