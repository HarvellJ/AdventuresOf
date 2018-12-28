package com.adventuresof.game.world;

import java.util.ArrayList;
import com.adventuresof.game.character.CharacterClass;
import com.adventuresof.game.character.GameCharacter;
import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.combat.Projectile;
import com.adventuresof.game.combat.SpellEnum;
import com.adventuresof.game.inventory.Item;

import com.adventuresof.game.item.ItemFactory;
import com.adventuresof.helpers.SoundManager;
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
	protected ArrayList<Projectile> activeProjectiles;

	public GameWorld(String mapPath) {
		map = new Map(mapPath);
		this.activeProjectiles = new ArrayList<Projectile>();
		this.spawnNPCs();
		this.spawnPlayer(); // load the player
		items = new ArrayList<Item>(); // instantiate map item list (items that exist on the map)
		this.spawnChanceItemsIntoWorld();
	}

	public ArrayList<Projectile> getActiveProjectiles() {
		return activeProjectiles;
	}

	public void setActiveProjectiles(ArrayList<Projectile> activeSpells) {
		this.activeProjectiles = activeSpells;
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

	public void update(float delta, ArrayList<NPC> NPCs) {
		//this.detectObjectCollisions();
		player.update(); // move player
		this.disposeOfObjects(); // disposes of objects marked as ready for disposal
		this.updateGameProjectiles();
		// move and update NPCs
		this.moveNPCs(); // calls the move method on NPCs which just sets the target locations randomly
		this.updateNPCs(); // actually updates the NPC's physical location
		// now check for any collisions following update calls to characters
		this.detectCollectionOfItemObjects(); // detect player collision with items
		this.detectCollisionOfProjectilesAndCharacters(); // detect spell hits
		this.detectCollisionWithTriggers(); 						
	}		

	public void performIceSpellCast(Circle targetingCircle) {
		for(NPC npc : this.NPCs) {
			if (Intersector.overlaps(targetingCircle, npc.getHitBox())) {
				this.player.performIceSpell(npc);
				SoundManager.playSoundEffect("audio/effects/ice.wav");
			}	
		}	
	}

	public void performTornadoSpellCast(float startX, float startY, float targetX, float targetY, GameCharacter firedBy) {
		// spawn projectile and send it in target's direction
		this.activeProjectiles.add
		(new Projectile(
				this.map.getAccessibleMapLayer(),
				startX,
				startY, 
				targetX,
				targetY,
				SpellEnum.Tornado,
				firedBy
				));
		SoundManager.playSoundEffect("audio/effects/spellCast.wav");
	}

	public void performArrowSpellCast(float startX, float startY, float targetX, float targetY, GameCharacter firedBy) {	
		// spawn projectile and send it in target's direction
		this.activeProjectiles.add
		(new Projectile(
				this.map.getAccessibleMapLayer(),
				startX,
				startY, 
				targetX,
				targetY,
				SpellEnum.Arrow,
				firedBy
				));
		SoundManager.playSoundEffect("audio/effects/Bow.wav");
	}

	public void spawnPlayer() {
		for (RectangleMapObject rectangleObject : this.map.getPlayerSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.setPlayer(new Player(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, CharacterClass.hybrid));
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

	protected abstract void spawnNPCs();

	protected abstract void detectCollisionWithTriggers();

	private void disposeOfObjects() {
		// remove perished NPCs
		for(int i = 0; i < this.NPCs.size(); i++) {
			if(this.NPCs.get(i).CanDispose()) {
				// spawn a relevant drop based on NPC
				this.spawnItem(this.NPCs.get(i).getHitBox());
				this.NPCs.remove(i);
			}
		}
		// remove objects - e.g. spell animations and stuff alike
		for(int i = 0; i < this.activeProjectiles.size(); i++) {
			if(this.activeProjectiles.get(i).CanDispose())
				this.activeProjectiles.remove(i);
		}
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

	private void detectCollisionOfProjectilesAndCharacters() {
		// check NPCs	
		for (Projectile spell : this.activeProjectiles) {		
			for (NPC npc : this.NPCs) {
				if(!Double.isNaN(npc.getHitBox().x )) {
					if (Intersector.overlaps(spell.getHitBox(), npc.getHitBox())) {
						if(spell.getFiredBy() != npc) {
							npc.damage(spell.getDamage());
							// instantly dispose of spell (or else it will continue to hit the target every frame until it reaches target destination)
							spell.setCanDispose(true);
						}
					}	
				}
			}
			if(!Double.isNaN(player.getHitBox().x )) {
				if (Intersector.overlaps(spell.getHitBox(), player.getHitBox())) {
					if(spell.getFiredBy() != player) {
						player.damage(spell.getDamage());
						// instantly dispose of spell (or else it will continue to hit the target every frame until it reaches target destination)
						spell.setCanDispose(true);
					}
				}	
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

	private void updateGameProjectiles() {
		for (int i = 0; i < this.activeProjectiles.size(); i++) {			
			this.activeProjectiles.get(i).update();	
		}
	}

	private void updateNPCs() {
		for (int i = 0; i < this.NPCs.size(); i++) {				
			// if they are still alive, update them
			this.NPCs.get(i).update();		
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

}
