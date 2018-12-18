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

import com.adventuresof.helpers.AnimationFactory;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Quest {

	private String title;
	private String description;
	private List<Task> tasks;
	private ProgressEnum progress;
	
	public Quest (String questName) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("SlayerQuest.xml");
			
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
				
				Task task = new Task (taskElement);
				tempList.add(task);

			}
			
			this.tasks = tempList;
			
			//System.out.println(title.getTextContent() + description.getTextContent());
			
			
			
//			NodeList taskList = doc.getElementsByTagName("Task");
			
//			for (int i = 0; i < taskList.getLength(); i++) {
//				Node task = taskList.item(i);
//			}
			
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
		
		String taskString = "";
		
		for (Task task : this.tasks) {
			taskString += "\n     " + task.getTitle();
		}
		
		String quest = ("Title: " + this.title + 
						"\nDescription: " + this.description + 
						"\nProgress: " + this.progress +
						"\nTasks: " + taskString);
		
		return quest;
	}

}
