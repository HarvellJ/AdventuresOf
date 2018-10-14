package com.mygdx.UI;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Entity;
import com.mygdx.profile.ProfileObserver;
import com.mygdx.UI.InventoryUI;
import com.mygdx.UI.StatusUI;

public class PlayerHUD implements Screen,  ProfileObserver {

	 private Stage _stage;
	 private Viewport _viewport;
	 private Camera _camera;
	 private StatusUI _statusUI;
	 private InventoryUI _inventoryUI;
	 
	 public PlayerHUD(Camera camera, Entity player) {
	 _viewport = new ScreenViewport(_camera);
	 _stage = new Stage(_viewport);
	 _statusUI = new StatusUI();
	 _inventoryUI = new InventoryUI();
	 }
	
	public PlayerHUD(OrthographicCamera _hudCamera, Entity _player) {
		// TODO Auto-generated constructor stub
	}

	public InputProcessor getStage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		_stage.act(delta);
        _stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
        _stage.getViewport().update(width, height, true);		
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
		 _stage.dispose();		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
