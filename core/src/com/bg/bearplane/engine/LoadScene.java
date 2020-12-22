package com.bg.bearplane.engine;

import com.bg.bearplane.gui.Scene;

public class LoadScene extends Scene {

	Bearable game;

	long update = 0;
	float progress = 0;

	public void start() {
		super.start();
	}

	public void updateBase() {
		Bearplane.updateAssetManager();
		if (Bearplane.isAssetLoadingDone()) {
			Bearplane.loadAssets();
			Bearplane.loaded();
		} else {
			super.updateBase();
		}
	}

	public void render() {
		if (tick > update) {
			update = tick + 100;
			progress = Bearplane.getAssetLoadProgress();
		}
		drawFont(0, game.getGameWidth() / 2, game.getGameHeight() / 2, (int) (progress * 100f) + "%", true, 3f);

	}

	@Override
	public void buttonPressed(String id) {

	}

	@Override
	public void enterPressedInField(String id) {

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
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
