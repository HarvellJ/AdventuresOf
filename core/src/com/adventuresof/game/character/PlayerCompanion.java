package com.adventuresof.game.character;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PlayerCompanion extends NPC {

	public PlayerCompanion(TiledMapTileLayer accessibleTiles, GameCharacter characterToFollow) {
		super(accessibleTiles);
		this.setTarget(characterToFollow);
	}

}
