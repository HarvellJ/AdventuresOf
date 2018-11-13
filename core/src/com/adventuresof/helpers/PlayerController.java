package com.adventuresof.helpers;

import java.util.ArrayList;
import java.util.Collections;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.world.GameRenderer;
import com.adventuresof.game.world.TutorialIsland;
import com.adventuresof.screens.MainGameScreen;
import com.adventuresof.screens.PlayerHUD;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlayerController implements InputProcessor{

	private TutorialIsland gameWorld;
	private GameRenderer gameRenderer;
	private PlayerHUD playerHUD;
	
	private boolean freezeSpellActivated;

	public PlayerController(TutorialIsland gameWorld, GameRenderer gameRenderer, PlayerHUD playerHUD) {
		this.gameWorld = gameWorld;
		this.gameRenderer = gameRenderer;
		this.playerHUD = playerHUD;
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
		if(keycode == Input.Keys.NUM_1)
			gameWorld.getPlayer().setPerformAttack(true);
		if(keycode == Input.Keys.NUM_2) {
			if(!freezeSpellActivated) {
				this.gameRenderer.setShowTargetCircle(true, 120);
				this.freezeSpellActivated = true;
			}else {
				this.gameRenderer.setShowTargetCircle(false, 0);
				this.freezeSpellActivated = false;
			}
		
		}
		
//		if(keycode == Input.Keys.SPACE) {
//			this.playerHUD.displayChat("jifdjgkfsvhgfhgfhgfhgfdhgfhgfhgf"
//					+ "gfhgfhgfhgfhgfhgfhgfhgdhgfghgf"
//					+ "hgfgfhgfhgfhhgfhfdghgfhgfhgfhgfhgf"
//					+ "hgfdgfhgfhgfhgfhgfhgfhrd");
//		}
//		
//		if(keycode == Input.Keys.ENTER) {
//			this.playerHUD.displayChat("test");
//		}
//		
//		//if(keycode == Input.Keys.NUM_3)
//			//gameWorld.getMap().getTiledMap().getLayers().get(0).setVisible(!gameWorld.getMap().getTiledMap().getLayers().get(0).isVisible());
//		//if(keycode == Input.Keys.I)
//		//	gameRenderer.toggleInventory();
		
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
			final NPC npc = this.gameWorld.targetLocationContainsNPC(newPosition);
			PlayerHUD hud = this.playerHUD;

			if(npc == null) {
				this.gameWorld.getPlayer().setTarget(null);
				this.gameWorld.getPlayer().setTargetLocation(newPosition);
	    		hud.displayText("");
	    		
			}else {
				
				this.gameWorld.getPlayer().setTarget(npc);
				
				if (npc.isTalkative) {
									 
					hud.displayChat(npc);
			    
//					final TextButton continueButton = hud.getButton();
//			    	continueButton.setVisible(true);
//
//					continueButton.addListener( new ClickListener() {
//					    @Override
//					    public void clicked(InputEvent event, float x, float y) {
//					    						    						    	
//					    	npc.setConversationIndex(npc.getConversationIndex() + 1);
//					    	System.out.println("Index " + npc.getConversationIndex());
//					    	System.out.println("Size " + npc.getConversation().size());
//					    	
//					    	if (npc.getConversationIndex() < npc.getConversation().size()) {
//
//					    		hud.displayChat(npc.getConversation().get(npc.getConversationIndex()));					    		
//
//					    	} else if (npc.getConversationIndex() == npc.getConversation().size()) {
//					    		System.out.println("hit");
//					    		hud.displayChat("");
//					    		//chatBox.;
//					    		continueButton.setVisible(false);
//						    	npc.setConversationIndex(0);
//
//					    	}
//					    }
//					} );
				} else {
					hud.displayText("This person doesn't look interested in talking right now.");					    		
				}
			}
		}
		else if (button == Buttons.LEFT) {
			// check active spells
			if(freezeSpellActivated) {
				Vector3 coordinates = new Vector3(this.gameRenderer.getTargetCircleX(), this.gameRenderer.getTargetCircleY(), 0);
				Vector3 positionInGame = gameRenderer.getCamera().unproject(coordinates);

				this.gameWorld.performIceSpellCast(new Circle(positionInGame.x,positionInGame.y, this.gameRenderer.getTargetCircleSize()));
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
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
