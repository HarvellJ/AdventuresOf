package com.adventuresof.game.item;

import java.util.ArrayList;
import java.util.Random;

import com.adventuresof.game.character.enums.CharacterLevel;

/**
 * Item factory Class
 * 
 */
public class ItemFactory {

	public static Random random = new Random();
	
	/**
	 * Generates an item from the map item spawn table
	 * For use on map spawn points, e.g. objects found randomly on the ground
	 * @return
	 */
	public static Item spawnItemForMap(ItemRarityEnum itemRarity) {	
		ArrayList<ItemEnum> itemsOfGivenRarity = new ArrayList<ItemEnum>();
		for(ItemEnum item : ItemEnum.values()) {
			if(item.getItemRarity() == itemRarity) {
				itemsOfGivenRarity.add(item);
			}
		}
		
		return new Item(itemsOfGivenRarity.get(random.nextInt(itemsOfGivenRarity.size())));
	}
	
	// TODO: Write unit tests for the drop table logic
	/**
	 * Generates an item from the map item spawn table
	 * For use on map spawn points, e.g. objects found randomly on the ground
	 * @return
	 */
	public static Item spawnItemFromNPC(CharacterLevel characterLevel) {
		// drop table logic...
		if(characterLevel == CharacterLevel.low)	
			return calculateLowLevelDrop();
		else if(characterLevel == CharacterLevel.normal)
			return calculateNormalLevelDrop();
		else if(characterLevel == CharacterLevel.high)
			return calculateHighLevelDrop();
		else if(characterLevel == CharacterLevel.powerful)
			return calculatePowerfulLevelDrop();
		else if(characterLevel == CharacterLevel.boss)
			return calculateBossLevelDrop();	
		// to cover all basis... if level is unknown, treat is as lowest
		else
			return calculateLowLevelDrop();
	}
	
	private static Item calculateLowLevelDrop() {
		// 90% chance of common, 10% chance of uncommon
		if(random.nextInt(100) > 89) {
			return spawnItemForMap(ItemRarityEnum.uncommon);
		}else {
			return spawnItemForMap(ItemRarityEnum.common);
		}
	}
	
	private static Item calculateNormalLevelDrop() {
		// 70% chance of common, 25% chance of uncommon, 5% chance of rare
		int randomResult = random.nextInt(100);
		if(randomResult > 94) {
			return spawnItemForMap(ItemRarityEnum.rare);
		}
		else if(randomResult > 74) {
			return spawnItemForMap(ItemRarityEnum.uncommon);
		}
		else {
			return spawnItemForMap(ItemRarityEnum.common);
		}
	}
	
	private static Item calculateHighLevelDrop() {
		// 55% chance of common, 20% chance of uncommon, 25% chance of rare
		int randomResult = random.nextInt(100);
		if(randomResult > 74) {
			return spawnItemForMap(ItemRarityEnum.rare);
		}
		else if(randomResult > 54) {
			return spawnItemForMap(ItemRarityEnum.uncommon);
		}
		else {
			return spawnItemForMap(ItemRarityEnum.common);
		}
	}
	
	private static Item calculatePowerfulLevelDrop() {
		// 50/50% chance of rare and uncommon
		int randomResult = random.nextInt(100);
		if(randomResult > 49) {
			return spawnItemForMap(ItemRarityEnum.rare);
		}
		else {
			return spawnItemForMap(ItemRarityEnum.uncommon);
		}
	}
	
	private static Item calculateBossLevelDrop() {
		// 100% chance of ultraRare 
		return spawnItemForMap(ItemRarityEnum.ultraRare);
	}
}
