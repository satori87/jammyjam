package com.bg.jammyjam.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.gui.Scene;
import com.bg.jammyjam.core.JammyJam;

public class OptionsScene extends Scene {


	@Override
	public void start() {
		super.start();
		addLabel("title", JammyJam.GAME_WIDTH / 2, 64, 2f, "Controls", Color.WHITE, true);
		//addFrame("f",400,500,200,200,false,false);
		//addButton("oops", 200, 200, 256, 48, "OOPS");
		addLabel("Movement", JammyJam.GAME_WIDTH / 2, 128, 2f, "Movement: Arrow Keys", Color.WHITE, true);
		addLabel("Time", JammyJam.GAME_WIDTH / 2, 192, 2f, "Fast Forward Time: Tab", Color.WHITE, true);
		addLabel("Menu", JammyJam.GAME_WIDTH / 2, 256, 2f, "Escape: Menu", Color.WHITE, true);
		
		
		addLabel("Credits:", JammyJam.GAME_WIDTH / 2, 384, 2f, "Credits:", Color.WHITE, true);
		addLabel("Bear", JammyJam.GAME_WIDTH / 2, 448, 2f, "Michael Whitlock (Bearable Games)", Color.WHITE, true);
		addLabel("Joker", JammyJam.GAME_WIDTH / 2, 512, 2f, "Christian Vanderbeck (Bearable Games)", Color.WHITE, true);
		addLabel("Mac", JammyJam.GAME_WIDTH / 2, 575, 2f, "Mac Canepi (Bearable Games)", Color.WHITE, true);
		addLabel("Infinite", JammyJam.GAME_WIDTH / 2, 640, 2f, "Faris (Wheeler Games)", Color.WHITE, true);
		addLabel("Santorno", JammyJam.GAME_WIDTH / 2, 704, 2f, "Ahmed Moustafa", Color.WHITE, true);
		
		
		//addFrame("topframe", )
		
		//addFrame(this)
		
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
		if(id.equals("oops")) {
			Scene.change("menu");
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
