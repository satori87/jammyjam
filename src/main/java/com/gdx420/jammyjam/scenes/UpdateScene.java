package com.gdx420.jammyjam.scenes;

import com.bg.bearplane.gui.Scene;
import com.gdx420.jammyjam.core.JammyJam;

public class UpdateScene extends Scene {

	long update = 0;
	float progress = 0;

	public void start() {
		super.start();
	}

	public void update() {
		
	}

	public void render() {
		//if (tick > update) {
		//	update = tick + 100;
		//	progress = BearGame.getAssetLoadProgress();
		//}
		drawFont(0, JammyJam.GAME_WIDTH / 2, JammyJam.GAME_HEIGHT / 2, "Updating", true, 3f);

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

}
