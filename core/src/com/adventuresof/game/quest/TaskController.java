package com.adventuresof.game.quest;

import java.util.ArrayList;
import java.util.Arrays;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.inventory.Item;
import com.adventuresof.game.inventory.ItemEnum;
import com.adventuresof.game.world.AdventuresOfGameWorld;
import com.adventuresof.screens.PlayerHUD;

public class TaskController {
	
	private Task task;
	private PlayerHUD hud;
	private NPC npc;
	private AdventuresOfGameWorld gameWorld;
	private Inventory inventory;
	
	public TaskController (Task task, PlayerHUD hud, NPC npc, AdventuresOfGameWorld gameWorld, Inventory inventory) {
		this.task = task;
		this.hud = hud;
		this.npc = npc;
		this.gameWorld = gameWorld;
		this.inventory = inventory;
	}
	
	public Boolean handleTask () { 
		
		System.out.println(task.getType());
		
		Boolean isComplete = false;

		switch(task.getType()) {
		
		
			case SLAYER : isComplete = slayerCheck();
				break;
			case CONVERSATION : isComplete = conversationCheck();
				break;
			case COLLECT: isComplete = collectionCheck();
				break;
		}
		

		return isComplete;
		
	}
	
	private Boolean slayerCheck () {
		//npcName = name of the monster you need to slay
		//target = number of that specific monster you have to slay
		//TO DO: some way of checking whether that specific monster has been killed
			//once target has been reached set task status to complete
		
		//this.task.setProgress(ProgressEnum.COMPLETE);
		
		String result = "";
				
		for (NPC npc : this.gameWorld.getPlayer().getNpcsKilled()) {
			
			System.out.println("Target: " + task.getTarget());
			System.out.println("npc: " + npc.getName());
			
			if (npc.getName().equals(task.getTarget())) {
				task.setTotalSlayed(task.getTotalSlayed() + 1);
			}
	
		}
		
		
		if (task.getTotalSlayed() >= task.getQuantity()) {
			this.npc.setConversation(task.getConversation());
			result = this.hud.displayChat(this.npc, this.gameWorld);
		} 
		
		if(result.equals("end")) {	
			this.task.setProgress(ProgressEnum.COMPLETE);
			
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean conversationCheck () {
		//npcName = name of npc you must talk to
		//TO DO: some way of checking that you have spoken to the NPC.
			//check the length of the array vs the total messages displayed?
		
		this.npc.setConversation(this.task.getConversation());
		
		String result = this.hud.displayChat(this.npc, this.gameWorld);
		
		
		if(result.equals("end")) {	
			this.task.setProgress(ProgressEnum.COMPLETE);
//			ArrayList<String> emptyArray = new ArrayList<String>(Arrays.asList(""));
//			this.npc.setConversation(emptyArray);
//			this.hud.displayChat(this.npc, this.gameWorld);

			return true;
		} else {
			return false;
		}

	}
	
	private Boolean collectionCheck () {
		//npcName = name of the npc that has sent you to collect an item
		//itemName = name of the item you have been sent to collect
		//TO DO: when speaking to the npc check whether the item is in the inventory
			//if so set task status to complete
			//if not repeat conversation
		
		String result = "";
		
		Item item = new Item(this.task.getItem());
				
		if(this.task.getQuantity() <= this.inventory.checkInventory(item)) {
			this.npc.setConversation(task.getConversation());
			result = this.hud.displayChat(this.npc, this.gameWorld);
		};
		
		if(result.equals("end")) {
			this.inventory.remove(item, this.task.getQuantity());
			this.task.setProgress(ProgressEnum.COMPLETE);
			
			return true;
		} else {
			return false;
		}
	}

}
