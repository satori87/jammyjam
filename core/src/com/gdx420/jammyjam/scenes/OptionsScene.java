package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.AudioManager;
import com.gdx420.jammyjam.core.JammyJam;
import com.gdx420.jammyjam.core.SystemSettings;

public class OptionsScene extends Scene {

	@Override
	public void start() {
		super.start();
		int halfWidth = JammyJam.GAME_WIDTH / 2;
		addLabel("title", halfWidth, 100, 2f, "Options", Color.WHITE, true);
		Color optionsColor = Color.YELLOW;
		addLabel("Music", halfWidth, 200, 2f, "Music: " + (SystemSettings.getMusicEnabled() ? "ON" : "OFF") , optionsColor, true);

		int buttonWidth = 256;
		int buttonHeight = 48;
		addButton("disableMusic", halfWidth, 300, buttonWidth, buttonHeight, "Disable Music");
		
	}
	
	public void update() {
		if(input.keyDown[Keys.ESCAPE]) {
			Scene.change("menu");
		}
	}
	
	public void render() {
		
	}

	@Override
	public void buttonPressed(String id) {
		switch (id) {
			case "disableMusic":
				SystemSettings.setMusicEnabled(false);
				AudioManager.stopMusic();
				getLabel("Music").text = "Music: OFF";			
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
