package com.adventuresof.game.quest;

import java.util.ArrayList;
import java.util.Arrays;

import com.adventuresof.game.character.entities.NPC;
import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.item.Item;
import com.adventuresof.game.world.AdventuresOfGameWorld;
import com.adventuresof.screens.PlayerHUD;

/**
 * Task Controller Class
 * 
 */
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
		
		String result = "";
		ArrayList<NPC> npcsKilled = this.gameWorld.getPlayer().getNpcsKilled();
				
		for (int i = 0; i < npcsKilled.size(); i++) {
			
			System.out.println("Target: " + task.getTarget());
			System.out.println("npc: " + npcsKilled.get(i).getName());
			
			if (npcsKilled.get(i).getName().equals(task.getTarget())) {
				task.setTotalSlayed(task.getTotalSlayed() + 1);
				npcsKilled.remove(i);
				
			}
	
		}
		
		
		if (task.getTotalSlayed() >= task.getQuantity()) {
			this.npc.setConversation(task.getConversation());
			result = this.hud.displayChat(this.npc, this.gameWorld);
		} else {
			int leftToKill = task.getQuantity() - task.getTotalSlayed();
			ArrayList<String> altConversation = new ArrayList<String>(Arrays.asList("You still have " + leftToKill + " " + task.getTarget() + "(s) left to kill."));
			this.npc.setConversation(altConversation);
			this.hud.displayChat(this.npc, this.gameWorld);
		}
		
		if(result.equals("end")) {	
			this.task.setProgress(ProgressEnum.COMPLETE);
			
			return true;
		} else {
			return false;
		}
		
	}
	
	public Boolean conversationCheck () {
		
		this.npc.setConversation(this.task.getConversation());
		
		String result = this.hud.displayChat(this.npc, this.gameWorld);
		
		
		if(result.equals("end")) {
			System.out.println("hit 2");
			this.task.setProgress(ProgressEnum.COMPLETE);

			return true;
		} else {
			return false;
		}

	}
	
	private Boolean collectionCheck () {
		
		String result = "";
		
		Item item = new Item(this.task.getItem());
				
		if(this.task.getQuantity() <= this.inventory.checkInventory(item)) {
			this.npc.setConversation(task.getConversation());
			result = this.hud.displayChat(this.npc, this.gameWorld);
		} else {
			int leftToCollection = task.getQuantity() - this.inventory.checkInventory(item);
			ArrayList<String> altConversation = new ArrayList<String>(Arrays.asList("You still have " + leftToCollection + " " + task.getItem() + "(s) left to collect."));
			this.npc.setConversation(altConversation);
			this.hud.displayChat(this.npc, this.gameWorld);
		}
		
		if(result.equals("end")) {
			this.inventory.remove(item, this.task.getQuantity());
			this.task.setProgress(ProgressEnum.COMPLETE);
			
			return true;
		} else {
			return false;
		}
	}

}
