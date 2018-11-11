package com.adventuresof.game.inventory;

import com.badlogic.gdx.math.Rectangle;

public class Item {

		private ItemEnum item;
	    private float positionX;
	    private float positionY;
	    private Rectangle hitbox;
		private String textureRegion;
	    
	    public Item(ItemEnum item) {
	    	this.setItem(item); 
	    }

		public float getPositionX() {
			return positionX;
		}

		public void setPositionX(float x) {
			this.positionX = x;
		}

		public float getPositionY() {
			return positionY;
		}

		public void setPositionY(float positionY) {
			this.positionY = positionY;
		}

		public Rectangle getHitbox() {
			return hitbox;
		}
		
		public void setHitbox(Rectangle hitbox) {
			this.hitbox = hitbox;
		}

		public ItemEnum getItem() {
			return item;
		}

		public void setItem(ItemEnum item) {
			this.item = item;
		}

		private Item(String textureRegion) {
			this.textureRegion = textureRegion;
		}

		public String getTextureRegion() {
			return textureRegion;
		}
}
