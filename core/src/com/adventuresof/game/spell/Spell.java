package com.adventuresof.game.spell;

import com.adventuresof.game.animation.SpellAnimation;
import com.adventuresof.game.character.GameCharacter;
import com.adventuresof.game.combat.Projectile;
import com.adventuresof.game.common.GameObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public abstract class Spell extends Projectile{

	// animation stuff
	protected SpellAnimation spellAnimation;

	protected GameCharacter target; // the spells current target. This can be friendly or hostile

    protected float radius;
    
	protected int damage;
    
	public Spell(float startX, float startY, float endX, float endY) {
		super(startX, startY, endX, endY);
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
