package com.adventuresof.game.quest;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Task {
	
	private String title;
	private String description;
	private ArrayList<String> conversation;
	
	public Task (Element task) {
		
		NodeList taskTitleList = task.getElementsByTagName("TaskTitle");
		Node taskTitle = (Element) taskTitleList.item(0);
		this.title = taskTitle.getTextContent();
		
		Object taskTextList = task.getElementsByTagName("TaskText") == null ? "" : task.getElementsByTagName("TaskText");
		
		if(taskTextList != "") { 
			Node taskText = (Element) ((NodeList) taskTextList).item(0);
			this.description = taskText.getTextContent();
			System.out.println(this.description);			
		}
		
		NodeList conversationList = task.getElementsByTagName("Conversation");
		Element conversation = (Element) conversationList.item(0);
		
		NodeList dialogueList = conversation.getElementsByTagName("Dialogue");
		
		ArrayList<String> tempList = new ArrayList<String>();
		
		for (int i = 0; i < dialogueList.getLength(); i++) {
			Node dialogue = (Element) dialogueList.item(i);
			System.out.println(dialogue.getTextContent());
			tempList.add(dialogue.getTextContent());	
		}
		
		this.conversation = tempList;
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
}
