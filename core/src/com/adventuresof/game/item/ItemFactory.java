package com.adventuresof.game.item;

import com.adventuresof.game.inventory.Item;
import com.adventuresof.game.inventory.ItemEnum;

public class ItemFactory {

	/**
	 * Generates an item from the map item spawn table
	 * For use on map spawn points, e.g. objects found randomly on the ground
	 * @return
	 */
	public static Item spawnItemForMap() {
		return new Item(ItemEnum.values()[0]);
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
