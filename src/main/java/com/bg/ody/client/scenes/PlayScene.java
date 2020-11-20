package com.bg.ody.client.scenes;

import com.badlogic.gdx.Input.Keys;
import com.bg.bearplane.gui.Scene;
import com.bg.ody.client.core.Assets;

public class PlayScene extends Scene {
	
	int x = 200;
	int y = 300;

	public PlayScene() {

	}

	public void start() {
		super.start();
		
	}

	public void update() {
		super.update();
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
		super.render();
		draw(Assets.textures.get("sprites"), x,y,64,0,32,32);
	}

	@Override
	public void buttonPressed(int id) {		
		
	}

	@Override
	public void enterPressedInField(int id) {

	}

}
