package com.adventuresof.screens;

import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.inventory.InventoryActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerHUD implements Screen{

	private InventoryActor inventoryActor;
	public  Stage stage;
	private Viewport viewport;
	
	public PlayerHUD(Camera camera) {
		viewport = new ScreenViewport(camera);
		stage = new Stage(viewport);
		DragAndDrop dragAndDrop = new DragAndDrop();
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		inventoryActor = new InventoryActor(new Inventory(), dragAndDrop, skin);
		stage.addActor(inventoryActor);

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
