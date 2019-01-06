package com.adventuresof.game.inventory;

import com.adventuresof.game.item.Item;
import com.badlogic.gdx.utils.Array;

public class Inventory {

	    private Array<Slot> slots;

	    public Inventory() {
	        slots = new Array<Slot>(24);
	        for (int i = 0; i < 24 ; i++) {
	            slots.add(new Slot(null, 0));
	        }	        
	    }

	    public int checkInventory(Item item) {
	        int amount = 0;

	        for (Slot slot : slots) {
	        	
	        	if (slot.getItem() != null) {	        		
		            if (slot.getItem().getItem().equals(item.getItem())) {
		                amount += slot.getAmount();
		            }
	        	}
	        	
	        }

	        return amount;
	    }

	    public boolean store(Item item, int amount) {
	        // first check for a slot with the same item type
	        Slot itemSlot = this.firstSlotWithItem(item);
	        if (itemSlot != null) {
	            itemSlot.add(item, amount);
	            return true;
	        } else {
	            // now check for an available empty slot
	            Slot emptySlot = this.firstSlotWithItem(null);
	            if (emptySlot != null) {
	                emptySlot.add(item, amount);
	                return true;
	            }
	        }

	        return false;
	    }

	    public Array<Slot> getSlots() {
	        return slots;
	    }

	    private Slot firstSlotWithItem(Item item) {
	        for (Slot slot : slots) {
	            if (slot.getItem() == item) {
	                return slot;
	            }
	        }

	        return null;
	    }
	    
	    public void remove(Item item, int amount) {
	    	
	    	for (Slot slot : slots) {
	        	
	        	if (slot.getItem() != null) {	        		
		            if (slot.getItem().getItem().equals(item.getItem())) {
		            	slot.take(amount);
		            }
	        	}
	        	
	        }
	    }

	}

