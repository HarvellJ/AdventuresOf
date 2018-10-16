package com.mygdx.game;


	import com.mygdx.screens.MainGameScreen;
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

	public class AdventuresOfGame extends Game {

		public SpriteBatch spriteBatch;


		@Override public void create () {
			spriteBatch = new SpriteBatch();
			this.setScreen(new MainGameScreen(this));
		}

		public void render () {
			super.render();
		}
	}
