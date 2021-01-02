package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.AudioManager;
import com.gdx420.jammyjam.core.DialogData;
import com.gdx420.jammyjam.core.DialogQueue;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.SystemSettings;

public class WinScene extends Scene {
	
	public WinScene() {}
	
	public void start() {
		super.start();
		setupDisplay();
		AudioManager.playWinMusic();
	}
	
	public void setupDisplay() {
		
		int halfWidth = JammyJam.GAME_WIDTH / 2;
		int halfHeight = JammyJam.GAME_HEIGHT / 2;
		int yOffset = 64;
		int halfYOffset = yOffset / 2;
		int buttonWidth = 256;
		int buttonHeight = 48;
		int frameWidth = 700;
		int frameHeight = 300;
		
		
		addLabel("title", halfWidth, halfHeight - 200, 4f, JammyJam.game.getGameName(), Color.WHITE, true);
		addFrame("frame", halfWidth, halfHeight + yOffset, frameWidth, frameHeight, true, true);
		addLabel("winText1", halfWidth, halfHeight - halfYOffset, 2f, "Congrats kid, you snagged", Color.WHITE, true);		
		addLabel("winText2", halfWidth, halfHeight, 2f, "the baddie and saved the day.", Color.WHITE, true);
		addLabel("winText3", halfWidth, halfHeight + halfYOffset, 2f, "Keep an eye out for the full", Color.WHITE, true);
		addLabel("winText42", halfWidth, halfHeight + yOffset, 2f, "version of this game someday.", Color.WHITE, true);
		addButton("quit", halfWidth, halfHeight + yOffset + halfYOffset + yOffset, buttonWidth, buttonHeight, "Quit");
	}
	

	public void update() {
	}

	public void render() {
	}

	@Override
	public void buttonPressed(String id) {	
		switch (id) {
		/*
		case "play":
			Scene.change("sleepPlayScene");
			AudioManager.playSleepMusic();
			break;
		case "editMap":
			Scene.change("edit");
			break;
		case "options":
			Scene.change("options");
			break;
		case "controls":
			Scene.change("controls");
			break;
			*/
		case "quit":
			System.exit(0);
			break;
		}
	}

	@Override
	public void enterPressedInField(String id) {
		
	}

	@Override
	public void enterPressedInList(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listChanged(String id, int sel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDown(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBox(String id) {
		// TODO Auto-generated method stub
		
	}

}
