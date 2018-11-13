package com.adventuresof.game.world;

import java.util.ArrayList;

import com.adventuresof.game.character.Enemy;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.inventory.Item;

import com.adventuresof.game.item.ItemFactory;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class GameWorld {
		protected Map map; // The map for this game world
		protected Player player;
		protected ArrayList<NPC> NPCs;
		protected ArrayList<Item> items; // an array of in-game items - representing the items that exist in the world

		public GameWorld(String mapPath) {
			
			map = new Map(mapPath);
			
			this.spawnNPCs();
			
			// load the player
			this.spawnPlayer();
			
			// instantiate map item list
			items = new ArrayList<Item>();
			
			this.spawnChanceItemsIntoWorld();
		}
		
		public ArrayList<NPC> getNPCs() {
			return NPCs;
		}

		public void setNPCs(ArrayList<NPC> nPCs) {
			NPCs = nPCs;
		}

		public Map getMap() {
			return map;
		}

		public void setMap(Map map) {
			this.map = map;
		}

		public Player getPlayer() {
			return player;
		}

		public void setPlayer(Player player) {
			this.player = player;
		}
		
		public ArrayList<Item> getItems() {
			return items;
		}

		public void setItems(ArrayList<Item> items) {
			this.items = items;
		}
		
		public void update(float delta) {
			//this.detectObjectCollisions();
			// move player
			player.update();
		    // move and update NPCs
			this.moveNPCs();
			this.updateNPCs();
			// now check for any collisions following update calls to characters
			this.detectCollectionOfItemObjects();
			this.detectCollisionWithTriggers();
		}
				
		protected abstract void spawnNPCs();
		
		protected abstract void detectCollisionWithTriggers();
		
		public void spawnPlayer() {
			for (RectangleMapObject rectangleObject : this.map.getPlayerSpawnObjects().getByType(RectangleMapObject.class)) {
				Rectangle rectangle = rectangleObject.getRectangle();
				// spawn in an 'enemy'
				this.setPlayer(new Player(map.getAccessibleMapLayer(), rectangle.x, rectangle.y));
				break;
			}
		}
		
		public NPC targetLocationContainsNPC(Vector3 location) {
			Rectangle rectangle = new Rectangle();
			rectangle.set(location.x, location.y, this.player.getHitBox().width, this.player.getHitBox().height);
			for(NPC npc : this.NPCs) {
				if (Intersector.overlaps(npc.getHitBox(), rectangle)) {
					return npc;
				}	
			}	
			// no npc found at target location, return null
			return null;
		}
		
		// detect player collision with items. If detection occurs, move from world inventory into player inventory
		private void detectCollectionOfItemObjects() {
			for (int i = 0; i < items.size(); i++) {
				if (Intersector.overlaps(this.items.get(i).getHitbox(), player.getHitBox())) {
					// check each of the spawns
				    this.collectItemFromMap(i);
				}	 
			}	    
		}
		
		private void collectItemFromMap(int itemIndex) {
			this.player.addItemToInventory(this.items.get(itemIndex));
			this.items.remove(itemIndex);
		}
		
		private void spawnChanceItemsIntoWorld() {
			for (RectangleMapObject rectangleObject : this.map.getItemSpawnPointObjects().getByType(RectangleMapObject.class)) {
				Rectangle rectangle = rectangleObject.getRectangle();
				// generate a random by chance item from the drop table
				this.spawnItem(rectangle);
				}									    			
		}
		
		private void moveNPCs() {
			for (NPC npc : this.NPCs) {
				npc.move(this.player);
			}
		}
		
		private void updateNPCs() {
			for (int i = 0; i < this.NPCs.size(); i++) {
				// check if they're dead - if so, remove them from game world
				if(this.NPCs.get(i).isDead()) {
					// spawn a relevant drop based on NPC
					this.spawnItem(this.NPCs.get(i).getHitBox());
					this.NPCs.remove(i);
				}else {
			    // if they are still alive, update them
					this.NPCs.get(i).update();
				}
			}
		}
		
		private void spawnItem(Rectangle rectangle) {
			Item item = ItemFactory.spawnItemForMap();
			if(item != null) {
				item.setPositionX(rectangle.x);
				item.setPositionY(rectangle.y);
				item.setHitbox(new Rectangle(rectangle.x, rectangle.y, 50f, 50f));
				this.items.add(item);
			}	
		}
		
		public void performIceSpellCast(Circle targetingCircle) {
			for(NPC npc : this.NPCs) {
				if (Intersector.overlaps(targetingCircle, npc.getHitBox())) {
					this.player.performIceSpell(npc);
				}	
			}	
		}
}
