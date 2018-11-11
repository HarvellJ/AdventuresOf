package com.adventuresof.game.inventory;

import com.badlogic.gdx.math.Rectangle;

public enum ItemEnum {
    SHIELD("items//shield.png"), SWORD("items//sword.png"), ARMOR("items//armor.png");

    private String texture;
    
    private ItemEnum(String texture) {
        this.texture = texture;
    }

    public String getTextureRegion() {
        return texture;
    }

}
