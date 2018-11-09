package com.adventuresof.game.inventory;

import com.badlogic.gdx.math.Rectangle;

public enum ItemEnum {
    SHIELD("shield.png"), SWORD("sword.png"), ARMOR("armor.png");

    private String texture;
    
    private ItemEnum(String texture) {
        this.texture = texture;
    }

    public String getTextureRegion() {
        return texture;
    }

}
