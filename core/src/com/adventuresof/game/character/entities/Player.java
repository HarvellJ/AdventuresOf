package com.adventuresof.game.character.entities;


import java.util.ArrayList;

import com.adventuresof.game.animation.CharacterAnimation;
import com.adventuresof.game.character.enums.CharacterClass;
import com.adventuresof.game.character.enums.CharacterLevel;
import com.adventuresof.game.common.enums.MovementSpeed;
import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.item.Item;
import com.adventuresof.game.item.ItemEffectEnum;
import com.adventuresof.game.quest.Quest;
import com.adventuresof.game.world.GameWorld;
import com.adventuresof.game.world.GameZone;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * A class to represent the Players character.
 * 
 */
public class Player extends GameCharacter{

	private Inventory inventory; // Stores items the player has collected
	private ArrayList<GameZone> discoveredZones; // Stores a list of discovered zones. Used for measuring player progress
	private ArrayList<Quest> quests;
	private ArrayList<NPC> npcsKilled = new ArrayList<NPC>();
	private String name;

	// texture sheet sizes
	private static final int MOVEMENT_SPRITE_SHEET_COLS = 4;
	private static final int MOVEMENT_SPRITE_SHEET_ROWS = 4;
	private static final int DEATH_SPRITE_SHEET_COLS = 4;
	private static final int DEATH_SPRITE_SHEET_ROWS = 4;
	private static final int COMBAT_SPRITE_SHEET_COLS = 4;
	private static final int COMBAT_SPRITE_SHEET_ROWS = 4;
	// movement values
	private static final int MOVE_LEFT_START_FRAME = 5;
	private static final int MOVE_LEFT_FRAMES = 3;
	private static final int MOVE_RIGHT_START_FRAME = 9;
	private static final int MOVE_RIGHT_FRAMES = 3;
	private static final int MOVE_DOWN_START_FRAME = 0;
	private static final int MOVE_DOWN_FRAMES = 3;
	private static final int MOVE_UP_START_FRAME = 13;
	private static final int MOVE_UP_FRAMES = 3;

	// idle values
	private static final int IDLE_LEFT_START_FRAME = 5;
	private static final int IDLE_RIGHT_START_FRAME = 9;
	private static final int IDLE_DOWN_START_FRAME = 0;
	private static final int IDLE_UP_START_FRAME = 13;

	// combat values
	private static final int ATTACK_LEFT_START_FRAME = 5;
	private static final int ATTCK_LEFT_FRAMES = 3;
	private static final int ATTACK_RIGHT_START_FRAME = 9;
	private static final int ATTACK_RIGHT_FRAMES = 3;
	private static final int ATTACK_DOWN_START_FRAME = 0;
	private static final int ATTACK_DOWN_FRAMES = 3;
	private static final int ATTACK_UP_START_FRAME = 13;
	private static final int ATTACK_UP_FRAMES = 3;

	// death values
	private static final int DEATH_ANIMATION_START_FRAME = 0;
	private static final int DEATH_ANIMATION_FRAMES = 3;
	
