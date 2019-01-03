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
	
	public void handleTask () { 
		
		System.out.println(task.getType());
		
		switch(task.getType()) {
		
			case SLAYER : slayerCheck("", 0);
				break;
			case CONVERSATION : conversationCheck();
				break;
			case COLLECT: collectionCheck();
				break;
		}
		
	}
	
	private void slayerCheck (String npcName, int quantity) {
		//npcName = name of the monster you need to slay
		//target = number of that specific monster you have to slay
		//TO DO: some way of checking whether that specific monster has been killed
			//once target has been reached set task status to complete
		
		//this.task.setProgress(ProgressEnum.COMPLETE);		
	}
	
	public void conversationCheck () {
		//npcName = name of npc you must talk to
		//TO DO: some way of checking that you have spoken to the NPC.
			//check the length of the array vs the total messages displayed?
		
		this.npc.setConversation(this.task.getConversation());
		
		String result = this.hud.displayChat(this.npc, this.gameWorld);
		
		
		if(result.equals("end")) {	
			this.task.setProgress(ProgressEnum.COMPLETE);
			ArrayList<String> emptyArray = new ArrayList<String>(Arrays.asList(""));
			this.npc.setConversation(emptyArray);
		};

	}
	
	private void collectionCheck () {
		//npcName = name of the npc that has sent you to collect an item
		//itemName = name of the item you have been sent to collect
		//TO DO: when speaking to the npc check whether the item is in the inventory
			//if so set task status to complete
			//if not repeat conversation
		
		String result = "";
		
		Item item = new Item(this.task.getItem());
				
		if(0 < this.inventory.checkInventory(item)) {
			result = this.hud.displayChat(this.npc, this.gameWorld);
		};
		
		if(result.equals("end")) {	
			this.inventory.remove(item, this.task.getQuantity());
			this.task.setProgress(ProgressEnum.COMPLETE);
			this.npc.setConversation(task.getConversation());
		};
	}

}
