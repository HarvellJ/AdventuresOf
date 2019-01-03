package com.adventuresof.screens;

import java.util.ArrayList;
import java.util.Collections;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.inventory.InventoryActor;
import com.adventuresof.game.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerHUD implements Screen{

	private InventoryActor inventoryActor;
	public  Stage stage;
	private Viewport viewport;
	private ArrayList<String> conversation = new ArrayList<String>();
	protected Table uiTable;
	private TextArea textArea;
	private int conversationIndex;
	private TextButton button;
	private Dialog chatBox;

	
	public PlayerHUD(Camera camera, Player player) {
		viewport = new ScreenViewport(camera);
		this.stage = new Stage(viewport);
		uiTable = new Table();
		uiTable.setFillParent(true);
		stage.addActor(uiTable);
		DragAndDrop dragAndDrop = new DragAndDrop();
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		inventoryActor = new InventoryActor(player.getInventory(), dragAndDrop, skin);
//		stage.addActor(inventoryActor);
		
		//setup textArea
		this.textArea = new TextArea("", skin);
		this.textArea.setDisabled(true);
		this.textArea.setText("");
		this.textArea.setWidth(800);
		this.textArea.setHeight(120);
		
		//setup continue button.
		this.button = new TextButton("Click to continue",skin);
		this.button.setVisible(false);

		//setup pane layout using table
//		Table paneTable = new Table();
//		paneTable.add(textArea).width(800);
//		paneTable.row();
//		paneTable.add(button).bottom().width(800);
		
		//setup actual pane using table
//		ScrollPane pane = new ScrollPane(paneTable, skin);
//		pane.setWidth(800);
		
		uiTable.pad(10);
		uiTable.add().bottom().colspan(3);
		uiTable.add().expandX().expandY();
		uiTable.add(inventoryActor).bottom();
		stage.addActor(textArea);	
		
	}
	
	public String displayChat (NPC npc, GameWorld gameWorld) {
		
		ArrayList<String> conversation = npc.getConversation();
    	
    	String returnValue = "";
    	
    	if (npc.getConversationIndex() < npc.getConversation().size()) {
    		
    		this.textArea.setText(npc.getName() + ": " + conversation.get(npc.getConversationIndex()));
			npc.setConversationIndex(npc.getConversationIndex() + 1);
			
			returnValue = "" + npc.getConversationIndex();
			
    	} else if (npc.getConversationIndex() == npc.getConversation().size()) {
    		this.textArea.setText("");
	    	npc.setConversationIndex(0);
	    	returnValue =  "end";
	    	
	    	// Only required for quick hack to set bandits to hostile, no longer needed
//	    	ArrayList<NPC> NPCs = gameWorld.getNPCs();
//	    	
//	    	for (NPC tempNPC : NPCs) {
//		    	if (tempNPC.getName() == "Bandit") {
//		    		tempNPC.setHostile(true);
//		    	}
//	    	}

    	}
    	
		return returnValue;
    	
	}
	
	public void displayText (String text) {
		this.textArea.setText(text);
	}

	public void toggleInventory() {
		if(inventoryActor.isVisible()) {
			  inventoryActor.setVisible(false);

		}else {
			  inventoryActor.setVisible(true);
		}
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void render(float delta) {
		//update inventory
		 stage.act();
	     stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public int getConversationIndex () {
		return this.conversationIndex;
	}
	
	public TextButton getButton () {
		return this.button;
	}
	
	public Dialog getChatBox () {
		return this.chatBox;
	}

}
