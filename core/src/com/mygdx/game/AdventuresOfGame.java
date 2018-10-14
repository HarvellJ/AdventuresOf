package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
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

public class AdventuresOfGame extends ApplicationAdapter implements InputProcessor {
	    Texture img;
	    TiledMap tiledMap;
	    OrthographicCamera camera;
	    TiledMapRenderer tiledMapRenderer;
	    ShapeRenderer shapeRenderer;
	    SpriteBatch sb;
	    Texture texture;
	    Sprite sprite;
	    
	    // the object layer ID
        int objectLayerId = 1;
	    
	    @Override public void create () {
	        float w = Gdx.graphics.getWidth();
	        float h = Gdx.graphics.getHeight();

	        camera = new OrthographicCamera();
	        camera.setToOrtho(false,w,h);
	        camera.update();
	        tiledMap = new TmxMapLoader().load("TestMap.tmx");
	        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	        Gdx.input.setInputProcessor(this);
	        
	        // object renderer
	        shapeRenderer = new ShapeRenderer();
	        
	        sb = new SpriteBatch();
	        texture = new Texture(Gdx.files.internal("run.gif"));
	        sprite = new Sprite(texture);
	    }

	    public void render () {
	        Gdx.gl.glClearColor(1, 0, 0, 1);
	        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        camera.update();
	        tiledMapRenderer.setView(camera);
	        tiledMapRenderer.render();
	        
	        // render any map objects 
	        shapeRenderer.setProjectionMatrix(camera.combined);
	        for (MapObject object : tiledMap.getLayers().get("collisionLayer").getObjects()) {
	        	if(object instanceof RectangleMapObject) {
	        		Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
	        		shapeRenderer.begin(ShapeType.Filled);
	        		shapeRenderer.rect(rectangle.x,  rectangle.y,  rectangle.width,   rectangle.height);
	        		shapeRenderer.end();
	        	}
	        	else if(object instanceof CircleMapObject) {
	        		Circle circle = ((CircleMapObject) object).getCircle();
	        		shapeRenderer.begin(ShapeType.Filled);
	        		shapeRenderer.circle(circle.x,  circle.y,  circle.radius);
	        		shapeRenderer.end();
	        	}	        	
	        }
	        
	        sb.setProjectionMatrix(camera.combined);
	        sb.begin();
	        sprite.draw(sb);
	        sb.end();
	    }

	    @Override public boolean keyDown(int keycode) {
	        return false;
	    }

	    @Override public boolean keyUp(int keycode) {
	        if(keycode == Input.Keys.LEFT)
	            camera.translate(-45,0);
	        if(keycode == Input.Keys.RIGHT)
	            camera.translate(45, 0);
	        if(keycode == Input.Keys.UP)
	            camera.translate(0,45);
	        if(keycode == Input.Keys.DOWN)
	            camera.translate(0,-45);
	        if(keycode == Input.Keys.NUM_1)
	            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
	        if(keycode == Input.Keys.NUM_2)
	            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
	        return false;
	    }

	    @Override public boolean keyTyped(char character) {

	        return false;
	    }

	    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
	        Vector3 newPosition = camera.unproject(clickCoordinates);
	        		        	        
	        // move character	        
	        sprite.setPosition(newPosition.x, newPosition.y);
	        
	        // Handle collision detection (after moving)
	        MapLayer collisionObjectLayer = (MapLayer)tiledMap.getLayers().get(objectLayerId);
	        MapObjects objects = collisionObjectLayer.getObjects();
	        	     
	        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

	            Rectangle rectangle = rectangleObject.getRectangle();
	            if (Intersector.overlaps(rectangle, sprite.getBoundingRectangle())) {
	                // collision occurred
			        sprite.setPosition(newPosition.x + rectangle.width, newPosition.y + rectangle.height);
	            	break;
	            }	     
	        }	          	        
	        return true;
	    }

	    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	        return false;
	    }

	    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
	        return false;
	    }

	    @Override public boolean mouseMoved(int screenX, int screenY) {
	        return false;
	    }

	    @Override public boolean scrolled(int amount) {
	        return false;
	    }
	}
	 

