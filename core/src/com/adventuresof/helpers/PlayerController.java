package com.adventuresof.helpers;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.world.GameRenderer;
import com.adventuresof.game.world.TutorialIsland;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class PlayerController implements InputProcessor{

	private TutorialIsland gameWorld;
	private GameRenderer gameRenderer;
	
	private boolean freezeSpellActivated;
	private boolean dartSpellActivated;
	
	public PlayerController(TutorialIsland gameWorld, GameRenderer gameRenderer) {
		this.gameWorld = gameWorld;
		this.gameRenderer = gameRenderer;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.LEFT)
			gameRenderer.getCamera().translate(-45,0);
		if(keycode == Input.Keys.RIGHT)
			gameRenderer.getCamera().translate(45, 0);
		if(keycode == Input.Keys.UP)
			gameRenderer.getCamera().translate(0,45);
		if(keycode == Input.Keys.DOWN)
			gameRenderer.getCamera().translate(0,-45);
		if(keycode == Input.Keys.NUM_1) {
			this.freezeSpellActivated = false;
			if(!dartSpellActivated) {
				this.gameRenderer.setShowTargetCircle(true, 30);
				this.dartSpellActivated = true;
			}else {
				this.gameRenderer.setShowTargetCircle(false, 0);
				this.dartSpellActivated = false;
			}
		
		}
		if(keycode == Input.Keys.NUM_2) {
			if(!freezeSpellActivated) {
				this.dartSpellActivated = false;
				this.gameRenderer.setShowTargetCircle(true, 120);
				this.freezeSpellActivated = true;
			}else {
				this.gameRenderer.setShowTargetCircle(false, 0);
				this.freezeSpellActivated = false;
			}
		
		}
		
		//if(keycode == Input.Keys.NUM_3)
			//gameWorld.getMap().getTiledMap().getLayers().get(0).setVisible(!gameWorld.getMap().getTiledMap().getLayers().get(0).isVisible());
		//if(keycode == Input.Keys.I)
		//	gameRenderer.toggleInventory();
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.RIGHT){
		Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
		Vector3 newPosition = gameRenderer.getCamera().unproject(clickCoordinates);
		
		// check if player has selected a position containing an NPC
		NPC npc = this.gameWorld.targetLocationContainsNPC(newPosition);
		if(npc == null) {
			this.gameWorld.getPlayer().setTarget(null);
			this.gameWorld.getPlayer().setTargetLocation(newPosition);
		}else {
			this.gameWorld.getPlayer().setTarget(npc);
		}
		}
		else if (button == Buttons.LEFT) {
			// check active spells
			if(freezeSpellActivated) {
				Vector3 coordinates = new Vector3(this.gameRenderer.getTargetCircleX(), this.gameRenderer.getTargetCircleY(), 0);
				Vector3 positionInGame = gameRenderer.getCamera().unproject(coordinates);

				this.gameWorld.performIceSpellCast(new Circle(positionInGame.x,positionInGame.y, this.gameRenderer.getTargetCircleSize()));
			}
			else if(dartSpellActivated) {
				Vector3 coordinates = new Vector3(this.gameRenderer.getTargetCircleX(), this.gameRenderer.getTargetCircleY(), 0);
				Vector3 positionInGame = gameRenderer.getCamera().unproject(coordinates);
				
				this.gameWorld.performDartSpellCast(new Circle(positionInGame.x,positionInGame.y, this.gameRenderer.getTargetCircleSize()));

			}
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// if the player has activated freeze spell, show targeting circle
		if(freezeSpellActivated)		
		{
			this.gameRenderer.setTargetingCircleLocation(screenX, screenY);
		}
		
		if(dartSpellActivated)		
		{
			this.gameRenderer.setTargetingCircleLocation(screenX, screenY);
		}
		
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
