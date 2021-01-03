package com.gdx420.jammyjam.scenes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.JammyJam;

public class ControlsScene extends Scene {


	@Override
	public void start() {
		super.start();
		int wordSpace = 50;
		int count = 1;
		float font = 2f;
		int center = JammyJam.GAME_WIDTH / 2;
		addLabel("title", center, wordSpace * count++, font, "Controls", Color.WHITE, true);
		Color controlsColor = Color.YELLOW;
		
		addLabel("Movement", center, wordSpace * count++, font, "Movement: Arrow Keys", controlsColor, true);
		addLabel("Space", center, wordSpace * count++, font, "Talk/Inspect: Space Bar", controlsColor, true);
		addLabel("Return", center, wordSpace * count++, font, "Dialog Confirm: Enter/Return", controlsColor, true);
		addLabel("Time", center, wordSpace * count++, font, "Fast Forward Time: Tab", controlsColor, true);
		addLabel("Menu", center, wordSpace * count++, font, "Menu: Escape", controlsColor, true);
		
		Color creditsColor = Color.BLUE;
		int creditStart = 500;
		count = 0;
		font -= 0.2f;
		wordSpace = 45;
		addLabel("Credits", center, creditStart + wordSpace * count++, font, "Credits", Color.GRAY, true);
		addLabel("Bear", center, creditStart + wordSpace * count++, font, "Michael Whitlock (Bearable Games)", creditsColor, true);
		addLabel("Joker", center, creditStart + wordSpace * count++, font, "Christian Vanderbeck (Bearable Games)", creditsColor, true);
		addLabel("Mac", center, creditStart + wordSpace * count++, font, "Mac Canepi (Bearable Games)", creditsColor, true);
		addLabel("Infinite", center, creditStart + wordSpace * count++, font, "Faris (Wheeler Games)", creditsColor, true);
		addLabel("Santorno", center, creditStart + wordSpace * count++, font, "Ahmed Moustafa", creditsColor, true);		
		
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
