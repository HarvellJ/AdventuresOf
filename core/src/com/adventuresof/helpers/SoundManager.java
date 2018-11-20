package com.adventuresof.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private static Music music;
	private static Sound sound;
	private static float musicVolume = 0.5f;
	public static boolean musicStatus = true;

	// Audio for Music	
	public static void playMusic(String filePath) {
		// Check to see if Music has been turned off by the player
		if(musicStatus == true) {
			if(music != null) {
				music.stop();
				music.dispose();
			}
			music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
			music.setLooping(true);
			music.setVolume(musicVolume);
			music.play();
		}	
	}
	
	// Audio for Music	
	public static void playSoundEffect(String filePath) {
		// Check to see if Music has been turned off by the player
		if(musicStatus == true) {
			
			sound = Gdx.audio.newSound(Gdx.files.internal(filePath));
			sound.play();
		}	
	}
	
	// Set the Music volume value
	public static void setMusicVolume(float volume) {
		musicVolume = volume;
		if(music != null) {
			music.setVolume(musicVolume);	
		}
	}
	
	// Set whether Music will be on or off
	public static void toggleMusic(boolean status) {
		musicStatus = status;
		
		if(music != null) {
			if(musicStatus == true) {
				music.setLooping(true);
				music.setVolume(musicVolume);
				music.play();
			} else {
					music.stop();
			}
		}	
	}
	public static void dispose() {
		if(music != null) {
			music.dispose();

}

	}
}