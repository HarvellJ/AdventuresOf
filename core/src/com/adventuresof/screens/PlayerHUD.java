package com.adventuresof.screens;

import java.util.ArrayList;
import java.util.Collections;

import com.adventuresof.game.character.entities.NPC;
import com.adventuresof.game.character.entities.Player;
import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.inventory.InventoryActor;
import com.adventuresof.game.quest.ProgressEnum;
import com.adventuresof.game.quest.Quest;
import com.adventuresof.game.quest.QuestInfoActor;
import com.adventuresof.game.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerHUD implements Screen{

	private InventoryActor inventoryActor;
	private QuestInfoActor questInfoActor;
	public  Stage stage;
	private Viewport viewport;
	protected Table uiTable;
	private TextArea textArea;
	private int conversationIndex;
	private TextButton button;
	private Dialog chatBox;
	private ScrollPane scrollPane;
	private List<String> list;
    private SpriteBatch batcher;
    float gameWidth, gameHeight;
	private Player player;
	private final Skin skin;
	
	TextArea coolDown3;
	TextArea coolDown1;
	TextArea coolDown2;
	TextArea coolDown4;

	public PlayerHUD(Camera camera, final Player player) {
		viewport = new ScreenViewport(camera);
		this.stage = new Stage(viewport);
		uiTable = new Table();
		uiTable.setFillParent(true);
		stage.addActor(uiTable);
		DragAndDrop dragAndDrop = new DragAndDrop();
		this.skin = new Skin(Gdx.files.internal("uiskin.json"));
		inventoryActor = new InventoryActor(player.getInventory(), dragAndDrop, skin);
		//questLogActor = new QuestLogActor(skin);
		this.player = player;
//		stage.addActor(inventoryActor);
		
		//setup textArea
		this.textArea = new TextArea("", skin);
		this.textArea.setDisabled(true);
		this.textArea.setText("");
		this.textArea.setWidth(800);
		this.textArea.setHeight(120);
		
		this.coolDown1 = new TextArea("", skin);
		this.coolDown2 = new TextArea("", skin);
		this.coolDown3 = new TextArea("", skin);
		this.coolDown4 = new TextArea("", skin);
		
		list = new List<String>(skin);
		String[] strings = new String[player.getQuests().size()];
		for (int i = 0; i < strings.length; i++) {
							
				switch(player.getQuests().get(i).getProgress()) {
				
				case COMPLETE : strings[i] = player.getQuests().get(i).getTitle() + " (COMPLETE)";
					break;
				case IN_PROGRESS : strings[i] = player.getQuests().get(i).getTitle() + " (IN PROGRESS)";
					break;
				case INCOMPLETE: strings[i] = player.getQuests().get(i).getTitle() + " (INCOMPLETE)";
					break;
			}
			
		}
		
		gameWidth = Gdx.graphics.getWidth();
		gameHeight = Gdx.graphics.getHeight();
		
		list.setItems(strings);
		final QuestInfoActor questInfoActor = new QuestInfoActor(skin, player);
		
		list.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println(list.getSelected());
				questInfoActor.setQuestInfo(list.getSelected().substring(0, list.getSelected().indexOf("(")-1));
				questInfoActor.setPosition(gameWidth / 2 - scrollPane.getWidth() / 4,
						gameHeight / 2 - scrollPane.getHeight() / 4);
				
				stage.addActor(questInfoActor);
			}
		});
		
		scrollPane = new ScrollPane(list,skin);
		scrollPane.setBounds(0, 0, gameWidth, gameHeight + 100);
		scrollPane.setSmoothScrolling(false);
		scrollPane.setPosition(gameWidth / 2 - scrollPane.getWidth() / 4,
				gameHeight / 2 - scrollPane.getHeight() / 4);
		scrollPane.setTransform(true);
		scrollPane.setScale(0.5f);
		scrollPane.setVisible(false);
		stage.addActor(scrollPane);
		
		//setup Achievement Diary button.
		this.button = new TextButton("Achievement Diary",skin);
		this.button.setVisible(true);
		final Skin listSkin = new Skin(Gdx.files.internal("uiskin.json"));
		listSkin.getFont("default-font").getData().setScale(2);
		list = new List<String>(listSkin);
		
		button.addListener( new ClickListener() {
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		        if (button.isChecked()) {
		    		
		    		String[] strings = new String[player.getQuests().size()];
		    		for (int i = 0; i < strings.length; i++) {
		    							
		    				switch(player.getQuests().get(i).getProgress()) {
		    				
		    				case COMPLETE : strings[i] = player.getQuests().get(i).getTitle() + " (COMPLETE)";
		    					break;
		    				case IN_PROGRESS : strings[i] = player.getQuests().get(i).getTitle() + " (IN PROGRESS)";
		    					break;
		    				case INCOMPLETE: strings[i] = player.getQuests().get(i).getTitle() + " (INCOMPLETE)";
		    					break;
		    			}
		    			
		    		}

		    		list.setItems(strings);
		    		list.pack();		    		
		    		list.addListener(new ChangeListener() {
		    			
		    			@Override
		    			public void changed(ChangeEvent event, Actor actor) {
		    				System.out.println(list.getSelected());
		    			
		    					questInfoActor.setQuestInfo(list.getSelected().substring(0, list.getSelected().indexOf("(")-1));
		    					questInfoActor.setPosition(gameWidth / 2 - scrollPane.getWidth() / 4,
		    							gameHeight / 2 - scrollPane.getHeight() / 4);
		    					
		    					stage.addActor(questInfoActor);
		    					
		    			}
		    		});
		    		
		    		scrollPane.setActor(list);
		    		
		        	scrollPane.setVisible(true);
		        } else {
		        	scrollPane.setVisible(false);
		        	questInfoActor.setVisible(false);
		        }
		    }
		} );
		
        //scrollPane.setScale(0.5f);
        
		//setup pane layout using table
