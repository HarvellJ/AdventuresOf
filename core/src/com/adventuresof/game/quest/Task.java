package com.adventuresof.game.quest;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.adventuresof.game.item.Item;
import com.adventuresof.game.item.ItemEnum;

public class Task {
	
	private String title;
	private String description;
	private String npcName;
	private String target;
	private ItemEnum item;
	private int quantity;
	private ArrayList<String> conversation;
	private TaskTypeEnum type;
	private ProgressEnum progress;
	private int totalSlayed = 0;
	
	public Task (Element task, String name) {
		
		//retrieving the task title from the task element
		NodeList taskTitleList = task.getElementsByTagName("TaskTitle");
		Node taskTitle = (Element) taskTitleList.item(0);
		this.title = taskTitle.getTextContent();
		
		//initialising the progress to incomplete
		this.setProgress(ProgressEnum.INCOMPLETE);
		
		//retrieving the taskText from the task element 
		NodeList taskTextList = task.getElementsByTagName("TaskText");
		
		//checking whether the taskText exist for this specific task element
		if(taskTextList.getLength() > 0) { 
			Node taskText = (Element) ((NodeList) taskTextList).item(0);
			this.description = taskText.getTextContent();
		}
		
		//retrieving the taskType from the task element
		NodeList taskTypeList = task.getElementsByTagName("Type");
		Node taskType = (Element) ((NodeList) taskTypeList).item(0);
		
		//setting the appropriate TaskTypeEnum based on the xml field
		if(taskType.getTextContent().equals("Slayer"))
			this.type = TaskTypeEnum.SLAYER;
		else if (taskType.getTextContent().equals("Conversation"))
			this.type = TaskTypeEnum.CONVERSATION;
		else if (taskType.getTextContent().equals("Collect"))
			this.type = TaskTypeEnum.COLLECT;
		
		//retrieving the NPC name from the task element
		NodeList npcList = task.getElementsByTagName("NPC");
		
		//checking whether the NPC exist for this specific task element
		if(npcList.getLength() > 0) { 
			Node npc = (Element) ((NodeList) npcList).item(0);
			this.setNpcName(npc.getTextContent());
		}
		
		//retrieving the conversation from the task element
		NodeList conversationList = task.getElementsByTagName("Conversation");
		
		
		//checking whether a conversation exist for this specific task element
		if(conversationList.getLength() > 0) {
			Element conversation = (Element) conversationList.item(0);
			
			NodeList dialogueList = conversation.getElementsByTagName("Dialogue");
			
			//creating a temporary list to store each line of the conversation
			ArrayList<String> tempList = new ArrayList<String>();
			
			//adding each line of the conversation to the tempList
			for (int i = 0; i < dialogueList.getLength(); i++) {
				Node dialogue = (Element) dialogueList.item(i);
				tempList.add(dialogue.getTextContent().replace("{CHARACTER_NAME}", name));	
			}
			
			//Once the tempList contains the whole conversation set this conversation to the tempList
			this.setConversation(tempList);
		}
		
		//retrieving the item from the task element
		NodeList itemList = task.getElementsByTagName("Item");
		
		//checking whether an item exist for this specific task element
		if(itemList.getLength() > 0) { 
			Node item = (Element) ((NodeList) itemList).item(0);
			
			//setting the appropriate ItemEnum based on the xml field
			if(item.getTextContent().equals("Armor"))
				this.item = ItemEnum.ARMOR_UNCOMMON;			
			else if(item.getTextContent().equals("Shield"))
				this.item = ItemEnum.SHIELD_UNCOMMON;
			else if(item.getTextContent().equals("Sword"))
				this.item = ItemEnum.SWORD_UNCOMMON;	
		}
		
		//retrieving the item quantity from the task element
		NodeList quantityList = task.getElementsByTagName("Quantity");
		
		//checking whether an item exist for this specific task element
		if(quantityList.getLength() > 0) { 
			Node quantity = (Element) ((NodeList) quantityList).item(0);
			this.setQuantity(Integer.parseInt(quantity.getTextContent()));
		}
		
		//retrieving the slayer target from the task element
		NodeList targetList = task.getElementsByTagName("Target");
		
		//checking whether a target exist for this specific task element
		if(targetList.getLength() > 0) { 
			Node target = (Element) ((NodeList) targetList).item(0);
			this.setTarget(target.getTextContent());
		}



	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getConversation() {
		return conversation;
	}

	public void setConversation(ArrayList<String> conversation) {
		this.conversation = conversation;
	}

	public ProgressEnum getProgress() {
		return progress;
	}

	public void setProgress(ProgressEnum progress) {
		this.progress = progress;
	}

	public TaskTypeEnum getType() {
		return type;
	}

	public void setType(TaskTypeEnum type) {
		this.type = type;
	}
	
	public String toString () {
		
		String taskDescription = "";
		String npcName = "";
		ArrayList<String> taskConversation;
		ArrayList<String> emptyArray = new ArrayList <String>();
		String conversationText = "\n        Conversation: ";
		
		taskDescription = getDescription() == null ? "" : " - " + getDescription();
		
		npcName = getNpcName() == null ? "" : "\n        NPC name: " + getNpcName();
		
		taskConversation = getConversation() == null ? emptyArray : getConversation();
		
		
		if(taskConversation.size() > 0) {
			for (String dialogue : getConversation()) {
				conversationText += "\n         " + dialogue;
			}
		} else {
			conversationText = "";
		}
			

		
		return getTitle() + taskDescription + npcName + conversationText + ".";
	}

	public String getNpcName() {
		return npcName;
	}

	public void setNpcName(String npcName) {
		this.npcName = npcName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int i) {
		this.quantity = i;
	}

	public ItemEnum getItem() {
		return item;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getTotalSlayed() {
		return totalSlayed;
	}

	public void setTotalSlayed(int totalSlayed) {
		this.totalSlayed = totalSlayed;
	}
}
