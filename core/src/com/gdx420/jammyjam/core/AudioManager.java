package com.gdx420.jammyjam.core;

import com.badlogic.gdx.audio.Sound;

public class AudioManager {
	private static Sound musicLoop = null;
	
	public static void stopMusic() {
		if(musicLoop != null)
			musicLoop.stop();
	}
	
	public static void playMenuMusic() {
		stopMusic();
		if(SystemSettings.getMusicEnabled()) {
			musicLoop = Assets.sounds.get("Dream_Music1");
			musicLoop.loop();
		}
	}
	
	public static void playSleepMusic() {
		stopMusic();
		if(SystemSettings.getMusicEnabled()) {
			musicLoop = Assets.sounds.get("Dream_Music2");
			musicLoop.loop();
		}
	}
	
	public static void playAwakeMusic() {
		stopMusic();
		if(SystemSettings.getMusicEnabled()) {
			musicLoop = Assets.sounds.get("Dream_Music1");
			musicLoop.loop();
		}
	}
	
	public static void playWinMusic(){
		stopMusic();
		if(SystemSettings.getMusicEnabled()) {
			musicLoop = Assets.sounds.get("Pursuit_seamless");
			musicLoop.loop();
		}
	}

}
