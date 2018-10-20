package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.Component;
import com.mygdx.game.ComponentSubject;


public abstract class InputComponent extends ComponentSubject implements Component, InputProcessor {

	protected Json _json;
    
    protected enum Mouse {
        SELECT, DOACTION
    }
    
    protected static Map<Mouse, Boolean> mouseButtons = new HashMap<Mouse, Boolean>();
    
    static {
        mouseButtons.put(Mouse.SELECT, false);
        mouseButtons.put(Mouse.DOACTION, false);
    };
    
    InputComponent(){
        _json = new Json();
    }
    
    public abstract void update(Entity entity, float delta);
		
}
