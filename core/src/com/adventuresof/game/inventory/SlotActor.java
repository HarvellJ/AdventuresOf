package com.adventuresof.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SlotActor extends ImageButton implements SlotListener {

	    private Slot slot;

	    private Skin skin;

	    public SlotActor(Skin skin, Slot slot) {
	        super(createStyle(skin, slot));
	        this.slot = slot;
	        this.skin = skin;

	        // notify actor when the slot changes
	        slot.addListener(this);
	    }

	    /**
	     * This will create a new style for our image button, with the correct image for the item type.
	     */
	    private static ImageButtonStyle createStyle(Skin skin, Slot slot) {
	        TextureAtlas icons = new TextureAtlas("icons.atlas");
	        TextureRegion image;
	        if (slot.getItem() != null) {
	        	image = new TextureRegion(new Texture(Gdx.files.internal(slot.getItem().getItem().getTextureRegion())));
	        } else {
	            image = icons.findRegion("nothing");
	        }
	        ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
	        style.imageUp = new TextureRegionDrawable(image);
	        style.imageDown = new TextureRegionDrawable(image);
	       
	        return style;
	    }

	    @Override
	    public void hasChanged(Slot slot) {
	        // when the slot changes, switch icon
	        setStyle(createStyle(skin, slot));
	    }
	    
	    

	    public Slot getSlot() {
	        return slot;
	    }

	}
