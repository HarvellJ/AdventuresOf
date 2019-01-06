package com.adventuresof.screens;

import java.util.ArrayList;
import java.util.Collections;

import com.adventuresof.game.character.NPC;
import com.adventuresof.game.character.Player;
import com.adventuresof.game.inventory.Inventory;
import com.adventuresof.game.inventory.InventoryActor;
import com.adventuresof.game.quest.Quest;
import com.adventuresof.game.quest.QuestInfoActor;
import com.adventuresof.game.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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
	

	
	public PlayerHUD(Camera camera, final Player player) {
		viewport = new ScreenViewport(camera);
		this.stage = new Stage(viewport);
		uiTable = new Table();
		uiTable.setFillParent(true);
		stage.addActor(uiTable);
		DragAndDrop dragAndDrop = new DragAndDrop();
		final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
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
		
		//setup continue button.
		this.button = new TextButton("Click to continue",skin);
		this.button.setVisible(false);
		
        list = new List<String>(skin);
        String[] strings = new String[player.getQuests().size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = player.getQuests().get(i).getTitle();
 
        }
        
        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();
        
        list.setItems(strings);
		final QuestInfoActor questLogActor = new QuestInfoActor(skin, player);
        
        list.addListener(new ChangeListener() {

			@Override
    		public void changed(ChangeEvent event, Actor actor) {
    			System.out.println(list.getSelected());
    			questLogActor.setQuestInfo(list.getSelected());
    			questLogActor.setPosition(gameWidth / 2 - scrollPane.getWidth() / 4,
		        		gameHeight / 2 - scrollPane.getHeight() / 4);
    			stage.addActor(questLogActor);
    			
    		}
    	});
        

        
        scrollPane = new ScrollPane(list,skin);
        scrollPane.setBounds(0, 0, gameWidth, gameHeight + 100);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setPosition(gameWidth / 2 - scrollPane.getWidth() / 4,
                gameHeight / 2 - scrollPane.getHeight() / 4);
        scrollPane.setTransform(true);
        scrollPane.setScale(0.5f);
        stage.addActor(scrollPane);
        
		//setup pane layout using table
//		Table paneTable = new Table();
//		paneTable.add(textArea).width(800);
//		paneTable.row();
//		paneTable.add(button).bottom().width(800);
		
		//setup actual pane using table
//		ScrollPane pane = new ScrollPane(paneTable, skin);
//		pane.setWidth(800);
		
		uiTable.pad(10);
		uiTable.add().bottom().colspan(3);
		uiTable.add().expandX().expandY();
		uiTable.add(inventoryActor).bottom();
		//uiTable.add(questLogActor).left();
		stage.addActor(textArea);	
		
	}
	
	public String displayChat (NPC npc, GameWorld gameWorld) {
		
		ArrayList<String> conversation = npc.getConversation();
    	
    	String returnValue = "";
    	
    	if (npc.getConversationIndex() < npc.getConversation().size()) {
    		
    		this.textArea.setText(npc.getName() + ": " + conversation.get(npc.getConversationIndex()));
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
