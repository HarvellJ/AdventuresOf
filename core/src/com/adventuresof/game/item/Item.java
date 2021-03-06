package com.adventuresof.game.item;

import com.badlogic.gdx.math.Rectangle;

/**
 * Item Class
 * 
 */
public class Item {

		private ItemEnum item;
	    private float positionX;
	    private float positionY;
	    private Rectangle hitbox;
	    
	    
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
}
