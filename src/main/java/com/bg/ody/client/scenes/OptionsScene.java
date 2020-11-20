package com.bg.ody.client.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.gui.Button;
import com.bg.bearplane.gui.Label;
import com.bg.bearplane.gui.Scene;
import com.bg.ody.client.core.Prefs;

public class OptionsScene extends Scene {


	@Override
	public void start() {
		super.start();
		labels.add(new Label(this, Prefs.GAME_WIDTH / 2, 64, 2f, "Options", Color.WHITE, true));
		
		buttons.add(new Button(this, 0, 500, 500, 256, 48, "OOPS"));
		
	}
	
	public void update() {
		if(input.keyDown[Keys.ESCAPE]) {
			Scene.change("menu");
		}
	}
	
	public void render() {
		
	}

	@Override
	public void buttonPressed(int id) {
		if(id == 0) {
			Scene.change("menu");
		}
	}

	@Override
	public void enterPressedInField(int id) {

	}

}
