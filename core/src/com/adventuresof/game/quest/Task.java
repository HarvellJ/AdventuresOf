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
	private TaskTypeEnum type;
	private ProgressEnum progress;
	
	public Task (Element task) {
		
		NodeList taskTitleList = task.getElementsByTagName("TaskTitle");
		Node taskTitle = (Element) taskTitleList.item(0);
		this.title = taskTitle.getTextContent();
		this.setProgress(ProgressEnum.INCOMPLETE);
		
		NodeList taskTextList = task.getElementsByTagName("TaskText");
		
		if(taskTextList.getLength() > 0) { 
			Node taskText = (Element) ((NodeList) taskTextList).item(0);
			this.description = taskText.getTextContent();
		}
		
		NodeList taskTypeList = task.getElementsByTagName("Type");
		Node taskType = (Element) ((NodeList) taskTypeList).item(0);
		
		if(taskType.getTextContent().equals("Slayer"))
			this.type = TaskTypeEnum.SLAYER;
		else if (taskType.getTextContent().equals("Conversation"))
			this.type = TaskTypeEnum.CONVERSATION;
		else if (taskType.getTextContent().equals("Collect"))
			this.type = TaskTypeEnum.COLLECT;
		
		NodeList conversationList = task.getElementsByTagName("Conversation");
		
		if(conversationList.getLength() > 0) {
			Element conversation = (Element) conversationList.item(0);
			
			NodeList dialogueList = conversation.getElementsByTagName("Dialogue");
			
			ArrayList<String> tempList = new ArrayList<String>();
			
			for (int i = 0; i < dialogueList.getLength(); i++) {
				Node dialogue = (Element) dialogueList.item(i);
				tempList.add(dialogue.getTextContent());	
			}
			
			this.setConversation(tempList);
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
		ArrayList<String> taskConversation;
		ArrayList<String> emptyArray = new ArrayList <String>();
		String conversationText = "\n        Conversation: ";
		
		taskDescription = getDescription() == null ? "" : " - " + getDescription();				
		
		taskConversation = getConversation() == null ? emptyArray : getConversation();
		
		if(taskConversation.size() > 0) {
			for (String dialogue : getConversation()) {
				conversationText += "\n         " + dialogue;
			}
		} else {
			conversationText = "";
		}
			

		
		return getTitle() + taskDescription + conversationText + ".";
	}
}
