package com.adventuresof.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public class InventoryActor extends Window {

	    public InventoryActor(Inventory inventory, DragAndDrop dragAndDrop, Skin skin) {
	        super("Inventory", skin);

	        // basic layout
	        setPosition(0, 0);
	        defaults().space(8);
	        row().fill().expandX();

	        // run through all slots and create SlotActors for each
	        int i = 0;
	        for (Slot slot : inventory.getSlots()) {
	            SlotActor slotActor = new SlotActor(skin, slot);
	            add(slotActor);

	           // ignored for now 
	           //dragAndDrop.addSource(new SlotSource(slotActor));
	           //dragAndDrop.addTarget(new SlotTarget(slotActor));

	            i++;
	            // every 5 cells, we are going to jump to a new row
	            if (i % 5 == 0) {
	                row();
	            }
	        }

	        pack();

	        // it is shown by default
	        setVisible(true);
	    }
	}

