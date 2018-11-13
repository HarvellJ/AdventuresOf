package com.adventuresof.game.character;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;

/**
 *
 */
public abstract class NPC extends GameCharacter {

	private Random r;
	public boolean isTalkative;
	private ArrayList<String> conversation;
	private int conversationIndex = 0;
	
	
	public NPC(TiledMapTileLayer accessibleTiles, String animationSheetName, int animationSheetCols,
			int animationSheetRows, float startX, float startY, boolean isStatic, boolean isHostile, int characterWidth, int characterHeight, boolean isTalkative, ArrayList<String> conversation, String name) {
		super(accessibleTiles, animationSheetName, animationSheetCols, animationSheetRows, startX, startY, isHostile, characterWidth, characterHeight , name);
		this.isStatic = isStatic;
		this.isTalkative = isTalkative;
		this.conversation = conversation;
		r = new Random();
	}
	
	/**
	 * Algorithm to move the NPC randomly
	 * Works by randomly setting the NPC's target location
	 */
	public void move() {
		if(!isStatic) {
			if(super.target != null) {
				//move to target location
				super.setTargetLocation(new Vector3((float)target.getCurrentPosition().x - 40, (float)target.getCurrentPosition().y + 40, 0));
			}
			else {
				if(this.pointToMoveTo == null) {		
					int moveThisFrame = r.nextInt(1000);
					if (moveThisFrame <= 5) {
						boolean isPositiveX = r.nextBoolean();
						boolean isPositiveY = r.nextBoolean();

						int moveX = r.nextInt(300);
						int moveY = r.nextInt(300);

						if (isPositiveX) {
							if (isPositiveY) {
								this.setTargetLocation(new Vector3(currentPosition.x + moveX, currentPosition.y + moveY, 0));
							}else {
								this.setTargetLocation(new Vector3(currentPosition.x + moveX, currentPosition.y - moveY, 0));

							}
						}else {
							if (isPositiveY) {
								this.setTargetLocation(new Vector3(currentPosition.x - moveX, currentPosition.y + moveY, 0));
							}else {
								this.setTargetLocation(new Vector3(currentPosition.x - moveX, currentPosition.y - moveY, 0));
							}
						}
					}		
				}
			}
		}
	}
	
	public ArrayList<String> getConversation () {
		return this.conversation;
	}
	
	public int getConversationIndex () {
		return this.conversationIndex;
	}
	
	public void setConversationIndex (int conversationIndex) {
		this.conversationIndex = conversationIndex;
	}
}
