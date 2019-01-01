package com.adventuresof.game.quest;

public class TaskController {
	
	private void slayerCheck (String npcName, int target) {
		//npcName = name of the monster you need to slay
		//target = number of that specific monster you have to slay
		//TO DO: some way of checking whether that specific monster has been killed
			//once target has been reached set task status to complete
		
	}
	
	private void conversationCheck (String npcName) {
		//npcName = name of npc you must talk to
		//TO DO: some way of checking that you have spoken to the NPC.
			//check the length of the array vs the total messages displayed?
	}
	
	private void collectionCheck (String npcName, String itemName) {
		//npcName = name of the npc that has sent you to collect an item
		//itemName = name of the item you have been sent to collect
		//TO DO: when speaking to the npc check whether the item is in the inventory
			//if so set task status to complete
			//if not repeat conversation
	}

}
