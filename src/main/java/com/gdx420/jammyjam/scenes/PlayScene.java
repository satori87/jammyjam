package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;

public class PlayScene extends Scene {
	
	int x = 200;
	int y = 300;

	public PlayScene() {

	}

	public void start() {
		super.start();
		
	}

	public void update() {
		checkKeys();
	}
	
	private void checkKeys() {
		if (input.keyDown[Keys.UP]) {
			y -= 1;
		} else if(input.keyDown[Keys.DOWN]) {
			y += 1;
		}
		if(input.keyDown[Keys.LEFT]) {
			x -= 1;
		} else if (input.keyDown[Keys.RIGHT]) {
			x += 1;
		}
		
		if(input.keyDown[Keys.ESCAPE]) {
			Scene.change("menu");
		}
		
	}

	public void render() {
		draw(Assets.textures.get("sprites"), x,y,64,0,32,32);
	}

	@Override
	public void buttonPressed(String id) {		
		
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
