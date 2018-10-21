package com.adventuresof.game.inventory;

public enum Item {
    SHIELD("shield.png"), SWORD("sword.png"), ARMOR("armor.png");

    private String texture;

    private Item(String texture) {
        this.texture = texture;
    }

    public String getTextureRegion() {
        return texture;
    }
}
