package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.EntityConfig;
import com.mygdx.game.Component;
import com.mygdx.game.Entity;
import com.mygdx.game.InputComponent;
import com.mygdx.game.ComponentObserver;
import com.mygdx.game.ComponentSubject;

public class Entity {

	 private static final String TAG = Entity.class.getSimpleName();
	 private Json _json;
	 private EntityConfig _entityConfig;
	 
	 public static final int FRAME_WIDTH = 16;
	 public static final int FRAME_HEIGHT = 16;
	 private static final int MAX_COMPONENTS = 5;
	 private Array<Component> _components;
	 private InputComponent _inputComponent;
	 
		public Entity(Entity entity){
			set(entity);
		}
		
		private Entity set(Entity entity) {
			_inputComponent = entity._inputComponent;

			if( _components == null ){
				_components = new Array<Component>(MAX_COMPONENTS);
			}
			_components.clear();
			_components.add(_inputComponent);

			_json = entity._json;

			_entityConfig = new EntityConfig(entity._entityConfig);
			return this;
		}

		public Entity(InputComponent inputComponent){
			_entityConfig = new EntityConfig();
			_json = new Json();

			_components = new Array<Component>(MAX_COMPONENTS);

			_inputComponent = inputComponent;

			_components.add(_inputComponent);
		}
		
	 public EntityConfig getEntityConfig() {
	 return _entityConfig;
	 }

	 static public EntityConfig getEntityConfig(String configFilePath){
			Json json = new Json();
			return json.fromJson(EntityConfig.class, Gdx.files.internal(configFilePath));
		}
	 
	 public void sendMessage(Component.MESSAGE messageType, String ...
			 args){
			  String fullMessage = messageType.toString();
			  for (String string : args) {
			  fullMessage += Component.MESSAGE_TOKEN + string;
			  }
			  for(Component component: _components){
			  component.receiveMessage(fullMessage);
			  }
	 }
	 
		public void registerObserver(ComponentObserver observer){
			_inputComponent.addObserver(observer);
		}

		public void unregisterObservers(){
			_inputComponent.removeAllObservers();
		}

		public void update(float delta){
			_inputComponent.update(this, delta);
		}

		public void updateInput(float delta){
			_inputComponent.update(this, delta);
		}
	
	static public Array<EntityConfig> getEntityConfigs(String
			 configFilePath){
			 Json json = new Json();
			 Array<EntityConfig> configs = new Array<EntityConfig>();
			 ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(configFilePath));
			 for (JsonValue jsonVal : list) {
			 configs.add(json.readValue(EntityConfig.class,
			 jsonVal));
			 }
			 return configs;
			 }
		
			  
	public InputProcessor getInputProcessor(){
		return _inputComponent;
	}
	
	public void setEntityConfig(EntityConfig entityConfig){
		this._entityConfig = entityConfig;
	}
}