	public Player(GameWorld gameWorld,TiledMapTileLayer accessibleTiles, float startPosX, float startPosY, CharacterClass characterClass, String name, String spriteSheet) {
		super(
				gameWorld,
				accessibleTiles,
				startPosX, startPosY,
				false,
				50, 50,
				new CharacterAnimation(spriteSheet, spriteSheet, spriteSheet,
				MOVEMENT_SPRITE_SHEET_ROWS, MOVEMENT_SPRITE_SHEET_COLS,
				COMBAT_SPRITE_SHEET_ROWS, COMBAT_SPRITE_SHEET_COLS,
				DEATH_SPRITE_SHEET_ROWS, DEATH_SPRITE_SHEET_COLS,
				MOVE_LEFT_START_FRAME, MOVE_LEFT_FRAMES,
				MOVE_RIGHT_START_FRAME, MOVE_RIGHT_FRAMES,
				MOVE_DOWN_START_FRAME, MOVE_DOWN_FRAMES,
				MOVE_UP_START_FRAME, MOVE_UP_FRAMES,
				IDLE_LEFT_START_FRAME, IDLE_RIGHT_START_FRAME, IDLE_DOWN_START_FRAME, IDLE_UP_START_FRAME,
				ATTACK_LEFT_START_FRAME, ATTCK_LEFT_FRAMES, 
				ATTACK_RIGHT_START_FRAME, ATTACK_RIGHT_FRAMES,
				ATTACK_DOWN_START_FRAME, ATTACK_DOWN_FRAMES,
				ATTACK_UP_START_FRAME, ATTACK_UP_FRAMES,
				DEATH_ANIMATION_START_FRAME, DEATH_ANIMATION_FRAMES
				), MovementSpeed.NORMAL_FAST.getSpeed(), false, name, characterClass, CharacterLevel.normal);
				
		this.isHostile = true;
		this.inventory = new Inventory();
		this.discoveredZones = new ArrayList<GameZone>();
		this.quests = new ArrayList<Quest>();
		this.name = name;
		
		Quest slayerQuest = new Quest("SlayerQuest", this.name);
		Quest collectionQuest = new Quest("CollectionQuest", this.name);
		Quest dragonSlayerQuest = new Quest("DragonSlayerQuest", this.name);
		Quest bountyHunterQuest = new Quest("BountyHunterQuest", this.name);
		Quest magicGemQuest = new Quest("magicGemQuest", this.name);
		Quest killApollo = new Quest("KillApollo", this.name);
		Quest killTheBasilisk = new Quest("killTheBasilisk", this.name);
		Quest killTheTwinHeadedDragon = new Quest("killTheTwinHeadedDragon", this.name);
		
		quests.add(slayerQuest);
		quests.add(collectionQuest);
		quests.add(dragonSlayerQuest);
		quests.add(bountyHunterQuest);
		quests.add(magicGemQuest);
		quests.add(killApollo);
		quests.add(killTheBasilisk);
		quests.add(killTheTwinHeadedDragon);

	}

	public ArrayList<GameZone> getDiscoveredZones() {
		return discoveredZones;
	}

	public void setDiscoveredZones(ArrayList<GameZone> discoveredZones) {
		this.discoveredZones = discoveredZones;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void addItemToInventory(Item item) {
		this.inventory.store(item, 1);
		// if item is buff, display buff message
		this.addItemBuff(item);
	}
	
	public void render(SpriteBatch spriteBatch) {
		if(this.isDying) {
			this.showGameOverMessage(spriteBatch);
		}
		super.render(spriteBatch);
	}
	
	private void showGameOverMessage(SpriteBatch spriteBatch) {
		BitmapFont font = new BitmapFont(); 
		font.setColor(new Color(Color.RED));
		font.getData().setScale(2);
		String timeSurvived = "Time survived: " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds";
		String gameOverText = "GameOver";
		font.draw(spriteBatch, gameOverText, this.currentPosition.x - timeSurvived.length()*6/2, this.currentPosition.y + 130);
		font.draw(spriteBatch, timeSurvived,  this.currentPosition.x - timeSurvived.length()*6/2,  this.currentPosition.y + 80);
	}
	
	private void addItemBuff(Item item) {
		if(item.getItem().getItemEffect() == ItemEffectEnum.defenceBoost) {
			this.buffDefence(item.getItem().getPower());
		}
		else if(item.getItem().getItemEffect() == ItemEffectEnum.healthBoost){
			this.buffHealth(item.getItem().getPower());
		}
		else if(item.getItem().getItemEffect() == ItemEffectEnum.damageBoost) {
			this.buffDamage(item.getItem().getPower());
		}
	}
	
	public void addDiscoveredZone(GameZone zone) {
		if(!this.hasDiscoveredZone(zone))
			this.discoveredZones.add(zone);
	}

	public boolean hasDiscoveredZone(GameZone zone) {
		for(GameZone discoveredZone : discoveredZones) {
			if(discoveredZone.equals(zone))
				return true;		
		}
		return false;
	} 
	
	public ArrayList<Quest> getQuests() {
		return quests;
	}

	public ArrayList<NPC> getNpcsKilled() {
		return npcsKilled;
	}

	public void setNpcsKilled(ArrayList<NPC> npcsKilled) {
		this.npcsKilled = npcsKilled;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