//		Table paneTable = new Table();
//		paneTable.add(textArea).width(800);
//		paneTable.row();
//		paneTable.add(button).bottom().width(800);
		
		//setup actual pane using table
//		ScrollPane pane = new ScrollPane(paneTable, skin);
//		pane.setWidth(800);
		
		uiTable.pad(10);
		uiTable.add().colspan(3).expandY();
		uiTable.add().expandX().expandY();
		uiTable.add().bottom().expandY();
		uiTable.row();
		uiTable.add().colspan(3);
		uiTable.add().expandX().expandY();
		uiTable.add(button).bottom();
		uiTable.row();
		uiTable.add().bottom().colspan(3);
		uiTable.add().expandX().expandY();
		uiTable.add(inventoryActor).bottom();
		stage.addActor(textArea);
		
		Texture yourTexture1 = new Texture(player.getCharacterClass().getAbilityOne().getActionBarImage());
		Image image1 = new Image(yourTexture1);
		image1.setWidth(75);
		image1.setHeight(75);
		image1.setPosition(810,
				0);
		stage.addActor(image1);
		
		Texture yourTexture2 = new Texture(player.getCharacterClass().getAbilityTwo().getActionBarImage());
		Image image2 = new Image(yourTexture2);
		image2.setWidth(75);
		image2.setHeight(75);
		image2.setPosition(905,
				0);
		stage.addActor(image2);
		
		Texture yourTexture3 = new Texture(player.getCharacterClass().getAbilityThree().getActionBarImage());
		Image image3 = new Image(yourTexture3);
		image3.setWidth(75);
		image3.setHeight(75);
		image3.setPosition(1000,
				0);
		stage.addActor(image3);
		
		Texture yourTexture4 = new Texture(player.getCharacterClass().getAbilityFour().getActionBarImage());
		Image image4 = new Image(yourTexture4);
		image4.setWidth(75);
		image4.setHeight(75);
		image4.setPosition(1095,
				0);
		stage.addActor(image4);
		
	}
	
	public String displayChat (NPC npc, GameWorld gameWorld) {
		
		ArrayList<String> conversation = npc.getConversation();
    	
    	String returnValue = "";
    	
    	if (npc.getConversationIndex() < npc.getConversation().size()) {
    		
    		this.textArea.setText(conversation.get(npc.getConversationIndex()));
			npc.setConversationIndex(npc.getConversationIndex() + 1);
			
			returnValue = "" + npc.getConversationIndex();
			
    	} else if (npc.getConversationIndex() == npc.getConversation().size()) {
    		this.textArea.setText("");
	    	npc.setConversationIndex(0);
	    	returnValue =  "end";

    	}
    	
		return returnValue;
    	
	}
	
	
	public void displayText (String text) {
		this.textArea.setText(text);
	}

	public void toggleInventory() {
		if(inventoryActor.isVisible()) {
			  inventoryActor.setVisible(false);

		}else {
			  inventoryActor.setVisible(true);
		}
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void render(float delta) {
		//update inventory
		if ((int)(System.currentTimeMillis() - player.getAbilityOneLastActivated())/1000 <= player.getCharacterClass().getAbilityOne().getCoolDown().getCoolDownDuration()/1000) {
			coolDown1.setText(Long.toString((int)(player.getAbilityOneLastActivated() - System.currentTimeMillis() + player.getCharacterClass().getAbilityOne().getCoolDown().getCoolDownDuration())/1000));
			coolDown1.setColor(Color.RED);
		} else {
			coolDown1.setText("Ready!");
			coolDown1.setColor(Color.GREEN);
		}
		coolDown1.setPosition(810, 80);
		coolDown1.setWidth(75);
		stage.addActor(coolDown1); 

	    
		if ((int)(System.currentTimeMillis() - player.getAbilityTwoLastActivated())/1000 <= player.getCharacterClass().getAbilityTwo().getCoolDown().getCoolDownDuration()/1000) {
			coolDown2.setText(Long.toString((int)(player.getAbilityTwoLastActivated()- System.currentTimeMillis() + player.getCharacterClass().getAbilityTwo().getCoolDown().getCoolDownDuration())/1000));
			coolDown2.setColor(Color.RED);
		} else {
			coolDown2.setText("Ready!");
			coolDown2.setColor(Color.GREEN);
		}
		coolDown2.setPosition(905, 80);
		coolDown2.setWidth(75);
		stage.addActor(coolDown2); 

	    
		if ((int)(System.currentTimeMillis() - player.getAbilityThreeLastActivated())/1000 <= player.getCharacterClass().getAbilityThree().getCoolDown().getCoolDownDuration()/1000) {
			coolDown3.setText(Long.toString((int)(player.getAbilityThreeLastActivated() - System.currentTimeMillis() + player.getCharacterClass().getAbilityThree().getCoolDown().getCoolDownDuration())/1000));
			coolDown3.setColor(Color.RED);
		} else {
			coolDown3.setText("Ready!");
			coolDown3.setColor(Color.GREEN);
		}
		coolDown3.setPosition(1000, 80);
		coolDown3.setWidth(75);
		stage.addActor(coolDown3); 
	
		
		if ((int)(System.currentTimeMillis() - player.getAbilityFourLastActivated())/1000 <= player.getCharacterClass().getAbilityFour().getCoolDown().getCoolDownDuration()/1000) {
			coolDown4.setText(Long.toString((int)(player.getAbilityFourLastActivated() - System.currentTimeMillis() + player.getCharacterClass().getAbilityFour().getCoolDown().getCoolDownDuration())/1000));
			coolDown4.setColor(Color.RED);
		} else {
			coolDown4.setText("Ready!");
			coolDown4.setColor(Color.GREEN);
		}
		coolDown4.setPosition(1095, 80);
		coolDown4.setWidth(75);
		stage.addActor(coolDown4); 
		stage.act();
	    stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public int getConversationIndex () {
		return this.conversationIndex;
	}
	
	public TextButton getButton () {
		return this.button;
	}
	
	public Dialog getChatBox () {
		return this.chatBox;
	}
}