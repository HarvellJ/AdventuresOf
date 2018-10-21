package com.adventuresof.game.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.inventory.Item;
import com.adventuresof.game.world.GameZone;
import com.adventuresof.helpers.AnimationFactory;
import com.adventuresof.screens.MainGameScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player extends GameCharacter{

	private Inventory inventory; // Stores items the player has collected
	private ArrayList<GameZone> discoveredZones;

	public Player(TiledMapTileLayer accessibleTiles) {
		super(accessibleTiles, "animation_sheet.png", 8, 5, 1700, 1300);
		this.inventory = new Inventory();
		this.discoveredZones = new ArrayList<GameZone>();
	}

	public ArrayList<GameZone> getDiscoveredZones() {
		return discoveredZones;
	}

	public void setDiscoveredZones(ArrayList<GameZone> discoveredZones) {
		this.discoveredZones = discoveredZones;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void addItemToInventory(Item item) {
		this.inventory.store(item, 1);
	}

	public void addDiscoveredZone(GameZone zone) {
		if(!this.hasDiscoveredZone(zone))
			this.discoveredZones.add(zone);
	}

	public boolean hasDiscoveredZone(GameZone zone) {
		for(GameZone discoveredZone : discoveredZones) {
			if(discoveredZone.equals(zone))
				return true;		
		}
		return false;
	}

	protected void createAnimations() {
		this.runRightAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 5, 15, 0.08f);
		this.runLeftAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 5, 21, 0.08f);
		this.runDownAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 4, 5, 0.08f);
		this.runUpAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 4, 10, 0.08f);

		this.attackDownAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 3, 27, 1f);
		this.attackUpAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 3, 30, 1f);
		this.attackRightAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 3, 33, 1f);
		this.attackLeftAnimation = AnimationFactory.createAnimation(animationSheetName, frameCols, frameRows, 3, 36, 1f);
		
		this.idleUpAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 29, 0.08f);
		this.idleDownAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 1, 0.08f);
		this.idleLeftAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 23, 0.08f);
		this.idleRightAnimation = AnimationFactory.createAnimation(this.animationSheetName, frameCols, frameRows, 1, 14, 0.08f);
	}    

	protected void initiateCharacterTextures() {
		textureBack = new Texture(Gdx.files.internal("knight_back.png"));
		textureFront = new Texture(Gdx.files.internal("knight_front.png"));
		textureFacingLeft = new Texture(Gdx.files.internal("knight_face_left.png"));
		textureFacingRight = new Texture(Gdx.files.internal("knight_face_right.png"));  	
	}
}
