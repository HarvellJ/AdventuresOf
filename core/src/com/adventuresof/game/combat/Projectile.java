package com.adventuresof.game.combat;

import com.adventuresof.game.common.GameObject;
import com.adventuresof.game.common.MoveableObject;
import com.badlogic.gdx.math.Vector3;

public abstract class Projectile extends MoveableObject{
	
	public Projectile(float startX, float startY, float endX, float endY) {
		super(startX, startY, endX, endY);
	}
	
}
