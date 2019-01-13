package com.adventuresof.game.quest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.Gdx;

public class Quest {

	private String title;
	private String description;
	private List<Task> tasks;

	private ProgressEnum progress;
	
	public Quest (String questName, String name) {
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(Gdx.files.internal("quests//" + questName + ".xml").read());
			
			NodeList questList = doc.getElementsByTagName("Quest");
			Element quest = (Element) questList.item(0);
			
			NodeList titleList = quest.getElementsByTagName("QuestTitle");
			Node title = (Element) titleList.item(0);
			this.title = title.getTextContent();
			
			NodeList descriptionList = quest.getElementsByTagName("QuestDescription");
			Node description = (Element) descriptionList.item(0);
			this.description = description.getTextContent();
			
			NodeList tasksList = quest.getElementsByTagName("Tasks");
			Element tasks = (Element) tasksList.item(0);
			
			NodeList taskList = tasks.getElementsByTagName("Task");
			
			ArrayList<Task> tempList = new ArrayList<Task>();
			
			for (int i = 0; i < taskList.getLength(); i++) {
				Element taskElement = (Element) taskList.item(i);
				
				Task task = new Task (taskElement, name);
				
				tempList.add(task);

			}
			
			this.tasks = tempList;
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		this.progress = ProgressEnum.INCOMPLETE;
	}
	
	@Override
	public String toString() {
		
		String taskText = "";
		String taskDescription = "";
		ArrayList<String> taskConversation;
		ArrayList<String> emptyString = new ArrayList <String>();
		String conversationText = "\n        Conversation: ";
		int i = 0;
		
		for (Task task : this.tasks) {
			i++;
			taskDescription = task.getDescription() == null ? "" : " - " + task.getDescription();				
			
			taskConversation = task.getConversation() == null ? emptyString : task.getConversation();
			
			if(taskConversation.size() > 0) {
				for (String dialogue : task.getConversation()) {
					conversationText += "\n         " + dialogue;
				}
			} else {
				conversationText = "";
			}
				

			
			taskText += "\n     " + i + ". " + task.getTitle() + taskDescription + conversationText + ".";
		}
		
		String quest = ("Title: " + this.title + 
						"\nDescription: " + this.description + 
						"\nProgress: " + this.progress +
						"\nTasks: " + taskText);
		
		return quest;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}

	public ProgressEnum getProgress() {
		return progress;
	}

	public void setProgress(ProgressEnum progress) {
		this.progress = progress;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}

}
