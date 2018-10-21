package com.adventuresof.game.character;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PlayerCompanion extends NPC {

	public PlayerCompanion(TiledMapTileLayer accessibleTiles, GameCharacter characterToFollow) {
		super(accessibleTiles);
		this.setTarget(characterToFollow);
		// when companion is created, send welcome message to player
		super.addMessageToMessageQueue("Hello there! Welcome to the tutorial");
		super.addMessageToMessageQueue("I am here to guide you through your adventures.");
		super.addMessageToMessageQueue("Lets start by moving around. Right click anywhere on the island to move to that location");
		super.addMessageToMessageQueue("And dont worry, you can't fall off into the ocean (I know you was wondering).");
	}

}
