package com.mygdx.game;

import com.adventuresof.helpers.ApplicationPreferences;
import com.adventuresof.helpers.SoundManager;
import com.adventuresof.screens.CharacterSelectionScreen;
import com.adventuresof.screens.CreditScreen;
import com.adventuresof.screens.EndScreen;
import com.adventuresof.screens.MainGameScreen;
import com.adventuresof.screens.MainMenuScreen;
import com.adventuresof.screens.NewGameScreen;
import com.adventuresof.screens.Resources;
import com.adventuresof.screens.ScreenType;
import com.adventuresof.screens.SettingsScreen;
import com.badlogic.gdx.Game;

public class AdventuresOfGame extends Game {
	
	private MainMenuScreen menuScreen;
	private MainGameScreen mainScreen;
	private SettingsScreen settingsScreen;
	private ApplicationPreferences preferences;
	private CharacterSelectionScreen characterSelectionScreen;
	private NewGameScreen newGameScreen;
	private CreditScreen creditScreen;
	private EndScreen endScreen;

    public Resources res;

	@Override public void create () {
        res = new Resources();
		preferences = new ApplicationPreferences();
		if(preferences.isMusicEnabled() == true) {
			SoundManager.toggleMusic(true);
			SoundManager.setMusicVolume(preferences.getMusicVolume());
		} else {
			SoundManager.toggleMusic(false);
			SoundManager.setMusicVolume(preferences.getMusicVolume());
		}		
		menuScreen = new MainMenuScreen(this);
		this.setScreen(menuScreen);
	}

	public void render () {
		super.render();
	}
	
	public void changeScreen(ScreenType s) {
		switch (s) {
		case MAINMENU:
			menuScreen = new MainMenuScreen(this);
			this.setScreen(menuScreen);
			break;
		case CHARACTERSELECTION:
			characterSelectionScreen = new CharacterSelectionScreen(this);
			this.setScreen(characterSelectionScreen);
			break;
		case MAINGAMESCREEN:
			if(mainScreen == null)
			mainScreen = new MainGameScreen(this);
			this.setScreen(mainScreen);
			break;
		case SETTINGS:
			settingsScreen = new SettingsScreen(this);
			this.setScreen(settingsScreen);
			break;
		case NEWGAMESCREEN:
			newGameScreen = new NewGameScreen(this);
			this.setScreen(newGameScreen);
			break;
		case CREDITS:
			creditScreen = new CreditScreen(this);
			this.setScreen(creditScreen);
			break;
		default: 
		}
	}
	
	public void showEndScreen(float completionTime) {
		endScreen = new EndScreen(this, completionTime);
		this.setScreen(endScreen);
	}
	
	public ApplicationPreferences getPreferences() {
		return preferences;
	}
	
    @Override
    public void dispose () {
        res.dispose();
    }
}