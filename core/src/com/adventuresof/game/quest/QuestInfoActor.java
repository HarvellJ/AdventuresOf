package com.adventuresof.game.quest;

import com.adventuresof.game.character.entities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Quest Actor Class
 * 
 */
public class QuestInfoActor extends Window {
	
	ScrollPane scrollPane;
    List<String> list;
    SpriteBatch batcher;
    float gameWidth, gameHeight;
    private Stage stage;
	private String title;
	private String description;
	private java.util.List<Task> tasks;
	private TextArea textArea;
	private Player player;
	private Skin skin;
 
    public QuestInfoActor(Skin skin, Player player) {
    	super("Quest", skin);
    	
    	this.player = player;
    	this.skin = skin;
    	gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();
    	   	
    }
    
    public void setQuestInfo(String selectedQuestTitle) {
    	for (Quest quest : player.getQuests()) { 
			
			if(quest.getTitle().equals(selectedQuestTitle)) {

				this.title = quest.getTitle();
				this.description = quest.getDescription();
				this.tasks = quest.getTasks();
			
				clear();
				
				Table uiTable = new Table(skin);
				uiTable.setFillParent(true);
				uiTable.pad(10);
				uiTable.row();
				uiTable.add("");
				uiTable.row();
				uiTable.add(this.title);
				uiTable.row();
				uiTable.add(this.description).expandX().expandY();
				uiTable.row();
				uiTable.add().bottom();
				
				int i = 0;
				
				for (Task task : this.tasks) {
					
					i++;
					
					if (task.getProgress().equals(ProgressEnum.INCOMPLETE)) {
						String taskDescription = task.getDescription() == null ? "" : " - " + task.getDescription();
						
						uiTable.row();
						uiTable.add("Step " + i + ": " + task.getTitle() + taskDescription);
						break;
					} 
//					else {
//						uiTable.row();
//						uiTable.add("Quest Complete!");
//						break;
//					}
					
				}
				
				add(uiTable);
								
		        pack();
				
		        setVisible(true);
			}
			
		}
    }

}


