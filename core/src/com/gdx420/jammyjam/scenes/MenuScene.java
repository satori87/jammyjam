package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.JammyJam;

public class MenuScene extends Scene {

	public static String status = "";

	public MenuScene() {

	}

	public void start() {
		super.start();
		int hw = JammyJam.GAME_WIDTH / 2;
		int hh = JammyJam.GAME_HEIGHT / 2;
		int y = 64;

		addLabel("status", hw, hh + 256, 2f, "", Color.WHITE, true);
		addFrame("frame", hw, hh - 60 + y, 288, 392, true, true);

		addLabel("title", hw, hh - 182 + y, 3f, "Jammy Jam", Color.WHITE, true);

		addButton("play", hw,  hh - 96 + y, 256, 48, "Test Play");
		addButton("lol", hw, hh - 32 + y, 256, 48, "Edit Map");
		addButton("options", hw, hh + 32 + y, 256, 48, "Options");
		addButton("quit", hw, hh + 96 + y, 256, 48, "Quit");

		
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
			//Scene.change("play");
			Scene.change("sleepPlayScene");
			break;
		case "lol":
			Scene.change("edit");
			break;
		case "options":
			Scene.change("options");
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
