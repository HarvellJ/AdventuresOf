package com.adventuresof.screens;

import java.util.ArrayList;
import java.util.Collections;

import com.adventuresof.game.character.Player;
import com.adventuresof.game.dialogue.DialogueActor;
import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.inventory.InventoryActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerHUD implements Screen{

	private InventoryActor inventoryActor;
	private DialogueActor dialogueActor;
	public  Stage stage;
	private Viewport viewport;
	private ArrayList<String> conversation = new ArrayList<String>();

	
	public PlayerHUD(Camera camera, Player player) {
		viewport = new ScreenViewport(camera);
		this.stage = new Stage(viewport);
		DragAndDrop dragAndDrop = new DragAndDrop();
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		inventoryActor = new InventoryActor(player.getInventory(), dragAndDrop, skin);
		dialogueActor = new DialogueActor(skin);
		stage.addActor(inventoryActor);
		
	}
	
	public void displayChat () {
		ArrayList<String> conversation = new ArrayList<String>();
		Collections.addAll(conversation, "test1", "test2","test");	
		System.out.println(conversation.get(0));
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		TextArea textArea = new TextArea(conversation.get(0), skin);
		textArea.setDisabled(true);
		ScrollPane pane = new ScrollPane(textArea, skin);
		this.stage.addActor(pane);
		conversation.remove(0);
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

}
