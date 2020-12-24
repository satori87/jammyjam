package com.gdx420.jammyjam.scenes;

import java.time.LocalTime;

import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.Assets;
import com.gdx420.jammyjam.core.JammyJam;

public class PlayScene extends LiveMapScene {
	
	LocalTime startSleepTime = LocalTime.of(22,  0);
	LocalTime startAwakeTime = LocalTime.of(8,  0);
	
	public PlayScene() {

	}

	public void start() {
		super.start();
		
	}

	public void update() {
		super.checkKeys();
	}
	
	void checkKeys() {
		super.checkKeys();
		
		if (input.keyDown[Keys.UP]) {
			JammyJam.game.player.y -= 1.0f;
		} else 
			if(input.keyDown[Keys.DOWN]) {
			JammyJam.game.player.y += 1.0f;
		}
		if(input.keyDown[Keys.LEFT]) {
			JammyJam.game.player.x -= 1.0f;
		} else if (input.keyDown[Keys.RIGHT]) {
			JammyJam.game.player.x += 1.0f;
		}
		
		if(input.keyDown[Keys.ESCAPE]) {
			Scene.change("menu");
		}
		
	}

	public void render() {
		super.render();

		draw(Assets.textures.get("sprites"), (int)JammyJam.game.player.x,(int)JammyJam.game.player.y,64,0,32,32);
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
