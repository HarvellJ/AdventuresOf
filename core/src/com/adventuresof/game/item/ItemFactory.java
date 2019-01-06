package com.adventuresof.game.item;

import java.util.Random;

import com.adventuresof.game.inventory.ItemEnum;

public class ItemFactory {

	/**
	 * Generates an item from the map item spawn table
	 * For use on map spawn points, e.g. objects found randomly on the ground
	 * @return
	 */
	public static Item spawnItemForMap() {
		Random r = new Random();
		
		return new Item(ItemEnum.values()[r.nextInt(ItemEnum.values().length)]);
	}
	
	/**
	 * Generates an item from the map item spawn table
	 * For use on map spawn points, e.g. objects found randomly on the ground
	 * @return
	 */
	public static ItemEnum spawnItemFromNPC(int npcLevel) {
		return null;
	}
	
}
