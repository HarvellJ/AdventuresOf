package com.adventuresof.game.world;

import java.util.ArrayList;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.inventory.Item;
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
			
			// load the player
			this.setPlayer(new Player(map.getAccessibleMapLayer(), "You"));
			
			// instantiate map item list
			items = new ArrayList<Item>();
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
		
		public abstract void update(float delta);		
		
		protected abstract void spawnNPCs();
		
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
		
		public void performIceSpellCast(Circle targetingCircle) {
			for(NPC npc : this.NPCs) {
				if (Intersector.overlaps(targetingCircle, npc.getHitBox())) {
					this.player.performIceSpell(npc);
				}	
			}	
		}
}
