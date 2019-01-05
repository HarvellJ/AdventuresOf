package com.adventuresof.helpers;

import java.util.ArrayList;
import java.util.Collections;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.combat.SpellType;
import com.adventuresof.game.quest.Quest;
import com.adventuresof.game.world.GameRenderer;
import com.adventuresof.game.world.AdventuresOfGameWorld;
import com.adventuresof.screens.MainGameScreen;
import com.adventuresof.screens.PlayerHUD;
import com.badlogic.gdx.Gdx;
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

	private AdventuresOfGameWorld gameWorld;
	private GameRenderer gameRenderer;
	private PlayerHUD playerHUD;

	private boolean abilityOneActivated;
	private boolean abilityTwoActivated;
	private boolean abilityThreeActivated;
	private boolean abilityFourActivated;

	public PlayerController(AdventuresOfGameWorld gameWorld, GameRenderer gameRenderer, PlayerHUD playerHUD) {
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
		if(keycode == Input.Keys.NUM_1) {
			// perform ice spell
			this.abilityTwoActivated = false;
			this.abilityThreeActivated = false;
			this.abilityFourActivated = false;
			if(!abilityOneActivated) {
				if(this.gameWorld.getPlayer().getCharacterClass().getAbilityOne().getSpellType() != SpellType.buff) {
					this.gameRenderer.setShowTargetCircle(true, this.gameWorld.getPlayer().getCharacterClass().getAbilityOne().getAreaOfAffect());
					this.abilityOneActivated = true;
				}else{
					this.gameWorld.getPlayer().performAbilityOne(this.gameWorld.getPlayer().getCurrentPosition().x, this.gameWorld.getPlayer().getCurrentPosition().y);			
				}
			}else {
				this.gameRenderer.setShowTargetCircle(false, 0);
				this.abilityOneActivated = false;
			}

		}
		if(keycode == Input.Keys.NUM_2) {
			// perform tornado spell
			this.abilityOneActivated = false;
			this.abilityThreeActivated = false;
			this.abilityFourActivated = false;
			if(!abilityTwoActivated) {
				if(this.gameWorld.getPlayer().getCharacterClass().getAbilityTwo().getSpellType() != SpellType.buff) {
					this.gameRenderer.setShowTargetCircle(true, this.gameWorld.getPlayer().getCharacterClass().getAbilityTwo().getAreaOfAffect());
					this.abilityTwoActivated = true;
				}
				else{
					this.gameWorld.getPlayer().performAbilityTwo(this.gameWorld.getPlayer().getCurrentPosition().x, this.gameWorld.getPlayer().getCurrentPosition().y);			
				}
			}else {
				this.gameRenderer.setShowTargetCircle(false, 0);
				this.abilityTwoActivated = false;
			}
		}
		if(keycode == Input.Keys.NUM_3) {
			// perform tornado spell
			this.abilityOneActivated = false;
			this.abilityTwoActivated = false;
			this.abilityFourActivated = false;

			if(!abilityThreeActivated) {
				if(this.gameWorld.getPlayer().getCharacterClass().getAbilityThree().getSpellType() != SpellType.buff) {
					this.gameRenderer.setShowTargetCircle(true, this.gameWorld.getPlayer().getCharacterClass().getAbilityThree().getAreaOfAffect());
					this.abilityThreeActivated = true;
				}else{
					this.gameWorld.getPlayer().performAbilityThree(this.gameWorld.getPlayer().getCurrentPosition().x, this.gameWorld.getPlayer().getCurrentPosition().y);			
				}
			}else {
				this.gameRenderer.setShowTargetCircle(false, 0);
				this.abilityThreeActivated = false;
			}
		}
		if(keycode == Input.Keys.NUM_4) {
			// perform tornado spell
			this.abilityOneActivated = false;
			this.abilityTwoActivated = false;
			this.abilityThreeActivated = false;
			if(!abilityFourActivated) {
				if(this.gameWorld.getPlayer().getCharacterClass().getAbilityFour().getSpellType() != SpellType.buff) {
					this.gameRenderer.setShowTargetCircle(true, this.gameWorld.getPlayer().getCharacterClass().getAbilityFour().getAreaOfAffect());
					this.abilityFourActivated = true;
				}else{
					this.gameWorld.getPlayer().performAbilityFour(this.gameWorld.getPlayer().getCurrentPosition().x, this.gameWorld.getPlayer().getCurrentPosition().y);			
				}
			}else {
				this.gameRenderer.setShowTargetCircle(false, 0);
				this.abilityFourActivated = false;
			}
		}

		//if(keycode == Input.Keys.NUM_3)
		//gameWorld.getMap().getTiledMap().getLayers().get(0).setVisible(!gameWorld.getMap().getTiledMap().getLayers().get(0).isVisible());
		//if(keycode == Input.Keys.I)
		//	gameRenderer.toggleInventory();

		if (keycode == Input.Keys.SPACE) {
			Quest test = new Quest("");
			System.out.println(test.toString());
		}

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
			//			NPC npc = this.gameWorld.targetLocationContainsNPC(newPosition);
			//			if(npc == null) {
			//this.gameWorld.getPlayer().setTarget(null);
			this.gameWorld.getPlayer().setTargetLocation(newPosition);
			//			}else {
			//				// only target hostile NPCs
			//				if(npc.isHostile())
			//				this.gameWorld.getPlayer().setTarget(npc);
			//			}

			// check if player has selected a position containing an NPC
			NPC npc = this.gameWorld.targetLocationContainsNPC(newPosition);
			PlayerHUD hud = this.playerHUD;

			if(npc == null) {
				this.gameWorld.getPlayer().setTarget(null);
				this.gameWorld.getPlayer().setTargetLocation(newPosition);
				hud.displayText("");

			} else {

				if (npc.isTalkative()) {
					this.gameWorld.getPlayer().setTargetLocation(newPosition);

					hud.displayChat(npc, this.gameWorld);

					/* Removed click to continue button for now as it was causing problems
					final TextButton continueButton = hud.getButton();
					continueButton.setVisible(true);

					continueButton.addListener( new ClickListener() {
					    @Override
					    public void clicked(InputEvent event, float x, float y) {

					    	npc.setConversationIndex(npc.getConversationIndex() + 1);
					    	System.out.println("Index " + npc.getConversationIndex());
					    	System.out.println("Size " + npc.getConversation().size());

					    	if (npc.getConversationIndex() < npc.getConversation().size()) {

					    		hud.displayChat(npc.getConversation().get(npc.getConversationIndex()));					    		

					    	} else if (npc.getConversationIndex() == npc.getConversation().size()) {
					    		System.out.println("hit");
					    		hud.displayChat("");
					    		//chatBox.;
					    		continueButton.setVisible(false);
						    	npc.setConversationIndex(0);

					    	}
					    }
					} );
					 */

				} else {
					hud.displayText("This person doesn't look interested in talking right now.");					    		
				}
			} 


		}

		else if (button == Buttons.LEFT) {
			Vector3 coordinates;
			Vector3 positionInGame;
			// check active spells
			if(abilityOneActivated) {			
				if(this.gameWorld.getPlayer().getCharacterClass().getAbilityOne().getSpellType() != SpellType.buff)
				{
					coordinates = new Vector3(this.gameRenderer.getTargetCircleX(), this.gameRenderer.getTargetCircleY(), 0);
					positionInGame = gameRenderer.getCamera().unproject(coordinates);
					this.gameWorld.getPlayer().performAbilityOne(positionInGame.x, positionInGame.y);
				}else {
					this.gameWorld.getPlayer().performAbilityOne(this.gameWorld.getPlayer().getCurrentPosition().x, this.gameWorld.getPlayer().getCurrentPosition().y);			
				}
			}
			else if(abilityTwoActivated) {	
				if(this.gameWorld.getPlayer().getCharacterClass().getAbilityTwo().getSpellType() != SpellType.buff)
				{
					coordinates = new Vector3(this.gameRenderer.getTargetCircleX(), this.gameRenderer.getTargetCircleY(), 0);
					positionInGame = gameRenderer.getCamera().unproject(coordinates);
					this.gameWorld.getPlayer().performAbilityTwo(positionInGame.x, positionInGame.y);
				}else {
					this.gameWorld.getPlayer().performAbilityTwo(this.gameWorld.getPlayer().getCurrentPosition().x, this.gameWorld.getPlayer().getCurrentPosition().y);			
				}
			}
			else if(abilityThreeActivated) {
				if(this.gameWorld.getPlayer().getCharacterClass().getAbilityThree().getSpellType() != SpellType.buff)
				{
					coordinates = new Vector3(this.gameRenderer.getTargetCircleX(), this.gameRenderer.getTargetCircleY(), 0);
					positionInGame = gameRenderer.getCamera().unproject(coordinates);
					this.gameWorld.getPlayer().performAbilityThree(positionInGame.x, positionInGame.y);
				}else {
					this.gameWorld.getPlayer().performAbilityThree(this.gameWorld.getPlayer().getCurrentPosition().x, this.gameWorld.getPlayer().getCurrentPosition().y);			
				}
			}
			else if(abilityFourActivated) {	
				if(this.gameWorld.getPlayer().getCharacterClass().getAbilityFour().getSpellType() != SpellType.buff)
				{
					coordinates = new Vector3(this.gameRenderer.getTargetCircleX(), this.gameRenderer.getTargetCircleY(), 0);
					positionInGame = gameRenderer.getCamera().unproject(coordinates);
					this.gameWorld.getPlayer().performAbilityFour(positionInGame.x, positionInGame.y);			
				}else {
					this.gameWorld.getPlayer().performAbilityFour(this.gameWorld.getPlayer().getCurrentPosition().x, this.gameWorld.getPlayer().getCurrentPosition().y);			
				}
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
		if(abilityOneActivated && this.gameWorld.getPlayer().getCharacterClass().getAbilityOne().getSpellType() != SpellType.buff)	
		{
			this.gameRenderer.setTargetingCircleLocation(screenX, screenY);
		}

		if(abilityTwoActivated && this.gameWorld.getPlayer().getCharacterClass().getAbilityTwo().getSpellType() != SpellType.buff)		
		{
			this.gameRenderer.setTargetingCircleLocation(screenX, screenY);
		}

		if(abilityThreeActivated && this.gameWorld.getPlayer().getCharacterClass().getAbilityThree().getSpellType() != SpellType.buff)		
		{
			this.gameRenderer.setTargetingCircleLocation(screenX, screenY);
		}

		if(abilityFourActivated && this.gameWorld.getPlayer().getCharacterClass().getAbilityFour().getSpellType() != SpellType.buff)		
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
