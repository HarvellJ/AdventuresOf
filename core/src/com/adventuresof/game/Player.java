package com.adventuresof.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class Player {
    private Vector3 pointToMoveTo;
    private Vector3 nextMove;
	private Texture texture;
	private TextureAtlas atlas;
    private Sprite sprite;
    private Animation<TextureRegion> runningAnimation;
    public Player() {
    	nextMove = new Vector3();
    	texture = new Texture(Gdx.files.internal("stoodStill.png"));
		sprite = new Sprite(texture);
		atlas = new TextureAtlas();
    }

    public void update() {
    	if(pointToMoveTo != null) {
    	
    	double destinationX = pointToMoveTo.x - sprite.getX();
        double destinationY = pointToMoveTo.y - sprite.getY();

    	double distanceToTravel = Math.sqrt(destinationX * destinationX + destinationY * destinationY);
    	destinationX = destinationX / distanceToTravel;
    	destinationY = destinationY / distanceToTravel;

    	double nextX = destinationX * 3;
    	double nextY = destinationY * 3;

    	double distanceTravelled = Math.sqrt(nextX * nextX + nextY * nextY);

    	if ( distanceTravelled > distanceToTravel )
    	{
    		pointToMoveTo = null;
    		runningAnimation = null;
    	}
    	else
    	{
    		sprite.setPosition(sprite.getX() +(float) nextX, sprite.getY() + (float) nextY); 
    		sprite.setFlip(destinationX < 0, false);
    	}
    	}
    }

    public void setTargetLocation(Vector3 pointToMoveTo) {
        this.pointToMoveTo = pointToMoveTo;
        this.startRunAnimation();
    }
    
    private void startRunAnimation() {
    	runningAnimation = 
    		    new Animation<TextureRegion>(0.033f, atlas.findRegions("adventurerRun"), PlayMode.LOOP);
    }

    public void drawPlayer(SpriteBatch spriteBatch) {
    	this.sprite.draw(spriteBatch);
    }
}
