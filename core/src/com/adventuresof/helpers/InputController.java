package com.adventuresof.helpers;

import com.adventuresof.game.character.Player;
import com.adventuresof.game.world.GameRenderer;
import com.adventuresof.game.world.GameWorld;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

public class InputController implements InputProcessor{

	private GameWorld gameWorld;
	private GameRenderer gameRenderer;

	public InputController(GameWorld gameWorld, GameRenderer gameRenderer) {
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
		if(keycode == Input.Keys.NUM_1)
			gameWorld.getMap().getTiledMap().getLayers().get(0).setVisible(!gameWorld.getMap().getTiledMap().getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			gameWorld.getMap().getTiledMap().getLayers().get(1).setVisible(!gameWorld.getMap().getTiledMap().getLayers().get(1).isVisible());		
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
		Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
		Vector3 newPosition = gameRenderer.getCamera().unproject(clickCoordinates);
		this.gameWorld.getPlayer().setTargetLocation(newPosition);
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
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
