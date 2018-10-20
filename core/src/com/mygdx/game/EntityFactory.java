package com.mygdx.game;

import com.badlogic.gdx.utils.Json;

public class EntityFactory {
	private static Json _json = new Json();

	public static enum EntityType {
		PLAYER
	}

	public static String PLAYER_CONFIG = "scripts/player.json";

	static public Entity getEntity(EntityType entityType) {
		Entity entity = null;
//		switch(entityType){
//        case PLAYER:
//            entity = new Entity(new PlayerInputComponent());
//            entity.setEntityConfig(Entity.getEntityConfig(EntityFactory.PLAYER_CONFIG));
//            entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, _json.toJson(entity.getEntityConfig()));
//            return entity;
//			default:
				return null;
//		}
	}
}
