package com.adventuresof.game.character;


import java.util.ArrayList;
import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.inventory.Item;
import com.adventuresof.game.world.GameZone;
import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player extends GameCharacter{

	private Inventory inventory; // Stores items the player has collected
	private ArrayList<GameZone> discoveredZones; // Stores a list of discovered zones. Used for measuring player progress

	public Player(TiledMapTileLayer accessibleTiles) {
		super(accessibleTiles, "animation_sheet.png", 8, 5, 1700, 1300, false, 50, 50);
		this.inventory = new Inventory();
		this.discoveredZones = new ArrayList<GameZone>();
	}

	public ArrayList<GameZone> getDiscoveredZones() {
		return discoveredZones;
	}

	public void setDiscoveredZones(ArrayList<GameZone> discoveredZones) {
		this.discoveredZones = discoveredZones;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void addItemToInventory(Item item) {
		this.inventory.store(item, 1);
	}

	public void addDiscoveredZone(GameZone zone) {
		if(!this.hasDiscoveredZone(zone))
			this.discoveredZones.add(zone);
	}

	public boolean hasDiscoveredZone(GameZone zone) {
		for(GameZone discoveredZone : discoveredZones) {
			if(discoveredZone.equals(zone))
				return true;		
		}
		return false;
	}
	
	public void performIceSpell(NPC npc) {
		// inflict damage
		npc.inflictDamage(this.generateRandomDamageAmount());
		npc.freeze();
	}

	protected void createAnimations() {
		this.runRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 5, 15, 0.08f);
		this.runLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 5, 21, 0.08f);
		this.runDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 4, 5, 0.08f);
		this.runUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 4, 10, 0.08f);

		this.attackDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 27, 1f);
		this.attackUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 30, 1f);
		this.attackRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 33, 1f);
		this.attackLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 3, 36, 1f);
		
		this.idleUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 29, 0.08f);
		this.idleDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.idleLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 23, 0.08f);
		this.idleRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 14, 0.08f);
	}    
}
