package com.adventuresof.game.character.entities;

import java.util.ArrayList;
import java.util.Random;

import com.adventuresof.game.animation.CharacterAnimation;
import com.adventuresof.game.character.enums.CharacterClass;
import com.adventuresof.game.character.enums.CharacterLevel;
import com.adventuresof.game.common.enums.MovementSpeed;
import com.adventuresof.game.world.GameWorld;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;

/**
 * Information about the NPC's
 */
public abstract class NPC extends GameCharacter {

	private Random r;
	private boolean isTalkative;
	private ArrayList<String> conversation;
	private int conversationIndex;
	private boolean hasQuest;
	private String questName;

	public NPC(
			GameWorld gameWorld,
			TiledMapTileLayer accessibleTiles, 
			float startX, float startY,
			boolean isStatic,
			boolean isHostile,
			int characterWidth, int characterHeight,
			CharacterAnimation characterAnimation, MovementSpeed speed,
			String name,
			boolean isTalkative,
			ArrayList<String> conversation, CharacterClass characterClass,
			CharacterLevel baseLevel
			)
	{
		super(gameWorld, accessibleTiles, startX, startY, isHostile, characterWidth, characterHeight, characterAnimation, varySpeed(speed.getSpeed()), false, name, characterClass, baseLevel);
		this.isStatic = isStatic;
		this.isTalkative = isTalkative;
		this.conversation = conversation;
		r = new Random();
	}
	public NPC(
			GameWorld gameWorld, TiledMapTileLayer accessibleTiles, 
			float startX, float startY,
			boolean isStatic,
			boolean isHostile,
			int characterWidth, int characterHeight,
			CharacterAnimation characterAnimation, MovementSpeed speed,
			String name,
			boolean isTalkative,
			ArrayList<String> conversation,
			boolean hasQuest, 
			String questName,CharacterClass characterClass,
			CharacterLevel baseLevel
			)
	{
		super(gameWorld, accessibleTiles, startX, startY, isHostile, characterWidth, characterHeight, characterAnimation, varySpeed(speed.getSpeed()), false, name, characterClass, baseLevel);
		this.isStatic = isStatic;
		this.isTalkative = isTalkative;
		this.conversation = conversation;
		this.hasQuest = hasQuest;
		this.questName = questName;
		
		r = new Random();
	}

	/**
	 * Algorithm to move the NPC randomly
	 * Works by randomly setting the NPC's target location
	 */
	public void move(Player player) {
		if(!isStatic) {
			if(super.target == null) {		
				// check for nearby player if hostile
				if(super.isHostile()) {
					if (Intersector.overlaps(super.getHitBox(), player.getHitBox())) {
						this.setTarget(player);
					}
				}
				if(this.pointToMoveTo == null) {		
					int moveThisFrame = r.nextInt(1000);
					if (moveThisFrame <= 5) {
						boolean isPositiveX = r.nextBoolean();
						boolean isPositiveY = r.nextBoolean();

						int moveX = r.nextInt(40);
						int moveY = r.nextInt(40);


						if (isPositiveX) {
							if (isPositiveY) {
								// only move if they haven't gone beyond allowed boundary (from spawn)
								if((currentPosition.x + moveX) < (spawnLocation.x + 50) || (currentPosition.y + moveY) < (spawnLocation.y + 50)) {
									this.setTargetLocation(new Vector3(currentPosition.x + moveX, currentPosition.y + moveY, 0));
								}
							}else {
								if((currentPosition.x + moveX) < (spawnLocation.x + 50) || (currentPosition.y - moveY) > (spawnLocation.y - 50)) {
									this.setTargetLocation(new Vector3(currentPosition.x + moveX, currentPosition.y - moveY, 0));
								}
							}
						}else {
							if (isPositiveY) {
								if((currentPosition.x - moveX) > (spawnLocation.x - 50) || (currentPosition.y + moveY) < (spawnLocation.y + 50)) {
									this.setTargetLocation(new Vector3(currentPosition.x - moveX, currentPosition.y + moveY, 0));
								}
							}else {
								if((currentPosition.x - moveX) > (spawnLocation.x - 50) || (currentPosition.y - moveY) < (spawnLocation.y + 50)) {
									this.setTargetLocation(new Vector3(currentPosition.x - moveX, currentPosition.y - moveY, 0));
								}
							}
						}
					}												

				}
			}
		}	
	}	

	private static float varySpeed(float speed) {
		// slightly varies the speed for the NPC. Used to stop NPC's from stacking on one of another as often
		Random r = new Random();
		return speed - 10 + r.nextFloat() * (speed + 30 - speed - 10);
	}

	public int getConversationIndex() {
		return conversationIndex;
	}

	public void setConversationIndex(int conversationIndex) {
		this.conversationIndex = conversationIndex;
	}

	public ArrayList<String> getConversation() {
		return conversation;
	}

	public void setConversation(ArrayList<String> conversation) {
		this.conversation = conversation;
	}

	public boolean isTalkative() {
		return isTalkative;
	}

	public void setTalkative(boolean isTalkative) {
		this.isTalkative = isTalkative;
	}

	public void setHostile (boolean isHostile) {
		this.isHostile = isHostile;
	}
	
	public String getQuestName() {
		return questName;
	}

	public void setQuestName(String questName) {
		this.questName = questName;
	}

	public boolean hasQuest() {
		return hasQuest;
	}
	
	public void setHasQuest(boolean hasQuest) {
		this.hasQuest = hasQuest;
	}
}
