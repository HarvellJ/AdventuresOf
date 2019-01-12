package com.adventuresof.game.combat;

import com.adventuresof.game.animation.SpellAnimation;
import com.adventuresof.game.character.entities.GameCharacter;
import com.adventuresof.game.common.GameObject;
import com.adventuresof.game.common.enums.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class InstantCastAbility extends GameObject{

	private SpellEnum spellType;
	private SpellAnimation spellAnimation;
	private float stateTime;
	protected int damage;

	protected Direction currentProjectileDirection;

	private GameCharacter castBy; // who cast the spell
	private GameCharacter target; // target of spell

	private String soundEffect; // stores the path to the sound effect for this projectile

	private float healthPoints; // this can either represent the health the spell heals (if none hostile) or the dammage the spell inflicsts if spell is hostile

	private boolean isHostile;

	TextureRegion firstAnimationFrame; // stores the first frame so we can derive when a full animation loop has been completed.
	TextureRegion currentAnimationFrame;
	boolean hasAnimationProgressed;

	public InstantCastAbility(
			SpellEnum spellType,
			GameCharacter target, GameCharacter castBy) {

		this.castBy = castBy;

		this.spellType = spellType;
		this.spellAnimation = spellType.getAnimation();
		this.soundEffect = spellType.getSoundEffect();
		this.healthPoints = spellType.getDamage();

		this.target = target;
		
		this.canDispose = false;

		this.isHostile = spellType.isHostile();
		this.damage = spellType.getDamage();

	}

	public float getHealthPoints() {
		return healthPoints;
	}

	public boolean isHostile() {
		return isHostile;
	}

	public GameCharacter getTarget() {
		return target;
	}

	public SpellEnum getSpell() {
		return spellType;
	}

	public String getSoundEffect() {
		return soundEffect;
	}

	public GameCharacter getCastBy() {
		return castBy;
	}

	@Override
	public void update() {
		this.stateTime += Gdx.graphics.getDeltaTime(); // increment state time
		// if the spell is a player buff, update location so it follows player.
		
		// prevent animation from looping
		if(firstAnimationFrame == null && currentAnimationFrame != null) {
			firstAnimationFrame = currentAnimationFrame;
		}
		else if(firstAnimationFrame != currentAnimationFrame && currentAnimationFrame != null) {
			hasAnimationProgressed = true;
		}

		// check if animation has ran a full loop, if so, remove it
		if(hasAnimationProgressed == true && firstAnimationFrame == currentAnimationFrame) {
			this.canDispose = true;
		}

	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Rectangle getHitBox() {
		return (Rectangle)hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}


	@Override
	public void render(SpriteBatch spriteBatch) {
		currentAnimationFrame = this.spellAnimation.getDownAnimation().getKeyFrame(stateTime, true);
		spriteBatch.draw(currentAnimationFrame, this.target.getCurrentPosition().x - 57, this.target.getCurrentPosition().y - 35); // Draw current frame
	}   

}
