package com.adventuresof.game.world;

import java.util.ArrayList;
import java.util.Random;

import com.adventuresof.game.character.entities.GameCharacter;
import com.adventuresof.game.character.entities.NPC;
import com.adventuresof.game.character.entities.Player;
import com.adventuresof.game.character.enums.CharacterClass;
import com.adventuresof.game.character.enums.CharacterLevel;
import com.adventuresof.game.combat.InstantCastAbility;
import com.adventuresof.game.combat.Projectile;
import com.adventuresof.game.combat.SpellEnum;
import com.adventuresof.game.item.Item;
import com.adventuresof.game.item.ItemFactory;
import com.adventuresof.game.item.ItemRarityEnum;
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
	protected ArrayList<InstantCastAbility> instantCastAbilities;
	protected ArrayList<AnimatedMapObject> animatedMapObjects;

	public GameWorld(String mapPath) {
		map = new Map(mapPath);
		this.activeProjectiles = new ArrayList<Projectile>();
		this.instantCastAbilities = new ArrayList<InstantCastAbility>();
		this.animatedMapObjects = new ArrayList<AnimatedMapObject>();
		this.spawnNPCs();
		this.spawnPlayer(); // load the player
		items = new ArrayList<Item>(); // instantiate map item list (items that exist on the map)
		this.spawnChanceItemsIntoWorld();
	}

	public ArrayList<Projectile> getActiveProjectiles() {
		return activeProjectiles;
	}

	public ArrayList<InstantCastAbility> getInstantCastAbilities() {
		return instantCastAbilities;
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

	public ArrayList<AnimatedMapObject> getAnimatedMapObjects() {
		return animatedMapObjects;
	}

	public void setAnimatedMapObjects(ArrayList<AnimatedMapObject> animatedMapObjects) {
		this.animatedMapObjects = animatedMapObjects;
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
		this.updateGameInstantCastSpells();
		this.updateAnimatedMapObjects();
		// move and update NPCs
		this.moveNPCs(); // calls the move method on NPCs which just sets the target locations randomly
		this.updateNPCs(); // actually updates the NPC's physical location
		// now check for any collisions following update calls to characters
		this.detectCollectionOfItemObjects(); // detect player collision with items
		this.detectCollisionOfProjectilesAndCharacters(); // detect spell hits
		this.detectCollisionWithTriggers(); 						
	}		
	
	public void performInstantSpellCast(Circle targetingCircle, SpellEnum spell) {
		for(NPC npc : this.NPCs) {
			if (Intersector.overlaps(targetingCircle, npc.getHitBox())) {
				if(npc.isHostile()) {
					InstantCastAbility ability = new InstantCastAbility(spell, npc, this.player);
					this.instantCastAbilities.add(ability);
					npc.hitWithInstantCastSpell(ability);
					SoundManager.playSoundEffect(ability.getSoundEffect());
				}
			}	
		}	
	}

	public void addInstantCastSpell(InstantCastAbility ability) {
		this.instantCastAbilities.add(ability);
		SoundManager.playSoundEffect(ability.getSoundEffect());
	}
	
	public void performSpellCast(Projectile projectile){
		this.activeProjectiles.add(projectile);
		SoundManager.playSoundEffect(projectile.getSoundEffect());
	}

	public void spawnPlayer() {
		for (RectangleMapObject rectangleObject : this.map.getPlayerSpawnObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// spawn in an 'enemy'
			this.setPlayer(new Player(this, map.getAccessibleMapLayer(), rectangle.x, rectangle.y, CharacterClass.mage, "Derrick"));
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

	protected abstract void spawnAnimatedMapObjects();
	
	protected abstract void updateAnimatedMapObjects();
	
	private void disposeOfObjects() {
		// remove perished NPCs
		
		ArrayList<NPC> tempArray = new ArrayList<NPC>();
		
		for(int i = 0; i < this.NPCs.size(); i++) {
			if(this.NPCs.get(i).CanDispose()) {
				// spawn a relevant drop based on NPC
				this.spawnItem(this.NPCs.get(i).getHitBox(), this.NPCs.get(i).getBaseLevel());
				System.out.println(this.NPCs.get(i).getName());
				this.NPCs.remove(i);
				this.player.getNpcsKilled().add(this.NPCs.get(i));
			}
		}
		
		// remove objects - e.g. spell animations and stuff alike
		for(int i = 0; i < this.activeProjectiles.size(); i++) {
			if(this.activeProjectiles.get(i).CanDispose())
				this.activeProjectiles.remove(i);
		}
		for(int i = 0; i < this.getInstantCastAbilities().size(); i++) {
			if(this.instantCastAbilities.get(i).CanDispose())
				this.instantCastAbilities.remove(i);
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
						// prevent NPC's from damaging one another and themselves
						if(spell.getFiredBy() != npc && !(spell.getFiredBy() instanceof NPC)) {
							npc.damage(spell.getDamage(), spell.getFiredBy());
							// instantly dispose of spell (or else it will continue to hit the target every frame until it reaches target destination)
							spell.setCanDispose(true);
						}
					}	
				}
			}
			if(!Double.isNaN(player.getHitBox().x )) {
				if (Intersector.overlaps(spell.getHitBox(), player.getHitBox())) {
					// prevent player from damaging themselves
					if(spell.getFiredBy() != player) {
						player.damage(spell.getDamage(), spell.getFiredBy());
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
		// spawn common/uncommon items
		Random r = new Random();
		for (RectangleMapObject rectangleObject : this.map.getItemSpawnPointObjects().getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			// generate a random number to determine item rarity (common/uncommon only here)
			if(r.nextInt(3) > 2) {
				this.spawnItem(rectangle, ItemRarityEnum.uncommon);
			}else {
				this.spawnItem(rectangle, ItemRarityEnum.common);
			}
		}		
		
		// spawn rares
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

	private void updateGameInstantCastSpells() {
		for (int i = 0; i < this.instantCastAbilities.size(); i++) {			
			this.instantCastAbilities.get(i).update();	
		}
	}

	private void updateNPCs() {
		for (int i = 0; i < this.NPCs.size(); i++) {				
			// if they are still alive, update them
			this.NPCs.get(i).update();		
		}
	}

	private void spawnItem(Rectangle rectangle, ItemRarityEnum itemRarity) {
		Item item = ItemFactory.spawnItemForMap(itemRarity);
		if(item != null) {
			item.setPositionX(rectangle.x);
			item.setPositionY(rectangle.y);
			item.setHitbox(new Rectangle(rectangle.x, rectangle.y, 50f, 50f));
			this.items.add(item);
		}	
	}
	
	private void spawnItem(Rectangle rectangle, CharacterLevel characterLevel) {
		Item item = ItemFactory.spawnItemFromNPC(characterLevel);
		if(item != null) {
			item.setPositionX(rectangle.x);
			item.setPositionY(rectangle.y);
			item.setHitbox(new Rectangle(rectangle.x, rectangle.y, 50f, 50f));
			this.items.add(item);
		}	
	}

}
