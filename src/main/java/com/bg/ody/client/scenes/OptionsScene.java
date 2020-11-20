package com.bg.ody.client.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.gui.Button;
import com.bg.bearplane.gui.CheckBox;
import com.bg.bearplane.gui.Label;
import com.bg.bearplane.gui.Scene;
import com.bg.ody.client.core.Options;
import com.bg.ody.shared.Shared;

public class OptionsScene extends Scene {

	/*
	 * 
	 * Chat dock: auto/top/bottom -auto chat bg: color/opacity/(texture??)
	 * -black,50% chat font size - 1f timestamp on/off 12/24 hour format default
	 * chat mode - mapsay
	 * 
	 * KEY MAPS chat key broadcast key up key -dpad up down key -dpad down left key
	 * -dpad left right key -dpad right attack key -control run key -shift interact
	 * key - /
	 * 
	 * 
	 * ADMIN KEY MAPS edit map key - f4 edit monster key f6
	 */
	Options options;

	Button[] btnChatDock = new Button[3];

	CheckBox chkChatBG;

	Label lblOpacity;

	Label lblChatFontSize;
	CheckBox chkChatUseTimestamp;
	CheckBox chkChatUse24Hour;
	Button[] btnChatDefaultChannel = new Button[2];

	@Override
	public void start() {
		super.start();
		labels.add(new Label(this, Shared.GAME_WIDTH / 2, 64, 2f, "Options", Color.WHITE, true));
		
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
