package com.adventuresof.game.item;

import com.badlogic.gdx.math.Rectangle;

public enum ItemEnum {
	// Common items
	 SHIELD_COMMON("items//shield_commnon.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.common, 1), 
	 SWORD_COMMON("items//sword_common.png", ItemEffectEnum.damageBoost, ItemRarityEnum.common, 1), 
	 ARMOR_COMMON("items//armor_common.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.common, 1),	
	
	// Un-common items
    SHIELD_UNCOMMON("items//shield_uncommon.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.uncommon, 2), 
    SWORD_UNCOMMON("items//sword_uncommon.png", ItemEffectEnum.damageBoost, ItemRarityEnum.uncommon, 2), 
    ARMOR_UNCOMMON("items//armor_uncommon.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.uncommon, 1),
    
    // Rare items
    SHIELD_RARE("items//shield_rare.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.rare, 3), 
    SWORD_RARE("items//sword_rare.png", ItemEffectEnum.damageBoost, ItemRarityEnum.rare, 3), 
    ARMOR_RARE("items//armor_rare.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.rare, 3),

    // Ultra rare items
    SHIELD_ULTRARARE("items//shield_ultra.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.ultraRare, 9), 
    SWORD_ULTRARARE("items//sword_ultra.png", ItemEffectEnum.damageBoost, ItemRarityEnum.ultraRare, 9), 
    ARMOR_ULTRARARE("items//armor_ultra.png", ItemEffectEnum.defenceBoost, ItemRarityEnum.ultraRare, 9);
    
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
