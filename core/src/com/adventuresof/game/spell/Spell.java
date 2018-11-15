package com.adventuresof.game.spell;

import com.adventuresof.game.character.GameCharacter;
import com.adventuresof.game.common.GameObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public abstract class Spell extends GameObject{
	protected int damage;

	// movement variables
	protected Vector3 pointToMoveTo; // used to move the character to a point
	protected Vector3 currentPosition; // stores current position of character
	protected float speed;
	protected boolean canRespawn;	    

	// animation stuff
	protected SpellAnimation spellAnimation;

	protected float stateTime;

	protected GameCharacter target; // the characters current target. This can be friendly or hostile

	// collision stuff
	protected Circle hitBox; // a mesh used to detect collisions with the character

	protected TiledMapTileLayer accessibleTiles; // represents the tiles that are accessible by the character
  
    protected float radius;
    
    
	public Circle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Circle hitBox) {
		this.hitBox = hitBox;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
