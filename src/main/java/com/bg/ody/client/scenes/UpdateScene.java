package com.bg.ody.client.scenes;

import com.bg.bearplane.gui.Scene;
import com.bg.ody.shared.Shared;

public class UpdateScene extends Scene {

	long update = 0;
	float progress = 0;

	public void start() {
		super.start();
	}

	public void update() {
		
	}

	public void render() {
		super.render();
		//if (tick > update) {
		//	update = tick + 100;
		//	progress = BearGame.getAssetLoadProgress();
		//}
		drawFont(0, Shared.GAME_WIDTH / 2, Shared.GAME_HEIGHT / 2, "Updating", true, 3f);

	}

	@Override
	public void buttonPressed(int id) {

	}

	@Override
	public void enterPressedInField(int id) {

	}

}
