package com.adventuresof.game.spell;

import com.adventuresof.game.animation.SpellAnimation;
import com.adventuresof.game.character.GameCharacter;
import com.adventuresof.game.combat.Projectile;
import com.adventuresof.game.common.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class Spell extends Projectile{

	// animation stuff
	protected SpellAnimation spellAnimation;

	protected GameCharacter target; // the spells current target. This can be friendly or hostile
        
	public Spell(float startX, float startY) {
		super(startX, startY);
	}
	
	@Override
	public void render(SpriteBatch spriteBatch) {
		// get the relevant animation frame (based on current character direction)
				TextureRegion currentAnimationFrame;
				currentAnimationFrame = this.getSpellAnimationFrame();			

				// Draw the character frame at the current position
				spriteBatch.draw(currentAnimationFrame, currentPosition.x -15, currentPosition.y - 10); // Draw current frame ()

	}
		
	private TextureRegion getSpellAnimationFrame() {
		return this.spellAnimation.getMoveAnimation().getKeyFrame(stateTime, true);
	}
	
	public void updateHitBox() {
		((Rectangle) this.hitBox).set(new Rectangle(this.currentPosition.x, this.currentPosition.y, this.projectileWidth, this.projectileHeight));
	}
}
