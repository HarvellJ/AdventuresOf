package com.adventuresof.game.item;

import com.badlogic.gdx.math.Rectangle;

public enum ItemEnum {
	// Common items
	 SHIELD_COMMON("items//shield.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.common, 1), 
	 SWORD_COMMON("items//sword.png", ItemEffectEnum.damageBoost, ItemRarityEnum.common, 1), 
	 ARMOR_COMMON("items//armor.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.common, 1),	
	
	// Un-common items
    SHIELD_UNCOMMON("items//shield.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.uncommon, 2), 
    SWORD_UNCOMMON("items//sword.png", ItemEffectEnum.damageBoost, ItemRarityEnum.uncommon, 2), 
    ARMOR_UNCOMMON("items//armor.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.uncommon, 1),
    
    // Rare items
    SHIELD_RARE("items//shield.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.rare, 3), 
    SWORD_RARE("items//sword.png", ItemEffectEnum.damageBoost, ItemRarityEnum.rare, 3), 
    ARMOR_RARE("items//armor.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.rare, 3),

    // Ultra rare items
    SHIELD_ULTRARARE("items//shield.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.ultraRare, 9), 
    SWORD_ULTRARARE("items//sword.png", ItemEffectEnum.damageBoost, ItemRarityEnum.ultraRare, 9), 
    ARMOR_ULTRARARE("items//armor.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.ultraRare, 9);
    
    private String texture;
    private ItemEffectEnum itemEffect;
    private ItemRarityEnum itemRarity;
    private int power; // represents the power of the item, e.g. the health it heals, or the damage it deals (based on effect)
    
    private ItemEnum(String texture, ItemEffectEnum itemEffect, ItemRarityEnum itemRarity, int power){
        this.texture = texture;
        this.itemEffect = itemEffect;
        this.itemRarity = itemRarity;
        this.power = power;
    }

    public String getTextureRegion() {
        return texture;
    }

	public ItemEffectEnum getItemEffect() {
		return itemEffect;
	}

	public int getPower() {
		return power;
	}

	public ItemRarityEnum getItemRarity() {
		return itemRarity;
	}
}
