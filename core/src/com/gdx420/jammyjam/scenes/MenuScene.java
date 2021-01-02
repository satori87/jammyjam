package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.AudioManager;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.SystemSettings;

public class MenuScene extends Scene {
	private static String status = "";
	
	public MenuScene() {}
	
	public void start() {
		super.start();
		setupDisplay();
		AudioManager.playMenuMusic();
	}
	
	public void setupDisplay() {
		int halfWidth = JammyJam.GAME_WIDTH / 2;
		int halfHeight = JammyJam.GAME_HEIGHT / 2;
		int yOffset = 64;
		int halfYOffset = yOffset / 2;
		int buttonWidth = 256;
		int buttonHeight = 48;
		int frameWidth = 288;
		int frameHeight = 300;
		
		addLabel("title", halfWidth, halfHeight - 200, 4f, JammyJam.game.getGameName(), Color.WHITE, true);
		addLabel("status", halfWidth, halfHeight + 256, 2f, status, Color.WHITE, true);
		
		if(SystemSettings.getMapEditorAvailable()) {		
			addFrame("frame", halfWidth, halfHeight + yOffset + halfYOffset, frameWidth, frameHeight + yOffset + halfYOffset, true, true);
		} else {
			addFrame("frame", halfWidth, halfHeight + yOffset, frameWidth, frameHeight, true, true);
		}
		
		int indexCount = 0;
		int buttonYOffsets[] = {
				halfHeight - (halfYOffset + yOffset) + yOffset,
				halfHeight - halfYOffset + yOffset,
				halfHeight + halfYOffset + yOffset,
				halfHeight + (halfYOffset + yOffset) + yOffset,
				halfHeight + (halfYOffset + yOffset * 2) + yOffset
				};		
						
		addButton("play", halfWidth,  buttonYOffsets[indexCount++], buttonWidth, buttonHeight, "Play Demo");
		if(SystemSettings.getMapEditorAvailable()) {
			addButton("editMap", halfWidth, buttonYOffsets[indexCount++], buttonWidth, buttonHeight, "Edit Map");
		}
		addButton("controls", halfWidth, buttonYOffsets[indexCount++], buttonWidth, buttonHeight, "Controls");
		addButton("options", halfWidth, buttonYOffsets[indexCount++], buttonWidth, buttonHeight, "Options");
		addButton("quit", halfWidth, buttonYOffsets[indexCount++], buttonWidth, buttonHeight, "Quit");
	}
	

	public void update() {
	}

	public void render() {
		getLabel("status").text = status;
	}

	@Override
	public void buttonPressed(String id) {	
		switch (id) {
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
