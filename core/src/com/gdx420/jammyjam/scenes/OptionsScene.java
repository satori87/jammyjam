package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.JammyJam;

public class OptionsScene extends Scene {


	@Override
	public void start() {
		super.start();
		addLabel("title", JammyJam.GAME_WIDTH / 2, 64, 2f, "Options", Color.WHITE, true);
		addFrame("f",400,500,200,200,false,false);
		addButton("oops", 200, 200, 256, 48, "OOPS");
		addField("text1", 5, 0, 100, 100, 80);
		addField("text2", 5, 1, 400, 100, 80);
		addField("text3", 5, 2, 400, 200, 80);
		
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
